"use strict";

var Planetoid = Planetoid || {};

Planetoid.ElementRetriever = {

    getElementsByIds: function (elementMap) {
        var elements = {};
        for (var elementKey in elementMap) {
            if (elementMap.hasOwnProperty(elementKey)) {
                var elementId = elementMap[elementKey];

                elements[elementKey] = document.getElementById(elementId);
            }
        }

        return elements;
    }
};