package com.vv.test;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ljdy on 2016/4/18.
 */
public class DrawLinTestView extends View {
    private Paint mPaint;
    public DrawLinTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(5);
        PathEffect effect = new DashPathEffect(new float[]{10,5,10,5},1);
        mPaint.setPathEffect(effect);
        Path path = new Path();
        path.moveTo(10,10);
        path.lineTo(200,300);
        path.lineTo(200,600);
        path.lineTo(10,600);
        path.close();
        canvas.drawPath(path, mPaint);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.CYAN);
        canvas.drawPath(path, mPaint);
    }
}
