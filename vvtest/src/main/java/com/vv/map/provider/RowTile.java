package com.vv.map.provider;

import java.io.Serializable;

/**
 * Copyright  @2016 珞珈德毅. All rights reserved.
 * <p>Description: 瓦片类 层级行列号信息</p>
 * @ClassName      RowTile
 * @Package        com.ljdy.maps
 * @Author         vvgool
 * @Time           2016/4/12
 */
public class RowTile implements Serializable {
    public int x;
    public int y;
    public int zoom;

    /**
     * 构造方法
     * @param x 列号
     * @param y 行号
     * @param zoom 层级号
     */
    public RowTile(int x, int y, int zoom) {
        this.x = x;
        this.y = y;
        this.zoom = zoom;
    }

    @Override
    public String toString() {
        return zoom + "_" + x + "_" + y;
    }
}
