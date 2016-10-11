package com.vv.video;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Created by wiesen on 16-7-21.
 */
public class VideoPlayerSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG="VideoPlayerView";
    private final MediaPlayer mMediaPlayer;
    private SurfaceHolder mSurfaceHolder;
    private Context mContext;
    private int mPosition=0;


    public VideoPlayerSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        mMediaPlayer = new MediaPlayer();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setDisplay(mSurfaceHolder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stop();
    }

    public void play(String path){
        mMediaPlayer.reset();
        try {
            mMediaPlayer.setDataSource(path);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pause(){
        if (mMediaPlayer.isPlaying()){
            mMediaPlayer.pause();
        }else {
            mMediaPlayer.start();
        }
    }

    public void setPosition(int position){
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.seekTo(position);
        }
    }

    public void stop(){
        if (mMediaPlayer.isPlaying()){
            mPosition=mMediaPlayer.getCurrentPosition();
            mMediaPlayer.stop();
        }
        mMediaPlayer.release();
    }

}
