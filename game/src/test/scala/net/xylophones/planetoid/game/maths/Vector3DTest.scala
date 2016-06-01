package net.xylophones.planetoid.game.maths

import org.junit.runner.RunWith
import org.scalatest.{FunSuite, _}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class Vector3DTest extends FunSuite with Matchers {

  val tolerance: Double = 0.00001

  test("vector addition using only positive numbers") {
    // given
    val v1 = Vector3D(1, 2, 3)
    val v2 = Vector3D(4, 5, 6)

    // when
    val result = v1 + v2

    // then
    result.x shouldBe 5d +- tolerance
    result.y shouldBe 7d +- tolerance
    result.z shouldBe 9d +- tolerance
  }

  test("vector addition using with negative numbers") {
    // given
    val v1 = Vector3D(-1, -2, -7)
    val v2 = Vector3D(3, 5, 9)

    // when
    val result = v1 + v2

    // then
    result.x shouldBe 2d +- tolerance
    result.y shouldBe 3d +- tolerance
    result.z shouldBe 2d +- tolerance
  }

  test("vector subtraction using only positive numbers") {
    // given
    val v1 = Vector3D(1, 2, 5)
    val v2 = Vector3D(3, 4, 6)

    // when
    val result = v1 - v2

    // then
    result.x shouldBe -2d +- tolerance
    result.y shouldBe -2d +- tolerance
    result.z shouldBe -1d +- tolerance
  }

  test("vector subtraction using with negative numbers") {
    // given
    val v1 = Vector3D(-1, -2, -4)
    val v2 = Vector3D(-3, -5, -6)

    // when
    val result = v1 - v2

    // then
    result.x shouldBe 2d +- tolerance
    result.y shouldBe 3d +- tolerance
    result.z shouldBe 2d +- tolerance
  }

  test("scaling") {
    // given
    val v1 = Vector3D(10, 20, 30)

    // when
    val result = v1 * 10

    // then
    result.x shouldBe 100d +- tolerance
    result.y shouldBe 200d +- tolerance
    result.z shouldBe 300d +- tolerance
  }

  test("division by scalar") {
    // given
    val v1 = Vector3D(10, 20, 30)

    // when
    val result = v1 / 10

    // then
    result.x shouldBe 1d +- tolerance
    result.y shouldBe 2d +- tolerance
    result.z shouldBe 3d +- tolerance
  }

  test("magnitude") {
    // given
    val v1 = Vector3D(7, 8, 9)

    // when
    val mag = v1.magnitude

    // then
    mag shouldBe 13.928388 +- tolerance
  }

  test("truncating when over max length") {
    // given
    val v1 = Vector3D(7, 8, 9)

    // when
    val result = v1 truncate 1

    // then
    result.magnitude shouldBe 1d +- tolerance
    result.x shouldBe (7d/13.928388) +- tolerance
    result.y shouldBe (8d/13.928388) +- tolerance
    result.z shouldBe (9d/13.928388) +- tolerance
  }

  test("truncating when under max length") {
    // given
    val v1 = Vector3D(1, 1, 1)

    // when
    val result = v1 truncate 2

    // then
    v1 shouldBe result
  }

  test("normalising") {
    // given
    val v1 = Vector3D(7, 8, 9)

    // when
    val result = v1.normalise

    // then
    result.magnitude shouldBe 1d +- tolerance
    result.x shouldBe (7d/13.928388) +- tolerance
    result.y shouldBe (8d/13.928388) +- tolerance
    result.z shouldBe (9d/13.928388) +- tolerance
  }

  test("normalising for zero length") {
    // given
    val v1 = Vector3D(0, 0, 0)

    // when
    val result = v1.normalise

    // then
    v1 shouldBe result
  }

  test("dot product") {
    // given
    val v1 = Vector3D(-6, 8, 0)
    val v2 = Vector3D(5, 12, 0)

    // when
    val result = v1 dot v2

    // then
    result shouldBe 66d +- tolerance
  }

  test("approximate comparison (~=) when similar") {
    // given
    val v1 = Vector3D(1.0000001, 1, 1)

    // when
    val approxEqual = v1 ~= Vector3D(1, 1, 1)

    // then
    approxEqual shouldBe true
  }

  test("approximate comparison (~=) when different") {
    // given
    val v1 = Vector3D(1.0000001, 1, 1)

    // when
    val approxEqual = v1 ~= Vector3D(9, 9, 9)

    // then
    approxEqual shouldBe false
  }
}
