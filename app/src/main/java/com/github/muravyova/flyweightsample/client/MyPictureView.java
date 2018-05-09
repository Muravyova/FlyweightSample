package com.github.muravyova.flyweightsample.client;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.github.muravyova.flyweightsample.flyweight.Particle;
import com.github.muravyova.flyweightsample.flyweight.ParticleFactory;

import java.util.List;

public class MyPictureView extends android.support.v7.widget.AppCompatImageView {

    private List<ListItem>  mItems;
    private final ParticleFactory mFactory = new ParticleFactory();

    public MyPictureView(Context context) {
        super(context);
    }

    public MyPictureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyPictureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mItems == null){
            return;
        }

        int width = getWidth();
        int height = getHeight();

        for (ListItem item : mItems){
            if (item.getCount() != 0){
                Particle particle = mFactory.getParticle(item);
                for (int count = 0; count < item.getCount(); count++){
                    particle.draw(canvas, (int) (Math.random() * width), (int) (Math.random() * height));
                }
            }
        }
    }

    public void setItems(List<ListItem> items){
        mItems = items;
        invalidate();
    }

    public void clear(){
        mItems = null;
        invalidate();
    }
}
