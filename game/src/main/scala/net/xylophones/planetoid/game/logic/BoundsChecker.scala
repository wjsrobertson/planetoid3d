package net.xylophones.planetoid.game.logic

import net.xylophones.planetoid.game.maths.Vector2D
import net.xylophones.planetoid.game.model.{GamePhysics, Circular}

class BoundsChecker {

  def isWithinBounds(position: Vector2D, gamePhysics: GamePhysics): Boolean = {
    val x = position.x
    val y = position.y

    x > 0 && x < gamePhysics.universeWidth && y > 0 && y < gamePhysics.universeHeight
  }

  def warpOverBounds(position: Vector2D, gamePhysics: GamePhysics): Vector2D = {
    if (isWithinBounds(position, gamePhysics)) position
    else {
      val x = (math.round(position.x) + gamePhysics.universeWidth) % gamePhysics.universeWidth
      val y = (math.round(position.y) + gamePhysics.universeHeight) % gamePhysics.universeHeight

      Vector2D(x, y)
    }
  }

}
