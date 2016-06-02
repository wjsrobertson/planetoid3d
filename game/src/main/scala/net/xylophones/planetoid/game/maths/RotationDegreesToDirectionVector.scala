package net.xylophones.planetoid.game.maths

/**
  * Convert a vector containing rotation around the x, y, z axes in degrees to a direction vector
  */
class RotationDegreesToDirectionVector {

  def toDirection(degrees: Vector3D): Vector3D = {
    val elevation = (degrees.x / 180) * Math.PI
    val heading = (degrees.y / 180) * Math.PI

    val x = -Math.sin(heading) * Math.cos(elevation)
    val y = Math.sin(elevation)
    val z = -Math.cos(elevation) * Math.cos(heading)

    Vector3D(x, y, z)
  }
}