package net.xylophones.planetoid.game.logic

import org.scalatest.FunSuite

class CurrentTimeSourceTest extends FunSuite {

  val underTest = new CurrentTimeSource

  test("current time is returned") {
    // given CurrentTimeSource instance and
    val aFewSeconds = 2000

    // when
    val time = underTest.currentTime()

    // then
    System.currentTimeMillis() - time < aFewSeconds
  }

}
