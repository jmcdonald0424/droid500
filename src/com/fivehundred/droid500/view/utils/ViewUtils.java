package com.fivehundred.droid500.view.utils;

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
}
