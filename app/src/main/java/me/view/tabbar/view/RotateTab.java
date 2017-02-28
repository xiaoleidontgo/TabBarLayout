package me.view.tabbar.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import lyh.library.cm.component.tabbar.TabBarLayout;
import lyh.library.cm.component.tabbar.TabUiHandler;
import lyh.library.cm.widget.animLayout.ITargetView;
import lyh.library.ultra.R;

/**
 * Created by lyh on 2016/8/13.
 * 旋转tab
 * 可以用作菜单旋转tab的默认实现，如果单个图片样式不满足，建议自己写好样式布局
 */
public class RotateTab extends LinearLayout implements TabUiHandler, ITargetView {

    public static final String TAG = "RotateTab";

    private ImageView imageView;
    private boolean isOpen = false;

    public RotateTab(Context context) {
        this(context, null);
    }

    public RotateTab(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RotateTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //init
        LayoutInflater.from(context).inflate(R.layout.layout_tab_rotate, this);
        imageView = (ImageView) findViewById(R.id.iv_icon_add);
    }

    @Override
    public void onSelected(TabBarLayout parent, int startTab, int endTab) {
        Log.d(TAG, "onSelected");
        if (!isOpen) {
            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_open_anim);
            animation.setFillAfter(true);
            imageView.startAnimation(animation);
            isOpen = true;
        }

    }

    @Override
    public void onReSelected(TabBarLayout parent, int startTab, int endTab) {
        Log.d(TAG, "onReSelected");
//        if (!isOpen) {
//            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_open_anim);
//            animation.setFillAfter(true);
//            imageView.startAnimation(animation);
//        } else {
//            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_close_anim);
//            animation.setFillAfter(true);
//            imageView.startAnimation(animation);
//        }
//        isOpen = !isOpen;
    }


    @Override
    public void onPressed() {
        Log.d(TAG, "onPressed");
    }


    @Override
    public void unSelected(TabBarLayout parent, int startTab, int endTab) {
        Log.d(TAG, "unSelected");
        if (isOpen) {
            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_close_anim);
            animation.setFillAfter(true);
            imageView.startAnimation(animation);
            isOpen = false;
        }
    }

    @Override
    public boolean hasPage() {
        return false;
    }

    //__________同步TargetView___________________下面的仅仅是为了环形菜单作为Target的实现，功能已经由上面的函数实现
    @Override
    public void onStartOpen() {
        if (!isOpen) {
            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_open_anim);
            animation.setFillAfter(true);
            imageView.startAnimation(animation);
            isOpen = true;
        }
    }

    @Override
    public void onStartClose() {
        if (isOpen) {
            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_close_anim);
            animation.setFillAfter(true);
            imageView.startAnimation(animation);
            isOpen = false;
        }
    }

    @Override
    public void onReached() {

    }
}
