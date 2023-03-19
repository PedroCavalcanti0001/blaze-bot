package me.pedroeugenio.blazebot.web.response

import java.time.LocalDateTime

data class CrashRecordDTO(
    val crashPoint: Double,
    val createdAt: LocalDateTime,
    val id: String
)