package com.vv.map.provider.local;

import android.os.AsyncTask;


import com.vv.map.interf.IDeuMapTileCallBack;
import com.vv.map.location.DeuMapConstants;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright  @2016 珞珈德毅. All rights reserved.
 * <p>Description: 离线地图设置地图包路径以及初始化类</p>
 * @ClassName      TileProviderOptions
 * @Package        com.ljdy.provider.local
 * @Author         vvgool
 * @Time           2016/4/12
 */
public class TileProviderOptions {
    private static TileProviderOptions mTileProviderOptions;
    private Map<Integer, String> mDeuMapPath;
    private Map<Integer, DecodeDeuMap> mDecodeDeuMaps;
    private IDeuMapTileCallBack mDeuMapTileCallBack;
    private final String normal = "normal.dat";
    private final String satellite = "map.dat";

    /**
     * 单例模式获取TileProviderOptions 对象
     * @return TileProviderOptions 对象
     */
    public static TileProviderOptions getInstance(){

        return InstanceHolder.mTileProviderOptions;
    }

    private static class InstanceHolder{
        private static final TileProviderOptions mTileProviderOptions = new TileProviderOptions();
    }

    /**
     * 构造函数
     */
    private TileProviderOptions() {
        mDeuMapPath = new HashMap<Integer, String>();
        mDecodeDeuMaps = new HashMap<Integer, DecodeDeuMap>();
        addOffOnlineMapType(DeuMapConstants.MAP_TYPE_NORMAL, normal);
        addOffOnlineMapType(DeuMapConstants.MAP_TYPE_SATELLITE, satellite);
    }

    /**
     * 添加离线模式下的地图类型
     * @param type 类型
     * @param path 地图文件路径
     */
    public void addOffOnlineMapType(int type, String path) {
        try {
            String p_path = DeuMapConstants.offOnlinePath+ File.separator + path;
            this.mDeuMapPath.put(type, p_path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据地图类型获取该类型地图文件路径
     * @param type 地图类型
     * @return 地图文件路径
     */
    public String getMapPathByType(int type) {
        if (mDeuMapPath.containsKey(type)) {
            return this.mDeuMapPath.get(type);
        }
        return null;
    }

    /**
     * 初始化地图索引
     */
    public void InitMapIndex() {
        new DecodeDeuMapIndex().execute();
    }

    /**
     * 设置地图索引完成监听接口
     * @param callBack IDeuMapTileCallBack 接口
     */
    public void setOnDecodeDeuMapIndexListener(IDeuMapTileCallBack callBack) {
        this.mDeuMapTileCallBack = callBack;
    }

    /**
     * 通过地图类型获取地图解析对象
     * @param type 地图类型
     * @return DecodeDeuMap 对象
     */
    public DecodeDeuMap getDecodeDeuMapByType(int type) {
        if (mDecodeDeuMaps.containsKey(type)) {
            return mDecodeDeuMaps.get(type);
        }
        return null;
    }

    /**
     * 清除地图类型集合
     */
    public void clearTile() {
        this.mDeuMapPath.clear();
        for (int type : mDecodeDeuMaps.keySet()) {
            try {
                mDecodeDeuMaps.get(type).closeFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.mDecodeDeuMaps.clear();
    }

    /**
     * 异步解析自定义瓦片索引
     */
   private class DecodeDeuMapIndex extends AsyncTask<Void, Void, Boolean> {


        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                for (int type : mDeuMapPath.keySet()) {
                    mDecodeDeuMaps.put(type, new DecodeDeuMap(mDeuMapPath.get(type)));
                }
            } catch (Exception e) {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (mDeuMapTileCallBack != null) {
                if (aBoolean) {
                    mDeuMapTileCallBack.TilePrePareOk();
                } else {
                    mDeuMapTileCallBack.TilePrePareError();
                }
            }
        }
    }
}
