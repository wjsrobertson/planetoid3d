package net.xylophones.planetoid.game.logic

import net.xylophones.planetoid.game.maths.Vector2D
import net.xylophones.planetoid.game.model.GameEvent.GameEvent
import net.xylophones.planetoid.game.model._
import org.junit.runner.RunWith
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, FunSuite}
import org.scalatest.junit.JUnitRunner
import net.xylophones.planetoid.game.logic.ModelTestObjectMother._

@RunWith(classOf[JUnitRunner])
class ExplosionHandlingGameUpdaterTest extends FunSuite with Matchers with MockitoSugar {

  val underTest = new ExplosionHandlingGameUpdater

  test("explosions created for both players and round countdown created") {
    // given
    val events = Set(GameEvent.PlayerLoseLife, GameEvent.Player1LoseLife, GameEvent.Player2LoseLife)
    val player1 = createDummyPlayerAtPosition(Vector2D(10, 10))
    val player2 = createDummyPlayerAtPosition(Vector2D(100, 100))
    val players = Players(player1, player2)
    val model = GameModel(createDummyPlanet(), players)
    val gameResults: GameModelUpdateResult = new GameModelUpdateResult(model, events)

    // when
    val results = underTest.update(gameResults, new GamePhysics, null)

    // then
    results.model.explosions.size shouldBe 2

    val explosion1 = Explosion(player1.rocket.position, player1.rocket.radius)
    val explosion2 = Explosion(player2.rocket.position, player2.rocket.radius)

    results.model.explosions should contain (explosion1)
    results.model.explosions should contain (explosion2)
    results.model.endRoundTimer.isDefined shouldBe true
  }

  test("explosions NOT created when no lives lost") {
    // given
    val events: Set[GameEvent] = Set.empty
    val model = GameModel(createDummyPlanet(), createDummyPlayers())
    val gameResults: GameModelUpdateResult = new GameModelUpdateResult(model, events)

    // when
    val results = underTest.update(gameResults, new GamePhysics, null)

    // then
    results.model.explosions.size shouldBe 0
  }

}
