"use strict";

var Planetoid = Planetoid || {};

Planetoid.ElementRetriever = {

    getMapOfElementsByIds: function (elementMap) {
        var elements = {};
        for (var elementKey in elementMap) {
            if (elementMap.hasOwnProperty(elementKey)) {
                var elementId = elementMap[elementKey];

                elements[elementKey] = document.getElementById(elementId);
            }
        }

        return elements;
    },

    getArrayOfElementsByIds: function (elementIdArray) {
        var elements = [];
        for (var i = 0; i < elementIdArray.length; i++) {
            var elementId = elementIdArray[i];
            elements.push(document.getElementById(elementId));
        }

        return elements;
    }
};