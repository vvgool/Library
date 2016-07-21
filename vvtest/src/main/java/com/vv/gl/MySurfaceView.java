package com.vv.gl;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.vv.test.MyGestureListener;

/**
 * Created by ljdy on 2016/6/15.
 */
public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mSurfaceHolder;
    private int mCanvasWidth;
    private int mCanvasHeight;
    private Paint mPaint;
    private final String TAG ="SurfaceView";

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.RED);
        Canvas canvas = mSurfaceHolder.lockCanvas();
        canvas.drawColor(Color.GRAY);
        mSurfaceHolder.unlockCanvasAndPost(canvas);
        Log.i(TAG,"surface had create");
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        Log.i(TAG,"surface had changed" + i +" next:" +i1 +"next" +i2);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        Log.i(TAG,"surface had destroy");
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(TAG,"do onDraw method");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mCanvasWidth = getMeasuredWidth();
        mCanvasHeight = getMeasuredHeight();
        Log.i(TAG,"onMeasure" +"width:" +mCanvasWidth +" height:"+mCanvasHeight);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mMyGestureListener.onTouchEvent(event);
    }

    private MyGestureListener mMyGestureListener  = new MyGestureListener() {
        @Override
        public void onTouchClick(MotionEvent event) {

        }

        @Override
        public void onTouchMove(MotionEvent event) {
            Canvas canvas = mSurfaceHolder.lockCanvas(new Rect(mCanvasWidth/4,mCanvasHeight/4,mCanvasWidth*3/4,mCanvasHeight*3/4));
            canvas.drawCircle(event.getX(),event.getY(),5,mPaint);
            mSurfaceHolder.unlockCanvasAndPost(canvas);
            Log.i(TAG,"doing move event"+"x:"+event.getX()+" y:"+event.getY());
        }

        @Override
        public void onTouchTwoPointDown(MotionEvent event) {

        }

        @Override
        public void onTouchTwoPointMove(MotionEvent event) {

        }

        @Override
        public void onTouchMorePointUp(MotionEvent event) {

        }
    };
}
