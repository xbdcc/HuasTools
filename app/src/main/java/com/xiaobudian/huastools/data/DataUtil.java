package com.xiaobudian.huastools.data;

/**
 * Created by caochang on 2016/4/18.
 */
public class DataUtil {

    public class Common{
        public static final int NET_ERROR=001;
        public static final int UNKNOWN_ERROR=002;
        public static final int NO_DATA=003;
        public static final int MalformedURLException=004;
        public static final int IOException=005;

        /**
         * 检查更新
         */
        public static final int CAN_BE_UPDATED=010;
        public static final int NO_NEED_TO_UPDATE=011;
        public static final int DOWNLOAD_FINISHED=012;
    }

    public class Education{
        public static final String LONGIN_SUCCESS="logining_success";
        public static final String COOKIE="cookie";
        public static final int EVALUATE_SUBMIT=105;
    }
}
