package com.zhiwei.credit.model.creditFlow.customer.enterprise;
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
 * BpCustEntDownstreamContract Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class BpCustEntDownstreamContract extends com.zhiwei.core.model.BaseModel {

    protected Integer downContractId;
	protected String dateMode;
	protected Integer count;
	protected BigDecimal contractMoney;
	protected BigDecimal saleIncomeyMoney;
	protected BigDecimal noContractSaleMoney;
	protected BpCustEntUpanddowncontract bpCustEntUpanddowncontract;


	/**
	 * Default Empty Constructor for class BpCustEntDownstreamContract
	 */
	public BpCustEntDownstreamContract () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class BpCustEntDownstreamContract
	 */
	public BpCustEntDownstreamContract (
		 Integer in_downContractId
        ) {
		this.setDownContractId(in_downContractId);
    }

    

	public BpCustEntUpanddowncontract getBpCustEntUpanddowncontract() {
		return bpCustEntUpanddowncontract;
	}

	public void setBpCustEntUpanddowncontract(
			BpCustEntUpanddowncontract bpCustEntUpanddowncontract) {
		this.bpCustEntUpanddowncontract = bpCustEntUpanddowncontract;
	}

	/**
	 * 	 * @return Integer
     * @hibernate.id column="downContractId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getDownContractId() {
		return this.downContractId;
	}
	
	/**
	 * Set the downContractId
	 */	
	public void setDownContractId(Integer aValue) {
		this.downContractId = aValue;
	}	

	public BigDecimal getContractMoney() {
		return contractMoney;
	}

	public void setContractMoney(BigDecimal contractMoney) {
		this.contractMoney = contractMoney;
	}

	public BigDecimal getSaleIncomeyMoney() {
		return saleIncomeyMoney;
	}

	public void setSaleIncomeyMoney(BigDecimal saleIncomeyMoney) {
		this.saleIncomeyMoney = saleIncomeyMoney;
	}

	public BigDecimal getNoContractSaleMoney() {
		return noContractSaleMoney;
	}

	public void setNoContractSaleMoney(BigDecimal noContractSaleMoney) {
		this.noContractSaleMoney = noContractSaleMoney;
	}

	/**
	 * 	 * @return String
	 * @hibernate.property column="dateMode" type="java.lang.String" length="255" not-null="true" unique="false"
	 */
	public String getDateMode() {
		return this.dateMode;
	}
	
	/**
	 * Set the dateMode
	 * @spring.validator type="required"
	 */	
	public void setDateMode(String aValue) {
		this.dateMode = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="count" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	





	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof BpCustEntDownstreamContract)) {
			return false;
		}
		BpCustEntDownstreamContract rhs = (BpCustEntDownstreamContract) object;
		return new EqualsBuilder()
				.append(this.downContractId, rhs.downContractId)
				.append(this.dateMode, rhs.dateMode)
				.append(this.count, rhs.count)
				.append(this.contractMoney, rhs.contractMoney)
				.append(this.saleIncomeyMoney, rhs.saleIncomeyMoney)
				.append(this.noContractSaleMoney, rhs.noContractSaleMoney)
				.isEquals();
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.downContractId) 
				.append(this.dateMode) 
				.append(this.count) 
				.append(this.contractMoney) 
				.append(this.saleIncomeyMoney) 
				.append(this.noContractSaleMoney) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("downContractId", this.downContractId) 
				.append("dateMode", this.dateMode) 
				.append("count", this.count) 
				.append("contractMoney", this.contractMoney) 
				.append("saleIncomeyMoney", this.saleIncomeyMoney) 
				.append("noContractSaleMoney", this.noContractSaleMoney) 
				.toString();
	}



}
