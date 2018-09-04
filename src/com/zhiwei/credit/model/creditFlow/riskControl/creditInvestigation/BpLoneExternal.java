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
 * @author 
 *
 */
/**
 * BpLoneExternal Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class BpLoneExternal extends com.zhiwei.core.model.BaseModel {
	/**
	 * 主键id
	 */
    protected Long externalId;
	/**
	 * 创建人姓名
	 */
	protected String creator;
	/**
	 * 创建人ID
	 */
	protected Long creatorId;
	/**
	 * 创建时间
	 */
	protected java.util.Date createDate;
	/**
	 * 客户类型
	 */
	protected String customerType;
	/**
	 * 客户ＩＤ
	 */
	protected Integer customerId;
	/**
	 * 客户名称
	 */
	protected String customerName;
	/**
	 * 客户证件号码
	 */
	protected String cardnumber;
	/**
	 * 贷款金额
	 */
	protected java.math.BigDecimal loneMoney;
	/**
	 * 贷款余额
	 */
	protected java.math.BigDecimal onLoneMoney;
	/**
	 * 贷款公司
	 */
	protected String loneCompany;
	/**
	 * 逾期天数
	 */
	protected Long overdueDays;
	/**
	 * 开始日期
	 */
	protected java.util.Date startDate;
	/**
	 * 结束日期
	 */
	protected java.util.Date intentDate;
	/**
	 * 项目状态
	 */
	protected Short projectStatus;
	/**
	 * 项目类型
	 */
	protected String businessType;
	/**
	 * 备注
	 */
	protected String remarks;
	/**
	 * 不与数据库相关，项目状态值
	 */
	protected String projectStatusValue;

	public String getProjectStatusValue() {
		return projectStatusValue;
	}

	public void setProjectStatusValue(String projectStatusValue) {
		this.projectStatusValue = projectStatusValue;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * Default Empty Constructor for class BpLoneExternal
	 */
	public BpLoneExternal () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class BpLoneExternal
	 */
	public BpLoneExternal (
		 Long in_externalId
        ) {
		this.setExternalId(in_externalId);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="externalId" type="java.lang.Long" generator-class="native"
	 */
	public Long getExternalId() {
		return this.externalId;
	}
	
	/**
	 * Set the externalId
	 */	
	public void setExternalId(Long aValue) {
		this.externalId = aValue;
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
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="onLoneMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getOnLoneMoney() {
		return this.onLoneMoney;
	}
	
	/**
	 * Set the onLoneMoney
	 */	
	public void setOnLoneMoney(java.math.BigDecimal aValue) {
		this.onLoneMoney = aValue;
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
	 * 	 * @return java.util.Date
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
	 * 	 * @return Short
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
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof BpLoneExternal)) {
			return false;
		}
		BpLoneExternal rhs = (BpLoneExternal) object;
		return new EqualsBuilder()
				.append(this.externalId, rhs.externalId)
				.append(this.creator, rhs.creator)
				.append(this.creatorId, rhs.creatorId)
				.append(this.createDate, rhs.createDate)
				.append(this.customerType, rhs.customerType)
				.append(this.customerId, rhs.customerId)
				.append(this.cardnumber, rhs.cardnumber)
				.append(this.loneMoney, rhs.loneMoney)
				.append(this.onLoneMoney, rhs.onLoneMoney)
				.append(this.loneCompany, rhs.loneCompany)
				.append(this.overdueDays, rhs.overdueDays)
				.append(this.startDate, rhs.startDate)
				.append(this.intentDate, rhs.intentDate)
				.append(this.projectStatus, rhs.projectStatus)
				.append(this.businessType, rhs.businessType)
				.append(this.remarks, rhs.remarks)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.externalId) 
				.append(this.creator) 
				.append(this.creatorId) 
				.append(this.createDate) 
				.append(this.customerType) 
				.append(this.customerId) 
				.append(this.cardnumber) 
				.append(this.loneMoney) 
				.append(this.onLoneMoney) 
				.append(this.loneCompany) 
				.append(this.overdueDays) 
				.append(this.startDate) 
				.append(this.intentDate) 
				.append(this.projectStatus) 
				.append(this.businessType) 
				.append(this.remarks) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("externalId", this.externalId) 
				.append("creator", this.creator) 
				.append("creatorId", this.creatorId) 
				.append("createDate", this.createDate) 
				.append("customerType", this.customerType) 
				.append("customerId", this.customerId) 
				.append("cardnumber", this.cardnumber) 
				.append("loneMoney", this.loneMoney) 
				.append("onLoneMoney", this.onLoneMoney) 
				.append("loneCompany", this.loneCompany) 
				.append("overdueDays", this.overdueDays) 
				.append("startDate", this.startDate) 
				.append("intentDate", this.intentDate) 
				.append("projectStatus", this.projectStatus) 
				.append("businessType", this.businessType) 
				.append("remarks", this.remarks) 
				.toString();
	}



}
