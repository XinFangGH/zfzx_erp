package com.zhiwei.credit.model.creditFlow.pawn.pawnItems;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Date;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * 
 * @author 
 *
 */
/**
 * VPawnItemsList Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class VPawnItemsList extends com.zhiwei.core.model.BaseModel {

	protected Long pawnItemId;
	protected String pawnItemName;
	protected Integer pawnItemType;
	protected String pawnItemTypeName;
	protected Long projectId;
	protected java.math.BigDecimal pawnItemMoney;
	protected java.util.Date accessTime;
	protected String assessedValuationValue;
	protected String businessType;
	protected Integer counts;
	protected java.math.BigDecimal discountRate;
	protected String remarks;
	protected String specificationsStatus;
	protected String projectName;
	
	protected String projectNumber;
	protected String customerName;
	protected Integer fileCount;//材料分数
	protected String pawnItemStatus;//当物状态    在当：underway，绝当 :vast，完成:finish
	protected String companyName;
	protected Date startDate;
	protected Date intentDate;
	protected String crkstatus;//出入库状态   0在库  1出库
	protected String storageLocation;
	/**
	 * Default Empty Constructor for class VPawnItemsList
	 */

	
	/**
	 * Default Key Fields Constructor for class VPawnItemsList
	 */
	public VPawnItemsList () {
    }

	public String getCrkstatus() {
		return crkstatus;
	}

	public void setCrkstatus(String crkstatus) {
		this.crkstatus = crkstatus;
	}

	public String getStorageLocation() {
		return storageLocation;
	}

	public void setStorageLocation(String storageLocation) {
		this.storageLocation = storageLocation;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getIntentDate() {
		return intentDate;
	}

	public void setIntentDate(Date intentDate) {
		this.intentDate = intentDate;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Integer getFileCount() {
		return fileCount;
	}



	public void setFileCount(Integer fileCount) {
		this.fileCount = fileCount;
	}



	public String getPawnItemStatus() {
		return pawnItemStatus;
	}



	public void setPawnItemStatus(String pawnItemStatus) {
		this.pawnItemStatus = pawnItemStatus;
	}


	/**
	 * 	 * @return Long
	 * @hibernate.property column="pawnItemId" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	public Long getPawnItemId() {
		return this.pawnItemId;
	}
	
	/**
	 * Set the pawnItemId
	 * @spring.validator type="required"
	 */	
	public void setPawnItemId(Long aValue) {
		this.pawnItemId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="pawnItemName" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getPawnItemName() {
		return this.pawnItemName;
	}
	
	/**
	 * Set the pawnItemName
	 */	
	public void setPawnItemName(String aValue) {
		this.pawnItemName = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="pawnItemType" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getPawnItemType() {
		return this.pawnItemType;
	}
	
	/**
	 * Set the pawnItemType
	 */	
	public void setPawnItemType(Integer aValue) {
		this.pawnItemType = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="mortgagenametypeid" type="java.lang.String" length="9" not-null="false" unique="false"
	 */
	public String getPawnItemTypeName() {
		return this.pawnItemTypeName;
	}
	
	/**
	 * Set the mortgagenametypeid
	 */	
	public void setPawnItemTypeName(String aValue) {
		this.pawnItemTypeName = aValue;
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
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="pawnItemMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getPawnItemMoney() {
		return this.pawnItemMoney;
	}
	
	/**
	 * Set the pawnItemMoney
	 */	
	public void setPawnItemMoney(java.math.BigDecimal aValue) {
		this.pawnItemMoney = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="accessTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getAccessTime() {
		return this.accessTime;
	}
	
	/**
	 * Set the accessTime
	 */	
	public void setAccessTime(java.util.Date aValue) {
		this.accessTime = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="assessedValuationValue" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getAssessedValuationValue() {
		return this.assessedValuationValue;
	}
	
	/**
	 * Set the assessedValuationValue
	 */	
	public void setAssessedValuationValue(String aValue) {
		this.assessedValuationValue = aValue;
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
	 * 	 * @return Integer
	 * @hibernate.property column="counts" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getCounts() {
		return this.counts;
	}
	
	/**
	 * Set the counts
	 */	
	public void setCounts(Integer aValue) {
		this.counts = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="discountRate" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getDiscountRate() {
		return this.discountRate;
	}
	
	/**
	 * Set the discountRate
	 */	
	public void setDiscountRate(java.math.BigDecimal aValue) {
		this.discountRate = aValue;
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
	 * 	 * @return String
	 * @hibernate.property column="specificationsStatus" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getSpecificationsStatus() {
		return this.specificationsStatus;
	}
	
	/**
	 * Set the specificationsStatus
	 */	
	public void setSpecificationsStatus(String aValue) {
		this.specificationsStatus = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="projectName" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getProjectName() {
		return this.projectName;
	}
	
	/**
	 * Set the projectName
	 */	
	public void setProjectName(String aValue) {
		this.projectName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="projectNumber" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getProjectNumber() {
		return this.projectNumber;
	}
	
	/**
	 * Set the projectNumber
	 */	
	public void setProjectNumber(String aValue) {
		this.projectNumber = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="customerName" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getCustomerName() {
		return this.customerName;
	}
	
	/**
	 * Set the customerName
	 */	
	public void setCustomerName(String aValue) {
		this.customerName = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof VPawnItemsList)) {
			return false;
		}
		VPawnItemsList rhs = (VPawnItemsList) object;
		return new EqualsBuilder()
				.append(this.pawnItemId, rhs.pawnItemId)
				.append(this.pawnItemName, rhs.pawnItemName)
				.append(this.pawnItemType, rhs.pawnItemType)
				.append(this.pawnItemTypeName, rhs.pawnItemTypeName)
				.append(this.projectId, rhs.projectId)
				.append(this.pawnItemMoney, rhs.pawnItemMoney)
				.append(this.accessTime, rhs.accessTime)
				.append(this.assessedValuationValue, rhs.assessedValuationValue)
				.append(this.businessType, rhs.businessType)
				.append(this.counts, rhs.counts)
				.append(this.discountRate, rhs.discountRate)
				.append(this.remarks, rhs.remarks)
				.append(this.specificationsStatus, rhs.specificationsStatus)
				.append(this.projectName, rhs.projectName)
				.append(this.projectNumber, rhs.projectNumber)
				.append(this.customerName, rhs.customerName)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.pawnItemId) 
				.append(this.pawnItemName) 
				.append(this.pawnItemType) 
				.append(this.pawnItemTypeName) 
				.append(this.projectId) 
				.append(this.pawnItemMoney) 
				.append(this.accessTime) 
				.append(this.assessedValuationValue) 
				.append(this.businessType) 
				.append(this.counts) 
				.append(this.discountRate) 
				.append(this.remarks) 
				.append(this.specificationsStatus) 
				.append(this.projectName) 
				.append(this.projectNumber) 
				.append(this.customerName) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("pawnItemId", this.pawnItemId) 
				.append("pawnItemName", this.pawnItemName) 
				.append("pawnItemType", this.pawnItemType) 
				.append("pawnItemTypeName", this.pawnItemTypeName) 
				.append("projectId", this.projectId) 
				.append("pawnItemMoney", this.pawnItemMoney) 
				.append("accessTime", this.accessTime) 
				.append("assessedValuationValue", this.assessedValuationValue) 
				.append("businessType", this.businessType) 
				.append("counts", this.counts) 
				.append("discountRate", this.discountRate) 
				.append("remarks", this.remarks) 
				.append("specificationsStatus", this.specificationsStatus) 
				.append("projectName", this.projectName) 
				.append("projectNumber", this.projectNumber) 
				.append("customerName", this.customerName) 
				.toString();
	}



}
