package me.pedroeugenio.blazebot.web.response

import java.util.*

open class StrategyRecordBaseDTO(
    open val key: UUID = UUID.randomUUID(),
    open val isG1:Boolean = false,
    open val g1Record: CrashRecordDTO? = null,
)