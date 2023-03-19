package me.pedroeugenio.blazebot.web.mapper

import me.pedroeugenio.blazebot.domain.GameResults
import me.pedroeugenio.blazebot.domain.MatchStrategies
import me.pedroeugenio.blazebot.domain.aftercrash.ACMostRepeatedIndex
import me.pedroeugenio.blazebot.domain.aftercrash.AfterCrashResult
import me.pedroeugenio.blazebot.domain.CrashRecord
import me.pedroeugenio.blazebot.domain.threegrens.AfterThreeGreensResult
import me.pedroeugenio.blazebot.domain.threegrens.TGSignal
import me.pedroeugenio.blazebot.web.request.MatchStrategiesRequest
import me.pedroeugenio.blazebot.web.response.*
import java.time.ZoneId


@JvmName("gameResultsToDTO")
fun GameResults.toDto() = GameResultsDTO(
    strategies = this.strategies,
    afterCrashResults = (this.afterCrashResults as AfterCrashResult).toDto(),
    afterThreeGreensResults = (this.afterTheeGreensResults as AfterThreeGreensResult).toDto()
)

@JvmName("afterThreeGreensResultToDTO")
fun AfterThreeGreensResult.toDto() = AfterThreeGreensResultDTO(
    signals = this.signals,
    normalWins = this.normalWins,
    normalLoss = this.normalLoss,
    g1Wins = this.g1Wins,
    g1Loss = this.g1Loss,
    exitFactor = this.exitFactor,
    assertiveness = this.assertiveness,
    results = this.results.toDto(),
    profiting = this.profiting,
    strategyName = StrategyEnumDTO.valueOf(this.strategyName.name),
)

@JvmName("afterCrashResultToDTO")
fun AfterCrashResult.toDto() = AfterCrashResultDTO(
    signals = this.signals,
    normalWins = this.normalWins,
    normalLoss = this.normalLoss,
    g1Wins = this.g1Wins,
    g1Loss = this.g1Loss,
    exitFactor = this.exitFactor,
    assertiveness = this.assertiveness,
    results = this.results.toDto(),
    additionFactor = if (this.results.isNotEmpty()) (this.results as List<ACMostRepeatedIndex>)[0].additionFactor else null,
    profiting = this.profiting,
    strategyName = StrategyEnumDTO.valueOf(this.strategyName.name),
)

@JvmName("afterCrashResultListToDTO")
fun List<ACMostRepeatedIndex>.toDto() = this.map { it.toDto() }

@JvmName("tGSignalToDTO")
fun List<TGSignal>.toDto() = this.map { it.toDto() }

@JvmName("aCMostRepeatedIndexToDto")
fun ACMostRepeatedIndex.toDto() = ACMostRepeatedIndexDTO(
    key = this.key,
    crashIndex = this.crashIndex,
    crashRecord = this.crashRecord.toDto(),
    playIndex = this.playIndex,
    playRecord = this.playRecord.toDto(),
    isG1 = this.isG1,
    g1Record = this.g1Record?.toDto(),
    additionFactor = this.additionFactor,
)


@JvmName("tGSinalToDto")
fun TGSignal.toDto() = TGSignalDTO(
    key = this.key,
    isG1 = this.isG1,
    nextRecords = this.nextRecords.toDto(),
    playRecord = playRecord.toDto(),
    firstRecord = firstRecord.toDto(),
    g1Record = g1Record?.toDto(),
)

@JvmName("crashRecordToDto")
fun CrashRecord.toDto() = CrashRecordDTO(
    crashPoint = this.crashPoint,
    createdAt = this.createdAt.atZone(ZoneId.of("UTC"))
        .withZoneSameInstant(ZoneId.of("America/Sao_Paulo")).toLocalDateTime(),
    id = this.id
)

@JvmName("crashRecordListToDto")
fun List<CrashRecord>.toDto() = this.map { it.toDto() }

fun MatchStrategiesRequest.toDomain() = MatchStrategies(
    afterCrashProperties = MatchStrategies.AfterCrashProperties(
        crashWin = this.afterCrashProperties.crashWin,
        valueForProfitCalc = this.afterCrashProperties.valueForProfitCalc.toBigDecimal(),
        g1Factor = this.afterCrashProperties.g1Factor,
    ),
    afterTheeGreens = MatchStrategies.AfterTheeGreens(
        crashWin = this.afterCrashProperties.crashWin,
        valueForProfitCalc = this.afterCrashProperties.valueForProfitCalc.toBigDecimal(),
        g1Factor = this.afterCrashProperties.g1Factor,
    )
)