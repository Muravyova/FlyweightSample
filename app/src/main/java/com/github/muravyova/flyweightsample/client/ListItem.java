package com.github.muravyova.flyweightsample.client;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;

import com.github.muravyova.flyweightsample.util.Colors;

import java.util.ArrayList;
import java.util.List;

public class ListItem {

    public enum Type{
        POINT,
        SQUARE
    }

    @NonNull
    private final String mId;
    private final Bitmap mBitmap;
    private int mCount;
    private int mColor;
    private final Type mType;
    private int mSize;
    private boolean mSelect;

    public ListItem(int color, Type type, int size) {
        mId = color + type.toString() + size;
        mColor = color;
        mType = type;
        mSize = size;
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        mBitmap = Bitmap.createBitmap(60,60, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mBitmap);
        switch (mType){
            case SQUARE:
                canvas.drawRect(3,3, 57, 57, paint);
                break;
            case POINT:
                canvas.drawCircle(30, 30, 27, paint);
                break;
        }
    }

    public int getCount() {
        return mCount;
    }

    public void setCount(int count) {
        mCount = count;
    }

    public int getColor() {
        return mColor;
    }

    public Type getType() {
        return mType;
    }

    public int getSize() {
        return mSize;
    }

    public boolean isSelect() {
        return mSelect;
    }

    public void setSelect(boolean select) {
        mSelect = select;
    }

    @NonNull
    public String getId() {
        return mId;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public static List<ListItem> getItems(){
        List<ListItem> items = new ArrayList<>();
        items.add(new ListItem(Colors.redColor, Type.POINT, 12));
        items.get(0).setSelect(true);
        items.add(new ListItem(Colors.greenColor, Type.POINT, 12));
        items.add(new ListItem(Colors.blueColor, Type.POINT, 12));
        items.add(new ListItem(Colors.redColor, Type.SQUARE, 12));
        items.add(new ListItem(Colors.greenColor, Type.SQUARE, 12));
        items.add(new ListItem(Colors.blueColor, Type.SQUARE, 12));
        return items;
    }
}
