package me.pedroeugenio.blazebot.strategies

import me.pedroeugenio.blazebot.boundary.output.BlazeCrashStrategyOutput
import me.pedroeugenio.blazebot.domain.*
import me.pedroeugenio.blazebot.domain.aftercrash.AfterCrashResult
import me.pedroeugenio.blazebot.domain.threegrens.AfterThreeGreensResult
import me.pedroeugenio.blazebot.domain.threegrens.TGSignal
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class AfterTheeGreens(val strategy: StrategyEnum = StrategyEnum.AFTER_THREE_GREENS) :
    BlazeCrashStrategyOutput {

    override fun check(
        blazeHistory: BlazeHistory<CrashRecord>,
        properties: BaseStrategyProperties
    ): StrategyBase {
        val records = blazeHistory.records.reversed()

        val playResults = getPlayResults(records, properties)

        val wins = playResults.filter { it.playRecord.crashPoint >= properties.crashWin }
        val losses = playResults.filter { it.playRecord.crashPoint < properties.crashWin }
        val normalWins = wins.size
        val normalLosses = losses.size
        val g1Results = getG1Results(records, losses)
        val g1Wins = g1Results.filter { it.g1Record!!.crashPoint >= properties.crashWin }.size
        val g1Loss = g1Results.size - g1Wins
        val totalWins = normalWins.plus(g1Wins)
        val signals = playResults.size

        val assertiveness = totalWins.times(100).div(signals)
        val allResults = getAllResults(playResults, g1Results)

        val profiting = checkProfiting(
            properties, signals = signals,
            normalWins = normalWins,
            normalLoss = normalLosses,
            g1AmountWins = g1Wins,
            g1AmountLoss = g1Loss,
        )

        return AfterThreeGreensResult(
            signals = signals,
            normalWins = normalWins,
            normalLoss = normalLosses,
            g1Wins = g1Wins,
            g1Loss = g1Loss,
            exitFactor = properties.crashWin,
            assertiveness = assertiveness.toDouble(),
            results = allResults,
            profiting = profiting
        )
    }

    private fun getAllResults(allResults: ArrayList<TGSignal>, g1Results: ArrayList<TGSignal>): List<TGSignal> {
        allResults.removeIf { g1Results.map { map -> map.key }.contains(it.key) }
        allResults.addAll(g1Results)
        return allResults
    }

    private fun getG1Results(records: List<CrashRecord>, losses: List<TGSignal>): ArrayList<TGSignal> {
        val signals = arrayListOf<TGSignal>()
        for (loss in losses) {
            val playIndex = records.indexOf(loss.playRecord)
            val nextG1Index = playIndex + 1
            if (records.size > nextG1Index) {
                val g1Record = records[nextG1Index]
                signals.add(
                    loss.copy(
                        g1Record = g1Record,
                        isG1 = true
                    )
                )
            }
        }
        return signals
    }

    private fun getPlayResults(records: List<CrashRecord>, properties: BaseStrategyProperties): ArrayList<TGSignal> {
        val signals = arrayListOf<TGSignal>()
        for (crashRecord in records) {
            if (crashRecord.crashPoint < 2.0) {
                val crashIndex = records.indexOf(crashRecord)
                val nextRecords = arrayListOf<CrashRecord>()
                for (currPlayTime in 1..4) {
                    val nextIndex = crashIndex + currPlayTime
                    if (records.size >= nextIndex) {
                        if (nextIndex < records.size) {
                            val nextRecord = records[nextIndex]
                            if (currPlayTime == 4) {
                                if (nextRecords.all { it.crashPoint > 2.0 }) {
                                    signals.add(
                                        TGSignal(
                                            firstRecord = crashRecord,
                                            playRecord = nextRecord,
                                            nextRecords = nextRecords
                                        )
                                    )
                                }
                            } else
                                nextRecords.add(nextRecord)
                        } else
                            break
                    }
                }
            }
        }
        return signals
    }

    private fun checkMatchNextThreeResults(records: List<CrashRecord>, currIndex: Int): Boolean {
        return records.filter {
            val thisIndex = records.indexOf(it)
            records.size > thisIndex && thisIndex - currIndex <= 3
        }.all { it.crashPoint >= 2.0 }
    }

    private fun checkProfiting(
        afterCrashProperties: BaseStrategyProperties,
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

    override fun getStrategyType(): StrategyEnum = strategy
}