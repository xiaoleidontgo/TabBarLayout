package me.view.tabbar;

/**
 * Created by lyh on 2016/7/11.
 * tab的管理者
 */
public interface TabHandler {

    /**
     * @param tabItemView   当前的Tabt条目
     * @param tabItem       点击的tab条目
     * @param isReSelected  是否是重复选中
     * @param isDoubleClick 是否是双击
     * @return true表示Tab会相应此次事件，false不响应
     */
    boolean onTabClick(
            TabUiHandler tabItemView,
            int tabItem,
            boolean isReSelected,
            boolean isDoubleClick);

}
