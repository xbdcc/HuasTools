package com.xiaobudian.huastools.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.xiaobudian.huastools.R;

/**
 * Created by 小不点 on 2016/6/11.
 */
public class FindPassword {


    public static void showAlertDialog(Context context){
        AlertDialog dialog=new AlertDialog.Builder(context).setTitle("找回密码").setMessage("1").
                setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                }).
                setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                }).
                create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

}
