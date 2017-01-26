package com.xiaobudian.huastools.ui.module;

import com.xiaobudian.huastools.data.LibraryApi;
import com.xiaobudian.huastools.ui.ActivityScope;
import com.xiaobudian.huastools.ui.FragmentScope;
import com.xiaobudian.huastools.ui.fragment.library.QueryBookFragment;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by 小不点 on 2016/4/21.
 */
@Module
public class LibraryModule {

    private QueryBookFragment mQueryBookFragment;
    public LibraryModule(QueryBookFragment queryBookFragment){
        mQueryBookFragment=queryBookFragment;
    }

    @Provides
    @FragmentScope
    QueryBookFragment provideQueryBookFragment(){
        return mQueryBookFragment;
    }


    @Provides
    @ActivityScope
    LibraryApi provideLibraryService(){
        OkHttpClient okHttpClient=new OkHttpClient.Builder()
                .connectTimeout(7, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit=new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(LibraryApi.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        return  retrofit.create(LibraryApi.class);
    }

}
