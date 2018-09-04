package com.zhiwei.credit.model.pay;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * 
 * @author 
 *
 */
/**
 * BpBidLoan Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 投标转账表 记录投标转账的记录 和投标状态
 */
public class BpBidLoan extends com.zhiwei.core.model.BaseModel {

    protected Long bidLoanId;
	protected Long platFormId;
	protected String loanNo;
	protected String orderNo;
	protected String batchNo;
	protected Double amount;
	protected String transferName;
	protected String remark;
	protected String loanOutFlag;
	protected String loanInFlag;
	protected Short state;


	/**
	 * Default Empty Constructor for class BpBidLoan
	 */
	public BpBidLoan () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class BpBidLoan
	 */
	public BpBidLoan (
		 Long in_bidLoanId
        ) {
		this.setBidLoanId(in_bidLoanId);
    }

    

	/**
	 * id	 * @return Long
     * @hibernate.id column="bidLoanId" type="java.lang.Long" generator-class="native"
	 */
	public Long getBidLoanId() {
		return this.bidLoanId;
	}
	
	/**
	 * Set the bidLoanId
	 */	
	public void setBidLoanId(Long aValue) {
		this.bidLoanId = aValue;
	}	

	/**
	 * 第三方支付平台id	 * @return Long
	 * @hibernate.property column="pId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */

	public Long getPlatFormId() {
		return platFormId;
	}

	public void setPlatFormId(Long platFormId) {
		this.platFormId = platFormId;
	}

	/**
	 * 乾多多流水号	 * @return String
	 * @hibernate.property column="loanNo" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	public String getLoanNo() {
		return this.loanNo;
	}
	
	


	/**
	 * Set the loanNo
	 */	
	public void setLoanNo(String aValue) {
		this.loanNo = aValue;
	}	

	/**
	 * 网贷平台订单号	 * @return String
	 * @hibernate.property column="orderNo" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	public String getOrderNo() {
		return this.orderNo;
	}
	
	/**
	 * Set the orderNo
	 */	
	public void setOrderNo(String aValue) {
		this.orderNo = aValue;
	}	

	/**
	 * 网贷平台标号	 * @return String
	 * @hibernate.property column="batchNo" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	public String getBatchNo() {
		return this.batchNo;
	}
	
	/**
	 * Set the batchNo
	 */	
	public void setBatchNo(String aValue) {
		this.batchNo = aValue;
	}	

	/**
	 * 金额	 * @return Double
	 * @hibernate.property column="amount" type="java.lang.Double" length="22" not-null="false" unique="false"
	 */
	public Double getAmount() {
		return this.amount;
	}
	
	/**
	 * Set the amount
	 */	
	public void setAmount(Double aValue) {
		this.amount = aValue;
	}	

	/**
	 * 用途	 * @return String
	 * @hibernate.property column="transferName" type="java.lang.String" length="200" not-null="false" unique="false"
	 */
	public String getTransferName() {
		return this.transferName;
	}
	
	/**
	 * Set the transferName
	 */	
	public void setTransferName(String aValue) {
		this.transferName = aValue;
	}	

	/**
	 * 备注	 * @return String
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
	 * 付款人标识	 * @return String
	 * @hibernate.property column="loanOutFlag" type="java.lang.String" length="10" not-null="false" unique="false"
	 */
	public String getLoanOutFlag() {
		return this.loanOutFlag;
	}
	
	/**
	 * Set the loanOutFlag
	 */	
	public void setLoanOutFlag(String aValue) {
		this.loanOutFlag = aValue;
	}	

	/**
	 * 收款人标识	 * @return String
	 * @hibernate.property column="loanInFlag" type="java.lang.String" length="10" not-null="false" unique="false"
	 */
	public String getLoanInFlag() {
		return this.loanInFlag;
	}
	
	/**
	 * Set the loanInFlag
	 */	
	public void setLoanInFlag(String aValue) {
		this.loanInFlag = aValue;
	}	

	/**
	 * 状态 （状态 （1投标中 2 还款中3已完成 4投标失败）	 * @return Short
	 * @hibernate.property column="state" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getState() {
		return this.state;
	}
	
	/**
	 * Set the state
	 */	
	public void setState(Short aValue) {
		this.state = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof BpBidLoan)) {
			return false;
		}
		BpBidLoan rhs = (BpBidLoan) object;
		return new EqualsBuilder()
				.append(this.bidLoanId, rhs.bidLoanId)
				.append(this.platFormId, rhs.platFormId)
				.append(this.loanNo, rhs.loanNo)
				.append(this.orderNo, rhs.orderNo)
				.append(this.batchNo, rhs.batchNo)
				.append(this.amount, rhs.amount)
				.append(this.transferName, rhs.transferName)
				.append(this.remark, rhs.remark)
				.append(this.loanOutFlag, rhs.loanOutFlag)
				.append(this.loanInFlag, rhs.loanInFlag)
				.append(this.state, rhs.state)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.bidLoanId) 
				.append(this.platFormId)
				.append(this.loanNo) 
				.append(this.orderNo) 
				.append(this.batchNo) 
				.append(this.amount) 
				.append(this.transferName) 
				.append(this.remark) 
				.append(this.loanOutFlag) 
				.append(this.loanInFlag) 
				.append(this.state) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("bidLoanId", this.bidLoanId) 
			     .append("platFormId", this.platFormId)
				.append("loanNo", this.loanNo) 
				.append("orderNo", this.orderNo) 
				.append("batchNo", this.batchNo) 
				.append("amount", this.amount) 
				.append("transferName", this.transferName) 
				.append("remark", this.remark) 
				.append("loanOutFlag", this.loanOutFlag) 
				.append("loanInFlag", this.loanInFlag) 
				.append("state", this.state) 
				.toString();
	}



}
