package com.xiaobudian.huastools;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by caochang on 2016/4/12.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    Application getApplication();

}
