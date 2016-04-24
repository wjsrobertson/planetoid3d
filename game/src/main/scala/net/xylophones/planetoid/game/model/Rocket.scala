package net.xylophones.planetoid.game.model

import net.xylophones.planetoid.game.maths.Vector2D

case class Rocket(val position: Vector2D,
                  val rotation: Vector2D,
                  val velocity: Vector2D,
                  val radius: Int) extends Circular