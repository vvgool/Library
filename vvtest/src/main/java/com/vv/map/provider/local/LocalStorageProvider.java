package com.vv.map.provider.local;

import android.graphics.Bitmap;


import com.vv.map.interf.IDeuMapTileHandlerCallBack;
import com.vv.map.provider.BaseDeuMapTileProvider;
import com.vv.map.provider.LoopRunnable;
import com.vv.map.provider.cache.BitMapCache;

import java.util.List;

/**
 * Copyright  @2016 珞珈德毅. All rights reserved.
 * <p>Description: 本地地图瓦片获取类</p>
 * @ClassName      LocalStorageProvider
 * @Package        com.ljdy.provider.local
 * @Author         vvgool
 * @Time           2016/4/12
 */
public class LocalStorageProvider extends BaseDeuMapTileProvider {
    private int mType;

    /**
     * 构造方法
     * @param bitMapCache BitMapCache对象
     * @param iDeuMapTileHandlerCallBack IDeuMapTileHandlerCallBack接口
     */
    public LocalStorageProvider(BitMapCache bitMapCache, IDeuMapTileHandlerCallBack iDeuMapTileHandlerCallBack) {
        super(bitMapCache,iDeuMapTileHandlerCallBack);
    }

    /**
     * 获取离线瓦片获取的LoopRunnable对象
     * @return LoopRunnable 对象
     */
    @Override
    public LoopRunnable getTileBitmap() {
        return new LocalMapLoop(mBitmapCache,iDeuMapTileHandlerCallBack);
    }
    /**
     * 设置地图显示类型
     * @param type
     */
    @Override
    public void setDeuMapType(int type) {
        this.mType = type;
    }


    private class LocalMapLoop extends LoopRunnable {

        public LocalMapLoop(BitMapCache cache, IDeuMapTileHandlerCallBack deuMapTileHandlerCallBack) {
            super(cache, deuMapTileHandlerCallBack);
        }

        @Override
        public void handlerTiles(List<String> rowTiles, BitMapCache mBitmapCache, IDeuMapTileHandlerCallBack iDeuMapTileHandlerCallBack) {
            super.handlerTiles(rowTiles,mBitmapCache,iDeuMapTileHandlerCallBack);
            resetCount();
        }

        @Override
        public boolean handlerTileBitmap(String rowTile, BitMapCache bitMapCache) {
            try {
                DecodeDeuMap decodeDeuMap = TileProviderOptions.getInstance().getDecodeDeuMapByType(mType);
                if (decodeDeuMap != null) {
                    Bitmap  bitmap = decodeDeuMap.queryBitmapByIndex(rowTile);
                    if (bitmap != null){
                        bitMapCache.addBitmapToCache(rowTile,bitmap);
                        return true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return false;
        }
    }

}
