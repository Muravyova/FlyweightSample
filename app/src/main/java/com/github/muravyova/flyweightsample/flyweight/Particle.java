package com.github.muravyova.flyweightsample.flyweight;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class Particle {

    protected final int mSize;
    protected Paint mPaint;

    protected Particle(int color, int size) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(color);
        mSize = size;
    }

    public abstract void draw(Canvas canvas, int startX, int startY);
}
