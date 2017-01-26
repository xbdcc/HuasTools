package com.xiaobudian.huastools;

import android.support.test.uiautomator.UiAutomatorTestCase;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import com.xiaobudian.huastools.uitest.util.ShellUtils;

import java.io.IOException;

/**
 * Created by caochang on 2016/9/18.
 */
public class TestHuasTools extends UiAutomatorTestCase{

    private UiDevice uidevice;
    private UiObject uiobject;

    public void testAPP() throws IOException, UiObjectNotFoundException {

        directStartApp();

        clickTab();

        education();

//        ShellUtils.execCommand("am force-stop com.xiaobudian.huastools",true);

//        scrollTab();

    }

    public void directStartApp() throws IOException, UiObjectNotFoundException {
        uidevice=getUiDevice().getInstance();
//        Runtime.getRuntime().exec("am start com.xiaobudian.huastools/.ui.activity.MainActivity");
        ShellUtils.execCommand("am start com.xiaobudian.huastools/.ui.activity.MainActivity",true);
        sleep(5000);
    }

    public void clickTab() throws UiObjectNotFoundException {
        UiObject uiobject=getUiObject("com.xiaobudian.huastools:id/rb_education");
        uiobject.click();
        sleep(1000);

        uiobject=getUiObject("com.xiaobudian.huastools:id/rb_library");
        uiobject.click();
        sleep(1000);

        uiobject=getUiObject("com.xiaobudian.huastools:id/rb_sunshine");
        uiobject.click();
        sleep(1000);

        uiobject=getUiObject("com.xiaobudian.huastools:id/rb_person");
        uiobject.click();
        sleep(1000);

        uiobject=getUiObject("com.xiaobudian.huastools:id/rb_sunshine");
        uiobject.click();
        sleep(1000);

        uiobject=getUiObject("com.xiaobudian.huastools:id/rb_library");
        uiobject.click();
        sleep(1000);

        uiobject=getUiObject("com.xiaobudian.huastools:id/rb_education");
        uiobject.click();
        sleep(1000);

        uiobject=getUiObject("com.xiaobudian.huastools:id/rb_home");
        uiobject.click();
        sleep(1000);


    }

    public void education() throws UiObjectNotFoundException {

        uiobject=getUiObject("com.xiaobudian.huastools:id/rb_education");
        uiobject.click();
        sleep(1000);

        uiobject=getUiObjectByText("点我登录");
        uiobject.clickAndWaitForNewWindow();
        sleep(1000);

        uiobject=getUiObject("com.xiaobudian.huastools:id/et_education_number");
        uiobject.setText("hello");
        sleep(2000);

    }

    public void scrollTab() throws UiObjectNotFoundException {

        uiobject=getUiObject("com.xiaobudian.huastools:id/ry_menu");
        uiobject.swipeLeft(1);
        sleep(2000);
    }


//    public void testPictureScorll() throws UiObjectNotFoundException {
////        getUiDevice().getInstance();
//
//        UiObject uiObject=new UiObject(new UiSelector().resourceId("com.xiaobudian.huastools:id/rb_home"));
//        uiObject.click();
//        sleep(1000);
//
//
//        uiObject=new UiObject(new UiSelector().resourceId("com.xiaobudian.huastools:id/rb_education"));
//        uiObject.click();
//        sleep(1000);
//
//        uiObject=new UiObject(new UiSelector().resourceId("com.xiaobudian.huastools:id/rb_home"));
//        for(int i=0;i<uiObject.getChildCount();i++){
//            uiObject.getChild(new UiSelector().className("android.widget.RadioButton"));
//        }
//
////        UiObject uiObject=new UiObject(new UiSelector().resourceId("com.xiaobudian.huastools:id/viewPager"));
////        uiObject.swipeLeft(4);
////        uiObject.swipeRight(4);
//
////        UiObject uiObject=new UiObject(new UiSelector().resourceId("com.xiaobudian.huastools:id/viewflipper"));
////        uiObject.swipeLeft(2);
////        sleep(1000);
////        uiObject.swipeRight(2);
//
//
////        UiScrollable uiScrollable=new UiScrollable(new UiSelector().scrollable(true));
////        uiScrollable.setAsHorizontalList();
////        uiScrollable.scrollForward();
////        uiScrollable.scrollForward();
////        uiScrollable.scrollForward();
////        uiScrollable.scrollForward();
//
//    }

    public UiObject getUiObject(String resourceId){
        return new UiObject(new UiSelector().resourceId(resourceId));
    }

    public UiObject getUiObjectByText(String text){
        return new UiObject(new UiSelector().text(text));
    }

//    public void teststartApp() throws UiObjectNotFoundException, IOException {
//        getUiDevice().pressHome();
//
//        UiObject allAppsButton=new UiObject(new UiSelector().description("应用"));
//        allAppsButton.clickAndWaitForNewWindow();
//
//        UiScrollable uiScrollable=new UiScrollable(new UiSelector().scrollable(true));
//        uiScrollable.setAsVerticalList();
//        uiScrollable.scrollBackward();
//
//        // 启应用
//        Runtime.getRuntime().exec("am start com.tencent.mobileqq/com.tencent.mobileqq.activity.SplashActivity");
//        sleep(3000);
////        UiObject languageAndInputItem = uiScrollable.getChildByText(
////                new UiSelector().text("下载"), "下载", true);
////        languageAndInputItem.clickAndWaitForNewWindow();
//
//    }
}
