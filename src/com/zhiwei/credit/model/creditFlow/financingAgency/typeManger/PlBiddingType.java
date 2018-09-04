package com.zhiwei.credit.model.creditFlow.financingAgency.typeManger;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
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
 * PlBiddingType Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 招标类型
 */
public class PlBiddingType extends com.zhiwei.core.model.BaseModel {
	/**
	 * 主键
	 */
	@Expose
    protected Long biddingTypeId;
	/**
	 * 名称
	 */
    @Expose
	protected String name;
    /**
	 * key值
	 */
    @Expose
	protected String keyStr;
    /**
	 * 备注
	 */
    @Expose
	protected String remark;

	protected java.util.Set plBidPlans = new java.util.HashSet();

	/**
	 * Default Empty Constructor for class PlBiddingType
	 */
	public PlBiddingType () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class PlBiddingType
	 */
	public PlBiddingType (
		 Long in_biddingTypeId
        ) {
		this.setBiddingTypeId(in_biddingTypeId);
    }


	public java.util.Set getPlBidPlans () {
		return plBidPlans;
	}	
	
	public void setPlBidPlans (java.util.Set in_plBidPlans) {
		this.plBidPlans = in_plBidPlans;
	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="biddingTypeId" type="java.lang.Long" generator-class="native"
	 */
	public Long getBiddingTypeId() {
		return this.biddingTypeId;
	}
	
	/**
	 * Set the biddingTypeId
	 */	
	public void setBiddingTypeId(Long aValue) {
		this.biddingTypeId = aValue;
	}	

	/**
	 * 名称	 * @return String
	 * @hibernate.property column="name" type="java.lang.String" length="20" not-null="false" unique="false"
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Set the name
	 */	
	public void setName(String aValue) {
		this.name = aValue;
	}	

	/**
	 * key	 * @return String
	 * @hibernate.property column="keyStr" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	public String getKeyStr() {
		return this.keyStr;
	}
	
	/**
	 * Set the keyStr
	 */	
	public void setKeyStr(String aValue) {
		this.keyStr = aValue;
	}	

	/**
	 * 描述	 * @return String
	 * @hibernate.property column="remark" type="java.lang.String" length="100" not-null="false" unique="false"
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
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof PlBiddingType)) {
			return false;
		}
		PlBiddingType rhs = (PlBiddingType) object;
		return new EqualsBuilder()
				.append(this.biddingTypeId, rhs.biddingTypeId)
				.append(this.name, rhs.name)
				.append(this.keyStr, rhs.keyStr)
				.append(this.remark, rhs.remark)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.biddingTypeId) 
				.append(this.name) 
				.append(this.keyStr) 
				.append(this.remark) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("biddingTypeId", this.biddingTypeId) 
				.append("name", this.name) 
				.append("keyStr", this.keyStr) 
				.append("remark", this.remark) 
				.toString();
	}



}
