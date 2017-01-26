package com.xiaobudian.huastools.ui.fragment.education;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaobudian.huastools.R;
import com.xiaobudian.huastools.data.DataUtil;

/**
 * Created by caochang on 2016/4/17.
 */
public class ScheduleFragment extends Fragment{

    private static final String TAG="ScheduleFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_schedule,container,false);

        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(DataUtil.Education.LONGIN_SUCCESS);
        getActivity().registerReceiver(receiver,intentFilter);
        Log.d(TAG,"onCreateView");
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().unregisterReceiver(receiver);
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DataUtil.Education.LONGIN_SUCCESS)) {
                Log.i(TAG, "成功登录教务系统，刷新课表fff信息。");
//                initData();
            }
        }
    };
}
