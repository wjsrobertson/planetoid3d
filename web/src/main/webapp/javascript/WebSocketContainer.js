"use strict";

var Planetoid = Planetoid || {};

Planetoid.WebSocketContainer = function (messageRouter, websocketUrl) {

    var heartbeatTimeMs = 10000;

    var _webSocket = null;

    var _timerId = null;

    function onMessage(event) {
        var message = JSON.parse(event.data);
        messageRouter.routeMessage(message);
    }

    function onClose() {
        _webSocket = null;

        if (_timerId != null) {
            clearTimeout(_timerId);
        }
    }

    function onOpen(webSocket, message) {
        webSocket.send(message);

        setInterval(function () {
            webSocket.send("heartbeat");
        }, heartbeatTimeMs);
    }

    function createWebSocket(message) {
        var webSocket = new WebSocket(websocketUrl);

        webSocket.onmessage = function (event) {
            onMessage(event);
        };

        webSocket.onclose = function () {
            onClose();
        };

        if (message) {
            webSocket.onopen = function () {
                onOpen(webSocket, message);
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