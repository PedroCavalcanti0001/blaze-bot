package me.pedroeugenio.blazebot.domain.aftercrash

import me.pedroeugenio.blazebot.domain.CrashRecord
import me.pedroeugenio.blazebot.domain.StrategyRecordBase
import java.util.UUID

data class ACMostRepeatedIndex(
    override val key:UUID = UUID.randomUUID(),
    val crashIndex: Int,
    val crashRecord: CrashRecord,
    val playIndex: Int,
    val playRecord: CrashRecord,
    override val isG1:Boolean = false,
    override val g1Record: CrashRecord? = null,
    val additionFactor:Int
) : StrategyRecordBase(key, isG1, g1Record)