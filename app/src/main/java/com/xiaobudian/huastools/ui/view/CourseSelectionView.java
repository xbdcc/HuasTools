package com.xiaobudian.huastools.ui.view;

/**
 * Created by 小不点 on 2016/6/20.
 */
public interface CourseSelectionView extends LoadDataView{

    void showView();

    void hideView();

    void parseData(String result);

    void parseList(String result);

    void parseSubmit(String result);
}
