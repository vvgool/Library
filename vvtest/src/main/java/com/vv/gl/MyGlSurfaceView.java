package com.vv.gl;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.vv.map.deu.MapViewGL;


/**
 * Created by ljdy on 2016/6/17.
 */
public class MyGlSurfaceView extends GLSurfaceView implements MapViewGL.RequestRenderFresh {
    private final Context mContext;
    private MapViewGL mMyRenderer;
    private float mCurrX;
    private float mCurrY;
    private ImageView image;

    public MyGlSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setEGLContextClientVersion(2);
        mMyRenderer = new MapViewGL(context);
        setRenderer(mMyRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        mMyRenderer.setOnRequestRenderFresh(this);
    }

   /* @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mCurrX = event.getX();
                mCurrY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:

                mMyRenderer.getCube().rx += event.getX() - mCurrX;
                mMyRenderer.getCube().ry += event.getY() - mCurrY;
                mCurrX = event.getX();
                mCurrY = event.getY();
                break;
        }
        return true;
    }*/

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mMyRenderer.onTouchEvent(event);
        return true;
    }

    @Override
    public void onFresh() {
        requestRender();
        if (image!=null){
            ((Activity)mContext).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    image.setImageBitmap(mMyRenderer.getBitmap());
                }
            });

        }
    }

    public void setImage(ImageView image) {
        this.image = image;
    }
}
