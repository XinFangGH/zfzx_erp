package com.sms.soap;

public interface ServicesSoap extends java.rmi.Remote {

    
	 /**
     * 直接发送，不需要登录服务器
     */
    public SendStatus directSend(java.lang.String userID,  //用户ID
    						     java.lang.String account, //账号
    						     java.lang.String password,//密码
    						     java.lang.String phones,  //手机号码，以逗号分隔
    						     java.lang.String content, //内容
    						     java.lang.String sendTime,//为空
    						     int sendType, 			   //类型
    						     java.lang.String postFixNumber, //保留
    						     int sign);				   //标记 0-仍在群发，1-群发完毕

    /**
     * 直接获取短信量信息
     */
    public StockDetails directGetStockDetails(java.lang.String userID, java.lang.String account, java.lang.String password) throws java.rmi.RemoteException;

    /**
     * 直接获取回复短信内容
     */
    public RepliedSMS directFetchSMS(java.lang.String userID, java.lang.String account, java.lang.String password) throws java.rmi.RemoteException;
    
    /**
     * 修改密码
     */
    public CommonReturn passwordChange(java.lang.String userID, java.lang.String account, java.lang.String password, java.lang.String newPSW) throws java.rmi.RemoteException;

    
}