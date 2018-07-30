package com.boot.integration.constant;

public enum ResponseEnum {

    USER_PAGE_SUCCESS("SUCCESS","10000","获取用户分页成功"),

    USER_PAGE_FAILURE("FAILURE","10001","获取用户分页失败"),

    USER_ROLE_SUCCESS("SUCCESS","10010","获取用户角色成功"),

    USER_ROLE_FAILURE("FAILURE","10011","获取用户角色失败");

    private String resResult;

    private String resCode;

    private String resMsg;

    ResponseEnum(String resResult, String resCode, String resMsg) {
        this.resResult = resResult;
        this.resCode = resCode;
        this.resMsg = resMsg;
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
}
