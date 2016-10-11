package vv.weight.menu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import vv.weight.R;

/**
 * Created by wiesen on 16-7-26.
 */
public class MenuBar extends ViewGroup {
    private static final String TAG="menuBar";
    private static final int HORIZONTAL = 0;
    private static final int VERTICAL =1;
    private int mOrientation = HORIZONTAL;
    private MenuBaseAdapter mMenuBaseAdapter;
    private int mIntervalSize;
    private int mIntervalColor;

    public MenuBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MenuBar);
        mIntervalSize = typedArray.getDimensionPixelSize(R.styleable.MenuBar_ChildIntervalSize,1);
        mIntervalColor = typedArray.getColor(R.styleable.MenuBar_ChildIntervalColor,Color.BLACK);
        mOrientation = typedArray.getInt(R.styleable.MenuBar_Orientation,HORIZONTAL);
        typedArray.recycle();
        setBackgroundColor(mIntervalColor);
    }

    public void setOrientation(int orientation){
        if (orientation == HORIZONTAL || orientation == VERTICAL){
            mOrientation = orientation;
            requestLayout();
        }
    }

    public void setAdapter(MenuBaseAdapter adapter){
        mMenuBaseAdapter = adapter;
        requestLayout();
    }




    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        removeAllViews();
        if (mMenuBaseAdapter == null){
            return;
        }
        int childCount = mMenuBaseAdapter.getSize();
        int childWidth = (getWidth() - mIntervalSize*(childCount-1))/childCount;
        int childHeight = (getHeight() - mIntervalSize *(childCount-1))/childCount;

        int left = l;
        int top = t;
        for (int i = 0;i<childCount;i++){
            View view= mMenuBaseAdapter.getContentView(i);
            if (mOrientation == HORIZONTAL){
                view.layout(left,t,left + childWidth,b);
                left += childWidth + mIntervalSize;
            }else {
                view.layout(l,top,r,top + childHeight);
                top += childHeight + mIntervalSize;
            }
            addView(view,i);
        }
    }
}
