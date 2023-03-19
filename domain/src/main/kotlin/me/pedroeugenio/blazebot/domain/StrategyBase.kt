package me.pedroeugenio.blazebot.domain

import java.math.BigDecimal

abstract class StrategyBase(
    open val strategyName: StrategyEnum,
    open val assertiveness: Double,
    open val signals: Int,
    open val normalWins: Int,
    open val normalLoss: Int,
    open val g1Wins: Int,
    open val g1Loss: Int,
    open val profiting: BigDecimal,
)