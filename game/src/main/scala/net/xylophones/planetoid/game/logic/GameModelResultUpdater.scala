package net.xylophones.planetoid.game.logic

import net.xylophones.planetoid.game.model.{GameModelUpdateResult, PlayerInput, GamePhysics, GameModel}

trait GameModelResultUpdater {

  def update(initialResults: GameModelUpdateResult, physics: GamePhysics, playerInputs: IndexedSeq[PlayerInput]): GameModelUpdateResult

}
