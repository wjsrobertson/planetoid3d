package net.xylophones.planetoid.game.model

class GameContainer(val gameId: String,
                    val player1Id: String,
                    val player2Id: String,
                    val physics: GamePhysics,
                    @volatile var model: GameModel,
                    @volatile var player1Input: PlayerInput,
                    @volatile var player2Input: PlayerInput)
