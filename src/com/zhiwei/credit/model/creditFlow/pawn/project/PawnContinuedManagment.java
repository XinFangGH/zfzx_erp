package com.zhiwei.credit.model.creditFlow.pawn.project;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * 
 * @author 
 *
 */
/**
 * PawnContinuedManagment Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class PawnContinuedManagment extends com.zhiwei.core.model.BaseModel {

    protected Long continueId;
	protected String continuePawnNum;
	protected Integer payintentPeriod;
	protected java.util.Date startDate;
	protected java.util.Date intentDate;
	protected String payaccrualType;
	protected java.math.BigDecimal accrual;
	protected java.math.BigDecimal monthFeeRate;
	protected Long managerId;
	protected String managerName;
	protected java.util.Date manageDate;
	protected String remarks;
	protected java.util.Date createDate;
	protected Long projectId;
	protected String businessType;
	protected Integer dayOfEveryPeriod;
	protected Integer payintentPerioDate;
	protected String isStartDatePay;
	protected Integer isPreposePayAccrual;
	protected Integer isInterestByOneTime;

	/**
	 * Default Empty Constructor for class PawnContinuedManagment
	 */
	public PawnContinuedManagment () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class PawnContinuedManagment
	 */
	public PawnContinuedManagment (
		 Long in_continueId
        ) {
		this.setContinueId(in_continueId);
    }

    

	public Integer getDayOfEveryPeriod() {
		return dayOfEveryPeriod;
	}

	public void setDayOfEveryPeriod(Integer dayOfEveryPeriod) {
		this.dayOfEveryPeriod = dayOfEveryPeriod;
	}

	public Integer getPayintentPerioDate() {
		return payintentPerioDate;
	}

	public void setPayintentPerioDate(Integer payintentPerioDate) {
		this.payintentPerioDate = payintentPerioDate;
	}

	public String getIsStartDatePay() {
		return isStartDatePay;
	}

	public void setIsStartDatePay(String isStartDatePay) {
		this.isStartDatePay = isStartDatePay;
	}

	public Integer getIsPreposePayAccrual() {
		return isPreposePayAccrual;
	}

	public void setIsPreposePayAccrual(Integer isPreposePayAccrual) {
		this.isPreposePayAccrual = isPreposePayAccrual;
	}

	public Integer getIsInterestByOneTime() {
		return isInterestByOneTime;
	}

	public void setIsInterestByOneTime(Integer isInterestByOneTime) {
		this.isInterestByOneTime = isInterestByOneTime;
	}

	/**
	 * 	 * @return Long
     * @hibernate.id column="continueId" type="java.lang.Long" generator-class="native"
	 */
	public Long getContinueId() {
		return this.continueId;
	}
	
	/**
	 * Set the continueId
	 */	
	public void setContinueId(Long aValue) {
		this.continueId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="continuePawnNum" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getContinuePawnNum() {
		return this.continuePawnNum;
	}
	
	/**
	 * Set the continuePawnNum
	 */	
	public void setContinuePawnNum(String aValue) {
		this.continuePawnNum = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="payintentPeriod" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getPayintentPeriod() {
		return this.payintentPeriod;
	}
	
	/**
	 * Set the payintentPeriod
	 */	
	public void setPayintentPeriod(Integer aValue) {
		this.payintentPeriod = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="startDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getStartDate() {
		return this.startDate;
	}
	
	/**
	 * Set the startDate
	 */	
	public void setStartDate(java.util.Date aValue) {
		this.startDate = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="intentDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getIntentDate() {
		return this.intentDate;
	}
	
	/**
	 * Set the intentDate
	 */	
	public void setIntentDate(java.util.Date aValue) {
		this.intentDate = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="payaccrualType" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	public String getPayaccrualType() {
		return this.payaccrualType;
	}
	
	/**
	 * Set the payaccrualType
	 */	
	public void setPayaccrualType(String aValue) {
		this.payaccrualType = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="accrual" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getAccrual() {
		return this.accrual;
	}
	
	/**
	 * Set the accrual
	 */	
	public void setAccrual(java.math.BigDecimal aValue) {
		this.accrual = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="monthFeeRate" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getMonthFeeRate() {
		return this.monthFeeRate;
	}
	
	/**
	 * Set the monthFeeRate
	 */	
	public void setMonthFeeRate(java.math.BigDecimal aValue) {
		this.monthFeeRate = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="managerId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getManagerId() {
		return this.managerId;
	}
	
	/**
	 * Set the managerId
	 */	
	public void setManagerId(Long aValue) {
		this.managerId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="managerName" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getManagerName() {
		return this.managerName;
	}
	
	/**
	 * Set the managerName
	 */	
	public void setManagerName(String aValue) {
		this.managerName = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="manageDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getManageDate() {
		return this.manageDate;
	}
	
	/**
	 * Set the manageDate
	 */	
	public void setManageDate(java.util.Date aValue) {
		this.manageDate = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="remarks" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getRemarks() {
		return this.remarks;
	}
	
	/**
	 * Set the remarks
	 */	
	public void setRemarks(String aValue) {
		this.remarks = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="createDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getCreateDate() {
		return this.createDate;
	}
	
	/**
	 * Set the createDate
	 */	
	public void setCreateDate(java.util.Date aValue) {
		this.createDate = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="projectId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getProjectId() {
		return this.projectId;
	}
	
	/**
	 * Set the projectId
	 */	
	public void setProjectId(Long aValue) {
		this.projectId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="businessType" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getBusinessType() {
		return this.businessType;
	}
	
	/**
	 * Set the businessType
	 */	
	public void setBusinessType(String aValue) {
		this.businessType = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof PawnContinuedManagment)) {
			return false;
		}
		PawnContinuedManagment rhs = (PawnContinuedManagment) object;
		return new EqualsBuilder()
				.append(this.continueId, rhs.continueId)
				.append(this.continuePawnNum, rhs.continuePawnNum)
				.append(this.payintentPeriod, rhs.payintentPeriod)
				.append(this.startDate, rhs.startDate)
				.append(this.intentDate, rhs.intentDate)
				.append(this.payaccrualType, rhs.payaccrualType)
				.append(this.accrual, rhs.accrual)
				.append(this.monthFeeRate, rhs.monthFeeRate)
				.append(this.managerId, rhs.managerId)
				.append(this.managerName, rhs.managerName)
				.append(this.manageDate, rhs.manageDate)
				.append(this.remarks, rhs.remarks)
				.append(this.createDate, rhs.createDate)
				.append(this.projectId, rhs.projectId)
				.append(this.businessType, rhs.businessType)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.continueId) 
				.append(this.continuePawnNum) 
				.append(this.payintentPeriod) 
				.append(this.startDate) 
				.append(this.intentDate) 
				.append(this.payaccrualType) 
				.append(this.accrual) 
				.append(this.monthFeeRate) 
				.append(this.managerId) 
				.append(this.managerName) 
				.append(this.manageDate) 
				.append(this.remarks) 
				.append(this.createDate) 
				.append(this.projectId) 
				.append(this.businessType) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("continueId", this.continueId) 
				.append("continuePawnNum", this.continuePawnNum) 
				.append("payintentPeriod", this.payintentPeriod) 
				.append("startDate", this.startDate) 
				.append("intentDate", this.intentDate) 
				.append("payaccrualType", this.payaccrualType) 
				.append("accrual", this.accrual) 
				.append("monthFeeRate", this.monthFeeRate) 
				.append("managerId", this.managerId) 
				.append("managerName", this.managerName) 
				.append("manageDate", this.manageDate) 
				.append("remarks", this.remarks) 
				.append("createDate", this.createDate) 
				.append("projectId", this.projectId) 
				.append("businessType", this.businessType) 
				.toString();
	}



}
