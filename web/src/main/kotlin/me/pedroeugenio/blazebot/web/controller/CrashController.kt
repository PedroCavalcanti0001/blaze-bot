package me.pedroeugenio.blazebot.web.controller

import me.pedroeugenio.blazebot.usecase.CrashCheckStrategiesUseCase
import me.pedroeugenio.blazebot.web.mapper.toDomain
import me.pedroeugenio.blazebot.web.mapper.toDto
import me.pedroeugenio.blazebot.web.request.MatchStrategiesRequest
import me.pedroeugenio.blazebot.web.response.GameResultsDTO
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/blazebot/crash")
class CrashController(private val crashCheckStrategiesUseCase: CrashCheckStrategiesUseCase) {

    @PostMapping()
    fun getMatchStrategies(@RequestBody body: MatchStrategiesRequest): GameResultsDTO {
        return crashCheckStrategiesUseCase.getStrategies(body.toDomain()).toDto()
    }
}