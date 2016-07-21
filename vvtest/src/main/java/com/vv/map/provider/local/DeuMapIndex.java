package com.vv.map.provider.local;


import com.vv.map.utils.ByteUtils;
import com.vv.map.utils.FileUtils;

/**
 * Copyright  @2016 珞珈德毅. All rights reserved.
 * <p>Description: 自定义瓦片地图索引段解析类</p>
 * @ClassName      DeuMapIndex
 * @Package        com.ljdy.provider.local
 * @Author         vvgool
 * @Time           2016/4/12
 */
public class DeuMapIndex {
    public String m_layer;//层号
    public String m_row;//列号
    public String m_line;//行号（图片名）
    public int m_picLen;//图片大小
    public int m_picLoc;//图片相对与数据段位置

    /**
     * 解析索引
     * @param bytes 将byte数组解析为瓦片索引信息
     */
    public void decodeIndexBytes(byte[] bytes) {
        byte[] layerName = ByteUtils.copyBytes(bytes, 0, 1);
        byte[] lineName = ByteUtils.copyBytes(bytes, 1, 4);
        byte[] picName = ByteUtils.copyBytes(bytes, 5, 4);
        byte[] picLoc = ByteUtils.copyBytes(bytes, 9, 4);
        byte[] picLen = ByteUtils.copyBytes(bytes, 13, 4);
        m_layer = String.valueOf(FileUtils.byteToInt(layerName));
        m_row = String.valueOf(FileUtils.byteToInt(lineName));
        m_line = String.valueOf(FileUtils.byteToInt(picName));
        m_picLen = FileUtils.byteToInt(picLen);
        m_picLoc = FileUtils.byteToInt(picLoc);
    }


}
