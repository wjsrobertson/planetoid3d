package net.xylophones.planetoid.game.logic

import net.xylophones.planetoid.game.RocketFactory
import net.xylophones.planetoid.game.model._

class RoundCompleteUpdater(rocketFactory: RocketFactory) extends GameModelResultUpdater {

  override def update(initialResult: GameModelUpdateResult, physics: GamePhysics, playerInputs: IndexedSeq[PlayerInput]): GameModelUpdateResult = {
    val model = initialResult.model

    if (isRoundEndTimerComplete(model)) {
      val winner = determineWinner(model.players.p1, model.players.p2)
      val p1Rocket = rocketFactory.getRocketAtInitialPosition(PlayerIdentifier.Player1, physics)
      val p2Rocket = rocketFactory.getRocketAtInitialPosition(PlayerIdentifier.Player2, physics)
      val players = Players.apply(model.players.p1.copy(rocket = p1Rocket), model.players.p2.copy(rocket = p2Rocket))

      if (winner == Winner.None) {
        val newTimer = createIncrementedRoundTimer(physics, model.roundTimer)
        val newModel = model.copy(players = players, winner = winner, roundTimer = newTimer, roundEndTimer = None, explosions = Set.empty)
        val event = GameEvent.RoundInitialised

        new GameModelUpdateResult(newModel, initialResult.events + event)
      } else {
        val newModel = model.copy(players = players, winner = winner)
        val event = GameEvent.GameOver

        new GameModelUpdateResult(newModel, initialResult.events + event)
      }
    } else {
      initialResult
    }
  }

  def isRoundEndTimerComplete(model: GameModel): Boolean = {
    model.roundEndTimer.isDefined && model.roundEndTimer.get.isComplete
  }

  def determineWinner(player1: Player, player2: Player): Winner.Value = {
    val winner = if (player1.numLives == 0 && player2.numLives == 0) Winner.Draw
                 else if (player1.numLives == 0) Winner.Player2
                 else if (player2.numLives == 0) Winner.Player1
                 else Winner.None

    winner
  }

  def createIncrementedRoundTimer(physics: GamePhysics, timer: RoundCountdownTimer) = {
    RoundCountdownTimer(timer.round + 1, remainingTimeMs = physics.roundStartDelayMilliseconds)
  }
}
