package net.xylophones.planetoid.game.logic

import net.xylophones.planetoid.game.maths.Vector2D
import net.xylophones.planetoid.game.model._
import net.xylophones.planetoid.game.logic.ModelTestObjectMother._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FunSuite, Matchers}

@RunWith(classOf[JUnitRunner])
class GameCollisionUpdaterTest extends FunSuite with Matchers {

  val underTest = new GameCollisionUpdater(new CollisionCalculator)

  test("collision between player1 and planet is detected") {
    /*
    given
     */
    val planet = Planet(Vector2D(10, 10), 10)
    val player1 = Player(createRocketAt(Vector2D(10, 10)), alive = true)
    val physics = new GamePhysics()
    val model = GameModel(planet, Vector(player1, createDummyPlayer()), Set.empty)

    // when
    val result = underTest.updateForCollisions(model, physics)

    // then
    val newModel = result.model
    val events = result.events

    newModel.players(0).alive shouldBe false
  }

  test("collision between player2 and planet is detected") {
    /*
    given
     */
    val planet = Planet(Vector2D(10, 10), 10)
    val player2 = Player(createRocketAt(Vector2D(10, 10)), alive = true)
    val physics = new GamePhysics()
    val model = GameModel(planet, Vector(createDummyPlayer(), player2), Set.empty)

    // when
    val result = underTest.updateForCollisions(model, physics)

    // then
    val newModel = result.model
    val events = result.events

    newModel.players(1).alive shouldBe false
  }

  test("collision between player1 and missile is detected and missile is removed") {
    /*
    given
     */
    val physics = new GamePhysics()
    val player1 = Player(createRocketAt(Vector2D(10, 10)), alive = true)
    val missile = new Missile(Vector2D(10, 10), Vector2D(0, 0), 2)
    val model = GameModel(createDummyPlanet(), Vector(player1, createDummyPlayer()), Set(missile))

    // when
    val result = underTest.updateForCollisions(model, physics)

    // then
    val newModel = result.model
    val events = result.events

    newModel.players(0).alive shouldBe false
    newModel.missiles shouldBe empty
  }
}