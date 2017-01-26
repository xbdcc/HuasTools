package com.xiaobudian.huastools.ui.presenter;

import android.util.Log;

import com.xiaobudian.huastools.data.EducationApi;
import com.xiaobudian.huastools.data.parse.ScoreParse;
import com.xiaobudian.huastools.exception.DefaultErrorBundle;
import com.xiaobudian.huastools.exception.ErrorBundle;
import com.xiaobudian.huastools.exception.ErrorMessageFactory;
import com.xiaobudian.huastools.ui.view.EvaluateDetailView;
import com.xiaobudian.huastools.util.ToastUtil;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 小不点 on 2016/6/11.
 */
public class EvaluateDetailPresenter implements BasePresenter{

    private static final String TAG ="EvaluateDetailPresenter" ;
    @Inject
    EducationApi mEducationApi;
    private EvaluateDetailView mEvaluateDetailView;

    public EvaluateDetailPresenter(EducationApi educationApi, EvaluateDetailView evaluateDetailView) {
        mEducationApi = educationApi;
        mEvaluateDetailView = evaluateDetailView;
    }

    public void loadData(String cookie,String url){
        loading();
        Log.d(TAG,"获取教务系统评教详情中");
        Call call= mEducationApi.getEvaluateDetail(cookie,url);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.code()==200){
                    loadSuccess();
                    String html=response.body().toString();
                    String message=ScoreParse.parseLoginResult(html);
                    if(message==null){
                        mEvaluateDetailView.parseData(html);
                    }else{
                        ToastUtil.showToast(mEvaluateDetailView.context(),message);
                    }
//                    mReplyView.showView();
                }else{
                    loadFailure();

                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                loadFailure();
                ErrorBundle errorBundle=new DefaultErrorBundle((Exception) t);
                String errMsg= ErrorMessageFactory.creat(mEvaluateDetailView.context(),errorBundle.getException());
                Log.e(TAG,"获取评教详情信息失败---"+errMsg);
                mEvaluateDetailView.showError(errMsg);
                mEvaluateDetailView.showRetry();
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
        mEvaluateDetailView.hideView();
        mEvaluateDetailView.hideLoading();
        mEvaluateDetailView.showRetry();
    }

    @Override
    public void loadSuccess() {
        mEvaluateDetailView.hideLoading();
        mEvaluateDetailView.showView();
        mEvaluateDetailView.hideRetry();
    }

    @Override
    public void loading() {
        mEvaluateDetailView.hideView();
        mEvaluateDetailView.hideRetry();
        mEvaluateDetailView.showLoading();
    }
}
