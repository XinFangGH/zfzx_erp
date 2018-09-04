package com.messageAlert.Action;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.messageAlert.model.SmsTemplate;
import com.messageAlert.service.SmsTemplateService;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.service.sms.MessageStrategyService;

public class SmsTemplateAction extends BaseAction{
	@Resource
	private SmsTemplateService smsTemplateService;
	
	private SmsTemplate smsTemplate;
	/**
	 * 获取erp短信模板.xml
	 * @return
	 */
	public String getTemplateXml(){
		//获取短信模板内容
		String appAbsolutePath = AppUtil.getAppAbsolutePath();
    	String configFilePath=appAbsolutePath+"WEB-INF/classes/conf/sms.xml";
		List<SmsTemplate> list =smsTemplateService.getSmsXml(configFilePath);
		Type type=new TypeToken<List<SmsTemplate>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true").append(",result:");
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		jsonString=buff.toString();
		return SUCCESS;
	}
	
	/**
	 * 修改短信模板信息
	 * @return
	 */
	public String updateTemplateXml(){
		if(null != smsTemplate.getKey() && !"".equals(smsTemplate.getKey())){
			//修改模板内容
			String appAbsolutePath = AppUtil.getAppAbsolutePath();
			String configFilePath=appAbsolutePath+"WEB-INF/classes/conf/sms.xml";
			smsTemplateService.updateSmsXml(smsTemplate, configFilePath);
			setJsonString("{success:true,msg:'修改模板成功！'}");
		}else{
			setJsonString("{success:true,msg:'修改模板失败，请检查短信模板是否正确！'}");
		}
		return SUCCESS;
	}
	
	/**
	 * 开始测试发送短信
	 * @return
	 */
	public String getSendInfo(){
		String content = this.getRequest().getParameter("content");
		String telphone = this.getRequest().getParameter("telphone");
		//读取模板
		String appAbsolutePath = AppUtil.getAppAbsolutePath();
    	String xmlPath=appAbsolutePath+"WEB-INF/classes/conf/sms.xml";
		List<SmsTemplate> list =smsTemplateService.getSmsXml(xmlPath);
		
		//读短信账号等信息
    	String configFilePath=appAbsolutePath+"WEB-INF/classes/conf/sendmessage_config.properties";
		Map<String,Object>  map = smsTemplateService.getAllSenduseExplainConfig(configFilePath);
		
		if(content!=null&&!content.equals("")){
			String replaceStr = map.get("replaceStr").toString();
			String replaceStrs[] = replaceStr.split("&");
			for(int i=0;i<replaceStrs.length;i++){
				String keyValue = replaceStrs[i];
				String keyValueStr[] = keyValue.split("=");
				content=content.replace(keyValueStr[0], keyValueStr[1]);
			}
			
			MessageStrategyService messageStrategy=(MessageStrategyService) AppUtil.getBean(map.get("sms_benname").toString());
			String result = messageStrategy.sendMsg(telphone,content);
			if(result!=null&&!result.equals("")){
				String  str[] = result.split("&");
				if(str.length>1){
					String strs[] = str[1].split("=");
					if(!strs[1].contains("??")){
						setJsonString("{success:true,msg:'"+strs[1]+"'}");
					}else{
						setJsonString("{success:true,msg:'链接失败'}");
					}
				}
			}else{
				  setJsonString("{success:true,msg:'发送失败，请检查短信模板是否正确！'}");
			}
		}
		return SUCCESS;
	}
	
	public SmsTemplate getSmsTemplate() {
		return smsTemplate;
	}

	public void setSmsTemplate(SmsTemplate smsTemplate) {
		this.smsTemplate = smsTemplate;
	}
}
