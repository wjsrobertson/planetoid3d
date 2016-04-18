//"use strict";

var Planetoid = Planetoid || {};

Planetoid.SoundPlayer = function(sounds, assetFactory) {

    var _sounds = sounds;
    var _assetFactory = assetFactory;
    var _audio = {};
    var _isSoundEnabled = false;
    var _counterForSound = {};

    init();

    function init() {
        sounds.forEach(function(sound){
            for (var i = 0; i < sound.numConcurrent; i++) {
                _audio[sound.name] = _audio[sound.name] || [];
                _audio[sound.name][i] = _assetFactory.loadAudio(sound.path);
            }
            _counterForSound[sound.name] = 0;
        });
    }

    return {
        playSound: function(soundName) {
            if (_isSoundEnabled && _audio[soundName]) {
                var counter = _counterForSound[soundName];
                _audio[soundName][counter].play();
                _counterForSound[soundName] = (counter + 1) % _sounds[soundName].numConcurrent;
            }
        },
        setSoundEnabled: function(isSoundEnabled) {
            _isSoundEnabled = isSoundEnabled;
        }
    };
};