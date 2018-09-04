package com.zhiwei.credit.model.admin;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * DepreType Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * depreciation type
 */
public class DepreType extends com.zhiwei.core.model.BaseModel {

    protected Long depreTypeId;
	protected String typeName;
	
	protected Integer deprePeriod;
	protected String typeDesc;
	protected Short calMethod;

//	protected java.util.Set fixedAssetss = new java.util.HashSet();

	/**
	 * Default Empty Constructor for class DepreType
	 */
	public DepreType () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class DepreType
	 */
	public DepreType (
		 Long in_depreTypeId
        ) {
		this.setDepreTypeId(in_depreTypeId);
    }


//	public java.util.Set getFixedAssetss () {
//		return fixedAssetss;
//	}	
//	
//	public void setFixedAssetss (java.util.Set in_fixedAssetss) {
//		this.fixedAssetss = in_fixedAssetss;
//	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="depreTypeId" type="java.lang.Long" generator-class="native"
	 */
	public Long getDepreTypeId() {
		return this.depreTypeId;
	}
	
	/**
	 * Set the depreTypeId
	 */	
	public void setDepreTypeId(Long aValue) {
		this.depreTypeId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="typeName" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getTypeName() {
		return this.typeName;
	}
	
	/**
	 * Set the typeName
	 * @spring.validator type="required"
	 */	
	public void setTypeName(String aValue) {
		this.typeName = aValue;
	}	

	

	/**
	 * 单位为月	 * @return java.math.BigDecimal
	 * @hibernate.property column="deprePeriod" type="java.math.BigDecimal" length="18" not-null="true" unique="false"
	 */
	public Integer getDeprePeriod() {
		return this.deprePeriod;
	}
	
	/**
	 * Set the deprePeriod
	 * @spring.validator type="required"
	 */	
	public void setDeprePeriod(Integer aValue) {
		this.deprePeriod = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="typeDesc" type="java.lang.String" length="500" not-null="false" unique="false"
	 */
	public String getTypeDesc() {
		return this.typeDesc;
	}
	
	/**
	 * Set the typeDesc
	 */	
	public void setTypeDesc(String aValue) {
		this.typeDesc = aValue;
	}	

	/**
	 * 1=平均年限法
            2=工作量法
            加速折旧法
            3=双倍余额递减法
            4=年数总和法	 * @return Short
	 * @hibernate.property column="calMethod" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getCalMethod() {
		return this.calMethod;
	}
	
	/**
	 * Set the calMethod
	 * @spring.validator type="required"
	 */	
	public void setCalMethod(Short aValue) {
		this.calMethod = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof DepreType)) {
			return false;
		}
		DepreType rhs = (DepreType) object;
		return new EqualsBuilder()
				.append(this.depreTypeId, rhs.depreTypeId)
				.append(this.typeName, rhs.typeName)
				.append(this.deprePeriod, rhs.deprePeriod)
				.append(this.typeDesc, rhs.typeDesc)
				.append(this.calMethod, rhs.calMethod)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.depreTypeId) 
				.append(this.typeName)
				.append(this.deprePeriod) 
				.append(this.typeDesc) 
				.append(this.calMethod) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("depreTypeId", this.depreTypeId) 
				.append("typeName", this.typeName) 
				.append("deprePeriod", this.deprePeriod) 
				.append("typeDesc", this.typeDesc) 
				.append("calMethod", this.calMethod) 
				.toString();
	}



}
