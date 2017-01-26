package com.xiaobudian.huastools.ui.fragment.education;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.xiaobudian.huastools.AppComponent;
import com.xiaobudian.huastools.R;
import com.xiaobudian.huastools.data.DataUtil;
import com.xiaobudian.huastools.data.EducationApi;
import com.xiaobudian.huastools.model.Score;
import com.xiaobudian.huastools.ui.activity.education.ScoreDetailActivity;
import com.xiaobudian.huastools.ui.adapter.ScoreRecyclerViewAdapter;
import com.xiaobudian.huastools.ui.component.DaggerEducationComponent;
import com.xiaobudian.huastools.ui.fragment.BaseFragment;
import com.xiaobudian.huastools.ui.module.EducationModule;
import com.xiaobudian.huastools.ui.presenter.ScorePersenter;
import com.xiaobudian.huastools.ui.view.ScoreView;
import com.xiaobudian.huastools.util.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by caochang on 2016/4/17.
 */
public class ScoreFragment extends BaseFragment implements ScoreView {

    private static final String TAG = "ScoreFragment";
    @Bind(R.id.pb_loading)
    ProgressBar mPbLoading;
    @Bind(R.id.tv_message)
    TextView mTvMessage;
    @Bind(R.id.b_retry)
    Button mBRetry;
    @Bind(R.id.s_course_time)
    Spinner mSCourseTime;
    @Bind(R.id.s_course_category)
    Spinner mSCourseCategory;
    @Bind(R.id.s_course_type)
    Spinner mSCourseType;
    @Bind(R.id.iv_search)
    ImageView mIvSearch;
    @Bind(R.id.rv_score)
    RecyclerView mRvScore;
    @Bind(R.id.et_name)
    EditText mEtName;
    @Bind(R.id.scrollView)
    ScrollView mScrollView;
    @Bind(R.id.rl_score)
    RelativeLayout mRlScore;

    private ScorePersenter mScorePersenter;
    @Inject
    EducationApi mEducationApi;

    private String time = "";
    private String category = "";
    private String name = "";
    private String type = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_score, container, false);
        ButterKnife.bind(this, view);

        mScorePersenter = new ScorePersenter(mEducationApi, this);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvScore.setLayoutManager(manager);
        mRvScore.addItemDecoration(new DividerItemDecoration(context(), DividerItemDecoration.VERTICAL_LIST));
        hideView();

        mBRetry.setOnClickListener(v ->loadData() );
        return view;
    }

    public void loadData() {
        hideView();
        mScorePersenter.getCourseCondition(educationCookie);
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerEducationComponent.builder()
                .appComponent(appComponent)
                .educationModule(new EducationModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void showView() {
        mRlScore.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideView() {
        mRlScore.setVisibility(View.GONE);
    }

    @Override
    public void renderScoreTime(ArrayList<String> l_id, ArrayList<String> l_data) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context(), android.R.layout.simple_spinner_item, l_data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSCourseTime.setAdapter(adapter);
        mSCourseTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                time = l_id.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void renderScoreCategory(ArrayList<String> list) {
        final ArrayList<String> ids = new ArrayList<>();
        ArrayList<String> datas = new ArrayList<>();
        for (int i = 1; i < list.size(); i = i + 2) {
            datas.add(list.get(i - 1));
            ids.add(list.get(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context(), android.R.layout.simple_spinner_dropdown_item, datas);
        mSCourseCategory.setAdapter(adapter);
        mSCourseCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = ids.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void renderScoreType(ArrayList<String> list) {
        final ArrayList<String> ids = new ArrayList<>();
        ArrayList<String> datas = new ArrayList<>();
        for (int i = 1; i < list.size(); i = i + 2) {
            datas.add(list.get(i - 1));
            ids.add(list.get(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context(), android.R.layout.simple_spinner_dropdown_item, datas);
        mSCourseType.setAdapter(adapter);
        mSCourseType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = ids.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void showRecycleView() {
        mRvScore.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRecycleView() {
        mRvScore.setVisibility(View.GONE);
    }

    @Override
    public void showMessage() {
        mTvMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideMessage() {
        mTvMessage.setVisibility(View.GONE);
    }

    @Override
    public void renderScore(List<Score> scores) {
        ScoreRecyclerViewAdapter scoreRecyclerViewAdapter = new ScoreRecyclerViewAdapter();
        scoreRecyclerViewAdapter.setScores(scores);
        mRvScore.setAdapter(scoreRecyclerViewAdapter);
        mRvScore.setNestedScrollingEnabled(false);//ScrollView 滑动顺滑
        scoreRecyclerViewAdapter.setOnItemClickListener(position -> {
            Score score = scores.get(position);
            Intent intent = new Intent(getActivity(), ScoreDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("score", score);
            intent.putExtras(bundle);
            startActivity(intent);
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
        mTvMessage.setText(message);
    }

    @Override
    public Context context() {
        return getActivity();
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DataUtil.Education.LONGIN_SUCCESS);
        getActivity().registerReceiver(receiver, intentFilter);
        Log.d(TAG, "onStart");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        getActivity().unregisterReceiver(receiver);
    }


    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DataUtil.Education.LONGIN_SUCCESS)) {
                Log.i(TAG, "成功登录教务系统，刷新成绩信息。");
                loadData();
//                initData();
            }
        }
    };

    @OnClick(R.id.iv_search)
    public void onClick() {
        Log.d(TAG, "查询成绩中");
        name = mEtName.getText().toString();
        mScorePersenter.getCourseResult(educationCookie, time, category, name, type);
    }

}
