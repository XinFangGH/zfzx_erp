package com.zhiwei.credit.model.creditFlow.finance;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;



/**
 * 
 * @author 
 *
 */
/**
 * SlFundIntent Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 款项计划信息表
 */
public class SlFundIntent extends com.zhiwei.core.model.BaseModel {

    protected Long fundIntentId;
	protected String fundType; //资金类型
	protected Short isFlat;  //是否平账
	protected java.util.Date intentDate;
	protected java.math.BigDecimal payMoney;
	protected java.math.BigDecimal incomeMoney;
	protected java.util.Date factDate;
	protected java.math.BigDecimal afterMoney;//已对账金额
	protected java.math.BigDecimal notMoney;  //未对账金额
	protected java.math.BigDecimal flatMoney; //平账金额
	protected Short isValid;    //0表示有效，1表示被取消，2表示展期的但还没被审批的3，变更利率还没被审批，4提前还款没被审批
	protected Short isCheck;  //0表示有效可以对帐 null或1表示不可以对账，3表示终止
    protected Short isChargeCertificate; //0表示已收取，1表示已退还,
    protected String isOverdue;    
    protected Long slSuperviseRecordId;  //展期id
    protected Long slEarlyRepaymentId;  //提前还款id
    protected Long slAlteraccrualRecordId;//利率变更id
    protected Long projectId;
	protected String businessType;
	protected String projectName;
	protected String projectNumber;
	protected java.math.BigDecimal punishAccrual ; //罚息利率
	protected Long isInitialorId;  //null 原始利息，数字为罚息，并且此数字是原始利息的Id
	protected Integer punishDays; //罚息天数
	protected java.math.BigDecimal accrualMoney;  //罚息金额
	protected String remark;
	protected Long lastslFundintentUrgeId;  //最后催收时间
	private Set<SlFundDetail> slFundDetails = new HashSet<SlFundDetail>(
			0);
	protected String remark1;//终止说明
	protected Long startUserId;//启动人
//	protected com.zhiwei.credit.model.project.SlSmallloanProject slSmallloanProject;
	

	protected java.math.BigDecimal overdueRate;//逾期罚息利率
	protected String fundTypeName;
	protected java.math.BigDecimal payInMoney;
	protected String status;
	protected String oppositeName;
	protected String opposittelephone;
	protected Date lastslFundintentUrgeTime;
	protected String contractName;
	protected Date projectStartDate;
	protected String tabType;  //应收，欠收，实收，已放
	protected BigDecimal faxiAfterMoney; //罚息已还金额
	protected String orgName;
	protected String startUserName;//启动人
	//add by liny 2013-1-4
	protected String  isAddByHand;// 用来标记是手动放款生成的本金放贷的记录  还是发送财务系统后生成的本金放款的记录
	//add by liuy 2013-02-04
	protected BigDecimal overdueRateLoan;//逾期贷款利率
	
	protected Integer payintentPeriod;
	
	protected Integer continueDates ;//顺延天数
	protected String continueDay ;//顺延至 几号
	protected String graceDay ;//宽限至 几号

	protected BigDecimal amountPayable ; //罚息金额 =罚息总额（accrualMoney）-顺延息（continueInterest）
	
	protected BigDecimal continueInterest ; //顺延息
	protected Date interestStarTime;//计息开始日
	protected Date interestEndTime;//计息截止日
	protected Long operateId;//用于展期删除  记录展期办理是产生影响的款项记录
	protected Short projectStatus;//用于记录操作之前项目的状态
	protected Long earlyOperateId;//用于提前还款删除   记录提前还款办理时产生影响的记录
	protected Long alteraccrualOperateId;//用于利率变更删除  记录利率变更办理时产生影响的记录
	
	protected Long investPersonId;//债权转让系统中债权投资人id
	protected String investPersonName;//债权转让中投资人姓名
	protected Long obligationId;//债权转让系统中债权产品表id
	protected Long obligationInfoId;//债权转让系统中债权购买表的id
	protected Long systemAccountId;//平台账户的id
	protected String accountNumber;//平台账户账号
	protected Long accountDealInfoId;//系统平台账户交易详细id
	
	protected String fundResource; //资金来源  add by zcb 2014--2-18 （自有：0代表小贷  1代表典当  ，平台:2）
	protected Long preceptId;//方案Id
	protected Long bidPlanId;//方案拆分后，各个标的id
	
	protected Short isLegal;//null||0  未提交法务部处理-----1，已提交法务部处理，2，法务部已处理
	
	protected Short isUrge;//是否启动还款催收流程；空或0：未启动，1：已启动
	protected String ids;
	protected Date factDay;//实际到账日
	protected java.math.BigDecimal principalRepayment;//本金
	protected java.math.BigDecimal loanInterest;//利息
	protected java.math.BigDecimal consultationMoney;//管理咨询费
	protected java.math.BigDecimal serviceMoney;//财务服务费
	protected java.math.BigDecimal synthesizeMoney;//综合费用
	protected java.math.BigDecimal synthesizeAfterMoney;//已对账金额
	protected java.math.BigDecimal punishMoney;//总罚息
	protected java.math.BigDecimal notSynthesizeMoney;//未对账合计金额
	protected BigDecimal sumMoney;//合计金额
	protected String oppositeType;
	protected Long oppositeID;
	
	//不与数据库映射字段
	protected BigDecimal fxnotMoney;//罚息未对账金额
	protected BigDecimal sumFlatMoney;//平账金额
	
	public String getOppositeType() {
		return oppositeType;
	}

	public void setOppositeType(String oppositeType) {
		this.oppositeType = oppositeType;
	}


	public Long getOppositeID() {
		return oppositeID;
	}

	public void setOppositeID(Long oppositeID) {
		this.oppositeID = oppositeID;
	}

	public BigDecimal getSumMoney() {
		return sumMoney;
	}

	public void setSumMoney(BigDecimal sumMoney) {
		this.sumMoney = sumMoney;
	}

	public java.math.BigDecimal getNotSynthesizeMoney() {
		return notSynthesizeMoney;
	}

	public void setNotSynthesizeMoney(java.math.BigDecimal notSynthesizeMoney) {
		this.notSynthesizeMoney = notSynthesizeMoney;
	}

	public java.math.BigDecimal getPunishMoney() {
		return punishMoney;
	}

	public Date getFactDay() {
		return factDay;
	}

	public void setFactDay(Date factDay) {
		this.factDay = factDay;
	}

	public void setPunishMoney(java.math.BigDecimal punishMoney) {
		this.punishMoney = punishMoney;
	}

	public java.math.BigDecimal getSynthesizeAfterMoney() {
		return synthesizeAfterMoney;
	}

	public void setSynthesizeAfterMoney(java.math.BigDecimal synthesizeAfterMoney) {
		this.synthesizeAfterMoney = synthesizeAfterMoney;
	}

	public java.math.BigDecimal getSynthesizeMoney() {
		return synthesizeMoney;
	}

	public void setSynthesizeMoney(java.math.BigDecimal synthesizeMoney) {
		this.synthesizeMoney = synthesizeMoney;
	}

	public java.math.BigDecimal getPrincipalRepayment() {
		return principalRepayment;
	}

	public void setPrincipalRepayment(java.math.BigDecimal principalRepayment) {
		this.principalRepayment = principalRepayment;
	}

	public java.math.BigDecimal getLoanInterest() {
		return loanInterest;
	}

	public void setLoanInterest(java.math.BigDecimal loanInterest) {
		this.loanInterest = loanInterest;
	}

	public java.math.BigDecimal getConsultationMoney() {
		return consultationMoney;
	}

	public void setConsultationMoney(java.math.BigDecimal consultationMoney) {
		this.consultationMoney = consultationMoney;
	}

	public java.math.BigDecimal getServiceMoney() {
		return serviceMoney;
	}

	public void setServiceMoney(java.math.BigDecimal serviceMoney) {
		this.serviceMoney = serviceMoney;
	}

	public Long getInvestPersonId() {
		return investPersonId;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Short getIsUrge() {
		return isUrge;
	}

	public void setIsUrge(Short isUrge) {
		this.isUrge = isUrge;
	}

	public void setInvestPersonId(Long investPersonId) {
		this.investPersonId = investPersonId;
	}

	public String getInvestPersonName() {
		return investPersonName;
	}
	
	public Short getIsLegal() {
		return isLegal;
	}

	public void setIsLegal(Short isLegal) {
		this.isLegal = isLegal;
	}

	public Long getBidPlanId() {
		return bidPlanId;
	}

	public void setBidPlanId(Long bidPlanId) {
		this.bidPlanId = bidPlanId;
	}

	public void setInvestPersonName(String investpersonName) {
		this.investPersonName = investpersonName;
	}

	public Long getObligationId() {
		return obligationId;
	}

	public void setObligationId(Long obligationId) {
		this.obligationId = obligationId;
	}

	public Long getObligationInfoId() {
		return obligationInfoId;
	}

	public void setObligationInfoId(Long obligationInfoId) {
		this.obligationInfoId = obligationInfoId;
	}

	public Long getSystemAccountId() {
		return systemAccountId;
	}

	public void setSystemAccountId(Long systemAccountId) {
		this.systemAccountId = systemAccountId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Long getAccountDealInfoId() {
		return accountDealInfoId;
	}

	public void setAccountDealInfoId(Long accountDealInfoId) {
		this.accountDealInfoId = accountDealInfoId;
	}

	public Long getOperateId() {
		return operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

	public Short getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(Short projectStatus) {
		this.projectStatus = projectStatus;
	}

	public Long getEarlyOperateId() {
		return earlyOperateId;
	}

	public void setEarlyOperateId(Long earlyOperateId) {
		this.earlyOperateId = earlyOperateId;
	}

	public Long getAlteraccrualOperateId() {
		return alteraccrualOperateId;
	}

	public void setAlteraccrualOperateId(Long alteraccrualOperateId) {
		this.alteraccrualOperateId = alteraccrualOperateId;
	}

	public String getContinueDay() {
		return continueDay;
	}

	public void setContinueDay(String continueDay) {
		this.continueDay = continueDay;
	}

	public String getGraceDay() {
		return graceDay;
	}

	public void setGraceDay(String graceDay) {
		this.graceDay = graceDay;
	}



	


	public BigDecimal getContinueInterest() {
		return continueInterest;
	}

	public void setContinueInterest(BigDecimal continueInterest) {
		this.continueInterest = continueInterest;
	}

	public Integer getContinueDates() {
		return continueDates;
	}

	public void setContinueDates(Integer continueDates) {
		this.continueDates = continueDates;
	}

	public BigDecimal getAmountPayable() {
		return amountPayable;
	}

	public void setAmountPayable(BigDecimal amountPayable) {
		this.amountPayable = amountPayable;
	}
	
	public Integer getPayintentPeriod() {
		return payintentPeriod;
	}

	public void setPayintentPeriod(Integer payintentPeriod) {
		this.payintentPeriod = payintentPeriod;
	}

	public java.math.BigDecimal getOverdueRateLoan() {
		return overdueRateLoan;
	}

	public void setOverdueRateLoan(java.math.BigDecimal overdueRateLoan) {
		this.overdueRateLoan = overdueRateLoan;
	}

	public String getIsAddByHand() {
		return isAddByHand;
	}

	public void setIsAddByHand(String isAddByHand) {
		this.isAddByHand = isAddByHand;
	}

	public Long getSlSuperviseRecordId() {
		return slSuperviseRecordId;
	}

	public void setSlSuperviseRecordId(Long slSuperviseRecordId) {
		this.slSuperviseRecordId = slSuperviseRecordId;
	}

	public Date getLastslFundintentUrgeTime() {
		return lastslFundintentUrgeTime;
	}

	public void setLastslFundintentUrgeTime(Date lastslFundintentUrgeTime) {
		this.lastslFundintentUrgeTime = lastslFundintentUrgeTime;
	}

	public Long getStartUserId() {
		return startUserId;
	}

	public void setStartUserId(Long startUserId) {
		this.startUserId = startUserId;
	}

	public String getStartUserName() {
		return startUserName;
	}

	public void setStartUserName(String startUserName) {
		this.startUserName = startUserName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Integer getPunishDays() {
		return punishDays;
	}

	public void setPunishDays(Integer punishDays) {
		this.punishDays = punishDays;
	}

	public Long getIsInitialorId() {
		return isInitialorId;
	}

	public void setIsInitialorId(Long isInitialorId) {
		this.isInitialorId = isInitialorId;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public java.math.BigDecimal getPunishAccrual() {
		return punishAccrual;
	}

	public void setPunishAccrual(java.math.BigDecimal punishAccrual) {
		this.punishAccrual = punishAccrual;
	}

	public String getTabType() {
		return tabType;
	}

	public void setTabType(String tabType) {
		this.tabType = tabType;
	}

	public Long getSlAlteraccrualRecordId() {
		return slAlteraccrualRecordId;
	}

	public void setSlAlteraccrualRecordId(Long slAlteraccrualRecordId) {
		this.slAlteraccrualRecordId = slAlteraccrualRecordId;
	}

	public Short getIsChargeCertificate() {
		return isChargeCertificate;
	}

	public void setIsChargeCertificate(Short isChargeCertificate) {
		this.isChargeCertificate = isChargeCertificate;
	}

	public Date getProjectStartDate() {
		return projectStartDate;
	}

	public void setProjectStartDate(Date projectStartDate) {
		this.projectStartDate = projectStartDate;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public Short getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(Short isCheck) {
		this.isCheck = isCheck;
	}
	public String getOppositeName() {
		return oppositeName;
	}

	public void setOppositeName(String oppositeName) {
		this.oppositeName = oppositeName;
	}

	public Long getLastslFundintentUrgeId() {
		return lastslFundintentUrgeId;
	}

	public void setLastslFundintentUrgeId(Long lastslFundintentUrgeId) {
		this.lastslFundintentUrgeId = lastslFundintentUrgeId;
	}

	public String getOpposittelephone() {
		return opposittelephone;
	}

	public void setOpposittelephone(String opposittelephone) {
		this.opposittelephone = opposittelephone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Short getIsValid() {
		return isValid;
	}

	public void setIsValid(Short isValid) {
		this.isValid = isValid;
	}

	public Long getSlEarlyRepaymentId() {
		return slEarlyRepaymentId;
	}

	public void setSlEarlyRepaymentId(Long slEarlyRepaymentId) {
		this.slEarlyRepaymentId = slEarlyRepaymentId;
	}

	public String getProjectNumber() {
		return projectNumber;
	}

	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}

	public Short getIsFlat() {
		return isFlat;
	}

	public void setIsFlat(Short isFlat) {
		this.isFlat = isFlat;
	}

	public java.math.BigDecimal getFlatMoney() {
		return flatMoney;
	}

	public void setFlatMoney(java.math.BigDecimal flatMoney) {
		this.flatMoney = flatMoney;
	}

	public Set<SlFundDetail> getSlFundDetails() {
		return slFundDetails;
	}

	public void setSlFundDetails(Set<SlFundDetail> slFundDetails) {
		this.slFundDetails = slFundDetails;
	}

	public java.math.BigDecimal getPayInMoney() {
		return payInMoney;
	}

	public void setPayInMoney(java.math.BigDecimal payInMoney) {
		this.payInMoney = payInMoney;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public java.math.BigDecimal getOverdueRate() {
		return overdueRate;
	}

	public void setOverdueRate(java.math.BigDecimal overdueRate) {
		this.overdueRate = overdueRate;
	}

	public String getFundTypeName() {
		return fundTypeName;
	}

	public void setFundTypeName(String fundTypeName) {
		this.fundTypeName = fundTypeName;
	}

	/**
	 * Default Empty Constructor for class SlFundIntent
	 */
	public SlFundIntent () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class SlFundIntent
	 */
	public SlFundIntent (
		 Long in_fundIntentId
        ) {
		this.setFundIntentId(in_fundIntentId);
    }

	
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="fundIntentId" type="java.lang.Long" generator-class="native"
	 */
	public Long getFundIntentId() {
		return this.fundIntentId;
	}
	
	/**
	 * Set the fundIntentId
	 */	
	public void setFundIntentId(Long aValue) {
		this.fundIntentId = aValue;
	}	

	/**
	 * 	 * @return Long
	 */
	
	/**
	 * Set the projectId
	 */	

	/**
	 * 资金类型 (贷款：贷款贷出、贷款收息、贷款收本、贷款佣金支付)(融资：融资借入、融资付息、融资付本，融资佣金支付)	 * @return Short
	 * @hibernate.property column="fundType" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public String getFundType() {
		return this.fundType;
	}
	
	/**
	 * Set the fundType
	 * @spring.validator type="required"
	 */	
	public void setFundType(String aValue) {
		this.fundType = aValue;
	}	

	/**
	 * 计划到帐日	 * @return java.util.Date
	 * @hibernate.property column="intentDate" type="java.util.Date" length="19"  unique="false"
	 */
	public java.util.Date getIntentDate() {
		return this.intentDate;
	}
	
	/**
	 * Set the intentDate
	 * @spring.validator type="required"
	 */	
	public void setIntentDate(java.util.Date aValue) {
		this.intentDate = aValue;
	}	

	/**
	 * 支出金额	 * @return java.math.BigDecimal
	 * @hibernate.property column="payMoney" type="java.math.BigDecimal" length="10" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getPayMoney() {
		return this.payMoney;
	}
	
	/**
	 * Set the payMoney
	 */	
	public void setPayMoney(java.math.BigDecimal aValue) {
		this.payMoney = aValue;
	}	

	/**
	 * 收入金额	 * @return java.math.BigDecimal
	 * @hibernate.property column="incomeMoney" type="java.math.BigDecimal" length="10" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getIncomeMoney() {
		return this.incomeMoney;
	}
	
	/**
	 * Set the incomeMoney
	 */	
	public void setIncomeMoney(java.math.BigDecimal aValue) {
		this.incomeMoney = aValue;
	}	

	/**
	 * 实际到帐日/实际还款日	 * @return java.util.Date
	 * @hibernate.property column="factDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getFactDate() {
		return this.factDate;
	}
	
	/**
	 * Set the factDate
	 */	
	public void setFactDate(java.util.Date aValue) {
		this.factDate = aValue;
	}	

	/**
	 * 已对帐金额	 * @return java.math.BigDecimal
	 * @hibernate.property column="afterMoney" type="java.math.BigDecimal" length="10" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getAfterMoney() {
		return this.afterMoney;
	}
	
	/**
	 * Set the afterMoney
	 */	
	public void setAfterMoney(java.math.BigDecimal aValue) {
		this.afterMoney = aValue;
	}	

	/**
	 * 未对帐金额	 * @return java.math.BigDecimal
	 * @hibernate.property column="notMoney" type="java.math.BigDecimal" length="10" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getNotMoney() {
		return this.notMoney;
	}
	
	/**
	 * Set the notMoney
	 */	
	public void setNotMoney(java.math.BigDecimal aValue) {
		this.notMoney = aValue;
	}	

	/**
	 * 是否逾期	 * @return String
	 * @hibernate.property column="isOverdue" type="java.lang.String" length="10" not-null="false" unique="false"
	 */
	public String getIsOverdue() {
		return this.isOverdue;
	}
	
	/**
	 * Set the isOverdue
	 */	
	public void setIsOverdue(String aValue) {
		this.isOverdue = aValue;
	}	

	/**
	 * 逾期利息总额	 * @return java.math.BigDecimal
	 * @hibernate.property column="accrualMoney" type="java.math.BigDecimal" length="10" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getAccrualMoney() {
		return this.accrualMoney;
	}
	
	/**
	 * Set the accrualMoney
	 */	
	public void setAccrualMoney(java.math.BigDecimal aValue) {
		this.accrualMoney = aValue;
	}	

	/**
	 * 备注	 * @return String
	 * @hibernate.property column="remark" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getRemark() {
		return this.remark;
	}
	
	/**
	 * Set the remark
	 */	
	public void setRemark(String aValue) {
		this.remark = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SlFundIntent)) {
			return false;
		}
		SlFundIntent rhs = (SlFundIntent) object;
		return new EqualsBuilder()
				.append(this.fundIntentId, rhs.fundIntentId)
						.append(this.fundType, rhs.fundType)
				.append(this.intentDate, rhs.intentDate)
				.append(this.payMoney, rhs.payMoney)
				.append(this.incomeMoney, rhs.incomeMoney)
				.append(this.factDate, rhs.factDate)
				.append(this.afterMoney, rhs.afterMoney)
				.append(this.notMoney, rhs.notMoney)
				.append(this.isOverdue, rhs.isOverdue)
				.append(this.accrualMoney, rhs.accrualMoney)
				.append(this.remark, rhs.remark)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.fundIntentId) 
						.append(this.fundType) 
				.append(this.intentDate) 
				.append(this.payMoney) 
				.append(this.incomeMoney) 
				.append(this.factDate) 
				.append(this.afterMoney) 
				.append(this.notMoney) 
				.append(this.isOverdue) 
				.append(this.accrualMoney) 
				.append(this.remark) 
				.toHashCode();
	}
	
	

	public BigDecimal getFaxiAfterMoney() {
		return faxiAfterMoney;
	}

	public void setFaxiAfterMoney(BigDecimal faxiAfterMoney) {
		this.faxiAfterMoney = faxiAfterMoney;
	}

	

	public Date getInterestStarTime() {
		return interestStarTime;
	}

	public void setInterestStarTime(Date interestStarTime) {
		this.interestStarTime = interestStarTime;
	}

	

	public Date getInterestEndTime() {
		return interestEndTime;
	}

	public void setInterestEndTime(Date interestEndTime) {
		this.interestEndTime = interestEndTime;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("fundIntentId", this.fundIntentId) 
						.append("fundType", this.fundType) 
				.append("intentDate", this.intentDate) 
				.append("payMoney", this.payMoney) 
				.append("incomeMoney", this.incomeMoney) 
				.append("factDate", this.factDate) 
				.append("afterMoney", this.afterMoney) 
				.append("notMoney", this.notMoney) 
				.append("isOverdue", this.isOverdue) 
				.append("accrualMoney", this.accrualMoney) 
				.append("remark", this.remark) 
				.toString();
	}

	public String getFundResource() {
		return fundResource;
	}

	public void setFundResource(String fundResource) {
		this.fundResource = fundResource;
	}

	public Long getPreceptId() {
		return preceptId;
	}

	public void setPreceptId(Long preceptId) {
		this.preceptId = preceptId;
	}

	public BigDecimal getFxnotMoney() {
		return fxnotMoney;
	}

	public void setFxnotMoney(BigDecimal fxnotMoney) {
		this.fxnotMoney = fxnotMoney;
	}

	public BigDecimal getSumFlatMoney() {
		return sumFlatMoney;
	}

	public void setSumFlatMoney(BigDecimal sumFlatMoney) {
		this.sumFlatMoney = sumFlatMoney;
	}



}