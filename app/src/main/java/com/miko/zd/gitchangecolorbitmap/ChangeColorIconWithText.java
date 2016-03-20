package com.miko.zd.gitchangecolorbitmap;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by zd on 2016/2/17.
 */
public class ChangeColorIconWithText extends View {

    private int mColor = 0xFF45C01A;
    private Bitmap mIconBitmap;
    private String mText = "易迅";

    private Canvas mCanvas;
    private Bitmap mBitmap;
    private Paint mPaint;
    private Paint mTextPaint;
    private float mAlpha = 1.0f;

    private Rect mIconRect;
    private Rect mTextBound;

    private int mTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
            12, getResources().getDisplayMetrics());


    public ChangeColorIconWithText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.ChangeColorIconWithText);

        int n = a.getIndexCount();

        for (int i = 0; i < n; i++) {

            int attr = a.getIndex(i);

            switch (attr) {
                case R.styleable.ChangeColorIconWithText_Icon:
                    BitmapDrawable mdrawable = (BitmapDrawable) a.getDrawable(attr);
                    mIconBitmap = mdrawable.getBitmap();
                    break;
                case R.styleable.ChangeColorIconWithText_text:
                    mText = a.getString(attr);
                    break;
                case R.styleable.ChangeColorIconWithText_Color:
                    mColor = a.getColor(attr, 0xFF45C01A);
                    break;
                case R.styleable.ChangeColorIconWithText_text_size:
                    mTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                            12, getResources().getDisplayMetrics());
                    break;

            }
        }
        a.recycle();

        mTextBound = new Rect();
        mTextPaint = new Paint();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(0Xff555555);
        mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBound);

    }

    public ChangeColorIconWithText(Context context, AttributeSet attrs) {

        this(context, attrs, 0);
    }

    public ChangeColorIconWithText(Context context) {
        this(context, null);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int iconWidth = Math.min(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(),
                getMeasuredHeight() - getPaddingTop() - getPaddingBottom()
                        - mTextBound.height());

        int iconleft = (getMeasuredWidth() - iconWidth) / 2;

        int icontop = getMeasuredHeight() / 2 - (iconWidth + mTextBound.height()) / 2;

        mIconRect = new Rect(iconleft, icontop, iconleft + iconWidth, icontop + iconWidth);
    }


    @Override
    protected void onDraw(Canvas canvas) {

        int alpha = (int) Math.ceil(255 * mAlpha);

        canvas.drawBitmap(mIconBitmap, null, mIconRect, null);
        setupTargetBitmap(alpha);
        canvas.drawBitmap(mBitmap, 0, 0, null);

        drawSourceText(canvas, alpha);
        setupTargetText(canvas, alpha);

    }

    private void drawSourceText(Canvas canvas, int alpha) {

        mTextPaint.setColor(0xff333333);
        mTextPaint.setAlpha(255 - alpha);
        int x = (getMeasuredWidth() - mTextBound.width()) / 2;
        int y = mIconRect.bottom + mTextBound.height() / 2;
        canvas.drawText(mText, x, y, mTextPaint);
    }

    private void setupTargetBitmap(int alpha) {

        mBitmap = Bitmap.createBitmap(getMeasuredWidth(),
                getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mPaint = new Paint();
        mPaint.setColor(mColor);
        mPaint.setAlpha(alpha);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mCanvas.drawRect(mIconRect, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mPaint.setAlpha(255);
        /*
        * 用自定义的paint绘制出图形
        * */
        mCanvas.drawBitmap(mIconBitmap, null, mIconRect, mPaint);


    }

    public void setupTargetText(Canvas canvas, int alpha) {

        mTextPaint.setColor(mColor);
        mTextPaint.setAlpha(alpha);
        int x = (getMeasuredWidth() - mTextBound.width()) / 2;
        int y = mIconRect.bottom + mTextBound.height() / 2;
        canvas.drawText(mText, x, y, mTextPaint);
    }


    /*
    * setting the alpha of the icon
    * */
    public void setIconAlpha(float alpha) {
        this.mAlpha = alpha;
        invalidateView();
    }

    /*
    *Toggle of change
    * */
    public void ChangeColorToggle() {
        if (this.mAlpha == 1.0f)
            setIconAlpha(0);
        else
            setIconAlpha(1.0f);
    }

    /*
    * Change Bitmap to transport
    * */
    public void ChangeToTp() {
        setIconAlpha(0);
    }
    /*
    * Change Bitmap to opacity
    * */

    public void ChangeToOp(){
        setIconAlpha(1.0f);
    }

    private static final String INSTANCE_STATUS = "instance_status";
    private static final String STATUS_ALPHA = "status_alpha";

    @Override
    protected Parcelable onSaveInstanceState() {

        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATUS, super.onSaveInstanceState());
        bundle.putFloat(STATUS_ALPHA, mAlpha);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {

        if (state instanceof Bundle) {

            Bundle bundle = (Bundle) state;
            mAlpha = bundle.getFloat(STATUS_ALPHA);
            Parcelable parcelable = bundle.getParcelable(INSTANCE_STATUS);
            super.onRestoreInstanceState(parcelable);
            return;
        }
    }

    /**
     * 重绘
     */
    private void invalidateView() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            invalidate();
        } else {
            postInvalidate();
        }
    }


}
