package com.xiaobudian.huastools.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.xiaobudian.huastools.data.DataUtil;
import com.xiaobudian.huastools.data.HttpUrl;
import com.xiaobudian.huastools.util.DialogUtil.OnClickCancelListener;
import com.xiaobudian.huastools.util.DialogUtil.OnClickSureListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by xiaobudian on 2016/1/9.
 */
public class Update {

    ToastUtil mToastUtil=new ToastUtil();
    private Context context;
    private int type;
    private static final String TAG="Version";

    private int versionCode;
    private String versionName;
    private List<UpdateInfo> infos;
    private int temp=0;
    private int position=0;
    private ProgressDialog dialog;
    private File file;

    public enum UpdateType{
        Auto,Click,Background
    }
    public static int Auto=1;
    public static int Click=2;
    public static int Background=3;

    public Update(Context context, int type) {
        super();
        this.context = context;
        this.type=type;
    }

    public void update(){
        getCurrentVersion();
        if(type==Click){
            dialog=ProgressDialog.show(context, "", "检测版本信息，请稍后...",true,true);
        }
        new Thread(runnable).start();

    }

    /**
     * 获取当前版本号
     */
    private void getCurrentVersion(){
        PackageManager manager=context.getPackageManager();
        try {
            PackageInfo info=manager.getPackageInfo(context.getPackageName(), 0);
            versionCode=info.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
    }


    Runnable runnable=new Runnable() {

        @Override
        public void run() {
            checkVersion();
        }

    };

    Handler handler=new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case DataUtil.Common.CAN_BE_UPDATED:
//                    dialog.dismiss();
                    Toast.makeText(context, infos.get(position).getVersionName(), Toast.LENGTH_SHORT).show();
                    showUpdateDialog();
                    break;
                case DataUtil.Common.NO_NEED_TO_UPDATE:
//                    dialog.dismiss();
                    if(type==Click){
                        ToastUtil.showToast(context, "当前已是最新版本");
                    }
                    break;
                case DataUtil.Common.DOWNLOAD_FINISHED:
                    InstallApk();
                    break;
                case DataUtil.Common.MalformedURLException:
                    ToastUtil.showToast(context, "MalformedURLException");
                    break;
                case DataUtil.Common.IOException:
                    ToastUtil.showToast(context, "IOException");
                    break;
                default:
                    break;
            }
            if(dialog!=null){
                dialog.dismiss();
            }
        };
    };

    private void checkVersion() {
        try {
            URL url=new URL(HttpUrl.check_update);
            HttpURLConnection connection=(HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(10*000);//设置超时时间10秒
            InputStream inputStream=connection.getInputStream();
            infos=UpdateInfoParser.getUpdateInfo(inputStream);
            for(int i=0;i<infos.size();i++){
                int j=infos.get(i).getVersionCode();
                if (j>temp) {
                    temp=j;
                    position=i;
                }
            }
            if (temp>versionCode) {
                Log.i(TAG, "有新的的版本" + infos.get(position).getVersionName());
                versionName=infos.get(position).getVersionName();
                handler.sendEmptyMessage(DataUtil.Common.CAN_BE_UPDATED);
            }else {
                Log.i(TAG, "当前已是最新版本");
                handler.sendEmptyMessage(DataUtil.Common.NO_NEED_TO_UPDATE);
            }
        } catch (MalformedURLException e) {
            handler.sendEmptyMessage(-1);
            e.printStackTrace();
        } catch (IOException e) {
            handler.sendEmptyMessage(-2);
            e.printStackTrace();
        }

    }

    private void showUpdateDialog(){


        DialogUtil utils=new DialogUtil();
        utils.showAlertDialog(context, "检测到新版本，是否更新",
                infos.get(position).getDescription(), new OnClickSureListener() {

                    @Override
                    public void onClickSure() {
                        Download();
                    }
                }, new OnClickCancelListener() {

                    @Override
                    public void onClickCancel() {
                        if(dialog!=null){
                            dialog.dismiss();
                        }
                    }
                });

    }

    /**
     * 下载新版APK
     */
    public void Download(){
        File filedir = getFiledir();
        file=new File(filedir,"HuasTools"+versionName+".apk");
        DownloadAsyncTask task=new DownloadAsyncTask(context, handler, file);
        task.execute(infos.get(position).getApkUrl());
    }

    File getFiledir() {
        File sd= Environment.getExternalStorageDirectory();
        String path=sd.getPath()+"/HuasTools";
        File filedir=new File(path);
        if (!filedir.exists()) {
            filedir.mkdir();
            Log.e("Update", "新建一个文件夹");
        }
        return filedir;
    }

    /**
     * 安装APK
     */
    private void InstallApk(){
        if (file.exists()) {
            Intent intent=new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
            context.startActivity(intent);
        }

    }

    public void receiveUpdate(String string){
        getCurrentVersion();
        try {
            JSONObject object=new JSONObject(string);

            System.out.println(object.getString("updateVersionCode"));
            int updateVersionCode=Integer.valueOf(object.getString("updateVersionCode"));
            if (updateVersionCode>versionCode) {
                String updateTitle=object.getString("updateTitle");
                String updatedeScription=object.getString("updatedeScription");
                String updateVersionName=object.getString("updateVersionName");
                final String updateUrl=object.getString("updateUrl");

                versionName=updateVersionName;

                DialogUtil.showDialogNotCancel(context, updateTitle, updatedeScription, new OnClickSureListener() {

                    @Override
                    public void onClickSure() {
                        File filedir = getFiledir();
                        file=new File(filedir,"HuasTools"+versionName+".apk");
                        DownloadAsyncTask task=new DownloadAsyncTask(context, handler, file);
                        task.show();
                        task.execute(updateUrl);
                    }
                }, new OnClickCancelListener() {

                    @Override
                    public void onClickCancel() {

                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}