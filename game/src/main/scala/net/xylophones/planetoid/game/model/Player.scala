package net.xylophones.planetoid.game.model

case class Player(val rocket: Rocket,
                  val numLives: Int,
                  val points: Int = 0,
                  val missiles: IndexedSeq[Missile] = Vector.empty)