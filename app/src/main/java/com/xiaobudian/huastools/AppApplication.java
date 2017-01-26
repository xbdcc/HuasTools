package com.xiaobudian.huastools;

import android.app.Application;
import android.content.Context;

import com.tencent.bugly.crashreport.CrashReport;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by caochang on 2016/4/12.
 */
public class AppApplication extends Application{

    private static Context context;
    private AppComponent appComponent;

    public static AppApplication get(Context context){
        return (AppApplication) context.getApplicationContext();
    }


    @Override
    public void onCreate() {
        super.onCreate();
        appComponent=DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        AppApplication.context=getApplicationContext();

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

            /* Bugly SDK初始化
        * 参数1：上下文对象
        * 参数2：APPID，平台注册时得到,注意替换成你的appId
        * 参数3：是否开启调试模式，调试模式下会输出'CrashReport'tag的日志
        */
        CrashReport.initCrashReport(getApplicationContext(), "xxxxxxxxxx", true);//改为自己

    }

    public AppComponent getAppComponent(){
        return appComponent;
    }

    public static Context getAppContext() {
        return AppApplication.context;
    }
}
