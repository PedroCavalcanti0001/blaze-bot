package me.pedroeugenio.blazebot.web.response

class GameResultsDTO(
    val strategies: Int,
    val afterCrashResults: AfterCrashResultDTO,
    val afterThreeGreensResults: AfterThreeGreensResultDTO
)