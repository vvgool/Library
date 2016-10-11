package com.vv.video;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.vv.test.R;

import java.io.File;

/**
 * Created by wiesen on 16-7-21.
 */
public class VideoActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    VideoPlayerSurfaceView mVideoPlayer;
    Button mPlay;
    Button mPause;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermission();
        setContentView(R.layout.activity_video);
        mVideoPlayer = (VideoPlayerSurfaceView) findViewById(R.id.vp_player);
        mPlay = (Button) findViewById(R.id.bt_play);
        mPause = (Button) findViewById(R.id.bt_pause);
        mPlay.setOnClickListener(this);
        mPause.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_play:
                File file = new File(Environment.getExternalStorageDirectory().getPath(),"video.flv");
                if(file.exists()) {
                    mVideoPlayer.play(file.getAbsolutePath());
                }
                break;
            case R.id.bt_pause:
                mVideoPlayer.pause();
                break;
        }
    }

    public void checkPermission(){
        int i = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (i != PackageManager.PERMISSION_GRANTED){
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}
