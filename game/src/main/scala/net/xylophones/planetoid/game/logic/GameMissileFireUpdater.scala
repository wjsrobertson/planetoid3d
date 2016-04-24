package net.xylophones.planetoid.game.logic

import net.xylophones.planetoid.game.model._

class GameMissileFireUpdater extends GameModelResultUpdater {

  override def update(initialResult: GameModelUpdateResult, physics: GamePhysics, playerInputs: IndexedSeq[PlayerInput]): GameModelUpdateResult = {
    val model = initialResult.model
    val numMissilesAtStart = getNumMissiles(model)

    val p1 = createMissileIfFiring(model.players(0), playerInputs(0))
    val p2 = createMissileIfFiring(model.players(1), playerInputs(1))

    val newModel = model.copy(players = Vector(p1, p2))
    val missileFired = getNumMissiles(newModel) > numMissilesAtStart
    val newEvents: Set[GameEvent.Value] = if (missileFired) Set(GameEvent.MissileFired) else Set.empty

    new GameModelUpdateResult(newModel, newEvents ++ initialResult.events)
  }

  def getNumMissiles(model: GameModel): Int = {
    model.players(0).missiles.size + model.players(1).missiles.size
  }

  def createMissileIfFiring(player: Player, input: PlayerInput) = {
    if (player.missiles.isEmpty && input.fireMissile) {
      val missile = new Missile(player.rocket.position, player.rocket.rotation, 10)
      val missiles = player.missiles :+ missile

      Player(player.rocket, player.numLives, player.points, missiles)
    } else {
      player
    }
  }
}
