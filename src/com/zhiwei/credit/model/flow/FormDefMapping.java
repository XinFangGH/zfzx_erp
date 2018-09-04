package com.zhiwei.credit.model.flow;

/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
 */
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * @description 表单定义映射管理
 * @class FormDefMapping
 * @author 智维软件
 * @updater YHZ
 * @company www.credit-software.com
 * @data 2010-12-28AM
 */
@SuppressWarnings("serial")
public class FormDefMapping extends com.zhiwei.core.model.BaseModel {
	/**
	 * 使用模板
	 */
	public final static Short USE_TEMPLATE=1;
	/**
	 * 非使用模板
	 */
	public final static Short NOT_USE_TEMPLATE=0;
	
	protected Long mappingId;
	protected Integer versionNo;
	protected com.zhiwei.credit.model.flow.ProDefinition proDefinition;
	protected com.zhiwei.credit.model.flow.FormDef formDef;

	protected String deployId;
	/**
	 * 使用模板作为表单
	 */
	protected Short useTemplate;
	
	protected java.util.Set<FieldRights> fieldRightss = new java.util.HashSet<FieldRights>();

	/**
	 * Default Empty Constructor for class FormDefMapping
	 */
	public FormDefMapping() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class FormDefMapping
	 */
	public FormDefMapping(Long in_mappingId) {
		this.setMappingId(in_mappingId);
	}

	public com.zhiwei.credit.model.flow.ProDefinition getProDefinition() {
		return proDefinition;
	}

	public void setProDefinition(
			com.zhiwei.credit.model.flow.ProDefinition in_proDefinition) {
		this.proDefinition = in_proDefinition;
	}

	public com.zhiwei.credit.model.flow.FormDef getFormDef() {
		return formDef;
	}

	public void setFormDef(com.zhiwei.credit.model.flow.FormDef in_formDef) {
		this.formDef = in_formDef;
	}

	public java.util.Set<FieldRights> getFieldRightss() {
		return fieldRightss;
	}

	public void setFieldRightss(java.util.Set<FieldRights> in_fieldRightss) {
		this.fieldRightss = in_fieldRightss;
	}

	/**
	 * * @return Long
	 * 
	 * @hibernate.id column="mappingId" type="java.lang.Long"
	 *               generator-class="native"
	 */
	public Long getMappingId() {
		return this.mappingId;
	}

	/**
	 * Set the mappingId
	 */
	public void setMappingId(Long aValue) {
		this.mappingId = aValue;
	}

	/**
	 * 表单ID * @return Long
	 */
	public Long getFormDefId() {
		return this.getFormDef() == null ? null : this.getFormDef()
				.getFormDefId();
	}

	/**
	 * Set the formDefId
	 */
	public void setFormDefId(Long aValue) {
		if (aValue == null) {
			formDef = null;
		} else if (formDef == null) {
			formDef = new com.zhiwei.credit.model.flow.FormDef(aValue);
			formDef.setVersion(new Integer(0));// set a version to cheat
			// hibernate only
		} else {
			//
			formDef.setFormDefId(aValue);
		}
	}

	/**
	 * * @return Long
	 */
	public Long getDefId() {
		return this.getProDefinition() == null ? null : this.getProDefinition()
				.getDefId();
	}

	/**
	 * Set the defId
	 */
	public void setDefId(Long aValue) {
		if (aValue == null) {
			proDefinition = null;
		} else if (proDefinition == null) {
			proDefinition = new com.zhiwei.credit.model.flow.ProDefinition(aValue);
			proDefinition.setVersion(new Integer(0));// set a version to cheat
			// hibernate only
		} else {
			//
			proDefinition.setDefId(aValue);
		}
	}

	/**
	 * * @return Integer
	 * 
	 * @hibernate.property column="versionNo" type="java.lang.Integer"
	 *                     length="10" not-null="true" unique="false"
	 */
	public Integer getVersionNo() {
		return this.versionNo;
	}

	/**
	 * Set the versionNo
	 * 
	 * @spring.validator type="required"
	 */
	public void setVersionNo(Integer aValue) {
		this.versionNo = aValue;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof FormDefMapping)) {
			return false;
		}
		FormDefMapping rhs = (FormDefMapping) object;
		return new EqualsBuilder().append(this.mappingId, rhs.mappingId)
				.append(this.versionNo, rhs.versionNo).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.mappingId).append(this.versionNo).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("mappingId", this.mappingId)
				.append("versionNo", this.versionNo).toString();
	}

	public String getDeployId() {
		return deployId;
	}

	public void setDeployId(String deployId) {
		this.deployId = deployId;
	}

	public Short getUseTemplate() {
		return useTemplate;
	}

	public void setUseTemplate(Short useTemplate) {
		this.useTemplate = useTemplate;
	}
	
	

}
