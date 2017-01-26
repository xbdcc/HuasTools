package com.xiaobudian.huastools.ui.view;

import com.xiaobudian.huastools.model.ReplyData;

import java.util.List;

/**
 * Created by 小不点 on 2016/4/23.
 */
public interface ReplyView extends LoadDataView{

    void showView();

    void hideView();

    void renderAcceptUnit(List<String> list_id,List<String> list_data);//受理单位下拉条

    void renderCategory(List<String> list_id,List<String> list_data);//类别下拉条

    void renderPage(String[] pages,List<String> l_page_id,List<String> l_page_data);//页码

    void showResult();

    void hideResult();

    void showMessage();

    void hideMessage();

    void renderReplyData(List<ReplyData> replyDatas,List<String> l_detail_url);
}
