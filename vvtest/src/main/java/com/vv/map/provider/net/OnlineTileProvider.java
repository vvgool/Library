package com.vv.map.provider.net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import com.vv.map.interf.IDeuMapTileHandlerCallBack;
import com.vv.map.interf.IDeuMapTileStatusCallBack;
import com.vv.map.location.DeuMapConstants;
import com.vv.map.provider.BaseDeuMapTileProvider;
import com.vv.map.provider.LoopRunnable;
import com.vv.map.provider.RowTile;
import com.vv.map.provider.cache.BitMapCache;
import com.vv.map.provider.wmts.WMTSInfo;
import com.vv.map.provider.wmts.WMTSUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * Copyright  @2016 珞珈德毅. All rights reserved.
 * <p>Description: 在线获取瓦片数据</p>
 * @ClassName      OnlineTileProvider
 * @Package        com.ljdy.provider.net
 * @Author         vvgool
 * @Time           2016/4/14
 */
public class OnlineTileProvider extends BaseDeuMapTileProvider {
    private int mType;

    /**
     * 构造方法
     * @param bitMapCache BitMapCache对象
     * @param iDeuMapTileHandlerCallBack IDeuMapTileHandlerCallBack接口
     */
    public OnlineTileProvider(BitMapCache bitMapCache, IDeuMapTileHandlerCallBack iDeuMapTileHandlerCallBack){
        super(bitMapCache,iDeuMapTileHandlerCallBack);
    }

    /**
     * 获取在线瓦片获取的LoopRunnable对象
     * @return LoopRunnable 对象
     */
    @Override
    public LoopRunnable getTileBitmap() {
        return new onLineMapLoop(mBitmapCache,iDeuMapTileHandlerCallBack);
    }

    /**
     * 设置地图类型 矢量or影像
     * @param type 地图类型
     */
    @Override
    public void setDeuMapType(int type) {
        this.mType = type;
    }

    private Bitmap getFileBitmap(String tile) throws FileNotFoundException{
        String[] tileStr = tile.split("_");
        String path = DeuMapConstants.onlinePath + File.separator+mType+File.separator+tileStr[0] +File.separator + tileStr[1] + File.separator + tileStr[2]+".deu";
        File file = new File(path);
        if (!file.exists()){
            return null;
        }
        FileInputStream  fileInputStream = new FileInputStream(file);
        Bitmap bitmap = null;
        if (fileInputStream != null){
            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inPreferredConfig = Bitmap.Config.RGB_565;
            bitmap = BitmapFactory.decodeStream(fileInputStream,null,opt);
        }
        return bitmap;
    }



    private class onLineMapLoop extends LoopRunnable implements IDeuMapTileStatusCallBack {

        private WMTSInfo wmtsInfo = new WMTSInfo();
        private boolean isWmts = false;

        public onLineMapLoop(BitMapCache cache, IDeuMapTileHandlerCallBack deuMapTileHandlerCallBack) {
            super(cache, deuMapTileHandlerCallBack);
        }

        @Override
        public void handlerTiles(List<String> rowTiles, BitMapCache mBitmapCache, IDeuMapTileHandlerCallBack iDeuMapTileHandlerCallBack) {
            isWmts= WMTSUtils.connectToMapServerGetInfo(wmtsInfo);
//            if (isWmts) {
                for (String rowTileString : rowTiles) {
                    if (handlerTileBitmap(rowTileString, mBitmapCache)) {
                        ++mSucceedCount;
                    } else {
                        ++mFailedCount;
                    }
                }
                if (mSucceedCount > 0) {
                    iDeuMapTileHandlerCallBack.onTileCompletedSuccess();
                }
//            }
            iDeuMapTileHandlerCallBack.onTileHashCodeEnd();
        }

        @Override
        public boolean handlerTileBitmap(String rowTile, BitMapCache bitMapCache) {
            Bitmap bitmap = null;
            try {
                bitmap = getFileBitmap(rowTile);
                if (bitmap != null){
                    bitMapCache.addBitmapToCache(rowTile.toString(),bitmap);
                    return true;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return false;
            }
            if (bitmap == null && isWmts){
                String[] tileStr = rowTile.split("_");
                return ApiClient.sendToServer(new RowTile(Integer.valueOf(tileStr[1]), Integer.valueOf(tileStr[2]),Integer.valueOf(tileStr[0])),mType,wmtsInfo,this);
            }
            return false;
        }

        @Override
        public void getTileBitmapFailed(RowTile tile) {

        }

        @Override
        public void getTileBitmapSucceed(RowTile tile, Bitmap bitmap) {
            mBitmapCache.addBitmapToCache(tile.toString(),bitmap);
        }
    }

}
