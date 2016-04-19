"use strict";

var Planetoid = Planetoid || {};

Planetoid.MessageSender = function (webSocketContainer) {

    function createMessage(messageType, payload) {
        return messageType + ":" + payload;
    }

    return {
        sendMessage(messageType, payload) {
            var message = createMessage(messageType, payload);

            webSocketContainer.getWebSocket().send(message);
        }
    }
};