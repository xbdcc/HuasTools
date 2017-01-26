package com.xiaobudian.huastools.exception;

import android.content.Context;

/**
 * Created by caochang on 2016/4/18.
 */
public class ErrorMessageFactory {
    public ErrorMessageFactory() {
    }

    /**
     * ErrorMessage工厂、返回错误类型
     *
     * @param context
     * @param exception
     * @return
     */
    public static String creat(Context context, Exception exception) {
        String msg = exception.getMessage();
//        String msg = context.getResources().getString(R.string.exception_message_generic);
//        if (exception instanceof NetworkConnectionException) {
//            msg = context.getResources().getString(R.string.exception_message_no_connection);
//        }
        return msg;
    }
}
