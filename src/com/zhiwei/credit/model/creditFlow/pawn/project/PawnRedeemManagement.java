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
 * PawnRedeemManagement Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class PawnRedeemManagement extends com.zhiwei.core.model.BaseModel {

    protected Long redeemId;
	protected String redeemNum;
	protected Long redeemWay;
	protected Long managerId;
	protected String managerName;
	protected java.util.Date redeemDate;
	protected String remarks;
	protected Long projectId;
	protected String businessType;
	protected String redeemWayValue;

	public String getRedeemWayValue() {
		return redeemWayValue;
	}

	public void setRedeemWayValue(String redeemWayValue) {
		this.redeemWayValue = redeemWayValue;
	}

	/**
	 * Default Empty Constructor for class PawnRedeemManagement
	 */
	public PawnRedeemManagement () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class PawnRedeemManagement
	 */
	public PawnRedeemManagement (
		 Long in_redeemId
        ) {
		this.setRedeemId(in_redeemId);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="redeemId" type="java.lang.Long" generator-class="native"
	 */
	public Long getRedeemId() {
		return this.redeemId;
	}
	
	/**
	 * Set the redeemId
	 */	
	public void setRedeemId(Long aValue) {
		this.redeemId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="redeemNum" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getRedeemNum() {
		return this.redeemNum;
	}
	
	/**
	 * Set the redeemNum
	 */	
	public void setRedeemNum(String aValue) {
		this.redeemNum = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="redeemWay" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getRedeemWay() {
		return this.redeemWay;
	}
	
	/**
	 * Set the redeemWay
	 */	
	public void setRedeemWay(Long aValue) {
		this.redeemWay = aValue;
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
	 * @hibernate.property column="redeemDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getRedeemDate() {
		return this.redeemDate;
	}
	
	/**
	 * Set the redeemDate
	 */	
	public void setRedeemDate(java.util.Date aValue) {
		this.redeemDate = aValue;
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
	 * @hibernate.property column="businessType" type="java.lang.String" length="50" not-null="false" unique="false"
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
		if (!(object instanceof PawnRedeemManagement)) {
			return false;
		}
		PawnRedeemManagement rhs = (PawnRedeemManagement) object;
		return new EqualsBuilder()
				.append(this.redeemId, rhs.redeemId)
				.append(this.redeemNum, rhs.redeemNum)
				.append(this.redeemWay, rhs.redeemWay)
				.append(this.managerId, rhs.managerId)
				.append(this.managerName, rhs.managerName)
				.append(this.redeemDate, rhs.redeemDate)
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
				.append(this.redeemId) 
				.append(this.redeemNum) 
				.append(this.redeemWay) 
				.append(this.managerId) 
				.append(this.managerName) 
				.append(this.redeemDate) 
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
				.append("redeemId", this.redeemId) 
				.append("redeemNum", this.redeemNum) 
				.append("redeemWay", this.redeemWay) 
				.append("managerId", this.managerId) 
				.append("managerName", this.managerName) 
				.append("redeemDate", this.redeemDate) 
				.append("remarks", this.remarks) 
				.append("projectId", this.projectId) 
				.append("businessType", this.businessType) 
				.toString();
	}



}
