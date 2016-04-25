package net.xylophones.planetoid.game

import java.util.concurrent.ConcurrentHashMap

import net.xylophones.planetoid.game.logic.GameUpdater
import net.xylophones.planetoid.game.model._

import scala.collection.convert.decorateAsScala._

class GameManager(val modelUpdater: GameUpdater) {

  private val games = new ConcurrentHashMap[String, GameContainer]().asScala

  def addGame(gameContainer: GameContainer) = {
    games.put(gameContainer.gameId, gameContainer)

    gameContainer.gameId
  }

  def removeGame(gameId: String) = {
    games.remove(gameId)
  }

  def applyUserInput(gameId: String, playerId: String, input: PlayerInput) = {
    val maybeGame: Option[GameContainer] = games.get(gameId)
    if (maybeGame.isDefined) {
      val game = maybeGame.get

      if (game.player1Id == playerId) {
        game.player1Input = input
      } else if (game.player2Id == playerId) {
        game.player2Input = input
      }
    }
  }

  def updateGame(gameId: String): Option[GameModelUpdateResult] = {
    val maybeGame: Option[GameContainer] = games.get(gameId)
    if (maybeGame.isDefined) {
      val game = maybeGame.get

      val model = game.model
      val inputs = Vector(game.player1Input, game.player2Input)
      val updateResults = modelUpdater.update(model, game.physics, inputs)

      game.model = updateResults.model

      removeGameIfComplete(game)

      Some(updateResults)
    } else {
      None
    }
  }

  def numCurrentGames = {
    games.size
  }

  private def removeGameIfComplete(game: GameContainer): Any = {
    if (game.model.winner != Winner.None) {
      removeGame(game.gameId)
    }
  }
}
