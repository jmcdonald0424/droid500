package com.fivehundred.droid500.view;

import android.graphics.PointF;

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

    @Override
    public void setScale(float scale) {
        super.scale = scale;
    }

    @Override
    public void setAngle(float angle) {
        super.angle = angle;
    }

    @Override
    public void setTranslation(PointF translation) {
        super.translation = translation;
    }
}