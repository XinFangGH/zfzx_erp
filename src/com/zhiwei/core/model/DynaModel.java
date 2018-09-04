package com.zhiwei.core.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.zhiwei.credit.model.flow.FormField;
import com.zhiwei.credit.model.flow.FormTable;

/**
 * Hibernate 动态实体类型
 * <B><P>EST-BPM -- http://www.hurongtime.com</P></B>
 * <B><P>Copyright (C)  JinZhi WanWei Software Company (北京互融时代软件有限公司)</P></B> 
 * <B><P>description:</P></B>
 * <P></P>
 * <P>product:joffice</P>
 * <P></P> 
 * @see com.hurong.core.model.DynaModel
 * <P></P>
 * @author 
 * @version V1
 * @create: 2011-1-22上午11:25:05
 */
public class DynaModel {
	
	/**
	 * 主键名
	 */
	private String primaryFieldName;
	/**
	 * 标题字段名
	 */
	private String subjectFieldName;
	
	/**
	 * 实体名称
	 */
	private String entityName;

	/**
	 * 字段数据<字段名，字段值>
	 */
	private Map<String, Object> datas=new HashMap<String, Object>();
	/**
	 * 字段类型<字段名，字段类型>
	 */
	private Map<String,Class> types=new HashMap<String, Class>();
	
	/**
	 * 字段显示的格式<字段名，字段格式>(格式如 yyyy-MM-dd或000.00等)
	 */
	private Map<String,String> formats=new HashMap<String,String>();
	/**
	 * 字段的标签格式<字段名，字段标签>
	 */
	private Map<String,String> labels=new HashMap<String,String>();

	public DynaModel() {
		
	}
	
	public DynaModel(String entityName){
		this.entityName=entityName;
	}
	
	/**
	 * 通过FormTable构造
	 * @param formTable
	 */
	public DynaModel(FormTable formTable){
		this.entityName=formTable.getEntityName();
		Iterator<FormField> it= formTable.getFormFields().iterator();
		while(it.hasNext()){
			FormField field=it.next();
			types.put(field.getFieldName(), field.getFieldJavaClass());
			labels.put(field.getFieldName(), field.getFieldLabel());
			if(StringUtils.isNotEmpty(field.getShowFormat())){
				formats.put(field.getFieldName(), field.getShowFormat());
			}
			//标题字段
			if(FormField.FLOW_TITLE.equals(field.getIsFlowTitle())){
				subjectFieldName=field.getFieldName();
			}
			//主键字段
			if(FormField.PRIMARY_KEY.equals(field.getIsPrimary())){
				primaryFieldName=field.getFieldName();
			}
		}
	}
	
	public static void main(String[]args){
		
	}

	public String getPrimaryFieldName() {
		return primaryFieldName;
	}

	public void setPrimaryFieldName(String primaryFieldName) {
		this.primaryFieldName = primaryFieldName;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public Map<String, Object> getDatas() {
		return datas;
	}

	public void setDatas(Map<String, Object> datas) {
		this.datas = datas;
	}

	public Map<String, Class> getTypes() {
		return types;
	}

	public void setTypes(Map<String, Class> types) {
		this.types = types;
	}
	
	public Map<String, String> getFormats() {
		return formats;
	}

	public void setFormats(Map<String, String> formats) {
		this.formats = formats;
	}

	public Object get(String filedName){
		return this.datas.get(filedName);
	}
	
	public void set(String filedName,Object value){
		this.datas.put(filedName,value);
	}
	
	public Class getType(String filedName){
		return this.types.get(filedName);
	}
	
	public void setType(String filedName,Class types){
		this.types.put(filedName,types);
	}

	public void setFormat(String fieldName,String format){
		this.formats.put(fieldName, format);
	}
	
	public String getFormat(String fieldName){
		return this.formats.get(fieldName);
	}

	public String getSubjectFieldName() {
		return subjectFieldName;
	}

	public void setSubjectFieldName(String subjectFieldName) {
		this.subjectFieldName = subjectFieldName;
	}

	public Map<String, String> getLabels() {
		return labels;
	}

	public void setLabels(Map<String, String> labels) {
		this.labels = labels;
	}
	
	public String getLabel(String fieldName){
		return this.labels.get(fieldName);
	}
	
	public void setLabel(String filedName,String fieldLabel){
		this.labels.put(fieldLabel, fieldLabel);
	}
	/**
	 * 取到主键值
	 * @return
	 */
	public Serializable getPkValue(){
		return (Serializable)datas.get(primaryFieldName);
	}
}
