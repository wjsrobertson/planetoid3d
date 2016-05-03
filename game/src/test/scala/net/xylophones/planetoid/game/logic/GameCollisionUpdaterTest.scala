package net.xylophones.planetoid.game.logic

import net.xylophones.planetoid.game.maths.Vector2D
import net.xylophones.planetoid.game.model._
import net.xylophones.planetoid.game.logic.ModelTestObjectMother._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FunSuite, Matchers}
import net.xylophones.planetoid.game.logic.ModelTestObjectMother._

@RunWith(classOf[JUnitRunner])
class GameCollisionUpdaterTest extends FunSuite with Matchers {

  val underTest = new GameCollisionUpdater(new CollisionCalculator)

  test("collision between player1 and planet is detected") {
    /*
    given
     */
    val planet = Planet(Vector2D(10, 10), 10)
    val player1 = Player(createRocketAt(Vector2D(10, 10)), numLives = 1)
    val physics = new GamePhysics()
    val model = GameModel(planet, Players(player1, createDummyPlayer()))

    // when
    val result = underTest.update(resultFromModel(model), physics, Vector.empty)

    // then
    val newModel = result.model
    val events = result.events

    newModel.players.p1.numLives shouldBe 0
    newModel.players.p2.points shouldBe 1
    events should contain (GameEvent.Player1LoseLife)
    events should contain (GameEvent.PlayerLoseLife)
  }

  test("collision between player2 and planet is detected") {
    /*
    given
     */
    val planet = Planet(Vector2D(10, 10), 10)
    val player2 = Player(createRocketAt(Vector2D(10, 10)), numLives = 1)
    val physics = new GamePhysics()
    val model = GameModel(planet, Players(createDummyPlayer(), player2))

    // when
    val result = underTest.update(resultFromModel(model), physics, Vector.empty)

    // then
    val newModel = result.model
    val events = result.events

    newModel.players.p2.numLives shouldBe 0
    newModel.players.p1.points shouldBe 1
  }

  test("collision between player1 and missile is detected and missile is removed") {
    /*
    given
     */
    val physics = new GamePhysics()
    val player1 = Player(createRocketAt(Vector2D(10, 10)), numLives = 1)
    val missile = new Missile(Vector2D(10, 10), Vector2D(0, 0), 2)
    val player2 = createDummyPlayerWithMissile(missile)
    val model = GameModel(createDummyPlanet(), Players(player1, player2))

    // when
    val result = underTest.update(resultFromModel(model), physics, Vector.empty)

    // then
    val newModel = result.model
    val events = result.events

    newModel.players.p1.numLives shouldBe 0
    newModel.players.p2.missiles shouldBe empty
    newModel.players.p2.points shouldBe 1
  }

  test("missiles are removed for player 2, even when collision between player1 and planet is detected") {
    /*
    given
     */
    val planet = Planet(Vector2D(10, 10), 10)
    val player1 = Player(createRocketAt(Vector2D(10, 10)), numLives = 1)
    val missile = new Missile(Vector2D(10, 10), Vector2D(0, 0), 2)
    val player2 = createDummyPlayerWithMissile(missile)
    val physics = new GamePhysics()
    val model = GameModel(planet, Players(player1, player2))

    // when
    val result = underTest.update(resultFromModel(model), physics, Vector.empty)

    // then
    val newModel = result.model
    val events = result.events

    newModel.players.p2.missiles shouldBe empty
  }

  test("collision between player1 and player2 is detected") {
    /*
    given
     */
    val player1 = Player(createRocketAt(Vector2D(10, 10)), numLives = 1)
    val player2 = Player(createRocketAt(Vector2D(10, 10)), numLives = 1)
    val physics = new GamePhysics()
    val model = GameModel(createDummyPlanet(), Players(player1, player2))

    // when
    val result = underTest.update(resultFromModel(model), physics, Vector.empty)

    // then
    val newModel = result.model
    val events = result.events

    newModel.players.p1.numLives shouldBe 0
    newModel.players.p1.points shouldBe 1
    newModel.players.p2.numLives shouldBe 0
    newModel.players.p2.points shouldBe 1
    events should contain (GameEvent.Player2LoseLife)
    events should contain (GameEvent.Player1LoseLife)
    events should contain (GameEvent.PlayerLoseLife)
  }

  test("no collision between player1 and player2 when they are not intersecting") {
    /*
    given
     */
    val player1 = Player(createRocketAt(Vector2D(1000, 1000)), numLives = 1)
    val player2 = Player(createRocketAt(Vector2D(10, 10)), numLives = 1)
    val physics = new GamePhysics()
    val model = GameModel(createDummyPlanet(), Players(player1, player2))

    // when
    val result = underTest.update(resultFromModel(model), physics, Vector.empty)

    // then
    val newModel = result.model
    val events = result.events

    newModel.players.p1 shouldBe player1
    newModel.players.p2 shouldBe player2
    events should not contain (GameEvent.Player2LoseLife)
    events should not contain (GameEvent.Player1LoseLife)
    events should not contain (GameEvent.PlayerLoseLife)
  }
}
