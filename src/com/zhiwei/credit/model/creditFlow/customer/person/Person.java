package com.zhiwei.credit.model.creditFlow.customer.person;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.zhiwei.core.model.BaseModel;


/**
 * CsPerson entity. @author MyEclipse Persistence Tools
 */

public class Person extends BaseModel  implements  java.io.Serializable {

	// Fields

	public String getCustomerSource() {
		return customerSource;
	}

	public void setCustomerSource(String customerSource) {
		this.customerSource = customerSource;
	}

	private Integer id;
	private Integer mateid;    //配偶id
	private String name;      //姓名       				1
	private Integer sex;      //性别                                            2   int
	private Integer cardtype;  //证件类型
	private String cardnumber;  //证件号码
	private String telphone;   //家庭电话
	private String cellphone; //手机号码				4
	private String fax;     //传值
	private String postcode;   //邮政编码
	private String postaddress;    //通信地址
	private Integer dgree;  //学历
	private Integer marry;       //婚姻
	private Integer peopletype;   //公民类型
	private java.util.Date birthday;   //出生日期
	private java.util.Date validity; //证件有效期
	private Integer nationality;   //名族
	private String address;    //固定住址                         new
	private Integer jobtotal ;  //任职年限
	private String hukou;     //户口所在地
	private String parenthukou;//户口所在省
	private Integer homeland;    //籍贯
	private String currentcompany;  //工作单位
	private Integer job;    // 职务					3    int
	private Integer techpersonnel;   //技术职称
	
	private Double grossasset;
	private Double homeasset;
	private Double grossdebt;
	private Double yeargrossexpend;
	private String creditbank;
	private String creditperson;
	private String creditaccount;
	private String wagebank;
	private String wageperson;
	private String wageaccount;
	private String matebank;
	private String mateperson;
	private String mateaccount;
	
	private String remarks;   //备注
	private String changer;    //最后一次修改人
	private java.util.Date changedate;   //最后修改时间
	private String creater;    //创建人
	private java.util.Date createdate;     //创建时间
	private String englishname;    //英文姓名
	private Boolean isheadoffamily;  //是否户主
	private String familyaddress;    //家庭住址       省/市/县
	private String familypostcode;   //邮编
	private Double housearea;   //现住宅面积
	private Integer homeshape;   //现住宅形式
	private Integer employway;   //占用方式
	private Double homeincome;   //家庭税后月收入
	private Double homeexpend;   //家庭月非贷款支出
	private Double homecreditexpend; //家庭月贷款支出
	private Double household;   // 供养人口


	private Integer degreewei;  //学位
	private String graduationunversity;   //毕业院校
	private Boolean ispublicservant; //是否公务员
	private Double registeredcapital; //工作单位注册资本
	private java.util.Date jobstarttime;   //本工作开始日期
	private String webstarttime;   //网站经营年限
	private String bossstarttime;  //企业开设日期
	private String techstarttime;  //执教起始日期
	private Double jobincome;  //本工作税后月收入
	private String unitaddress; //单位地址
	private Integer unitproperties; // 单位性质
	private String unitphone;     //单位电话
	private String unitpostcode; //邮政编码
	//private Integer assuretype;   //担保物类型
	private String pinyinname;    //拼音名
	//private Double monthIncome;    //月收入
	private String roadname; //路名
	private String roadnum; //路号
	private String communityname;    //社区名
	private String doorplatenum;     //门牌号
	
	private String selfemail;    //电子邮箱
	private Integer familysheng;   //省
	private Integer familyshi;  //市
	private Integer familyxian; //县/区
	
	
	private String relationname;   //关系人姓名
	private String relationphone; //固定电话
	private String relationcellphone; //手机
	private Integer relationship;  //关系

	private Double homeotherincome;
	private Double befMonthBalance;
	private Double bef2MonthBalance;
	private Double bef3MonthBalance;
	private Double bef4MonthBalance;
	private Double bef5MonthBalance;
	private Double bef6MonthBalance;
	
	private String beforeName;//曾用名
	private Boolean steadyWage;//收入是否稳定  
	
	private Integer showMonth;//月份数(银行余额)
	
	private String zhusuo;//住所地址
	
	private String belongedId;
	private Long createrId;
	private String customerLevel;//客户级别
	private Boolean isBlack;//是否是黑名单
	private String blackReason;//列入黑名单的原因
	private String personCount;//家庭人数
	private String childrenCount;//子女数
	private Short politicalStatus;//政治面貌
	private String livingLife;//本地居住年限
	private String recordAndRemarks;//信用记录及说明
	private BigDecimal disposableCapital;//家庭月可支配金额
	
	private Double averageMonthWage; //月平均工资
	private Double debts;	//负债
	private String  primaryBusiness; //主营业务
	private Double monthRent; //月租
	
	private java.util.Date drivelicenseValided;//行驶证有效期
	private java.util.Date residenceValided;//居住证有效期
	
	private String qq;//QQ
	private String microMessage; //微信
	private java.util.Date residenceDate; //居住时间
	
	private Integer companyDepartment;// 所属部门
	private String companyFax;//公司传真
	private String department;// 所属部门
	
	private Integer age;//年龄
	private Integer payDate;//发薪日期
	
	private String hangyeName;//行业类别
	private Integer hangyeType;
	private String companyScale;//公司规模
	
	private Double jobYearincom;//
	private String collegeYear;//最高学历毕业年份
	private String hireEmail;//工作邮箱
	private Integer hireCity;//工作城市
	private Boolean havehouse;//是否有房产（0无，1有）
	private Boolean havehouseloan;//是否有房贷(0无，1有)
	private Boolean havecar;//是否有车产（0无，1有）
	private Boolean havecarloan;//是否有车贷（0无，1有）
	private Integer careerType;//职业类型：（0网商，1工薪，2私营企业主）
	private Integer liveCity;//居住城市-市
	
	//教师信息
	protected String teacherCompanyName;
	protected String teacherPosition;
	protected java.math.BigDecimal teacherMonthlyIncome;
	protected String teacherEmail;
	protected String PerentteacherCity;
	protected String teacherCity;
	protected String teacherAddress;
	protected String teacherStartYear;
	protected String teacherCompanyPhone;
	protected String archivesBelonging;//档案归属地
	protected Long shopId;//门店Id
	protected String shopName;//门店名称
	//网店信息字段
	protected String webshopName;        //网点名称                
	protected java.math.BigDecimal webshopMonthlyIncome;  //网店月经营收入
	protected String webshopEmail;      //网店联系邮箱
	protected String webshopProvince;   //网店所在省份
	protected String webshopCity;       //网店所在城市
	protected String webshopAddress;    //网店经营地址
	protected String webshopStartYear;  //网店经营年限
	protected String webshopPhone;      //网店联系电话
	/**
	 * 客户来源  
	 * credit : 信贷客户
	 */
	protected String customerSource;
	
	



	//不与数据库映射
	private String sexvalue;
	private String jobvalue;
	private String marryvalue;
	private String cardtypevalue;
	private String mateValue;
	private String belongedName;
	private Integer personPhotoId;
	private String personPhotoUrl;
	private String personPhotoExtendName;
	private Integer personSFZZId;
	private String personSFZZUrl;
	private String personSFZZExtendName;
	private Integer personSFZFId;
	private String personSFZFUrl;
	private String personSFZFExtendName;
	private Integer parentHomeland;//籍贯省
	private Integer parentLiveCity;//居住城市省
	private Integer parentHireCity;//工作城市-省
	private String companyName;//所属公司
	private String hireCityName;//工作城市-市名称

	private  BigDecimal availableMoney;//法人可用额度

	public BigDecimal getAvailableMoney() {
		return availableMoney;
	}

	public void setAvailableMoney(BigDecimal availableMoney) {
		this.availableMoney = availableMoney;
	}

	// Constructors
	
	protected Set<BpCustPersonNegativeSurvey> negativeInfos = new HashSet<BpCustPersonNegativeSurvey>();
	protected Set<BpCustPersonEducation> educationInfos = new HashSet<BpCustPersonEducation>();
	protected Set<BpCustPersonPublicActivity> activityInfos = new HashSet<BpCustPersonPublicActivity>();
	protected Set<BpCustPersonWorkExperience> workExperienceInfos = new HashSet<BpCustPersonWorkExperience>();
	protected Set<CsPersonCar> personCars = new HashSet<CsPersonCar>();
	protected Set<CsPersonHouse> personHouses = new HashSet<CsPersonHouse>();
	
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
	 * 网站账户信息
	 */
	private String p2pName;
	private String p2pcardnumber;
	private String p2pcellphone;
	private String p2pemail;
	private String p2pTrueName;
	private String thirdPayFlagId;
	private String isCheckCard;//身份证是否通过验证
	
	public Long getAccountId() {
		return accountId;
	}

	
	public String getHireCityName() {
		return hireCityName;
	}

	public void setHireCityName(String hireCityName) {
		this.hireCityName = hireCityName;
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


	
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getArchivesBelonging() {
		return archivesBelonging;
	}

	public void setArchivesBelonging(String archivesBelonging) {
		this.archivesBelonging = archivesBelonging;
	}

	public String getWebshopName() {
		return webshopName;
	}

	public void setWebshopName(String webshopName) {
		this.webshopName = webshopName;
	}

	public java.math.BigDecimal getWebshopMonthlyIncome() {
		return webshopMonthlyIncome;
	}

	public void setWebshopMonthlyIncome(java.math.BigDecimal webshopMonthlyIncome) {
		this.webshopMonthlyIncome = webshopMonthlyIncome;
	}

	public String getWebshopEmail() {
		return webshopEmail;
	}

	public void setWebshopEmail(String webshopEmail) {
		this.webshopEmail = webshopEmail;
	}

	public String getWebshopProvince() {
		return webshopProvince;
	}

	public void setWebshopProvince(String webshopProvince) {
		this.webshopProvince = webshopProvince;
	}

	public String getWebshopCity() {
		return webshopCity;
	}

	public void setWebshopCity(String webshopCity) {
		this.webshopCity = webshopCity;
	}

	public String getWebshopAddress() {
		return webshopAddress;
	}

	public void setWebshopAddress(String webshopAddress) {
		this.webshopAddress = webshopAddress;
	}

	public String getWebshopStartYear() {
		return webshopStartYear;
	}

	public void setWebshopStartYear(String webshopStartYear) {
		this.webshopStartYear = webshopStartYear;
	}

	public String getWebshopPhone() {
		return webshopPhone;
	}

	public void setWebshopPhone(String webshopPhone) {
		this.webshopPhone = webshopPhone;
	}
	
	public Integer getParentHireCity() {
		return parentHireCity;
	}

	public void setParentHireCity(Integer parentHireCity) {
		this.parentHireCity = parentHireCity;
	}

	public Integer getParentLiveCity() {
		return parentLiveCity;
	}

	public void setParentLiveCity(Integer parentLiveCity) {
		this.parentLiveCity = parentLiveCity;
	}

	public Integer getParentHomeland() {
		return parentHomeland;
	}

	public void setParentHomeland(Integer parentHomeland) {
		this.parentHomeland = parentHomeland;
	}

	public String getMateValue() {
		return mateValue;
	}

	public void setMateValue(String mateValue) {
		this.mateValue = mateValue;
	}

	public String getBelongedName() {
		return belongedName;
	}

	public void setBelongedName(String belongedName) {
		this.belongedName = belongedName;
	}

	public Integer getPersonPhotoId() {
		return personPhotoId;
	}

	public void setPersonPhotoId(Integer personPhotoId) {
		this.personPhotoId = personPhotoId;
	}

	public String getPersonPhotoUrl() {
		return personPhotoUrl;
	}

	public void setPersonPhotoUrl(String personPhotoUrl) {
		this.personPhotoUrl = personPhotoUrl;
	}

	public String getPersonPhotoExtendName() {
		return personPhotoExtendName;
	}

	public void setPersonPhotoExtendName(String personPhotoExtendName) {
		this.personPhotoExtendName = personPhotoExtendName;
	}

	public Integer getPersonSFZZId() {
		return personSFZZId;
	}

	public void setPersonSFZZId(Integer personSFZZId) {
		this.personSFZZId = personSFZZId;
	}

	public String getPersonSFZZUrl() {
		return personSFZZUrl;
	}

	public void setPersonSFZZUrl(String personSFZZUrl) {
		this.personSFZZUrl = personSFZZUrl;
	}

	public String getPersonSFZZExtendName() {
		return personSFZZExtendName;
	}

	public void setPersonSFZZExtendName(String personSFZZExtendName) {
		this.personSFZZExtendName = personSFZZExtendName;
	}

	public Integer getPersonSFZFId() {
		return personSFZFId;
	}

	public void setPersonSFZFId(Integer personSFZFId) {
		this.personSFZFId = personSFZFId;
	}

	public String getPersonSFZFUrl() {
		return personSFZFUrl;
	}

	public void setPersonSFZFUrl(String personSFZFUrl) {
		this.personSFZFUrl = personSFZFUrl;
	}

	public String getPersonSFZFExtendName() {
		return personSFZFExtendName;
	}

	public void setPersonSFZFExtendName(String personSFZFExtendName) {
		this.personSFZFExtendName = personSFZFExtendName;
	}

	public String getCollegeYear() {
		return collegeYear;
	}

	public void setCollegeYear(String collegeYear) {
		this.collegeYear = collegeYear;
	}

	public String getHireEmail() {
		return hireEmail;
	}

	public void setHireEmail(String hireEmail) {
		this.hireEmail = hireEmail;
	}

	public Integer getHireCity() {
		return hireCity;
	}

	public void setHireCity(Integer hireCity) {
		this.hireCity = hireCity;
	}

	public Boolean getHavehouse() {
		return havehouse;
	}

	public void setHavehouse(Boolean havehouse) {
		this.havehouse = havehouse;
	}

	public Boolean getHavehouseloan() {
		return havehouseloan;
	}

	public void setHavehouseloan(Boolean havehouseloan) {
		this.havehouseloan = havehouseloan;
	}

	public String getWebstarttime() {
		return webstarttime;
	}

	public void setWebstarttime(String webstarttime) {
		this.webstarttime = webstarttime;
	}

	public String getParenthukou() {
		return parenthukou;
	}

	public void setParenthukou(String parenthukou) {
		this.parenthukou = parenthukou;
	}

	public String getBossstarttime() {
		return bossstarttime;
	}

	public void setBossstarttime(String bossstarttime) {
		this.bossstarttime = bossstarttime;
	}

	public String getTechstarttime() {
		return techstarttime;
	}

	public void setTechstarttime(String techstarttime) {
		this.techstarttime = techstarttime;
	}

	public Boolean getHavecar() {
		return havecar;
	}

	public void setHavecar(Boolean havecar) {
		this.havecar = havecar;
	}

	public Boolean getHavecarloan() {
		return havecarloan;
	}

	public void setHavecarloan(Boolean havecarloan) {
		this.havecarloan = havecarloan;
	}

	public Integer getCareerType() {
		return careerType;
	}

	public void setCareerType(Integer careerType) {
		this.careerType = careerType;
	}

	public Integer getLiveCity() {
		return liveCity;
	}

	public void setLiveCity(Integer liveCity) {
		this.liveCity = liveCity;
	}

	public java.util.Date getValidity() {
		return validity;
	}

	public void setValidity(java.util.Date validity) {
		this.validity = validity;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getTeacherCompanyName() {
		return teacherCompanyName;
	}

	public void setTeacherCompanyName(String teacherCompanyName) {
		this.teacherCompanyName = teacherCompanyName;
	}

	public String getTeacherPosition() {
		return teacherPosition;
	}

	public void setTeacherPosition(String teacherPosition) {
		this.teacherPosition = teacherPosition;
	}

	public java.math.BigDecimal getTeacherMonthlyIncome() {
		return teacherMonthlyIncome;
	}

	public void setTeacherMonthlyIncome(java.math.BigDecimal teacherMonthlyIncome) {
		this.teacherMonthlyIncome = teacherMonthlyIncome;
	}

	public String getPerentteacherCity() {
		return PerentteacherCity;
	}

	public void setPerentteacherCity(String perentteacherCity) {
		PerentteacherCity = perentteacherCity;
	}

	public String getTeacherEmail() {
		return teacherEmail;
	}

	public void setTeacherEmail(String teacherEmail) {
		this.teacherEmail = teacherEmail;
	}

	public String getTeacherCity() {
		return teacherCity;
	}

	public void setTeacherCity(String teacherCity) {
		this.teacherCity = teacherCity;
	}

	public String getTeacherAddress() {
		return teacherAddress;
	}

	public void setTeacherAddress(String teacherAddress) {
		this.teacherAddress = teacherAddress;
	}

	public String getTeacherStartYear() {
		return teacherStartYear;
	}

	public void setTeacherStartYear(String teacherStartYear) {
		this.teacherStartYear = teacherStartYear;
	}

	public String getTeacherCompanyPhone() {
		return teacherCompanyPhone;
	}

	public void setTeacherCompanyPhone(String teacherCompanyPhone) {
		this.teacherCompanyPhone = teacherCompanyPhone;
	}

	/** default constructor */
	public Person() {
	}
	
	public Person(Integer id, Integer mateid, String name, Integer sex,
			Integer cardtype, String cardnumber, String telphone,
			String cellphone, String fax, String postcode, String postaddress,
			Integer dgree, Integer marry, Integer peopletype,
			java.util.Date birthday,Date validity, Integer nationality, String address,
			Integer jobtotal, String hukou,String parenthukou, Integer homeland,
			String currentcompany, Integer job, Integer techpersonnel,
			Double grossasset, Double homeasset, Double grossdebt,
			Double yeargrossexpend, String creditbank, String creditperson,
			String creditaccount, String wagebank, String wageperson,
			String wageaccount, String matebank, String mateperson,
			String mateaccount, String remarks, String changer,
			java.util.Date changedate, String creater, java.util.Date createdate,
			String englishname, Boolean isheadoffamily, String familyaddress,
			String familypostcode, Double housearea, Integer homeshape,
			Integer employway, Double homeincome, Double homeexpend,
			Double homecreditexpend, Double household, Integer degreewei,
			String graduationunversity, Boolean ispublicservant,
			Double registeredcapital, java.util.Date jobstarttime, Double jobincome,
			String unitaddress, Integer unitproperties, String unitphone,
			String unitpostcode, String pinyinname, String roadname,
			String roadnum, String communityname, String doorplatenum,
			String selfemail, Integer familysheng, Integer familyshi,
			Integer familyxian, String relationname, String relationphone,
			String relationcellphone, Integer relationship,
			Double homeotherincome, Double befMonthBalance,
			Double bef2MonthBalance, Double bef3MonthBalance,
			Double bef4MonthBalance, Double bef5MonthBalance,
			Double bef6MonthBalance, String beforeName, Boolean steadyWage,
			Integer showMonth,String zhusuo,String belongedId,
	        Long createrId,String customerLevel,Boolean isBlack,String blackReason,
	        String personCount,Short politicalStatus,String livingLife,String recordAndRemarks,BigDecimal disposableCapital
	        ) {
		super();
		this.id = id;
		this.mateid = mateid;
		this.name = name;
		this.sex = sex;
		this.cardtype = cardtype;
		this.cardnumber = cardnumber;
		this.telphone = telphone;
		this.cellphone = cellphone;
		this.fax = fax;
		this.postcode = postcode;
		this.postaddress = postaddress;
		this.dgree = dgree;
		this.marry = marry;
		this.peopletype = peopletype;
		this.birthday = birthday;
		this.validity=validity;
		this.nationality = nationality;
		this.address = address;
		this.jobtotal = jobtotal;
		this.hukou = hukou;
		this.homeland = homeland;
		this.currentcompany = currentcompany;
		this.job = job;
		this.techpersonnel = techpersonnel;
		this.grossasset = grossasset;
		this.homeasset = homeasset;
		this.grossdebt = grossdebt;
		this.yeargrossexpend = yeargrossexpend;
		this.creditbank = creditbank;
		this.creditperson = creditperson;
		this.creditaccount = creditaccount;
		this.wagebank = wagebank;
		this.wageperson = wageperson;
		this.wageaccount = wageaccount;
		this.matebank = matebank;
		this.mateperson = mateperson;
		this.mateaccount = mateaccount;
		this.remarks = remarks;
		this.changer = changer;
		this.parenthukou=parenthukou;
		this.changedate = changedate;
		this.creater = creater;
		this.createdate = createdate;
		this.englishname = englishname;
		this.isheadoffamily = isheadoffamily;
		this.familyaddress = familyaddress;
		this.familypostcode = familypostcode;
		this.housearea = housearea;
		this.homeshape = homeshape;
		this.employway = employway;
		this.homeincome = homeincome;
		this.homeexpend = homeexpend;
		this.homecreditexpend = homecreditexpend;
		this.household = household;
		this.degreewei = degreewei;
		this.graduationunversity = graduationunversity;
		this.ispublicservant = ispublicservant;
		this.registeredcapital = registeredcapital;
		this.jobstarttime = jobstarttime;
		this.jobincome = jobincome;
		this.unitaddress = unitaddress;
		this.unitproperties = unitproperties;
		this.unitphone = unitphone;
		this.unitpostcode = unitpostcode;
		this.pinyinname = pinyinname;
		this.roadname = roadname;
		this.roadnum = roadnum;
		this.communityname = communityname;
		this.doorplatenum = doorplatenum;
		this.selfemail = selfemail;
		this.familysheng = familysheng;
		this.familyshi = familyshi;
		this.familyxian = familyxian;
		this.relationname = relationname;
		this.relationphone = relationphone;
		this.relationcellphone = relationcellphone;
		this.relationship = relationship;
		this.homeotherincome = homeotherincome;
		this.befMonthBalance = befMonthBalance;
		this.bef2MonthBalance = bef2MonthBalance;
		this.bef3MonthBalance = bef3MonthBalance;
		this.bef4MonthBalance = bef4MonthBalance;
		this.bef5MonthBalance = bef5MonthBalance;
		this.bef6MonthBalance = bef6MonthBalance;
		this.beforeName = beforeName;
		this.steadyWage = steadyWage;
		this.showMonth = showMonth;
		this.zhusuo = zhusuo;
		this.belongedId = belongedId;
		this.createrId = createrId;
		this.customerLevel=customerLevel;
		this.isBlack=isBlack;
		this.blackReason=blackReason;
		this.personCount=personCount;
		this.politicalStatus=politicalStatus;
		this.livingLife=livingLife;
		this.recordAndRemarks=recordAndRemarks;
		this.disposableCapital=disposableCapital;
	}

	public Person(Integer aValue){
		this.setId(aValue);
	}
	
	public String getHangyeName() {
		return hangyeName;
	}

	public void setHangyeName(String hangyeName) {
		this.hangyeName = hangyeName;
	}

	public Integer getHangyeType() {
		return hangyeType;
	}

	public void setHangyeType(Integer hangyeType) {
		this.hangyeType = hangyeType;
	}

	public String getCompanyScale() {
		return companyScale;
	}

	public void setCompanyScale(String companyScale) {
		this.companyScale = companyScale;
	}

	/** full constructor */
	
	// Property accessors
	public Integer getId() {
		return this.id;
	}

	public String getSexvalue() {
		return sexvalue;
	}

	public void setSexvalue(String sexvalue) {
		this.sexvalue = sexvalue;
	}

	public String getJobvalue() {
		return jobvalue;
	}

	public void setJobvalue(String jobvalue) {
		this.jobvalue = jobvalue;
	}

	public String getMarryvalue() {
		return marryvalue;
	}

	public void setMarryvalue(String marryvalue) {
		this.marryvalue = marryvalue;
	}

	/*public String getCardtypevalue() {
		return Cardtypevalue;
	}

	public void setCardtypevalue(String cardtypevalue) {
		Cardtypevalue = cardtypevalue;
	}*/

	public String getSelfemail() {
		return selfemail;
	}
	public String getCardtypevalue() {
		return cardtypevalue;
	}

	public void setCardtypevalue(String cardtypevalue) {
		this.cardtypevalue = cardtypevalue;
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

	public void setSelfemail(String selfemail) {
		this.selfemail = selfemail;
	}
	public Double getHousehold() {
		return household;
	}

	public void setHousehold(Double household) {
		this.household = household;
	}
	public String getRoadname() {
		return roadname;
	}

	public void setRoadname(String roadname) {
		this.roadname = roadname;
	}

	public String getRoadnum() {
		return roadnum;
	}

	public void setRoadnum(String roadnum) {
		this.roadnum = roadnum;
	}

	public String getCommunityname() {
		return communityname;
	}

	public void setCommunityname(String communityname) {
		this.communityname = communityname;
	}

	public String getDoorplatenum() {
		return doorplatenum;
	}

	public void setDoorplatenum(String doorplatenum) {
		this.doorplatenum = doorplatenum;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getMateid() {
		return mateid;
	}

	public void setMateid(Integer mateid) {
		this.mateid = mateid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCardtype() {
		return this.cardtype;
	}

	public void setCardtype(Integer cardtype) {
		this.cardtype = cardtype;
	}

	public String getCardnumber() {
		return this.cardnumber;
	}

	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}

	public String getTelphone() {
		return this.telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getCellphone() {
		return this.cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getPostcode() {
		return this.postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getPostaddress() {
		return this.postaddress;
	}

	public void setPostaddress(String postaddress) {
		this.postaddress = postaddress;
	}

	public Integer getDgree() {
		return this.dgree;
	}

	public void setDgree(Integer dgree) {
		this.dgree = dgree;
	}

	public Integer getMarry() {
		return this.marry;
	}

	public void setMarry(Integer marry) {
		this.marry = marry;
	}

	public Integer getPeopletype() {
		return this.peopletype;
	}

	public void setPeopletype(Integer peopletype) {
		this.peopletype = peopletype;
	}

	public java.util.Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(java.util.Date birthday) {
		this.birthday = birthday;
	}

	public Integer getNationality() {
		return this.nationality;
	}

	public void setNationality(Integer nationality) {
		this.nationality = nationality;
	}

	public String getHukou() {
		return this.hukou;
	}

	public void setHukou(String hukou) {
		this.hukou = hukou;
	}

	public Integer getHomeland() {
		return this.homeland;
	}

	public void setHomeland(Integer homeland) {
		this.homeland = homeland;
	}

	public String getCurrentcompany() {
		return this.currentcompany;
	}

	public void setCurrentcompany(String currentcompany) {
		this.currentcompany = currentcompany;
	}

	public Integer getTechpersonnel() {
		return this.techpersonnel;
	}

	public void setTechpersonnel(Integer techpersonnel) {
		this.techpersonnel = techpersonnel;
	}

	public Double getGrossasset() {
		return this.grossasset;
	}

	public void setGrossasset(Double grossasset) {
		this.grossasset = grossasset;
	}

	public Double getHomeasset() {
		return this.homeasset;
	}

	public void setHomeasset(Double homeasset) {
		this.homeasset = homeasset;
	}

	public Double getGrossdebt() {
		return this.grossdebt;
	}

	public void setGrossdebt(Double grossdebt) {
		this.grossdebt = grossdebt;
	}

	public Double getYeargrossexpend() {
		return this.yeargrossexpend;
	}

	public void setYeargrossexpend(Double yeargrossexpend) {
		this.yeargrossexpend = yeargrossexpend;
	}

	public String getCreditbank() {
		return this.creditbank;
	}

	public void setCreditbank(String creditbank) {
		this.creditbank = creditbank;
	}

	public String getCreditperson() {
		return this.creditperson;
	}

	public void setCreditperson(String creditperson) {
		this.creditperson = creditperson;
	}

	public String getCreditaccount() {
		return this.creditaccount;
	}

	public void setCreditaccount(String creditaccount) {
		this.creditaccount = creditaccount;
	}

	public String getWagebank() {
		return this.wagebank;
	}

	public void setWagebank(String wagebank) {
		this.wagebank = wagebank;
	}

	public String getWageperson() {
		return this.wageperson;
	}

	public void setWageperson(String wageperson) {
		this.wageperson = wageperson;
	}

	public String getWageaccount() {
		return this.wageaccount;
	}

	public void setWageaccount(String wageaccount) {
		this.wageaccount = wageaccount;
	}

	public String getMatebank() {
		return this.matebank;
	}

	public void setMatebank(String matebank) {
		this.matebank = matebank;
	}

	public String getMateperson() {
		return this.mateperson;
	}

	public void setMateperson(String mateperson) {
		this.mateperson = mateperson;
	}

	public String getMateaccount() {
		return this.mateaccount;
	}

	public void setMateaccount(String mateaccount) {
		this.mateaccount = mateaccount;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getChanger() {
		return this.changer;
	}

	public void setChanger(String changer) {
		this.changer = changer;
	}

	public java.util.Date getChangedate() {
		return this.changedate;
	}

	public void setChangedate(java.util.Date changedate) {
		this.changedate = changedate;
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

	public String getEnglishname() {
		return this.englishname;
	}

	public void setEnglishname(String englishname) {
		this.englishname = englishname;
	}

	public Boolean getIsheadoffamily() {
		return isheadoffamily;
	}

	public void setIsheadoffamily(Boolean isheadoffamily) {
		this.isheadoffamily = isheadoffamily;
	}

	public String getFamilyaddress() {
		return this.familyaddress;
	}

	public void setFamilyaddress(String familyaddress) {
		this.familyaddress = familyaddress;
	}

	public String getFamilypostcode() {
		return this.familypostcode;
	}

	public void setFamilypostcode(String familypostcode) {
		this.familypostcode = familypostcode;
	}

	public Double getHousearea() {
		return this.housearea;
	}

	public void setHousearea(Double housearea) {
		this.housearea = housearea;
	}


	public Double getHomeincome() {
		return this.homeincome;
	}

	public void setHomeincome(Double homeincome) {
		this.homeincome = homeincome;
	}

	public Double getHomeexpend() {
		return this.homeexpend;
	}

	public void setHomeexpend(Double homeexpend) {
		this.homeexpend = homeexpend;
	}

	public Double getHomecreditexpend() {
		return this.homecreditexpend;
	}

	public void setHomecreditexpend(Double homecreditexpend) {
		this.homecreditexpend = homecreditexpend;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getJob() {
		return job;
	}

	public void setJob(Integer job) {
		this.job = job;
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

	public Integer getDegreewei() {
		return degreewei;
	}

	public void setDegreewei(Integer degreewei) {
		this.degreewei = degreewei;
	}

	public String getGraduationunversity() {
		return this.graduationunversity;
	}

	public void setGraduationunversity(String graduationunversity) {
		this.graduationunversity = graduationunversity;
	}

	

	public Boolean getIspublicservant() {
		return ispublicservant;
	}

	public void setIspublicservant(Boolean ispublicservant) {
		this.ispublicservant = ispublicservant;
	}

	public Double getRegisteredcapital() {
		return this.registeredcapital;
	}

	public void setRegisteredcapital(Double registeredcapital) {
		this.registeredcapital = registeredcapital;
	}

	public java.util.Date getJobstarttime() {
		return this.jobstarttime;
	}

	public void setJobstarttime(java.util.Date jobstarttime) {
		this.jobstarttime = jobstarttime;
	}

	public Double getJobincome() {
		return this.jobincome;
	}

	public void setJobincome(Double jobincome) {
		this.jobincome = jobincome;
	}

	public String getUnitaddress() {
		return this.unitaddress;
	}

	public void setUnitaddress(String unitaddress) {
		this.unitaddress = unitaddress;
	}

	public Integer getUnitproperties() {
		return this.unitproperties;
	}

	public void setUnitproperties(Integer unitproperties) {
		this.unitproperties = unitproperties;
	}

	public String getUnitphone() {
		return this.unitphone;
	}

	public String getUnitpostcode() {
		return unitpostcode;
	}

	public void setUnitpostcode(String unitpostcode) {
		this.unitpostcode = unitpostcode;
	}

	public String getRelationname() {
		return relationname;
	}

	public void setRelationname(String relationname) {
		this.relationname = relationname;
	}

	public String getRelationphone() {
		return relationphone;
	}

	public void setRelationphone(String relationphone) {
		this.relationphone = relationphone;
	}

	public String getRelationcellphone() {
		return relationcellphone;
	}

	public void setRelationcellphone(String relationcellphone) {
		this.relationcellphone = relationcellphone;
	}

	public Integer getRelationship() {
		return relationship;
	}

	public void setRelationship(Integer relationship) {
		this.relationship = relationship;
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

	public void setUnitphone(String unitphone) {
		this.unitphone = unitphone;
	}
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getJobtotal() {
		return jobtotal;
	}

	public void setJobtotal(Integer jobtotal) {
		this.jobtotal = jobtotal;
	}

	public String getPinyinname() {
		return pinyinname;
	}

	public void setPinyinname(String pinyinname) {
		this.pinyinname = pinyinname;
	}

	public String getZhusuo() {
		return zhusuo;
	}

	public void setZhusuo(String zhusuo) {
		this.zhusuo = zhusuo;
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

	public String getCustomerLevel() {
		return customerLevel;
	}

	public void setCustomerLevel(String customerLevel) {
		this.customerLevel = customerLevel;
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

	public Set<BpCustPersonNegativeSurvey> getNegativeInfos() {
		return negativeInfos;
	}

	public void setNegativeInfos(Set<BpCustPersonNegativeSurvey> negativeInfos) {
		this.negativeInfos = negativeInfos;
	}

	public Set<BpCustPersonEducation> getEducationInfos() {
		return educationInfos;
	}

	public void setEducationInfos(Set<BpCustPersonEducation> educationInfos) {
		this.educationInfos = educationInfos;
	}

	public Set<BpCustPersonPublicActivity> getActivityInfos() {
		return activityInfos;
	}

	public void setActivityInfos(Set<BpCustPersonPublicActivity> activityInfos) {
		this.activityInfos = activityInfos;
	}

	public Set<BpCustPersonWorkExperience> getWorkExperienceInfos() {
		return workExperienceInfos;
	}

	public void setWorkExperienceInfos(
			Set<BpCustPersonWorkExperience> workExperienceInfos) {
		this.workExperienceInfos = workExperienceInfos;
	}

	public Set<CsPersonCar> getPersonCars() {
		return personCars;
	}

	public void setPersonCars(Set<CsPersonCar> personCars) {
		this.personCars = personCars;
	}

	public Set<CsPersonHouse> getPersonHouses() {
		return personHouses;
	}

	public void setPersonHouses(Set<CsPersonHouse> personHouses) {
		this.personHouses = personHouses;
	}

	public Double getAverageMonthWage() {
		return averageMonthWage;
	}

	public void setAverageMonthWage(Double averageMonthWage) {
		this.averageMonthWage = averageMonthWage;
	}

	public Double getDebts() {
		return debts;
	}

	public void setDebts(Double debts) {
		this.debts = debts;
	}

	public String getPrimaryBusiness() {
		return primaryBusiness;
	}

	public void setPrimaryBusiness(String primaryBusiness) {
		this.primaryBusiness = primaryBusiness;
	}

	public Double getMonthRent() {
		return monthRent;
	}

	public void setMonthRent(Double monthRent) {
		this.monthRent = monthRent;
	}

	public java.util.Date getDrivelicenseValided() {
		return drivelicenseValided;
	}

	public void setDrivelicenseValided(java.util.Date drivelicenseValided) {
		this.drivelicenseValided = drivelicenseValided;
	}

	public java.util.Date getResidenceValided() {
		return residenceValided;
	}

	public void setResidenceValided(java.util.Date residenceValided) {
		this.residenceValided = residenceValided;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getMicroMessage() {
		return microMessage;
	}

	public void setMicroMessage(String microMessage) {
		this.microMessage = microMessage;
	}

	public java.util.Date getResidenceDate() {
		return residenceDate;
	}

	public void setResidenceDate(java.util.Date residenceDate) {
		this.residenceDate = residenceDate;
	}

	public Integer getCompanyDepartment() {
		return companyDepartment;
	}

	public void setCompanyDepartment(Integer companyDepartment) {
		this.companyDepartment = companyDepartment;
	}

	public String getCompanyFax() {
		return companyFax;
	}

	public void setCompanyFax(String companyFax) {
		this.companyFax = companyFax;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getPayDate() {
		return payDate;
	}

	public void setPayDate(Integer payDate) {
		this.payDate = payDate;
	}

	public String getChildrenCount() {
		return childrenCount;
	}

	public void setChildrenCount(String childrenCount) {
		this.childrenCount = childrenCount;
	}
	
	public String getP2pName() {
		return p2pName;
	}

	public void setP2pName(String p2pName) {
		this.p2pName = p2pName;
	}

	public String getP2pcardnumber() {
		return p2pcardnumber;
	}

	public void setP2pcardnumber(String p2pcardnumber) {
		this.p2pcardnumber = p2pcardnumber;
	}

	public String getP2pcellphone() {
		return p2pcellphone;
	}

	public void setP2pcellphone(String p2pcellphone) {
		this.p2pcellphone = p2pcellphone;
	}

	public String getP2pemail() {
		return p2pemail;
	}

	public void setP2pemail(String p2pemail) {
		this.p2pemail = p2pemail;
	}

	public String getP2pTrueName() {
		return p2pTrueName;
	}

	public void setP2pTrueName(String p2pTrueName) {
		this.p2pTrueName = p2pTrueName;
	}

	public Object toElementStr() {
		StringBuffer sb = new StringBuffer();
		sb.append("甲方（借款人）:"+this.getName()+"</br>");
		sb.append("证件号码："+this.getCardnumber()+"</br>");
		sb.append("通讯地址："+this.getPostaddress()+"</br>");
		sb.append("联系电话："+this.getCellphone()+"</br>");
		return sb.toString();
	}

	public void setJobYearincom(Double jobYearincom) {
		this.jobYearincom = jobYearincom;
	}

	public Double getJobYearincom() {
		return jobYearincom;
	}

	public void setThirdPayFlagId(String thirdPayFlagId) {
		this.thirdPayFlagId = thirdPayFlagId;
	}

	public String getThirdPayFlagId() {
		return thirdPayFlagId;
	}

	public String getIsCheckCard() {
		return isCheckCard;
	}

	public void setIsCheckCard(String isCheckCard) {
		this.isCheckCard = isCheckCard;
	}

	
	
	
	
}