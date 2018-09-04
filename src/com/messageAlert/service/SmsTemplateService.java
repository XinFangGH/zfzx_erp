package com.messageAlert.service;

import java.util.List;
import java.util.Map;

import com.messageAlert.model.SmsTemplate;


public interface SmsTemplateService{
	/**
	 * 使用Dom4j读取xml文件，拿到短信模板
	 * @param filePath  文件路径
	 * @return
	 */
	public List<SmsTemplate> getSmsXml(String filePath);
	/**
	 * 修改短信模板
	 * @param smsTemplate
	 */
	public void updateSmsXml(SmsTemplate smsTemplate,String filePath);
	/**
	 * 读取短信配置信息
	 * @param filePath
	 * @return
	 */
	public Map<String,Object> getAllSenduseExplainConfig(String filePath);
}
