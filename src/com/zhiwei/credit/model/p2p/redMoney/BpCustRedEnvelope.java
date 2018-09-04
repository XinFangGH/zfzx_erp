package com.zhiwei.credit.model.p2p.redMoney;
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
 * BpCustRedEnvelope Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class BpCustRedEnvelope extends com.zhiwei.core.model.BaseModel {

    protected Long redId;
    /**
     * 红包名称
     */
	protected String name;
	/**
	 * 红包金额
	 */
	protected java.math.BigDecimal distributemoney;
	/**
	 * 已派发金额
	 */
	protected java.math.BigDecimal eddistributemoney;
	/**
	 * 红包人数
	 */
	protected int distributecount;
	/**
	 * 状态，0未派发，1已部分派发，2已全部派发
	 */
	protected Short distributestatus;
	/**
	 * 派发时间
	 */
	protected java.util.Date distributeTime;
	/**
	 * 创建时间
	 */
	protected java.util.Date createTime;
	/**
	 * 备注
	 */
	protected String remarks;


	/**
	 * Default Empty Constructor for class BpCustRedEnvelope
	 */
	public BpCustRedEnvelope () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class BpCustRedEnvelope
	 */
	public BpCustRedEnvelope (
		 Long in_redId
        ) {
		this.setRedId(in_redId);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="redId" type="java.lang.Long" generator-class="native"
	 */
	public Long getRedId() {
		return this.redId;
	}
	
	/**
	 * Set the redId
	 */	
	public void setRedId(Long aValue) {
		this.redId = aValue;
	}	

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public int getDistributecount() {
		return distributecount;
	}

	public void setDistributecount(int distributecount) {
		this.distributecount = distributecount;
	}

	public java.math.BigDecimal getEddistributemoney() {
		return eddistributemoney;
	}

	public void setEddistributemoney(java.math.BigDecimal eddistributemoney) {
		this.eddistributemoney = eddistributemoney;
	}

	/**
	 * 	 * @return String
	 * @hibernate.property column="name" type="java.lang.String" length="50" not-null="false" unique="false"
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
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="distributemoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getDistributemoney() {
		return this.distributemoney;
	}
	
	/**
	 * Set the distributemoney
	 */	
	public void setDistributemoney(java.math.BigDecimal aValue) {
		this.distributemoney = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="distributestatus" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getDistributestatus() {
		return this.distributestatus;
	}
	
	/**
	 * Set the distributestatus
	 */	
	public void setDistributestatus(Short aValue) {
		this.distributestatus = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="distributeTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getDistributeTime() {
		return this.distributeTime;
	}
	
	/**
	 * Set the distributeTime
	 */	
	public void setDistributeTime(java.util.Date aValue) {
		this.distributeTime = aValue;
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
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof BpCustRedEnvelope)) {
			return false;
		}
		BpCustRedEnvelope rhs = (BpCustRedEnvelope) object;
		return new EqualsBuilder()
				.append(this.redId, rhs.redId)
				.append(this.name, rhs.name)
				.append(this.distributemoney, rhs.distributemoney)
				.append(this.distributestatus, rhs.distributestatus)
				.append(this.distributeTime, rhs.distributeTime)
				.append(this.remarks, rhs.remarks)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.redId) 
				.append(this.name) 
				.append(this.distributemoney) 
				.append(this.distributestatus) 
				.append(this.distributeTime) 
				.append(this.remarks) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("redId", this.redId) 
				.append("name", this.name) 
				.append("distributemoney", this.distributemoney) 
				.append("distributestatus", this.distributestatus) 
				.append("distributeTime", this.distributeTime) 
				.append("remarks", this.remarks) 
				.toString();
	}



}
