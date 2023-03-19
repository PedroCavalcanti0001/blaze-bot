package me.pedroeugenio.blazebot.web.response

import me.pedroeugenio.blazebot.domain.CrashRecord
import me.pedroeugenio.blazebot.domain.StrategyRecordBase
import java.util.*
import kotlin.collections.LinkedHashSet

data class TGSignalDTO(
    override val key: UUID = UUID.randomUUID(),
    val firstRecord: CrashRecordDTO,
    val nextRecords: List<CrashRecordDTO>,
    val playRecord: CrashRecordDTO,
    override val isG1: Boolean = false,
    override val g1Record: CrashRecordDTO? = null,
) : StrategyRecordBaseDTO(key, isG1, g1Record)