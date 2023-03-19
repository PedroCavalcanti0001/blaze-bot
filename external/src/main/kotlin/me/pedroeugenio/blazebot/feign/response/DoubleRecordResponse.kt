package me.pedroeugenio.blazebot.feign.response

import com.fasterxml.jackson.annotation.JsonProperty
import me.pedroeugenio.blazebot.domain.ColorEnum
import java.time.LocalDateTime

data class DoubleRecordResponse(
    val color: ColorEnum,
    @JsonProperty("created_at") val createdAt: LocalDateTime,
    val id: String,
    val roll: Int
)