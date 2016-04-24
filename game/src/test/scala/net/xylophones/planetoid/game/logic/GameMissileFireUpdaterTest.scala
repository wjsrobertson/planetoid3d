package net.xylophones.planetoid.game.logic

import net.xylophones.planetoid.game.model.{GamePhysics, PlayerInput, GameEvent, GameModel}
import net.xylophones.planetoid.game.logic.ModelTestObjectMother._
import org.junit.runner.RunWith
import org.scalatest.{FunSuite, Matchers}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class GameMissileFireUpdaterTest extends FunSuite with Matchers {

  val underTest = new GameMissileFireUpdater

  test("missile gets launched when player is firing") {
    // given
    val model = GameModel(createDummyPlanet(), createDummyPlayers())
    val player1Input = PlayerInput(left = false, right = false, thrust = false, fireMissile = true)
    val player2Input = PlayerInput(left = false, right = false, thrust = false, fireMissile = false)

    // when
    val result = underTest.update(resultFromModel(model), new GamePhysics(),  Vector(player1Input, player2Input))

    // then
    val newModel = result.model
    val events = result.events

    newModel.players.p1.missiles should have size 1
    val missileRotation = newModel.players.p1.missiles(0).rotation
    val missileRotationMatchesRocket = missileRotation ~= model.players.p1.rocket.rotation
    missileRotationMatchesRocket shouldBe true

    // and
    events.contains(GameEvent.MissileFired) shouldBe true
  }

}
