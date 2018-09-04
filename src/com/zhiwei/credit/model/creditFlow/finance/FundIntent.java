package com.zhiwei.credit.model.creditFlow.finance;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.annotations.SerializedName;
import com.zhiwei.core.model.BaseModel;

public class FundIntent extends BaseModel {
	/**
	 * @author dell
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 主键id
	 */
	protected Long fundIntentId;
	/**
	 * 资金类型
	 */
	protected String fundType; //资金类型
	/**
	 * 是否平账
	 */
	protected Short isFlat;  //是否平账
	/**
	 * 计划到账日
	 */
	protected java.util.Date intentDate;
	/**
	 * 支出金额
	 */
	protected java.math.BigDecimal payMoney;
	/**
	 * 收入金额
	 */
	protected java.math.BigDecimal incomeMoney;
	/**
	 * 实际到帐日
	 */
	protected java.util.Date factDate;
	/**
	 * 已对账金额
	 */
	protected java.math.BigDecimal afterMoney;//已对账金额
	/**
	 * 未对账金额
	 */
	protected java.math.BigDecimal notMoney;  //未对账金额
	/**
	 * 平账金额
	 */
	protected java.math.BigDecimal flatMoney; //平账金额
	/**
	 * 0表示有效，1表示被取消，2表示展期的但还没被审批的3，变更利率还没被审批，4提前还款没被审批
	 */
	protected Short isValid;    //0表示有效，1表示被取消，2表示展期的但还没被审批的3，变更利率还没被审批，4提前还款没被审批
	/**
	 * 0表示有效可以对账 null或1表示不可以对账，3表示终止
	 */
	protected Short isCheck;  //0表示有效可以对账 null或1表示不可以对账，3表示终止
	/**
	 * 0表示已收取，1表示已退还,
	 */
    protected Short isChargeCertificate; //0表示已收取，1表示已退还,
	/**
	 * 是否逾期
	 */
    protected String isOverdue; 
	/**
	 * 展期id
	 */
    protected Long slSuperviseRecordId;  //展期id
	/**
	 * 提前还款id
	 */
    protected Long slEarlyRepaymentId;  //提前还款id
	/**
	 * 利率变更id
	 */
    protected Long slAlteraccrualRecordId;//利率变更id
	/**
	 * sl_smallloan_project表的projectId
	 */
    protected Long projectId;
	/**
	 * 业务种类
	 */
	protected String businessType;
	/**
	 * 项目名称
	 */
	protected String projectName;
	/**
	 * 项目编号
	 */
	protected String projectNumber;
	/**
	 * 罚息利率
	 */
	protected java.math.BigDecimal punishAccrual ; //罚息利率
	/**
	 * null 原始利息，数字为罚息，并且此数字是原始利息的Id
	 */
	protected Long isInitialorId;  //null 原始利息，数字为罚息，并且此数字是原始利息的Id
	/**
	 * 罚息天数
	 */
	protected Integer punishDays; //罚息天数
	/**
	 * 罚息金额
	 */
	protected java.math.BigDecimal accrualMoney;  //罚息金额
	/**
	 * 备注
	 */
	protected String remark;
	/**
	 * 最后催收ID
	 */
	protected Long lastslFundintentUrgeId;  //最后催收时间
	/**
	 * 终止说明
	 */
	protected String remark1;//终止说明
	/**
	 * 启动人
	 */
	protected Long startUserId;//启动人
//	protected com.zhiwei.credit.model.project.SlSmallloanProject slSmallloanProject;
	
	/**
	 * 逾期罚息利率
	 */
	protected java.math.BigDecimal overdueRate;//逾期罚息利率
	/**
	 * 资金类型名称
	 */
	protected String fundTypeName;
	/**
	 * 主键id
	 */
	protected java.math.BigDecimal payInMoney;
	/**
	 * 状态
	 */
	protected String status;
	/**
	 * 客户名称
	 */
	protected String oppositeName;
	/**
	 * 客户手机号码
	 */
	protected String opposittelephone;
	/**
	 * 最后催款时间
	 */
	protected Date lastslFundintentUrgeTime;
	protected String contractName;
	/**
	 * 项目开始时间
	 */
	protected Date projectStartDate;
	/**
	 * 应收，欠收，实收，已放
	 */
	protected String tabType;  //应收，欠收，实收，已放
	/**
	 * 罚息已还金额
	 */
	protected BigDecimal faxiAfterMoney; //罚息已还金额
	protected String orgName;
	/**
	 * 启动人
	 */
	protected String startUserName;//启动人
	/**
	 * 用来标记是手动放款生成的本金放贷的记录  还是发送财务系统后生成的本金放款的记录
	 */
	//add by liny 2013-1-4
	protected String  isAddByHand;// 用来标记是手动放款生成的本金放贷的记录  还是发送财务系统后生成的本金放款的记录
	/**
	 * 逾期贷款利率
	 */
	//add by liuy 2013-02-04
	protected BigDecimal overdueRateLoan;//逾期贷款利率
	/**
	 * 第几期
	 */
	protected Integer payintentPeriod;
	/**
	 * 顺延天数
	 */
	protected Integer continueDates ;//顺延天数
	/**
	 * 顺延至 几号
	 */
	protected String continueDay ;//顺延至 几号
	/**
	 * 宽限至 几号
	 */
	protected String graceDay ;//宽限至 几号
	/**
	 * 罚息金额 =罚息总额（accrualMoney）-顺延息（continueInterest）
	 */
	protected BigDecimal amountPayable ; //罚息金额 =罚息总额（accrualMoney）-顺延息（continueInterest）
	/**
	 * 顺延息
	 */
	protected BigDecimal continueInterest ; //顺延息
	/**
	 * 计息开始日
	 */
	protected Date InterestStarTime;//计息开始日
	/**
	 * 计息截止日
	 */
	protected Date InterestEndTime;//计息截止日
	/**
	 * 用于展期删除  记录展期办理是产生影响的款项记录
	 */
	protected Long operateId;//用于展期删除  记录展期办理是产生影响的款项记录
	/**
	 * 用于记录操作之前项目的状态
	 */
	protected Short projectStatus;//用于记录操作之前项目的状态
	/**
	 * 用于提前还款删除   记录提前还款办理时产生影响的记录
	 */
	protected Long earlyOperateId;//用于提前还款删除   记录提前还款办理时产生影响的记录
	/**
	 * 用于利率变更删除  记录利率变更办理时产生影响的记录
	 */
	protected Long alteraccrualOperateId;//用于利率变更删除  记录利率变更办理时产生影响的记录
	/**
	 * 债权转让系统中债权投资人id----investPersonInfo-中-investId
	 */
	protected Long investPersonId;//债权转让系统中债权投资人id----investPersonInfo-中-investId
	/**
	 * 债权转让中投资人姓名
	 */
	protected String investPersonName;//债权转让中投资人姓名
	/**
	 * 债权转让系统中债权产品表id
	 */
	protected Long obligationId;//债权转让系统中债权产品表id
	/**
	 * 债权转让系统中债权购买表的id
	 */
	protected Long obligationInfoId;//债权转让系统中债权购买表的id
	/**
	 * 平台账户的id
	 */
	protected Long systemAccountId;//平台账户的id
	/**
	 * 平台账户账号
	 */
	protected String accountNumber;//平台账户账号
	/**
	 * 系统平台账户交易详细id
	 */
	protected Long accountDealInfoId;//系统平台账户交易详细id
	/**
	 * 资金来源  add by zcb 2014--2-18 （自有：0代表小贷  1代表典当  ，平台:2）
	 */
	protected String fundResource; //资金来源  add by zcb 2014--2-18 （自有：0代表小贷  1代表典当  ，平台:2）
	/**
	 * bp_fund_project表的id，资金方案Id
	 */
	protected Long preceptId;//方案Id
	@SerializedName("name")
	/**
	 * 订单号
	 */
	private String orderNo;
	
	
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Long getFundIntentId() {
		return fundIntentId;
	}
	public void setFundIntentId(Long fundIntentId) {
		this.fundIntentId = fundIntentId;
	}
	public String getFundType() {
		return fundType;
	}
	public void setFundType(String fundType) {
		this.fundType = fundType;
	}
	public Short getIsFlat() {
		return isFlat;
	}
	public void setIsFlat(Short isFlat) {
		this.isFlat = isFlat;
	}
	public java.util.Date getIntentDate() {
		return intentDate;
	}
	public void setIntentDate(java.util.Date intentDate) {
		this.intentDate = intentDate;
	}
	public java.math.BigDecimal getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(java.math.BigDecimal payMoney) {
		this.payMoney = payMoney;
	}
	public java.math.BigDecimal getIncomeMoney() {
		return incomeMoney;
	}
	public void setIncomeMoney(java.math.BigDecimal incomeMoney) {
		this.incomeMoney = incomeMoney;
	}
	public java.util.Date getFactDate() {
		return factDate;
	}
	public void setFactDate(java.util.Date factDate) {
		this.factDate = factDate;
	}
	public java.math.BigDecimal getAfterMoney() {
		return afterMoney;
	}
	public void setAfterMoney(java.math.BigDecimal afterMoney) {
		this.afterMoney = afterMoney;
	}
	public java.math.BigDecimal getNotMoney() {
		return notMoney;
	}
	public void setNotMoney(java.math.BigDecimal notMoney) {
		this.notMoney = notMoney;
	}
	public java.math.BigDecimal getFlatMoney() {
		return flatMoney;
	}
	public void setFlatMoney(java.math.BigDecimal flatMoney) {
		this.flatMoney = flatMoney;
	}
	public Short getIsValid() {
		return isValid;
	}
	public void setIsValid(Short isValid) {
		this.isValid = isValid;
	}
	public Short getIsCheck() {
		return isCheck;
	}
	public void setIsCheck(Short isCheck) {
		this.isCheck = isCheck;
	}
	public Short getIsChargeCertificate() {
		return isChargeCertificate;
	}
	public void setIsChargeCertificate(Short isChargeCertificate) {
		this.isChargeCertificate = isChargeCertificate;
	}
	public String getIsOverdue() {
		return isOverdue;
	}
	public void setIsOverdue(String isOverdue) {
		this.isOverdue = isOverdue;
	}
	public Long getSlSuperviseRecordId() {
		return slSuperviseRecordId;
	}
	public void setSlSuperviseRecordId(Long slSuperviseRecordId) {
		this.slSuperviseRecordId = slSuperviseRecordId;
	}
	public Long getSlEarlyRepaymentId() {
		return slEarlyRepaymentId;
	}
	public void setSlEarlyRepaymentId(Long slEarlyRepaymentId) {
		this.slEarlyRepaymentId = slEarlyRepaymentId;
	}
	public Long getSlAlteraccrualRecordId() {
		return slAlteraccrualRecordId;
	}
	public void setSlAlteraccrualRecordId(Long slAlteraccrualRecordId) {
		this.slAlteraccrualRecordId = slAlteraccrualRecordId;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectNumber() {
		return projectNumber;
	}
	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}
	public java.math.BigDecimal getPunishAccrual() {
		return punishAccrual;
	}
	public void setPunishAccrual(java.math.BigDecimal punishAccrual) {
		this.punishAccrual = punishAccrual;
	}
	public Long getIsInitialorId() {
		return isInitialorId;
	}
	public void setIsInitialorId(Long isInitialorId) {
		this.isInitialorId = isInitialorId;
	}
	public Integer getPunishDays() {
		return punishDays;
	}
	public void setPunishDays(Integer punishDays) {
		this.punishDays = punishDays;
	}
	public java.math.BigDecimal getAccrualMoney() {
		return accrualMoney;
	}
	public void setAccrualMoney(java.math.BigDecimal accrualMoney) {
		this.accrualMoney = accrualMoney;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getLastslFundintentUrgeId() {
		return lastslFundintentUrgeId;
	}
	public void setLastslFundintentUrgeId(Long lastslFundintentUrgeId) {
		this.lastslFundintentUrgeId = lastslFundintentUrgeId;
	}
	
	public String getRemark1() {
		return remark1;
	}
	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	public Long getStartUserId() {
		return startUserId;
	}
	public void setStartUserId(Long startUserId) {
		this.startUserId = startUserId;
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
	public java.math.BigDecimal getPayInMoney() {
		return payInMoney;
	}
	public void setPayInMoney(java.math.BigDecimal payInMoney) {
		this.payInMoney = payInMoney;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOppositeName() {
		return oppositeName;
	}
	public void setOppositeName(String oppositeName) {
		this.oppositeName = oppositeName;
	}
	public String getOpposittelephone() {
		return opposittelephone;
	}
	public void setOpposittelephone(String opposittelephone) {
		this.opposittelephone = opposittelephone;
	}
	public Date getLastslFundintentUrgeTime() {
		return lastslFundintentUrgeTime;
	}
	public void setLastslFundintentUrgeTime(Date lastslFundintentUrgeTime) {
		this.lastslFundintentUrgeTime = lastslFundintentUrgeTime;
	}
	public String getContractName() {
		return contractName;
	}
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	public Date getProjectStartDate() {
		return projectStartDate;
	}
	public void setProjectStartDate(Date projectStartDate) {
		this.projectStartDate = projectStartDate;
	}
	public String getTabType() {
		return tabType;
	}
	public void setTabType(String tabType) {
		this.tabType = tabType;
	}
	public BigDecimal getFaxiAfterMoney() {
		return faxiAfterMoney;
	}
	public void setFaxiAfterMoney(BigDecimal faxiAfterMoney) {
		this.faxiAfterMoney = faxiAfterMoney;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getStartUserName() {
		return startUserName;
	}
	public void setStartUserName(String startUserName) {
		this.startUserName = startUserName;
	}
	public String getIsAddByHand() {
		return isAddByHand;
	}
	public void setIsAddByHand(String isAddByHand) {
		this.isAddByHand = isAddByHand;
	}
	public BigDecimal getOverdueRateLoan() {
		return overdueRateLoan;
	}
	public void setOverdueRateLoan(BigDecimal overdueRateLoan) {
		this.overdueRateLoan = overdueRateLoan;
	}
	public Integer getPayintentPeriod() {
		return payintentPeriod;
	}
	public void setPayintentPeriod(Integer payintentPeriod) {
		this.payintentPeriod = payintentPeriod;
	}
	public Integer getContinueDates() {
		return continueDates;
	}
	public void setContinueDates(Integer continueDates) {
		this.continueDates = continueDates;
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
	public BigDecimal getAmountPayable() {
		return amountPayable;
	}
	public void setAmountPayable(BigDecimal amountPayable) {
		this.amountPayable = amountPayable;
	}
	public BigDecimal getContinueInterest() {
		return continueInterest;
	}
	public void setContinueInterest(BigDecimal continueInterest) {
		this.continueInterest = continueInterest;
	}
	public Date getInterestStarTime() {
		return InterestStarTime;
	}
	public void setInterestStarTime(Date interestStarTime) {
		InterestStarTime = interestStarTime;
	}
	public Date getInterestEndTime() {
		return InterestEndTime;
	}
	public void setInterestEndTime(Date interestEndTime) {
		InterestEndTime = interestEndTime;
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
	public Long getInvestPersonId() {
		return investPersonId;
	}
	public void setInvestPersonId(Long investPersonId) {
		this.investPersonId = investPersonId;
	}
	public String getInvestPersonName() {
		return investPersonName;
	}
	public void setInvestPersonName(String investPersonName) {
		this.investPersonName = investPersonName;
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
	
	
}
