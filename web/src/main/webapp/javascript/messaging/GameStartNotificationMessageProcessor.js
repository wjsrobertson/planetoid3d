"use strict";

var Planetoid = Planetoid || {};

Planetoid.GameStartNotificationMessageProcessor = function (gameDetails, displayControl, gameOrchestrator, soundPlayer) {
    return {
        processMessage: function (gameStartMessage) {
            gameDetails.setGameId(gameStartMessage.gameId);
            gameDetails.setOpponentName(gameStartMessage.opponentName);
            gameDetails.setGameInProggress(true);
            gameDetails.setIsPlayer1(gameStartMessage.isPlayerOne);

            displayControl.displayGameScreen();

            gameOrchestrator.startGameLoop();

            soundPlayer.playSound('GameStart');
        }
    }
};