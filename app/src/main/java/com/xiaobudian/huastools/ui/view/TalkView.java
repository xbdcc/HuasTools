package com.xiaobudian.huastools.ui.view;

import java.util.List;

/**
 * Created by 小不点 on 2016/4/24.
 */
public interface TalkView extends LoadDataView{

    void showView();

    void hideView();

    void renderAcceptUnit(List<String> list_id, List<String> list_data);//受理单位下拉条

    void showResult();

    void hideResult();

}
