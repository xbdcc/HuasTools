package com.xiaobudian.huastools.ui.activity.education;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.xiaobudian.huastools.AppComponent;
import com.xiaobudian.huastools.R;
import com.xiaobudian.huastools.databinding.ActivityScoreDetailBinding;
import com.xiaobudian.huastools.model.Score;
import com.xiaobudian.huastools.ui.activity.BaseActivity;

/**
 * Created by 小不点 on 2016/4/20.
 */
public class ScoreDetailActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityScoreDetailBinding binding= DataBindingUtil.setContentView(this,R.layout.activity_score_detail);
        Score score= (Score) getIntent().getSerializableExtra("score");
        binding.setScore(score);

        setTitle(R.string.score_detail);
        back();
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }
}
