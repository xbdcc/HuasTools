package com.xiaobudian.huastools.ui.presenter;

/**
 * Created by caochang on 2016/4/18.
 */
public interface BasePresenter {

    void resume();

    void pause();

    void destroy();

    /**
     * 加载失败的界面控制
     */
    void loadFailure();

    /**
     * 加载成功的界面控制
     */
    void loadSuccess();

    /**
     * 加载中的界面控制
     */
    void loading();

}
