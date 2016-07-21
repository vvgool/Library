package com.vv.map.provider;


import com.vv.map.interf.IDeuMapTileHandlerCallBack;
import com.vv.map.provider.cache.BitMapCache;

/**
 * Copyright  @2016 珞珈德毅. All rights reserved.
 * <p>Description: 瓦片获取基类</p>
 * @ClassName      BaseDeuMapTileProvider
 * @Package        com.ljdy.provider
 * @Author         vvgool
 * @Time           2016/4/14
 */
public abstract class BaseDeuMapTileProvider {
    public BitMapCache mBitmapCache;
    public IDeuMapTileHandlerCallBack iDeuMapTileHandlerCallBack;

    public BaseDeuMapTileProvider(BitMapCache bitMapCache, IDeuMapTileHandlerCallBack deuMapTileHandlerCallBack){
        this.mBitmapCache = bitMapCache;
        this.iDeuMapTileHandlerCallBack = deuMapTileHandlerCallBack;
    }

    /**
     * 获取瓦片获取的Runnable对象
     * @return LoopRunnable 对象
     */
    public abstract LoopRunnable getTileBitmap();

    /**
     * 设置地图显示类型
     * @param type 地图类型
     */
    public abstract void setDeuMapType(int type);
}
