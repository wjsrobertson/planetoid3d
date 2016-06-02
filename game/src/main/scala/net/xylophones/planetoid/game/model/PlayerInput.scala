package net.xylophones.planetoid.game.model

case class PlayerInput(val left: Boolean = false,
                       val right: Boolean = false,
                       val thrust: Boolean = false,
                       val reverseThrust: Boolean = false,
                       val fireMissile: Boolean = false,
                       val up: Boolean = false,
                       val down: Boolean = false)