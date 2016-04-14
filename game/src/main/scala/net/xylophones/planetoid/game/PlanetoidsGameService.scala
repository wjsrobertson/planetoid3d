package net.xylophones.planetoid.game

import net.xylophones.planetoid.game.model.PlayerInput

class PlanetoidsGameService(manager: GameManager, factory: GameContainerFactory) {

  def createGame(player1Id: String, player2Id: String): String = {
    val gameContainer = factory.createGameContainer(player1Id, player2Id)
    manager.addGame(gameContainer)

    gameContainer.gameId
  }

  def removeGame(gameId: String) = {
    manager.removeGame(gameId)
  }

  def applyUserInput(gameId: String, playerId: String, input: PlayerInput) = {
    manager.applyUserInput(gameId, playerId, input)
  }

  def updateGame(gameId: String) = {
    manager.updateGame(gameId)
  }

}
