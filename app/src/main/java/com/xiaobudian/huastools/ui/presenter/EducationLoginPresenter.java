package com.xiaobudian.huastools.ui.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.xiaobudian.huastools.data.EducationApi;
import com.xiaobudian.huastools.data.GloabField;
import com.xiaobudian.huastools.data.parse.ScoreParse;
import com.xiaobudian.huastools.exception.DefaultErrorBundle;
import com.xiaobudian.huastools.exception.ErrorBundle;
import com.xiaobudian.huastools.exception.ErrorMessageFactory;
import com.xiaobudian.huastools.ui.fragment.BaseFragment;
import com.xiaobudian.huastools.ui.view.EducationLoginView;
import com.xiaobudian.huastools.util.PreferencesUtils;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by caochang on 2016/4/18.
 */
public class EducationLoginPresenter implements BasePresenter{

    private static final String TAG="EducationLoginPresenter";

    @Inject
    EducationApi mEducationApi;

    private GloabField.Status cookie_status= GloabField.Status.STATE_NO_RESPONSE;
    private EducationLoginView mEducationLoginView;
    private String cookie=null;

    public EducationLoginPresenter(EducationLoginView educationLoginView, EducationApi educationApi) {
        mEducationLoginView = educationLoginView;
        mEducationApi=educationApi;
    }

    public void getCookie(int type) {
        if(type==1){
            loading();
        }
        Log.d(TAG,"获取cookie中");
        Call<String> call=mEducationApi.getCookie();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String headers=response.headers().toString();
                if (TextUtils.isEmpty(headers)||!headers.contains("Set-Cookie")){
                    setCookie_status(GloabField.Status.STATE_FAILURE);
                    mEducationLoginView.showError("连接服务器失败");
                    loadFailure();
                    Log.d(TAG,"获取cookie失败");
                } else {
                    cookie=response.headers().get("Set-Cookie");
                    setCookie_status(GloabField.Status.STATE_OK);
                    mEducationLoginView.showError("获取Cookie成功/连接服务器成功");
                    Log.d(TAG,"获取cookie成功");
                    mEducationLoginView.successGetCookie();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                cookie = null;
                setCookie_status(GloabField.Status.STATE_FAILURE);
                ErrorBundle errorBundle = new DefaultErrorBundle((Exception) t);
                String errorMsg = ErrorMessageFactory.creat(mEducationLoginView.context(), errorBundle.getException());
                Log.e(TAG,"获取cookie失败"+errorMsg);
                mEducationLoginView.showError(errorMsg);
                loadFailure();
                mEducationLoginView.showMessage();
            }
        });
    }


    public void login(final String username, final String password,int type) {
        if(cookie==null){
            getCookie(type);
        }
        else if(type==1){
            Call<String> call = mEducationApi.login(cookie, username, password);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    loadSuccess();
                    String body = response.body().toString();
                    String errorMsg = new ScoreParse().parseLoginResult(body);
                    if ("".equals(errorMsg)) {
                        mEducationLoginView.showError("登录成功");
                    BaseFragment.educationCookie=cookie;
                        PreferencesUtils.setEducationPassword(username,password,true,true);
                        mEducationLoginView.showMessage();
                        mEducationLoginView.successLogin();
                    } else {
                        mEducationLoginView.showMessage();
                        mEducationLoginView.showError(errorMsg);
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    loadFailure();
                    ErrorBundle errorBundle = new DefaultErrorBundle((Exception) t);
                    String errorMsg = ErrorMessageFactory.creat(mEducationLoginView.context(), errorBundle.getException());
                    mEducationLoginView.showError(errorMsg);
                    mEducationLoginView.showMessage();
                    mEducationLoginView.successLogin();
                }
            });
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void loadFailure() {
        mEducationLoginView.hideLoading();
    }

    @Override
    public void loadSuccess() {
        mEducationLoginView.hideLoading();
    }

    @Override
    public void loading() {
        mEducationLoginView.showLoading();
        mEducationLoginView.hideMessage();
    }

    public GloabField.Status getCookie_status() {
        return cookie_status;
    }

    public void setCookie_status(GloabField.Status cookie_status) {
        this.cookie_status = cookie_status;
    }
}
