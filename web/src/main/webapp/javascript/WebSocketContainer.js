"use strict";

var Planetoid = Planetoid || {};

Planetoid.WebSocketContainer = function (messageRouter, websocketUrl) {

    var _webSocket = null;

    function onMessage(event) {
        var message = JSON.parse(event.data);
        messageRouter.routeMessage(message);
    }

    function createWebSocket(message) {
        var webSocket = new WebSocket(websocketUrl);

        webSocket.onmessage = function (event) {
            onMessage(event);
        };

        webSocket.onclose = function () {
            _webSocket = null;
        };

        if (message) {
            webSocket.onopen = function () {
                webSocket.send(message);
            };
        }

        return webSocket;
    }

    return {
        sendWhenOpen(message) {
            if (_webSocket == null) {
                _webSocket = createWebSocket(message);
            } else {
                _webSocket.send(message);
            }
        }
    }
};