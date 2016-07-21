package com.vv.map.provider;


import com.vv.map.interf.IDeuMapTileHandlerCallBack;
import com.vv.map.provider.cache.BitMapCache;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright  @2016 珞珈德毅. All rights reserved.
 * <p>Description: 瓦片获取的异步对象</p>
 * @ClassName      LoopRunnable
 * @Package        com.ljdy.provider
 * @Author         vvgool
 * @Time           2016/4/26
 */
public abstract class LoopRunnable implements Runnable {
    private BitMapCache mBitmapCache;
    private IDeuMapTileHandlerCallBack iDeuMapTileHandlerCallBack;
    private List<String> mRowTiles = new ArrayList<String>();
    public int mSucceedCount = 0;
    public int mFailedCount = 0;

    public LoopRunnable(BitMapCache cache, IDeuMapTileHandlerCallBack deuMapTileHandlerCallBack){
        this.mBitmapCache = cache;
        this.iDeuMapTileHandlerCallBack = deuMapTileHandlerCallBack;
    }

    public void addTile(List<String> rowTiles){
        if (rowTiles != null){
            mRowTiles.clear();
            mRowTiles.addAll(rowTiles);
        }
    }

    public void handlerTiles(List<String> rowTiles, BitMapCache mBitmapCache, IDeuMapTileHandlerCallBack iDeuMapTileHandlerCallBack){
        for (String rowTileString : rowTiles){
            if(handlerTileBitmap(rowTileString,mBitmapCache)){
                ++mSucceedCount;
            }else {
                ++mFailedCount;
            }
        }
        if (mFailedCount == mRowTiles.size()){
            iDeuMapTileHandlerCallBack.onTileCompletedFail();
        }else {
            iDeuMapTileHandlerCallBack.onTileCompletedSuccess();
        }
        iDeuMapTileHandlerCallBack.onTileHashCodeEnd();
    }

    public abstract boolean handlerTileBitmap(String rowTile,BitMapCache bitMapCache);

    @Override
    public void run() {
        if (mRowTiles.size() >0) {
            handlerTiles(mRowTiles, mBitmapCache, iDeuMapTileHandlerCallBack);
        }
    }


    public void resetCount(){
        mSucceedCount = 0;
        mFailedCount = 0;
    }

}
