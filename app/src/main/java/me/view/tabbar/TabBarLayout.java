package me.view.tabbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by lyh on 2016/7/11.
 * <p/>
 * TabBar控件的核心类,处理与用户的交互
 * 实现可定制每个item的样式,抽象出item,回调出用户事件用业务逻辑调用
 * <p/>
 * 对单个tab的回调逻辑为：
 * 当当前tab选中时，按下此tab不会回调unSelected()，
 * 当选中其它tab时，会依次回调tab的状态
 * <p/>
 */
public class TabBarLayout extends LinearLayout {

    public static final String TAG = TabBarLayout.class.getSimpleName();

    /**
     * 事件回调对象
     */
    private TabHandler mHandler;
    /**
     * 当前选中的栏目
     */
    private int mCurrentItem = 0;
    /**
     * 默认的颜色值
     */
    private int mDefaultColor = Color.parseColor("#FFFFFF");

    /**
     * 手势识别
     */
    private GestureDetector mGestureDetector;

    /**
     * 表示可能存在的未选中的noPageTab标签
     */
    private int noPageTabSelectedIndex = -1;

    //    private Paint mPaint;
    private Drawable mBgDrawable;

    public TabBarLayout(Context context) {
        super(context);
        init(null);
    }

    public TabBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(null);
    }

    public TabBarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    /**
     * 初始化
     *
     * @param attrs
     */
    private void init(AttributeSet attrs) {

//        TypedArray ta = getContext().obtainStyledAttributes(attrs, null);
        //init...
//        ta.recycle();

        this.setMinimumHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                50,
                new DisplayMetrics()));

        this.setOrientation(HORIZONTAL);
        this.setGravity(Gravity.CENTER);
        this.setBackgroundColor(mDefaultColor);
        mBgDrawable = new AnimBgDrawable(this);
        this.setBackground(mBgDrawable);
        mGestureDetector = new GestureDetector(getContext(), new UserGestureDetector());
//        mPaint = new Paint();
    }

    /**
     * 对外接口，添加底部tab导航栏
     */
    public TabBarLayout addTab(TabUiHandler tabBar) {
        if (tabBar == null) {
            return this;
        }

        LinearLayout.LayoutParams lp =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.width = 0;
        lp.weight = 1;
        this.addView((View) tabBar, lp);
        if (this.getChildCount() == 1) {
            mCurrentItem = 0;
            selectedTab(mCurrentItem);
        }
        return this;
    }

    /**
     * 获取获取事件回调
     *
     * @param handler
     */
    public void setTabHandler(TabHandler handler) {
        if (handler != null) {
            this.mHandler = handler;
        } else {
            throw new NullPointerException("the tabHandler is null reference");
        }
    }

    //draw line on tabBar top
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
//        mPaint.setColor(Color.GRAY);
//        mPaint.setStyle(Paint.Style.STROKE);
//        canvas.drawLine(0, getTop() - 3, getWidth(), getTop() - 3, mPaint);
    }

    //关于状态保存
    @Override
    protected Parcelable onSaveInstanceState() {
        return super.onSaveInstanceState();
    }

    //状态恢复
    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
    }

    /**
     * double click ?/?
     */
//    用于记录用户是不是连续点击了两次tab栏
    private boolean isDoubleClick = false;
    private int downTab = mCurrentItem;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                int x = (int) event.getRawX();
                int y = (int) event.getRawY();

                downTab = (getTabIndex(x, y) == -1 ? mCurrentItem : getTabIndex(x, y));

                if (!((TabUiHandler) getChildAt(downTab)).hasPage())
                    return true;

                //按在无效位置
                // /*或初始tab则直接返回*/
                if (downTab == -1)
                    return false;

                setPressedTab(downTab);

                //当设置了自定义Drawable作为背景时，且符合TabUiHandler的规范则回调相应状态
//                if (mBgDrawable != null && mBgDrawable instanceof TabAnimHandler) {
//                    ((TabAnimHandler) mBgDrawable).onAnimStart(0, 0);
//                }

                break;
            case MotionEvent.ACTION_MOVE:
                int mx = (int) event.getRawX();
                int my = (int) event.getRawY();
                int moveTab = getTabIndex(mx, my);

                if (!((TabUiHandler) getChildAt(downTab)).hasPage())
                    return true;

                if (downTab != moveTab && downTab != mCurrentItem) {
                    unSelectedTab(downTab);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                if (downTab != mCurrentItem)
                    unSelectedTab(downTab);
                return true;

            case MotionEvent.ACTION_UP:
                int x1 = (int) event.getRawX();
                int y1 = (int) event.getRawY();
                int upTab = getTabIndex(x1, y1);

                if (!((TabUiHandler) getChildAt(downTab)).hasPage()) {
                    return true;
                }

                //判断按下的位置和抬起的位置是不是一样
                if (upTab == downTab) {

                    //点击已经选中的Tab
                    if (upTab == mCurrentItem) {
                        ((TabUiHandler) getChildAt(mCurrentItem)).onReSelected(this, 0, 0);
                    }
                    //更改选中Tab
                    if (upTab != mCurrentItem) {
                        unSelectedTab(mCurrentItem);
                        selectedTab(downTab);
                    }

                    //如果返回true,则表示这一次的事件有效，false则表示无效
                    if (mHandler != null) {
                        if (!mHandler.onTabClick(
                                (TabUiHandler) getChildAt(downTab),
                                downTab,
                                false,
                                isDoubleClick)) {
                            //当前选中tab与按下位置不一样，取消开始tab选中
                            unSelectedTab(downTab);
                            selectedTab(mCurrentItem);
                            return true;
                        } else {
                            //将可能存在的noPage Tab 状态改变一下
                            if (upTab != mCurrentItem)
                                tryToResetNoPageTab();
                        }
                    }//回调end

                    mCurrentItem = downTab;

                }
                //按下的位置和抬起的位置不一样
                else {
                    //开始按下与初始tab位置不一样
                    if (downTab != mCurrentItem)
                        unSelectedTab(downTab);
                }

                break;
        }
        return true;
    }

    /**
     * 将可能存在的noPageTab标签置位
     */
    public void tryToResetNoPageTab() {
        if (noPageTabSelectedIndex != -1) {
            ((TabUiHandler) getChildAt(noPageTabSelectedIndex)).unSelected(this, 0, 0);
            noPageTabSelectedIndex = -1;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 通过点击的位置判断当前的索引
     */
    private int getTabIndex(int x, int y) {
        if (x == 0) return -1;
        int count = getChildCount();
        int tabWidth = getWidth() / count;
        for (int i = 1; i <= count; i++) {
            if (tabWidth * i >= x) {
                return i - 1;
            }
        }
        return -1;
    }

    /**
     * 用户操作手势识别
     *
     * @return
     */
    class UserGestureDetector extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.d("RotateTab", "onDoubleTap");
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.d("RotateTab", "onSingleTapUp");
            //这里仅处理 hasPage==false 的子View逻辑
            if (mHandler == null) return true;

            int noPageTabIndex = getTabIndex((int) e.getRawX(), (int) e.getRawY());
            TabUiHandler noPageTab = (TabUiHandler) getChildAt(noPageTabIndex);
            if (noPageTab.hasPage()) return true;

            if (mHandler.onTabClick(
                    (TabUiHandler) getChildAt(TabBarLayout.this.noPageTabSelectedIndex),
                    TabBarLayout.this.noPageTabSelectedIndex == -1 ?
                            noPageTabIndex :
                            TabBarLayout.this.noPageTabSelectedIndex,
                    false,
                    isDoubleClick)) {

                if (TabBarLayout.this.noPageTabSelectedIndex == noPageTabIndex) {
                    noPageTab.onReSelected(TabBarLayout.this, 0, 0);
                } else {
                    noPageTab.onSelected(TabBarLayout.this, 0, 0);
                }
                TabBarLayout.this.noPageTabSelectedIndex = noPageTabIndex;
            }

            return true;
        }
    }

    /**
     * 设置选中的tab栏
     */
    private void setSelectedTab(int itemIndex) {
        if (itemIndex == mCurrentItem) return;
        unSelectedTab(mCurrentItem);
        selectedTab(itemIndex);
        mCurrentItem = itemIndex;
    }

    //see top
    private void selectedTab(int index) {
        if (index < 0 || index > getChildCount()) return;
        TabUiHandler tabItemView = (TabUiHandler) this.getChildAt(index);
        tabItemView.onSelected(this, 0, 0);
    }

    //see top top
    private void unSelectedTab(int index) {
        if (index < 0 || index > getChildCount()) return;
        TabUiHandler tabItemView = (TabUiHandler) this.getChildAt(index);
        tabItemView.unSelected(this, 0, 0);
    }

    /**
     * 获取TabBar当前选中的tab索引
     *
     * @return
     */
    public int getCurrentItem() {
        return mCurrentItem;
    }

    /**
     * 外界调用
     *
     * @param item
     */
    public void setCurrentItem(int item) {
        if (mHandler != null && item != mCurrentItem) {
            if (mHandler.onTabClick(
                    (TabUiHandler) getChildAt(downTab),
                    item,
                    false,
                    isDoubleClick)) {
                //当前选中tab与按下位置不一样，取消开始tab选中
                unSelectedTab(mCurrentItem);
                selectedTab(item);
                mCurrentItem = item;
                tryToResetNoPageTab();
            }
        }
    }

    /**
     * 设置按住的Tab标签
     */
    private void setPressedTab(int index) {
        if (index == mCurrentItem || index < 0 || index > getChildCount()) return;
        TabUiHandler tabItemView = (TabUiHandler) this.getChildAt(index);
        tabItemView.onPressed();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 适应用户在xml文件中指定contentView的情况
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {

            if (i == mCurrentItem) {
                ((TabUiHandler) getChildAt(i)).onSelected(this, 0, 0);
            } else {
                ((TabUiHandler) getChildAt(i)).unSelected(this, 0, 0);
            }

            if (mCurrentItem == -1) {
                ((TabUiHandler) getChildAt(0)).onSelected(this, 0, 0);
                mCurrentItem = 0;
            }
        }//for end

    }

}
