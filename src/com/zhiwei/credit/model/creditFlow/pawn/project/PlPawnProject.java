package com.zhiwei.credit.model.creditFlow.pawn.project;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * @author 
 *
 */
/**
 * PlPawnProject Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class PlPawnProject extends com.zhiwei.core.model.BaseProject {

    protected Long projectId;
	protected String businessType;
	protected String operationType;
	protected String flowType;
	protected String mineType;
	protected Long mineId;
	protected String oppositeType;
	protected Long oppositeID;
	protected String projectName;
	protected String projectNumber;
	protected java.math.BigDecimal projectMoney;
	protected java.util.Date startDate;
	protected java.util.Date intentDate;
	protected String payaccrualType;
	protected java.math.BigDecimal accrual;
	protected java.math.BigDecimal monthFeeRate;
	protected java.math.BigDecimal overdueRateLoan;
	protected java.math.BigDecimal overdueRate;
	protected Short projectStatus;
	protected Integer isPreposePayAccrual;
	protected String appUserId;
	protected java.util.Date createDate;
	protected Integer payintentPeriod;
	protected Integer payintentPerioDate;
	protected String isStartDatePay;
	protected Integer dayOfEveryPeriod;
	protected Integer isInterestByOneTime;
	protected java.math.BigDecimal comprehensiveCost;
	protected Long pawnType;
	protected Long productName;
	protected String pawnApplication;
	protected String remarks;
	protected String phnumber;
	protected Date endDate;
	protected String mineName; //无需保留到数据库的字段，临时字段 add by gaoqingrui
	protected Long lockStatus;//锁定状态  当票没有挂失，值就为0，挂失后值就为挂失记录的id,补发后值再一次变为0
	/**
	 * Default Empty Constructor for class PlPawnProject
	 */
	public PlPawnProject () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class PlPawnProject
	 */
	public PlPawnProject (
		 Long in_projectId
        ) {
		this.setProjectId(in_projectId);
    }

    

	public Long getLockStatus() {
		return lockStatus;
	}

	public void setLockStatus(Long lockStatus) {
		this.lockStatus = lockStatus;
	}

	public String getMineName() {
		return mineName;
	}

	public void setMineName(String mineName) {
		this.mineName = mineName;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getPhnumber() {
		return phnumber;
	}

	public void setPhnumber(String phnumber) {
		this.phnumber = phnumber;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * 	 * @return Long
     * @hibernate.id column="projectId" type="java.lang.Long" generator-class="native"
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
	 * @hibernate.property column="businessType" type="java.lang.String" length="50" not-null="false" unique="false"
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
	 * 	 * @return String
	 * @hibernate.property column="operationType" type="java.lang.String" length="30" not-null="false" unique="false"
	 */
	public String getOperationType() {
		return this.operationType;
	}
	
	/**
	 * Set the operationType
	 */	
	public void setOperationType(String aValue) {
		this.operationType = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="flowType" type="java.lang.String" length="200" not-null="false" unique="false"
	 */
	public String getFlowType() {
		return this.flowType;
	}
	
	/**
	 * Set the flowType
	 */	
	public void setFlowType(String aValue) {
		this.flowType = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="mineType" type="java.lang.String" length="30" not-null="false" unique="false"
	 */
	public String getMineType() {
		return this.mineType;
	}
	
	/**
	 * Set the mineType
	 */	
	public void setMineType(String aValue) {
		this.mineType = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="mineId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getMineId() {
		return this.mineId;
	}
	
	/**
	 * Set the mineId
	 */	
	public void setMineId(Long aValue) {
		this.mineId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="oppositeType" type="java.lang.String" length="30" not-null="false" unique="false"
	 */
	public String getOppositeType() {
		return this.oppositeType;
	}
	
	/**
	 * Set the oppositeType
	 */	
	public void setOppositeType(String aValue) {
		this.oppositeType = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="oppositeID" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getOppositeID() {
		return this.oppositeID;
	}
	
	/**
	 * Set the oppositeID
	 */	
	public void setOppositeID(Long aValue) {
		this.oppositeID = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="projectName" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getProjectName() {
		return this.projectName;
	}
	
	/**
	 * Set the projectName
	 */	
	public void setProjectName(String aValue) {
		this.projectName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="projectNumber" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getProjectNumber() {
		return this.projectNumber;
	}
	
	/**
	 * Set the projectNumber
	 */	
	public void setProjectNumber(String aValue) {
		this.projectNumber = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="projectMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getProjectMoney() {
		return this.projectMoney;
	}
	
	/**
	 * Set the projectMoney
	 */	
	public void setProjectMoney(java.math.BigDecimal aValue) {
		this.projectMoney = aValue;
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
	 * @hibernate.property column="payaccrualType" type="java.lang.String" length="30" not-null="false" unique="false"
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
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="overdueRateLoan" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getOverdueRateLoan() {
		return this.overdueRateLoan;
	}
	
	/**
	 * Set the overdueRateLoan
	 */	
	public void setOverdueRateLoan(java.math.BigDecimal aValue) {
		this.overdueRateLoan = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="overdueRate" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getOverdueRate() {
		return this.overdueRate;
	}
	
	/**
	 * Set the overdueRate
	 */	
	public void setOverdueRate(java.math.BigDecimal aValue) {
		this.overdueRate = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="projectStatus" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getProjectStatus() {
		return this.projectStatus;
	}
	
	/**
	 * Set the projectStatus
	 */	
	public void setProjectStatus(Short aValue) {
		this.projectStatus = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="isPreposePayAccrual" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getIsPreposePayAccrual() {
		return this.isPreposePayAccrual;
	}
	
	/**
	 * Set the isPreposePayAccrual
	 */	
	public void setIsPreposePayAccrual(Integer aValue) {
		this.isPreposePayAccrual = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="appUserId" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getAppUserId() {
		return this.appUserId;
	}
	
	/**
	 * Set the appUserId
	 */	
	public void setAppUserId(String aValue) {
		this.appUserId = aValue;
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
	 * 	 * @return Integer
	 * @hibernate.property column="payintentPerioDate" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getPayintentPerioDate() {
		return this.payintentPerioDate;
	}
	
	/**
	 * Set the payintentPerioDate
	 */	
	public void setPayintentPerioDate(Integer aValue) {
		this.payintentPerioDate = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="isStartDatePay" type="java.lang.String" length="30" not-null="false" unique="false"
	 */
	public String getIsStartDatePay() {
		return this.isStartDatePay;
	}
	
	/**
	 * Set the isStartDatePay
	 */	
	public void setIsStartDatePay(String aValue) {
		this.isStartDatePay = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="companyId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getCompanyId() {
		return this.companyId;
	}
	
	/**
	 * Set the companyId
	 */	
	public void setCompanyId(Long aValue) {
		this.companyId = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="dayOfEveryPeriod" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getDayOfEveryPeriod() {
		return this.dayOfEveryPeriod;
	}
	
	/**
	 * Set the dayOfEveryPeriod
	 */	
	public void setDayOfEveryPeriod(Integer aValue) {
		this.dayOfEveryPeriod = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="isInterestByOneTime" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getIsInterestByOneTime() {
		return this.isInterestByOneTime;
	}
	
	/**
	 * Set the isInterestByOneTime
	 */	
	public void setIsInterestByOneTime(Integer aValue) {
		this.isInterestByOneTime = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="comprehensiveCost" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getComprehensiveCost() {
		return this.comprehensiveCost;
	}
	
	/**
	 * Set the comprehensiveCost
	 */	
	public void setComprehensiveCost(java.math.BigDecimal aValue) {
		this.comprehensiveCost = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="pawnType" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getPawnType() {
		return this.pawnType;
	}
	
	/**
	 * Set the pawnType
	 */	
	public void setPawnType(Long aValue) {
		this.pawnType = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="productName" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getProductName() {
		return this.productName;
	}
	
	/**
	 * Set the productName
	 */	
	public void setProductName(Long aValue) {
		this.productName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="pawnApplication" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getPawnApplication() {
		return this.pawnApplication;
	}
	
	/**
	 * Set the pawnApplication
	 */	
	public void setPawnApplication(String aValue) {
		this.pawnApplication = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof PlPawnProject)) {
			return false;
		}
		PlPawnProject rhs = (PlPawnProject) object;
		return new EqualsBuilder()
				.append(this.projectId, rhs.projectId)
				.append(this.businessType, rhs.businessType)
				.append(this.operationType, rhs.operationType)
				.append(this.flowType, rhs.flowType)
				.append(this.mineType, rhs.mineType)
				.append(this.mineId, rhs.mineId)
				.append(this.oppositeType, rhs.oppositeType)
				.append(this.oppositeID, rhs.oppositeID)
				.append(this.projectName, rhs.projectName)
				.append(this.projectNumber, rhs.projectNumber)
				.append(this.projectMoney, rhs.projectMoney)
				.append(this.startDate, rhs.startDate)
				.append(this.intentDate, rhs.intentDate)
				.append(this.payaccrualType, rhs.payaccrualType)
				.append(this.accrual, rhs.accrual)
				.append(this.monthFeeRate, rhs.monthFeeRate)
				.append(this.overdueRateLoan, rhs.overdueRateLoan)
				.append(this.overdueRate, rhs.overdueRate)
				.append(this.projectStatus, rhs.projectStatus)
				.append(this.isPreposePayAccrual, rhs.isPreposePayAccrual)
				.append(this.appUserId, rhs.appUserId)
				.append(this.createDate, rhs.createDate)
				.append(this.payintentPeriod, rhs.payintentPeriod)
				.append(this.payintentPerioDate, rhs.payintentPerioDate)
				.append(this.isStartDatePay, rhs.isStartDatePay)
				.append(this.companyId, rhs.companyId)
				.append(this.dayOfEveryPeriod, rhs.dayOfEveryPeriod)
				.append(this.isInterestByOneTime, rhs.isInterestByOneTime)
				.append(this.comprehensiveCost, rhs.comprehensiveCost)
				.append(this.pawnType, rhs.pawnType)
				.append(this.productName, rhs.productName)
				.append(this.pawnApplication, rhs.pawnApplication)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.projectId) 
				.append(this.businessType) 
				.append(this.operationType) 
				.append(this.flowType) 
				.append(this.mineType) 
				.append(this.mineId) 
				.append(this.oppositeType) 
				.append(this.oppositeID) 
				.append(this.projectName) 
				.append(this.projectNumber) 
				.append(this.projectMoney) 
				.append(this.startDate) 
				.append(this.intentDate) 
				.append(this.payaccrualType) 
				.append(this.accrual) 
				.append(this.monthFeeRate) 
				.append(this.overdueRateLoan) 
				.append(this.overdueRate) 
				.append(this.projectStatus) 
				.append(this.isPreposePayAccrual) 
				.append(this.appUserId) 
				.append(this.createDate) 
				.append(this.payintentPeriod) 
				.append(this.payintentPerioDate) 
				.append(this.isStartDatePay) 
				.append(this.companyId) 
				.append(this.dayOfEveryPeriod) 
				.append(this.isInterestByOneTime) 
				.append(this.comprehensiveCost) 
				.append(this.pawnType) 
				.append(this.productName) 
				.append(this.pawnApplication) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("projectId", this.projectId) 
				.append("businessType", this.businessType) 
				.append("operationType", this.operationType) 
				.append("flowType", this.flowType) 
				.append("mineType", this.mineType) 
				.append("mineId", this.mineId) 
				.append("oppositeType", this.oppositeType) 
				.append("oppositeID", this.oppositeID) 
				.append("projectName", this.projectName) 
				.append("projectNumber", this.projectNumber) 
				.append("projectMoney", this.projectMoney) 
				.append("startDate", this.startDate) 
				.append("intentDate", this.intentDate) 
				.append("payaccrualType", this.payaccrualType) 
				.append("accrual", this.accrual) 
				.append("monthFeeRate", this.monthFeeRate) 
				.append("overdueRateLoan", this.overdueRateLoan) 
				.append("overdueRate", this.overdueRate) 
				.append("projectStatus", this.projectStatus) 
				.append("isPreposePayAccrual", this.isPreposePayAccrual) 
				.append("appUserId", this.appUserId) 
				.append("createDate", this.createDate) 
				.append("payintentPeriod", this.payintentPeriod) 
				.append("payintentPerioDate", this.payintentPerioDate) 
				.append("isStartDatePay", this.isStartDatePay) 
				.append("companyId", this.companyId) 
				.append("dayOfEveryPeriod", this.dayOfEveryPeriod) 
				.append("isInterestByOneTime", this.isInterestByOneTime) 
				.append("comprehensiveCost", this.comprehensiveCost) 
				.append("pawnType", this.pawnType) 
				.append("productName", this.productName) 
				.append("pawnApplication", this.pawnApplication) 
				.toString();
	}



}
