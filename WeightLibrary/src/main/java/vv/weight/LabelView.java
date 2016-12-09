package vv.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;


/**
 * Created by wiesen on 16-12-9.
 */

public class LabelView extends LinearLayout {
    private static final String TAG = "LabelView";

    private int mLabelBackgroundColor = Color.RED;
    private int mLabelTextColor = Color.WHITE;
    private int mLabelTextSize = 14;
    private int mLabelViewWidth = dp2px(14);
    private int mLabelPadding = dp2px(2);
    private String mLabelText = "9.5折";
    private Paint mLabelTextPaint = new Paint();

    public LabelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //使其调用onDraw方法
        setWillNotDraw(false);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LabelView);
        mLabelBackgroundColor = typedArray.getColor(R.styleable.LabelView_label_background,Color.RED);
        mLabelTextSize = typedArray.getDimensionPixelSize(R.styleable.LabelView_label_text_size,14);
        mLabelTextColor = typedArray.getColor(R.styleable.LabelView_label_text_color,Color.WHITE);
        mLabelPadding = typedArray.getDimensionPixelSize(R.styleable.LabelView_label_text_padding,dp2px(2));
        mLabelText = typedArray.getString(R.styleable.LabelView_label_text);
        typedArray.recycle();
    }


    public void setLabelString(String labelString){
        this.mLabelText = labelString;
        invalidate();
    }

    public void setLabelPaddingByDp(int padding){
        this.mLabelPadding = dp2px(padding);
        invalidate();
    }

    public void setLabelBackgroundColor(int color){
        this.mLabelBackgroundColor = color;
        invalidate();
    }

    public void setLabelTextSize(int size){
        this.mLabelTextSize = size;
        invalidate();
    }

    public void setLabelTextColor(int color){
        this.mLabelTextColor = color;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (TextUtils.isEmpty(mLabelText))
            return;
        calculateLabelViewWidth();
        int width = getWidth();
        int height = getHeight();
        if (mLabelViewWidth > width || mLabelViewWidth >height){
            return;
        }
        drawLabelBackground(width,canvas);
        drawLabelText(width,canvas);

    }

    //绘制label背景
    private void drawLabelBackground(int width, Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(mLabelBackgroundColor);
        Path path = new Path();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL);
        path.moveTo(width - mLabelViewWidth,0);
        path.lineTo(width, mLabelViewWidth);
        path.lineTo(width,0);
        path.close();
        canvas.drawPath(path,paint);
    }
    //绘制Label标签文字
    private void drawLabelText(int width, Canvas canvas){
        Path path = new Path();
        path.moveTo(width - mLabelViewWidth,0);
        path.lineTo(width, mLabelViewWidth);
        float textLength = mLabelTextPaint.measureText(mLabelText);
        int distance = (int) distance2Point(width - mLabelViewWidth,0,width, mLabelViewWidth);
        Log.d(TAG,"textLength:" + textLength + "\t distance:" + distance);
        canvas.drawTextOnPath(mLabelText,path,(distance - textLength)/2,-(mLabelPadding*3/2),mLabelTextPaint);
    }

    private void calculateLabelViewWidth(){
        mLabelTextPaint.setColor(mLabelTextColor);
        mLabelTextPaint.setTextSize(mLabelTextSize);
        mLabelTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mLabelTextPaint.setAntiAlias(true);
        mLabelTextPaint.setDither(true);
        //计算文字长度
        float textLength = mLabelTextPaint.measureText(mLabelText);
        Paint.FontMetrics fontMetrics = mLabelTextPaint.getFontMetrics();
        //计算文字高度
        float textHeight = fontMetrics.descent + Math.abs(fontMetrics.ascent) + fontMetrics.leading;
        //计算标签三角形顶点到最长边距离
        float pointToLine = textLength/2 + mLabelPadding*2 +textHeight;
        //计算三角形直角边长度
        mLabelViewWidth = (int) Math.sqrt(pointToLine*pointToLine*2);
    }


    private int dp2px(float value){
        return (int) (getResources().getDisplayMetrics().density * value + 0.5f);
    }

    //计算两点间距离
    private double distance2Point(float x1,float y1,float x2,float y2){
        float disX = Math.abs(x1 - x2);
        float disY = Math.abs(y1 - y2);
        return Math.sqrt(disX*disX + disY*disY);
    }
}
