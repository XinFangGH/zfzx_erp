package com.zhiwei.credit.model.creditFlow.customer.enterprise;


import java.math.BigDecimal;
import java.util.Date;

import com.google.gson.annotations.Expose;
import com.zhiwei.core.model.BaseModel;

public class EnterpriseView  extends BaseModel  implements java.io.Serializable {


	private static final long serialVersionUID = 1L;
    private Integer id;
 	private Integer controlpersonid;
 	private Integer legalpersonid;
 	private Integer linkmampersonid;
 	private String enterprisename;
 	private String shortname;
 	private String organizecode;
 	private String ownership;
 	private String tradetype;
 	private String cciaa;
 	private String managescope;
 	private String capitalkind;
 	private Double registermoney;
 	private String address;
 	private String factaddress;
 	private String managecity;
 	private String gslname;
 	private Boolean gslexamine;
 	private java.util.Date gslexaminedate;
 	private String taxname;
 	private String taxnum;
 	private Boolean taxexamine;
 	private String website;
 	private String changer;
 	private java.util.Date changedate;
 	private String controlperson;
 	private String legalperson;
 	private String linkperson;
 	private String ownershipv;
 	private String tradetypev;
 	private String capitalkindv;
 	private String linkpersontel;
 	private String linkpersonmobile;
 	private Integer linkpersonjob;
 	private String lkmduty;
 	private java.util.Date taxexaminedate;
 	private String enjoytax;
 	private String dstaxname;
 	private Boolean dstaxexamine;
 	private String dstaxnum;
 	private java.util.Date dsexaminedate;
 	private String enjoyds;
 	private String creater;
 	private java.util.Date createdate;
 	private Long createrId;//企业客户信息录入人
    private String belongedId;//企业客户所有人
     
     private String test1;
     private String test2 ;
     private String test3 ;
     private String test4 ;
     
 	 private String telephone ;               //联系电话
	 private String fax ;                     //传真
	 private String receiveMail ;             //收件人
	 private String email;
	 
	private java.util.Date registerstartdate;       //营业执照起始日期
	private java.util.Date registerenddate ;    //营业执照截止日期 
    private String creditaccountbum ;     //  企业贷款卡号码
    private String area;                 // 企业通信地址
    private java.util.Date opendate ;         //企业成立日期
    private Integer employeetotal ; // 职工人数
    private String  postcoding ; //邮政编码
    
    private String managecityvalue ;
	private String gslnamevalue ;
	private Integer hangyetype;
	private String hangyetypevalue;
    private Boolean isBlack;
    private String blackReason;
    private String customerLevel;
    private String orgName;
    private Long orgUserId;
	 private String orgUserName;
    
	 /**
	  * 资金账户
	  */
	protected Long accountId;
	protected String accountName;
	protected String accountNumber;
	protected Long investmentPersonId;
	protected Short investPersionType;
	protected BigDecimal totalMoney;
	
	/**
	 * p2p账号管理
	 */
	protected Long p2pId;
	protected String loginname;
	protected String truename;
	protected String cardcode;
	protected String thirdPayFlagId;
	protected String p2ptelphone;
	
	
	public Long getP2pId() {
		return p2pId;
	}

	public void setP2pId(Long p2pId) {
		this.p2pId = p2pId;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	public String getCardcode() {
		return cardcode;
	}

	public void setCardcode(String cardcode) {
		this.cardcode = cardcode;
	}

	public String getThirdPayFlagId() {
		return thirdPayFlagId;
	}

	public void setThirdPayFlagId(String thirdPayFlagId) {
		this.thirdPayFlagId = thirdPayFlagId;
	}

	public String getP2ptelphone() {
		return p2ptelphone;
	}

	public void setP2ptelphone(String p2ptelphone) {
		this.p2ptelphone = p2ptelphone;
	}

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
	 
	 

	public Long getOrgUserId() {
		return orgUserId;
	}

	public void setOrgUserId(Long orgUserId) {
		this.orgUserId = orgUserId;
	}

	public String getOrgUserName() {
		return orgUserName;
	}

	public void setOrgUserName(String orgUserName) {
		this.orgUserName = orgUserName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Boolean getIsBlack() {
		return isBlack;
	}

	public void setIsBlack(Boolean isBlack) {
		this.isBlack = isBlack;
	}

	public String getBlackReason() {
		return blackReason;
	}

	public void setBlackReason(String blackReason) {
		this.blackReason = blackReason;
	}

	public String getCustomerLevel() {
		return customerLevel;
	}

	public void setCustomerLevel(String customerLevel) {
		this.customerLevel = customerLevel;
	}

	public String getHangyetypevalue() {
		return hangyetypevalue;
	}

	public void setHangyetypevalue(String hangyetypevalue) {
		this.hangyetypevalue = hangyetypevalue;
	}

	public Integer getHangyetype() {
		return hangyetype;
	}

	public void setHangyetype(Integer hangyetype) {
		this.hangyetype = hangyetype;
	}

	public String getTest1() {
		return test1;
	}

	public void setTest1(String test1) {
		this.test1 = test1;
	}

	public String getTest2() {
		return test2;
	}

	public void setTest2(String test2) {
		this.test2 = test2;
	}

	public String getTest3() {
		return test3;
	}

	public void setTest3(String test3) {
		this.test3 = test3;
	}

	public String getTest4() {
		return test4;
	}

	public void setTest4(String test4) {
		this.test4 = test4;
	}

	public EnterpriseView(Integer id, Integer controlpersonid,
			Integer legalpersonid, Integer linkmampersonid,
			String enterprisename, String shortname, String organizecode,
			String ownership, String tradetype, String cciaa,
			String managescope, String capitalkind, Double registermoney,
			String address, String factaddress, String managecity,
			String gslname, Boolean gslexamine, Date gslexaminedate,
			String taxname, String taxnum, Boolean taxexamine, String website,
			String changer, Date changedate, String controlperson,
			String legalperson, String linkperson, String ownershipv,
			String tradetypev, String capitalkindv, String linkpersontel,
			String linkpersonmobile, Integer linkpersonjob, String lkmduty,
			Date taxexaminedate, String enjoytax, String dstaxname,
			Boolean dstaxexamine, String dstaxnum, Date dsexaminedate,
			String enjoyds, String creater, Date createdate, Long createrId,
			String belongedId, String test1, String test2, String test3,
			String test4, String telephone, String fax, String receiveMail,
			String email, Date registerstartdate, Date registerenddate,
			String creditaccountbum, String area, Date opendate,
			Integer employeetotal, String postcoding, String managecityvalue,
			String gslnamevalue, Integer hangyetype, String hangyetypevalue,
			Boolean isBlack, String blackReason, String customerLevel,
			String orgName, Long orgUserId, String orgUserName) {
		super();
		this.id = id;
		this.controlpersonid = controlpersonid;
		this.legalpersonid = legalpersonid;
		this.linkmampersonid = linkmampersonid;
		this.enterprisename = enterprisename;
		this.shortname = shortname;
		this.organizecode = organizecode;
		this.ownership = ownership;
		this.tradetype = tradetype;
		this.cciaa = cciaa;
		this.managescope = managescope;
		this.capitalkind = capitalkind;
		this.registermoney = registermoney;
		this.address = address;
		this.factaddress = factaddress;
		this.managecity = managecity;
		this.gslname = gslname;
		this.gslexamine = gslexamine;
		this.gslexaminedate = gslexaminedate;
		this.taxname = taxname;
		this.taxnum = taxnum;
		this.taxexamine = taxexamine;
		this.website = website;
		this.changer = changer;
		this.changedate = changedate;
		this.controlperson = controlperson;
		this.legalperson = legalperson;
		this.linkperson = linkperson;
		this.ownershipv = ownershipv;
		this.tradetypev = tradetypev;
		this.capitalkindv = capitalkindv;
		this.linkpersontel = linkpersontel;
		this.linkpersonmobile = linkpersonmobile;
		this.linkpersonjob = linkpersonjob;
		this.lkmduty = lkmduty;
		this.taxexaminedate = taxexaminedate;
		this.enjoytax = enjoytax;
		this.dstaxname = dstaxname;
		this.dstaxexamine = dstaxexamine;
		this.dstaxnum = dstaxnum;
		this.dsexaminedate = dsexaminedate;
		this.enjoyds = enjoyds;
		this.creater = creater;
		this.createdate = createdate;
		this.createrId = createrId;
		this.belongedId = belongedId;
		this.test1 = test1;
		this.test2 = test2;
		this.test3 = test3;
		this.test4 = test4;
		this.telephone = telephone;
		this.fax = fax;
		this.receiveMail = receiveMail;
		this.email = email;
		this.registerstartdate = registerstartdate;
		this.registerenddate = registerenddate;
		this.creditaccountbum = creditaccountbum;
		this.area = area;
		this.opendate = opendate;
		this.employeetotal = employeetotal;
		this.postcoding = postcoding;
		this.managecityvalue = managecityvalue;
		this.gslnamevalue = gslnamevalue;
		this.hangyetype = hangyetype;
		this.hangyetypevalue = hangyetypevalue;
		this.isBlack = isBlack;
		this.blackReason = blackReason;
		this.customerLevel = customerLevel;
		this.orgName = orgName;
		this.orgUserId = orgUserId;
		this.orgUserName = orgUserName;
	}

	public EnterpriseView(Integer id, Integer controlpersonid,
			Integer legalpersonid, Integer linkmampersonid,
			String enterprisename, String shortname, String organizecode,
			String ownership, String tradetype, String area, String cciaa,
			String managescope, java.util.Date registerstartdate,java.util.Date registerenddate,
			String capitalkind, Double registermoney, String address,
			String factaddress, String managecity, String gslname,
			Boolean gslexamine, java.util.Date gslexaminedate, String taxname,
			String taxnum, Boolean taxexamine, String website, String changer,
			java.util.Date changedate, String controlperson, String legalperson,
			String linkperson, String ownershipv, String tradetypev,
			String capitalkindv, String linkpersontel, String linkpersonmobile,
			Integer linkpersonjob, String lkmduty, java.util.Date taxexaminedate,
			String enjoytax, String dstaxname, Boolean dstaxexamine,
			String dstaxnum, java.util.Date dsexaminedate, String enjoyds,
			String creater, java.util.Date createdate, String test1, String test2,
			String test3, String test4,String creditaccountbum,
			String telephone ,String fax,String receiveMail,String gslnamevalue,
			java.util.Date opendate,Integer employeetotal,String  postcoding,String managecityvalue) {
		super();
		this.id = id;
		this.controlpersonid = controlpersonid;
		this.legalpersonid = legalpersonid;
		this.linkmampersonid = linkmampersonid;
		this.enterprisename = enterprisename;
		this.shortname = shortname;
		this.organizecode = organizecode;
		this.ownership = ownership;
		this.tradetype = tradetype;
		this.area = area;
		this.cciaa = cciaa;
		this.managescope = managescope;
		this.capitalkind = capitalkind;
		this.registermoney = registermoney;
		this.address = address;
		this.factaddress = factaddress;
		this.managecity = managecity;
		this.gslname = gslname;
		this.gslexamine = gslexamine;
		this.gslexaminedate = gslexaminedate;
		this.taxname = taxname;
		this.taxnum = taxnum;
		this.taxexamine = taxexamine;
		this.website = website;
		this.changer = changer;
		this.changedate = changedate;
		this.controlperson = controlperson;
		this.legalperson = legalperson;
		this.linkperson = linkperson;
		this.ownershipv = ownershipv;
		this.tradetypev = tradetypev;
		this.capitalkindv = capitalkindv;
		this.linkpersontel = linkpersontel;
		this.linkpersonmobile = linkpersonmobile;
		this.linkpersonjob = linkpersonjob;
		this.lkmduty = lkmduty;
		this.taxexaminedate = taxexaminedate;
		this.enjoytax = enjoytax;
		this.dstaxname = dstaxname;
		this.dstaxexamine = dstaxexamine;
		this.dstaxnum = dstaxnum;
		this.dsexaminedate = dsexaminedate;
		this.enjoyds = enjoyds;
		this.creater = creater;
		this.createdate = createdate;
		this.test1 = test1;
		this.test2 = test2;
		this.test3 = test3;
		this.test4 = test4;
		this.telephone =telephone ;
		this.fax = fax ;
		this.receiveMail = receiveMail ;
		this.registerstartdate = registerstartdate ;
		this.registerenddate = registerenddate ;
		this.creditaccountbum = creditaccountbum ;
		this.opendate = opendate ;
		this.employeetotal = employeetotal ;
		this.postcoding = postcoding ;
		this.managecityvalue = managecityvalue ;
		this.gslnamevalue = gslnamevalue ;
	}

	public EnterpriseView() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getControlpersonid() {
		return controlpersonid;
	}

	public void setControlpersonid(Integer controlpersonid) {
		this.controlpersonid = controlpersonid;
	}

	public Integer getLegalpersonid() {
		return legalpersonid;
	}

	public void setLegalpersonid(Integer legalpersonid) {
		this.legalpersonid = legalpersonid;
	}

	public Integer getLinkmampersonid() {
		return linkmampersonid;
	}

	public void setLinkmampersonid(Integer linkmampersonid) {
		this.linkmampersonid = linkmampersonid;
	}

	public String getEnterprisename() {
		return enterprisename;
	}

	public void setEnterprisename(String enterprisename) {
		this.enterprisename = enterprisename;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public String getOrganizecode() {
		return organizecode;
	}

	public void setOrganizecode(String organizecode) {
		this.organizecode = organizecode;
	}

	public String getOwnership() {
		return ownership;
	}

	public void setOwnership(String ownership) {
		this.ownership = ownership;
	}

	public String getTradetype() {
		return tradetype;
	}

	public void setTradetype(String tradetype) {
		this.tradetype = tradetype;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCciaa() {
		return cciaa;
	}

	public void setCciaa(String cciaa) {
		this.cciaa = cciaa;
	}

	public String getManagescope() {
		return managescope;
	}

	public void setManagescope(String managescope) {
		this.managescope = managescope;
	}

	public String getCapitalkind() {
		return capitalkind;
	}

	public void setCapitalkind(String capitalkind) {
		this.capitalkind = capitalkind;
	}

	public Double getRegistermoney() {
		return registermoney;
	}

	public void setRegistermoney(Double registermoney) {
		this.registermoney = registermoney;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFactaddress() {
		return factaddress;
	}

	public void setFactaddress(String factaddress) {
		this.factaddress = factaddress;
	}

	public String getManagecity() {
		return managecity;
	}

	public void setManagecity(String managecity) {
		this.managecity = managecity;
	}

	public String getGslname() {
		return gslname;
	}

	public void setGslname(String gslname) {
		this.gslname = gslname;
	}

	public Boolean getGslexamine() {
		return gslexamine;
	}

	public void setGslexamine(Boolean gslexamine) {
		this.gslexamine = gslexamine;
	}

	public java.util.Date getGslexaminedate() {
		return gslexaminedate;
	}

	public void setGslexaminedate(java.util.Date gslexaminedate) {
		this.gslexaminedate = gslexaminedate;
	}

	public String getTaxname() {
		return taxname;
	}

	public void setTaxname(String taxname) {
		this.taxname = taxname;
	}

	public String getTaxnum() {
		return taxnum;
	}

	public void setTaxnum(String taxnum) {
		this.taxnum = taxnum;
	}

	public Boolean getTaxexamine() {
		return taxexamine;
	}

	public void setTaxexamine(Boolean taxexamine) {
		this.taxexamine = taxexamine;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getChanger() {
		return changer;
	}

	public void setChanger(String changer) {
		this.changer = changer;
	}

	public java.util.Date getChangedate() {
		return changedate;
	}

	public void setChangedate(java.util.Date changedate) {
		this.changedate = changedate;
	}

	public String getControlperson() {
		return controlperson;
	}

	public void setControlperson(String controlperson) {
		this.controlperson = controlperson;
	}

	public String getLegalperson() {
		return legalperson;
	}

	public void setLegalperson(String legalperson) {
		this.legalperson = legalperson;
	}

	public String getLinkperson() {
		return linkperson;
	}

	public void setLinkperson(String linkperson) {
		this.linkperson = linkperson;
	}

	public String getOwnershipv() {
		return ownershipv;
	}

	public void setOwnershipv(String ownershipv) {
		this.ownershipv = ownershipv;
	}

	public String getTradetypev() {
		return tradetypev;
	}

	public void setTradetypev(String tradetypev) {
		this.tradetypev = tradetypev;
	}

	public String getCapitalkindv() {
		return capitalkindv;
	}

	public void setCapitalkindv(String capitalkindv) {
		this.capitalkindv = capitalkindv;
	}

	public String getLinkpersontel() {
		return linkpersontel;
	}

	public void setLinkpersontel(String linkpersontel) {
		this.linkpersontel = linkpersontel;
	}

	public String getLinkpersonmobile() {
		return linkpersonmobile;
	}

	public void setLinkpersonmobile(String linkpersonmobile) {
		this.linkpersonmobile = linkpersonmobile;
	}

	public Integer getLinkpersonjob() {
		return linkpersonjob;
	}

	public void setLinkpersonjob(Integer linkpersonjob) {
		this.linkpersonjob = linkpersonjob;
	}

	public String getLkmduty() {
		return lkmduty;
	}

	public void setLkmduty(String lkmduty) {
		this.lkmduty = lkmduty;
	}

	public java.util.Date getTaxexaminedate() {
		return taxexaminedate;
	}

	public void setTaxexaminedate(java.util.Date taxexaminedate) {
		this.taxexaminedate = taxexaminedate;
	}

	public String getEnjoytax() {
		return enjoytax;
	}

	public void setEnjoytax(String enjoytax) {
		this.enjoytax = enjoytax;
	}

	public String getDstaxname() {
		return dstaxname;
	}

	public void setDstaxname(String dstaxname) {
		this.dstaxname = dstaxname;
	}

	public Boolean getDstaxexamine() {
		return dstaxexamine;
	}

	public void setDstaxexamine(Boolean dstaxexamine) {
		this.dstaxexamine = dstaxexamine;
	}

	public String getDstaxnum() {
		return dstaxnum;
	}

	public void setDstaxnum(String dstaxnum) {
		this.dstaxnum = dstaxnum;
	}

	public java.util.Date getDsexaminedate() {
		return dsexaminedate;
	}

	public void setDsexaminedate(java.util.Date dsexaminedate) {
		this.dsexaminedate = dsexaminedate;
	}

	public String getEnjoyds() {
		return enjoyds;
	}

	public void setEnjoyds(String enjoyds) {
		this.enjoyds = enjoyds;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public java.util.Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(java.util.Date createdate) {
		this.createdate = createdate;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getReceiveMail() {
		return receiveMail;
	}

	public void setReceiveMail(String receiveMail) {
		this.receiveMail = receiveMail;
	}

	public java.util.Date getRegisterstartdate() {
		return registerstartdate;
	}

	public void setRegisterstartdate(java.util.Date registerstartdate) {
		this.registerstartdate = registerstartdate;
	}

	public java.util.Date getRegisterenddate() {
		return registerenddate;
	}

	public void setRegisterenddate(java.util.Date registerenddate) {
		this.registerenddate = registerenddate;
	}

	public String getCreditaccountbum() {
		return creditaccountbum;
	}

	public void setCreditaccountbum(String creditaccountbum) {
		this.creditaccountbum = creditaccountbum;
	}

	public java.util.Date getOpendate() {
		return opendate;
	}

	public void setOpendate(java.util.Date opendate) {
		this.opendate = opendate;
	}

	public Integer getEmployeetotal() {
		return employeetotal;
	}

	public void setEmployeetotal(Integer employeetotal) {
		this.employeetotal = employeetotal;
	}

	public String getPostcoding() {
		return postcoding;
	}

	public void setPostcoding(String postcoding) {
		this.postcoding = postcoding;
	}

	public String getManagecityvalue() {
		return managecityvalue;
	}

	public void setManagecityvalue(String managecityvalue) {
		this.managecityvalue = managecityvalue;
	}

	public String getGslnamevalue() {
		return gslnamevalue;
	}

	public void setGslnamevalue(String gslnamevalue) {
		this.gslnamevalue = gslnamevalue;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public String toElementStr(){
		StringBuffer sb = new StringBuffer();
		sb.append("甲方（借款人）:"+this.getEnterprisename()+"</br>");
		sb.append("证件号码："+this.getCciaa()+"</br>");
		sb.append("通讯地址："+this.getAddress()+"</br>");
		sb.append("联系电话："+this.getTelephone()+"</br>");
		return sb.toString();
	}
   
}