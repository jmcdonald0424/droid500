package com.fivehundred.droid500.utils;

import android.util.Log;
import com.fivehundred.droid500.BuildConfig;

public class ConversionUtils{
    
    public static String getSuitLabel(String suit){
        switch(suit){
            case GameConstants.SPADES:
                return GameConstants.SPADES_LABEL;
            case GameConstants.CLUBS:
                return GameConstants.CLUBS_LABEL;
            case GameConstants.DIAMONDS:
                return GameConstants.DIAMONDS_LABEL;
            case GameConstants.HEARTS:
                return GameConstants.HEARTS_LABEL;
            case GameConstants.JOKER:
                return GameConstants.JOKER_LABEL;
            default:                
                if(BuildConfig.DEBUG){
                    String message = suit + " not found";
                    Log.e(ApplicationConstants.TAG, message);
                }
                return null;
        }
    }
    
    public static String getPowerLabel(Integer power){
        switch(power){
            case 11:
                return GameConstants.JACK_LABEL;
            case 12:
                return GameConstants.QUEEN_LABEL;
            case 13:
                return GameConstants.KING_LABEL;
            case 14:
                return GameConstants.ACE_LABEL;
            case 15:
                return GameConstants.JOKER_LABEL;
            default:
                return power.toString();
        }
    }
}