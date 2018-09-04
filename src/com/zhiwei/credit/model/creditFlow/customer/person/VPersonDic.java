package com.zhiwei.credit.model.creditFlow.customer.person;


import java.math.BigDecimal;

import com.zhiwei.core.model.BaseModel;

/**
 * VPersonDic entity. @author MyEclipse Persistence Tools
 */

public class VPersonDic extends BaseModel implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private Integer sex;   
	private Integer cardtype;    
	private String sexvalue;
	private Integer familysheng;
	private Integer familyshi;
	private Integer familyxian;
	private String shengvalue;
	private String shivalue;
	private String xianvalue;
	private String peopletypevalue;
	private Integer nationality;    //
	private java.util.Date birthday;
	private Integer peopletype;    //
	private Integer marry;    //
	private Integer dgree;    //
	private String postaddress;
	private String postcode;
	private String fax;
	private String cellphone;
	private String telphone;
	private String cardnumber;
	private String nationalityvalue;
	private String marryvalue;
	private String cardtypevalue;
	private String dgreevalue;
	private String degreeweivalue;
	private Integer degreewei;    //  g
	private Double homecreditexpend;
	private Double homeexpend;
	private String jobvalue;
	private String sonnelvalue;
	private String employwayvalue;
	private Integer homeshape;   // g
	private Integer employway;    //  g
	private Double homeincome;
	private String homeshapevalue;
	private String unitProvalue;
	private String selfemail;
	private String doorplatenum;
	private String communityname;
	private String roadnum;
	private String roadname;
	private Integer relationship;
	private String relationcellphone;
	private String relationphone;
	private String relationname;
	private String unitpostcode;
	private String unitphone;
	private Integer unitproperties;
	private String unitaddress;
	private Double jobincome;
	private java.util.Date jobstarttime;
	private Double registeredcapital;
	private Boolean ispublicservant;
	private String graduationunversity;
	private Double housearea;
	private String familypostcode;
	private String familyaddress;
	private Boolean isheadoffamily;
	private String englishname;
	private java.util.Date createdate;
	private String creater;
	private java.util.Date changedate;
	private String changer;
	private String remarks;
	private String mateaccount;
	private String mateperson;
	private String matebank;
	private String wageaccount;
	private String wageperson;
	private String wagebank;
	private String creditaccount;
	private String creditperson;
	private String creditbank;
	private Double yeargrossexpend;
	private Double grossdebt;
	private Double homeasset;
	private Double grossasset;
	private Integer techpersonnel;    //  职称
	private Integer job;     //  g
	private String currentcompany;
	private String homeland;
	private String hukou;
	private String address;
	private Integer jobtotal;
	private Integer id;
	private String unitpropertiesvalue;
	private Double household;
	private Integer mateid;
	
	private Double homeotherincome;
	private Double befMonthBalance;
	private Double bef2MonthBalance;
	private Double bef3MonthBalance;
	private Double bef4MonthBalance;
	private Double bef5MonthBalance;
	private Double bef6MonthBalance;
	private String relationshipvalue;
	private String relationship2value;
	private String relationship3value;
	
	private String beforeName;
	private Boolean steadyWage;
	
	private Integer showMonth;
	private String mateValue ;
	private String age ;
	private String personPhotoUrl;
	private Integer personPhotoId;
	private String personPhotoExtendName;
	
	private String personSFZZUrl;
	private Integer personSFZZId;
	private String personSFZZExtendName;
	
	private String personSFZFUrl;
	private Integer personSFZFId;
	private String personSFZFExtendName;
	
	private String zhusuo;
	
	private String belongedId;
	private String belongedName;
	private Long createrId;
	private String customerLevel;
	private Boolean isBlack;
	private String blackReason;
	private String orgName;
	private String personCount;//家庭人数
	private Short politicalStatus;//政治面貌
	private String livingLife;//本地居住年限
	private String recordAndRemarks;//信用记录及说明
	private BigDecimal disposableCapital;//家庭月可支配金额    
    private String bankName;//银行名称
    private String bankNum;//贷款卡卡号
	private Long bankId;
	private Integer enterpriseBankId;
    private Short openType;//开户类型
    private Short accountType;//账户类型
    private String khname;//开户名称
    private String areaId;
    private String areaName;
    private String bankOutletsName;
    //大连天储区分 子女数家庭人数
    private String childrenCount;//子女数
    private Long orgUserId;
	 private String orgUserName;
    
    private String archivesBelonging;
    
    
    
    public String getArchivesBelonging() {
		return archivesBelonging;
	}

	public void setArchivesBelonging(String archivesBelonging) {
		this.archivesBelonging = archivesBelonging;
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

	public String getPersonCount() {
		return personCount;
	}

	public void setPersonCount(String personCount) {
		this.personCount = personCount;
	}

	public Short getPoliticalStatus() {
		return politicalStatus;
	}

	public void setPoliticalStatus(Short politicalStatus) {
		this.politicalStatus = politicalStatus;
	}

	public String getLivingLife() {
		return livingLife;
	}

	public void setLivingLife(String livingLife) {
		this.livingLife = livingLife;
	}

	public String getRecordAndRemarks() {
		return recordAndRemarks;
	}

	public void setRecordAndRemarks(String recordAndRemarks) {
		this.recordAndRemarks = recordAndRemarks;
	}

	public BigDecimal getDisposableCapital() {
		return disposableCapital;
	}

	public void setDisposableCapital(BigDecimal disposableCapital) {
		this.disposableCapital = disposableCapital;
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
	public String getPersonPhotoExtendName() {
		return personPhotoExtendName;
	}
	public void setPersonPhotoExtendName(String personPhotoExtendName) {
		this.personPhotoExtendName = personPhotoExtendName;
	}
	public String getPersonSFZZExtendName() {
		return personSFZZExtendName;
	}
	public void setPersonSFZZExtendName(String personSFZZExtendName) {
		this.personSFZZExtendName = personSFZZExtendName;
	}
	public String getPersonSFZFExtendName() {
		return personSFZFExtendName;
	}
	public void setPersonSFZFExtendName(String personSFZFExtendName) {
		this.personSFZFExtendName = personSFZFExtendName;
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
	public String getZhusuo() {
		return zhusuo;
	}
	public void setZhusuo(String zhusuo) {
		this.zhusuo = zhusuo;
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
	public String getPersonPhotoUrl() {
		return personPhotoUrl;
	}
	public void setPersonPhotoUrl(String personPhotoUrl) {
		this.personPhotoUrl = personPhotoUrl;
	}
	public Integer getPersonPhotoId() {
		return personPhotoId;
	}
	public void setPersonPhotoId(Integer personPhotoId) {
		this.personPhotoId = personPhotoId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public void setJob(Integer job) {
		this.job = job;
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
	public Integer getJob() {
		return job;
	}
	public Integer getFamilysheng() {
		return familysheng;
	}
	public void setFamilysheng(Integer familysheng) {
		this.familysheng = familysheng;
	}
	public Integer getFamilyshi() {
		return familyshi;
	}
	public void setFamilyshi(Integer familyshi) {
		this.familyshi = familyshi;
	}
	public Integer getFamilyxian() {
		return familyxian;
	}
	public void setFamilyxian(Integer familyxian) {
		this.familyxian = familyxian;
	}
	public String getShengvalue() {
		return shengvalue;
	}
	public void setShengvalue(String shengvalue) {
		this.shengvalue = shengvalue;
	}
	public String getShivalue() {
		return shivalue;
	}
	public void setShivalue(String shivalue) {
		this.shivalue = shivalue;
	}
	public String getXianvalue() {
		return xianvalue;
	}
	public void setXianvalue(String xianvalue) {
		this.xianvalue = xianvalue;
	}
	public String getPeopletypevalue() {
		return peopletypevalue;
	}
	public void setPeopletypevalue(String peopletypevalue) {
		this.peopletypevalue = peopletypevalue;
	}
	public Integer getNationality() {
		return nationality;
	}
	public void setNationality(Integer nationality) {
		this.nationality = nationality;
	}
	public java.util.Date getBirthday() {
		return birthday;
	}
	public void setBirthday(java.util.Date birthday) {
		this.birthday = birthday;
	}
	public Integer getPeopletype() {
		return peopletype;
	}
	public void setPeopletype(Integer peopletype) {
		this.peopletype = peopletype;
	}
	public Integer getMarry() {
		return marry;
	}
	public void setMarry(Integer marry) {
		this.marry = marry;
	}
	public Integer getDgree() {
		return dgree;
	}
	public void setDgree(Integer dgree) {
		this.dgree = dgree;
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
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getCellphone() {
		return cellphone;
	}
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	public String getTelphone() {
		return telphone;
	}
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	public String getCardnumber() {
		return cardnumber;
	}
	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}
	public String getNationalityvalue() {
		return nationalityvalue;
	}
	public void setNationalityvalue(String nationalityvalue) {
		this.nationalityvalue = nationalityvalue;
	}
	public String getMarryvalue() {
		return marryvalue;
	}
	public void setMarryvalue(String marryvalue) {
		this.marryvalue = marryvalue;
	}
	public String getCardtypevalue() {
		return cardtypevalue;
	}
	public void setCardtypevalue(String cardtypevalue) {
		this.cardtypevalue = cardtypevalue;
	}
	public String getDgreevalue() {
		return dgreevalue;
	}
	public void setDgreevalue(String dgreevalue) {
		this.dgreevalue = dgreevalue;
	}
	public String getDegreeweivalue() {
		return degreeweivalue;
	}
	public void setDegreeweivalue(String degreeweivalue) {
		this.degreeweivalue = degreeweivalue;
	}
	
	public Double getHomecreditexpend() {
		return homecreditexpend;
	}
	public void setHomecreditexpend(Double homecreditexpend) {
		this.homecreditexpend = homecreditexpend;
	}
	public Double getHomeexpend() {
		return homeexpend;
	}
	public void setHomeexpend(Double homeexpend) {
		this.homeexpend = homeexpend;
	}
	public String getJobvalue() {
		return jobvalue;
	}
	public void setJobvalue(String jobvalue) {
		this.jobvalue = jobvalue;
	}
	public String getSonnelvalue() {
		return sonnelvalue;
	}
	public void setSonnelvalue(String sonnelvalue) {
		this.sonnelvalue = sonnelvalue;
	}
	public String getEmploywayvalue() {
		return employwayvalue;
	}
	public void setEmploywayvalue(String employwayvalue) {
		this.employwayvalue = employwayvalue;
	}
	
	public Double getHomeincome() {
		return homeincome;
	}
	public void setHomeincome(Double homeincome) {
		this.homeincome = homeincome;
	}
	public String getHomeshapevalue() {
		return homeshapevalue;
	}
	public void setHomeshapevalue(String homeshapevalue) {
		this.homeshapevalue = homeshapevalue;
	}
	public String getUnitProvalue() {
		return unitProvalue;
	}
	public void setUnitProvalue(String unitProvalue) {
		this.unitProvalue = unitProvalue;
	}
	public String getSelfemail() {
		return selfemail;
	}
	public void setSelfemail(String selfemail) {
		this.selfemail = selfemail;
	}
	public String getDoorplatenum() {
		return doorplatenum;
	}
	public void setDoorplatenum(String doorplatenum) {
		this.doorplatenum = doorplatenum;
	}
	public String getCommunityname() {
		return communityname;
	}
	public void setCommunityname(String communityname) {
		this.communityname = communityname;
	}
	public String getRoadnum() {
		return roadnum;
	}
	public void setRoadnum(String roadnum) {
		this.roadnum = roadnum;
	}
	public String getRoadname() {
		return roadname;
	}
	public void setRoadname(String roadname) {
		this.roadname = roadname;
	}
	public Integer getRelationship() {
		return relationship;
	}
	public void setRelationship(Integer relationship) {
		this.relationship = relationship;
	}
	public String getRelationcellphone() {
		return relationcellphone;
	}
	public void setRelationcellphone(String relationcellphone) {
		this.relationcellphone = relationcellphone;
	}
	public String getRelationphone() {
		return relationphone;
	}
	public void setRelationphone(String relationphone) {
		this.relationphone = relationphone;
	}
	public String getRelationname() {
		return relationname;
	}
	public void setRelationname(String relationname) {
		this.relationname = relationname;
	}
	public String getUnitpostcode() {
		return unitpostcode;
	}
	public void setUnitpostcode(String unitpostcode) {
		this.unitpostcode = unitpostcode;
	}
	public String getUnitphone() {
		return unitphone;
	}
	public void setUnitphone(String unitphone) {
		this.unitphone = unitphone;
	}
	public Integer getUnitproperties() {
		return unitproperties;
	}
	public void setUnitproperties(Integer unitproperties) {
		this.unitproperties = unitproperties;
	}
	public String getUnitaddress() {
		return unitaddress;
	}
	public void setUnitaddress(String unitaddress) {
		this.unitaddress = unitaddress;
	}
	public Double getJobincome() {
		return jobincome;
	}
	public void setJobincome(Double jobincome) {
		this.jobincome = jobincome;
	}
	public java.util.Date getJobstarttime() {
		return jobstarttime;
	}
	public void setJobstarttime(java.util.Date jobstarttime) {
		this.jobstarttime = jobstarttime;
	}
	public Double getRegisteredcapital() {
		return registeredcapital;
	}
	public void setRegisteredcapital(Double registeredcapital) {
		this.registeredcapital = registeredcapital;
	}
	
	public Boolean getIspublicservant() {
		return ispublicservant;
	}
	public void setIspublicservant(Boolean ispublicservant) {
		this.ispublicservant = ispublicservant;
	}
	public String getGraduationunversity() {
		return graduationunversity;
	}
	public void setGraduationunversity(String graduationunversity) {
		this.graduationunversity = graduationunversity;
	}
	public Double getHousearea() {
		return housearea;
	}
	public void setHousearea(Double housearea) {
		this.housearea = housearea;
	}
	public String getFamilypostcode() {
		return familypostcode;
	}
	public void setFamilypostcode(String familypostcode) {
		this.familypostcode = familypostcode;
	}
	public String getFamilyaddress() {
		return familyaddress;
	}
	public void setFamilyaddress(String familyaddress) {
		this.familyaddress = familyaddress;
	}
	public Boolean getIsheadoffamily() {
		return isheadoffamily;
	}
	public void setIsheadoffamily(Boolean isheadoffamily) {
		this.isheadoffamily = isheadoffamily;
	}
	public String getEnglishname() {
		return englishname;
	}
	public void setEnglishname(String englishname) {
		this.englishname = englishname;
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
	public java.util.Date getChangedate() {
		return changedate;
	}
	public void setChangedate(java.util.Date changedate) {
		this.changedate = changedate;
	}
	public String getChanger() {
		return changer;
	}
	public void setChanger(String changer) {
		this.changer = changer;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getMateaccount() {
		return mateaccount;
	}
	public void setMateaccount(String mateaccount) {
		this.mateaccount = mateaccount;
	}
	public String getMateperson() {
		return mateperson;
	}
	public void setMateperson(String mateperson) {
		this.mateperson = mateperson;
	}
	public String getMatebank() {
		return matebank;
	}
	public void setMatebank(String matebank) {
		this.matebank = matebank;
	}
	public String getWageaccount() {
		return wageaccount;
	}
	public void setWageaccount(String wageaccount) {
		this.wageaccount = wageaccount;
	}
	public String getWageperson() {
		return wageperson;
	}
	public void setWageperson(String wageperson) {
		this.wageperson = wageperson;
	}
	public String getWagebank() {
		return wagebank;
	}
	public void setWagebank(String wagebank) {
		this.wagebank = wagebank;
	}
	public String getCreditaccount() {
		return creditaccount;
	}
	public void setCreditaccount(String creditaccount) {
		this.creditaccount = creditaccount;
	}
	public String getCreditperson() {
		return creditperson;
	}
	public void setCreditperson(String creditperson) {
		this.creditperson = creditperson;
	}
	public String getCreditbank() {
		return creditbank;
	}
	public void setCreditbank(String creditbank) {
		this.creditbank = creditbank;
	}
	public Double getYeargrossexpend() {
		return yeargrossexpend;
	}
	public void setYeargrossexpend(Double yeargrossexpend) {
		this.yeargrossexpend = yeargrossexpend;
	}
	public Double getGrossdebt() {
		return grossdebt;
	}
	public void setGrossdebt(Double grossdebt) {
		this.grossdebt = grossdebt;
	}
	public Double getHomeasset() {
		return homeasset;
	}
	public void setHomeasset(Double homeasset) {
		this.homeasset = homeasset;
	}
	public Double getGrossasset() {
		return grossasset;
	}
	public void setGrossasset(Double grossasset) {
		this.grossasset = grossasset;
	}
	public Integer getTechpersonnel() {
		return techpersonnel;
	}
	public void setTechpersonnel(Integer techpersonnel) {
		this.techpersonnel = techpersonnel;
	}
	
	public Integer getDegreewei() {
		return degreewei;
	}
	public void setDegreewei(Integer degreewei) {
		this.degreewei = degreewei;
	}
	public Integer getHomeshape() {
		return homeshape;
	}
	public void setHomeshape(Integer homeshape) {
		this.homeshape = homeshape;
	}
	public Integer getEmployway() {
		return employway;
	}
	public void setEmployway(Integer employway) {
		this.employway = employway;
	}
	public String getCurrentcompany() {
		return currentcompany;
	}
	public void setCurrentcompany(String currentcompany) {
		this.currentcompany = currentcompany;
	}
	public String getHomeland() {
		return homeland;
	}
	public void setHomeland(String homeland) {
		this.homeland = homeland;
	}
	public String getHukou() {
		return hukou;
	}
	public void setHukou(String hukou) {
		this.hukou = hukou;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUnitpropertiesvalue() {
		return unitpropertiesvalue;
	}
	public void setUnitpropertiesvalue(String unitpropertiesvalue) {
		this.unitpropertiesvalue = unitpropertiesvalue;
	}
	public Double getHousehold() {
		return household;
	}
	public void setHousehold(Double household) {
		this.household = household;
	}
	public Double getHomeotherincome() {
		return homeotherincome;
	}
	public void setHomeotherincome(Double homeotherincome) {
		this.homeotherincome = homeotherincome;
	}
	public Double getBefMonthBalance() {
		return befMonthBalance;
	}
	public void setBefMonthBalance(Double befMonthBalance) {
		this.befMonthBalance = befMonthBalance;
	}
	public String getRelationshipvalue() {
		return relationshipvalue;
	}
	public void setRelationshipvalue(String relationshipvalue) {
		this.relationshipvalue = relationshipvalue;
	}
	public String getRelationship2value() {
		return relationship2value;
	}
	public void setRelationship2value(String relationship2value) {
		this.relationship2value = relationship2value;
	}
	public String getRelationship3value() {
		return relationship3value;
	}
	public void setRelationship3value(String relationship3value) {
		this.relationship3value = relationship3value;
	}
	
	public Integer getMateid() {
		return mateid;
	}
	public void setMateid(Integer mateid) {
		this.mateid = mateid;
	}
	
	public Double getBef2MonthBalance() {
		return bef2MonthBalance;
	}
	public void setBef2MonthBalance(Double bef2MonthBalance) {
		this.bef2MonthBalance = bef2MonthBalance;
	}
	public Double getBef3MonthBalance() {
		return bef3MonthBalance;
	}
	public void setBef3MonthBalance(Double bef3MonthBalance) {
		this.bef3MonthBalance = bef3MonthBalance;
	}
	public Double getBef4MonthBalance() {
		return bef4MonthBalance;
	}
	public void setBef4MonthBalance(Double bef4MonthBalance) {
		this.bef4MonthBalance = bef4MonthBalance;
	}
	public Double getBef5MonthBalance() {
		return bef5MonthBalance;
	}
	public void setBef5MonthBalance(Double bef5MonthBalance) {
		this.bef5MonthBalance = bef5MonthBalance;
	}
	public Double getBef6MonthBalance() {
		return bef6MonthBalance;
	}
	public void setBef6MonthBalance(Double bef6MonthBalance) {
		this.bef6MonthBalance = bef6MonthBalance;
	}
	public String getBeforeName() {
		return beforeName;
	}
	public void setBeforeName(String beforeName) {
		this.beforeName = beforeName;
	}
	public Boolean getSteadyWage() {
		return steadyWage;
	}
	public void setSteadyWage(Boolean steadyWage) {
		this.steadyWage = steadyWage;
	}
	public Integer getShowMonth() {
		return showMonth;
	}
	public void setShowMonth(Integer showMonth) {
		this.showMonth = showMonth;
	}
	public VPersonDic() {
		super();
	}
	public String getMateValue() {
		return mateValue;
	}
	public void setMateValue(String mateValue) {
		this.mateValue = mateValue;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public Integer getJobtotal() {
		return jobtotal;
	}
	public void setJobtotal(Integer jobtotal) {
		this.jobtotal = jobtotal;
	}
	
	public String getChildrenCount() {
		return childrenCount;
	}

	public void setChildrenCount(String childrenCount) {
		this.childrenCount = childrenCount;
	}
	
	

	public VPersonDic(String name, Integer sex, Integer cardtype,
			String sexvalue, Integer familysheng, Integer familyshi,
			Integer familyxian, String shengvalue, String shivalue,
			String xianvalue, String peopletypevalue, Integer nationality,
			java.util.Date birthday, Integer peopletype, Integer marry,
			Integer dgree, String postaddress, String postcode, String fax,
			String cellphone, String telphone, String cardnumber,
			String nationalityvalue, String marryvalue, String cardtypevalue,
			String dgreevalue, String degreeweivalue, Integer degreewei,
			Double homecreditexpend, Double homeexpend, String jobvalue,
			String sonnelvalue, String employwayvalue, Integer homeshape,
			Integer employway, Double homeincome, String homeshapevalue,
			String unitProvalue, String selfemail, String doorplatenum,
			String communityname, String roadnum, String roadname,
			Integer relationship, String relationcellphone,
			String relationphone, String relationname, String unitpostcode,
			String unitphone, Integer unitproperties, String unitaddress,
			Double jobincome, java.util.Date jobstarttime, Double registeredcapital,
			Boolean ispublicservant, String graduationunversity,
			Double housearea, String familypostcode, String familyaddress,
			Boolean isheadoffamily, String englishname, java.util.Date createdate,
			String creater, java.util.Date changedate, String changer,
			String remarks, String mateaccount, String mateperson,
			String matebank, String wageaccount, String wageperson,
			String wagebank, String creditaccount, String creditperson,
			String creditbank, Double yeargrossexpend, Double grossdebt,
			Double homeasset, Double grossasset, Integer techpersonnel,
			Integer job, String currentcompany, String homeland, String hukou,
			String address, Integer id, String unitpropertiesvalue,
			Double household, Integer mateid, Double homeotherincome,
			Double befMonthBalance, Double bef2MonthBalance,
			Double bef3MonthBalance, Double bef4MonthBalance,
			Double bef5MonthBalance, Double bef6MonthBalance,
			String relationshipvalue, String relationship2value,
			String relationship3value, String beforeName, Boolean steadyWage,
			Integer showMonth,String mateValue,String age,Integer jobtotal,String zhusuo) {
		super();
		this.name = name;
		this.sex = sex;
		this.cardtype = cardtype;
		this.sexvalue = sexvalue;
		this.familysheng = familysheng;
		this.familyshi = familyshi;
		this.familyxian = familyxian;
		this.shengvalue = shengvalue;
		this.shivalue = shivalue;
		this.xianvalue = xianvalue;
		this.peopletypevalue = peopletypevalue;
		this.nationality = nationality;
		this.birthday = birthday;
		this.peopletype = peopletype;
		this.marry = marry;
		this.dgree = dgree;
		this.postaddress = postaddress;
		this.postcode = postcode;
		this.fax = fax;
		this.cellphone = cellphone;
		this.telphone = telphone;
		this.cardnumber = cardnumber;
		this.nationalityvalue = nationalityvalue;
		this.marryvalue = marryvalue;
		this.cardtypevalue = cardtypevalue;
		this.dgreevalue = dgreevalue;
		this.degreeweivalue = degreeweivalue;
		this.degreewei = degreewei;
		this.homecreditexpend = homecreditexpend;
		this.homeexpend = homeexpend;
		this.jobvalue = jobvalue;
		this.sonnelvalue = sonnelvalue;
		this.employwayvalue = employwayvalue;
		this.homeshape = homeshape;
		this.employway = employway;
		this.homeincome = homeincome;
		this.homeshapevalue = homeshapevalue;
		this.unitProvalue = unitProvalue;
		this.selfemail = selfemail;
		this.doorplatenum = doorplatenum;
		this.communityname = communityname;
		this.roadnum = roadnum;
		this.roadname = roadname;
		this.relationship = relationship;
		this.relationcellphone = relationcellphone;
		this.relationphone = relationphone;
		this.relationname = relationname;
		this.unitpostcode = unitpostcode;
		this.unitphone = unitphone;
		this.unitproperties = unitproperties;
		this.unitaddress = unitaddress;
		this.jobincome = jobincome;
		this.jobstarttime = jobstarttime;
		this.registeredcapital = registeredcapital;
		this.ispublicservant = ispublicservant;
		this.graduationunversity = graduationunversity;
		this.housearea = housearea;
		this.familypostcode = familypostcode;
		this.familyaddress = familyaddress;
		this.isheadoffamily = isheadoffamily;
		this.englishname = englishname;
		this.createdate = createdate;
		this.creater = creater;
		this.changedate = changedate;
		this.changer = changer;
		this.remarks = remarks;
		this.mateaccount = mateaccount;
		this.mateperson = mateperson;
		this.matebank = matebank;
		this.wageaccount = wageaccount;
		this.wageperson = wageperson;
		this.wagebank = wagebank;
		this.creditaccount = creditaccount;
		this.creditperson = creditperson;
		this.creditbank = creditbank;
		this.yeargrossexpend = yeargrossexpend;
		this.grossdebt = grossdebt;
		this.homeasset = homeasset;
		this.grossasset = grossasset;
		this.techpersonnel = techpersonnel;
		this.job = job;
		this.currentcompany = currentcompany;
		this.homeland = homeland;
		this.hukou = hukou;
		this.address = address;
		this.id = id;
		this.unitpropertiesvalue = unitpropertiesvalue;
		this.household = household;
		this.mateid = mateid;
		this.homeotherincome = homeotherincome;
		this.befMonthBalance = befMonthBalance;
		this.bef2MonthBalance = bef2MonthBalance;
		this.bef3MonthBalance = bef3MonthBalance;
		this.bef4MonthBalance = bef4MonthBalance;
		this.bef5MonthBalance = bef5MonthBalance;
		this.bef6MonthBalance = bef6MonthBalance;
		this.relationshipvalue = relationshipvalue;
		this.relationship2value = relationship2value;
		this.relationship3value = relationship3value;
		this.beforeName = beforeName;
		this.steadyWage = steadyWage;
		this.showMonth = showMonth;
		this.mateValue = mateValue;
		this.jobtotal = jobtotal ;
		this.age = age ;
		this.zhusuo = zhusuo;
		
	}
}