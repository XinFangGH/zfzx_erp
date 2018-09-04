package com.zhiwei.credit.service.thirdPay.fuiou.util;

public class HttpRsp{
    int statusCode;
    String rspStr;
    
    public int getStatusCode(){
        return statusCode;
    }
    public void setStatusCode(int statusCode){
        this.statusCode = statusCode;
    }
    public String getRspStr(){
        return rspStr;
    }
    public void setRspStr(String rspStr){
        this.rspStr = rspStr;
    }
    
    
}
