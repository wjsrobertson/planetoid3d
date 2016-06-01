package net.xylophones.planetoid.game.logic

import net.xylophones.planetoid.game.maths.Vector3D
import net.xylophones.planetoid.game.model.{GamePhysics, Missile}
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class MissilePositionCalculatorTest extends FunSuite with Matchers {

  val underTest = new MissilePositionCalculator

  test("missile should move according to speed") {
    // given
    val position = Vector3D(0, 0)
    val rotation = Vector3D(0, 1)
    val missile = new Missile(position, rotation, 999)
    val physics = new GamePhysics(missileSpeed = 10)

    // when
    val updatedMissile = underTest.updateMissilePosition(missile, physics)

    // then
    val correctPosition = updatedMissile.position ~= Vector3D(0, 10)
    correctPosition shouldBe true
  }

}