"use strict";

var Planetoid = Planetoid || {};

/**
 * Holds all the game state
 *
 * TODO - harmonise DTO names between JS / Java / Scala - userInput / GameController / PlayerInput
 */
Planetoid.GameDetails = function () {

    var _userInput = {
        'left': false,
        'right': false,
        'thrust': false,
        'fireMissile': false,
        'reverseThrust': false
    };
    var _gameInProgress;
    var _gameModel;
    var _userName;
    var _userId;
    var _gameId;
    var _opponentName;
    var _isPlayer1;

    return {
        setGameInProggress: function(gameInProggress) {
            _gameInProgress = gameInProggress
        },
        isGameInProggress: function() {
            return _gameInProgress;
        },
        setUserId: function(userId) {
            _userId = userId
        },
        getUserId: function() {
            return _userId;
        },
        setUserName: function(userName) {
            _userName = userName
        },
        getUserName: function() {
            return _userName;
        },
        setGameId: function(gameId) {
            _gameId = gameId;
        },
        getGameId: function() {
            return _gameId;
        },
        setGameModel: function (gameModel) {
            _gameModel = gameModel;
        },
        getGameModel: function () {
            return _gameModel;
        },
        getUserInput: function () {
            return _userInput;
        },
        setOpponentName: function(opponentName) {
            _opponentName = opponentName
        },
        getOpponentName: function() {
            return _opponentName;
        },
        setIsPlayer1: function(isPlayer1) {
            _isPlayer1 = isPlayer1
        },
        isPlayer1: function() {
            return _isPlayer1;
        }
    }
};