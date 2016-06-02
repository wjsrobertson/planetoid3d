package net.xylophones.planetoid.game.logic

import net.xylophones.planetoid.game.maths.RotationDegreesToDirectionVector
import net.xylophones.planetoid.game.model.{GamePhysics, Missile}

class MissilePositionCalculator(directionCalculator: RotationDegreesToDirectionVector) {

  def updateMissilePosition(missile: Missile, physics: GamePhysics): Missile = {
    val direction = directionCalculator.toDirection(missile.rotation)

    val newPosition = missile.position + (direction * physics.missileSpeed)

    new Missile(newPosition, missile.rotation, missile.radius)
  }

}
