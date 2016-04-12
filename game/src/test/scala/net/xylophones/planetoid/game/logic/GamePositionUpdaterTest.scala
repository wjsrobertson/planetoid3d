package net.xylophones.planetoid.game.logic

import net.xylophones.planetoid.game.maths.Vector2D
import net.xylophones.planetoid.game.model._
import org.junit.runner.RunWith
import org.scalatest.{FunSuite, Matchers}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class GamePositionUpdaterTest extends FunSuite with Matchers {

  val tolerance: Double = 0.00001

  val underTest = new GamePositionUpdater(
    new MissileCalculator,
    new RocketCalculator(new BoundsChecker),
    new BoundsChecker
  )

  val vec = Vector2D(0, 0)

  test("missile gets removed if it is offscreen") {
    /*
     given
      */
    val phys = new GamePhysics
    val xOffscreen = phys.universeWidth + 99
    val missile = Missile(Vector2D(xOffscreen, 10), Vector2D(1, 0), 10)
    val inputs = createDummyPlayerInput()
    val model = createGameModelFromMissile(missile)

    // when
    val newModel = underTest.updateRocketAndMissilePositions(model, phys, inputs)

    // then
    newModel.missiles shouldBe empty
  }

  test("missile is not removed if it is onscreen") {
    /*
     given
      */
    val phys = new GamePhysics
    val xOnScreen = phys.universeWidth - 10
    val missile = Missile(Vector2D(xOnScreen, 10), Vector2D(1, 0), 10)
    val inputs = createDummyPlayerInput()
    val model = createGameModelFromMissile(missile)

    // when
    val newModel = underTest.updateRocketAndMissilePositions(model, phys, inputs)

    // then
    newModel.missiles should have size 1
  }

  test("rocket gets warped to other side if it is offscreen") {
    /*
     given
     */
    val phys = new GamePhysics(gForce = 0)
    val xOffScreen = phys.universeWidth + 99
    val rocket = Rocket(Vector2D(xOffScreen, 10), vec, vec, 10)
    val inputs = createDummyPlayerInput()
    val model = createGameModelWithRocketAsPLayer1(rocket)

    // when
    val newModel = underTest.updateRocketAndMissilePositions(model, phys, inputs)

    // then
    val player1 = newModel.players(0)
    player1.rocket.position.x shouldBe 99d +- tolerance
  }

  test("rocket does not get warped to other side if it is onscreen") {
    /*
     given
     */
    val phys = new GamePhysics(gForce = 0)
    val xOnScreen = 120d
    val rocket = Rocket(Vector2D(xOnScreen, 10), vec, vec, 10)
    val inputs = createDummyPlayerInput()
    val model = createGameModelWithRocketAsPLayer1(rocket)

    // when
    val newModel = underTest.updateRocketAndMissilePositions(model, phys, inputs)

    // then
    val player1 = newModel.players(0)
    player1.rocket.position.x shouldBe xOnScreen +- tolerance
  }

  def createGameModelWithRocketAsPLayer1(rocket: Rocket) = {
    val players = Vector(Player(rocket, alive = true), createDummyPlayer())
    val planet = createDummyPlanet()
    val model = GameModel(planet, players, Vector())

    model
  }

  def createGameModelFromMissile(missile: Missile): GameModel = {
    val players = createDummyPlayers()
    val planet = createDummyPlanet()
    val model = GameModel(planet, players, Vector(missile))

    model
  }

  private def createDummyPlanet() = {
    Planet(vec, 10)
  }

  private def createDummyPlayer() = {
    val rocket = Rocket(vec, vec, vec, 10)

    Player(rocket, alive = true)
  }

  private def createDummyPlayers() = {
    val rocket = Rocket(vec, vec, vec, 10)

    Vector(createDummyPlayer(), createDummyPlayer())
  }

  private def createDummyPlayerInput() = {
    Vector(PlayerInput(), PlayerInput())
  }

}
