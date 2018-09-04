package com.zhiwei.credit.model.creditFlow.customer.bankRelationPerson;

import com.zhiwei.core.model.BaseModel;

/**
 * CsBankContactperson entity. @author MyEclipse Persistence Tools
 */

public class CustomerBankRelationPerson extends BaseModel {

	private static final long serialVersionUID = 1L;
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
	private java.util.Date createdate;
	private String changer;
	private java.util.Date changerdate;
	private String bankaddress;
	private String hkszd;  //户口所在地
	private Integer sex ;
	private Integer fenbankid;
	
	private String bankName;
	
	private String belongedId;
	private Long createrId;
	private Long companyId;//分公司ID

	// Constructors

	public String getBankName() {
		return bankName;
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

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/** default constructor */
	public CustomerBankRelationPerson() {
	}

	/** full constructor */
	public CustomerBankRelationPerson(Integer bankid, String number, String name,
			String duty, String blmphone, String blmtelephone, String fax,
			String email, String homeaddress, String nativeplace,
			String birthplace, String mate, String parents, Integer marriage,
			java.util.Date marriagedate, java.util.Date birthday, String childname1,
			java.util.Date childbirthday1, String childname2,
			java.util.Date childbirthday2, String interest1, String interest2,
			String interest3, String interest4, String creater,
			java.util.Date createdate, String changer, java.util.Date changerdate ,
			String bankaddress , String hkszd ,Integer sex ,Integer fenbankid,Long companyId) {
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
		this.createdate = createdate;
		this.changer = changer;
		this.changerdate = changerdate;
		this.bankaddress = bankaddress;
		this.hkszd = hkszd ;
		this.sex = sex ;
		this.fenbankid = fenbankid ;
		this.companyId = companyId;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBankid() {
		return this.bankid;
	}

	public void setBankid(Integer bankid) {
		this.bankid = bankid;
	}

	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDuty() {
		return this.duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getBlmphone() {
		return this.blmphone;
	}

	public void setBlmphone(String blmphone) {
		this.blmphone = blmphone;
	}

	public String getBlmtelephone() {
		return this.blmtelephone;
	}

	public void setBlmtelephone(String blmtelephone) {
		this.blmtelephone = blmtelephone;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHomeaddress() {
		return this.homeaddress;
	}

	public void setHomeaddress(String homeaddress) {
		this.homeaddress = homeaddress;
	}

	public String getNativeplace() {
		return this.nativeplace;
	}

	public void setNativeplace(String nativeplace) {
		this.nativeplace = nativeplace;
	}

	public String getBirthplace() {
		return this.birthplace;
	}

	public void setBirthplace(String birthplace) {
		this.birthplace = birthplace;
	}

	public String getMate() {
		return this.mate;
	}

	public void setMate(String mate) {
		this.mate = mate;
	}

	public String getParents() {
		return this.parents;
	}

	public void setParents(String parents) {
		this.parents = parents;
	}

	public Integer getMarriage() {
		return this.marriage;
	}

	public void setMarriage(Integer marriage) {
		this.marriage = marriage;
	}

	public java.util.Date getMarriagedate() {
		return this.marriagedate;
	}

	public void setMarriagedate(java.util.Date marriagedate) {
		this.marriagedate = marriagedate;
	}

	public java.util.Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(java.util.Date birthday) {
		this.birthday = birthday;
	}

	public String getChildname1() {
		return this.childname1;
	}

	public void setChildname1(String childname1) {
		this.childname1 = childname1;
	}

	public java.util.Date getChildbirthday1() {
		return this.childbirthday1;
	}

	public void setChildbirthday1(java.util.Date childbirthday1) {
		this.childbirthday1 = childbirthday1;
	}

	public String getChildname2() {
		return this.childname2;
	}

	public void setChildname2(String childname2) {
		this.childname2 = childname2;
	}

	public java.util.Date getChildbirthday2() {
		return this.childbirthday2;
	}

	public void setChildbirthday2(java.util.Date childbirthday2) {
		this.childbirthday2 = childbirthday2;
	}

	public String getInterest1() {
		return this.interest1;
	}

	public void setInterest1(String interest1) {
		this.interest1 = interest1;
	}

	public String getInterest2() {
		return this.interest2;
	}

	public void setInterest2(String interest2) {
		this.interest2 = interest2;
	}

	public String getInterest3() {
		return this.interest3;
	}

	public void setInterest3(String interest3) {
		this.interest3 = interest3;
	}

	public String getInterest4() {
		return this.interest4;
	}

	public void setInterest4(String interest4) {
		this.interest4 = interest4;
	}

	public String getCreater() {
		return this.creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public java.util.Date getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(java.util.Date createdate) {
		this.createdate = createdate;
	}

	public String getChanger() {
		return this.changer;
	}

	public void setChanger(String changer) {
		this.changer = changer;
	}

	public java.util.Date getChangerdate() {
		return this.changerdate;
	}

	public void setChangerdate(java.util.Date changerdate) {
		this.changerdate = changerdate;
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

}