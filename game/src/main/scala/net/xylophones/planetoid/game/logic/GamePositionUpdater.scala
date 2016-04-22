package net.xylophones.planetoid.game.logic

import net.xylophones.planetoid.game.model._

class GamePositionUpdater(missileCalculator: MissilePositionCalculator,
                          rocketCalculator: RocketPositionCalculator,
                          boundsChecker: BoundsChecker) {

  def updateRocketAndMissilePositions(model: GameModel, physics: GamePhysics, playerInputs: IndexedSeq[PlayerInput]) = {
    val updatedPlayers = updateRocketsWarpingIfOffScreen(model, physics, playerInputs)

    new GameModelUpdateResult(GameModel(model.planet, updatedPlayers), Set.empty)
  }

  private def updateRocketsWarpingIfOffScreen(model: GameModel, physics: GamePhysics, inputs: IndexedSeq[PlayerInput]) = {
    val playerInput = Map(
      (model.players(0), inputs(0)),
      (model.players(1), inputs(1))
    )

    val updatedPlayers = model.players.map(p => {
      val missiles = updateMissilesRemovingIfOffScreen(p.missiles, physics)
      val rocket = rocketCalculator.updateRocketPosition(p.rocket, playerInput(p), model.planet, physics)
      Player(rocket, p.numLives, p.points, missiles)
    })

    updatedPlayers
  }

  private def updateMissilesRemovingIfOffScreen(missiles: IndexedSeq[Missile], physics: GamePhysics) = {
    val updatedMissiles = missiles
      .map(m => missileCalculator.updateMissilePosition(m, physics))
      .filter(m => boundsChecker.isWithinBounds(m.position, physics))

    updatedMissiles
  }
}
