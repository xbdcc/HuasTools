package com.xiaobudian.huastools.ui.component;

import com.xiaobudian.huastools.AppComponent;
import com.xiaobudian.huastools.ui.ActivityScope;
import com.xiaobudian.huastools.ui.fragment.library.QueryBookFragment;
import com.xiaobudian.huastools.ui.module.LibraryModule;

import dagger.Component;

/**
 * Created by 小不点 on 2016/4/21.
 */
@ActivityScope
@Component(modules = LibraryModule.class,dependencies = AppComponent.class)
public interface LibraryComponent {

    QueryBookFragment inject(QueryBookFragment queryBookFragment);
}
