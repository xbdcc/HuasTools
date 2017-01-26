package com.xiaobudian.huastools.ui.activity;

import android.os.Bundle;

import com.xiaobudian.huastools.AppComponent;
import com.xiaobudian.huastools.R;

/**
 * Created by 小不点 on 2016/5/21.
 */
public class AboutActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

        back();
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }
}
