package com.xiaobudian.huastools.data;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by caochang on 2016/4/18.
 */
public interface EducationApi {
    String BASE_URL = "http://220.168.209.130:20011/";

    @Headers("Accept: text/html, application/xhtml+xml, */*")
    @GET("jsxsd/")//获取Cookie
    Call<String> getCookie();

    @FormUrlEncoded
    @POST("jsxsd/xk/LoginToXk")//登录
    Call<String> login(@Header("Cookie") String cookie, @Field("USERNAME") String username, @Field("PASSWORD") String password);

    @POST("jsxsd/kscj/cjcx_query")//获取查成绩选项信息
    Call<String> getScoreCondition(@Header("Cookie") String cookie);

    @FormUrlEncoded
    @POST("jsxsd/kscj/cjcx_list")//查询成绩
    Call<String> searchScore(@Header("Cookie") String cookie, @Field("kksj") String kksj, @Field("kcxz") String kcxz, @Field("kcmc") String kcmc, @Field("xsfs") String xsfs);

    @FormUrlEncoded
    @POST("jsxsd/kscj/cjcx_list")
    Call<String> searchCourse(@Header("Cookie") String cookie, @Field("kksj") String kksj, @Field("kcxz") String kcxz, @Field("kcmc") String kcmc, @Field("xsfs") String xsfs);

    @GET//教务系统选课主页面网址
    Call<String> getCourseSelectionMain(@Header("Cookie") String cookie,@Url String url);

    @GET//教务系统选课列表网址
    Call<String> getCourseSelectionList(@Header("Cookie") String cookie,@Url String url);

    @GET//教务系统选课详情页网址，后续添加动态使用
    Call<String> getCourseSelectionDetail(@Header("Cookie") String cookie,@Url String url);

    @FormUrlEncoded
    @POST("jsxsd/xk/processXk")//教务系统提交选课的网址
    Call<String> getCourseSubmit(@Header("Cookie") String cookie, @Field("jx0404id") String jx0404id,
                              @Field("jx0502id") String jx0502id, @Field("jx0502zbid") String jx0502zbid);

    @FormUrlEncoded
    @POST("jsxsd/xk/processTx")//教务系统提交退选的网址
    Call<String> getCourseQuit(@Header("Cookie") String cookie, @Field("jx0404id") String jx0404id,
                              @Field("jx0502id") String jx0502id, @Field("jx0502zbid") String jx0502zbid);

//    //教务系统选课主页面网址
//    public static String education_course_selection_main=education_login+"/xk/AccessToXk?Ves632DSdyV=NEW_XSD_PYGL";
//
//    //教务系统选课详情页网址，后续添加动态使用
//    public static String education_course_selection_detail=education_login;
//
//    //教务系统提交选课的网址
//    public static String education_course_submit=education_login+"/xk/processXk";
//
//    //教务系统选课退选的网址
//    public static String education_course_quit=education_login+"/xk/processTx";


    @POST("jsxsd/xspj/xspj_find.do?Ves632DSdyV=NEW_XSD_JXPJ")//获取评教主页面
    Call<String> getEvaluateMain(@Header("Cookie") String cookie);

    @GET
    Call<String> getEvaluateList(@Header("Cookie") String cookie,@Url String url);//获取评教列表

    @GET
    Call<String> getEvaluateDetail(@Header("Cookie") String cookie,@Url String url);//获取评教详情

    @FormUrlEncoded
    @POST("jsxsd/xspj/xspj_save.do")//评教提交保存网址
    Call<String> a(@Header("Cookie") String cookie, @FieldMap Map<String,String> map);

}
