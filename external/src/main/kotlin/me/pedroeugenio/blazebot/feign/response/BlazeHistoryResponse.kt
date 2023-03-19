package me.pedroeugenio.blazebot.feign.response

import com.fasterxml.jackson.annotation.JsonProperty
import me.pedroeugenio.blazebot.domain.BlazeHistory
import me.pedroeugenio.blazebot.domain.CrashRecord
import me.pedroeugenio.blazebot.domain.DoubleRecord

data class BlazeHistoryResponse<T>(
    val records: List<T>,
    @JsonProperty("total_pages") val totalPages: Int
)

fun BlazeHistoryResponse<CrashRecordResponse>.toCrashDomain() = BlazeHistory(
    totalPages = this.totalPages,
    records = this.records.map { CrashRecord(it.crashPoint, it.createdAt, it.id) }
)

fun BlazeHistoryResponse<DoubleRecordResponse>.toDoubleDomain() = BlazeHistory(
    totalPages = this.totalPages,
    records = this.records.map { DoubleRecord(it.color, it.createdAt, it.id, it.roll) }
)