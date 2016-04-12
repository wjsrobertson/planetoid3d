package net.xylophones.planetoid.game.model

class GamePhysics(val gForce: Double = 1,
                  val planetRadius: Int = 100,
                  val rocketMass: Double = 1,
                  val rocketThrustForce: Double = 1,
                  val rocketMaxSpeed: Double = 10,
                  val rocketRotationSpeed: Double = 1,
                  val rocketRadius: Int = 30,
                  val missileSpeed: Double = 5,
                  val missileRadius: Int = 4,
                  val universeWidth: Int = 3840,
                  val universeHeight: Int = 2160)