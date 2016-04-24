package net.xylophones.planetoid.game.logic

import net.xylophones.planetoid.game.model._

class GamePositionUpdater(missileCalculator: MissilePositionCalculator,
                          rocketCalculator: RocketPositionCalculator,
                          boundsChecker: BoundsChecker) extends GameModelResultUpdater {

  override def update(initialResult: GameModelUpdateResult, physics: GamePhysics, playerInputs: IndexedSeq[PlayerInput]): GameModelUpdateResult = {
    val p1 = updatePlayer(initialResult.model.players.p1, playerInputs(0), initialResult.model, physics)
    val p2 = updatePlayer(initialResult.model.players.p1, playerInputs(1), initialResult.model, physics)
    val updatedPlayers = Players(p1, p2)

    val newModel = initialResult.model.copy(players = updatedPlayers)
    new GameModelUpdateResult(newModel, initialResult.events)
  }

  private def updatePlayer(player: Player, input: PlayerInput, model: GameModel, physics: GamePhysics) = {
    val missiles = updateMissilesRemovingIfOffScreen(player.missiles, physics)
    val rocket = rocketCalculator.updateRocketPosition(player.rocket, input, model.planet, physics)

    Player(rocket, player.numLives, player.points, missiles)
  }

  private def updateMissilesRemovingIfOffScreen(missiles: IndexedSeq[Missile], physics: GamePhysics) = {
    val updatedMissiles = missiles
      .map(m => missileCalculator.updateMissilePosition(m, physics))
      .filter(m => boundsChecker.isWithinBounds(m.position, physics))

    updatedMissiles
  }
}
