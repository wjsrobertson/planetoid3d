"use strict";

var Planetoid = Planetoid || {};

Planetoid.GameUpdateMessageProcessor = function (gameDetails, soundPlayer, displayControl) {

    function updateGameModel(gameModel) {
        gameDetails.setGameModel(gameModel);
    }

    function handleEvent(gameEvent, gameModel) {
        soundPlayer.playSound(gameEvent);

        console.log("received game event " + gameEvent);

        if (gameEvent == 'GameOver') {
            handleGameOver(gameModel);
        } else if (gameEvent == 'PlayerLoseLife' || gameEvent == 'RoundInitialised' || gameEvent == 'RoundStart') {
            displayControl.updateStats();
        }
    }

    function handleGameOver(gameModel) {
        gameDetails.setGameInProggress(false);

        var winner = gameModel.winner;

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
                handleEvent(event, gameUpdate.model);
            });
        }
    }
};