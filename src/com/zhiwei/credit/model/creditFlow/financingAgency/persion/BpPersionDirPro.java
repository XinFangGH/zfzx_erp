package com.zhiwei.credit.model.creditFlow.financingAgency.persion;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

import com.google.gson.annotations.Expose;

/**
 * 
 * @author 
 *
 */
/**
 * BpPersionDirPro Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 个人直投标项目缓存表
 */
public class BpPersionDirPro extends com.zhiwei.core.model.BaseModel {
	/**
	 * 主键
	 */
	@Expose
    protected Long pdirProId;
	/**
	 * sl_smallloan_project表的projectId
	 */
	@Expose
	protected Long proId;
	/**
	 * bp_fund_project表的id，资金方案Id
	 */
	@Expose
	protected Long moneyPlanId; //资金方案Id
	/**
	 * 平台收费比率
	 */
	@Expose
	protected Double planMoneyScale; //平台收费比率
	/**
	 * 业务种类
	 */
	@Expose
	protected String businessType;
	/**
	 * 借款人Id
	 */
	@Expose
	protected Long persionId;
	/**
	 * 借款人姓名
	 */
	@Expose
	protected String persionName;
	/**
	 * 项目名称（—P2P）
	 */
	@Expose
	protected String proName;
	/**
	 * 项目编号
	 */
	@Expose
	protected String proNumber;
	/**
	 * 年化利率
	 */
	@Expose
	protected java.math.BigDecimal yearInterestRate;
	/**
	 * 月化利率
	 */
	@Expose
	protected java.math.BigDecimal monthInterestRate;
	/**
	 * 日化利率
	 */
	@Expose
	protected java.math.BigDecimal dayInterestRate;
	/**
	 * 合计利率
	 */
	@Expose
	protected java.math.BigDecimal totalInterestRate;
	/**
	 * 计息周期
	 */
	@Expose
	protected String interestPeriod;
	/**
	 * 计息方式
	 */
	@Expose
	protected String payIntersetWay;
	/**
	 * 未招标金额
	 */
	@Expose
	protected java.math.BigDecimal bidMoney;
	/**
	 * 借款期限
	 */
	@Expose
	protected Integer loanLife;
	/**
	 * 发标日期
	 */
	@Expose
	protected java.util.Date bidTime;
	/**
	 * 创建日期
	 */
	@Expose
	protected java.util.Date createTime;
	/**
	 * 修改日期
	 */
	@Expose
	protected java.util.Date updateTime;
	/**
	 * 维护状态（0未维护，1已维护）
	 */
	@Expose
	protected Integer keepStat;
	/**
	 * 方案状态（0 方案已经发放 1方案未制定完成）
	 */
	@Expose
	protected Integer schemeStat;
	/**
	 * 借款人性别
	 */
	@Expose
	protected String sex;
	/**
	 * 借款人年龄
	 */
	@Expose
	protected Integer age;
	/**
	 * 借款人学历
	 */
	@Expose
	protected String education;
	/**
	 * 借款人婚姻状态
	 */
	@Expose
	protected String marriage;
	/**
	 * 用户名
	 */
	@Expose
	protected String userName;
	/**
	 * 借款人月收入
	 */
	@Expose
	protected java.math.BigDecimal monthIncome;
	/**
	 * 借款人地址
	 */
	@Expose
	protected String address;
	/**
	 * 借款人公司行业
	 */
	@Expose
	protected String companyIndustry;
	/**
	 * 借款人公司规模
	 */
	@Expose
	protected String companyScale;
	/**
	 * 借款人职务
	 */
	@Expose
	protected String position;
	/**
	 * 工作时间
	 */
	@Expose
	protected String workTime;
	/**
	 * 工作城市
	 */
	@Expose
	protected String workCity;
	/**
	 * 有无房产，0有，1无
	 */
	@Expose
	protected Integer houseProperty;
	/**
	 * 有无房贷，0有，1无
	 */
	@Expose
	protected Integer houseLoan;
	/**
	 * 有无车产，0有，1无
	 */
	@Expose
	protected Integer vehicleProperty;
	/**
	 * 有无车贷，0有，1无
	 */
	@Expose
	protected Integer vehicleLoan;
	/**
	 * 已发标金额
	 */
	@Expose
	protected java.math.BigDecimal publishOrMoney;//已发标金额
	/**
	 * 已发标数量
	 */
	@Expose
	protected int publishOrNum;//已发标数量
	/**
	 * 已发布  占比
	 */
	@Expose
	protected Double rate;//已发布  占比
	/**
	 * 剩余金额
	 */
	@Expose
	protected java.math.BigDecimal residueMoney;//剩余金额
	/**
	 * 还款周期
	 */
	@Expose
	protected String payAcctualType;
	/**
	 * 自定义天数 如果该值不为空 说明还款周期为 自定义周期 已天为单位
	 */
	@Expose
	protected Integer custDate; //自定义天数 如果该值不为空 说明还款周期为 自定义周期 已天为单位
	/**
	 * 表示收款人姓名：债权标表示原始债权人姓名，直投标表示借款人姓名
	 */
	@Expose
	private String receiverName;//表示收款人姓名：债权标表示原始债权人姓名，直投标表示借款人姓名
	/**
	 * 表示收款人P2P账号：债权标表示原始债权人P2P账号，直投标表示借款人P2P账号
	 */
	@Expose
	private String receiverP2PAccountNumber;//表示收款人P2P账号：债权标表示原始债权人P2P账号，直投标表示借款人P2P账号
	protected PlPersionDirProKeep plPersionDirProKeep;
	
	/**
	 * 披露创建时间
	 */
	@Expose
	protected java.util.Date disclosureCreateDate;
	/**
	 * 披露修改时间
	 */
	@Expose
	protected java.util.Date disclosureUpdateDate;
	/**
	 * 融资资金运用情况
	 */
	@Expose
	protected String moneyUseSituation;
	/**
	 * 借款人经营状况及财务状况
	 */
	@Expose
	protected String managementSituation;
	/**
	 * 借款人还款能力变化情况
	 */
	@Expose
	protected String repaymentChangeSituation;
	

	public PlPersionDirProKeep getPlPersionDirProKeep() {
		return plPersionDirProKeep;
	}

	public void setPlPersionDirProKeep(PlPersionDirProKeep plPersionDirProKeep) {
		this.plPersionDirProKeep = plPersionDirProKeep;
	}


	
	public String getPayAcctualType() {
		return payAcctualType;
	}

	public void setPayAcctualType(String payAcctualType) {
		this.payAcctualType = payAcctualType;
	}

	public Integer getCustDate() {
		return custDate;
	}

	public void setCustDate(Integer custDate) {
		this.custDate = custDate;
	}

	public java.math.BigDecimal getPublishOrMoney() {
		return publishOrMoney;
	}

	public void setPublishOrMoney(java.math.BigDecimal publishOrMoney) {
		this.publishOrMoney = publishOrMoney;
	}

	public int getPublishOrNum() {
		return publishOrNum;
	}

	public void setPublishOrNum(int publishOrNum) {
		this.publishOrNum = publishOrNum;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Long getMoneyPlanId() {
		return moneyPlanId;
	}
	
	public Double getPlanMoneyScale() {
		return planMoneyScale;
	}

	public void setPlanMoneyScale(Double planMoneyScale) {
		this.planMoneyScale = planMoneyScale;
	}

	public void setMoneyPlanId(Long moneyPlanId) {
		this.moneyPlanId = moneyPlanId;
	}
	public java.math.BigDecimal getResidueMoney() {
		return residueMoney;
	}

	public void setResidueMoney(java.math.BigDecimal residueMoney) {
		this.residueMoney = residueMoney;
	}

	protected java.util.Set plBidPlans = new java.util.HashSet();
	protected java.util.Set plPersionDirProKeeps = new java.util.HashSet();

	
	/**
	 * 立项项目来源（线上借款，以及别的来源）
	 * credit  表示线上个人信贷项目
	 * null  表示目前线下项目
	 */
	private String resource;
	/**
	 * 立项项目来源主键Id
	 * credit  来源表：BpFinanceApplyUser
	 */
	private Long loanId;
	/**
	 * Default Empty Constructor for class BpPersionDirPro
	 */
	public BpPersionDirPro () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class BpPersionDirPro
	 */
	public BpPersionDirPro (
		 Long in_pdirProId
        ) {
		this.setPdirProId(in_pdirProId);
    }


	public java.util.Set getPlBidPlans () {
		return plBidPlans;
	}	
	
	public void setPlBidPlans (java.util.Set in_plBidPlans) {
		this.plBidPlans = in_plBidPlans;
	}

	public java.util.Set getPlPersionDirProKeeps () {
		return plPersionDirProKeeps;
	}	
	
	public void setPlPersionDirProKeeps (java.util.Set in_plPersionDirProKeeps) {
		this.plPersionDirProKeeps = in_plPersionDirProKeeps;
	}
    

	/**
	 * PdirProjId	 * @return Long
     * @hibernate.id column="pdirProId" type="java.lang.Long" generator-class="native"
	 */
	public Long getPdirProId() {
		return this.pdirProId;
	}
	
	/**
	 * Set the pdirProId
	 */	
	public void setPdirProId(Long aValue) {
		this.pdirProId = aValue;
	}	

	/**
	 * 项目id	 * @return Long
	 * @hibernate.property column="proId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getProId() {
		return this.proId;
	}
	
	/**
	 * Set the proId
	 */	
	public void setProId(Long aValue) {
		this.proId = aValue;
	}	

	/**
	 * 业务品种	 * @return String
	 * @hibernate.property column="businessType" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	public String getBusinessType() {
		return this.businessType;
	}
	
	/**
	 * Set the businessType
	 */	
	public void setBusinessType(String aValue) {
		this.businessType = aValue;
	}	

	/**
	 * 个人Id	 * @return Long
	 * @hibernate.property column="persionId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getPersionId() {
		return this.persionId;
	}
	
	/**
	 * Set the persionId
	 */	
	public void setPersionId(Long aValue) {
		this.persionId = aValue;
	}	

	/**
	 * 个人名称	 * @return String
	 * @hibernate.property column="persionName" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getPersionName() {
		return this.persionName;
	}
	
	/**
	 * Set the persionName
	 */	
	public void setPersionName(String aValue) {
		this.persionName = aValue;
	}	

	/**
	 * 项目名称	 * @return String
	 * @hibernate.property column="proName" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getProName() {
		return this.proName;
	}
	
	/**
	 * Set the proName
	 */	
	public void setProName(String aValue) {
		this.proName = aValue;
	}	

	/**
	 * 项目编号	 * @return String
	 * @hibernate.property column="proNumber" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getProNumber() {
		return this.proNumber;
	}
	
	/**
	 * Set the proNumber
	 */	
	public void setProNumber(String aValue) {
		this.proNumber = aValue;
	}	

	/**
	 * 年化利率	 * @return java.math.BigDecimal
	 * @hibernate.property column="yearInterestRate" type="java.math.BigDecimal" length="10" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getYearInterestRate() {
		return this.yearInterestRate;
	}
	
	/**
	 * Set the yearInterestRate
	 */	
	public void setYearInterestRate(java.math.BigDecimal aValue) {
		this.yearInterestRate = aValue;
	}	

	/**
	 * 月化利率	 * @return java.math.BigDecimal
	 * @hibernate.property column="monthInterestRate" type="java.math.BigDecimal" length="10" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getMonthInterestRate() {
		return this.monthInterestRate;
	}
	
	/**
	 * Set the monthInterestRate
	 */	
	public void setMonthInterestRate(java.math.BigDecimal aValue) {
		this.monthInterestRate = aValue;
	}	

	/**
	 * 日化利率	 * @return java.math.BigDecimal
	 * @hibernate.property column="dayInterestRate" type="java.math.BigDecimal" length="10" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getDayInterestRate() {
		return this.dayInterestRate;
	}
	
	/**
	 * Set the dayInterestRate
	 */	
	public void setDayInterestRate(java.math.BigDecimal aValue) {
		this.dayInterestRate = aValue;
	}	

	/**
	 * 合计利率	 * @return java.math.BigDecimal
	 * @hibernate.property column="totalInterestRate" type="java.math.BigDecimal" length="10" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getTotalInterestRate() {
		return this.totalInterestRate;
	}
	
	/**
	 * Set the totalInterestRate
	 */	
	public void setTotalInterestRate(java.math.BigDecimal aValue) {
		this.totalInterestRate = aValue;
	}	

	/**
	 * 计息周期	 * @return String
	 * @hibernate.property column="interestPeriod" type="java.lang.String" length="10" not-null="false" unique="false"
	 */
	public String getInterestPeriod() {
		return this.interestPeriod;
	}
	
	/**
	 * Set the interestPeriod
	 */	
	public void setInterestPeriod(String aValue) {
		this.interestPeriod = aValue;
	}	

	/**
	 * 付息方式	 * @return String
	 * @hibernate.property column="payIntersetWay" type="java.lang.String" length="10" not-null="false" unique="false"
	 */
	public String getPayIntersetWay() {
		return this.payIntersetWay;
	}
	
	/**
	 * Set the payIntersetWay
	 */	
	public void setPayIntersetWay(String aValue) {
		this.payIntersetWay = aValue;
	}	

	/**
	 * 未招标金额	 * @return java.math.BigDecimal
	 * @hibernate.property column="bidMoney" type="java.math.BigDecimal" length="10" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getBidMoney() {
		return this.bidMoney;
	}
	
	/**
	 * Set the bidMoney
	 */	
	public void setBidMoney(java.math.BigDecimal aValue) {
		this.bidMoney = aValue;
	}	

	/**
	 * 期望借款期限	 * @return Integer
	 * @hibernate.property column="loanLife" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getLoanLife() {
		return this.loanLife;
	}
	
	/**
	 * Set the loanLife
	 */	
	public void setLoanLife(Integer aValue) {
		this.loanLife = aValue;
	}	

	/**
	 * 发标日期/期望到位日期	 * @return java.util.Date
	 * @hibernate.property column="bidTime" type="java.util.Date" length="10" not-null="false" unique="false"
	 */
	public java.util.Date getBidTime() {
		return this.bidTime;
	}
	
	/**
	 * Set the bidTime
	 */	
	public void setBidTime(java.util.Date aValue) {
		this.bidTime = aValue;
	}	

	/**
	 * 创建日期	 * @return java.util.Date
	 * @hibernate.property column="createTime" type="java.util.Date" length="10" not-null="false" unique="false"
	 */
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	/**
	 * Set the createTime
	 */	
	public void setCreateTime(java.util.Date aValue) {
		this.createTime = aValue;
	}	

	/**
	 * 修改日期	 * @return java.util.Date
	 * @hibernate.property column="updateTime" type="java.util.Date" length="10" not-null="false" unique="false"
	 */
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
	
	/**
	 * Set the updateTime
	 */	
	public void setUpdateTime(java.util.Date aValue) {
		this.updateTime = aValue;
	}	

	/**
	 * 维护状态（0 未维护 1已经维护）	 * @return Integer
	 * @hibernate.property column="keepStat" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getKeepStat() {
		return this.keepStat;
	}
	
	/**
	 * Set the keepStat
	 */	
	public void setKeepStat(Integer aValue) {
		this.keepStat = aValue;
	}	

	/**
	 * 方案状态（0 方案已经发放 1方案未制定完成）	 * @return Integer
	 * @hibernate.property column="schemeStat" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getSchemeStat() {
		return this.schemeStat;
	}
	
	/**
	 * Set the schemeStat
	 */	
	public void setSchemeStat(Integer aValue) {
		this.schemeStat = aValue;
	}	

	/**
	 * 性别	 * @return String
	 * @hibernate.property column="sex" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	public String getSex() {
		return this.sex;
	}
	
	/**
	 * Set the sex
	 */	
	public void setSex(String aValue) {
		this.sex = aValue;
	}	

	/**
	 * 年龄	 * @return Integer
	 * @hibernate.property column="age" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getAge() {
		return this.age;
	}
	
	/**
	 * Set the age
	 */	
	public void setAge(Integer aValue) {
		this.age = aValue;
	}	

	/**
	 * 学历	 * @return String
	 * @hibernate.property column="education" type="java.lang.String" length="30" not-null="false" unique="false"
	 */
	public String getEducation() {
		return this.education;
	}
	
	/**
	 * Set the education
	 */	
	public void setEducation(String aValue) {
		this.education = aValue;
	}	

	/**
	 * 婚姻	 * @return String
	 * @hibernate.property column="marriage" type="java.lang.String" length="20" not-null="false" unique="false"
	 */
	public String getMarriage() {
		return this.marriage;
	}
	
	/**
	 * Set the marriage
	 */	
	public void setMarriage(String aValue) {
		this.marriage = aValue;
	}	

	/**
	 * 用户名	 * @return String
	 * @hibernate.property column="userName" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	public String getUserName() {
		return this.userName;
	}
	
	/**
	 * Set the userName
	 */	
	public void setUserName(String aValue) {
		this.userName = aValue;
	}	

	/**
	 * 月收入	 * @return java.math.BigDecimal
	 * @hibernate.property column="monthIncome" type="java.math.BigDecimal" length="10" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getMonthIncome() {
		return this.monthIncome;
	}
	
	/**
	 * Set the monthIncome
	 */	
	public void setMonthIncome(java.math.BigDecimal aValue) {
		this.monthIncome = aValue;
	}	

	/**
	 * 现居住地	 * @return String
	 * @hibernate.property column="address" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getAddress() {
		return this.address;
	}
	
	/**
	 * Set the address
	 */	
	public void setAddress(String aValue) {
		this.address = aValue;
	}	

	/**
	 * 公司行业	 * @return String
	 * @hibernate.property column="companyIndustry" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getCompanyIndustry() {
		return this.companyIndustry;
	}
	
	/**
	 * Set the companyIndustry
	 */	
	public void setCompanyIndustry(String aValue) {
		this.companyIndustry = aValue;
	}	

	/**
	 * 公司规模	 * @return String
	 * @hibernate.property column="companyScale" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getCompanyScale() {
		return this.companyScale;
	}
	
	/**
	 * Set the companyScale
	 */	
	public void setCompanyScale(String string) {
		this.companyScale = string;
	}	

	/**
	 * 职位	 * @return String
	 * @hibernate.property column="position" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getPosition() {
		return this.position;
	}
	
	/**
	 * Set the position
	 */	
	public void setPosition(String aValue) {
		this.position = aValue;
	}	

	/**
	 * 工作时间	 * @return String
	 * @hibernate.property column="workTime" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getWorkTime() {
		return this.workTime;
	}
	
	/**
	 * Set the workTime
	 */	
	public void setWorkTime(String aValue) {
		this.workTime = aValue;
	}	

	/**
	 * 工作城市	 * @return String
	 * @hibernate.property column="workCity" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getWorkCity() {
		return this.workCity;
	}
	
	/**
	 * Set the workCity
	 */	
	public void setWorkCity(String aValue) {
		this.workCity = aValue;
	}	

	/**
	 * 房产 0 有 1无	 * @return Integer
	 * @hibernate.property column="houseProperty" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getHouseProperty() {
		return this.houseProperty;
	}
	
	/**
	 * Set the houseProperty
	 */	
	public void setHouseProperty(Integer aValue) {
		this.houseProperty = aValue;
	}	

	/**
	 * 房贷 0 有 1无	 * @return Integer
	 * @hibernate.property column="houseLoan" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getHouseLoan() {
		return this.houseLoan;
	}
	
	/**
	 * Set the houseLoan
	 */	
	public void setHouseLoan(Integer aValue) {
		this.houseLoan = aValue;
	}	

	/**
	 * 车产 0 有 1无	 * @return Integer
	 * @hibernate.property column="vehicleProperty" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getVehicleProperty() {
		return this.vehicleProperty;
	}
	
	/**
	 * Set the vehicleProperty
	 */	
	public void setVehicleProperty(Integer aValue) {
		this.vehicleProperty = aValue;
	}	

	/**
	 * 车贷 0 有 1无	 * @return Integer
	 * @hibernate.property column="vehicleLoan" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getVehicleLoan() {
		return this.vehicleLoan;
	}
	
	/**
	 * Set the vehicleLoan
	 */	
	public void setVehicleLoan(Integer aValue) {
		this.vehicleLoan = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof BpPersionDirPro)) {
			return false;
		}
		BpPersionDirPro rhs = (BpPersionDirPro) object;
		return new EqualsBuilder()
				.append(this.pdirProId, rhs.pdirProId)
				.append(this.proId, rhs.proId)
				.append(this.businessType, rhs.businessType)
				.append(this.persionId, rhs.persionId)
				.append(this.persionName, rhs.persionName)
				.append(this.proName, rhs.proName)
				.append(this.proNumber, rhs.proNumber)
				.append(this.yearInterestRate, rhs.yearInterestRate)
				.append(this.monthInterestRate, rhs.monthInterestRate)
				.append(this.dayInterestRate, rhs.dayInterestRate)
				.append(this.totalInterestRate, rhs.totalInterestRate)
				.append(this.interestPeriod, rhs.interestPeriod)
				.append(this.payIntersetWay, rhs.payIntersetWay)
				.append(this.bidMoney, rhs.bidMoney)
				.append(this.loanLife, rhs.loanLife)
				.append(this.bidTime, rhs.bidTime)
				.append(this.createTime, rhs.createTime)
				.append(this.updateTime, rhs.updateTime)
				.append(this.keepStat, rhs.keepStat)
				.append(this.schemeStat, rhs.schemeStat)
				.append(this.sex, rhs.sex)
				.append(this.age, rhs.age)
				.append(this.education, rhs.education)
				.append(this.marriage, rhs.marriage)
				.append(this.userName, rhs.userName)
				.append(this.monthIncome, rhs.monthIncome)
				.append(this.address, rhs.address)
				.append(this.companyIndustry, rhs.companyIndustry)
				.append(this.companyScale, rhs.companyScale)
				.append(this.position, rhs.position)
				.append(this.workTime, rhs.workTime)
				.append(this.workCity, rhs.workCity)
				.append(this.houseProperty, rhs.houseProperty)
				.append(this.houseLoan, rhs.houseLoan)
				.append(this.vehicleProperty, rhs.vehicleProperty)
				.append(this.vehicleLoan, rhs.vehicleLoan)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.pdirProId) 
				.append(this.proId) 
				.append(this.businessType) 
				.append(this.persionId) 
				.append(this.persionName) 
				.append(this.proName) 
				.append(this.proNumber) 
				.append(this.yearInterestRate) 
				.append(this.monthInterestRate) 
				.append(this.dayInterestRate) 
				.append(this.totalInterestRate) 
				.append(this.interestPeriod) 
				.append(this.payIntersetWay) 
				.append(this.bidMoney) 
				.append(this.loanLife) 
				.append(this.bidTime) 
				.append(this.createTime) 
				.append(this.updateTime) 
				.append(this.keepStat) 
				.append(this.schemeStat) 
				.append(this.sex) 
				.append(this.age) 
				.append(this.education) 
				.append(this.marriage) 
				.append(this.userName) 
				.append(this.monthIncome) 
				.append(this.address) 
				.append(this.companyIndustry) 
				.append(this.companyScale) 
				.append(this.position) 
				.append(this.workTime) 
				.append(this.workCity) 
				.append(this.houseProperty) 
				.append(this.houseLoan) 
				.append(this.vehicleProperty) 
				.append(this.vehicleLoan) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("pdirProId", this.pdirProId) 
				.append("proId", this.proId) 
				.append("businessType", this.businessType) 
				.append("persionId", this.persionId) 
				.append("persionName", this.persionName) 
				.append("proName", this.proName) 
				.append("proNumber", this.proNumber) 
				.append("yearInterestRate", this.yearInterestRate) 
				.append("monthInterestRate", this.monthInterestRate) 
				.append("dayInterestRate", this.dayInterestRate) 
				.append("totalInterestRate", this.totalInterestRate) 
				.append("interestPeriod", this.interestPeriod) 
				.append("payIntersetWay", this.payIntersetWay) 
				.append("bidMoney", this.bidMoney) 
				.append("loanLife", this.loanLife) 
				.append("bidTime", this.bidTime) 
				.append("createTime", this.createTime) 
				.append("updateTime", this.updateTime) 
				.append("keepStat", this.keepStat) 
				.append("schemeStat", this.schemeStat) 
				.append("sex", this.sex) 
				.append("age", this.age) 
				.append("education", this.education) 
				.append("marriage", this.marriage) 
				.append("userName", this.userName) 
				.append("monthIncome", this.monthIncome) 
				.append("address", this.address) 
				.append("companyIndustry", this.companyIndustry) 
				.append("companyScale", this.companyScale) 
				.append("position", this.position) 
				.append("workTime", this.workTime) 
				.append("workCity", this.workCity) 
				.append("houseProperty", this.houseProperty) 
				.append("houseLoan", this.houseLoan) 
				.append("vehicleProperty", this.vehicleProperty) 
				.append("vehicleLoan", this.vehicleLoan) 
				.toString();
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverP2PAccountNumber(String receiverP2PAccountNumber) {
		this.receiverP2PAccountNumber = receiverP2PAccountNumber;
	}

	public String getReceiverP2PAccountNumber() {
		return receiverP2PAccountNumber;
	}

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getResource() {
		return resource;
	}

	public java.util.Date getDisclosureCreateDate() {
		return disclosureCreateDate;
	}

	public void setDisclosureCreateDate(java.util.Date disclosureCreateDate) {
		this.disclosureCreateDate = disclosureCreateDate;
	}

	public java.util.Date getDisclosureUpdateDate() {
		return disclosureUpdateDate;
	}

	public void setDisclosureUpdateDate(java.util.Date disclosureUpdateDate) {
		this.disclosureUpdateDate = disclosureUpdateDate;
	}

	public String getMoneyUseSituation() {
		return moneyUseSituation;
	}

	public void setMoneyUseSituation(String moneyUseSituation) {
		this.moneyUseSituation = moneyUseSituation;
	}

	public String getManagementSituation() {
		return managementSituation;
	}

	public void setManagementSituation(String managementSituation) {
		this.managementSituation = managementSituation;
	}

	public String getRepaymentChangeSituation() {
		return repaymentChangeSituation;
	}

	public void setRepaymentChangeSituation(String repaymentChangeSituation) {
		this.repaymentChangeSituation = repaymentChangeSituation;
	}

}
