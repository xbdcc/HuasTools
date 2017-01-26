package com.xiaobudian.huastools.ui.presenter;


import android.util.Log;

import com.xiaobudian.huastools.data.LibraryApi;
import com.xiaobudian.huastools.data.parse.QueryBookParse;
import com.xiaobudian.huastools.exception.DefaultErrorBundle;
import com.xiaobudian.huastools.exception.ErrorBundle;
import com.xiaobudian.huastools.exception.ErrorMessageFactory;
import com.xiaobudian.huastools.ui.fragment.library.QueryBookFragment;
import com.xiaobudian.huastools.ui.view.QueryBookView;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 小不点 on 2016/4/21.
 */
public class QueryBookPresenter implements BasePresenter{

    private static final String TAG = "QueryBookPresenter";
    @Inject
    LibraryApi mLibraryApi;
    private QueryBookView mQueryBookView;
    private QueryBookFragment mQueryBookFragment;

    public QueryBookPresenter(LibraryApi libraryApi, QueryBookView queryBookView) {
        mLibraryApi = libraryApi;
        mQueryBookView = queryBookView;
    }

    public void queryBook(String title,String type){
        Log.d(TAG,"queryBook");
        loading();
        Call call=mLibraryApi.getBook(title,"4",type);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                String result=response.body().toString();
                Log.d(TAG,"成功查书");
                loadSuccess();
                QueryBookParse queryBookParse=new QueryBookParse();
                queryBookParse.getBook(result);
                mQueryBookView.showError(queryBookParse.getMessage());
                mQueryBookView.renderBook(queryBookParse.getBooks(),result);
//                Log.i(TAG,result);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                loadFailure();
                ErrorBundle errorBundle=new DefaultErrorBundle((Exception) t);
                String errMsg= ErrorMessageFactory.creat(mQueryBookView.context(),errorBundle.getException());
                Log.e(TAG,"查书失败---"+errMsg);
                mQueryBookView.showError(errMsg);
            }
        });

    }


    public void changePage(String url){
        Log.d(TAG,"queryBook");
        loading();
        mQueryBookView.hidePage();
        Call call=mLibraryApi.getBookChangePage(url);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                String result=response.body().toString();
                Log.d(TAG,"页码更换");
                if(response.code()==200){
                    loadSuccess();
                    mQueryBookView.showPage();
                    QueryBookParse queryBookParse=new QueryBookParse();
                    queryBookParse.getBook(result);
                    mQueryBookView.showError(queryBookParse.getMessage());
                    mQueryBookView.renderBook(queryBookParse.getBooks(),result);
                }else {
                    loadFailure();
                }
//                Log.i(TAG,result);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                loadFailure();
                ErrorBundle errorBundle=new DefaultErrorBundle((Exception) t);
                String errMsg= ErrorMessageFactory.creat(mQueryBookView.context(),errorBundle.getException());
                Log.e(TAG,"查书失败---"+errMsg);
                mQueryBookView.showError(errMsg);
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
        mQueryBookView.hideLoading();
        mQueryBookView.showRetry();
        mQueryBookView.showRecycleView();
    }

    @Override
    public void loadSuccess() {
        mQueryBookView.hideLoading();
        mQueryBookView.showRetry();
        mQueryBookView.showRecycleView();
    }

    @Override
    public void loading() {
        mQueryBookView.hideRetry();
        mQueryBookView.hideRecycleView();
        mQueryBookView.showLoading();
    }
}
