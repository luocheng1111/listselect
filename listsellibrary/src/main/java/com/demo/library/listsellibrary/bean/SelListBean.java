package com.demo.library.listsellibrary.bean;


import java.io.Serializable;

public class SelListBean implements Serializable{

    private String theFisrtPinYin;
    private String title;
    private int initPos;

    public SelListBean(String title, int initPos) {
        this.title = title;
        this.initPos = initPos;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTheFisrtPinYin() {
        return theFisrtPinYin;
    }

    public void setTheFisrtPinYin(String theFisrtPinYin) {
        this.theFisrtPinYin = theFisrtPinYin;
    }

    public int getInitPos() {
        return initPos;
    }

    public void setInitPos(int initPos) {
        this.initPos = initPos;
    }
}
