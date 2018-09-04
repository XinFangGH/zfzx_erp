package com.zhiwei.credit.model.creditFlow.leaseFinance.project;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * 
 * @author 
 *
 */
/**
 * FlLeaseFinanceProject Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
@SuppressWarnings("serial")
public class FlLeaseFinanceProject extends com.zhiwei.core.model.BaseProject {

    protected Long projectId;
	protected String businessType;//业务类别
	protected String operationType;//业务品种
	protected String flowType;//流程类别
	protected String mineType;//主体类别
	protected Long mineId;//主体Id
	protected String oppositeType;//客户类别
	protected Long oppositeID;//客户Id
	protected String projectName;//项目名称
	protected String projectNumber;//项目编号
	protected String appUserId;//项目经理
	protected String leasingType;//租赁类型     
	protected Long mainWays;//主担保方式
	protected Long leaseClassification;//租赁分类
	protected String leasedPropertyPlace;//租赁物所在地
	protected Long industryCategory;//行业类别
	protected java.math.BigDecimal projectMoney;//租赁成本
	protected java.math.BigDecimal allMoney;//租赁总额
	protected java.math.BigDecimal leaseDepositRate;//租赁保证金率
	protected java.math.BigDecimal leaseDepositMoney;//租赁保证金费
	protected java.math.BigDecimal rentalFeeRate;//租赁手续费率
	protected java.math.BigDecimal rentalFeeMoney;//租赁手续费
	protected java.math.BigDecimal leaseRetentionFeeRate;//租赁物件留购费率
	protected java.math.BigDecimal leaseRetentionFeeMoney;//租赁物件留购费
	protected java.math.BigDecimal rentalRate;//租赁费率
	protected java.math.BigDecimal rentalMoney;//租赁费
	protected String accrualtype;//还款方式
	protected String payaccrualType;//还款周期
	protected String feePayaccrualType;//租赁手续费还款周期
	protected Integer payintentPeriod;//租金期限
	protected Short projectStatus;//项目状态
	protected Integer payintentPerioDate;//固定日期
	protected java.util.Date createDate;//启动时间
	protected String isStartDatePay;//每期还款日
	protected Integer dayOfEveryPeriod;//自定义天数
	protected java.util.Date startDate;//租赁起始日
	protected java.util.Date endDate;//租赁起始日
	protected java.util.Date intentDate;//租赁截至日
	protected java.math.BigDecimal eachRentalReceivable;//每期应收租金
	protected java.math.BigDecimal connotationRate;//内涵收益率
	protected java.math.BigDecimal overdueRate;//逾期罚息利率
	protected String mineName; //无需保留到数据库的字段，临时字段 add by gaoqingrui
	
	protected Short isOtherFlow;//是否属于处于利率变更，展期，提前还款的款项计划重新生成：默认为0表示没有贷后流程，1表示处于展期流程中；2表示提前还款流程；3表示利率变更流程
	
	protected BigDecimal accrualnew;//利率变更
	protected String breachComment;//违约说明
	
	
	

	

	public BigDecimal getAccrualnew() {
		return accrualnew;
	}

	public void setAccrualnew(BigDecimal accrualnew) {
		this.accrualnew = accrualnew;
	}

	public Short getIsOtherFlow() {
		return isOtherFlow;
	}

	public void setIsOtherFlow(Short isOtherFlow) {
		this.isOtherFlow = isOtherFlow;
	}

	public String getBreachComment() {
		return breachComment;
	}

	public void setBreachComment(String breachComment) {
		this.breachComment = breachComment;
	}

	/**
	 * Default Empty Constructor for class FlLeaseFinanceProject
	 */
	public FlLeaseFinanceProject () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class FlLeaseFinanceProject
	 */
	public FlLeaseFinanceProject (
		 Long in_projectId
        ) {
		this.setProjectId(in_projectId);
    }
	
	public java.util.Date getEndDate() {
		return endDate;
	}

	public void setEndDate(java.util.Date endDate) {
		this.endDate = endDate;
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
	 * 	 * @return String
	 * @hibernate.property column="operationType" type="java.lang.String" length="100" not-null="false" unique="false"
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
	 * @hibernate.property column="mineType" type="java.lang.String" length="50" not-null="false" unique="false"
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
	 * @hibernate.property column="oppositeType" type="java.lang.String" length="50" not-null="false" unique="false"
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
	 * 	 * @return String
	 * @hibernate.property column="appUserId" type="java.lang.String" length="100" not-null="false" unique="false"
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
	 * 	 * @return String
	 * @hibernate.property column="leasingType" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getLeasingType() {
		return this.leasingType;
	}
	
	/**
	 * Set the leasingType
	 */	
	public void setLeasingType(String aValue) {
		this.leasingType = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="mainWays" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getMainWays() {
		return this.mainWays;
	}
	
	/**
	 * Set the mainWays
	 */	
	public void setMainWays(Long aValue) {
		this.mainWays = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="leaseClassification" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getLeaseClassification() {
		return this.leaseClassification;
	}
	
	/**
	 * Set the leaseClassification
	 */	
	public void setLeaseClassification(Long aValue) {
		this.leaseClassification = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="leasedPropertyPlace" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getLeasedPropertyPlace() {
		return this.leasedPropertyPlace;
	}
	
	/**
	 * Set the leasedPropertyPlace
	 */	
	public void setLeasedPropertyPlace(String aValue) {
		this.leasedPropertyPlace = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="industryCategory" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getIndustryCategory() {
		return this.industryCategory;
	}
	
	/**
	 * Set the industryCategory
	 */	
	public void setIndustryCategory(Long aValue) {
		this.industryCategory = aValue;
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
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="allMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getAllMoney() {
		return this.allMoney;
	}
	
	/**
	 * Set the allMoney
	 */	
	public void setAllMoney(java.math.BigDecimal aValue) {
		this.allMoney = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="leaseDepositRate" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getLeaseDepositRate() {
		return this.leaseDepositRate;
	}
	
	/**
	 * Set the leaseDepositRate
	 */	
	public void setLeaseDepositRate(java.math.BigDecimal aValue) {
		this.leaseDepositRate = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="leaseDepositMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getLeaseDepositMoney() {
		return this.leaseDepositMoney;
	}
	
	/**
	 * Set the leaseDepositMoney
	 */	
	public void setLeaseDepositMoney(java.math.BigDecimal aValue) {
		this.leaseDepositMoney = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="rentalFeeRate" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getRentalFeeRate() {
		return this.rentalFeeRate;
	}
	
	/**
	 * Set the rentalFeeRate
	 */	
	public void setRentalFeeRate(java.math.BigDecimal aValue) {
		this.rentalFeeRate = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="rentalFeeMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getRentalFeeMoney() {
		return this.rentalFeeMoney;
	}
	
	/**
	 * Set the rentalFeeMoney
	 */	
	public void setRentalFeeMoney(java.math.BigDecimal aValue) {
		this.rentalFeeMoney = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="leaseRetentionFeeRate" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getLeaseRetentionFeeRate() {
		return this.leaseRetentionFeeRate;
	}
	
	/**
	 * Set the leaseRetentionFeeRate
	 */	
	public void setLeaseRetentionFeeRate(java.math.BigDecimal aValue) {
		this.leaseRetentionFeeRate = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="leaseRetentionFeeMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getLeaseRetentionFeeMoney() {
		return this.leaseRetentionFeeMoney;
	}
	
	/**
	 * Set the leaseRetentionFeeMoney
	 */	
	public void setLeaseRetentionFeeMoney(java.math.BigDecimal aValue) {
		this.leaseRetentionFeeMoney = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="rentalRate" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getRentalRate() {
		return this.rentalRate;
	}
	
	/**
	 * Set the rentalRate
	 */	
	public void setRentalRate(java.math.BigDecimal aValue) {
		this.rentalRate = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="rentalMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getRentalMoney() {
		return this.rentalMoney;
	}
	
	/**
	 * Set the rentalMoney
	 */	
	public void setRentalMoney(java.math.BigDecimal aValue) {
		this.rentalMoney = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="accrualtype" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getAccrualtype() {
		return this.accrualtype;
	}
	
	/**
	 * Set the accrualtype
	 */	
	public void setAccrualtype(String aValue) {
		this.accrualtype = aValue;
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
	 * 	 * @return String
	 * @hibernate.property column="feePayaccrualType" type="java.lang.String" length="30" not-null="false" unique="false"
	 */
	public String getFeePayaccrualType() {
		return this.feePayaccrualType;
	}
	
	/**
	 * Set the feePayaccrualType
	 */	
	public void setFeePayaccrualType(String aValue) {
		this.feePayaccrualType = aValue;
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
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="eachRentalReceivable" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getEachRentalReceivable() {
		return this.eachRentalReceivable;
	}
	
	/**
	 * Set the eachRentalReceivable
	 */	
	public void setEachRentalReceivable(java.math.BigDecimal aValue) {
		this.eachRentalReceivable = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="connotationRate" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getConnotationRate() {
		return this.connotationRate;
	}
	
	/**
	 * Set the connotationRate
	 */	
	public void setConnotationRate(java.math.BigDecimal aValue) {
		this.connotationRate = aValue;
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
	 * 	 * @return Long
	 * @hibernate.property column="companyId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getCompanyId() {
		return this.companyId;
	}
	
	
	
	
	public String getMineName() {
		return mineName;
	}

	public void setMineName(String mineName) {
		this.mineName = mineName;
	}

	/**
	 * Set the companyId
	 */	
	public void setCompanyId(Long aValue) {
		this.companyId = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof FlLeaseFinanceProject)) {
			return false;
		}
		FlLeaseFinanceProject rhs = (FlLeaseFinanceProject) object;
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
				.append(this.appUserId, rhs.appUserId)
				.append(this.leasingType, rhs.leasingType)
				.append(this.mainWays, rhs.mainWays)
				.append(this.leaseClassification, rhs.leaseClassification)
				.append(this.leasedPropertyPlace, rhs.leasedPropertyPlace)
				.append(this.industryCategory, rhs.industryCategory)
				.append(this.projectMoney, rhs.projectMoney)
				.append(this.allMoney, rhs.allMoney)
				.append(this.leaseDepositRate, rhs.leaseDepositRate)
				.append(this.leaseDepositMoney, rhs.leaseDepositMoney)
				.append(this.rentalFeeRate, rhs.rentalFeeRate)
				.append(this.rentalFeeMoney, rhs.rentalFeeMoney)
				.append(this.leaseRetentionFeeRate, rhs.leaseRetentionFeeRate)
				.append(this.leaseRetentionFeeMoney, rhs.leaseRetentionFeeMoney)
				.append(this.rentalRate, rhs.rentalRate)
				.append(this.rentalMoney, rhs.rentalMoney)
				.append(this.accrualtype, rhs.accrualtype)
				.append(this.payaccrualType, rhs.payaccrualType)
				.append(this.feePayaccrualType, rhs.feePayaccrualType)
				.append(this.payintentPeriod, rhs.payintentPeriod)
				.append(this.projectStatus, rhs.projectStatus)
				.append(this.payintentPerioDate, rhs.payintentPerioDate)
				.append(this.createDate, rhs.createDate)
				.append(this.isStartDatePay, rhs.isStartDatePay)
				.append(this.dayOfEveryPeriod, rhs.dayOfEveryPeriod)
				.append(this.startDate, rhs.startDate)
				.append(this.intentDate, rhs.intentDate)
				.append(this.eachRentalReceivable, rhs.eachRentalReceivable)
				.append(this.connotationRate, rhs.connotationRate)
				.append(this.overdueRate, rhs.overdueRate)
				.append(this.companyId, rhs.companyId)
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
				.append(this.appUserId) 
				.append(this.leasingType) 
				.append(this.mainWays) 
				.append(this.leaseClassification) 
				.append(this.leasedPropertyPlace) 
				.append(this.industryCategory) 
				.append(this.projectMoney) 
				.append(this.allMoney) 
				.append(this.leaseDepositRate) 
				.append(this.leaseDepositMoney) 
				.append(this.rentalFeeRate) 
				.append(this.rentalFeeMoney) 
				.append(this.leaseRetentionFeeRate) 
				.append(this.leaseRetentionFeeMoney) 
				.append(this.rentalRate) 
				.append(this.rentalMoney) 
				.append(this.accrualtype) 
				.append(this.payaccrualType) 
				.append(this.feePayaccrualType) 
				.append(this.payintentPeriod) 
				.append(this.projectStatus) 
				.append(this.payintentPerioDate) 
				.append(this.createDate) 
				.append(this.isStartDatePay) 
				.append(this.dayOfEveryPeriod) 
				.append(this.startDate) 
				.append(this.intentDate) 
				.append(this.eachRentalReceivable) 
				.append(this.connotationRate) 
				.append(this.overdueRate) 
				.append(this.companyId) 
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
				.append("appUserId", this.appUserId) 
				.append("leasingType", this.leasingType) 
				.append("mainWays", this.mainWays) 
				.append("leaseClassification", this.leaseClassification) 
				.append("leasedPropertyPlace", this.leasedPropertyPlace) 
				.append("industryCategory", this.industryCategory) 
				.append("projectMoney", this.projectMoney) 
				.append("allMoney", this.allMoney) 
				.append("leaseDepositRate", this.leaseDepositRate) 
				.append("leaseDepositMoney", this.leaseDepositMoney) 
				.append("rentalFeeRate", this.rentalFeeRate) 
				.append("rentalFeeMoney", this.rentalFeeMoney) 
				.append("leaseRetentionFeeRate", this.leaseRetentionFeeRate) 
				.append("leaseRetentionFeeMoney", this.leaseRetentionFeeMoney) 
				.append("rentalRate", this.rentalRate) 
				.append("rentalMoney", this.rentalMoney) 
				.append("accrualtype", this.accrualtype) 
				.append("payaccrualType", this.payaccrualType) 
				.append("feePayaccrualType", this.feePayaccrualType) 
				.append("payintentPeriod", this.payintentPeriod) 
				.append("projectStatus", this.projectStatus) 
				.append("payintentPerioDate", this.payintentPerioDate) 
				.append("createDate", this.createDate) 
				.append("isStartDatePay", this.isStartDatePay) 
				.append("dayOfEveryPeriod", this.dayOfEveryPeriod) 
				.append("startDate", this.startDate) 
				.append("intentDate", this.intentDate) 
				.append("eachRentalReceivable", this.eachRentalReceivable) 
				.append("connotationRate", this.connotationRate) 
				.append("overdueRate", this.overdueRate) 
				.append("companyId", this.companyId) 
				.toString();
	}
}
