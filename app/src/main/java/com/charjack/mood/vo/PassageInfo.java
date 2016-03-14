package com.charjack.mood.vo;

import android.widget.ImageView;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/3/13.
 */
/*
* {"data":[{"id":"61f9a2a636a243cab4c32f99542a8d8e","description":"不求安慰，只希望有人能懂。","img":"http://www.himimarket.com/uploadimg/177820160131123304.jpg","type":"情感","addtime":"2016-01-31 12:33:04.0","zannum":"58729","dianjinum":"0","fengxiangnum":"0","shoucangnum":"3","tid":"1761","realzannum":"0","realshoucangnum":"3","publish":"1","publishdate":null,"haszan":"0","hasliked":"0"}
* */
public class PassageInfo implements Serializable{
    private String addtime = "1";
    private String description ="1";  //need to use
    private String dianjinum = "1";
    private String fengxiangnum= "1";
    private String hasliked= "1";
    private String haszan= "1";
    private String id= "1";
    private String img = "1";  //need to use
    private String publish= "1";
    private String publishdate= "1";
    private String realshoucangnum= "1";
    private String realzannum= "1";
    private String shoucangnum= "1";
    private String tid= "1";
    private String type= "1";
    private String zannum = "1";  //need to use

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getZannum() {
        return zannum;
    }

    public void setZannum(String zannum) {
        this.zannum = zannum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDianjinum() {
        return dianjinum;
    }

    public void setDianjinum(String dianjinum) {
        this.dianjinum = dianjinum;
    }

    public String getFengxiangnum() {
        return fengxiangnum;
    }

    public void setFengxiangnum(String fengxiangnum) {
        this.fengxiangnum = fengxiangnum;
    }

    public String getHasliked() {
        return hasliked;
    }

    public void setHasliked(String hasliked) {
        this.hasliked = hasliked;
    }

    public String getHaszan() {
        return haszan;
    }

    public void setHaszan(String haszan) {
        this.haszan = haszan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPublish() {
        return publish;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    public String getPublishdate() {
        return publishdate;
    }

    public void setPublishdate(String publishdate) {
        this.publishdate = publishdate;
    }

    public String getRealshoucangnum() {
        return realshoucangnum;
    }

    public void setRealshoucangnum(String realshoucangnum) {
        this.realshoucangnum = realshoucangnum;
    }

    public String getRealzannum() {
        return realzannum;
    }

    public void setRealzannum(String realzannum) {
        this.realzannum = realzannum;
    }

    public String getShoucangnum() {
        return shoucangnum;
    }

    public void setShoucangnum(String shoucangnum) {
        this.shoucangnum = shoucangnum;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
