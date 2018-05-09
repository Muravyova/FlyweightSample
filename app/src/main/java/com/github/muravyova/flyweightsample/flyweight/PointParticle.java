package com.github.muravyova.flyweightsample.flyweight;

import android.graphics.Canvas;

public class PointParticle extends Particle {

    public PointParticle(int color, int size) {
        super(color, size);
    }

    @Override
    public void draw(Canvas canvas, int startX, int startY) {
        canvas.drawCircle(startX, startY, mSize/2, mPaint);
    }
}
