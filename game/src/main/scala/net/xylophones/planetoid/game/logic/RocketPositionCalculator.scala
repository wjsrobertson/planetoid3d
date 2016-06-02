package net.xylophones.planetoid.game.logic

import net.xylophones.planetoid.game.maths.{RotationDegreesToDirectionVector, Vector3D}
import net.xylophones.planetoid.game.model.{GamePhysics, Planet, PlayerInput, Rocket}

class RocketPositionCalculator(boundsChecker: BoundsChecker, directionCalculator: RotationDegreesToDirectionVector) {

  def updateRocketPosition(rocket: Rocket, input: PlayerInput, planet: Planet, physics: GamePhysics): Rocket = {
    val planetForce = forceDueToPlanetGravity(rocket, planet, physics.gForce)

    val rotation = newRotationBasedOnPlayerInput(rocket, input, physics.rocketRotationSpeed)
    val direction = directionCalculator.toDirection(rotation)

    val thrustForce = if (input.thrust) direction * physics.rocketThrustForce
                      else if (input.reverseThrust) direction * physics.rocketReverseThrustForce
                      else Vector3D.zero

    val accel = (planetForce + thrustForce) / physics.rocketMass

    val velocity = (rocket.velocity + accel) truncate physics.rocketMaxSpeed
    val newPosition = rocket.position + velocity

    val position = boundsChecker.warpOverBounds(newPosition, physics)

    Rocket(position, rotation, velocity, rocket.radius)
  }

  private def forceDueToPlanetGravity(player: Rocket, planet: Planet, gForce: Double): Vector3D = {
    val directionToPlanet: Vector3D = (planet.position - player.position).normalise
    val planetForce = directionToPlanet * gForce

    planetForce
  }

  private def newRotationBasedOnPlayerInput(player: Rocket, input: PlayerInput, rotationSpeed: Double): Vector3D = {
    val xChange = if (input.left) rotationSpeed
                  else if (input.right) -rotationSpeed
                  else 0

    val yChange = if (input.up) rotationSpeed
                  else if (input.down) -rotationSpeed
                  else 0

    val x = player.rotation.x + xChange
    val y = player.rotation.y + yChange

    player.rotation.copy(x = x, y = y)
  }
}
