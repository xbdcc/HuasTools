package com.xiaobudian.huastools.ui.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xiaobudian.huastools.AppComponent;
import com.xiaobudian.huastools.R;
import com.xiaobudian.huastools.data.SunshineApi;
import com.xiaobudian.huastools.databinding.ActivityReplyDetailsBinding;
import com.xiaobudian.huastools.model.ReplyDetail;
import com.xiaobudian.huastools.ui.component.DaggerSunshineComponent;
import com.xiaobudian.huastools.ui.module.SunshineModule;
import com.xiaobudian.huastools.ui.presenter.ReplyDetailPresenter;
import com.xiaobudian.huastools.ui.view.ReplyDetailView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 小不点 on 2016/4/24.
 */
public class ReplyDetailActivity extends BaseActivity implements ReplyDetailView {

    @Inject
    SunshineApi mSunshineApi;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.pb_loading)
    ProgressBar mPbLoading;
    @Bind(R.id.tv_result)
    TextView mTvResult;
    @Bind(R.id.b_retry)
    Button mBRetry;
    @Bind(R.id.ll_view)
    LinearLayout mLlView;
    private ReplyDetailPresenter mReplyDetailPresenter;

    private ActivityReplyDetailsBinding activityReplyDetailsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityReplyDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_reply_details);
        ButterKnife.bind(this);

        hideView();

        mReplyDetailPresenter = new ReplyDetailPresenter(mSunshineApi, this);
        String url = getIntent().getStringExtra("url");
        mReplyDetailPresenter.loadData(url);

    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerSunshineComponent.builder()
                .appComponent(appComponent)
                .sunshineModule(new SunshineModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void showView() {
        mLlView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideView() {
        mLlView.setVisibility(View.GONE);
    }

    @Override
    public void initView(ReplyDetail replyDetail) {
        activityReplyDetailsBinding.setReplyDetail(replyDetail);
        showView();
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
        mTvResult.setVisibility(View.VISIBLE);
        mBRetry.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetry() {
        mTvResult.setVisibility(View.GONE);
        mBRetry.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Context context() {
        return this;
    }
}
