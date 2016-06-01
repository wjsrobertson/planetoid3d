package net.xylophones.planetoid.game.maths

import scala.math._

case class Vector3D(val x: Double,
                    val y: Double,
                    val z: Double) {

  def +(that: Vector3D) = Vector3D(x + that.x, y + that.y, z + that.z)

  def -(that: Vector3D) = Vector3D(x - that.x, y - that.y, z - that.z)

  def *(scale: Double) = Vector3D(x * scale, y * scale, z * scale)

  def /(scale: Double) = Vector3D(x / scale, y / scale, z / scale)

  def dot(that: Vector3D) = (that.x * x) + (that.y * y) + (that.z * z)

  def magnitude = Math.sqrt(x * x + y * y + z * z)

  def truncate(maxLength: Double): Vector3D = {
    if (magnitude > maxLength) this * (maxLength / magnitude)
    else this
  }

  def normalise = {
    if (magnitude > 0) this * (1 / magnitude)
    else Vector3D(0, 0, 0)
  }

  /*
  def rotate(radians: Double): Vector3D = {
    val x1 = (x * cos(radians)) - (y * sin(radians))
    val y1 = (x * sin(radians)) + (y * cos(radians))

    Vector3D(x1, y1)
  }
  */

  def ~=(that: Vector3D) = {
    val epsilon = 0.00001

    def isClose(v1: Double, v2: Double) = abs(v1 - v2) < epsilon

    isClose(x, that.x) && isClose(y, that.y) && isClose(z, that.z)
  }
}

object Vector3D {
  val zero = Vector3D(0, 0, 0)
}