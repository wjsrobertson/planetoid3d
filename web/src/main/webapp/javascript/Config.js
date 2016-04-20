"use strict";

var Planetoid = Planetoid || {};

Planetoid.Config = {

    webRoot: 'http:/localhost:8080/planetoid',

    websocketUrl: 'ws:/localhost:8080/planetoid/websocket',

    sounds: [{
        name: 'PlayerLoseLife',
        path: 'assets/sound/game_over.ogg',
        numConcurrent: 1
    }, {
        name: 'MissileFired',
        path: 'assets/sound/shoot.ogg',
        numConcurrent: 3
    }]

};