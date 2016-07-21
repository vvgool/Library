package com.vv.gl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.opengl.GLSurfaceView;
import android.util.Log;

import com.vv.test.R;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by ljdy on 2016/6/20.
 */
public class MyRenderer implements GLSurfaceView.Renderer {
    private static final String TAG ="GL_Renderer";
    private static final int MAP_SIZE = 256;
    private Context mContext;
    private int mCurrentWidth;
    private int mCurrentHeight;
    private int mCanvasWidth;
    private int mCanvasHeight;


    private float[] mVertexArray ={
            0.0f,  0.5f, 0.0f,  // 0, Top Left
            0.0f,  0.0f, 0.0f,  // 1, Bottom Left
            0.5f,  0.0f, 0.0f,  // 2, Bottom Right
            0.5f,  0.5f, 0.0f,  // 3, Top Right
    };

    private short[] mIndexArray ={
            0,1,2,0,2,3
    };
    private float[] mColorArray ={
            1f, 0f, 0f, 1f, // vertex 0 red
            0f, 1f, 0f, 1f, // vertex 1 green
            0f, 0f, 1f, 1f, // vertex 2 blue
            1f, 0f, 1f, 1f, // vertex 3 magenta
    };

    private FloatBuffer mVertexBuffer = setToFloatBuffer(mVertexArray);
    private ShortBuffer mIndexBuffer = setToShortBuffer(mIndexArray);
    private FloatBuffer mColorBuffer = setToFloatBuffer(mColorArray);

    private Cube cube;
    private TextureTest textureTest;

    public MyRenderer(Context context){
        this.mContext = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        Log.i(TAG,"onSurfaceCreated");
        // Set the background color to black ( rgba ).
        gl10.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);  // OpenGL docs.
        // Enable Smooth Shading, default not really needed.
        gl10.glShadeModel(GL10.GL_SMOOTH);// OpenGL docs.
        // Depth buffer setup.
        gl10.glClearDepthf(1.0f);// OpenGL docs.
        // Enables depth testing.
        gl10.glEnable(GL10.GL_DEPTH_TEST);// OpenGL docs.
        // The type of depth testing to do.
        gl10.glDepthFunc(GL10.GL_LEQUAL);// OpenGL docs.
        // Really nice perspective calculations.
        gl10.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, // OpenGL docs.
                GL10.GL_NICEST);
        cube = new Cube(1,1,1);
        cube.setColors(mColorArray);
        cube.rx = 30;
        cube.ry = 30;
        textureTest = new TextureTest(mContext);
//        textureTest.onCreate(gl10);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        mCurrentWidth = width;
        mCurrentHeight = height;
        Log.i(TAG,"onSurfaceChanged");
        // Sets the current view port to the new size.
        gl.glViewport(0, 0, width, height);// OpenGL docs.
        // Select the projection matrix
        gl.glMatrixMode(GL10.GL_PROJECTION);// OpenGL docs.
        // Reset the projection matrix
        gl.glLoadIdentity();// OpenGL docs.
        // Calculate the aspect ratio of the window
//        GLU.gluPerspective(gl, 45.0f,
//                (float) width / (float) height,
//                0.1f, 100.0f);
        float ratio =width < height? width/height:height/width;
////        gl.glFrustumf(0, width,0,height,width,ratio);
        gl.glOrthof(0,width,0,height,-10,10);
        // Select the modelview matrix
        gl.glMatrixMode(GL10.GL_MODELVIEW);// OpenGL docs.
        // Reset the modelview matrix
        gl.glLoadIdentity();// OpenGL docs.
        createCanvas(gl,width,height);

    }

    //计算Canvas地图大小
    public void createCanvas(GL10 gl,int width,int height){
        mCanvasWidth = (width%MAP_SIZE==0?width/MAP_SIZE +2 :width/MAP_SIZE +3)*MAP_SIZE;
        mCanvasHeight = (height%MAP_SIZE == 0? height/MAP_SIZE +2:height/MAP_SIZE +3)*MAP_SIZE;
        Bitmap bitmap = Bitmap.createBitmap(mCanvasWidth,mCanvasHeight, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        Bitmap map = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.w419);
        canvas.drawColor(Color.BLUE);
        canvas.drawBitmap(map,MAP_SIZE*3,MAP_SIZE*3,null);
        textureTest.onCreate(gl,bitmap);
        createFloatVertix();
    }
    @Override
    public void onDrawFrame(GL10 gl10) {
        Log.i(TAG,"onDrawFrame");
        // Clears the screen and depth buffer.
        gl10.glClear(GL10.GL_COLOR_BUFFER_BIT | // OpenGL docs.
                GL10.GL_DEPTH_BUFFER_BIT);
        gl10.glLoadIdentity();
//        gl10.glPushMatrix();
//        gl10.glTranslatef(0,0,-10);
//        drawSqu(gl10);

//        cube.draw(gl10);
        textureTest.drawTexture(gl10);

    }

    public Cube getCube(){
        return cube;
    }


    public  FloatBuffer setToFloatBuffer(final float[] floats){
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(floats.length*4);
        byteBuffer.order(ByteOrder.nativeOrder());
        FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
        floatBuffer.put(floats);
        floatBuffer.position(0);
        return floatBuffer;
    }


    public  ShortBuffer setToShortBuffer(final short[] shorts){
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(shorts.length*2);
        byteBuffer.order(ByteOrder.nativeOrder());
        ShortBuffer shortBuffer = byteBuffer.asShortBuffer();
        shortBuffer.put(shorts);
        shortBuffer.position(0);
        return shortBuffer;
    }

    public void drawSqu(GL10 gl10){
        gl10.glEnableClientState(GL10.GL_COLOR_ARRAY);
        gl10.glColorPointer(4,GL10.GL_FLOAT,0,mColorBuffer);


        // Counter-clockwise winding.
        gl10.glFrontFace(GL10.GL_CCW);
        // Enable face culling.
        gl10.glEnable(GL10.GL_CULL_FACE);
        // What faces to remove with the face culling.
        gl10.glCullFace(GL10.GL_BACK);

        // Enabled the vertices buffer for writing
        //and to be used during
        // rendering.
        gl10.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        // Specifies the location and data format of
        //an array of vertex
        // coordinates to use when rendering.
        gl10.glVertexPointer(3, GL10.GL_FLOAT, 0,
                mVertexBuffer);


        gl10.glDrawElements(GL10.GL_TRIANGLES, mIndexArray.length,
                GL10.GL_UNSIGNED_SHORT, mIndexBuffer);

        // Disable the vertices buffer.
        gl10.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        // Disable face culling.
        gl10.glDisable(GL10.GL_CULL_FACE);

        gl10.glDisableClientState(GL10.GL_COLOR_ARRAY);
    }


    public float[] changePXToGL(float X, float Y){
        float point[] = new float[2];
        if (X <= mCurrentWidth/2){
            point[0] =-(mCurrentWidth/2 - X)/mCurrentWidth;
        }else {
            point[0] = (X - mCurrentWidth/2)/mCurrentWidth;
        }

        if (Y <= mCurrentHeight/2){
            point[1] = (mCurrentHeight/2 - Y) / mCurrentHeight;
            point[1] =-(Y - mCurrentHeight/2) / mCurrentHeight;
        }
        return point;
    }

    public float[] changePX2GL(float X,float Y){
        float point[] = new float[20];
        point[0] = X;
        point[1] = mCurrentHeight - Y;
        return point;
    }


    public void createFloatVertix(){
        float[] point0_0 = changePX2GL(-(mCanvasWidth-mCurrentWidth)/2,-(mCanvasHeight-mCurrentHeight)/2);
        float[] point0_1 = changePX2GL(-(mCanvasWidth-mCurrentWidth)/2,(mCanvasHeight-mCurrentHeight)/2+mCurrentHeight);
        float[] point1_1 = changePX2GL((mCanvasWidth-mCurrentWidth)/2+mCurrentWidth,(mCanvasHeight-mCurrentHeight)/2+mCurrentHeight);
        float[] point1_0 = changePX2GL((mCanvasWidth-mCurrentWidth)/2+mCurrentWidth,-(mCanvasHeight-mCurrentHeight)/2);

        float[] vertix ={
                point0_0[0],point0_0[1],0,
                point0_1[0],point0_1[1],0,
                point1_0[0],point1_0[1],0,
                point1_1[0],point1_1[1],0
        };

        float[] verticx = {
            0,256,0,
                0,0,0,
                256,256,0,
                256,0,0
        };
        textureTest.setVertices(vertix);
    }
}
