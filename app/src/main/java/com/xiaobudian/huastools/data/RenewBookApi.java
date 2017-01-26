package com.xiaobudian.huastools.data;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by 小不点 on 2016/4/22.
 */
public interface RenewBookApi {

    String BASE_URL_COOKIE="http://m.5read.com/";
    String BASE_URL="http://mc.m.5read.com/";


    @GET("/")//获取Cookie
    Call<String> getCookie();


//    @GET("irdUser/login/opac/opacLogin.jspx")//获取图书列表 kw,查询的书名，xc为4 type查询的条件
//    Call<String> getBook(@Query("schoolid") String title, @Query("userType") String xc, @Query("searchtype") String type);



    @FormUrlEncoded
    @POST("irdUser/login/opac/opacLogin.jspx")//查询成绩
    Call<String> login(@Header("Cookie") String cookie,@Field("username") String username, @Field("password") String password, @Field("schoolid") String schoolid, @Field("userType") String userType, @Field("backurl") String backurl);


}
