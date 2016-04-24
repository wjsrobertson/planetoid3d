package net.xylophones.planetoid.game.logic

import net.xylophones.planetoid.game.model._

class GameUpdater(gameModelResultUpdaters: IndexedSeq[GameModelResultUpdater], roundStartUpdater: RoundStartCountdownUpdater) {

  def update(model: GameModel, physics: GamePhysics, playerInputs: IndexedSeq[PlayerInput]): GameModelUpdateResult = {
    if (hasRoundStarted(model)) {
      var results = new GameModelUpdateResult(model, Set.empty)

      gameModelResultUpdaters.foreach(updater => results = updater.update(results, physics, playerInputs))

      results
    } else {
      roundStartUpdater.updateRoundTimer(model)
    }
  }

  def hasRoundStarted(model: GameModel) = model.roundTimer.isComplete


}
