package com.xiaobudian.huastools.ui.fragment.sunshine;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.xiaobudian.huastools.AppComponent;
import com.xiaobudian.huastools.R;
import com.xiaobudian.huastools.data.SunshineApi;
import com.xiaobudian.huastools.ui.component.DaggerSunshineComponent;
import com.xiaobudian.huastools.ui.fragment.BaseFragment;
import com.xiaobudian.huastools.ui.module.SunshineModule;
import com.xiaobudian.huastools.ui.presenter.TalkPresenter;
import com.xiaobudian.huastools.ui.view.TalkView;
import com.xiaobudian.huastools.util.ToastUtil;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by caochang on 2016/4/18.
 */
public class TalkFragment extends BaseFragment implements TalkView {

    @Bind(R.id.s_sunshine_submit_object)
    Spinner mSSunshineSubmitObject;
    @Bind(R.id.ll_view)
    LinearLayout mLlView;
    @Bind(R.id.et_sunshine_submit_theme)
    EditText mEtSunshineSubmitTheme;
    @Bind(R.id.et_sunshine_submit_content)
    EditText mEtSunshineSubmitContent;
    @Bind(R.id.et_sunshine_submit_name)
    EditText mEtSunshineSubmitName;
    @Bind(R.id.et_sunshine_submit_email)
    EditText mEtSunshineSubmitEmail;
    @Bind(R.id.et_sunshine_submit_mobile_phone)
    EditText mEtSunshineSubmitMobilePhone;
    @Bind(R.id.b_sunshine_submit)
    Button mBSunshineSubmit;
    @Bind(R.id.pb_loading)
    ProgressBar mPbLoading;
    @Bind(R.id.tv_result)
    TextView mTvResult;
    @Bind(R.id.b_retry)
    Button mBRetry;

    @Inject
    SunshineApi mSunshineApi;
    @Bind(R.id.rb_sunshine_submit_consultation)
    RadioButton mRbSunshineSubmitConsultation;
    @Bind(R.id.rb_sunshine_submit_advice)
    RadioButton mRbSunshineSubmitAdvice;
    @Bind(R.id.rb_sunshine_submit_complaint)
    RadioButton mRbSunshineSubmitComplaint;
    @Bind(R.id.rb_sunshine_submit_praise)
    RadioButton mRbSunshineSubmitPraise;
    @Bind(R.id.rb_sunshine_submit_help)
    RadioButton mRbSunshineSubmitHelp;
    @Bind(R.id.rg_sunshine_letter_category)
    RadioGroup mRgSunshineLetterCategory;
    private TalkPresenter mTalkPresenter;
    private String requestType = "";
    private String accept_unit = "";
    private String theme = "";
    private String content = "";
    private String name = "";
    private String email = "";
    private String phone = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_talk, container, false);
        ButterKnife.bind(this, view);

        mTalkPresenter = new TalkPresenter(mSunshineApi, this);

        hideView();
        mTalkPresenter.initView();

        mRgSunshineLetterCategory.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {

                switch (arg1) {
                    case R.id.rb_sunshine_submit_consultation:
                        requestType = 1 + "";
                        break;
                    case R.id.rb_sunshine_submit_advice:
                        requestType = 2 + "";
                        break;
                    case R.id.rb_sunshine_submit_complaint:
                        requestType = 3 + "";
                        break;
                    case R.id.rb_sunshine_submit_praise:
                        requestType = 4 + "";
                        break;
                    case R.id.rb_sunshine_submit_help:
                        requestType = "5";
                        break;
                }
//                if (arg1==mRbSunshineSubmitConsultation.getId()) {
//                    requestTyp=1+"";
//                }
//                if (arg1==mRbSunshineSubmitAdvice.getId()) {
//                    requestTyp=2+"";
//                }
//                if (arg1==mRbSunshineSubmitComplaint.getId()) {
//                    requestTyp=3+"";
//                }if(arg1==mRbSunshineSubmitPraise.getId()) {
//                    requestTyp=4+"";
//                }else{
//                    requestTyp="";
//                }
            }
        });
        return view;
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

//    @OnClick(R.id.b_sunshine_submit)
//    public void onClick() {
////        theme = mEtSunshineSubmitTheme.getText().toString();
////        content = mEtSunshineSubmitContent.getText().toString();
////        name = mEtSunshineSubmitName.getText().toString();
////        email = mEtSunshineSubmitEmail.getText().toString();
////        phone = mEtSunshineSubmitMobilePhone.getText().toString();
////        if (requestTyp.equals("")||theme.equals("") || content.equals("") || name.equals("") || email.equals("") || phone.equals("")) {
////            ToastUtil.showToast(getContext(), "请输入完整信息");
////        } else {
////            mTalkPresenter.submit(requestTyp,accept_unit,theme,content,name,email,phone);
////        }
//    }

    @Override
    public void showView() {
        mLlView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideView() {
        mLlView.setVisibility(View.GONE);
    }

    @Override
    public void renderAcceptUnit(List<String> list_id, List<String> list_data) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context(), android.R.layout.simple_spinner_dropdown_item, list_data);
        mSSunshineSubmitObject.setAdapter(adapter);
        mSSunshineSubmitObject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                accept_unit = list_id.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void showResult() {
        mTvResult.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideResult() {
        mTvResult.setVisibility(View.GONE);
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
        mTvResult.setVisibility(View.VISIBLE);
        mBRetry.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetry() {
        mTvResult.setVisibility(View.GONE);
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

    @OnClick({R.id.b_sunshine_submit, R.id.b_retry})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.b_sunshine_submit:
                theme = mEtSunshineSubmitTheme.getText().toString();
                content = mEtSunshineSubmitContent.getText().toString();
                name = mEtSunshineSubmitName.getText().toString();
                email = mEtSunshineSubmitEmail.getText().toString();
                phone = mEtSunshineSubmitMobilePhone.getText().toString();
                if (requestType.equals("")||theme.equals("") || content.equals("") || name.equals("") || email.equals("") || phone.equals("")) {
                    ToastUtil.showToast(getContext(), "请输入完整信息");
                } else {
                    mTalkPresenter.submit(requestType,accept_unit,theme,content,name,email,phone);
                }
                break;
            case R.id.b_retry:
                mTalkPresenter.initView();
                break;
        }
    }
}