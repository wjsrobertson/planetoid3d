package net.xylophones.planetoid.game

import net.xylophones.planetoid.game.logic.GameUpdater
import net.xylophones.planetoid.game.logic.ModelTestObjectMother._
import net.xylophones.planetoid.game.model._
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfterEach, FunSuite, Matchers}
import org.mockito.Mockito._
import org.mockito.Matchers.{eq => eqm}

@RunWith(classOf[JUnitRunner])
class GameManagerTest extends FunSuite with Matchers with MockitoSugar with BeforeAndAfterEach {

  val modelUpdater = mock[GameUpdater]
  val result = mock[GameModelUpdateResult]
  val model = mock[GameModel]
  val inputCaptor = ArgumentCaptor.forClass(classOf[Vector[PlayerInput]])

  val underTest = new GameManager(modelUpdater)

  override def beforeEach() = {
    when(result.model).thenReturn(model)
  }

  test("game can be added then removed") {
    // given
    val container = createGameContainer()

    // when
    val gameId = underTest.addGame(container)
    // then
    gameId shouldBe "game ID"

    // when
    val removed: Option[GameContainer] = underTest.removeGame(gameId)
    removed.isDefined shouldBe true
  }

  test("user input gets applied when game is updated") {
    // given
    val container = createGameContainer()
    val userInput = PlayerInput(thrust = true)
    underTest.addGame(container)

    // when
    when(modelUpdater.update(eqm(container.model), eqm(container.physics), inputCaptor.capture())).thenReturn(result)
    underTest.applyUserInput("game ID", "player 1 ID", userInput)
    underTest.updateGame("game ID")

    // then
    val playerInputs = inputCaptor.getValue
    playerInputs should have size 2

    val p1Input = playerInputs(0)
    p1Input.thrust shouldBe true
  }

  test("game is removed once complete") {
    // given
    val container = createGameContainerWithWinner()
    underTest.addGame(container)

    // when
    when(modelUpdater.update(eqm(container.model), eqm(container.physics), inputCaptor.capture())).thenReturn(result)
    underTest.updateGame("game ID")

    // then
    underTest.numCurrentGames shouldBe 0
  }
}
