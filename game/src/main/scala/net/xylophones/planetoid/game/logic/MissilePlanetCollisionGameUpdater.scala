package net.xylophones.planetoid.game.logic

import net.xylophones.planetoid.game.model._

class MissilePlanetCollisionGameUpdater(collisionCalculator: CollisionCalculator) extends GameModelResultUpdater {

  override def update(initialResults: GameModelUpdateResult, physics: GamePhysics, playerInputs: IndexedSeq[PlayerInput]): GameModelUpdateResult = {
    val p1 = initialResults.model.players.p1
    val p2 = initialResults.model.players.p2

    val p1Missiles = filterMissilesCollidingWithPlanet(p1, initialResults.model.planet)
    val p2Missiles = filterMissilesCollidingWithPlanet(p2, initialResults.model.planet)

    if (someMissilesGotFiltered(p1.missiles, p2.missiles, p1Missiles, p2Missiles)) {
      val newPlayer1 = p1.copy(missiles = p1Missiles)
      val newPlayer2 = p2.copy(missiles = p2Missiles)
      val players = Players(newPlayer1, newPlayer2)
      val newModel = initialResults.model.copy(players = players)

      new GameModelUpdateResult(newModel, initialResults.events)
    } else {
      initialResults
    }
  }

  def someMissilesGotFiltered(p1OldMissiles: IndexedSeq[Missile], p2OldMissiles: IndexedSeq[Missile],
                              p1NewMissiles: IndexedSeq[Missile], p2NewMissiles: IndexedSeq[Missile]) = {

    p1NewMissiles.size != p1OldMissiles.size || p2NewMissiles.size != p2OldMissiles.size
  }

  def filterMissilesCollidingWithPlanet(player: Player, planet: Planet): IndexedSeq[Missile] = {
     player.missiles.filter(missile => ! collisionCalculator.isCollision(missile, planet))
  }
}
