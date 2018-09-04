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
 * SlFundDetail Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 资金明细表
 */
public class SlFundDetail extends com.zhiwei.core.model.BaseModel {

    protected Long fundDetailId;
//	protected Long fundIntentId;
//	protected Long fundQlideId;
    protected SlFundIntent slFundIntent;
    protected SlFundQlide slFundQlide;
	

	protected Long overdueNum;
	protected java.math.BigDecimal overdueAccrual; //罚息金额
	protected java.util.Date operTime;   //操作时间
	protected java.math.BigDecimal afterMoney;  //本次对账金额
	protected java.util.Date factDate;
	protected String transactionType;
	protected java.lang.Short iscancel; //是否撤销，1表示撤销，null正常
	protected String cancelremark;
	protected String premark;//平账备注
	
	protected String checkuser;
	
	
	@Expose
	protected String intentFundType;
	protected java.math.BigDecimal overdueRate;
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
	public String getPremark() {
		return premark;
	}

	public void setPremark(String premark) {
		this.premark = premark;
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

	public String getCheckuser() {
		return checkuser;
	}

	public void setCheckuser(String checkuser) {
		this.checkuser = checkuser;
	}

	public String getIntentFundType() {
		return intentFundType;
	}

	public void setIntentFundType(String intentFundType) {
		this.intentFundType = intentFundType;
	}

	/**
	 * Default Empty Constructor for class SlFundDetail
	 */
	public SlFundDetail () {
		super();
	}
	
	

	public String getCancelremark() {
		return cancelremark;
	}

	public void setCancelremark(String cancelremark) {
		this.cancelremark = cancelremark;
	}

	public java.math.BigDecimal getOverdueRate() {
		return overdueRate;
	}

	public void setOverdueRate(java.math.BigDecimal overdueRate) {
		this.overdueRate = overdueRate;
	}

	/**
	 * Default Key Fields Constructor for class SlFundDetail
	 */
	public SlFundDetail (
		 Long in_fundDetailId
        ) {
		this.setFundDetailId(in_fundDetailId);
    }

    

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	/**
	 * id号	 * @return Long
     * @hibernate.id column="fundDetailId" type="java.lang.Long" generator-class="native"
	 */
	public Long getFundDetailId() {
		return this.fundDetailId;
	}
	
	/**
	 * Set the fundDetailId
	 */	
	public void setFundDetailId(Long aValue) {
		this.fundDetailId = aValue;
	}	

	/**
	
	

	/**
	 * 逾期天数	 * @return Short
	 * @hibernate.property column="overdueNum" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
		

	/**
	 * 逾期利息	 * @return java.math.BigDecimal
	 * @hibernate.property column="overdueAccrual" type="java.math.BigDecimal" length="10" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getOverdueAccrual() {
		return this.overdueAccrual;
	}
	
	public Long getOverdueNum() {
		return overdueNum;
	}

	public void setOverdueNum(Long overdueNum) {
		this.overdueNum = overdueNum;
	}
	public SlFundIntent getSlFundIntent() {
		return slFundIntent;
	}

	public void setSlFundIntent(SlFundIntent slFundIntent) {
		this.slFundIntent = slFundIntent;
	}

	public SlFundQlide getSlFundQlide() {
		return slFundQlide;
	}

	public void setSlFundQlide(SlFundQlide slFundQlide) {
		this.slFundQlide = slFundQlide;
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
		if (!(object instanceof SlFundDetail)) {
			return false;
		}
		SlFundDetail rhs = (SlFundDetail) object;
		return new EqualsBuilder()
				.append(this.fundDetailId, rhs.fundDetailId)
			
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
				.append(this.fundDetailId) 
			
				.append(this.overdueNum) 
				.append(this.overdueAccrual) 
				.append(this.operTime) 
				.append(this.afterMoney) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("fundDetailId", this.fundDetailId) 
			
				.append("overdueNum", this.overdueNum) 
				.append("overdueAccrual", this.overdueAccrual) 
				.append("operTime", this.operTime) 
				.append("afterMoney", this.afterMoney) 
				.toString();
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



}
