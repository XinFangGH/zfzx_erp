package com.zhiwei.credit.model.creditFlow.lawsuitguarantee.project;
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
 * SgLawsuitguaranteeProject Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class SgLawsuitguaranteeProject extends com.zhiwei.core.model.BaseModel {

    protected Long projectId;
	protected String projectName;
	protected String projectNumber;
	protected String operationType;
	protected String flowType;
	protected String mineType;
	protected Long mineId;
	protected String oppositeType;
	protected Long oppositeID;
	protected java.math.BigDecimal projectMoney;
	protected Long currencyType;
	protected java.util.Date startDate;
	protected java.math.BigDecimal premiumRate;
	protected String businessType;
	protected String appUserId;
	protected String acceptCourt;
	protected java.util.Date createDate;
	
	/**
	 * projectStatus=0:申请中项目;
	 * projectStatus=1:在保项目;
	 * projectStatus=2:已完成项目;
	 * projectStatus=3:已终止项目;
	 */
	protected Short projectStatus;
	
	protected String mineName;
	protected String appUserName;


	/**
	 * Default Empty Constructor for class SgLawsuitguaranteeProject
	 */
	public SgLawsuitguaranteeProject () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class SgLawsuitguaranteeProject
	 */
	public SgLawsuitguaranteeProject (
		 Long in_projectId
        ) {
		this.setProjectId(in_projectId);
    }

    

	/**
	 * 主键ID	 * @return Long
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

	public String getMineName() {
		return mineName;
	}

	public void setMineName(String mineName) {
		this.mineName = mineName;
	}

	public String getAppUserName() {
		return appUserName;
	}

	public void setAppUserName(String appUserName) {
		this.appUserName = appUserName;
	}

	/**
	 * 项目名称	 * @return String
	 * @hibernate.property column="projectName" type="java.lang.String" length="250" not-null="true" unique="false"
	 */
	public String getProjectName() {
		return this.projectName;
	}
	
	/**
	 * Set the projectName
	 * @spring.validator type="required"
	 */	
	public void setProjectName(String aValue) {
		this.projectName = aValue;
	}	

	public java.util.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * 项目编号 自动生成	 * @return String
	 * @hibernate.property column="projectNumber" type="java.lang.String" length="50" not-null="true" unique="false"
	 */
	public String getProjectNumber() {
		return this.projectNumber;
	}
	
	/**
	 * Set the projectNumber
	 * @spring.validator type="required"
	 */	
	public void setProjectNumber(String aValue) {
		this.projectNumber = aValue;
	}	

	/**
	 * 业务品种	 * @return String
	 * @hibernate.property column="operationType" type="java.lang.String" length="30" not-null="true" unique="false"
	 */
	public String getOperationType() {
		return this.operationType;
	}
	
	/**
	 * Set the operationType
	 * @spring.validator type="required"
	 */	
	public void setOperationType(String aValue) {
		this.operationType = aValue;
	}	

	/**
	 * 流程(项目)类别	 * @return String
	 * @hibernate.property column="flowType" type="java.lang.String" length="30" not-null="true" unique="false"
	 */
	public String getFlowType() {
		return this.flowType;
	}
	
	/**
	 * Set the flowType
	 * @spring.validator type="required"
	 */	
	public void setFlowType(String aValue) {
		this.flowType = aValue;
	}	

	/**
	 * 我方主体类型	 * @return String
	 * @hibernate.property column="mineType" type="java.lang.String" length="30" not-null="true" unique="false"
	 */
	public String getMineType() {
		return this.mineType;
	}
	
	/**
	 * Set the mineType
	 * @spring.validator type="required"
	 */	
	public void setMineType(String aValue) {
		this.mineType = aValue;
	}	

	/**
	 * 我方主体ID	 * @return Long
	 * @hibernate.property column="mineId" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	public Long getMineId() {
		return this.mineId;
	}
	
	/**
	 * Set the mineId
	 * @spring.validator type="required"
	 */	
	public void setMineId(Long aValue) {
		this.mineId = aValue;
	}	

	/**
	 * 对方主体类型	 * @return String
	 * @hibernate.property column="oppositeType" type="java.lang.String" length="30" not-null="true" unique="false"
	 */
	public String getOppositeType() {
		return this.oppositeType;
	}
	
	/**
	 * Set the oppositeType
	 * @spring.validator type="required"
	 */	
	public void setOppositeType(String aValue) {
		this.oppositeType = aValue;
	}	

	/**
	 * 对方主体ID	 * @return Long
	 * @hibernate.property column="oppositeID" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	public Long getOppositeID() {
		return this.oppositeID;
	}
	
	/**
	 * Set the oppositeID
	 * @spring.validator type="required"
	 */	
	public void setOppositeID(Long aValue) {
		this.oppositeID = aValue;
	}	

	/**
	 * 项目金额	 * @return java.math.BigDecimal
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
	 * 币种	 * @return Long
	 * @hibernate.property column="currencyType" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getCurrencyType() {
		return this.currencyType;
	}
	
	/**
	 * Set the currencyType
	 */	
	public void setCurrencyType(Long aValue) {
		this.currencyType = aValue;
	}	

	/**
	 * 承接日期	 * @return java.util.Date
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
	 * 保证金金额	 * @return java.math.BigDecimal
	 * @hibernate.property column="premiumRate" type="java.math.BigDecimal" length="10" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getPremiumRate() {
		return this.premiumRate;
	}
	
	/**
	 * Set the premiumRate
	 */	
	public void setPremiumRate(java.math.BigDecimal aValue) {
		this.premiumRate = aValue;
	}	

	/**
	 * 	 * @return String
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
	 * 	 * @return String
	 * @hibernate.property column="appUserId" type="java.lang.String" length="250" not-null="false" unique="false"
	 */
	public String getAppUserId() {
		return this.appUserId;
	}
	
	/**
	 * Set the appUserId
	 */	
	public void setAppUserId(String aValue) {
		this.appUserId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="acceptCourt" type="java.lang.String" length="250" not-null="false" unique="false"
	 */
	public String getAcceptCourt() {
		return this.acceptCourt;
	}
	
	/**
	 * Set the acceptCourt
	 */	
	public void setAcceptCourt(String aValue) {
		this.acceptCourt = aValue;
	}	

	public Short getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(Short projectStatus) {
		this.projectStatus = projectStatus;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SgLawsuitguaranteeProject)) {
			return false;
		}
		SgLawsuitguaranteeProject rhs = (SgLawsuitguaranteeProject) object;
		return new EqualsBuilder()
				.append(this.projectId, rhs.projectId)
				.append(this.projectName, rhs.projectName)
				.append(this.projectNumber, rhs.projectNumber)
				.append(this.operationType, rhs.operationType)
				.append(this.flowType, rhs.flowType)
				.append(this.mineType, rhs.mineType)
				.append(this.mineId, rhs.mineId)
				.append(this.oppositeType, rhs.oppositeType)
				.append(this.oppositeID, rhs.oppositeID)
				.append(this.projectMoney, rhs.projectMoney)
				.append(this.currencyType, rhs.currencyType)
				.append(this.startDate, rhs.startDate)
				.append(this.premiumRate, rhs.premiumRate)
				.append(this.businessType, rhs.businessType)
				.append(this.appUserId, rhs.appUserId)
				.append(this.acceptCourt, rhs.acceptCourt)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.projectId) 
				.append(this.projectName) 
				.append(this.projectNumber) 
				.append(this.operationType) 
				.append(this.flowType) 
				.append(this.mineType) 
				.append(this.mineId) 
				.append(this.oppositeType) 
				.append(this.oppositeID) 
				.append(this.projectMoney) 
				.append(this.currencyType) 
				.append(this.startDate) 
				.append(this.premiumRate) 
				.append(this.businessType) 
				.append(this.appUserId) 
				.append(this.acceptCourt) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("projectId", this.projectId) 
				.append("projectName", this.projectName) 
				.append("projectNumber", this.projectNumber) 
				.append("operationType", this.operationType) 
				.append("flowType", this.flowType) 
				.append("mineType", this.mineType) 
				.append("mineId", this.mineId) 
				.append("oppositeType", this.oppositeType) 
				.append("oppositeID", this.oppositeID) 
				.append("projectMoney", this.projectMoney) 
				.append("currencyType", this.currencyType) 
				.append("startDate", this.startDate) 
				.append("premiumRate", this.premiumRate) 
				.append("businessType", this.businessType) 
				.append("appUserId", this.appUserId) 
				.append("acceptCourt", this.acceptCourt) 
				.toString();
	}



}
