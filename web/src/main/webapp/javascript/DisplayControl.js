"use strict";

var Planetoid = Planetoid || {};

Planetoid.DisplayControl = function (elements) {

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
        }
    }
};

