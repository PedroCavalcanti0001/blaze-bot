package me.pedroeugenio.blazebot.domain

import java.math.BigDecimal

abstract class BaseStrategyProperties(
    open val crashWin: Double, open val valueForProfitCalc: BigDecimal, open val g1Factor: Double
)
