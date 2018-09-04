package com.zhiwei.credit.model.hrm;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * SalaryPayoff Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class SalaryPayoff extends com.zhiwei.core.model.BaseModel {

	public static short CHECK_FLAG_NONE = (short)0;//未审核
	public static short CHECK_FLAG_PASS = (short)1;//审核通过
	public static short CHECK_FLAG_NOT_PASS = (short)2;//审核未通过
	
    protected Long recordId;
	protected String fullname;
	protected Long userId;
	protected String profileNo;
	protected String idNo;
	protected java.math.BigDecimal standAmount;
	protected java.math.BigDecimal encourageAmount;
	protected java.math.BigDecimal deductAmount;
	protected java.math.BigDecimal achieveAmount;
	protected String encourageDesc;
	protected String deductDesc;
	protected String memo;
	protected java.math.BigDecimal acutalAmount;
	protected java.util.Date regTime;
	protected String register;
	protected String checkName;
	protected java.util.Date checkTime;
	protected Short checkStatus;
	protected java.util.Date startTime;
	protected java.util.Date endTime;
	protected String checkOpinion;
	protected Long standardId;


	/**
	 * Default Empty Constructor for class SalaryPayoff
	 */
	public SalaryPayoff () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class SalaryPayoff
	 */
	public SalaryPayoff (
		 Long in_recordId
        ) {
		this.setRecordId(in_recordId);
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
	 * 员工姓名	 * @return String
	 * @hibernate.property column="fullname" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getFullname() {
		return this.fullname;
	}
	
	/**
	 * Set the fullname
	 * @spring.validator type="required"
	 */	
	public void setFullname(String aValue) {
		this.fullname = aValue;
	}	

	/**
	 * 所属员工	 * @return Long
	 * @hibernate.property column="userId" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	public Long getUserId() {
		return this.userId;
	}
	
	/**
	 * Set the userId
	 * @spring.validator type="required"
	 */	
	public void setUserId(Long aValue) {
		this.userId = aValue;
	}	

	/**
	 * 档案编号	 * @return String
	 * @hibernate.property column="profileNo" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getProfileNo() {
		return this.profileNo;
	}
	
	/**
	 * Set the profileNo
	 */	
	public void setProfileNo(String aValue) {
		this.profileNo = aValue;
	}	

	/**
	 * 身份证号	 * @return String
	 * @hibernate.property column="idNo" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getIdNo() {
		return this.idNo;
	}
	
	/**
	 * Set the idNo
	 */	
	public void setIdNo(String aValue) {
		this.idNo = aValue;
	}	

	/**
	 * 薪标金额	 * @return java.math.BigDecimal
	 * @hibernate.property column="standAmount" type="java.math.BigDecimal" length="18" not-null="true" unique="false"
	 */
	public java.math.BigDecimal getStandAmount() {
		return this.standAmount;
	}
	
	/**
	 * Set the standAmount
	 * @spring.validator type="required"
	 */	
	public void setStandAmount(java.math.BigDecimal aValue) {
		this.standAmount = aValue;
	}	

	/**
	 * 奖励金额	 * @return java.math.BigDecimal
	 * @hibernate.property column="encourageAmount" type="java.math.BigDecimal" length="18" not-null="true" unique="false"
	 */
	public java.math.BigDecimal getEncourageAmount() {
		return this.encourageAmount;
	}
	
	/**
	 * Set the encourageAmount
	 * @spring.validator type="required"
	 */	
	public void setEncourageAmount(java.math.BigDecimal aValue) {
		this.encourageAmount = aValue;
	}	

	/**
	 * 扣除工资	 * @return java.math.BigDecimal
	 * @hibernate.property column="deductAmount" type="java.math.BigDecimal" length="18" not-null="true" unique="false"
	 */
	public java.math.BigDecimal getDeductAmount() {
		return this.deductAmount;
	}
	
	/**
	 * Set the deductAmount
	 * @spring.validator type="required"
	 */	
	public void setDeductAmount(java.math.BigDecimal aValue) {
		this.deductAmount = aValue;
	}	

	/**
	 * 效绩工资	 * @return java.math.BigDecimal
	 * @hibernate.property column="achieveAmount" type="java.math.BigDecimal" length="18" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getAchieveAmount() {
		return this.achieveAmount;
	}
	
	/**
	 * Set the achieveAmount
	 */	
	public void setAchieveAmount(java.math.BigDecimal aValue) {
		this.achieveAmount = aValue;
	}	

	/**
	 * 奖励描述	 * @return String
	 * @hibernate.property column="encourageDesc" type="java.lang.String" length="512" not-null="false" unique="false"
	 */
	public String getEncourageDesc() {
		return this.encourageDesc;
	}
	
	/**
	 * Set the encourageDesc
	 */	
	public void setEncourageDesc(String aValue) {
		this.encourageDesc = aValue;
	}	

	/**
	 * 扣除描述	 * @return String
	 * @hibernate.property column="deductDesc" type="java.lang.String" length="512" not-null="false" unique="false"
	 */
	public String getDeductDesc() {
		return this.deductDesc;
	}
	
	/**
	 * Set the deductDesc
	 */	
	public void setDeductDesc(String aValue) {
		this.deductDesc = aValue;
	}	

	/**
	 * 备注描述	 * @return String
	 * @hibernate.property column="memo" type="java.lang.String" length="512" not-null="false" unique="false"
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
	 * 实发金额	 * @return java.math.BigDecimal
	 * @hibernate.property column="acutalAmount" type="java.math.BigDecimal" length="18" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getAcutalAmount() {
		return this.acutalAmount;
	}
	
	/**
	 * Set the acutalAmount
	 */	
	public void setAcutalAmount(java.math.BigDecimal aValue) {
		this.acutalAmount = aValue;
	}	

	/**
	 * 登记时间	 * @return java.util.Date
	 * @hibernate.property column="regTime" type="java.util.Date" length="19" not-null="true" unique="false"
	 */
	public java.util.Date getRegTime() {
		return this.regTime;
	}
	
	/**
	 * Set the regTime
	 * @spring.validator type="required"
	 */	
	public void setRegTime(java.util.Date aValue) {
		this.regTime = aValue;
	}	

	/**
	 * 登记人	 * @return String
	 * @hibernate.property column="register" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getRegister() {
		return this.register;
	}
	
	/**
	 * Set the register
	 */	
	public void setRegister(String aValue) {
		this.register = aValue;
	}	

	/**
	 * 审批人	 * @return String
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
	 * 审批时间	 * @return java.util.Date
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
	 * 审批状态
            0=草稿
            1=通过审批
            2=未通过审批
            	 * @return Short
	 * @hibernate.property column="checkStatus" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getCheckStatus() {
		return this.checkStatus;
	}
	
	/**
	 * Set the checkStatus
	 */	
	public void setCheckStatus(Short aValue) {
		this.checkStatus = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
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

	public String getCheckOpinion() {
		return checkOpinion;
	}

	public void setCheckOpinion(String checkOpinion) {
		this.checkOpinion = checkOpinion;
	}

	public Long getStandardId() {
		return standardId;
	}

	public void setStandardId(Long standardId) {
		this.standardId = standardId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SalaryPayoff)) {
			return false;
		}
		SalaryPayoff rhs = (SalaryPayoff) object;
		return new EqualsBuilder()
				.append(this.recordId, rhs.recordId)
				.append(this.fullname, rhs.fullname)
				.append(this.userId, rhs.userId)
				.append(this.profileNo, rhs.profileNo)
				.append(this.idNo, rhs.idNo)
				.append(this.standAmount, rhs.standAmount)
				.append(this.encourageAmount, rhs.encourageAmount)
				.append(this.deductAmount, rhs.deductAmount)
				.append(this.achieveAmount, rhs.achieveAmount)
				.append(this.encourageDesc, rhs.encourageDesc)
				.append(this.deductDesc, rhs.deductDesc)
				.append(this.memo, rhs.memo)
				.append(this.acutalAmount, rhs.acutalAmount)
				.append(this.regTime, rhs.regTime)
				.append(this.register, rhs.register)
				.append(this.checkName, rhs.checkName)
				.append(this.checkTime, rhs.checkTime)
				.append(this.checkStatus, rhs.checkStatus)
				.append(this.startTime, rhs.startTime)
				.append(this.endTime, rhs.endTime)
				.append(this.checkOpinion, rhs.checkOpinion)
				.append(this.standardId, rhs.standardId)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.recordId) 
				.append(this.fullname) 
				.append(this.userId) 
				.append(this.profileNo) 
				.append(this.idNo) 
				.append(this.standAmount) 
				.append(this.encourageAmount) 
				.append(this.deductAmount) 
				.append(this.achieveAmount) 
				.append(this.encourageDesc) 
				.append(this.deductDesc) 
				.append(this.memo) 
				.append(this.acutalAmount) 
				.append(this.regTime) 
				.append(this.register) 
				.append(this.checkName) 
				.append(this.checkTime) 
				.append(this.checkStatus) 
				.append(this.startTime) 
				.append(this.endTime) 
				.append(this.checkOpinion)
				.append(this.standardId)
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("recordId", this.recordId) 
				.append("fullname", this.fullname) 
				.append("userId", this.userId) 
				.append("profileNo", this.profileNo) 
				.append("idNo", this.idNo) 
				.append("standAmount", this.standAmount) 
				.append("encourageAmount", this.encourageAmount) 
				.append("deductAmount", this.deductAmount) 
				.append("achieveAmount", this.achieveAmount) 
				.append("encourageDesc", this.encourageDesc) 
				.append("deductDesc", this.deductDesc) 
				.append("memo", this.memo) 
				.append("acutalAmount", this.acutalAmount) 
				.append("regTime", this.regTime) 
				.append("register", this.register) 
				.append("checkName", this.checkName) 
				.append("checkTime", this.checkTime) 
				.append("checkStatus", this.checkStatus) 
				.append("startTime", this.startTime) 
				.append("endTime", this.endTime) 
				.append("checkOpinion",this.checkOpinion)
				.append("standardId",this.standardId)
				.toString();
	}

}
