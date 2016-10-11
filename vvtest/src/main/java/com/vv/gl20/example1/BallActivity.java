package com.vv.gl20.example1;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.vv.test.R;

/**
 * Created by wiesen on 16-7-26.
 */
public class BallActivity extends AppCompatActivity {
    private static final String TAG="BALL";
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        Log.i(TAG,"2222");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"1111");
        setContentView(R.layout.activity_ball);
    }
}
