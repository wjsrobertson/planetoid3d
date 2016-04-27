"use strict";

var Planetoid = Planetoid || {};

Planetoid.GameUpdateMessageProcessor = function (liveGameDetails, soundPlayer, displayControl) {

    function updateGameModel(gameModel) {
        liveGameDetails.setGameModel(gameModel);
    }

    function handleEvent(gameEvent) {
        soundPlayer.playSound(gameEvent);

        if (gameEvent == 'GameOver') {
            handleGameOver();
        }
    }

    function handleGameOver() {
        var winner = liveGameDetails.getGameModel().winner;

        if (winner == 'Draw') {
            displayControl.displayDrawScreen();
        } else if (winner == 'Player1') {
            if (liveGameDetails.isPlayer1()) {
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