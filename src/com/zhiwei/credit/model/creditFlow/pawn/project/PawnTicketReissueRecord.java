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
 * PawnTicketReissueRecord Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class PawnTicketReissueRecord extends com.zhiwei.core.model.BaseModel {

    protected Long reissueRecordId;
	protected java.util.Date reissueTime;
	protected String reissuePerson;
	protected Long operatorId;
	protected String operatorName;
	protected String remarks;
	protected Long projectId;
	protected String businessType;
	protected Long lossRecordId;


	/**
	 * Default Empty Constructor for class PawnTicketReissueRecord
	 */
	public PawnTicketReissueRecord () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class PawnTicketReissueRecord
	 */
	public PawnTicketReissueRecord (
		 Long in_reissueRecordId
        ) {
		this.setReissueRecordId(in_reissueRecordId);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="reissueRecordId" type="java.lang.Long" generator-class="native"
	 */
	public Long getReissueRecordId() {
		return this.reissueRecordId;
	}
	
	/**
	 * Set the reissueRecordId
	 */	
	public void setReissueRecordId(Long aValue) {
		this.reissueRecordId = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="reissueTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getReissueTime() {
		return this.reissueTime;
	}
	
	/**
	 * Set the reissueTime
	 */	
	public void setReissueTime(java.util.Date aValue) {
		this.reissueTime = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="reissuePerson" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getReissuePerson() {
		return this.reissuePerson;
	}
	
	/**
	 * Set the reissuePerson
	 */	
	public void setReissuePerson(String aValue) {
		this.reissuePerson = aValue;
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
	 * @hibernate.property column="operatorName" type="java.lang.String" length="100" not-null="false" unique="false"
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
	 * 	 * @return Long
	 * @hibernate.property column="lossRecordId" type="java.lang.Long" length="19" not-null="false" unique="false"
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
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof PawnTicketReissueRecord)) {
			return false;
		}
		PawnTicketReissueRecord rhs = (PawnTicketReissueRecord) object;
		return new EqualsBuilder()
				.append(this.reissueRecordId, rhs.reissueRecordId)
				.append(this.reissueTime, rhs.reissueTime)
				.append(this.reissuePerson, rhs.reissuePerson)
				.append(this.operatorId, rhs.operatorId)
				.append(this.operatorName, rhs.operatorName)
				.append(this.remarks, rhs.remarks)
				.append(this.projectId, rhs.projectId)
				.append(this.businessType, rhs.businessType)
				.append(this.lossRecordId, rhs.lossRecordId)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.reissueRecordId) 
				.append(this.reissueTime) 
				.append(this.reissuePerson) 
				.append(this.operatorId) 
				.append(this.operatorName) 
				.append(this.remarks) 
				.append(this.projectId) 
				.append(this.businessType) 
				.append(this.lossRecordId) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("reissueRecordId", this.reissueRecordId) 
				.append("reissueTime", this.reissueTime) 
				.append("reissuePerson", this.reissuePerson) 
				.append("operatorId", this.operatorId) 
				.append("operatorName", this.operatorName) 
				.append("remarks", this.remarks) 
				.append("projectId", this.projectId) 
				.append("businessType", this.businessType) 
				.append("lossRecordId", this.lossRecordId) 
				.toString();
	}



}
