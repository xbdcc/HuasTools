package com.xiaobudian.huastools.data;

import android.util.Log;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 小不点 on 2016/6/20.
 */
public class GetNetData {

    private static final String TAG="GetNetData";
    public static Map<String,String> education_cookies=null;

    private static String result;
    public static int get_html_timeout=10*1000;
    public static int post_html_timeout=10*1000;

    public static void setEducationCookies(String cookie){
        education_cookies=new HashMap<>();
        Pattern p=Pattern.compile("(?<==).*(?=;)");
        Matcher m=p.matcher(cookie);
        while(m.find()){
            education_cookies.put("JSESSIONID", m.group());
        }
        Log.d(TAG,education_cookies.toString());
    }

    /**
     * 通过get方法获取html
     * @param url
     * @return
     */
    public static String getEducationHtml(String cookie,String url){
        setEducationCookies(cookie);
        Log.e(TAG,cookie);
        result=null;
        try {
            result=Jsoup.connect(url).cookies(education_cookies).timeout(get_html_timeout).get().toString();
        } catch (IOException e) {
            Log.i(TAG, "无数据");
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 通过post方法获取html
     * @param url
     * @param map
     * @return
     */
    public static String postEducationHtml(String cookie,String url,Map<String,String> map){
        setEducationCookies(cookie);
        result=null;
        try {
            result= Jsoup.connect(url).data(map).cookies(education_cookies).timeout(post_html_timeout).post().toString();
        } catch (IOException e) {
            Log.i(TAG, "无数据");
            e.printStackTrace();
        }
        return result;
    }
}
