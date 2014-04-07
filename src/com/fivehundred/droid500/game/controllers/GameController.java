package com.fivehundred.droid500.game.controllers;

import com.fivehundred.droid500.game.MainGame;

public interface GameController {

    public MainGame createNewGame();
    public void startGame(MainGame game);
}
