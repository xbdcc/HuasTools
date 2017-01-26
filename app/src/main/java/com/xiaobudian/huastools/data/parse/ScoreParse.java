package com.xiaobudian.huastools.data.parse;

import android.util.Log;

import com.xiaobudian.huastools.model.Score;
import com.xiaobudian.huastools.model.User;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caochang on 2016/4/18.
 */
public class ScoreParse {

    private static final String TAG = "HtmlParser";
    private List<Score> scoreList;
    private ArrayList<String> list;

    private ArrayList<String> l_time_id;
    private ArrayList<String> l_time_data;

    /**
     * 解析成绩明细
     *
     * @param html
     */
    public List<Score> parseScore(String html) {
        if (html == null || html.length() < 1)
            return null;
        scoreList = new ArrayList<>();
        Document doc = Jsoup.parse(html, "UTF-8");
        Elements tables = doc.getElementsByTag("table");
        int witchTable = 0;
        for (Element table : tables) {
            switch (witchTable) {
                case 0:
                    Log.i(TAG, "parse:  case0");
                    for (Element tr : table.getElementsByTag("tr")) {
                        for (Element td : tr.getElementsByTag("td")) {
                        }
                    }
                    break;

                case 1:
                    Log.i(TAG, "parse:  case1");
                    int j = 0;
                    for (Element tr : table.getElementsByTag("tr")) {
                        int i = 0;
                        // 跳过第一层空白值
                        if (j == 0) {
                            j++;
                            continue;
                        }
                        Score score = new Score();
                        for (Element td : tr.getElementsByTag("td")) {
                            String content = td.text();
                            switch (i) {
                                case 1:
                                    score.setTime(content);
                                    break;
                                case 2:
                                    score.setId(content);
                                    break;
                                case 3:
                                    score.setName(content);
                                    break;
                                case 4:
                                    score.setGrade(content);
                                    break;
                                case 5:
                                    score.setCredit(content);
                                    break;
                                case 6:
                                    score.setClass_times(content);
                                    break;
                                case 7:
                                    score.setEvaluation_mode(content);
                                    break;
                                case 8:
                                    score.setAttribute(content);
                                    break;
                                case 9:
                                    score.setProperty(content);
                                    break;
                            }
                            ++i;
                        }
                        Log.e(TAG, "parseScore:" + score.toString());
                        if (score != null) {
                            scoreList.add(score);
                        }
                    }
                    break;
                case 2:
                    Log.i(TAG, "parse:  case2");
                    for (Element tr : table.getElementsByTag("tr")) {
                        for (Element td : tr.getElementsByTag("td")) {
                        }
                    }
                    break;
                case 3:
                    for (Element tr : table.getElementsByTag("tr")) {
                        for (Element td : tr.getElementsByTag("td")) {
                        }
                    }
                    break;
            }
            witchTable++;
        }
        return scoreList;
    }

    /**
     * 直接返回  姓名（学号）  格式
     *
     * @param html
     * @return
     */
    public String parseUser(String html) {
        if (html == null || html.length() < 1)
            return null;
        Document doc = Jsoup.parse(html, "UTF-8");
        Elements tables = doc.select(".Nsb_top_menu_nc");
        return tables.text();
    }

    /**
     * 解析登录时是否出错
     *
     * @param html
     * @return
     */
    public static String parseLoginResult(String html) {
        if (html == null || html.length() < 1)
            return null;
        Document doc = Jsoup.parse(html, "UTF-8");
        Elements tables = doc.select(".dlmi");
        String errorMsg = tables.text();
        Log.e(TAG, "parseLoginError:" + errorMsg);
        errorMsg.replace(" ", "");
        errorMsg = errorMsg.replace("用户名: 密码:", "");
        return errorMsg;
    }

    /**
     * 获取开课时间
     *
     * @param html
     * @return
     */
    public ArrayList<String> parseCourseTime(String html){
        if (html == null || html.length() < 1)
            return null;
        list=new ArrayList<>();
        l_time_id=new ArrayList<>();
        l_time_data=new ArrayList<>();
        Document document = Jsoup.parse(html, "UTF-8");
        Elements elements=document.select("select[id=kksj]");
        Elements e=elements.select("option");
        for(int i=0;i<e.size();i++){
            l_time_data.add(e.get(i).text());
            l_time_id.add(e.get(i).attr("value"));
        }
        return  list;
    }
//    public ArrayList<String> parseTermList(String html) {
//        if (html == null || html.length() < 1)
//            return null;
//        ArrayList<String> timeList = new ArrayList<String>();
//        Document doc = Jsoup.parse(html, "UTF-8");
//        Elements tables = doc.select(".Nsb_layout_r");
//
//        int witchTable = 0;
//        for (Element table : tables) {
//            for (Element tr : table.getElementsByTag("tr")) {
//                for (Element td : tr.getElementsByTag("td")) {
//                    for (Element op : td.getElementsByTag("option")) {
//
//                        if (witchTable == 0) {
//                            timeList.add("全部");
//                            ++witchTable;
//                            continue;
//                        }
//                        if (witchTable > 0 && witchTable < 10) {
//                            timeList.add(op.text().toString());
//                        }
//                        ++witchTable;
//                    }
//                }
//            }
//        }
//        return timeList;
//    }

    /**
     * 获取查询成绩的课程性质列表,list偶数为nam,基数为value
     * @param html
     * @return
     */
    public ArrayList<String> parseCourseCategoryData(String html){
        if (html == null || html.length() < 1)
            return null;
        list=new ArrayList<>();
        Document document = Jsoup.parse(html, "UTF-8");
        Elements elements=document.select("select[id=kcxz]");
        Elements e=elements.select("option");
        for(int i=0;i<e.size();i++){
            list.add(e.get(i).text());
            list.add(e.get(i).attr("value"));
        }
        return  list;
    }

    /**
     * 获取查询成绩的显示方式列表,list偶数为nam,基数为value
     * @param html
     * @return
     */
    public ArrayList<String> parseCourseType(String html){
        if (html == null || html.length() < 1)
            return null;
        list=new ArrayList<>();
        Document document = Jsoup.parse(html, "UTF-8");
        Elements elements=document.select("select[id=xsfs]");
        Elements e=elements.select("option");
        for(int i=0;i<e.size();i++){
            list.add(e.get(i).text());
            list.add(e.get(i).attr("value"));
        }
        return  list;
    }
    /**
     * 用户信息，姓名：学号：
     *
     * @param html
     */
    public User parseUserInfo(String html) {
        User user = new User();
        String s = null;

        // 如果传入值空，则解析失败，返回
        if (html == null || html.length() < 1)
            return null;

        Document doc = Jsoup.parse(html, "UTF-8");
        Elements tables = doc.select(".block1text");
        s = tables.text();

        // 如果ID修改，则解析失败，返回失败
        if ("".equals(tables.text()))
            return null;

        Log.i(TAG, "parseUser: " + tables.text());

        StringBuffer sb = new StringBuffer();
        StringBuffer sb2 = new StringBuffer();

        boolean isName = true;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == 32) {
                isName = false;
                continue;
            }
            if (isName) {
                sb.append(c);
            } else {
                sb2.append(c);
            }
        }
        user.setName(sb.toString().replace("姓名：", ""));
        user.setId(sb.toString().replace("学号：", ""));

        // 如果格式改变，则解析失败
        if (user.getId() == null)
            return null;
        return user;
    }

    public ArrayList<String> getL_time_id() {
        return l_time_id;
    }

    public ArrayList<String> getL_time_data() {
        return l_time_data;
    }
}
