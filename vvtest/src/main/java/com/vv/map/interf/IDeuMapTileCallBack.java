package com.vv.map.interf;

/**
 * Copyright  @2016 珞珈德毅. All rights reserved.
 * <p>Description: 地图初始化完成与否的接口</p>
 * @ClassName      IDeuMapTileCallBack
 * @Package        com.ljdy.interf
 * @Author         vvgool
 * @Time           2016/4/13
 */
public interface IDeuMapTileCallBack {
    /**
     * 地图瓦片初始化完成
     */
    void TilePrePareOk();

    /**
     * 瓦片初始化失败
     */
    void TilePrePareError();
}
