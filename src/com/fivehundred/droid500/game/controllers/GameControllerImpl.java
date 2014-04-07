package com.fivehundred.droid500.game.controllers;

import com.fivehundred.droid500.game.MainGame;

public class GameControllerImpl implements GameController {

    @Override
    public MainGame createNewGame() {
        MainGame newGame = new MainGame();

        return newGame;
    }
    
    @Override
    public void startGame(MainGame game){
        game.shuffleDeck();
        game.dealDeck();
    }
}
