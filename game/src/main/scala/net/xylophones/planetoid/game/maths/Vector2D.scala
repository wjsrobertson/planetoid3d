package net.xylophones.planetoid.game.maths

case class Vector2D(val x: Double, val y: Double) {

  def +(that: Vector2D) = Vector2D(x + that.x, y + that.y)

  def -(that: Vector2D) = Vector2D(x - that.x, y - that.y)

  def scaleBy(scale: Double) = Vector2D(x * scale, y * scale)

  def dot(that: Vector2D) = (that.x * x) + (that.y * y)

  def magnitude = Math.sqrt(x * x + y * y)

  def truncate(maxLength: Double): Vector2D = {
    if (magnitude > maxLength) this scaleBy (maxLength / magnitude)
    else this
  }

  def normalise = {
    if (magnitude > 0) this scaleBy (1 / magnitude)
    else Vector2D(0, 0)
  }

}
