package net.xylophones.planetoid.game.model

import com.fasterxml.jackson.core.`type`.TypeReference

// class type alias required for jackson
class PlayerIdentifierType extends TypeReference[PlayerIdentifier.type]

object PlayerIdentifier extends Enumeration {
  type PlayerIdentifier = Value

  val Player1 = Value("Player1")
  val Player2 = Value("Player2")

}