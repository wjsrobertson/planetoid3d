"use strict";

var Planetoid = Planetoid || {};

Planetoid.GameStartRequestMessageSender = function (messageSender, gameDetails) {

    function createMessage() {
        return {
            'id': gameDetails.getUserId(),
            'name': gameDetails.getUserName()
        }
    }

    return {
        sendMessage: function () {
            messageSender.sendMessage("greq", createMessage());
        }
    }
};