package com.vv.medialib.picture;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Created by wiesen on 16-7-16.
 */
public class PictureEffect {

    /**
     * 怀旧风格图片效果
     * 算法： R = 0.393*r + 0.769*g + 0.189*b
     *       G = 0.349*r + 0.686*g + 0.168*b
     *       B = 0.272*r + 0.534*g + 0.131*b
     * @param sourceBitmap 需要添加效果的图片
     * @return 添加效果之后的图片
     */
    public static Bitmap createOldEffectStyle(Bitmap sourceBitmap){
        int bitmapWidth = sourceBitmap.getWidth();
        int bitmapHeight = sourceBitmap.getHeight();

        Bitmap finalBitmap = Bitmap.createBitmap(bitmapWidth,bitmapHeight, Bitmap.Config.RGB_565);

        int[] sourcePixels = new int[bitmapWidth*bitmapHeight];
        sourceBitmap.getPixels(sourcePixels,0,bitmapWidth,0,0,bitmapWidth,bitmapHeight);

        for (int i = 0; i<bitmapHeight;i++){
            for (int j = 0;j<bitmapWidth;j++){
                int currentPixel = sourcePixels[i*bitmapWidth+j];
                int sourceR = Color.red(currentPixel);
                int sourceG = Color.green(currentPixel);
                int sourceB = Color.blue(currentPixel);
                int finalR = (int) (0.393*sourceR + 0.769*sourceG + 0.189*sourceB);
                int finalG = (int) (0.349*sourceR + 0.686*sourceG + 0.168*sourceB);
                int finalB = (int) (0.272*sourceR + 0.534*sourceG + 0.131*sourceB);
                sourcePixels[i*bitmapWidth+j] = Color.argb(255,Math.min(finalR,255),Math.min(finalG,255),Math.min(finalB,255));
            }
        }
        finalBitmap.setPixels(sourcePixels,0,bitmapWidth,0,0,bitmapWidth,bitmapHeight);
        return finalBitmap;
    }

    /**
     * 模糊效果
     * 简单算法：将像素点周围八个点包括自身一共九个点的RGB值分别相加后平均，作为当前像素点的RGB值，即可实现效果。
     * E.r = (A.r + B.r + C.r + D.r + E.r + F.r + G.r + H.r + I.r) /9  // r表示的是E像素点RGB值的R值
     * @param sourceBitmap 需要添加效果的图片
     * @return 添加效果之后的图片
     */
    public static Bitmap createBlurEffectStyleByNormal(Bitmap sourceBitmap) {
        int bitmapWidth = sourceBitmap.getWidth();
        int bitmapHeight = sourceBitmap.getHeight();

        Bitmap finalBitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.RGB_565);

        for (int i = 1; i < bitmapWidth - 1; i++) {
            for (int j = 1; j < bitmapHeight - 1; j++) {
                int finalR = 0;
                int finalG = 0;
                int finalB = 0;
                for (int m = 0; m < 9; m++) {
                    int s = 0;
                    int p = 0;
                    switch (m) {
                        case 0:
                            s = i - 1;
                            p = j - 1;
                            break;
                        case 1:
                            s = i;
                            p = j - 1;
                            break;
                        case 2:
                            s = i + 1;
                            p = j - 1;
                            break;
                        case 3:
                            s = i + 1;
                            p = j;
                            break;
                        case 4:
                            s = i + 1;
                            p = j + 1;
                            break;
                        case 5:
                            s = i;
                            p = j + 1;
                            break;
                        case 6:
                            s = i - 1;
                            p = j + 1;
                            break;
                        case 7:
                            s = i - 1;
                            p = j;
                            break;
                        case 8:
                            s = i;
                            p = j;
                    }
                    int pixColor = sourceBitmap.getPixel(s, p);
                    finalR += Color.red(pixColor);
                    finalG += Color.green(pixColor);
                    finalB += Color.blue(pixColor);
                }
                finalR = (int) (finalG/9f);
                finalG = (int) (finalG/9f);
                finalB = (int) (finalB/9f);
                finalR = Math.min(255,Math.max(finalR,0));
                finalG = Math.min(255,Math.max(finalG,0));
                finalB = Math.min(255,Math.max(finalB,0));
                finalBitmap.setPixel(i,j,Color.argb(255,finalR,finalG,finalB));
            }
        }
        return finalBitmap;
    }

    /**
     * 模糊效果（高斯模糊）
     * int[] gauss = new int[] { 1, 2, 1, 2, 4, 2, 1, 2, 1 };
     * 算法是：将九个点的RGB值分别与高斯矩阵中的对应项相乘的和，然后再除以一个相应的值作为当前像素点的RGB值。
     * int delta = 16;
     * E.r =( A.r * gauss[0] + B.r * gauss[1] + C.r * gauss[2] + D.r * gauss[3] +
     * E.r * gauss[4] + F.r * gauss[5] + G.r * gauss[6] + H.r * gauss[7] + I.r * gauss[8]) / delta
     * @param sourceBitmap 需要添加效果的图片
     * @return 添加效果之后的图片
     */
    public static Bitmap createBlurEffectStyleByGauss(Bitmap sourceBitmap){
        int bitmapWidth = sourceBitmap.getWidth();
        int bitmapHeight = sourceBitmap.getHeight();

        Bitmap finalBitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.RGB_565);

        // 高斯矩阵
        int[] gauss = new int[] { 1, 2, 1, 2, 4, 2, 1, 2, 1 };
        int delta = 16; // 值越小图片会越亮，越大则越暗

        int[] sourcePixels = new int[bitmapWidth*bitmapHeight];
        sourceBitmap.getPixels(sourcePixels,0,bitmapWidth,0,0,bitmapWidth,bitmapHeight);

        for (int i = 1 ; i < bitmapHeight-1;i++ ){
            for (int j = 1; j<bitmapWidth-1;j++){
                int finalR = 0;
                int finalG = 0;
                int finalB = 0;
                int idx = 0;
                for (int m = -1; m <= 1; m++) {
                    for (int n = -1; n <= 1; n++) {
                        int pixColor = sourcePixels[(i + m) * bitmapWidth + j + n];

                        finalR += Color.red(pixColor) * gauss[idx];
                        finalG += Color.green(pixColor) * gauss[idx];
                        finalB += Color.blue(pixColor) * gauss[idx];
                        idx++;
                    }
                }
                finalR = Math.min(255,Math.max(finalR/delta,0));
                finalG = Math.min(255,Math.max(finalG/delta,0));
                finalB = Math.min(255,Math.max(finalB/delta,0));
                sourcePixels[i*bitmapWidth + j] = Color.argb(255,finalR,finalG,finalB);
            }
        }
        finalBitmap.setPixels(sourcePixels,0,bitmapWidth,0,0,bitmapWidth,bitmapHeight);
        return finalBitmap;
    }
}
