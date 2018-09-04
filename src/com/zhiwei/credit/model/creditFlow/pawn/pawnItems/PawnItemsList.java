package com.zhiwei.credit.model.creditFlow.pawn.pawnItems;
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
 * PawnItemsList Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class PawnItemsList extends com.zhiwei.core.model.BaseModel {

    protected Long pawnItemId;
	protected Integer pawnItemType;//当物类型
	protected String pawnItemName;//当物名称
	protected String specificationsStatus;//规格和状态
	protected Integer counts;//数量
	protected String assessedValuationValue;//评估价值
	protected java.math.BigDecimal discountRate;//折当率
	protected java.math.BigDecimal pawnItemMoney;//当物金额
	protected java.util.Date accessTime;//获取时间
	protected String remarks;
	protected Long projectId;
	protected String businessType;
	protected Integer fileCount;//材料分数
	protected String pawnItemStatus;//当物状态    在当：underway，绝当 :vast，完成:finish
	/**
	 * Default Empty Constructor for class PawnItemsList
	 */
	public PawnItemsList () {
		super();
	}
	
	
	public String getPawnItemStatus() {
		return pawnItemStatus;
	}


	public void setPawnItemStatus(String pawnItemStatus) {
		this.pawnItemStatus = pawnItemStatus;
	}


	public Integer getFileCount() {
		return fileCount;
	}

	public void setFileCount(Integer fileCount) {
		this.fileCount = fileCount;
	}

	/**
	 * Default Key Fields Constructor for class PawnItemsList
	 */
	public PawnItemsList (
		 Long in_pawnItemId
        ) {
		this.setPawnItemId(in_pawnItemId);
    }

    

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	/**
	 * 	 * @return Long
     * @hibernate.id column="pawnItemId" type="java.lang.Long" generator-class="native"
	 */
	public Long getPawnItemId() {
		return this.pawnItemId;
	}
	
	/**
	 * Set the pawnItemId
	 */	
	public void setPawnItemId(Long aValue) {
		this.pawnItemId = aValue;
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
	 * @hibernate.property column="reamrks" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getRemarks() {
		return this.remarks;
	}
	
	/**
	 * Set the reamrks
	 */	
	public void setRemarks(String aValue) {
		this.remarks = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof PawnItemsList)) {
			return false;
		}
		PawnItemsList rhs = (PawnItemsList) object;
		return new EqualsBuilder()
				.append(this.pawnItemId, rhs.pawnItemId)
				.append(this.pawnItemType, rhs.pawnItemType)
				.append(this.pawnItemName, rhs.pawnItemName)
				.append(this.specificationsStatus, rhs.specificationsStatus)
				.append(this.counts, rhs.counts)
				.append(this.assessedValuationValue, rhs.assessedValuationValue)
				.append(this.discountRate, rhs.discountRate)
				.append(this.pawnItemMoney, rhs.pawnItemMoney)
				.append(this.accessTime, rhs.accessTime)
				.append(this.remarks, rhs.remarks)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.pawnItemId) 
				.append(this.pawnItemType) 
				.append(this.pawnItemName) 
				.append(this.specificationsStatus) 
				.append(this.counts) 
				.append(this.assessedValuationValue) 
				.append(this.discountRate) 
				.append(this.pawnItemMoney) 
				.append(this.accessTime) 
				.append(this.remarks) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("pawnItemId", this.pawnItemId) 
				.append("pawnItemType", this.pawnItemType) 
				.append("pawnItemName", this.pawnItemName) 
				.append("specificationsStatus", this.specificationsStatus) 
				.append("counts", this.counts) 
				.append("assessedValuationValue", this.assessedValuationValue) 
				.append("discountRate", this.discountRate) 
				.append("pawnItemMoney", this.pawnItemMoney) 
				.append("accessTime", this.accessTime) 
				.append("reamrks", this.remarks) 
				.toString();
	}



}
