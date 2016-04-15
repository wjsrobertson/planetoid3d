package net.xylophones.planetoid.game.model

import com.fasterxml.jackson.module.scala.JsonScalaEnumeration

class GameModelUpdateResult(val model: GameModel,
                            @JsonScalaEnumeration(classOf[GameEventType]) val events: Set[GameEvent.Value])