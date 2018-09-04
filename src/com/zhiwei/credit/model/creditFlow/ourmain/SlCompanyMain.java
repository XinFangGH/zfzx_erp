package com.zhiwei.credit.model.creditFlow.ourmain;
/*
 *  北京互融时代软件有限公司 JOffice协同办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2009 BEIJING JINZHIWANWEI SOFTWARES LIMITED COMPANY
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;


/**
 * SlCompanyMain Base Java Bean, base class for the.oa.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class SlCompanyMain extends com.zhiwei.core.model.BaseModel {

    protected Long companyMainId;
	protected String corName;//企业名称
	protected String simpleName;//企业简称
	protected String lawName;//法人姓名
	protected String organizeCode;//组织机构代码
	protected String businessCode;//营业执照
	protected Short haveCharcter;//所有制
	protected String tax;//传真
	protected String tel;//电话
	protected String mail;//邮箱
	protected String messageAddress;//通讯地址
    protected String sjjyAddress;//实际经营地址
    protected String postalCode;//邮政编码
    protected Short isPledge;//是否可以抵质押。0：否；1：是
    
    protected java.util.Date registerStartDate; //注册时间
    protected Double registerMoney;  //注册资本(万元)
    protected Integer hangyeType; //行业类型
    protected Long personMainId;//我方主体个人主体表主键
    protected String hangyeTypeValue;//行业类型数据字典值
    protected Short bizhong;//币种
    /**
     * //证件类型 ：1:三证合一   ，2::非三证合一
     */
    protected Integer documentType;//证件类型
    /**
     * 税务登记号码
     */
    protected String taxnum; 
    
    

	/**
	 * Default Empty Constructor for class SlCompanyMain
	 */
	public SlCompanyMain () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class SlCompanyMain
	 */
	public SlCompanyMain (
		 Long in_id
        ) {
		this.setCompanyMainId(in_id);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="id" type="java.lang.Long" generator-class="native"
	 */
	public Long getCompanyMainId() {
		return this.companyMainId;
	}
	
	/**
	 * Set the id
	 */	
	public void setCompanyMainId(Long aValue) {
		this.companyMainId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="corName" type="java.lang.String" length="250" not-null="false" unique="false"
	 */
	public String getCorName() {
		return this.corName;
	}
	
	/**
	 * Set the corName
	 */	
	public void setCorName(String aValue) {
		this.corName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="simpleName" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getSimpleName() {
		return this.simpleName;
	}
	
	/**
	 * Set the simpleName
	 */	
	public void setSimpleName(String aValue) {
		this.simpleName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="lawName" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	public String getLawName() {
		return this.lawName;
	}
	
	/**
	 * Set the lawName
	 */	
	public void setLawName(String aValue) {
		this.lawName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="organizeCode" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getOrganizeCode() {
		return this.organizeCode;
	}
	
	/**
	 * Set the organizeCode
	 */	
	public void setOrganizeCode(String aValue) {
		this.organizeCode = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="businessCode" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getBusinessCode() {
		return this.businessCode;
	}
	
	/**
	 * Set the businessCode
	 */	
	public void setBusinessCode(String aValue) {
		this.businessCode = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="haveCharcter" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getHaveCharcter() {
		return this.haveCharcter;
	}
	
	/**
	 * Set the haveCharcter
	 */	
	public void setHaveCharcter(Short aValue) {
		this.haveCharcter = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="tax" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	public String getTax() {
		return this.tax;
	}
	
	/**
	 * Set the tax
	 */	
	public void setTax(String aValue) {
		this.tax = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="tel" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	public String getTel() {
		return this.tel;
	}
	
	/**
	 * Set the tel
	 */	
	public void setTel(String aValue) {
		this.tel = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="mail" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	public String getMail() {
		return this.mail;
	}
	
	/**
	 * Set the mail
	 */	
	public void setMail(String aValue) {
		this.mail = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="messageAddress" type="java.lang.String" length="250" not-null="false" unique="false"
	 */
	public String getMessageAddress() {
		return this.messageAddress;
	}
	
	/**
	 * Set the messageAddress
	 */	
	public void setMessageAddress(String aValue) {
		this.messageAddress = aValue;
	}	

	public String getSjjyAddress() {
		return sjjyAddress;
	}

	public void setSjjyAddress(String sjjyAddress) {
		this.sjjyAddress = sjjyAddress;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public Short getIsPledge() {
		return isPledge;
	}

	public void setIsPledge(Short isPledge) {
		this.isPledge = isPledge;
	}

	public java.util.Date getRegisterStartDate() {
		return registerStartDate;
	}

	public void setRegisterStartDate(java.util.Date registerStartDate) {
		this.registerStartDate = registerStartDate;
	}

	public Double getRegisterMoney() {
		return registerMoney;
	}

	public void setRegisterMoney(Double registerMoney) {
		this.registerMoney = registerMoney;
	}

	public Integer getHangyeType() {
		return hangyeType;
	}

	public void setHangyeType(Integer hangyeType) {
		this.hangyeType = hangyeType;
	}

	public Long getPersonMainId() {
		return personMainId;
	}

	public void setPersonMainId(Long personMainId) {
		this.personMainId = personMainId;
	}

	public String getHangyeTypeValue() {
		return hangyeTypeValue;
	}

	public void setHangyeTypeValue(String hangyeTypeValue) {
		this.hangyeTypeValue = hangyeTypeValue;
	}

	public Short getBizhong() {
		return bizhong;
	}

	public void setBizhong(Short bizhong) {
		this.bizhong = bizhong;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SlCompanyMain)) {
			return false;
		}
		SlCompanyMain rhs = (SlCompanyMain) object;
		return new EqualsBuilder()
				.append(this.companyMainId, rhs.companyMainId)
				.append(this.corName, rhs.corName)
				.append(this.simpleName, rhs.simpleName)
				.append(this.lawName, rhs.lawName)
				.append(this.organizeCode, rhs.organizeCode)
				.append(this.businessCode, rhs.businessCode)
				.append(this.haveCharcter, rhs.haveCharcter)
				.append(this.tax, rhs.tax)
				.append(this.tel, rhs.tel)
				.append(this.mail, rhs.mail)
				.append(this.messageAddress, rhs.messageAddress)
				.append(this.sjjyAddress, rhs.sjjyAddress)
				.append(this.postalCode, rhs.postalCode)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.companyMainId) 
				.append(this.corName) 
				.append(this.simpleName) 
				.append(this.lawName) 
				.append(this.organizeCode) 
				.append(this.businessCode) 
				.append(this.haveCharcter) 
				.append(this.tax) 
				.append(this.tel) 
				.append(this.mail) 
				.append(this.messageAddress)
				.append(this.sjjyAddress)
				.append(this.postalCode)
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.companyMainId) 
				.append("corName", this.corName) 
				.append("simpleName", this.simpleName) 
				.append("lawName", this.lawName) 
				.append("organizeCode", this.organizeCode) 
				.append("businessCode", this.businessCode) 
				.append("haveCharcter", this.haveCharcter) 
				.append("tax", this.tax) 
				.append("tel", this.tel) 
				.append("mail", this.mail) 
				.append("messageAddress", this.messageAddress) 
				.append("sjjyAddress", this.sjjyAddress)
				.append("postalCode", this.postalCode)
				.toString();
	}

	public Integer getDocumentType() {
		return documentType;
	}

	public void setDocumentType(Integer documentType) {
		this.documentType = documentType;
	}

	public String getTaxnum() {
		return taxnum;
	}

	public void setTaxnum(String taxnum) {
		this.taxnum = taxnum;
	}



}
