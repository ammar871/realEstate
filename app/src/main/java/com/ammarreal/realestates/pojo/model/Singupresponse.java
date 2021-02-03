
package com.ammarreal.realestates.pojo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Singupresponse {

    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("result")
    @Expose
    private List<Result> result ;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

}
