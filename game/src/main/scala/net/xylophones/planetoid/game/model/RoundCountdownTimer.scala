package net.xylophones.planetoid.game.model

case class RoundCountdownTimer(val round: Int = 0,
                               val lastTimeStampMs: Long = System.currentTimeMillis,
                               val remainingTimeMs: Long = 0) {

  def isComplete = remainingTimeMs == 0

}