package com.charjack.mood.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/14.
 */
public class WebPassageInfo implements Serializable {
    private String status = "1";
    private ArrayList<PassageInfo> data;
    private String code = "1";
    public String getStatus() {
        return status;
    }

    public WebPassageInfo(){}

    public WebPassageInfo(ArrayList<PassageInfo> data){
        this.data =data;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<PassageInfo> getData() {
        return data;
    }

    public void setData(ArrayList<PassageInfo> data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}
