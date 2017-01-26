package com.xiaobudian.huastools.ui.view;

import android.content.Context;

/**
 * Created by caochang on 2016/4/5.
 */
public interface LoadDataView {

    void showLoading();

    void hideLoading();

    void showRetry();

    void hideRetry();

    void showError(String message);

    Context context();

}
