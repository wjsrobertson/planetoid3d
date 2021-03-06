package net.xylophones.planetoid.game.model

import com.fasterxml.jackson.core.`type`.TypeReference

// class type alias required for jackson
class WinnerType extends TypeReference[Winner.type]

object Winner extends Enumeration {
  type Winner = Value

  val None = Value("None")
  val Player1 = Value("Player1")
  val Player2 = Value("Player2")
  val Draw = Value("Draw")

}