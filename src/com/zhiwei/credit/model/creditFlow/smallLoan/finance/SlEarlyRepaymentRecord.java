package com.zhiwei.credit.model.creditFlow.smallLoan.finance;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * 
 * @author 
 *
 */
/**
 * SlEarlyRepaymentRecord Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class SlEarlyRepaymentRecord extends com.zhiwei.core.model.BaseModel {
	/**
	 * 主键id
	 */
    protected Long slEarlyRepaymentId;
	/**
	 * 借款项目主键id,  bp_fund_project的id
	 */
    protected Long projectId;
	/**
	 * 提前还款原因（貌似现在系统没有用）
	 */
	protected String reason;
	/**
	 * 提前还款时间
	 */
	protected java.util.Date earlyDate;
	/**
	 * 
	 */
	protected java.util.Date surplusEndDate;
	/**
	 * 提前还款金额
	 */
	protected java.math.BigDecimal earlyProjectMoney;
	/**
	 * 是否按还款日还款,
	 */
	protected String isStartDatePay; 
	/**
	 * 每期还款日,
	 */
	protected Integer payintentPerioDate; 
	/**
	 * 自定义周期的天数,
	 */
	protected Integer dayOfEveryPeriod; 
	/**
	 * 0代表未审核  1代表未提交审查  5 代表审核通过 6 代表未审核通过
	 */
	  protected Integer checkStatus;  
		/**
		 * 启动时间
		 */
	  protected Date opTime;
		/**
		 * 日期模型
		 */
	protected String dateMode;  
	/**
	 * 还款期数
	 */
	protected Integer payintentPeriod; 
	/**
	 * 计息方式
	 */
	protected String accrualtype;  
	/**
	 * 付息方式
	 */
	protected String payaccrualType;   
	/**
	 * 是否前置付息 0否 	1 是
	 */
	protected Integer isPreposePayAccrual; 
	/**
	 * 贷款利率
	 */
	protected java.math.BigDecimal accrual; 
	/**
	 * 管理咨询费率
	 */
	 protected java.math.BigDecimal managementConsultingOfRate; 
		/**
		 * 财务服务费率
		 */
    protected java.math.BigDecimal financeServiceOfRate; 

	/**
	 * 还款期数
	 */
    protected Integer prepayintentPeriod; 
	/**
	 * 剩余本金金额
	 */
    protected java.math.BigDecimal surplusProjectMoney;
	/**
	 * 创建者名称
	 */
    protected String creator;
	/**
	 * 业务种类
	 */
    protected String businessType;
	/**
	 * 招标计划的Id
	 */
    protected Long bidPlanId;
	/**
	 * 补偿息金额
	 */
    protected BigDecimal penaltyMoney;
	/**
	 * 补偿天数
	 */
    protected Integer penaltyDays;
    
    
	public Long getBidPlanId() {
		return bidPlanId;
	}

	public void setBidPlanId(Long bidPlanId) {
		this.bidPlanId = bidPlanId;
	}

	public BigDecimal getPenaltyMoney() {
		return penaltyMoney;
	}

	public void setPenaltyMoney(BigDecimal penaltyMoney) {
		this.penaltyMoney = penaltyMoney;
	}

	public Integer getPenaltyDays() {
		return penaltyDays;
	}

	public void setPenaltyDays(Integer penaltyDays) {
		this.penaltyDays = penaltyDays;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	/**
	 * Default Empty Constructor for class SlEarlyRepaymentRecord
	 */
	public SlEarlyRepaymentRecord () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class SlEarlyRepaymentRecord
	 */
	public SlEarlyRepaymentRecord (
		 Long in_slEarlyRepaymentId
        ) {
		this.setSlEarlyRepaymentId(in_slEarlyRepaymentId);
    }

    

	public Date getOpTime() {
		return opTime;
	}

	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}

	public Integer getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

	public java.util.Date getSurplusEndDate() {
		return surplusEndDate;
	}

	public void setSurplusEndDate(java.util.Date surplusEndDate) {
		this.surplusEndDate = surplusEndDate;
	}

	public Integer getDayOfEveryPeriod() {
		return dayOfEveryPeriod;
	}

	public void setDayOfEveryPeriod(Integer dayOfEveryPeriod) {
		this.dayOfEveryPeriod = dayOfEveryPeriod;
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

	public String getDateMode() {
		return dateMode;
	}

	public void setDateMode(String dateMode) {
		this.dateMode = dateMode;
	}

	public Integer getPrepayintentPeriod() {
		return prepayintentPeriod;
	}

	public void setPrepayintentPeriod(Integer prepayintentPeriod) {
		this.prepayintentPeriod = prepayintentPeriod;
	}

	public Integer getPayintentPeriod() {
		return payintentPeriod;
	}

	public void setPayintentPeriod(Integer payintentPeriod) {
		this.payintentPeriod = payintentPeriod;
	}

	public String getAccrualtype() {
		return accrualtype;
	}

	public void setAccrualtype(String accrualtype) {
		this.accrualtype = accrualtype;
	}

	public String getPayaccrualType() {
		return payaccrualType;
	}

	public void setPayaccrualType(String payaccrualType) {
		this.payaccrualType = payaccrualType;
	}

	public Integer getIsPreposePayAccrual() {
		return isPreposePayAccrual;
	}

	public void setIsPreposePayAccrual(Integer isPreposePayAccrual) {
		this.isPreposePayAccrual = isPreposePayAccrual;
	}

	public java.math.BigDecimal getAccrual() {
		return accrual;
	}

	public void setAccrual(java.math.BigDecimal accrual) {
		this.accrual = accrual;
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

	/**
	 * 	 * @return Long
     * @hibernate.id column="slEarlyRepaymentId" type="java.lang.Long" generator-class="native"
	 */
	public Long getSlEarlyRepaymentId() {
		return this.slEarlyRepaymentId;
	}
	
	/**
	 * Set the slEarlyRepaymentId
	 */	
	public void setSlEarlyRepaymentId(Long aValue) {
		this.slEarlyRepaymentId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="reason" type="java.lang.String" length="65535" not-null="false" unique="false"
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
	 * @hibernate.property column="earlyDate" type="java.util.Date" length="10" not-null="false" unique="false"
	 */
	public java.util.Date getEarlyDate() {
		return this.earlyDate;
	}
	
	/**
	 * Set the earlyDate
	 */	
	public void setEarlyDate(java.util.Date aValue) {
		this.earlyDate = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="earlyProjectMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getEarlyProjectMoney() {
		return this.earlyProjectMoney;
	}
	
	/**
	 * Set the earlyProjectMoney
	 */	
	public void setEarlyProjectMoney(java.math.BigDecimal aValue) {
		this.earlyProjectMoney = aValue;
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

	public java.math.BigDecimal getSurplusProjectMoney() {
		return surplusProjectMoney;
	}

	public void setSurplusProjectMoney(java.math.BigDecimal surplusProjectMoney) {
		this.surplusProjectMoney = surplusProjectMoney;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SlEarlyRepaymentRecord)) {
			return false;
		}
		SlEarlyRepaymentRecord rhs = (SlEarlyRepaymentRecord) object;
		return new EqualsBuilder()
				.append(this.slEarlyRepaymentId, rhs.slEarlyRepaymentId)
				.append(this.reason, rhs.reason)
				.append(this.earlyDate, rhs.earlyDate)
				.append(this.earlyProjectMoney, rhs.earlyProjectMoney)
				.append(this.projectId, rhs.projectId)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.slEarlyRepaymentId) 
				.append(this.reason) 
				.append(this.earlyDate) 
				.append(this.earlyProjectMoney) 
				.append(this.projectId) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("slEarlyRepaymentId", this.slEarlyRepaymentId) 
				.append("reason", this.reason) 
				.append("earlyDate", this.earlyDate) 
				.append("earlyProjectMoney", this.earlyProjectMoney) 
				.append("projectId", this.projectId) 
				.toString();
	}



}
