package net.xylophones.planetoid.game.model

case class GameModel(val planet: Planet,
                     val players: IndexedSeq[Player])