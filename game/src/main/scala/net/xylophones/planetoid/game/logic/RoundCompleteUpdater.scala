package net.xylophones.planetoid.game.logic

import net.xylophones.planetoid.game.model._

class RoundCompleteUpdater extends GameModelResultUpdater {

  override def update(initialResult: GameModelUpdateResult, physics: GamePhysics, playerInputs: IndexedSeq[PlayerInput]): GameModelUpdateResult = {
    val model = initialResult.model

    if (initialResult.events.contains(GameEvent.PlayerLoseLife)) {
      val winner = determineWinner(model.players(0), model.players(1))

      val events: Set[GameEvent.Value] = if (winner == Winner.None) Set.empty
                                         else Set(GameEvent.GameOver)

      val newTimer = if (winner == Winner.None) createIncrementedRoundTimer(physics, model.roundTimer)
                     else model.roundTimer

      val newModel = model.copy(roundTimer = newTimer, winner = winner)

      new GameModelUpdateResult(newModel, events ++ initialResult.events)
    } else {
      initialResult
    }
  }

  def determineWinner(player1: Player, player2: Player): Winner.Value = {
    val winner = if (player1.numLives == 0 && player2.numLives == 0) Winner.Draw
                 else if (player1.numLives == 0) Winner.Player2
                 else if (player2.numLives == 0) Winner.Player1
                 else Winner.None

    winner
  }

  def createIncrementedRoundTimer(physics: GamePhysics, timer: RoundCountdownTimer) = {
    RoundCountdownTimer(timer.round + 1, System.currentTimeMillis, -physics.roundStartDelayMilliseconds)
  }
}
