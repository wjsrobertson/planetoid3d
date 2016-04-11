package net.xylophones.planetoid.game.logic

import net.xylophones.planetoid.game.maths.Vector2D
import net.xylophones.planetoid.game.model.{PlayerInput, Rocket, Planet, GamePhysics}
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner
import scala.math.Pi

@RunWith(classOf[JUnitRunner])
class RocketCalculatorTest extends FunSuite with Matchers {

  val underTest: RocketCalculator = new RocketCalculator()
  val planet: Planet = Planet(Vector2D(100, 100), 10)

  test("planet's gravity attracts rocket") {
    // given
    val physics: GamePhysics = new GamePhysics()
    val input = PlayerInput()
    val rocket = rocketAtOriginPointingUp()

    // when
    val updatedRocket: Rocket = underTest.updateRocket(rocket, input, planet, physics)

    // then
    val movingTowardsPlanet = updatedRocket.velocity.normalise ~= Vector2D(1, 1).normalise
    movingTowardsPlanet shouldBe true
  }

  test("rocket rotates when user input indicates left") {
    val physics: GamePhysics = new GamePhysics(rotationSpeed = Pi / 2, gForce = 1, thrustForce = 10)
    val input = PlayerInput(left = true)
    val rocket = rocketAtOriginPointingUp()

    // when
    val updatedRocket: Rocket = underTest.updateRocket(rocket, input, planet, physics)

    // then
    val rorationFaces90DegCounterClockwise = updatedRocket.rotation ~= Vector2D(-1, 0)
    rorationFaces90DegCounterClockwise shouldBe true
  }

  test("rocket rotates when user input indicates right") {
    val physics: GamePhysics = new GamePhysics(rotationSpeed = Pi / 2, gForce = 1, thrustForce = 10)
    val input = PlayerInput(right = true)
    val rocket = rocketAtOriginPointingUp()

    // when
    val updatedRocket: Rocket = underTest.updateRocket(rocket, input, planet, physics)

    // then
    val rorationFaces90DegClockwise = updatedRocket.rotation ~= Vector2D(1, 0)
    rorationFaces90DegClockwise shouldBe true
  }

  test("rocket moves forward when user input indicates thrust") {
    val physics: GamePhysics = new GamePhysics(gForce = 0, thrustForce = 10, spaceShipMass = 1)
    val input = PlayerInput(thrust = true)
    val rocket = rocketAtOriginPointingUp()

    // when
    val updatedRocket: Rocket = underTest.updateRocket(rocket, input, planet, physics)

    // then
    val positionIsUp10 = updatedRocket.position ~= Vector2D(0, 10)
    positionIsUp10 shouldBe true
  }

  def rocketAtOriginPointingUp(): Rocket = {
    val position = Vector2D(0, 0)
    val rotation = Vector2D(0, 1)
    val velocity = Vector2D(0, 0)

    Rocket(position, rotation, velocity)
  }
}