package net.xylophones.planetoid.game.logic

import net.xylophones.planetoid.game.maths.Vector3D
import net.xylophones.planetoid.game.model._
import net.xylophones.planetoid.game.logic.ModelTestObjectMother._
import org.junit.runner.RunWith
import org.scalatest.{FunSuite, Matchers}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class GamePositionUpdaterTest extends FunSuite with Matchers {

  val tolerance: Double = 0.00001

  val underTest = new GamePositionUpdater(
    new MissilePositionCalculator,
    new RocketPositionCalculator(new BoundsChecker),
    new BoundsChecker
  )

  test("missile gets removed if it is offscreen") {
    /*
     given
      */
    val phys = new GamePhysics
    val xOffscreen = phys.universeWidth + 99
    val missile = new Missile(Vector3D(xOffscreen, 10), Vector3D(1, 0), 10)
    val inputs = createDummyPlayerInput()
    val model = createGameModelWithPlayer1Missile(missile)

    // when
    val result = underTest.update(resultFromModel(model), phys, inputs)

    // then
    val newModel = result.model
    newModel.players.p1.missiles shouldBe empty
  }

  test("missile is not removed if it is onscreen") {
    /*
     given
      */
    val phys = new GamePhysics(missileSpeed = 0)
    val xOnScreen = phys.universeWidth - 10
    val missile = new Missile(Vector3D(xOnScreen, 10), Vector3D(1, 0), 10)
    val inputs = createDummyPlayerInput()
    val model = createGameModelWithPlayer1Missile(missile)

    // when
    val result = underTest.update(resultFromModel(model), phys, inputs)

    // then
    val newModel = result.model
    newModel.players.p1.missiles should have size 1
  }

  test("rocket gets warped to other side if it is offscreen") {
    /*
     given
     */
    val phys = new GamePhysics(gForce = 0)
    val xOffScreen = -(phys.universeWidth + 99)
    val rocket = Rocket(Vector3D(xOffScreen, 0, 0), vec, vec, 10)
    val inputs = createDummyPlayerInput()
    val model = createGameModelWithRocketAsPLayer1(rocket)

    // when
    val result = underTest.update(resultFromModel(model), phys, inputs)

    // then
    val newModel = result.model
    val player1 = newModel.players.p1
    player1.rocket.position.x shouldBe phys.universeWidth.toDouble +- tolerance
  }

  test("rocket does not get warped to other side if it is onscreen") {
    /*
     given
     */
    val phys = new GamePhysics(gForce = 0)
    val xOnScreen = 120d
    val rocket = Rocket(Vector3D(xOnScreen, 10), vec, vec, 10)
    val inputs = createDummyPlayerInput()
    val model = createGameModelWithRocketAsPLayer1(rocket)

    // when
    val result = underTest.update(resultFromModel(model), phys, inputs)

    // then
    val newModel = result.model
    val player1 = newModel.players.p1
    player1.rocket.position.x shouldBe xOnScreen +- tolerance
  }
}
