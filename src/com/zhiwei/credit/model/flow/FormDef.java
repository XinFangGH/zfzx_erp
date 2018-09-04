package com.zhiwei.credit.model.flow;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

import com.google.gson.annotations.Expose;

/**
 * FormDef Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class FormDef extends com.zhiwei.core.model.BaseModel {
	/**
	 * 缺省流程表单定义ID
	 */
	public static final Long DEFAULT_FLOW_FORMID=1l;
	/**
	 * 未生成实体
	 */
	public static final Short NOT_GEN=0;
	/**
	 * 生成实体
	 */
	public static final Short HAS_GEN=1;
	/**
	 * 发布状态
	 */
	public static final Short HAS_Pub=1;
	/**
	 * 草稿状态
	 */
	public static final Short NOT_Pub=0;
	@Expose
    protected Long formDefId;
	@Expose
	protected String formTitle;
	@Expose
	protected String formDesp;
	@Expose
	protected String defHtml;
	@Expose
	protected Short status;
	@Expose
	protected Short formType;
	@Expose
	protected Short isDefault;
	@Expose
	protected Short isGen;

	

	//transient protected java.util.Set formTables = new java.util.HashSet();
	protected java.util.Set<FormTable> formTables = new java.util.HashSet<FormTable>();
	
	/**
	 * 取得主表
	 * @return
	 */
	public FormTable getMainTable(){
		Iterator<FormTable> it=formTables.iterator();
		while(it.hasNext()){
			FormTable formTable=it.next();
			if(FormTable.MAIN_TABLE.equals(formTable.getIsMain())){
				return formTable;
			}
		}
		return null;
	}
	
	/**
	 * 取得从表
	 * @return
	 */
	public FormTable getSubTable(){
		Iterator<FormTable> it=formTables.iterator();
		while(it.hasNext()){
			FormTable formTable=it.next();
			if(!FormTable.MAIN_TABLE.equals(formTable.getIsMain())){
				return formTable;
			}
		}
		return null;
	}
	/**
	 * 取得所有子表
	 */
	public List<FormTable> getSubTables(){
		Iterator<FormTable> it=formTables.iterator();
		List<FormTable> formTables=new ArrayList<FormTable>();
		while(it.hasNext()){
			FormTable formTable=it.next();
			if(!FormTable.MAIN_TABLE.equals(formTable.getIsMain())){
				formTables.add(formTable);
			}
		}
		return formTables;
	}
	/**
	 * Default Empty Constructor for class FormDef
	 */
	public FormDef () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class FormDef
	 */
	public FormDef (
		 Long in_formDefId
        ) {
		this.setFormDefId(in_formDefId);
    }


	public java.util.Set getFormTables () {
		return formTables;
	}	
	
	public void setFormTables (java.util.Set in_formTables) {
		this.formTables = in_formTables;
	}
    

	/**
	 * 表单ID	 * @return Long
     * @hibernate.id column="formDefId" type="java.lang.Long" generator-class="native"
	 */
	public Long getFormDefId() {
		return this.formDefId;
	}
	
	/**
	 * Set the formDefId
	 */	
	public void setFormDefId(Long aValue) {
		this.formDefId = aValue;
	}	

	/**
	 * 表单标题	 * @return String
	 * @hibernate.property column="formTitle" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getFormTitle() {
		return this.formTitle;
	}
	
	/**
	 * Set the formTitle
	 * @spring.validator type="required"
	 */	
	public void setFormTitle(String aValue) {
		this.formTitle = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="formDesp" type="java.lang.String" length="65535" not-null="false" unique="false"
	 */
	public String getFormDesp() {
		return this.formDesp;
	}
	
	/**
	 * Set the formDesp
	 */	
	public void setFormDesp(String aValue) {
		this.formDesp = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="defHtml" type="java.lang.String" length="65535" not-null="false" unique="false"
	 */
	public String getDefHtml() {
		return this.defHtml;
	}
	
	/**
	 * Set the defHtml
	 */	
	public void setDefHtml(String aValue) {
		this.defHtml = aValue;
	}	

	/**
	 * 0=草稿状态
            1=正式状态	 * @return Short
	 * @hibernate.property column="status" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getStatus() {
		return this.status;
	}
	
	public Short getFormType() {
		return formType;
	}

	public void setFormType(Short formType) {
		this.formType = formType;
	}

	public Short getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Short isDefault) {
		this.isDefault = isDefault;
	}

	/**
	 * Set the status
	 * @spring.validator type="required"
	 */	
	public void setStatus(Short aValue) {
		this.status = aValue;
	}	
	public Short getIsGen() {
		return isGen;
	}
	public void setIsGen(Short isGen) {
		this.isGen = isGen;
	}
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof FormDef)) {
			return false;
		}
		FormDef rhs = (FormDef) object;
		return new EqualsBuilder()
				.append(this.formDefId, rhs.formDefId)
				.append(this.formTitle, rhs.formTitle)
				.append(this.formDesp, rhs.formDesp)
				.append(this.defHtml, rhs.defHtml)
				.append(this.status, rhs.status)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.formDefId) 
				.append(this.formTitle) 
				.append(this.formDesp) 
				.append(this.defHtml) 
				.append(this.status) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("formDefId", this.formDefId) 
				.append("formTitle", this.formTitle) 
				.append("formDesp", this.formDesp) 
				.append("defHtml", this.defHtml) 
				.append("status", this.status) 
				.toString();
	}



}
