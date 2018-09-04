package com.zhiwei.credit.model.creditFlow.riskControl.creditInvestigation;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * 
 * @author luyue
 *
 */
/**
 * BpBadCredit Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class BpBadCredit extends com.zhiwei.core.model.BaseModel {
	/**
	 * 主键id
	 */
    protected Long creditId;//主键
	/**
	 * 记录添加人
	 */
	protected String creator;
	/**
	 * 记录添加人ID
	 */
	protected Long creatorId;
	/**
	 * 上报的日期
	 */
	protected java.util.Date createDate;
	/**
	 * 客户类型
	 */
	protected String customerType;
	/**
	 * 客户ID
	 */
	protected Integer customerId;
	/**
	 * 客户名称
	 */
	protected String customerName;
	/**
	 * 证件号码
	 */
	protected String cardnumber;
	/**
	 * 金额
	 */
	protected java.math.BigDecimal loneMoney;
	/**
	 * 逾期天数
	 */
	protected Long overdueDays;
	/**
	 * 业务类型
	 */
	protected String businessType;
	/**
	 * 项目 名称
	 */
	protected String projectName;
	/**
	 * 备注
	 */
	protected String remarks;
	/**
	 * 上报类型 （0是内部，1是外部）
	 */
	protected Short reportType;
	/**
	 * 贷款单位
	 */
	protected String loneCompany;
	/**
	 * 针对内部上报的款项ID
	 */
	protected Long moneyId;
	/**
	 * 针对内部上报的款项表的标识（fund代表sl_fund_intent，charge代表sl_actual_to_charge）,后续可拓展
	 */
	protected String moneyType;
	/**
	 * 不良类型
	 */
	protected Short badType;
	
	/**
	 * 不与数据库想关联，不良类型值
	 */
	protected String badTypeValue;
    
	public String getBadTypeValue() {
		return badTypeValue;
	}

	public void setBadTypeValue(String badTypeValue) {
		this.badTypeValue = badTypeValue;
	}

	/**
	 * Default Empty Constructor for class BpBadCredit
	 */
	public BpBadCredit () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class BpBadCredit
	 */
	public BpBadCredit (
		 Long in_creditId
        ) {
		this.setCreditId(in_creditId);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="creditId" type="java.lang.Long" generator-class="native"
	 */
	public Long getCreditId() {
		return this.creditId;
	}
	
	/**
	 * Set the creditId
	 */	
	public void setCreditId(Long aValue) {
		this.creditId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="creator" type="java.lang.String" length="250" not-null="false" unique="false"
	 */
	public String getCreator() {
		return this.creator;
	}
	
	/**
	 * Set the creator
	 */	
	public void setCreator(String aValue) {
		this.creator = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="creatorId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getCreatorId() {
		return this.creatorId;
	}
	
	/**
	 * Set the creatorId
	 */	
	public void setCreatorId(Long aValue) {
		this.creatorId = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="createDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getCreateDate() {
		return this.createDate;
	}
	
	/**
	 * Set the createDate
	 */	
	public void setCreateDate(java.util.Date aValue) {
		this.createDate = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="customerType" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	public String getCustomerType() {
		return this.customerType;
	}
	
	/**
	 * Set the customerType
	 */	
	public void setCustomerType(String aValue) {
		this.customerType = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="customerId" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getCustomerId() {
		return this.customerId;
	}
	
	/**
	 * Set the customerId
	 */	
	public void setCustomerId(Integer aValue) {
		this.customerId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="customerName" type="java.lang.String" length="300" not-null="false" unique="false"
	 */
	public String getCustomerName() {
		return this.customerName;
	}
	
	/**
	 * Set the customerName
	 */	
	public void setCustomerName(String aValue) {
		this.customerName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="cardnumber" type="java.lang.String" length="20" not-null="false" unique="false"
	 */
	public String getCardnumber() {
		return this.cardnumber;
	}
	
	/**
	 * Set the cardnumber
	 */	
	public void setCardnumber(String aValue) {
		this.cardnumber = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="loneMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getLoneMoney() {
		return this.loneMoney;
	}
	
	/**
	 * Set the loneMoney
	 */	
	public void setLoneMoney(java.math.BigDecimal aValue) {
		this.loneMoney = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="overdueDays" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getOverdueDays() {
		return this.overdueDays;
	}
	
	/**
	 * Set the overdueDays
	 */	
	public void setOverdueDays(Long aValue) {
		this.overdueDays = aValue;
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
	 * 	 * @return String
	 * @hibernate.property column="remarks" type="java.lang.String" length="65535" not-null="false" unique="false"
	 */
	public String getRemarks() {
		return this.remarks;
	}
	
	/**
	 * Set the remarks
	 */	
	public void setRemarks(String aValue) {
		this.remarks = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="reportType" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getReportType() {
		return this.reportType;
	}
	
	/**
	 * Set the reportType
	 */	
	public void setReportType(Short aValue) {
		this.reportType = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="loneCompany" type="java.lang.String" length="300" not-null="false" unique="false"
	 */
	public String getLoneCompany() {
		return this.loneCompany;
	}
	
	/**
	 * Set the loneCompany
	 */	
	public void setLoneCompany(String aValue) {
		this.loneCompany = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="moneyId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getMoneyId() {
		return this.moneyId;
	}
	
	/**
	 * Set the moneyId
	 */	
	public void setMoneyId(Long aValue) {
		this.moneyId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="moneyType" type="java.lang.String" length="20" not-null="false" unique="false"
	 */
	public String getMoneyType() {
		return this.moneyType;
	}
	
	/**
	 * Set the moneyType
	 */	
	public void setMoneyType(String aValue) {
		this.moneyType = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="badType" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getBadType() {
		return this.badType;
	}
	
	/**
	 * Set the badType
	 */	
	public void setBadType(Short aValue) {
		this.badType = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof BpBadCredit)) {
			return false;
		}
		BpBadCredit rhs = (BpBadCredit) object;
		return new EqualsBuilder()
				.append(this.creditId, rhs.creditId)
				.append(this.creator, rhs.creator)
				.append(this.creatorId, rhs.creatorId)
				.append(this.createDate, rhs.createDate)
				.append(this.customerType, rhs.customerType)
				.append(this.customerId, rhs.customerId)
				.append(this.customerName, rhs.customerName)
				.append(this.cardnumber, rhs.cardnumber)
				.append(this.loneMoney, rhs.loneMoney)
				.append(this.overdueDays, rhs.overdueDays)
				.append(this.businessType, rhs.businessType)
				.append(this.projectName, rhs.projectName)
				.append(this.remarks, rhs.remarks)
				.append(this.reportType, rhs.reportType)
				.append(this.loneCompany, rhs.loneCompany)
				.append(this.moneyId, rhs.moneyId)
				.append(this.moneyType, rhs.moneyType)
				.append(this.badType, rhs.badType)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.creditId) 
				.append(this.creator) 
				.append(this.creatorId) 
				.append(this.createDate) 
				.append(this.customerType) 
				.append(this.customerId) 
				.append(this.customerName) 
				.append(this.cardnumber) 
				.append(this.loneMoney) 
				.append(this.overdueDays) 
				.append(this.businessType) 
				.append(this.projectName) 
				.append(this.remarks) 
				.append(this.reportType) 
				.append(this.loneCompany) 
				.append(this.moneyId) 
				.append(this.moneyType) 
				.append(this.badType) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("creditId", this.creditId) 
				.append("creator", this.creator) 
				.append("creatorId", this.creatorId) 
				.append("createDate", this.createDate) 
				.append("customerType", this.customerType) 
				.append("customerId", this.customerId) 
				.append("customerName", this.customerName) 
				.append("cardnumber", this.cardnumber) 
				.append("loneMoney", this.loneMoney) 
				.append("overdueDays", this.overdueDays) 
				.append("businessType", this.businessType) 
				.append("projectName", this.projectName) 
				.append("remarks", this.remarks) 
				.append("reportType", this.reportType) 
				.append("loneCompany", this.loneCompany) 
				.append("moneyId", this.moneyId) 
				.append("moneyType", this.moneyType) 
				.append("badType", this.badType) 
				.toString();
	}



}
