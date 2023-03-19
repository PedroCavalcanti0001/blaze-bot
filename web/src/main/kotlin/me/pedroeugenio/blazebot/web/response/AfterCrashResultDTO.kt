package me.pedroeugenio.blazebot.web.response

import java.math.BigDecimal

class AfterCrashResultDTO(
    val signals: Int,
    val normalWins: Int,
    val normalLoss: Int,
    val g1Wins: Int,
    val g1Loss: Int,
    val exitFactor: Double,
    val assertiveness: Double,
    val additionFactor: Int? = null,
    val strategyName: StrategyEnumDTO,
    val profiting: BigDecimal,
    val results: List<ACMostRepeatedIndexDTO>,
)