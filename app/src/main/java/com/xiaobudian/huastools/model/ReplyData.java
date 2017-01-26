package com.xiaobudian.huastools.model;

/**
 * Created by 小不点 on 2016/4/23.
 */
public class ReplyData {
    private String serial_number;
    private String theme;
    private String date;
    private String category;

    public String getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAccepting_unit() {
        return accepting_unit;
    }

    public void setAccepting_unit(String accepting_unit) {
        this.accepting_unit = accepting_unit;
    }

    public String getProcessing_status() {
        return processing_status;
    }

    public void setProcessing_status(String processing_status) {
        this.processing_status = processing_status;
    }

    private String accepting_unit;
    private String processing_status;
}
