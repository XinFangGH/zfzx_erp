package com.messageAlert.service.impl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.messageAlert.model.SmsTemplate;
import com.messageAlert.service.SmsTemplateService;

public class SmsTemplateServiceImpl implements SmsTemplateService {
	
	protected transient final Log logger = LogFactory.getLog(getClass());
	
	@Override
	public List<SmsTemplate> getSmsXml(String filePath){
		List<SmsTemplate> smsList = new ArrayList<SmsTemplate>();
    	try{
    		Document document=null;  
	        SAXReader reader=new SAXReader();  
	        InputStream ifile = new FileInputStream(filePath); //防止出现中文路径报错
	        InputStreamReader ir = new InputStreamReader(ifile, "UTF-8"); 
	        document=reader.read(ir);  
	        //查找结点  
	        List list=document.selectNodes("/sms/templates/template");  
	        Iterator it=list.iterator();  
	        while(it.hasNext()){
	            Element element=(Element) it.next();//节点遍历  
	            SmsTemplate smsTemplate = new SmsTemplate();
	            smsTemplate.setKey(element.attribute("key").getValue());
	            smsTemplate.setUseExplain(element.attribute("useExplain").getValue());
	            smsTemplate.setProhibitUse(element.attribute("prohibitUse").getValue());
	            smsTemplate.setIsTest(element.attribute("isTest").getValue());
	            smsTemplate.setContent(element.getText().trim());
	            smsList.add(smsTemplate);
	        }
    	}catch(Exception ex){
    		logger.error(ex.getMessage());
    	}
    	return smsList;
	}

	@Override
	public void updateSmsXml(SmsTemplate smsTemplate,String filePath) {
		try {
			Document document=null;  
	        SAXReader reader=new SAXReader();  
	        InputStream ifile = new FileInputStream(filePath); //防止出现中文路径报错
	        InputStreamReader ir = new InputStreamReader(ifile, "UTF-8"); 
	        document=reader.read(ir);  
	        //查找结点  
	        List list=document.selectNodes("/sms/templates/template");  
	        Iterator it=list.iterator();
	        while(it.hasNext()){
	            Element element=(Element) it.next();//节点遍历  
	            Attribute attr=element.attribute("key");//节点属性值
	            if(attr.getValue().equals(smsTemplate.getKey())){//遍历出与页面传过的的key值相等的节点，处理对应节点数据
	            	element.attribute("prohibitUse").setValue(smsTemplate.getProhibitUse());
	            	element.attribute("isTest").setValue(smsTemplate.getIsTest());
	            	element.attribute("useExplain").setValue(smsTemplate.getUseExplain());
	            	element.setText(smsTemplate.getContent());
	            }
	        }
	        XMLWriter xmlwriter = new XMLWriter(new FileOutputStream(filePath));
	        xmlwriter.write(document);
	        xmlwriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	@Override
	public Map<String,Object> getAllSenduseExplainConfig(String filePath){
		Map<String,Object> map =new HashMap<String,Object>();
    	Properties props=new Properties();
    	try{
    		FileInputStream fis=new FileInputStream(filePath);
    		Reader r = new InputStreamReader(fis, "UTF-8"); 
    		props.load(r);
    		Iterator it= props.keySet().iterator();
    		while(it.hasNext()){
    			String key=(String)it.next();
    			map.put(key, props.get(key).toString());
    		}
    	}catch(Exception ex){
    		logger.error(ex.getMessage());
    	}
    	return map;
	}
	
}
