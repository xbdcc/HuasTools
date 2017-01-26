package com.xiaobudian.huastools.ui.presenter;

import android.support.v4.app.Fragment;

import com.xiaobudian.huastools.ui.activity.MainActivity;
import com.xiaobudian.huastools.ui.adapter.MyFragmentPagerAdapter;
import com.xiaobudian.huastools.ui.view.MainView;

import java.util.ArrayList;

/**
 * Created by caochang on 2016/4/14.
 */
public class MainPresenter implements BaseViewPresenter<MainView> {

    private MainActivity mMainActivity;
    private MainView mMainView;

    public MainPresenter(MainActivity mainActivity){
        this.mMainActivity=mainActivity;
    }

    @Override
    public void attachView(MainView view) {
        mMainView=view;
    }

    @Override
    public void detachView() {
        this.mMainView=null;
    }

    public void initView(ArrayList<Fragment> fragments){
        MyFragmentPagerAdapter myFragmentPagerAdapter=new MyFragmentPagerAdapter
                (mMainActivity.getSupportFragmentManager(),fragments);
        mMainView.initViewPager(myFragmentPagerAdapter);

    }


}
