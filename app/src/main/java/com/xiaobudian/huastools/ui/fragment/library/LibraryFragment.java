package com.xiaobudian.huastools.ui.fragment.library;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.xiaobudian.huastools.R;
import com.xiaobudian.huastools.ui.adapter.MyPagerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by caochang on 2016/4/15.
 */
public class LibraryFragment extends Fragment {


    private static final String TAG ="LibraryFragment" ;
    @Bind(R.id.sliding_tabs)
    TabLayout mSlidingTabs;
    @Bind(R.id.viewpager)
    public ViewPager mViewpager;
    @Bind(R.id.tv_message)
    TextView mTvMessage;
    @Bind(R.id.b_retry)
    Button mBRetry;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_library, container, false);
        ButterKnife.bind(this, view);

        setupViewPager(mViewpager);

        mSlidingTabs.addTab(mSlidingTabs.newTab().setText(R.string.query_book));
        mSlidingTabs.addTab(mSlidingTabs.newTab().setText(R.string.renew_book));
        mSlidingTabs.setupWithViewPager(mViewpager);


        Log.d(TAG,"onCreate");
        return view;
    }

    private void setupViewPager(ViewPager mViewPager) {
        MyPagerAdapter adapter = new MyPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new QueryBookFragment(), getResources().getString(R.string.query_book));
        adapter.addFragment(new RenewBookFragment(), getResources().getString(R.string.renew_book));
        mViewPager.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
