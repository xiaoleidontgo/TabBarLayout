package me.view.tabbar;


/**
 * Created by lyh on 2016/7/11.
 * tab样式与状态
 */
public interface TabUiHandler {

    /**
     * 选中此Tab
     * <p/>
     * end from endTab
     *
     * @param startTab
     * @param endTab
     */
    void onSelected(TabBarLayout parent, int startTab, int endTab);

    /**
     * 当此标签重复选中的时候
     *
     * @param parent
     * @param startTab
     * @param endTab
     */
    void onReSelected(TabBarLayout parent, int startTab, int endTab);

    /**
     * 当按住此Tab
     */
    void onPressed();

    /**
     * 当此Tab从选中状态获取从按住状态改变
     *
     * @param startTab
     * @param endTab
     */
    void unSelected(TabBarLayout parent, int startTab, int endTab);


    /**
     * 表示这个tabItem的实例对应一个页面
     * <p>
     * 返回false之后意味着：
     * 这是一个独立的Tab标签，选中,会和其他的标签同时选中，
     * 但点击其他标签后，会回调false标签的unSelected()
     *
     * @return false 没有界面  true有界面
     */
    boolean hasPage();

}
