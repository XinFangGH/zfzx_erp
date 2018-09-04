package com.zhiwei.credit.model.creditFlow.financeProject;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

public class FlFinancingProject extends com.zhiwei.core.model.BaseModel {

	private static final long serialVersionUID = 1L;
	protected Long projectId; 
	protected String businessType;  //  业务类别
	protected String operationType;  //业务种类
	protected String flowType; //流程类别 
	protected String mineType;  //我方主体类型
	protected Long mineId;  //我方主体ID
	protected String oppositeType;  //对方主体类型  
	protected Long oppositeID;   //对方主体ID
	protected String projectName;  //项目名称
	protected String projectNumber;   //项目编号
	protected java.math.BigDecimal projectMoney;  //项目金额
	protected java.math.BigDecimal payProjectMoney;
	protected String dateMode; //日期模型
	protected java.util.Date startDate;// 贷款日期
	protected java.util.Date intentDate;//还款日期
	protected String accrualtype; //计息方式
	protected java.math.BigDecimal accrual;//贷款利率
	protected String payaccrualType;  //付息方式
	protected java.math.BigDecimal overdueRate; //逾期费率
	protected Integer isPreposePayAccrual;//是否前置付息 0否 	1 是
	protected Short projectStatus;//项目状态
	protected String appUserId;//项目经理ID
	protected String mineName;//我方主体名称  无映射   
	protected String appUsers;//项目经理名称 (临时获得)
	protected Date createDate;//项目创建时间
    protected java.math.BigDecimal managementConsultingOfRate; //管理咨询费率
    protected java.math.BigDecimal financeServiceOfRate; //财务服务费率
    protected Long currency;   //币种
	protected Long purposeType; //贷款用途
	protected java.math.BigDecimal accrualMoney; //利息总额
    protected java.math.BigDecimal incomechargeMoney; //杂项费用收入总额
    protected java.math.BigDecimal consultationMoney; //咨询费用总额
    protected java.math.BigDecimal paychargeMoney; //杂项费用支出总额
    protected java.math.BigDecimal serviceMoney; //服务费用总额
    protected java.math.BigDecimal annualNetProfit; //年化净利率
    protected Date endDate; //项目结束时间
    protected java.math.BigDecimal breachRate; //违约金比例
    protected String appUserName;//项目经理名字
    protected Integer payintentPeriod;//融资期数
    protected String isStartDatePay; //是否按还款日还款,
    protected Integer payintentPerioDate; //每期还款日,
    protected Integer dayOfEveryPeriod; //自定义周期的天数
    protected Short isAheadPay; //是否允许提前还款 0:否 1:是
	protected Integer isInterestByOneTime;//是否一次性支付利息
	protected BigDecimal yearAccrualRate;
	protected BigDecimal dayAccrualRate;
	protected BigDecimal sumAccrualRate;
	protected String projectBigMoney;//金额大写
	protected BigDecimal accrualNew;


	public void setAccrualNew(BigDecimal accrualNew) {
		this.accrualNew = accrualNew;
	}

	public BigDecimal getAccrualNew() {
		return accrualNew;
	}


	public String getProjectBigMoney() {
		return projectBigMoney;
	}

	public void setProjectBigMoney(String projectBigMoney) {
		this.projectBigMoney = projectBigMoney;
	}

	public Integer getIsInterestByOneTime() {
		return isInterestByOneTime;
	}

	public void setIsInterestByOneTime(Integer isInterestByOneTime) {
		this.isInterestByOneTime = isInterestByOneTime;
	}

	public BigDecimal getYearAccrualRate() {
		return yearAccrualRate;
	}

	public void setYearAccrualRate(BigDecimal yearAccrualRate) {
		this.yearAccrualRate = yearAccrualRate;
	}

	public BigDecimal getDayAccrualRate() {
		return dayAccrualRate;
	}

	public void setDayAccrualRate(BigDecimal dayAccrualRate) {
		this.dayAccrualRate = dayAccrualRate;
	}

	public BigDecimal getSumAccrualRate() {
		return sumAccrualRate;
	}

	public void setSumAccrualRate(BigDecimal sumAccrualRate) {
		this.sumAccrualRate = sumAccrualRate;
	}

	public java.math.BigDecimal getManagementConsultingOfRate() {
		return managementConsultingOfRate;
	}

	public void setManagementConsultingOfRate(
			java.math.BigDecimal managementConsultingOfRate) {
		this.managementConsultingOfRate = managementConsultingOfRate;
	}

	public java.math.BigDecimal getFinanceServiceOfRate() {
		return financeServiceOfRate;
	}

	public void setFinanceServiceOfRate(java.math.BigDecimal financeServiceOfRate) {
		this.financeServiceOfRate = financeServiceOfRate;
	}

	public Long getCurrency() {
		return currency;
	}

	public void setCurrency(Long currency) {
		this.currency = currency;
	}

	public Long getPurposeType() {
		return purposeType;
	}

	public void setPurposeType(Long purposeType) {
		this.purposeType = purposeType;
	}

	/**
	 * Default Empty Constructor for class FlFinancingProject
	 */
	public FlFinancingProject () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class FlFinancingProject
	 */
	public FlFinancingProject (
		 Long in_projectId
        ) {
		this.setProjectId(in_projectId);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="projectId" type="java.lang.Long" generator-class="native"
	 */
	public Long getProjectId() {
		return this.projectId;
	}
	
	/**
	 * Set the projectId
	 */	
	public void setProjectId(Long aValue) {
		this.projectId = aValue;
	}	

	/**
	 * 业务品种	 * @return String
	 * @hibernate.property column="operationType" type="java.lang.String" length="30" not-null="false" unique="false"
	 */
	public String getOperationType() {
		return this.operationType;
	}
	
	/**
	 * Set the operationType
	 */	
	public void setOperationType(String aValue) {
		this.operationType = aValue;
	}	

	/**
	 * 流程类别	 * @return String
	 * @hibernate.property column="flowType" type="java.lang.String" length="30" not-null="false" unique="false"
	 */
	public String getFlowType() {
		return this.flowType;
	}
	
	/**
	 * Set the flowType
	 */	
	public void setFlowType(String aValue) {
		this.flowType = aValue;
	}	

	/**
	 * 我方主体类型	 * @return String
	 * @hibernate.property column="mineType" type="java.lang.String" length="30" not-null="false" unique="false"
	 */
	public String getMineType() {
		return this.mineType;
	}
	
	/**
	 * Set the mineType
	 */	
	public void setMineType(String aValue) {
		this.mineType = aValue;
	}	

	/**
	 * 我方主体表id	 * @return Long
	 * @hibernate.property column="mineId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getMineId() {
		return this.mineId;
	}
	
	/**
	 * Set the mineId
	 */	
	public void setMineId(Long aValue) {
		this.mineId = aValue;
	}	

	/**
	 * 对方主体类型	 * @return String
	 * @hibernate.property column="oppositeType" type="java.lang.String" length="30" not-null="false" unique="false"
	 */
	public String getOppositeType() {
		return this.oppositeType;
	}
	
	/**
	 * Set the oppositeType
	 */	
	public void setOppositeType(String aValue) {
		this.oppositeType = aValue;
	}	

	/**
	 * 对方主体类型ID(客户表ID)	 * @return Long
	 * @hibernate.property column="oppositeID" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getOppositeID() {
		return this.oppositeID;
	}
	
	/**
	 * Set the oppositeID
	 */	
	public void setOppositeID(Long aValue) {
		this.oppositeID = aValue;
	}	

	/**
	 * 项目名称	 * @return String
	 * @hibernate.property column="projectName" type="java.lang.String" length="250" not-null="false" unique="false"
	 */
	public String getProjectName() {
		return this.projectName;
	}
	
	/**
	 * Set the projectName
	 */	
	public void setProjectName(String aValue) {
		this.projectName = aValue;
	}	

	/**
	 * 项目编号	 * @return String
	 * @hibernate.property column="projectNumber" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	public String getProjectNumber() {
		return this.projectNumber;
	}
	
	/**
	 * Set the projectNumber
	 */	
	public void setProjectNumber(String aValue) {
		this.projectNumber = aValue;
	}	

	/**
	 * 融资金额(元)	 * @return java.math.BigDecimal
	 * @hibernate.property column="projectMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getProjectMoney() {
		return this.projectMoney;
	}
	
	/**
	 * Set the projectMoney
	 */	
	public void setProjectMoney(java.math.BigDecimal aValue) {
		this.projectMoney = aValue;
	}	

	/**
	 * 计息方式	 * @return String
	 * @hibernate.property column="accrualtype" type="java.lang.String" length="30" not-null="false" unique="false"
	 */
	public String getAccrualtype() {
		return this.accrualtype;
	}
	
	/**
	 * Set the accrualType
	 */	
	public void setAccrualtype(String aValue) {
		this.accrualtype = aValue;
	}	

	/**
	 * 是否前置付息	 * @return Integer
	 * @hibernate.property column="isPreposePayAccrual" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getIsPreposePayAccrual() {
		return this.isPreposePayAccrual;
	}
	
	/**
	 * Set the isPreposePayAccrual
	 */	
	public void setIsPreposePayAccrual(Integer aValue) {
		this.isPreposePayAccrual = aValue;
	}	

	/**
	 * 付息方式	 * @return String
	 * @hibernate.property column="payaccrualType" type="java.lang.String" length="30" not-null="false" unique="false"
	 */
	public String getPayaccrualType() {
		return this.payaccrualType;
	}
	
	/**
	 * Set the payaccrualType
	 */	
	public void setPayaccrualType(String aValue) {
		this.payaccrualType = aValue;
	}	

	/**
	 * 融资起始日	 * @return java.util.Date
	 * @hibernate.property column="startDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getStartDate() {
		return this.startDate;
	}
	
	/**
	 * Set the startDate
	 */	
	public void setStartDate(java.util.Date aValue) {
		this.startDate = aValue;
	}	

	/**
	 * 融资还款日	 * @return java.util.Date
	 * @hibernate.property column="intentDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getIntentDate() {
		return this.intentDate;
	}
	
	/**
	 * Set the intentDate
	 */	
	public void setIntentDate(java.util.Date aValue) {
		this.intentDate = aValue;
	}	

	/**
	 * 日期模型	 * @return String
	 * @hibernate.property column="dateMode" type="java.lang.String" length="30" not-null="false" unique="false"
	 */
	public String getDateMode() {
		return this.dateMode;
	}
	
	/**
	 * Set the dateMode
	 */	
	public void setDateMode(String aValue) {
		this.dateMode = aValue;
	}	

	/**
	 * 融资利率(%)	 * @return java.math.BigDecimal
	 * @hibernate.property column="accrual" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getAccrual() {
		return this.accrual;
	}
	
	/**
	 * Set the accrual
	 */	
	public void setAccrual(java.math.BigDecimal aValue) {
		this.accrual = aValue;
	}	

	/**
	 * 逾期费率(%/日)	 * @return java.math.BigDecimal
	 * @hibernate.property column="overdueRate" type="java.math.BigDecimal" length="10" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getOverdueRate() {
		return this.overdueRate;
	}
	
	/**
	 * Set the overdueRate
	 */	
	public void setOverdueRate(java.math.BigDecimal aValue) {
		this.overdueRate = aValue;
	}	

	/**
	 * 融资种类KEY	 * @return String
	 * @hibernate.property column="businessType" type="java.lang.String" length="30" not-null="false" unique="false"
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
	 * 项目状态	 * @return Short
	 * @hibernate.property column="projectStatus" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getProjectStatus() {
		return this.projectStatus;
	}
	
	/**
	 * Set the projectStatus
	 */	
	public void setProjectStatus(Short aValue) {
		this.projectStatus = aValue;
	}	

	
	public String getAppUserId() {
		return appUserId;
	}

	public void setAppUserId(String appUserId) {
		this.appUserId = appUserId;
	}

	public String getMineName() {
		return mineName;
	}

	public void setMineName(String mineName) {
		this.mineName = mineName;
	}

	public String getAppUsers() {
		return appUsers;
	}

	public void setAppUsers(String appUsers) {
		this.appUsers = appUsers;
	}

	
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public java.math.BigDecimal getAccrualMoney() {
		return accrualMoney;
	}

	public void setAccrualMoney(java.math.BigDecimal accrualMoney) {
		this.accrualMoney = accrualMoney;
	}

	public java.math.BigDecimal getIncomechargeMoney() {
		return incomechargeMoney;
	}

	public void setIncomechargeMoney(java.math.BigDecimal incomechargeMoney) {
		this.incomechargeMoney = incomechargeMoney;
	}

	public java.math.BigDecimal getConsultationMoney() {
		return consultationMoney;
	}

	public void setConsultationMoney(java.math.BigDecimal consultationMoney) {
		this.consultationMoney = consultationMoney;
	}

	public java.math.BigDecimal getPaychargeMoney() {
		return paychargeMoney;
	}

	public void setPaychargeMoney(java.math.BigDecimal paychargeMoney) {
		this.paychargeMoney = paychargeMoney;
	}

	public java.math.BigDecimal getServiceMoney() {
		return serviceMoney;
	}

	public void setServiceMoney(java.math.BigDecimal serviceMoney) {
		this.serviceMoney = serviceMoney;
	}

	public java.math.BigDecimal getAnnualNetProfit() {
		return annualNetProfit;
	}

	public void setAnnualNetProfit(java.math.BigDecimal annualNetProfit) {
		this.annualNetProfit = annualNetProfit;
	}

	
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public java.math.BigDecimal getBreachRate() {
		return breachRate;
	}

	public void setBreachRate(java.math.BigDecimal breachRate) {
		this.breachRate = breachRate;
	}

	public Integer getPayintentPeriod() {
		return payintentPeriod;
	}

	public void setPayintentPeriod(Integer payintentPeriod) {
		this.payintentPeriod = payintentPeriod;
	}

	public String getIsStartDatePay() {
		return isStartDatePay;
	}

	public void setIsStartDatePay(String isStartDatePay) {
		this.isStartDatePay = isStartDatePay;
	}

	public Integer getPayintentPerioDate() {
		return payintentPerioDate;
	}

	public void setPayintentPerioDate(Integer payintentPerioDate) {
		this.payintentPerioDate = payintentPerioDate;
	}

	public Integer getDayOfEveryPeriod() {
		return dayOfEveryPeriod;
	}

	public void setDayOfEveryPeriod(Integer dayOfEveryPeriod) {
		this.dayOfEveryPeriod = dayOfEveryPeriod;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof FlFinancingProject)) {
			return false;
		}
		FlFinancingProject rhs = (FlFinancingProject) object;
		return new EqualsBuilder()
				.append(this.projectId, rhs.projectId)
				.append(this.operationType, rhs.operationType)
				.append(this.flowType, rhs.flowType)
				.append(this.mineType, rhs.mineType)
				.append(this.mineId, rhs.mineId)
				.append(this.oppositeType, rhs.oppositeType)
				.append(this.oppositeID, rhs.oppositeID)
				.append(this.projectName, rhs.projectName)
				.append(this.projectNumber, rhs.projectNumber)
				.append(this.projectMoney, rhs.projectMoney)
				.append(this.accrualtype, rhs.accrualtype)
				.append(this.isPreposePayAccrual, rhs.isPreposePayAccrual)
				.append(this.payaccrualType, rhs.payaccrualType)
				.append(this.startDate, rhs.startDate)
				.append(this.intentDate, rhs.intentDate)
				.append(this.dateMode, rhs.dateMode)
				.append(this.accrual, rhs.accrual)
				.append(this.overdueRate, rhs.overdueRate)
				.append(this.businessType, rhs.businessType)
				.append(this.projectStatus, rhs.projectStatus)
				.append(this.accrualMoney, rhs.accrualMoney)
				.append(this.incomechargeMoney, rhs.incomechargeMoney)
				.append(this.consultationMoney, rhs.consultationMoney)
				.append(this.paychargeMoney, rhs.paychargeMoney)
				.append(this.serviceMoney, rhs.serviceMoney)
				.append(this.annualNetProfit, rhs.annualNetProfit)
				.append(this.endDate, rhs.endDate)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.projectId) 
				.append(this.operationType) 
				.append(this.flowType) 
				.append(this.mineType) 
				.append(this.mineId) 
				.append(this.oppositeType) 
				.append(this.oppositeID) 
				.append(this.projectName) 
				.append(this.projectNumber) 
				.append(this.projectMoney) 
				.append(this.accrualtype) 
				.append(this.isPreposePayAccrual) 
				.append(this.payaccrualType) 
				.append(this.startDate) 
				.append(this.intentDate) 
				.append(this.dateMode) 
				.append(this.accrual) 
				.append(this.overdueRate) 
				.append(this.businessType) 
				.append(this.projectStatus) 
				.append(this.accrualMoney)
				.append(this.incomechargeMoney)
				.append(this.consultationMoney)
				.append(this.paychargeMoney)
				.append(this.serviceMoney)
				.append(this.annualNetProfit)
				.append(this.endDate)
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("projectId", this.projectId) 
				.append("operationType", this.operationType) 
				.append("flowType", this.flowType) 
				.append("mineType", this.mineType) 
				.append("mineId", this.mineId) 
				.append("oppositeType", this.oppositeType) 
				.append("oppositeID", this.oppositeID) 
				.append("projectName", this.projectName) 
				.append("projectNumber", this.projectNumber) 
				.append("projectMoney", this.projectMoney) 
				.append("accrualtype", this.accrualtype) 
				.append("isPreposePayAccrual", this.isPreposePayAccrual) 
				.append("payaccrualType", this.payaccrualType) 
				.append("startDate", this.startDate) 
				.append("intentDate", this.intentDate) 
				.append("dateMode", this.dateMode) 
				.append("accrual", this.accrual) 
				.append("overdueRate", this.overdueRate) 
				.append("businessType", this.businessType) 
				.append("projectStatus", this.projectStatus) 
				.append("accrualMoney", this.accrualMoney)
				.append("incomechargeMoney", this.incomechargeMoney)
				.append("consultationMoney", this.consultationMoney)
				.append("paychargeMoney", this.paychargeMoney)
				.append("serviceMoney", this.serviceMoney)
				.append("annualNetProfit", this.annualNetProfit)
				.toString();
	}

	public String getAppUserName() {
		return appUserName;
	}

	public void setAppUserName(String appUserName) {
		this.appUserName = appUserName;
	}

	public java.math.BigDecimal getPayProjectMoney() {
		return payProjectMoney;
	}

	public void setPayProjectMoney(java.math.BigDecimal payProjectMoney) {
		this.payProjectMoney = payProjectMoney;
	}

	public Short getIsAheadPay() {
		return isAheadPay;
	}

	public void setIsAheadPay(Short isAheadPay) {
		this.isAheadPay = isAheadPay;
	}
}
