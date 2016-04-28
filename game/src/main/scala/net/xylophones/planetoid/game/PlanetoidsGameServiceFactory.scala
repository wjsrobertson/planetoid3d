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
    val rocketFactory = new RocketFactory
    val factory = new GameContainerFactory(rocketFactory)
    val roundCompleteUpdater = new RoundCompleteUpdater(rocketFactory)
    val updaters = Vector(positionUpdater, collisionUpdater, missileFireUpdater, roundCompleteUpdater, planetMissileCollisionUpdater)
    val modelUpdater = new GameUpdater(updaters, roundStartUpdater)
    val manager = new GameManager(modelUpdater)
    val service = new PlanetoidsGameService(manager, factory)

    service
  }

}
