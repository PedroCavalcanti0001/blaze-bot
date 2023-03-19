package me.pedroeugenio.blazebot.boundary.input

import me.pedroeugenio.blazebot.domain.GameResults
import me.pedroeugenio.blazebot.domain.MatchStrategies

interface CrashCheckStrategiesInput {

    fun getStrategies(toDomain: MatchStrategies): GameResults
}