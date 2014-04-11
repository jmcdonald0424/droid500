package com.fivehundred.droid500.game.controllers;

import com.fivehundred.droid500.game.Card;
import com.fivehundred.droid500.game.MainGame;
import com.fivehundred.droid500.utils.Logger;
import java.util.List;

public class GameControllerImpl implements GameController {

    @Override
    public MainGame createNewGame(int playerCount) {
        MainGame newGame = new MainGame(playerCount);

        return newGame;
    }
    
    @Override
    public void startGame(MainGame game){
        startNewHand(game);
    }
    
    @Override
    public void startNewHand(MainGame game){
        game.createDeck();
        Logger.log("DEALING DECK ::::: Deck size: " + game.getDeck().size());
        game.dealDeck();
        Logger.log("STARTING BIDS ::::: Deck size: " + game.getDeck().size());
        int winner = game.bid();
        Logger.log("UPDATING TRUMP CARDS ::::: Deck size: " + game.getDeck().size());
        game.updateTrumpCards();
        Logger.log("OPENING KITTY ::::: Deck size: " + game.getDeck().size());
        game.openKitty(winner);
        Logger.log("STARTING HAND ::::: Deck size: " + game.getDeck().size());
        game.startHand(winner);        
    }
}
