package com.lib.opengl.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.lib.opengl.base.GL20DrawBase;
import com.lib.opengl.GL20ScreenPosition;
import com.lib.opengl.GL20ShaderUtils;
import com.lib.opengl.GL20TextureUtils;
import com.lib.opengl.GLBufferUtils;
import com.lib.opengl.MatrixState;
import com.lib.opengl.R;

import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by wiesen on 16-7-7.
 */
public class TextureGL20 extends GL20DrawBase implements GLSurfaceView.Renderer{
    private final Context mContext;
    private GL20ScreenPosition mScreenPosition;
    private float[] mTextureCoods={
            0,0,
            0,1,
            1,0,
            1,1
    };
    private float[] mVertices ={
            -0.5f,0.5f,0,
            -0.5f,-0.5f,0,
            0.5f,0.5f,0,
            0.5f,-0.5f,0
    };

    private int mTextureID;
    private FloatBuffer mVerticeBuf;
    private FloatBuffer mTextureCoodBuf;
    private int mAttribPosition;
    private int muMVPMatrixHandle;
    private int mAttribTexCoord;

    public TextureGL20(Context context){
        mContext = context;
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
        mVertices = vertix;
        mVerticeBuf = GLBufferUtils.buildFloatBuffer(mVertices);
        mTextureCoodBuf = GLBufferUtils.buildFloatBuffer(mTextureCoods);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.w419);
        mTextureID = GL20TextureUtils.loadTexture(bitmap);


    }

    @Override
    protected void onCreateProgram() {
        try {
            String vertexStr = GL20ShaderUtils.loadFromAssetsFile("texture_vertex.txt",mContext.getResources());
            String fragmentStr = GL20ShaderUtils.loadFromAssetsFile("texture_frag.txt",mContext.getResources());
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
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureID);
//        GLES20.glUniform1i(mUniformTexture, 0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);

        GLES20.glDisableVertexAttribArray(mAttribPosition);
        GLES20.glDisableVertexAttribArray(mAttribTexCoord);

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        onCreate();
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
        doDraw();
    }

}
