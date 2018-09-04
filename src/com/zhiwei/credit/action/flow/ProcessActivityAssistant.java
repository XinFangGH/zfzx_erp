package com.zhiwei.credit.action.flow;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;

import com.zhiwei.core.jbpm.pv.ParamField;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.XmlUtil;

/**
 * 流程任务辅助类
 * @author csx
 *
 */
public class ProcessActivityAssistant {
	
	private static final  Log logger=LogFactory.getLog(ProcessActivityAssistant.class);
	
	public static Map<String,ParamField> constructMobileFieldMap(String processName,String activityName){
		String fieldsXmlLoaction=getMobileFieldsAbsPath(processName, activityName);
		return genFieldMap(processName,activityName,fieldsXmlLoaction);
	}
	
	public static Map<String, ParamField> constructFieldMap(String processName,String activityName){
		String fieldsXmlLoaction=getFieldAbsPath(processName, activityName);
		return genFieldMap(processName,activityName,fieldsXmlLoaction);
	}

	/**
	 * 构建提交的流程或任务对应的表单信息字段
	 * @param processName 流程名称
	 * @param activityName 活动名称
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, ParamField> genFieldMap(String processName,String activityName,String fieldsXmlLoaction){
		
		//String fieldsXmlLoaction=getFieldAbsPath(processName, activityName);
		
		InputStream is=null;
		Map<String,ParamField>map=new LinkedHashMap<String, ParamField>();
		try{
			is=new FileInputStream(fieldsXmlLoaction);
		}catch(Exception ex){
			logger.warn("error when read the file from " + activityName + "-fields.xml, the reason is not upload ");
		}
		
		if(is==null){
			try{
				is=new FileInputStream(getCommonFieldsAbsPath(activityName));
			}catch(Exception ex){
				logger.warn("error when read the file from 通用、表单-fields.xml, the reason is not upload ");
			}
		}
		
		Document doc=XmlUtil.load(is);
		if(doc!=null){
			Element fields=doc.getRootElement();
			List<Element> els = fields.elements();
			for(Element el:els){
				String name=el.attribute("name").getValue();
				
				Attribute attLabel = el.attribute("label");
				Attribute attType = el.attribute("type");
				Attribute attLength = el.attribute("length");
				Attribute attIsShowed=el.attribute("isShowed");
				
				String label=attLabel==null?name:attLabel.getValue();
				String type=attType==null?ParamField.FIELD_TYPE_VARCHAR:attType.getValue();
				Integer length=attLength==null?0:new Integer(attLength.getValue());
				Short isShowed=(attIsShowed==null||"true".equals(attIsShowed.getValue()))?(short)1:(short)0;
				
				ParamField pf=new ParamField(name,type,label,length,isShowed);
				map.put(name, pf);
			}
		}

		return map;
	}
	
	public static String getStartFormPath(String processName){
		return  "/"+ processName+"/开始.vm";
	}
	/**
	 * 
	 * @param processName
	 * @return
	 */
	public static String getFormPath(String processName,String activityName){
		return "/"+ processName+"/" + activityName+ ".vm";
	}
	
	/**
	 * 取得某流程表单的映射字段文件绝对路径
	 * @param processName
	 * @param activityName
	 * @return
	 */
	public static String getFieldAbsPath(String processName,String activityName){
		return AppUtil.getFlowFormAbsolutePath() + processName+"/" + activityName + "-fields.xml";
	}
	
	/**
	 * 取得手机下的流程模块表单的映射字段文件绝对路径
	 * @param processName
	 * @param activityName
	 * @return
	 */
	public static String getMobileFieldsAbsPath(String processName,String activityName){
		return AppUtil.getMobileFlowFlowAbsPath() + processName+"/" + activityName + "-fields.xml";
		
	}
	
	/**
	 * 目的是为了兼容V1.2版本把所有开始节点均命名为"开始"
	 * @param processName
	 * @return
	 */
	public static String getFieldStartAbsPath(String processName){
		return AppUtil.getFlowFormAbsolutePath() + processName+"/开始-fields.xml";
	}
	

	/**
	 * 取得通用的开始表单
	 * @return
	 */
	public static String getCommonFormPath(String activityName){
		if("开始".equals(activityName)){
			return "/通用/开始.vm";
		}else{
			return "/通用/表单.vm";
		}
		
	}
	/**
	 * 取得通用的映射字段文件绝对路径
	 * @param activityName
	 * @return
	 */
	public static String getCommonFieldsAbsPath(String activityName){		
		String absPath=AppUtil.getFlowFormAbsolutePath();
		
		if("开始".equals(activityName)){
			return absPath+"通用/开始-fields.xml";
		}else{
			return absPath+"通用/表单-fields.xml";
		}
	}
	
}
