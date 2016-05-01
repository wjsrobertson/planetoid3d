package net.xylophones.planetoid.game.logic

import net.xylophones.planetoid.game.model.{PlayerInput, GamePhysics, GameModelUpdateResult}

class RoundCompleteCountdownUpdater(currentTimeSource: CurrentTimeSource) extends GameModelResultUpdater {

  override def update(initialResults: GameModelUpdateResult, physics: GamePhysics, playerInputs: IndexedSeq[PlayerInput]): GameModelUpdateResult = {
    val model = initialResults.model

    if (model.endRoundTimer.isDefined) {
      val timer = model.endRoundTimer.get
      val now = currentTimeSource.currentTime()
      val msSinceLastUpdate = now - timer.lastTimeStampMs
      val newRemainingTime = Math.max(timer.remainingTimeMs - msSinceLastUpdate, 0)

      val newTimer = timer.copy(lastTimeStampMs = now, remainingTimeMs = newRemainingTime)

      val newModel = model.copy(endRoundTimer = Some(newTimer))
      new GameModelUpdateResult(newModel, initialResults.events)
    } else {
      initialResults
    }
  }
}
