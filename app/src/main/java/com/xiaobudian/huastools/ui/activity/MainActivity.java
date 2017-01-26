package com.xiaobudian.huastools.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.xiaobudian.huastools.AppComponent;
import com.xiaobudian.huastools.R;
import com.xiaobudian.huastools.ui.activity.education.EducationLoginActivity;
import com.xiaobudian.huastools.ui.adapter.MyFragmentPagerAdapter;
import com.xiaobudian.huastools.ui.component.DaggerMainComponent;
import com.xiaobudian.huastools.ui.fragment.HomeFragment;
import com.xiaobudian.huastools.ui.fragment.education.EducationFragment;
import com.xiaobudian.huastools.ui.fragment.library.LibraryFragment;
import com.xiaobudian.huastools.ui.fragment.person.PersonFragment;
import com.xiaobudian.huastools.ui.fragment.sunshine.SunshineFragment;
import com.xiaobudian.huastools.ui.module.MainModule;
import com.xiaobudian.huastools.ui.presenter.MainPresenter;
import com.xiaobudian.huastools.ui.view.MainView;
import com.xiaobudian.huastools.util.ToastUtil;
import com.xiaobudian.huastools.util.Update;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;

public class MainActivity extends BaseActivity implements MainView {


    @Inject
    MainPresenter mMainPresenter;

    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.viewPager)
    ViewPager mViewPager;
    @Bind(R.id.rb_home)
    RadioButton mRbHome;
    @Bind(R.id.rb_education)
    RadioButton mRbEducation;
    @Bind(R.id.rb_library)
    RadioButton mRbLibrary;
    @Bind(R.id.rb_sunshine)
    RadioButton mRbSunshine;
    @Bind(R.id.rb_person)
    RadioButton mRbPerson;
    @Bind(R.id.rg_menu)
    RadioGroup mRgMenu;

    private HomeFragment mHomeFragment;
    private EducationFragment mEducationFragment;
    private LibraryFragment mLibraryFragment;
    private SunshineFragment mSunshineFragment;
    private PersonFragment mPersonFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mMainPresenter.attachView(this);

        initView();

        Update update=new Update(this,Update.Auto);
        update.update();

//        CrashReport.testJavaCrash();//测试bugly 异常上报
    }

    public void initView(){
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);

        mRbHome.setOnClickListener(new MyOnclickListener(0));
        mRbEducation.setOnClickListener(new MyOnclickListener(1));
        mRbLibrary.setOnClickListener(new MyOnclickListener(2));
        mRbSunshine.setOnClickListener(new MyOnclickListener(3));
        mRbPerson.setOnClickListener(new MyOnclickListener(4));

        mHomeFragment = new HomeFragment();
        mEducationFragment=new EducationFragment();
        mLibraryFragment=new LibraryFragment();
        mSunshineFragment=new SunshineFragment();
        mPersonFragment=new PersonFragment();
        ArrayList<Fragment> fragments=new ArrayList<>();
        fragments.add(mHomeFragment);
        fragments.add(mEducationFragment);
        fragments.add(mLibraryFragment);
        fragments.add(mSunshineFragment);
        fragments.add(mPersonFragment);
        mMainPresenter.initView(fragments);

    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMainComponent.builder()
                .appComponent(appComponent)
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainPresenter.detachView();
    }

    @Override
    public void initViewPager(final MyFragmentPagerAdapter myFragmentPagerAdapter) {
        mViewPager.setAdapter(myFragmentPagerAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.addOnPageChangeListener(new MyPagerOnPageChangeListener());

        mHomeFragment.setGridClickListener(new HomeFragment.GridClickListener() {
            @Override
            public void OnGridClick(int x, int y) {
                Log.e("setGridClickListener", "OnGridClick: X=" + x + "Y=" + y);
                mViewPager.setCurrentItem(x);
                switch (x) {
                    case 1:
                        mEducationFragment.mViewpager.setCurrentItem(y);
                        break;
                    case 2:
                        mLibraryFragment.mViewpager.setCurrentItem(y);
                        break;
                    case 3:
                        mSunshineFragment.mViewpager.setCurrentItem(y);
                        break;
                }
            }
        });

    }

    @Override
    public void checkUpdate() {

    }

    /**
     * 绑定点击监听事件
     */
    public class MyOnclickListener implements View.OnClickListener {

        private int index = 0;

        public MyOnclickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            Log.i("Onclick----------->", index + "     " + v.getId());
            mViewPager.setCurrentItem(index, false);
            setBottom(index);

        }
    }

    /**
     * ViewPager的PageChangeListener(页面改变的监听器)
     */
    private class MyPagerOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        /**
         * 滑动ViewPager的时候，让上方的tab自动切换
         */
        @Override
        public void onPageSelected(int position) {
            setBottom(position);
            mViewPager.setCurrentItem(position, false);
            Log.d("主菜单-------selected", "position:" + position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private void setBottom(int index) {
        switch (index) {
            case 0:
                mToolbar.getMenu().clear();
                mTvTitle.setText(R.string.home);
                mRbHome.setChecked(true);
                mRbEducation.setChecked(false);
                mRbLibrary.setChecked(false);
                mRbSunshine.setChecked(false);
                mRbPerson.setChecked(false);

                break;
            case 1:
                mToolbar.getMenu().clear();
                mToolbar.inflateMenu(R.menu.menu_education);
                mTvTitle.setText(R.string.education);
                mRbHome.setChecked(false);
                mRbEducation.setChecked(true);
                mRbLibrary.setChecked(false);
                mRbSunshine.setChecked(false);
                mRbPerson.setChecked(false);
                break;
            case 2:
                mToolbar.getMenu().clear();
//                mToolbar.inflateMenu(R.menu.menu_library);
                mTvTitle.setText(R.string.library);
                mRbHome.setChecked(false);
                mRbEducation.setChecked(false);
                mRbLibrary.setChecked(true);
                mRbSunshine.setChecked(false);
                mRbPerson.setChecked(false);
                break;
            case 3:
                mToolbar.getMenu().clear();
                mTvTitle.setText(R.string.sunshine);
                mRbHome.setChecked(false);
                mRbEducation.setChecked(false);
                mRbLibrary.setChecked(false);
                mRbSunshine.setChecked(true);
                mRbPerson.setChecked(false);
                break;
            case 4:
                mToolbar.getMenu().clear();
                mTvTitle.setText(R.string.person);
                mRbHome.setChecked(false);
                mRbEducation.setChecked(false);
                mRbLibrary.setChecked(false);
                mRbSunshine.setChecked(false);
                mRbPerson.setChecked(true);
                break;
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        Intent intent=new Intent();
        if(id==R.id.education_change_account){
            ToastUtil.showToast(this,"gfg");
            intent.setClass(this,EducationLoginActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }
}
