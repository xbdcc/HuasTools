package com.xiaobudian.huastools.ui.presenter;

import android.app.ProgressDialog;
import android.util.Log;

import com.xiaobudian.huastools.R;
import com.xiaobudian.huastools.data.EducationApi;
import com.xiaobudian.huastools.exception.DefaultErrorBundle;
import com.xiaobudian.huastools.exception.ErrorBundle;
import com.xiaobudian.huastools.exception.ErrorMessageFactory;
import com.xiaobudian.huastools.ui.view.CourseSelectionView;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 小不点 on 2016/6/20.
 */
public class CourseSelectionPresenter implements BasePresenter{

    private static final String TAG="CourseSelectionPresent";

    private ProgressDialog dialog;
    private int dialog_type=1;//1为对话框在里面，2为对话框在上面
    private String url;

    @Inject
    EducationApi mEducationApi;
    private CourseSelectionView mCourseSelectionView;

    public CourseSelectionPresenter(EducationApi educationApi, CourseSelectionView courseSelectionView) {
        mEducationApi = educationApi;
        mCourseSelectionView = courseSelectionView;
    }

    /**
     * 获取选课数量
     * @param cookie
     */
    public void getCourseSelectionCount(String cookie){
        dialog_type=1;
        loading();
        url=EducationApi.BASE_URL+"jsxsd/xk/AccessToXk?Ves632DSdyV=NEW_XSD_PYGL";
        Call<String> call = mEducationApi.getCourseSelectionMain(cookie,url);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String body = response.body().toString();
                if(response.code()==200){
                    mCourseSelectionView.parseData(body);
                    Log.e(TAG,body.toString());
                    mCourseSelectionView.showView();
                }else{
                    loadFailure();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                loadFailure();
                ErrorBundle errorBundle = new DefaultErrorBundle((Exception) t);
                String errorMsg = ErrorMessageFactory.creat(mCourseSelectionView.context(), errorBundle.getException());
                mCourseSelectionView.showError(errorMsg);
                mCourseSelectionView.showRetry();
            }
        });
    }

    /**
     * 获取选课列表
     * @param cookie
     */
    public void getCourseSelectionList(String cookie,String url){
        loading();
        Call<String> call = mEducationApi.getCourseSelectionList(cookie,url);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String body = response.body().toString();
                if(response.code()==200){
                    mCourseSelectionView.parseList(body);
                    mCourseSelectionView.showView();
                    loadSuccess();
                }else{
                    loadFailure();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                loadFailure();
                ErrorBundle errorBundle = new DefaultErrorBundle((Exception) t);
                String errorMsg = ErrorMessageFactory.creat(mCourseSelectionView.context(), errorBundle.getException());
                mCourseSelectionView.showError(errorMsg);
                mCourseSelectionView.showRetry();
            }
        });
    }

    /**
     * 选课退选
     * @param cookie
     */
    public void getCourseSelectionSubmit(String cookie,String jx0404id,String jx0502id, String jx0502zbid,int type){
        dialog_type=2;
        loading();
        Call<String> call;
        if(type==1){
            call = mEducationApi.getCourseSubmit(cookie,jx0404id,jx0502id,jx0502zbid);
        }else {
            call = mEducationApi.getCourseQuit(cookie,jx0404id,jx0502id,jx0502zbid);
        }
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String body = response.body().toString();
                if(response.code()==200){
                    mCourseSelectionView.parseSubmit(body);
                    mCourseSelectionView.showView();
                    loadSuccess();
                }else{
                    loadFailure();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                loadFailure();
                ErrorBundle errorBundle = new DefaultErrorBundle((Exception) t);
                String errorMsg = ErrorMessageFactory.creat(mCourseSelectionView.context(), errorBundle.getException());
                mCourseSelectionView.showError(errorMsg);
                mCourseSelectionView.showRetry();
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
        if(dialog_type==1){
            mCourseSelectionView.hideLoading();
            mCourseSelectionView.showRetry();
        }else {
            dialog.dismiss();
        }
    }

    @Override
    public void loadSuccess() {
        if(dialog_type==1){
            mCourseSelectionView.hideLoading();
            mCourseSelectionView.hideRetry();
        }else {
            dialog.dismiss();
        }
    }

    @Override
    public void loading() {
        if(dialog_type==1){
            mCourseSelectionView.hideRetry();
            mCourseSelectionView.showLoading();
        }else {
            dialog= ProgressDialog.show(mCourseSelectionView.context(), "",mCourseSelectionView.context().getString(R.string.data_loading),true,true);
        }
    }
}
