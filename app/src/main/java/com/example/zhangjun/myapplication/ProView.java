package com.example.zhangjun.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zj on 2017/8/1.
 */

public class ProView extends View {
    private Paint mPaint;
    private Paint mLinPaint;
    private Paint mTextPaint;
    private int currIndex = 2;
    private int sum = 3;//设置有多少个笑脸
    private int bitIconWH;

    private String[] str = {"基本信息", "工作信息", "联系人"};
    //两边需要空格
    private int mar;
    private int textSize;

    public ProView(Context context) {
        super(context);
        init(context);
    }

    public ProView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ProView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap bitmaps = getNameToBitmap(R.mipmap.btn_yuandian_pre);
        // 取得想要缩放的matrix参数
        Bitmap newbm = BitmapUtils.zoomImg(bitmaps, bitIconWH, bitIconWH);
        bitIconWH = newbm.getWidth();
        sum = str.length;
        int width = getWidth() - mar * 2;

        //计算每条线的宽度
        int linWidth = (width - bitIconWH * sum) / (sum - 1) - 5;
        int left = mar;
        int btY = 0;
        int linY = 0;
        int bitH = newbm.getHeight();
        if (getHeight() > bitH) {//计算图片Y轴的位置
            btY = getHeight() / 2 - bitH / 2;
            linY = btY + bitH / 2;
        }
        for (int i = 1; i <= sum; i++) {
            if (i == currIndex) {
                newbm= getNameToBitmap(R.mipmap.btn_yuandian);
                mTextPaint.setColor(Color.parseColor("#FD8D95"));
            }else{
                newbm= getNameToBitmap(R.mipmap.btn_yuandian_pre);
                mTextPaint.setColor(Color.parseColor("#888888"));
            }
            canvas.drawBitmap(newbm, left, btY - bitIconWH / 2, mPaint);
            canvas.drawText(str[i - 1], left + bitIconWH / 2 - getTextWidth(str[i - 1]) / 2 + 5, btY + bitIconWH + 30, mTextPaint);
            if (i < sum) {//线比圆少一个
                canvas.drawLine(left + bitIconWH, btY, left + linWidth + bitIconWH, btY, mLinPaint);
            }
            left = left + linWidth + bitIconWH;

        }
    }

    private void initPaint() {
        mTextPaint = new Paint();
        mTextPaint.setColor(Color.parseColor("#888888"));
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(textSize);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(8);
        //抗锯齿
        mPaint.setAntiAlias(true);
        mLinPaint=new Paint();
        mLinPaint.setColor(Color.parseColor("#D1D1D1"));
        mLinPaint.setStrokeWidth(8);
        //抗锯齿
        mLinPaint.setAntiAlias(true);
    }

    private void init(Context context) {
        mar = (int) context.getResources().getDimension(R.dimen.par_ma);
        bitIconWH = (int) context.getResources().getDimension(R.dimen.big_width);
        textSize = (int) context.getResources().getDimension(R.dimen.text_size);
        initPaint();
    }

    private int getTextWidth(String str) {
        Paint pFont = new Paint();
        Rect rect = new Rect();
        pFont.setTextSize(textSize);
        pFont.getTextBounds(str, 0, str.length(), rect);
        return rect.width();
    }

    private Bitmap getNameToBitmap(@DrawableRes int rid) {
        return BitmapUtils.zoomImg(BitmapFactory.decodeResource(getResources(), rid), bitIconWH, bitIconWH);
    }
}
