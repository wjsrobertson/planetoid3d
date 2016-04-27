"use strict";

var Planetoid = Planetoid || {};

Planetoid.TextInputDescription = {
    addDescription: function(input, description) {
        if (input.value == '') {
            input.value = description
        }

        input.addEventListener('focus', function(){
            if (this.value == description) {
                this.value = '';
            }
        });

        input.addEventListener('blur', function(){
            if ( this.value=='') {
                this.value = description
            }
        });
    }
};