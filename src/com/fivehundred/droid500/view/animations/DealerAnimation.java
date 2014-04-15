package com.fivehundred.droid500.view.animations;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.graphics.PointF;
import com.fivehundred.droid500.game.MainGame;
import com.fivehundred.droid500.view.Sprite;
import com.fivehundred.droid500.view.controllers.AnimationController;
import com.fivehundred.droid500.view.utils.ViewUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.inject.Inject;

public class DealerAnimation{
    
    private final MainGame game;
    private final float ssu;
    private int cardCount;
    private final int dropPointArea = 40;
    private float xCoord;
    private float yCoord;
    private int cardIndex;
    private int playerIndex;
    private int playerCardCount = 0;
    private int kittyCardCount = 0;
    private Sprite currentSprite;
    private final PointF dealerLocation;
    
    @Inject AnimationController animation;
    
    public DealerAnimation(MainGame game, float ssu){
        this.game = game;
        this.ssu = ssu;
        cardCount = 0;
        dealerLocation = ViewUtils.getDealerLocation(game.getDealerIndex());
    }
    
    public void dealCards(){
        if(cardCount > 44){
            return;
        }
        if(cardCount % 9 == 0){
            playerIndex = 4;
            cardIndex = kittyCardCount++;
            currentSprite = game.getKitty().get(cardIndex).getSprite();
        }else{
            playerIndex = (playerCardCount + game.getDealerIndex() + 1) % 4;   
            cardIndex = (int)Math.floor(playerCardCount++/4);    
            currentSprite = game.getPlayers().get(playerIndex).getCards().get(cardIndex).getSprite();
        }
        xCoord = ViewUtils.getDropPoint(playerIndex).x + dropPointArea / 2;
        yCoord = ViewUtils.getDropPoint(playerIndex).y + dropPointArea / 2;

        currentSprite.setTranslation(dealerLocation.x * ssu, dealerLocation.y * ssu); 
        currentSprite.setScalar(ssu);

        PointF endTranslation = new PointF((xCoord - new Random().nextInt(dropPointArea)) * ssu, (yCoord - new Random().nextInt(dropPointArea)) * ssu);            
        float angle = 990 - (new Random()).nextInt(180); 

        List<Animator> animations = new ArrayList<>();
        animations.add(animation.translate(currentSprite, endTranslation));
        animations.add(animation.rotate(currentSprite, angle));
        animations.add(animation.scale(currentSprite, 1.35f, 1.00f));
        AnimatorSet set = new AnimatorSet();
        set.setDuration(500);
        set.playTogether(animations);
        cardCount++;
        set.addListener(new AnimatorListenerAdapter(){
            @Override
            public void onAnimationStart(Animator animation){
                
            }
            @Override
            public void onAnimationEnd(Animator animation){
                dealCards();
            }
        });          
        set.start();
    }
}