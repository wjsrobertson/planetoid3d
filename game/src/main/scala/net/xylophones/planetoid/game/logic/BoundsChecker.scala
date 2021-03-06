package net.xylophones.planetoid.game.logic

import net.xylophones.planetoid.game.maths.Vector3D
import net.xylophones.planetoid.game.model.{GamePhysics, Circular}

class BoundsChecker {

  def isWithinBounds(position: Vector3D, gamePhysics: GamePhysics): Boolean = {
    position.magnitude < gamePhysics.universeWidth
  }

  def warpOverBounds(position: Vector3D, gamePhysics: GamePhysics): Vector3D = {
    if (isWithinBounds(position, gamePhysics)) position
    else Vector3D(-position.x, -position.y, -position.z).truncate(gamePhysics.universeWidth)
  }

}
