package com.xiaobudian.huastools.ui.view;

import com.xiaobudian.huastools.model.ReplyDetail;

/**
 * Created by 小不点 on 2016/4/26.
 */
public interface ReplyDetailView extends LoadDataView{


    void showView();

    void hideView();

    void initView(ReplyDetail replyDetail);
}
