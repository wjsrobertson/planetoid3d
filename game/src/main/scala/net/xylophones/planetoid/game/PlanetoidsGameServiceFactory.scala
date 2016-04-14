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
    val positionUpdater = new GamePositionUpdater(missilePositionCalculator, rocketCalculator, boundsChecker)
    val factory = new GameContainerFactory
    val modelUpdater = new GameModelUpdater(positionUpdater, collisionUpdater, missileFireUpdater)
    val manager = new GameManager(modelUpdater)
    val service = new PlanetoidsGameService(manager, factory)

    service
  }

}
