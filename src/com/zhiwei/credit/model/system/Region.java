package com.zhiwei.credit.model.system;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * Region Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * ������������
 */
public class Region extends com.zhiwei.core.model.BaseModel {

    protected Long regionId;
	protected String regionName;
	protected Short regionType;
	protected Long parentId;


	/**
	 * Default Empty Constructor for class Region
	 */
	public Region () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Region
	 */
	public Region (
		 Long in_regionId
        ) {
		this.setRegionId(in_regionId);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="regionId" type="java.lang.Long" generator-class="native"
	 */
	public Long getRegionId() {
		return this.regionId;
	}
	
	/**
	 * Set the regionId
	 */	
	public void setRegionId(Long aValue) {
		this.regionId = aValue;
	}	

	/**
	 * 地区名称	 * @return String
	 * @hibernate.property column="regionName" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getRegionName() {
		return this.regionName;
	}
	
	/**
	 * Set the regionName
	 * @spring.validator type="required"
	 */	
	public void setRegionName(String aValue) {
		this.regionName = aValue;
	}	

	/**
	 * 地区类型
            1=省份
            2=市	 * @return Short
	 * @hibernate.property column="regionType" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getRegionType() {
		return this.regionType;
	}
	
	/**
	 * Set the regionType
	 * @spring.validator type="required"
	 */	
	public void setRegionType(Short aValue) {
		this.regionType = aValue;
	}	

	/**
	 * 上级地区	 * @return Long
	 * @hibernate.property column="parentId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getParentId() {
		return this.parentId;
	}
	
	/**
	 * Set the parentId
	 */	
	public void setParentId(Long aValue) {
		this.parentId = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Region)) {
			return false;
		}
		Region rhs = (Region) object;
		return new EqualsBuilder()
				.append(this.regionId, rhs.regionId)
				.append(this.regionName, rhs.regionName)
				.append(this.regionType, rhs.regionType)
				.append(this.parentId, rhs.parentId)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.regionId) 
				.append(this.regionName) 
				.append(this.regionType) 
				.append(this.parentId) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("regionId", this.regionId) 
				.append("regionName", this.regionName) 
				.append("regionType", this.regionType) 
				.append("parentId", this.parentId) 
				.toString();
	}



}
