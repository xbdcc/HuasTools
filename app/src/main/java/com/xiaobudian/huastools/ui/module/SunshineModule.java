package com.xiaobudian.huastools.ui.module;

import com.xiaobudian.huastools.data.SunshineApi;
import com.xiaobudian.huastools.ui.ActivityScope;
import com.xiaobudian.huastools.ui.FragmentScope;
import com.xiaobudian.huastools.ui.activity.ReplyDetailActivity;
import com.xiaobudian.huastools.ui.fragment.sunshine.ReplyFragment;
import com.xiaobudian.huastools.ui.fragment.sunshine.TalkFragment;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by 小不点 on 2016/4/23.
 */
@Module
public class SunshineModule {

    private ReplyFragment mReplyFragment;
    private ReplyDetailActivity mReplyDetailActivity;
    private TalkFragment mTalkFragment;
    public SunshineModule(ReplyFragment replyFragment){
        mReplyFragment=replyFragment;
    }
    public SunshineModule(ReplyDetailActivity replyDetailActivity){mReplyDetailActivity=replyDetailActivity;}
    public SunshineModule(TalkFragment talkFragment){mTalkFragment=talkFragment;}

    @Provides
    @FragmentScope
    ReplyFragment provideReplyFragment(){
        return mReplyFragment;
    }

    @Provides
    @ActivityScope
    ReplyDetailActivity provideReplyDetailActivity(){
        return mReplyDetailActivity;
    }

   @Provides
   @FragmentScope
   TalkFragment provideTalkFragment(){
       return mTalkFragment;
   }

    @Provides
    @ActivityScope
    SunshineApi provideSunshineService(){
        OkHttpClient okHttpClient=new OkHttpClient.Builder()
                .connectTimeout(7, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit=new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(SunshineApi.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        return  retrofit.create(SunshineApi.class);
    }
}
