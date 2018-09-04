package com.zhiwei.credit.model.creditFlow.guarantee.EnterpriseBusiness;
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
 * GlCompensatory Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class GlCompensatory extends com.zhiwei.core.model.BaseModel {

    protected Long compensatoryId;
	protected Long projectId;
	protected String businessType;
	protected java.math.BigDecimal loanMoney;
	protected java.math.BigDecimal bondMoney;
	protected java.math.BigDecimal compensatoryAmount;
	protected java.util.Date compensatoryDate;
	protected String compensatoryRemarks;


	/**
	 * Default Empty Constructor for class GlCompensatory
	 */
	public GlCompensatory () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class GlCompensatory
	 */
	public GlCompensatory (
		 Long in_compensatoryId
        ) {
		this.setCompensatoryId(in_compensatoryId);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="compensatoryId" type="java.lang.Long" generator-class="native"
	 */
	public Long getCompensatoryId() {
		return this.compensatoryId;
	}
	
	/**
	 * Set the compensatoryId
	 */	
	public void setCompensatoryId(Long aValue) {
		this.compensatoryId = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="projectId" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	public Long getProjectId() {
		return this.projectId;
	}
	
	/**
	 * Set the projectId
	 * @spring.validator type="required"
	 */	
	public void setProjectId(Long aValue) {
		this.projectId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="businessType" type="java.lang.String" length="50" not-null="true" unique="false"
	 */
	public String getBusinessType() {
		return this.businessType;
	}
	
	/**
	 * Set the businessType
	 * @spring.validator type="required"
	 */	
	public void setBusinessType(String aValue) {
		this.businessType = aValue;
	}	

	/**
	 * 贷款余额	 * @return java.math.BigDecimal
	 * @hibernate.property column="loanMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getLoanMoney() {
		return this.loanMoney;
	}
	
	/**
	 * Set the loanMoney
	 */	
	public void setLoanMoney(java.math.BigDecimal aValue) {
		this.loanMoney = aValue;
	}	

	/**
	 * 证金保余额	 * @return java.math.BigDecimal
	 * @hibernate.property column="BondMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getBondMoney() {
		return this.bondMoney;
	}
	
	/**
	 * Set the BondMoney
	 */	
	public void setBondMoney(java.math.BigDecimal aValue) {
		this.bondMoney = aValue;
	}	

	/**
	 * 代偿金额	 * @return java.math.BigDecimal
	 * @hibernate.property column="compensatoryAmount" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getCompensatoryAmount() {
		return this.compensatoryAmount;
	}
	
	/**
	 * Set the compensatoryAmount
	 */	
	public void setCompensatoryAmount(java.math.BigDecimal aValue) {
		this.compensatoryAmount = aValue;
	}	

	/**
	 * 代偿时间	 * @return java.util.Date
	 * @hibernate.property column="compensatoryDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getCompensatoryDate() {
		return this.compensatoryDate;
	}
	
	/**
	 * Set the compensatoryDate
	 */	
	public void setCompensatoryDate(java.util.Date aValue) {
		this.compensatoryDate = aValue;
	}	

	/**
	 * 代偿说明	 * @return String
	 * @hibernate.property column="compensatoryRemarks" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getCompensatoryRemarks() {
		return this.compensatoryRemarks;
	}
	
	/**
	 * Set the compensatoryRemarks
	 */	
	public void setCompensatoryRemarks(String aValue) {
		this.compensatoryRemarks = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof GlCompensatory)) {
			return false;
		}
		GlCompensatory rhs = (GlCompensatory) object;
		return new EqualsBuilder()
				.append(this.compensatoryId, rhs.compensatoryId)
				.append(this.projectId, rhs.projectId)
				.append(this.businessType, rhs.businessType)
				.append(this.loanMoney, rhs.loanMoney)
				.append(this.bondMoney, rhs.bondMoney)
				.append(this.compensatoryAmount, rhs.compensatoryAmount)
				.append(this.compensatoryDate, rhs.compensatoryDate)
				.append(this.compensatoryRemarks, rhs.compensatoryRemarks)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.compensatoryId) 
				.append(this.projectId) 
				.append(this.businessType) 
				.append(this.loanMoney) 
				.append(this.bondMoney) 
				.append(this.compensatoryAmount) 
				.append(this.compensatoryDate) 
				.append(this.compensatoryRemarks) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("compensatoryId", this.compensatoryId) 
				.append("projectId", this.projectId) 
				.append("businessType", this.businessType) 
				.append("loanMoney", this.loanMoney) 
				.append("BondMoney", this.bondMoney) 
				.append("compensatoryAmount", this.compensatoryAmount) 
				.append("compensatoryDate", this.compensatoryDate) 
				.append("compensatoryRemarks", this.compensatoryRemarks) 
				.toString();
	}



}
