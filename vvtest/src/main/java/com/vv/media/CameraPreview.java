package com.vv.media;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraManager;
import android.media.ImageReader;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.TextureView;

/**
 * Created by wiesen on 16-7-14.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class CameraPreview extends TextureView implements TextureView.SurfaceTextureListener, CameraUtils.SurfaceLoading {
    private static final String TAG = "CameraPreview";
    private SurfaceTexture mSurfaceTexture;

    private CameraManager mCameraManager;
    private Context mContext;
    private String[] mCameraList;
    private ImageReader mImageReader;
    private CameraUtils mCameraUtils;


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CameraPreview(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mCameraUtils = new CameraUtils(context);
        setSurfaceTextureListener(this);
        mCameraUtils.onSurfaceLoading(this);
    }


    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
//        initCamera();
        mCameraUtils.openBackCamera();

    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    @Override
    public Surface onSurfaceLoading() {

        return new Surface(getSurfaceTexture());
    }
}
