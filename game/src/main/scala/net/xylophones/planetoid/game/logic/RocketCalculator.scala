package net.xylophones.planetoid.game.logic

import net.xylophones.planetoid.game.maths.Vector2D
import net.xylophones.planetoid.game.model.{Planet, PlayerInput, Rocket, GamePhysics}

class RocketCalculator {

  def updateRocket(player: Rocket, input: PlayerInput, planet: Planet, physics: GamePhysics): Rocket = {
    val planetForce = forceDueToPlanetGravity(player, planet, physics.gForce)

    val rotation = newRotation(player, input, physics.rotationSpeed)

    val thrustForce = if (input.thrust) rotation * physics.thrustForce else Vector2D(0, 0)

    val accel = (planetForce + thrustForce) / physics.spaceShipMass

    val velocity = (player.velocity + accel) truncate physics.spaceShipMaxSpeed
    val position = player.position + velocity

    Rocket(position, rotation, velocity)
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
