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
 * BpCustEntUpstreamContract Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class BpCustEntUpstreamContract extends com.zhiwei.core.model.BaseModel {

    protected Integer upContractId;
	protected String mainBuyer;
	protected BigDecimal buyerContractMoney;
	protected String buyerContractDuration;
	protected String contractPolicy;
	protected BpCustEntUpanddowncontract bpCustEntUpanddowncontract;


	/**
	 * Default Empty Constructor for class BpCustEntUpstreamContract
	 */
	public BpCustEntUpstreamContract () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class BpCustEntUpstreamContract
	 */
	public BpCustEntUpstreamContract (
		 Integer in_upContractId
        ) {
		this.setUpContractId(in_upContractId);
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
     * @hibernate.id column="upContractId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getUpContractId() {
		return this.upContractId;
	}
	
	/**
	 * Set the upContractId
	 */	
	public void setUpContractId(Integer aValue) {
		this.upContractId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="mainBuyer" type="java.lang.String" length="255" not-null="true" unique="false"
	 */
	public String getMainBuyer() {
		return this.mainBuyer;
	}
	
	/**
	 * Set the mainBuyer
	 * @spring.validator type="required"
	 */	
	public void setMainBuyer(String aValue) {
		this.mainBuyer = aValue;
	}	


	public BigDecimal getBuyerContractMoney() {
		return buyerContractMoney;
	}

	public void setBuyerContractMoney(BigDecimal buyerContractMoney) {
		this.buyerContractMoney = buyerContractMoney;
	}




	public String getBuyerContractDuration() {
		return buyerContractDuration;
	}

	public void setBuyerContractDuration(String buyerContractDuration) {
		this.buyerContractDuration = buyerContractDuration;
	}

	public String getContractPolicy() {
		return contractPolicy;
	}

	public void setContractPolicy(String contractPolicy) {
		this.contractPolicy = contractPolicy;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof BpCustEntUpstreamContract)) {
			return false;
		}
		BpCustEntUpstreamContract rhs = (BpCustEntUpstreamContract) object;
		return new EqualsBuilder()
				.append(this.upContractId, rhs.upContractId)
				.append(this.mainBuyer, rhs.mainBuyer)
				.append(this.buyerContractMoney, rhs.buyerContractMoney)
				.append(this.buyerContractDuration, rhs.buyerContractDuration)
				.append(this.contractPolicy, rhs.contractPolicy)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.upContractId) 
				.append(this.mainBuyer) 
				.append(this.buyerContractMoney) 
				.append(this.buyerContractDuration) 
				.append(this.contractPolicy) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("upContractId", this.upContractId) 
				.append("mainBuyer", this.mainBuyer) 
				.append("buyerContractMoney", this.buyerContractMoney) 
				.append("buyerContractDuration", this.buyerContractDuration) 
				.append("contractPolicy", this.contractPolicy) 
				.toString();
	}



}
