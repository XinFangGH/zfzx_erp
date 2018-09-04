package com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
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
 * PlMmOrderAssignInterest Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 派息表
 */
public class PlMmOrderAssignInterest extends com.zhiwei.core.model.BaseModel {

	/**
	 * 主键
	 */
    protected Long assignInterestId;
    /**
	 * 订单Id
	 */
	protected Long orderId;
	/**
	 * 投资人Id
	 */
	protected Long investPersonId;
	/**
	 * 投资人姓名
	 */
	protected String investPersonName;
	/**
	 * 理财产品Id
	 */
	protected Long mmplanId;
	/**
	 * 理财产品名称
	 */
	protected String mmName;
	/**
	 * 资金类型
	 * loanInterest：利息，riskRate：风险保证金，liquidatedDamages：提前赎回违约金
	 * principalRepayment：本金，couponInterest：优惠券利息奖励，principalCoupons：优惠券返现奖励
	 * subjoinInterest：加息券加息奖励 ，commoninterest：普通加息，raiseinterest：募集期奖励
	 */
	protected String fundType;
	/**
	 * 期数
	 */
	protected Integer periods;
	/**
	 * 是否锁定
	 * 1：锁定
	 */
	protected String lockType;
	/**
	 * 收入金额
	 */
	protected java.math.BigDecimal incomeMoney;
	/**
	 * 支出金额
	 */
	protected java.math.BigDecimal payMoney;
	/**
	 * 计划到账日
	 */
	protected java.util.Date intentDate;
	/**
	 * 实际到账日
	 */
	protected java.util.Date factDate;
	/**
	 * 已派金额
	 */
	protected java.math.BigDecimal afterMoney;
	/**
	 * 类型
	 * mmplan：D计划，mmproduce：理财产品，experience：体验标,UPlan：U计划
	 */
	protected String keystr;
	/**
	 * 是否有效
	 * 0：有效，1：无效
	 */
	protected Short isValid;
	/**
	 * 是否显示
	 * 0：显示，1：不显示
	 */
	protected Short isCheck;
	/**
	 * 提前赎回Id
	 */
	protected Long earlyRedemptionId;
	 /**
     * 用来标注还款成功情况（1：资金已经从出款人账户成功转到了标的账户,2;已经还款完成）
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
	 * 第三方交易流水号（针对放款，还款，派息，操作记录字段）
	 */
	protected String requestNo;
	/**
	 * 最新请求第三方交易时间
	 */
	protected Date requestDate;
	/**
	 * 请求第三方交易次数
	 */
	protected Integer requestCount=0;
	
	//不与数据库映射
	protected String rebateType;
	protected String rebateWay;
	protected String mmNumber;
	protected String sumMoney;
	public String rebateTypeName;
	public String rebateWayName;
	public String fundTypeName;
	public String raiseRate;
	public String buyDatetime;
	public String startinInterestTime;
	public String days;
	public String loginName;
	public String couponsIncome;
	public String addrateIncome;
	public String sumIncome;
	public String notIncome;
	/**
	 * 已对账的金额之和
	 */
	public String sumAfterMoney;
	
	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public Integer getRequestCount() {
		return requestCount;
	}

	public void setRequestCount(Integer requestCount) {
		this.requestCount = requestCount;
	}

	public String getSumAfterMoney() {
		return sumAfterMoney;
	}

	public void setSumAfterMoney(String sumAfterMoney) {
		this.sumAfterMoney = sumAfterMoney;
	}

	/**
	 * Default Empty Constructor for class PlMmOrderAssignInterest
	 */
	public PlMmOrderAssignInterest () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class PlMmOrderAssignInterest
	 */
	public PlMmOrderAssignInterest (
		 Long in_assignInterestId
        ) {
		this.setAssignInterestId(in_assignInterestId);
    }

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getCouponsIncome() {
		return couponsIncome;
	}

	public void setCouponsIncome(String couponsIncome) {
		this.couponsIncome = couponsIncome;
	}

	public String getAddrateIncome() {
		return addrateIncome;
	}

	public void setAddrateIncome(String addrateIncome) {
		this.addrateIncome = addrateIncome;
	}

	public String getSumIncome() {
		return sumIncome;
	}

	public void setSumIncome(String sumIncome) {
		this.sumIncome = sumIncome;
	}

	public String getNotIncome() {
		return notIncome;
	}

	public void setNotIncome(String notIncome) {
		this.notIncome = notIncome;
	}

	public String getBuyDatetime() {
		return buyDatetime;
	}

	public void setBuyDatetime(String buyDatetime) {
		this.buyDatetime = buyDatetime;
	}

	public String getStartinInterestTime() {
		return startinInterestTime;
	}

	public void setStartinInterestTime(String startinInterestTime) {
		this.startinInterestTime = startinInterestTime;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public String getRaiseRate() {
		return raiseRate;
	}

	public void setRaiseRate(String raiseRate) {
		this.raiseRate = raiseRate;
	}

	public String getRebateTypeName() {
		return rebateTypeName;
	}

	public void setRebateTypeName(String rebateTypeName) {
		this.rebateTypeName = rebateTypeName;
	}

	public String getRebateWayName() {
		return rebateWayName;
	}

	public void setRebateWayName(String rebateWayName) {
		this.rebateWayName = rebateWayName;
	}

	public String getFundTypeName() {
		return fundTypeName;
	}

	public void setFundTypeName(String fundTypeName) {
		this.fundTypeName = fundTypeName;
	}

	public String getRebateType() {
		return rebateType;
	}

	public void setRebateType(String rebateType) {
		this.rebateType = rebateType;
	}

	public String getRebateWay() {
		return rebateWay;
	}

	public void setRebateWay(String rebateWay) {
		this.rebateWay = rebateWay;
	}

	public String getMmNumber() {
		return mmNumber;
	}

	public void setMmNumber(String mmNumber) {
		this.mmNumber = mmNumber;
	}

	public String getSumMoney() {
		return sumMoney;
	}

	public void setSumMoney(String sumMoney) {
		this.sumMoney = sumMoney;
	}

	public String getLockType() {
		return lockType;
	}

	public void setLockType(String lockType) {
		this.lockType = lockType;
	}

	public Integer getPeriods() {
		return periods;
	}

	public void setPeriods(Integer periods) {
		this.periods = periods;
	}

	public Short getIsValid() {
		return isValid;
	}

	public void setIsValid(Short isValid) {
		this.isValid = isValid;
	}

	public Short getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(Short isCheck) {
		this.isCheck = isCheck;
	}

	public Long getEarlyRedemptionId() {
		return earlyRedemptionId;
	}

	public void setEarlyRedemptionId(Long earlyRedemptionId) {
		this.earlyRedemptionId = earlyRedemptionId;
	}

	public java.math.BigDecimal getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(java.math.BigDecimal payMoney) {
		this.payMoney = payMoney;
	}

	public java.util.Date getFactDate() {
		return factDate;
	}

	public void setFactDate(java.util.Date factDate) {
		this.factDate = factDate;
	}

	public java.math.BigDecimal getAfterMoney() {
		return afterMoney;
	}

	public void setAfterMoney(java.math.BigDecimal afterMoney) {
		this.afterMoney = afterMoney;
	}

	public String getKeystr() {
		return keystr;
	}

	public void setKeystr(String keystr) {
		this.keystr = keystr;
	}

	/**
	 * id	 * @return Long
     * @hibernate.id column="assignInterestId" type="java.lang.Long" generator-class="native"
	 */
	public Long getAssignInterestId() {
		return this.assignInterestId;
	}
	
	/**
	 * Set the assignInterestId
	 */	
	public void setAssignInterestId(Long aValue) {
		this.assignInterestId = aValue;
	}	

	/**
	 * 订单id	 * @return Long
	 * @hibernate.property column="orderId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getOrderId() {
		return this.orderId;
	}
	
	/**
	 * Set the orderId
	 */	
	public void setOrderId(Long aValue) {
		this.orderId = aValue;
	}	

	/**
	 * id投资人	 * @return Long
	 * @hibernate.property column="investPersonId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getInvestPersonId() {
		return this.investPersonId;
	}
	
	/**
	 * Set the investPersonId
	 */	
	public void setInvestPersonId(Long aValue) {
		this.investPersonId = aValue;
	}	

	/**
	 * 投资人姓名	 * @return String
	 * @hibernate.property column="investPersonName" type="java.lang.String" length="10" not-null="false" unique="false"
	 */
	public String getInvestPersonName() {
		return this.investPersonName;
	}
	
	/**
	 * Set the investPersonName
	 */	
	public void setInvestPersonName(String aValue) {
		this.investPersonName = aValue;
	}	

	/**
	 * 理财产品的id	 * @return Long
	 * @hibernate.property column="mmplanId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getMmplanId() {
		return this.mmplanId;
	}
	
	/**
	 * Set the mmplanId
	 */	
	public void setMmplanId(Long aValue) {
		this.mmplanId = aValue;
	}	

	/**
	 * 理财产品的名称	 * @return String
	 * @hibernate.property column="mmName" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getMmName() {
		return this.mmName;
	}
	
	/**
	 * Set the mmName
	 */	
	public void setMmName(String aValue) {
		this.mmName = aValue;
	}	

	/**
	 * 类型loanInterest，principalRepayment	 * @return String
	 * @hibernate.property column="fundType" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getFundType() {
		return this.fundType;
	}
	
	/**
	 * Set the fundType
	 */	
	public void setFundType(String aValue) {
		this.fundType = aValue;
	}	

	/**
	 * 收入	 * @return java.math.BigDecimal
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
	 * 日	 * @return java.util.Date
	 * @hibernate.property column="intentDate" type="java.util.Date" length="10" not-null="false" unique="false"
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
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof PlMmOrderAssignInterest)) {
			return false;
		}
		PlMmOrderAssignInterest rhs = (PlMmOrderAssignInterest) object;
		return new EqualsBuilder()
				.append(this.assignInterestId, rhs.assignInterestId)
				.append(this.orderId, rhs.orderId)
				.append(this.investPersonId, rhs.investPersonId)
				.append(this.investPersonName, rhs.investPersonName)
				.append(this.mmplanId, rhs.mmplanId)
				.append(this.mmName, rhs.mmName)
				.append(this.fundType, rhs.fundType)
				.append(this.incomeMoney, rhs.incomeMoney)
				.append(this.intentDate, rhs.intentDate)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.assignInterestId) 
				.append(this.orderId) 
				.append(this.investPersonId) 
				.append(this.investPersonName) 
				.append(this.mmplanId) 
				.append(this.mmName) 
				.append(this.fundType) 
				.append(this.incomeMoney) 
				.append(this.intentDate) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("assignInterestId", this.assignInterestId) 
				.append("orderId", this.orderId) 
				.append("investPersonId", this.investPersonId) 
				.append("investPersonName", this.investPersonName) 
				.append("mmplanId", this.mmplanId) 
				.append("mmName", this.mmName) 
				.append("fundType", this.fundType) 
				.append("incomeMoney", this.incomeMoney) 
				.append("intentDate", this.intentDate) 
				.toString();
	}
	
	public Integer getLoanerRepayMentStatus() {
		return loanerRepayMentStatus;
	}

	public void setLoanerRepayMentStatus(Integer loanerRepayMentStatus) {
		this.loanerRepayMentStatus = loanerRepayMentStatus;
	}

	public String getInvestRequestNo() {
		return investRequestNo;
	}

	public void setInvestRequestNo(String investRequestNo) {
		this.investRequestNo = investRequestNo;
	}
	
	public String getLoanerRequestNo() {
		return loanerRequestNo;
	}

	public void setLoanerRequestNo(String loanerRequestNo) {
		this.loanerRequestNo = loanerRequestNo;
	}




}
