package net.xylophones.planetoid.game.logic

import net.xylophones.planetoid.game.model.{GamePhysics, Missile}

class MissileCalculator {

  def updateMissilePosition(missile: Missile, physics: GamePhysics): Missile = {
    val newPosition = missile.position + (missile.rotation.normalise * physics.missileSpeed)

    Missile(newPosition, missile.rotation, missile.radius)
  }

}
