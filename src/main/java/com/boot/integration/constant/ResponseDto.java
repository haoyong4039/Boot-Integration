package com.boot.integration.constant;

import java.util.List;

public class ResponseDto<T> {

    private String resResult = "SUCCESS";

    private String resCode = "10000";

    private String resMsg = "成功";

    private T data;

    private List<T> dataList;

    public ResponseDto() {
    }

    public ResponseDto(ResponseEnum responseEnum, T data,List<T> dataList) {
        this.resResult = responseEnum.getResResult();
        this.resCode = responseEnum.getResCode();
        this.resMsg = responseEnum.getResMsg();
        this.data = data;
        this.dataList = dataList;
    }

    public ResponseDto(ResponseEnum responseEnum) {
        this.resResult = responseEnum.getResResult();
        this.resCode = responseEnum.getResCode();
        this.resMsg = responseEnum.getResMsg();
    }

    public String getResResult() {
        return resResult;
    }

    public void setResResult(String resResult) {
        this.resResult = resResult;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}
