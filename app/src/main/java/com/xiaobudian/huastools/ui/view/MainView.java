package com.xiaobudian.huastools.ui.view;

import com.xiaobudian.huastools.ui.adapter.MyFragmentPagerAdapter;

/**
 * Created by caochang on 2016/4/14.
 */
public interface MainView {

    void initViewPager(MyFragmentPagerAdapter myFragmentPagerAdapter);

    void checkUpdate();

}
