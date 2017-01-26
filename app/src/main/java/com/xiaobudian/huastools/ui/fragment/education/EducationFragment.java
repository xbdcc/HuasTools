package com.xiaobudian.huastools.ui.fragment.education;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xiaobudian.huastools.AppComponent;
import com.xiaobudian.huastools.R;
import com.xiaobudian.huastools.data.DataUtil;
import com.xiaobudian.huastools.data.EducationApi;
import com.xiaobudian.huastools.ui.activity.education.EducationLoginActivity;
import com.xiaobudian.huastools.ui.adapter.MyPagerAdapter;
import com.xiaobudian.huastools.ui.component.DaggerEducationComponent;
import com.xiaobudian.huastools.ui.fragment.BaseFragment;
import com.xiaobudian.huastools.ui.module.EducationModule;
import com.xiaobudian.huastools.ui.presenter.EducationPresenter;
import com.xiaobudian.huastools.ui.view.EducationView;
import com.xiaobudian.huastools.util.PreferencesUtils;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by caochang on 2016/4/15.
 */
public class EducationFragment extends BaseFragment implements EducationView {

    private static final String TAG = "EducationFragment";

    public static boolean isLogin = false;
    @Bind(R.id.sliding_tabs)
    TabLayout mSlidingTabs;
    @Bind(R.id.viewpager)
    public ViewPager mViewpager;
    @Bind(R.id.pb_loading)
    ProgressBar mPbLoading;
    @Bind(R.id.tv_message)
    TextView mTvMessage;

    @Inject
    EducationApi mEducationApi;
    @Bind(R.id.b_retry)
    Button mBRetry;

    private EducationPresenter mEducationPresenter;
    public static String cookie;
    private boolean isFirstShow = true;
    public static int buttonType=1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_education, container, false);
        ButterKnife.bind(this, view);

        setupViewPager(mViewpager);

        mSlidingTabs.addTab(mSlidingTabs.newTab().setText(R.string.score));
        mSlidingTabs.addTab(mSlidingTabs.newTab().setText(R.string.schedule));
        mSlidingTabs.addTab(mSlidingTabs.newTab().setText(R.string.course_selection));
        mSlidingTabs.addTab(mSlidingTabs.newTab().setText(R.string.evaluate));

        mSlidingTabs.setupWithViewPager(mViewpager);

        mEducationPresenter = new EducationPresenter(mEducationApi, this);
        if (isFirstShow) {
            loadData();
        }
        Log.d(TAG, "onCreateView");
        return view;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerEducationComponent.builder()
                .appComponent(appComponent)
                .educationModule(new EducationModule(this))
                .build()
                .inject(this);
    }

    private void setupViewPager(ViewPager mViewPager) {
        MyPagerAdapter adapter = new MyPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new ScoreFragment(), getResources().getString(R.string.score));
        adapter.addFragment(new ScheduleFragment(), getResources().getString(R.string.schedule));
        adapter.addFragment(new CourseSelectionFragment(), getResources().getString(R.string.course_selection));
        adapter.addFragment(new EvaluateFragment(), getResources().getString(R.string.evaluate));
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(adapter);
//        Log.e(TAG, "setCurrentPage: " + getCurrentPage());
//        viewpager.setCurrentItem(getCurrentPage());
    }

    public void loadData() {
        boolean isAutoLogin = PreferencesUtils.getEducationAuto();

        if (!isLogin) {//如果没有登录就登录
            mTvMessage.setText(R.string.education_login_please);
            mBRetry.setText(R.string.education_login_button);
            if (isAutoLogin&&isFirstShow) {//第一次登录成功以后自动登录
                Log.d(TAG, "自动登录");
                mEducationPresenter.getCookie();
//            String username="201310010229";
//            String password="201310010229cxr";
            } else {
                Log.d(TAG, "没有登录显示要登录");
//                showLoading();
                showRetry();
            }
        }
    }

    @Override
    public void showLoading() {
        mPbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mPbLoading.setVisibility(View.GONE);
    }

    @Override
    public void showRetry() {
        mTvMessage.setVisibility(View.VISIBLE);
        mBRetry.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetry() {
        mTvMessage.setVisibility(View.GONE);
        mBRetry.setVisibility(View.GONE);
    }

    public void showError(String message) {
        mTvMessage.setText(message);
    }

    public Context context() {
        return getActivity();
    }

    @Override
    public void successgetCookie() {
        String username = PreferencesUtils.getEducationUsername();
        String password = PreferencesUtils.getEducationPassword();
        Log.d(TAG, "登录中" + "：用户名" + username + " 密码" + password);
        mEducationPresenter.login(username, password);
    }

    @Override
    public void successLogin() {
        educationCookie = mEducationPresenter.cookie;
        Log.d(TAG, "登录成功，更新数据");
        isEducationLogin = true;
        Intent intent = new Intent();
        intent.setAction(DataUtil.Education.LONGIN_SUCCESS);
        context().sendBroadcast(intent);
    }

    @OnClick(R.id.b_retry)
    public void onClick() {
        if(buttonType==1){
            Intent intent = new Intent();
            intent.setClass(getActivity(), EducationLoginActivity.class);
            startActivity(intent);
        }else {
            mEducationPresenter.getCookie();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DataUtil.Education.LONGIN_SUCCESS);
        getActivity().registerReceiver(receiver, intentFilter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        Log.d(TAG, "onDestroyView");
        getActivity().unregisterReceiver(receiver);
    }


    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DataUtil.Education.LONGIN_SUCCESS)) {
                Log.i(TAG, "成功登录教务系统，刷新成绩信息。");
                hideRetry();
                hideLoading();
                isFirstShow = false;
//                initData();
            }
        }
    };

}