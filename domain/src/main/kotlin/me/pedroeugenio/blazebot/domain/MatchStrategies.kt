package me.pedroeugenio.blazebot.domain

import java.math.BigDecimal

class MatchStrategies(
    val afterCrashProperties: AfterCrashProperties,
    val afterTheeGreens: AfterTheeGreens,
) {
    class AfterCrashProperties(
        override val crashWin: Double,
        override val valueForProfitCalc: BigDecimal,
        override val g1Factor: Double
    ) : BaseStrategyProperties(crashWin, valueForProfitCalc, g1Factor)

    class AfterTheeGreens(
        override val crashWin: Double,
        override val valueForProfitCalc: BigDecimal,
        override val g1Factor: Double
    ) : BaseStrategyProperties(crashWin, valueForProfitCalc, g1Factor)

    fun byStrategyType(strategyEnum: StrategyEnum): BaseStrategyProperties = when (strategyEnum) {
        StrategyEnum.AFTER_CRASH -> afterCrashProperties
        StrategyEnum.AFTER_THREE_GREENS -> afterTheeGreens
    }
}

