package net.xylophones.planetoid.game

import net.xylophones.planetoid.game.maths.Vector2D
import net.xylophones.planetoid.game.model.{Rocket, PlayerIdentifier, GamePhysics}
import org.scalatest.{FunSuite, Matchers}

class RocketFactoryTest extends FunSuite with Matchers {

  val underTest = new RocketFactory

  test("player 1 is facing left") {
    // given
    val phys = new GamePhysics

    // when
    val rocket = underTest.getRocketAtInitialPosition(PlayerIdentifier.Player1, phys)

    // then
    val facingLeft = rocket.rotation ~= Vector2D(-1, 0)
    facingLeft shouldBe true
  }

  test("player 1 is left of planet") {
    // given
    val phys = new GamePhysics

    // when
    val rocket = underTest.getRocketAtInitialPosition(PlayerIdentifier.Player1, phys)

    // then

    rocket.position.x should be < (phys.universeWidth / 2).toDouble
  }

  test("player 2 is facing right") {
    // given
    val phys = new GamePhysics

    // when
    val rocket = underTest.getRocketAtInitialPosition(PlayerIdentifier.Player2, phys)

    // then
    val facingRight = rocket.rotation ~= Vector2D(1, 0)
    facingRight shouldBe true
  }

  test("player 2 is right of planet") {
    // given
    val phys = new GamePhysics

    // when
    val rocket = underTest.getRocketAtInitialPosition(PlayerIdentifier.Player2, phys)

    // then

    rocket.position.x should be > (phys.universeWidth / 2).toDouble
  }

}
