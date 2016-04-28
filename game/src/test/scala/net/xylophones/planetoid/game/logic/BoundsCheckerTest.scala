package net.xylophones.planetoid.game.logic

import net.xylophones.planetoid.game.maths.Vector2D
import net.xylophones.planetoid.game.model.GamePhysics
import org.junit.runner.RunWith
import org.scalatest.{FunSuite, Matchers}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class BoundsCheckerTest extends FunSuite with Matchers {

  val underTest = new BoundsChecker()

  test("point is within bounds") {
    // given
    val position = Vector2D(5, 5)
    val phys = new GamePhysics(universeHeight = 10, universeWidth = 10)

    // when
    val isWithinBounds = underTest.isWithinBounds(position, phys)

    // then
    isWithinBounds shouldBe true
  }

  test("point is outside bounds") {
    // given
    val position = Vector2D(15, 15)
    val phys = new GamePhysics(universeHeight = 10, universeWidth = 10)

    // when
    val isWithinBounds = underTest.isWithinBounds(position, phys)

    // then
    isWithinBounds shouldBe false
  }

  test("point is warped over width") {
    // given
    val position = Vector2D(15, 5)
    val phys = new GamePhysics(universeHeight = 10, universeWidth = 10)

    // when
    val warped = underTest.warpOverBounds(position, phys)

    // then
    val isWarped = warped ~= Vector2D(5, 5)
    isWarped shouldBe true
  }

  test("point is warped over width when negative") {
    // given
    val position = Vector2D(-5, 5)
    val phys = new GamePhysics(universeHeight = 10, universeWidth = 10)

    // when
    val warped = underTest.warpOverBounds(position, phys)

    // then
    val isWarped = warped ~= Vector2D(5, 5)
    isWarped shouldBe true
  }

  test("point is warped over height when negative") {
    // given
    val position = Vector2D(5, -5)
    val phys = new GamePhysics(universeHeight = 10, universeWidth = 10)

    // when
    val warped = underTest.warpOverBounds(position, phys)

    // then
    val isWarped = warped ~= Vector2D(5, 5)
    isWarped shouldBe true
  }

  test("point is warped over width and height when negative") {
    // given
    val position = Vector2D(-5, -5)
    val phys = new GamePhysics(universeHeight = 10, universeWidth = 10)

    // when
    val warped = underTest.warpOverBounds(position, phys)

    // then
    val isWarped = warped ~= Vector2D(5, 5)
    isWarped shouldBe true
  }

}
