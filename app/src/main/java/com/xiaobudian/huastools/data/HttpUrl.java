package com.xiaobudian.huastools.data;

/**
 * Created by 小不点 on 2016/5/21.
 */
public class HttpUrl {

    //教务系统评教提交保存网址
    public static String education_evaluate_submit=EducationApi.BASE_URL+"/jsxsd/xspj/xspj_save.do";

    //获取版本信息来检测更新网址
    public static final String check_update="http://xbdcc.github.io/xiaobudian/HuasTools/update.xml";

    //联系我页面
    public static final String contact_me="http://t.cn/RyU2KwX";

    public static String education_login="http://220.168.209.130:20011/jsxsd/";

    //教务系统选课主页面网址
    public static String education_course_selection_main=education_login+"xk/AccessToXk?Ves632DSdyV=NEW_XSD_PYGL";

    //教务系统选课详情页网址，后续添加动态使用
    public static String education_course_selection_detail=education_login;

    //教务系统提交选课的网址
    public static String education_course_submit=education_login+"xk/processXk";

    //教务系统选课退选的网址
    public static String education_course_quit=education_login+"xk/processTx";

}
