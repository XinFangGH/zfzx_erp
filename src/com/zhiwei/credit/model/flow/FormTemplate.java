package com.zhiwei.credit.model.flow;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * FormTemplate Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class FormTemplate extends com.zhiwei.core.model.BaseModel {
	/**
	 * TempType 1=Ext模板
	 * 2=Url模板
	 */
	public static final Short TEMP_TYPE_EXT=1;
	/**
	 * URL模板
	 * 
	 */
	public static final Short TEMP_TYPE_URL=2;
	/**
	 * JSP模板
	 */
	public static final Short TEMP_TYPE_JSP=3;
	
    protected Long templateId;
	protected String nodeName;
	protected String tempContent;
	protected String extDef;
	protected String formUrl;
	protected Short tempType=1;
	protected com.zhiwei.credit.model.flow.FormDefMapping formDefMapping;


	/**
	 * Default Empty Constructor for class FormTemplate
	 */
	public FormTemplate () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class FormTemplate
	 */
	public FormTemplate (
		 Long in_templateId
        ) {
		this.setTemplateId(in_templateId);
    }

	
	public com.zhiwei.credit.model.flow.FormDefMapping getFormDefMapping () {
		return formDefMapping;
	}	
	
	public void setFormDefMapping (com.zhiwei.credit.model.flow.FormDefMapping in_formDefMapping) {
		this.formDefMapping = in_formDefMapping;
	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="templateId" type="java.lang.Long" generator-class="native"
	 */
	public Long getTemplateId() {
		return this.templateId;
	}
	
	/**
	 * Set the templateId
	 */	
	public void setTemplateId(Long aValue) {
		this.templateId = aValue;
	}	

	/**
	 * 映射ID	 * @return Long
	 */
	public Long getMappingId() {
		return this.getFormDefMapping()==null?null:this.getFormDefMapping().getMappingId();
	}
	
	/**
	 * Set the mappingId
	 */	
	public void setMappingId(Long aValue) {
	    if (aValue==null) {
	    	formDefMapping = null;
	    } else if (formDefMapping == null) {
	        formDefMapping = new com.zhiwei.credit.model.flow.FormDefMapping(aValue);
	        formDefMapping.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
	    	//
			formDefMapping.setMappingId(aValue);
	    }
	}	

	/**
	 * 节点名称	 * @return String
	 * @hibernate.property column="nodeName" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getNodeName() {
		return this.nodeName;
	}
	
	/**
	 * Set the nodeName
	 * @spring.validator type="required"
	 */	
	public void setNodeName(String aValue) {
		this.nodeName = aValue;
	}	

	/**
	 * 模板内容	 * @return String
	 * @hibernate.property column="tempContent" type="java.lang.String" length="65535" not-null="false" unique="false"
	 */
	public String getTempContent() {
		return this.tempContent;
	}
	
	/**
	 * Set the tempContent
	 */	
	public void setTempContent(String aValue) {
		this.tempContent = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof FormTemplate)) {
			return false;
		}
		FormTemplate rhs = (FormTemplate) object;
		return new EqualsBuilder()
				.append(this.templateId, rhs.templateId)
						.append(this.nodeName, rhs.nodeName)
				.append(this.tempContent, rhs.tempContent)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.templateId) 
						.append(this.nodeName) 
				.append(this.tempContent) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("templateId", this.templateId) 
						.append("nodeName", this.nodeName) 
				.append("tempContent", this.tempContent) 
				.toString();
	}

	public String getExtDef() {
		return extDef;
	}

	public void setExtDef(String extDef) {
		this.extDef = extDef;
	}

	public String getFormUrl() {
		return formUrl;
	}

	public void setFormUrl(String formUrl) {
		this.formUrl = formUrl;
	}

	public Short getTempType() {
		return tempType;
	}

	public void setTempType(Short tempType) {
		this.tempType = tempType;
	}

}
