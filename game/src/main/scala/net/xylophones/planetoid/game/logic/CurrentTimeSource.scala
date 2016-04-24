package net.xylophones.planetoid.game.logic

/**
  * Mockable class providing current time in MS
  */
class CurrentTimeSource {

  def currentTime() = System.currentTimeMillis()

}
