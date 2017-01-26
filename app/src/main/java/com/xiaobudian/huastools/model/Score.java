package com.xiaobudian.huastools.model;

import java.io.Serializable;

/**
 * Created by caochang on 2016/4/18.
 */
public class Score implements Serializable{

    private String time;// 开课学期
    private String id;// 课程编号
    private String name;//课程名称
    private String grade;// 成绩
    private String credit;// 学分
    private String class_times;//总学时
    private String evaluation_mode;//考核方式
    private String attribute;//课程属性
    private String property ;// 课程性质

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getClass_times() {
        return class_times;
    }

    public void setClass_times(String class_times) {
        this.class_times = class_times;
    }

    public String getEvaluation_mode() {
        return evaluation_mode;
    }

    public void setEvaluation_mode(String evaluation_mode) {
        this.evaluation_mode = evaluation_mode;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    @Override
    public String toString() {
        return "Score{" +
                "time='" + time + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", grade='" + grade + '\'' +
                ", credit='" + credit + '\'' +
                ", class_times='" + class_times + '\'' +
                ", evaluation_mode='" + evaluation_mode + '\'' +
                ", attribute='" + attribute + '\'' +
                ", property='" + property + '\'' +
                '}';
    }
}
