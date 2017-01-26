package com.xiaobudian.huastools.ui.fragment.library;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaobudian.huastools.R;
import com.xiaobudian.huastools.data.RenewBookApi;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by caochang on 2016/4/18.
 */
public class RenewBookFragment extends Fragment {


    private static final String TAG = "RenewBookFragment";
    String cookie;

    private Retrofit retrofitCookie, retrofitLogin;
    private RenewBookApi renewBookApiCookie, renewBookApiLogin;
    OkHttpClient okHttpClient;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_renew_book, container, false);

        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(7, TimeUnit.SECONDS)
                .build();

        retrofitCookie = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(RenewBookApi.BASE_URL_COOKIE)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        renewBookApiCookie = retrofitCookie.create(RenewBookApi.class);

        Call call = renewBookApiCookie.getCookie();
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.e(TAG, "onResponse: "+response.headers());
                Log.e(TAG, "onResponse: "+response.headers().name(0));
                Log.e(TAG, "onResponse: "+response.headers().value(3));
                Log.e(TAG, "onResponse: "+response.headers().size());

                for (int i = 0 ;i < response.headers().size(); i ++) {
                    String setCookie = response.headers().value(i);
                    if (setCookie.contains("JSESSIONID")){
                        cookie =setCookie;
                        break;
                    }
                }
//                cookie = response.headers().get("Set-Cookie");
                Log.d(TAG, "cookie=" + cookie);
//                Log.d(TAG, "body=" + response.body().toString());
                login(cookie);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d(TAG, "获取cookie失败");
            }
        });


//        call=renewBookApi.getCookie();

        return view;
    }

    public void login(String cookie) {
        String username = "42011719940103233X";
        String password = "00fbfbbvb00";
        String schoolid = "53";
        String userType = "0";
        String backurl = "";
        retrofitLogin = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(RenewBookApi.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        renewBookApiLogin = retrofitLogin.create(RenewBookApi.class);
        Call call2 = renewBookApiLogin.login(cookie, username, password, schoolid, userType, backurl);
        call2.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.d(TAG, "--------------------------------------");
//                String location=response.headers().get("Location");
                Log.d(TAG, response.headers().toString());
                Log.d(TAG, "code=" + response.code());
//                String body = response.body().toString();
//                Log.d(TAG, body);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d(TAG, "登录失败");
            }
        });
    }
}