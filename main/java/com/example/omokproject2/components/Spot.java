package com.example.omokproject2.components;

import android.graphics.Paint;

public class Spot {
    public int x, y;
    public float size;
    public Paint paint;
    public Spot(int x, int y, float size, int color){
        this.x = x;
        this.y = y;
        this.size = size;
        this.paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
    }
    public Spot(int x, int y, float size, int color, float width){
        this.x = x;
        this.y = y;
        this.size = size;
        this.paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(width);
    }
    public Spot(int x, int y, float size, Paint paint){
        this.x = x;
        this.y = y;
        this.size = size;
        this.paint = new Paint(paint);
    }
}
