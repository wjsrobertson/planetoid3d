package net.xylophones.planetoid.game.logic

import net.xylophones.planetoid.game.maths.Vector2D
import net.xylophones.planetoid.game.model._

object ModelTestObjectMother {

  val vec = Vector2D(0, 0)

  def createDummyPlanet() = Planet(Vector2D(10000, 10000), 10)

  def createDummyPlayer() = {
    val vec = Vector2D(-1000, -1000)
    val rocket = Rocket(vec, vec, vec, 10)

    Player(rocket, numLives = 1)
  }

  def createDummyPlayerWithMissile(missile: Missile) = {
    val vec = Vector2D(-1000, -1000)
    val rocket = Rocket(vec, vec, vec, 10)

    Player(rocket, numLives = 1, missiles = Vector(missile))
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

  def createDummyModel() = GameModel(createDummyPlanet(), createDummyPlayers())


  def createGameModelWithRocketAsPLayer1(rocket: Rocket) = {
    val players = Vector(Player(rocket, numLives = 1), createDummyPlayer())
    val planet = createDummyPlanet()
    val model = GameModel(planet, players)

    model
  }

  def createGameModelWithPlayer1Missile(missile: Missile): GameModel = {
    val player1 = createDummyPlayerWithMissile(missile)
    val player2 = createDummyPlayer()
    val planet = createDummyPlanet()
    val model = GameModel(planet, Vector(player1, player2))

    model
  }

  def createDummyPlayerInput() = {
    Vector(PlayerInput(), PlayerInput())
  }

  def createDummyRocket() = {
    Rocket(vec, vec, vec, 10)
  }


  def resultFromModel(model: GameModel) = new GameModelUpdateResult(model, Set.empty)
}
