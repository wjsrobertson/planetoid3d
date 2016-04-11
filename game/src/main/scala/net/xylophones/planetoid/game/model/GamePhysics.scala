package net.xylophones.planetoid.game.model

class GamePhysics(val gForce: Double = 1,
                  val spaceShipMass: Double = 1,
                  val thrustForce: Double = 1,
                  val spaceShipMaxVelocity: Double = 10,
                  val rotationSpeed: Double = 1)