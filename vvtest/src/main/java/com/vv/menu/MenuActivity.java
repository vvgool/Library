package com.vv.menu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.vv.test.R;

import java.util.ArrayList;

import vv.weight.menu.MenuBar;

/**
 * Created by wiesen on 16-7-26.
 */
public class MenuActivity extends AppCompatActivity {
    private MenuBar mMenuBar;
    private ArrayList<String> mDate = new ArrayList<>();
    private MenuBarAdapter mAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mMenuBar = (MenuBar) findViewById(R.id.mb_title);
        for (int i = 0;i<4;i++){
            mDate.add("ti"+i);
        }
        mAdapter = new MenuBarAdapter(this,mDate);
        mMenuBar.setAdapter(mAdapter);
    }



}
