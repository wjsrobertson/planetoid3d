package net.xylophones.planetoid.game

import net.xylophones.planetoid.game.maths.Vector3D
import net.xylophones.planetoid.game.model._

class GameContainerFactory(rocketFactory: RocketFactory) {

  def createGameContainer(player1Id: String, player2Id: String) = {
    val phys = new GamePhysics
    val planetPosition = Vector3D(0, 0, 0)
    val planet = Planet(planetPosition, phys.planetRadius)

    val p1Rocket = rocketFactory.getRocketAtInitialPosition(PlayerIdentifier.Player1, phys)
    val player1 = Player(p1Rocket, phys.numLives)

    val p2Rocket = rocketFactory.getRocketAtInitialPosition(PlayerIdentifier.Player2, phys)
    val player2 = Player(p2Rocket, phys.numLives)

    val roundTimer = RoundCountdownTimer(remainingTimeMs = phys.roundStartDelayMilliseconds)
    val model = GameModel(planet, Players(player1, player2), roundTimer = roundTimer)

    new GameContainer(uuid, player1Id, player2Id, phys, model, PlayerInput(), PlayerInput())
  }

  private def uuid = java.util.UUID.randomUUID.toString


}
