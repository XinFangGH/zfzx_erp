package com.messageAlert.service;

import java.util.Map;

public interface SmsSendService {
	/**
	 * 短信发送公共方法
	 * 此map集合,key为${***}的,都为短信内容参数,key不带${***}的，为获取模板的必要参数
	 */
	public void smsSending(Map<String, String> map);
}
