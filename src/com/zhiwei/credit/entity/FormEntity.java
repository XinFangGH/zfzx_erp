package com.zhiwei.credit.entity;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;


import com.zhiwei.core.util.FunctionsUtil;
/**
 * 流程表单基类实体
 * <B><P>EST-BPM -- http://www.hurongtime.com</P></B>
 * <B><P>Copyright (C)  JinZhi WanWei Software Company (北京互融时代软件有限公司)</P></B> 
 * <B><P>description:</P></B>
 * <P></P>
 * <P>product:joffice</P>
 * <P></P> 
 * @see com.zhiwei.credit.entity.FormEntity
 * <P></P>
 * @author 
 * @version V1
 * @create: 2010-12-30下午02:05:05
 */
public class FormEntity implements Serializable{
	
	
	/**
	 * 返回html详细信息,若表单实体实现该方法，则会直接调用该方法进行显示数据格式，
	 * 父类只是用于显示各个字段的简单的数据，子类可以重写以
	 * @return
	 */
	
	public String getHtml(){
		StringBuffer sb=new StringBuffer();
		Field[]fields=getClass().getDeclaredFields();
		for(Field field:fields){
			try{
				Method getMethod=getClass().getDeclaredMethod("get"+FunctionsUtil.makeFirstLetterUpperCase(field.getName()),new Class<?>[]{});
				if(getMethod!=null){
					Object val=getMethod.invoke(this, new Object[]{});
					if(field.getType().toString().indexOf("java.util.Date")!=-1){
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						val=sdf.format(val);
					}
					
					sb.append("<b>"+field.getName()+":</b>").append(val.toString()).append("<br/>");
				}
			}catch(Exception ex){
				
			}
		}
		return sb.toString();
	}
}
