"use strict";

var Planetoid = Planetoid || {};

Planetoid.Initialiser = {};

Planetoid.Initialiser.bindAndListenForGameStart = function (canvasId, nameInputId, startGameButtonId) {

    var assetFactory = Planetoid.AssetFactory(Planetoid.Config.webRoot);
    var soundPlayer = Planetoid.SoundPlayer(Planetoid.Config.sounds, assetFactory);
    var gameDetails = Planetoid.GameDetails();

    var messageProcessors = {
        'GameStartNotification': Planetoid.GameStartNotificationMessageProcessor(gameDetails),
        'GameModelUpdateResult': Planetoid.GameUpdateMessageProcessor(gameDetails, soundPlayer)
    };
    var messageRouter = Planetoid.MessageRouter(messageProcessors);

    var webSocketContainer = Planetoid.WebSocketContainer(messageRouter, Planetoid.Config.websocketUrl);
    webSocketContainer.getWebSocket(); // create one straight away for now - TODO remove and add game states

    var messageSender = Planetoid.MessageSender(webSocketContainer);
    var playerInputMessageSender = Planetoid.PlayerInputMessageSender(gameDetails, messageSender);

    var canvas = document.getElementById(canvasId);
    var gameDiv =  window; //document.getElementByName("game");
    var userInputHandler = Planetoid.UserInputHandler(window, gameDetails, playerInputMessageSender);
    var view = Planetoid.CanvasView(canvas, gameDetails, Planetoid.Config.images);

    var gameOrchestrator = Planetoid.GameOrchestrator(view, gameDetails);

    var startRequestMessageSender = Planetoid.GameStartRequestMessageSender(messageSender, gameDetails);

    var nameInput = document.getElementById(nameInputId);
    var startGameButton = document.getElementById(startGameButtonId);
    var gameStarter = Planetoid.GameStarter(nameInput, startGameButton, gameDetails, startRequestMessageSender);

    userInputHandler.bindAndListenForGameStart();
    gameStarter.bindAndListenForGameStart();
    gameOrchestrator.startGameLoop();

};