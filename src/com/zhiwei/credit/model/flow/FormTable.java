package com.zhiwei.credit.model.flow;

/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
 */
import java.util.Iterator;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

import com.google.gson.annotations.Expose;

/**
 * FormTable Base Java Bean, base class for the.credit.model, mapped directly to
 * database table
 * 
 * Avoid changing this file if not necessary, will be overwritten.
 * 
 * TODO: add class/table comments
 */
@SuppressWarnings("serial")
public class FormTable extends com.zhiwei.core.model.BaseModel {
	/**
	 * 流程表单产生表的表名前缀
	 */
	public static final String TABLE_PRE_NAME = "WF_";
	/**
	 * 主表
	 */
	public static final Short MAIN_TABLE = 1;
	/**
	 * 从表
	 */
	public static final Short NOT_MAIN_TABLE = 0;

	@Expose
	protected Long tableId;
	@Expose
	protected String tableName;
	@Expose
	protected String tableKey;
	@Expose
	protected Short isMain;
	transient protected com.zhiwei.credit.model.flow.FormDef formDef;
	@Expose
	protected java.util.Set<FormField> formFields = new java.util.HashSet<FormField>();

	/**
	 * 取得某个表其主键
	 * 
	 * @return
	 */
	public FormField getPrimaryField() {
		Iterator<FormField> it = formFields.iterator();
		while (it.hasNext()) {
			FormField formField = it.next();
			if (FormField.PRIMARY_KEY.equals(formField.getIsPrimary())) {
				return formField;
			}
		}
		return null;
	}

	/**
	 * 取得某个表标题字段
	 * 
	 * @return
	 */
	public FormField getFlowTitleField() {
		Iterator<FormField> it = formFields.iterator();
		while (it.hasNext()) {
			FormField formField = it.next();
			if (FormField.FLOW_TITLE.equals(formField.getIsFlowTitle())) {
				return formField;
			}
		}
		return null;
	}

	/**
	 * 实体名称
	 * 
	 * @return
	 */
	public String getEntityName() {
		return TABLE_PRE_NAME + tableKey;
	}

	// public String getEntityClass(){
	// return "com.zhiwei.credit.entity."+getEntityName();
	// }

	/**
	 * Default Empty Constructor for class FormTable
	 */
	public FormTable() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class FormTable
	 */
	public FormTable(Long in_tableId) {
		this.setTableId(in_tableId);
	}

	public com.zhiwei.credit.model.flow.FormDef getFormDef() {
		return formDef;
	}

	public void setFormDef(com.zhiwei.credit.model.flow.FormDef in_formDef) {
		this.formDef = in_formDef;
	}

	public java.util.Set<FormField> getFormFields() {
		return formFields;
	}

	public void setFormFields(java.util.Set<FormField> in_formFields) {
		this.formFields = in_formFields;
	}

	/**
	 * * @return Long
	 * 
	 * @hibernate.id column="tableId" type="java.lang.Long"
	 *               generator-class="native"
	 */
	public Long getTableId() {
		return this.tableId;
	}

	/**
	 * Set the tableId
	 */
	public void setTableId(Long aValue) {
		this.tableId = aValue;
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
	 * * @return String
	 * 
	 * @hibernate.property column="tableName" type="java.lang.String"
	 *                     length="128" not-null="true" unique="false"
	 */
	public String getTableName() {
		return this.tableName;
	}

	/**
	 * Set the tableName
	 * 
	 * @spring.validator type="required"
	 */
	public void setTableName(String aValue) {
		this.tableName = aValue;
	}

	/**
	 * * @return String
	 * 
	 * @hibernate.property column="tableKey" type="java.lang.String"
	 *                     length="128" not-null="true" unique="false"
	 */
	public String getTableKey() {
		return this.tableKey;
	}

	/**
	 * Set the tableKey
	 * 
	 * @spring.validator type="required"
	 */
	public void setTableKey(String aValue) {
		this.tableKey = aValue;
	}

	public Short getIsMain() {
		return isMain;
	}

	public void setIsMain(Short isMain) {
		this.isMain = isMain;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof FormTable)) {
			return false;
		}
		FormTable rhs = (FormTable) object;
		return new EqualsBuilder().append(this.tableId, rhs.tableId).append(
				this.tableName, rhs.tableName).append(this.tableKey,
				rhs.tableKey).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.tableId)
				.append(this.tableName).append(this.tableKey).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("tableId", this.tableId)
				.append("tableName", this.tableName).append("tableKey",
						this.tableKey).toString();
	}

}
