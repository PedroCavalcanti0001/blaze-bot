package me.pedroeugenio.blazebot.domain

import java.time.LocalDateTime

data class CrashRecord(
    val crashPoint: Double,
    val createdAt: LocalDateTime,
    val id: String
)