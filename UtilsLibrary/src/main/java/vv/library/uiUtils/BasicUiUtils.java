package vv.library.uiUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.RelativeLayout;

/**
 * Some method help do some UI works
 */
public class BasicUiUtils {



    /**
     * Expand a view which has already collapsed
     *
     * @param v
     */
    public static void expandViews(final View v) {
        v.measure(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        final int targtetHeight = v.getMeasuredHeight();

        v.getLayoutParams().height = 0;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? RelativeLayout.LayoutParams.WRAP_CONTENT
                        : (int) (targtetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (targtetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }


    /**
     * Collapse a view which has already expanded
     *
     * @param v
     */
    public static void collapseViews(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density) * 1);
        v.startAnimation(a);
    }

    /**
     * Pop a simple alertdialog which only shows title,message and "OK" button
     *
     * @param context
     * @param title
     * @param message
     */
    public static void popAlertDialog(Context context, String title, String message) {
        new AlertDialog.Builder(context)
                .setPositiveButton("OK", null)
                .setTitle(title)
                .setMessage(message).show();
    }

    /**
     * Pop a simple alertdialog which only shows title,message and "OK" button
     *
     * @param context
     * @param title
     * @param message
     */
    public static void popAlertDialog(Context context, int title, String message) {
        new AlertDialog.Builder(context)
                .setPositiveButton("OK", null)
                .setTitle(title)
                .setMessage(message).show();
    }




    /**
     * Set the activity to be full screen
     * @param activity
     * @param isFullScreen
     */
    public static void setActivityFullScreen(Activity activity,
                                             boolean isFullScreen) {
        if (isFullScreen) {
            activity.getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            activity.getWindow().clearFlags(
                    WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        } else {
            activity.getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            activity.getWindow().clearFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    /**
     * Set activity to be portrait
     * @param activity
     * @param isPortrait
     */
    public static void setActivityPortraitOrientation(Activity activity,
                                                      boolean isPortrait) {
        if (isPortrait) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        }
    }

    /**
     * Lock the screen orientation as the current state
     * @param activity
     */
    public static void lockScreenOrientation(Activity activity) {
        if (activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        if (activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    /**
     * Unlock the screen orientation
     * @param activity
     */
    public static void unlockScreenOrientation(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }

    /**
     * Get height of the device
     * @param context
     * @return
     */
    public static int getDeviceHeight(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        return manager.getDefaultDisplay().getHeight();
    }

    /**
     * Get height of status bar
     * @param activity
     * @return
     */
    public static int getStatusBarHeight(Activity activity) {
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        return frame.top;
    }

    /**
     * Get height of the top
     * @param activity
     * @return
     */
    public static int getTopBarHeight(Activity activity) {
        return activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
    }

}
