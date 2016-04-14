package net.xylophones.planetoid.game.logic

import net.xylophones.planetoid.game.maths.Vector2D
import net.xylophones.planetoid.game.model.{GameModel, Player, Rocket, Planet}

object ModelTestObjectMother {

  val vec = Vector2D(0, 0)

  def createDummyPlanet() = Planet(Vector2D(10000, 10000), 10)

  def createDummyPlayer() = {
    val vec = Vector2D(-1000, -1000)
    val rocket = Rocket(vec, vec, vec, 10)

    Player(rocket, alive = true)
  }

  def createDummyPlayers() = {
    val rocket = Rocket(vec, vec, vec, 10)

    Vector(createDummyPlayer(), createDummyPlayer())
  }

  def createRocketAtOriginPointingUp() = {
    val position = Vector2D(0, 0)
    val rotation = Vector2D(0, 1)
    val velocity = Vector2D(0, 0)

    Rocket(position, rotation, velocity, 999)
  }

  def createRocketAt(position: Vector2D) = Rocket(position, Vector2D(0, 1), Vector2D(0, 0), 5)

  def createDummyModel() = GameModel(createDummyPlanet(), createDummyPlayers(), Set())

}
