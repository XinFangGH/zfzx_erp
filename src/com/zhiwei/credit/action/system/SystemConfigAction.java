package com.zhiwei.credit.action.system;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSON;
import net.sf.json.xml.XMLSerializer;

import org.dom4j.Document;
import org.dom4j.Element;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.model.BaseModel;
import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.util.XmlUtil;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;


import com.zhiwei.credit.model.creditFlow.assuretenet.OurProcreditAssuretenet;
import com.zhiwei.credit.model.system.CsHoliday;
import com.zhiwei.credit.service.system.CsHolidayService;
import com.zhiwei.credit.service.system.DictionaryIndependentService;
import com.zhiwei.credit.service.system.DictionaryService;
/**
 * 
 * @author 
 *实现对系统zhiwei.xml 的属性配置
 */
public class SystemConfigAction extends BaseAction{
	
private static String jsonStr="";
	
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		//将数据转成JSON格式
		String str="";
		String confPath = AppUtil.getAppAbsolutePath() + "/WEB-INF/classes";
		String dataInitFile = confPath + "/zhiwei.xml";
		Document rootDoc = XmlUtil.load(dataInitFile);
		if(rootDoc!=null){
		str=XmlUtil.docToString(rootDoc);
		}
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(JsonUtil.xmlStrToJson(str));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		String systemName=this.getRequest().getParameter("systemConfig.systemName");
		String systemVersion=this.getRequest().getParameter("systemConfig.systemVersion");
		String isGroupVersion=this.getRequest().getParameter("isGroupVersion");
		String isOA=this.getRequest().getParameter("isOA");
		String ftpIp=this.getRequest().getParameter("systemConfig.ftpIp");
		String graceDayNum=this.getRequest().getParameter("systemConfig.graceDayNum");
		String ftpUsName=this.getRequest().getParameter("systemConfig.ftpUsName");
		String ftpPss=this.getRequest().getParameter("systemConfig.ftpPss");
		
		String ftpPort=this.getRequest().getParameter("systemConfig.ftpPort");
		String zipOutPutPath=this.getRequest().getParameter("systemConfig.zipOutPutPath");
		String interest=this.getRequest().getParameter("insterest");
		String defaultInterest=this.getRequest().getParameter("defaultInterest");
		
		String isActualDay=this.getRequest().getParameter("isActualDay");
		String controlMoney=this.getRequest().getParameter("systemConfig.controlMoney");
		if(isGroupVersion.equals("1")){
				isGroupVersion="true";
		}else{
			isGroupVersion="false";
		}
		
		if(isOA.equals("1")){
			isOA="true";
			}else{
				isOA="false";
			}
		String confPath = AppUtil.getAppAbsolutePath() + "/WEB-INF/classes";
		String xmlFile = confPath + "/zhiwei.xml";
		Document rootDoc = XmlUtil.load(xmlFile);
		Element root=rootDoc.getRootElement();
		//取得 systemConfig元素 
		Element sysConItems = root.element("systemConfig");
		//取得 systemConfig 下的子元素 
		List<Element> list = sysConItems.elements();
		for(Element el : list){
			
			if(el.getName().equals("systemName")){
				el.setText(systemName);
			}else if(el.getName().equals("systemVersion")){
				el.setText(systemVersion);
			}else if(el.getName().equals("isGroupVersion")){
				el.setText(isGroupVersion);
			}else if(el.getName().equals("isOA")){
				el.setText(isOA);
			}else if(el.getName().equals("ftpIp")){
				el.setText(ftpIp);
			}else if(el.getName().equals("graceDayNum")){
				el.setText(graceDayNum);
			}else if(el.getName().equals("ftpUsName")){
				el.setText(ftpUsName);
			}else if(el.getName().equals("ftpPss")){
				el.setText(ftpPss);
			}else if(el.getName().equals("ftpPort")){
				el.setText(ftpPort);
			}else if(el.getName().equals("zipOutPutPath")){
				el.setText(zipOutPutPath);
			}else if(el.getName().equals("interest")){
				el.setText(interest);
			}/*else if(el.getName().equals("isActualDay")){
				el.setText(isActualDay);
			}*/else if(el.getName().equals("defaultInterest")){
				el.setText(defaultInterest);
			}else if(el.getName().equals("controlMoney")){
				el.setText(controlMoney);
			}else{
				
			}
		}
		XmlUtil.docToXmlFile(rootDoc, xmlFile);
		return SUCCESS;
		
	}

}
