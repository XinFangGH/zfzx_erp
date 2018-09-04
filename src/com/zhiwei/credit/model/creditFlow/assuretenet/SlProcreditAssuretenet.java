package com.zhiwei.credit.model.creditFlow.assuretenet;
/*
 *  北京互融时代软件有限公司 hurong协同办公管理系统   -- http://www.hurongtime.com/
 *  Copyright (C) 2008-2009 BEIJING JINZHIWANWEI SOFTWARES LIMITED COMPANY
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * SlProcreditAssuretenet Base Java Bean, base class for the.oa.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class SlProcreditAssuretenet extends com.zhiwei.core.model.BaseModel {

	private static final long serialVersionUID = 1L;
	protected Long assuretenetId;
	protected String assuretenet;
	protected String businessmanageropinion;
	protected String riskmanageropinion;
	protected String projid;
	protected String sortvalue;
	protected String businessTypeKey;
	//产品Id
	protected Long  productId;
	//贷款条件配置项的id
	protected Long settingId;
	
	public Long getSettingId() {
		return settingId;
	}

	public void setSettingId(Long settingId) {
		this.settingId = settingId;
	}

	

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getBusinessTypeKey() {
		return businessTypeKey;
	}

	public void setBusinessTypeKey(String businessTypeKey) {
		this.businessTypeKey = businessTypeKey;
	}

	/**
	 * Default Empty Constructor for class SlProcreditAssuretenet
	 */
	public SlProcreditAssuretenet () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class SlProcreditAssuretenet
	 */
	public SlProcreditAssuretenet (
			Long in_id
        ) {
		this.setAssuretenetId(in_id);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="SlProcreditAssuretenetId" type="java.lang.Long" generator-class="native"
	 */
	public Long getAssuretenetId() {
		return this.assuretenetId;
	}
	
	/**
	 * Set the id
	 */	
	public void setAssuretenetId(Long aValue) {
		this.assuretenetId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="assuretenet" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getAssuretenet() {
		return this.assuretenet;
	}
	
	/**
	 * Set the assuretenet
	 */	
	public void setAssuretenet(String aValue) {
		this.assuretenet = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="businessmanageropinion" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	public String getBusinessmanageropinion() {
		return this.businessmanageropinion;
	}
	
	/**
	 * Set the businessmanageropinion
	 */	
	public void setBusinessmanageropinion(String aValue) {
		this.businessmanageropinion = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="riskmanageropinion" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	public String getRiskmanageropinion() {
		return this.riskmanageropinion;
	}
	
	/**
	 * Set the riskmanageropinion
	 */	
	public void setRiskmanageropinion(String aValue) {
		this.riskmanageropinion = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="projid" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getProjid() {
		return this.projid;
	}
	
	/**
	 * Set the projid
	 */	
	public void setProjid(String aValue) {
		this.projid = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="sortvalue" type="java.lang.String" length="10" not-null="false" unique="false"
	 */
	public String getSortvalue() {
		return this.sortvalue;
	}
	
	/**
	 * Set the sortvalue
	 */	
	public void setSortvalue(String aValue) {
		this.sortvalue = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SlProcreditAssuretenet)) {
			return false;
		}
		SlProcreditAssuretenet rhs = (SlProcreditAssuretenet) object;
		return new EqualsBuilder()
				.append(this.assuretenetId, rhs.assuretenetId)
				.append(this.assuretenet, rhs.assuretenet)
				.append(this.businessmanageropinion, rhs.businessmanageropinion)
				.append(this.riskmanageropinion, rhs.riskmanageropinion)
				.append(this.projid, rhs.projid)
				.append(this.sortvalue, rhs.sortvalue)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.assuretenetId) 
				.append(this.assuretenet) 
				.append(this.businessmanageropinion) 
				.append(this.riskmanageropinion) 
				.append(this.projid) 
				.append(this.sortvalue) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("SlProcreditAssuretenetId", this.assuretenetId) 
				.append("assuretenet", this.assuretenet) 
				.append("businessmanageropinion", this.businessmanageropinion) 
				.append("riskmanageropinion", this.riskmanageropinion) 
				.append("projid", this.projid) 
				.append("sortvalue", this.sortvalue) 
				.toString();
	}



}
