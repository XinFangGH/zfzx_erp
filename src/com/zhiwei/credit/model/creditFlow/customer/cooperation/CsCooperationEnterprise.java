package com.zhiwei.credit.model.creditFlow.customer.cooperation;

import java.math.BigDecimal;

/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/

/**
 * 
 * @author 
 *
 */
/**
 * CsCooperationEnterprise Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class CsCooperationEnterprise extends com.zhiwei.core.model.BaseModel {

    protected Long id;
	protected String name;
	/**
	 * 类型
	 * guarantee ： 担保   lenders :债权客户     
	 */
	protected String type="lenders";
	protected String typeFrom;
	protected String licenseNumber;
	protected String organizationNumber;
	protected String tellPhone;
	protected String fax;
	protected String registeredMoney;
	protected java.util.Date buildDate;
	protected java.util.Date cooperationDate;
	protected String companyURL;
	protected String companyAddress;
	protected String companyIntro;
	protected String pname;
	/**
	 * 性别
	 * 0男
	 * 1女
	 */
	protected String psex;
	protected String pphone;
	protected String pappellation;
	protected String pemail;
	protected String pcardNumber;
	/**
	 * 是否启用
	 * 0启用
	 * 1禁用
	 */
	protected String isUsing = "0";
	/**
	 * 是否开通P2P
	 * 0开通
	 * 1没开通
	 */
	protected String isOpenP2P = "1";
	/**
	 * 证件类型 ：三证合一   ，三证分开
	 */
	protected Long documentType;
	/**
	 * 税务登记号码
	 */
	protected String taxnum;  
	
	
	protected String p2ploginname;
	protected String p2ptruename;
	protected String p2pcardcode;
	protected String p2ptelphone;
	protected String p2pemail;
	protected String p2pid;
	protected String p2pisForbidden;
	protected String thirdPayFlagId;
	protected String isCheckCard;//身份证是否通过验证
	
	public String getThirdPayFlagId() {
		return thirdPayFlagId;
	}

	public void setThirdPayFlagId(String thirdPayFlagId) {
		this.thirdPayFlagId = thirdPayFlagId;
	}

	/**
	 * 资金账户
	 */
	protected Long accountId;
	protected String accountName;
	protected String accountNumber;
	protected Long investmentPersonId;
	protected Short investPersionType;
	protected BigDecimal totalMoney; 
	
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Long getInvestmentPersonId() {
		return investmentPersonId;
	}

	public void setInvestmentPersonId(Long investmentPersonId) {
		this.investmentPersonId = investmentPersonId;
	}

	public Short getInvestPersionType() {
		return investPersionType;
	}

	public void setInvestPersionType(Short investPersionType) {
		this.investPersionType = investPersionType;
	}

	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}


	

	public String getP2ploginname() {
		return p2ploginname;
	}

	public void setP2ploginname(String p2ploginname) {
		this.p2ploginname = p2ploginname;
	}

	public String getP2ptruename() {
		return p2ptruename;
	}

	public void setP2ptruename(String p2ptruename) {
		this.p2ptruename = p2ptruename;
	}

	public String getP2pcardcode() {
		return p2pcardcode;
	}

	public void setP2pcardcode(String p2pcardcode) {
		this.p2pcardcode = p2pcardcode;
	}

	public String getP2ptelphone() {
		return p2ptelphone;
	}

	public void setP2ptelphone(String p2ptelphone) {
		this.p2ptelphone = p2ptelphone;
	}

	public String getP2pemail() {
		return p2pemail;
	}

	public void setP2pemail(String p2pemail) {
		this.p2pemail = p2pemail;
	}

	public String getP2pid() {
		return p2pid;
	}

	public void setP2pid(String p2pid) {
		this.p2pid = p2pid;
	}

	public String getP2pisForbidden() {
		return p2pisForbidden;
	}

	public void setP2pisForbidden(String p2pisForbidden) {
		this.p2pisForbidden = p2pisForbidden;
	}

	/**
	 * Default Empty Constructor for class CsCooperationEnterprise
	 */
	public CsCooperationEnterprise () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class CsCooperationEnterprise
	 */
	public CsCooperationEnterprise (
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
	 * 机构名称	 * @return String
	 * @hibernate.property column="name" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Set the name
	 */	
	public void setName(String aValue) {
		this.name = aValue;
	}	

	/**
	 * 机构类型	 * @return String
	 * @hibernate.property column="type" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getType() {
		return this.type;
	}
	
	/**
	 * Set the type
	 */	
	public void setType(String aValue) {
		this.type = aValue;
	}	

	/**
	 * 机构来源	 * @return String
	 * @hibernate.property column="typeFrom" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getTypeFrom() {
		return this.typeFrom;
	}
	
	/**
	 * Set the typeFrom
	 */	
	public void setTypeFrom(String aValue) {
		this.typeFrom = aValue;
	}	

	/**
	 * 营业执照号码	 * @return String
	 * @hibernate.property column="licenseNumber" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getLicenseNumber() {
		return this.licenseNumber;
	}
	
	/**
	 * Set the licenseNumber
	 */	
	public void setLicenseNumber(String aValue) {
		this.licenseNumber = aValue;
	}	

	/**
	 * 组织机构号码	 * @return String
	 * @hibernate.property column="organizationNumber" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getOrganizationNumber() {
		return this.organizationNumber;
	}
	
	/**
	 * Set the organizationNumber
	 */	
	public void setOrganizationNumber(String aValue) {
		this.organizationNumber = aValue;
	}	

	/**
	 * 公司电话	 * @return String
	 * @hibernate.property column="tellPhone" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getTellPhone() {
		return this.tellPhone;
	}
	
	/**
	 * Set the tellPhone
	 */	
	public void setTellPhone(String aValue) {
		this.tellPhone = aValue;
	}	

	/**
	 * 传真	 * @return String
	 * @hibernate.property column="fax" type="java.lang.String" length="255" not-null="false" unique="false"
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
	 * 注册资本金	 * @return String
	 * @hibernate.property column="registeredMoney" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getRegisteredMoney() {
		return this.registeredMoney;
	}
	
	/**
	 * Set the registeredMoney
	 */	
	public void setRegisteredMoney(String aValue) {
		this.registeredMoney = aValue;
	}	

	/**
	 * 公司成立日期	 * @return java.util.Date
	 * @hibernate.property column="buildDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getBuildDate() {
		return this.buildDate;
	}
	
	/**
	 * Set the buildDate
	 */	
	public void setBuildDate(java.util.Date aValue) {
		this.buildDate = aValue;
	}	

	/**
	 * 合作公司开始时间	 * @return java.util.Date
	 * @hibernate.property column="cooperationDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getCooperationDate() {
		return this.cooperationDate;
	}
	
	/**
	 * Set the cooperationDate
	 */	
	public void setCooperationDate(java.util.Date aValue) {
		this.cooperationDate = aValue;
	}	

	/**
	 * 公司网址	 * @return String
	 * @hibernate.property column="companyURL" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getCompanyURL() {
		return this.companyURL;
	}
	
	/**
	 * Set the companyURL
	 */	
	public void setCompanyURL(String aValue) {
		this.companyURL = aValue;
	}	

	/**
	 * 公司地址	 * @return String
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
	 * 公司简介	 * @return String
	 * @hibernate.property column="companyIntro" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getCompanyIntro() {
		return this.companyIntro;
	}
	
	/**
	 * Set the companyIntro
	 */	
	public void setCompanyIntro(String aValue) {
		this.companyIntro = aValue;
	}	

	/**
	 * 联系人	 * @return String
	 * @hibernate.property column="pName" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getPname() {
		return this.pname;
	}
	
	/**
	 * Set the pName
	 */	
	public void setPname(String aValue) {
		this.pname = aValue;
	}	

	/**
	 * 联系人性别	 * @return String
	 * @hibernate.property column="pSex" type="java.lang.String" length="1" not-null="false" unique="false"
	 */
	public String getPsex() {
		return this.psex;
	}
	
	/**
	 * Set the pSex
	 */	
	public void setPsex(String aValue) {
		this.psex = aValue;
	}	

	/**
	 * 联系电话	 * @return String
	 * @hibernate.property column="pPhone" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getPphone() {
		return this.pphone;
	}
	
	/**
	 * Set the pPhone
	 */	
	public void setPphone(String aValue) {
		this.pphone = aValue;
	}	

	/**
	 * 称谓	 * @return String
	 * @hibernate.property column="pAppellation" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getPappellation() {
		return this.pappellation;
	}
	
	/**
	 * Set the pAppellation
	 */	
	public void setPappellation(String aValue) {
		this.pappellation = aValue;
	}	

	/**
	 * 邮箱	 * @return String
	 * @hibernate.property column="pEmail" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getPemail() {
		return this.pemail;
	}
	
	/**
	 * Set the pEmail
	 */	
	public void setPemail(String aValue) {
		this.pemail = aValue;
	}	

	/**
	 * 身份证号码	 * @return String
	 * @hibernate.property column="pCardNumber" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getPcardNumber() {
		return this.pcardNumber;
	}
	
	/**
	 * Set the pCardNumber
	 */	
	public void setPcardNumber(String aValue) {
		this.pcardNumber = aValue;
	}	

	/**
	 * 是否启用	 * @return String
	 * @hibernate.property column="isUsing" type="java.lang.String" length="1" not-null="false" unique="false"
	 */
	public String getIsUsing() {
		return this.isUsing;
	}
	
	/**
	 * Set the isUsing
	 */	
	public void setIsUsing(String aValue) {
		this.isUsing = aValue;
	}	

	/**
	 * 是否开通P2P	 * @return String
	 * @hibernate.property column="isOpenP2P" type="java.lang.String" length="1" not-null="false" unique="false"
	 */
	public String getIsOpenP2P() {
		return this.isOpenP2P;
	}
	
	/**
	 * Set the isOpenP2P
	 */	
	public void setIsOpenP2P(String aValue) {
		this.isOpenP2P = aValue;
	}

	public Long getDocumentType() {
		return documentType;
	}

	public void setDocumentType(Long documentType) {
		this.documentType = documentType;
	}

	public String getTaxnum() {
		return taxnum;
	}

	public void setTaxnum(String taxnum) {
		this.taxnum = taxnum;
	}

	public String getIsCheckCard() {
		return isCheckCard;
	}

	public void setIsCheckCard(String isCheckCard) {
		this.isCheckCard = isCheckCard;
	}	





}
