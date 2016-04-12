package net.xylophones.planetoid.game.logic

import net.xylophones.planetoid.game.model._

class GamePositionUpdater(missileCalculator: MissileCalculator,
                          rocketCalculator: RocketCalculator,
                          boundsChecker: BoundsChecker) {

  def updateRocketAndMissilePositions(model: GameModel, physics: GamePhysics, playerInputs: IndexedSeq[PlayerInput]) = {
    val updatedMissiles = updateMissilesRemovingIfOffScreen(model, physics)
    val updatedPlayers = updateRocketsWarpingIfOffScreen(model, physics, playerInputs)

    GameModel(model.planet, updatedPlayers, updatedMissiles)
  }

  private def updateRocketsWarpingIfOffScreen(model: GameModel, physics: GamePhysics, inputs: IndexedSeq[PlayerInput]) = {
    val playerInput = Map(
      (model.players(0), inputs(0)),
      (model.players(1), inputs(1))
    )

    val updatedPlayers = model.players.map(p => {
      val rocket = rocketCalculator.updateRocketPosition(p.rocket, playerInput(p), model.planet, physics)
      Player(rocket, p.alive)
    })

    updatedPlayers
  }

  private def updateMissilesRemovingIfOffScreen(gameModel: GameModel, physics: GamePhysics) = {
    val updatedMissiles = gameModel.missiles
      .map(m => missileCalculator.updateMissilePosition(m, physics))
      .filter(m => boundsChecker.isWithinBounds(m.position, physics))

    updatedMissiles
  }
}
