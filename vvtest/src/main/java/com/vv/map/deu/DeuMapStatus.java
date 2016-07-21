package com.vv.map.deu;


import com.vv.map.location.DeuMapConstants;
import com.vv.map.location.GeoPoint;

import java.io.Serializable;

/**
 * Copyright  @2016 珞珈德毅. All rights reserved.
 * <p>Description: 地图中心点状态类</p>
 * @ClassName      DeuMapStatus
 * @Package        com.ljdy.maps
 * @Author         vvgool
 * @Time           2016/4/12
 */
public class DeuMapStatus implements Serializable {
    int mZoom;
    GeoPoint mGeoPoint;//地图中心点经纬度

    /**
     * 构造方法
     * @param geoPoint 经纬度对象
     * @param zoom 当前层级
     */
    public DeuMapStatus(GeoPoint geoPoint, int zoom) {
        this.mZoom = zoom;
        this.mGeoPoint = geoPoint;
    }

    /**
     * 设置当前地图中心经纬度
     * @param geoPoint 经纬度对象
     */
    public void setGeoPoint(GeoPoint geoPoint) {
        this.mGeoPoint = geoPoint;
    }

    /**
     * 设置地图层级
     * @param zoomLevel 层级
     */
    public void setZoomLevel(int zoomLevel) {
        if (zoomLevel > DeuMapConstants.MAX_ZOOM || zoomLevel < DeuMapConstants.MIN_ZOOM) {
            return;
        }
        this.mZoom = zoomLevel;
    }

    /**
     * 获取经纬度对象
     * @return GeoPoint 对象
     */
    public GeoPoint getGeoPoint() {
        return this.mGeoPoint;
    }

    /**
     * 获取当前层级
     * @return 层级
     */
    public int getCurrentZoom() {
        return this.mZoom;
    }
}
