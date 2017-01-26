package com.xiaobudian.huastools.ui.component;

import com.xiaobudian.huastools.AppComponent;
import com.xiaobudian.huastools.ui.ActivityScope;
import com.xiaobudian.huastools.ui.activity.MainActivity;
import com.xiaobudian.huastools.ui.module.MainModule;

import dagger.Component;

/**
 * Created by caochang on 2016/4/15.
 */
@ActivityScope
@Component(modules = MainModule.class,dependencies = AppComponent.class)
public interface MainComponent {

    MainActivity inject(MainActivity mainActivity);

}
