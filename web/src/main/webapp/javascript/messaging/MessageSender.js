"use strict";

var Planetoid = Planetoid || {};

Planetoid.MessageSender = function (webSocketContainer) {

    function createMessage(messageType, payload) {
        return messageType + ":" + JSON.stringify(payload);
    }

    return {
        sendMessage(messageType, payload) {
            var message = createMessage(messageType, payload);

            webSocketContainer.sendWhenOpen(message);

                //.getWebSocket().send(message);
        }
    }
};