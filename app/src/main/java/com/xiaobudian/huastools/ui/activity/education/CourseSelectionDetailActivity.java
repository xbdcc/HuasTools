package com.xiaobudian.huastools.ui.activity.education;

import android.os.Bundle;
import android.widget.TextView;

import com.xiaobudian.huastools.AppComponent;
import com.xiaobudian.huastools.R;
import com.xiaobudian.huastools.model.CourseSelection;
import com.xiaobudian.huastools.ui.activity.BaseActivity;

/**
 * Created by xiaobudian on 2016/1/9.
 */
public class CourseSelectionDetailActivity extends BaseActivity{

    private CourseSelection selection;
    private TextView tv_course_name;
    private TextView tv_course_category;
    private TextView tv_course_total_time;
    private TextView tv_course_score;
    private TextView tv_course_teacher;
    private TextView tv_course_week;
    private TextView tv_course_study_time;
    private TextView tv_course_study_place;
    private TextView tv_course_limit_select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_selection_detail);

        setTitle(getResources().getString(R.string.course_detail));

        tv_course_name=getViewById(R.id.tv_course_name);
        tv_course_category=getViewById(R.id.tv_course_category);
        tv_course_total_time=getViewById(R.id.tv_course_total_time);
        tv_course_score=getViewById(R.id.tv_course_score);
        tv_course_teacher=getViewById(R.id.tv_course_teacher);
        tv_course_week=getViewById(R.id.tv_course_week);
        tv_course_study_time=getViewById(R.id.tv_course_study_time);
        tv_course_study_place=getViewById(R.id.tv_course_study_place);
        tv_course_limit_select=getViewById(R.id.tv_course_limit_select);

        selection= (CourseSelection) getIntent().getSerializableExtra("selection");
        tv_course_name.setText(selection.getCourse_name());
        tv_course_category.setText(selection.getCourse_category());
        tv_course_total_time.setText(selection.getCourse_total_time());
        tv_course_score.setText(selection.getCourse_study_score());
        tv_course_teacher.setText(selection.getCourse_study_teacher());
        tv_course_week.setText(selection.getCourse_study_total_time());
        tv_course_study_time.setText(selection.getCourse_study_time());
        tv_course_study_place.setText(selection.getCourse_study_place());
        tv_course_limit_select.setText(selection.getCourse_limit_selected());

        back();

    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }
}
