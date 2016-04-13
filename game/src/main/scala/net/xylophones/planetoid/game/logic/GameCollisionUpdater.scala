package net.xylophones.planetoid.game.logic

import net.xylophones.planetoid.game.model._

class GameCollisionUpdater(collisionCalculator: CollisionCalculator) {

  private case class CollisionResult(val player: Player,
                                     val isCollision: Boolean = false,
                                     val events: Set[GameEvent.Value] = Set.empty,
                                     val impactMissiles: Set[Missile] = Set.empty)

  def updateForCollisions(model: GameModel, physics: GamePhysics): GameModelUpdateResult = {
    val p1Result = checkMissileOrPlanetCollision(model.players(0), model.missiles, model.planet)
    val p2Result = checkMissileOrPlanetCollision(model.players(1), model.missiles, model.planet)

    if (p1Result.isCollision || p2Result.isCollision) {
      val impactMissiles = p1Result.impactMissiles ++ p2Result.impactMissiles
      val remainingMissiles = model.missiles -- impactMissiles

      val newModel = GameModel(model.planet, Vector(p1Result.player, p2Result.player), remainingMissiles)
      val events = p1Result.events ++ p2Result.events

      new GameModelUpdateResult(newModel, events)
    } else {
      new GameModelUpdateResult(model, Set.empty)
    }
  }

  private def checkMissileOrPlanetCollision(player: Player, missiles: Set[Missile], planet: Planet) = {
    val missileResult = checkMissileCollision(player, missiles)
    val planetResult = checkPlanetCollision(player, planet)

    mergeCollisionResults(missileResult, planetResult)
  }

  private def checkMissileCollision(player: Player, missiles: Set[Missile]) = {
    val collidingMissiles = missiles.filter((m: Missile) => collisionCalculator.isCollision(m, player.rocket))

    if (collidingMissiles.nonEmpty) {
      val updatedPlayer = Player(player.rocket, alive = false)

      CollisionResult(player = updatedPlayer, isCollision = true, events = Set(GameEvent.PlayerLoseLife), impactMissiles = collidingMissiles)
    } else CollisionResult(player = player)
  }

  private def checkPlanetCollision(player: Player, planet: Planet): CollisionResult = {
    val isColliding = collisionCalculator.isCollision(player.rocket, planet)

    if (isColliding) {
      val updatedPlayer = Player(player.rocket, alive = false)

      CollisionResult(player = updatedPlayer, isCollision = true, events = Set(GameEvent.PlayerLoseLife))
    } else {
      CollisionResult(player = player)
    }
  }

  private def mergeCollisionResults(r1: CollisionResult, r2: CollisionResult) = {
    if (r1.isCollision && !r2.isCollision) {
      r1
    } else if (r2.isCollision && !r1.isCollision) {
      r2
    } else {
      CollisionResult(r1.player, isCollision = true, r1.events ++ r2.events, r1.impactMissiles ++ r2.impactMissiles)
    }
  }
}
