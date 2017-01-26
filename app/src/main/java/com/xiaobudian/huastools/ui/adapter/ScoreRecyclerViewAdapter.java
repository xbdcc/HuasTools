package com.xiaobudian.huastools.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiaobudian.huastools.R;
import com.xiaobudian.huastools.model.Score;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 小不点 on 2016/4/20.
 */
public class ScoreRecyclerViewAdapter extends RecyclerView.Adapter<ScoreRecyclerViewAdapter.RepoViewHolder> {


    private List<Score> score;


    private OnItemClickListener onItemClickListener;

    public ScoreRecyclerViewAdapter() {
        score = new ArrayList<>();
    }

    public void setScores(List<Score> score) {
        if (score == null) {
            this.score = new ArrayList<>();
        } else {
            this.score = score;
            notifyItemChanged(this.score.size() - 1);
        }
    }

    @Override
    public RepoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_score, parent, false);

        return new RepoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RepoViewHolder holder, int position) {
        holder.bindTo(score.get(position));

        // 设置回调接口
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(view -> onItemClickListener.OnItemClick(position));
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return score.size();
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public static class RepoViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_name)
        TextView mTvName;
        @Bind(R.id.tv_grade)
        TextView mTvGrade;
        @Bind(R.id.tv_property)
        TextView mTvProrerty;

        public RepoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindTo(Score score) {
            mTvName.setText(score.getName());
            mTvGrade.setText(score.getGrade());
            mTvProrerty.setText(score.getProperty() + "/" + score.getAttribute());
        }
    }
}