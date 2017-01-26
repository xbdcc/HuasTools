package com.xiaobudian.huastools.ui.activity.education;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.xiaobudian.huastools.AppComponent;
import com.xiaobudian.huastools.R;
import com.xiaobudian.huastools.data.DataUtil;
import com.xiaobudian.huastools.data.EducationApi;
import com.xiaobudian.huastools.data.HttpUrl;
import com.xiaobudian.huastools.ui.activity.BaseActivity;
import com.xiaobudian.huastools.ui.component.DaggerEducationComponent;
import com.xiaobudian.huastools.ui.fragment.BaseFragment;
import com.xiaobudian.huastools.ui.module.EducationModule;
import com.xiaobudian.huastools.ui.presenter.EvaluateDetailPresenter;
import com.xiaobudian.huastools.ui.view.EvaluateDetailView;
import com.xiaobudian.huastools.util.ToastUtil;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 小不点 on 2016/6/11.
 */
public class EvaluateDetailActivity extends BaseActivity implements EvaluateDetailView {

    private static final String TAG = "EvaluateDetailActivity";

    @Bind(R.id.tv_menu_title)
    TextView mTvMenuTitle;
    @Bind(R.id.b_submit)
    Button mBSubmit;
    @Bind(R.id.et_advice)
    EditText mEtAdvice;
    @Bind(R.id.lv_radio_item)
    ListView mLvRadioItem;
    @Bind(R.id.pb_loading)
    ProgressBar mPbLoading;
    @Bind(R.id.tv_message)
    TextView mTvMessage;
    @Bind(R.id.b_retry)
    Button mBRetry;
    @Bind(R.id.ll_view)
    LinearLayout mLlView;

    private EvaluateDetailPresenter mEvaluateDetailPresenter;
    @Inject
    EducationApi mEducationApi;

    private List<String> l_text;
    private List<String> l_rank;
    private List<String> l_radio_name;
    private List<String> l_radio_value;
    private List<BasicNameValuePair> pairs;

    private ProgressDialog dialog;
    private int id;
    private List<String> l_radio_score;
    private boolean isRepeat;
    private String result = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_detail);
        ButterKnife.bind(this);

        mEvaluateDetailPresenter = new EvaluateDetailPresenter(mEducationApi, this);
        result = getIntent().getStringExtra("url");

        loadData();
        back();
        setTitle("评教详情");
        mBRetry.setOnClickListener(v -> loadData());
    }

    public void loadData() {
        hideView();
        mEvaluateDetailPresenter.loadData(BaseFragment.educationCookie, result);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerEducationComponent.builder()
                .appComponent(appComponent)
                .educationModule(new EducationModule(this))
                .build()
                .inject(this);
    }

    @OnClick(R.id.b_submit)
    public void onClick() {
        String jynr = mEtAdvice.getText().toString();
        for (int i = 1; i < 10; i++) {
            id = Integer.parseInt(l_radio_score.get(i - 1));
            int id1 = Integer.parseInt(l_radio_score.get(i));
            if (id1 - id == 5) {
                isRepeat = true;
            } else {
                isRepeat = false;
                break;
            }
        }
        if (isRepeat) {
            ToastUtil.showToast(EvaluateDetailActivity.this, "不能全部相同！");
        } else {
            for (int i = 0; i < 10; i++) {
                id = Integer.parseInt(l_radio_score.get(i));
                addData(l_radio_name.get(id), l_radio_value.get(id));
            }
            addData("jynr", jynr);
            System.out.println("共提交" + pairs.size() + "项");

            dialog= ProgressDialog.show(EvaluateDetailActivity.this, "",getString(R.string.data_loading));
            new Thread(runnable).start();
        }
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            result = null;
            DefaultHttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(HttpUrl.education_evaluate_submit);
            try {
                post.setEntity(new UrlEncodedFormEntity(pairs, "utf-8"));
                post.setHeader("Cookie", BaseFragment.educationCookie);
                result = EntityUtils.toString(client.execute(post).getEntity());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (result == null) {
                handler.sendEmptyMessage(DataUtil.Common.UNKNOWN_ERROR);
            } else {
                handler.sendEmptyMessage(DataUtil.Education.EVALUATE_SUBMIT);
            }
            pairs = new ArrayList<>();
        }
    };

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            dialog.dismiss();
            switch (msg.what) {
                case DataUtil.Common.UNKNOWN_ERROR:
                    ToastUtil.showError(EvaluateDetailActivity.this);
                    break;
                case DataUtil.Education.EVALUATE_SUBMIT:
                    Log.i(TAG, result);
                    Intent intent = new Intent();
                    intent.setAction(DataUtil.Education.EVALUATE_SUBMIT + "");
                    sendBroadcast(intent);
                    finish();
                    break;
            }
        }
    };

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
        pairs = new ArrayList<>();
        l_text = new ArrayList<>();
        l_radio_name = new ArrayList<>();
        l_radio_value = new ArrayList<>();
        l_radio_score = new ArrayList<>();
        Document document = Jsoup.parse(result);
        //获取表格内容,总共需提交82个字段
        Elements elements = document.select("body").select("form");

        final Elements e_text = elements.select("tbody").select("tr");
        List<Map<String, String>> list = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            l_text.add(e_text.get(i).select("td").get(0).text());
        }
//        SimpleAdapter adapter=new SimpleAdapter(this,list,R.layout.evaluate_detail_item,
//                new String[]{"text"},new int[]{R.id.tv_evaluate_text});
        MyAdapter adapter = new MyAdapter();
        mLvRadioItem.setAdapter(adapter);
//        Utility.setListViewHeightBasedOnChildren(mLvRadioItem);
//        setListViewHeightBasedOnChildren(lv_radio_item);

        for (int i = 0; i < 50; i += 5) {
            l_radio_score.add(i + "");
        }
        System.out.println("---------------------" + l_radio_score);

        //hiden里需提交的字段，共71个
        Elements e_hidden = elements.select("input[type=hidden]");
        for (int i = 0; i < e_hidden.size(); i++) {
            Element e = e_hidden.get(i);
            if (i == 0) {
                addData(e.attr("name"), "1");//0保存，1提交
                addData(e.attr("name"), "1");//0保存，1提交
            } else {
                addData(e.attr("name"), e.attr("value"));
            }
        }
        Elements e_radio = elements.select("input[type=radio]");
        for (int i = 0; i < e_radio.size(); i++) {
            Element e = e_radio.get(i);
            l_radio_name.add(e.attr("name"));
            l_radio_value.add(e.attr("value"));
        }

    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            Log.i(TAG, "总共有" + l_text.size() + "项");
            return l_text.size();
        }

        @Override
        public Object getItem(int position) {
            return l_text.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(EvaluateDetailActivity.this).inflate(R.layout.item_evaluate_detail, null);
                holder.tv_message = (TextView) convertView.findViewById(R.id.tv_evaluate_text);
                holder.rg_rank = (RadioGroup) convertView.findViewById(R.id.rg_rank);
                holder.rb_excellent = (RadioButton) convertView.findViewById(R.id.rb_excellent);
                holder.rb_good = (RadioButton) convertView.findViewById(R.id.rb_good);
                holder.rb_medium = (RadioButton) convertView.findViewById(R.id.rb_medium);
                holder.rb_pass = (RadioButton) convertView.findViewById(R.id.rb_pass);
                holder.rb_fail = (RadioButton) convertView.findViewById(R.id.rb_fail);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv_message.setText(l_text.get(position));
//            l_radio_score.set(position,holder.rg_rank.getCheckedRadioButtonId())
            id = position * 5;
            holder.rg_rank.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    switch (checkedId) {
                        case R.id.rb_excellent:
                            id = position * 5;
                            Log.i(TAG, "提交的第" + id + "项");
                            break;
                        case R.id.rb_good:
                            id = position * 5 + 1;
                            Log.i(TAG, "提交的第" + id + "项");
                            break;
                        case R.id.rb_medium:
                            id = position * 5 + 2;
                            Log.i(TAG, "提交的第" + id + "项");
                            break;
                        case R.id.rb_pass:
                            id = position * 5 + 3;
                            Log.i(TAG, "提交的第" + id + "项");
                            break;
                        case R.id.rb_fail:
                            id = position * 5 + 4;
                            Log.i(TAG, "提交的第" + id + "项");
                            break;
                    }
//                    addData(l_radio_name.get(id), l_radio_value.get(id));
                    l_radio_score.set(position, id + "");

                    System.out.println("---------------------" + l_radio_score);
                }
            });
            return convertView;
        }

        class ViewHolder {
            TextView tv_message;
            RadioGroup rg_rank;
            RadioButton rb_excellent;
            RadioButton rb_good;
            RadioButton rb_medium;
            RadioButton rb_pass;
            RadioButton rb_fail;
        }
    }

    public void addData(String name, String value) {
        pairs.add(new BasicNameValuePair(name, value));
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
        mTvMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetry() {
        mBRetry.setVisibility(View.GONE);
        mTvMessage.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        mTvMessage.setText(message);
    }

    @Override
    public Context context() {
        return this;
    }
}
