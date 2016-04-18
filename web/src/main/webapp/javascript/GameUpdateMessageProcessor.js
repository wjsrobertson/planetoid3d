//"use strict";

var Planetoid = Planetoid || {};

Planetoid.GameUpdateMessageProcessor = function (liveGameDetails, soundPlayer) {

    var _liveGameDetails = liveGameDetails;
    var _soundPlayer = soundPlayer;

    function updateGameModel(gameModel) {
        _liveGameDetails.setGameModel(gameModel);
    }

    function handleEvent(event) {
        _soundPlayer.playSound(event);
    }

    return {
        processGameUpdate: function (gameUpdate) {
            updateGameModel(gameUpdate.model);

            gameUpdate.events.forEach(function (event) {
                handleEvent(event);
            });
        }
    }
};