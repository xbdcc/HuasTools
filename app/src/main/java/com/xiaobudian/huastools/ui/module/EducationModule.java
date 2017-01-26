package com.xiaobudian.huastools.ui.module;

import com.xiaobudian.huastools.data.EducationApi;
import com.xiaobudian.huastools.ui.ActivityScope;
import com.xiaobudian.huastools.ui.FragmentScope;
import com.xiaobudian.huastools.ui.activity.education.EducationLoginActivity;
import com.xiaobudian.huastools.ui.activity.education.EvaluateDetailActivity;
import com.xiaobudian.huastools.ui.fragment.education.CourseSelectionFragment;
import com.xiaobudian.huastools.ui.fragment.education.EducationFragment;
import com.xiaobudian.huastools.ui.fragment.education.EvaluateFragment;
import com.xiaobudian.huastools.ui.fragment.education.ScoreFragment;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by caochang on 2016/4/18.
 */
@Module
public class EducationModule {

    private EducationFragment mEducationFragment;
    private EducationLoginActivity mEducationLoginActivity;
    private ScoreFragment mScoreFragment;
    private CourseSelectionFragment mCourseSelectionFragment;
    private EvaluateFragment mEvaluateFragment;
    private EvaluateDetailActivity mEvaluateDetailActivity;

    public EducationModule(EducationFragment educationFragment){
        mEducationFragment=educationFragment;
    }

    public EducationModule(EducationLoginActivity educationLoginActivity){
        mEducationLoginActivity=educationLoginActivity;
    }

    public EducationModule(ScoreFragment scoreFragment){
        mScoreFragment=scoreFragment;
    }

    public EducationModule(CourseSelectionFragment courseSelectionFragment){
        mCourseSelectionFragment=courseSelectionFragment;
    }

    public EducationModule(EvaluateFragment evaluateFragment){
        mEvaluateFragment=evaluateFragment;
    }

    public EducationModule(EvaluateDetailActivity evaluateDetailActivity){
        mEvaluateDetailActivity=evaluateDetailActivity;
    }

    @Provides
    @FragmentScope
    EducationFragment provideEducationFragment(){
        return mEducationFragment;
    }

    @Provides
    @ActivityScope
    EducationLoginActivity provideEducationLoginActivity(){
        return mEducationLoginActivity;
    }

    @Provides
    @FragmentScope
    ScoreFragment provideScoreFragment(){
        return mScoreFragment;
    }

    @Provides
    @FragmentScope
    CourseSelectionFragment provideCourseSelectionFragment(){
        return mCourseSelectionFragment;
    }

    @Provides
    @FragmentScope
    EvaluateFragment provideEvaluateFragment(){return mEvaluateFragment;}

    @Provides
    @ActivityScope
    EvaluateDetailActivity provideEvaluateDetailActivity(){return mEvaluateDetailActivity;}

    @Provides
    @ActivityScope
    EducationApi provideEducationService(){

        OkHttpClient client=new OkHttpClient.Builder()
                .connectTimeout(7, TimeUnit.SECONDS)
                .build();//设置15秒超时

        Retrofit retrofit=new Retrofit.Builder()
                .client(client)
                .baseUrl(EducationApi.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        return  retrofit.create(EducationApi.class);
    }
}
