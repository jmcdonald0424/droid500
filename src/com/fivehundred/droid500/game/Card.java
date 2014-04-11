package com.fivehundred.droid500.game;

import com.fivehundred.droid500.utils.ConversionUtils;
import com.fivehundred.droid500.utils.GameConstants;
import com.fivehundred.droid500.utils.Logger;
import com.fivehundred.droid500.view.Sprite;

public class Card{
	
    private Integer power;
    private String suit;
    private Sprite sprite;
    
    public Card(String suit, Integer power){
        this.suit = suit;
        this.power = power;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(ConversionUtils.getPowerLabel(power));
        if(!power.equals(GameConstants.JOKER_POWER)){
            sb.append(" of ");
            /*if(power.equals(GameConstants.COMPLIMENTARY_JACK_POWER)){
                sb.append(ConversionUtils.getSuitLabel(getComplimentarySuit()));
            }else{                
                sb.append(ConversionUtils.getSuitLabel(suit));
            }*/
            sb.append(ConversionUtils.getSuitLabel(suit));
        }
        return sb.toString();
    }
    
    public String getComplimentarySuit(){
        switch(suit){
            case GameConstants.SPADES:
                return GameConstants.CLUBS;
            case GameConstants.CLUBS:
                return GameConstants.SPADES;
            case GameConstants.DIAMONDS:
                return GameConstants.HEARTS;
            case GameConstants.HEARTS:
                return GameConstants.DIAMONDS;
            case GameConstants.JOKER:
                return "NONE";
            default:
                Logger.logError("Complimentary suit not found for " + suit);
                return null;
        }
    }
    
    public boolean isTrump(){
        return power > GameConstants.ACE_POWER+1;
    }
    
    public boolean isJack(){
        switch(power){
            case GameConstants.JACK_POWER:
            case GameConstants.COMPLIMENTARY_JACK_POWER:
            case GameConstants.TRUMP_JACK_POWER:
                return true;
            default:
                return false;
        }
    }
    
    public boolean isJoker(){
        return power.equals(GameConstants.JOKER_POWER);
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}