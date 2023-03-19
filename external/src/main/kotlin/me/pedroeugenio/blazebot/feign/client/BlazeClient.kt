package me.pedroeugenio.blazebot.feign.client

import me.pedroeugenio.blazebot.feign.response.BlazeHistoryResponse
import me.pedroeugenio.blazebot.feign.response.CrashRecordResponse
import me.pedroeugenio.blazebot.feign.response.DoubleRecordResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import java.time.LocalDate
import java.time.ZoneOffset

@FeignClient(value = "blazeclient", url = "\${blaze.api.url}")
interface BlazeClient {

    @RequestMapping("roulette_games/history")
    fun getDoubleHistory(
    ): BlazeHistoryResponse<DoubleRecordResponse>

    @RequestMapping("crash_games/history")
    fun getCrashistory(): ResponseEntity<BlazeHistoryResponse<CrashRecordResponse>>
}