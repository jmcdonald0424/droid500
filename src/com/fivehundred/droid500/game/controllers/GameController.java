package com.fivehundred.droid500.game.controllers;

import com.fivehundred.droid500.game.MainGame;

public interface GameController {

    public MainGame createNewGame(int playerCount);
    public void startGame(MainGame game);
    public void startNewHand(MainGame game);
}
