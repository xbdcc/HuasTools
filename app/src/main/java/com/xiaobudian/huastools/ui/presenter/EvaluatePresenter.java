package com.xiaobudian.huastools.ui.presenter;

import android.util.Log;
import android.widget.ListView;

import com.xiaobudian.huastools.data.EducationApi;
import com.xiaobudian.huastools.exception.DefaultErrorBundle;
import com.xiaobudian.huastools.exception.ErrorBundle;
import com.xiaobudian.huastools.exception.ErrorMessageFactory;
import com.xiaobudian.huastools.ui.view.EvaluateView;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 小不点 on 2016/6/11.
 */
public class EvaluatePresenter implements BasePresenter {

    private static final String TAG="EvaluatePresenter";

    @Inject
    EducationApi mEducationApi;
    private EvaluateView mEvaluateView;

    public EvaluatePresenter(EducationApi educationApi, EvaluateView evaluateView) {
        mEducationApi = educationApi;
        mEvaluateView = evaluateView;
    }
    

    public void getEvaluateCount(String cookie){
        loading();
        Call<String> call = mEducationApi.getEvaluateMain(cookie);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String body = response.body().toString();
                if(response.code()==200){
                    mEvaluateView.parseData(body);
                    mEvaluateView.showView();
                    loadSuccess();
                }else{
                    loadFailure();
                }
                Log.e(TAG, "onResponse:Code =" + response.code());
                Log.e(TAG, "onResponse:Headers =" + response.headers());
                Log.e(TAG, "onResponse:Body =" + response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                loadFailure();
                ErrorBundle errorBundle = new DefaultErrorBundle((Exception) t);
                String errorMsg = ErrorMessageFactory.creat(mEvaluateView.context(), errorBundle.getException());
                mEvaluateView.showError(errorMsg);
                mEvaluateView.showRetry();
            }
        });
    }

    public void getEvaluateList(String cookie,ListView listView,String url){
        loading();
        Call<String> call = mEducationApi.getEvaluateList(cookie,url);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String body = response.body().toString();
                if(response.code()==200){
                    mEvaluateView.parseList(listView,body);
                    loadSuccess();
                }else {
                    loadFailure();
                }
                Log.e(TAG, "onResponse:Code =" + response.code());
                Log.e(TAG, "onResponse:Headers =" + response.headers());
                Log.e(TAG, "onResponse:Body =" + response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                loadFailure();
                ErrorBundle errorBundle = new DefaultErrorBundle((Exception) t);
                String errorMsg = ErrorMessageFactory.creat(mEvaluateView.context(), errorBundle.getException());
                mEvaluateView.showError(errorMsg);
                mEvaluateView.showRetry();
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

    }

    @Override
    public void loadFailure() {
        mEvaluateView.hideLoading();
        mEvaluateView.showMessage();
    }

    @Override
    public void loadSuccess() {
        mEvaluateView.hideMessage();
        mEvaluateView.hideLoading();
    }

    @Override
    public void loading() {
        mEvaluateView.hideMessage();
        mEvaluateView.showLoading();
    }
}
