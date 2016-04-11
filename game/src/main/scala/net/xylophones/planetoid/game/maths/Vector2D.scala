package net.xylophones.planetoid.game.maths

import scala.math._

case class Vector2D(val x: Double, val y: Double) {

  def +(that: Vector2D) = Vector2D(x + that.x, y + that.y)

  def -(that: Vector2D) = Vector2D(x - that.x, y - that.y)

  def *(scale: Double) = Vector2D(x * scale, y * scale)

  def /(scale: Double) = Vector2D(x / scale, y / scale)

  def dot(that: Vector2D) = (that.x * x) + (that.y * y)

  def magnitude = Math.sqrt(x * x + y * y)

  def truncate(maxLength: Double): Vector2D = {
    if (magnitude > maxLength) this * (maxLength / magnitude)
    else this
  }

  def normalise = {
    if (magnitude > 0) this * (1 / magnitude)
    else Vector2D(0, 0)
  }

  def rotate(radians: Double): Vector2D = {
    val x1 = (x * cos(radians)) - (y * sin(radians))
    val y1 = (x * sin(radians)) + (y * cos(radians))

    Vector2D(x1, y1)
  }

  def ~=(that: Vector2D) = {
    val epsilon = 0.00001

    abs(x - that.x) < epsilon && abs(y - that.y) < epsilon
  }
}