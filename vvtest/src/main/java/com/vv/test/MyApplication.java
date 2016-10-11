package com.vv.test;

import android.app.Application;

import com.vv.map.provider.local.TileProviderOptions;

/**
 * Created by wiesen on 16-7-12.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        TileProviderOptions.getInstance().InitMapIndex();
//        CatchLogHandler.getInstance().init(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        TileProviderOptions.getInstance().clearTile();
        System.gc();
    }
}
