package net.xylophones.planetoid.game.logic

import net.xylophones.planetoid.game.model.GameEvent._
import net.xylophones.planetoid.game.model.{GameEvent, PlayerInput, GamePhysics, GameModelUpdateResult}

class RoundCompleteCountdownUpdater(currentTimeSource: CurrentTimeSource) extends GameModelResultUpdater {

  override def update(initialResults: GameModelUpdateResult, physics: GamePhysics, playerInputs: IndexedSeq[PlayerInput]): GameModelUpdateResult = {
    val model = initialResults.model

    if (model.roundEndTimer.isDefined) {
      val timer = model.roundEndTimer.get
      val now = currentTimeSource.currentTime()
      val msSinceLastUpdate = now - timer.lastTimeStampMs
      val newRemainingTime = Math.max(timer.remainingTimeMs - msSinceLastUpdate, 0)

      val newTimer = timer.copy(lastTimeStampMs = now, remainingTimeMs = newRemainingTime)

      val events: Set[GameEvent] = if (newTimer.isComplete) Set(GameEvent.RoundComplete)
                                   else Set()

      val newModel = model.copy(roundEndTimer = Some(newTimer))
      new GameModelUpdateResult(newModel, initialResults.events ++ events)
    } else {
      initialResults
    }
  }
}
