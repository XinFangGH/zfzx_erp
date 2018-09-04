package com.zhiwei.credit.model.personal;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * HolidayRecord Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 
 */
public class HolidayRecord extends com.zhiwei.core.model.BaseModel {

    protected Long recordId;
	protected java.util.Date startTime;
	protected java.util.Date endTime;
	protected String descp;
	protected Long userId;
	protected String fullname;
	protected Short isAll;

	//全公司假期
	public final static Short IS_ALL_HOLIDAY=1;
	//个人假期
	public final static Short IS_PERSONAL_HOLIDAY=0;
	/**
	 * Default Empty Constructor for class HolidayRecord
	 */
	public HolidayRecord () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class HolidayRecord
	 */
	public HolidayRecord (
		 Long in_recordId
        ) {
		this.setRecordId(in_recordId);
    }

    

	public String getDescp() {
		return descp;
	}

	public void setDescp(String descp) {
		this.descp = descp;
	}

	
	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	/**
	 * 	 * @return Long
     * @hibernate.id column="recordId" type="java.lang.Long" generator-class="native"
	 */
	public Long getRecordId() {
		return this.recordId;
	}
	
	/**
	 * Set the recordId
	 */	
	public void setRecordId(Long aValue) {
		this.recordId = aValue;
	}	

	/**
	 * 开始日期	 * @return java.util.Date
	 * @hibernate.property column="startTime" type="java.util.Date" length="19" not-null="true" unique="false"
	 */
	public java.util.Date getStartTime() {
		return this.startTime;
	}
	
	/**
	 * Set the startTime
	 * @spring.validator type="required"
	 */	
	public void setStartTime(java.util.Date aValue) {
		this.startTime = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="endTime" type="java.util.Date" length="19" not-null="true" unique="false"
	 */
	public java.util.Date getEndTime() {
		return this.endTime;
	}
	
	/**
	 * Set the endTime
	 * @spring.validator type="required"
	 */	
	public void setEndTime(java.util.Date aValue) {
		this.endTime = aValue;
	}	

	/**
	 * 所属用户
            若为某员工的假期，则为员工自己的id	 * @return Long
	 * @hibernate.property column="userId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getUserId() {
		return this.userId;
	}
	
	/**
	 * Set the userId
	 */	
	public void setUserId(Long aValue) {
		this.userId = aValue;
	}	

	/**
	 * 是否为全公司假期
            1=是
            0=否	 * @return Short
	 * @hibernate.property column="isAll" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getIsAll() {
		return this.isAll;
	}
	
	/**
	 * Set the isAll
	 * @spring.validator type="required"
	 */	
	public void setIsAll(Short aValue) {
		this.isAll = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof HolidayRecord)) {
			return false;
		}
		HolidayRecord rhs = (HolidayRecord) object;
		return new EqualsBuilder()
				.append(this.recordId, rhs.recordId)
				.append(this.startTime, rhs.startTime)
				.append(this.endTime, rhs.endTime)
				.append(this.userId, rhs.userId)
				.append(this.isAll, rhs.isAll)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.recordId) 
				.append(this.startTime) 
				.append(this.endTime) 
				.append(this.userId) 
				.append(this.isAll) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("recordId", this.recordId) 
				.append("startTime", this.startTime) 
				.append("endTime", this.endTime) 
				.append("userId", this.userId) 
				.append("isAll", this.isAll) 
				.toString();
	}

}
