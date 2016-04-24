package net.xylophones.planetoid.game.logic

import net.xylophones.planetoid.game.logic.ModelTestObjectMother._
import net.xylophones.planetoid.game.model._
import org.junit.runner.RunWith
import org.mockito.Matchers.{any, eq => eqm}
import org.mockito.Mockito._
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar
import org.scalatest.{FunSuite, Matchers}

@RunWith(classOf[JUnitRunner])
class GameUpdaterTest extends FunSuite with Matchers with MockitoSugar {

  val inGameUpdater = mock[GameModelResultUpdater]
  val roundStartUpdater = mock[RoundStartCountdownUpdater]
  val roundTimer = mock[RoundCountdownTimer]

  val updaters = Vector(inGameUpdater)
  val underTest = new GameUpdater(updaters, roundStartUpdater)

  test("round start check is not performed when timer is complete") {
    // given
    val physics = new GamePhysics
    val input: Vector[PlayerInput] = Vector()
    val model = GameModel(createDummyPlanet(), createDummyPlayers(), roundTimer)

    when(roundTimer.isComplete).thenReturn(true)

    // when
    val result = underTest.update(model, physics, input)

    // then
    verifyNoMoreInteractions(roundStartUpdater)
    verify(inGameUpdater).update(any(classOf[GameModelUpdateResult]), eqm(physics), eqm(input))
  }

  test("round start check is performed when timer is not complete") {
    // given
    val model = GameModel(createDummyPlanet(), createDummyPlayers(), roundTimer)
    when(roundTimer.isComplete).thenReturn(false)

    // when
    val result = underTest.update(model, new GamePhysics, Vector())

    // then
    verify(roundStartUpdater).updateRoundTimer(model)
    verifyNoMoreInteractions(inGameUpdater)
  }

}
