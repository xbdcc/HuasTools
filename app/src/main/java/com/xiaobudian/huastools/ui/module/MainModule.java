package com.xiaobudian.huastools.ui.module;

import com.xiaobudian.huastools.ui.ActivityScope;
import com.xiaobudian.huastools.ui.activity.MainActivity;
import com.xiaobudian.huastools.ui.presenter.MainPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by caochang on 2016/4/13.
 */
@Module
public class MainModule {

    private MainActivity mMainActivity;
    public MainModule(MainActivity mainActivity){
        mMainActivity=mainActivity;
    }

    @Provides
    @ActivityScope
    MainActivity provideMainActivity(){
        return mMainActivity;
    }

    @Provides
    @ActivityScope
    MainPresenter provideMainPresenter(MainActivity mainActivity){
        return  new MainPresenter(mainActivity);
    }

}
