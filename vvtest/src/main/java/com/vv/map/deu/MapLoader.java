package com.vv.map.deu;

import android.graphics.Bitmap;
import android.graphics.Point;

import com.vv.map.location.DeuMapConstants;
import com.vv.map.provider.RowTile;
import com.vv.map.provider.cache.BitMapCache;
import com.vv.map.provider.cache.DeuTileLoadHandler;
import com.vv.map.utils.DeuGeoUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wiesen on 16-7-11.
 */
public class MapLoader {
    private static final int TileSize = DeuMapConstants.TILE_SIZE;
    private List<String> mHaveNoTileList = new ArrayList<String>();
    private BitMapCache mBitmapCache;
    private MapViewGL mMapViewGL;
    private boolean mMapShowEnabled = true;
    private DeuTileLoadHandler mTileLoadHandler;

    public MapLoader(MapViewGL mapViewGL,BitMapCache bitMapCache){
        mTileLoadHandler = new DeuTileLoadHandler(bitMapCache,mapViewGL);
        mBitmapCache = bitMapCache;
        mMapViewGL = mapViewGL;
    }

    protected void loadCurrentMap(final Point mapPixelXY , boolean enabled){
        mMapShowEnabled = enabled;
        pixelXYToTileOffsetPixelXY(mapPixelXY);
        loading(mapPixelXY,mMapViewGL.getZoomLevel());
    }

    /**
     * 获取瓦片行列号
     *
     * @param point 瓦片的地图坐标
     * @return Point对象 point.x中为瓦片列号 point.y 中为瓦片行号
     */
    private Point getTileXY(Point point) {
        return DeuGeoUtils.PixelXYToTileXY(point.x, point.y, null);
    }

    /**
     * 屏幕中心坐标减去画布中心到画布左上角距离，
     * 获取到画布左上角地图屏幕坐标，将屏幕坐标
     * 转换为瓦片行列号。
     * 获取画布绘制的行列瓦片数量
     *
     * @param pixelXY 地图中心点地图坐标
     * @param zoomLevel 地图当前层级
     */
    private void loading(final Point pixelXY, final int zoomLevel){
        try {
            int canvasWidth = mMapViewGL.getLayerDrawable().getWidth();
            int canvasHeight = mMapViewGL.getLayerDrawable().getHeight();
            Point tileXY = getTileXY(pixelXY);
            int tileNumX = canvasWidth / TileSize;
            int tileNumY = canvasHeight / TileSize;
            RowTile mRowTile = new RowTile(tileXY.x-tileNumX/2, tileXY.y-tileNumY/2, zoomLevel);
            loadMap(tileNumX, tileNumY, mRowTile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取地图偏移量并更新MapCanvas中的数据
     *
     * @param pixelXY 瓦片中心点经纬度转换而来的地图坐标
     */
    private void pixelXYToTileOffsetPixelXY(final Point pixelXY) {
        this.mMapViewGL.freshOffsetPix(-pixelXY.x % TileSize,-pixelXY.y % TileSize);
    }

    private void loadMap(final int numX,final int numY,final RowTile rowTile){
        RowTile pTile = new RowTile(rowTile.x, rowTile.y, rowTile.zoom);
        mHaveNoTileList.clear();
        for (int i = 0; i < numX; i++) {
            pTile.y = rowTile.y;
            for (int j = 0; j < numY; j++) {
                Bitmap bitmap = mBitmapCache.getBitmapFromCache(pTile.toString());
                mMapViewGL.getMapDrawable().loadSingleDrawable(i,j,mMapShowEnabled?bitmap:null);
                if (bitmap == null){
                    mHaveNoTileList.add(pTile.toString());
                }
                pTile.y++;
            }
            pTile.x++;
        }
        mMapViewGL.freshTexture();
        if (mHaveNoTileList.size()>0){
            try {
                mTileLoadHandler.loadMapBitmap(mHaveNoTileList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
