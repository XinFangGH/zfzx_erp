package com.zhiwei.credit.service.sms;

/**
 * 发送短信接口
 * svn:songwj
 * */
public interface MessageStrategyService {

	/**
	 * 发送短信方法
	 * @param message  短信内容 
	 * */
	public  String sendMsg(String phone,String content);
}
   