package net.xylophones.planetoid.game.model

import com.fasterxml.jackson.core.`type`.TypeReference

// class type alias required for jackson
class GameEventType extends TypeReference[GameEvent.type]

object GameEvent extends Enumeration {
  type GameEvent = Value

  val PlayerLoseLife = Value("PlayerLoseLife")
  val MissileFired = Value("MissileFired")
  val RoundStart = Value("RoundStart")
  val RoundInitialised = Value("RoundInitialised")
  val GameOver = Value("GameOver")

}
