package net.xylophones.planetoid.game.logic

import net.xylophones.planetoid.game.model._

class ExplosionHandlingGameUpdater extends GameModelResultUpdater {

  override def update(initialResults: GameModelUpdateResult, physics: GamePhysics, playerInputs: IndexedSeq[PlayerInput]): GameModelUpdateResult = {
    val events = initialResults.events
    val model = initialResults.model

    if (events.contains(GameEvent.PlayerLoseLife)) {
      val p1 = model.players.p1
      val p2 = model.players.p2

      val e1: Option[Explosion] = if (events.contains(GameEvent.Player1LoseLife)) Some(Explosion(p1.rocket.position, p1.rocket.radius))
                                  else None
      val e2: Option[Explosion] = if (events.contains(GameEvent.Player2LoseLife)) Some(Explosion(p2.rocket.position, p2.rocket.radius))
                                  else None

      val explosions: Set[Explosion] = model.explosions ++ e1 ++ e2
      val endGameTimer = RoundCountdownTimer(remainingTimeMs = physics.explosionDurationMilliseconds)
      new GameModelUpdateResult(model.copy(explosions = explosions, roundEndTimer = Some(endGameTimer)), events + GameEvent.Explosion)
    } else {
      initialResults
    }
  }
}
