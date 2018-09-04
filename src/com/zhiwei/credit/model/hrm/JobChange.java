package com.zhiwei.credit.model.hrm;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * JobChange Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class JobChange extends com.zhiwei.core.model.BaseModel {

    protected Long changeId;
	protected Long profileId;
	protected String profileNo;
	protected String userName;
	protected Long orgJobId;
	protected String orgJobName;
	protected Long newJobId;
	protected String newJobName;
	protected Long orgStandardId;
	protected Long newStandardId;
	protected String orgStandardNo;
	protected String orgStandardName;
	protected Long orgDepId;
	protected String orgDepName;
	protected java.math.BigDecimal orgTotalMoney;
	protected String newStandardNo;
	protected String newStandardName;
	protected Long newDepId;
	protected String newDepName;
	protected java.math.BigDecimal newTotalMoney;
	protected String changeReason;
	protected String regName;
	protected java.util.Date regTime;
	protected String checkName;
	protected java.util.Date checkTime;
	protected String checkOpinion;
	protected Short status;
	protected String memo;


	/**
	 * Default Empty Constructor for class JobChange
	 */
	public JobChange () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class JobChange
	 */
	public JobChange (
		 Long in_changeId
        ) {
		this.setChangeId(in_changeId);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="changeId" type="java.lang.Long" generator-class="native"
	 */
	public Long getChangeId() {
		return this.changeId;
	}
	
	
	
	public Long getOrgStandardId() {
		return orgStandardId;
	}

	public void setOrgStandardId(Long orgStandardId) {
		this.orgStandardId = orgStandardId;
	}

	public Long getNewStandardId() {
		return newStandardId;
	}

	public void setNewStandardId(Long newStandardId) {
		this.newStandardId = newStandardId;
	}

	/**
	 * Set the changeId
	 */	
	public void setChangeId(Long aValue) {
		this.changeId = aValue;
	}	

	public java.util.Date getRegTime() {
		return regTime;
	}

	public void setRegTime(java.util.Date regTime) {
		this.regTime = regTime;
	}
	
	
	/**
	 * 	 * @return Long
	 * @hibernate.property column="profileId" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	public Long getProfileId() {
		return this.profileId;
	}
	
	/**
	 * Set the profileId
	 * @spring.validator type="required"
	 */	
	public void setProfileId(Long aValue) {
		this.profileId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="profileNo" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getProfileNo() {
		return this.profileNo;
	}
	
	/**
	 * Set the profileNo
	 * @spring.validator type="required"
	 */	
	public void setProfileNo(String aValue) {
		this.profileNo = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="userName" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getUserName() {
		return this.userName;
	}
	
	/**
	 * Set the userName
	 */	
	public void setUserName(String aValue) {
		this.userName = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="orgJobId" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	public Long getOrgJobId() {
		return this.orgJobId;
	}
	
	/**
	 * Set the orgJobId
	 * @spring.validator type="required"
	 */	
	public void setOrgJobId(Long aValue) {
		this.orgJobId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="orgJobName" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getOrgJobName() {
		return this.orgJobName;
	}
	
	/**
	 * Set the orgJobName
	 * @spring.validator type="required"
	 */	
	public void setOrgJobName(String aValue) {
		this.orgJobName = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="newJobId" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	public Long getNewJobId() {
		return this.newJobId;
	}
	
	/**
	 * Set the newJobId
	 * @spring.validator type="required"
	 */	
	public void setNewJobId(Long aValue) {
		this.newJobId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="newJobName" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getNewJobName() {
		return this.newJobName;
	}
	
	/**
	 * Set the newJobName
	 * @spring.validator type="required"
	 */	
	public void setNewJobName(String aValue) {
		this.newJobName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="orgStandardNo" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getOrgStandardNo() {
		return this.orgStandardNo;
	}
	
	/**
	 * Set the orgStandardNo
	 */	
	public void setOrgStandardNo(String aValue) {
		this.orgStandardNo = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="orgStandardName" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getOrgStandardName() {
		return this.orgStandardName;
	}
	
	/**
	 * Set the orgStandardName
	 */	
	public void setOrgStandardName(String aValue) {
		this.orgStandardName = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="orgDepId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getOrgDepId() {
		return this.orgDepId;
	}
	
	/**
	 * Set the orgDepId
	 */	
	public void setOrgDepId(Long aValue) {
		this.orgDepId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="orgDepName" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getOrgDepName() {
		return this.orgDepName;
	}
	
	/**
	 * Set the orgDepName
	 */	
	public void setOrgDepName(String aValue) {
		this.orgDepName = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="orgTotalMoney" type="java.math.BigDecimal" length="18" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getOrgTotalMoney() {
		return this.orgTotalMoney;
	}
	
	/**
	 * Set the orgTotalMoney
	 */	
	public void setOrgTotalMoney(java.math.BigDecimal aValue) {
		this.orgTotalMoney = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="newStandardNo" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getNewStandardNo() {
		return this.newStandardNo;
	}
	
	/**
	 * Set the newStandardNo
	 */	
	public void setNewStandardNo(String aValue) {
		this.newStandardNo = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="newStandardName" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getNewStandardName() {
		return this.newStandardName;
	}
	
	/**
	 * Set the newStandardName
	 */	
	public void setNewStandardName(String aValue) {
		this.newStandardName = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="newDepId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getNewDepId() {
		return this.newDepId;
	}
	
	/**
	 * Set the newDepId
	 */	
	public void setNewDepId(Long aValue) {
		this.newDepId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="newDepName" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getNewDepName() {
		return this.newDepName;
	}
	
	/**
	 * Set the newDepName
	 */	
	public void setNewDepName(String aValue) {
		this.newDepName = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="newTotalMoney" type="java.math.BigDecimal" length="18" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getNewTotalMoney() {
		return this.newTotalMoney;
	}
	
	/**
	 * Set the newTotalMoney
	 */	
	public void setNewTotalMoney(java.math.BigDecimal aValue) {
		this.newTotalMoney = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="changeReason" type="java.lang.String" length="1024" not-null="false" unique="false"
	 */
	public String getChangeReason() {
		return this.changeReason;
	}
	
	/**
	 * Set the changeReason
	 */	
	public void setChangeReason(String aValue) {
		this.changeReason = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="regName" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getRegName() {
		return this.regName;
	}
	
	/**
	 * Set the regName
	 */	
	public void setRegName(String aValue) {
		this.regName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="checkName" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getCheckName() {
		return this.checkName;
	}
	
	/**
	 * Set the checkName
	 */	
	public void setCheckName(String aValue) {
		this.checkName = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="checkTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getCheckTime() {
		return this.checkTime;
	}
	
	/**
	 * Set the checkTime
	 */	
	public void setCheckTime(java.util.Date aValue) {
		this.checkTime = aValue;
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
	 * 状态
            
            -1=草稿
            0=提交审批
            1=通过审批
            2=未通过审批
            	 * @return Short
	 * @hibernate.property column="status" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getStatus() {
		return this.status;
	}
	
	/**
	 * Set the status
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
		if (!(object instanceof JobChange)) {
			return false;
		}
		JobChange rhs = (JobChange) object;
		return new EqualsBuilder()
				.append(this.changeId, rhs.changeId)
				.append(this.profileId, rhs.profileId)
				.append(this.profileNo, rhs.profileNo)
				.append(this.userName, rhs.userName)
				.append(this.orgJobId, rhs.orgJobId)
				.append(this.orgStandardId, rhs.orgStandardId)
				.append(this.newStandardId, rhs.newStandardId)
				.append(this.orgJobName, rhs.orgJobName)
				.append(this.newJobId, rhs.newJobId)
				.append(this.newJobName, rhs.newJobName)
				.append(this.orgStandardNo, rhs.orgStandardNo)
				.append(this.orgStandardName, rhs.orgStandardName)
				.append(this.orgDepId, rhs.orgDepId)
				.append(this.orgDepName, rhs.orgDepName)
				.append(this.orgTotalMoney, rhs.orgTotalMoney)
				.append(this.newStandardNo, rhs.newStandardNo)
				.append(this.newStandardName, rhs.newStandardName)
				.append(this.newDepId, rhs.newDepId)
				.append(this.newDepName, rhs.newDepName)
				.append(this.newTotalMoney, rhs.newTotalMoney)
				.append(this.changeReason, rhs.changeReason)
				.append(this.regName, rhs.regName)
				.append(this.regTime, rhs.regTime)
				.append(this.checkName, rhs.checkName)
				.append(this.checkTime, rhs.checkTime)
				.append(this.checkOpinion, rhs.checkOpinion)
				.append(this.status, rhs.status)
				.append(this.memo, rhs.memo)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.changeId) 
				.append(this.profileId) 
				.append(this.profileNo) 
				.append(this.userName) 
				.append(this.orgJobId) 
				.append(this.orgJobName) 
				.append(this.newJobId) 
				.append(this.newJobName) 
				.append(this.orgStandardNo) 
				.append(this.orgStandardName) 
				.append(this.orgDepId) 
				.append(this.orgStandardId)
				.append(this.newStandardId)
				.append(this.orgDepName) 
				.append(this.orgTotalMoney) 
				.append(this.newStandardNo) 
				.append(this.newStandardName) 
				.append(this.newDepId) 
				.append(this.newDepName) 
				.append(this.newTotalMoney) 
				.append(this.changeReason) 
				.append(this.regName) 
				.append(this.regTime) 
				.append(this.checkName) 
				.append(this.checkTime) 
				.append(this.checkOpinion) 
				.append(this.status) 
				.append(this.memo) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("changeId", this.changeId) 
				.append("profileId", this.profileId) 
				.append("profileNo", this.profileNo) 
				.append("userName", this.userName) 
				.append("orgJobId", this.orgJobId) 
				.append("orgJobName", this.orgJobName) 
				.append("newJobId", this.newJobId) 
				.append("newJobName", this.newJobName) 
				.append("orgStandardId",this.orgStandardId)
				.append("newStandardId",this.newStandardId)
				.append("orgStandardNo", this.orgStandardNo) 
				.append("orgStandardName", this.orgStandardName) 
				.append("orgDepId", this.orgDepId) 
				.append("orgDepName", this.orgDepName) 
				.append("orgTotalMoney", this.orgTotalMoney) 
				.append("newStandardNo", this.newStandardNo) 
				.append("newStandardName", this.newStandardName) 
				.append("newDepId", this.newDepId) 
				.append("newDepName", this.newDepName) 
				.append("newTotalMoney", this.newTotalMoney) 
				.append("changeReason", this.changeReason) 
				.append("regName", this.regName) 
				.append("regTime", this.regTime)
				.append("checkName", this.checkName) 
				.append("checkTime", this.checkTime) 
				.append("checkOpinion", this.checkOpinion) 
				.append("status", this.status) 
				.append("memo", this.memo) 
				.toString();
	}



}
