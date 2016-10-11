package com.vv.media;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Surface;

import java.util.Arrays;

/**
 * Created by wiesen on 16-7-14.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class CameraUtils {
    private static final String TAG = "Camera2";
    private final Handler mHandler;
    private CameraManager mCameraManager;
    private CameraDevice mCameraDevice;
    private final Context mContext;
    private SurfaceLoading mSurfaceLoading;

    public CameraUtils(Context context) {
        mContext = context;
        mCameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        HandlerThread handlerThread = new HandlerThread("Camera2");
        handlerThread.start();
        mHandler = new Handler(handlerThread.getLooper());
    }


    public boolean openFrontCamera() {
            try {
                String[] useCameras = mCameraManager.getCameraIdList();
                for (String id : useCameras) {
                    if (id.equals("1")) {
                        openCamera(id);
                        return true;
                    }
                }
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        return false;
    }

    public boolean openBackCamera(){
        try {
            String[] useCameras = mCameraManager.getCameraIdList();
                for (String id : useCameras) {
                    if (id.equals("0")) {
                        openCamera(id);
                        return true;
                    }
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        return false;
    }




    private void openCamera(String id){
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.e(TAG,"need to join camera permission");
            return;
        }
        try {
            mCameraManager.openCamera(id, mCameraStateCallback, mHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }


    public void closeCamera(){
        if (mCameraDevice != null){
            mCameraDevice.close();
        }
    }

    private CaptureRequest.Builder mPreviewBuilder;
    private CameraDevice.StateCallback mCameraStateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(CameraDevice camera) {
            try {
                Log.i(TAG,"camera opened");
                mCameraDevice = camera;
                 if (mSurfaceLoading != null) {
                    mPreviewBuilder = camera.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
                    mPreviewBuilder.addTarget(mSurfaceLoading.onSurfaceLoading());
                    camera.createCaptureSession(Arrays.asList(mSurfaceLoading.onSurfaceLoading()), mCameraSessionCallback, mHandler);
                }
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onDisconnected(CameraDevice camera) {

        }

        @Override
        public void onError(CameraDevice camera, int error) {
            camera.close();
        }
    };

    private CameraCaptureSession.StateCallback mCameraSessionCallback = new CameraCaptureSession.StateCallback() {
        @Override
        public void onConfigured(CameraCaptureSession session) {
            Log.i(TAG,"camera session configured");
            try {
                mPreviewBuilder.set(CaptureRequest.CONTROL_AF_MODE,
                        CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
                mPreviewBuilder.set(CaptureRequest.CONTROL_AE_MODE,
                        CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
                session.setRepeatingRequest(mPreviewBuilder.build(),mCameraSessionCaptureCallback,mHandler);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onConfigureFailed(CameraCaptureSession session) {
            Log.i(TAG,"camera session configure failed");
        }
    };

    private CameraCaptureSession.CaptureCallback mCameraSessionCaptureCallback = new CameraCaptureSession.CaptureCallback() {
        @Override
        public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result) {
            super.onCaptureCompleted(session, request, result);
        }
    };

    public void onSurfaceLoading(SurfaceLoading surfaceLoading){
        mSurfaceLoading = surfaceLoading;
    }
    public interface SurfaceLoading{
        Surface onSurfaceLoading();
    }
}
