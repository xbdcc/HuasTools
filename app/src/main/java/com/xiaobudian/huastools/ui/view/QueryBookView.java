package com.xiaobudian.huastools.ui.view;

import com.xiaobudian.huastools.model.Book;

import java.util.List;

/**
 * Created by 小不点 on 2016/4/21.
 */
public interface QueryBookView extends LoadDataView{

    void renderBook(List<Book> books,String result);

    void showRecycleView();

    void hideRecycleView();

    void showPage();

    void hidePage();
}
