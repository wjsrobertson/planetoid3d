package net.xylophones.planetoid.game.model

import net.xylophones.planetoid.game.maths.Vector3D

case class Rocket(val position: Vector3D,
                  val rotation: Vector3D,
                  val velocity: Vector3D,
                  val radius: Int) extends Circular