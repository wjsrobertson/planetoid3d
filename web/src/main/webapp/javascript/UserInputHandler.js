"use strict";

var Planetoid = Planetoid || {};

Planetoid.UserInputHandler = function(container, liveGameDetails) {

    var _liveGameDetails = liveGameDetails;

    container.addEventListener('keydown',
        function (event) {
            pressedKeys[event.keyCode] = true;
        }
    );

    container.addEventListener('keyup',
        function (event) {
            pressedKeys[event.keyCode] = false;
        }
    );

    /*
    Keys = {
        LEFT_KEY: 37,
        RIGHT_KEY: 39,
        UP_KEY: 38,
        DOWN_KEY: 40,
        SPACE_KEY: 32,
        S_KEY: 83,
        N_KEY: 78,
        A_KEY: 65
    };
    */

};