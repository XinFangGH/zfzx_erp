package com.zhiwei.credit.model.customer;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

import com.google.gson.annotations.Expose;

/**
 * Customer Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * ������������
 */
public class Customer extends com.zhiwei.core.model.BaseModel {

	@Expose
    protected Long customerId;
	@Expose
	protected String customerNo;
	@Expose
	protected String industryType;
	@Expose
	protected String customerSource;
	@Expose
	protected Short customerType;
	@Expose
	protected Integer companyScale ;//公司规模
	@Expose
	protected String customerName;
	@Expose
	protected String customerManager;
	@Expose
	protected String phone;
	@Expose
	protected String fax;
	@Expose
	protected String site;
	@Expose
	protected String email;
	@Expose
	protected String state;
	@Expose
	protected String city;
	@Expose
	protected String zip;
	@Expose
	protected String address;
	@Expose
	protected java.math.BigDecimal registerFun;
	@Expose
	protected java.math.BigDecimal turnOver;
	@Expose
	protected String currencyUnit;
	@Expose
	protected String otherDesc;
	@Expose
	protected String principal;
	@Expose
	protected String openBank;
	@Expose
	protected String accountsNo;
	@Expose
	protected String taxNo;
	@Expose
	protected String notes;
	@Expose
	protected Short rights;

	protected java.util.Set<CusLinkman> cusLinkmans = new java.util.HashSet<CusLinkman>();

	protected java.util.Set<Project> projects = new java.util.HashSet<Project>();

	protected java.util.Set<CusConnection> cusConnections = new java.util.HashSet<CusConnection>();

	/**
	 * Default Empty Constructor for class Customer
	 */
	public Customer () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Customer
	 */
	public Customer (
		 Long in_customerId
        ) {
		this.setCustomerId(in_customerId);
    }


	public java.util.Set<CusLinkman> getCusLinkmans () {
		return cusLinkmans;
	}	
	
	public void setCusLinkmans (java.util.Set<CusLinkman> in_cusLinkmans) {
		this.cusLinkmans = in_cusLinkmans;
	}
    

	public java.util.Set<Project> getProjects() {
		return projects;
	}

	public void setProjects(java.util.Set<Project> projects) {
		this.projects = projects;
	}

	public java.util.Set<CusConnection> getCusConnections() {
		return cusConnections;
	}

	public void setCusConnections(java.util.Set<CusConnection> cusConnections) {
		this.cusConnections = cusConnections;
	}

	/**
	 * 	 * @return Long
     * @hibernate.id column="customerId" type="java.lang.Long" generator-class="native"
	 */
	public Long getCustomerId() {
		return this.customerId;
	}
	
	/**
	 * Set the customerId
	 */	
	public void setCustomerId(Long aValue) {
		this.customerId = aValue;
	}	

	/**
	 * 客户号
            自动生成	 * @return String
	 * @hibernate.property column="customerNo" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getCustomerNo() {
		return this.customerNo;
	}
	
	/**
	 * Set the customerNo
	 * @spring.validator type="required"
	 */	
	public void setCustomerNo(String aValue) {
		this.customerNo = aValue;
	}	

	/**
	 * 所属行业
            有缺省的选择，也可以输入	 * @return String
	 * @hibernate.property column="industryType" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getIndustryType() {
		return this.industryType;
	}
	
	/**
	 * Set the industryType
	 * @spring.validator type="required"
	 */	
	public void setIndustryType(String aValue) {
		this.industryType = aValue;
	}	

	/**
	 * 客户来源
            可编辑，可添加

            电话访问
            网络
            客户或朋友介绍	 * @return String
	 * @hibernate.property column="customerSource" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getCustomerSource() {
		return this.customerSource;
	}
	
	/**
	 * Set the customerSource
	 */	
	public void setCustomerSource(String aValue) {
		this.customerSource = aValue;
	}	

	public Integer getCompanyScale() {
		return companyScale;
	}

	public void setCompanyScale(Integer companyScale) {
		this.companyScale = companyScale;
	}

	/**
	 * 1=正式客户
            2=重要客户
            3＝潜在客户
            4＝无效客户	 * @return Short
	 * @hibernate.property column="customerType" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getCustomerType() {
		return this.customerType;
	}
	
	/**
	 * Set the customerType
	 * @spring.validator type="required"
	 */	
	public void setCustomerType(Short aValue) {
		this.customerType = aValue;
	}	

	/**
	 * 客户名称
            一般为公司名称	 * @return String
	 * @hibernate.property column="customerName" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getCustomerName() {
		return this.customerName;
	}
	
	/**
	 * Set the customerName
	 * @spring.validator type="required"
	 */	
	public void setCustomerName(String aValue) {
		this.customerName = aValue;
	}	

	/**
	 * 负责该客户的经理	 * @return String
	 * @hibernate.property column="customerManager" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getCustomerManager() {
		return this.customerManager;
	}
	
	/**
	 * Set the customerManager
	 * @spring.validator type="required"
	 */	
	public void setCustomerManager(String aValue) {
		this.customerManager = aValue;
	}	

	/**
	 * 电话	 * @return String
	 * @hibernate.property column="phone" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getPhone() {
		return this.phone;
	}
	
	/**
	 * Set the phone
	 * @spring.validator type="required"
	 */	
	public void setPhone(String aValue) {
		this.phone = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="fax" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getFax() {
		return this.fax;
	}
	
	/**
	 * Set the fax
	 */	
	public void setFax(String aValue) {
		this.fax = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="site" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getSite() {
		return this.site;
	}
	
	/**
	 * Set the site
	 */	
	public void setSite(String aValue) {
		this.site = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="email" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getEmail() {
		return this.email;
	}
	
	/**
	 * Set the email
	 */	
	public void setEmail(String aValue) {
		this.email = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="state" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getState() {
		return this.state;
	}
	
	/**
	 * Set the state
	 */	
	public void setState(String aValue) {
		this.state = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="city" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getCity() {
		return this.city;
	}
	
	/**
	 * Set the city
	 */	
	public void setCity(String aValue) {
		this.city = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="zip" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getZip() {
		return this.zip;
	}
	
	/**
	 * Set the zip
	 */	
	public void setZip(String aValue) {
		this.zip = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="address" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getAddress() {
		return this.address;
	}
	
	/**
	 * Set the address
	 */	
	public void setAddress(String aValue) {
		this.address = aValue;
	}	


	public java.math.BigDecimal getRegisterFun() {
		return registerFun;
	}

	public void setRegisterFun(java.math.BigDecimal registerFun) {
		this.registerFun = registerFun;
	}

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="turnOver" type="java.math.BigDecimal" length="10" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getTurnOver() {
		return this.turnOver;
	}
	
	/**
	 * Set the turnOver
	 */	
	public void setTurnOver(java.math.BigDecimal aValue) {
		this.turnOver = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="otherDesc" type="java.lang.String" length="800" not-null="false" unique="false"
	 */
	public String getOtherDesc() {
		return this.otherDesc;
	}
	
	
	public String getCurrencyUnit() {
		return currencyUnit;
	}

	public void setCurrencyUnit(String currencyUnit) {
		this.currencyUnit = currencyUnit;
	}

	/**
	 * Set the otherDesc
	 */	
	public void setOtherDesc(String aValue) {
		this.otherDesc = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="principal" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getPrincipal() {
		return this.principal;
	}
	
	/**
	 * Set the principal
	 */	
	public void setPrincipal(String aValue) {
		this.principal = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="openBank" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getOpenBank() {
		return this.openBank;
	}
	
	/**
	 * Set the openBank
	 */	
	public void setOpenBank(String aValue) {
		this.openBank = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="accountsNo" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getAccountsNo() {
		return this.accountsNo;
	}
	
	/**
	 * Set the accountsNo
	 */	
	public void setAccountsNo(String aValue) {
		this.accountsNo = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="taxNo" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getTaxNo() {
		return this.taxNo;
	}
	
	/**
	 * Set the taxNo
	 */	
	public void setTaxNo(String aValue) {
		this.taxNo = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="notes" type="java.lang.String" length="500" not-null="false" unique="false"
	 */
	public String getNotes() {
		return this.notes;
	}
	
	/**
	 * Set the notes
	 */	
	public void setNotes(String aValue) {
		this.notes = aValue;
	}	

	/**
	 * 1=客户经理及上级经理有权查看
            2=公开
            3=共享人员有权查看	 * @return Short
	 * @hibernate.property column="rights" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getRights() {
		return this.rights;
	}
	
	/**
	 * Set the rights
	 * @spring.validator type="required"
	 */	
	public void setRights(Short aValue) {
		this.rights = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Customer)) {
			return false;
		}
		Customer rhs = (Customer) object;
		return new EqualsBuilder()
				.append(this.customerId, rhs.customerId)
				.append(this.customerNo, rhs.customerNo)
				.append(this.industryType, rhs.industryType)
				.append(this.customerSource, rhs.customerSource)
				.append(this.customerType, rhs.customerType)
				.append(this.companyScale, rhs.companyScale)
				.append(this.customerName, rhs.customerName)
				.append(this.customerManager, rhs.customerManager)
				.append(this.phone, rhs.phone)
				.append(this.fax, rhs.fax)
				.append(this.site, rhs.site)
				.append(this.email, rhs.email)
				.append(this.state, rhs.state)
				.append(this.city, rhs.city)
				.append(this.zip, rhs.zip)
				.append(this.address, rhs.address)
				.append(this.registerFun, rhs.registerFun)
				.append(this.turnOver, rhs.turnOver)
				.append(this.currencyUnit, rhs.currencyUnit)
				.append(this.otherDesc, rhs.otherDesc)
				.append(this.principal, rhs.principal)
				.append(this.openBank, rhs.openBank)
				.append(this.accountsNo, rhs.accountsNo)
				.append(this.taxNo, rhs.taxNo)
				.append(this.notes, rhs.notes)
				.append(this.rights, rhs.rights)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.customerId) 
				.append(this.customerNo) 
				.append(this.industryType) 
				.append(this.customerSource) 
				.append(this.customerType) 
				.append(this.companyScale)
				.append(this.customerName) 
				.append(this.customerManager) 
				.append(this.phone) 
				.append(this.fax) 
				.append(this.site) 
				.append(this.email) 
				.append(this.state) 
				.append(this.city) 
				.append(this.zip) 
				.append(this.address)
				.append(this.registerFun)
				.append(this.turnOver) 
				.append(this.currencyUnit)
				.append(this.otherDesc) 
				.append(this.principal) 
				.append(this.openBank) 
				.append(this.accountsNo) 
				.append(this.taxNo) 
				.append(this.notes) 
				.append(this.rights) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("customerId", this.customerId) 
				.append("customerNo", this.customerNo) 
				.append("industryType", this.industryType) 
				.append("customerSource", this.customerSource) 
				.append("customerType", this.customerType) 
				.append("companyScale",this.companyScale)
				.append("customerName", this.customerName) 
				.append("customerManager", this.customerManager) 
				.append("phone", this.phone) 
				.append("fax", this.fax) 
				.append("site", this.site) 
				.append("email", this.email) 
				.append("state", this.state) 
				.append("city", this.city) 
				.append("zip", this.zip) 
				.append("address", this.address) 
				.append("registerFun",this.registerFun)
				.append("turnOver", this.turnOver) 
				.append("currencyUnit",this.currencyUnit)
				.append("otherDesc", this.otherDesc) 
				.append("principal", this.principal) 
				.append("openBank", this.openBank) 
				.append("accountsNo", this.accountsNo) 
				.append("taxNo", this.taxNo) 
				.append("notes", this.notes) 
				.append("rights", this.rights) 
				.toString();
	}



}
