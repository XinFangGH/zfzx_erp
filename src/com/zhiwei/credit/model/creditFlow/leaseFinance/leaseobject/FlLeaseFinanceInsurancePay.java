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
 * FlLeaseFinanceInsurancePay Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class FlLeaseFinanceInsurancePay extends com.zhiwei.core.model.BaseModel {

    protected Long id;
	protected String insuranceCode;
	protected String insuranceCompanyName;
	protected String standardSize;
	protected java.util.Date outInsuranceDate;
	protected java.util.Date submitDate;
	protected String outInsuranceReason;
	protected java.math.BigDecimal loseMoney;
	protected java.math.BigDecimal repayMoney;
	protected String status;//[[1, '已经理赔'], [2, '已报案'],[3,'免赔'],[4,'未理赔']]
	protected java.util.Date payDate;
	protected Long leaseObjectId;


	/**
	 * Default Empty Constructor for class FlLeaseFinanceInsurancePay
	 */
	public FlLeaseFinanceInsurancePay () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class FlLeaseFinanceInsurancePay
	 */
	public FlLeaseFinanceInsurancePay (
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
	 * 设备型号	 * @return String
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


	public java.util.Date getOutInsuranceDate() {
		return outInsuranceDate;
	}

	public void setOutInsuranceDate(java.util.Date outInsuranceDate) {
		this.outInsuranceDate = outInsuranceDate;
	}

	/**
	 * 报案时间	 * @return java.util.Date
	 * @hibernate.property column="submitDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getSubmitDate() {
		return this.submitDate;
	}
	
	/**
	 * Set the submitDate
	 */	
	public void setSubmitDate(java.util.Date aValue) {
		this.submitDate = aValue;
	}	

	/**
	 * 出险原因	 * @return String
	 * @hibernate.property column="outInsuranceReason" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getOutInsuranceReason() {
		return this.outInsuranceReason;
	}
	
	/**
	 * Set the outInsuranceReason
	 */	
	public void setOutInsuranceReason(String aValue) {
		this.outInsuranceReason = aValue;
	}	

	/**
	 * 定损金额（元）	 * @return java.math.BigDecimal
	 * @hibernate.property column="loseMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getLoseMoney() {
		return this.loseMoney;
	}
	
	/**
	 * Set the loseMoney
	 */	
	public void setLoseMoney(java.math.BigDecimal aValue) {
		this.loseMoney = aValue;
	}	

	/**
	 * 偿付金额	 * @return java.math.BigDecimal
	 * @hibernate.property column="repayMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getRepayMoney() {
		return this.repayMoney;
	}
	
	/**
	 * Set the repayMoney
	 */	
	public void setRepayMoney(java.math.BigDecimal aValue) {
		this.repayMoney = aValue;
	}	

	/**
	 * 理赔状态	 * @return String
	 * @hibernate.property column="status" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getStatus() {
		return this.status;
	}
	
	/**
	 * Set the status
	 */	
	public void setStatus(String aValue) {
		this.status = aValue;
	}	

	/**
	 * 赔付时间	 * @return java.util.Date
	 * @hibernate.property column="payDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getPayDate() {
		return this.payDate;
	}
	
	/**
	 * Set the payDate
	 */	
	public void setPayDate(java.util.Date aValue) {
		this.payDate = aValue;
	}	

	/**
	 * 租赁物Id	 * @return Long
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
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof FlLeaseFinanceInsurancePay)) {
			return false;
		}
		FlLeaseFinanceInsurancePay rhs = (FlLeaseFinanceInsurancePay) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.insuranceCode, rhs.insuranceCode)
				.append(this.insuranceCompanyName, rhs.insuranceCompanyName)
				.append(this.standardSize, rhs.standardSize)
				.append(this.outInsuranceDate, rhs.outInsuranceDate)
				.append(this.submitDate, rhs.submitDate)
				.append(this.outInsuranceReason, rhs.outInsuranceReason)
				.append(this.loseMoney, rhs.loseMoney)
				.append(this.repayMoney, rhs.repayMoney)
				.append(this.status, rhs.status)
				.append(this.payDate, rhs.payDate)
				.append(this.leaseObjectId, rhs.leaseObjectId)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.insuranceCode) 
				.append(this.insuranceCompanyName) 
				.append(this.standardSize) 
				.append(this.outInsuranceDate) 
				.append(this.submitDate) 
				.append(this.outInsuranceReason) 
				.append(this.loseMoney) 
				.append(this.repayMoney) 
				.append(this.status) 
				.append(this.payDate) 
				.append(this.leaseObjectId) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("insuranceCode", this.insuranceCode) 
				.append("insuranceCompanyName", this.insuranceCompanyName) 
				.append("standardSize", this.standardSize) 
				.append("outInsuranceDate", this.outInsuranceDate) 
				.append("submitDate", this.submitDate) 
				.append("outInsuranceReason", this.outInsuranceReason) 
				.append("loseMoney", this.loseMoney) 
				.append("repayMoney", this.repayMoney) 
				.append("status", this.status) 
				.append("payDate", this.payDate) 
				.append("leaseObjectId", this.leaseObjectId) 
				.toString();
	}



}
