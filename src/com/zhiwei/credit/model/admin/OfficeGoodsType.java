package com.zhiwei.credit.model.admin;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * OfficeGoodsType Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * ������������������
 */
public class OfficeGoodsType extends com.zhiwei.core.model.BaseModel {

    protected Long typeId;
	protected String typeName;

//	protected java.util.Set officeGoodss = new java.util.HashSet();

	/**
	 * Default Empty Constructor for class OfficeGoodsType
	 */
	public OfficeGoodsType () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class OfficeGoodsType
	 */
	public OfficeGoodsType (
		 Long in_typeId
        ) {
		this.setTypeId(in_typeId);
    }


//	public java.util.Set getOfficeGoodss () {
//		return officeGoodss;
//	}	
//	
//	public void setOfficeGoodss (java.util.Set in_officeGoodss) {
//		this.officeGoodss = in_officeGoodss;
//	}
//    

	/**
	 * 	 * @return Long
     * @hibernate.id column="typeId" type="java.lang.Long" generator-class="native"
	 */
	public Long getTypeId() {
		return this.typeId;
	}
	
	/**
	 * Set the typeId
	 */	
	public void setTypeId(Long aValue) {
		this.typeId = aValue;
	}	

	/**
	 * 分类名称	 * @return String
	 * @hibernate.property column="typeName" type="java.lang.String" length="128" not-null="true" unique="false"
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
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof OfficeGoodsType)) {
			return false;
		}
		OfficeGoodsType rhs = (OfficeGoodsType) object;
		return new EqualsBuilder()
				.append(this.typeId, rhs.typeId)
				.append(this.typeName, rhs.typeName)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.typeId) 
				.append(this.typeName) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("typeId", this.typeId) 
				.append("typeName", this.typeName) 
				.toString();
	}



}
