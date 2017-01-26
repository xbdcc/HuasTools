package com.xiaobudian.huastools.exception;

/**
 * Created by caochang on 2016/4/18.
 */
public interface ErrorBundle {
    Exception getException();

    String getErrorMessage();
}
