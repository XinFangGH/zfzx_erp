package com.zhiwei.credit.model.creditFlow.customer.bankRelationPerson;

import com.zhiwei.core.model.BaseModel;

/**
 * VBankBankcontactperson entity. @author MyEclipse Persistence Tools
 */

public class VBankBankcontactperson extends BaseModel  {

	// Fields
	private String bankname;
	private Integer id;
	private Integer bankid;
	private String number;
	private String name;
	private String duty;
	private String blmphone;
	private String blmtelephone;
	private String fax;
	private String email;
	private String homeaddress;
	private String nativeplace;
	private String birthplace;
	private String mate;
	private String parents;
	private Integer marriage;
	private java.util.Date marriagedate;
	private java.util.Date birthday;
	private String childname1;
	private java.util.Date childbirthday1;
	private String childname2;
	private java.util.Date childbirthday2;
	private String interest1;
	private String interest2;
	private String interest3;
	private String interest4;
	private String creater;
	private String marriagename;
	private Integer dicbankid ;
	private String bankaddress;
	private String hkszd ;
	
	
	private Integer sex ;
	private String sexvalue ;
	private Integer fenbankid;
	private String fenbankvalue;
	
	private String belongedId;
	private Long createrId;
	private String belongedName;
	private Long companyId;//分公司ID
	private String orgName;//分公司名称
	
	public String getBelongedName() {
		return belongedName;
	}
	public void setBelongedName(String belongedName) {
		this.belongedName = belongedName;
	}
	public String getBelongedId() {
		return belongedId;
	}
	public void setBelongedId(String belongedId) {
		this.belongedId = belongedId;
	}
	
	public Long getCreaterId() {
		return createrId;
	}
	public void setCreaterId(Long createrId) {
		this.createrId = createrId;
	}
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getBankid() {
		return bankid;
	}
	public void setBankid(Integer bankid) {
		this.bankid = bankid;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDuty() {
		return duty;
	}
	public void setDuty(String duty) {
		this.duty = duty;
	}
	public String getBlmphone() {
		return blmphone;
	}
	public void setBlmphone(String blmphone) {
		this.blmphone = blmphone;
	}
	public String getBlmtelephone() {
		return blmtelephone;
	}
	public void setBlmtelephone(String blmtelephone) {
		this.blmtelephone = blmtelephone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getHomeaddress() {
		return homeaddress;
	}
	public void setHomeaddress(String homeaddress) {
		this.homeaddress = homeaddress;
	}
	public String getNativeplace() {
		return nativeplace;
	}
	public void setNativeplace(String nativeplace) {
		this.nativeplace = nativeplace;
	}
	public String getBirthplace() {
		return birthplace;
	}
	public void setBirthplace(String birthplace) {
		this.birthplace = birthplace;
	}
	public String getMate() {
		return mate;
	}
	public void setMate(String mate) {
		this.mate = mate;
	}
	public String getParents() {
		return parents;
	}
	public void setParents(String parents) {
		this.parents = parents;
	}
	public Integer getMarriage() {
		return marriage;
	}
	public void setMarriage(Integer marriage) {
		this.marriage = marriage;
	}
	public java.util.Date getMarriagedate() {
		return marriagedate;
	}
	public void setMarriagedate(java.util.Date marriagedate) {
		this.marriagedate = marriagedate;
	}
	public java.util.Date getBirthday() {
		return birthday;
	}
	public void setBirthday(java.util.Date birthday) {
		this.birthday = birthday;
	}
	public String getChildname1() {
		return childname1;
	}
	public void setChildname1(String childname1) {
		this.childname1 = childname1;
	}
	public java.util.Date getChildbirthday1() {
		return childbirthday1;
	}
	public void setChildbirthday1(java.util.Date childbirthday1) {
		this.childbirthday1 = childbirthday1;
	}
	public String getChildname2() {
		return childname2;
	}
	public void setChildname2(String childname2) {
		this.childname2 = childname2;
	}
	public java.util.Date getChildbirthday2() {
		return childbirthday2;
	}
	public void setChildbirthday2(java.util.Date childbirthday2) {
		this.childbirthday2 = childbirthday2;
	}
	public String getInterest1() {
		return interest1;
	}
	public void setInterest1(String interest1) {
		this.interest1 = interest1;
	}
	public String getInterest2() {
		return interest2;
	}
	public void setInterest2(String interest2) {
		this.interest2 = interest2;
	}
	public String getInterest3() {
		return interest3;
	}
	public void setInterest3(String interest3) {
		this.interest3 = interest3;
	}
	public String getInterest4() {
		return interest4;
	}
	public void setInterest4(String interest4) {
		this.interest4 = interest4;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public String getMarriagename() {
		return marriagename;
	}
	public void setMarriagename(String marriagename) {
		this.marriagename = marriagename;
	}
	public Integer getDicbankid() {
		return dicbankid;
	}
	public void setDicbankid(Integer dicbankid) {
		this.dicbankid = dicbankid;
	}
	public String getBankaddress() {
		return bankaddress;
	}
	public void setBankaddress(String bankaddress) {
		this.bankaddress = bankaddress;
	}
	public String getHkszd() {
		return hkszd;
	}
	public void setHkszd(String hkszd) {
		this.hkszd = hkszd;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getSexvalue() {
		return sexvalue;
	}
	public void setSexvalue(String sexvalue) {
		this.sexvalue = sexvalue;
	}
	public String getFenbankvalue() {
		return fenbankvalue;
	}
	public void setFenbankvalue(String fenbankvalue) {
		this.fenbankvalue = fenbankvalue;
	}
	public Integer getFenbankid() {
		return fenbankid;
	}
	public void setFenbankid(Integer fenbankid) {
		this.fenbankid = fenbankid;
	}
	
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public VBankBankcontactperson(String bankname, Integer id,
			Integer bankid, String number, String name, String duty,
			String blmphone, String blmtelephone, String fax, String email,
			String homeaddress, String nativeplace, String birthplace,
			String mate, String parents, Integer marriage,
			java.util.Date marriagedate, java.util.Date birthday, String childname1,
			java.util.Date childbirthday1, String childname2,
			java.util.Date childbirthday2, String interest1, String interest2,
			String interest3, String interest4, String creater,
			String marriagename , Integer dicbankid ,String bankaddress,String hkszd
			,String sexvalue ,Integer sex ,String fenbankvalue ,Integer fenbankid,Long companyId) {
		super();
		this.bankname = bankname;
		this.id = id;
		this.bankid = bankid;
		this.number = number;
		this.name = name;
		this.duty = duty;
		this.blmphone = blmphone;
		this.blmtelephone = blmtelephone;
		this.fax = fax;
		this.email = email;
		this.homeaddress = homeaddress;
		this.nativeplace = nativeplace;
		this.birthplace = birthplace;
		this.mate = mate;
		this.parents = parents;
		this.marriage = marriage;
		this.marriagedate = marriagedate;
		this.birthday = birthday;
		this.childname1 = childname1;
		this.childbirthday1 = childbirthday1;
		this.childname2 = childname2;
		this.childbirthday2 = childbirthday2;
		this.interest1 = interest1;
		this.interest2 = interest2;
		this.interest3 = interest3;
		this.interest4 = interest4;
		this.creater = creater;
		this.marriagename = marriagename;
		this.dicbankid = dicbankid ;
		this.bankaddress = bankaddress;
		this.hkszd = hkszd ;
		this.sexvalue=sexvalue ;
		this.sex = sex ;
		this.fenbankvalue = fenbankvalue ;
		this.fenbankid = fenbankid ;
		this.companyId = companyId;
	}
	public VBankBankcontactperson(){};

}