package com.xiaobudian.huastools.data.parse;

import android.util.Log;

import com.xiaobudian.huastools.model.Book;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 小不点 on 2016/4/21.
 */
public class QueryBookParse {

    private static final String TAG ="QueryBookParse" ;
    private String message="";
    private List<Book> mBooks;
    private Book mBook;
    private Document mDocument;
    private Elements mElements;
    private Elements e;
    private int i,j;

    public void getBook(String html){
        mBooks=new ArrayList<>();
        mDocument= Jsoup.parse(html, "UTF-8");
        mElements=mDocument.select("div[class=account]");//总共搜索结果
        message=mElements.text();
        mElements=mDocument.select("ul").select("li");
        if(mElements.size()==0)
            Log.d(TAG,"查到0本相关图书");
        for(i=0;i<mElements.size();i++){
            mBook=new Book();
            e=mElements.get(i).select("div");
            mBook.setTitle(e.get(0).text());
            e=e.get(1).select("p");
            mBook.setPublish(e.get(0).text());
            mBook.setIsbn(e.get(1).text());
            mBook.setPrice(e.get(2).text());
            mBook.setPage(e.get(3).text());
            mBook.setTime(e.get(4).text());
            mBooks.add(mBook);
        }
    }

    public String getMessage() {
        return message;
    }

    public List<Book> getBooks() {
        return mBooks;
    }

}
