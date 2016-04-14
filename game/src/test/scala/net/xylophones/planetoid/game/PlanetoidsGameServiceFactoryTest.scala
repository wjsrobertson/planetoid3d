package net.xylophones.planetoid.game

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FunSuite, Matchers}

@RunWith(classOf[JUnitRunner])
class PlanetoidsGameServiceFactoryTest extends FunSuite with Matchers {

  val underTest = new PlanetoidsGameServiceFactory

  test("usable service is created") {
    // when
    val service = underTest.create()

    // then
    val gameId: String = service.createGame("p1", "p2")
    (gameId.length > 0) shouldBe true
  }


}
