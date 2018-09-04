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
 * BpCustEntUpanddownstream Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class BpCustEntUpanddownstream extends com.zhiwei.core.model.BaseModel {

    protected Integer upAndDownCustomId;
	protected String remarks;
	protected Enterprise enterprise;
	  protected Set<BpCustEntDownstreamCustom> bpCustEntDownstreamCustomlist=new HashSet<BpCustEntDownstreamCustom>();
	  protected Set<BpCustEntUpstreamCustom> bpCustEntUpstreamCustomlist=new HashSet<BpCustEntUpstreamCustom>();
	  

	/**
	 * Default Empty Constructor for class BpCustEntUpanddownstream
	 */
	public BpCustEntUpanddownstream () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class BpCustEntUpanddownstream
	 */
	public BpCustEntUpanddownstream (
			Integer in_upAndDownCustomId
        ) {
		this.setUpAndDownCustomId(in_upAndDownCustomId);
    }

    

	public Enterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	public Set<BpCustEntDownstreamCustom> getBpCustEntDownstreamCustomlist() {
		return bpCustEntDownstreamCustomlist;
	}

	public void setBpCustEntDownstreamCustomlist(
			Set<BpCustEntDownstreamCustom> bpCustEntDownstreamCustomlist) {
		this.bpCustEntDownstreamCustomlist = bpCustEntDownstreamCustomlist;
	}

	public Set<BpCustEntUpstreamCustom> getBpCustEntUpstreamCustomlist() {
		return bpCustEntUpstreamCustomlist;
	}

	public void setBpCustEntUpstreamCustomlist(
			Set<BpCustEntUpstreamCustom> bpCustEntUpstreamCustomlist) {
		this.bpCustEntUpstreamCustomlist = bpCustEntUpstreamCustomlist;
	}



	public Integer getUpAndDownCustomId() {
		return upAndDownCustomId;
	}

	public void setUpAndDownCustomId(Integer upAndDownCustomId) {
		this.upAndDownCustomId = upAndDownCustomId;
	}

	/**
	 * 	 * @return String
	 * @hibernate.property column="remarks" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getRemarks() {
		return this.remarks;
	}
	
	/**
	 * Set the remarks
	 */	
	public void setRemarks(String aValue) {
		this.remarks = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="enterpriseId" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof BpCustEntUpanddownstream)) {
			return false;
		}
		BpCustEntUpanddownstream rhs = (BpCustEntUpanddownstream) object;
		return new EqualsBuilder()
				.append(this.upAndDownCustomId, rhs.upAndDownCustomId)
				.append(this.remarks, rhs.remarks)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.upAndDownCustomId) 
				.append(this.remarks) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("upAndDownCustomId", this.upAndDownCustomId) 
				.append("remarks", this.remarks) 
				.toString();
	}



}
