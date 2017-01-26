package com.xiaobudian.huastools.model;

import java.io.Serializable;

/**
 * Created by xiaobudian on 2016/1/7.
 */
public class CourseSelection implements Serializable{
    private String course_name;//课程名称，如：tmpKc[1] = "礼仪与社交（网络课程）";
    private String course_unit;//课程所属单位，如：tmpKc[2] = "教务处";
    private String course_selection_category;//课程选择总类，如：tmpKc[3] = "公选";
    private String course_study_score;//课程学分，如：tmpKc[4] = "2.0";
//    private String course_has_selected;//已选人数，如：tmpKc[5] = "0";
    private String course_limit_selected;//限选人数，如：tmpKc[6] = 500;
    private String course_study_teacher;//课程老师，如：tmpKc[7] = "";
    private String course_study_total_time;//周次，如：tmpKc[8] = "null";
    private String course_study_time;//上课时间，如：tmpKc[9] = "";
    private String course_study_place;//上课地点，如：tmpKc[9] = "";
    private String course_submit_data;//选课提交数据，如：tmpKc[11] = "201520162000170";
    private String course_number;//课程编号，如：tmpKc[12] = "13003089";
    private String course_total_time;//学时，如：tmpKc[15] = "28.0";
    private String course_category;//课程类别，如：tmpKc[16] = "人文社科";
    private String course_class;//课堂，如：tmpKc[20] = "临班111"

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCourse_unit() {
        return course_unit;
    }

    public void setCourse_unit(String course_unit) {
        this.course_unit = course_unit;
    }

    public String getCourse_selection_category() {
        return course_selection_category;
    }

    public void setCourse_selection_category(String course_selection_category) {
        this.course_selection_category = course_selection_category;
    }

    public String getCourse_study_score() {
        return course_study_score;
    }

    public void setCourse_study_score(String course_study_score) {
        this.course_study_score = course_study_score;
    }

    public String getCourse_limit_selected() {
        return course_limit_selected;
    }

    public void setCourse_limit_selected(String course_limit_selected) {
        this.course_limit_selected = course_limit_selected;
    }

    public String getCourse_study_teacher() {
        return course_study_teacher;
    }

    public void setCourse_study_teacher(String course_study_teacher) {
        this.course_study_teacher = course_study_teacher;
    }

    public String getCourse_study_total_time() {
        return course_study_total_time;
    }

    public void setCourse_study_total_time(String course_study_total_time) {
        this.course_study_total_time = course_study_total_time;
    }

    public String getCourse_study_time() {
        return course_study_time;
    }

    public void setCourse_study_time(String course_study_time) {
        this.course_study_time = course_study_time;
    }

    public String getCourse_study_place() {
        return course_study_place;
    }

    public void setCourse_study_place(String course_study_place) {
        this.course_study_place = course_study_place;
    }

    public String getCourse_submit_data() {
        return course_submit_data;
    }

    public void setCourse_submit_data(String course_submit_data) {
        this.course_submit_data = course_submit_data;
    }

    public String getCourse_number() {
        return course_number;
    }

    public void setCourse_number(String course_number) {
        this.course_number = course_number;
    }

    public String getCourse_total_time() {
        return course_total_time;
    }

    public void setCourse_total_time(String course_total_time) {
        this.course_total_time = course_total_time;
    }

    public String getCourse_category() {
        return course_category;
    }

    public void setCourse_category(String course_category) {
        this.course_category = course_category;
    }

    public String getCourse_class() {
        return course_class;
    }

    public void setCourse_class(String course_class) {
        this.course_class = course_class;
    }
}
