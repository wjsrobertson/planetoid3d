"use strict";

var Planetoid = Planetoid || {};

Planetoid.Initialiser = {};

Planetoid.Initialiser.bindAndListen = function (settings) {

    var gameDetails = Planetoid.GameDetails();
    var canvas = document.getElementById(settings.viewPaneElements.canvas);
    var gameView = Planetoid.CanvasView(canvas, gameDetails, Planetoid.Config.images);

    var displayControlElements = Planetoid.ElementRetriever.getMapOfElementsByIds(settings.viewPaneElements);
    var displayControl = new Planetoid.DisplayControl(displayControlElements, gameDetails);

    var input = document.getElementById(settings.userInputElements.nameInput);
    Planetoid.TextInputDescription.addDescription(input, 'enter your name');

    var assetFactory = Planetoid.AssetFactory(Planetoid.Config.webRoot);
    var soundPlayer = Planetoid.SoundPlayer(Planetoid.Config.sounds, assetFactory);

    var gameOrchestrator = Planetoid.GameOrchestrator(gameView, gameDetails);

    var messageProcessors = {
        'GameStartNotification': Planetoid.GameStartNotificationMessageProcessor(gameDetails, displayControl, gameOrchestrator),
        'GameModelUpdateResult': Planetoid.GameUpdateMessageProcessor(gameDetails, soundPlayer, displayControl),
        'GameAbortNotification': Planetoid.GameAbortMessageProcessor(gameDetails, displayControl)
    };
    var messageRouter = Planetoid.MessageRouter(messageProcessors);

    var webSocketContainer = Planetoid.WebSocketContainer(messageRouter, Planetoid.Config.websocketUrl);

    var messageSender = Planetoid.MessageSender(webSocketContainer);
    var playerInputMessageSender = Planetoid.PlayerInputMessageSender(gameDetails, messageSender);

    var userInputHandler = Planetoid.UserInputHandler(window, gameDetails, playerInputMessageSender);

    var startRequestMessageSender = Planetoid.GameStartRequestMessageSender(messageSender, gameDetails);

    var nameInput = document.getElementById(settings.userInputElements.nameInput);

    var startGameButtons = Planetoid.ElementRetriever.getArrayOfElementsByIds(settings.userInputElements.startGameButtons);
    var gameStarter = Planetoid.GameStarter(nameInput, startGameButtons, gameDetails, startRequestMessageSender, displayControl);

    userInputHandler.bindAndListen();
    gameStarter.bindAndListenForGameStart();

};