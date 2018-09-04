package com.zhiwei.credit.model.admin;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * AssetsType Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class AssetsType extends com.zhiwei.core.model.BaseModel {

    protected Long assetsTypeId;
	protected String typeName;

//	protected java.util.Set fixedAssetss = new java.util.HashSet();

	/**
	 * Default Empty Constructor for class AssetsType
	 */
	public AssetsType () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class AssetsType
	 */
	public AssetsType (
		 Long in_assetsTypeId
        ) {
		this.setAssetsTypeId(in_assetsTypeId);
    }


//	public java.util.Set getFixedAssetss () {
//		return fixedAssetss;
//	}	
//	
//	public void setFixedAssetss (java.util.Set in_fixedAssetss) {
//		this.fixedAssetss = in_fixedAssetss;
//	}
//    

	/**
	 * 	 * @return Long
     * @hibernate.id column="assetsTypeId" type="java.lang.Long" generator-class="native"
	 */
	public Long getAssetsTypeId() {
		return this.assetsTypeId;
	}
	
	/**
	 * Set the assetsTypeId
	 */	
	public void setAssetsTypeId(Long aValue) {
		this.assetsTypeId = aValue;
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
		if (!(object instanceof AssetsType)) {
			return false;
		}
		AssetsType rhs = (AssetsType) object;
		return new EqualsBuilder()
				.append(this.assetsTypeId, rhs.assetsTypeId)
				.append(this.typeName, rhs.typeName)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.assetsTypeId) 
				.append(this.typeName) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("assetsTypeId", this.assetsTypeId) 
				.append("typeName", this.typeName) 
				.toString();
	}



}
