package net.xylophones.planetoid.game.logic

import net.xylophones.planetoid.game.maths.Vector2D
import net.xylophones.planetoid.game.model._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FunSuite, Matchers}

@RunWith(classOf[JUnitRunner])
class GameCollisionUpdaterTest extends FunSuite with Matchers {

  val underTest = new GameCollisionUpdater(new CollisionCalculator)

  test("collision between player1 and planet is detected") {
    /*
    given
     */
    val planet = Planet(Vector2D(10, 10), 10)
    val player1 = Player(createRocketAt(Vector2D(10, 10)), alive = true)
    val physics = new GamePhysics()
    val model = GameModel(planet, Vector(player1, createDummyPlayer()), Vector())

    // when
    val result: (GameModel, Seq[GameEvent.Value]) = underTest.update(model, physics)

    // then
    val newModel = result._1
    val events = result._2

    newModel.players(0).alive shouldBe false
  }

  private def createRocketAt(position: Vector2D) = {
    Rocket(position, Vector2D(0,1), Vector2D(0,0), 5)
  }

  private def createDummyPlayer() = {
    val vec = Vector2D(0, 0)
    val rocket = Rocket(vec, vec, vec, 10)

    Player(rocket, alive = true)
  }

}
