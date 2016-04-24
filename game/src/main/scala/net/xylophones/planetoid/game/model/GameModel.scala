package net.xylophones.planetoid.game.model

import com.fasterxml.jackson.module.scala.JsonScalaEnumeration
import net.xylophones.planetoid.game.model.Winner.Winner

// TODO - planet could move into GamePhysics
// TODO - replace IndexedSeq[Player] with object - {player1, player2}
case class GameModel(val planet: Planet,
                     val players: IndexedSeq[Player],
                     val roundTimer: RoundCountdownTimer = RoundCountdownTimer(),
                     @JsonScalaEnumeration(classOf[WinnerType]) val  winner: Winner = Winner.None)