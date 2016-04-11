package net.xylophones.planetoid.game.logic

import net.xylophones.planetoid.game.maths.Vector2D
import net.xylophones.planetoid.game.model.{GamePhysics, Missile}
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class MissileCalculatorTest extends FunSuite with Matchers {

  val underTest = new MissileCalaulator()

  test("missile should move according to speed") {
    // given
    val position = Vector2D(0, 0)
    val rotation = Vector2D(0, 1)
    val missile = Missile(position, rotation)
    val physics = new GamePhysics(missileSpeed = 10)

    // when
    val updatedMissile = underTest.updateMissile(missile, physics)

    // then
    val correctPosition = updatedMissile.position ~= Vector2D(0, 10)
    correctPosition shouldBe true
  }

}