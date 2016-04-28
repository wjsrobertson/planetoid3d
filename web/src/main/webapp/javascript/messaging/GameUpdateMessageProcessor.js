"use strict";

var Planetoid = Planetoid || {};

Planetoid.GameUpdateMessageProcessor = function (gameDetails, soundPlayer, displayControl) {

    function updateGameModel(gameModel) {
        gameDetails.setGameModel(gameModel);
    }

    function handleEvent(gameEvent) {
        soundPlayer.playSound(gameEvent);

        if (gameEvent == 'GameOver') {
            handleGameOver();
        } else if (gameEvent == 'PlayerLoseLife' || gameEvent == 'RoundInitialised' || gameEvent == 'RoundStart') {
            displayControl.updateStats();
        }
    }

    function handleGameOver() {
        var winner = gameDetails.getGameModel().winner;

        if (winner == 'Draw') {
            displayControl.displayDrawScreen();
        } else if (winner == 'Player1') {
            if (gameDetails.isPlayer1()) {
                displayControl.displayWinScreen();
            } else {
                displayControl.displayLoseScreen();
            }
        }
    }

    return {
        processMessage: function (gameUpdate) {
            updateGameModel(gameUpdate.model);

            gameUpdate.events.forEach(function (event) {
                handleEvent(event);
            });
        }
    }
};