package com.vv.gl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by ljdy on 2016/6/28.
 */
public class TextureTest {
    private static final int size = 256/2;
    private float[] mVertices ={
            -0.5f,0.5f,0,
            -0.5f,-0.5f,0,
            0.5f,0.5f,0,
            0.5f,-0.5f,0
    };


    private float[] mTextureCoods={
             0,0,
            0,1,
            1,0,
            1,1
    };
    private FloatBuffer mVerticeBuf;
    private FloatBuffer mTextureCoodBuf;
    private FloatBuffer mIndexBuf;
    private Context mContext;
    private int mTextureID;

    public TextureTest(Context context){
        this.mContext = context;
        setTextureCoods(mTextureCoods);
    }

    public void onCreate(GL10 gl10,Bitmap bitmap){
//        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.w419);
        mTextureID =  loadTexture(gl10,bitmap);
    }

    public int loadTexture(GL10 gl,Bitmap bitmap){
            int textureIds[] = new int[1];
            gl.glGenTextures(1, textureIds, 0);
            int textureId = textureIds[0];
            gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
            GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
                    GL10.GL_NEAREST);
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
                    GL10.GL_NEAREST);
            gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
            bitmap.recycle();
            return textureId;
    }


    public void drawTexture(GL10 gl10){
        gl10.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl10.glVertexPointer(3,GL10.GL_FLOAT,0,mVerticeBuf);

        gl10.glEnable(GL10.GL_TEXTURE_2D);
        gl10.glBindTexture(GL10.GL_TEXTURE_2D,mTextureID);
        gl10.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl10.glTexCoordPointer(2,GL10.GL_FLOAT,0,mTextureCoodBuf);
        gl10.glDrawArrays(GL10.GL_TRIANGLE_STRIP,0,mVertices.length/3);
        gl10.glDisable(GL10.GL_TEXTURE_2D);
    }


    public void setVertices(float[] vertices){
        this.mVertices = vertices;
        this.mVerticeBuf = setToFloatBuffer(mVertices);
    }

    public void setTextureCoods(float[] textureCoods){
        this.mTextureCoods = textureCoods;
        this.mTextureCoodBuf = setToFloatBuffer(mTextureCoods);
    }


    public  FloatBuffer setToFloatBuffer(final float[] floats){
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(floats.length*4);
        byteBuffer.order(ByteOrder.nativeOrder());
        FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
        floatBuffer.put(floats);
        floatBuffer.position(0);
        return floatBuffer;
    }
}
