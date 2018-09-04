package com.messageAlert.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.messageAlert.model.SmsTemplate;
import com.messageAlert.service.SmsDetailsService;
import com.messageAlert.service.SmsTemplateService;
import com.zhiwei.core.util.AppUtil;

public class SmsDetailsServiceImpl implements SmsDetailsService {
	@Resource
	private SmsTemplateService smsTemplateService;
	
	Map configMap=AppUtil.getConfigMap();
	
	@Override
	public String getContent(Map<String, String> map) {
		//模板路径
		String appAbsolutePath = AppUtil.getAppAbsolutePath();
    	String xmlPath=appAbsolutePath+"WEB-INF/classes/conf/sms.xml";
    	map.put("filePath", xmlPath);
		String content = "";
		List<SmsTemplate> smsList = smsTemplateService.getSmsXml(map.get("filePath"));
		for(SmsTemplate smsTemplate : smsList){
			if(smsTemplate.getKey().equals(map.get("key"))){
				content = smsTemplate.getContent();
				content = content.replace("${subject}", configMap.get("subject").toString()).replace("${phone}", configMap.get("phone").toString());
				Iterator it = map.entrySet().iterator();
				while (it.hasNext()) {
				    Map.Entry entry = (Map.Entry) it.next();
				    String key = entry.getKey().toString();
				    String value = entry.getValue().toString();
				    int s = key.indexOf("${");  
				    if(s>=0){
				    	content = content.replace(key, value);
				    }
				}
			}
		}
		return content;
	}
	
}
