package com.zhiwei.credit.model.creditFlow.pawn.project;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.gson.annotations.Expose;

/**
 * 
 * @author 
 *
 */
/**
 * VPawnProject Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class VPawnProject extends com.zhiwei.core.model.BaseModel {

	protected Long runId;
	protected Long projectId;
	protected String subject;
	protected String creator;
	protected Long userId;
	protected Long defId;
	protected String piId;
	protected java.util.Date createtime;
	protected Short runStatus;
	protected String processName;
	protected String businessType;
	protected String customerName;
	protected String projectNumber;
	protected java.math.BigDecimal accrual;
	protected String appUserId;
	protected java.math.BigDecimal comprehensiveCost;
	protected java.util.Date createDate;
	protected Integer dayOfEveryPeriod;
	protected String flowType;
	protected java.util.Date intentDate;
	protected Integer isInterestByOneTime;
	protected Integer isPreposePayAccrual;
	protected String isStartDatePay;
	protected Long mineId;
	protected String mineType;
	protected java.math.BigDecimal monthFeeRate;
	protected String operationType;
	protected Long oppositeID;
	protected String oppositeType;
	protected java.math.BigDecimal overdueRate;
	protected java.math.BigDecimal overdueRateLoan;
	protected String pawnApplication;
	protected Long pawnType;
	protected String payaccrualType;
	protected Integer payintentPeriod;
	protected Integer payintentPerioDate;
	protected String phnumber;
	protected java.math.BigDecimal projectMoney;
	protected Short projectStatus;
	protected String remarks;
	protected java.util.Date startDate;
	protected String taskId;
	protected String activityName;
	protected java.util.Date taskCreateTime;
	protected java.util.Date endTime;
	protected java.util.Date taskLimitTime;
	protected Long piDbid;
	protected String projectName;
	protected String orgName;
	protected String appUserName;
	protected String executor;//任务执行人
	protected Integer continueCount;
	protected Date continuedstartDate;
	protected Date continuedIntentDate;
	protected Date redeemDate;
	protected Date vastDate;
	protected String companyName;//非数据库关联字段 
	protected Integer  contractCount;//合同个数
	protected Integer morContractCount;//担保物合同个数
	protected String businessManagerValue;//担保物合同个数*/
	protected String breachComment;//担保物合同个数*/
	protected Long lockStatus;//锁定状态  当票没有挂失，值就为0，挂失后值就为挂失记录的id,补发后值再一次变为0
	private Date loanStartDate;//开始时间
	private Date repaymentDate;//结束时间
	
	
	public Date getLoanStartDate() {
		return loanStartDate;
	}




	public void setLoanStartDate(Date loanStartDate) {
		this.loanStartDate = loanStartDate;
	}




	public Date getRepaymentDate() {
		return repaymentDate;
	}




	public void setRepaymentDate(Date repaymentDate) {
		this.repaymentDate = repaymentDate;
	}




	public Long getLockStatus() {
		return lockStatus;
	}




	public void setLockStatus(Long lockStatus) {
		this.lockStatus = lockStatus;
	}




	/**
	 * Default Empty Constructor for class VPawnProject
	 */
	public VPawnProject () {
		super();
	}
	
	
	

	public Integer getContractCount() {
		return contractCount;
	}




	public void setContractCount(Integer contractCount) {
		this.contractCount = contractCount;
	}




	public Integer getMorContractCount() {
		return morContractCount;
	}




	public void setMorContractCount(Integer morContractCount) {
		this.morContractCount = morContractCount;
	}




	public String getBusinessManagerValue() {
		return businessManagerValue;
	}




	public void setBusinessManagerValue(String businessManagerValue) {
		this.businessManagerValue = businessManagerValue;
	}




	public String getBreachComment() {
		return breachComment;
	}




	public void setBreachComment(String breachComment) {
		this.breachComment = breachComment;
	}


	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	public Date getRedeemDate() {
		return redeemDate;
	}


	public void setRedeemDate(Date redeemDate) {
		this.redeemDate = redeemDate;
	}


	public Date getVastDate() {
		return vastDate;
	}


	public void setVastDate(Date vastDate) {
		this.vastDate = vastDate;
	}


	public Integer getContinueCount() {
		return continueCount;
	}


	public void setContinueCount(Integer continueCount) {
		this.continueCount = continueCount;
	}



	public Date getContinuedstartDate() {
		return continuedstartDate;
	}




	public void setContinuedstartDate(Date continuedstartDate) {
		this.continuedstartDate = continuedstartDate;
	}




	public Date getContinuedIntentDate() {
		return continuedIntentDate;
	}


	public void setContinuedIntentDate(Date continuedIntentDate) {
		this.continuedIntentDate = continuedIntentDate;
	}


	public String getOrgName() {
		return orgName;
	}


	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}


	public String getAppUserName() {
		return appUserName;
	}


	public void setAppUserName(String appUserName) {
		this.appUserName = appUserName;
	}


	public String getExecutor() {
		return executor;
	}


	public void setExecutor(String executor) {
		this.executor = executor;
	}


	public String getProjectName() {
		return projectName;
	}


	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}


	/**
	 * 	 * @return Long
	 * @hibernate.property column="runId" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	public Long getRunId() {
		return this.runId;
	}
	
	/**
	 * Set the runId
	 * @spring.validator type="required"
	 */	
	public void setRunId(Long aValue) {
		this.runId = aValue;
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
	 * 标题
            一般为流程名称＋格式化的时间	 * @return String
	 * @hibernate.property column="subject" type="java.lang.String" length="256" not-null="true" unique="false"
	 */
	public String getSubject() {
		return this.subject;
	}
	
	/**
	 * Set the subject
	 * @spring.validator type="required"
	 */	
	public void setSubject(String aValue) {
		this.subject = aValue;
	}	

	/**
	 * 创建人	 * @return String
	 * @hibernate.property column="creator" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getCreator() {
		return this.creator;
	}
	
	/**
	 * Set the creator
	 */	
	public void setCreator(String aValue) {
		this.creator = aValue;
	}	

	/**
	 * 所属用户	 * @return Long
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
	 * 所属流程定义	 * @return Long
	 * @hibernate.property column="defId" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	public Long getDefId() {
		return this.defId;
	}
	
	/**
	 * Set the defId
	 * @spring.validator type="required"
	 */	
	public void setDefId(Long aValue) {
		this.defId = aValue;
	}	

	/**
	 * 流程实例ID	 * @return String
	 * @hibernate.property column="piId" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getPiId() {
		return this.piId;
	}
	
	/**
	 * Set the piId
	 */	
	public void setPiId(String aValue) {
		this.piId = aValue;
	}	

	/**
	 * 创建时间	 * @return java.util.Date
	 * @hibernate.property column="createtime" type="java.util.Date" length="19" not-null="true" unique="false"
	 */
	public java.util.Date getCreatetime() {
		return this.createtime;
	}
	
	/**
	 * Set the createtime
	 * @spring.validator type="required"
	 */	
	public void setCreatetime(java.util.Date aValue) {
		this.createtime = aValue;
	}	

	/**
	 * 0=尚未启动
            1=已经启动流程
            2=运行结束	 * @return Short
	 * @hibernate.property column="runStatus" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getRunStatus() {
		return this.runStatus;
	}
	
	/**
	 * Set the runStatus
	 * @spring.validator type="required"
	 */	
	public void setRunStatus(Short aValue) {
		this.runStatus = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="processName" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getProcessName() {
		return this.processName;
	}
	
	/**
	 * Set the processName
	 */	
	public void setProcessName(String aValue) {
		this.processName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="businessType" type="java.lang.String" length="30" not-null="false" unique="false"
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
	 * @hibernate.property column="customerName" type="java.lang.String" length="200" not-null="false" unique="false"
	 */
	public String getCustomerName() {
		return this.customerName;
	}
	
	/**
	 * Set the customerName
	 */	
	public void setCustomerName(String aValue) {
		this.customerName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="projectNumber" type="java.lang.String" length="50" not-null="false" unique="false"
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
	 * @hibernate.property column="dayOfEveryPeriod" type="java.lang.Long" length="19" not-null="false" unique="false"
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
	 * 	 * @return Long
	 * @hibernate.property column="isInterestByOneTime" type="java.lang.Long" length="19" not-null="false" unique="false"
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
	 * 	 * @return Long
	 * @hibernate.property column="isPreposePayAccrual" type="java.lang.Long" length="19" not-null="false" unique="false"
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
	 * 	 * @return Long
	 * @hibernate.property column="payintentPeriod" type="java.lang.Long" length="19" not-null="false" unique="false"
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
	 * 	 * @return Long
	 * @hibernate.property column="payintentPerioDate" type="java.lang.Long" length="19" not-null="false" unique="false"
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
	 * @hibernate.property column="phnumber" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getPhnumber() {
		return this.phnumber;
	}
	
	/**
	 * Set the phnumber
	 */	
	public void setPhnumber(String aValue) {
		this.phnumber = aValue;
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
	 * 	 * @return Integer
	 * @hibernate.property column="projectStatus" type="java.lang.Integer" length="10" not-null="false" unique="false"
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
	 * 	 * @return String
	 * @hibernate.property column="taskId" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getTaskId() {
		return this.taskId;
	}
	
	/**
	 * Set the taskId
	 */	
	public void setTaskId(String aValue) {
		this.taskId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="activityName" type="java.lang.String" length="256" not-null="false" unique="false"
	 */
	public String getActivityName() {
		return this.activityName;
	}
	
	/**
	 * Set the activityName
	 */	
	public void setActivityName(String aValue) {
		this.activityName = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="taskCreateTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getTaskCreateTime() {
		return this.taskCreateTime;
	}
	
	/**
	 * Set the taskCreateTime
	 */	
	public void setTaskCreateTime(java.util.Date aValue) {
		this.taskCreateTime = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="endTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getEndTime() {
		return this.endTime;
	}
	
	/**
	 * Set the endTime
	 */	
	public void setEndTime(java.util.Date aValue) {
		this.endTime = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="taskLimitTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getTaskLimitTime() {
		return this.taskLimitTime;
	}
	
	/**
	 * Set the taskLimitTime
	 */	
	public void setTaskLimitTime(java.util.Date aValue) {
		this.taskLimitTime = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="piDbid" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getPiDbid() {
		return this.piDbid;
	}
	
	/**
	 * Set the piDbid
	 */	
	public void setPiDbid(Long aValue) {
		this.piDbid = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof VPawnProject)) {
			return false;
		}
		VPawnProject rhs = (VPawnProject) object;
		return new EqualsBuilder()
				.append(this.runId, rhs.runId)
				.append(this.projectId, rhs.projectId)
				.append(this.subject, rhs.subject)
				.append(this.creator, rhs.creator)
				.append(this.userId, rhs.userId)
				.append(this.defId, rhs.defId)
				.append(this.piId, rhs.piId)
				.append(this.createtime, rhs.createtime)
				.append(this.runStatus, rhs.runStatus)
				.append(this.processName, rhs.processName)
				.append(this.businessType, rhs.businessType)
				.append(this.customerName, rhs.customerName)
				.append(this.projectNumber, rhs.projectNumber)
				.append(this.accrual, rhs.accrual)
				.append(this.appUserId, rhs.appUserId)
				.append(this.companyId, rhs.companyId)
				.append(this.comprehensiveCost, rhs.comprehensiveCost)
				.append(this.createDate, rhs.createDate)
				.append(this.dayOfEveryPeriod, rhs.dayOfEveryPeriod)
				.append(this.flowType, rhs.flowType)
				.append(this.intentDate, rhs.intentDate)
				.append(this.isInterestByOneTime, rhs.isInterestByOneTime)
				.append(this.isPreposePayAccrual, rhs.isPreposePayAccrual)
				.append(this.isStartDatePay, rhs.isStartDatePay)
				.append(this.mineId, rhs.mineId)
				.append(this.mineType, rhs.mineType)
				.append(this.monthFeeRate, rhs.monthFeeRate)
				.append(this.operationType, rhs.operationType)
				.append(this.oppositeID, rhs.oppositeID)
				.append(this.oppositeType, rhs.oppositeType)
				.append(this.overdueRate, rhs.overdueRate)
				.append(this.overdueRateLoan, rhs.overdueRateLoan)
				.append(this.pawnApplication, rhs.pawnApplication)
				.append(this.pawnType, rhs.pawnType)
				.append(this.payaccrualType, rhs.payaccrualType)
				.append(this.payintentPeriod, rhs.payintentPeriod)
				.append(this.payintentPerioDate, rhs.payintentPerioDate)
				.append(this.phnumber, rhs.phnumber)
				.append(this.projectMoney, rhs.projectMoney)
				.append(this.projectStatus, rhs.projectStatus)
				.append(this.remarks, rhs.remarks)
				.append(this.startDate, rhs.startDate)
				.append(this.taskId, rhs.taskId)
				.append(this.activityName, rhs.activityName)
				.append(this.taskCreateTime, rhs.taskCreateTime)
				.append(this.endTime, rhs.endTime)
				.append(this.taskLimitTime, rhs.taskLimitTime)
				.append(this.piDbid, rhs.piDbid)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.runId) 
				.append(this.projectId) 
				.append(this.subject) 
				.append(this.creator) 
				.append(this.userId) 
				.append(this.defId) 
				.append(this.piId) 
				.append(this.createtime) 
				.append(this.runStatus) 
				.append(this.processName) 
				.append(this.businessType) 
				.append(this.customerName) 
				.append(this.projectNumber) 
				.append(this.accrual) 
				.append(this.appUserId) 
				.append(this.companyId) 
				.append(this.comprehensiveCost) 
				.append(this.createDate) 
				.append(this.dayOfEveryPeriod) 
				.append(this.flowType) 
				.append(this.intentDate) 
				.append(this.isInterestByOneTime) 
				.append(this.isPreposePayAccrual) 
				.append(this.isStartDatePay) 
				.append(this.mineId) 
				.append(this.mineType) 
				.append(this.monthFeeRate) 
				.append(this.operationType) 
				.append(this.oppositeID) 
				.append(this.oppositeType) 
				.append(this.overdueRate) 
				.append(this.overdueRateLoan) 
				.append(this.pawnApplication) 
				.append(this.pawnType) 
				.append(this.payaccrualType) 
				.append(this.payintentPeriod) 
				.append(this.payintentPerioDate) 
				.append(this.phnumber) 
				.append(this.projectMoney) 
				.append(this.projectStatus) 
				.append(this.remarks) 
				.append(this.startDate) 
				.append(this.taskId) 
				.append(this.activityName) 
				.append(this.taskCreateTime) 
				.append(this.endTime) 
				.append(this.taskLimitTime) 
				.append(this.piDbid) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("runId", this.runId) 
				.append("projectId", this.projectId) 
				.append("subject", this.subject) 
				.append("creator", this.creator) 
				.append("userId", this.userId) 
				.append("defId", this.defId) 
				.append("piId", this.piId) 
				.append("createtime", this.createtime) 
				.append("runStatus", this.runStatus) 
				.append("processName", this.processName) 
				.append("businessType", this.businessType) 
				.append("customerName", this.customerName) 
				.append("projectNumber", this.projectNumber) 
				.append("accrual", this.accrual) 
				.append("appUserId", this.appUserId) 
				.append("companyId", this.companyId) 
				.append("comprehensiveCost", this.comprehensiveCost) 
				.append("createDate", this.createDate) 
				.append("dayOfEveryPeriod", this.dayOfEveryPeriod) 
				.append("flowType", this.flowType) 
				.append("intentDate", this.intentDate) 
				.append("isInterestByOneTime", this.isInterestByOneTime) 
				.append("isPreposePayAccrual", this.isPreposePayAccrual) 
				.append("isStartDatePay", this.isStartDatePay) 
				.append("mineId", this.mineId) 
				.append("mineType", this.mineType) 
				.append("monthFeeRate", this.monthFeeRate) 
				.append("operationType", this.operationType) 
				.append("oppositeID", this.oppositeID) 
				.append("oppositeType", this.oppositeType) 
				.append("overdueRate", this.overdueRate) 
				.append("overdueRateLoan", this.overdueRateLoan) 
				.append("pawnApplication", this.pawnApplication) 
				.append("pawnType", this.pawnType) 
				.append("payaccrualType", this.payaccrualType) 
				.append("payintentPeriod", this.payintentPeriod) 
				.append("payintentPerioDate", this.payintentPerioDate) 
				.append("phnumber", this.phnumber) 
				.append("projectMoney", this.projectMoney) 
				.append("projectStatus", this.projectStatus) 
				.append("remarks", this.remarks) 
				.append("startDate", this.startDate) 
				.append("taskId", this.taskId) 
				.append("activityName", this.activityName) 
				.append("taskCreateTime", this.taskCreateTime) 
				.append("endTime", this.endTime) 
				.append("taskLimitTime", this.taskLimitTime) 
				.append("piDbid", this.piDbid) 
				.toString();
	}



}
