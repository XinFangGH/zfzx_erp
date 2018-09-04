package com.zhiwei.credit.model.creditFlow.finance;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

import com.google.gson.annotations.Expose;

/**
 * 
 * @author 
 *
 */
/**
 * SlChargeDetail Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class SlChargeDetail extends com.zhiwei.core.model.BaseModel {

    protected Long chargeDetailId;
//	protected Long chargeIntentId;
//	protected Long fundQlideId;
    protected SlActualToCharge slActualToCharge;
    protected SlFundQlide slFundQlide;
	protected Long overdueNum;
	protected java.math.BigDecimal overdueAccrual;
	protected java.util.Date operTime;
	protected java.math.BigDecimal afterMoney;
	protected java.util.Date factDate;
	protected String transactionType;
	protected java.lang.Short iscancel;
	protected String cancelremark;
	protected String checkuser;

	
	
	
	
	@Expose
	protected String glideNum;
	@Expose
	protected String qlidemyAccount;
	@Expose
	protected java.math.BigDecimal qlidepayMoney;
	@Expose
	protected java.math.BigDecimal qlideincomeMoney;
	@Expose
	protected java.math.BigDecimal qlidedialMoney;
	@Expose
	protected java.math.BigDecimal qlideafterMoney;
	@Expose
	protected java.math.BigDecimal qlidenotMoney;
	@Expose
	protected java.util.Date qlidefactDate;
	@Expose
	protected String qlideopAccount;
	@Expose
	protected String qlide;
	@Expose
	protected String qlideopOpenName;
	@Expose
	protected String qlidecurrency;
	@Expose
	protected String qlidetransactionType;
	@Expose
	protected Short qlidefundType;
	/**
	 * Default Empty Constructor for class SlChargeDetail
	 */
	public SlChargeDetail () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class SlChargeDetail
	 */
	public SlChargeDetail (
		 Long in_chargeDetailId
        ) {
		this.setChargeDetailId(in_chargeDetailId);
    }

    

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public java.util.Date getFactDate() {
		return factDate;
	}

	public void setFactDate(java.util.Date factDate) {
		this.factDate = factDate;
	}

	public java.lang.Short getIscancel() {
		return iscancel;
	}

	public void setIscancel(java.lang.Short iscancel) {
		this.iscancel = iscancel;
	}

	public String getCancelremark() {
		return cancelremark;
	}

	public void setCancelremark(String cancelremark) {
		this.cancelremark = cancelremark;
	}

	public String getCheckuser() {
		return checkuser;
	}

	public void setCheckuser(String checkuser) {
		this.checkuser = checkuser;
	}

	/**
	 * id号	 * @return Long
     * @hibernate.id column="chargeDetailId" type="java.lang.Long" generator-class="native"
	 */
	public Long getChargeDetailId() {
		return this.chargeDetailId;
	}
	
	/**
	 * Set the chargeDetailId
	 */	
	public void setChargeDetailId(Long aValue) {
		this.chargeDetailId = aValue;
	}	

	/**
	 * 款项表id	 * @return Long
	 * @hibernate.property column="fundIntentId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	
	/**
	 * 逾期天数	 * @return Long
	 * @hibernate.property column="overdueNum" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getOverdueNum() {
		return this.overdueNum;
	}
	

	public SlActualToCharge getSlActualToCharge() {
		return slActualToCharge;
	}

	public void setSlActualToCharge(SlActualToCharge slActualToCharge) {
		this.slActualToCharge = slActualToCharge;
	}

	public SlFundQlide getSlFundQlide() {
		return slFundQlide;
	}

	public void setSlFundQlide(SlFundQlide slFundQlide) {
		this.slFundQlide = slFundQlide;
	}

	/**
	 * Set the overdueNum
	 */	
	public void setOverdueNum(Long aValue) {
		this.overdueNum = aValue;
	}	

	/**
	 * 逾期利息	 * @return java.math.BigDecimal
	 * @hibernate.property column="overdueAccrual" type="java.math.BigDecimal" length="10" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getOverdueAccrual() {
		return this.overdueAccrual;
	}
	
	/**
	 * Set the overdueAccrual
	 */	
	public void setOverdueAccrual(java.math.BigDecimal aValue) {
		this.overdueAccrual = aValue;
	}	

	/**
	 * 操作时间	 * @return java.util.Date
	 * @hibernate.property column="operTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getOperTime() {
		return this.operTime;
	}
	
	/**
	 * Set the operTime
	 */	
	public void setOperTime(java.util.Date aValue) {
		this.operTime = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="afterMoney" type="java.math.BigDecimal" length="10" not-null="false" unique="false"
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
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SlChargeDetail)) {
			return false;
		}
		SlChargeDetail rhs = (SlChargeDetail) object;
		return new EqualsBuilder()
				.append(this.chargeDetailId, rhs.chargeDetailId)
				.append(this.overdueNum, rhs.overdueNum)
				.append(this.overdueAccrual, rhs.overdueAccrual)
				.append(this.operTime, rhs.operTime)
				.append(this.afterMoney, rhs.afterMoney)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.chargeDetailId) 
				.append(this.overdueNum) 
				.append(this.overdueAccrual) 
				.append(this.operTime) 
				.append(this.afterMoney) 
				.toHashCode();
	}

	public String getGlideNum() {
		return glideNum;
	}

	public void setGlideNum(String glideNum) {
		this.glideNum = glideNum;
	}

	public String getQlidemyAccount() {
		return qlidemyAccount;
	}

	public void setQlidemyAccount(String qlidemyAccount) {
		this.qlidemyAccount = qlidemyAccount;
	}

	public java.math.BigDecimal getQlidepayMoney() {
		return qlidepayMoney;
	}

	public void setQlidepayMoney(java.math.BigDecimal qlidepayMoney) {
		this.qlidepayMoney = qlidepayMoney;
	}

	public java.math.BigDecimal getQlideincomeMoney() {
		return qlideincomeMoney;
	}

	public void setQlideincomeMoney(java.math.BigDecimal qlideincomeMoney) {
		this.qlideincomeMoney = qlideincomeMoney;
	}

	public java.math.BigDecimal getQlidedialMoney() {
		return qlidedialMoney;
	}

	public void setQlidedialMoney(java.math.BigDecimal qlidedialMoney) {
		this.qlidedialMoney = qlidedialMoney;
	}

	public java.math.BigDecimal getQlideafterMoney() {
		return qlideafterMoney;
	}

	public void setQlideafterMoney(java.math.BigDecimal qlideafterMoney) {
		this.qlideafterMoney = qlideafterMoney;
	}

	public java.math.BigDecimal getQlidenotMoney() {
		return qlidenotMoney;
	}

	public void setQlidenotMoney(java.math.BigDecimal qlidenotMoney) {
		this.qlidenotMoney = qlidenotMoney;
	}

	public java.util.Date getQlidefactDate() {
		return qlidefactDate;
	}

	public void setQlidefactDate(java.util.Date qlidefactDate) {
		this.qlidefactDate = qlidefactDate;
	}

	public String getQlideopAccount() {
		return qlideopAccount;
	}

	public void setQlideopAccount(String qlideopAccount) {
		this.qlideopAccount = qlideopAccount;
	}

	public String getQlide() {
		return qlide;
	}

	public void setQlide(String qlide) {
		this.qlide = qlide;
	}

	public String getQlideopOpenName() {
		return qlideopOpenName;
	}

	public void setQlideopOpenName(String qlideopOpenName) {
		this.qlideopOpenName = qlideopOpenName;
	}

	public String getQlidecurrency() {
		return qlidecurrency;
	}

	public void setQlidecurrency(String qlidecurrency) {
		this.qlidecurrency = qlidecurrency;
	}

	public String getQlidetransactionType() {
		return qlidetransactionType;
	}

	public void setQlidetransactionType(String qlidetransactionType) {
		this.qlidetransactionType = qlidetransactionType;
	}

	public Short getQlidefundType() {
		return qlidefundType;
	}

	public void setQlidefundType(Short qlidefundType) {
		this.qlidefundType = qlidefundType;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("chargeDetailId", this.chargeDetailId) 
				.append("overdueNum", this.overdueNum) 
				.append("overdueAccrual", this.overdueAccrual) 
				.append("operTime", this.operTime) 
				.append("afterMoney", this.afterMoney) 
				.toString();
	}



}
