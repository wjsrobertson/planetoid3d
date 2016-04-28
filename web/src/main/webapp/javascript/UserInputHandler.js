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

    function setOrUnsetActionFlag(event, setOrUnset) {
        if (gameDetails.isGameInProggress()) {
            var keyCode = event.keyCode;
            var actionName = keyCodesForActions[keyCode];
            if (actionName) {
                gameDetails.getUserInput()[actionName] = setOrUnset;
                playerInputMessageSender.sendMessage();

                event.stopPropagation();
                event.preventDefault();
            }
        }
    }

    return {
        bindAndListen: function () {
            container.addEventListener('keydown',
                function (event) {
                    setOrUnsetActionFlag(event, true);
                }
            );

            container.addEventListener('keyup',
                function (event) {
                    setOrUnsetActionFlag(event, false);
                }
            );
        }
    }
};