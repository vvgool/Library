package com.vv.map.provider.cache;



import com.vv.map.deu.MapViewGL;
import com.vv.map.interf.IDeuMapTileHandlerCallBack;
import com.vv.map.location.DeuMapConstants;
import com.vv.map.provider.DeuMapTileProviderManager;
import com.vv.map.provider.LoopRunnable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Copyright  @2016 珞珈德毅. All rights reserved.
 * <p>Description: 地图瓦片加载类</p>
 * @ClassName      DeuTileLoadHandler
 * @Package        com.ljdy.provider.cache
 * @Author         vvgool
 * @Time           2016/4/12
 */
public class DeuTileLoadHandler implements IDeuMapTileHandlerCallBack {
    private final int mTileSize = DeuMapConstants.TILE_SIZE;
    private final BitMapCache mImageCache;
    private MapViewGL mDeuMap;
    private DeuMapTileProviderManager mMapTileProviderManager;
    private ExecutorService mExecutor;
    private List<LoopRunnable> mRunnableList ;
    private boolean mThreadIsRunning = false;
    /**
     * 构造方法
     * @param bitMapCache 瓦片缓存对象
     * @param deuMap DeuMap 对象
     */
    public DeuTileLoadHandler(BitMapCache bitMapCache, MapViewGL deuMap) {
        this.mDeuMap = deuMap;
        this.mImageCache = bitMapCache;
        mMapTileProviderManager = new DeuMapTileProviderManager( deuMap.getMapOptions(),mImageCache,this);
        mExecutor = Executors.newSingleThreadExecutor();
        mRunnableList = new ArrayList<LoopRunnable>();
    }

    /**
     * 加载地图瓦片
     * @param haveNoBitmapList
     * @throws IOException
     */
    public void loadMapBitmap(List<String> haveNoBitmapList) throws IOException {
        synchronized (mRunnableList) {
            LoopRunnable runnable = mMapTileProviderManager.getTileBitmap();
            runnable.addTile(haveNoBitmapList);
            mRunnableList.add(runnable);
            if (!mThreadIsRunning) {
                if (mRunnableList.size() > 0) {
                    mExecutor.submit(runnable);
                    mRunnableList.clear();
                    mThreadIsRunning = true;
                }
            }
        }
    }

    /**
     * 加载下一数据
     */
    private void threadNext(){
        synchronized (mRunnableList) {
            mThreadIsRunning = false;
            if (!mThreadIsRunning) {
                if (mRunnableList.size() > 0) {
                    LoopRunnable runnable = mRunnableList.get(mRunnableList.size() - 1);
                    mExecutor.submit(runnable);
                    mRunnableList.clear();
                    mThreadIsRunning = true;
                }
            }
        }
    }

    /**
     * 获取地图在线or离线管理类对象
     * @return DeuMapTileProviderManager 对象
     */
    public DeuMapTileProviderManager getMapTileProviderManager() {
        return this.mMapTileProviderManager;
    }



    @Override
    public void onTileCompletedSuccess() {
        mDeuMap.loadMap();
    }

    @Override
    public void onTileCompletedFail() {

    }

    @Override
    public void onTileHashCodeEnd() {
        threadNext();
    }
}
