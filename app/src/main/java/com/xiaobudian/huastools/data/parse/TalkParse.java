package com.xiaobudian.huastools.data.parse;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 小不点 on 2016/4/24.
 */
public class TalkParse {

    private static final String TAG ="requestTyp" ;
    private String s_say_notice;
    private List<String> l_say_object_id;
    private List<String> l_say_object_data;

    private String[] s_get_number;//返回结果数组
    private String result;//直接返回字符串，推荐

    /**
     * 获得我要说话信息
     * @param html
     */
    public void parse_sayword(String html){
        if ((html==null)||(html.length()<1)) {
            return;
        }
        Document document=Jsoup.parse(html);
        l_say_object_id=new ArrayList<String>();
        l_say_object_data=new ArrayList<String>();

        /**
         * 获取我要说话页面说明文字
         */
        Elements e_notice=document.select("tbody").get(3).select("font");
        s_say_notice=e_notice.text().toString();

        /**
         * 获取我要说话页面提交对象列表信息
         */
        Elements e_object=document.select("select[name=deptno]").select("option");
        for(Element e:e_object){
            l_say_object_id.add(e.attr("value").toString());
            l_say_object_data.add(e.text().toString());
        }
    }

    /**
     * 获得提交后显示的单号
     * @param html
     */
    public void parse_sayword_get_number(String html){
        Document document= Jsoup.parse(html);
        Log.d(TAG,document.toString());
        System.out.println("----------"+document);
        s_get_number=new String[3];
        Elements elements=document.select("table").select("font");
        result=elements.text();
        for(int i=0;i<elements.size();i++){
            s_get_number[i]=elements.get(i).text().toString()+"\n";
        }
    }

    public String get_notice(){
        return s_say_notice;
    }
    public List<String> get_object_id(){
        return l_say_object_id;
    }
    public List<String> get_object_data(){
        return l_say_object_data;
    }

    public String[] get_sayword_number(){
        return s_get_number;
    }

    public String getResult(){
        return  result;
    }

}
