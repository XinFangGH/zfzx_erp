package com.zhiwei.credit.model.personal;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * DutySystem Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 
 */
public class DutySystem extends com.zhiwei.core.model.BaseModel {
	//缺省的班制
	public static final Short DEFAULT=1;
	//非缺省的班制
	public static final Short NOT_DEFAULT=0;
	
    protected Long systemId;
	protected String systemName;
	protected String systemSetting;
	protected String systemDesc;
	protected Short isDefault;


	/**
	 * Default Empty Constructor for class DutySystem
	 */
	public DutySystem () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class DutySystem
	 */
	public DutySystem (
		 Long in_systemId
        ) {
		this.setSystemId(in_systemId);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="systemId" type="java.lang.Long" generator-class="native"
	 */
	public Long getSystemId() {
		return this.systemId;
	}
	
	/**
	 * Set the systemId
	 */	
	public void setSystemId(Long aValue) {
		this.systemId = aValue;
	}	

	/**
	 * 班制名称	 * @return String
	 * @hibernate.property column="systemName" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getSystemName() {
		return this.systemName;
	}
	
	/**
	 * Set the systemName
	 * @spring.validator type="required"
	 */	
	public void setSystemName(String aValue) {
		this.systemName = aValue;
	}	

	/**
	 * 班次	 * @return String
	 * @hibernate.property column="systemSetting" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getSystemSetting() {
		return this.systemSetting;
	}
	
	/**
	 * Set the systemSetting
	 * @spring.validator type="required"
	 */	
	public void setSystemSetting(String aValue) {
		this.systemSetting = aValue;
	}	

	/**
	 * 班次描述	 * @return String
	 * @hibernate.property column="systemDesc" type="java.lang.String" length="256" not-null="true" unique="false"
	 */
	public String getSystemDesc() {
		return this.systemDesc;
	}
	
	/**
	 * Set the systemDesc
	 * @spring.validator type="required"
	 */	
	public void setSystemDesc(String aValue) {
		this.systemDesc = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof DutySystem)) {
			return false;
		}
		DutySystem rhs = (DutySystem) object;
		return new EqualsBuilder()
				.append(this.systemId, rhs.systemId)
				.append(this.systemName, rhs.systemName)
				.append(this.systemSetting, rhs.systemSetting)
				.append(this.systemDesc, rhs.systemDesc)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.systemId) 
				.append(this.systemName) 
				.append(this.systemSetting) 
				.append(this.systemDesc) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("systemId", this.systemId) 
				.append("systemName", this.systemName) 
				.append("systemSetting", this.systemSetting) 
				.append("systemDesc", this.systemDesc) 
				.toString();
	}

	public Short getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Short isDefault) {
		this.isDefault = isDefault;
	}

}
