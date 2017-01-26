package com.xiaobudian.huastools.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaobudian.huastools.AppApplication;
import com.xiaobudian.huastools.AppComponent;
import com.xiaobudian.huastools.R;

/**
 * Created by caochang on 2016/4/12.
 */
public abstract class BaseActivity extends AppCompatActivity{

    private static final String TAG="BaseActivity";

    private ImageView mBack;
    private TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActivityComponent(AppApplication.get(this).getAppComponent());
        Log.d(TAG,getClass().getSimpleName());
    }

    protected abstract void setupActivityComponent(AppComponent appComponent);

    public void back(){
        mBack=getViewById(R.id.iv_back);
        mBack.setOnClickListener(v->finish());
//        mBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//                Log.d("gdg","gfg");
//            }
//        });
    }

    public void setTitle(String title){
        tv_title= (TextView) findViewById(R.id.tv_title);
    }

    public <T extends View>T getViewById(int id){
        try{
            return (T)findViewById(id);
        }catch (ClassCastException e){
            Log.e(TAG,"Could not cast View to create class.",e);
            throw e;
        }
    }
}
