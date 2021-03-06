package net.xylophones.planetoid.game.model

import net.xylophones.planetoid.game.maths.Vector3D

case class Planet(val position: Vector3D,
                  val radius: Int) extends Circular