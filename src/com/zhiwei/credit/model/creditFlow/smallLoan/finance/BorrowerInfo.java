package com.zhiwei.credit.model.creditFlow.smallLoan.finance;
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
 * BorrowerInfo Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class BorrowerInfo extends com.zhiwei.core.model.BaseModel {

    protected Long borrowerInfoId;
	protected Short type;
	protected Integer customerId;
	protected String cardNum;
	protected String relation;
	protected String address;
	protected String telPhone;
	protected String job;
	protected String assets;
	protected String monthlyIncome;
	protected String remarks;
	protected Long projectId;
	protected String businessType;
	protected String operationType;
    protected String customerName;
    
	/**
	 * Default Empty Constructor for class BorrowerInfo
	 */
	public BorrowerInfo () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class BorrowerInfo
	 */
	public BorrowerInfo (
		 Long in_borrowerInfoId
        ) {
		this.setBorrowerInfoId(in_borrowerInfoId);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="borrowerInfoId" type="java.lang.Long" generator-class="native"
	 */
	public Long getBorrowerInfoId() {
		return this.borrowerInfoId;
	}
	
	/**
	 * Set the borrowerInfoId
	 */	
	public void setBorrowerInfoId(Long aValue) {
		this.borrowerInfoId = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="type" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getType() {
		return this.type;
	}
	
	/**
	 * Set the type
	 */	
	public void setType(Short aValue) {
		this.type = aValue;
	}	

	
	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	/**
	 * 	 * @return String
	 * @hibernate.property column="cardNum" type="java.lang.String" length="30" not-null="false" unique="false"
	 */
	public String getCardNum() {
		return this.cardNum;
	}
	
	/**
	 * Set the cardNum
	 */	
	public void setCardNum(String aValue) {
		this.cardNum = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="relation" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getRelation() {
		return this.relation;
	}
	
	/**
	 * Set the relation
	 */	
	public void setRelation(String aValue) {
		this.relation = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="address" type="java.lang.String" length="255" not-null="false" unique="false"
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
	 * 	 * @return String
	 * @hibernate.property column="telPhone" type="java.lang.String" length="30" not-null="false" unique="false"
	 */
	public String getTelPhone() {
		return this.telPhone;
	}
	
	/**
	 * Set the telPhone
	 */	
	public void setTelPhone(String aValue) {
		this.telPhone = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="job" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getJob() {
		return this.job;
	}
	
	/**
	 * Set the job
	 */	
	public void setJob(String aValue) {
		this.job = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="assets" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getAssets() {
		return this.assets;
	}
	
	/**
	 * Set the assets
	 */	
	public void setAssets(String aValue) {
		this.assets = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="monthlyIncome" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getMonthlyIncome() {
		return this.monthlyIncome;
	}
	
	/**
	 * Set the monthlyIncome
	 */	
	public void setMonthlyIncome(String aValue) {
		this.monthlyIncome = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="remarks" type="java.lang.String" length="255" not-null="false" unique="false"
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

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof BorrowerInfo)) {
			return false;
		}
		BorrowerInfo rhs = (BorrowerInfo) object;
		return new EqualsBuilder()
				.append(this.borrowerInfoId, rhs.borrowerInfoId)
				.append(this.type, rhs.type)
				.append(this.customerId, rhs.customerId)
				.append(this.cardNum, rhs.cardNum)
				.append(this.relation, rhs.relation)
				.append(this.address, rhs.address)
				.append(this.telPhone, rhs.telPhone)
				.append(this.job, rhs.job)
				.append(this.assets, rhs.assets)
				.append(this.monthlyIncome, rhs.monthlyIncome)
				.append(this.remarks, rhs.remarks)
				.append(this.projectId, rhs.projectId)
				.append(this.businessType, rhs.businessType)
				.append(this.operationType, rhs.operationType)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.borrowerInfoId) 
				.append(this.type) 
				.append(this.customerId)
				.append(this.cardNum) 
				.append(this.relation) 
				.append(this.address) 
				.append(this.telPhone) 
				.append(this.job) 
				.append(this.assets) 
				.append(this.monthlyIncome) 
				.append(this.remarks) 
				.append(this.projectId) 
				.append(this.businessType) 
				.append(this.operationType) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("borrowerInfoId", this.borrowerInfoId) 
				.append("type", this.type) 
				.append("customerId",this.customerId)
				.append("cardNum", this.cardNum) 
				.append("relation", this.relation) 
				.append("address", this.address) 
				.append("telPhone", this.telPhone) 
				.append("job", this.job) 
				.append("assets", this.assets) 
				.append("monthlyIncome", this.monthlyIncome) 
				.append("remarks", this.remarks) 
				.append("projectId", this.projectId) 
				.append("businessType", this.businessType) 
				.append("operationType", this.operationType) 
				.toString();
	}

	public String toElementStr(){
		StringBuffer sb = new StringBuffer();
		sb.append("甲方（借款人）:"+this.getCustomerName()+"</br>");
		sb.append("证件号码："+this.getCardNum()+"</br>");
		sb.append("通讯地址："+this.getAddress()+"</br>");
		sb.append("联系电话："+this.getTelPhone()+"</br>");
		return sb.toString();
	}


}
