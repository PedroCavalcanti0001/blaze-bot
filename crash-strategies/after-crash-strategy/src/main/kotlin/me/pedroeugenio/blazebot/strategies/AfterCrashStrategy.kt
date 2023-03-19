package me.pedroeugenio.blazebot.strategies

import me.pedroeugenio.blazebot.boundary.output.BlazeCrashStrategyOutput
import me.pedroeugenio.blazebot.domain.*
import me.pedroeugenio.blazebot.domain.aftercrash.ACMostRepeatedIndex
import me.pedroeugenio.blazebot.domain.aftercrash.AfterCrashResult
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class AfterCrashStrategy(val strategy: StrategyEnum = StrategyEnum.AFTER_CRASH) :
    BlazeCrashStrategyOutput {

    override fun check(
        blazeHistory: BlazeHistory<CrashRecord>,
        properties: BaseStrategyProperties
    ): StrategyBase {
        val filter = blazeHistory.records.reversed()

        val playResults = getPlayResults(filter, properties as MatchStrategies.AfterCrashProperties)

        val groups = playResults.groupBy { it.additionFactor }

        val bestPlayFactor = groups.maxByOrNull { it.value.size }

        if (bestPlayFactor?.key == null)
            throw IllegalArgumentException("Ocorreu um erro")

        val g1Results = getG1Results(bestPlayFactor, groups, filter)
        val g1WinsResults = g1Results.filter { it.g1Record!!.crashPoint >= properties.crashWin }
        val normalWins = bestPlayFactor.value.size
        val normalLoss = g1Results.size
        val g1Wins = g1WinsResults.size
        val g1Loss = g1Results.size - g1Wins
        val totalWins = normalWins.plus(g1Wins)
        val signals = playResults.size

        val assertiveness = totalWins.times(100).div(signals)


        val allResults = getAllResults(ArrayList(groups.values.flatten()), g1Results)

        val profiting = checkProfiting(properties,signals = signals,
            normalWins = normalWins,
            normalLoss = normalLoss,
            g1AmountWins = g1Wins,
            g1AmountLoss = g1Loss,)


        return AfterCrashResult(
            signals = signals,
            normalWins = normalWins,
            normalLoss = normalLoss,
            g1Wins = g1Wins,
            g1Loss = g1Loss,
            exitFactor = properties.crashWin,
            assertiveness = assertiveness.toDouble(),
            results = allResults,
            profiting = profiting
        )
    }

    private fun checkProfiting(
        afterCrashProperties: MatchStrategies.AfterCrashProperties,
        signals: Int,
        normalWins: Int,
        normalLoss: Int,
        g1AmountWins: Int,
        g1AmountLoss: Int
    ): BigDecimal {
        val valueForCalc = afterCrashProperties.valueForProfitCalc

        val normalProfit = valueForCalc.multiply(normalWins.toBigDecimal())
        val normalLoss = valueForCalc.multiply(normalLoss.toBigDecimal())
        val g1EntriesValue = valueForCalc.multiply(afterCrashProperties.g1Factor.toBigDecimal())
        val g1WinsProfit = g1EntriesValue.multiply(g1AmountWins.toBigDecimal())
        val g1Losses = g1EntriesValue.multiply(g1AmountLoss.toBigDecimal())
        val totalProfit = normalProfit.plus(g1WinsProfit).minus(normalLoss).minus(g1Losses)
        return totalProfit
    }

    private fun getAllResults(
        allResults: ArrayList<ACMostRepeatedIndex>,
        g1Results: ArrayList<ACMostRepeatedIndex>
    ): ArrayList<ACMostRepeatedIndex> {
        allResults.removeIf { g1Results.map { map -> map.key }.contains(it.key) }
        allResults.addAll(g1Results)
        return allResults
    }

    override fun getStrategyType(): StrategyEnum = strategy

    private fun getG1Results(
        bestPlayFactor: Map.Entry<Int, List<ACMostRepeatedIndex>>?,
        groups: Map<Int, List<ACMostRepeatedIndex>>,
        filter: List<CrashRecord>
    ): ArrayList<ACMostRepeatedIndex> {
        val g1Results = arrayListOf<ACMostRepeatedIndex>()
        bestPlayFactor?.also { key ->
            val differentFactorGroups = groups.filter { filt -> filt.key != bestPlayFactor.key }.values.flatten()
            for (acMostRepeatedIndex in differentFactorGroups) {
                val crashIndex = acMostRepeatedIndex.crashIndex
                val playIndex = crashIndex + key.key
                val g1Index = playIndex + key.key
                if (g1Index < filter.size) {
                    val normalResult = filter[playIndex]
                    val g1Result = filter[g1Index]
                    val result = acMostRepeatedIndex.copy(
                        isG1 = true,
                        g1Record = g1Result,
                        additionFactor = bestPlayFactor.key,
                        playRecord = normalResult
                    )
                    g1Results.add(result)
                }
            }
        }
        return g1Results
    }

    private fun getPlayResults(filter: List<CrashRecord>, afterCrashProperties: MatchStrategies.AfterCrashProperties): ArrayList<ACMostRepeatedIndex> {
        val indexWinList = arrayListOf<ACMostRepeatedIndex>()
        for (record in filter) {
            if (record.crashPoint == 0.0) {
                val crashIndex = filter.indexOf(record)
                for (currPlayTime in 1..10) {
                    val nextIndex = crashIndex + currPlayTime
                    if (filter.size >= nextIndex) {
                        if (nextIndex < filter.size) {
                            val playRecord = filter[nextIndex]
                            if (playRecord.crashPoint >= afterCrashProperties.crashWin) {
                                indexWinList.add(
                                    ACMostRepeatedIndex(
                                        additionFactor = currPlayTime,
                                        crashIndex = crashIndex,
                                        crashRecord = record,
                                        playIndex = nextIndex,
                                        playRecord = playRecord,
                                    )
                                )
                                break
                            }
                        } else
                            break
                    }
                }

            }
        }
        return indexWinList
    }
}