package com.messageAlert.service;

import java.util.Map;

public interface SmsDetailsService {
	/**
	 * 方法说明:此map集合,key为${***}的,都为短信内容参数,key不带${***}的，为获取模板的必要参数
	 * @param map
	 * @return
	 */
	public String getContent(Map<String, String> map);
}
