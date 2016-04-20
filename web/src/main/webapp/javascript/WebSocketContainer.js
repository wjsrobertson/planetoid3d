"use strict";

var Planetoid = Planetoid || {};

Planetoid.WebSocketContainer = function (messageRouter, websocketUrl) {

    var _webSocket;

    function onClose() {
        _webSocket = null;
    }

    function onMessage(event) {
        var message = JSON.parse(event.data);
        messageRouter.routeMessage(message);
    }

    function createWebSocket() {
        var webSocket = new WebSocket(websocketUrl);

        webSocket.onmessage = function (event) {
            onMessage(event);
        };

        webSocket.onclose = function () {
            onClose();
        };

        return webSocket;
    }

    return {
        getWebSocket: function() {
            if (! _webSocket) {
                _webSocket = createWebSocket();
            }

            return _webSocket;
        }
    }
};