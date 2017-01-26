package com.xiaobudian.huastools.ui.presenter;

import android.util.Log;

import com.xiaobudian.huastools.data.SunshineApi;
import com.xiaobudian.huastools.data.parse.ReplyParse;
import com.xiaobudian.huastools.exception.DefaultErrorBundle;
import com.xiaobudian.huastools.exception.ErrorBundle;
import com.xiaobudian.huastools.exception.ErrorMessageFactory;
import com.xiaobudian.huastools.ui.view.ReplyView;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 小不点 on 2016/4/23.
 */
public class ReplyPresenter implements BasePresenter{

    private static final String TAG ="ReplyPresenter" ;
    @Inject
    SunshineApi mSunshineApi;
    private ReplyView mReplyView;

    public ReplyPresenter(SunshineApi sunshineApi, ReplyView replyView) {
        mSunshineApi = sunshineApi;
        mReplyView = replyView;
    }

    public void initView(){
        loading();
        Call call= mSunshineApi.getReplys();
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.code()==200){
                    loadSuccess();
                    String html=response.body().toString();
                    ReplyParse replyParse=new ReplyParse();
                    replyParse.parseAll(html);
                    mReplyView.renderAcceptUnit(replyParse.get_accepting_unit_id(),replyParse.get_accepting_unit_data());
                    mReplyView.renderCategory(replyParse.get_category_id(),replyParse.get_category_data());
                    mReplyView.renderReplyData(replyParse.getReplyDatas(),replyParse.get_details_html());
                    mReplyView.renderPage(replyParse.get_pages(),replyParse.get_page_id(),replyParse.get_page_data());
                    mReplyView.showView();
                }else{
                    loadFailure();
                    mReplyView.hideView();
                    mReplyView.showRetry();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                loadFailure();
                ErrorBundle errorBundle=new DefaultErrorBundle((Exception) t);
                String errMsg= ErrorMessageFactory.creat(mReplyView.context(),errorBundle.getException());
                Log.e(TAG,"获取我要说话信息失败---"+errMsg);
                mReplyView.showError(errMsg);
                mReplyView.showRetry();
            }
        });

    }


    public void query(String accepting_unit_id,String category_id,String title,String processcode,String page){
        mReplyView.hideResult();
        loading();
        Log.d(TAG,"accepting_unit_id:"+accepting_unit_id+"   category_id:"+category_id+"   title:"+title
                +"   processcode:"+processcode+"  page: "+page);
        Call call= mSunshineApi.getReplys(accepting_unit_id,category_id,title,processcode,page);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.code()==200){
                    loadSuccess();
                    mReplyView.showResult();
                    String html=response.body().toString();
                    ReplyParse replyParse=new ReplyParse();
                    replyParse.parseAll(html);
                    mReplyView.renderReplyData(replyParse.getReplyDatas(),replyParse.get_details_html());
                    mReplyView.renderPage(replyParse.get_pages(),replyParse.get_page_id(),replyParse.get_page_data());
                }else{
                    loadFailure();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                loadFailure();
                mReplyView.hideResult();
                ErrorBundle errorBundle=new DefaultErrorBundle((Exception) t);
                String errMsg= ErrorMessageFactory.creat(mReplyView.context(),errorBundle.getException());
                Log.e(TAG,"获取查看回复信息失败---"+errMsg);
                mReplyView.showError(errMsg);
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
        mSunshineApi=null;
    }

    @Override
    public void loadFailure() {
        mReplyView.hideLoading();
        mReplyView.showMessage();
    }

    @Override
    public void loadSuccess() {
        mReplyView.hideMessage();
        mReplyView.hideLoading();
    }

    @Override
    public void loading() {
        mReplyView.hideMessage();
        mReplyView.showLoading();
    }
}
