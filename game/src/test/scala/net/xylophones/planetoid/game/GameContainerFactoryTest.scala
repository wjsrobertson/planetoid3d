package net.xylophones.planetoid.game

import net.xylophones.planetoid.game.model.GameContainer
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FunSuite, Matchers}

@RunWith(classOf[JUnitRunner])
class GameContainerFactoryTest extends FunSuite with Matchers {

  val underTest = new GameContainerFactory

  test("game has correct player IDs") {
    val gameContainer: GameContainer = underTest.createGameContainer("p1", "p2")

    gameContainer.player1Id shouldBe "p1"
    gameContainer.player2Id shouldBe "p2"
  }

  test("game has a game ID") {
    val gameContainer: GameContainer = underTest.createGameContainer("p1", "p2")

    gameContainer.gameId.length should be > 0
  }

  test("players are not colliding with planet") {
    // when
    val gameContainer: GameContainer = underTest.createGameContainer("p1", "p2")

    /*
    then
     */
    val planet = gameContainer.model.planet

    val p1Distance: Double = (planet.position - gameContainer.model.players(0).rocket.position).magnitude
    (p1Distance > (planet.radius + gameContainer.model.players(0).rocket.radius)) shouldBe true

    val p2Distance = (planet.position - gameContainer.model.players(1).rocket.position).magnitude
    (p1Distance > (planet.radius + gameContainer.model.players(1).rocket.radius)) shouldBe true
  }

  test("players are not colliding with each other") {
    // when
    val gameContainer: GameContainer = underTest.createGameContainer("p1", "p2")

    /*
    then
     */
    val planet = gameContainer.model.planet

    val p1 = gameContainer.model.players(0).rocket
    val p2 = gameContainer.model.players(1).rocket

    val distance: Double = (p2.position - p1.position).magnitude
    (distance > (p1.radius + p2.radius)) shouldBe true
  }
}
