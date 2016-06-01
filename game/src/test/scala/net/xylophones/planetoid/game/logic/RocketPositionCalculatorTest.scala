package net.xylophones.planetoid.game.logic

import net.xylophones.planetoid.game.maths.Vector3D
import net.xylophones.planetoid.game.model.{PlayerInput, Rocket, Planet, GamePhysics}
import net.xylophones.planetoid.game.logic.ModelTestObjectMother._
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner
import scala.math.Pi

@RunWith(classOf[JUnitRunner])
class RocketPositionCalculatorTest extends FunSuite with Matchers {

  val underTest: RocketPositionCalculator = new RocketPositionCalculator(new BoundsChecker())
  val planet: Planet = Planet(Vector3D(100, 100, 100), 999)

  test("planet's gravity attracts rocket") {
    // given
    val physics: GamePhysics = new GamePhysics(gForce=0.2)
    val input = PlayerInput()
    val rocket = createRocketAtOriginPointingUp()

    // when
    val updatedRocket: Rocket = underTest.updateRocketPosition(rocket, input, planet, physics)

    // then
    val movingTowardsPlanet = updatedRocket.velocity.normalise ~= Vector3D(1, 1, 1).normalise
    movingTowardsPlanet shouldBe true
  }

  ignore("rocket rotates when user input indicates left") {
    val physics: GamePhysics = new GamePhysics(rocketRotationSpeed = Pi / 2, gForce = 1, rocketThrustForce = 10)
    val input = PlayerInput(left = true)
    val rocket = createRocketAtOriginPointingUp()

    // when
    val updatedRocket: Rocket = underTest.updateRocketPosition(rocket, input, planet, physics)

    // then
    val rorationFaces90DegCounterClockwise = updatedRocket.rotation ~= Vector3D(-1, 0, 0)
    rorationFaces90DegCounterClockwise shouldBe true
  }

  ignore("rocket rotates when user input indicates right") {
    val physics: GamePhysics = new GamePhysics(rocketRotationSpeed = Pi / 2, gForce = 1, rocketThrustForce = 10)
    val input = PlayerInput(right = true)
    val rocket = createRocketAtOriginPointingUp()

    // when
    val updatedRocket: Rocket = underTest.updateRocketPosition(rocket, input, planet, physics)

    // then
    val rorationFaces90DegClockwise = updatedRocket.rotation ~= Vector3D(1, 0, 0)
    rorationFaces90DegClockwise shouldBe true
  }

  test("rocket moves forward when user input indicates thrust") {
    val physics: GamePhysics = new GamePhysics(gForce = 0, rocketThrustForce = 10, rocketMass = 1)
    val input = PlayerInput(thrust = true)
    val rocket = createRocketAtOriginPointingUp()

    // when
    val updatedRocket: Rocket = underTest.updateRocketPosition(rocket, input, planet, physics)

    // then
    val positionIsUp10 = updatedRocket.position ~= Vector3D(0, 10, 0)
    positionIsUp10 shouldBe true
  }

  test("rocket moves backwards when user input indicates reverse thrust") {
    val physics: GamePhysics = new GamePhysics(gForce = 0, rocketReverseThrustForce = -5, rocketMass = 1)
    val input = PlayerInput(reverseThrust = true)
    val rocket = createRocketAt10_0PointingUp()

    // when
    val updatedRocket: Rocket = underTest.updateRocketPosition(rocket, input, planet, physics)

    // then
    val positionIsUp10 = updatedRocket.position ~= Vector3D(0, 5, 0)
    positionIsUp10 shouldBe true
  }
}