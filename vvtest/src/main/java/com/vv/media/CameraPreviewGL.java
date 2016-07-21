package com.vv.media;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.Surface;

import com.lib.opengl.GL20ScreenPosition;
import com.lib.opengl.GL20ShaderUtils;
import com.lib.opengl.GLBufferUtils;
import com.lib.opengl.MatrixState;
import com.lib.opengl.base.GL20DrawBase;

import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by wiesen on 16-7-14.
 */
public class CameraPreviewGL extends GLSurfaceView {
    private Context mContext;
    private SurfaceTexture mSurfaceTexture;

    public CameraPreviewGL(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setEGLContextClientVersion(2);
        CameraRender cameraRender = new CameraRender();
        setRenderer(cameraRender);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    private class CameraRender extends GL20DrawBase implements Renderer, SurfaceTexture.OnFrameAvailableListener, CameraUtils.SurfaceLoading {
        private GL20ScreenPosition mScreenPosition;
        private float[] mTextureCoods={
                0,0,
                0,1,
                1,0,
                1,1
        };
        private float[] mVertices ={
                -1f,1f,0,
                -1f,-1f,0,
                1f,1f,0,
                1f,-1f,0
        };

        private int mTextureID;
        private FloatBuffer mVerticeBuf;
        private FloatBuffer mTextureCoodBuf;
        private int mAttribPosition;
        private int muMVPMatrixHandle;
        private int mAttribTexCoord;
        private CameraUtils mCameraUtils;
        @Override
        protected void onCreateProgram() {
            try {
                String vertexStr = GL20ShaderUtils.loadFromAssetsFile("texture_vertex.txt",mContext.getResources());
                String fragmentStr = GL20ShaderUtils.loadFromAssetsFile("camera_frag.txt",mContext.getResources());
                mGL20Factory.createShader(new GL20ShaderUtils(vertexStr,fragmentStr))
                        .createProgram();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onGetLocation() {
            mAttribPosition = mGL20Factory.getAttribLocation("aPosition");
            //获取程序中总变换矩阵引用id
            muMVPMatrixHandle = mGL20Factory.getUniformLocation("uMVPMatrix");
            mAttribTexCoord = mGL20Factory.getAttribLocation("aTexCoor");
        }

        @Override
        protected void doDraw() {
            GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT|GLES20.GL_COLOR_BUFFER_BIT);
            mGL20Factory.useProgram();
            MatrixState.setInitStack();
            //将最终变换矩阵传入shader程序
            GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrixMM(), 0);
            GLES20.glEnable(GLES20.GL_TEXTURE_2D);
            GLES20.glVertexAttribPointer(mAttribPosition, 3, GLES20.GL_FLOAT, false, 0, mVerticeBuf);
            GLES20.glVertexAttribPointer(mAttribTexCoord, 2, GLES20.GL_FLOAT, false, 0, mTextureCoodBuf);

            GLES20.glEnableVertexAttribArray(mAttribPosition);
            GLES20.glEnableVertexAttribArray(mAttribTexCoord);

            //绑定纹理
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
            GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, mTextureID);
//        GLES20.glUniform1i(mUniformTexture, 0);

            GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);

            GLES20.glDisableVertexAttribArray(mAttribPosition);
            GLES20.glDisableVertexAttribArray(mAttribTexCoord);
        }

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            onCreate();
            initTexture();
            mCameraUtils = new CameraUtils(mContext);
            mCameraUtils.onSurfaceLoading(this);
            mCameraUtils.openBackCamera();
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            GLES20.glViewport(0,0,width,height);
            mScreenPosition = GL20ScreenPosition.getInstance(width,height);
            //设置屏幕背景色RGBA
            GLES20.glClearColor(0.5f,0.5f,0.5f, 1.0f);
            float ratio =(float) width / (float) height;
            MatrixState.setPrjectOrthoM(-ratio,ratio,-1,1,1,4);
            //调用此方法产生摄像机9参数位置矩阵
//        MatrixState.setCamera(0,0,2,0f,0f,0f,0f,1.0f,0f);
            initBuf();
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            mSurfaceTexture.updateTexImage();
            doDraw();
        }

        private void initTexture(){
            int[] textures = new int[2];
            GLES20.glGenTextures(2,textures,0);
            mTextureID = textures[0];
            GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, mTextureID);
            GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                    GLES20.GL_TEXTURE_MIN_FILTER,GLES20.GL_LINEAR);
            GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                    GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                    GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
            GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                    GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
            mSurfaceTexture = new SurfaceTexture(textures[0]);
            mSurfaceTexture.setOnFrameAvailableListener(this);
        }

        private void initBuf(){
            float[] point0_0 = mScreenPosition.changePX2GL(0,0);
            float[] point0_1 = mScreenPosition.changePX2GL(0,256);
            float[] point1_1 = mScreenPosition.changePX2GL(256,256);
            float[] point1_0 = mScreenPosition.changePX2GL(256,0);

            float[] vertix ={
                    point0_0[0],point0_0[1],0,
                    point0_1[0],point0_1[1],0,
                    point1_0[0],point1_0[1],0,
                    point1_1[0],point1_1[1],0
            };
//            mVertices = vertix;
            mVerticeBuf = GLBufferUtils.buildFloatBuffer(mVertices);
            mTextureCoodBuf = GLBufferUtils.buildFloatBuffer(mTextureCoods);
        }

        @Override
        public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        }

        @Override
        public Surface onSurfaceLoading() {
            return new Surface(mSurfaceTexture);
        }
    }
}
