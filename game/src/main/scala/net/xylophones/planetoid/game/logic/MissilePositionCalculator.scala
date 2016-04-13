package net.xylophones.planetoid.game.logic

import net.xylophones.planetoid.game.model.{GamePhysics, Missile}

class MissilePositionCalculator {

  def updateMissilePosition(missile: Missile, physics: GamePhysics): Missile = {
    val newPosition = missile.position + (missile.rotation.normalise * physics.missileSpeed)

    new Missile(newPosition, missile.rotation, missile.radius)
  }

}
