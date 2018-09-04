package com.zhiwei.credit.model.creditFlow.creditAssignment.customer;


import com.zhiwei.core.model.BaseModel;

/**
 * VPersonDic entity. @author MyEclipse Persistence Tools
 */

public class VInvestmentPerson extends BaseModel implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Long investId;   //投资客户id
	protected String investName;//投资客户姓名
	protected Long shopId;

	protected String shopName;
	private Integer sex;   
	private Integer cardtype;    
	private String sexvalue;
	private String postaddress;
	private String postcode;
	private String cellphone;
	private String cardnumber;
	private String cardtypevalue;
	private String selfemail;
	private java.util.Date createdate;
	private String creater;

	//身份证图片
	private String personSFZZUrl;
	private Integer personSFZZId;
	private String personSFZZExtendName;
	
	private String personSFZFUrl;
	private Integer personSFZFId;
	private String personSFZFExtendName;

	//银行卡图片上传personYHKZId
	
	private String personYHKZUrl;
	private Integer personYHKZId;
	private String personYHKZExtendName;
	
	private String personYHKFUrl;
	private Integer personYHKFId;
	private String personYHKFExtendName;
	private String belongedId;
	private String belongedName;
	private Long createrId;
	
	private String orgName;
    private String bankName;//银行名称
    private String bankNum;//贷款卡卡号
    private Long accountId;//银行表的主键
	private Long bankId;
	private Integer enterpriseBankId;
    private Short openType;//开户类型
    private Short accountType;//账户类型
    private String khname;//开户名称
    private String areaId;
    private String areaName;
    private String bankOutletsName;
	private String remarks;
	private Short openCurrency;//0本币1外币
	private Short isEnterprise;//是否是企业(0企业，1是个人)
	private Short isInvest;//是否是投资客户(0是,1否)
	private Short iscredit;//是否有贷款
	private Integer changeCardStatus;
	private Integer contractStatus; //流程是否启动 (1表示启动)
    
	protected String birthDay;
	
    public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	
	public String getBankOutletsName() {
		return bankOutletsName;
	}

	public void setBankOutletsName(String bankOutletsName) {
		this.bankOutletsName = bankOutletsName;
	}

	public Integer getEnterpriseBankId() {
		return enterpriseBankId;
	}

	public void setEnterpriseBankId(Integer enterpriseBankId) {
		this.enterpriseBankId = enterpriseBankId;
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public Short getOpenType() {
		return openType;
	}

	public void setOpenType(Short openType) {
		this.openType = openType;
	}

	public Short getAccountType() {
		return accountType;
	}

	public void setAccountType(Short accountType) {
		this.accountType = accountType;
	}

	public String getKhname() {
		return khname;
	}

	public void setKhname(String khname) {
		this.khname = khname;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankNum() {
		return bankNum;
	}

	public void setBankNum(String bankNum) {
		this.bankNum = bankNum;
	}

	

	public String getOrgName() {
			return orgName;
		}

		public void setOrgName(String orgName) {
			this.orgName = orgName;
	}
	
	public Long getCreaterId() {
		return createrId;
	}
	public void setCreaterId(Long createrId) {
		this.createrId = createrId;
	}
	public String getBelongedId() {
		return belongedId;
	}
	public void setBelongedId(String belongedId) {
		this.belongedId = belongedId;
	}
	public String getBelongedName() {
		return belongedName;
	}
	public void setBelongedName(String belongedName) {
		this.belongedName = belongedName;
	}

	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getCardtype() {
		return cardtype;
	}
	public void setCardtype(Integer cardtype) {
		this.cardtype = cardtype;
	}
	public String getSexvalue() {
		return sexvalue;
	}
	public void setSexvalue(String sexvalue) {
		this.sexvalue = sexvalue;
	}

	public String getPostaddress() {
		return postaddress;
	}
	public void setPostaddress(String postaddress) {
		this.postaddress = postaddress;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getCellphone() {
		return cellphone;
	}
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getCardnumber() {
		return cardnumber;
	}
	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}

	public String getCardtypevalue() {
		return cardtypevalue;
	}
	public void setCardtypevalue(String cardtypevalue) {
		this.cardtypevalue = cardtypevalue;
	}

	public String getSelfemail() {
		return selfemail;
	}
	public void setSelfemail(String selfemail) {
		this.selfemail = selfemail;
	}

	public java.util.Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(java.util.Date createdate) {
		this.createdate = createdate;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	
	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public void setPersonSFZZUrl(String personSFZZUrl) {
		this.personSFZZUrl = personSFZZUrl;
	}

	public String getPersonSFZZUrl() {
		return personSFZZUrl;
	}

	public void setPersonSFZZId(Integer personSFZZId) {
		this.personSFZZId = personSFZZId;
	}

	public Integer getPersonSFZZId() {
		return personSFZZId;
	}

	public void setPersonSFZZExtendName(String personSFZZExtendName) {
		this.personSFZZExtendName = personSFZZExtendName;
	}

	public String getPersonSFZZExtendName() {
		return personSFZZExtendName;
	}

	public void setPersonSFZFUrl(String personSFZFUrl) {
		this.personSFZFUrl = personSFZFUrl;
	}

	public String getPersonSFZFUrl() {
		return personSFZFUrl;
	}

	public void setPersonSFZFId(Integer personSFZFId) {
		this.personSFZFId = personSFZFId;
	}

	public Integer getPersonSFZFId() {
		return personSFZFId;
	}

	public void setPersonSFZFExtendName(String personSFZFExtendName) {
		this.personSFZFExtendName = personSFZFExtendName;
	}

	public String getPersonSFZFExtendName() {
		return personSFZFExtendName;
	}
	
	public Long getInvestId() {
		return investId;
	}

	public void setInvestId(Long investId) {
		this.investId = investId;
	}

	public String getInvestName() {
		return investName;
	}

	public void setInvestName(String investName) {
		this.investName = investName;
	}

	public Integer getPersonYHKZId() {
		return personYHKZId;
	}

	public void setPersonYHKZId(Integer personYHKZId) {
		this.personYHKZId = personYHKZId;
	}

	public String getPersonYHKZExtendName() {
		return personYHKZExtendName;
	}

	public void setPersonYHKZExtendName(String personYHKZExtendName) {
		this.personYHKZExtendName = personYHKZExtendName;
	}

	public String getPersonYHKFUrl() {
		return personYHKFUrl;
	}

	public void setPersonYHKFUrl(String personYHKFUrl) {
		this.personYHKFUrl = personYHKFUrl;
	}

	public Integer getPersonYHKFId() {
		return personYHKFId;
	}

	public void setPersonYHKFId(Integer personYHKFId) {
		this.personYHKFId = personYHKFId;
	}

	public String getPersonYHKFExtendName() {
		return personYHKFExtendName;
	}

	public void setPersonYHKFExtendName(String personYHKFExtendName) {
		this.personYHKFExtendName = personYHKFExtendName;
	}

	public void setPersonYHKZUrl(String personYHKZUrl) {
		this.personYHKZUrl = personYHKZUrl;
	}

	public String getPersonYHKZUrl() {
		return personYHKZUrl;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setOpenCurrency(Short openCurrency) {
		this.openCurrency = openCurrency;
	}

	public Short getOpenCurrency() {
		return openCurrency;
	}

	public void setIsEnterprise(Short isEnterprise) {
		this.isEnterprise = isEnterprise;
	}

	public Short getIsEnterprise() {
		return isEnterprise;
	}

	public void setIsInvest(Short isInvest) {
		this.isInvest = isInvest;
	}

	public Short getIsInvest() {
		return isInvest;
	}

	public Short getIscredit() {
		return iscredit;
	}

	public void setIscredit(Short iscredit) {
		this.iscredit = iscredit;
	}


	public Integer getContractStatus() {
		return contractStatus;
	}

	public Long getAccountId() {
		return accountId;
	}



	public void setContractStatus(Integer contractStatus) {
		this.contractStatus = contractStatus;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Integer getChangeCardStatus() {
		return changeCardStatus;
	}

	public void setChangeCardStatus(Integer changeCardStatus) {
		this.changeCardStatus = changeCardStatus;
	}

	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}



	

}