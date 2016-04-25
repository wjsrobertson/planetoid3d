package net.xylophones.planetoid.game

import net.xylophones.planetoid.game.maths.Vector2D
import net.xylophones.planetoid.game.model.{GamePhysics, PlayerIdentifier, Rocket}

class RocketFactory {

  def getRocketAtInitialPosition(playerType: PlayerIdentifier.Value, phys: GamePhysics): Rocket = {
    val rotation = initialRotation(playerType)
    val position = initialPosition(playerType, phys)

    Rocket(position, rotation, Vector2D.zero, phys.rocketRadius)
  }

  private def initialRotation(playerType: PlayerIdentifier.Value): Vector2D = {
    if (playerType == PlayerIdentifier.Player1) Vector2D(-1, 0)
    else Vector2D(1, 0)
  }

  private def initialPosition(playerType: PlayerIdentifier.Value, phys: GamePhysics) = {
    if (playerType == PlayerIdentifier.Player1) player1InitialPosition(phys)
    else player2InitialPosition(phys)
  }

  private def player1InitialPosition(phys: GamePhysics): Vector2D = {
    val y = phys.universeHeight / 2
    val x = (phys.universeWidth - phys.planetRadius) / 8

    Vector2D(x, y)
  }

  private def player2InitialPosition(phys: GamePhysics): Vector2D = {
    val y = phys.universeHeight / 2
    val x = phys.universeWidth - (phys.universeWidth - phys.planetRadius) / 8

    Vector2D(x, y)
  }
}
