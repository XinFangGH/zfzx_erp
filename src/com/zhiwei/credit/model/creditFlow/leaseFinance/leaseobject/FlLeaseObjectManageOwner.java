package com.zhiwei.credit.model.creditFlow.leaseFinance.leaseobject;
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
 * FlLeaseObjectManageOwner Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class FlLeaseObjectManageOwner extends com.zhiwei.core.model.BaseModel {

    protected Long recordId;
	protected Long leaseObjectId;
	protected String oldOwner;
	protected String changeReason;
	protected String newOwner;


	/**
	 * Default Empty Constructor for class FlLeaseObjectManageOwner
	 */
	public FlLeaseObjectManageOwner () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class FlLeaseObjectManageOwner
	 */
	public FlLeaseObjectManageOwner (
		 Long in_recordId
        ) {
		this.setRecordId(in_recordId);
    }

    

	/**
	 * id	 * @return Long
     * @hibernate.id column="recordId" type="java.lang.Long" generator-class="native"
	 */
	public Long getRecordId() {
		return this.recordId;
	}
	
	/**
	 * Set the recordId
	 */	
	public void setRecordId(Long aValue) {
		this.recordId = aValue;
	}	

	/**
	 * 租赁物id	 * @return Long
	 * @hibernate.property column="leaseObjectId" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	public Long getLeaseObjectId() {
		return this.leaseObjectId;
	}
	
	/**
	 * Set the leaseObjectId
	 * @spring.validator type="required"
	 */	
	public void setLeaseObjectId(Long aValue) {
		this.leaseObjectId = aValue;
	}	

	/**
	 * 原所有权人	 * @return String
	 * @hibernate.property column="oldOwner" type="java.lang.String" length="255" not-null="true" unique="false"
	 */
	public String getOldOwner() {
		return this.oldOwner;
	}
	
	/**
	 * Set the oldOwner
	 * @spring.validator type="required"
	 */	
	public void setOldOwner(String aValue) {
		this.oldOwner = aValue;
	}	

	/**
	 * 变更原因说明	 * @return String
	 * @hibernate.property column="changeReason" type="java.lang.String" length="500" not-null="false" unique="false"
	 */
	public String getChangeReason() {
		return this.changeReason;
	}
	
	/**
	 * Set the changeReason
	 */	
	public void setChangeReason(String aValue) {
		this.changeReason = aValue;
	}	

	/**
	 * 变更后所有权人	 * @return String
	 * @hibernate.property column="newOwner" type="java.lang.String" length="255" not-null="true" unique="false"
	 */
	public String getNewOwner() {
		return this.newOwner;
	}
	
	/**
	 * Set the newOwner
	 * @spring.validator type="required"
	 */	
	public void setNewOwner(String aValue) {
		this.newOwner = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof FlLeaseObjectManageOwner)) {
			return false;
		}
		FlLeaseObjectManageOwner rhs = (FlLeaseObjectManageOwner) object;
		return new EqualsBuilder()
				.append(this.recordId, rhs.recordId)
				.append(this.leaseObjectId, rhs.leaseObjectId)
				.append(this.oldOwner, rhs.oldOwner)
				.append(this.changeReason, rhs.changeReason)
				.append(this.newOwner, rhs.newOwner)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.recordId) 
				.append(this.leaseObjectId) 
				.append(this.oldOwner) 
				.append(this.changeReason) 
				.append(this.newOwner) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("recordId", this.recordId) 
				.append("leaseObjectId", this.leaseObjectId) 
				.append("oldOwner", this.oldOwner) 
				.append("changeReason", this.changeReason) 
				.append("newOwner", this.newOwner) 
				.toString();
	}



}
