package com.zhiwei.credit.model.flow;

import com.zhiwei.core.jbpm.pv.ParamField;

/**
 * 对应流程EXT表单定义中的控件的属性
 * 
 * @author csx
 * 
 */
public class ExtFormItem {
	private String name;
	private String xtype;
	private boolean allowBlank;
	private String fieldLabel;
	private String format;
	private int maxLength;
	private boolean hidden;

	public String getIsShowed(){
		if(hidden){
			return "false";
		}
		return "true";
	}
	
	public String getType(){
		
		if("textfield".equals(xtype) || "textarea".equals(xtype)){
			return ParamField.FIELD_TYPE_VARCHAR;
		}else if("datefield".equals(xtype)){
			return ParamField.FIELD_TYPE_DATE;
		}else if("radio".equals(xtype)){
			return ParamField.FIELD_TYPE_VARCHAR;
		}else if("checkbox".equals(xtype)){
			return ParamField.FIELD_TYPE_VARCHAR;
		}else if("numberfield".equals(xtype)){
			return ParamField.FIELD_TYPE_DECIMAL;
		}
		
		return ParamField.FIELD_TYPE_VARCHAR;
	}


	public ExtFormItem() {
		super();
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getXtype() {
		return xtype;
	}


	public void setXtype(String xtype) {
		this.xtype = xtype;
	}


	public boolean isAllowBlank() {
		return allowBlank;
	}


	public void setAllowBlank(boolean allowBlank) {
		this.allowBlank = allowBlank;
	}


	public String getFieldLabel() {
		return fieldLabel;
	}


	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}


	public String getFormat() {
		return format;
	}


	public void setFormat(String format) {
		this.format = format;
	}


	public int getMaxLength() {
		return maxLength;
	}


	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}


	public boolean isHidden() {
		return hidden;
	}


	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

}
