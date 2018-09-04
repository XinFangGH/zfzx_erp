package com.zhiwei.credit.model.creditFlow.finance.compensatory;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
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
 * PlBidCompensatoryFlow Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class PlBidCompensatoryFlow extends com.zhiwei.core.model.BaseModel {

    protected Long id;
	protected Long compensatoryId;
	protected java.math.BigDecimal backPunishMoney=new BigDecimal(0);
	protected java.math.BigDecimal backCompensatoryMoney=new BigDecimal(0);
	protected java.math.BigDecimal flateMoney=new BigDecimal(0);
	protected String backType;
	protected String requestNo;
	protected Integer backStatus;
	/**
	 * 回款登记日期
	 */
	protected Date backDate;


	public Date getBackDate() {
		return backDate;
	}

	public void setBackDate(Date backDate) {
		this.backDate = backDate;
	}

	/**
	 * Default Empty Constructor for class PlBidCompensatoryFlow
	 */
	public PlBidCompensatoryFlow () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class PlBidCompensatoryFlow
	 */
	public PlBidCompensatoryFlow (
		 Long in_id
        ) {
		this.setId(in_id);
    }

    

	/**
	 * 	 * @return Long
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
	 * 代偿的代偿款项	 * @return Long
	 * @hibernate.property column="compensatoryId" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	public Long getCompensatoryId() {
		return this.compensatoryId;
	}
	
	/**
	 * Set the compensatoryId
	 * @spring.validator type="required"
	 */	
	public void setCompensatoryId(Long aValue) {
		this.compensatoryId = aValue;
	}	

	/**
	 * 偿还代偿产生的罚息	 * @return java.math.BigDecimal
	 * @hibernate.property column="backPunishMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getBackPunishMoney() {
		return this.backPunishMoney;
	}
	
	/**
	 * Set the backPunishMoney
	 */	
	public void setBackPunishMoney(java.math.BigDecimal aValue) {
		this.backPunishMoney = aValue;
	}	

	/**
	 * 偿还代偿金额	 * @return java.math.BigDecimal
	 * @hibernate.property column="backCompensatoryMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getBackCompensatoryMoney() {
		return this.backCompensatoryMoney;
	}
	
	/**
	 * Set the backCompensatoryMoney
	 */	
	public void setBackCompensatoryMoney(java.math.BigDecimal aValue) {
		this.backCompensatoryMoney = aValue;
	}	

	/**
	 * 平账金额	 * @return java.math.BigDecimal
	 * @hibernate.property column="flateMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getFlateMoney() {
		return this.flateMoney;
	}
	
	/**
	 * Set the flateMoney
	 */	
	public void setFlateMoney(java.math.BigDecimal aValue) {
		this.flateMoney = aValue;
	}	

	/**
	 * 偿还类型（线上偿还，线下登记回款）  	 * @return String
	 * @hibernate.property column="backType" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getBackType() {
		return this.backType;
	}
	
	/**
	 * Set the backType
	 */	
	public void setBackType(String aValue) {
		this.backType = aValue;
	}	

	/**
	 * 流水号（第三方偿还流水号）	 * @return Integer
	 * @hibernate.property column="requestNo" type="java.lang.String" length="10" not-null="false" unique="false"
	 */
	public String getRequestNo() {
		return this.requestNo;
	}
	
	/**
	 * Set the requestNo
	 */	
	public void setRequestNo(String aValue) {
		this.requestNo = aValue;
	}	

	/**
	 * 偿还代偿款记录状态：0表示未偿还，1表示审核中，2表示已偿还清楚	 * @return Integer
	 * @hibernate.property column="backStatus" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getBackStatus() {
		return this.backStatus;
	}
	
	/**
	 * Set the backStatus
	 */	
	public void setBackStatus(Integer aValue) {
		this.backStatus = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof PlBidCompensatoryFlow)) {
			return false;
		}
		PlBidCompensatoryFlow rhs = (PlBidCompensatoryFlow) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.compensatoryId, rhs.compensatoryId)
				.append(this.backPunishMoney, rhs.backPunishMoney)
				.append(this.backCompensatoryMoney, rhs.backCompensatoryMoney)
				.append(this.flateMoney, rhs.flateMoney)
				.append(this.backType, rhs.backType)
				.append(this.requestNo, rhs.requestNo)
				.append(this.backStatus, rhs.backStatus)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.compensatoryId) 
				.append(this.backPunishMoney) 
				.append(this.backCompensatoryMoney) 
				.append(this.flateMoney) 
				.append(this.backType) 
				.append(this.requestNo) 
				.append(this.backStatus) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("compensatoryId", this.compensatoryId) 
				.append("backPunishMoney", this.backPunishMoney) 
				.append("backCompensatoryMoney", this.backCompensatoryMoney) 
				.append("flateMoney", this.flateMoney) 
				.append("backType", this.backType) 
				.append("requestNo", this.requestNo) 
				.append("backStatus", this.backStatus) 
				.toString();
	}

}
