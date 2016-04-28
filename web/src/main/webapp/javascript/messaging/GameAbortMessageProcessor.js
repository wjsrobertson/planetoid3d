"use strict";

var Planetoid = Planetoid || {};

Planetoid.GameAbortMessageProcessor = function (gameDetails, displayControl) {
    return {
        processMessage: function (gameAbortMessage) {
            if (gameDetails.getGameId() == gameAbortMessage.gameId) {
                displayControl.displayAbortScreen();
            }
        }
    }
};