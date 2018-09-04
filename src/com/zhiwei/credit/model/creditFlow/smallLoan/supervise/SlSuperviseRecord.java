package com.zhiwei.credit.model.creditFlow.smallLoan.supervise;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

public class SlSuperviseRecord extends com.zhiwei.core.model.BaseModel {

	private static final long serialVersionUID = 1L;
	protected Long id;
	protected String reason;
	protected java.util.Date startDate;
	protected java.util.Date endDate;
	
	protected Long projectId;
	protected String remark;
	protected java.math.BigDecimal continuationMoney;
	protected java.math.BigDecimal continuationRate; //展期利率
	protected Integer payintentPeriod; //还款期数
	protected String payaccrualType;   //付息方式
	protected String payaccrualTypeName;   //付息方式
	protected String accrualtype;  //计息方式
	protected String isStartDatePay; //是否按还款日还款,
	protected Integer payintentPerioDate; //每期还款日,
	protected Integer dayOfEveryPeriod; //自定义周期的天数,
	
	
	protected String accrualtypeName;
	protected Integer isPreposePayAccrualsupervise; //是否前置付息 0否 	1 是
    protected java.math.BigDecimal managementConsultingOfRate; //管理咨询费率
    protected java.math.BigDecimal financeServiceOfRate; //财务服务费率
    protected Integer checkStatus;  //0代表未审核  1代表未提交审查  5 代表审核通过 6 代表未审核通过
    protected Date opTime;//启动时间
    protected java.math.BigDecimal overdueRate;//罚息利率
    protected String businessType;
    protected String creator;
    private   String AppUserName;
    protected BigDecimal continuationRateNew;
    protected BigDecimal overdueRateLoan;
    protected Boolean isPreposePayConsultingCheck;
    protected String dateMode;
    protected Integer isInterestByOneTime;
    protected BigDecimal yearAccrualRate;
    protected BigDecimal dayAccrualRate;
    protected BigDecimal sumAccrualRate;
    protected BigDecimal yearManagementConsultingOfRate;
    protected BigDecimal dayManagementConsultingOfRate;
    protected BigDecimal sumManagementConsultingOfRate;
    protected BigDecimal yearFinanceServiceOfRate;
    protected BigDecimal dayFinanceServiceOfRate;
    protected BigDecimal sumFinanceServiceOfRate;
    private String feePayaccrualType;//租赁费还款周期
    private BigDecimal eachRentalReceivable;//每期应还租金
    
    protected String baseBusinessType;
    /**
     * 
     * 展期的债权是否进入债权库，1为已进入债券库，null or 0 为木有
     */
    private Short isInRights;//

	public Short getIsInRights() {
		return isInRights;
	}
	public void setIsInRights(Short isInRights) {
		this.isInRights = isInRights;
	}
	public String getFeePayaccrualType() {
		return feePayaccrualType;
	}
	public void setFeePayaccrualType(String feePayaccrualType) {
		this.feePayaccrualType = feePayaccrualType;
	}
	public BigDecimal getEachRentalReceivable() {
		return eachRentalReceivable;
	}
	public void setEachRentalReceivable(BigDecimal eachRentalReceivable) {
		this.eachRentalReceivable = eachRentalReceivable;
	}
	public String getBaseBusinessType() {
		return baseBusinessType;
	}
	public void setBaseBusinessType(String baseBusinessType) {
		this.baseBusinessType = baseBusinessType;
	}
	public String getDateMode() {
		return dateMode;
	}
	public void setDateMode(String dateMode) {
		this.dateMode = dateMode;
	}
	public Integer getIsInterestByOneTime() {
		return isInterestByOneTime;
	}
	public void setIsInterestByOneTime(Integer isInterestByOneTime) {
		this.isInterestByOneTime = isInterestByOneTime;
	}
	public BigDecimal getYearAccrualRate() {
		return yearAccrualRate;
	}
	public void setYearAccrualRate(BigDecimal yearAccrualRate) {
		this.yearAccrualRate = yearAccrualRate;
	}
	public BigDecimal getDayAccrualRate() {
		return dayAccrualRate;
	}
	public void setDayAccrualRate(BigDecimal dayAccrualRate) {
		this.dayAccrualRate = dayAccrualRate;
	}
	public BigDecimal getSumAccrualRate() {
		return sumAccrualRate;
	}
	public void setSumAccrualRate(BigDecimal sumAccrualRate) {
		this.sumAccrualRate = sumAccrualRate;
	}
	public BigDecimal getYearManagementConsultingOfRate() {
		return yearManagementConsultingOfRate;
	}
	public void setYearManagementConsultingOfRate(
			BigDecimal yearManagementConsultingOfRate) {
		this.yearManagementConsultingOfRate = yearManagementConsultingOfRate;
	}
	public BigDecimal getDayManagementConsultingOfRate() {
		return dayManagementConsultingOfRate;
	}
	public void setDayManagementConsultingOfRate(
			BigDecimal dayManagementConsultingOfRate) {
		this.dayManagementConsultingOfRate = dayManagementConsultingOfRate;
	}
	public BigDecimal getSumManagementConsultingOfRate() {
		return sumManagementConsultingOfRate;
	}
	public void setSumManagementConsultingOfRate(
			BigDecimal sumManagementConsultingOfRate) {
		this.sumManagementConsultingOfRate = sumManagementConsultingOfRate;
	}
	public BigDecimal getYearFinanceServiceOfRate() {
		return yearFinanceServiceOfRate;
	}
	public void setYearFinanceServiceOfRate(BigDecimal yearFinanceServiceOfRate) {
		this.yearFinanceServiceOfRate = yearFinanceServiceOfRate;
	}
	public BigDecimal getDayFinanceServiceOfRate() {
		return dayFinanceServiceOfRate;
	}
	public void setDayFinanceServiceOfRate(BigDecimal dayFinanceServiceOfRate) {
		this.dayFinanceServiceOfRate = dayFinanceServiceOfRate;
	}
	public BigDecimal getSumFinanceServiceOfRate() {
		return sumFinanceServiceOfRate;
	}
	public void setSumFinanceServiceOfRate(BigDecimal sumFinanceServiceOfRate) {
		this.sumFinanceServiceOfRate = sumFinanceServiceOfRate;
	}
	public Boolean getIsPreposePayConsultingCheck() {
		return isPreposePayConsultingCheck;
	}
	public void setIsPreposePayConsultingCheck(Boolean isPreposePayConsultingCheck) {
		this.isPreposePayConsultingCheck = isPreposePayConsultingCheck;
	}
	public BigDecimal getOverdueRateLoan() {
		return overdueRateLoan;
	}
	public void setOverdueRateLoan(BigDecimal overdueRateLoan) {
		this.overdueRateLoan = overdueRateLoan;
	}
	public BigDecimal getContinuationRateNew() {
		return continuationRateNew;
	}
	public void setContinuationRateNew(BigDecimal continuationRateNew) {
		this.continuationRateNew = continuationRateNew;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public java.math.BigDecimal getOverdueRate() {
		return overdueRate;
	}
	public void setOverdueRate(java.math.BigDecimal overdueRate) {
		this.overdueRate = overdueRate;
	}
	public Integer getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}
	
	public Date getOpTime() {
		return opTime;
	}
	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}
	public Integer getDayOfEveryPeriod() {
		return dayOfEveryPeriod;
	}
	public void setDayOfEveryPeriod(Integer dayOfEveryPeriod) {
		this.dayOfEveryPeriod = dayOfEveryPeriod;
	}
	public java.math.BigDecimal getContinuationMoney() {
		return continuationMoney;
	}
	public void setContinuationMoney(java.math.BigDecimal continuationMoney) {
		this.continuationMoney = continuationMoney;
	}
	public String getPayaccrualTypeName() {
		return payaccrualTypeName;
	}
	public void setPayaccrualTypeName(String payaccrualTypeName) {
		this.payaccrualTypeName = payaccrualTypeName;
	}
	public String getAccrualtypeName() {
		return accrualtypeName;
	}
	public void setAccrualtypeName(String accrualtypeName) {
		this.accrualtypeName = accrualtypeName;
	}
	
	public String getIsStartDatePay() {
		return isStartDatePay;
	}
	public void setIsStartDatePay(String isStartDatePay) {
		this.isStartDatePay = isStartDatePay;
	}
	public Integer getPayintentPerioDate() {
		return payintentPerioDate;
	}
	public void setPayintentPerioDate(Integer payintentPerioDate) {
		this.payintentPerioDate = payintentPerioDate;
	}
	public String getPayaccrualType() {
		return payaccrualType;
	}
	public void setPayaccrualType(String payaccrualType) {
		this.payaccrualType = payaccrualType;
	}
	public String getAccrualtype() {
		return accrualtype;
	}
	public void setAccrualtype(String accrualtype) {
		this.accrualtype = accrualtype;
	}
	
	public Integer getPayintentPeriod() {
		return payintentPeriod;
	}
	public void setPayintentPeriod(Integer payintentPeriod) {
		this.payintentPeriod = payintentPeriod;
	}
	public java.math.BigDecimal getManagementConsultingOfRate() {
		return managementConsultingOfRate;
	}
	public void setManagementConsultingOfRate(
			java.math.BigDecimal managementConsultingOfRate) {
		this.managementConsultingOfRate = managementConsultingOfRate;
	}
	public java.math.BigDecimal getFinanceServiceOfRate() {
		return financeServiceOfRate;
	}
	public void setFinanceServiceOfRate(java.math.BigDecimal financeServiceOfRate) {
		this.financeServiceOfRate = financeServiceOfRate;
	}
	public java.math.BigDecimal getContinuationRate() {
		return continuationRate;
	}
	public void setContinuationRate(java.math.BigDecimal continuationRate) {
		this.continuationRate = continuationRate;
	}
	public SlSuperviseRecord () {
		super();
	}
	
	public SlSuperviseRecord (
		 Long in_id
        ) {
		this.setId(in_id);
    }

	public Long getId() {
		return this.id;
	}
	
	/**
	 * Set the id
	 */	
	public void setId(Long aValue) {
		this.id = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="reason" type="java.lang.String" length="200" not-null="false" unique="false"
	 */
	public String getReason() {
		return this.reason;
	}
	
	/**
	 * Set the reason
	 */	
	public void setReason(String aValue) {
		this.reason = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="startDate" type="java.util.Date" length="10" not-null="false" unique="false"
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
	 * @hibernate.property column="endDate" type="java.util.Date" length="10" not-null="false" unique="false"
	 */
	public java.util.Date getEndDate() {
		return this.endDate;
	}
	
	/**
	 * Set the endDate
	 */	
	public void setEndDate(java.util.Date aValue) {
		this.endDate = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="projectId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getProjectId() {
		return this.projectId;
	}
	

	public Integer getIsPreposePayAccrualsupervise() {
		return isPreposePayAccrualsupervise;
	}
	public void setIsPreposePayAccrualsupervise(Integer isPreposePayAccrualsupervise) {
		this.isPreposePayAccrualsupervise = isPreposePayAccrualsupervise;
	}
	/**
	 * Set the projectId
	 */	
	public void setProjectId(Long aValue) {
		this.projectId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="remark" type="java.lang.String" length="200" not-null="false" unique="false"
	 */
	public String getRemark() {
		return this.remark;
	}
	
	/**
	 * Set the remark
	 */	
	public void setRemark(String aValue) {
		this.remark = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SlSuperviseRecord)) {
			return false;
		}
		SlSuperviseRecord rhs = (SlSuperviseRecord) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.reason, rhs.reason)
				.append(this.startDate, rhs.startDate)
				.append(this.endDate, rhs.endDate)
				.append(this.projectId, rhs.projectId)
				.append(this.remark, rhs.remark)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.reason) 
				.append(this.startDate) 
				.append(this.endDate) 
				.append(this.projectId) 
				.append(this.remark) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("reason", this.reason) 
				.append("startDate", this.startDate) 
				.append("endDate", this.endDate) 
				.append("projectId", this.projectId) 
				.append("remark", this.remark) 
				.toString();
	}
	public void setAppUserName(String appUserName) {
		AppUserName = appUserName;
	}
	public String getAppUserName() {
		return AppUserName;
	}



}
