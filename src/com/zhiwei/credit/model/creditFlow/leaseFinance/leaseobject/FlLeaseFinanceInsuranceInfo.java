package com.zhiwei.credit.model.creditFlow.leaseFinance.leaseobject;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * @author 
 *
 */
/**
 * FlLeaseFinanceInsuranceInfo Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class FlLeaseFinanceInsuranceInfo extends com.zhiwei.core.model.BaseModel {

    protected Long insuranceId;
	protected String insuranceName;
	protected String insuranceCompanyName;
	protected String insuranceCode;
	protected java.math.BigDecimal insuranceMoney;
	protected java.util.Date startDate;
	protected java.util.Date endDate;
	protected String insurancePerson;
	protected String insuranceComment;
	protected Long leaseObjectId;


	/**
	 * Default Empty Constructor for class FlLeaseFinanceInsuranceInfo
	 */
	public FlLeaseFinanceInsuranceInfo () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class FlLeaseFinanceInsuranceInfo
	 */
	public FlLeaseFinanceInsuranceInfo (
		 Long in_insuranceId
        ) {
		this.setInsuranceId(in_insuranceId);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="insuranceId" type="java.lang.Long" generator-class="native"
	 */
	public Long getInsuranceId() {
		return this.insuranceId;
	}
	
	/**
	 * Set the insuranceId
	 */	
	public void setInsuranceId(Long aValue) {
		this.insuranceId = aValue;
	}	

	/**
	 * 保险名称	 * @return String
	 * @hibernate.property column="insuranceName" type="java.lang.String" length="255" not-null="true" unique="false"
	 */
	public String getInsuranceName() {
		return this.insuranceName;
	}
	
	/**
	 * Set the insuranceName
	 * @spring.validator type="required"
	 */	
	public void setInsuranceName(String aValue) {
		this.insuranceName = aValue;
	}	

	/**
	 * 保险公司名称	 * @return String
	 * @hibernate.property column="insuranceCompanyName" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getInsuranceCompanyName() {
		return this.insuranceCompanyName;
	}
	
	/**
	 * Set the insuranceCompanyName
	 */	
	public void setInsuranceCompanyName(String aValue) {
		this.insuranceCompanyName = aValue;
	}	

	/**
	 * 保单编号	 * @return String
	 * @hibernate.property column="insuranceCode" type="java.lang.String" length="255" not-null="true" unique="false"
	 */
	public String getInsuranceCode() {
		return this.insuranceCode;
	}
	
	/**
	 * Set the insuranceCode
	 * @spring.validator type="required"
	 */	
	public void setInsuranceCode(String aValue) {
		this.insuranceCode = aValue;
	}	

	/**
	 * 金额（万元）	 * @return java.math.BigDecimal
	 * @hibernate.property column="insuranceMoney" type="java.math.BigDecimal" length="12" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getInsuranceMoney() {
		return this.insuranceMoney;
	}
	
	/**
	 * Set the insuranceMoney
	 */	
	public void setInsuranceMoney(java.math.BigDecimal aValue) {
		this.insuranceMoney = aValue;
	}	

	/**
	 * 保险起始日期	 * @return java.util.Date
	 * @hibernate.property column="startDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getStartDate() {
		return this.startDate;
	}
	
	/**
	 * Set the startDate
	 */	
	public void setStartDate(java.util.Date aValue) {
		this.startDate = aValue;
	}	

	/**
	 * 保险截止日期	 * @return java.util.Date
	 * @hibernate.property column="endDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getEndDate() {
		return this.endDate;
	}
	
	/**
	 * Set the endDate
	 */	
	public void setEndDate(java.util.Date aValue) {
		this.endDate = aValue;
	}	

	/**
	 * 保险受益人	 * @return String
	 * @hibernate.property column="insurancePerson" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getInsurancePerson() {
		return this.insurancePerson;
	}
	
	/**
	 * Set the insurancePerson
	 */	
	public void setInsurancePerson(String aValue) {
		this.insurancePerson = aValue;
	}	

	/**
	 * 备注	 * @return String
	 * @hibernate.property column="insuranceComment" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getInsuranceComment() {
		return this.insuranceComment;
	}
	
	/**
	 * Set the insuranceComment
	 */	
	public void setInsuranceComment(String aValue) {
		this.insuranceComment = aValue;
	}	

	/**
	 * 租赁标的id	 * @return Long
	 * @hibernate.property column="proejctId" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	public Long getLeaseObjectId() {
		return this.leaseObjectId;
	}
	
	/**
	 * Set the proejctId
	 * @spring.validator type="required"
	 */	
	public void setLeaseObjectId(Long aValue) {
		this.leaseObjectId = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof FlLeaseFinanceInsuranceInfo)) {
			return false;
		}
		FlLeaseFinanceInsuranceInfo rhs = (FlLeaseFinanceInsuranceInfo) object;
		return new EqualsBuilder()
				.append(this.insuranceId, rhs.insuranceId)
				.append(this.insuranceName, rhs.insuranceName)
				.append(this.insuranceCompanyName, rhs.insuranceCompanyName)
				.append(this.insuranceCode, rhs.insuranceCode)
				.append(this.insuranceMoney, rhs.insuranceMoney)
				.append(this.startDate, rhs.startDate)
				.append(this.endDate, rhs.endDate)
				.append(this.insurancePerson, rhs.insurancePerson)
				.append(this.insuranceComment, rhs.insuranceComment)
				.append(this.leaseObjectId, rhs.leaseObjectId)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.insuranceId) 
				.append(this.insuranceName) 
				.append(this.insuranceCompanyName) 
				.append(this.insuranceCode) 
				.append(this.insuranceMoney) 
				.append(this.startDate) 
				.append(this.endDate) 
				.append(this.insurancePerson) 
				.append(this.insuranceComment) 
				.append(this.leaseObjectId) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("insuranceId", this.insuranceId) 
				.append("insuranceName", this.insuranceName) 
				.append("insuranceCompanyName", this.insuranceCompanyName) 
				.append("insuranceCode", this.insuranceCode) 
				.append("insuranceMoney", this.insuranceMoney) 
				.append("startDate", this.startDate) 
				.append("endDate", this.endDate) 
				.append("insurancePerson", this.insurancePerson) 
				.append("insuranceComment", this.insuranceComment) 
				.append("proejctId", this.leaseObjectId) 
				.toString();
	}



}
