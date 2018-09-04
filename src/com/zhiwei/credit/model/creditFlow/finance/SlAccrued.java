package com.zhiwei.credit.model.creditFlow.finance;
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
 * SlAccrued Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class SlAccrued extends com.zhiwei.core.model.BaseModel {

    protected Long accruedId;
	protected Long projectId;
	protected String businessType;
	protected java.util.Date interestfactDate; //上一次计提参考的已对账时间
	protected java.util.Date consultfactDate; 
	protected java.util.Date accruedDate;
	protected java.math.BigDecimal accruedinterestMoney;
	protected java.math.BigDecimal accruedconsultMoney;
	/**
	 * Default Empty Constructor for class SlAccrued
	 */
	public SlAccrued () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class SlAccrued
	 */
	public SlAccrued (
		 Long in_accruedId
        ) {
		this.setAccruedId(in_accruedId);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="accruedId" type="java.lang.Long" generator-class="native"
	 */
	public Long getAccruedId() {
		return this.accruedId;
	}
	
	/**
	 * Set the accruedId
	 */	
	public void setAccruedId(Long aValue) {
		this.accruedId = aValue;
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

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	/**
	 * 	 * @return String
	 * @hibernate.property column="businessType" type="java.lang.String" length="30" not-null="false" unique="false"
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
	 * 实际到账日/实际还款日	 * @return java.util.Date
	 * @hibernate.property column="factDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="accruedDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getAccruedDate() {
		return this.accruedDate;
	}
	
	/**
	 * Set the accruedDate
	 */	
	public void setAccruedDate(java.util.Date aValue) {
		this.accruedDate = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="accruedMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SlAccrued)) {
			return false;
		}
		SlAccrued rhs = (SlAccrued) object;
		return new EqualsBuilder()
				.append(this.accruedId, rhs.accruedId)
				.append(this.projectId, rhs.projectId)
				.append(this.businessType, rhs.businessType)
				.append(this.accruedDate, rhs.accruedDate)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.accruedId) 
				.append(this.projectId) 
				.append(this.businessType) 
				.append(this.accruedDate) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("accruedId", this.accruedId) 
				.append("projectId", this.projectId) 
				.append("businessType", this.businessType) 
				.append("accruedDate", this.accruedDate) 
				.toString();
	}

	public java.math.BigDecimal getAccruedinterestMoney() {
		return accruedinterestMoney;
	}

	public void setAccruedinterestMoney(java.math.BigDecimal accruedinterestMoney) {
		this.accruedinterestMoney = accruedinterestMoney;
	}

	public java.math.BigDecimal getAccruedconsultMoney() {
		return accruedconsultMoney;
	}

	public void setAccruedconsultMoney(java.math.BigDecimal accruedconsultMoney) {
		this.accruedconsultMoney = accruedconsultMoney;
	}

	public java.util.Date getInterestfactDate() {
		return interestfactDate;
	}

	public void setInterestfactDate(java.util.Date interestfactDate) {
		this.interestfactDate = interestfactDate;
	}

	public java.util.Date getConsultfactDate() {
		return consultfactDate;
	}

	public void setConsultfactDate(java.util.Date consultfactDate) {
		this.consultfactDate = consultfactDate;
	}



}
