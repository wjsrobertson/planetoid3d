package net.xylophones.planetoid.game

import net.xylophones.planetoid.game.logic.GameModelUpdater
import net.xylophones.planetoid.game.logic.ModelTestObjectMother._
import net.xylophones.planetoid.game.model.{GameModelUpdateResult, GameContainer, GamePhysics, PlayerInput}
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar
import org.scalatest.{FunSuite, Matchers}
import org.mockito.Mockito._
import org.mockito.Matchers.{eq => eqm}

@RunWith(classOf[JUnitRunner])
class GameManagerTest extends FunSuite with Matchers /*with MockFactory*/ with MockitoSugar {

  val modelUpdater = mock[GameModelUpdater]
  val result = mock[GameModelUpdateResult]
  val inputCaptor = ArgumentCaptor.forClass(classOf[Vector[PlayerInput]])

  val underTest = new GameManager(modelUpdater)

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

  private def createGameContainer() = {
    new GameContainer(
      "game ID",
      "player 1 ID",
      "player 2 ID",
      new GamePhysics,
      createDummyModel(),
      PlayerInput(),
      PlayerInput()
    )
  }
}
