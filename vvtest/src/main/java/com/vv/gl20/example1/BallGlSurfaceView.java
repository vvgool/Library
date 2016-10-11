package com.vv.gl20.example1;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.lib.opengl.MatrixState;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by wiesen on 16-7-26.
 */
public class BallGlSurfaceView extends GLSurfaceView {
    private final float TOUCH_SCALE_FACTOR = 180.0f/320;//角度缩放比例
    private BallRenderer mRenderer;
    private Ball mBall;
    private float mPreviousY;//上次的触控位置Y坐标
    private float mPreviousX;//上次的触控位置X坐标

    public BallGlSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLContextClientVersion(2);
        mRenderer = new BallRenderer(context);
        setRenderer(mRenderer);
    }

    //触摸事件回调方法
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float y = e.getY();
        float x = e.getX();
        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float dy = y - mPreviousY;//计算触控笔Y位移
                float dx = x - mPreviousX;//计算触控笔X位移
                mBall.yAngle += dx * TOUCH_SCALE_FACTOR;//设置填充椭圆绕y轴旋转的角度
                mBall.xAngle+= dy * TOUCH_SCALE_FACTOR;//设置填充椭圆绕x轴旋转的角度
        }
        mPreviousY = y;//记录触控笔位置
        mPreviousX = x;//记录触控笔位置
        return true;
    }

    private class BallRenderer implements Renderer {


        private Context mContext;

        public BallRenderer(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            mBall = new Ball(mContext);
            mBall.onCreate();
            //设置屏幕背景色RGBA
            GLES20.glClearColor(0f,0f,0f, 1.0f);
            //打开深度检测
            GLES20.glEnable(GLES20.GL_DEPTH_TEST);
            //打开背面剪裁
            GLES20.glEnable(GLES20.GL_CULL_FACE);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {

            //设置视窗大小及位置
            GLES20.glViewport(0, 0, width, height);
            //计算GLSurfaceView的宽高比
           float ratio = (float) width / height;
            // 调用此方法计算产生透视投影矩阵
            MatrixState.setProjectFrustumM(-ratio, ratio, -1, 1, 20, 100);
            // 调用此方法产生摄像机9参数位置矩阵
            MatrixState.setCamera(0, 0, 30, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

            //初始化变换矩阵
            MatrixState.setInitStack();
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            //清除深度缓冲与颜色缓冲
            GLES20.glClear( GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
            //保护现场
            MatrixState.pushMatrix();
            //绘制球
            MatrixState.pushMatrix();
            mBall.doDraw();
            MatrixState.popMatrix();
            //恢复现场
            MatrixState.popMatrix();

//            //保护现场
//            MatrixState.pushMatrix();
//            //绘制球
//            MatrixState.pushMatrix();
//            MatrixState.translate(-1.2f, 0, 0);
//            mBall.doDraw();
//            MatrixState.popMatrix();
//            //绘制球
//            MatrixState.pushMatrix();
//            MatrixState.translate(1.2f, 0, 0);
//            mBall.doDraw();
//            MatrixState.popMatrix();
//            //恢复现场
//            MatrixState.popMatrix();
        }
    }
}
