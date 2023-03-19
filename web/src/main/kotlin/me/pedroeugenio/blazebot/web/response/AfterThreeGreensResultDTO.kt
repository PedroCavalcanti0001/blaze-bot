package me.pedroeugenio.blazebot.web.response

import me.pedroeugenio.blazebot.domain.StrategyBase
import me.pedroeugenio.blazebot.domain.StrategyEnum
import me.pedroeugenio.blazebot.domain.StrategyRecordBase
import me.pedroeugenio.blazebot.domain.threegrens.TGSignal
import java.math.BigDecimal

class AfterThreeGreensResultDTO(
    override val signals: Int,
    override val normalWins: Int,
    override val normalLoss: Int,
    override val g1Wins: Int,
    override val g1Loss: Int,
    override val assertiveness: Double,
    override val strategyName: StrategyEnumDTO,
    override val profiting: BigDecimal,
    val exitFactor: Double,
    val results: List<TGSignalDTO>
) : StrategyBaseDTO(
    assertiveness = assertiveness,
    g1Loss = g1Loss,
    g1Wins = g1Wins,
    normalLoss = normalLoss,
    normalWins = normalWins,
    signals = signals,
    strategyName = strategyName,
    profiting = profiting
)