package com.xiaobudian.huastools.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.xiaobudian.huastools.AppApplication;
import com.xiaobudian.huastools.AppComponent;
import com.xiaobudian.huastools.R;

/**
 * Created by caochang on 2016/4/15.
 */
public abstract class BaseFragment extends Fragment{

    private static final String TAG = "BaseFragment";

    private TextView tv_title;
    private TextView tv_message;
    private Button b_retry;
    public static boolean isEducationLogin;
    public static String educationCookie;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setupFragmentComponent(AppApplication.get(getActivity()).getAppComponent());
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    protected abstract void setupFragmentComponent(AppComponent appComponent);

    public void setTitle(String title){
        tv_title= (TextView) getActivity().findViewById(R.id.tv_title);
    }

    public void setMessage(String message){
        tv_message=(TextView)getActivity().findViewById(R.id.tv_message);
        tv_message.setText(message);
    }

    public void setMessage(int id){
        tv_message=(TextView)getActivity().findViewById(R.id.tv_message);
        tv_message.setText(getResources().getString(id));
    }

    public void setButtonText(String text){
        b_retry=(Button)getActivity().findViewById(R.id.b_retry);
        b_retry.setText(text);
    }

    public void setButtonText(int id){
        b_retry=(Button)getActivity().findViewById(R.id.b_retry);
        b_retry.setText(getResources().getString(id));
    }




}
