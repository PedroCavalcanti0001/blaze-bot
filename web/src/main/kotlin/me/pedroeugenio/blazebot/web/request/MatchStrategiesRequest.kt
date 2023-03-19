package me.pedroeugenio.blazebot.web.request


data class MatchStrategiesRequest(
    val afterCrashProperties:AfterCrashPropertiesRequest
)
data class AfterCrashPropertiesRequest(val crashWin:Double, val valueForProfitCalc:Double, val g1Factor:Double)
