package net.xylophones.planetoid.game.logic

import net.xylophones.planetoid.game.logic.ModelTestObjectMother._
import net.xylophones.planetoid.game.maths.Vector2D
import net.xylophones.planetoid.game.model._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, FunSuite}
import org.mockito.Matchers.{any, eq => eqm}
import org.mockito.Mockito._

@RunWith(classOf[JUnitRunner])
class MissilePlanetCollisionGameUpdaterTest extends FunSuite with Matchers with MockitoSugar {

  val collisionCalculator = mock[CollisionCalculator]

  val underTest = new MissilePlanetCollisionGameUpdater(collisionCalculator)

  test("player 2 missiles get filtered when they are colliding with the planet") {
    // given
    val physics = new GamePhysics()
    val player1 = createDummyPlayer()
    val missile = new Missile(Vector2D(10, 10), Vector2D(0, 0), 2)
    val player2 = createDummyPlayerWithMissile(missile)
    val planet = createDummyPlanet()
    val model = GameModel(planet, Players(player1, player2))

    when(collisionCalculator.isCollision(missile, planet)).thenReturn(true);

    // when
    val result = underTest.update(resultFromModel(model), physics, Vector.empty)

    // then
    result.model.players.p2.missiles shouldBe empty
  }

  test("player 1 missiles get filtered when they are colliding with the planet") {
    // given
    val physics = new GamePhysics()
    val player2 = createDummyPlayer()
    val missile = new Missile(Vector2D(10, 10), Vector2D(0, 0), 2)
    val player1 = createDummyPlayerWithMissile(missile)
    val planet = createDummyPlanet()
    val model = GameModel(planet, Players(player1, player2))

    when(collisionCalculator.isCollision(missile, planet)).thenReturn(true);

    // when
    val result = underTest.update(resultFromModel(model), physics, Vector.empty)

    // then
    result.model.players.p2.missiles shouldBe empty
  }

  test("player 1 and player 2 missiles do not get filtered when they are not colliding with the planet") {
    // given
    val physics = new GamePhysics()
    val missile = new Missile(Vector2D(10, 10), Vector2D(0, 0), 2)
    val player1 = createDummyPlayerWithMissile(missile)
    val player2 = createDummyPlayerWithMissile(missile)
    val planet = createDummyPlanet()
    val model = GameModel(planet, Players(player1, player2))

    when(collisionCalculator.isCollision(missile, planet)).thenReturn(false)

    // when
    val result = underTest.update(resultFromModel(model), physics, Vector.empty)

    // then
    result.model.players.p1.missiles should not be empty
    result.model.players.p2.missiles should not be empty
  }
}
