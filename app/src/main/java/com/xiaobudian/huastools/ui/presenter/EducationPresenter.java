package com.xiaobudian.huastools.ui.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.xiaobudian.huastools.data.EducationApi;
import com.xiaobudian.huastools.data.parse.ScoreParse;
import com.xiaobudian.huastools.exception.DefaultErrorBundle;
import com.xiaobudian.huastools.exception.ErrorBundle;
import com.xiaobudian.huastools.exception.ErrorMessageFactory;
import com.xiaobudian.huastools.ui.fragment.BaseFragment;
import com.xiaobudian.huastools.ui.fragment.education.EducationFragment;
import com.xiaobudian.huastools.ui.view.EducationView;
import com.xiaobudian.huastools.util.PreferencesUtils;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by caochang on 2016/4/19.
 */
public class EducationPresenter implements BasePresenter {

    private static final String TAG="EducationPresenter";
    public String cookie=null;

    @Inject
    EducationApi mEducationApi;
    private EducationView mEducationView;

    public EducationPresenter(EducationApi educationApi, EducationView educationView) {
        mEducationApi = educationApi;
        mEducationView = educationView;
    }


    /**
     * 获取Cookie
     */
    public void getCookie(){
        loading();
        Log.d(TAG,"获取cookie中");
        Call<String> call=mEducationApi.getCookie();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String headers=response.headers().toString();
                if (TextUtils.isEmpty(headers)||!headers.contains("Set-Cookie")){
                    Log.d(TAG,"获取cookie失败");
                    BaseFragment.isEducationLogin=false;
                    EducationFragment.buttonType=2;
                } else {
                    cookie=response.headers().get("Set-Cookie");
                    Log.d(TAG,"获取cookie成功 Cookie:"+cookie);
                    mEducationView.successgetCookie();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                cookie = null;
                loadFailure();
                EducationFragment.buttonType=2;
                BaseFragment.isEducationLogin=false;
                ErrorBundle errorBundle = new DefaultErrorBundle((Exception) t);
                String errorMsg = ErrorMessageFactory.creat(mEducationView.context(), errorBundle.getException());
                mEducationView.showError(errorMsg);
            }
        });
    }

    public void login(final String username, final String password) {
        Log.d(TAG,"postdata:"+"cookie"+cookie);
        Call<String> call = mEducationApi.login(cookie, username, password);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                loadSuccess();
                String body = response.body().toString();
                String errorMsg = new ScoreParse().parseLoginResult(body);
                if ("".equals(errorMsg)) {
                    mEducationView.showError("登录成功");
                    Log.d(TAG,"登录成功");
                    BaseFragment.isEducationLogin=true;
                    BaseFragment.educationCookie=cookie;
                    PreferencesUtils.setEducationPassword(username,password,true,true);
                    mEducationView.successLogin();
                } else {
//                    mEducationView.showError(errorMsg);
                    mEducationView.showRetry();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                loadFailure();
                BaseFragment.isEducationLogin=false;
                ErrorBundle errorBundle = new DefaultErrorBundle((Exception) t);
                String errorMsg = ErrorMessageFactory.creat(mEducationView.context(), errorBundle.getException());
                mEducationView.showError(errorMsg);
                mEducationView.successLogin();
            }
        });
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        mEducationView=null;
    }

    @Override
    public void loadFailure() {
        mEducationView.hideLoading();
        mEducationView.showRetry();

    }

    @Override
    public void loadSuccess() {
        mEducationView.hideLoading();
        mEducationView.hideRetry();
    }

    @Override
    public void loading() {
        mEducationView.hideRetry();
        mEducationView.showLoading();
    }
}
