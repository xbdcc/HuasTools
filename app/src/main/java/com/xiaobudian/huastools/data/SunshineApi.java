package com.xiaobudian.huastools.data;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by caochang on 2016/4/18.
 */
public interface SunshineApi {


    //阳光教育平台进入主显示页
    public static final String Sunshine_Mainshow = "http://www.huas.cn:316/sunhuas/RequestAction_prolist";
    //我要说话填写内容页面
    public static final String Sunshine_Sayword="http://www.huas.cn:316/sunhuas/RequestAction_proaddUI";
    //提交我要说话获取单号页面
    public static final String Sunshine_Sayword_Get="http://www.huas.cn:316/sunhuas/RequestAction_proadd";

    String BASE_URL = "http://www.huas.cn:316/sunhuas/";

//    @Headers("Accept: text/html, application/xhtml+xml, */*")
//    @GET("jsxsd/")//获取Cookie
//    Call<String> getCookie();
//
    @GET("RequestAction_prolist")//获取教育阳光服务中心回复列表信息
    Call<String> getReplys();

    @FormUrlEncoded
    @POST("RequestAction_prolist")//阳光教育平台进入主显示页
    Call<String> getReplys(@Field("deptno") String deptno, @Field("requestType") String requestType, @Field("title") String title, @Field("processcode") String processcode, @Field("pageView.currentpage") String currentpage);


    @GET
    Call<String> getReplysDetails(@Url String url);

    @GET("RequestAction_proaddUI")//我要说话填写内容页面
    Call<String> getTalk();

    @FormUrlEncoded
    @POST("RequestAction_proadd")//提交我要说话获取单号页面
    Call<String> getTalkResult(@Field("requestType") String requestTyp
            , @Field("deptno") String deptno, @Field("title") String title, @Field("content") String content
            , @Field("username") String username, @Field("email") String email, @Field("tel") String tel);

}
