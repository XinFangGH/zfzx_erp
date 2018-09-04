package com.zhiwei.credit.model.customer;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

import com.google.gson.annotations.Expose;
import com.zhiwei.credit.model.system.FileAttach;

/**
 * Project Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * ������������
 */
public class Project extends com.zhiwei.core.model.BaseModel {
	@Expose
    protected Long projectId;
	@Expose
	protected String projectName;
	@Expose
	protected String projectNo;
	@Expose
	protected String reqDesc;
	@Expose
	protected Short isContract;
	@Expose
	protected String fullname;
	@Expose
	protected String mobile;
	@Expose
	protected String phone;
	@Expose
	protected String fax;
	@Expose
	protected String otherContacts;
	@Expose
	protected com.zhiwei.credit.model.system.AppUser appUser;
	@Expose
	protected com.zhiwei.credit.model.customer.Customer customer;

	protected java.util.Set contracts = new java.util.HashSet();
	@Expose
	protected java.util.Set<FileAttach> projectFiles = new java.util.HashSet<FileAttach>();

	/**
	 * Default Empty Constructor for class Project
	 */
	public Project () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Project
	 */
	public Project (
		 Long in_projectId
        ) {
		this.setProjectId(in_projectId);
    }

	
	public com.zhiwei.credit.model.system.AppUser getAppUser () {
		return appUser;
	}	
	
	public void setAppUser (com.zhiwei.credit.model.system.AppUser in_appUser) {
		this.appUser = in_appUser;
	}
	
	public com.zhiwei.credit.model.customer.Customer getCustomer () {
		return customer;
	}	
	
	public void setCustomer (com.zhiwei.credit.model.customer.Customer in_customer) {
		this.customer = in_customer;
	}

	public java.util.Set getContracts () {
		return contracts;
	}	
	
	public void setContracts (java.util.Set in_contracts) {
		this.contracts = in_contracts;
	}

	public java.util.Set getProjectFiles () {
		return projectFiles;
	}	
	
	public void setProjectFiles (java.util.Set in_projectFiles) {
		this.projectFiles = in_projectFiles;
	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="projectId" type="java.lang.Long" generator-class="native"
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
	 * 项目名称	 * @return String
	 * @hibernate.property column="projectName" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getProjectName() {
		return this.projectName;
	}
	
	/**
	 * Set the projectName
	 * @spring.validator type="required"
	 */	
	public void setProjectName(String aValue) {
		this.projectName = aValue;
	}	

	/**
	 * 项目编号	 * @return String
	 * @hibernate.property column="projectNo" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getProjectNo() {
		return this.projectNo;
	}
	
	/**
	 * Set the projectNo
	 * @spring.validator type="required"
	 */	
	public void setProjectNo(String aValue) {
		this.projectNo = aValue;
	}	

	/**
	 * 需求描述	 * @return String
	 * @hibernate.property column="reqDesc" type="java.lang.String" length="65535" not-null="false" unique="false"
	 */
	public String getReqDesc() {
		return this.reqDesc;
	}
	
	/**
	 * Set the reqDesc
	 */	
	public void setReqDesc(String aValue) {
		this.reqDesc = aValue;
	}	

	/**
	 * 是否签订合同	 * @return Short
	 * @hibernate.property column="isContract" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getIsContract() {
		return this.isContract;
	}
	
	/**
	 * Set the isContract
	 * @spring.validator type="required"
	 */	
	public void setIsContract(Short aValue) {
		this.isContract = aValue;
	}	

	/**
	 * 联系人姓名	 * @return String
	 * @hibernate.property column="fullname" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getFullname() {
		return this.fullname;
	}
	
	/**
	 * Set the fullname
	 * @spring.validator type="required"
	 */	
	public void setFullname(String aValue) {
		this.fullname = aValue;
	}	

	/**
	 * 手机	 * @return String
	 * @hibernate.property column="mobile" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getMobile() {
		return this.mobile;
	}
	
	/**
	 * Set the mobile
	 */	
	public void setMobile(String aValue) {
		this.mobile = aValue;
	}	

	/**
	 * 电话	 * @return String
	 * @hibernate.property column="phone" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getPhone() {
		return this.phone;
	}
	
	/**
	 * Set the phone
	 */	
	public void setPhone(String aValue) {
		this.phone = aValue;
	}	

	/**
	 * 传真	 * @return String
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
	 * 其他联系方式	 * @return String
	 * @hibernate.property column="otherContacts" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getOtherContacts() {
		return this.otherContacts;
	}
	
	/**
	 * Set the otherContacts
	 */	
	public void setOtherContacts(String aValue) {
		this.otherContacts = aValue;
	}	

	/**
	 * 所属客户	 * @return Long
	 */
	public Long getCustomerId() {
		return this.getCustomer()==null?null:this.getCustomer().getCustomerId();
	}
	
	/**
	 * Set the customerId
	 */	
	public void setCustomerId(Long aValue) {
	    if (aValue==null) {
	    	customer = null;
	    } else if (customer == null) {
	        customer = new com.zhiwei.credit.model.customer.Customer(aValue);
	        customer.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			customer.setCustomerId(aValue);
	    }
	}	

	/**
	 * 业务人员	 * @return Long
	 */
	public Long getUserId() {
		return this.getAppUser()==null?null:this.getAppUser().getUserId();
	}
	
	/**
	 * Set the userId
	 */	
	public void setUserId(Long aValue) {
	    if (aValue==null) {
	    	appUser = null;
	    } else if (appUser == null) {
	        appUser = new com.zhiwei.credit.model.system.AppUser(aValue);
	        appUser.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			appUser.setUserId(aValue);
	    }
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Project)) {
			return false;
		}
		Project rhs = (Project) object;
		return new EqualsBuilder()
				.append(this.projectId, rhs.projectId)
				.append(this.projectName, rhs.projectName)
				.append(this.projectNo, rhs.projectNo)
				.append(this.reqDesc, rhs.reqDesc)
				.append(this.isContract, rhs.isContract)
				.append(this.fullname, rhs.fullname)
				.append(this.mobile, rhs.mobile)
				.append(this.phone, rhs.phone)
				.append(this.fax, rhs.fax)
				.append(this.otherContacts, rhs.otherContacts)
								.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.projectId) 
				.append(this.projectName) 
				.append(this.projectNo) 
				.append(this.reqDesc) 
				.append(this.isContract) 
				.append(this.fullname) 
				.append(this.mobile) 
				.append(this.phone) 
				.append(this.fax) 
				.append(this.otherContacts) 
								.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("projectId", this.projectId) 
				.append("projectName", this.projectName) 
				.append("projectNo", this.projectNo) 
				.append("reqDesc", this.reqDesc) 
				.append("isContract", this.isContract) 
				.append("fullname", this.fullname) 
				.append("mobile", this.mobile) 
				.append("phone", this.phone) 
				.append("fax", this.fax) 
				.append("otherContacts", this.otherContacts) 
								.toString();
	}



}
