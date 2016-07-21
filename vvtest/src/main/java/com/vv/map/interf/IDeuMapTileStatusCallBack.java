package com.vv.map.interf;

import android.graphics.Bitmap;

import com.vv.map.provider.RowTile;


/**
 * Copyright  @2016 珞珈德毅. All rights reserved.
 * <p>Description: 地图瓦片获取状态信息接口</p>
 * @ClassName      IDeuMapTileStatusCallBack
 * @Package        com.ljdy.interf
 * @Author         vvgool
 * @Time           2016/4/13
 */
public interface IDeuMapTileStatusCallBack {
    /**
     * 获取瓦片失败
     * @param tile 瓦片信息
     */
    void getTileBitmapFailed(RowTile tile);

    /**
     * 获取瓦片成功
     * @param tile 瓦片信息
     * @param bitmap 瓦片图片数据
     */
    void getTileBitmapSucceed(RowTile tile, Bitmap bitmap);
}
