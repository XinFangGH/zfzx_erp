package com.zhiwei.credit.model.creditFlow.pawn.project;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
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
 * PawnTicketLossRecord Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class PawnTicketLossRecord extends com.zhiwei.core.model.BaseModel {

    protected Long lossRecordId;
	protected java.util.Date lossTime;
	protected String lossPerson;
	protected java.math.BigDecimal lossCost;
	protected Long operatorId;
	protected String operatorName;
	protected String remarks;
	protected java.util.Date createTime;
	protected Long projectId;
	protected String businessType;


	/**
	 * Default Empty Constructor for class PawnTicketLossRecord
	 */
	public PawnTicketLossRecord () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class PawnTicketLossRecord
	 */
	public PawnTicketLossRecord (
		 Long in_lossRecordId
        ) {
		this.setLossRecordId(in_lossRecordId);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="lossRecordId" type="java.lang.Long" generator-class="native"
	 */
	public Long getLossRecordId() {
		return this.lossRecordId;
	}
	
	/**
	 * Set the lossRecordId
	 */	
	public void setLossRecordId(Long aValue) {
		this.lossRecordId = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="lossTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getLossTime() {
		return this.lossTime;
	}
	
	/**
	 * Set the lossTime
	 */	
	public void setLossTime(java.util.Date aValue) {
		this.lossTime = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="lossPerson" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getLossPerson() {
		return this.lossPerson;
	}
	
	/**
	 * Set the lossPerson
	 */	
	public void setLossPerson(String aValue) {
		this.lossPerson = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="lossCost" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getLossCost() {
		return this.lossCost;
	}
	
	/**
	 * Set the lossCost
	 */	
	public void setLossCost(java.math.BigDecimal aValue) {
		this.lossCost = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="operatorId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getOperatorId() {
		return this.operatorId;
	}
	
	/**
	 * Set the operatorId
	 */	
	public void setOperatorId(Long aValue) {
		this.operatorId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="operatorName" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getOperatorName() {
		return this.operatorName;
	}
	
	/**
	 * Set the operatorName
	 */	
	public void setOperatorName(String aValue) {
		this.operatorName = aValue;
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
	 * 	 * @return java.util.Date
	 * @hibernate.property column="createTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	/**
	 * Set the createTime
	 */	
	public void setCreateTime(java.util.Date aValue) {
		this.createTime = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="projectId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getProjectId() {
		return this.projectId;
	}
	
	/**
	 * Set the projectId
	 */	
	public void setProjectId(Long aValue) {
		this.projectId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="businessType" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getBusinessType() {
		return this.businessType;
	}
	
	/**
	 * Set the businessType
	 */	
	public void setBusinessType(String aValue) {
		this.businessType = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof PawnTicketLossRecord)) {
			return false;
		}
		PawnTicketLossRecord rhs = (PawnTicketLossRecord) object;
		return new EqualsBuilder()
				.append(this.lossRecordId, rhs.lossRecordId)
				.append(this.lossTime, rhs.lossTime)
				.append(this.lossPerson, rhs.lossPerson)
				.append(this.lossCost, rhs.lossCost)
				.append(this.operatorId, rhs.operatorId)
				.append(this.operatorName, rhs.operatorName)
				.append(this.remarks, rhs.remarks)
				.append(this.createTime, rhs.createTime)
				.append(this.projectId, rhs.projectId)
				.append(this.businessType, rhs.businessType)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.lossRecordId) 
				.append(this.lossTime) 
				.append(this.lossPerson) 
				.append(this.lossCost) 
				.append(this.operatorId) 
				.append(this.operatorName) 
				.append(this.remarks) 
				.append(this.createTime) 
				.append(this.projectId) 
				.append(this.businessType) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("lossRecordId", this.lossRecordId) 
				.append("lossTime", this.lossTime) 
				.append("lossPerson", this.lossPerson) 
				.append("lossCost", this.lossCost) 
				.append("operatorId", this.operatorId) 
				.append("operatorName", this.operatorName) 
				.append("remarks", this.remarks) 
				.append("createTime", this.createTime) 
				.append("projectId", this.projectId) 
				.append("businessType", this.businessType) 
				.toString();
	}



}
