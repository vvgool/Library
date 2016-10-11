package com.vv.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.vv.test.R;

import java.util.ArrayList;
import java.util.List;

import vv.weight.menu.MenuBaseAdapter;

/**
 * Created by wiesen on 16-7-26.
 */
public class MenuBarAdapter extends MenuBaseAdapter {
    private Context mContext;
    private List<String> mDate ;
    public MenuBarAdapter(Context context,ArrayList<String> list) {
        mContext = context;
        mDate = list;
    }

    @Override
    public int getSize() {
        return mDate.size();
    }

    @Override
    public View getContentView(int position) {
        TextView textView = (TextView) LayoutInflater.from(mContext).inflate(R.layout.text,null);
        textView.setText(mDate.get(position));
        return textView;
    }
}
