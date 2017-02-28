package me.view.tabbar;

/**
 * Created by lyh on 2016/9/4.
 * When Custom the Drawable should implements the interface
 */
public interface TabAnimHandler {

    void onAnimStart(int startTab, int endTab);

    void onAnimStop();

}
