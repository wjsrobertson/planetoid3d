package net.xylophones.planetoid.game.logic

import net.xylophones.planetoid.game.model.{RoundCountdownTimer, GameEvent, GameModelUpdateResult, GameModel}
import org.junit.runner.RunWith
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, FunSuite}
import org.scalatest.junit.JUnitRunner
import org.mockito.Mockito._
import org.mockito.Matchers.{eq => eqm}
import net.xylophones.planetoid.game.logic.ModelTestObjectMother._

@RunWith(classOf[JUnitRunner])
class RoundStartCountdownUpdaterTest extends FunSuite with Matchers  with MockitoSugar{

  val currentTimeSource = mock[CurrentTimeSource]

  val underTest = new RoundStartCountdownUpdater(currentTimeSource)

  test("RoundStart event is triggered when timer runs out") {
    // given
    val model = GameModel(createDummyPlanet(), createDummyPlayers(),
      RoundCountdownTimer(0, 1000, 600))
    when(currentTimeSource.currentTime()).thenReturn(1600)

    // when
    val result = underTest.updateRoundTimer(model)

    // then
    result.events should contain (GameEvent.RoundStart)
    result.model.roundTimer.remainingTimeMs shouldBe 0
    result.model.roundTimer.round shouldBe 1
  }

  test("RoundStart event is not triggered when timer has not run out") {
    // given
    val model = GameModel(createDummyPlanet(), createDummyPlayers(),
      RoundCountdownTimer(0, 1000, 600))
    when(currentTimeSource.currentTime()).thenReturn(1599)

    // when
    val result = underTest.updateRoundTimer(model)

    // then
    result.events should not contain (GameEvent.RoundStart)
    result.model.roundTimer.remainingTimeMs shouldBe 1
    result.model.roundTimer.round shouldBe 0
  }

}
