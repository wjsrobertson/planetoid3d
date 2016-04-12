package net.xylophones.planetoid.game.logic

import scala.collection.mutable.MutableList
import net.xylophones.planetoid.game.model.{Player, GameEvent, GameModel, GamePhysics}

class GameCollisionUpdater(collisionCalculator: CollisionCalculator) {

  def update(model: GameModel, physics: GamePhysics): (GameModel, Seq[GameEvent.Value]) = {
    var events = new MutableList[GameEvent.Value]
    var player1Alive = true
    val playerRocket = model.players(0).rocket

    if (collisionCalculator.isCollision(playerRocket, model.planet)) {
      player1Alive = false
      events += GameEvent.PlayerLoseLife
    }

    val newPlayer1 = if (player1Alive) model.players(0) else Player(playerRocket, alive = false)
    val newModel = GameModel(model.planet, Vector(newPlayer1, model.players(1)), model.missiles )

    (newModel, events)
  }

}
