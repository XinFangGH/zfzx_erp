package com.zhiwei.credit.model.flow;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

import com.google.gson.annotations.Expose;
import com.zhiwei.credit.FlowConstants;

/**
 * FormField Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class FormField extends com.zhiwei.core.model.BaseModel {
	/**
	 * 表单标题
	 */
	public static final Short FLOW_TITLE=1;
	/**
	 * 非表单标题
	 */
	public static final Short NOT_FLOW_TITLE=0;

	/**
	 * 主键
	 */
	public static final Short PRIMARY_KEY=1;
	/**
	 * 非主键
	 */
	public static final Short NOT_PRIMARY_KEY=0;
	/**
	 * 设计的可视化
	 */
	public static final Short IS_SHOW=1;
	/**
	 * 设计的不可视化
	 */
	public static final Short UN_SHOW=2;
	/**
	 * 手工加入
	 */
	public static final Short HAND_IN=3;
	@Expose
    protected Long fieldId;
	@Expose
	protected String fieldName;
	@Expose
	protected String fieldLabel;
	@Expose
	protected String fieldType;
	@Expose
	protected Short isRequired;
	@Expose
	protected Integer fieldSize;
	@Expose
	protected String fieldDscp;
	@Expose
	protected Short isPrimary;
	@Expose
	protected String foreignKey;
	@Expose
	protected String foreignTable;
	@Expose
	protected Short isList;
	@Expose
	protected Short isQuery;
	@Expose
	protected String showFormat;
	transient protected com.zhiwei.credit.model.flow.FormTable formTable;
	@Expose
	protected Short isFlowTitle;
    @Expose
    protected Short isDesignShow;
    protected java.util.Set<FieldRights> fieldRightss = new java.util.HashSet<FieldRights>();
	/**
	 * Default Empty Constructor for class FormField
	 */
	public FormField () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class FormField
	 */
	public FormField (
		 Long in_fieldId
        ) {
		this.setFieldId(in_fieldId);
    }

	
	public com.zhiwei.credit.model.flow.FormTable getFormTable () {
		return formTable;
	}	
	
	public void setFormTable (com.zhiwei.credit.model.flow.FormTable in_formTable) {
		this.formTable = in_formTable;
	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="fieldId" type="java.lang.Long" generator-class="native"
	 */
	public Long getFieldId() {
		return this.fieldId;
	}
	
	/**
	 * Set the fieldId
	 */	
	public void setFieldId(Long aValue) {
		this.fieldId = aValue;
	}	

	/**
	 * 	 * @return Long
	 */
	public Long getTableId() {
		return this.getFormTable()==null?null:this.getFormTable().getTableId();
	}
	
	/**
	 * Set the tableId
	 */	
	public void setTableId(Long aValue) {
	    if (aValue==null) {
	    	formTable = null;
	    } else if (formTable == null) {
	        formTable = new com.zhiwei.credit.model.flow.FormTable(aValue);
	        formTable.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
	    	//
			formTable.setTableId(aValue);
	    }
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="fieldName" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getFieldName() {
		return this.fieldName;
	}
	
	/**
	 * Set the fieldName
	 */	
	public void setFieldName(String aValue) {
		this.fieldName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="fieldType" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getFieldType() {
		return this.fieldType;
	}
	
	/**
	 * Set the fieldType
	 */	
	public void setFieldType(String aValue) {
		this.fieldType = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="isRequired" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getIsRequired() {
		return this.isRequired;
	}
	
	/**
	 * Set the isRequired
	 */	
	public void setIsRequired(Short aValue) {
		this.isRequired = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="fieldSize" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getFieldSize() {
		return this.fieldSize;
	}
	
	/**
	 * Set the fieldSize
	 */	
	public void setFieldSize(Integer aValue) {
		this.fieldSize = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="fieldDscp" type="java.lang.String" length="1024" not-null="false" unique="false"
	 */
	public String getFieldDscp() {
		return this.fieldDscp;
	}
	
	/**
	 * Set the fieldDscp
	 */	
	public void setFieldDscp(String aValue) {
		this.fieldDscp = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="isPrimary" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getIsPrimary() {
		return this.isPrimary;
	}
	
	/**
	 * Set the isPrimary
	 */	
	public void setIsPrimary(Short aValue) {
		this.isPrimary = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="foreignKey" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getForeignKey() {
		return this.foreignKey;
	}
	
	/**
	 * Set the foreignKey
	 */	
	public void setForeignKey(String aValue) {
		this.foreignKey = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="foreignTable" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getForeignTable() {
		return this.foreignTable;
	}
	
	/**
	 * Set the foreignTable
	 */	
	public void setForeignTable(String aValue) {
		this.foreignTable = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="isList" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getIsList() {
		return this.isList;
	}
	
	/**
	 * Set the isList
	 */	
	public void setIsList(Short aValue) {
		this.isList = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="isQuery" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getIsQuery() {
		return this.isQuery;
	}
	
	/**
	 * Set the isQuery
	 */	
	public void setIsQuery(Short aValue) {
		this.isQuery = aValue;
	}	

	public String getShowFormat() {
		return showFormat;
	}

	public void setShowFormat(String showFormat) {
		this.showFormat = showFormat;
	}
	
	public Short getIsFlowTitle() {
		return isFlowTitle;
	}

	public void setIsFlowTitle(Short isFlowTitle) {
		this.isFlowTitle = isFlowTitle;
	}

	public String getFieldLabel() {
		return fieldLabel;
	}

	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}
	
	public Short getIsDesignShow() {
		return isDesignShow;
	}

	public void setIsDesignShow(Short isDesignShow) {
		this.isDesignShow = isDesignShow;
	}

	/**
	 * 返回其JAVA类型
	 * @return
	 */
	public Class getFieldJavaClass(){
		return FlowConstants.FIELD_TYPE_MAP.get(fieldType);
	}

	public java.util.Set<FieldRights> getFieldRightss() {
		return fieldRightss;
	}

	public void setFieldRightss(java.util.Set<FieldRights> fieldRightss) {
		this.fieldRightss = fieldRightss;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof FormField)) {
			return false;
		}
		FormField rhs = (FormField) object;
		return new EqualsBuilder()
				.append(this.fieldId, rhs.fieldId)
						.append(this.fieldName, rhs.fieldName)
				.append(this.fieldType, rhs.fieldType)
				.append(this.isRequired, rhs.isRequired)
				.append(this.fieldSize, rhs.fieldSize)
				.append(this.fieldDscp, rhs.fieldDscp)
				.append(this.isPrimary, rhs.isPrimary)
				.append(this.foreignKey, rhs.foreignKey)
				.append(this.foreignTable, rhs.foreignTable)
				.append(this.isList, rhs.isList)
				.append(this.isQuery, rhs.isQuery)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.fieldId) 
						.append(this.fieldName) 
				.append(this.fieldType) 
				.append(this.isRequired) 
				.append(this.fieldSize) 
				.append(this.fieldDscp) 
				.append(this.isPrimary) 
				.append(this.foreignKey) 
				.append(this.foreignTable) 
				.append(this.isList) 
				.append(this.isQuery) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("fieldId", this.fieldId) 
						.append("fieldName", this.fieldName) 
				.append("fieldType", this.fieldType) 
				.append("isRequired", this.isRequired) 
				.append("fieldSize", this.fieldSize) 
				.append("fieldDscp", this.fieldDscp) 
				.append("isPrimary", this.isPrimary) 
				.append("foreignKey", this.foreignKey) 
				.append("foreignTable", this.foreignTable) 
				.append("isList", this.isList) 
				.append("isQuery", this.isQuery) 
				.toString();
	}

	

}
