package com.zhiwei.credit.model.hrm;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

import com.google.gson.annotations.Expose;

/**
 * StandSalary Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class StandSalary extends com.zhiwei.core.model.BaseModel {

	@Expose
    protected Long standardId;
	@Expose
	protected String standardNo;
	@Expose
	protected String standardName;
	@Expose
	protected java.math.BigDecimal totalMoney;
	@Expose
	protected String framer;
	@Expose
	protected java.util.Date setdownTime;
	@Expose
	protected String checkName;
	@Expose
	protected java.util.Date checkTime;
	@Expose
	protected String modifyName;
	@Expose
	protected java.util.Date modifyTime;
	@Expose
	protected String checkOpinion;
	@Expose
	protected Short status;
	@Expose
	protected String memo;

	protected java.util.Set standSalaryItems = new java.util.HashSet();

	/**
	 * Default Empty Constructor for class StandSalary
	 */
	public StandSalary () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class StandSalary
	 */
	public StandSalary (
		 Long in_standardId
        ) {
		this.setStandardId(in_standardId);
    }


	public java.util.Set getStandSalaryItems () {
		return standSalaryItems;
	}	
	
	public void setStandSalaryItems (java.util.Set in_standSalaryItems) {
		this.standSalaryItems = in_standSalaryItems;
	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="standardId" type="java.lang.Long" generator-class="native"
	 */
	public Long getStandardId() {
		return this.standardId;
	}
	
	/**
	 * Set the standardId
	 */	
	public void setStandardId(Long aValue) {
		this.standardId = aValue;
	}	

	/**
	 * Ð½³ê±ê×¼±àºÅ
            Î©Ò»	 * @return String
	 * @hibernate.property column="standardNo" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getStandardNo() {
		return this.standardNo;
	}
	
	/**
	 * Set the standardNo
	 * @spring.validator type="required"
	 */	
	public void setStandardNo(String aValue) {
		this.standardNo = aValue;
	}	

	/**
	 * ±ê×¼Ãû³Æ	 * @return String
	 * @hibernate.property column="standardName" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getStandardName() {
		return this.standardName;
	}
	
	/**
	 * Set the standardName
	 * @spring.validator type="required"
	 */	
	public void setStandardName(String aValue) {
		this.standardName = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="totalMoney" type="java.math.BigDecimal" length="18" not-null="true" unique="false"
	 */
	public java.math.BigDecimal getTotalMoney() {
		return this.totalMoney;
	}
	
	/**
	 * Set the totalMoney
	 * @spring.validator type="required"
	 */	
	public void setTotalMoney(java.math.BigDecimal aValue) {
		this.totalMoney = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="framer" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getFramer() {
		return this.framer;
	}
	
	/**
	 * Set the framer
	 */	
	public void setFramer(String aValue) {
		this.framer = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="setdownTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getSetdownTime() {
		return this.setdownTime;
	}
	
	/**
	 * Set the setdownTime
	 */	
	public void setSetdownTime(java.util.Date aValue) {
		this.setdownTime = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="checkName" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getCheckName() {
		return this.checkName;
	}
	
	/**
	 * Set the checkName
	 */	
	public void setCheckName(String aValue) {
		this.checkName = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="checkTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getCheckTime() {
		return this.checkTime;
	}
	
	/**
	 * Set the checkTime
	 */	
	public void setCheckTime(java.util.Date aValue) {
		this.checkTime = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="modifyName" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getModifyName() {
		return this.modifyName;
	}
	
	/**
	 * Set the modifyName
	 */	
	public void setModifyName(String aValue) {
		this.modifyName = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="modifyTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getModifyTime() {
		return this.modifyTime;
	}
	
	/**
	 * Set the modifyTime
	 */	
	public void setModifyTime(java.util.Date aValue) {
		this.modifyTime = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="checkOpinion" type="java.lang.String" length="512" not-null="false" unique="false"
	 */
	public String getCheckOpinion() {
		return this.checkOpinion;
	}
	
	/**
	 * Set the checkOpinion
	 */	
	public void setCheckOpinion(String aValue) {
		this.checkOpinion = aValue;
	}	

	/**
	 * 0=²Ý¸å
            1=ÉóÅú
            2=Î´Í¨¹ýÉóÅú	 * @return Short
	 * @hibernate.property column="status" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getStatus() {
		return this.status;
	}
	
	/**
	 * Set the status
	 * @spring.validator type="required"
	 */	
	public void setStatus(Short aValue) {
		this.status = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="memo" type="java.lang.String" length="512" not-null="false" unique="false"
	 */
	public String getMemo() {
		return this.memo;
	}
	
	/**
	 * Set the memo
	 */	
	public void setMemo(String aValue) {
		this.memo = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof StandSalary)) {
			return false;
		}
		StandSalary rhs = (StandSalary) object;
		return new EqualsBuilder()
				.append(this.standardId, rhs.standardId)
				.append(this.standardNo, rhs.standardNo)
				.append(this.standardName, rhs.standardName)
				.append(this.totalMoney, rhs.totalMoney)
				.append(this.framer, rhs.framer)
				.append(this.setdownTime, rhs.setdownTime)
				.append(this.checkName, rhs.checkName)
				.append(this.checkTime, rhs.checkTime)
				.append(this.modifyName, rhs.modifyName)
				.append(this.modifyTime, rhs.modifyTime)
				.append(this.checkOpinion, rhs.checkOpinion)
				.append(this.status, rhs.status)
				.append(this.memo, rhs.memo)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.standardId) 
				.append(this.standardNo) 
				.append(this.standardName) 
				.append(this.totalMoney) 
				.append(this.framer) 
				.append(this.setdownTime) 
				.append(this.checkName) 
				.append(this.checkTime) 
				.append(this.modifyName) 
				.append(this.modifyTime) 
				.append(this.checkOpinion) 
				.append(this.status) 
				.append(this.memo) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("standardId", this.standardId) 
				.append("standardNo", this.standardNo) 
				.append("standardName", this.standardName) 
				.append("totalMoney", this.totalMoney) 
				.append("framer", this.framer) 
				.append("setdownTime", this.setdownTime) 
				.append("checkName", this.checkName) 
				.append("checkTime", this.checkTime) 
				.append("modifyName", this.modifyName) 
				.append("modifyTime", this.modifyTime) 
				.append("checkOpinion", this.checkOpinion) 
				.append("status", this.status) 
				.append("memo", this.memo) 
				.toString();
	}



}
