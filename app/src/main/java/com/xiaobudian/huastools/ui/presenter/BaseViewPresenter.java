package com.xiaobudian.huastools.ui.presenter;

/**
 * Created by caochang on 2016/4/12.
 */
public interface BaseViewPresenter<V> {

    void attachView(V view);

    void detachView();

}
