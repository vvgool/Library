package vv.library.systemUtils;

import android.content.Context;
import android.util.TypedValue;

/**
 *@Desc {dp/dip/ps/px转换}
 *@Author Wiesen Wang
 *@Email vv_gool@163.com
 *@Time  16-6-12
 */
public class DimenUtils {

    /**
     * Converte dp to pixels
     *
     * @param context
     * @param dpValue
     * @return pixels
     */
    public static int dip2px(Context context, float dpValue) {
        return (int) Math.ceil(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics()));
    }

    /**
     * Converting pixels to dp
     *
     * @param context
     * @param pxValue
     * @return dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * Converte dp to pixels
     *
     * @param context
     * @param dpValue
     * @return pixels
     */
    public static float dp2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) Math.ceil(dpValue * scale);
    }
    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param context
     * @param pxValue
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param context
     * @param spValue
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
