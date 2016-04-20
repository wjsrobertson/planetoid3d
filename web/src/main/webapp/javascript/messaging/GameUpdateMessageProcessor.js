"use strict";

var Planetoid = Planetoid || {};

Planetoid.GameUpdateMessageProcessor = function (liveGameDetails, soundPlayer) {

    function updateGameModel(gameModel) {
        liveGameDetails.setGameModel(gameModel);
    }

    function handleEvent(event) {
        soundPlayer.playSound(event);
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