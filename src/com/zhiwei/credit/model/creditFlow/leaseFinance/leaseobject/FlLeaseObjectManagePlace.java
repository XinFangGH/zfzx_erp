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
 * FlLeaseObjectManagePlace Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class FlLeaseObjectManagePlace extends com.zhiwei.core.model.BaseModel {

    protected Long id;
	protected Long leaseObjectId;
	protected String oldPlace;
	protected String destPlace;
	protected java.util.Date moveDate;
	protected String operationPersonId;
	protected java.util.Date operationDate;
	protected String standardSize;
	
	protected String operationPersonName;//避免视图添加 无数据库对应字段  返回前要set


	/**
	 * Default Empty Constructor for class FlLeaseObjectManagePlace
	 */
	public FlLeaseObjectManagePlace () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class FlLeaseObjectManagePlace
	 */
	public FlLeaseObjectManagePlace (
		 Long in_id
        ) {
		this.setId(in_id);
    }

	
    


	public String getOperationPersonName() {
		return operationPersonName;
	}

	public void setOperationPersonName(String operationPersonName) {
		this.operationPersonName = operationPersonName;
	}

	/**
	 * 	 * @return Long
     * @hibernate.id column="id" type="java.lang.Long" generator-class="native"
	 */
	public Long getId() {
		return this.id;
	}
	
	/**
	 * Set the id
	 */	
	public void setId(Long aValue) {
		this.id = aValue;
	}	

	/**
	 * 租赁物id	 * @return Long
	 * @hibernate.property column="leaseObjectId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getLeaseObjectId() {
		return this.leaseObjectId;
	}
	
	/**
	 * Set the leaseObjectId
	 */	
	public void setLeaseObjectId(Long aValue) {
		this.leaseObjectId = aValue;
	}	

	/**
	 * 原始存放地点	 * @return String
	 * @hibernate.property column="oldPlace" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getOldPlace() {
		return this.oldPlace;
	}
	
	/**
	 * Set the oldPlace
	 */	
	public void setOldPlace(String aValue) {
		this.oldPlace = aValue;
	}	

	/**
	 * 转移地点	 * @return String
	 * @hibernate.property column="destPlace" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getDestPlace() {
		return this.destPlace;
	}
	
	/**
	 * Set the destPlace
	 */	
	public void setDestPlace(String aValue) {
		this.destPlace = aValue;
	}	

	/**
	 * 转移时间	 * @return java.util.Date
	 * @hibernate.property column="moveDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getMoveDate() {
		return this.moveDate;
	}
	
	/**
	 * Set the moveDate
	 */	
	public void setMoveDate(java.util.Date aValue) {
		this.moveDate = aValue;
	}	

	/**
	 * 操作人id	 * @return String
	 * @hibernate.property column="operationPersonId" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getOperationPersonId() {
		return this.operationPersonId;
	}
	
	/**
	 * Set the operationPersonId
	 */	
	public void setOperationPersonId(String aValue) {
		this.operationPersonId = aValue;
	}	

	/**
	 * 操作时间	 * @return java.util.Date
	 * @hibernate.property column="operationDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getOperationDate() {
		return this.operationDate;
	}
	
	/**
	 * Set the operationDate
	 */	
	public void setOperationDate(java.util.Date aValue) {
		this.operationDate = aValue;
	}	

	/**
	 * 规格型号	 * @return String
	 * @hibernate.property column="standardSize" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getStandardSize() {
		return this.standardSize;
	}
	
	/**
	 * Set the standardSize
	 */	
	public void setStandardSize(String aValue) {
		this.standardSize = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof FlLeaseObjectManagePlace)) {
			return false;
		}
		FlLeaseObjectManagePlace rhs = (FlLeaseObjectManagePlace) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.leaseObjectId, rhs.leaseObjectId)
				.append(this.oldPlace, rhs.oldPlace)
				.append(this.destPlace, rhs.destPlace)
				.append(this.moveDate, rhs.moveDate)
				.append(this.operationPersonId, rhs.operationPersonId)
				.append(this.operationDate, rhs.operationDate)
				.append(this.standardSize, rhs.standardSize)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.leaseObjectId) 
				.append(this.oldPlace) 
				.append(this.destPlace) 
				.append(this.moveDate) 
				.append(this.operationPersonId) 
				.append(this.operationDate) 
				.append(this.standardSize) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("leaseObjectId", this.leaseObjectId) 
				.append("oldPlace", this.oldPlace) 
				.append("destPlace", this.destPlace) 
				.append("moveDate", this.moveDate) 
				.append("operationPersonId", this.operationPersonId) 
				.append("operationDate", this.operationDate) 
				.append("standardSize", this.standardSize) 
				.toString();
	}



}
