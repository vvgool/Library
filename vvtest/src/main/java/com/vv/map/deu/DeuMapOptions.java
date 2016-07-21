package com.vv.map.deu;


import com.vv.map.provider.cache.BitMapCache;
import com.vv.map.location.DeuMapConstants;
import com.vv.map.location.GeoPoint;

/**
 * Copyright  @2016 珞珈德毅. All rights reserved.
 * <p>Description: 地图控制设置的类</p>
 * @ClassName      DeuMapOptions
 * @Package        com.ljdy.maps
 * @Author         vvgool
 * @Time           2016/4/12
 */
public class DeuMapOptions {
    private boolean mIsCompassEnabled = false;//设置是否允许指南针，默认不允许。

    private boolean mIsOverlookingGesturesEnabled = false;//设置是否允许俯视手势，默认不允许

    private boolean mIsRotateGesturesEnabled = false;//设置是否允许旋转手势，默认不允许

    private boolean mIsScaleControlEnabled = true;//  设置是否显示比例尺控件

    private boolean mIsScrollGesturesEnabled = true;//设置是否允许拖拽手势，默认允许

    private boolean mIsZoomControlsEnabled = true;//设置是否显示缩放控件

    private boolean mIsZoomGesturesEnabled = true;// 设置是否允许缩放手势

    private DeuMapStatus mDeuMapStatus;//设置地图初始化时的地图状态， 默认地图中心点为北京天安门，缩放级别为 12.0

    private int mMapType = DeuMapConstants.MAP_TYPE_SATELLITE;//当前显示地图类型

    private boolean mDeuMapEnabled = true;//设置地图是否显示

    private boolean mIsOffOnline = true;//设置地图是否离线

//    private DeuMap mDeuMap;

    private BitMapCache mBitMapCache;

    /**
     * 构造方法
     * @param deuMap DeuMap 对象
     * @param bitMapCache
     */
    public DeuMapOptions(/*DeuMap deuMap, BitMapCache bitMapCache*/) {
//        this.mDeuMap = deuMap;
//        this.mBitMapCache = bitMapCache;
        mDeuMapStatus = new DeuMapStatus(new GeoPoint(DeuMapConstants.Default_Longitude,
                DeuMapConstants.Default_Latitude), DeuMapConstants.Default_Zoom);
    }

    /**
     * 设置是否允许指南针[暂时未涉及]
     * @param enabled true 允许 false 不允许
     */
    public void compassEnabled(boolean enabled) {
        this.mIsCompassEnabled = enabled;
    }

    /**
     * 获取是否允许指南针设置值
     * @return true 允许 false 不允许
     */
    public boolean getCompassEnabled() {
        return this.mIsCompassEnabled;
    }

    /**
     * 设置当前地图中心状态
     * @param status  DeuMapStatus 对象
     */
    public void setMapStatus(DeuMapStatus status) {
        this.mDeuMapStatus = status;
    }

    /**
     * 获取当前地图中心状态对象
     * @return DeuMapStatus 对象
     */
    public DeuMapStatus getMapStatus() {
        return this.mDeuMapStatus;
    }

    /**
     * 设置地图的类型:影像图  矢量图
     * @param mapType DeuMapConstants.MAP_TYPE_NORMAL 影像图 DeuMapConstants.MAP_TYPE_SATELLITE 矢量图
     */
//    public void setMapType(int mapType) {
//        this.mMapType = mapType;
//        mDeuMap.getMapTileProviderManager().setDeuMapType(mapType);
//        mDeuMap.getMapCanvas().TilePrePareOk();
//    }

    /**
     * 获取地图的类型
     * @return 获取当前地图类型
     */
    public int getMapType() {
        return this.mMapType;
    }

    /**
     * 设置是否允许俯视手势[目前未涉及]
     * @param enabled true 允许 false 不允许
     */
    public void overlookingGesturesEnabled(boolean enabled) {
        this.mIsOverlookingGesturesEnabled = enabled;
    }

    /**
     * 获取设置是否允许俯视手势
     * @return true 允许 false 不允许
     */
    public boolean getOverlookingGesturesEnabled() {
        return this.mIsOverlookingGesturesEnabled;
    }

    /**
     * 设置是否允许旋转手势
     * @param enabled true 允许 false 不允许
     */
    public void rotateGesturesEnabled(boolean enabled) {
        this.mIsRotateGesturesEnabled = enabled;
    }

    /**
     * 获取是否允许旋转手势
     * @return true 允许 false　不允许
     */
    public boolean getRotateGesturesEnabled() {
        return this.mIsRotateGesturesEnabled;
    }

    /**
     * 设置是否显示比例尺控件
     * @param enabled　true 允许 false 不允许
     */
    public void scaleControlEnabled(boolean enabled) {
        this.mIsScaleControlEnabled = enabled;
    }

    /**
     * 获取是否显示比例尺
     * @return true 允许 false 不允许
     */
    public boolean getScaleControlEnabled() {
        return this.mIsScaleControlEnabled;
    }

    /**
     * 设置是否允许拖拽手势
     * @param enabled true 允许 false 不允许
     */
    public void scrollGesturesEnabled(boolean enabled) {
        this.mIsScrollGesturesEnabled = enabled;
    }

    /**
     * 获取是否允许拖拽
     * @return true 允许 false 不允许
     */
    public boolean getScrollGesturesEnabled() {
        return this.mIsScrollGesturesEnabled;
    }

    /**
     * 设置是否显示缩放控件
     * @param enabled true 显示 false 不显示
     */
    public void zoomControlsEnabled(boolean enabled) {
        this.mIsZoomControlsEnabled = enabled;
    }

    /**
     * 获取是否显示缩放控件
     * @return true 显示 false 不显示
     */
    public boolean getZoomControlsEnabled() {
        return this.mIsZoomControlsEnabled;
    }

    /**
     * 设置是否允许缩放手势
     * @param enabled true 允许 false 不允许
     */
    public void zoomGesturesEnabled(boolean enabled) {
        this.mIsZoomGesturesEnabled = enabled;
    }

    /**
     * 获取是否允许缩放手势
     * @return true 允许 false 不允许
     */
    public boolean getZoomGesturesEnabled() {
        return this.mIsZoomGesturesEnabled;
    }

    /**
     * 设置地图是否显示
     * @param enabled true 显示 false 不显示
     */
    public void DeuMapEnabled(boolean enabled) {
        this.mDeuMapEnabled = enabled;
//        mDeuMap.getMapCanvas().TilePrePareOk();
    }

    /**
     * 获取是否显示地图
     * @return true 显示 false 不显示
     */
    public boolean getDeuMapEnabled() {
        return this.mDeuMapEnabled;
    }

    /**
     * 设置地图是否离线
     * @param enabled true 离线 false 在线
     */
    public void setDeuMapOffOnline(boolean enabled) {
        this.mIsOffOnline = enabled;
        mBitMapCache.clearCache();
//        mDeuMap.getMapCanvas().TilePrePareOk();
    }

    /**
     * 获取当前是否为离线模式
     * @return true 离线 false 在线
     */
    public boolean getDeuMapOffOnline() {
        return this.mIsOffOnline;
    }

}
