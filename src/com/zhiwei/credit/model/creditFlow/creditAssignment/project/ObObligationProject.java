package com.zhiwei.credit.model.creditFlow.creditAssignment.project;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * 
 * @author 
 *
 */
/**
 * ObObligationProject Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class ObObligationProject extends com.zhiwei.core.model.BaseModel {

	protected Long id;
	protected Long projectId;//项目表的id
	protected String businessType;//项目表的businessType
	protected String  projectName;//项目表的项目名称
	protected String projectNumber;//项目表的项目编号
	protected String obligationName;//债权产品的名称
	protected String obligationNumber;//债权产品的编号
	protected java.math.BigDecimal projectMoney;//债权产品的发行的金额
	protected String purposeType;//债权产品的发行目的
	protected Short currency;//债权产品发行的币种
	protected java.math.BigDecimal accrual;//债权产品的发行利率
	protected Short dateMode;//债权产品的发行计息日期模型
	protected java.util.Date startDate;//债权产品发行开始的时间
	protected java.util.Date intentDate;//根据债权产品发行的期限得出债权产品的结束时间，即债权产品自动关闭时间
	protected Integer payintentPeriod;//债权产品的发行期限
	protected Integer dayOfEveryPeriod;//债权产品自定义周期的天数,
	protected String isStartDatePay;//债权产品是否按固定日归还投资人利息,
	protected Integer payintentPerioDate;//债权产品每期固定付息日,
	protected String accrualtype;//债权产品的计息方式（指的是按等额本金，等额本息。。。。）
	protected String payaccrualType; //债权产品的付息方式（指是按年还是按月。日，季）
	protected String overdueRateType;//债权产品逾期付息时的逾期费率的计算方式,1按日2，按期
	protected String overdueRate;//债权产品逾期付息时的逾期费率
	protected Short isAheadPay;//债权产品是否允许提前归还投资人资金
	protected Short aheadDayNum;//债权产品允许提前归还投资人资金需提前多少天通知投资人
	protected java.math.BigDecimal breachRate;//日利化利息
	protected java.math.BigDecimal breachMoney;//日化利率
	protected Integer isPreposePayAccrual;//是否前置付息 0否 	1 是
	protected java.math.BigDecimal minInvestMent;//最小投资额
	protected Long totalQuotient;//投资总分额（totalQuotient=projectMoney/minInvestMent）
	protected java.util.Date factEndDate;//债权产品实际关闭时间（默认加载的是自动关闭时间）
	protected Short obligationStatus;//债权产品的状态（0默认债权产品进行中，1表示债权产品自动（债权匹配完成）关闭，2表示手动关闭债权产品）
	protected Integer projectStatus;//项目状态(1代表已发布,0代表未发布)
	protected Long minQuotient;//债权产品最小投资份额
	//没有与数据库字段映射
	protected java.math.BigDecimal mappingMoney;//已匹配金额
	protected java.math.BigDecimal unmappingMoney;//未匹配金额
	protected Long unmappingQuotient;//未匹配份额
	
	

	public java.math.BigDecimal getMappingMoney() {
		return mappingMoney;
	}

	public void setMappingMoney(java.math.BigDecimal mappingMoney) {
		this.mappingMoney = mappingMoney;
	}

	public java.math.BigDecimal getUnmappingMoney() {
		return unmappingMoney;
	}

	public void setUnmappingMoney(java.math.BigDecimal unmappingMoney) {
		this.unmappingMoney = unmappingMoney;
	}

	public Long getUnmappingQuotient() {
		return unmappingQuotient;
	}

	public void setUnmappingQuotient(Long unmappingQuotient) {
		this.unmappingQuotient = unmappingQuotient;
	}

	/**
	 * Default Empty Constructor for class ObObligationProject
	 */
	public ObObligationProject () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ObObligationProject
	 */
	public ObObligationProject (
		 Long in_id
        ) {
		this.setId(in_id);
    }

    

	/**
	 * 	 * @return Long
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
	 * 	 * @return Long
	 * @hibernate.property column="projectId" type="java.lang.Long" length="19" not-null="false" unique="false"
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
	 * 	 * @return String
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
	 * 	 * @return String
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
	 * 	 * @return String
	 * @hibernate.property column="obligationName" type="java.lang.String" length="150" not-null="false" unique="false"
	 */
	public String getObligationName() {
		return this.obligationName;
	}
	
	/**
	 * Set the obligationName
	 */	
	public void setObligationName(String aValue) {
		this.obligationName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="obligationNumber" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getObligationNumber() {
		return this.obligationNumber;
	}
	
	/**
	 * Set the obligationNumber
	 */	
	public void setObligationNumber(String aValue) {
		this.obligationNumber = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="projectMoney" type="java.math.BigDecimal" length="40" not-null="false" unique="false"
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
	 * 贷款用途	 * @return String
	 * @hibernate.property column="purposeType" type="java.lang.String" length="65535" not-null="false" unique="false"
	 */
	public String getPurposeType() {
		return this.purposeType;
	}
	
	/**
	 * Set the purposeType
	 */	
	public void setPurposeType(String aValue) {
		this.purposeType = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="currency" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getCurrency() {
		return this.currency;
	}
	
	/**
	 * Set the currency
	 */	
	public void setCurrency(Short aValue) {
		this.currency = aValue;
	}	

	/**
	 * 债权产品利率	 * @return java.math.BigDecimal
	 * @hibernate.property column="accrual" type="java.math.BigDecimal" length="40" not-null="false" unique="false"
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
	 * 	 * @return Short
	 * @hibernate.property column="dateMode" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getDateMode() {
		return this.dateMode;
	}
	
	/**
	 * Set the dateMode
	 */	
	public void setDateMode(Short aValue) {
		this.dateMode = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
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
	 * 还款日	 * @return java.util.Date
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
	 * 	 * @return Integer
	 * @hibernate.property column="payintentPeriod" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getPayintentPeriod() {
		return this.payintentPeriod;
	}
	
	/**
	 * Set the payintentPeriod
	 */	
	public void setPayintentPeriod(Integer aValue) {
		this.payintentPeriod = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="dayOfEveryPeriod" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getDayOfEveryPeriod() {
		return this.dayOfEveryPeriod;
	}
	
	/**
	 * Set the dayOfEveryPeriod
	 */	
	public void setDayOfEveryPeriod(Integer aValue) {
		this.dayOfEveryPeriod = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="isStartDatePay" type="java.lang.String" length="30" not-null="false" unique="false"
	 */
	public String getIsStartDatePay() {
		return this.isStartDatePay;
	}
	
	/**
	 * Set the isStartDatePay
	 */	
	public void setIsStartDatePay(String aValue) {
		this.isStartDatePay = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="payintentPerioDate" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getPayintentPerioDate() {
		return this.payintentPerioDate;
	}
	
	/**
	 * Set the payintentPerioDate
	 */	
	public void setPayintentPerioDate(Integer aValue) {
		this.payintentPerioDate = aValue;
	}	

	/**
	 * 计息方式	 * @return String
	 * @hibernate.property column="accrualtype" type="java.lang.String" length="30" not-null="false" unique="false"
	 */
	public String getAccrualtype() {
		return this.accrualtype;
	}
	
	/**
	 * Set the accrualtype
	 */	
	public void setAccrualtype(String aValue) {
		this.accrualtype = aValue;
	}	

	/**
	 * 	 * @return String
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
	 * 逾期费率的计算方式	 * @return String
	 * @hibernate.property column="overdueRateType" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	public String getOverdueRateType() {
		return this.overdueRateType;
	}
	
	/**
	 * Set the overdueRateType
	 */	
	public void setOverdueRateType(String aValue) {
		this.overdueRateType = aValue;
	}	

	/**
	 * 逾期费率	 * @return String
	 * @hibernate.property column="overdueRate" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	public String getOverdueRate() {
		return this.overdueRate;
	}
	
	/**
	 * Set the overdueRate
	 */	
	public void setOverdueRate(String aValue) {
		this.overdueRate = aValue;
	}	

	/**
	 * 是否允许提前还款	 * @return Short
	 * @hibernate.property column="isAheadPay" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getIsAheadPay() {
		return this.isAheadPay;
	}
	
	/**
	 * Set the isAheadPay
	 */	
	public void setIsAheadPay(Short aValue) {
		this.isAheadPay = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="aheadDayNum" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getAheadDayNum() {
		return this.aheadDayNum;
	}
	
	/**
	 * Set the aheadDayNum
	 */	
	public void setAheadDayNum(Short aValue) {
		this.aheadDayNum = aValue;
	}	

	/**
	 * 违约金比例	 * @return java.math.BigDecimal
	 * @hibernate.property column="breachRate" type="java.math.BigDecimal" length="40" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getBreachRate() {
		return this.breachRate;
	}
	
	/**
	 * Set the breachRate
	 */	
	public void setBreachRate(java.math.BigDecimal aValue) {
		this.breachRate = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="breachMoney" type="java.math.BigDecimal" length="40" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getBreachMoney() {
		return this.breachMoney;
	}
	
	/**
	 * Set the breachMoney
	 */	
	public void setBreachMoney(java.math.BigDecimal aValue) {
		this.breachMoney = aValue;
	}	

	/**
	 * 	 * @return Integer
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
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="minInvestMent" type="java.math.BigDecimal" length="40" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getMinInvestMent() {
		return this.minInvestMent;
	}
	
	/**
	 * Set the minInvestMent
	 */	
	public void setMinInvestMent(java.math.BigDecimal aValue) {
		this.minInvestMent = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="totalQuotient" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getTotalQuotient() {
		return this.totalQuotient;
	}
	
	/**
	 * Set the totalQuotient
	 */	
	public void setTotalQuotient(Long aValue) {
		this.totalQuotient = aValue;
	}	

	/**
	 * 债权产品实际结束时间	 * @return java.util.Date
	 * @hibernate.property column="factEndDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getFactEndDate() {
		return this.factEndDate;
	}
	
	/**
	 * Set the factEndDate
	 */	
	public void setFactEndDate(java.util.Date aValue) {
		this.factEndDate = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="companyId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getCompanyId() {
		return this.companyId;
	}
	
	/**
	 * Set the companyId
	 */	
	public void setCompanyId(Long aValue) {
		this.companyId = aValue;
	}	

	/**
	 * 发布产品的状态	 * @return Short
	 * @hibernate.property column="obligationStatus" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getObligationStatus() {
		return this.obligationStatus;
	}
	
	/**
	 * Set the obligationStatus
	 */	
	public void setObligationStatus(Short aValue) {
		this.obligationStatus = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="projectName" type="java.lang.String" length="100" not-null="false" unique="false"
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
	 * 	 * @return Integer
	 * @hibernate.property column="projectStatus" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getProjectStatus() {
		return this.projectStatus;
	}
	
	/**
	 * Set the projectStatus
	 */	
	public void setProjectStatus(Integer aValue) {
		this.projectStatus = aValue;
	}	

	/**
	 * 最小投资份额	 * @return Long
	 * @hibernate.property column="minQuotient" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getMinQuotient() {
		return this.minQuotient;
	}
	
	/**
	 * Set the minQuotient
	 */	
	public void setMinQuotient(Long aValue) {
		this.minQuotient = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ObObligationProject)) {
			return false;
		}
		ObObligationProject rhs = (ObObligationProject) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.projectId, rhs.projectId)
				.append(this.businessType, rhs.businessType)
				.append(this.projectNumber, rhs.projectNumber)
				.append(this.obligationName, rhs.obligationName)
				.append(this.obligationNumber, rhs.obligationNumber)
				.append(this.projectMoney, rhs.projectMoney)
				.append(this.purposeType, rhs.purposeType)
				.append(this.currency, rhs.currency)
				.append(this.accrual, rhs.accrual)
				.append(this.dateMode, rhs.dateMode)
				.append(this.startDate, rhs.startDate)
				.append(this.intentDate, rhs.intentDate)
				.append(this.payintentPeriod, rhs.payintentPeriod)
				.append(this.dayOfEveryPeriod, rhs.dayOfEveryPeriod)
				.append(this.isStartDatePay, rhs.isStartDatePay)
				.append(this.payintentPerioDate, rhs.payintentPerioDate)
				.append(this.accrualtype, rhs.accrualtype)
				.append(this.payaccrualType, rhs.payaccrualType)
				.append(this.overdueRateType, rhs.overdueRateType)
				.append(this.overdueRate, rhs.overdueRate)
				.append(this.isAheadPay, rhs.isAheadPay)
				.append(this.aheadDayNum, rhs.aheadDayNum)
				.append(this.breachRate, rhs.breachRate)
				.append(this.breachMoney, rhs.breachMoney)
				.append(this.isPreposePayAccrual, rhs.isPreposePayAccrual)
				.append(this.minInvestMent, rhs.minInvestMent)
				.append(this.totalQuotient, rhs.totalQuotient)
				.append(this.factEndDate, rhs.factEndDate)
				.append(this.companyId, rhs.companyId)
				.append(this.obligationStatus, rhs.obligationStatus)
				.append(this.projectName, rhs.projectName)
				.append(this.projectStatus, rhs.projectStatus)
				.append(this.minQuotient, rhs.minQuotient)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.projectId) 
				.append(this.businessType) 
				.append(this.projectNumber) 
				.append(this.obligationName) 
				.append(this.obligationNumber) 
				.append(this.projectMoney) 
				.append(this.purposeType) 
				.append(this.currency) 
				.append(this.accrual) 
				.append(this.dateMode) 
				.append(this.startDate) 
				.append(this.intentDate) 
				.append(this.payintentPeriod) 
				.append(this.dayOfEveryPeriod) 
				.append(this.isStartDatePay) 
				.append(this.payintentPerioDate) 
				.append(this.accrualtype) 
				.append(this.payaccrualType) 
				.append(this.overdueRateType) 
				.append(this.overdueRate) 
				.append(this.isAheadPay) 
				.append(this.aheadDayNum) 
				.append(this.breachRate) 
				.append(this.breachMoney) 
				.append(this.isPreposePayAccrual) 
				.append(this.minInvestMent) 
				.append(this.totalQuotient) 
				.append(this.factEndDate) 
				.append(this.companyId) 
				.append(this.obligationStatus) 
				.append(this.projectName) 
				.append(this.projectStatus) 
				.append(this.minQuotient) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("projectId", this.projectId) 
				.append("businessType", this.businessType) 
				.append("projectNumber", this.projectNumber) 
				.append("obligationName", this.obligationName) 
				.append("obligationNumber", this.obligationNumber) 
				.append("projectMoney", this.projectMoney) 
				.append("purposeType", this.purposeType) 
				.append("currency", this.currency) 
				.append("accrual", this.accrual) 
				.append("dateMode", this.dateMode) 
				.append("startDate", this.startDate) 
				.append("intentDate", this.intentDate) 
				.append("payintentPeriod", this.payintentPeriod) 
				.append("dayOfEveryPeriod", this.dayOfEveryPeriod) 
				.append("isStartDatePay", this.isStartDatePay) 
				.append("payintentPerioDate", this.payintentPerioDate) 
				.append("accrualtype", this.accrualtype) 
				.append("payaccrualType", this.payaccrualType) 
				.append("overdueRateType", this.overdueRateType) 
				.append("overdueRate", this.overdueRate) 
				.append("isAheadPay", this.isAheadPay) 
				.append("aheadDayNum", this.aheadDayNum) 
				.append("breachRate", this.breachRate) 
				.append("breachMoney", this.breachMoney) 
				.append("isPreposePayAccrual", this.isPreposePayAccrual) 
				.append("minInvestMent", this.minInvestMent) 
				.append("totalQuotient", this.totalQuotient) 
				.append("factEndDate", this.factEndDate) 
				.append("companyId", this.companyId) 
				.append("obligationStatus", this.obligationStatus) 
				.append("projectName", this.projectName) 
				.append("projectStatus", this.projectStatus) 
				.append("minQuotient", this.minQuotient) 
				.toString();
	}



}
