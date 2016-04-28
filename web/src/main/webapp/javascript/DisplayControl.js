"use strict";

var Planetoid = Planetoid || {};

Planetoid.DisplayControl = function (elements, gameDetails) {

    var show = function (element) {
        element.style.display = 'block';
    };

    var hide = function (element) {
        element.style.display = 'none';
    };

    var hideChildren = function (element) {
        for (var i = 0; i < element.children.length; i++) {
            var child = element.children[i];

            hide(child);
        }
    };

    var showNotificationPane = function (paneElement) {
        hideChildren(elements.notificationArea);
        hide(elements.canvas);
        show(paneElement);
        show(elements.notificationArea);
    };

    return {
        displayGameScreen: function () {
            hide(elements.notificationArea);
            show(elements.canvas);
        },
        displayWinScreen: function () {
            showNotificationPane(elements.winPane);
        },
        displayLoseScreen: function () {
            showNotificationPane(elements.losePane);
        },
        displayDrawScreen: function () {
            showNotificationPane(elements.drawPane);
        },
        displayStartScreen: function () {
            showNotificationPane(elements.loginPane);
        },
        displayWaitingScreen: function () {
            showNotificationPane(elements.waitingPane);
        },
        displayAbortScreen: function() {
            showNotificationPane(elements.abortPane);
        },
        // TODO - pass in elements
        updateStats: function () {
            var gameModel = gameDetails.getGameModel();

            document.getElementById("player1-points").textContent = gameModel.players.p1.points;
            document.getElementById("player2-points").textContent = gameModel.players.p2.points;

            document.getElementById("player1-lives").textContent = gameModel.players.p1.numLives;
            document.getElementById("player2-lives").textContent = gameModel.players.p2.numLives;

            var player1Name = gameDetails.isPlayer1() ? gameDetails.getUserName() : gameDetails.getOpponentName();
            var player2Name = gameDetails.isPlayer1() ? gameDetails.getOpponentName() : gameDetails.getUserName();

            document.getElementById("player1-name").textContent = player1Name;
            document.getElementById("player2-name").textContent = player2Name;
        }
    }
};

