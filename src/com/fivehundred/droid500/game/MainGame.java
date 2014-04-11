package com.fivehundred.droid500.game;

import android.util.SparseArray;
import com.fivehundred.droid500.game.controllers.GameController;
import com.fivehundred.droid500.utils.GameConstants;
import com.fivehundred.droid500.utils.GameUtils;
import com.fivehundred.droid500.utils.Logger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;

public class MainGame {

    private List<Player> players = new ArrayList<>();
    private List<Card> deck = new ArrayList<>();
    private List<Card> kitty = new ArrayList<>();
    private int dealerSeat = 0;
    private int currentPlayerIndex = 0;
    private String trumpSuit = "";
    private Hand currentHand;
    private List<Hand> hands = new ArrayList<>();
    private int handScore[] = new int[2];
    private int gameScore[] = new int[2];
    private int bidTeam = 0;
    private SparseArray<String> winningBid;
    
    @Inject GameController gameController;
    
    public MainGame(int playerCount){
        buildPlayers(playerCount);
    }
    
    private void buildPlayers(int playerCount){
        for(int playerIndex=0; playerIndex<playerCount; playerIndex++){
            players.add(new Player(playerIndex));
        }
    }
    
    public void createDeck(){
        clearCards();
        buildDeck();
        shuffleDeck();
    }
    
    private void clearCards(){
        deck.clear();
        kitty.clear();
        for(Player player : players){
            player.clearCards();
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
        // Add Joker
        Card card = new Card(GameConstants.JOKER, GameConstants.JOKER_POWER);
        deck.add(card);
        String message = card.toString() + " added to deck";
        Logger.log(message);
    }
    
    public void shuffleDeck(){
        Collections.shuffle(deck);
        Logger.log("Shuffled Deck:");
        Logger.log(deck);
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
    
    public int bid(){  
        int playerCount = players.size();
        int winner = 0;
        int highestBid = 0;
        String winningSuit = "";
        SparseArray<SparseArray<String>> allBids = new SparseArray<SparseArray<String>>();
        for(int i=0; i<playerCount; i++){
            int playerIndex = (dealerSeat + 1 + i) % (playerCount); // Bids always start with player to left of dealer
            Player nextBidder = players.get(playerIndex);
            SparseArray<String> bid = nextBidder.bid();
            allBids.put(playerIndex, bid);
            int bidPower = bid.keyAt(0);
            String bidSuit = bid.valueAt(0);
            Logger.lineBreak();
            if(!(highestBid > bidPower)){
                if(highestBid == 0){
                    if(bidPower < 6){
                        highestBid = 6;
                    }else{
                        highestBid = bidPower;
                    }
                    winningSuit = bidSuit;
                    winner = playerIndex;
                    Logger.log("Player " + playerIndex + " starts the bids with " + highestBid + " " + bidSuit);
                }else if(highestBid < bidPower){
                    highestBid = bidPower;
                    winningSuit = bidSuit;
                    winner = playerIndex;
                    Logger.log("Player " + playerIndex + " raises bid " + bidPower + " " + bidSuit);
                }else if(bid.valueAt(0).equals(GameUtils.highestSuit(bidSuit, winningSuit))){
                    winningSuit = bidSuit;
                    winner = playerIndex;
                    Logger.log("Player " + playerIndex + " raises bid " + bidPower + " " + bidSuit);
                }else{
                    Logger.log("Player " + playerIndex + " passes.");
                }
            }else{
                Logger.log("Player " + playerIndex + " passes.");
            }
            Logger.lineBreak();
            Logger.log("Player " + playerIndex + " bids " + bidPower + " of " + bidSuit);
        }
        /*for(int i=0; i < allBids.size(); i++){
            int bid = allBids.valueAt(i).keyAt(0);
            String suit = allBids.valueAt(i).valueAt(0); 
            if(bid > highestBid){
                highestBid = bid;
                winner = i;
                winningSuit = suit;
            }else if(bid == highestBid
                    && !suit.equals(winningSuit)){
                if(suit.equals(GameUtils.highestSuit(suit, winningSuit))){
                    winner = i;
                    winningSuit = suit;
                }
            }
        }*/
        Logger.log("Player " + winner + " wins the bid with " + highestBid + " of " + winningSuit);
        trumpSuit = winningSuit;
        bidTeam = winner % 2;
        winningBid = allBids.get(winner);
        return winner;
    }
    
    public void openKitty(int winnerIndex){
        Player winner = players.get(winnerIndex);
        winner.getCards().addAll(kitty);
        winner.reduceHand();
    }
    
    public void updateTrumpCards(){
        Logger.log("Updating trump cards:");
        String cardLabel = "";
        for(Card card : deck){
            cardLabel = card.toString();
            if(trumpSuit.equals(card.getSuit())
                    && !card.isJack()
                    && !card.isJoker()){
                
                if(card.getPower() > 10){
                    // Face cards (except Jacks) increased by 10
                    card.setPower(card.getPower()+10);                     
                }else{
                    // Value cards increased by 11
                    card.setPower(card.getPower()+11);                   
                }
                Logger.log(cardLabel + " now power " + card.getPower());  
            }
            if(card.isJack()){
                if(trumpSuit.equals(card.getSuit())){
                    card.setPower(GameConstants.TRUMP_JACK_POWER);
                }else if(trumpSuit.equals(card.getComplimentarySuit())){
                    card.setPower(GameConstants.COMPLIMENTARY_JACK_POWER);
                    card.setSuit(trumpSuit);
                    Logger.log(card.toString() + " now suit " + card.getSuit()); 
                }else{
                    card.setPower(GameConstants.JACK_POWER);
                }
                Logger.log(cardLabel + " now power " + card.getPower()); 
            }
            if(card.isJoker()){
                card.setSuit(trumpSuit);
                Logger.log(card.toString() + " now suit " + card.getSuit()); 
            }
        }
    }
    
    public void startHand(int winnerIndex){
        currentPlayerIndex = winnerIndex;
        reset();
        newHand();
    }
    
    private void reset(){
        hands.clear();
        handScore[0] = 0;
        handScore[1] = 0;
    }
    
    private void newHand(){
        currentHand = new Hand(trumpSuit);
        hands.add(currentHand);
        progressGame();
    }
    
    public void progressGame(){
        if(!players.get(currentPlayerIndex).getCards().isEmpty()){
            if(!currentHand.isLocked()){
                currentPlayerIndex = players.get(currentPlayerIndex).play(currentHand);
                progressGame();
            }else{
                scoreHand();
                if(hands.size() < 10){
                    newHand(); 
                }else{
                    updateGame();                      
                }
            }
        }else{
            Logger.log("Player " + currentPlayerIndex + " is out of cards");
            updateGame();
        }        
    }
    
    private void scoreHand(){
        handScore[currentHand.getWinnerIndex() % 2]++;
    }
    
    private void updateGame(){
        Logger.lineBreak();
        Logger.log("Series complete");
        Logger.lineBreak(2);
        int bidValue = GameUtils.getBidValue(winningBid);
        if(handScore[bidTeam] < winningBid.keyAt(0)){
            gameScore[bidTeam] -= bidValue;
            Logger.log("Team " + bidTeam + " failed to achieve their bid");
            Logger.log(bidValue + " deducted from their score.");
        }else{
            gameScore[bidTeam] += bidValue;
            Logger.log("Team " + bidTeam + " successfully achieved their bid");
            Logger.log(bidValue + " added to their score.");
        }
        int defendingTeam = (bidTeam + 1) % 2;
        gameScore[defendingTeam] += (handScore[defendingTeam] * 10);
        Logger.lineBreak();
        Logger.log("Team 0: " + gameScore[0]);
        Logger.log("Team 1: " + gameScore[1]);
        Logger.lineBreak();
        boolean gameOver = false;
        for(int i=0; i<gameScore.length; i++){
            if(gameScore[i] > 500 || gameScore[i] < -500){
                gameOver = true;
                break;
            }
        }
        if(gameOver){
            Logger.lineBreak();
            Logger.log("GAME OVER");
            Logger.lineBreak();
            if(gameScore[0] > gameScore[1]){
                endGame(0);
            }else if(gameScore[1] > gameScore[0]){
                endGame(1);
            }
        }else{
            gameController.startNewHand(this);
        }
    }
    
    private void endGame(int winningTeam){
        Logger.log("Team " + winningTeam + " wins with a score of " + gameScore[winningTeam]);
        Logger.lineBreak(2);
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
