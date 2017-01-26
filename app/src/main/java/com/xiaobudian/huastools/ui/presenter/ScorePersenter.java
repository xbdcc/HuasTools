package com.xiaobudian.huastools.ui.presenter;

import android.util.Log;

import com.xiaobudian.huastools.data.EducationApi;
import com.xiaobudian.huastools.data.parse.ScoreParse;
import com.xiaobudian.huastools.exception.DefaultErrorBundle;
import com.xiaobudian.huastools.exception.ErrorBundle;
import com.xiaobudian.huastools.exception.ErrorMessageFactory;
import com.xiaobudian.huastools.model.Score;
import com.xiaobudian.huastools.ui.view.ScoreView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by caochang on 2016/4/19.
 */
public class ScorePersenter implements BasePresenter {

    private static final String TAG="ScorePersenter";

    @Inject
    EducationApi mEducationApi;
    private ScoreView mScoreView;

    public ScorePersenter(EducationApi educationApi, ScoreView scoreView) {
        mEducationApi = educationApi;
        mScoreView = scoreView;
    }

    /**
     * 获取查询条件，查询时显示progressbar，查询失败显示message和retry按钮，查询成功显示结果
     * @param cookie
     */
    public void getCourseCondition(String cookie) {
        Log.i(TAG, "getCourseCondition: ");
        mScoreView.hideRetry();
        loading();
        Log.d(TAG,"post_data:cookie"+cookie);
        Call<String> call = mEducationApi.getScoreCondition(cookie);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String body = response.body().toString();
                ScoreParse scoreParse=new ScoreParse();
                ArrayList<String> time = scoreParse.parseCourseTime(body);
                ArrayList<String> categorys=scoreParse.parseCourseCategoryData(body);
                ArrayList<String> types=scoreParse.parseCourseType(body);
                if (categorys.size() == 0) {
                    loadFailure();
                    mScoreView.showError("没有数据");
                    mScoreView.showRetry();
                } else {
                    mScoreView.renderScoreTime(scoreParse.getL_time_id(),scoreParse.getL_time_data());
                    mScoreView.renderScoreCategory(categorys);
                    mScoreView.renderScoreType(types);
                    loadSuccess();
                    mScoreView.showView();
                }
//                Log.i(TAG, "onResponse: " + cookie);
                Log.e(TAG, "onResponse:Code =" + response.code());
                Log.e(TAG, "onResponse:Headers =" + response.headers());
                Log.e(TAG, "onResponse:Body =" + response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                loadFailure();
                ErrorBundle errorBundle = new DefaultErrorBundle((Exception) t);
                String errorMsg = ErrorMessageFactory.creat(mScoreView.context(), errorBundle.getException());
                mScoreView.showError(errorMsg);
                mScoreView.showRetry();
            }
        });
    }

    /**
     * 查询课表结果，查询时显示progressbar，查询失败显示message，查询成功显示结果
     * @param cookie
     * @param time
     * @param category
     * @param name
     * @param type
     */
    public void getCourseResult(String cookie,String time,String category,String name,String type){
        Log.i(TAG, "getCourseResult: cookie"+cookie+" time:"+time+" category:"+category+" name:"+name+" type"+type);
        loading();
        Call<String> call = mEducationApi.searchCourse(cookie,time, category, name, type);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                List<Score> scoreList;
                Log.d(TAG,"----------"+response.body());
                scoreList = new ScoreParse().parseScore(response.body());
                if (scoreList == null) {
                    loadFailure();
                    mScoreView.showError("解析失败");
                    Log.d(TAG,"解析失败");
                } else if (scoreList.size() == 0) {
                    loadFailure();
                    mScoreView.showError(" 没有课程");
                    Log.d(TAG,"没有课程");
                } else {
                    loadSuccess();
                    mScoreView.renderScore(scoreList);
                    Log.d(TAG,"查询成绩成功");
//                    scoreActivityView.showError(courseName + " 的结果");
                }
//                Log.i(TAG, "onResponse: " + cookie);
                Log.e(TAG, "onResponse:Code =" + response.code());
                Log.e(TAG, "onResponse:Headers =" + response.headers());
                Log.e(TAG, "onResponse:Body =" + response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                loadFailure();
                ErrorBundle errorBundle = new DefaultErrorBundle((Exception) t);
                String errorMsg = ErrorMessageFactory.creat(mScoreView.context(), errorBundle.getException());
                mScoreView.showError(errorMsg);
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
//        mScoreView=null;
    }

    @Override
    public void loadFailure() {
        mScoreView.hideRecycleView();
        mScoreView.hideLoading();
        mScoreView.showMessage();
    }

    @Override
    public void loadSuccess() {
        mScoreView.hideMessage();
        mScoreView.hideLoading();
        mScoreView.showRecycleView();
    }

    @Override
    public void loading() {
        mScoreView.hideRecycleView();
        mScoreView.hideMessage();
        mScoreView.showLoading();
    }

}
