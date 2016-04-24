package net.xylophones.planetoid.game.model

class GamePhysics(val gForce: Double = 0.2,
                  val planetRadius: Int = 300,
                  val rocketMass: Double = 1,
                  val rocketThrustForce: Double = 1,
                  val rocketReverseThrustForce: Double = -1,
                  val rocketMaxSpeed: Double = 20,
                  val rocketRotationSpeed: Double = -0.05,
                  val rocketRadius: Int = 100,
                  val missileSpeed: Double = 5,
                  val missileRadius: Int = 4,
                  val universeWidth: Int = 3840,
                  val universeHeight: Int = 2160,
                  val numLives: Int = 3,
                  val maxMissilesPerPlayer: Int = 1,
                  val roundStartDelayMilliseconds: Int = 3999)