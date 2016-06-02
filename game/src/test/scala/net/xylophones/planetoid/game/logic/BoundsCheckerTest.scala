package net.xylophones.planetoid.game.logic

import net.xylophones.planetoid.game.maths.Vector3D
import net.xylophones.planetoid.game.model.GamePhysics
import org.junit.runner.RunWith
import org.scalatest.{FunSuite, Matchers}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class BoundsCheckerTest extends FunSuite with Matchers {

  val underTest = new BoundsChecker()

  test("point is within bounds") {
    // given
    val position = Vector3D(5, 5, 5)
    val phys = new GamePhysics(universeWidth = 10)

    // when
    val isWithinBounds = underTest.isWithinBounds(position, phys)

    // then
    isWithinBounds shouldBe true
  }

  test("point is outside bounds") {
    // given
    val position = Vector3D(15, 15, 15)
    val phys = new GamePhysics(universeWidth = 10)

    // when
    val isWithinBounds = underTest.isWithinBounds(position, phys)

    // then
    isWithinBounds shouldBe false
  }

  test("point is warped over x") {
    // given
    val position = Vector3D(11, 0, 0)
    val phys = new GamePhysics(universeWidth = 10)

    // when
    val warped = underTest.warpOverBounds(position, phys)

    // then
    val isWarped = warped ~= Vector3D(-10, 0, 0)
    isWarped shouldBe true
  }

  test("point is warped over x when negative") {
    // given
    val position = Vector3D(-10, 0, 0)
    val phys = new GamePhysics(universeWidth = 10)

    // when
    val warped = underTest.warpOverBounds(position, phys)

    // then
    val isWarped = warped ~= Vector3D(10, 0, 0)
    isWarped shouldBe true
  }

  test("point is warped over y when negative") {
    // given
    val position = Vector3D(0, -10, 0)
    val phys = new GamePhysics(universeWidth = 10)

    // when
    val warped = underTest.warpOverBounds(position, phys)

    // then
    val isWarped = warped ~= Vector3D(0, 10, 0)
    isWarped shouldBe true
  }

  test("point is warped over x, y and z") {
    // given
    val position = Vector3D(10, 10, 10)
    val phys = new GamePhysics(universeWidth = 10)

    // when
    val warped = underTest.warpOverBounds(position, phys)

    // then
    val magnitude = Math.sqrt(10 * 10 + 10 * 10 + 10 * 10)
    val distance = -10 * (phys.universeWidth / magnitude)
    val isWarped = warped ~= Vector3D(distance, distance, distance)
    isWarped shouldBe true
  }

}
