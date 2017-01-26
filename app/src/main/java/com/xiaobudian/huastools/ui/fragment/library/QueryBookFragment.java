package com.xiaobudian.huastools.ui.fragment.library;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.xiaobudian.huastools.AppComponent;
import com.xiaobudian.huastools.R;
import com.xiaobudian.huastools.data.LibraryApi;
import com.xiaobudian.huastools.model.Book;
import com.xiaobudian.huastools.ui.adapter.QueryBookAdapter;
import com.xiaobudian.huastools.ui.component.DaggerLibraryComponent;
import com.xiaobudian.huastools.ui.fragment.BaseFragment;
import com.xiaobudian.huastools.ui.module.LibraryModule;
import com.xiaobudian.huastools.ui.presenter.QueryBookPresenter;
import com.xiaobudian.huastools.ui.view.QueryBookView;
import com.xiaobudian.huastools.util.DividerItemDecoration;
import com.xiaobudian.huastools.util.ToastUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by caochang on 2016/4/18.
 */
public class QueryBookFragment extends BaseFragment implements QueryBookView {

    @Bind(R.id.et_name)
    EditText mEtName;
    @Bind(R.id.b_search)
    Button mBSearch;
    @Bind(R.id.rg_type)
    RadioGroup mRgType;
    @Bind(R.id.rb_book_title)
    RadioButton mRbBookTitle;
    @Bind(R.id.rb_book_author)
    RadioButton mRbBookAuthor;
    @Bind(R.id.rb_book_theme)
    RadioButton mRbBookTheme;
    @Bind(R.id.rb_book_isbn)
    RadioButton mRbBookIsbn;
    @Bind(R.id.pb_loading)
    ProgressBar mPbLoading;
    @Bind(R.id.tv_message)
    TextView mTvMessage;
    @Bind(R.id.rv_book)
    RecyclerView mRvBook;

    @Inject
    LibraryApi mLibraryApi;
    @Bind(R.id.b_book_last_page)
    Button mBBookLastPage;
    @Bind(R.id.b_book_next_page)
    Button mBBookNextPage;
    @Bind(R.id.scrollView2)
    ScrollView mScrollView2;
    @Bind(R.id.ll_page)
    LinearLayout mLlPage;
    private QueryBookPresenter mQueryBookPresenter;

    private String title = "";
    private String type = "";
    private String last_url = "";
    private String next_url = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_querybook, container, false);
        ButterKnife.bind(this, view);

        mQueryBookPresenter = new QueryBookPresenter(mLibraryApi, this);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvBook.setLayoutManager(manager);
        mRvBook.setNestedScrollingEnabled(false);//ScrollView 滑动顺滑
        mRvBook.addItemDecoration(new DividerItemDecoration(context(), DividerItemDecoration.VERTICAL_LIST));
        return view;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerLibraryComponent.builder()
                .appComponent(appComponent)
                .libraryModule(new LibraryModule(this))
                .build()
                .inject(this);
    }


    @Override
    public void renderBook(List<Book> books, String result) {
        QueryBookAdapter queryBookAdapter = new QueryBookAdapter();
        queryBookAdapter.setBooks(books);
        mRvBook.setAdapter(queryBookAdapter);

        showPage();
        Document document = Jsoup.parse(result, "UTF-8");
        Elements elements = document.select("div[class=pagenum]").select("td");//页码部分
        String url = "http://220.168.209.130:9999/";
        last_url = url + elements.get(0).select("a").attr("href");
        next_url = url + elements.get(2).select("a").attr("href");
    }

    @Override
    public void showRecycleView() {
        mRvBook.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRecycleView() {
        mRvBook.setVisibility(View.GONE);
    }

    @Override
    public void showPage() {
        mLlPage.setVisibility(View.VISIBLE);
    }

    @Override
    public void hidePage() {
        mLlPage.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        mPbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mPbLoading.setVisibility(View.GONE);
    }

    @Override
    public void showRetry() {
        mTvMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetry() {
        mTvMessage.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        mTvMessage.setText(message);
    }

    @Override
    public Context context() {
        return getActivity();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.b_search, R.id.b_book_last_page, R.id.b_book_next_page})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.b_search:
                title = mEtName.getText().toString();
                mRgType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.rb_book_title:
                                type = "4";
                                break;
                            case R.id.rb_book_author:
                                type = "5";
                                break;
                            case R.id.rb_book_theme:
                                type = "7";
                                break;
                            case R.id.rb_book_isbn:
                                type = "3";
                                break;
                        }
                    }
                });
                if (title.equals("")) {
                    ToastUtil.showToast(getActivity(), R.string.can_not_null);
                } else {
                    mQueryBookPresenter.queryBook(title, type);
                }
                break;
            case R.id.b_book_last_page:
                if (title.equals("")) {
                    ToastUtil.showToast(getActivity(), R.string.can_not_null);
                } else {
                    mQueryBookPresenter.changePage(last_url);
                }
                break;
            case R.id.b_book_next_page:
                if (title.equals("")) {
                    ToastUtil.showToast(getActivity(), R.string.can_not_null);
                } else {
                    mQueryBookPresenter.changePage(next_url);
                }
                break;
        }
    }

//    @OnClick(R.id.b_search)
//    public void onClick() {
//        title = mEtName.getText().toString();
//        mRgType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch (checkedId){
//                    case R.id.rb_book_title:
//                        type="4";
//                        break;
//                    case R.id.rb_book_author:
//                        type="5";
//                        break;
//                    case R.id.rb_book_theme:
//                        type="7";
//                        break;
//                    case R.id.rb_book_isbn:
//                        type="3";
//                        break;
//                }
//            }
//        });
//        if(title.equals("")){
//            ToastUtil.showToast(getActivity(),R.string.can_not_null);
//        }else {
//            mQueryBookPresenter.queryBook(title,type);
//        }
//    }
}