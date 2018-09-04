package com.zhiwei.credit.model.creditFlow.customer.person.workcompany;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
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
 * WorkCompany Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class WorkCompany extends com.zhiwei.core.model.BaseModel {

    protected Integer id;
	protected String workCompanyName;//公司名称
	protected Integer companyType;//公司类型
	protected String companyTelephone;//公司电话
	protected String fax;//公司传真
	protected String postcoding;
	protected String companyAddress;//公司地址
	protected String cciaa;
	protected java.util.Date businessDate;//经营时间
	protected String primaryBusinessType;
	protected Integer employeeTotal;//员工人数
	protected Integer belongDepartment;//所属部门
	protected Integer workContent;
	protected java.util.Date onCompanyDate;
	protected Double monthIncome;//月收入
	protected Integer personId;


	/**
	 * Default Empty Constructor for class WorkCompany
	 */
	public WorkCompany () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class WorkCompany
	 */
	public WorkCompany (
		 Integer in_id
        ) {
		this.setId(in_id);
    }

    

	/**
	 * 	 * @return Integer
     * @hibernate.id column="id" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getId() {
		return this.id;
	}
	
	/**
	 * Set the id
	 */	
	public void setId(Integer aValue) {
		this.id = aValue;
	}	


	/**
	 * 	 * @return Integer
	 * @hibernate.property column="companyType" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getCompanyType() {
		return this.companyType;
	}
	
	/**
	 * Set the companyType
	 */	
	public void setCompanyType(Integer aValue) {
		this.companyType = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="companyTelephone" type="java.lang.String" length="20" not-null="false" unique="false"
	 */
	public String getCompanyTelephone() {
		return this.companyTelephone;
	}
	
	/**
	 * Set the companyTelephone
	 */	
	public void setCompanyTelephone(String aValue) {
		this.companyTelephone = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="fax" type="java.lang.String" length="20" not-null="false" unique="false"
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
	 * @hibernate.property column="postcoding" type="java.lang.String" length="20" not-null="false" unique="false"
	 */
	public String getPostcoding() {
		return this.postcoding;
	}
	
	/**
	 * Set the postcoding
	 */	
	public void setPostcoding(String aValue) {
		this.postcoding = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="companyAddress" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getCompanyAddress() {
		return this.companyAddress;
	}
	
	/**
	 * Set the companyAddress
	 */	
	public void setCompanyAddress(String aValue) {
		this.companyAddress = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="cciaa" type="java.lang.String" length="45" not-null="false" unique="false"
	 */
	public String getCciaa() {
		return this.cciaa;
	}
	
	/**
	 * Set the cciaa
	 */	
	public void setCciaa(String aValue) {
		this.cciaa = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="businessDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getBusinessDate() {
		return this.businessDate;
	}
	
	/**
	 * Set the businessDate
	 */	
	public void setBusinessDate(java.util.Date aValue) {
		this.businessDate = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="primaryBusinessType" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	public String getPrimaryBusinessType() {
		return this.primaryBusinessType;
	}
	
	/**
	 * Set the primaryBusinessType
	 */	
	public void setPrimaryBusinessType(String aValue) {
		this.primaryBusinessType = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="employeeTotal" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getEmployeeTotal() {
		return this.employeeTotal;
	}
	
	/**
	 * Set the employeeTotal
	 */	
	public void setEmployeeTotal(Integer aValue) {
		this.employeeTotal = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="belongDepartment" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getBelongDepartment() {
		return this.belongDepartment;
	}
	
	/**
	 * Set the belongDepartment
	 */	
	public void setBelongDepartment(Integer aValue) {
		this.belongDepartment = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="workContent" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getWorkContent() {
		return this.workContent;
	}
	
	/**
	 * Set the workContent
	 */	
	public void setWorkContent(Integer aValue) {
		this.workContent = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="onCompanyDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getOnCompanyDate() {
		return this.onCompanyDate;
	}
	
	/**
	 * Set the onCompanyDate
	 */	
	public void setOnCompanyDate(java.util.Date aValue) {
		this.onCompanyDate = aValue;
	}	

	/**
	 * 	 * @return Double
	 * @hibernate.property column="monthIncome" type="java.lang.Double" length="10" not-null="false" unique="false"
	 */
	public Double getMonthIncome() {
		return this.monthIncome;
	}
	
	/**
	 * Set the monthIncome
	 */	
	public void setMonthIncome(Double aValue) {
		this.monthIncome = aValue;
	}	
	
	public String getWorkCompanyName() {
		return workCompanyName;
	}

	public void setWorkCompanyName(String workCompanyName) {
		this.workCompanyName = workCompanyName;
	}

	

	public Integer getPersonId() {
		return personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof WorkCompany)) {
			return false;
		}
		WorkCompany rhs = (WorkCompany) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.workCompanyName, rhs.workCompanyName)
				.append(this.companyType, rhs.companyType)
				.append(this.companyTelephone, rhs.companyTelephone)
				.append(this.fax, rhs.fax)
				.append(this.postcoding, rhs.postcoding)
				.append(this.companyAddress, rhs.companyAddress)
				.append(this.cciaa, rhs.cciaa)
				.append(this.businessDate, rhs.businessDate)
				.append(this.primaryBusinessType, rhs.primaryBusinessType)
				.append(this.employeeTotal, rhs.employeeTotal)
				.append(this.belongDepartment, rhs.belongDepartment)
				.append(this.workContent, rhs.workContent)
				.append(this.onCompanyDate, rhs.onCompanyDate)
				.append(this.monthIncome, rhs.monthIncome)
				.append(this.personId, rhs.personId)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.workCompanyName) 
				.append(this.companyType) 
				.append(this.companyTelephone) 
				.append(this.fax) 
				.append(this.postcoding) 
				.append(this.companyAddress) 
				.append(this.cciaa) 
				.append(this.businessDate) 
				.append(this.primaryBusinessType) 
				.append(this.employeeTotal) 
				.append(this.belongDepartment) 
				.append(this.workContent) 
				.append(this.onCompanyDate) 
				.append(this.monthIncome) 
				.append(this.personId) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("workCompanyName", this.workCompanyName) 
				.append("companyType", this.companyType) 
				.append("companyTelephone", this.companyTelephone) 
				.append("fax", this.fax) 
				.append("postcoding", this.postcoding) 
				.append("companyAddress", this.companyAddress) 
				.append("cciaa", this.cciaa) 
				.append("businessDate", this.businessDate) 
				.append("primaryBusinessType", this.primaryBusinessType) 
				.append("employeeTotal", this.employeeTotal) 
				.append("belongDepartment", this.belongDepartment) 
				.append("workContent", this.workContent) 
				.append("onCompanyDate", this.onCompanyDate) 
				.append("monthIncome", this.monthIncome) 
				.append("personId", this.personId) 
				.toString();
	}



}
