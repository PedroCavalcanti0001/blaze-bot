package me.pedroeugenio.blazebot.web.response

import java.math.BigDecimal

abstract class StrategyBaseDTO(
    open val strategyName: StrategyEnumDTO,
    open val assertiveness: Double,
    open val signals: Int,
    open val normalWins: Int,
    open val normalLoss: Int,
    open val g1Wins: Int,
    open val g1Loss: Int,
    open val profiting: BigDecimal,
)