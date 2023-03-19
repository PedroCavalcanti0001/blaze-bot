package me.pedroeugenio.blazebot.domain

import java.time.LocalDateTime

data class DoubleRecord(
    val color: ColorEnum,
    val createdAt: LocalDateTime,
    val id: String,
    val roll: Int
)