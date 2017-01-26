package com.xiaobudian.huastools.ui.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaobudian.huastools.BR;
import com.xiaobudian.huastools.R;
import com.xiaobudian.huastools.databinding.ItemReplyBinding;
import com.xiaobudian.huastools.model.ReplyData;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by 小不点 on 2016/4/23.
 */
public class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.MyViewHolder> {


    private List<ReplyData> mReplyDatas;


    private OnItemClickListener onItemClickListener;

    public ReplyAdapter() {
        mReplyDatas = new ArrayList<>();
    }

    public void setReplyDatas(List<ReplyData> replyDatas) {
        if (mReplyDatas == null) {
            mReplyDatas= new ArrayList<>();
        } else {
            mReplyDatas = replyDatas;
            notifyItemChanged(this.mReplyDatas.size() - 1);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemReplyBinding itemReplyBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_reply,parent,false);
        MyViewHolder viewHolder=new MyViewHolder(itemReplyBinding.getRoot());
        viewHolder.setItemReplyBinding(itemReplyBinding);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bindTo(mReplyDatas.get(position));

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
        return mReplyDatas.size();
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

//        @Bind(R.id.tv_name)
//        TextView mTvName;
//        @Bind(R.id.tv_grade)
//        TextView mTvGrade;
//        @Bind(R.id.tv_property)
//        TextView mTvProrerty;


        private ItemReplyBinding mItemReplyBinding;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindTo(ReplyData replyData) {

            mItemReplyBinding.setVariable(BR.reply,replyData);
            mItemReplyBinding.executePendingBindings();
//            mTvName.setText(score.getName());
//            mTvGrade.setText(score.getGrade());
//            mTvProrerty.setText(score.getProperty() + "/" + score.getAttribute());
        }

        public ItemReplyBinding getItemReplyBinding() {
            return mItemReplyBinding;
        }

        public void setItemReplyBinding(ItemReplyBinding itemReplyBinding) {
            mItemReplyBinding = itemReplyBinding;
        }
    }
}