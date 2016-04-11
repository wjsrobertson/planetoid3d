package net.xylophones.planetoid.game.logic

import net.xylophones.planetoid.game.maths.Vector2D
import net.xylophones.planetoid.game.model.{Planet, PlayerInput, Rocket, GamePhysics}

class RocketCalculator {

  def updateRocket(rocket: Rocket, input: PlayerInput, planet: Planet, physics: GamePhysics): Rocket = {
    val planetForce = forceDueToPlanetGravity(rocket, planet, physics.gForce)

    val rotation = newRotation(rocket, input, physics.rocketRotationSpeed)

    val thrustForce = if (input.thrust) rotation * physics.rocketThrustForce else Vector2D(0, 0)

    val accel = (planetForce + thrustForce) / physics.rocketMass

    val velocity = (rocket.velocity + accel) truncate physics.rocketMaxSpeed
    val position = rocket.position + velocity

    Rocket(position, rotation, velocity, rocket.radius)
  }

  private def forceDueToPlanetGravity(player: Rocket, planet: Planet, gForce: Double): Vector2D = {
    val directionToPlanet: Vector2D = (planet.position - player.position).normalise
    val planetForce = directionToPlanet * gForce

    planetForce
  }

  private def newRotation(player: Rocket, input: PlayerInput, rotationSpeed: Double): Vector2D = {
    if (input.left) player.rotation.rotate(rotationSpeed).normalise
    else if (input.right) player.rotation.rotate(-rotationSpeed).normalise
    else player.rotation
  }
}
