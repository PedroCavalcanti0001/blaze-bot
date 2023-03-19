package me.pedroeugenio.blazebot.boundary.output

import me.pedroeugenio.blazebot.domain.*
import me.pedroeugenio.blazebot.domain.aftercrash.AfterCrashResult

interface BlazeCrashStrategyOutput {

    fun check(blazeHistory: BlazeHistory<CrashRecord>, properties: BaseStrategyProperties): StrategyBase

    fun getStrategyType(): StrategyEnum
}