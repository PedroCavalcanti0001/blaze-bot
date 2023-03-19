package me.pedroeugenio.blazebot.domain

import java.util.*

open class StrategyRecordBase(
    open val key: UUID = UUID.randomUUID(),
    open val isG1:Boolean = false,
    open val g1Record: CrashRecord? = null,
)