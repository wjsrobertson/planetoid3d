package net.xylophones.planetoid.game.logic

import net.xylophones.planetoid.game.maths.{RotationDegreesToDirectionVector, Vector3D}
import net.xylophones.planetoid.game.model.{PlayerInput, Rocket, Planet, GamePhysics}
import net.xylophones.planetoid.game.logic.ModelTestObjectMother._
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner
import scala.math.Pi

@RunWith(classOf[JUnitRunner])
class RocketPositionCalculatorTest extends FunSuite with Matchers {

  val underTest: RocketPositionCalculator = new RocketPositionCalculator(new BoundsChecker, new RotationDegreesToDirectionVector)
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

  test("rocket rotates when user input indicates left") {
    val physics: GamePhysics = new GamePhysics(rocketRotationSpeed = 10, gForce = 1, rocketThrustForce = 10)
    val input = PlayerInput(left = true)
    val rocket = createRocketAtOriginPointingForward()

    // when
    val updatedRocket: Rocket = underTest.updateRocketPosition(rocket, input, planet, physics)

    // then
    val tenDegCounterClockwise = updatedRocket.rotation ~= Vector3D(10, 0, 0)
    tenDegCounterClockwise shouldBe true
  }

  test("rocket rotates when user input indicates right") {
    val physics: GamePhysics = new GamePhysics(rocketRotationSpeed = 10, gForce = 1, rocketThrustForce = 10)
    val input = PlayerInput(right = true)
    val rocket = createRocketAtOriginPointingUp()

    // when
    val updatedRocket: Rocket = underTest.updateRocketPosition(rocket, input, planet, physics)

    // then
    val tenDegClockwise = updatedRocket.rotation ~= Vector3D(80, 0, 0)
    tenDegClockwise shouldBe true
  }

  test("rocket rotates when user input indicates up") {
    val physics: GamePhysics = new GamePhysics(rocketRotationSpeed = 10, gForce = 1, rocketThrustForce = 10)
    val input = PlayerInput(up = true)
    val rocket = createRocketAtOriginPointingForward()

    // when
    val updatedRocket: Rocket = underTest.updateRocketPosition(rocket, input, planet, physics)

    // then
    val tenDegUp = updatedRocket.rotation ~= Vector3D(0, 10, 0)
    tenDegUp shouldBe true
  }

  test("rocket rotates when user input indicates down") {
    val physics: GamePhysics = new GamePhysics(rocketRotationSpeed = 10, gForce = 1, rocketThrustForce = 10)
    val input = PlayerInput(down = true)
    val rocket = createRocketAtOriginPointingForward()

    // when
    val updatedRocket: Rocket = underTest.updateRocketPosition(rocket, input, planet, physics)

    // then
    val tenDegDown = updatedRocket.rotation ~= Vector3D(0, -10, 0)
    tenDegDown shouldBe true
  }

  test("rocket moves forward when user input indicates thrust") {
    val physics: GamePhysics = new GamePhysics(gForce = 0, rocketThrustForce = 10, rocketMass = 1)
    val input = PlayerInput(thrust = true)
    val rocket = createRocketAtOriginPointingForward()

    // when
    val updatedRocket: Rocket = underTest.updateRocketPosition(rocket, input, planet, physics)

    // then
    val positionIsForward10 = updatedRocket.position ~= Vector3D(0, 0, -10)
    positionIsForward10 shouldBe true
  }

  test("rocket moves backwards when user input indicates reverse thrust") {
    val physics: GamePhysics = new GamePhysics(gForce = 0, rocketReverseThrustForce = -5, rocketMass = 1)
    val input = PlayerInput(reverseThrust = true)
    val rocket = createRocketAtOriginPointingForward()

    // when
    val updatedRocket: Rocket = underTest.updateRocketPosition(rocket, input, planet, physics)

    // then
    val positionIsBack5 = updatedRocket.position ~= Vector3D(0, 0, 5)
    positionIsBack5 shouldBe true
  }

  test("rocket moves backwards when user input indicates reverse thrust when facing up") {
    val physics: GamePhysics = new GamePhysics(gForce = 0, rocketReverseThrustForce = -5, rocketMass = 1)
    val input = PlayerInput(reverseThrust = true)
    val rocket = createRocketAtOriginPointingUp()

    // when
    val updatedRocket: Rocket = underTest.updateRocketPosition(rocket, input, planet, physics)

    // then
    val positionIsUp5 = updatedRocket.position ~= Vector3D(0, -5, 0)
    positionIsUp5 shouldBe true
  }
}