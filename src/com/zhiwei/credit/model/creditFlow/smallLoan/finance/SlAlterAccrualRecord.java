package com.zhiwei.credit.model.creditFlow.smallLoan.finance;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
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
 * SlAlterAccrualRecord Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class SlAlterAccrualRecord extends com.zhiwei.core.model.BaseModel {

    protected Long slAlteraccrualRecordId;
	protected String reason;
	protected java.util.Date alelrtDate;
	protected java.math.BigDecimal alterProjectMoney;
	protected Long projectId;
	protected String dateMode;
	protected String accrualtype;
	protected java.math.BigDecimal accrual;
	protected String payaccrualType;
	protected Integer isPreposePayAccrual;
	protected java.math.BigDecimal managementConsultingOfRate;
	protected java.math.BigDecimal financeServiceOfRate;
	protected java.util.Date surplusEndDate;
	protected Integer payintentPeriod;

	 protected Integer alterpayintentPeriod; //还款期数
	protected String isStartDatePay; //是否按还款日还款,
	protected Integer payintentPerioDate; //每期还款日,
	protected Integer dayOfEveryPeriod; //自定义周期的天数,
	
	  protected Integer checkStatus;  //0代表未审核  1代表未提交审查  5 代表审核通过 6 代表未审核通过
	  protected Date opTime;//启动时间
	  protected String creator;//创建人
	  protected java.math.BigDecimal oriaccrual;//用于删除记录是找到项目的原始记录
	  
	  protected String baseBusinessType;//〓利率变更改动 add  by gao
	  
	  
	public String getBaseBusinessType() {
		return baseBusinessType;
	}

	public void setBaseBusinessType(String baseBusinessType) {
		this.baseBusinessType = baseBusinessType;
	}

	/**
	 * Default Empty Constructor for class SlAlterAccrualRecord
	 */
	public SlAlterAccrualRecord () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class SlAlterAccrualRecord
	 */
	public SlAlterAccrualRecord (
		 Long in_slAlteraccrualRecordId
        ) {
		this.setSlAlteraccrualRecordId(in_slAlteraccrualRecordId);
    }

    

	public java.math.BigDecimal getOriaccrual() {
		return oriaccrual;
	}

	public void setOriaccrual(java.math.BigDecimal oriaccrual) {
		this.oriaccrual = oriaccrual;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
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

	/**
	 * 	 * @return Long
     * @hibernate.id column="slAlteraccrualRecordId" type="java.lang.Long" generator-class="native"
	 */
	public Long getSlAlteraccrualRecordId() {
		return this.slAlteraccrualRecordId;
	}
	
	/**
	 * Set the slAlteraccrualRecordId
	 */	
	public void setSlAlteraccrualRecordId(Long aValue) {
		this.slAlteraccrualRecordId = aValue;
	}	

	public String getIsStartDatePay() {
		return isStartDatePay;
	}

	public void setIsStartDatePay(String isStartDatePay) {
		this.isStartDatePay = isStartDatePay;
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
	 * @hibernate.property column="alelrtDate" type="java.util.Date" length="10" not-null="false" unique="false"
	 */
	public java.util.Date getAlelrtDate() {
		return this.alelrtDate;
	}
	
	/**
	 * Set the alelrtDate
	 */	
	public void setAlelrtDate(java.util.Date aValue) {
		this.alelrtDate = aValue;
	}	



	public Integer getAlterpayintentPeriod() {
		return alterpayintentPeriod;
	}

	public void setAlterpayintentPeriod(Integer alterpayintentPeriod) {
		this.alterpayintentPeriod = alterpayintentPeriod;
	}

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="alterProjectMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getAlterProjectMoney() {
		return this.alterProjectMoney;
	}
	
	/**
	 * Set the alterProjectMoney
	 */	
	public void setAlterProjectMoney(java.math.BigDecimal aValue) {
		this.alterProjectMoney = aValue;
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
	 * 日期模型	 * @return String
	 * @hibernate.property column="dateMode" type="java.lang.String" length="30" not-null="false" unique="false"
	 */
	public String getDateMode() {
		return this.dateMode;
	}
	
	/**
	 * Set the dateMode
	 */	
	public void setDateMode(String aValue) {
		this.dateMode = aValue;
	}	

	/**
	 * 计息方式	 * @return String
	 * @hibernate.property column="accrualtype" type="java.lang.String" length="30" not-null="false" unique="false"
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
	 * 利率	 * @return java.math.BigDecimal
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
	 * 付息方式	 * @return String
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
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="managementConsultingOfRate" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getManagementConsultingOfRate() {
		return this.managementConsultingOfRate;
	}
	
	/**
	 * Set the managementConsultingOfRate
	 */	
	public void setManagementConsultingOfRate(java.math.BigDecimal aValue) {
		this.managementConsultingOfRate = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="financeServiceOfRate" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getFinanceServiceOfRate() {
		return this.financeServiceOfRate;
	}
	
	/**
	 * Set the financeServiceOfRate
	 */	
	public void setFinanceServiceOfRate(java.math.BigDecimal aValue) {
		this.financeServiceOfRate = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="surplusEndDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getSurplusEndDate() {
		return this.surplusEndDate;
	}
	
	/**
	 * Set the surplusEndDate
	 */	
	public void setSurplusEndDate(java.util.Date aValue) {
		this.surplusEndDate = aValue;
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
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SlAlterAccrualRecord)) {
			return false;
		}
		SlAlterAccrualRecord rhs = (SlAlterAccrualRecord) object;
		return new EqualsBuilder()
				.append(this.slAlteraccrualRecordId, rhs.slAlteraccrualRecordId)
				.append(this.reason, rhs.reason)
				.append(this.alelrtDate, rhs.alelrtDate)
				.append(this.alterProjectMoney, rhs.alterProjectMoney)
				.append(this.projectId, rhs.projectId)
				.append(this.dateMode, rhs.dateMode)
				.append(this.accrualtype, rhs.accrualtype)
				.append(this.accrual, rhs.accrual)
				.append(this.payaccrualType, rhs.payaccrualType)
				.append(this.isPreposePayAccrual, rhs.isPreposePayAccrual)
				.append(this.managementConsultingOfRate, rhs.managementConsultingOfRate)
				.append(this.financeServiceOfRate, rhs.financeServiceOfRate)
				.append(this.surplusEndDate, rhs.surplusEndDate)
				.append(this.payintentPeriod, rhs.payintentPeriod)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.slAlteraccrualRecordId) 
				.append(this.reason) 
				.append(this.alelrtDate) 
				.append(this.alterProjectMoney) 
				.append(this.projectId) 
				.append(this.dateMode) 
				.append(this.accrualtype) 
				.append(this.accrual) 
				.append(this.payaccrualType) 
				.append(this.isPreposePayAccrual) 
				.append(this.managementConsultingOfRate) 
				.append(this.financeServiceOfRate) 
				.append(this.surplusEndDate) 
				.append(this.payintentPeriod) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("slAlteraccrualRecordId", this.slAlteraccrualRecordId) 
				.append("reason", this.reason) 
				.append("alelrtDate", this.alelrtDate) 
				.append("alterProjectMoney", this.alterProjectMoney) 
				.append("projectId", this.projectId) 
				.append("dateMode", this.dateMode) 
				.append("accrualtype", this.accrualtype) 
				.append("accrual", this.accrual) 
				.append("payaccrualType", this.payaccrualType) 
				.append("isPreposePayAccrual", this.isPreposePayAccrual) 
				.append("managementConsultingOfRate", this.managementConsultingOfRate) 
				.append("financeServiceOfRate", this.financeServiceOfRate) 
				.append("surplusEndDate", this.surplusEndDate) 
				.append("payintentPeriod", this.payintentPeriod) 
				.toString();
	}



}
