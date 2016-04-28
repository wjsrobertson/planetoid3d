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

    val player1 = model.players.p1
    val p1Result = checkMissileOrPlanetCollision(player1, model.players.p2.missiles, model.planet)

    val player2 = model.players.p2
    val p2Result = checkMissileOrPlanetCollision(player2, model.players.p1.missiles, model.planet)

    if (p1Result.isCollision || p2Result.isCollision) {
      val p1Lives = if (p1Result.isCollision) player1.numLives - 1 else player1.numLives
      val p2Points = if (p1Result.isCollision) player2.points + 1 else player2.points

      val p2Lives = if (p2Result.isCollision) player2.numLives - 1 else player2.numLives
      val p1Points = if (p2Result.isCollision) player1.points + 1 else player1.points

      val p1 = Player(player1.rocket, p1Lives, p1Points, Vector.empty)
      val p2 = Player(player2.rocket, p2Lives, p2Points, Vector.empty)

      val playerEvent = if (p1Lives != player1.numLives) GameEvent.Player1LoseLife
                        else GameEvent.Player2LoseLife

      val newModel = model.copy(players = Players(p1, p2))
      val events = initialResult.events + playerEvent + GameEvent.PlayerLoseLife
      new GameModelUpdateResult(newModel, events)
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
    } else if (r1.isCollision && r2.isCollision) {
      CollisionResult(isCollision = true, r1.impactMissiles ++ r2.impactMissiles)
    } else {
      CollisionResult.empty
    }
  }
}
