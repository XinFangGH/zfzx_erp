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
 * Contract Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class Contract extends com.zhiwei.core.model.BaseModel {

	@Expose
    protected Long contractId;
	@Expose
	protected String contractNo;
	@Expose
	protected String subject;
	@Expose
	protected java.math.BigDecimal contractAmount;
	@Expose
	protected String mainItem;
	@Expose
	protected String salesAfterItem;
	@Expose
	protected java.util.Date validDate;
	@Expose
	protected java.util.Date expireDate;
	@Expose
	protected String serviceDep;
	@Expose
	protected String serviceMan;
	@Expose
	protected String signupUser;
	@Expose
	protected java.util.Date signupTime;
	@Expose
	protected String creator;
	@Expose
	protected java.util.Date createtime;
	@Expose
	protected String consignAddress;
	@Expose
	protected String consignee;
	@Expose
	protected com.zhiwei.credit.model.customer.Project project;

	protected java.util.Set contractConfigs = new java.util.HashSet();
	@Expose
	protected java.util.Set contractFiles = new java.util.HashSet();

	/**
	 * Default Empty Constructor for class Contract
	 */
	public Contract () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Contract
	 */
	public Contract (
		 Long in_contractId
        ) {
		this.setContractId(in_contractId);
    }

	
	public com.zhiwei.credit.model.customer.Project getProject () {
		return project;
	}	
	
	public void setProject (com.zhiwei.credit.model.customer.Project in_project) {
		this.project = in_project;
	}

	public java.util.Set getContractConfigs () {
		return contractConfigs;
	}	
	
	public void setContractConfigs (java.util.Set in_contractConfigs) {
		this.contractConfigs = in_contractConfigs;
	}

	public java.util.Set getContractFiles () {
		return contractFiles;
	}	
	
	public void setContractFiles (java.util.Set in_contractFiles) {
		this.contractFiles = in_contractFiles;
	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="contactId" type="java.lang.Long" generator-class="native"
	 */
	public Long getContractId() {
		return this.contractId;
	}
	
	/**
	 * Set the contactId
	 */	
	public void setContractId(Long aValue) {
		this.contractId = aValue;
	}	

	/**
	 * 合同编号	 * @return String
	 * @hibernate.property column="contractNo" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getContractNo() {
		return this.contractNo;
	}
	
	/**
	 * Set the contractNo
	 * @spring.validator type="required"
	 */	
	public void setContractNo(String aValue) {
		this.contractNo = aValue;
	}	

	/**
	 * 合同标题	 * @return String
	 * @hibernate.property column="subject" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getSubject() {
		return this.subject;
	}
	
	/**
	 * Set the subject
	 * @spring.validator type="required"
	 */	
	public void setSubject(String aValue) {
		this.subject = aValue;
	}	

	/**
	 * 合同金额	 * @return java.math.BigDecimal
	 * @hibernate.property column="contractAmount" type="java.math.BigDecimal" length="10" not-null="true" unique="false"
	 */
	public java.math.BigDecimal getContractAmount() {
		return this.contractAmount;
	}
	
	/**
	 * Set the contractAmount
	 * @spring.validator type="required"
	 */	
	public void setContractAmount(java.math.BigDecimal aValue) {
		this.contractAmount = aValue;
	}	

	/**
	 * 主要条款	 * @return String
	 * @hibernate.property column="mainItem" type="java.lang.String" length="4000" not-null="false" unique="false"
	 */
	public String getMainItem() {
		return this.mainItem;
	}
	
	/**
	 * Set the mainItem
	 */	
	public void setMainItem(String aValue) {
		this.mainItem = aValue;
	}	

	/**
	 * 售后条款	 * @return String
	 * @hibernate.property column="salesAfterItem" type="java.lang.String" length="4000" not-null="false" unique="false"
	 */
	public String getSalesAfterItem() {
		return this.salesAfterItem;
	}
	
	/**
	 * Set the salesAfterItem
	 */	
	public void setSalesAfterItem(String aValue) {
		this.salesAfterItem = aValue;
	}	

	/**
	 * 生效日期	 * @return java.util.Date
	 * @hibernate.property column="validDate" type="java.util.Date" length="19" not-null="true" unique="false"
	 */
	public java.util.Date getValidDate() {
		return this.validDate;
	}
	
	/**
	 * Set the validDate
	 * @spring.validator type="required"
	 */	
	public void setValidDate(java.util.Date aValue) {
		this.validDate = aValue;
	}	

	/**
	 * 有效期	 * @return java.util.Date
	 * @hibernate.property column="expireDate" type="java.util.Date" length="19" not-null="true" unique="false"
	 */
	public java.util.Date getExpireDate() {
		return this.expireDate;
	}
	
	/**
	 * Set the expireDate
	 * @spring.validator type="required"
	 */	
	public void setExpireDate(java.util.Date aValue) {
		this.expireDate = aValue;
	}	

	/**
	 * 维修部门	 * @return String
	 * @hibernate.property column="serviceDep" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getServiceDep() {
		return this.serviceDep;
	}
	
	/**
	 * Set the serviceDep
	 */	
	public void setServiceDep(String aValue) {
		this.serviceDep = aValue;
	}	

	/**
	 * 维修负责人	 * @return String
	 * @hibernate.property column="serviceMan" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getServiceMan() {
		return this.serviceMan;
	}
	
	/**
	 * Set the serviceMan
	 */	
	public void setServiceMan(String aValue) {
		this.serviceMan = aValue;
	}	

	/**
	 * 签约人	 * @return String
	 * @hibernate.property column="signupUser" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getSignupUser() {
		return this.signupUser;
	}
	
	/**
	 * Set the signupUser
	 * @spring.validator type="required"
	 */	
	public void setSignupUser(String aValue) {
		this.signupUser = aValue;
	}	

	/**
	 * 签约时间	 * @return java.util.Date
	 * @hibernate.property column="signupTime" type="java.util.Date" length="19" not-null="true" unique="false"
	 */
	public java.util.Date getSignupTime() {
		return this.signupTime;
	}
	
	/**
	 * Set the signupTime
	 * @spring.validator type="required"
	 */	
	public void setSignupTime(java.util.Date aValue) {
		this.signupTime = aValue;
	}	

	/**
	 * 录入人	 * @return String
	 * @hibernate.property column="creator" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getCreator() {
		return this.creator;
	}
	
	/**
	 * Set the creator
	 * @spring.validator type="required"
	 */	
	public void setCreator(String aValue) {
		this.creator = aValue;
	}	

	/**
	 * 录入时间	 * @return java.util.Date
	 * @hibernate.property column="createtime" type="java.util.Date" length="19" not-null="true" unique="false"
	 */
	public java.util.Date getCreatetime() {
		return this.createtime;
	}
	
	/**
	 * Set the createtime
	 * @spring.validator type="required"
	 */	
	public void setCreatetime(java.util.Date aValue) {
		this.createtime = aValue;
	}	

	/**
	 * 所属项目	 * @return Long
	 */
	public Long getProjectId() {
		return this.getProject()==null?null:this.getProject().getProjectId();
	}
	
	/**
	 * Set the projectId
	 */	
	public void setProjectId(Long aValue) {
	    if (aValue==null) {
	    	project = null;
	    } else if (project == null) {
	        project = new com.zhiwei.credit.model.customer.Project(aValue);
	        project.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			project.setProjectId(aValue);
	    }
	}	

	/**
	 * 收货地址	 * @return String
	 * @hibernate.property column="consignAddress" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getConsignAddress() {
		return this.consignAddress;
	}
	
	/**
	 * Set the consignAddress
	 */	
	public void setConsignAddress(String aValue) {
		this.consignAddress = aValue;
	}	

	/**
	 * 收货人	 * @return String
	 * @hibernate.property column="consignee" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getConsignee() {
		return this.consignee;
	}
	
	/**
	 * Set the consignee
	 */	
	public void setConsignee(String aValue) {
		this.consignee = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Contract)) {
			return false;
		}
		Contract rhs = (Contract) object;
		return new EqualsBuilder()
				.append(this.contractId, rhs.contractId)
				.append(this.contractNo, rhs.contractNo)
				.append(this.subject, rhs.subject)
				.append(this.contractAmount, rhs.contractAmount)
				.append(this.mainItem, rhs.mainItem)
				.append(this.salesAfterItem, rhs.salesAfterItem)
				.append(this.validDate, rhs.validDate)
				.append(this.expireDate, rhs.expireDate)
				.append(this.serviceDep, rhs.serviceDep)
				.append(this.serviceMan, rhs.serviceMan)
				.append(this.signupUser, rhs.signupUser)
				.append(this.signupTime, rhs.signupTime)
				.append(this.creator, rhs.creator)
				.append(this.createtime, rhs.createtime)
						.append(this.consignAddress, rhs.consignAddress)
				.append(this.consignee, rhs.consignee)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.contractId) 
				.append(this.contractNo) 
				.append(this.subject) 
				.append(this.contractAmount) 
				.append(this.mainItem) 
				.append(this.salesAfterItem) 
				.append(this.validDate) 
				.append(this.expireDate) 
				.append(this.serviceDep) 
				.append(this.serviceMan) 
				.append(this.signupUser) 
				.append(this.signupTime) 
				.append(this.creator) 
				.append(this.createtime) 
						.append(this.consignAddress) 
				.append(this.consignee) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("contactId", this.contractId) 
				.append("contractNo", this.contractNo) 
				.append("subject", this.subject) 
				.append("contractAmount", this.contractAmount) 
				.append("mainItem", this.mainItem) 
				.append("salesAfterItem", this.salesAfterItem) 
				.append("validDate", this.validDate) 
				.append("expireDate", this.expireDate) 
				.append("serviceDep", this.serviceDep) 
				.append("serviceMan", this.serviceMan) 
				.append("signupUser", this.signupUser) 
				.append("signupTime", this.signupTime) 
				.append("creator", this.creator) 
				.append("createtime", this.createtime) 
						.append("consignAddress", this.consignAddress) 
				.append("consignee", this.consignee) 
				.toString();
	}



}
