package com.xiaobudian.huastools.model;

/**
 * Created by 小不点 on 2016/4/21.
 */
public class Book {

    private String title;
    private String publish;
    private String isbn;
    private String price;
    private String page;
    private String time;

    public Book(){

    }

    public Book(String title, String publish, String isbn, String price, String page, String time) {
        this.title = title;
        this.publish = publish;
        this.isbn = isbn;
        this.price = price;
        this.page = page;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublish() {
        return publish;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
