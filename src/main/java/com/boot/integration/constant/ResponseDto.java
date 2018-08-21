package com.boot.integration.constant;

import java.util.List;
import java.util.Map;

public class ResponseDto
{

    private int retCode;

    private Object retData;

    public ResponseDto()
    {
    }

    public ResponseDto(int retCode, Object retData)
    {
        this.retCode = retCode;
        this.retData = retData;
    }

    public int getRetCode()
    {
        return retCode;
    }

    public void setRetCode(int retCode)
    {
        this.retCode = retCode;
    }

    public Object getRetData()
    {
        return retData;
    }

    public void setRetData(Object retData)
    {
        this.retData = retData;
    }
}
