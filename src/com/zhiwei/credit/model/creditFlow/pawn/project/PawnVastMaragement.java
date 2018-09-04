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
 * PawnVastMaragement Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class PawnVastMaragement extends com.zhiwei.core.model.BaseModel {

    protected Long vastId;
	protected String vastNumber;
	protected Long vastWay;
	protected Long managerId;
	protected String managerName;
	protected java.util.Date vastDate;
	protected String remarks;
	protected Long projectId;
	protected String businessType;
	protected String vastWayValue;

	/**
	 * Default Empty Constructor for class PawnVastMaragement
	 */
	public PawnVastMaragement () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class PawnVastMaragement
	 */
	public PawnVastMaragement (
		 Long in_vastId
        ) {
		this.setVastId(in_vastId);
    }

    

	public String getVastWayValue() {
		return vastWayValue;
	}

	public void setVastWayValue(String vastWayValue) {
		this.vastWayValue = vastWayValue;
	}

	/**
	 * 	 * @return Long
     * @hibernate.id column="vastId" type="java.lang.Long" generator-class="native"
	 */
	public Long getVastId() {
		return this.vastId;
	}
	
	/**
	 * Set the vastId
	 */	
	public void setVastId(Long aValue) {
		this.vastId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="vastNumber" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getVastNumber() {
		return this.vastNumber;
	}
	
	/**
	 * Set the vastNumber
	 */	
	public void setVastNumber(String aValue) {
		this.vastNumber = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="vastWay" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getVastWay() {
		return this.vastWay;
	}
	
	/**
	 * Set the vastWay
	 */	
	public void setVastWay(Long aValue) {
		this.vastWay = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="managerId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getManagerId() {
		return this.managerId;
	}
	
	/**
	 * Set the managerId
	 */	
	public void setManagerId(Long aValue) {
		this.managerId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="managerName" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getManagerName() {
		return this.managerName;
	}
	
	/**
	 * Set the managerName
	 */	
	public void setManagerName(String aValue) {
		this.managerName = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="vastDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getVastDate() {
		return this.vastDate;
	}
	
	/**
	 * Set the vastDate
	 */	
	public void setVastDate(java.util.Date aValue) {
		this.vastDate = aValue;
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
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof PawnVastMaragement)) {
			return false;
		}
		PawnVastMaragement rhs = (PawnVastMaragement) object;
		return new EqualsBuilder()
				.append(this.vastId, rhs.vastId)
				.append(this.vastNumber, rhs.vastNumber)
				.append(this.vastWay, rhs.vastWay)
				.append(this.managerId, rhs.managerId)
				.append(this.managerName, rhs.managerName)
				.append(this.vastDate, rhs.vastDate)
				.append(this.remarks, rhs.remarks)
				.append(this.projectId, rhs.projectId)
				.append(this.businessType, rhs.businessType)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.vastId) 
				.append(this.vastNumber) 
				.append(this.vastWay) 
				.append(this.managerId) 
				.append(this.managerName) 
				.append(this.vastDate) 
				.append(this.remarks) 
				.append(this.projectId) 
				.append(this.businessType) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("vastId", this.vastId) 
				.append("vastNumber", this.vastNumber) 
				.append("vastWay", this.vastWay) 
				.append("managerId", this.managerId) 
				.append("managerName", this.managerName) 
				.append("vastDate", this.vastDate) 
				.append("remarks", this.remarks) 
				.append("projectId", this.projectId) 
				.append("businessType", this.businessType) 
				.toString();
	}



}
