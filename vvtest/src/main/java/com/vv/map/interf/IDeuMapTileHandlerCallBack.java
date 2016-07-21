package com.vv.map.interf;

/**
 * Copyright  @2016 珞珈德毅. All rights reserved.
 * <p>Description: 瓦片加载异步获取接口</p>
 * @ClassName      IDeuMapTileHandlerCallBack
 * @Package        com.ljdy.interf
 * @Author         vvgool
 * @Time           2016/4/26
 */
public interface IDeuMapTileHandlerCallBack {
    /**
     * 获取图片成功
     */
    void onTileCompletedSuccess();

    /**
     * 获取图片失败
     */
    void onTileCompletedFail();

    /**
     * 当前界面瓦片获取完成
     */
    void onTileHashCodeEnd();
}
