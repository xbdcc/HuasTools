package com.xiaobudian.huastools.ui.presenter;

import android.util.Log;

import com.xiaobudian.huastools.data.SunshineApi;
import com.xiaobudian.huastools.data.parse.ReplyParse;
import com.xiaobudian.huastools.exception.DefaultErrorBundle;
import com.xiaobudian.huastools.exception.ErrorBundle;
import com.xiaobudian.huastools.exception.ErrorMessageFactory;
import com.xiaobudian.huastools.model.ReplyDetail;
import com.xiaobudian.huastools.ui.view.ReplyDetailView;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 小不点 on 2016/4/26.
 */
public class ReplyDetailPresenter implements BasePresenter{

    private static final String TAG ="ReplyDetailPresenter" ;
    @Inject
    SunshineApi mSunshineApi;
    private ReplyDetailView mReplyDetailView;

    public ReplyDetailPresenter(SunshineApi sunshineApi, ReplyDetailView replyDetailView) {
        mSunshineApi = sunshineApi;
        mReplyDetailView = replyDetailView;
    }

    public void loadData(String url){
        loading();
        Call call=mSunshineApi.getReplysDetails(url);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.code()==200){
                    String html=response.body().toString();
                    Log.d(TAG,html);
                    ReplyParse replyParse=new ReplyParse();
                    List<String> replyDetails=replyParse.getReplyDetails(html);
                    ReplyDetail replyDetail=new ReplyDetail();
                    replyDetail.setLetter_theme(replyDetails.get(0));
                    replyDetail.setLetter_theme_show(replyDetails.get(1));
                    replyDetail.setLetter_unit(replyDetails.get(2));
                    replyDetail.setLetter_unit_show(replyDetails.get(3));
                    replyDetail.setLetter_category(replyDetails.get(4));
                    replyDetail.setLetter_category_show(replyDetails.get(5));
                    replyDetail.setLetter_grade(replyDetails.get(6));
                    replyDetail.setLetter_grade_show(replyDetails.get(7));
                    replyDetail.setSubmit_time(replyDetails.get(8));
                    replyDetail.setSubmit_time_show(replyDetails.get(9));
                    replyDetail.setSolve_time(replyDetails.get(10));
                    replyDetail.setSolve_time_show(replyDetails.get(11));
                    replyDetail.setClick_time(replyDetails.get(12));
                    replyDetail.setClick_time_show(replyDetails.get(13));
                    replyDetail.setLetter_content(replyDetails.get(14));
                    replyDetail.setLetter_content_show(replyDetails.get(15));
                    replyDetail.setCurrent_state(replyDetails.get(18));
                    replyDetail.setCurrent_state_show(replyDetails.get(19));
                    replyDetail.setReply_content(replyDetails.get(20));
                    replyDetail.setReply_content_show(replyDetails.get(21));
                    mReplyDetailView.initView(replyDetail);
                    loadSuccess();
                    Log.d(TAG,"获取回复详情成功");
                }else {
                    loadFailure();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                loadFailure();
                ErrorBundle errorBundle=new DefaultErrorBundle((Exception) t);
                String errMsg= ErrorMessageFactory.creat(mReplyDetailView.context(),errorBundle.getException());
                Log.e(TAG,"获取回复详情失败---"+errMsg);
                mReplyDetailView.showError(errMsg);
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
        mReplyDetailView.hideLoading();
        mReplyDetailView.showRetry();
    }

    @Override
    public void loadSuccess() {
        mReplyDetailView.hideLoading();
        mReplyDetailView.hideRetry();
    }

    @Override
    public void loading() {
        mReplyDetailView.showLoading();
        mReplyDetailView.hideRetry();
    }
}
