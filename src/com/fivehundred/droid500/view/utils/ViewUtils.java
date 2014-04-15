package com.fivehundred.droid500.view.utils;

import android.graphics.PointF;
import android.graphics.RectF;

public class ViewUtils {

    public static RectF copy(RectF rect) {
        RectF newRect = new RectF();
        newRect.left = rect.left;
        newRect.top = rect.top;
        newRect.right = rect.right;
        newRect.bottom = rect.bottom;
        return newRect;
    }
    
    public static PointF getDropPoint(int playerIndex){
        // Divide table by 3 to determine quadrant size
        float divider = ViewConstants.BASE_SCALE_MIN / 3;
        float center = divider / 2;
        int xIndex = playerIndex > 0 ? (playerIndex > 3 ? 1 : playerIndex - 1) : playerIndex + 1;
        int yIndex = playerIndex < 3 ? playerIndex : (playerIndex > 3 ? 1 : playerIndex % 2);
        float xCoord = xIndex * divider + center;
        float yCoord = yIndex * divider + center;
        return new PointF(xCoord, yCoord);
    }
    
    public static PointF getDealerLocation(int dealerIndex){
        float divider = ViewConstants.BASE_SCALE_MIN / 3;        
        float center = divider + (divider / 2);
        switch(dealerIndex){
            case 0:
                return new PointF(center, center - (divider * 2));
            case 1:
                return new PointF(center - (divider * 2), center);
            case 2:
                return new PointF(center, center + (divider * 2));
            case 3:
                return new PointF(center + (divider * 2), center);
            default:                
                return new PointF(center, center - (divider * 2));
        }
    }
}
