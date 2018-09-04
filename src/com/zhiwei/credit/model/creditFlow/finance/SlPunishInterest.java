package com.zhiwei.credit.model.creditFlow.finance;
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
 * SlPunishInterest Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class SlPunishInterest extends com.zhiwei.core.model.BaseModel {

    protected Long punishInterestId;
	protected Long projectId;
	protected java.util.Date intentDate;
	protected java.math.BigDecimal payMoney;
	protected java.math.BigDecimal incomeMoney;
	protected java.util.Date factDate;
	protected java.math.BigDecimal afterMoney;
	protected java.math.BigDecimal notMoney;
	protected String businessType;
	protected Long fundIntentId;
	protected String fundType;
	protected String premark;
	protected Integer punishDays;
    
	protected Integer continueDates ;//顺延天数

	protected BigDecimal amountPayable ; //罚息金额 =罚息总额（accrualMoney）-顺延息（continueInterest）
	
	protected BigDecimal continueInterest ; //顺延息
	
	public String getPremark() {
		return premark;
	}

	public void setPremark(String premark) {
		this.premark = premark;
	}

	protected String continueDay ;//顺延至 几号
	protected String graceDay ;//宽限至 几号
	
	protected BigDecimal flatMoney;//平账金额
	
	
	public BigDecimal getFlatMoney() {
		return flatMoney;
	}

	public void setFlatMoney(BigDecimal flatMoney) {
		this.flatMoney = flatMoney;
	}

	public String getContinueDay() {
		return continueDay;
	}

	public void setContinueDay(String continueDay) {
		this.continueDay = continueDay;
	}

	public String getGraceDay() {
		return graceDay;
	}

	public void setGraceDay(String graceDay) {
		this.graceDay = graceDay;
	}

	public Integer getContinueDates() {
		return continueDates;
	}

	public void setContinueDates(Integer continueDates) {
		this.continueDates = continueDates;
	}

	public BigDecimal getAmountPayable() {
		return amountPayable;
	}

	public void setAmountPayable(BigDecimal amountPayable) {
		this.amountPayable = amountPayable;
	}

	public BigDecimal getContinueInterest() {
		return continueInterest;
	}

	public void setContinueInterest(BigDecimal continueInterest) {
		this.continueInterest = continueInterest;
	}

	protected 	BigDecimal PayInMoney;


	/**
	 * Default Empty Constructor for class SlPunishInterest
	 */
	public SlPunishInterest () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class SlPunishInterest
	 */
	public SlPunishInterest (
		 Long in_punishInterestId
        ) {
		this.setPunishInterestId(in_punishInterestId);
    }

    

	public Integer getPunishDays() {
		return punishDays;
	}

	public void setPunishDays(Integer punishDays) {
		this.punishDays = punishDays;
	}

	public BigDecimal getPayInMoney() {
		return PayInMoney;
	}

	public void setPayInMoney(BigDecimal payInMoney) {
		PayInMoney = payInMoney;
	}

	/**
	 * 	 * @return Long
     * @hibernate.id column="punishInterestId" type="java.lang.Long" generator-class="native"
	 */
	public Long getPunishInterestId() {
		return this.punishInterestId;
	}
	
	/**
	 * Set the punishInterestId
	 */	
	public void setPunishInterestId(Long aValue) {
		this.punishInterestId = aValue;
	}	

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getFundType() {
		return fundType;
	}

	public void setFundType(String fundType) {
		this.fundType = fundType;
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

	public Long getFundIntentId() {
		return fundIntentId;
	}

	public void setFundIntentId(Long fundIntentId) {
		this.fundIntentId = fundIntentId;
	}

	/**
	 * è®¡åˆ’åˆ°å¸æ—¥	 * @return java.util.Date
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
	 * 支出金额	 * @return java.math.BigDecimal
	 * @hibernate.property column="payMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getPayMoney() {
		return this.payMoney;
	}
	
	/**
	 * Set the payMoney
	 */	
	public void setPayMoney(java.math.BigDecimal aValue) {
		this.payMoney = aValue;
	}	

	/**
	 * 收入金额	 * @return java.math.BigDecimal
	 * @hibernate.property column="incomeMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getIncomeMoney() {
		return this.incomeMoney;
	}
	
	/**
	 * Set the incomeMoney
	 */	
	public void setIncomeMoney(java.math.BigDecimal aValue) {
		this.incomeMoney = aValue;
	}	

	/**
	 * 实际到帐日/实际还款日	 * @return java.util.Date
	 * @hibernate.property column="factDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getFactDate() {
		return this.factDate;
	}
	
	/**
	 * Set the factDate
	 */	
	public void setFactDate(java.util.Date aValue) {
		this.factDate = aValue;
	}	

	/**
	 * 已对帐金额	 * @return java.math.BigDecimal
	 * @hibernate.property column="afterMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getAfterMoney() {
		return this.afterMoney;
	}
	
	/**
	 * Set the afterMoney
	 */	
	public void setAfterMoney(java.math.BigDecimal aValue) {
		this.afterMoney = aValue;
	}	

	/**
	 * 未对帐金额	 * @return java.math.BigDecimal
	 * @hibernate.property column="notMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getNotMoney() {
		return this.notMoney;
	}
	
	/**
	 * Set the notMoney
	 */	
	public void setNotMoney(java.math.BigDecimal aValue) {
		this.notMoney = aValue;
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
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SlPunishInterest)) {
			return false;
		}
		SlPunishInterest rhs = (SlPunishInterest) object;
		return new EqualsBuilder()
				.append(this.punishInterestId, rhs.punishInterestId)
				.append(this.projectId, rhs.projectId)
				.append(this.intentDate, rhs.intentDate)
				.append(this.payMoney, rhs.payMoney)
				.append(this.incomeMoney, rhs.incomeMoney)
				.append(this.factDate, rhs.factDate)
				.append(this.afterMoney, rhs.afterMoney)
				.append(this.notMoney, rhs.notMoney)
				.append(this.businessType, rhs.businessType)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.punishInterestId) 
				.append(this.projectId) 
				.append(this.intentDate) 
				.append(this.payMoney) 
				.append(this.incomeMoney) 
				.append(this.factDate) 
				.append(this.afterMoney) 
				.append(this.notMoney) 
				.append(this.businessType) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("punishInterestId", this.punishInterestId) 
				.append("projectId", this.projectId) 
				.append("intentDate", this.intentDate) 
				.append("payMoney", this.payMoney) 
				.append("incomeMoney", this.incomeMoney) 
				.append("factDate", this.factDate) 
				.append("afterMoney", this.afterMoney) 
				.append("notMoney", this.notMoney) 
				.append("businessType", this.businessType) 
				.toString();
	}



}
