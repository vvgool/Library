package com.vv.test;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.vv.gl.MyGlSurfaceView;

public class MainActivity extends AppCompatActivity {
    private MyGlSurfaceView myGlSurfaceView;
    private ImageView mImageView;
    private ImageView mImageThumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        mImageView = (ImageView) findViewById(R.id.iv_picture);
//        mImageThumbnail = (ImageView) findViewById(R.id.iv_picture_thumbnail);
//        mImageView.setImageBitmap(PictureEffect.createBlurEffectStyleByGauss
//                (BitmapFactory.decodeResource(getResources(),R.drawable.pic)));
//        Bitmap bitmap=ThumbnailUtils.extractThumbnail(PictureEffect.createOldEffectStyle
//                (BitmapFactory.decodeResource(getResources(),R.drawable.pic)),100,100);
//        mImageThumbnail.setImageBitmap(bitmap);
        initView();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void initView(){
        myGlSurfaceView = (MyGlSurfaceView) findViewById(R.id.sv_map);
        mImageView = (ImageView) findViewById(R.id.im_test);
        myGlSurfaceView.setImage(mImageView);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
