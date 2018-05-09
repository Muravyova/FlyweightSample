package com.github.muravyova.flyweightsample.flyweight;

import android.graphics.Canvas;

public class SquareParticle extends Particle {

    public SquareParticle(int color, int size) {
        super(color, size);
    }

    @Override
    public void draw(Canvas canvas, int startX, int startY) {
        canvas.drawRect(startX, startY, startX + mSize, startY + mSize, mPaint);
    }
}
