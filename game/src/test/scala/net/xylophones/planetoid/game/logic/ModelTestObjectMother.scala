package net.xylophones.planetoid.game.logic

import net.xylophones.planetoid.game.maths.Vector3D
import net.xylophones.planetoid.game.model.Winner.Winner
import net.xylophones.planetoid.game.model._

object ModelTestObjectMother {

  val vec = Vector3D(0, 0)

  def createDummyPlanet() = Planet(Vector3D(10000, 10000), 10)

  def createDummyPlayer() = {
    val vec = Vector3D(-1000, -1000)
    val rocket = Rocket(vec, vec, vec, 10)

    Player(rocket, numLives = 1)
  }

  def createDummyPlayerWithMissile(missile: Missile) = {
    val vec = Vector3D(-1000, -1000)
    val rocket = Rocket(vec, vec, vec, 10)

    Player(rocket, numLives = 1, missiles = Vector(missile))
  }

  def createDummyPlayerAtPosition(position: Vector3D) = {
    val rocket = Rocket(position, vec, vec, 10)

    Player(rocket, numLives = 1)
  }

  def createDummyPlayers() = {
    val rocket = Rocket(vec, vec, vec, 10)

    Players(createDummyPlayer(), createDummyPlayer())
  }

  def createRocketAtOriginPointingUp() = {
    val position = Vector3D(0, 0)
    val rotation = Vector3D(0, 1)
    val velocity = Vector3D(0, 0)

    Rocket(position, rotation, velocity, 999)
  }

  def createRocketAt10_0PointingUp() = {
    val position = Vector3D(0, 10)
    val rotation = Vector3D(0, 1)
    val velocity = Vector3D(0, 0)

    Rocket(position, rotation, velocity, 999)
  }

  def createRocketAt(position: Vector3D) = Rocket(position, Vector3D(0, 1), Vector3D(0, 0), 5)

  def createDummyModel() = GameModel(createDummyPlanet(), createDummyPlayers())

  def createDummyModelWithWinner(winner: Winner) = GameModel(createDummyPlanet(), createDummyPlayers(), winner=winner)

  def createGameModelWithRocketAsPLayer1(rocket: Rocket) = {
    val players = Players(Player(rocket, numLives = 1), createDummyPlayer())
    val planet = createDummyPlanet()
    val model = GameModel(planet, players)

    model
  }

  def createGameModelWithPlayer1Missile(missile: Missile): GameModel = {
    val player1 = createDummyPlayerWithMissile(missile)
    val player2 = createDummyPlayer()
    val planet = createDummyPlanet()
    val model = GameModel(planet, Players(player1, player2))

    model
  }

  def createDummyPlayerInput() = {
    Vector(PlayerInput(), PlayerInput())
  }

  def createDummyRocket() = {
    Rocket(vec, vec, vec, 10)
  }

  def resultFromModel(model: GameModel) = new GameModelUpdateResult(model, Set.empty)

  def createGameContainerWithWinner() = {
    new GameContainer(
      "game ID",
      "player 1 ID",
      "player 2 ID",
      new GamePhysics,
      createDummyModelWithWinner(Winner.Player1),
      PlayerInput(),
      PlayerInput()
    )
  }

  def createGameContainer() = {
    new GameContainer(
      "game ID",
      "player 1 ID",
      "player 2 ID",
      new GamePhysics,
      createDummyModel(),
      PlayerInput(),
      PlayerInput()
    )
  }

  def createDummyExplosion() = Explosion(vec, 9)
}
