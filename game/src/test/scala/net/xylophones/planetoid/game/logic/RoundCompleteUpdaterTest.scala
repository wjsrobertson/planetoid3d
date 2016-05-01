package net.xylophones.planetoid.game.logic

import net.xylophones.planetoid.game.RocketFactory
import net.xylophones.planetoid.game.logic.ModelTestObjectMother._
import net.xylophones.planetoid.game.model._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FunSuite, Matchers}

@RunWith(classOf[JUnitRunner])
class RoundCompleteUpdaterTest extends FunSuite with Matchers {

  val completTimer = RoundCountdownTimer(remainingTimeMs = 0)
  val underTest = new RoundCompleteUpdater(new RocketFactory)

  test("player 1 wins and round not incremented when player 2 has no lives left and a PlayerLoseLife has been triggered") {
    // given
    val player1 = Player(createDummyRocket(), 1)
    val player2 = Player(createDummyRocket(), 0)
    val modelUpdateResult = new GameModelUpdateResult(GameModel(createDummyPlanet(), Players(player1, player2), endRoundTimer = Some(completTimer)), Set.empty)

    //when
    val result = underTest.update(modelUpdateResult, new GamePhysics, Vector.empty)

    // then
    result.model.winner shouldBe Winner.Player1
    result.events should contain(GameEvent.GameOver)
    result.model.roundTimer.round shouldBe 0
  }

  test("player 2 wins and round not incremented when player 1 has no lives left and a PlayerLoseLife has been triggered") {
    // given
    val player1 = Player(createDummyRocket(), 0)
    val player2 = Player(createDummyRocket(), 1)
    val modelUpdateResult = new GameModelUpdateResult(GameModel(createDummyPlanet(), Players(player1, player2), endRoundTimer = Some(completTimer)), Set.empty)

    //when
    val result = underTest.update(modelUpdateResult, new GamePhysics, Vector.empty)

    // then
    result.model.winner shouldBe Winner.Player2
    result.events should contain(GameEvent.GameOver)
    result.model.roundTimer.round shouldBe 0
  }

  test("draw and round not incremented when both player 1 and player 2 have no lives left and a PlayerLoseLife has been triggered") {
    // given
    val player1 = Player(createDummyRocket(), 0)
    val player2 = Player(createDummyRocket(), 0)
    val modelUpdateResult = new GameModelUpdateResult(GameModel(createDummyPlanet(), Players(player1, player2), endRoundTimer = Some(completTimer)), Set.empty)

    //when
    val result = underTest.update(modelUpdateResult, new GamePhysics, Vector.empty)

    // then
    result.model.winner shouldBe Winner.Draw
    result.events should contain(GameEvent.GameOver)
    result.model.roundTimer.round shouldBe 0
  }

  test("no winner and round incremented when both players have lives left and a PlayerLoseLife has been triggered") {
    // given
    val player1 = Player(createDummyRocket(), 1)
    val player2 = Player(createDummyRocket(), 1)
    val modelUpdateResult = new GameModelUpdateResult(GameModel(createDummyPlanet(), Players(player1, player2), endRoundTimer = Some(completTimer)), Set.empty)

    //when
    val result = underTest.update(modelUpdateResult, new GamePhysics, Vector.empty)

    // then
    result.model.winner shouldBe Winner.None
    result.events should not contain (GameEvent.GameOver)
    result.model.roundTimer.round shouldBe 1
  }

  test("if no winner then players positions are reset, round initialised event is triggered and end round timer is None") {
    // given
    val phys = new GamePhysics
    val player1 = Player(createDummyRocket(), 1)
    val player2 = Player(createDummyRocket(), 1)
    val modelUpdateResult = new GameModelUpdateResult(GameModel(createDummyPlanet(), Players(player1, player2), endRoundTimer = Some(completTimer)), Set.empty)

    //when
    val result = underTest.update(modelUpdateResult,phys, Vector.empty)

    // then
    result.model.players.p1.rocket.position.x should be < (phys.universeWidth / 2).toDouble
    result.model.players.p2.rocket.position.x should be > (phys.universeWidth / 2).toDouble
    result.events should contain(GameEvent.RoundInitialised)
    result.model.endRoundTimer.isDefined shouldBe false
  }
}
