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
        player2Missile: loadImage(imageConfig.player2Missile)
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

    return {
        draw: function () {
            var gameModel = gameDetails.getGameModel();
            if (gameModel) {
                render(gameModel.planet, _entities.planet);
                render(gameModel.players[0].rocket, _entities.player1);
                render(gameModel.players[1].rocket, _entities.player2);
            }
        }
    }
};