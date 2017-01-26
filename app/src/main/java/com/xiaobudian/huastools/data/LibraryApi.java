package com.xiaobudian.huastools.data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by 小不点 on 2016/4/21.
 */
public interface LibraryApi {

    String BASE_URL="http://220.168.209.130:9999/";

    @Headers("Accept: text/html, application/xhtml+xml, */*")
    @GET("jsxsd/")//获取Cookie
    Call<String> getCookie();

    @GET("search?")//获取图书列表 kw,查询的书名，xc为4 type查询的条件
    Call<String> getBook(@Query("kw") String title, @Query("xc") String xc, @Query("searchtype") String type);

    @GET
    Call<String> getBookChangePage(@Url String url);//获取更改页面结果
}
