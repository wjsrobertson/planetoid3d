"use strict";

var Planetoid = Planetoid || {};

Planetoid.AssetFactory = function (webroot) {

    return {
        loadAudio: function (path) {
            return new Audio(webroot + '/' + path);
        },
        loadImage: function (path) {
            return new Image(webroot + '/' + path);
        }
    }
};