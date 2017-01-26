package com.xiaobudian.huastools.ui.presenter;

import android.util.Log;

import com.xiaobudian.huastools.data.SunshineApi;
import com.xiaobudian.huastools.data.parse.TalkParse;
import com.xiaobudian.huastools.exception.DefaultErrorBundle;
import com.xiaobudian.huastools.exception.ErrorBundle;
import com.xiaobudian.huastools.exception.ErrorMessageFactory;
import com.xiaobudian.huastools.ui.view.TalkView;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 小不点 on 2016/4/24.
 */
public class TalkPresenter implements BasePresenter{

    private static final String TAG = "TalkPresenter";
    @Inject
    SunshineApi mSunshineApi;
    private TalkView mTalkView;

    public TalkPresenter(SunshineApi sunshineApi, TalkView talkView) {
        mSunshineApi = sunshineApi;
        mTalkView = talkView;
    }

    public void initView(){
        Call call=mSunshineApi.getTalk();
        loading();
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.code()==200){
                    loadSuccess();
                    String html=response.body().toString();
                    TalkParse talkParse=new TalkParse();
                    talkParse.parse_sayword(html);
                    mTalkView.renderAcceptUnit(talkParse.get_object_id(),talkParse.get_object_data());
                    mTalkView.showView();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                loadFailure();
                ErrorBundle errorBundle=new DefaultErrorBundle((Exception) t);
                String errMsg= ErrorMessageFactory.creat(mTalkView.context(),errorBundle.getException());
                Log.e(TAG,"获取我要说话信息失败---"+errMsg);
                mTalkView.showError(errMsg);
            }
        });
    }

    public void submit(String requestTyp,String deptno,String title,String content,String username,String email,String tel){
        Call call=mSunshineApi.getTalkResult(requestTyp,deptno,title,content,username,email,tel);
        loading();
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.code()==200){
                    loadSuccess();
                    String html=response.body().toString();
                    TalkParse talkParse=new TalkParse();
                    talkParse.parse_sayword_get_number(html);
//                    String[] message=talkParse.get_sayword_number();
//                    Log.d(TAG,"------------>"+message);
                    String result=talkParse.getResult();
                    mTalkView.showError(result);
                    mTalkView.showResult();
                }else{
                    Log.e(TAG,"提交我要说话失败");
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                loadFailure();
                ErrorBundle errorBundle=new DefaultErrorBundle((Exception) t);
                String errMsg= ErrorMessageFactory.creat(mTalkView.context(),errorBundle.getException());
                Log.e(TAG,"获取我要说话信息失败---"+errMsg);
                mTalkView.showError(errMsg);
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
        mTalkView=null;
    }

    @Override
    public void loadFailure() {
        mTalkView.hideLoading();
        mTalkView.showRetry();
    }

    @Override
    public void loadSuccess() {
        mTalkView.hideRetry();
        mTalkView.hideLoading();
    }

    @Override
    public void loading() {
        mTalkView.hideRetry();
        mTalkView.showLoading();
    }
}
