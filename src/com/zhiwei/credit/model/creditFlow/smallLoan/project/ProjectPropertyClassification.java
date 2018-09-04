package com.zhiwei.credit.model.creditFlow.smallLoan.project;
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
 * ProjectPropertyClassification Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class ProjectPropertyClassification extends com.zhiwei.core.model.BaseModel {

    protected Long id;
	protected String areaId;
	protected String guarId;
	protected String yeWuType;
	protected String loansAt;
	protected String loansAtId;
	protected String induId;
	protected String loanIndustry;
	protected String enterpriseIndustryClassified;
	protected Long projectId;
	protected String businessType;
	protected String custScaleId;
	protected String repaymentWayId;


	/**
	 * Default Empty Constructor for class ProjectPropertyClassification
	 */
	public ProjectPropertyClassification () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ProjectPropertyClassification
	 */
	public ProjectPropertyClassification (
		 Long in_id
        ) {
		this.setId(in_id);
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
	 * 	 * @return String
	 * @hibernate.property column="areaId" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getAreaId() {
		return this.areaId;
	}
	
	/**
	 * Set the areaId
	 */	
	public void setAreaId(String aValue) {
		this.areaId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="guarId" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getGuarId() {
		return this.guarId;
	}
	
	/**
	 * Set the guarId
	 */	
	public void setGuarId(String aValue) {
		this.guarId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="yeWuType" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getYeWuType() {
		return this.yeWuType;
	}
	
	/**
	 * Set the yeWuType
	 */	
	public void setYeWuType(String aValue) {
		this.yeWuType = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="loansAt" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getLoansAt() {
		return this.loansAt;
	}
	
	/**
	 * Set the loansAt
	 */	
	public void setLoansAt(String aValue) {
		this.loansAt = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="loansAtId" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getLoansAtId() {
		return this.loansAtId;
	}
	
	/**
	 * Set the loansAtId
	 */	
	public void setLoansAtId(String aValue) {
		this.loansAtId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="induId" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getInduId() {
		return this.induId;
	}
	
	/**
	 * Set the induId
	 */	
	public void setInduId(String aValue) {
		this.induId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="loanIndustry" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getLoanIndustry() {
		return this.loanIndustry;
	}
	
	/**
	 * Set the loanIndustry
	 */	
	public void setLoanIndustry(String aValue) {
		this.loanIndustry = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="enterpriseIndustryClassified" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getEnterpriseIndustryClassified() {
		return this.enterpriseIndustryClassified;
	}
	
	/**
	 * Set the enterpriseIndustryClassified
	 */	
	public void setEnterpriseIndustryClassified(String aValue) {
		this.enterpriseIndustryClassified = aValue;
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
	 * 	 * @return String
	 * @hibernate.property column="custScaleId" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	public String getCustScaleId() {
		return this.custScaleId;
	}
	
	/**
	 * Set the custScaleId
	 */	
	public void setCustScaleId(String aValue) {
		this.custScaleId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="repaymentWayId" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	public String getRepaymentWayId() {
		return this.repaymentWayId;
	}
	
	/**
	 * Set the repaymentWayId
	 */	
	public void setRepaymentWayId(String aValue) {
		this.repaymentWayId = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ProjectPropertyClassification)) {
			return false;
		}
		ProjectPropertyClassification rhs = (ProjectPropertyClassification) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.areaId, rhs.areaId)
				.append(this.guarId, rhs.guarId)
				.append(this.yeWuType, rhs.yeWuType)
				.append(this.loansAt, rhs.loansAt)
				.append(this.loansAtId, rhs.loansAtId)
				.append(this.induId, rhs.induId)
				.append(this.loanIndustry, rhs.loanIndustry)
				.append(this.enterpriseIndustryClassified, rhs.enterpriseIndustryClassified)
				.append(this.projectId, rhs.projectId)
				.append(this.businessType, rhs.businessType)
				.append(this.custScaleId, rhs.custScaleId)
				.append(this.repaymentWayId, rhs.repaymentWayId)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.areaId) 
				.append(this.guarId) 
				.append(this.yeWuType) 
				.append(this.loansAt) 
				.append(this.loansAtId) 
				.append(this.induId) 
				.append(this.loanIndustry) 
				.append(this.enterpriseIndustryClassified) 
				.append(this.projectId) 
				.append(this.businessType) 
				.append(this.custScaleId) 
				.append(this.repaymentWayId) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("areaId", this.areaId) 
				.append("guarId", this.guarId) 
				.append("yeWuType", this.yeWuType) 
				.append("loansAt", this.loansAt) 
				.append("loansAtId", this.loansAtId) 
				.append("induId", this.induId) 
				.append("loanIndustry", this.loanIndustry) 
				.append("enterpriseIndustryClassified", this.enterpriseIndustryClassified) 
				.append("projectId", this.projectId) 
				.append("businessType", this.businessType) 
				.append("custScaleId", this.custScaleId) 
				.append("repaymentWayId", this.repaymentWayId) 
				.toString();
	}



}
