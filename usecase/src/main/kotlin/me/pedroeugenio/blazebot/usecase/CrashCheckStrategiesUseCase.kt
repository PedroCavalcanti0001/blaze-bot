package me.pedroeugenio.blazebot.usecase

import me.pedroeugenio.blazebot.boundary.input.CrashCheckStrategiesInput
import me.pedroeugenio.blazebot.boundary.output.BlazeCrashStrategyOutput
import me.pedroeugenio.blazebot.domain.GameResults
import me.pedroeugenio.blazebot.domain.MatchStrategies
import me.pedroeugenio.blazebot.domain.StrategyEnum
import me.pedroeugenio.blazebot.domain.aftercrash.AfterCrashResult
import me.pedroeugenio.blazebot.domain.threegrens.AfterThreeGreensResult
import me.pedroeugenio.blazebot.feign.client.BlazeClient
import me.pedroeugenio.blazebot.feign.response.toCrashDomain
import org.springframework.stereotype.Service


@Service
class CrashCheckStrategiesUseCase(
    private val strategies: List<BlazeCrashStrategyOutput>,
    private val blazeClient: BlazeClient
) :
    CrashCheckStrategiesInput {
    override fun getStrategies(toDomain: MatchStrategies): GameResults {
        val crashHistory = blazeClient.getCrashistory()
        var afterCrashResult: AfterCrashResult? = null
        var afterThreeGreensResult: AfterThreeGreensResult? = null
        crashHistory.body?.toCrashDomain()?.let { records ->
            for (strategy in strategies) {
                val check = strategy.check(records, toDomain.byStrategyType(strategy.getStrategyType()))
                when (strategy.getStrategyType()) {
                    StrategyEnum.AFTER_CRASH ->
                        afterCrashResult = check as AfterCrashResult

                    StrategyEnum.AFTER_THREE_GREENS ->
                        afterThreeGreensResult = check as AfterThreeGreensResult


                }
            }
        }
        return GameResults(
            strategies.size,
            afterCrashResults = afterCrashResult,
            afterTheeGreensResults = afterThreeGreensResult
        )
    }

}