package net.xylophones.planetoid.game

import net.xylophones.planetoid.game.maths.Vector2D
import net.xylophones.planetoid.game.model._

class GameContainerFactory {

  def createGameContainer(player1Id: String, player2Id: String) = {
    val phys = new GamePhysics
    val planetPosition = Vector2D(phys.universeWidth / 2, phys.universeHeight / 2)
    val planet = Planet(planetPosition, phys.planetRadius)

    val playerY = phys.universeHeight / 2

    val player1X = (phys.universeWidth - phys.planetRadius) / 4
    val player1Pos = Vector2D(player1X, playerY)
    val player1Rotation = (player1Pos - planetPosition).normalise
    val player1 = Player(Rocket(player1Pos, player1Rotation, Vector2D(0, 0), phys.rocketRadius), phys.numLives)

    val player2X = player1X * 3
    val player2Pos = Vector2D(player2X, playerY)
    val player2Rotation = (planetPosition - player1Pos).normalise
    val player2 = Player(Rocket(player2Pos, player2Rotation, Vector2D(0, 0), phys.rocketRadius), phys.numLives)

    val model = GameModel(planet, Vector(player1, player2))

    new GameContainer(uuid, player1Id, player2Id, phys, model, PlayerInput(), PlayerInput())
  }


  private def uuid = java.util.UUID.randomUUID.toString


}
