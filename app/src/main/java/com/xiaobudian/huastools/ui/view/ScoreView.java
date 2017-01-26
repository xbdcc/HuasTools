package com.xiaobudian.huastools.ui.view;

import com.xiaobudian.huastools.model.Score;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caochang on 2016/4/18.
 */
public interface ScoreView extends LoadDataView{

    void showView();

    void hideView();

    void renderScoreTime(ArrayList<String> l_id,ArrayList<String> l_data);//开课时间下拉条

    void renderScoreCategory(ArrayList<String> list);//课程性质下拉条

    void renderScoreType(ArrayList<String> list);//显示方式下拉条

    void showRecycleView();

    void hideRecycleView();

    void showMessage();

    void hideMessage();

    void renderScore(List<Score> scores);
}
