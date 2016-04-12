package net.xylophones.planetoid.game.logic

import net.xylophones.planetoid.game.model.Circular

class CollisionCalculator {

  def isCollision(circ1: Circular, circ2: Circular): Boolean = {
    (circ1.position - circ2.position).magnitude < circ1.radius + circ2.radius
  }

}
