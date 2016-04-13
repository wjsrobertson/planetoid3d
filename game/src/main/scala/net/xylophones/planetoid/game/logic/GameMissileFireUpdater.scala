package net.xylophones.planetoid.game.logic

import net.xylophones.planetoid.game.model._

class GameMissileFireUpdater {

  def updateToFireMissiles(model: GameModel, playerInputs: IndexedSeq[PlayerInput]): GameModelUpdateResult = {
    val p1Missile = createMissileIfFiring(model.players(0), playerInputs(0))
    val p2Missile = createMissileIfFiring(model.players(1), playerInputs(1))
    val missiles = model.missiles ++ p1Missile ++ p2Missile

    val newModel = GameModel(model.planet, model.players, missiles)
    val missileFired = p1Missile.isDefined || p2Missile.isDefined
    val events: Set[GameEvent.Value] = if (missileFired) Set(GameEvent.MissileFired) else Set.empty

    new GameModelUpdateResult(newModel, events)
  }

  def createMissileIfFiring(player: Player, input: PlayerInput) = {
    if (player.alive && input.fireMissile) {
      val missile = new Missile(player.rocket.position, player.rocket.rotation, 10)

      Some(missile)
    } else {
      None
    }
  }

}
