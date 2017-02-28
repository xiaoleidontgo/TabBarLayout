package me.view.tabbar.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import lyh.library.cm.component.tabbar.TabBarLayout;
import lyh.library.cm.component.tabbar.TabUiHandler;
import lyh.library.ultra.R;

/**
 * Created by lyh on 2016/7/11.
 * 经典tab样式默认实现
 */
public class ClassicTab extends LinearLayout implements TabUiHandler {


    //icon && title
    private ImageView mIvIcon;
    private TextView mTvTitle;
    //drawable && title && color
    private String mTitle;
    private Drawable mNormalIcon;
    private Drawable mSelectedIcon;
    private int mNormalColor = Color.BLACK;
    private int mSelectedColor = Color.BLUE;
//    private RippleDrawable mRippleDrawable;


    public ClassicTab(Context context) {
        this(context, null);
    }

    public ClassicTab(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClassicTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.layout_tab_classic, this);
        mIvIcon = (ImageView) findViewById(R.id.iv_icon);
        mTvTitle = (TextView) findViewById(R.id.tv_title);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TabBarLayout);
        //icon
        mNormalIcon = context.getResources().getDrawable(ta.getResourceId(R.styleable.TabBarLayout_normal_icon, R.drawable.empty));
        mSelectedIcon = context.getResources().getDrawable(ta.getResourceId(R.styleable.TabBarLayout_selected_icon, R.drawable.empty));
        //title
        mTitle = ta.getString(R.styleable.TabBarLayout_title_text);
        if (mTitle == null)
            mTitle = context.getResources().getString(ta.getResourceId(R.styleable.TabBarLayout_title_text, R.string.app_name));
        //color
        mNormalColor = context.getResources().getColor(ta.getResourceId(R.styleable.TabBarLayout_normal_title_color, R.color.colorGray));
        mSelectedColor = context.getResources().getColor(ta.getResourceId(R.styleable.TabBarLayout_selected_title_color, R.color.colorGray));
        ta.recycle();

        mTvTitle.setText(mTitle);
        mTvTitle.setTextSize(12);
        mTvTitle.setTextColor(mNormalColor);
        mTvTitle.setGravity(Gravity.CENTER);
        mIvIcon.setImageDrawable(mNormalIcon);


    }


    /**
     * 用代码添加tab时调用
     *
     * @param context
     * @param title
     * @param normalIcon
     * @param setSelectedIcon
     * @param normalColor
     * @param selectedColor
     */
    public ClassicTab(Context context, String title,
                      Drawable normalIcon, Drawable setSelectedIcon,
                      int normalColor, int selectedColor) {
        super(context);
        this.mTitle = title;
        this.mNormalIcon = normalIcon;
        this.mSelectedIcon = setSelectedIcon;
        this.mNormalColor = normalColor;
        this.mSelectedColor = selectedColor;
//        android:foreground="?attr/selectableItemBackground"
//        int selectableItemBackground = android.support.v7.appcompat.R.attr.selectableItemBackground;
//        this.setForeground(getResources().getDrawable(selectableItemBackground, null));
//        mRippleDrawable = new RippleDrawable();
//        this.setBackground(mRippleDrawable);
        initItemView();
    }


    private void initItemView() {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.topMargin = 5;
        mIvIcon = new ImageView(getContext());
        mTvTitle = new TextView(getContext());
        mTvTitle.setText(mTitle);
        mTvTitle.setTextSize(12);
        mTvTitle.setTextColor(mNormalColor);
        mTvTitle.setGravity(Gravity.CENTER);
        mIvIcon.setImageDrawable(mNormalIcon);
        this.setOrientation(VERTICAL);
        this.addView(mIvIcon, lp);
        this.addView(mTvTitle);
        this.setPadding(5, 5, 5, 5);
        this.setGravity(Gravity.CENTER);
    }


    @Override
    public void onSelected(TabBarLayout parent, int startTab, int endTab) {
        if (mIvIcon != null && mTvTitle != null) {
            mIvIcon.setImageDrawable(mSelectedIcon);
            mTvTitle.setTextColor(mSelectedColor);
//            mRippleDrawable.start();
        } else {
            throw new NullPointerException("the itemView cant be null");
        }
    }

    @Override
    public void onReSelected(TabBarLayout parent, int startTab, int endTab) {

    }

    @Override
    public void onPressed() {
        if (mIvIcon != null && mTvTitle != null) {
            mIvIcon.setImageDrawable(mSelectedIcon);
        } else {
            throw new NullPointerException("the itemView cant be null");
        }
    }

    @Override
    public void unSelected(TabBarLayout parent, int startTab, int endTab) {
        if (mIvIcon != null && mTvTitle != null) {
            mIvIcon.setImageDrawable(mNormalIcon);
            mTvTitle.setTextColor(mNormalColor);
//            mRippleDrawable.stop();
        } else {
            throw new NullPointerException("the itemView cant be null");
        }
    }

    @Override
    public boolean hasPage() {
        return true;
    }

}
