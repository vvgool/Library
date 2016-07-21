package com.vv.map.provider.net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;


import com.vv.map.https.HttpClientOptions;
import com.vv.map.interf.IDeuMapTileStatusCallBack;
import com.vv.map.location.DeuMapConstants;
import com.vv.map.provider.RowTile;
import com.vv.map.provider.wmts.WMTSInfo;
import com.vv.map.provider.wmts.WMTSUtils;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Copyright  @2016 珞珈德毅. All rights reserved.
 * <p>Description: 联网api</p>
 * @ClassName ApiClient
 * @Package com.ljdy.provider.net
 * @Author S_sxl
 * @Time 2016/4/18
 */
public class ApiClient {
    private static String TAG="TileClient";
    /**
     * 联网加载瓦片
     * @param tile
     * @param type
     * @param iDeuMapTileStatusCallBack
     */
    public static boolean sendToServer(final RowTile tile, final int type, WMTSInfo info, final IDeuMapTileStatusCallBack iDeuMapTileStatusCallBack){
                HttpClientOptions options = HttpClientOptions.getInstance();
                String url= WMTSUtils.getTileUrl(options.getServiceUrl(),info,tile.zoom,tile.x,tile.y);
                byte[] response=options.doGet(url/*, new IDeuMapHttpCallBack() {
                    @Override
                    public void httpConnectSucceed(byte[] response) {
                        writeByteToCaChe(response,type,tile);
                        Bitmap  bitmap = BitmapFactory.decodeByteArray(response, 0, response.length);
                        Log.i(TAG,response.length+"" + "string:"+new String(response).toString());
                        iDeuMapTileStatusCallBack.getTileBitmapSucceed(tile,bitmap);
                    }

                    @Override
                    public void httpConnectFailed(StringBuffer strErr) {
                        iDeuMapTileStatusCallBack.getTileBitmapFailed(tile);
                    }
                }*/);
            if (response!= null){
                writeByteToCaChe(response,type,tile);
                Bitmap bitmap = BitmapFactory.decodeByteArray(response, 0, response.length);
                Log.i(TAG,response.length+"" + "string:"+new String(response).toString());
                iDeuMapTileStatusCallBack.getTileBitmapSucceed(tile,bitmap);
                return true;
            }else {
                iDeuMapTileStatusCallBack.getTileBitmapFailed(tile);
            }
        return false;
    }

    /**
     * 联网获取的瓦片缓存到手机内存中
     * @param img
     * @param type
     * @param tile
     */
    private static void writeByteToCaChe(byte[] img, int type, RowTile tile) {
        try {
            String path = DeuMapConstants.onlinePath + File.separator +type+File.separator +tile.zoom + File.separator + tile.x;
            File dirFile = new File(path);
            if(!dirFile.exists()){
                dirFile.mkdirs();
            }
            FileOutputStream fops = new FileOutputStream(path+ File.separator + tile.y + ".deu");
            fops.write(img);
            fops.flush();
            fops.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
