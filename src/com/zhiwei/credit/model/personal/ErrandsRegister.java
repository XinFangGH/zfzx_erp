package com.zhiwei.credit.model.personal;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * ErrandsRegister Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 
 */
public class ErrandsRegister extends com.zhiwei.core.model.BaseModel {

    protected Long dateId;
	protected Long approvalId;
	protected String descp;
	protected java.util.Date startTime;
	protected java.util.Date endTime;
	protected Short status;
	protected String approvalOption;
	protected String approvalName;
	protected Short flag;
	protected Long runId;
	protected com.zhiwei.credit.model.system.AppUser appUser;
	
	/**
	 * 未审批
	 */
	public static final Short STATUS_UNCHECKED=0;
	/**
	 * 审批通过
	 */
	public static final Short STATUS_APPROVAL=1;
	/**
	 * 审批未通过
	 */
	public static final Short STATUS_UNAPPROVAL=2;
	
	//加班
	public static final Short FLAG_OVERTIME=0;
	//请假
	public static final Short FLAG_LEAVE=1;
	//外出 
	public static final Short FLAG_OUT=2; 
	
	/**
	 * Default Empty Constructor for class ErrandsRegister
	 */
	public ErrandsRegister () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ErrandsRegister
	 */
	public ErrandsRegister (
		 Long in_dateId
        ) {
		this.setDateId(in_dateId);
    }

	
	public com.zhiwei.credit.model.system.AppUser getAppUser () {
		return appUser;
	}	
	
	public void setAppUser (com.zhiwei.credit.model.system.AppUser in_appUser) {
		this.appUser = in_appUser;
	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="dateId" type="java.lang.Long" generator-class="native"
	 */
	public Long getDateId() {
		return this.dateId;
	}
	
	/**
	 * Set the dateId
	 */	
	public void setDateId(Long aValue) {
		this.dateId = aValue;
	}	

	/**
	 * 外出/加班人员	 * @return Long
	 * @hibernate.property column="userId" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	public Long getUserId() {
		return this.getAppUser()==null?null:this.getAppUser().getUserId();
	}
	
	/**
	 * Set the userId
	 * @spring.validator type="required"
	 */	
	public void setUserId(Long aValue) {
		if (aValue==null) {
	    	appUser = null;
	    } else if (appUser == null) {
	        appUser = new com.zhiwei.credit.model.system.AppUser(aValue);
	        appUser.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			appUser.setUserId(aValue);
	    }
	}	

	/**
	 * 描述	 * @return String
	 * @hibernate.property column="descp" type="java.lang.String" length="500" not-null="true" unique="false"
	 */
	public String getDescp() {
		return this.descp;
	}
	
	/**
	 * Set the descp
	 * @spring.validator type="required"
	 */	
	public void setDescp(String aValue) {
		this.descp = aValue;
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
	 * 结束日期	 * @return java.util.Date
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
	 * 审批人ID	 * @return Long
	 */
	public Long getApprovalId() {
		return this.approvalId;
	}
	
	/**
	 * Set the approvalId
	 */	
	public void setApprovalId(Long aValue) {
	    this.approvalId=aValue;
	}	

	/**
	 * 审批状态	 * @return Short
	 * @hibernate.property column="status" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getStatus() {
		return this.status;
	}
	
	/**
	 * Set the status
	 * @spring.validator type="required"
	 */	
	public void setStatus(Short aValue) {
		this.status = aValue;
	}	

	/**
	 * 审批意见	 * @return String
	 * @hibernate.property column="approvalOption" type="java.lang.String" length="500" not-null="false" unique="false"
	 */
	public String getApprovalOption() {
		return this.approvalOption;
	}
	
	/**
	 * Set the approvalOption
	 */	
	public void setApprovalOption(String aValue) {
		this.approvalOption = aValue;
	}	

	/**
	 * 审批人	 * @return String
	 * @hibernate.property column="approvalName" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getApprovalName() {
		return this.approvalName;
	}
	
	/**
	 * Set the approvalName
	 * @spring.validator type="required"
	 */	
	public void setApprovalName(String aValue) {
		this.approvalName = aValue;
	}	

	/**
	 * 标识
            1=加班
            2=外出	 * @return Short
	 * @hibernate.property column="flag" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getFlag() {
		return this.flag;
	}
	
	/**
	 * Set the flag
	 */	
	public void setFlag(Short aValue) {
		this.flag = aValue;
	}	

	public Long getRunId() {
		return runId;
	}

	public void setRunId(Long runId) {
		this.runId = runId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ErrandsRegister)) {
			return false;
		}
		ErrandsRegister rhs = (ErrandsRegister) object;
		return new EqualsBuilder()
				.append(this.dateId, rhs.dateId)
				.append(this.approvalId, rhs.approvalId)
				.append(this.descp, rhs.descp)
				.append(this.startTime, rhs.startTime)
				.append(this.endTime, rhs.endTime)
						.append(this.status, rhs.status)
				.append(this.approvalOption, rhs.approvalOption)
				.append(this.approvalName, rhs.approvalName)
				.append(this.flag, rhs.flag)
				.append(this.runId, rhs.runId)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.dateId) 
				.append(this.approvalId) 
				.append(this.descp) 
				.append(this.startTime) 
				.append(this.endTime) 
						.append(this.status) 
				.append(this.approvalOption) 
				.append(this.approvalName) 
				.append(this.flag) 
				.append(this.runId)
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("dateId", this.dateId) 
				.append("userId", this.approvalId) 
				.append("descp", this.descp) 
				.append("startTime", this.startTime) 
				.append("endTime", this.endTime) 
						.append("status", this.status) 
				.append("approvalOption", this.approvalOption) 
				.append("approvalName", this.approvalName) 
				.append("flag", this.flag) 
				.append("runId",this.runId)
				.toString();
	}



}
