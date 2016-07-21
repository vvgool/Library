package com.vv.map.provider;


import com.vv.map.deu.DeuMapOptions;
import com.vv.map.interf.IDeuMapTileHandlerCallBack;
import com.vv.map.location.DeuMapConstants;
import com.vv.map.provider.cache.BitMapCache;
import com.vv.map.provider.local.LocalStorageProvider;
import com.vv.map.provider.net.OnlineTileProvider;
import com.vv.map.utils.FileUtils;

/**
 * Copyright  @2016 珞珈德毅. All rights reserved.
 * <p>Description: 瓦片数据管理类</p>
 * @ClassName      DeuMapTileProviderManager
 * @Package        com.ljdy.provider
 * @Author         vvgool
 * @Time           2016/4/14
 */
public class DeuMapTileProviderManager extends BaseDeuMapTileProvider{
    private DeuMapOptions mDeuMapOptions;
    private LocalStorageProvider mLocalStorageProvider;
    private OnlineTileProvider mOnlineTileProvider;

    /**
     * 构造方法
     * @param deuMapOptions DeuMapOptions对象
     * @param bitMapCache BitMapCache对象
     * @param iDeuMapTileHandlerCallBack  IDeuMapTileStatusCallBack 接口对象
     */
    public DeuMapTileProviderManager(DeuMapOptions deuMapOptions, BitMapCache bitMapCache,
                                     IDeuMapTileHandlerCallBack iDeuMapTileHandlerCallBack) {
        super(bitMapCache,iDeuMapTileHandlerCallBack);
        this.mDeuMapOptions = deuMapOptions;
        initFileDir();
        mLocalStorageProvider = new LocalStorageProvider(bitMapCache,iDeuMapTileHandlerCallBack);
        mOnlineTileProvider = new OnlineTileProvider(bitMapCache,iDeuMapTileHandlerCallBack);
        setDeuMapType(mDeuMapOptions.getMapType());
    }

    /**
     * 获取瓦片的LoopRunnable对象
     * @return LoopRunnable 对象
     */
    @Override
    public LoopRunnable getTileBitmap() {
        LoopRunnable runnable = null;
        if (!mDeuMapOptions.getDeuMapOffOnline()) {
            runnable = mOnlineTileProvider.getTileBitmap();
        }else{
            runnable = mLocalStorageProvider.getTileBitmap();
        }
        return runnable;
    }

    /**
     * 设置地图显示类型
     * @param type
     */
    public void setDeuMapType(int type) {
        mBitmapCache.clearCache();
        mLocalStorageProvider.setDeuMapType(type);
        mOnlineTileProvider.setDeuMapType(type);
    }

    /**
     * 初始化在线离线文件夹
     */
    private void initFileDir(){
        FileUtils.createFile(DeuMapConstants.offOnlinePath);
        FileUtils.createFile(DeuMapConstants.onlinePath);
    }
}
