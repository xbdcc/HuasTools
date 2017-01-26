package com.xiaobudian.huastools.ui.component;

import com.xiaobudian.huastools.AppComponent;
import com.xiaobudian.huastools.ui.ActivityScope;
import com.xiaobudian.huastools.ui.activity.ReplyDetailActivity;
import com.xiaobudian.huastools.ui.fragment.sunshine.ReplyFragment;
import com.xiaobudian.huastools.ui.fragment.sunshine.TalkFragment;
import com.xiaobudian.huastools.ui.module.SunshineModule;

import dagger.Component;

/**
 * Created by 小不点 on 2016/4/23.
 */
@ActivityScope
@Component(modules = SunshineModule.class,dependencies = AppComponent.class)
public interface SunshineComponent {

    ReplyFragment inject(ReplyFragment replyFragment);

    ReplyDetailActivity inject(ReplyDetailActivity replyDetailActivity);

    TalkFragment inject(TalkFragment talkFragment);

}
