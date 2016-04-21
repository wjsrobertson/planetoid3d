"use strict";

var Planetoid = Planetoid || {};

Planetoid.UserInputHandler = function (container, gameDetails, playerInputMessageSender) {

    var keyCodesForActions = {
        37: 'left',
        39: 'right',
        38: 'thrust',
        32: 'fireMissile',
        40: 'reverseThrust'
    };

    return {
        bindAndListenForGameStart: function () {
            container.addEventListener('keydown',
                function (event) {
                    if (gameDetails.isGameInProggress()) {
                        var keyCode = event.keyCode;
                        var actionName = keyCodesForActions[keyCode];
                        if (actionName) {
                            gameDetails.getUserInput()[actionName] = true;
                            playerInputMessageSender.sendMessage();
                        }
                    }
                }
            );

            container.addEventListener('keyup',
                function (event) {
                    if (gameDetails.isGameInProggress()) {
                        var keyCode = event.keyCode;
                        var actionName = keyCodesForActions[keyCode];
                        if (actionName) {
                            gameDetails.getUserInput()[actionName] = false;
                            playerInputMessageSender.sendMessage();
                        }
                    }
                }
            );
        }
    }
};