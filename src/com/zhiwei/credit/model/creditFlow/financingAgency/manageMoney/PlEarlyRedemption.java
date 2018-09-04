package com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
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
 * PlEarlyRedemption Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class PlEarlyRedemption extends com.zhiwei.core.model.BaseModel {

	/**
	 * 主键
	 */
    protected Long earlyRedemptionId;
    /**
	 * 创建者
	 */
	protected String creator;
	/**
	 * 创建日期
	 */
	protected java.util.Date createDate;
	/**
	 * 提前赎回日期
	 */
	protected java.util.Date earlyDate;
	/**
	 * 提前赎回金额
	 */
	protected java.math.BigDecimal earlyMoney;
	/**
	 * 提前赎回金额
	 */
	protected java.math.BigDecimal residualPrincipal;
	/**
	 * 罚息天数
	 */
	protected Integer penaltyDays;
	/**
	 * 提起赎回违约金比例
	 */
	protected java.math.BigDecimal liquidatedDamagesRate;
	/**
	 * 违约金
	 */
	protected java.math.BigDecimal liquidatedDamages;
	/**
	 * 状态
	 * 0:办理中，3:已终止,1:已通过
	 */
	protected Short checkStatus;
	/**
	 * 类型
	 * mmplan：理财计划，mmproduce：理财产品，UPlan：U计划
	 */
	protected String keystr;
	/**
	 * 外键
	 * 订单orderId
	 */
	protected PlManageMoneyPlanBuyinfo plManageMoneyPlanBuyinfo;
	 /**
     * 用来标注转账成功情况（1：资金已经从出款人账户成功转到了标的账户,2;已经还款完成）
     * 主要针对于联动优势对于派息款要调两次接口 
     */
	protected Integer loanerRepayMentStatus;
	/**
	 * 交易流水号（资金从出款人账户转到标的账户流水号）
	 * 
	 */
	protected String loanerRequestNo;
	/**
	 * 交易流水号（给投资人转账流水号）
	 * 
	 */
	protected String investRequestNo;
	/**
	 * 实际交易总金额
	 */
	protected BigDecimal realTranseactionAmount;
	/**
	 * Default Empty Constructor for class PlEarlyRedemption
	 */
	public PlEarlyRedemption () {
		super();
	}
	
	
	
	
	public String getKeystr() {
		return keystr;
	}

	public void setKeystr(String keystr) {
		this.keystr = keystr;
	}

	public PlManageMoneyPlanBuyinfo getPlManageMoneyPlanBuyinfo() {
		return plManageMoneyPlanBuyinfo;
	}

	public void setPlManageMoneyPlanBuyinfo(
			PlManageMoneyPlanBuyinfo plManageMoneyPlanBuyinfo) {
		this.plManageMoneyPlanBuyinfo = plManageMoneyPlanBuyinfo;
	}

	/**
	 * Default Key Fields Constructor for class PlEarlyRedemption
	 */
	public PlEarlyRedemption (
		 Long in_earlyRedemption
        ) {
		this.setEarlyRedemptionId(in_earlyRedemption);
    }

    

	public Short getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Short checkStatus) {
		this.checkStatus = checkStatus;
	}

	/**
	 * 	 * @return Long
     * @hibernate.id column="earlyRedemption" type="java.lang.Long" generator-class="native"
	 */
	public Long getEarlyRedemptionId() {
		return this.earlyRedemptionId;
	}
	
	/**
	 * Set the earlyRedemption
	 */	
	public void setEarlyRedemptionId(Long aValue) {
		this.earlyRedemptionId = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="orderId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */

	/**
	 * 	 * @return String
	 * @hibernate.property column="creator" type="java.lang.String" length="100" not-null="false" unique="false"
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
	 * 	 * @return java.util.Date
	 * @hibernate.property column="earlyDate" type="java.util.Date" length="19" not-null="false" unique="false"
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
	 * 提前赎回本金	 * @return java.math.BigDecimal
	 * @hibernate.property column="earlyMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getEarlyMoney() {
		return this.earlyMoney;
	}
	
	/**
	 * Set the earlyMoney
	 */	
	public void setEarlyMoney(java.math.BigDecimal aValue) {
		this.earlyMoney = aValue;
	}	

	/**
	 * 剩余本金	 * @return java.math.BigDecimal
	 * @hibernate.property column="residualPrincipal" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getResidualPrincipal() {
		return this.residualPrincipal;
	}
	
	/**
	 * Set the residualPrincipal
	 */	
	public void setResidualPrincipal(java.math.BigDecimal aValue) {
		this.residualPrincipal = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="penaltyDays" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getPenaltyDays() {
		return this.penaltyDays;
	}
	
	/**
	 * Set the penaltyDays
	 */	
	public void setPenaltyDays(Integer aValue) {
		this.penaltyDays = aValue;
	}	

	/**
	 * 提前赎回违约金比例	 * @return java.math.BigDecimal
	 * @hibernate.property column="liquidatedDamagesRate" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getLiquidatedDamagesRate() {
		return this.liquidatedDamagesRate;
	}
	
	/**
	 * Set the liquidatedDamagesRate
	 */	
	public void setLiquidatedDamagesRate(java.math.BigDecimal aValue) {
		this.liquidatedDamagesRate = aValue;
	}	

	/**
	 * 违约金	 * @return java.math.BigDecimal
	 * @hibernate.property column="liquidatedDamages" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getLiquidatedDamages() {
		return this.liquidatedDamages;
	}
	
	/**
	 * Set the liquidatedDamages
	 */	
	public void setLiquidatedDamages(java.math.BigDecimal aValue) {
		this.liquidatedDamages = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof PlEarlyRedemption)) {
			return false;
		}
		PlEarlyRedemption rhs = (PlEarlyRedemption) object;
		return new EqualsBuilder()
				.append(this.earlyRedemptionId, rhs.earlyRedemptionId)
				.append(this.creator, rhs.creator)
				.append(this.createDate, rhs.createDate)
				.append(this.earlyDate, rhs.earlyDate)
				.append(this.earlyMoney, rhs.earlyMoney)
				.append(this.residualPrincipal, rhs.residualPrincipal)
				.append(this.penaltyDays, rhs.penaltyDays)
				.append(this.liquidatedDamagesRate, rhs.liquidatedDamagesRate)
				.append(this.liquidatedDamages, rhs.liquidatedDamages)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.earlyRedemptionId) 
				.append(this.creator) 
				.append(this.createDate) 
				.append(this.earlyDate) 
				.append(this.earlyMoney) 
				.append(this.residualPrincipal) 
				.append(this.penaltyDays) 
				.append(this.liquidatedDamagesRate) 
				.append(this.liquidatedDamages) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("earlyRedemptionId", this.earlyRedemptionId) 
				.append("creator", this.creator) 
				.append("createDate", this.createDate) 
				.append("earlyDate", this.earlyDate) 
				.append("earlyMoney", this.earlyMoney) 
				.append("residualPrincipal", this.residualPrincipal) 
				.append("penaltyDays", this.penaltyDays) 
				.append("liquidatedDamagesRate", this.liquidatedDamagesRate) 
				.append("liquidatedDamages", this.liquidatedDamages) 
				.toString();
	}

	public String getInvestRequestNo() {
		return investRequestNo;
	}

	public void setInvestRequestNo(String investRequestNo) {
		this.investRequestNo = investRequestNo;
	}

	public Integer getLoanerRepayMentStatus() {
		return loanerRepayMentStatus;
	}

	public void setLoanerRepayMentStatus(Integer loanerRepayMentStatus) {
		this.loanerRepayMentStatus = loanerRepayMentStatus;
	}

	public String getLoanerRequestNo() {
		return loanerRequestNo;
	}

	public void setLoanerRequestNo(String loanerRequestNo) {
		this.loanerRequestNo = loanerRequestNo;
	}




	public BigDecimal getRealTranseactionAmount() {
		return realTranseactionAmount;
	}




	public void setRealTranseactionAmount(BigDecimal realTranseactionAmount) {
		this.realTranseactionAmount = realTranseactionAmount;
	}


}
