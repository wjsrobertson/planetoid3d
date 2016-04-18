//"use strict";

var Planetoid = Planetoid || {};

Planetoid.AssetFactory = function (webroot) {

    var _webroot = webroot;

    return {
        loadAudio: function (path) {
            return new Audio(_webroot + '/' + path);
        },
        loadImage: function (path) {
            return new Image(_webroot + '/' + path);
        }
    }
};