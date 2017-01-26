package com.xiaobudian.huastools.ui.activity.education;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xiaobudian.huastools.AppComponent;
import com.xiaobudian.huastools.R;
import com.xiaobudian.huastools.data.DataUtil;
import com.xiaobudian.huastools.data.EducationApi;
import com.xiaobudian.huastools.ui.activity.BaseActivity;
import com.xiaobudian.huastools.ui.component.DaggerEducationComponent;
import com.xiaobudian.huastools.ui.module.EducationModule;
import com.xiaobudian.huastools.ui.presenter.EducationLoginPresenter;
import com.xiaobudian.huastools.ui.view.EducationLoginView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by caochang on 2016/4/18.
 */
public class EducationLoginActivity extends BaseActivity implements EducationLoginView {

    private static final String TAG = "EducationLoginActivity";

    @Bind(R.id.et_education_number)
    EditText mEtEducationNumber;
    @Bind(R.id.et_education_password)
    EditText mEtEducationPassword;
    @Bind(R.id.b_educaiton_login)
    Button mBEducaitonLogin;
    @Bind(R.id.pb_loading)
    ProgressBar mPbLoading;
    @Bind(R.id.tv_result)
    TextView mTvResult;

    EducationLoginPresenter mEducationLoginPresenter;
    private String username;
    private String password;
    private int type=0;

    @Inject
    EducationApi mEducationApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education_login);
        ButterKnife.bind(this);
        back();
        Log.d(TAG, "onCreate");
        mEducationLoginPresenter = new EducationLoginPresenter(this, mEducationApi);
        mEducationLoginPresenter.getCookie(type);
    }


    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerEducationComponent.builder()
                .appComponent(appComponent)
                .educationModule(new EducationModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void showMessage() {
        mTvResult.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideMessage() {
        mTvResult.setVisibility(View.GONE);
    }

    @Override
    public void successGetCookie() {
        String username=mEtEducationNumber.getText().toString();
        String password=mEtEducationPassword.getText().toString();
        mEducationLoginPresenter.login(username,password,type);

    }

    @Override
    public void successLogin() {
        Intent intent=new Intent();
        intent.setAction(DataUtil.Education.LONGIN_SUCCESS);
        sendBroadcast(intent);
        finish();
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

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {
        mTvResult.setText(message);
    }

    @Override
    public Context context() {
        return EducationLoginActivity.this;
    }

    @OnClick(R.id.b_educaiton_login)
    public void onClick() {
        type=1;
        mEducationLoginPresenter.getCookie(1);
    }

}
