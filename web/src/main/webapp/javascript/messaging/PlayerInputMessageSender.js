"use strict";

var Planetoid = Planetoid || {};

Planetoid.PlayerInputMessageSender = function (gameDetails, messageSender) {

    function createMessage() {
        //var message = Object.create({}, gameDetails.getUserInput());
        var message = JSON.parse(JSON.stringify(gameDetails.getUserInput()));
        //message.left = true;
        message.gameId = gameDetails.getGameId();
        message.userId = gameDetails.getUserId();

        return message;
    }

    return {
        sendMessage: function () {
            messageSender.sendMessage("ctrl", createMessage());
        }
    }
};
