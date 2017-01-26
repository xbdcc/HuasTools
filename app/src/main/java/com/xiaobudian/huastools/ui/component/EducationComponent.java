package com.xiaobudian.huastools.ui.component;

import com.xiaobudian.huastools.AppComponent;
import com.xiaobudian.huastools.data.EducationApi;
import com.xiaobudian.huastools.ui.ActivityScope;
import com.xiaobudian.huastools.ui.activity.education.EducationLoginActivity;
import com.xiaobudian.huastools.ui.activity.education.EvaluateDetailActivity;
import com.xiaobudian.huastools.ui.fragment.education.CourseSelectionFragment;
import com.xiaobudian.huastools.ui.fragment.education.EducationFragment;
import com.xiaobudian.huastools.ui.fragment.education.EvaluateFragment;
import com.xiaobudian.huastools.ui.fragment.education.ScoreFragment;
import com.xiaobudian.huastools.ui.module.EducationModule;

import dagger.Component;

/**
 * Created by caochang on 2016/4/18.
 */
@ActivityScope
@Component(modules = EducationModule.class,dependencies = AppComponent.class)
public interface EducationComponent {

    EducationFragment inject(EducationFragment educationFragment);

    EducationLoginActivity inject(EducationLoginActivity educationLoginActivity);

    ScoreFragment inject(ScoreFragment scoreFragment);

    CourseSelectionFragment inject(CourseSelectionFragment courseSelectionFragment);

    EvaluateFragment inject(EvaluateFragment evaluateFragment);

    EvaluateDetailActivity inject(EvaluateDetailActivity evaluateDetailActivity);

    EducationApi getEducationService();

}
