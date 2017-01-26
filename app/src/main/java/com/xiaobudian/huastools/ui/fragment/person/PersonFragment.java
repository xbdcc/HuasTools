package com.xiaobudian.huastools.ui.fragment.person;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.xiaobudian.huastools.R;
import com.xiaobudian.huastools.data.HttpUrl;
import com.xiaobudian.huastools.ui.activity.AboutActivity;
import com.xiaobudian.huastools.util.Update;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by caochang on 2016/4/15.
 */
public class PersonFragment extends Fragment {

    @Bind(R.id.b_about)
    Button mBAbout;
    @Bind(R.id.b_check_update)
    Button mBCheckUpdate;
    @Bind(R.id.b_contact_me)
    Button mBContactMe;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.b_about, R.id.b_check_update, R.id.b_contact_me})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.b_about:
                intent=new Intent(getActivity(), AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.b_check_update:
                Update update=new Update(getActivity(),Update.Click);
                update.update();
                break;
            case R.id.b_contact_me:
                Uri uri=Uri.parse(HttpUrl.contact_me);
                intent=new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
                break;
        }
    }
}