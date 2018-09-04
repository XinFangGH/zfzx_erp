package com.zhiwei.credit.model.creditFlow.financingAgency;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidSale;

/**
 * 
 * @author 
 *
 */
/**
 * PlBidSale Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 债权挂牌转让表
 */
public class PlBidSale extends com.hurong.core.model.BaseModel {
	/**
	 * 主键id
	 */
    protected Long id;
	/**
	 * 原始债权人id
	 */
	protected Long oldCustID;
	/**
	 * 当前债权持有人id(如果转让过则为最新债权人id)
	 */
	protected Long outCustID;
	/**
	 * 购买人的id
	 */
	protected Long inCustID;
	/**
	 * 标表id,pl_bid_plan的bidId
	 */
	protected Long bidPlanID;
	/**
	 * 投标情况表id,pl_bid_info的id
	 */
	protected Long bidInfoID;
	/**
	 * 挂牌开始时间
	 */
	protected java.util.Date saleStartingTime;
	/**
	 * 挂牌开始时间
	 */
	protected java.util.Date saleStartTime;
	/**
	 * 挂牌关闭时间
	 */
	protected java.util.Date saleCloseTime;
	/**
	 * 购买处理时间
	 */
	protected java.util.Date saleDealTime;
	/**
	 * 成功购买时间
	 */
	protected java.util.Date saleSuccessTime;
	/**
	 * 当前可出让本金
	 */
	protected java.math.BigDecimal saleMoney;
	/**
	 * 当前可出让本金
	 */
	protected java.math.BigDecimal beginMoney;
	/**
	 * 折让金
	 */
	protected java.math.BigDecimal changeMoney;
	/**
	 * 折让比例（占本金千分之？）
	 */
	protected Integer changeMoneyRate;
	/**
	 * 转让类型   1为降价，0为加价
	 */
	protected Short changeMoneyType;
	/**
	 * 0，挂牌中；1挂牌成功 3，受让人转帐中；4，交易成功；9，交易手动关闭；10，交易过 期；7，转到债权库(稳安贷特有)； 
	 */
	protected Short saleStatus; 
	/**
	 * 实际交易金额
	 */
	protected java.math.BigDecimal sumMoney;
	/**
	 * 实际服务费
	 */
	protected java.math.BigDecimal transferFee;
	/**
	 * 预收服务费
	 */
	protected java.math.BigDecimal preTransferFee;
	/**
	 * 预收费流水号
	 */
	protected String preTransferFeeRequestNo;
	/**
	 * 债权交易新债权人的流水号
	 */
	protected String newOrderNo;
	/**
	 * 转让前债权人名称
	 */
	protected String oldCustName;
	/**
	 * 当前债权持有人名称(如果转让过则为最新债权人名称)
	 */
	protected String outCustName;
	/**
	 * 购买人名称
	 */
	protected String inCustName;
	/**
	 * 债权交易预收款收取状态    1：转实收   2退了多收费用
	 */
	protected Short preTransferFeeStatus;
	protected String preAuthorizationNum; //富友，预授权合同号，双乾转账审核流水号
	
	protected String returnRequestNo;//返款流水号
	protected String returnStatus;//返款状态 2=已返款，null/1=未返款

	//数据库没有的
	protected String orderNo;
	protected BigDecimal yearAccrualRate;
	protected java.util.Date startDate;
	protected java.util.Date intentDate;
	protected String bidProName;
	protected String accrualtype;  //计息方式
	protected java.util.Date nextPayDate;
	protected java.math.BigDecimal reBackFee;//退费金额
	
	
	
	public String getReturnRequestNo() {
		return returnRequestNo;
	}

	public void setReturnRequestNo(String returnRequestNo) {
		this.returnRequestNo = returnRequestNo;
	}

	public String getReturnStatus() {
		return returnStatus;
	}

	public void setReturnStatus(String returnStatus) {
		this.returnStatus = returnStatus;
	}

	public String getPreAuthorizationNum() {
		return preAuthorizationNum;
	}

	public void setPreAuthorizationNum(String preAuthorizationNum) {
		this.preAuthorizationNum = preAuthorizationNum;
	}

	public Short getPreTransferFeeStatus() {
		return preTransferFeeStatus;
	}

	public void setPreTransferFeeStatus(Short preTransferFeeStatus) {
		this.preTransferFeeStatus = preTransferFeeStatus;
	}
	public java.math.BigDecimal getReBackFee() {
		return reBackFee;
	}

	public void setReBackFee(java.math.BigDecimal reBackFee) {
		this.reBackFee = reBackFee;
	}

	public String getPreTransferFeeRequestNo() {
		return preTransferFeeRequestNo;
	}

	public void setPreTransferFeeRequestNo(String preTransferFeeRequestNo) {
		this.preTransferFeeRequestNo = preTransferFeeRequestNo;
	}
	public BigDecimal getYearAccrualRate() {
		return yearAccrualRate;
	}

	public void setYearAccrualRate(BigDecimal yearAccrualRate) {
		this.yearAccrualRate = yearAccrualRate;
	}

	public java.math.BigDecimal getPreTransferFee() {
		return preTransferFee;
	}

	public void setPreTransferFee(java.math.BigDecimal preTransferFee) {
		this.preTransferFee = preTransferFee;
	}

	public java.util.Date getSaleStartingTime() {
		return saleStartingTime;
	}

	public void setSaleStartingTime(java.util.Date saleStartingTime) {
		this.saleStartingTime = saleStartingTime;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getNewOrderNo() {
		return newOrderNo;
	}

	public void setNewOrderNo(String newOrderNo) {
		this.newOrderNo = newOrderNo;
	}

	public java.util.Date getNextPayDate() {
		return nextPayDate;
	}

	public String getOutCustName() {
		return outCustName;
	}

	public void setOutCustName(String outCustName) {
		this.outCustName = outCustName;
	}

	public String getOldCustName() {
		return oldCustName;
	}

	public void setOldCustName(String oldCustName) {
		this.oldCustName = oldCustName;
	}

	public String getInCustName() {
		return inCustName;
	}

	public void setInCustName(String inCustName) {
		this.inCustName = inCustName;
	}

	public void setNextPayDate(java.util.Date nextPayDate) {
		this.nextPayDate = nextPayDate;
	}

	public String getAccrualtype() {
		return accrualtype;
	}

	public void setAccrualtype(String accrualtype) {
		this.accrualtype = accrualtype;
	}

	public java.util.Date getStartDate() {
		return startDate;
	}

	public void setStartDate(java.util.Date startDate) {
		this.startDate = startDate;
	}

	public java.util.Date getIntentDate() {
		return intentDate;
	}

	public void setIntentDate(java.util.Date intentDate) {
		this.intentDate = intentDate;
	}

	public String getBidProName() {
		return bidProName;
	}

	public void setBidProName(String bidProName) {
		this.bidProName = bidProName;
	}

	/**
	 * Default Empty Constructor for class PlBidSale
	 */
	public PlBidSale () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class PlBidSale
	 */
	public PlBidSale (
		 Long in_id
        ) {
		this.setId(in_id);
    }

    

	/**
	 * id	 * @return Long
     * @hibernate.id column="id" type="java.lang.Long" generator-class="native"
	 */
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
	 * 原始债权人在注册用户表中的ID	 * @return Long
	 * @hibernate.property column="oldCustID" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	public Long getOldCustID() {
		return this.oldCustID;
	}
	
	/**
	 * Set the oldCustID
	 * @spring.validator type="required"
	 */	
	public void setOldCustID(Long aValue) {
		this.oldCustID = aValue;
	}	

	/**
	 * 本次交易出让人在注册用户表中的ID	 * @return Long
	 * @hibernate.property column="outCustID" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	public Long getOutCustID() {
		return this.outCustID;
	}
	
	/**
	 * Set the outCustID
	 * @spring.validator type="required"
	 */	
	public void setOutCustID(Long aValue) {
		this.outCustID = aValue;
	}	

	/**
	 * 受让人注册用户表ID	 * @return Long
	 * @hibernate.property column="inCustID" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	public Long getInCustID() {
		return this.inCustID;
	}
	
	/**
	 * Set the inCustID
	 * @spring.validator type="required"
	 */	
	public void setInCustID(Long aValue) {
		this.inCustID = aValue;
	}	

	/**
	 * 转让的债权在pl_bid_plan表中的ID	 * @return Long
	 * @hibernate.property column="bidPlanID" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getBidPlanID() {
		return this.bidPlanID;
	}
	
	/**
	 * Set the bidPlanID
	 */	
	public void setBidPlanID(Long aValue) {
		this.bidPlanID = aValue;
	}	

	/**
	 * 转让的债权在pl_bid_plan表中的ID	 * @return Long
	 * @hibernate.property column="bidInfoID" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	public Long getBidInfoID() {
		return this.bidInfoID;
	}
	
	/**
	 * Set the bidInfoID
	 * @spring.validator type="required"
	 */	
	public void setBidInfoID(Long aValue) {
		this.bidInfoID = aValue;
	}	

	/**
	 * 挂牌交易正式开始时间	 * @return java.util.Date
	 * @hibernate.property column="saleStartTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getSaleStartTime() {
		return this.saleStartTime;
	}
	
	/**
	 * Set the saleStartTime
	 */	
	public void setSaleStartTime(java.util.Date aValue) {
		this.saleStartTime = aValue;
	}	

	/**
	 * 手动关闭挂牌的操作时间	 * @return java.util.Date
	 * @hibernate.property column="saleCloseTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getSaleCloseTime() {
		return this.saleCloseTime;
	}
	
	/**
	 * Set the saleCloseTime
	 */	
	public void setSaleCloseTime(java.util.Date aValue) {
		this.saleCloseTime = aValue;
	}	

	/**
	 * 受让人开始摘牌时间	 * @return java.util.Date
	 * @hibernate.property column="saleDealTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getSaleDealTime() {
		return this.saleDealTime;
	}
	
	/**
	 * Set the saleDealTime
	 */	
	public void setSaleDealTime(java.util.Date aValue) {
		this.saleDealTime = aValue;
	}	

	/**
	 * 受让人成功摘牌时间	 * @return java.util.Date
	 * @hibernate.property column="saleSuccessTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getSaleSuccessTime() {
		return this.saleSuccessTime;
	}
	
	/**
	 * Set the saleSuccessTime
	 */	
	public void setSaleSuccessTime(java.util.Date aValue) {
		this.saleSuccessTime = aValue;
	}	

	/**
	 * 剩余可转让本金	 * @return java.math.BigDecimal
	 * @hibernate.property column="saleMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getSaleMoney() {
		return this.saleMoney;
	}
	
	/**
	 * Set the saleMoney
	 */	
	public void setSaleMoney(java.math.BigDecimal aValue) {
		this.saleMoney = aValue;
		if(this.changeMoneyRate!=null&&this.changeMoneyType!=null&&this.saleMoney!=null){
			this.changeMoney=this.saleMoney.multiply(new BigDecimal(this.changeMoneyRate)).divide(new BigDecimal(1000)).setScale(2, BigDecimal.ROUND_HALF_UP);
			
		}
	}	

	/**
	 * 挂牌时剩余可转让本金	 * @return java.math.BigDecimal
	 * @hibernate.property column="beginMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getBeginMoney() {
		return this.beginMoney;
	}
	
	/**
	 * Set the beginMoney
	 */	
	public void setBeginMoney(java.math.BigDecimal aValue) {
		this.beginMoney = aValue;
	}	

	/**
	 * 折价/加价金额	 * @return java.math.BigDecimal
	 * @hibernate.property column="changeMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getChangeMoney() {
		return this.changeMoney;
	}
	
	/**
	 * Set the changeMoney
	 */	
	public void setChangeMoney(java.math.BigDecimal aValue) {
		this.changeMoney = aValue;
	}	

	/**
	 * 折价率	 * @return Integer
	 * @hibernate.property column="changeMoneyRate" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getChangeMoneyRate() {
		
		return this.changeMoneyRate;
	}
	
	/**
	 * Set the changeMoneyRate
	 */	
	public void setChangeMoneyRate(Integer aValue) {
		this.changeMoneyRate = aValue;
		
	}	

	/**
	 * 加价/折价类型	 * @return Short
	 * @hibernate.property column="changeMoneyType" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getChangeMoneyType() {
		return this.changeMoneyType;
	}
	
	/**
	 * Set the changeMoneyType
	 */	
	public void setChangeMoneyType(Short aValue) {
		this.changeMoneyType = aValue;
	}	

	/**
	 * 挂牌状态	 * @return Short
	 * @hibernate.property column="saleStatus" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getSaleStatus() {
		return this.saleStatus;
	}
	
	/**
	 * Set the saleStatus
	 */	
	public void setSaleStatus(Short aValue) {
		this.saleStatus = aValue;
	}	

	/**
	 * 交易完成时的总结款金额	 * @return java.math.BigDecimal
	 * @hibernate.property column="sumMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getSumMoney() {
		return this.sumMoney;
	}
	
	/**
	 * Set the sumMoney
	 */	
	public void setSumMoney(java.math.BigDecimal aValue) {
		this.sumMoney = aValue;
	}	

	/**
	 * 转让服务费	 * @return java.math.BigDecimal
	 * @hibernate.property column="transferFee" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getTransferFee() {
		return this.transferFee;
	}
	
	/**
	 * Set the transferFee
	 */	
	public void setTransferFee(java.math.BigDecimal aValue) {
		this.transferFee = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof PlBidSale)) {
			return false;
		}
		PlBidSale rhs = (PlBidSale) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.oldCustID, rhs.oldCustID)
				.append(this.outCustID, rhs.outCustID)
				.append(this.inCustID, rhs.inCustID)
				.append(this.bidPlanID, rhs.bidPlanID)
				.append(this.bidInfoID, rhs.bidInfoID)
				.append(this.saleStartTime, rhs.saleStartTime)
				.append(this.saleCloseTime, rhs.saleCloseTime)
				.append(this.saleDealTime, rhs.saleDealTime)
				.append(this.saleSuccessTime, rhs.saleSuccessTime)
				.append(this.saleMoney, rhs.saleMoney)
				.append(this.beginMoney, rhs.beginMoney)
				.append(this.changeMoney, rhs.changeMoney)
				.append(this.changeMoneyRate, rhs.changeMoneyRate)
				.append(this.changeMoneyType, rhs.changeMoneyType)
				.append(this.saleStatus, rhs.saleStatus)
				.append(this.sumMoney, rhs.sumMoney)
				.append(this.transferFee, rhs.transferFee)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.oldCustID) 
				.append(this.outCustID) 
				.append(this.inCustID) 
				.append(this.bidPlanID) 
				.append(this.bidInfoID) 
				.append(this.saleStartTime) 
				.append(this.saleCloseTime) 
				.append(this.saleDealTime) 
				.append(this.saleSuccessTime) 
				.append(this.saleMoney) 
				.append(this.beginMoney) 
				.append(this.changeMoney) 
				.append(this.changeMoneyRate) 
				.append(this.changeMoneyType) 
				.append(this.saleStatus) 
				.append(this.sumMoney) 
				.append(this.transferFee) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("oldCustID", this.oldCustID) 
				.append("outCustID", this.outCustID) 
				.append("inCustID", this.inCustID) 
				.append("bidPlanID", this.bidPlanID) 
				.append("bidInfoID", this.bidInfoID) 
				.append("saleStartTime", this.saleStartTime) 
				.append("saleCloseTime", this.saleCloseTime) 
				.append("saleDealTime", this.saleDealTime) 
				.append("saleSuccessTime", this.saleSuccessTime) 
				.append("saleMoney", this.saleMoney) 
				.append("beginMoney", this.beginMoney) 
				.append("changeMoney", this.changeMoney) 
				.append("changeMoneyRate", this.changeMoneyRate) 
				.append("changeMoneyType", this.changeMoneyType) 
				.append("saleStatus", this.saleStatus) 
				.append("sumMoney", this.sumMoney) 
				.append("transferFee", this.transferFee) 
				.toString();
	}



}
