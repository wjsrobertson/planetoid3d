package net.xylophones.planetoid.game.logic

import net.xylophones.planetoid.game.model.Circular

class CollisionCalculator {

  def isCollision(circ1: Circular, circ2: Circular): Boolean = {
    val minRadius = Math.min(circ1.radius, circ2.radius)
    val maxRadius = Math.max(circ1.radius, circ2.radius)

    (circ1.position - circ2.position).magnitude < (maxRadius + (minRadius * 0.7))
  }

}
