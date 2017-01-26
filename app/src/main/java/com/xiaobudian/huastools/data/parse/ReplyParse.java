package com.xiaobudian.huastools.data.parse;

import com.xiaobudian.huastools.model.ReplyData;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 小不点 on 2016/4/23.
 */
public class ReplyParse {

    private List<String> l_accepting_unit_id;
    private List<String> l_accepting_unit_data;
    private List<String> l_category_id;
    private List<String> l_category_data;
    private List<ReplyData> mReplyDatas;
    private List<String> l_details_html;

    private String[] pages;
    private List<String> l_page_id;
    private List<String> l_page_data;

    /**
     * 获得主页信息
     * @param html
     */
    public void parseAll(String html){
        if ((html==null)||(html.length()<1)) {
            return;
        }

        l_accepting_unit_id=new ArrayList<String>();
        l_accepting_unit_data=new ArrayList<String>();
        l_category_id=new ArrayList<String>();
        l_category_data=new ArrayList<String>();
        mReplyDatas=new ArrayList<ReplyData>();
        l_details_html=new ArrayList<String>();
        l_page_id=new ArrayList<String>();
        l_page_data=new ArrayList<String>();

        Document document= Jsoup.parse(html);

        /**
         * 获取受理单位列表
         */
        Elements e_accepting_unit=document.select("select[name=deptno]").select("option");
        for(Element e:e_accepting_unit){
            l_accepting_unit_id.add(e.attr("value").toString());
            l_accepting_unit_data.add(e.text().toString());
        }

        /**
         * 获取类别列表
         */
        Elements e_category=document.select("select[id=requestType]").select("option");
        for(Element e:e_category){
            l_category_id.add(e.attr("value").toString());
            l_category_data.add(e.text().toString());
        }

        /**
         * 获取列表信息
         */
        Elements e_list=document.select("tbody").get(1).select("tr");
        for(int i=1;i<e_list.size();i++){
            ReplyData replyData=new ReplyData();
            replyData.setSerial_number(e_list.get(i).select("td").get(0).text().toString());
            replyData.setTheme(e_list.get(i).select("td").get(1).text().toString());
            replyData.setDate(e_list.get(i).select("td").get(2).text().toString());
            replyData.setCategory(e_list.get(i).select("td").get(3).text().toString());
            replyData.setAccepting_unit(e_list.get(i).select("td").get(4).text().toString());
            replyData.setProcessing_status(e_list.get(i).select("td").get(5).text().toString());
            mReplyDatas.add(replyData);
        }

        /**
         * 获取详情网址
         */
        for(int i=1;i<e_list.size();i++){
            l_details_html.add("http://www.huas.cn:316/sunhuas/"+e_list.get(i).select("a").attr("href"));
        }

        /**
         * 获取页码
         */
        Elements e_page=document.select("tbody").last().select("td");
        pages=new String[4];
        pages[0]=e_page.get(0).text().toString();
        pages[1]=e_page.get(1).text().toString();
        pages[2]=e_page.get(2).text().toString();
        pages[3]=e_page.get(4).text().toString();
        Elements e_page_select=e_page.get(3).select("option");
        for(Element e:e_page_select){
            l_page_id.add(e.attr("value").toString());
            l_page_data.add(e.text().toString());
        }

    }

    /**
     * 获取查看回复详情信息
     * @param html
     * @return
     */
    public List<String> getReplyDetails(String html){

        List<String> l_reply_detials=new ArrayList<>();

        Document document=Jsoup.parse(html);
        Elements elements2=document.select("tbody").get(1).select("tr");
        for(int i=0;i<elements2.size()-6;i++){
            Elements elements=elements2.get(i).select("td");
            for(int j=0;j<elements.size();j++){
                if(i==4&&j==1){
                    String text=elements.get(j).select("div[id=towaddress]").text();
                    l_reply_detials.add(text);
                }else if(i==6&&j==1){
                    String text=elements.get(j).select("div[id=towcontent]").text();
                    l_reply_detials.add(text);
                }
                else{
                    l_reply_detials.add(elements.get(j).text().toString());
//                    l_reply_detials.add(elements2.get(i).select("td").get(j).text().toString());
                }
            }
        }
        return l_reply_detials;
    }

    public List<String> get_accepting_unit_id(){
        return l_accepting_unit_id;
    }
    public List<String> get_accepting_unit_data(){
        return l_accepting_unit_data;
    }
    public List<String> get_category_id(){
        return l_category_id;
    }
    public List<String> get_category_data(){
        return l_category_data;
    }

    public List<ReplyData> getReplyDatas() {
        return mReplyDatas;
    }

    public List<String> get_details_html(){
        return l_details_html;
    }


    public String[] get_pages(){
        return pages;
    }
    public List<String> get_page_id(){
        return l_page_id;
    }
    public List<String> get_page_data(){
        return l_page_data;
    }



}
