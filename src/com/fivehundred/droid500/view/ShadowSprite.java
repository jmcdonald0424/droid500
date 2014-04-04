package com.fivehundred.droid500.view;

public class ShadowSprite extends Sprite{
    
    @Override
    public void translate(float dX, float dY) {
        super.translation.x += dX;
        super.translation.y += dY;
    }

    @Override
    public void scale(float dS) {
        super.scale += dS;
    }

    @Override
    public void rotate(float dA) {
        super.angle += dA;
    }
}