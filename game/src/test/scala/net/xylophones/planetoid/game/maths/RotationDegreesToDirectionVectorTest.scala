package net.xylophones.planetoid.game.maths

import org.junit.runner.RunWith
import org.scalatest.{FunSuite, Matchers}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class RotationDegreesToDirectionVectorTest extends FunSuite with Matchers {

  val underTest = new RotationDegreesToDirectionVector

  test("0,0,0 faces negative z axis") {
    // given rotation 0,0,0
    val rotation = Vector3D(0,0,0)

    // when direction vector is calculated
    val direction = underTest.toDirection(rotation)

    // then the direction vector is facing the direction of the negative z axis
    val isFacingNegativeZ = direction ~= Vector3D(0, 0, -1)
    isFacingNegativeZ shouldBe true
  }

  test("0,90,0 faces negative x azis") {
    // given rotation 0,90,0
    val rotation = Vector3D(0,90,0)

    // when direction vector is calculated
    val direction = underTest.toDirection(rotation)

    // then the direction vector is facing the direction of the negative x axis
    val isFacingNegativeX = direction ~= Vector3D(-1, 0, 0)
    isFacingNegativeX shouldBe true
  }

  test("90,0,0 faces positive y azis") {
    // given rotation 0,90,0
    val rotation = Vector3D(90,0,0)

    // when direction vector is calculated
    val direction = underTest.toDirection(rotation)

    // then the direction vector is facing the direction of the positive y axis
    val isFacingPositiveY = direction ~= Vector3D(0, 1, 0)
    isFacingPositiveY shouldBe true
  }

  test("180,0,0 faces positive z axis") {
    // given rotation 0,0,0
    val rotation = Vector3D(180,0,0)

    // when direction vector is calculated
    val direction = underTest.toDirection(rotation)

    // then the direction vector is facing the direction of the negative z axis
    val isFacingPositiveZ = direction ~= Vector3D(0, 0, 1)
    isFacingPositiveZ shouldBe true
  }

  test("0,180,0 faces positive z axis") {
    // given rotation 0,0,0
    val rotation = Vector3D(0,180,0)

    // when direction vector is calculated
    val direction = underTest.toDirection(rotation)

    // then the direction vector is facing the direction of the positive z axis
    val isFacingPositiveZ = direction ~= Vector3D(0, 0, 1)
    isFacingPositiveZ shouldBe true
  }

}
