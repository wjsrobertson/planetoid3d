"use strict";

var Planetoid = Planetoid || {};

Planetoid.CanvasView = function(canvas, gameDetails) {

    return {
        draw: function() {
            canvas.innerHTML = JSON.stringify(gameDetails.getGameModel());
        }
    }

};