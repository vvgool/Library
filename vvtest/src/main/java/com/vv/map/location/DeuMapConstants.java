package com.vv.map.location;

import com.vv.map.utils.FileUtils;

import java.io.File;

/**
 * Copyright  @2016 珞珈德毅. All rights reserved.
 * <p>Description: 地图基础配置类</p>
 * @ClassName      DeuMapConstants
 * @Package        com.ljdy.util
 * @Author         vvgool
 * @Time           2016/4/18
 */
public class DeuMapConstants {
    public static final double EarthRadius = 6378137;
    public static final double MinLatitude = -85.05112878;
    public static final double MaxLatitude = 85.05112878;
    public static final double MinLongitude = -180;
    public static final double MaxLongitude = 180;
    public final static int TILE_SIZE = 256;//单个瓦片的大小
    public final static int MAX_ZOOM = 18;//瓦片地图最大层级
    public final static int MIN_ZOOM = 1;//瓦片地图最小层级
    public final static double Default_Longitude =/*116.403875*/114.31667;
    public final static double Default_Latitude =/*39.915168*/30.51667;
    public final static int Default_Zoom = 17;//初始层级
    public final static int MAP_TYPE_NORMAL = 0;//瓦片地图的类型
    public final static int MAP_TYPE_SATELLITE = MAP_TYPE_NORMAL + 1;

    public static final String DeuMobileDefaultPath = FileUtils.getStoragePath() + File.separator + "DeuMobile";
    //DeuMobile工程目录
    public static final String offOnlinePath = FileUtils.getStoragePath() + File.separator + "DeuMobile" + File.separator + "Map";
    //瓦片地图缓存目录
    public static final String onlinePath = offOnlinePath + File.separator + "Cache";
    //地图服务器地址
    public static final String ROOT_URL = "http://192.168.200.92:6080/arcgis/rest/services/deumobile/deumobile/MapServer";

}
