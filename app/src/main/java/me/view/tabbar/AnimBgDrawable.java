package me.view.tabbar;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.view.View;

import lyh.library.cm.utils.LocalDisplay;

/**
 * Created by lyh on 2016/8/26.
 * 作为TabBarLayout的背景实现Tab切换时的动画效果
 */
public class AnimBgDrawable extends Drawable implements TabAnimHandler, Animatable {

    private Paint mPaint;
    private boolean drawaRipple = false;

    public AnimBgDrawable(View mViewGroup) {
//        this.mViewGroup = mViewGroup;
        mPaint = new Paint();
    }

    /*******************************
     * // Drawable回调
     *******************************/
    @Override
    public void draw(Canvas canvas) {
        Rect rect = getBounds();

        //background
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(rect, mPaint);

        mPaint.setColor(Color.GRAY);
        mPaint.setStyle(Paint.Style.STROKE);
        //line
        canvas.drawLine(rect.left, rect.top, rect.right, rect.top, mPaint);

        if (drawaRipple) {
        }

    }

    @Override
    public void setBounds(Rect bounds) {
        super.setBounds(bounds);
    }

    /*********************
     * getIntrinsicWidth、getIntrinsicHeight主要是为了在View使用wrap_content的时候
     * ，提供一下尺寸，默认为-1可不是我们希望的。setBounds就是去设置下绘制的范围。
     *
     * @return
     */
    @Override
    public int getIntrinsicWidth() {
        return LocalDisplay.SCREEN_WIDTH_PIXELS;
    }

    @Override
    public int getIntrinsicHeight() {
        return LocalDisplay.SCREEN_HEIGHT_PIXELS;
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }

    /*******************************
     * // 动画相关回调
     *******************************/

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }

    /*******************************
     * // TabBar回调的用户操作
     *******************************/
    @Override
    public void onAnimStart(int startTab, int endTab) {
        drawaRipple = true;
        invalidateSelf();
        drawaRipple = false;
    }

    @Override
    public void onAnimStop() {

    }
}
