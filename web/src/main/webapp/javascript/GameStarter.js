"use strict";

var Planetoid = Planetoid || {};

Planetoid.GameStarter = function (nameInput, startGameButtons, gameDetails, startRequestMessgeSender, displayControl) {

    function clearGameDetails() {
        gameDetails.setGameInProggress(false);
        gameDetails.setGameId(null);
        gameDetails.setOpponentName(null);
    }

    function startGame() {
        clearGameDetails();

        gameDetails.setUserName(nameInput.value);
        var id = (Math.random() + 1).toString(36).substr(2, 37); // TODO - better ID generation
        gameDetails.setUserId(id);
        startRequestMessgeSender.sendMessage();
        displayControl.displayWaitingScreen();
    }

    return {
        bindAndListenForGameStart: function() {
            startGameButtons.forEach(function(button) {
                button.addEventListener("click", startGame);
            });
        }
    }

};