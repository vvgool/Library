package com.vv.map.interf;


import com.vv.map.deu.DeuMapOptions;
import com.vv.map.location.GeoPoint;

/**
 * Copyright  @2016 珞珈德毅. All rights reserved.
 * <p>Description: 地图操作接口</p>
 * @ClassName      IDeuMapControl
 * @Package        com.ljdy.interf
 * @Author         vvgool
 * @Time           2016/4/13
 */
public interface IDeuMapControl {
    /**
     * 地图放大
     */
    void zoomIn();

    /**
     * 地图缩小
     */
    void zoomOut();

    /**
     * 获取当前地图层级
     * @return 层级值
     */
    int getZoomLevel();

    /**
     * 设置当前层级
     * @param zoomLevel 层级值
     */
    void setZoomLevel(int zoomLevel);

    /**
     * 获取地图中心点经纬度信息
     * @return GeoPoint 对象
     */
    GeoPoint getMapCenter();

    /**
     * 设置地图中心点经纬度信息
     * @param geoPoint GeoPoint对象
     */
    void setMapCenter(GeoPoint geoPoint);

    /**
     * 获取地图设置对象
     * @return DeuMapOptions对象
     */
    DeuMapOptions getMapOptions();

//    /**
//     * 离线地图离线包设置类对象
//     * @return  TileProviderOptions 对象
//     */
//    TileProviderOptions getTileProviderOptions();
}
