package com.zhiwei.credit.model.creditFlow.fund.project;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
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
 * SettlementInfo Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 每日计算保有量
 */
public class SettlementInfo extends com.zhiwei.core.model.BaseModel {

    protected Long infoId;
	protected java.math.BigDecimal settleMoney;
	protected java.math.BigDecimal royaltyRatio;
	protected java.math.BigDecimal royaltyMoney;
	protected java.util.Date createDate;


	/**
	 * Default Empty Constructor for class SettlementInfo
	 */
	public SettlementInfo () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class SettlementInfo
	 */
	public SettlementInfo (
		 Long in_infoId
        ) {
		this.setInfoId(in_infoId);
    }

    

	/**
	 * infoId	 * @return Long
     * @hibernate.id column="infoId" type="java.lang.Long" generator-class="native"
	 */
	public Long getInfoId() {
		return this.infoId;
	}
	
	/**
	 * Set the infoId
	 */	
	public void setInfoId(Long aValue) {
		this.infoId = aValue;
	}	

	/**
	 * 保有量金额	 * @return java.math.BigDecimal
	 * @hibernate.property column="settleMoney" type="java.math.BigDecimal" length="10" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getSettleMoney() {
		return this.settleMoney;
	}
	
	/**
	 * Set the settleMoney
	 */	
	public void setSettleMoney(java.math.BigDecimal aValue) {
		this.settleMoney = aValue;
	}	

	/**
	 * 提成比例	 * @return java.math.BigDecimal
	 * @hibernate.property column="royaltyRatio" type="java.math.BigDecimal" length="10" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getRoyaltyRatio() {
		return this.royaltyRatio;
	}
	
	/**
	 * Set the royaltyRatio
	 */	
	public void setRoyaltyRatio(java.math.BigDecimal aValue) {
		this.royaltyRatio = aValue;
	}	

	/**
	 * 提成金额	 * @return java.math.BigDecimal
	 * @hibernate.property column="royaltyMoney" type="java.math.BigDecimal" length="10" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getRoyaltyMoney() {
		return this.royaltyMoney;
	}
	
	/**
	 * Set the royaltyMoney
	 */	
	public void setRoyaltyMoney(java.math.BigDecimal aValue) {
		this.royaltyMoney = aValue;
	}	

	/**
	 * 生成时间	 * @return java.util.Date
	 * @hibernate.property column="createDate" type="java.util.Date" length="10" not-null="false" unique="false"
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
	 * 部门ID	 * @return Long
	 * @hibernate.property column="orgId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getOrgId() {
		return this.orgId;
	}
	
	/**
	 * Set the orgId
	 */	
	public void setOrgId(Long aValue) {
		this.orgId = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SettlementInfo)) {
			return false;
		}
		SettlementInfo rhs = (SettlementInfo) object;
		return new EqualsBuilder()
				.append(this.infoId, rhs.infoId)
				.append(this.settleMoney, rhs.settleMoney)
				.append(this.royaltyRatio, rhs.royaltyRatio)
				.append(this.royaltyMoney, rhs.royaltyMoney)
				.append(this.createDate, rhs.createDate)
				.append(this.orgId, rhs.orgId)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.infoId) 
				.append(this.settleMoney) 
				.append(this.royaltyRatio) 
				.append(this.royaltyMoney) 
				.append(this.createDate) 
				.append(this.orgId) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("infoId", this.infoId) 
				.append("settleMoney", this.settleMoney) 
				.append("royaltyRatio", this.royaltyRatio) 
				.append("royaltyMoney", this.royaltyMoney) 
				.append("createDate", this.createDate) 
				.append("orgId", this.orgId) 
				.toString();
	}



}
