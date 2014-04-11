package com.fivehundred.droid500.utils;

import com.fivehundred.droid500.game.Card;
import java.util.Comparator;

public class CardComparator implements Comparator<Card>{
    
    private final int order;
    
    public CardComparator(int order){
        this.order = order;
    }
    
    @Override
    public int compare(Card card1, Card card2){
        int comparison = compare(card1.getPower(), card2.getPower());
        return comparison == 0 ? compare(card1.getSuit(), card2.getSuit()) : comparison;
    }
    
    private int compare(Integer a, Integer b){
        int power1 = order == 1 ? a : b;
        int power2 = order == 1 ? b : a;
        return power1 < power2 ? 1
             : power1 > power2 ? -1
             : 0;
    }
    
    private int compare(String a, String b){
        int power1 = order == 1 ? GameUtils.valueSuit(a) : GameUtils.valueSuit(b);
        int power2 = order == 1 ? GameUtils.valueSuit(b) : GameUtils.valueSuit(a);
        return power1 < power2 ? 1
             : power1 > power2 ? -1
             : 0;
    }
}