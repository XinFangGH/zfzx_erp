package com.zhiwei.credit.model.creditFlow.customer.enterprise;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Date;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

import com.google.gson.annotations.Expose;

/**
 * 
 * @author 
 *
 */
/**
 * BpCustEntLawsuit Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class BpCustEntLawsuit extends com.zhiwei.core.model.BaseModel {

    protected Integer lawsuitId;
	protected Date registerDate;
	protected String registerReason;
	protected String relatedMoney;
	protected String role;
	protected String recordUser;
	protected Date recordTime;
	protected Enterprise enterprise;


	/**
	 * Default Empty Constructor for class BpCustEntLawsuit
	 */
	public BpCustEntLawsuit () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class BpCustEntLawsuit
	 */


    

	public Enterprise getEnterprise() {
		return enterprise;
	}

	public Integer getLawsuitId() {
		return lawsuitId;
	}

	public void setLawsuitId(Integer lawsuitId) {
		this.lawsuitId = lawsuitId;
	}




	public String getRecordUser() {
		return recordUser;
	}

	public void setRecordUser(String recordUser) {
		this.recordUser = recordUser;
	}

	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	
		



	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	/**
	 * 	 * @return String
	 * @hibernate.property column="registerReason" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getRegisterReason() {
		return this.registerReason;
	}
	
	/**
	 * Set the registerReason
	 */	
	public void setRegisterReason(String aValue) {
		this.registerReason = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="relatedMoney" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getRelatedMoney() {
		return this.relatedMoney;
	}
	
	/**
	 * Set the relatedMoney
	 */	
	public void setRelatedMoney(String aValue) {
		this.relatedMoney = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="role" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getRole() {
		return this.role;
	}
	
	/**
	 * Set the role
	 */	
	public void setRole(String aValue) {
		this.role = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="recordUser" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	

	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof BpCustEntLawsuit)) {
			return false;
		}
		BpCustEntLawsuit rhs = (BpCustEntLawsuit) object;
		return new EqualsBuilder()
				.append(this.lawsuitId, rhs.lawsuitId)
				.append(this.registerDate, rhs.registerDate)
				.append(this.registerReason, rhs.registerReason)
				.append(this.relatedMoney, rhs.relatedMoney)
				.append(this.role, rhs.role)
				.append(this.recordUser, rhs.recordUser)
				.append(this.recordTime, rhs.recordTime)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.lawsuitId) 
				.append(this.registerDate) 
				.append(this.registerReason) 
				.append(this.relatedMoney) 
				.append(this.role) 
				.append(this.recordUser) 
				.append(this.recordTime) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("lawsuitId", this.lawsuitId) 
				.append("registerDate", this.registerDate) 
				.append("registerReason", this.registerReason) 
				.append("relatedMoney", this.relatedMoney) 
				.append("role", this.role) 
				.append("recordUser", this.recordUser) 
				.append("recordTime", this.recordTime) 
				.toString();
	}



}
