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
 * GlRecovery Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class GlRecovery extends com.zhiwei.core.model.BaseModel {

    protected Long recoveryId;
	protected Long projectId;
	protected String businessType;
	protected java.math.BigDecimal compensatoryAmount;
	protected java.math.BigDecimal compensatoryBalance;
	protected java.util.Date compensatoryDate;
	protected java.math.BigDecimal recoveryMoney;
	protected java.util.Date recoveryDate;
	protected String recoverySource;
	protected String recoveryRemarks;


	/**
	 * Default Empty Constructor for class GlRecovery
	 */
	public GlRecovery () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class GlRecovery
	 */
	public GlRecovery (
		 Long in_recoveryId
        ) {
		this.setRecoveryId(in_recoveryId);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="recoveryId" type="java.lang.Long" generator-class="native"
	 */
	public Long getRecoveryId() {
		return this.recoveryId;
	}
	
	/**
	 * Set the recoveryId
	 */	
	public void setRecoveryId(Long aValue) {
		this.recoveryId = aValue;
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
	 * 代偿余额	 * @return java.math.BigDecimal
	 * @hibernate.property column="compensatoryBalance" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getCompensatoryBalance() {
		return this.compensatoryBalance;
	}
	
	/**
	 * Set the compensatoryBalance
	 */	
	public void setCompensatoryBalance(java.math.BigDecimal aValue) {
		this.compensatoryBalance = aValue;
	}	

	/**
	 * 代偿日期	 * @return java.util.Date
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
	 * 偿追金额	 * @return java.math.BigDecimal
	 * @hibernate.property column="recoveryMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getRecoveryMoney() {
		return this.recoveryMoney;
	}
	
	/**
	 * Set the recoveryMoney
	 */	
	public void setRecoveryMoney(java.math.BigDecimal aValue) {
		this.recoveryMoney = aValue;
	}	

	/**
	 * 追偿日期	 * @return java.util.Date
	 * @hibernate.property column="recoveryDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getRecoveryDate() {
		return this.recoveryDate;
	}
	
	/**
	 * Set the recoveryDate
	 */	
	public void setRecoveryDate(java.util.Date aValue) {
		this.recoveryDate = aValue;
	}	

	/**
	 * 追偿资金来源	 * @return String
	 * @hibernate.property column="recoverySource" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getRecoverySource() {
		return this.recoverySource;
	}
	
	/**
	 * Set the recoverySource
	 */	
	public void setRecoverySource(String aValue) {
		this.recoverySource = aValue;
	}	

	/**
	 * 追偿说明	 * @return String
	 * @hibernate.property column="recoveryRemarks" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getRecoveryRemarks() {
		return this.recoveryRemarks;
	}
	
	/**
	 * Set the recoveryRemarks
	 */	
	public void setRecoveryRemarks(String aValue) {
		this.recoveryRemarks = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof GlRecovery)) {
			return false;
		}
		GlRecovery rhs = (GlRecovery) object;
		return new EqualsBuilder()
				.append(this.recoveryId, rhs.recoveryId)
				.append(this.projectId, rhs.projectId)
				.append(this.businessType, rhs.businessType)
				.append(this.compensatoryAmount, rhs.compensatoryAmount)
				.append(this.compensatoryBalance, rhs.compensatoryBalance)
				.append(this.compensatoryDate, rhs.compensatoryDate)
				.append(this.recoveryMoney, rhs.recoveryMoney)
				.append(this.recoveryDate, rhs.recoveryDate)
				.append(this.recoverySource, rhs.recoverySource)
				.append(this.recoveryRemarks, rhs.recoveryRemarks)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.recoveryId) 
				.append(this.projectId) 
				.append(this.businessType) 
				.append(this.compensatoryAmount) 
				.append(this.compensatoryBalance) 
				.append(this.compensatoryDate) 
				.append(this.recoveryMoney) 
				.append(this.recoveryDate) 
				.append(this.recoverySource) 
				.append(this.recoveryRemarks) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("recoveryId", this.recoveryId) 
				.append("projectId", this.projectId) 
				.append("businessType", this.businessType) 
				.append("compensatoryAmount", this.compensatoryAmount) 
				.append("compensatoryBalance", this.compensatoryBalance) 
				.append("compensatoryDate", this.compensatoryDate) 
				.append("recoveryMoney", this.recoveryMoney) 
				.append("recoveryDate", this.recoveryDate) 
				.append("recoverySource", this.recoverySource) 
				.append("recoveryRemarks", this.recoveryRemarks) 
				.toString();
	}



}
