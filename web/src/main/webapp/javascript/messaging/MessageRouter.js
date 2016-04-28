"use strict";

var Planetoid = Planetoid || {};

Planetoid.MessageRouter = function (messageProcessors) {

    return {
        routeMessage: function(message){
            var messageProcessor = messageProcessors[message.messageType];
            if (messageProcessor) {
                console.log("received message with type " + message.messageType);
                messageProcessor.processMessage(message.payload);
            }
        }
    }
};