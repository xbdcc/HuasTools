package com.xiaobudian.huastools.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.xiaobudian.huastools.R;

/**
 * Created by caochang on 2016/4/12.
 */
public class ToastUtil {

    private static final String TAG ="ToastUtil" ;

    public static void showToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        Log.d(TAG,"4545");
    }

    public static void showToast(Context context,int resId){
        Toast.makeText(context,resId, Toast.LENGTH_SHORT).show();
    }

    public static void showNoNet(Context context){
        showToast(context, R.string.no_net);
    }
    public static void showError(Context context){
        showToast(context,R.string.unkonown_error);
    }
}
