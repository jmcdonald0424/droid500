package com.fivehundred.droid500.game;

import com.fivehundred.droid500.utils.ConversionUtils;
import com.fivehundred.droid500.utils.GameConstants;
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
        if(!suit.equals(GameConstants.JOKER)){
            sb.append(" of ");
            sb.append(ConversionUtils.getSuitLabel(suit));
        }
        return sb.toString();
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