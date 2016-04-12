package net.xylophones.planetoid.game.logic

import net.xylophones.planetoid.game.model.{GameEvent, GameModel, GamePhysics}

class GameCollisionUpdater(collisionCalculator: CollisionCalculator) {

  def update(model: GameModel, physics: GamePhysics): (GameModel, Seq[GameEvent]) = {
    (model, Nil)
  }

}
