package com.zhiwei.credit.model.p2p;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.gson.annotations.Expose;

/**
 * 
 * @author 
 *
 */
/**
 * BpCustMember Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 会员表
 */
public class BpCustMember extends com.zhiwei.core.model.BaseModel {
   
	@Expose
    protected Long id;
    /**
     * 登录帐号
     */
	protected String loginname;
	/**
	 * 真实姓名
	 */
	protected String truename;
	/**
	 * 登录密码
	 */
	protected String password;
	/**
	 * 用户自己的推荐码
	 */
	protected String plainpassword;
	/**
	 * 手机号码
	 */
	protected String telphone;
	/**
	 * 邮箱
	 */
	protected String email;
	/**
	 * 0，个人；1，企业
	 */
	protected Integer type;
	/**
	 * p2p-  0，男；1是女  erp- 312男；313女
	 */
	protected Integer sex;
	protected String sexname;//性别名称
	/**
	 * 证件类型
	 */
	protected Integer cardtype;
	protected String cardtypename;//证件类型名称
	/**
	 * 身份证号
	 */
	protected String cardcode;
	/**
	 * 出生日期
	 */
	protected java.util.Date birthday;
	/**
	 * 头像
	 */
	protected String headImage;
	/**
	 * 籍贯市
	 */
	protected Integer nativePlaceCity;
	/**
	 * 民族
	 */
	protected Integer nation;
	/**
	 * 居住电话
	 */
	protected String homePhone;
	/**
	 * 现居住地址
	 */
	protected String relationAddress;
	/**
	 * 现居住地址邮编
	 */
	protected String postCode;
	/**
	 * QQ号码
	 */
	protected String QQ;
	/**
	 * 交易密码
	 */
	protected String paymentCode;
	/**
	 * 登录安全问题
	 */
	protected String securityQuestion;
	/**
	 * 登录安全问题答案
	 */
	protected String securityAnswer;
	/**
	 * 角色ID
	 */
	protected Integer roleId;   
	/**
	 * 注册日期
	 */
	protected java.util.Date registrationDate;
	/**
	 * 户口所在地市
	 */
	protected Integer liveCity;
	/**
	 * 婚姻状况
	 */
	protected Integer marry;
	/**
	 * 传真号码
	 */
	protected String fax;
	/**
	 * 是否被删除
	 */
	protected Integer isDelete;
	/**
	 * 是否被禁用
	 */
	protected Integer isForbidden;
	/**
	 * 钱多多账号
	 */
	protected String moneymoremoreId;
	/**
	 * 邮箱是否已认证 1是
	 */
	protected String isCheckEmail;
	/**
	 * 电话是否已认证 1是
	 */
	protected String isCheckPhone;
	/**
     *
	 * 身份证是否已认证 1是
	 */
	protected String isCheckCard;
	
	public static final Short THIRDPAY_ACCTIVED=0;//已激活账户
	public static final Short THIRDPAY_DEACCTIVED=1;//未激活账户
	
	protected Short  thirdPayStatus=THIRDPAY_ACCTIVED;//第三方账户状态：默认已激活状态
	/**
	 * 第三方支付配置 如：gopayConfig 为国付宝 和表sys_config 中对应';
	 */
	protected String thirdPayConfig ;
	/**
	 * VARCHAR(50) COMMENT '第三方支付 返回唯一标识';
	 */
	protected String thirdPayFlagId ;
	/**
	 * VARCHAR(50) COMMENT '第三方支付 备注标识 如汇付 返回的usrID';
	 */
	protected String  thirdPayFlag0 ;
	
	/**
	 * 投标（1代表开启，0代表关闭）
	 */
	protected String tender;
	/**
	 * 还款（1代表开启，0代表关闭）
	 */
	protected String refund;
	/**
	 * 二次分配审核（1代表开启，0代表关闭
	 */
	protected String secondAudit;
	/**
	 * 是否为VIP
	 */
	protected Short isVip;  
	
	/**
	 * 系统账户金额统计字段，不与数据库做映射
	 * @return
	 */
	protected java.math.BigDecimal   totalMoney;//系统账户总额
	protected java.math.BigDecimal   totalInvestMoney;//该账户投资人累计投资金额
	protected java.math.BigDecimal   freezeMoney;//投资预冻结金额
	protected java.math.BigDecimal   availableInvestMoney;//该账户目前可用金额
	protected java.math.BigDecimal   principalRepayment;//表示该账户已经收回本金
	protected java.math.BigDecimal   allInterest;//表示该账户累计的收益
	protected java.math.BigDecimal   totalRecharge;//表示该账户累计充值金额
	protected java.math.BigDecimal   totalEnchashment;//表示该账户累计取现金额 
	protected java.math.BigDecimal   unChargeMoney;//表示该账户未结转金额（富有金账户使用字段）


	
	/**
	 * 会员积分
	 */
	protected Long score;
	/**
	 * 会员类型（数据字典中的标志）
	 */
	protected Long category;
	/**
	 * 会员等级
	 */
	protected Integer memberGrade;
	protected String directReferralsName;//直接推荐人姓名
	protected String indirectReferenceName;//间接推荐人姓名
	protected Long directReferralsId;//直接推荐人主键
	protected Long indirectReferenceId;//间接推荐人主键
	//protected String categoryName;//会员类型
	
	protected String levelMark;//会员等级名称
	/**
	 * 子女人数
	 */
	private Integer havechildren;
	/**
	 * 最高学历
	 */
	private Integer collegeDegree;
	/**
	 * 入学年份
	 */
	private String collegeYear;
	/**
	 * 毕业院校
	 */
	private String collegename;
	/**
	 * 直系亲属姓名
	 */
	private String relDirName;
	/**
	 * 直系亲属关系
	 */
	private Integer relDirType;
	/**
	 * 直系亲属手机
	 */
	private String relDirPhone;
	/**
	 * 其他亲属姓名
	 */
	private String relOtherName;
	/**
	 * 其他亲属关系
	 */
	private Integer relOtherType;
	/**
	 * 其他亲属手机
	 */
	private String relOtherPhone;
	/**
	 * 其他联系人姓名
	 */
	private String relFriendName;
	/**
	 * 其他联系人关系
	 */
	private Integer relFriendType;
	/**
	 * 其他联系人手机
	 */
	private String relFriendPhone;
	/**
	 * 供职公司名称
	 */
	private String hireCompanyname;
	/**
	 * 职位
	 */
	private Integer hirePosition;
	/**
	 * 月收入
	 */
	private java.math.BigDecimal hireMonthlyincome;
	/**
	 * 工作邮箱
	 */
	private String hireEmail;
	/**
	 * 工作城市
	 */
	private Integer hireCity;
	/**
	 * 供职公司地址
	 */
	private String hireAddress;
	/**
	 * 供职公司类别
	 */
	private Integer hireCompanytype;
	/**
	 * 供职公司行业
	 */
	private Integer hireCompanycategory;
	/**
	 * 供职公司规模
	 */
	private Integer hireCompanysize;
	/**
	 * 在现单位工作年限
	 */
	private java.util.Date hireStartyear;
	/**
	 * 供职公司电话
	 */
	private String hireCompanyphone;
	/**
	 * 是否有房产
	 */
	private Integer havehouse;
	/**
	 * 是否有房贷
	 */
	private Integer havehouseloan;
	/**
	 * 是否有车产
	 */
	private Integer havecar;
	/**
	 * 是否有车贷
	 */
	private Integer havecarloan;


	/**
	 * 是众签签章usercode
	 */
	private String userCode;


	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	private String strHouse;
	private String strHouseLoan;
	private String strCar;
	private String strCarLoan;
	private String strMarry;
	private String strChildren;
	/**
	 * 用户注册时填写的推荐人的推荐码
	 */
    protected String recommandPerson;//邀请人推荐码 svn:songwj
    protected Integer recommandNum;//邀请人数 svn:songwj
    
    protected Integer secondRecommandNum;//二级推荐用户个数
    
    protected String num;//svn:songwj
    protected String num2;
    
	protected java.math.BigDecimal   totalLoanMoney;//累计融资金额svn:songwj
	protected java.math.BigDecimal   totalPrincipalRepaymentMoney;//累计还本付息svn:songwj
	protected java.math.BigDecimal   totalNotPrincipalRepaymentMoney;//剩余未还本息svn:songwj
	
	protected Integer parentNativePlaceCity;//籍贯省
	protected Integer parentLiveCity;//居住城市省
	protected Integer parentHireCity;//工作城市省
	protected String hireCompanycategoryName;//行业名称
	
	//网店信息字段
	protected String webshopName;        //网点名称                
	protected java.math.BigDecimal webshopMonthlyIncome;  //网店月经营收入
	protected String webshopEmail;      //网店联系邮箱
	protected String webshopProvince;   //网店所在省份
	protected String webshopCity;       //网店所在城市
	protected String webshopAddress;    //网店经营地址
	protected String webshopStartYear;  //网店经营年限
	protected String webshopPhone;      //网店联系电话
	
   //对应公司相关信息
	/**
	 * 公司名称
	 */
	protected String bossCompanyName;   
	/**
	 * 职位
	 */
	protected String bossPosition;
	/**
	 * 月收入
	 */
	protected java.math.BigDecimal bossMonthlyIncome;
	/**
	 * 工作邮箱
	 */
	protected String bossEmail;
	/**
	 * 工作城市
	 */
	protected String bossCity;
	/**
	 * 公司地址
	 */
	protected String bossAddress;
	/**
	 * 公司类别
	 */
	protected String bossCompanyType;
	/**
	 * 公司行业
	 */
	protected String bossCompanyCategory;
	/**
	 * 公司规模
	 */
	protected String bossCompanySize;
	/**
	 * 在现单位工作年限
	 */
	protected String bossStartYear;
	/**
	 * 公司电话
	 */
	protected String bossCompanyPhone;

	/**
	 * 单位名称
	 */
	protected String teacherCompanyName;
	/**
	 * 职位
	 */
	protected String teacherPosition;
	/**
	 * 月收入
	 */
	protected java.math.BigDecimal teacherMonthlyIncome;
	/**
	 * 工作邮箱
	 */
	protected String teacherEmail;
	/**
	 * 工作城市
	 */
	protected String teacherCity;
	/**
	 * 单位地址
	 */
	protected String teacherAddress;
	/**
	 * 教龄
	 */
	protected String teacherStartYear;
	/**
	 * 单位电话
	 */
	protected String teacherCompanyPhone;
	
	
	protected java.util.Date memberDuedate;   //会员到期日
	protected java.util.Date memberDuedate1;
	protected String recommandPerson1;        
	protected Integer recommandNum1;
	protected String sinawb;                  //新浪微博账号
	protected String isCheckSinaWB;           //新浪微博是否已绑定
	protected String isCheckQQ;               //QQ号是否已绑定
	
	protected java.util.Date isCheckCardTime;  //身份证验证时间
	protected String isCheckCardMessage;       //身份证验证返回信息
	protected Integer isSync;
	protected Integer liveProvice;
	//======================================================
	
	public static final Integer CUSTOMER_PERSON=0;
	public static final Integer CUSTOMER_ENTERPRISE=1;
	/**
	 * 客户类型：默认值0，表示个人客户
	 * 0个人客户,1企业客户,2担保客户
	 */
	protected Integer customerType=0;
	/**
	 * 企业客户-银行开户许可证
	 */
	protected String bankLicense;


	/**
     * 企业客户-营业执照
     */
	protected String businessLicense;
	/**
	 * 企业客户-税务登记号
	 */
	protected String taxNo;
	/**
	 * 企业客户-法人姓名
	 */
	protected String legalPerson;
	/**
	 * 企业客户-法人身份证号码
	 */
	protected String legalNo;
	public Integer getCustType() {
		return custType;
	}

	public void setCustType(Integer custType) {
		this.custType = custType;
	}

	/**
	 * 企业客户-企业联系人
	 */
	protected String contactPerson;
	
	protected Integer custType;
	/**
	 * 企业客户类型
	 */
	protected Short entCompanyType;//客户类型 0投资客户 1担保户
	
	private String departmentRecommend;//部门推荐码
	
	
    public String getDepartmentRecommend() {
		return departmentRecommend;
	}

	public void setDepartmentRecommend(String departmentRecommend) {
		this.departmentRecommend = departmentRecommend;
	}

	public Short getEntCompanyType() {
		return entCompanyType;
	}

	public void setEntCompanyType(Short entCompanyType) {
		this.entCompanyType = entCompanyType;
	}

	public Integer getCustomerType() {
		return customerType;
	}

	public void setCustomerType(Integer customerType) {
		this.customerType = customerType;
	}

	
	public String getBankLicense() {
		return bankLicense;
	}

	public void setBankLicense(String bankLicense) {
		this.bankLicense = bankLicense;
	}

	public String getBusinessLicense() {
		return businessLicense;
	}

	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}

	public String getTaxNo() {
		return taxNo;
	}

	public void setTaxNo(String taxNo) {
		this.taxNo = taxNo;
	}

	public String getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}

	public String getLegalNo() {
		return legalNo;
	}

	public void setLegalNo(String legalNo) {
		this.legalNo = legalNo;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	

	public Integer getMarry() {
		return marry;
	}

	public void setMarry(Integer marry) {
		this.marry = marry;
	}

	public String getHireCompanycategoryName() {
		return hireCompanycategoryName;
	}

	public void setHireCompanycategoryName(String hireCompanycategoryName) {
		this.hireCompanycategoryName = hireCompanycategoryName;
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

	public Integer getParentNativePlaceCity() {
		return parentNativePlaceCity;
	}

	public void setParentNativePlaceCity(Integer parentNativePlaceCity) {
		this.parentNativePlaceCity = parentNativePlaceCity;
	}

	public java.math.BigDecimal getTotalPrincipalRepaymentMoney() {
		return totalPrincipalRepaymentMoney;
	}

	public void setTotalPrincipalRepaymentMoney(
			java.math.BigDecimal totalPrincipalRepaymentMoney) {
		this.totalPrincipalRepaymentMoney = totalPrincipalRepaymentMoney;
	}

	public java.math.BigDecimal getTotalNotPrincipalRepaymentMoney() {
		return totalNotPrincipalRepaymentMoney;
	}

	public void setTotalNotPrincipalRepaymentMoney(
			java.math.BigDecimal totalNotPrincipalRepaymentMoney) {
		this.totalNotPrincipalRepaymentMoney = totalNotPrincipalRepaymentMoney;
	}

	public String getRecommandPerson() {
		return recommandPerson;
	}

	public void setRecommandPerson(String recommandPerson) {
		this.recommandPerson = recommandPerson;
	}

	public Integer getRecommandNum() {
		return recommandNum;
	}

	public void setRecommandNum(Integer recommandNum) {
		this.recommandNum = recommandNum;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getStrMarry() {
		return strMarry;
	}

	public void setStrMarry(String strMarry) {
		this.strMarry = strMarry;
	}

	public String getStrChildren() {
		return strChildren;
	}

	public void setStrChildren(String strChildren) {
		this.strChildren = strChildren;
	}

	public String getStrHouse() {
		return strHouse;
	}

	public void setStrHouse(String strHouse) {
		this.strHouse = strHouse;
	}

	public String getStrHouseLoan() {
		return strHouseLoan;
	}

	public void setStrHouseLoan(String strHouseLoan) {
		this.strHouseLoan = strHouseLoan;
	}

	public String getStrCar() {
		return strCar;
	}

	public void setStrCar(String strCar) {
		this.strCar = strCar;
	}

	public String getStrCarLoan() {
		return strCarLoan;
	}

	public void setStrCarLoan(String strCarLoan) {
		this.strCarLoan = strCarLoan;
	}

/*	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}*/

	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
		this.score = score;
	}

	public Long getCategory() {
		return category;
	}

	public void setCategory(Long category) {
		this.category = category;
	}

	

	public Integer getMemberGrade() {
		return memberGrade;
	}

	public void setMemberGrade(Integer memberGrade) {
		this.memberGrade = memberGrade;
	}

	public String getDirectReferralsName() {
		return directReferralsName;
	}

	public void setDirectReferralsName(String directReferralsName) {
		this.directReferralsName = directReferralsName;
	}

	public String getIndirectReferenceName() {
		return indirectReferenceName;
	}

	public void setIndirectReferenceName(String indirectReferenceName) {
		this.indirectReferenceName = indirectReferenceName;
	}

	public Long getDirectReferralsId() {
		return directReferralsId;
	}

	public void setDirectReferralsId(Long directReferralsId) {
		this.directReferralsId = directReferralsId;
	}

	public Long getIndirectReferenceId() {
		return indirectReferenceId;
	}

	public void setIndirectReferenceId(Long indirectReferenceId) {
		this.indirectReferenceId = indirectReferenceId;
	}

	public java.math.BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(java.math.BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	public java.math.BigDecimal getTotalInvestMoney() {
		return totalInvestMoney;
	}

	public void setTotalInvestMoney(java.math.BigDecimal totalInvestMoney) {
		this.totalInvestMoney = totalInvestMoney;
	}

	public java.math.BigDecimal getFreezeMoney() {
		return freezeMoney;
	}

	public void setFreezeMoney(java.math.BigDecimal freezeMoney) {
		this.freezeMoney = freezeMoney;
	}

	public java.math.BigDecimal getAvailableInvestMoney() {
		return availableInvestMoney;
	}

	public void setAvailableInvestMoney(java.math.BigDecimal availableInvestMoney) {
		this.availableInvestMoney = availableInvestMoney;
	}

	public java.math.BigDecimal getPrincipalRepayment() {
		return principalRepayment;
	}

	public void setPrincipalRepayment(java.math.BigDecimal principalRepayment) {
		this.principalRepayment = principalRepayment;
	}

	public java.math.BigDecimal getAllInterest() {
		return allInterest;
	}

	public void setAllInterest(java.math.BigDecimal allInterest) {
		this.allInterest = allInterest;
	}

	public java.math.BigDecimal getTotalRecharge() {
		return totalRecharge;
	}

	public void setTotalRecharge(java.math.BigDecimal totalRecharge) {
		this.totalRecharge = totalRecharge;
	}

	public java.math.BigDecimal getTotalEnchashment() {
		return totalEnchashment;
	}

	public void setTotalEnchashment(java.math.BigDecimal totalEnchashment) {
		this.totalEnchashment = totalEnchashment;
	}

	public java.math.BigDecimal getUnChargeMoney() {
		return unChargeMoney;
	}

	public void setUnChargeMoney(java.math.BigDecimal unChargeMoney) {
		this.unChargeMoney = unChargeMoney;
	}

	public void setThirdPayConfig(String thirdPayConfig) {
		this.thirdPayConfig = thirdPayConfig;
	}

	public String getThirdPayConfig() {
		return thirdPayConfig;
	}
	public String getThirdPayFlagId() {
		return thirdPayFlagId;
	}

	public void setThirdPayFlagId(String thirdPayFlagId) {
		this.thirdPayFlagId = thirdPayFlagId;
	}

	public String getThirdPayFlag0() {
		return thirdPayFlag0;
	}

	public void setThirdPayFlag0(String thirdPayFlag0) {
		this.thirdPayFlag0 = thirdPayFlag0;
	}


	public String getMoneymoremoreId() {
		return moneymoremoreId;
	}

	public void setMoneymoremoreId(String moneymoremoreId) {
		this.moneymoremoreId = moneymoremoreId;
	}
	
	public Integer getLiveProvice() {
		return liveProvice;
	}

	public void setLiveProvice(Integer liveProvice) {
		this.liveProvice = liveProvice;
	}

	public String getIsCheckEmail() {
		return isCheckEmail;
	}

	public void setIsCheckEmail(String isCheckEmail) {
		this.isCheckEmail = isCheckEmail;
	}

	public String getIsCheckPhone() {
		return isCheckPhone;
	}

	public void setIsCheckPhone(String isCheckPhone) {
		this.isCheckPhone = isCheckPhone;
	}

	public String getIsCheckCard() {
		return isCheckCard;
	}

	public void setIsCheckCard(String isCheckCard) {
		this.isCheckCard = isCheckCard;
	}

	/**
	 * Default Empty Constructor for class BpCustMember
	 */
	public BpCustMember () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class BpCustMember
	 */
	public BpCustMember (
		 Long in_id
        ) {
		this.setId(in_id);
    }

    

	public String getSexname() {
		return sexname;
	}

	public void setSexname(String sexname) {
		this.sexname = sexname;
	}

	public String getCardtypename() {
		return cardtypename;
	}

	public void setCardtypename(String cardtypename) {
		this.cardtypename = cardtypename;
	}

	public String getWebshopName() {
		return webshopName;
	}

	public void setWebshopName(String webshopName) {
		this.webshopName = webshopName;
	}

	/**
	 * id唯一标示符	 * @return Long
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
	 * 登录名	 * @return String
	 * @hibernate.property column="loginname" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getLoginname() {
		return this.loginname;
	}
	
	/**
	 * Set the loginname
	 */	
	public void setLoginname(String aValue) {
		this.loginname = aValue;
	}	

	/**
	 * 真实姓名	 * @return String
	 * @hibernate.property column="truename" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getTruename() {
		return this.truename;
	}
	
	/**
	 * Set the truename
	 */	
	public void setTruename(String aValue) {
		this.truename = aValue;
	}	

	/**
	 * 密码（加密）	 * @return String
	 * @hibernate.property column="password" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getPassword() {
		return this.password;
	}
	
	/**
	 * Set the password
	 */	
	public void setPassword(String aValue) {
		this.password = aValue;
	}	

	/**
	 * 登录密码	 * @return String
	 * @hibernate.property column="plainpassword" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getPlainpassword() {
		return this.plainpassword;
	}
	
	/**
	 * Set the plainpassword
	 */	
	public void setPlainpassword(String aValue) {
		this.plainpassword = aValue;
	}	

	/**
	 * 手机号码	 * @return String
	 * @hibernate.property column="telphone" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getTelphone() {
		return this.telphone;
	}
	
	/**
	 * Set the telphone
	 */	
	public void setTelphone(String aValue) {
		this.telphone = aValue;
	}	

	/**
	 * 邮箱	 * @return String
	 * @hibernate.property column="email" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getEmail() {
		return this.email;
	}
	
	/**
	 * Set the email
	 */	
	public void setEmail(String aValue) {
		this.email = aValue;
	}	

	/**
	 * 会员类型（0个人，1企业）	 * @return Integer
	 * @hibernate.property column="type" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getType() {
		return this.type;
	}
	
	/**
	 * Set the type
	 */	
	public void setType(Integer aValue) {
		this.type = aValue;
	}	

	/**
	 * 性别（0男性，1女性）	 * @return Integer
	 * @hibernate.property column="sex" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getSex() {
		return this.sex;
	}
	
	/**
	 * Set the sex
	 */	
	public void setSex(Integer aValue) {
		this.sex = aValue;
	}	

	/**
	 * 证件类型	 * @return Integer
	 * @hibernate.property column="cardtype" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getCardtype() {
		return this.cardtype;
	}
	
	/**
	 * Set the cardtype
	 */	
	public void setCardtype(Integer aValue) {
		this.cardtype = aValue;
	}	

	/**
	 * 证件号码	 * @return String
	 * @hibernate.property column="cardcode" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getCardcode() {
		return this.cardcode;
	}
	
	/**
	 * Set the cardcode
	 */	
	public void setCardcode(String aValue) {
		this.cardcode = aValue;
	}	

	/**
	 * 出生日期	 * @return java.util.Date
	 * @hibernate.property column="birthday" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getBirthday() {
		return this.birthday;
	}
	
	/**
	 * Set the birthday
	 */	
	public void setBirthday(java.util.Date aValue) {
		this.birthday = aValue;
	}	

	/**
	 * 头像	 * @return String
	 * @hibernate.property column="headImage" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getHeadImage() {
		return this.headImage;
	}
	
	/**
	 * Set the headImage
	 */	
	public void setHeadImage(String aValue) {
		this.headImage = aValue;
	}	


	/**
	 * 籍贯市	 * @return String
	 * @hibernate.property column="nativePlaceCity" type="java.lang.String" length="10" not-null="false" unique="false"
	 */
	public Integer getNativePlaceCity() {
		return this.nativePlaceCity;
	}
	
	/**
	 * Set the nativePlaceCity
	 */	
	public void setNativePlaceCity(Integer aValue) {
		this.nativePlaceCity = aValue;
	}	

	/**
	 * 民族	 * @return String
	 * @hibernate.property column="nation" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public Integer getNation() {
		return this.nation;
	}
	
	/**
	 * Set the nation
	 */	
	public void setNation(Integer aValue) {
		this.nation = aValue;
	}	

	/**
	 * 家庭电话	 * @return String
	 * @hibernate.property column="homePhone" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getHomePhone() {
		return this.homePhone;
	}
	
	/**
	 * Set the homePhone
	 */	
	public void setHomePhone(String aValue) {
		this.homePhone = aValue;
	}	

	/**
	 * 联系地址	 * @return String
	 * @hibernate.property column="relationAddress" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getRelationAddress() {
		return this.relationAddress;
	}
	
	/**
	 * Set the relationAddress
	 */	
	public void setRelationAddress(String aValue) {
		this.relationAddress = aValue;
	}	

	/**
	 * 邮编	 * @return String
	 * @hibernate.property column="postCode" type="java.lang.String" length="255" not-null="false" unique="false"
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
	 * qq	 * @return String
	 * @hibernate.property column="QQ" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getQQ() {
		return this.QQ;
	}
	
	/**
	 * Set the QQ
	 */	
	public void setQQ(String aValue) {
		this.QQ = aValue;
	}	

	

	/**
	 * 支付密码	 * @return String
	 * @hibernate.property column="paymentCode" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getPaymentCode() {
		return this.paymentCode;
	}
	
	/**
	 * Set the paymentCode
	 */	
	public void setPaymentCode(String aValue) {
		this.paymentCode = aValue;
	}	

	/**
	 * 密码保护问题	 * @return String
	 * @hibernate.property column="securityQuestion" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getSecurityQuestion() {
		return this.securityQuestion;
	}
	
	/**
	 * Set the securityQuestion
	 */	
	public void setSecurityQuestion(String aValue) {
		this.securityQuestion = aValue;
	}	

	/**
	 * 密码保护答案	 * @return String
	 * @hibernate.property column="securityAnswer" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getSecurityAnswer() {
		return this.securityAnswer;
	}
	
	/**
	 * Set the securityAnswer
	 */	
	public void setSecurityAnswer(String aValue) {
		this.securityAnswer = aValue;
	}	

	/**
	 * 角色ID	 * @return Integer
	 * @hibernate.property column="roleId" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getRoleId() {
		return this.roleId;
	}
	
	/**
	 * Set the roleId
	 */	
	public void setRoleId(Integer aValue) {
		this.roleId = aValue;
	}	

	/**
	 * 注册时间	 * @return java.util.Date
	 * @hibernate.property column="registrationDate"  length="19" not-null="false" unique="false"
	 */
	public java.util.Date getRegistrationDate() {
		return this.registrationDate;
	}
	
	/**
	 * Set the registrationDate
	 */	
	public void setRegistrationDate(java.util.Date aValue) {
		this.registrationDate = aValue;
	}	

	/**
	 * 居住城市-市	 * @return String
	 * @hibernate.property column="liveCity" type="java.lang.String" length="20" not-null="false" unique="false"
	 */
	public Integer getLiveCity() {
		return this.liveCity;
	}
	
	/**
	 * Set the liveCity
	 */	
	public void setLiveCity(Integer aValue) {
		this.liveCity = aValue;
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
	 * 是否删除	 * @return Integer
	 * @hibernate.property column="isDelete" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getIsDelete() {
		return this.isDelete;
	}
	
	/**
	 * Set the isDelete
	 */	
	public void setIsDelete(Integer aValue) {
		this.isDelete = aValue;
	}	

	/**
	 * 是否禁用	 * @return Integer
	 * @hibernate.property column="isForbidden" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getIsForbidden() {
		return this.isForbidden;
	}
	
	/**
	 * Set the isForbidden
	 */	
	public void setIsForbidden(Integer aValue) {
		this.isForbidden = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof BpCustMember)) {
			return false;
		}
		BpCustMember rhs = (BpCustMember) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.loginname, rhs.loginname)
				.append(this.truename, rhs.truename)
				.append(this.password, rhs.password)
				.append(this.plainpassword, rhs.plainpassword)
				.append(this.telphone, rhs.telphone)
				.append(this.email, rhs.email)
				.append(this.type, rhs.type)
				.append(this.sex, rhs.sex)
				.append(this.cardtype, rhs.cardtype)
				.append(this.cardcode, rhs.cardcode)
				.append(this.birthday, rhs.birthday)
				.append(this.headImage, rhs.headImage)
				.append(this.nativePlaceCity, rhs.nativePlaceCity)
				.append(this.nation, rhs.nation)
				.append(this.homePhone, rhs.homePhone)
				.append(this.relationAddress, rhs.relationAddress)
				.append(this.postCode, rhs.postCode)
				.append(this.QQ, rhs.QQ)
				.append(this.paymentCode, rhs.paymentCode)
				.append(this.securityQuestion, rhs.securityQuestion)
				.append(this.securityAnswer, rhs.securityAnswer)
				.append(this.roleId, rhs.roleId)
				.append(this.registrationDate, rhs.registrationDate)
				.append(this.liveCity, rhs.liveCity)
				.append(this.marry, rhs.marry)
				.append(this.fax, rhs.fax)
				.append(this.isDelete, rhs.isDelete)
				.append(this.isForbidden, rhs.isForbidden)
				.append(this.recommandPerson, rhs.recommandPerson)
				.append(this.recommandNum, rhs.recommandNum)
				.append(this.totalLoanMoney, rhs.totalLoanMoney)
				.append(this.num, rhs.num)
				.append(this.totalPrincipalRepaymentMoney, rhs.totalPrincipalRepaymentMoney)
				.append(this.totalNotPrincipalRepaymentMoney, rhs.totalNotPrincipalRepaymentMoney)
				.isEquals();
	}
	
	
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.loginname) 
				.append(this.truename) 
				.append(this.password) 
				.append(this.plainpassword) 
				.append(this.telphone) 
				.append(this.email) 
				.append(this.type) 
				.append(this.sex) 
				.append(this.cardtype) 
				.append(this.cardcode) 
				.append(this.birthday) 
				.append(this.headImage) 
				.append(this.nativePlaceCity) 
				.append(this.nation) 
				.append(this.homePhone) 
				.append(this.relationAddress) 
				.append(this.postCode) 
				.append(this.QQ) 
				.append(this.paymentCode) 
				.append(this.securityQuestion) 
				.append(this.securityAnswer) 
				.append(this.roleId) 
				.append(this.registrationDate) 
				.append(this.liveCity) 
				.append(this.marry) 
				.append(this.fax) 
				.append(this.isDelete) 
				.append(this.isForbidden) 
				.append(this.recommandPerson) 
				.append(this.recommandNum) 
				.append(this.totalLoanMoney) 
				.append(this.num) 
				.append(this.totalPrincipalRepaymentMoney) 
				.append(this.totalNotPrincipalRepaymentMoney) 
				.toHashCode();
	}

	
	

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("loginname", this.loginname) 
				.append("truename", this.truename) 
				.append("password", this.password) 
				.append("plainpassword", this.plainpassword) 
				.append("telphone", this.telphone) 
				.append("email", this.email) 
				.append("type", this.type) 
				.append("sex", this.sex) 
				.append("cardtype", this.cardtype) 
				.append("cardcode", this.cardcode) 
				.append("birthday", this.birthday) 
				.append("headImage", this.headImage) 
				.append("nativePlaceCity", this.nativePlaceCity) 
				.append("nation", this.nation) 
				.append("homePhone", this.homePhone) 
				.append("relationAddress", this.relationAddress) 
				.append("postCode", this.postCode) 
				.append("QQ", this.QQ) 
				.append("paymentCode", this.paymentCode) 
				.append("securityQuestion", this.securityQuestion) 
				.append("securityAnswer", this.securityAnswer) 
				.append("roleId", this.roleId) 
				.append("registrationDate", this.registrationDate) 
				.append("liveCity", this.liveCity) 
				.append("marry", this.marry) 
				.append("fax", this.fax) 
				.append("isDelete", this.isDelete) 
				.append("isForbidden", this.isForbidden) 
				.append("recommandPerson", this.recommandPerson) 
				.append("recommandNum", this.recommandNum) 
				
				.append("num", this.num) 
				.append("totalLoanMoney", this.totalLoanMoney) 
				.append("totalPrincipalRepaymentMoney", this.totalPrincipalRepaymentMoney) 
				.append("totalNotPrincipalRepaymentMoney", this.totalNotPrincipalRepaymentMoney) 
				.toString();
	}
	 

	 

	public Integer getHavechildren() {
		return havechildren;
	}

	public void setHavechildren(Integer havechildren) {
		this.havechildren = havechildren;
	}

	public Integer getCollegeDegree() {
		return collegeDegree;
	}

	public void setCollegeDegree(Integer collegeDegree) {
		this.collegeDegree = collegeDegree;
	}

	

	public String getCollegeYear() {
		return collegeYear;
	}

	public void setCollegeYear(String collegeYear) {
		this.collegeYear = collegeYear;
	}

	public String getCollegename() {
		return collegename;
	}

	public void setCollegename(String collegename) {
		this.collegename = collegename;
	}

	public String getRelDirName() {
		return relDirName;
	}

	public void setRelDirName(String relDirName) {
		this.relDirName = relDirName;
	}

	public Integer getRelDirType() {
		return relDirType;
	}

	public void setRelDirType(Integer relDirType) {
		this.relDirType = relDirType;
	}

	public String getRelDirPhone() {
		return relDirPhone;
	}

	public void setRelDirPhone(String relDirPhone) {
		this.relDirPhone = relDirPhone;
	}

	public String getRelOtherName() {
		return relOtherName;
	}

	public void setRelOtherName(String relOtherName) {
		this.relOtherName = relOtherName;
	}

	public Integer getRelOtherType() {
		return relOtherType;
	}

	public void setRelOtherType(Integer relOtherType) {
		this.relOtherType = relOtherType;
	}

	public String getRelOtherPhone() {
		return relOtherPhone;
	}

	public void setRelOtherPhone(String relOtherPhone) {
		this.relOtherPhone = relOtherPhone;
	}

	public String getRelFriendName() {
		return relFriendName;
	}

	public void setRelFriendName(String relFriendName) {
		this.relFriendName = relFriendName;
	}

	public Integer getRelFriendType() {
		return relFriendType;
	}

	public void setRelFriendType(Integer relFriendType) {
		this.relFriendType = relFriendType;
	}

	public String getRelFriendPhone() {
		return relFriendPhone;
	}

	public void setRelFriendPhone(String relFriendPhone) {
		this.relFriendPhone = relFriendPhone;
	}

/*	public Integer getCareerType() {
		return careerType;
	}

	public void setCareerType(Integer careerType) {
		this.careerType = careerType;
	}*/

	

	public String getHireCompanyname() {
		return hireCompanyname;
	}

	public void setHireCompanyname(String hireCompanyname) {
		this.hireCompanyname = hireCompanyname;
	}

	public java.math.BigDecimal getWebshopMonthlyIncome() {
		return webshopMonthlyIncome;
	}

	public void setWebshopMonthlyIncome(java.math.BigDecimal webshopMonthlyIncome) {
		this.webshopMonthlyIncome = webshopMonthlyIncome;
	}

	public Integer getHirePosition() {
		return hirePosition;
	}

	public void setHirePosition(Integer hirePosition) {
		this.hirePosition = hirePosition;
	}

	public java.math.BigDecimal getHireMonthlyincome() {
		return hireMonthlyincome;
	}

	public void setHireMonthlyincome(java.math.BigDecimal hireMonthlyincome) {
		this.hireMonthlyincome = hireMonthlyincome;
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

	public String getHireAddress() {
		return hireAddress;
	}

	public void setHireAddress(String hireAddress) {
		this.hireAddress = hireAddress;
	}

	public Integer getHireCompanytype() {
		return hireCompanytype;
	}

	public void setHireCompanytype(Integer hireCompanytype) {
		this.hireCompanytype = hireCompanytype;
	}

	public Integer getHireCompanycategory() {
		return hireCompanycategory;
	}

	public void setHireCompanycategory(Integer hireCompanycategory) {
		this.hireCompanycategory = hireCompanycategory;
	}

	public Integer getHireCompanysize() {
		return hireCompanysize;
	}

	public void setHireCompanysize(Integer hireCompanysize) {
		this.hireCompanysize = hireCompanysize;
	}

	public Date getHireStartyear() {
		return hireStartyear;
	}

	public void setHireStartyear(Date hireStartyear) {
		this.hireStartyear = hireStartyear;
	}

	public String getHireCompanyphone() {
		return hireCompanyphone;
	}

	public void setHireCompanyphone(String hireCompanyphone) {
		this.hireCompanyphone = hireCompanyphone;
	}

	public Integer getHavehouse() {
		return havehouse;
	}

	public void setHavehouse(Integer havehouse) {
		this.havehouse = havehouse;
	}

	public Integer getHavehouseloan() {
		return havehouseloan;
	}

	public void setHavehouseloan(Integer havehouseloan) {
		this.havehouseloan = havehouseloan;
	}

	public Integer getHavecar() {
		return havecar;
	}

	public void setHavecar(Integer havecar) {
		this.havecar = havecar;
	}

	public Integer getHavecarloan() {
		return havecarloan;
	}

	public void setHavecarloan(Integer havecarloan) {
		this.havecarloan = havecarloan;
	}
	
	/*public String getWebShopName() {
		return webshopName;
	}

	public void setWebShopName(String webShopName) {
		this.webshopName = webShopName;
	}*/

	/*public java.math.BigDecimal getWebShopMonthlyIncome() {
		return webShopMonthlyIncome;
	}

	public void setWebShopMonthlyIncome(java.math.BigDecimal webShopMonthlyIncome) {
		this.webShopMonthlyIncome = webShopMonthlyIncome;
	}*/

/*	public String getWebshopeMail() {
		return webshopeMail;
	}

	public void setWebshopeMail(String webshopeMail) {
		this.webshopeMail = webshopeMail;
	}
*/
	public String getWebShopProvince() {
		return webshopProvince;
	}

	public void setWebShopProvince(String webShopProvince) {
		this.webshopProvince = webShopProvince;
	}

	public String getWebShopCity() {
		return webshopCity;
	}

	public void setWebShopCity(String webShopCity) {
		this.webshopCity = webShopCity;
	}

	public String getWebShopAddress() {
		return webshopAddress;
	}

	public void setWebShopAddress(String webShopAddress) {
		this.webshopAddress = webShopAddress;
	}

	public String getWebShopStartYear() {
		return webshopStartYear;
	}

	public void setWebShopStartYear(String webShopStartYear) {
		this.webshopStartYear = webShopStartYear;
	}

	public String getWebShopPhone() {
		return webshopPhone;
	}

	public void setWebShopPhone(String webShopPhone) {
		this.webshopPhone = webShopPhone;
	}

	public String getWebshopEmail() {
		return webshopEmail;
	}

	public void setWebshopEmail(String webshopEmail) {
		this.webshopEmail = webshopEmail;
	}

	public String getBossCompanyName() {
		return bossCompanyName;
	}

	public void setBossCompanyName(String bossCompanyName) {
		this.bossCompanyName = bossCompanyName;
	}

	public String getBossPosition() {
		return bossPosition;
	}

	public void setBossPosition(String bossPosition) {
		this.bossPosition = bossPosition;
	}

	public java.math.BigDecimal getBossMonthlyIncome() {
		return bossMonthlyIncome;
	}

	public void setBossMonthlyIncome(java.math.BigDecimal bossMonthlyIncome) {
		this.bossMonthlyIncome = bossMonthlyIncome;
	}

/*	public String getBosseMail() {
		return bosseMail;
	}

	public void setBosseMail(String bosseMail) {
		this.bosseMail = bosseMail;
	}*/

	public String getBossCity() {
		return bossCity;
	}

	public void setBossCity(String bossCity) {
		this.bossCity = bossCity;
	}

	public String getBossAddress() {
		return bossAddress;
	}

	public void setBossAddress(String bossAddress) {
		this.bossAddress = bossAddress;
	}

	public String getBossCompanyType() {
		return bossCompanyType;
	}

	public void setBossCompanyType(String bossCompanyType) {
		this.bossCompanyType = bossCompanyType;
	}

	public String getBossEmail() {
		return bossEmail;
	}

	public void setBossEmail(String bossEmail) {
		this.bossEmail = bossEmail;
	}

	public String getBossCompanyCategory() {
		return bossCompanyCategory;
	}

	public void setBossCompanyCategory(String bossCompanyCategory) {
		this.bossCompanyCategory = bossCompanyCategory;
	}

	public String getBossCompanySize() {
		return bossCompanySize;
	}

	public void setBossCompanySize(String bossCompanySize) {
		this.bossCompanySize = bossCompanySize;
	}

	public String getBossStartYear() {
		return bossStartYear;
	}

	public void setBossStartYear(String bossStartYear) {
		this.bossStartYear = bossStartYear;
	}

	public String getBossCompanyPhone() {
		return bossCompanyPhone;
	}

	public void setBossCompanyPhone(String bossCompanyPhone) {
		this.bossCompanyPhone = bossCompanyPhone;
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

	public java.util.Date getMemberDuedate() {
		return memberDuedate;
	}

	public void setMemberDuedate(java.util.Date memberDuedate) {
		this.memberDuedate = memberDuedate;
	}

	public java.util.Date getMemberDuedate1() {
		return memberDuedate1;
	}

	public void setMemberDuedate1(java.util.Date memberDuedate1) {
		this.memberDuedate1 = memberDuedate1;
	}

	public String getRecommandPerson1() {
		return recommandPerson1;
	}

	public void setRecommandPerson1(String recommandPerson1) {
		this.recommandPerson1 = recommandPerson1;
	}


	public Integer getRecommandNum1() {
		return recommandNum1;
	}

	public void setRecommandNum1(Integer recommandNum1) {
		this.recommandNum1 = recommandNum1;
	}

	public String getSinawb() {
		return sinawb;
	}

	public void setSinawb(String sinawb) {
		this.sinawb = sinawb;
	}

	public String getIsCheckSinaWB() {
		return isCheckSinaWB;
	}

	public void setIsCheckSinaWB(String isCheckSinaWB) {
		this.isCheckSinaWB = isCheckSinaWB;
	}

	public String getIsCheckQQ() {
		return isCheckQQ;
	}

	public void setIsCheckQQ(String isCheckQQ) {
		this.isCheckQQ = isCheckQQ;
	}

	public java.math.BigDecimal getTotalLoanMoney() {
		return totalLoanMoney;
	}

	public void setTotalLoanMoney(java.math.BigDecimal totalLoanMoney) {
		this.totalLoanMoney = totalLoanMoney;
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

	public java.util.Date getIsCheckCardTime() {
		return isCheckCardTime;
	}

	public void setIsCheckCardTime(java.util.Date isCheckCardTime) {
		this.isCheckCardTime = isCheckCardTime;
	}

	public String getIsCheckCardMessage() {
		return isCheckCardMessage;
	}

	public void setIsCheckCardMessage(String isCheckCardMessage) {
		this.isCheckCardMessage = isCheckCardMessage;
	}

	public Integer getIsSync() {
		return isSync;
	}

	public void setIsSync(Integer isSync) {
		this.isSync = isSync;
	}


	public String getLevelMark() {
		return levelMark;
	}

	public void setLevelMark(String levelMark) {
		this.levelMark = levelMark;
	}

	public Short getThirdPayStatus() {
		return thirdPayStatus;
	}

	public void setThirdPayStatus(Short thirdPayStatus) {
		this.thirdPayStatus = thirdPayStatus;
	}

	public Integer getSecondRecommandNum() {
		return secondRecommandNum;
	}

	public void setSecondRecommandNum(Integer secondRecommandNum) {
		this.secondRecommandNum = secondRecommandNum;
	}

	public String getNum2() {
		return num2;
	}

	public void setNum2(String num2) {
		this.num2 = num2;
	}
	
	public String getTender() {
			return tender;
	}

	public void setTender(String tender) {
		this.tender = tender;
	}

	public String getRefund() {
		return refund;
	}

	public void setRefund(String refund) {
		this.refund = refund;
	}

	public String getSecondAudit() {
		return secondAudit;
	}

	public void setSecondAudit(String secondAudit) {
		this.secondAudit = secondAudit;
	}

	public Short getIsVip() {
		return isVip;
	}

	public void setIsVip(Short isVip) {
		this.isVip = isVip;
	}


	/**
	 *
	 * 年期限投资金额
	 * @auther: XinFang
	 * @date: 2018/7/5 17:50
	 */
	private BigDecimal yearMoney;

	//七月邀请人数
    private  Integer mouthInventCount;
     //七月活动总金额
    private  BigDecimal sumActicityMoney;

    public BigDecimal getSumActicityMoney() {
        return sumActicityMoney;
    }

    public void setSumActicityMoney(BigDecimal sumActicityMoney) {
        this.sumActicityMoney = sumActicityMoney;
    }

    public Integer getMouthInventCount() {
        return mouthInventCount;
    }

    public void setMouthInventCount(Integer mouthInventCount) {
        this.mouthInventCount = mouthInventCount;
    }

    public BigDecimal getYearMoney() {
		return yearMoney;
	}

	public void setYearMoney(BigDecimal yearMoney) {
		this.yearMoney = yearMoney;
	}
}
