package net.xylophones.planetoid.game.model

import com.fasterxml.jackson.module.scala.JsonScalaEnumeration
import net.xylophones.planetoid.game.model.Winner.Winner

case class GameModel(val planet: Planet,
                     val players: Players,
                     val explosions: Set[Explosion] = Set(),
                     @JsonScalaEnumeration(classOf[WinnerType]) val winner: Winner = Winner.None,
                     val roundTimer: RoundCountdownTimer = RoundCountdownTimer.empty,
                     val endRoundTimer: Option[RoundCountdownTimer] = None)