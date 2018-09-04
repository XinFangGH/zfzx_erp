package com.zhiwei.credit.model.creditFlow.customer.enterprise;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * 
 * @author 
 *
 */
/**
 * BpCustEntUpanddowncontract Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class BpCustEntUpanddowncontract extends com.zhiwei.core.model.BaseModel {

    protected Integer upAndDownContractId;
	protected String upremarks;
	protected String downremarks;
	protected Enterprise enterprise;
	  protected Set<BpCustEntDownstreamContract> bpCustEntDownstreamContractlist=new HashSet<BpCustEntDownstreamContract>();
	  protected Set<BpCustEntUpstreamContract> bpCustEntUpstreamContractlist=new HashSet<BpCustEntUpstreamContract>();
	  


	/**
	 * Default Empty Constructor for class BpCustEntUpanddowncontract
	 */
	public BpCustEntUpanddowncontract () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class BpCustEntUpanddowncontract
	 */
	public BpCustEntUpanddowncontract (
		 Integer in_upAndDownContractId
        ) {
		this.setUpAndDownContractId(in_upAndDownContractId);
    }

    

	public Enterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	public Set<BpCustEntDownstreamContract> getBpCustEntDownstreamContractlist() {
		return bpCustEntDownstreamContractlist;
	}

	public void setBpCustEntDownstreamContractlist(
			Set<BpCustEntDownstreamContract> bpCustEntDownstreamContractlist) {
		this.bpCustEntDownstreamContractlist = bpCustEntDownstreamContractlist;
	}

	public Set<BpCustEntUpstreamContract> getBpCustEntUpstreamContractlist() {
		return bpCustEntUpstreamContractlist;
	}

	public void setBpCustEntUpstreamContractlist(
			Set<BpCustEntUpstreamContract> bpCustEntUpstreamContractlist) {
		this.bpCustEntUpstreamContractlist = bpCustEntUpstreamContractlist;
	}

	/**
	 * 	 * @return Integer
     * @hibernate.id column="upAndDownContractId" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getUpAndDownContractId() {
		return this.upAndDownContractId;
	}
	
	/**
	 * Set the upAndDownContractId
	 */	
	public void setUpAndDownContractId(Integer aValue) {
		this.upAndDownContractId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="upremarks" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getUpremarks() {
		return this.upremarks;
	}
	
	/**
	 * Set the upremarks
	 */	
	public void setUpremarks(String aValue) {
		this.upremarks = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="downremarks" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getDownremarks() {
		return this.downremarks;
	}
	
	/**
	 * Set the downremarks
	 */	
	public void setDownremarks(String aValue) {
		this.downremarks = aValue;
	}	


	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof BpCustEntUpanddowncontract)) {
			return false;
		}
		BpCustEntUpanddowncontract rhs = (BpCustEntUpanddowncontract) object;
		return new EqualsBuilder()
				.append(this.upAndDownContractId, rhs.upAndDownContractId)
				.append(this.upremarks, rhs.upremarks)
				.append(this.downremarks, rhs.downremarks)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.upAndDownContractId) 
				.append(this.upremarks) 
				.append(this.downremarks) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("upAndDownContractId", this.upAndDownContractId) 
				.append("upremarks", this.upremarks) 
				.append("downremarks", this.downremarks) 
				.toString();
	}



}
