package net.xylophones.planetoid.game.logic

import net.xylophones.planetoid.game.model._

class GameModelUpdater(positionUpdater: GamePositionUpdater,
                       collisionUpdater: GameCollisionUpdater,
                       missileFireUpdater: GameMissileFireUpdater) {

  def update(model: GameModel, physics: GamePhysics, playerInputs: IndexedSeq[PlayerInput]) = {
    val positionResult = positionUpdater.updateRocketAndMissilePositions(model, physics, playerInputs)
    val collisionResults = collisionUpdater.updateForCollisions(positionResult.model, physics)
    val missileResults = missileFireUpdater.updateToFireMissiles(collisionResults.model, playerInputs)

    val events = collisionResults.events ++  missileResults.events ++ positionResult.events

    new GameModelUpdateResult(missileResults.model, events)
  }

}
