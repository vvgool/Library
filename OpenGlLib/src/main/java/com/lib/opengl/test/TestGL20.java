package com.lib.opengl.test;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.lib.opengl.GL20ShaderUtils;
import com.lib.opengl.base.GL20DrawBase;
import com.lib.opengl.GL20ScreenPosition;
import com.lib.opengl.GLBufferUtils;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by ljdy on 2016/7/5.
 */
public class TestGL20 extends GL20DrawBase implements GLSurfaceView.Renderer{

    private final Context mContext;
    private int mPositionHandle;
    private int mColorHandle;
    private float[] mOrthoM = new float[16];
    private float[] mLookM = new float[16];

    private float[] mVertex={
            -0.5f,  0.5f, 0.0f,  // 0, Top Left
            -0.5f,  -0.5f, 0.0f,  // 1, Bottom Left
            0.5f,  -0.5f, 0.0f,  // 2, Bottom Right
            0.5f,  0.5f, 0.0f  // 3, Top Right
     };

    private FloatBuffer mVertexBuffer;

    private short[] mIndexArray ={
            0,1,2,0,2,3
    };
    private float[] mColorArray ={
            1f, 0f, 0f, 1f, // vertex 0 red
            0f, 1f, 0f, 1f, // vertex 1 green
            0f, 0f, 1f, 1f, // vertex 2 blue
            1f, 0f, 1f, 1f, // vertex 3 magenta
    };
    private FloatBuffer mColorBuffer;
    private ShortBuffer mIndexBuffer;
    private GL20ScreenPosition mScreenPosition;
    public TestGL20(Context context){
        mContext = context;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    protected void onCreateProgram() {
        try {
            String vertexShader = GL20ShaderUtils.loadFromAssetsFile("text_vertex.txt",mContext.getResources());
            String fragmentShader = GL20ShaderUtils.loadFromAssetsFile("text_frag.txt",mContext.getResources());
            mGL20Factory.createShader(new GL20ShaderUtils(vertexShader,fragmentShader))
                    .createProgram();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onGetLocation() {
        // 获取指向vertex shader的成员vPosition的 handle
        mPositionHandle=mGL20Factory.getAttribLocation("aPosition");
        mColorHandle = mGL20Factory.getAttribLocation("aColor");
    }

    @Override
    protected void doDraw() {
        // Counter-clockwise winding.
        GLES20.glFrontFace(GL10.GL_CCW);
        // Enable face culling.
        GLES20.glEnable(GL10.GL_CULL_FACE);
        // What faces to remove with the face culling.
        GLES20.glCullFace(GL10.GL_BACK);
        // 将program加入OpenGL ES环境中
        mGL20Factory.useProgram();
        GLES20.glClear(GL10.GL_COLOR_BUFFER_BIT | // OpenGL docs.
                GL10.GL_DEPTH_BUFFER_BIT);
        GLES20.glClearColor(0,1,1,0);

        // 启用一个指向三角形的顶点数组的handle
        // 准备三角形的坐标数据
        GLES20.glVertexAttribPointer(mPositionHandle, 3,
                GLES20.GL_FLOAT, false,
                0, mVertexBuffer);

        GLES20.glVertexAttribPointer(mColorHandle, 4,
                GLES20.GL_FLOAT, false,
                0, mColorBuffer);

        // 设置三角形的颜色
//        GLES20.glUniform4fv(mColorHandle, 1, mColorArray,0);
        GLES20.glEnableVertexAttribArray(mColorHandle);
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        // 画三角形
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, mIndexArray.length,GLES20.GL_UNSIGNED_SHORT, mIndexBuffer);


//        GLES20.glDrawArrays(GLES20.GL_TRIANGLES,0,mVertex.length/3);
        // 禁用指向三角形的顶点数组
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        onCreate();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0,0,width,height);
        float ratio =(float) width / (float) height;
        Matrix.orthoM(mOrthoM,0,-ratio,ratio,-1,1,1,4);
//        Matrix.setLookAtM(mLookM,0,0,0,3,0,0,0,1,1,1);
        mScreenPosition = GL20ScreenPosition.getInstance(width,height);
        initBuf();
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        doDraw();
    }

    public void initBuf(){
        float[] point0_0 = mScreenPosition.changePX2GL(0-128,0-128);
        float[] point0_1 = mScreenPosition.changePX2GL(0-128,256-128);
        float[] point1_1 = mScreenPosition.changePX2GL(256-128,256-128);
        float[] point1_0 = mScreenPosition.changePX2GL(256-128,0-128);

        float[] vertix ={
                point0_0[0],point0_0[1],0,
                point0_1[0],point0_1[1],0,
                point1_1[0],point1_1[1],0,
                point1_0[0],point1_0[1],0
        };
        mVertex = vertix;
        mVertexBuffer = GLBufferUtils.buildFloatBuffer(mVertex);
        mColorBuffer = GLBufferUtils.buildFloatBuffer(mColorArray);
        mIndexBuffer = GLBufferUtils.buildShortBuffer(mIndexArray);
    }
}
