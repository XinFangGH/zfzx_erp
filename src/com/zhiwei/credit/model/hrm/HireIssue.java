package com.zhiwei.credit.model.hrm;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * HireIssue Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * ������������
 */
public class HireIssue extends com.zhiwei.core.model.BaseModel {

	public static Short PASS_CHECK=1;//通过审核
	public static Short NOTPASS_CHECK=2;//不通过审核
	public static Short NOTYETPASS_CHECK=0;//未审核
    protected Long hireId;
	protected String title;
	protected java.util.Date startDate;
	protected java.util.Date endDate;
	protected Integer hireCount;
	protected String jobName;
	protected String jobCondition;
	protected String regFullname;
	protected java.util.Date regDate;
	protected String modifyFullname;
	protected java.util.Date modifyDate;
	protected String checkFullname;
	protected String checkOpinion;
	protected java.util.Date checkDate;
	protected Short status;
	protected String memo;


	/**
	 * Default Empty Constructor for class HireIssue
	 */
	public HireIssue () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class HireIssue
	 */
	public HireIssue (
		 Long in_hireId
        ) {
		this.setHireId(in_hireId);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="hireId" type="java.lang.Long" generator-class="native"
	 */
	public Long getHireId() {
		return this.hireId;
	}
	
	/**
	 * Set the hireId
	 */	
	public void setHireId(Long aValue) {
		this.hireId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="title" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getTitle() {
		return this.title;
	}
	
	/**
	 * Set the title
	 * @spring.validator type="required"
	 */	
	public void setTitle(String aValue) {
		this.title = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="startDate" type="java.util.Date" length="19" not-null="true" unique="false"
	 */
	public java.util.Date getStartDate() {
		return this.startDate;
	}
	
	/**
	 * Set the startDate
	 * @spring.validator type="required"
	 */	
	public void setStartDate(java.util.Date aValue) {
		this.startDate = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="endDate" type="java.util.Date" length="19" not-null="true" unique="false"
	 */
	public java.util.Date getEndDate() {
		return this.endDate;
	}
	
	/**
	 * Set the endDate
	 * @spring.validator type="required"
	 */	
	public void setEndDate(java.util.Date aValue) {
		this.endDate = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="hireCount" type="java.lang.Integer" length="10" not-null="true" unique="false"
	 */
	public Integer getHireCount() {
		return this.hireCount;
	}
	
	/**
	 * Set the hireCount
	 * @spring.validator type="required"
	 */	
	public void setHireCount(Integer aValue) {
		this.hireCount = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="jobName" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getJobName() {
		return this.jobName;
	}
	
	/**
	 * Set the jobName
	 * @spring.validator type="required"
	 */	
	public void setJobName(String aValue) {
		this.jobName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="jobCondition" type="java.lang.String" length="1024" not-null="false" unique="false"
	 */
	public String getJobCondition() {
		return this.jobCondition;
	}
	
	/**
	 * Set the jobCondition
	 */	
	public void setJobCondition(String aValue) {
		this.jobCondition = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="regFullname" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getRegFullname() {
		return this.regFullname;
	}
	
	/**
	 * Set the regFullname
	 * @spring.validator type="required"
	 */	
	public void setRegFullname(String aValue) {
		this.regFullname = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="regDate" type="java.util.Date" length="19" not-null="true" unique="false"
	 */
	public java.util.Date getRegDate() {
		return this.regDate;
	}
	
	/**
	 * Set the regDate
	 * @spring.validator type="required"
	 */	
	public void setRegDate(java.util.Date aValue) {
		this.regDate = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="modifyFullname" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getModifyFullname() {
		return this.modifyFullname;
	}
	
	/**
	 * Set the modifyFullname
	 */	
	public void setModifyFullname(String aValue) {
		this.modifyFullname = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="modifyDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getModifyDate() {
		return this.modifyDate;
	}
	
	/**
	 * Set the modifyDate
	 */	
	public void setModifyDate(java.util.Date aValue) {
		this.modifyDate = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="checkFullname" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getCheckFullname() {
		return this.checkFullname;
	}
	
	/**
	 * Set the checkFullname
	 */	
	public void setCheckFullname(String aValue) {
		this.checkFullname = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="checkOpinion" type="java.lang.String" length="512" not-null="false" unique="false"
	 */
	public String getCheckOpinion() {
		return this.checkOpinion;
	}
	
	/**
	 * Set the checkOpinion
	 */	
	public void setCheckOpinion(String aValue) {
		this.checkOpinion = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="checkDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getCheckDate() {
		return this.checkDate;
	}
	
	/**
	 * Set the checkDate
	 */	
	public void setCheckDate(java.util.Date aValue) {
		this.checkDate = aValue;
	}	

	/**
	 * 1=通过审核
            0=未审核
            2=审核不通过	 * @return Short
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
	 * 	 * @return String
	 * @hibernate.property column="memo" type="java.lang.String" length="1024" not-null="false" unique="false"
	 */
	public String getMemo() {
		return this.memo;
	}
	
	/**
	 * Set the memo
	 */	
	public void setMemo(String aValue) {
		this.memo = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof HireIssue)) {
			return false;
		}
		HireIssue rhs = (HireIssue) object;
		return new EqualsBuilder()
				.append(this.hireId, rhs.hireId)
				.append(this.title, rhs.title)
				.append(this.startDate, rhs.startDate)
				.append(this.endDate, rhs.endDate)
				.append(this.hireCount, rhs.hireCount)
				.append(this.jobName, rhs.jobName)
				.append(this.jobCondition, rhs.jobCondition)
				.append(this.regFullname, rhs.regFullname)
				.append(this.regDate, rhs.regDate)
				.append(this.modifyFullname, rhs.modifyFullname)
				.append(this.modifyDate, rhs.modifyDate)
				.append(this.checkFullname, rhs.checkFullname)
				.append(this.checkOpinion, rhs.checkOpinion)
				.append(this.checkDate, rhs.checkDate)
				.append(this.status, rhs.status)
				.append(this.memo, rhs.memo)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.hireId) 
				.append(this.title) 
				.append(this.startDate) 
				.append(this.endDate) 
				.append(this.hireCount) 
				.append(this.jobName) 
				.append(this.jobCondition) 
				.append(this.regFullname) 
				.append(this.regDate) 
				.append(this.modifyFullname) 
				.append(this.modifyDate) 
				.append(this.checkFullname) 
				.append(this.checkOpinion) 
				.append(this.checkDate) 
				.append(this.status) 
				.append(this.memo) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("hireId", this.hireId) 
				.append("title", this.title) 
				.append("startDate", this.startDate) 
				.append("endDate", this.endDate) 
				.append("hireCount", this.hireCount) 
				.append("jobName", this.jobName) 
				.append("jobCondition", this.jobCondition) 
				.append("regFullname", this.regFullname) 
				.append("regDate", this.regDate) 
				.append("modifyFullname", this.modifyFullname) 
				.append("modifyDate", this.modifyDate) 
				.append("checkFullname", this.checkFullname) 
				.append("checkOpinion", this.checkOpinion) 
				.append("checkDate", this.checkDate) 
				.append("status", this.status) 
				.append("memo", this.memo) 
				.toString();
	}



}
