package com.vv.gl20.example1;

import android.content.Context;
import android.opengl.GLES20;

import com.lib.opengl.GL20ShaderUtils;
import com.lib.opengl.GLBufferUtils;
import com.lib.opengl.MatrixState;
import com.lib.opengl.base.GL20DrawBase;

import java.nio.FloatBuffer;
import java.util.ArrayList;

/**
 * Created by wiesen on 16-7-26.
 */
public class Ball extends GL20DrawBase {
    private static final int UNIT_SIZE =1;
    int muMVPMatrixHandle;// 总变换矩阵引用
    int maPositionHandle; // 顶点位置属性引用
    int muRHandle;// 球的半径属性引用e
    Context mContext;

    FloatBuffer mVertexBuffer;// 顶点坐标数据缓冲
    int vCount = 0;
    float yAngle = 0;// 绕y轴旋转的角度
    float xAngle = 0;// 绕x轴旋转的角度
    float zAngle = 0;// 绕z轴旋转的角度
    float r = 0.8f;

    public Ball(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initVertexData();
    }

    @Override
    protected void onCreateProgram() {
        try {
            // 顶点着色器
            String mVertexShader= GL20ShaderUtils.loadFromAssetsFile("vertex.txt",mContext.getResources());

            // 片元着色器
            String mFragmentShader = GL20ShaderUtils.loadFromAssetsFile("frag.txt",mContext.getResources());
            mGL20Factory.createShader(new GL20ShaderUtils(mVertexShader,mFragmentShader))
                    .createProgram();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onGetLocation() {
        // 获取程序中顶点位置属性引用
        maPositionHandle = mGL20Factory.getAttribLocation("aPosition");
        // 获取程序中总变换矩阵引用
        muMVPMatrixHandle = mGL20Factory.getUniformLocation("uMVPMatrix");
        // 获取程序中球半径引用
        muRHandle = mGL20Factory.getUniformLocation("uR");
    }

    @Override
    protected void doDraw() {
        MatrixState.rotate(xAngle, 1, 0, 0);//绕X轴转动
        MatrixState.rotate(yAngle, 0, 1, 0);//绕Y轴转动
        MatrixState.rotate(zAngle, 0, 0, 1);//绕Z轴转动
        // 制定使用某套shader程序
        mGL20Factory.useProgram();
        // 将最终变换矩阵传入shader程序
        GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false,
                MatrixState.getFinalMatrix(), 0);
        // 将半径尺寸传入shader程序
        GLES20.glUniform1f(muRHandle, r * UNIT_SIZE);
        // 为画笔指定顶点位置数据
        GLES20.glVertexAttribPointer(maPositionHandle, 3, GLES20.GL_FLOAT,
                false, 3 * 4, mVertexBuffer);
        // 允许顶点位置数据数组
        GLES20.glEnableVertexAttribArray(maPositionHandle);
        // 绘制球
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vCount);
    }

    // 初始化顶点坐标数据的方法
    public void initVertexData() {
        // 顶点坐标数据的初始化================begin============================
        ArrayList<Float> alVertix = new ArrayList<Float>();// 存放顶点坐标的ArrayList
        final int angleSpan = 10;// 将球进行单位切分的角度
        // 垂直方向angleSpan度一份
        for (int vAngle = -90; vAngle < 90; vAngle = vAngle + angleSpan){
            for (int hAngle = 0; hAngle <= 360; hAngle = hAngle + angleSpan)// 水平方向angleSpan度一份
            {// 纵向横向各到一个角度后计算对应的此点在球面上的坐标
                float x0 = (float) (r * UNIT_SIZE
                        * Math.cos(Math.toRadians(vAngle)) * Math.cos(Math
                        .toRadians(hAngle)));
                float y0 = (float) (r * UNIT_SIZE
                        * Math.cos(Math.toRadians(vAngle)) * Math.sin(Math
                        .toRadians(hAngle)));
                float z0 = (float) (r * UNIT_SIZE * Math.sin(Math
                        .toRadians(vAngle)));

                float x1 = (float) (r * UNIT_SIZE
                        * Math.cos(Math.toRadians(vAngle)) * Math.cos(Math
                        .toRadians(hAngle + angleSpan)));
                float y1 = (float) (r * UNIT_SIZE
                        * Math.cos(Math.toRadians(vAngle)) * Math.sin(Math
                        .toRadians(hAngle + angleSpan)));
                float z1 = (float) (r * UNIT_SIZE * Math.sin(Math
                        .toRadians(vAngle)));

                float x2 = (float) (r * UNIT_SIZE
                        * Math.cos(Math.toRadians(vAngle + angleSpan)) * Math
                        .cos(Math.toRadians(hAngle + angleSpan)));
                float y2 = (float) (r * UNIT_SIZE
                        * Math.cos(Math.toRadians(vAngle + angleSpan)) * Math
                        .sin(Math.toRadians(hAngle + angleSpan)));
                float z2 = (float) (r * UNIT_SIZE * Math.sin(Math
                        .toRadians(vAngle + angleSpan)));

                float x3 = (float) (r * UNIT_SIZE
                        * Math.cos(Math.toRadians(vAngle + angleSpan)) * Math
                        .cos(Math.toRadians(hAngle)));
                float y3 = (float) (r * UNIT_SIZE
                        * Math.cos(Math.toRadians(vAngle + angleSpan)) * Math
                        .sin(Math.toRadians(hAngle)));
                float z3 = (float) (r * UNIT_SIZE * Math.sin(Math
                        .toRadians(vAngle + angleSpan)));

                // 将计算出来的XYZ坐标加入存放顶点坐标的ArrayList
                alVertix.add(x1);
                alVertix.add(y1);
                alVertix.add(z1);
                alVertix.add(x3);
                alVertix.add(y3);
                alVertix.add(z3);
                alVertix.add(x0);
                alVertix.add(y0);
                alVertix.add(z0);

                alVertix.add(x1);
                alVertix.add(y1);
                alVertix.add(z1);
                alVertix.add(x2);
                alVertix.add(y2);
                alVertix.add(z2);
                alVertix.add(x3);
                alVertix.add(y3);
                alVertix.add(z3);
            }
        }
        vCount = alVertix.size() / 3;// 顶点的数量为坐标值数量的1/3，因为一个顶点有3个坐标

        // 将alVertix中的坐标值转存到一个float数组中
        float vertices[] = new float[vCount * 3];
        for (int i = 0; i < alVertix.size(); i++) {
            vertices[i] = alVertix.get(i);
        }

        // 创建顶点坐标数据缓冲
        // vertices.length*4是因为一个整数四个字节
        mVertexBuffer = GLBufferUtils.buildFloatBuffer(vertices);
        // 特别提示：由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer
        // 转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题
    }
}
