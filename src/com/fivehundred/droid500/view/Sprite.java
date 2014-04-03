package com.fivehundred.droid500.view;

import android.graphics.PointF;
import android.graphics.RectF;
import com.fivehundred.droid500.view.utils.ViewUtils;

public class Sprite {

    private RectF base;
    private RectF scaledBase;
    private PointF translation;
    private float angle;
    private float scale;
    private float uvs[];
    private int index;

    public Sprite() {
        base = new RectF(-20f, 30f, 20f, -30f); // Left, Top, Right, Bottom relative to 0,0
        scaledBase = ViewUtils.copy(base);
        translation = new PointF(160f, 240f);
        scale = 1f;
        angle = 0;
    }

    public Sprite(float x, float y) {
        base = new RectF(-20f, 30f, 20f, -30f); // Left, Top, Right, Bottom relative to 0,0
        scaledBase = ViewUtils.copy(base);
        translation = new PointF(x, y);
        scale = 1f;
        angle = 0;
    }

    public void setBaseScale(float ssu) {
        scaledBase.left = base.left * ssu;
        scaledBase.top = base.top * ssu;
        scaledBase.right = base.right * ssu;
        scaledBase.bottom = base.bottom * ssu;
        translation.x *= ssu;
        translation.y *= ssu;
    }

    public void translate(float dX, float dY) {
        translation.x += dX;
        translation.y += dY;
    }

    public void scale(float dS) {
        scale += dS;
    }

    public void rotate(float dA) {
        angle += dA;
    }

    public float[] getVertices() {

		// Order of vertices manipulation: scale -> rotate -> translate
        // SCALE
        float x1 = scaledBase.left * scale;
        float x2 = scaledBase.right * scale;
        float y1 = scaledBase.bottom * scale;
        float y2 = scaledBase.top * scale;

		// ROTATE
        // Detach from Rect for rotation
        PointF one = new PointF(x1, y2);
        PointF two = new PointF(x1, y1);
        PointF three = new PointF(x2, y1);
        PointF four = new PointF(x2, y2);
        float s = (float) Math.sin(angle);
        float c = (float) Math.cos(angle);
        one.x = x1 * c - y2 * s;
        one.y = x1 * s + y2 * c;
        two.x = x1 * c - y1 * s;
        two.y = x1 * s + y1 * c;
        three.x = x2 * c - y1 * s;
        three.y = x2 * s + y1 * c;
        four.x = x2 * c - y2 * s;
        four.y = x2 * s + y2 * c;

        // TRANSLATE
        one.x += translation.x;
        one.y += translation.y;
        two.x += translation.x;
        two.y += translation.y;
        three.x += translation.x;
        three.y += translation.y;
        four.x += translation.x;
        four.y += translation.y;

        return new float[]{
            one.x, one.y, 0.0f,
            two.x, two.y, 0.0f,
            three.x, three.y, 0.0f,
            four.x, four.y, 0.0f
        };
    }

    public void generateUvCoords(int imageIndex) {
        uvs = new float[8];
        index = imageIndex;
        int size = Atlas.MAIN_ATLAS.getSize();
        int columns = Atlas.MAIN_ATLAS.getColumnCount();
        int rows = (int) Math.ceil(size / columns);
        float xOffset = 1.0f / columns;
        float yOffset = 1.0f / rows;
        float xMargin = 1.0f / 85.3f; // This value is dependent on the image, so consider passing in or creating subclass to handle it
        float x1 = xOffset * (imageIndex % columns) + xMargin;
        float x2 = x1 + xOffset - xMargin * 2;
        float y1 = yOffset * (imageIndex % rows);
        float y2 = y1 + yOffset;

        // Top Left
        uvs[0] = x1;
        uvs[1] = y1;
        // Bottom Left
        uvs[2] = x1;
        uvs[3] = y2;
        // Bottom Right
        uvs[4] = x2;
        uvs[5] = y2;
        // Top Right
        uvs[6] = x2;
        uvs[7] = y1;
    }

    public float[] getUvs() {
        return uvs;
    }

    public void setUvs(float uvs[]) {
        this.uvs = uvs;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}
