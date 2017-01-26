package com.xiaobudian.huastools.ui.fragment.education;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xiaobudian.huastools.AppComponent;
import com.xiaobudian.huastools.R;
import com.xiaobudian.huastools.data.DataUtil;
import com.xiaobudian.huastools.data.EducationApi;
import com.xiaobudian.huastools.model.Evaluate;
import com.xiaobudian.huastools.ui.activity.education.EvaluateDetailActivity;
import com.xiaobudian.huastools.ui.adapter.EvaluateListAdapter;
import com.xiaobudian.huastools.ui.component.DaggerEducationComponent;
import com.xiaobudian.huastools.ui.fragment.BaseFragment;
import com.xiaobudian.huastools.ui.module.EducationModule;
import com.xiaobudian.huastools.ui.presenter.EvaluatePresenter;
import com.xiaobudian.huastools.ui.view.EvaluateView;
import com.xiaobudian.huastools.util.Utility;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by caochang on 2016/4/17.
 */
public class EvaluateFragment extends BaseFragment implements EvaluateView {

    private static final String TAG = "EvaluateFragment";
    @Bind(R.id.pb_loading)
    ProgressBar mPbLoading;
    @Bind(R.id.tv_message)
    TextView mTvMessage;
    @Bind(R.id.b_retry)
    Button mBRetry;
    @Bind(R.id.tv_theory_course)
    TextView mTvTheoryCourse;
    @Bind(R.id.lv_theory_course)
    ListView mLvTheoryCourse;
    @Bind(R.id.tv_practice_course)
    TextView mTvPracticeCourse;
    @Bind(R.id.lv_practice_course)
    ListView mLvPracticeCourse;
    @Bind(R.id.ll_view)
    LinearLayout mLlView;
    @Bind(R.id.tv_result)
    TextView mTvResult;

    private EvaluatePresenter mEvaluatePresenter;
    @Inject
    EducationApi mEducationApi;

    private String url_1;
    private String url_2;
    private String url_deatil;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_evaluate, container, false);
        Log.d(TAG, "onCreateView");
        ButterKnife.bind(this, view);

        mEvaluatePresenter = new EvaluatePresenter(mEducationApi, this);
        hideView();
        return view;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerEducationComponent.builder()
                .appComponent(appComponent)
                .educationModule(new EducationModule(this))
                .build()
                .inject(this);
    }

    public void loadData() {
        hideView();
        mEvaluatePresenter.getEvaluateCount(educationCookie);
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DataUtil.Education.LONGIN_SUCCESS);
        intentFilter.addAction(DataUtil.Education.EVALUATE_SUBMIT + "");
        getActivity().registerReceiver(receiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(receiver);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.b_retry)
    public void onClick() {
        loadData();
    }

    @Override
    public void showView() {
        mLlView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideView() {
        mLlView.setVisibility(View.GONE);
    }

    @Override
    public void parseData(String result) {
        Document document = Jsoup.parse(result);
        //获取评价主页面表格内容
        Elements elements = document.select("form").select("tbody");
        //如果try没异常就能选课，布局显示，如果异常布局不显示
        try {
            Element elements2 = elements.select("tr").get(1);//如果1没有结果就没开放，抛出异常
            Elements elements3 = elements2.select("td").get(6).select("a");
            //获取操作字段和链接
            Log.i(TAG, elements3.toString());
            url_1 = elements3.get(0).attr("href");
            String t_1 = elements3.get(0).text();
            mTvTheoryCourse.setText(t_1);
            mTvTheoryCourse.setVisibility(View.VISIBLE);
            url_2 = elements3.get(1).attr("href");
            String t_2 = elements3.get(1).text();
            mTvPracticeCourse.setText(t_2);
            mTvPracticeCourse.setVisibility(View.VISIBLE);
            url_1 = EducationApi.BASE_URL + url_1;
            url_2 = EducationApi.BASE_URL + url_2;

            mEvaluatePresenter.getEvaluateList(educationCookie, mLvTheoryCourse, url_1);
            mEvaluatePresenter.getEvaluateList(educationCookie, mLvPracticeCourse, url_2);
            mTvResult.setVisibility(View.GONE);
        } catch (Exception e) {
            Log.i(TAG, "当前不是评教时间");
            mTvResult.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void parseList(ListView listView, String result) {
        Document document = Jsoup.parse(result);
        //获取评价主页面表格内容
        Elements elements = document.select("form").select("tbody");
        Elements elements2 = elements.select("tr");
        final ArrayList<Evaluate> evaluates = new ArrayList<>();
        for (int i = 1; i < elements2.size(); i++) {
            Evaluate evaluate = new Evaluate();
            Element e = elements2.get(i);
            evaluate.setName(e.select("td").get(2).text());
            evaluate.setTeacher(e.select("td").get(3).text());
            evaluate.setScore(e.select("td").get(4).text());
            evaluate.setIs_evaluate(e.select("td").get(5).text());
            evaluate.setIs_submit(e.select("td").get(6).text());
            url_deatil = e.select("td").get(7).select("a").attr("href");

            //正则表达式，获取单引号之间的内容
            Pattern pattern = Pattern.compile("(?<=').*(?=')");
            Matcher matcher = pattern.matcher(url_deatil);
            while (matcher.find()) {
                url_deatil = matcher.group();
            }
            evaluate.setUrl(url_deatil);
            evaluates.add(evaluate);
        }
        EvaluateListAdapter adapter = new EvaluateListAdapter(getActivity(), evaluates);
        listView.setAdapter(adapter);
        Utility.setListViewHeightBasedOnChildren(listView);
        listView.setVisibility(View.VISIBLE);
        //列表点击跳转到评教详情页面
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                dialog= ProgressDialog.show(getActivity(), "",getString(R.string.data_loading));
                url_deatil = EducationApi.BASE_URL + evaluates.get(position).getUrl();
                Intent intent = new Intent();
                intent.setClass(getActivity(), EvaluateDetailActivity.class);
                intent.putExtra("url", url_deatil);
                startActivity(intent);
                Log.i(TAG, "详情页---" + url_deatil);
            }
        });
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

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DataUtil.Education.LONGIN_SUCCESS)) {
                Log.i(TAG, "成功登录教务系统，刷新评教信息。");
                loadData();
            } else if (intent.getAction().equals(DataUtil.Education.EVALUATE_SUBMIT + "")) {
                Log.i(TAG, "收到EvaluateDetail回传给EvaluateFragment的广播");
                loadData();
            }
        }
    };
}