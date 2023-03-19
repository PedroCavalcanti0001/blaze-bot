package me.pedroeugenio.blazebot.domain


class GameResults(
    val strategies: Int,
    val afterCrashResults: StrategyBase?,
    val afterTheeGreensResults:StrategyBase?
)