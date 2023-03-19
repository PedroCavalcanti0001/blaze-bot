package me.pedroeugenio.blazebot.feign.response

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class CrashRecordResponse(
    @JsonProperty("crash_point") val crashPoint: Double,
    @JsonProperty("created_at") val createdAt: LocalDateTime,
    val id: String
)