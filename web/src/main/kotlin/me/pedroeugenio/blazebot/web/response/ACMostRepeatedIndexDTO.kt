package me.pedroeugenio.blazebot.web.response

import java.util.UUID

data class ACMostRepeatedIndexDTO(
    var key:UUID = UUID.randomUUID(),
    val crashIndex: Int,
    val crashRecord: CrashRecordDTO,
    val playIndex: Int,
    val playRecord: CrashRecordDTO,
    val isG1:Boolean = false,
    val g1Record:CrashRecordDTO? = null,
    val additionFactor:Int
)