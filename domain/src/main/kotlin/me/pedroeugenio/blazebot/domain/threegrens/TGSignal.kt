package me.pedroeugenio.blazebot.domain.threegrens

import me.pedroeugenio.blazebot.domain.CrashRecord
import me.pedroeugenio.blazebot.domain.StrategyRecordBase
import java.util.*
import kotlin.collections.LinkedHashSet

data class TGSignal(
    override val key: UUID = UUID.randomUUID(),
    val firstRecord: CrashRecord,
    val nextRecords: List<CrashRecord>,
    val playRecord:CrashRecord,
    override val isG1:Boolean = false,
    override val g1Record: CrashRecord? = null,
): StrategyRecordBase(key, isG1, g1Record)