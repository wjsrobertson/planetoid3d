"use strict";

var Planetoid = Planetoid || {};

Planetoid.GameStartNotificationMessageProcessor = function (gameDetails) {
    return {
        processMessage: function (gameStartMessage) {
            gameDetails.setGameId(gameStartMessage.gameId);
            gameDetails.setOpponentName(gameStartMessage.opponentName);
            gameDetails.setGameInProggress(true);
            gameDetails.setIsPlayer1(gameStartMessage.isPlayerOne);
        }
    }
};