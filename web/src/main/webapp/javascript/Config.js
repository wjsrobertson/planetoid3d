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
    }],

    images: {
        player1: {
            path: 'assets/images/playerShip2_blue.png',
            scale: 1,
            width: 112,
            height: 75
        },
        player2: {
            path: 'assets/images/playerShip2_orange.png',
            scale: 1,
            width: 112,
            height: 75
        },
        player1Missile: {
            path: 'assets/images/laserBlue08.png',
            scale: 0.25,
            width: 112,
            height: 75
        },
        player2Missile: {
            path: 'assets/images/laserRed08.png',
            scale: 0.25,
            width: 112,
            height: 75
        },
        planet: {
            path: 'assets/images/worldgen.gif',
            scale: 3,
            width: 200,
            height: 200
        }
    }
};