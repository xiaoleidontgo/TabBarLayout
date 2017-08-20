# TabBarLayout
一个可拓展的android底栏组件（不维护，建议使用官方组件BottomNavigationView）

使用：
<!--底部Tab栏-->
        <lyh.library.cm.component.tabbar.TabBarLayout
            android:id="@+id/tab_bar"
            android:layout_width="match_parent"
            android:layout_height="@dimen/tabbar_height"
            android:layout_alignParentBottom="true"
            android:orientation="horizontal">

            <lyh.library.cm.component.tabbar.view.ClassicTab
                android:layout_width="0dp"
                android:layout_height="match_parent"
                android:layout_weight="1"
                android:foreground="?attr/selectableItemBackground"
                app:normal_icon="@mipmap/home_normal"
                app:normal_title_color="@color/colorGray"
                app:selected_icon="@mipmap/home_pressed"
                app:selected_title_color="@color/colorBlack"
                app:title_text="主页" />

            <lyh.library.cm.component.tabbar.view.ClassicTab
                android:layout_width="0dp"
                android:layout_height="match_parent"
                android:layout_weight="1"
                android:foreground="?attr/selectableItemBackground"
                app:normal_icon="@mipmap/im_normal"
                app:normal_title_color="@color/colorGray"
                app:selected_icon="@mipmap/im_pressed"
                app:selected_title_color="@color/colorBlack"
                app:title_text="信息" />

            <lyh.library.cm.component.tabbar.view.RotateTab
                android:layout_width="0dp"
                android:layout_height="match_parent"
                android:layout_weight="1" />

            <lyh.library.cm.component.tabbar.view.ClassicTab
                android:layout_width="0dp"
                android:layout_height="match_parent"
                android:layout_weight="1"
                android:foreground="?attr/selectableItemBackground"
                app:normal_icon="@mipmap/photo_normal"
                app:normal_title_color="@color/colorGray"
                app:selected_icon="@mipmap/photo_pressed"
                app:selected_title_color="@color/colorBlack"
                app:title_text="图片" />

            <lyh.library.cm.component.tabbar.view.ClassicTab
                android:layout_width="0dp"
                android:layout_height="match_parent"
                android:layout_weight="1"
                android:foreground="?attr/selectableItemBackground"
                app:normal_icon="@mipmap/me_normal"
                app:normal_title_color="@color/colorGray"
                app:selected_icon="@mipmap/me_pressed"
                app:selected_title_color="@color/colorBlack"
                app:title_text="我" />
        </lyh.library.cm.component.tabbar.TabBarLayout>
