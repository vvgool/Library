package com.vv.map.provider.local;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import com.vv.map.utils.ByteUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright  @2016 珞珈德毅. All rights reserved.
 * <p>Description: 自定义瓦片地图解析</p>
 * @ClassName      DecodeDeuMap
 * @Package        com.ljdy.provider.local
 * @Author         vvgool
 * @Time           2016/4/12
 */
public class DecodeDeuMap {
    private byte[] jsonByte = new byte[1024 * 8];
    private RandomAccessFile rmf;
    private DeuMapLabs jsonTool;
    private Map<String, TileIndex> m_DecodeIndexMap;

    /**
     * 构造方法
     * @param path 自定义瓦片文件路径
     */
    public DecodeDeuMap(String path) {
        m_DecodeIndexMap = new HashMap<String, TileIndex>();
        getFileInstance(path);
    }

    /**
     * 初始化地图文件并获取解析描述信息
     */
    public void getFileInstance(String path) {
        try {
            File file = new File(path);
            if (!file.exists()){
                return;
            }
            rmf = new RandomAccessFile(file, "r");
            decodeJsonInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * json解析描述信息
     *
     * @throws IOException
     */
    public void decodeJsonInfo() throws IOException {
        rmf.seek(0);
        rmf.read(jsonByte, 0, jsonByte.length);
        String jsonString = new String(jsonByte);
        jsonTool = new DeuMapLabs();
        jsonTool.decodeJson(jsonString);
        decodeIndexBytes();
    }

    /**
     * 根据描述信息中索引段起始位置，索引段长度、瓦片数量等遍历解析瓦片索引，
     * 并保存至Map中key： 层_列_行 value： TileIndex
     *
     * @throws IOException
     */
    public void decodeIndexBytes() throws IOException {
        if (jsonTool == null) {
            return;
        }
        byte[] _indexByte = new byte[jsonTool.index_len];
        rmf.seek(jsonTool.index_start);
        rmf.read(_indexByte, 0, _indexByte.length);
        int length = _indexByte.length / 17;
        DeuMapIndex indexBean = new DeuMapIndex();
        for (int i = 0; i < length; i++) {
            byte[] index = ByteUtils.copyBytes(_indexByte, i * 17, 17);
            indexBean.decodeIndexBytes(index);
            m_DecodeIndexMap.put(indexBean.m_layer + "_" +
                            indexBean.m_row + "_" + indexBean.m_line,
                    new TileIndex(indexBean.m_picLoc, indexBean.m_picLen));
        }
    }

    /**
     * 根据key： 层_行_列 获取该瓦片在数据段中相对起始点及瓦片长度，读取文件中瓦片
     *
     * @param key 层_行_列
     * @return bitmap 瓦片Bitmap
     * @throws IOException
     */
    public Bitmap queryBitmapByIndex(String key) throws Exception {
        Bitmap bitmap = null;
        if (m_DecodeIndexMap != null && m_DecodeIndexMap.containsKey(key)) {
            TileIndex tileBean = m_DecodeIndexMap.get(key);
            byte[] temp = new byte[tileBean.iTileLen];
            rmf.seek(tileBean.iOffset + jsonTool.data_start);
            rmf.read(temp, 0, temp.length);
            bitmap = BitmapFactory.decodeByteArray(temp, 0, temp.length);
            temp = null;
        }
        return bitmap;
    }

    /**
     * 根据key： 层_行_列 获取该瓦片在数据段中相对起始点及瓦片长度，读取文件中瓦片
     *
     * @param key 层_行_列
     * @return io BufferedInputStream 对象
     * @throws IOException
     */
    public BufferedInputStream queryStreamByIndex(String key) throws IOException {
        if (m_DecodeIndexMap != null && m_DecodeIndexMap.containsKey(key)) {
            TileIndex tileBean = m_DecodeIndexMap.get(key);
            byte[] temp = new byte[tileBean.iTileLen];
            rmf.seek(tileBean.iOffset + jsonTool.data_start);
            rmf.read(temp, 0, temp.length);
            BufferedInputStream io = null;
            if (temp.length > 0) {
                io = new BufferedInputStream(new ByteArrayInputStream(temp), 4096);
                return io;
            }
        }
        return null;
    }

    /**
     * 获取瓦片描述信息
     * @return DeuMapLabs 对象
     */
    public DeuMapLabs getMapInfo() {
        return jsonTool;
    }

    /**
     * 清除瓦片索引信息
     */
    public void clearDecodeIndexMap() {
        m_DecodeIndexMap.clear();
    }

    /**
     * 获取瓦片索引集合
     * @return Map<String, TileIndex> 集合
     */
    public Map<String, TileIndex> getDecodeIndexMap() {
        return m_DecodeIndexMap;
    }

    public void setDecodeIndexMap(Map<String, TileIndex> indexMap) {
        this.m_DecodeIndexMap = indexMap;
    }

    /**
     * 关闭
     * @throws IOException
     */
    public void closeFile() throws IOException {
        rmf.close();
    }
}
