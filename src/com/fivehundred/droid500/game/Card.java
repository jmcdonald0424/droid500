package com.fivehundred.droid500.game;

import com.fivehundred.droid500.view.Sprite;

public class Card{
	
    private Integer power;
    private String suit;
    private Sprite sprite;
    
    public Card(){
        
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