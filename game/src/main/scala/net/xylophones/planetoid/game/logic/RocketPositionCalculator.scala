package net.xylophones.planetoid.game.logic

import net.xylophones.planetoid.game.maths.Vector2D
import net.xylophones.planetoid.game.model.{GamePhysics, Planet, PlayerInput, Rocket}

class RocketPositionCalculator(boundsChecker: BoundsChecker) {

  def updateRocketPosition(rocket: Rocket, input: PlayerInput, planet: Planet, physics: GamePhysics): Rocket = {
    val planetForce = forceDueToPlanetGravity(rocket, planet, physics.gForce)

    val rotation = newRotationBasedOnPlayerInput(rocket, input, physics.rocketRotationSpeed)

    val thrustForce = if (input.thrust) rotation * physics.rocketThrustForce
                      else if (input.reverseThrust) rotation * physics.rocketReverseThrustForce
                      else Vector2D(0, 0)

    val accel = (planetForce + thrustForce) / physics.rocketMass

    val velocity = (rocket.velocity + accel) truncate physics.rocketMaxSpeed
    val newPosition = rocket.position + velocity

    val position = boundsChecker.warpOverBounds(newPosition, physics)

    Rocket(position, rotation, velocity, rocket.radius)
  }

  private def forceDueToPlanetGravity(player: Rocket, planet: Planet, gForce: Double): Vector2D = {
    val directionToPlanet: Vector2D = (planet.position - player.position).normalise
    val planetForce = directionToPlanet * gForce

    planetForce
  }

  private def newRotationBasedOnPlayerInput(player: Rocket, input: PlayerInput, rotationSpeed: Double): Vector2D = {
    if (input.left) player.rotation.rotate(rotationSpeed).normalise
    else if (input.right) player.rotation.rotate(-rotationSpeed).normalise
    else player.rotation
  }
}
