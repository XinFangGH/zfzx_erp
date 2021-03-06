package com.zhiwei.credit.model.creditFlow.financingAgency.business;
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
 * BpBusinessOrPro Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 企业债权标项目缓存表
 */
public class BpBusinessOrPro extends com.zhiwei.core.model.BaseModel {
	@Expose
    protected Long borProId;
	@Expose
	protected Long proId;
	@Expose
	protected Long moneyPlanId; //资金方案Id
	@Expose
	protected Double planMoneyScale; //平台收费比率
	@Expose
	protected String businessType;
	@Expose
	protected Long businessId;
	@Expose
	protected String businessName;
	@Expose
	protected String proName;
	@Expose
	protected String proNumber;
	@Expose
	protected java.math.BigDecimal yearInterestRate;
	@Expose
	protected java.math.BigDecimal monthInterestRate;
	@Expose
	protected java.math.BigDecimal dayInterestRate;
	@Expose
	protected java.math.BigDecimal totalInterestRate;
	@Expose
	protected String interestPeriod;
	@Expose
	protected String payIntersetWay;
	@Expose
	protected java.math.BigDecimal bidMoney;
	@Expose
	protected String payAcctualType;
	@Expose
	protected Integer custDate; //自定义天数 如果该值不为空 说明还款周期为 自定义周期 已天为单位
	/**
	 * orginalType  债权类型
	 * 1 表示 线上债权招标项目
	 * 10表示理财产品债权库项目
	 * 20表示理财计划债权库项目
	 * 其余状态后续功能添加
	 */
	@Expose
	private Short orginalType;//债权类型
	@Expose
	private String  reciverType;//表示收款人来源（合作机构个人，企业）
	@Expose
	private Long reciverId;//表示收款人Id（合作机构个人表，企业表）
	@Expose
	private String receiverName;//表示收款人姓名：债权标表示原始债权人姓名，直投标表示借款人姓名
	@Expose
	private String receiverP2PAccountNumber;//表示收款人P2P账号：债权标表示原始债权人P2P账号，直投标表示借款人P2P账号
	/**
	 * 以下数据不与数据库相关联，展期申请记录的ID
	 * @return
	 */
	protected Long superviseRecordId;
	/**
	 * 展期开始日期
	 * @return
	 */
	protected java.util.Date startDate;
	/**
	 * 展期结束日期
	 * @return
	 */
	protected java.util.Date endDate;
	/**
	 * 展期申请金额
	 * @return
	 */
	protected java.math.BigDecimal continuationMoney;
	
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
	
	public Long getSuperviseRecordId() {
		return superviseRecordId;
	}

	public void setSuperviseRecordId(Long superviseRecordId) {
		this.superviseRecordId = superviseRecordId;
	}

	public java.util.Date getStartDate() {
		return startDate;
	}

	public void setStartDate(java.util.Date startDate) {
		this.startDate = startDate;
	}

	public java.util.Date getEndDate() {
		return endDate;
	}

	public void setEndDate(java.util.Date endDate) {
		this.endDate = endDate;
	}

	public java.math.BigDecimal getContinuationMoney() {
		return continuationMoney;
	}

	public void setContinuationMoney(java.math.BigDecimal continuationMoney) {
		this.continuationMoney = continuationMoney;
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

	@Expose
	protected Integer loanLife;
	
	public Integer getLoanLife() {
		return loanLife;
	}

	public void setLoanLife(Integer loanLife) {
		this.loanLife = loanLife;
	}
	@Expose
	protected java.util.Date bidTime;
	@Expose
	protected java.util.Date createTime;
	@Expose
	protected java.util.Date updateTime;
	@Expose
	protected java.util.Date loanStarTime;
	@Expose
	protected java.util.Date loanEndTime;
	@Expose
	protected String obligatoryPersion;
	@Expose
	protected Integer keepStat;
	@Expose
	protected Integer schemeStat;
	
	@Expose
	protected java.math.BigDecimal publishOrMoney;//已发标金额
	@Expose
	protected int publishOrNum;//已发标数量
	@Expose
	protected Double rate;//已发布  占比
	@Expose
	protected java.math.BigDecimal residueMoney;//剩余金额

	 //维护记录
	protected PlBusinessDirProKeep plBusinessDirProKeep;
	
	public PlBusinessDirProKeep getPlBusinessDirProKeep() {
		return plBusinessDirProKeep;
	}

	public void setPlBusinessDirProKeep(PlBusinessDirProKeep plBusinessDirProKeep) {
		this.plBusinessDirProKeep = plBusinessDirProKeep;
	}
	protected java.util.Set plBidPlans = new java.util.HashSet();
	protected java.util.Set plBusinessDirProKeeps = new java.util.HashSet();

	/**
	 * Default Empty Constructor for class BpBusinessOrPro
	 */
	public BpBusinessOrPro () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class BpBusinessOrPro
	 */
	public BpBusinessOrPro (
		 Long in_borProId
        ) {
		this.setBorProId(in_borProId);
    }
	public Double getPlanMoneyScale() {
		return planMoneyScale;
	}

	public void setPlanMoneyScale(Double planMoneyScale) {
		this.planMoneyScale = planMoneyScale;
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

	public Long getMoneyPlanId() {
		return moneyPlanId;
	}

	public void setMoneyPlanId(Long moneyPlanId) {
		this.moneyPlanId = moneyPlanId;
	}
	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public java.math.BigDecimal getResidueMoney() {
		return residueMoney;
	}

	public void setResidueMoney(java.math.BigDecimal residueMoney) {
		this.residueMoney = residueMoney;
	}

	public java.util.Set getPlBidPlans () {
		return plBidPlans;
	}	
	
	public void setPlBidPlans (java.util.Set in_plBidPlans) {
		this.plBidPlans = in_plBidPlans;
	}

	public java.util.Set getPlBusinessDirProKeeps () {
		return plBusinessDirProKeeps;
	}	
	
	public void setPlBusinessDirProKeeps (java.util.Set in_plBusinessDirProKeeps) {
		this.plBusinessDirProKeeps = in_plBusinessDirProKeeps;
	}
    

	/**
	 * orProjId	 * @return Long
     * @hibernate.id column="borProId" type="java.lang.Long" generator-class="native"
	 */
	public Long getBorProId() {
		return this.borProId;
	}
	
	/**
	 * Set the borProId
	 */	
	public void setBorProId(Long aValue) {
		this.borProId = aValue;
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
	 * 企业id	 * @return Long
	 * @hibernate.property column="businessId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getBusinessId() {
		return this.businessId;
	}
	
	/**
	 * Set the businessId
	 */	
	public void setBusinessId(Long aValue) {
		this.businessId = aValue;
	}	

	/**
	 * 借款企业名称	 * @return String
	 * @hibernate.property column="businessName" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getBusinessName() {
		return this.businessName;
	}
	
	/**
	 * Set the businessName
	 */	
	public void setBusinessName(String aValue) {
		this.businessName = aValue;
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
	 * 招标金额/第一次当前可转让金额等于招标金额	 * @return java.math.BigDecimal
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
	 * 发标日期	 * @return java.util.Date
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
	 * 借款开始日	 * @return java.util.Date
	 * @hibernate.property column="loanStarTime" type="java.util.Date" length="10" not-null="false" unique="false"
	 */
	public java.util.Date getLoanStarTime() {
		return this.loanStarTime;
	}
	
	/**
	 * Set the loanStarTime
	 */	
	public void setLoanStarTime(java.util.Date aValue) {
		this.loanStarTime = aValue;
	}	

	/**
	 * 借款到期日	 * @return java.util.Date
	 * @hibernate.property column="loanEndTime" type="java.util.Date" length="10" not-null="false" unique="false"
	 */
	public java.util.Date getLoanEndTime() {
		return this.loanEndTime;
	}
	
	/**
	 * Set the loanEndTime
	 */	
	public void setLoanEndTime(java.util.Date aValue) {
		this.loanEndTime = aValue;
	}	

	/**
	 * 当前债权持有人（保存名称）	 * @return String
	 * @hibernate.property column="obligatoryPersion" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	public String getObligatoryPersion() {
		return this.obligatoryPersion;
	}
	
	/**
	 * Set the obligatoryPersion
	 */	
	public void setObligatoryPersion(String aValue) {
		this.obligatoryPersion = aValue;
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
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof BpBusinessOrPro)) {
			return false;
		}
		BpBusinessOrPro rhs = (BpBusinessOrPro) object;
		return new EqualsBuilder()
				.append(this.borProId, rhs.borProId)
				.append(this.proId, rhs.proId)
				.append(this.businessType, rhs.businessType)
				.append(this.businessId, rhs.businessId)
				.append(this.businessName, rhs.businessName)
				.append(this.proName, rhs.proName)
				.append(this.proNumber, rhs.proNumber)
				.append(this.yearInterestRate, rhs.yearInterestRate)
				.append(this.monthInterestRate, rhs.monthInterestRate)
				.append(this.dayInterestRate, rhs.dayInterestRate)
				.append(this.totalInterestRate, rhs.totalInterestRate)
				.append(this.interestPeriod, rhs.interestPeriod)
				.append(this.payIntersetWay, rhs.payIntersetWay)
				.append(this.bidMoney, rhs.bidMoney)
				.append(this.bidTime, rhs.bidTime)
				.append(this.createTime, rhs.createTime)
				.append(this.updateTime, rhs.updateTime)
				.append(this.loanStarTime, rhs.loanStarTime)
				.append(this.loanEndTime, rhs.loanEndTime)
				.append(this.obligatoryPersion, rhs.obligatoryPersion)
				.append(this.keepStat, rhs.keepStat)
				.append(this.schemeStat, rhs.schemeStat)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.borProId) 
				.append(this.proId) 
				.append(this.businessType) 
				.append(this.businessId) 
				.append(this.businessName) 
				.append(this.proName) 
				.append(this.proNumber) 
				.append(this.yearInterestRate) 
				.append(this.monthInterestRate) 
				.append(this.dayInterestRate) 
				.append(this.totalInterestRate) 
				.append(this.interestPeriod) 
				.append(this.payIntersetWay) 
				.append(this.bidMoney) 
				.append(this.bidTime) 
				.append(this.createTime) 
				.append(this.updateTime) 
				.append(this.loanStarTime) 
				.append(this.loanEndTime) 
				.append(this.obligatoryPersion) 
				.append(this.keepStat) 
				.append(this.schemeStat) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("borProId", this.borProId) 
				.append("proId", this.proId) 
				.append("businessType", this.businessType) 
				.append("businessId", this.businessId) 
				.append("businessName", this.businessName) 
				.append("proName", this.proName) 
				.append("proNumber", this.proNumber) 
				.append("yearInterestRate", this.yearInterestRate) 
				.append("monthInterestRate", this.monthInterestRate) 
				.append("dayInterestRate", this.dayInterestRate) 
				.append("totalInterestRate", this.totalInterestRate) 
				.append("interestPeriod", this.interestPeriod) 
				.append("payIntersetWay", this.payIntersetWay) 
				.append("bidMoney", this.bidMoney) 
				.append("bidTime", this.bidTime) 
				.append("createTime", this.createTime) 
				.append("updateTime", this.updateTime) 
				.append("loanStarTime", this.loanStarTime) 
				.append("loanEndTime", this.loanEndTime) 
				.append("obligatoryPersion", this.obligatoryPersion) 
				.append("keepStat", this.keepStat) 
				.append("schemeStat", this.schemeStat) 
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

	public void setOrginalType(Short orginalType) {
		this.orginalType = orginalType;
	}

	public Short getOrginalType() {
		return orginalType;
	}

	public void setReciverType(String reciverType) {
		this.reciverType = reciverType;
	}

	public String getReciverType() {
		return reciverType;
	}

	public void setReciverId(Long reciverId) {
		this.reciverId = reciverId;
	}

	public Long getReciverId() {
		return reciverId;
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
