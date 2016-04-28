package net.xylophones.planetoid.game.logic

import org.scalatest.FunSuite

class ExplosionHandlingGameUpdaterTest extends FunSuite {

  val underTest = new ExplosionHandlingGameUpdater

  test("update can be called") {
    underTest.update(null, null, null)
  }

}
