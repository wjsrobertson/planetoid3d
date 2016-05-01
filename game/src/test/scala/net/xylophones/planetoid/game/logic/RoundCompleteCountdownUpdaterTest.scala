package net.xylophones.planetoid.game.logic

import net.xylophones.planetoid.game.logic.ModelTestObjectMother._
import net.xylophones.planetoid.game.model.{GamePhysics, GameEvent, RoundCountdownTimer, GameModel}
import org.junit.runner.RunWith
import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, FunSuite}
import org.scalatest.junit.JUnitRunner


@RunWith(classOf[JUnitRunner])
class RoundCompleteCountdownUpdaterTest extends FunSuite with Matchers with MockitoSugar {

  val currentTimeSource = mock[CurrentTimeSource]

  val underTest = new RoundCompleteCountdownUpdater(currentTimeSource)

  test("counter gets decremented when exists") {
      // given
      val model = GameModel(createDummyPlanet(), createDummyPlayers(), roundEndTimer = Some(RoundCountdownTimer(0, 1000, 600)))
      when(currentTimeSource.currentTime()).thenReturn(1599)

      // when
      val result = underTest.update(resultFromModel(model), new GamePhysics(), null)

      // then
      result.model.roundEndTimer.get.remainingTimeMs shouldBe 1
    }

}
