"use strict";

var Planetoid = Planetoid || {};

Planetoid.GameOrchestrator = function (view, gameDetails) {

    function updateView() {
        if (gameDetails.isGameInProggress()) {
            view.draw();
            requestAnimationFrame(updateView);
        }
    }

    return {
        startGameLoop: function () {
            requestAnimationFrame(updateView);
        }
    }
};