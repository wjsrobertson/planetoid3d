"use strict";

var Planetoid = Planetoid || {};

Planetoid.LiveGameDetails = function (physics) {

    var _physics = physics;
    var _userInput = {left: false, right: false, thrust: false, fireMissile: false};
    var _gameModel;

    return {
        setGameModel: function (gameModel) {
            _gameModel = gameModel;
        },
        getGameModel: function () {
            return _gameModel;
        },
        setUserInput: function (userInput) {
            _userInput = userInput;
        },
        getUserInput: function () {
            return _userInput;
        },
        getPhysics: function () {
            return _physics;
        }
    }
};