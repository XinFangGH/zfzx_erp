package com.zhiwei.credit.model.creditFlow.customer.salesRecord;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
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
 * CsSalesRecord Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class CsSalesRecord extends com.zhiwei.core.model.BaseModel {

    protected Long saleId;
	protected Long userId;
	protected String userName;
	protected String userNumber;
	protected Long userGroupId;
	protected String userGroupName;
	protected Integer saleCommCount;
	protected String saleCommTime;
	protected Integer faceToseeCount;
	protected Integer faceToDealCount;
	protected String saleRemark;
	protected java.util.Date createDate;
	
	protected Integer personType;
	
	//不与数据库映射字段
	protected String companyName;


	public java.util.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * Default Empty Constructor for class CsSalesRecord
	 */
	public CsSalesRecord () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class CsSalesRecord
	 */
	public CsSalesRecord (
		 Long in_saleId
        ) {
		this.setSaleId(in_saleId);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="saleId" type="java.lang.Long" generator-class="native"
	 */
	public Long getSaleId() {
		return this.saleId;
	}
	
	/**
	 * Set the saleId
	 */	
	public void setSaleId(Long aValue) {
		this.saleId = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="userId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getUserId() {
		return this.userId;
	}
	
	/**
	 * Set the userId
	 */	
	public void setUserId(Long aValue) {
		this.userId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="userName" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getUserName() {
		return this.userName;
	}
	
	/**
	 * Set the userName
	 */	
	public void setUserName(String aValue) {
		this.userName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="userNumber" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getUserNumber() {
		return this.userNumber;
	}
	
	/**
	 * Set the userNumber
	 */	
	public void setUserNumber(String aValue) {
		this.userNumber = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="userGroupId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getUserGroupId() {
		return this.userGroupId;
	}
	
	/**
	 * Set the userGroupId
	 */	
	public void setUserGroupId(Long aValue) {
		this.userGroupId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="userGroupName" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getUserGroupName() {
		return this.userGroupName;
	}
	
	/**
	 * Set the userGroupName
	 */	
	public void setUserGroupName(String aValue) {
		this.userGroupName = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="saleCommCount" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getSaleCommCount() {
		return this.saleCommCount;
	}
	
	/**
	 * Set the saleCommCount
	 */	
	public void setSaleCommCount(Integer aValue) {
		this.saleCommCount = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="saleCommTime" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getSaleCommTime() {
		return this.saleCommTime;
	}
	
	/**
	 * Set the saleCommTime
	 */	
	public void setSaleCommTime(String aValue) {
		this.saleCommTime = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="faceToseeCount" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getFaceToseeCount() {
		return this.faceToseeCount;
	}
	
	/**
	 * Set the faceToseeCount
	 */	
	public void setFaceToseeCount(Integer aValue) {
		this.faceToseeCount = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="faceToDealCount" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getFaceToDealCount() {
		return this.faceToDealCount;
	}
	
	/**
	 * Set the faceToDealCount
	 */	
	public void setFaceToDealCount(Integer aValue) {
		this.faceToDealCount = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="saleRemark" type="java.lang.String" length="65535" not-null="false" unique="false"
	 */
	public String getSaleRemark() {
		return this.saleRemark;
	}
	
	/**
	 * Set the saleRemark
	 */	
	public void setSaleRemark(String aValue) {
		this.saleRemark = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof CsSalesRecord)) {
			return false;
		}
		CsSalesRecord rhs = (CsSalesRecord) object;
		return new EqualsBuilder()
				.append(this.saleId, rhs.saleId)
				.append(this.userId, rhs.userId)
				.append(this.userName, rhs.userName)
				.append(this.userNumber, rhs.userNumber)
				.append(this.userGroupId, rhs.userGroupId)
				.append(this.userGroupName, rhs.userGroupName)
				.append(this.saleCommCount, rhs.saleCommCount)
				.append(this.saleCommTime, rhs.saleCommTime)
				.append(this.faceToseeCount, rhs.faceToseeCount)
				.append(this.faceToDealCount, rhs.faceToDealCount)
				.append(this.saleRemark, rhs.saleRemark)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.saleId) 
				.append(this.userId) 
				.append(this.userName) 
				.append(this.userNumber) 
				.append(this.userGroupId) 
				.append(this.userGroupName) 
				.append(this.saleCommCount) 
				.append(this.saleCommTime) 
				.append(this.faceToseeCount) 
				.append(this.faceToDealCount) 
				.append(this.saleRemark) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("saleId", this.saleId) 
				.append("userId", this.userId) 
				.append("userName", this.userName) 
				.append("userNumber", this.userNumber) 
				.append("userGroupId", this.userGroupId) 
				.append("userGroupName", this.userGroupName) 
				.append("saleCommCount", this.saleCommCount) 
				.append("saleCommTime", this.saleCommTime) 
				.append("faceToseeCount", this.faceToseeCount) 
				.append("faceToDealCount", this.faceToDealCount) 
				.append("saleRemark", this.saleRemark) 
				.toString();
	}

	public Integer getPersonType() {
		return personType;
	}

	public void setPersonType(Integer personType) {
		this.personType = personType;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}



}
