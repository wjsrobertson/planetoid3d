package net.xylophones.planetoid.game

import net.xylophones.planetoid.game.logic._

class PlanetoidsGameServiceFactory {

  def create() = {
    val boundsChecker = new BoundsChecker
    val collisionCalculator = new CollisionCalculator
    val missilePositionCalculator = new MissilePositionCalculator
    val missileFireUpdater = new GameMissileFireUpdater
    val rocketCalculator = new RocketPositionCalculator(boundsChecker)
    val collisionUpdater = new GameCollisionUpdater(collisionCalculator)
    val planetMissileCollisionUpdater = new MissilePlanetCollisionGameUpdater(collisionCalculator)
    val positionUpdater = new GamePositionUpdater(missilePositionCalculator, rocketCalculator, boundsChecker)
    val currentTimeSource = new CurrentTimeSource
    val roundStartUpdater = new RoundStartCountdownUpdater(currentTimeSource)
    val roundCompleteCountdownUpdater = new RoundCompleteCountdownUpdater(currentTimeSource)
    val rocketFactory = new RocketFactory
    val factory = new GameContainerFactory(rocketFactory)
    val roundCompleteUpdater = new RoundCompleteUpdater(rocketFactory)
    val explosionGameUpdater = new ExplosionHandlingGameUpdater
     val updaters = Vector(
       roundCompleteUpdater,
       positionUpdater,
       collisionUpdater,
       missileFireUpdater,
       planetMissileCollisionUpdater,
       explosionGameUpdater)
    val modelUpdater = new GameUpdater(updaters, roundStartUpdater, roundCompleteCountdownUpdater)
    val manager = new GameManager(modelUpdater)
    val service = new PlanetoidsGameService(manager, factory)

    service
  }

}
