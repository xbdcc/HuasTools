package com.xiaobudian.huastools.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.xiaobudian.huastools.AppApplication;

/**
 * Created by xiaobudian on 2016/1/8.
 */
public class PreferencesUtils {

    private static final Context context= AppApplication.getAppContext();
    private static final String PREFERENCE_COURSE_CONTROL ="course_control";
    public static final String USESTATUS="useSatus";
    public static final String STOPTITLE="stopTitle";
    public static final String STOPDESCRIPTION="stopDescription";
    public static final String STOPTIME="stopTime";
    public static final String EDUCATIONCOOKIE="educationCookie";


    private static final String USE_STATUS="use_status";

    private static SharedPreferences.Editor editor;

    private static final String EDUCATION_DATA="education_data";
    private static final String EDUCATION_USSRNAME="education_username";
    private static final String EDUCATION_PASSWORD="education_password";
    private static final String EDUCATION_REMEMBER="education_remember";
    private static final String EDUCATION_AUTO="education_auto";

    public static SharedPreferences getSharedPreferences(){
        SharedPreferences sharedPreferences=context.getSharedPreferences(PREFERENCE_COURSE_CONTROL,Context.MODE_PRIVATE);
        return sharedPreferences;
    }

    /**
     * 设置可使用的状态
     * @param value
     */
    public static void setUseStatus(boolean value){
        setBoolean(USE_STATUS,value);
    }

    /**
     * 得到可使用的状态
     * @return
     */
    public static boolean getUsestatus(){
        return getBoolean(USE_STATUS,true);
    }


    private static SharedPreferences.Editor getEditor() {
        return getSharedPreferences().edit();
    }

    /**
     * 设置是否保存密码自动登录
     * @param username
     * @param password
     * @param isRemember
     * @param autoLogin
     */
    public static void setEducationPassword(String username,String password,
                                            boolean isRemember,boolean autoLogin){
        editor=getEditor();
        editor.putString(EDUCATION_USSRNAME,username);
        editor.putString(EDUCATION_PASSWORD,password);
        editor.putBoolean(EDUCATION_REMEMBER,isRemember);
        editor.putBoolean(EDUCATION_AUTO,autoLogin);
        editor.commit();
    }

    /**
     * 获取教务系统登录账号
     * @return
     */
    public static String getEducationUsername(){
        return getSharedPreferences().getString(EDUCATION_USSRNAME,"");
    }

    /**
     * 获取教务系统登录密码
     * @return
     */
    public static String getEducationPassword(){
        return getSharedPreferences().getString(EDUCATION_PASSWORD,"");
    }

    /**
     * 获取登录教务系统是否记住密码
     * @return
     */
    public static boolean getEducationRemember(){
        return getSharedPreferences().getBoolean(EDUCATION_REMEMBER, false);
    }

    /**
     * 获取登录教务系统是否自动登录
     */
    public static boolean getEducationAuto(){
        return getSharedPreferences().getBoolean(EDUCATION_AUTO,false);
    }




    public static void setBoolean(String key,boolean value){
        editor=getEditor();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getBoolean(String key,boolean value){
        return getSharedPreferences().getBoolean(key, value);
    }
}
