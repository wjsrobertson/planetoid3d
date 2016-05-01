"use strict";

var Planetoid = Planetoid || {};

Planetoid.Config = {

    webRoot: 'http://localhost:8080/planetoid',

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
            scale: 1,
            width: 112,
            height: 75
        },
        player2Missile: {
            path: 'assets/images/laserRed08.png',
            scale: 1,
            width: 112,
            height: 75
        },
        planet: {
            path: 'assets/images/worldgen.gif',
            scale: 1,
            width: 200,
            height: 200
        },
        shield1: {
            path: 'assets/images/shield1.png',
            scale: 1,
            width: 133,
            height: 108
        },
        shield2: {
            path: 'assets/images/shield2.png',
            scale: 1,
            width: 143,
            height: 119
        },
        shield3: {
            path: 'assets/images/shield3.png',
            scale: 1,
            width: 144,
            height: 137
        },
        explosion1: {
            path: 'assets/images/explosion1.png',
            scale: 1,
            width: 128,
            height: 128
        },
        explosion2: {
            path: 'assets/images/explosion2.png',
            scale: 1,
            width: 128,
            height: 128
        },
        explosion3: {
            path: 'assets/images/explosion3.png',
            scale: 1,
            width: 128,
            height: 128
        },
        explosion4: {
            path: 'assets/images/explosion4.png',
            scale: 1,
            width: 128,
            height: 128
        },
        explosion5: {
            path: 'assets/images/explosion5.png',
            scale: 1,
            width: 128,
            height: 128
        },
        explosion6: {
            path: 'assets/images/explosion6.png',
            scale: 1,
            width: 128,
            height: 128
        },
        explosion7: {
            path: 'assets/images/explosion7.png',
            scale: 1,
            width: 128,
            height: 128
        },
        explosion8: {
            path: 'assets/images/explosion8.png',
            scale: 1,
            width: 128,
            height: 128
        },
        explosion9: {
            path: 'assets/images/explosion9.png',
            scale: 1,
            width: 128,
            height: 128
        },
        explosion10: {
            path: 'assets/images/explosion10.png',
            scale: 1,
            width: 128,
            height: 128
        },
        explosion11: {
            path: 'assets/images/explosion11.png',
            scale: 1,
            width: 128,
            height: 128
        },
        explosion12: {
            path: 'assets/images/explosion12.png',
            scale: 1,
            width: 128,
            height: 128
        },
        explosion13: {
            path: 'assets/images/explosion13.png',
            scale: 1,
            width: 128,
            height: 128
        },
        explosion14: {
            path: 'assets/images/explosion14.png',
            scale: 1,
            width: 128,
            height: 128
        },
        explosion15: {
            path: 'assets/images/explosion15.png',
            scale: 1,
            width: 128,
            height: 128
        },
        explosion16: {
            path: 'assets/images/explosion16.png',
            scale: 1,
            width: 128,
            height: 128
        }
    }
};