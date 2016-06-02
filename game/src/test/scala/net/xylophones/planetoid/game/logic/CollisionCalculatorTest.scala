package net.xylophones.planetoid.game.logic

import net.xylophones.planetoid.game.maths.Vector3D
import net.xylophones.planetoid.game.model.Circular
import org.junit.runner.RunWith
import org.scalatest.{Matchers, FunSuite}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class CollisionCalculatorTest extends FunSuite with Matchers {

  var underTest = new CollisionCalculator()

  test("two intersecting circles do not collide if < 70% of smallest radius away") {
    /*
    given
     */
    val c1 = new Circular {
      override def radius = 200

      override def position = Vector3D(0, 0, 0)
    }

    val c2 = new Circular {
      override def radius = 100

      override def position = Vector3D(269, 0, 0)
    }

    // when
    val collision = underTest.isCollision(c1, c2)

    // then
    collision shouldBe true
  }

  test("two intersecting circles do not collide if > 70% of smallest radius away") {
    /*
    given
     */
    val c1 = new Circular {
      override def radius = 200

      override def position = Vector3D(0, 0, 0)
    }

    val c2 = new Circular {
      override def radius = 100

      override def position = Vector3D(271, 0, 0)
    }

    // when
    val collision = underTest.isCollision(c1, c2)

    // then
    collision shouldBe false
  }

  test("two identical circles collide") {
    /*
    given
     */
    val c1 = new Circular {
      override def radius = 10

      override def position = Vector3D(0, 0, 0)
    }

    val c2 = c1

    // when
    val collision = underTest.isCollision(c1, c2)

    // then
    collision shouldBe true
  }

  test("two non-intersecting circles do not collide") {
    /*
    given
     */
    val c1 = new Circular {
      override def radius = 10

      override def position = Vector3D(0, 0, 0)
    }

    val c2 = new Circular {
      override def radius = 2

      override def position = Vector3D(90, 0, 0)
    }

    // when
    val collision = underTest.isCollision(c1, c2)

    // then
    collision shouldBe false
  }

}
