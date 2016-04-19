"use strict";

var Planetoid = Planetoid || {};

Planetoid.MessageRouter = function (messageProcessors) {

    return {
        routeMessage: function(message){
            var messageProcessor = messageProcessors[message.messageType];
            if (messageProcessor) {
                messageProcessor.processMessage(message.payload);
            }
        }
    }
};