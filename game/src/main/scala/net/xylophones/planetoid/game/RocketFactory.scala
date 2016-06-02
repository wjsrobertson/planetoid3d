package net.xylophones.planetoid.game

import net.xylophones.planetoid.game.maths.Vector3D
import net.xylophones.planetoid.game.model.{GamePhysics, PlayerIdentifier, Rocket}

class RocketFactory {

  def getRocketAtInitialPosition(playerType: PlayerIdentifier.Value, phys: GamePhysics): Rocket = {
    val rotation = initialRotation(playerType)
    val position = initialPosition(playerType, phys)

    Rocket(position, rotation, Vector3D.zero, phys.rocketRadius)
  }

  private def initialRotation(playerType: PlayerIdentifier.Value): Vector3D = {
    if (playerType == PlayerIdentifier.Player1) Vector3D(-1, 0, 0)
    else Vector3D(1, 0, 0)
  }

  private def initialPosition(playerType: PlayerIdentifier.Value, phys: GamePhysics) = {
    if (playerType == PlayerIdentifier.Player1) player1InitialPosition(phys)
    else player2InitialPosition(phys)
  }

  private def player1InitialPosition(phys: GamePhysics): Vector3D = {
    val x = 0
    val y = 0
    val z = -(phys.universeWidth - (phys.universeWidth - phys.planetRadius) / 8)

    Vector3D(x, y, z)
  }

  private def player2InitialPosition(phys: GamePhysics): Vector3D = {
    val x = 0
    val y = 0
    val z = phys.universeWidth - (phys.universeWidth - phys.planetRadius) / 8

    Vector3D(x, y, z)
  }
}
