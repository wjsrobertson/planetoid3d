"use strict";

var Planetoid = Planetoid || {};

Planetoid.GameStartNotificationMessageProcessor = function (gameDetails) {
    return {
        processMessage: function (gameStartMessage) {
            gameDetails.setGameId(gameStartMessage.gameId);
            gameDetails.setGameInProggress(true);
        }
    }
};