package com.xiaobudian.huastools.ui.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.databinding.library.baseAdapters.BR;
import com.xiaobudian.huastools.R;
import com.xiaobudian.huastools.databinding.ItemQuerybookBinding;
import com.xiaobudian.huastools.model.Book;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by 小不点 on 2016/4/21.
 */
public class QueryBookAdapter extends RecyclerView.Adapter<QueryBookAdapter.MyViewHolder> {

    private List<Book> mBooks;
    public QueryBookAdapter() {
        mBooks = new ArrayList<>();
    }

    public void setBooks(List<Book> books) {
        if (mBooks == null) {
            mBooks = new ArrayList<>();
        } else {
            mBooks = books;
            notifyItemChanged(mBooks.size() - 1);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemQuerybookBinding querybookBinding=DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_querybook,parent,false);
        MyViewHolder viewHolder=new MyViewHolder(querybookBinding.getRoot());
        viewHolder.setQuerybookBinding(querybookBinding);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bindTo(mBooks.get(position));
    }


    @Override
    public int getItemCount() {
        return mBooks.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {


        private ItemQuerybookBinding mQuerybookBinding;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public ItemQuerybookBinding getQuerybookBinding() {
            return mQuerybookBinding;
        }

        public void setQuerybookBinding(ItemQuerybookBinding querybookBinding) {
            mQuerybookBinding = querybookBinding;
        }

        public void bindTo(Book book) {
            mQuerybookBinding.setVariable(BR.book,book);
            mQuerybookBinding.executePendingBindings();
        }
    }
}