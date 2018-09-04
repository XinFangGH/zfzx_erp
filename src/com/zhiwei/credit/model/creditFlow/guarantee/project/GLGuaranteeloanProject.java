package com.zhiwei.credit.model.creditFlow.guarantee.project;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @author 申德轩
 *  
 */
public class GLGuaranteeloanProject extends com.zhiwei.core.model.BaseProject
{

	private static final long serialVersionUID = 1L;
	
	protected Long projectId;   //主键ID
	 
	protected String projectName;  //项目名称
	
	protected String businessType;//  业务类别
	
	protected String projectNumber;  //项目编号  自动生成
	
	protected String operationType; //业务品种
	
	protected String flowType; //流程(项目)类别
	
	protected String mineType ; //我方主体类型
	
	protected Long mineId; // 我方主体ID 
	
	protected String oppositeType; //对方主体类型
	
	protected Long oppositeID; //对方主体ID
	
	protected Long purposeType; //贷款用途

    protected Long creditType;  //信贷种类
    
    protected BigDecimal projectMoney; //项目金额
    
    protected BigDecimal  loanRate; // 贷款利率  
    
    protected Long currencyType;//币种
    
    protected Long loanType; //担保方式
    
    protected Date acceptDate; //承接日期
    
    protected Date intentDate; //截至日期
    
    protected Integer  dueTime; // 期限
    
    protected String  reimbursementSource;// 还款资金来源
    
    protected Long premiumRepayType;// 保费偿还方式
    
    protected Long premiumcollectType;// 保费收取方式
    
    protected BigDecimal overdueRate; //逾期费率
    
    protected BigDecimal standoverRate; //展期费率
    
    protected BigDecimal  earnestmoney; //保证金金额
    
    protected Long  earnestmoneyType; // 保证金 收取方式
    
    protected BigDecimal deditRate;//违约金比例
    
    protected BigDecimal  bankEarnestmoneyScale;// 向银行支付的保证金比例
    
    protected BigDecimal  customerEarnestmoneyScale;// 向客户收取的保证金比例
    
    protected Integer bankId; //银行ID
    
    protected Short projectStatus; //项目状态 0-保前,1-保中
    
    protected Date createDate;

	protected String mineName;
	
	protected Date endDate; //项目结束时间
	
	protected String appUserIdOfA; //业务A角
	
	protected String appUserIdOfB; //业务B角
	
	protected String appUserName;
	
	protected BigDecimal premiumRate;// 保费费率
	
	protected Long  projectSource;// 项目来源
	
    protected Long repayType; //还贷方式  add by gaoqingrui
	


	/*
	 * projectStatus=0 保前
	 * bmStatus=0:保前-进行中
	 * bmStatus=10:保前-已挂起(与多个不同的项目表以及和任务相关的表状态一致,都为10,避免不同的地方是不同的值,而本身所代表的意思一样。)
	 * bmStatus=3:保前-已终止
	 * 
	 * projectStatus=1 保中
	 * bmStatus=0:保中-进行中
	 * bmStatus=1:保中-违约处理
	 * bmStatus=2:保中-已完成
	 * bmStatus=3:保中-已终止
	 */
	protected Short bmStatus;  //保前保中项目状态
  
	protected java.math.BigDecimal paypremiumMoney;//
	protected java.math.BigDecimal paycustomerEarnestMoney;//
	protected java.math.BigDecimal flatpremiumMoney; //
	protected java.math.BigDecimal flatcustomerEarnestMoney; //
	
	protected java.math.BigDecimal paychargeMoney;//杂项费用支出
	protected java.math.BigDecimal incomechargeMoney; //杂项费用收入
	protected java.math.BigDecimal payincomechargeMoney;//已还金额(杂项收入)
	protected java.math.BigDecimal paypaychargeMoney;//杂项费用支出
	protected java.math.BigDecimal flatincomechargeMoney; //平帐金额
	protected java.math.BigDecimal flatpaychargeMoney; //平帐金额
	public BigDecimal getDeditRate() {
		return deditRate;
	}

	public void setDeditRate(BigDecimal deditRate) {
		this.deditRate = deditRate;
	}

	public Long getPremiumcollectType() {
		return premiumcollectType;
	}

	public void setPremiumcollectType(Long premiumcollectType) {
		this.premiumcollectType = premiumcollectType;
	}

	public Date getIntentDate() {
		return intentDate;
	}

	public void setIntentDate(Date intentDate) {
		this.intentDate = intentDate;
	}

	public String getAppUserName() {
		return appUserName;
	}

	public void setAppUserName(String appUserName) {
		this.appUserName = appUserName;
	}

	public Long getProjectSource() {
		return projectSource;
	}

	public void setProjectSource(Long projectSource) {
		this.projectSource = projectSource;
	}

	public BigDecimal getPremiumRate() {
		return premiumRate;
	}

	public void setPremiumRate(BigDecimal premiumRate) {
		this.premiumRate = premiumRate;
	}

	public String getAppUserIdOfA() {
		return appUserIdOfA;
	}

	public void setAppUserIdOfA(String appUserIdOfA) {
		this.appUserIdOfA = appUserIdOfA;
	}

	public String getAppUserIdOfB() {
		return appUserIdOfB;
	}

	public void setAppUserIdOfB(String appUserIdOfB) {
		this.appUserIdOfB = appUserIdOfB;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
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

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getFlowType() {
		return flowType;
	}

	public void setFlowType(String flowType) {
		this.flowType = flowType;
	}


	public Long getMineId() {
		return mineId;
	}

	public void setMineId(Long mineId) {
		this.mineId = mineId;
	}

	public String getMineType() {
		return mineType;
	}

	public void setMineType(String mineType) {
		this.mineType = mineType;
	}

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

	public Long getPurposeType() {
		return purposeType;
	}

	public void setPurposeType(Long purposeType) {
		this.purposeType = purposeType;
	}

	public Long getCreditType() {
		return creditType;
	}

	public void setCreditType(Long creditType) {
		this.creditType = creditType;
	}

	public BigDecimal getProjectMoney() {
		return projectMoney;
	}

	public void setProjectMoney(BigDecimal projectMoney) {
		this.projectMoney = projectMoney;
	}

	public BigDecimal getLoanRate() {
		return loanRate;
	}

	public void setLoanRate(BigDecimal loanRate) {
		this.loanRate = loanRate;
	}

	public Long getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(Long currencyType) {
		this.currencyType = currencyType;
	}

	public Long getLoanType() {
		return loanType;
	}

	public void setLoanType(Long loanType) {
		this.loanType = loanType;
	}

	public Date getAcceptDate() {
		return acceptDate;
	}

	public void setAcceptDate(Date acceptDate) {
		this.acceptDate = acceptDate;
	}

	public Integer getDueTime() {
		return dueTime;
	}

	public void setDueTime(Integer dueTime) {
		this.dueTime = dueTime;
	}

	public String getReimbursementSource() {
		return reimbursementSource;
	}

	public void setReimbursementSource(String reimbursementSource) {
		this.reimbursementSource = reimbursementSource;
	}

	public Long getPremiumRepayType() {
		return premiumRepayType;
	}

	public void setPremiumRepayType(Long premiumRepayType) {
		this.premiumRepayType = premiumRepayType;
	}

	public BigDecimal getOverdueRate() {
		return overdueRate;
	}

	public void setOverdueRate(BigDecimal overdueRate) {
		this.overdueRate = overdueRate;
	}

	public BigDecimal getStandoverRate() {
		return standoverRate;
	}

	public void setStandoverRate(BigDecimal standoverRate) {
		this.standoverRate = standoverRate;
	}

	public Long getEarnestmoneyType() {
		return earnestmoneyType;
	}

	public void setEarnestmoneyType(Long earnestmoneyType) {
		this.earnestmoneyType = earnestmoneyType;
	}

	public BigDecimal getBankEarnestmoneyScale() {
		return bankEarnestmoneyScale;
	}

	public void setBankEarnestmoneyScale(BigDecimal bankEarnestmoneyScale) {
		this.bankEarnestmoneyScale = bankEarnestmoneyScale;
	}

	public BigDecimal getCustomerEarnestmoneyScale() {
		return customerEarnestmoneyScale;
	}

	public void setCustomerEarnestmoneyScale(BigDecimal customerEarnestmoneyScale) {
		this.customerEarnestmoneyScale = customerEarnestmoneyScale;
	}

	public Integer getBankId() {
		return bankId;
	}

	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}

	public Short getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(Short projectStatus) {
		this.projectStatus = projectStatus;
	}

	public String getMineName() {
		return mineName;
	}

	public void setMineName(String mineName) {
		this.mineName = mineName;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public BigDecimal getEarnestmoney() {
		return earnestmoney;
	}

	public void setEarnestmoney(BigDecimal earnestmoney) {
		this.earnestmoney = earnestmoney;
	}

	public Short getBmStatus() {
		return bmStatus;
	}

	public void setBmStatus(Short bmStatus) {
		this.bmStatus = bmStatus;
	}

	public java.math.BigDecimal getPaypremiumMoney() {
		return paypremiumMoney;
	}

	public void setPaypremiumMoney(java.math.BigDecimal paypremiumMoney) {
		this.paypremiumMoney = paypremiumMoney;
	}

	public java.math.BigDecimal getPaycustomerEarnestMoney() {
		return paycustomerEarnestMoney;
	}

	public void setPaycustomerEarnestMoney(
			java.math.BigDecimal paycustomerEarnestMoney) {
		this.paycustomerEarnestMoney = paycustomerEarnestMoney;
	}

	public java.math.BigDecimal getFlatpremiumMoney() {
		return flatpremiumMoney;
	}

	public void setFlatpremiumMoney(java.math.BigDecimal flatpremiumMoney) {
		this.flatpremiumMoney = flatpremiumMoney;
	}

	public java.math.BigDecimal getFlatcustomerEarnestMoney() {
		return flatcustomerEarnestMoney;
	}

	public void setFlatcustomerEarnestMoney(
			java.math.BigDecimal flatcustomerEarnestMoney) {
		this.flatcustomerEarnestMoney = flatcustomerEarnestMoney;
	}

	public java.math.BigDecimal getPaychargeMoney() {
		return paychargeMoney;
	}

	public void setPaychargeMoney(java.math.BigDecimal paychargeMoney) {
		this.paychargeMoney = paychargeMoney;
	}

	public java.math.BigDecimal getIncomechargeMoney() {
		return incomechargeMoney;
	}

	public void setIncomechargeMoney(java.math.BigDecimal incomechargeMoney) {
		this.incomechargeMoney = incomechargeMoney;
	}

	public java.math.BigDecimal getPayincomechargeMoney() {
		return payincomechargeMoney;
	}

	public void setPayincomechargeMoney(java.math.BigDecimal payincomechargeMoney) {
		this.payincomechargeMoney = payincomechargeMoney;
	}

	public java.math.BigDecimal getPaypaychargeMoney() {
		return paypaychargeMoney;
	}

	public void setPaypaychargeMoney(java.math.BigDecimal paypaychargeMoney) {
		this.paypaychargeMoney = paypaychargeMoney;
	}

	public java.math.BigDecimal getFlatincomechargeMoney() {
		return flatincomechargeMoney;
	}

	public void setFlatincomechargeMoney(java.math.BigDecimal flatincomechargeMoney) {
		this.flatincomechargeMoney = flatincomechargeMoney;
	}

	public java.math.BigDecimal getFlatpaychargeMoney() {
		return flatpaychargeMoney;
	}

	public void setFlatpaychargeMoney(java.math.BigDecimal flatpaychargeMoney) {
		this.flatpaychargeMoney = flatpaychargeMoney;
	}
	
	public Long getRepayType() {
		return repayType;
	}

	public void setRepayType(Long repayType) {
		this.repayType = repayType;
	}
    

}
