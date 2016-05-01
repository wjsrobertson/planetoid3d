package net.xylophones.planetoid.game.logic

import net.xylophones.planetoid.game.model._

class GameUpdater(gameModelResultUpdaters: IndexedSeq[GameModelResultUpdater], roundStartUpdater: RoundStartCountdownUpdater, roundCompleteCountdownUpdater: RoundCompleteCountdownUpdater) {

  def update(model: GameModel, physics: GamePhysics, playerInputs: IndexedSeq[PlayerInput]): GameModelUpdateResult = {
    if (isRoundEndCountdownInProgress(model)) {
      val results = new GameModelUpdateResult(model, Set.empty)
      roundCompleteCountdownUpdater.update(results, physics, playerInputs)
    } else if (isRoundStartCountdownInProgress(model)) {
      roundStartUpdater.updateRoundTimer(model)
    } else {
      var results = new GameModelUpdateResult(model, Set.empty)
      gameModelResultUpdaters.foreach(updater => results = updater.update(results, physics, playerInputs))
      results
    }
  }

  def isRoundEndCountdownInProgress(model: GameModel) = model.roundEndTimer.isDefined && ! model.roundEndTimer.get.isComplete

  def isRoundStartCountdownInProgress(model: GameModel) = ! model.roundTimer.isComplete
}
