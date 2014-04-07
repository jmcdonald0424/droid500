package com.fivehundred.droid500.game;

import com.fivehundred.droid500.utils.GameConstants;
import com.fivehundred.droid500.utils.Logger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainGame {

    private List<Player> players = new ArrayList<>();
    private List<Card> deck = new ArrayList<>();
    private List<Card> kitty = new ArrayList<>();
    private int dealerSeat = 0;
    
    public MainGame(){
        for(int i=0; i<4; i++){
            players.add(new Player());
        }
    }
    
    public void shuffleDeck(){
        buildDeck();
        Collections.shuffle(deck);
        Logger.log("Shuffled Deck:");
        for(Card card : deck){
            String message = deck.indexOf(card) + ": " + card.toString();
            Logger.log(message);
        }
    }
    
    private void buildDeck(){
        for(int suit=0; suit<4; suit++){            
            // Power: 4(4) to Ace (14)
            for(int power=4; power<15; power++){
                Card card = new Card(GameConstants.SUITS[suit], power);
                deck.add(card);
                String message = card.toString() + " added to deck";
                Logger.log(message);
            }
        }
        // Add Joker(15)
        Card card = new Card(GameConstants.JOKER, 15);
        deck.add(card);
        String message = card.toString() + " added to deck";
        Logger.log(message);
    }
    
    public void dealDeck(){
        Logger.log("Deal Cards:");
        int count = 1;
        for(Card card : deck){
            if(deck.indexOf(card) % 9 == 0){
                kitty.add(card);
                String message = card.toString() + " dealt to kitty";
                Logger.log(message);
                continue;
            }
            int playerIndex = count % 4;
            players.get(playerIndex).getCards().add(card);
            String message = card.toString() + " dealt to player " + playerIndex;
            Logger.log(message);
            count++;
        }
        Logger.log("Cards have been dealt:");
        for(Player player : players){
            StringBuilder message = new StringBuilder();
            message.append("Player ");
            message.append(players.indexOf(player));
            message.append(": ");
            message.append(player.getCards().size());
            Logger.log(message.toString());
        }
        Logger.log("Kitty: " + kitty.size());
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Card> getDeck() {
        return deck;
    }

    public void setDeck(List<Card> deck) {
        this.deck = deck;
    }

    public List<Card> getKitty() {
        return kitty;
    }

    public void setKitty(List<Card> kitty) {
        this.kitty = kitty;
    }

    public int getDealerSeat() {
        return dealerSeat;
    }

    public void setDealerSeat(int dealerSeat) {
        this.dealerSeat = dealerSeat;
    }
}
