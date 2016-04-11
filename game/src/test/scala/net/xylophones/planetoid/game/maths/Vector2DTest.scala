package net.xylophones.planetoid.game.maths

import org.junit.runner.RunWith
import org.scalatest.{FunSuite, _}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class Vector2DTest extends FunSuite with Matchers {

  val tolerance: Double = 0.00001

  test("vector addition using only positive numbers") {
    // given
    val v1 = Vector2D(1, 2)
    val v2 = Vector2D(3, 4)

    // when
    val result = v1 + v2

    // then
    result.x shouldBe 4d +- tolerance
    result.y shouldBe 6d +- tolerance
  }

  test("vector addition using with negative numbers") {
    // given
    val v1 = Vector2D(-1, -2)
    val v2 = Vector2D(3, 5)

    // when
    val result = v1 + v2

    // then
    result.x shouldBe 2d +- tolerance
    result.y shouldBe 3d +- tolerance
  }

  test("vector subtraction using only positive numbers") {
    // given
    val v1 = Vector2D(1, 2)
    val v2 = Vector2D(3, 4)

    // when
    val result = v1 - v2

    // then
    result.x shouldBe -2d +- tolerance
    result.y shouldBe -2d +- tolerance
  }

  test("vector subtraction using with negative numbers") {
    // given
    val v1 = Vector2D(-1, -2)
    val v2 = Vector2D(-3, -5)

    // when
    val result = v1 - v2

    // then
    result.x shouldBe 2d +- tolerance
    result.y shouldBe 3d +- tolerance
  }

  test("scaling") {
    // given
    val v1 = Vector2D(10, 20)

    // when
    val result = v1 * 10

    // then
    result.x shouldBe 100d +- tolerance
    result.y shouldBe 200d +- tolerance
  }

  test("magnitude") {
    // given
    val v1 = Vector2D(7, 24)

    // when
    val mag = v1.magnitude

    // then
    mag shouldBe 25d +- tolerance
  }

  test("truncating when over max length") {
    // given
    val v1 = Vector2D(7, 24)

    // when
    val result = v1 truncate 1

    // then
    result.magnitude shouldBe 1d +- tolerance
    result.x shouldBe (7d/25d) +- tolerance
    result.y shouldBe (24d/25d) +- tolerance
  }

  test("truncating when under max length") {
    // given
    val v1 = Vector2D(1, 1)

    // when
    val result = v1 truncate 2

    // then
    v1 shouldBe result
  }

  test("normalising") {
    // given
    val v1 = Vector2D(7, 24)

    // when
    val result = v1.normalise

    // then
    result.magnitude shouldBe 1d +- tolerance
    result.x shouldBe (7d/25d) +- tolerance
    result.y shouldBe (24d/25d) +- tolerance
  }

  test("normalising for zero length") {
    // given
    val v1 = Vector2D(0, 0)

    // when
    val result = v1.normalise

    // then
    v1 shouldBe result
  }

  test("dot product") {
    // given
    val v1 = Vector2D(-6, 8)
    val v2 = Vector2D(5, 12)

    // when
    val result = v1 dot v2

    // then
    result shouldBe 66d +- tolerance
  }

  test("approximate comparison (~=) when similar") {
    // given
    val v1 = Vector2D(1.0000001, 1)

    // when
    val approxEqual = v1 ~= Vector2D(1, 1)

    // then
    approxEqual shouldBe true
  }

  test("approximate comparison (~=) when different") {
    // given
    val v1 = Vector2D(1.0000001, 1)

    // when
    val approxEqual = v1 ~= Vector2D(9, 9)

    // then
    approxEqual shouldBe false
  }

  test("rotate") {
    // given
    val v1 = Vector2D(0, 1)

    // when
    val rotated = v1.rotate(scala.math.Pi / 2)

    // then
    val approxEqual = rotated ~= Vector2D(-1, 0)
    approxEqual shouldBe true
  }
}
