package com.xiaobudian.huastools.uitest;

import android.support.test.uiautomator.UiAutomatorTestCase;
import android.support.test.uiautomator.UiDevice;

/**
 * Created by caochang on 2016/9/18.
 */
public class UITest extends UiAutomatorTestCase{


    public void test(){
        UiDevice uiDevice=getUiDevice().getInstance();
        uiDevice.click(180,400);
        System.out.println(uiDevice.getLauncherPackageName());
    }

//    public void testDemo() throws UiObjectNotFoundException {
//        getUiDevice().pressHome();//返回桌面
//
//        UiObject allAppsButton=new UiObject(new UiSelector().description("应用"));
//
//        allAppsButton.clickAndWaitForNewWindow();
//
//        // 进入设置菜单
//        UiObject settingApp = new UiObject(new UiSelector().text("设置"));
//        settingApp.click();
//        //休眠3秒
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e1) {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//        }
//        // 进入语言和输入法设置
//        UiScrollable settingItems = new UiScrollable(new UiSelector().scrollable(true));
//
//        UiObject languageAndInputItem = settingItems.getChildByText(
//                new UiSelector().text("语言和输入法"), "语言和输入法", true);
//        languageAndInputItem.clickAndWaitForNewWindow();
//    }
}
