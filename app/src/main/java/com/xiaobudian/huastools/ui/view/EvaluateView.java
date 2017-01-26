package com.xiaobudian.huastools.ui.view;

import android.widget.ListView;

/**
 * Created by 小不点 on 2016/6/11.
 */
public interface EvaluateView extends LoadDataView{

    void showView();

    void hideView();

    void parseData(String result);

    void parseList(ListView listView, String result);

    void showMessage();

    void hideMessage();

}
