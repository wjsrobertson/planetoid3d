package net.xylophones.planetoid.game.logic

import net.xylophones.planetoid.game.model._

class GameCollisionUpdater(collisionCalculator: CollisionCalculator) extends GameModelResultUpdater {

  private case class CollisionResult(val isCollision: Boolean = false,
                                     val impactMissiles: IndexedSeq[Missile] = IndexedSeq.empty)

  private object CollisionResult {
    def empty = CollisionResult()
  }

  override def update(initialResult: GameModelUpdateResult, physics: GamePhysics, playerInputs: IndexedSeq[PlayerInput]): GameModelUpdateResult = {
    val model = initialResult.model

    val player1 = model.players(0)
    val p1Result = checkMissileOrPlanetCollision(player1, model.players(1).missiles, model.planet)

    val player2 = model.players(1)
    val p2Result = checkMissileOrPlanetCollision(player2, model.players(0).missiles, model.planet)

    if (p1Result.isCollision || p2Result.isCollision) {
      val p1Lives = if (p1Result.isCollision) player1.numLives - 1 else player1.numLives
      val p2Missiles = if (p1Result.isCollision) player2.missiles diff p1Result.impactMissiles else player2.missiles

      val p2Lives = if (p2Result.isCollision) player2.numLives - 1 else player2.numLives
      val p1Missiles = if (p2Result.isCollision) player1.missiles diff p1Result.impactMissiles else player1.missiles

      val p1 = Player(player1.rocket, p1Lives, player1.points, p1Missiles)
      val p2 = Player(player2.rocket, p2Lives, player2.points, p2Missiles)

      val events = Set(GameEvent.PlayerLoseLife)
      val newModel = model.copy(players = Vector(p1, p2))

      new GameModelUpdateResult(newModel, events ++ initialResult.events)
    } else {
      new GameModelUpdateResult(model, initialResult.events)
    }
  }

  private def checkMissileOrPlanetCollision(player: Player, missiles: IndexedSeq[Missile], planet: Planet) = {
    val missileResult = checkMissileCollision(player, missiles)
    val planetResult = checkPlanetCollision(player, planet)

    mergeCollisionResults(missileResult, planetResult)
  }

  private def checkMissileCollision(player: Player, missiles: IndexedSeq[Missile]) = {
    val collidingMissiles = missiles.filter((m: Missile) => collisionCalculator.isCollision(m, player.rocket))

    if (collidingMissiles.nonEmpty) CollisionResult(isCollision = true, impactMissiles = collidingMissiles)
    else CollisionResult.empty
  }

  private def checkPlanetCollision(player: Player, planet: Planet): CollisionResult = {
    val isColliding = collisionCalculator.isCollision(player.rocket, planet)

    if (isColliding) CollisionResult(isCollision = true)
    else CollisionResult.empty
  }

  private def mergeCollisionResults(r1: CollisionResult, r2: CollisionResult) = {
    if (r1.isCollision && !r2.isCollision) {
      r1
    } else if (r2.isCollision && !r1.isCollision) {
      r2
    } else {
      CollisionResult(isCollision = true, r1.impactMissiles ++ r2.impactMissiles)
    }
  }
}
