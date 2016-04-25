"use strict";

var Planetoid = Planetoid || {};

Planetoid.CanvasView = function (canvas, gameDetails, imageConfig) {

    var _gameWidth = 3840;
    var _gameHeight = 2160;
    var _canvasWidth = 960;
    var _canvasHeight = 540;
    var _xRatio;
    var _yRatio;
    var _entities = {
        player1: loadImage(imageConfig.player1),
        player2: loadImage(imageConfig.player2),
        planet: loadImage(imageConfig.planet),
        player1Missile: loadImage(imageConfig.player1Missile),
        player2Missile: loadImage(imageConfig.player2Missile),
        highlight1: loadImage(imageConfig.shield1),
        highlight2: loadImage(imageConfig.shield2),
        highlight3: loadImage(imageConfig.shield3)
    };

    updateRatios();

    function loadImage(imageConfig) {
        var svgNamespaceURI = 'http://www.w3.org/2000/svg';
        var xlinkNamespaceURI = 'http://www.w3.org/1999/xlink';

        var image = document.createElementNS(svgNamespaceURI, 'image');
        image.setAttributeNS(xlinkNamespaceURI, 'href', imageConfig.path);
        image.setAttribute('x', '50');
        image.setAttribute('y', '50');
        image.setAttribute('visibility', 'visible');

        canvas.appendChild(image);

        return {
            imageElement: image,
            config: imageConfig,
            rotation: 0
        };
    }

    function updateRatios() {
        _xRatio = _canvasWidth / _gameWidth;
        _yRatio = _canvasHeight / _gameHeight;
    }

    function renderSurround(model, entities, remainingTime) {
        var totalTime = 3000;
        var ratio = remainingTime / totalTime;
        var angle = (360 * ratio * 3) % 360;

        var numFrames = 3;
        var framesInTotalTime = 360;
        var index =  Math.floor((ratio * framesInTotalTime)) % numFrames;

        var scale = ratio * 1.5

        var entity = entities[index];

        var modelHeight = model.radius * 2;

        var height = scale * modelHeight * _yRatio;
        var width = height * (entity.config.height / entity.config.width);

        var midX = _xRatio * model.position.x;
        var midY = _xRatio * model.position.y;
        var x = midX - (width / 2);
        var y = midY - (height / 2);

        entity.imageElement.setAttribute('width', width);
        entity.imageElement.setAttribute('height', height);
        entity.imageElement.setAttribute('visibility', 'visibile');
        entity.imageElement.setAttribute('x', x);
        entity.imageElement.setAttribute('y', y);

        var translate = 'rotate(' + angle + ' ' + midX + ' ' + midY + ')';

        entity.imageElement.setAttribute('transform', translate);
    }

    function render(model, entity) {
        var modelHeight = model.radius * 2;

        var height = modelHeight * _yRatio;
        var width = height * (entity.config.height / entity.config.width);

        var midX = _xRatio * model.position.x;
        var midY = _xRatio * model.position.y;
        var x = midX - (width / 2);
        var y = midY - (height / 2);

        entity.imageElement.setAttribute('width', width);
        entity.imageElement.setAttribute('height', height);
        entity.imageElement.setAttribute('visibility', 'visibile');
        entity.imageElement.setAttribute('x', x);
        entity.imageElement.setAttribute('y', y);

        if (model.rotation) {
            var radians = Math.atan2(model.rotation.y, model.rotation.x) - (3 * Math.PI / 2);
            var degrees = Math.round(radians * 180 / Math.PI);
            var translate = 'rotate(' + degrees + ' ' + midX + ' ' + midY + ')';

            entity.imageElement.setAttribute('transform', translate);
        }
    }

    function hideEntity(entity) {
        entity.imageElement.setAttribute('visibility', 'hidden');
    }

    return {
        draw: function () {
            var gameModel = gameDetails.getGameModel();
            if (gameModel) {
                render(gameModel.planet, _entities.planet);

                if (gameModel.roundTimer.remainingTimeMs > 300) {
                    var rocket = gameDetails.isPlayer1() ? gameModel.players.p1.rocket : gameModel.players.p2.rocket;
                    renderSurround(rocket, [_entities.highlight1, _entities.highlight2, _entities.highlight3], gameModel.roundTimer.remainingTimeMs);
                } else {
                    hideEntity(_entities.highlight1);
                    hideEntity(_entities.highlight2);
                    hideEntity(_entities.highlight3);
                }

                render(gameModel.players.p1.rocket, _entities.player1);
                if (gameModel.players.p1.missiles.length > 0) {
                    render(gameModel.players.p1.missiles[0], _entities.player1Missile);
                } else {
                    hideEntity(_entities.player1Missile);
                }

                render(gameModel.players.p2.rocket, _entities.player2);
                if (gameModel.players.p2.missiles.length > 0) {
                    render(gameModel.players.p2.missiles[0], _entities.player2Missile);
                } else {
                    hideEntity(_entities.player2Missile);
                }

                document.getElementById("player1Points").textContent = gameModel.players.p1.points;
                document.getElementById("player2Points").textContent = gameModel.players.p2.points;

                document.getElementById("player1Lives").textContent = gameModel.players.p1.numLives;
                document.getElementById("player2Lives").textContent = gameModel.players.p2.numLives;

                document.getElementById("player1Name").textContent = gameDetails.getUserName();
                document.getElementById("player2Name").textContent = gameDetails.getOpponentName();

                document.getElementById("round").textContent = gameModel.roundTimer.round;
                document.getElementById("countdown").textContent = gameModel.roundTimer.remainingTimeMs;
                document.getElementById("winner").textContent = gameModel.winner;
            }
        }
    }
};