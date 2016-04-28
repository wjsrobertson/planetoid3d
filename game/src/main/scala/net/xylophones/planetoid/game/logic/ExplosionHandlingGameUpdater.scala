package net.xylophones.planetoid.game.logic

import net.xylophones.planetoid.game.model.{PlayerInput, GamePhysics, GameModelUpdateResult}

class ExplosionHandlingGameUpdater  extends GameModelResultUpdater {
  override def update(initialResults: GameModelUpdateResult, physics: GamePhysics, playerInputs: IndexedSeq[PlayerInput]): GameModelUpdateResult = {
    initialResults
  }
}
