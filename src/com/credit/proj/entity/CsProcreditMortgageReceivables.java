package com.credit.proj.entity;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
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
 * CsProcreditMortgageReceivables Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class CsProcreditMortgageReceivables extends com.zhiwei.core.model.BaseModel {

    protected Long receivablesId;
	protected Long enterpriseId;
	protected String enterpriseName;
	protected String accountsPayCompany;
	protected java.math.BigDecimal receivableMoney;
	protected String contractNumber;
	protected java.util.Date intentDate;
	protected String billNumber;
	protected java.util.Date billDate;
	protected Integer mortgageId;
	protected String objectType;

	/**
	 * Default Empty Constructor for class CsProcreditMortgageReceivables
	 */
	public CsProcreditMortgageReceivables () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class CsProcreditMortgageReceivables
	 */
	public CsProcreditMortgageReceivables (
		 Long in_receivablesId
        ) {
		this.setReceivablesId(in_receivablesId);
    }

    

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	/**
	 * 	 * @return Long
     * @hibernate.id column="receivablesId" type="java.lang.Long" generator-class="native"
	 */
	public Long getReceivablesId() {
		return this.receivablesId;
	}
	
	/**
	 * Set the receivablesId
	 */	
	public void setReceivablesId(Long aValue) {
		this.receivablesId = aValue;
	}	

	/**
	 * 企业Id	 * @return Long
	 * @hibernate.property column="enterpriseId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getEnterpriseId() {
		return this.enterpriseId;
	}
	
	/**
	 * Set the enterpriseId
	 */	
	public void setEnterpriseId(Long aValue) {
		this.enterpriseId = aValue;
	}	

	/**
	 * 企业名称	 * @return String
	 * @hibernate.property column="enterpriseName" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getEnterpriseName() {
		return this.enterpriseName;
	}
	
	/**
	 * Set the enterpriseName
	 */	
	public void setEnterpriseName(String aValue) {
		this.enterpriseName = aValue;
	}	

	/**
	 * 应付账款公司	 * @return String
	 * @hibernate.property column="accountsPayCompany" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getAccountsPayCompany() {
		return this.accountsPayCompany;
	}
	
	/**
	 * Set the accountsPayCompany
	 */	
	public void setAccountsPayCompany(String aValue) {
		this.accountsPayCompany = aValue;
	}	

	/**
	 * 应收金额	 * @return java.math.BigDecimal
	 * @hibernate.property column="receivableMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getReceivableMoney() {
		return this.receivableMoney;
	}
	
	/**
	 * Set the receivableMoney
	 */	
	public void setReceivableMoney(java.math.BigDecimal aValue) {
		this.receivableMoney = aValue;
	}	

	/**
	 * 合同编号	 * @return String
	 * @hibernate.property column="contractNumber" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getContractNumber() {
		return this.contractNumber;
	}
	
	/**
	 * Set the contractNumber
	 */	
	public void setContractNumber(String aValue) {
		this.contractNumber = aValue;
	}	

	/**
	 * 应收账款到期日	 * @return java.util.Date
	 * @hibernate.property column="intentDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getIntentDate() {
		return this.intentDate;
	}
	
	/**
	 * Set the intentDate
	 */	
	public void setIntentDate(java.util.Date aValue) {
		this.intentDate = aValue;
	}	

	/**
	 * 发票号码	 * @return String
	 * @hibernate.property column="billNumber" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getBillNumber() {
		return this.billNumber;
	}
	
	/**
	 * Set the billNumber
	 */	
	public void setBillNumber(String aValue) {
		this.billNumber = aValue;
	}	

	/**
	 * 发票日期	 * @return java.util.Date
	 * @hibernate.property column="billDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getBillDate() {
		return this.billDate;
	}
	
	/**
	 * Set the billDate
	 */	
	public void setBillDate(java.util.Date aValue) {
		this.billDate = aValue;
	}	

	/**
	 * 抵质押物Id	 * @return Integer
	 * @hibernate.property column="mortgageId" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getMortgageId() {
		return this.mortgageId;
	}
	
	/**
	 * Set the mortgageId
	 */	
	public void setMortgageId(Integer aValue) {
		this.mortgageId = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof CsProcreditMortgageReceivables)) {
			return false;
		}
		CsProcreditMortgageReceivables rhs = (CsProcreditMortgageReceivables) object;
		return new EqualsBuilder()
				.append(this.receivablesId, rhs.receivablesId)
				.append(this.enterpriseId, rhs.enterpriseId)
				.append(this.enterpriseName, rhs.enterpriseName)
				.append(this.accountsPayCompany, rhs.accountsPayCompany)
				.append(this.receivableMoney, rhs.receivableMoney)
				.append(this.contractNumber, rhs.contractNumber)
				.append(this.intentDate, rhs.intentDate)
				.append(this.billNumber, rhs.billNumber)
				.append(this.billDate, rhs.billDate)
				.append(this.mortgageId, rhs.mortgageId)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.receivablesId) 
				.append(this.enterpriseId) 
				.append(this.enterpriseName) 
				.append(this.accountsPayCompany) 
				.append(this.receivableMoney) 
				.append(this.contractNumber) 
				.append(this.intentDate) 
				.append(this.billNumber) 
				.append(this.billDate) 
				.append(this.mortgageId) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("receivablesId", this.receivablesId) 
				.append("enterpriseId", this.enterpriseId) 
				.append("enterpriseName", this.enterpriseName) 
				.append("accountsPayCompany", this.accountsPayCompany) 
				.append("receivableMoney", this.receivableMoney) 
				.append("contractNumber", this.contractNumber) 
				.append("intentDate", this.intentDate) 
				.append("billNumber", this.billNumber) 
				.append("billDate", this.billDate) 
				.append("mortgageId", this.mortgageId) 
				.toString();
	}



}
