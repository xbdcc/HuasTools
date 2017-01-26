package com.xiaobudian.huastools.ui.fragment.sunshine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.xiaobudian.huastools.AppComponent;
import com.xiaobudian.huastools.R;
import com.xiaobudian.huastools.data.SunshineApi;
import com.xiaobudian.huastools.model.ReplyData;
import com.xiaobudian.huastools.ui.activity.ReplyDetailActivity;
import com.xiaobudian.huastools.ui.adapter.ReplyAdapter;
import com.xiaobudian.huastools.ui.component.DaggerSunshineComponent;
import com.xiaobudian.huastools.ui.fragment.BaseFragment;
import com.xiaobudian.huastools.ui.module.SunshineModule;
import com.xiaobudian.huastools.ui.presenter.ReplyPresenter;
import com.xiaobudian.huastools.ui.view.ReplyView;
import com.xiaobudian.huastools.util.DividerItemDecoration;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by caochang on 2016/4/18.
 */
public class ReplyFragment extends BaseFragment implements ReplyView {

    private static final String TAG = "ReplyFragment";
    @Bind(R.id.s_sunshine_accepting_unit)
    Spinner mSSunshineAcceptingUnit;
    @Bind(R.id.ll_view)
    LinearLayout mLlView;
    @Bind(R.id.s_sunshine_category)
    Spinner mSSunshineCategory;
    @Bind(R.id.et_sunshine_theme)
    EditText mEtSunshineTheme;
    @Bind(R.id.et_sunshine_acceptance_number)
    EditText mEtSunshineAcceptanceNumber;
    @Bind(R.id.b_sunshine_submit)
    Button mBSunshineSubmit;
    @Bind(R.id.rv_sunshine_main)
    RecyclerView mRvSunshineMain;
    @Bind(R.id.tv_sunshine_current_page)
    TextView mTvSunshineCurrentPage;
    @Bind(R.id.s_sunshine_select_page)
    Spinner mSSunshineSelectPage;
    @Bind(R.id.tv_sunshine_total_page)
    TextView mTvSunshineTotalPage;
    @Bind(R.id.pb_loading)
    ProgressBar mPbLoading;
    @Bind(R.id.tv_result)
    TextView mTvResult;
    @Bind(R.id.ll_result)
    LinearLayout mLlResult;
    @Bind(R.id.b_retry)
    Button mBRetry;

    @Inject
    SunshineApi mSunshineApi;
    private ReplyPresenter mReplyPresenter;

    private String sunshine_html;
    private String accepting_unit_id = "";
    private String category_id = "";
    private String theme = "";
    private String processcode = "";
    private String page_id = "";
    private boolean isSelected;
    private int mPostion=0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_reply, container, false);
        ButterKnife.bind(this, view);

        mReplyPresenter = new ReplyPresenter(mSunshineApi, this);
        hideView();

        initView();
        return view;
    }

    public void initView() {
        mReplyPresenter.initView();
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerSunshineComponent.builder()
                .appComponent(appComponent)
                .sunshineModule(new SunshineModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.b_sunshine_submit, R.id.b_retry})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.b_sunshine_submit:
                page_id = "1";
                mPostion=0;
                theme = mEtSunshineTheme.getText().toString();
                processcode = mEtSunshineAcceptanceNumber.getText().toString();
                mReplyPresenter.query(accepting_unit_id, category_id, theme, processcode, page_id);
                break;
            case R.id.b_retry:
                hideRetry();
                initView();
                break;
        }
    }

    @Override
    public void showView() {
        mLlView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideView() {
        mLlView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void renderAcceptUnit(List<String> list_id, List<String> list_data) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context(), android.R.layout.simple_spinner_dropdown_item, list_data);
        mSSunshineAcceptingUnit.setAdapter(adapter);
        mSSunshineAcceptingUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                accepting_unit_id = list_id.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void renderCategory(List<String> list_id, List<String> list_data) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context(), android.R.layout.simple_spinner_dropdown_item, list_data);
        mSSunshineCategory.setAdapter(adapter);
        mSSunshineCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category_id = list_id.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void renderPage(String[] pages, List<String> l_page_id, List<String> l_page_data) {
        mTvSunshineCurrentPage.setText(pages[0]);
        mTvSunshineTotalPage.setText(pages[3]);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context(), android.R.layout.simple_spinner_dropdown_item, l_page_data);
        mSSunshineSelectPage.setAdapter(adapter);
        mSSunshineSelectPage.setSelection(mPostion);
        Log.d(TAG,"----------------position"+mPostion);
        mSSunshineSelectPage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                page_id = l_page_id.get(position);
                if(mPostion!=position){
                    theme = mEtSunshineTheme.getText().toString();
                    processcode = mEtSunshineAcceptanceNumber.getText().toString();
                    mReplyPresenter.query(accepting_unit_id, category_id, theme, processcode, page_id);
                    mPostion=position;
                }

//                if (isSelected) {

//                } else {
//                    isSelected = true;
//                }
//                mReplyPresenter.query(accepting_unit_id,category_id,theme,processcode,page_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void showResult() {
        mLlResult.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideResult() {
        mLlResult.setVisibility(View.GONE);
    }

    @Override
    public void showMessage() {
        mTvResult.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideMessage() {
        mTvResult.setVisibility(View.GONE);
    }

    @Override
    public void renderReplyData(List<ReplyData> replyDatas, List<String> l_detail_url) {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvSunshineMain.setLayoutManager(manager);
        mRvSunshineMain.addItemDecoration(new DividerItemDecoration(context(), DividerItemDecoration.VERTICAL_LIST));
        ReplyAdapter replyAdapter = new ReplyAdapter();
        replyAdapter.setReplyDatas(replyDatas);
        mRvSunshineMain.setAdapter(replyAdapter);
        mRvSunshineMain.setNestedScrollingEnabled(false);//ScrollView 滑动顺滑
        replyAdapter.setOnItemClickListener(new ReplyAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                String url = l_detail_url.get(position);
                Log.d(TAG, "---------->" + url);
                Intent intent=new Intent(context(),ReplyDetailActivity.class);
                intent.putExtra("url",url);
                context().startActivity(intent);
            }
        });
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
        mBRetry.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetry() {
        mBRetry.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        mTvResult.setText(message);
    }

    @Override
    public Context context() {
        return getActivity();
    }

}