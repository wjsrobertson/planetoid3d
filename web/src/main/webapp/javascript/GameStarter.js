"use strict";

var Planetoid = Planetoid || {};

Planetoid.GameStarter = function (nameInput, startGameButton, gameDetails, startRequestMessgeSender) {

    function startGame() {
        gameDetails.setUserName(nameInput.value);
        var id = (Math.random() + 1).toString(36).substr(2, 37); // TODO - better ID generation
        gameDetails.setUserId(id);
        startRequestMessgeSender.sendMessage();
    }

    return {
        bindAndListenForGameStart: function() {
            startGameButton.addEventListener("click", startGame);
        }
    }

};