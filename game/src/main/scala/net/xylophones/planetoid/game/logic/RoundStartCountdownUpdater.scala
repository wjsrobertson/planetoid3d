package net.xylophones.planetoid.game.logic

import net.xylophones.planetoid.game.model.{RoundCountdownTimer, GameModel, GameModelUpdateResult, GameEvent}

class RoundStartCountdownUpdater(currentTimeSource: CurrentTimeSource) {

  def updateRoundTimer(model: GameModel) = {
    val timer = model.roundTimer

    val now = currentTimeSource.currentTime()
    val msSinceLastUpdate = now - timer.lastTimeStampMs
    val newRemainingTime = Math.max(timer.remainingTimeMs - msSinceLastUpdate, 0)

    val isNewRound = newRemainingTime == 0

    val round = if (isNewRound) timer.round + 1
                else timer.round
    val newRoundTimer = RoundCountdownTimer(round, now, newRemainingTime)

    val events: Set[GameEvent.Value] = if (isNewRound) Set(GameEvent.RoundStart)
                                       else Set.empty
    val newModel = model.copy(roundTimer = newRoundTimer)

    new GameModelUpdateResult(newModel, events)
  }

}
