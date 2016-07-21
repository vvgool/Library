package com.vv.map.deu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.vv.map.interf.IDeuMapControl;
import com.vv.map.interf.IDeuMapTileCallBack;
import com.vv.map.location.DeuMapConstants;
import com.vv.map.location.GeoPoint;
import com.vv.map.provider.cache.BitMapCache;
import com.vv.map.provider.local.TileProviderOptions;
import com.vv.test.MyGestureListener;
import com.vv.map.utils.DeuGeoUtils;

/**
 * Created by vvgool on 16-6-14.
 */
public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback ,IDeuMapControl {
    private static final int TileSize = DeuMapConstants.TILE_SIZE;
    private final Context mContext;
    private SurfaceHolder mSurfaceHolder;
    private LayerDrawable mLayerDrawable;

    private DeuMapStatus mDeuMapStatus;
    private DeuMapOptions mDeuMapOptions;
    private MapLoader mMapLoader;
    private BitMapCache mBitmapCache;

    private Point mCenterPoint;
    private Rect mLimitRect;
    private float mMoveX = 0;
    private float mMoveY = 0;
    private float mStartX = 0;
    private float mStartY = 0;
    private float mScale = 1;
    private Matrix mMatrix ;
    private float mStartDistance;

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        mContext = context;
        mMatrix = new Matrix();
        mLimitRect = new Rect();
        mDeuMapStatus = new DeuMapStatus(
                new GeoPoint(DeuMapConstants.Default_Longitude
                        ,DeuMapConstants.Default_Latitude),DeuMapConstants.Default_Zoom);
        freshMapCenterPoint();
        mDeuMapOptions = new DeuMapOptions();
        mBitmapCache = new BitMapCache(context);
//        mMapLoader = new MapLoader(this,mBitmapCache);
        TileProviderOptions.getInstance().setOnDecodeDeuMapIndexListener(mapTileCallBack);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mLayerDrawable = new LayerDrawable();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mLayerDrawable.onLayerSizeChanged(width,height);
        mStartX = -(mLayerDrawable.getWidth()-width)/2;
        mStartY =-(mLayerDrawable.getHeight()-height)/2;
        loadMap();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mLayerDrawable.onDestroy();
    }

    @Override
    public void zoomIn() {

    }

    @Override
    public void zoomOut() {

    }

    @Override
    public int getZoomLevel() {
        return mDeuMapStatus.getCurrentZoom();
    }

    @Override
    public void setZoomLevel(int zoomLevel) {
        mDeuMapStatus.setZoomLevel(zoomLevel);
        freshMapCenterPoint();
    }

    @Override
    public GeoPoint getMapCenter() {
        return mDeuMapStatus.getGeoPoint();
    }

    @Override
    public void setMapCenter(GeoPoint geoPoint) {
        freshMapCenterPoint();
        mDeuMapStatus.setGeoPoint(geoPoint);
    }

    @Override
    public DeuMapOptions getMapOptions() {
        return mDeuMapOptions;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mTouchEvent.onTouchEvent(event);
        freshMap();
        return true;
    }

    public void freshMap() {
        Canvas canvas = mSurfaceHolder.lockCanvas();
        mMatrix.setScale(mScale,mScale,getWidth()/2,getHeight()/2);
        canvas.concat(mMatrix);
        canvas.drawBitmap(mLayerDrawable.getBitmap(),mStartX + mMoveX,mStartY + mMoveY,null);
        mSurfaceHolder.unlockCanvasAndPost(canvas);
    }

    public LayerDrawable getLayerDrawable(){
        return mLayerDrawable;
    }

    public MapDrawable getMapDrawable(){
        return mLayerDrawable.getMapDrawable();
    }

    public void loadMap(){
        mMapLoader.loadCurrentMap(mCenterPoint,mDeuMapOptions.getDeuMapEnabled());
    }

    public Bitmap getBitmap(){
        return mLayerDrawable.getBitmap();
    }

    /**
     * 更新画布的偏移量
     *
     * @param moveX X方向移动的距离
     * @param moveY y方向移动的距离
     */
    public void freshOffsetPix(float moveX, float moveY) {
        this.mMoveX = moveX;
        this.mMoveY = moveY;
    }

    /**
     *
     * 根据地图中心经纬度更新中心坐标
     */
    private void freshMapCenterPoint() {
        mCenterPoint = DeuGeoUtils.LatLongToPixelXY(mDeuMapStatus.getGeoPoint().getLatitude()
                , mDeuMapStatus.getGeoPoint().getLongitude(), mDeuMapStatus.getCurrentZoom(), mCenterPoint);

        Point startP=DeuGeoUtils.LatLongToPixelXY(DeuMapConstants.MaxLatitude
                , DeuMapConstants.MinLongitude, mDeuMapStatus.getCurrentZoom(), null);
        Point endP=DeuGeoUtils.LatLongToPixelXY(DeuMapConstants.MinLatitude
                , DeuMapConstants.MaxLongitude, mDeuMapStatus.getCurrentZoom(), null);
        mLimitRect.set(startP.x,startP.y,endP.x,endP.y);
    }


    /**
     * 更新地图中心点经纬度
     */
    private void freshMapGeoLatLon() {
        mDeuMapStatus.setGeoPoint(DeuGeoUtils.PixelXYToLatLong(mCenterPoint.x, mCenterPoint.y, mDeuMapStatus.getCurrentZoom(), null));
    }

    /**
     * 计算两点间的距离
     * @param event 事件响应的对象
     * @return float
     */
    private float getSpaceByTwoPoint(MotionEvent event) throws Exception{
        float disX = event.getX(0) - event.getX(1);
        float disY = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(disX * disX + disY * disY);
    }

    private IDeuMapTileCallBack mapTileCallBack = new IDeuMapTileCallBack() {
        @Override
        public void TilePrePareOk() {
            loadMap();
        }

        @Override
        public void TilePrePareError() {

        }
    };

    private MyGestureListener mTouchEvent=new  MyGestureListener() {

        @Override
        public void onTouchClick(MotionEvent event) {

        }

        @Override
        public void onTouchMove(MotionEvent event) {
            mapTranslateGesture(event);
        }

        @Override
        public void onTouchTwoPointDown(MotionEvent event) {
            try {
                mStartDistance = getSpaceByTwoPoint(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onTouchTwoPointMove(MotionEvent event) {
            mapZoomScaleGesture(event);
        }

        @Override
        public void onTouchMorePointUp(MotionEvent event) {
            if (mScale > 1.5) {
                zoomIn();

            } else if (mScale < 0.5) {
                zoomOut();
            }
            mScale = 1;
        }

        /**
         * 手指缩放手势事件
         * @param event 事件响应的对象
         */
        private void mapZoomScaleGesture(MotionEvent event) {
            if (mDeuMapOptions.getZoomGesturesEnabled()) {
                try {
                    mScale *= getSpaceByTwoPoint(event) / mStartDistance;
                    mStartDistance = getSpaceByTwoPoint(event);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        /**
         * 手指移动事件
         * @param event 事件响应的对象
         */
        private void mapTranslateGesture(MotionEvent event) {
            if (mDeuMapOptions.getScrollGesturesEnabled()) {
                float moveX = event.getX() - mDownXPoint;
                float moveY = event.getY() - mDownYPoint;
                mCenterPoint.x -= moveX;
                mCenterPoint.y -= moveY;
                if (mCenterPoint.x > mLimitRect.right||
                        mCenterPoint.x<mLimitRect.left) {
                    mCenterPoint.x = mCenterPoint.x < mLimitRect.left? mLimitRect.left:mLimitRect.right;
                }else {
                    mMoveX += moveX;
                }

                if (mCenterPoint.y>mLimitRect.bottom||
                        mCenterPoint.y<mLimitRect.top){
                    mCenterPoint.y = mCenterPoint.y < mLimitRect.top? mLimitRect.top:mLimitRect.bottom;
                }else {
                    mMoveY += moveY;
                }
                freshMapGeoLatLon();
            }
            mDownXPoint = event.getX();
            mDownYPoint = event.getY();

            if (Math.abs(mMoveX) > TileSize || Math.abs(mMoveY) > TileSize) {
                loadMap();
            }
        }
    };


}
