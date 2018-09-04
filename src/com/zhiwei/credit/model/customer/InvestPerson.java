package com.zhiwei.credit.model.customer;
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
 * InvestPerson Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class InvestPerson extends com.zhiwei.core.model.BaseModel {

    protected Long perId;
	protected String cardNumber;
	protected String cardType;
	protected String cardTypeValue;
	protected Integer isdelete;
	protected String perName;
	protected String perSex;
	protected String phoneNumber;
	protected String postAddress;
	protected String postCode;
	protected java.util.Date perBirthday;
	protected String perBirthdayStr;
	private String creater; 
	private Long createrId;
	private java.util.Date createdate;     //创建时间
	protected String personSFZZUrl;
	protected Integer personSFZZId;
	protected String personSFZZExtendName;
	
	protected String personSFZFUrl;
	protected Integer personSFZFId;
	protected String personSFZFExtendName;
	protected String orgName;
	protected String customerLevel;//客户级别 : potentialCustomers==潜在客户；formalCustomer==正式客户；bigCustomer==大客户
	protected String filiation;//关系
	protected String linkmanName;//联系人姓名
	protected String linkmanPhone;//联系人电话
	private java.util.Date lostCareDate;//最后关怀时间
	private String perEmail;//邮箱地址
	
	private String areaId;//所在区域ID
	private String areaText;//地区名称
	private String remarks;//备注信息
	
	private String belongedId;//共享人Id
	private String belonger;//共享人名字
	private String depName;//共享人所在部门

	public String getCustomerLevel() {
		return customerLevel;
	}

	public void setCustomerLevel(String customerLevel) {
		this.customerLevel = customerLevel;
	}

	public String getFiliation() {
		return filiation;
	}

	public void setFiliation(String filiation) {
		this.filiation = filiation;
	}

	public String getLinkmanName() {
		return linkmanName;
	}

	public void setLinkmanName(String linkmanName) {
		this.linkmanName = linkmanName;
	}

	public String getLinkmanPhone() {
		return linkmanPhone;
	}

	public void setLinkmanPhone(String linkmanPhone) {
		this.linkmanPhone = linkmanPhone;
	}

	public java.util.Date getLostCareDate() {
		return lostCareDate;
	}

	public void setLostCareDate(java.util.Date lostCareDate) {
		this.lostCareDate = lostCareDate;
	}

	public String getPerEmail() {
		return perEmail;
	}

	public void setPerEmail(String perEmail) {
		this.perEmail = perEmail;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAreaText() {
		return areaText;
	}

	public void setAreaText(String areaText) {
		this.areaText = areaText;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getBelongedId() {
		return belongedId;
	}

	public void setBelongedId(String belongedId) {
		this.belongedId = belongedId;
	}

	public String getBelonger() {
		return belonger;
	}

	public void setBelonger(String belonger) {
		this.belonger = belonger;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public String getPerBirthdayStr() {
		return perBirthdayStr;
	}

	public void setPerBirthdayStr(String perBirthdayStr) {
		this.perBirthdayStr = perBirthdayStr;
	}

	/**
	 * Default Empty Constructor for class InvestPerson
	 */
	public InvestPerson () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class InvestPerson
	 */
	public InvestPerson (
		 Long in_perId
        ) {
		this.setPerId(in_perId);
    }

    

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**
	 * 	 * @return Long
     * @hibernate.id column="per_id" type="java.lang.Long" generator-class="native"
	 */
	public Long getPerId() {
		return this.perId;
	}
	
	/**
	 * Set the perId
	 */	
	public void setPerId(Long aValue) {
		this.perId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="card_number" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getCardNumber() {
		return this.cardNumber;
	}
	
	/**
	 * Set the cardNumber
	 */	
	public void setCardNumber(String aValue) {
		this.cardNumber = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="card_type" type="java.lang.String" length="11" not-null="false" unique="false"
	 */
	public String getCardType() {
		return this.cardType;
	}
	
	/**
	 * Set the cardType
	 */	
	public void setCardType(String aValue) {
		this.cardType = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="isdelete" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getIsdelete() {
		return this.isdelete;
	}
	
	/**
	 * Set the isdelete
	 */	
	public void setIsdelete(Integer aValue) {
		this.isdelete = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="per_name" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getPerName() {
		return this.perName;
	}
	
	/**
	 * Set the perName
	 */	
	public void setPerName(String aValue) {
		this.perName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="per_sex" type="java.lang.String" length="11" not-null="false" unique="false"
	 */
	public String getPerSex() {
		return this.perSex;
	}
	
	/**
	 * Set the perSex
	 */	
	public void setPerSex(String aValue) {
		this.perSex = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="phone_number" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getPhoneNumber() {
		return this.phoneNumber;
	}
	
	/**
	 * Set the phoneNumber
	 */	
	public void setPhoneNumber(String aValue) {
		this.phoneNumber = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="post_address" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getPostAddress() {
		return this.postAddress;
	}
	
	/**
	 * Set the postAddress
	 */	
	public void setPostAddress(String aValue) {
		this.postAddress = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="post_code" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getPostCode() {
		return this.postCode;
	}
	
	/**
	 * Set the postCode
	 */	
	public void setPostCode(String aValue) {
		this.postCode = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="per_birthday" type="java.util.Date" length="10" not-null="false" unique="false"
	 */
	public java.util.Date getPerBirthday() {
		return this.perBirthday;
	}
	
	/**
	 * Set the perBirthday
	 */	
	public void setPerBirthday(java.util.Date aValue) {
		this.perBirthday = aValue;
	}	

	public String getPersonSFZZUrl() {
		return personSFZZUrl;
	}

	public void setPersonSFZZUrl(String personSFZZUrl) {
		this.personSFZZUrl = personSFZZUrl;
	}

	public Integer getPersonSFZZId() {
		return personSFZZId;
	}

	public void setPersonSFZZId(Integer personSFZZId) {
		this.personSFZZId = personSFZZId;
	}

	public String getPersonSFZZExtendName() {
		return personSFZZExtendName;
	}

	public void setPersonSFZZExtendName(String personSFZZExtendName) {
		this.personSFZZExtendName = personSFZZExtendName;
	}

	public String getPersonSFZFUrl() {
		return personSFZFUrl;
	}

	public void setPersonSFZFUrl(String personSFZFUrl) {
		this.personSFZFUrl = personSFZFUrl;
	}

	public Integer getPersonSFZFId() {
		return personSFZFId;
	}

	public void setPersonSFZFId(Integer personSFZFId) {
		this.personSFZFId = personSFZFId;
	}

	public String getPersonSFZFExtendName() {
		return personSFZFExtendName;
	}

	public void setPersonSFZFExtendName(String personSFZFExtendName) {
		this.personSFZFExtendName = personSFZFExtendName;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public Long getCreaterId() {
		return createrId;
	}

	public void setCreaterId(Long createrId) {
		this.createrId = createrId;
	}

	public java.util.Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(java.util.Date createdate) {
		this.createdate = createdate;
	}

	public String getCardTypeValue() {
		return cardTypeValue;
	}

	public void setCardTypeValue(String cardTypeValue) {
		this.cardTypeValue = cardTypeValue;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof InvestPerson)) {
			return false;
		}
		InvestPerson rhs = (InvestPerson) object;
		return new EqualsBuilder()
				.append(this.perId, rhs.perId)
				.append(this.cardNumber, rhs.cardNumber)
				.append(this.cardType, rhs.cardType)
				.append(this.isdelete, rhs.isdelete)
				.append(this.perName, rhs.perName)
				.append(this.perSex, rhs.perSex)
				.append(this.phoneNumber, rhs.phoneNumber)
				.append(this.postAddress, rhs.postAddress)
				.append(this.postCode, rhs.postCode)
				.append(this.perBirthday, rhs.perBirthday)
				.append(this.creater, rhs.creater)
				.append(this.createrId, rhs.createrId) 
				.append(this.createdate, rhs.createdate) 
				.append(this.companyId, rhs.companyId)
				.append(this.personSFZZId, rhs.personSFZZId) 
				.append(this.personSFZZUrl, rhs.personSFZZUrl) 
				.append(this.personSFZZExtendName, rhs.personSFZZExtendName) 
				.append(this.personSFZFExtendName, rhs.personSFZFExtendName) 
				.append(this.personSFZFId, rhs.personSFZFId) 
				.append(this.personSFZFUrl, rhs.personSFZFUrl) 
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.perId) 
				.append(this.cardNumber) 
				.append(this.cardType) 
				.append(this.isdelete) 
				.append(this.perName) 
				.append(this.perSex) 
				.append(this.phoneNumber) 
				.append(this.postAddress) 
				.append(this.postCode) 
				.append(this.perBirthday) 
				.append(this.creater)
				.append(this.createrId) 
				.append(this.createdate) 
				.append(this.companyId)
				.append(this.personSFZZId) 
				.append(this.personSFZZUrl) 
				.append(this.personSFZZExtendName) 
				.append(this.personSFZFExtendName) 
				.append(this.personSFZFId) 
				.append(this.personSFZFUrl) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("perId", this.perId) 
				.append("cardNumber", this.cardNumber) 
				.append("cardType", this.cardType) 
				.append("isdelete", this.isdelete) 
				.append("perName", this.perName) 
				.append("perSex", this.perSex) 
				.append("phoneNumber", this.phoneNumber) 
				.append("postAddress", this.postAddress)
				.append("postCode", this.postCode)
				.append("perBirthday", this.perBirthday)
				.append("creater", this.creater)
				.append("createrId", this.createrId) 
				.append("createdate", this.createdate) 
				.append("companyId", this.companyId)
				.append("personSFZZId", this.personSFZZId) 
				.append("personSFZZUrl", this.personSFZZUrl) 
				.append("personSFZZExtendName", this.personSFZZExtendName) 
				.append("personSFZFExtendName", this.personSFZFExtendName) 
				.append("personSFZFId", this.personSFZFId) 
				.append("personSFZFUrl", this.personSFZFUrl) 
				.toString();
	}



}
