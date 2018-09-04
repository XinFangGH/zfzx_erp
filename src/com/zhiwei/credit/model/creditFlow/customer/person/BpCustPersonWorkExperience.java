package com.zhiwei.credit.model.creditFlow.customer.person;
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
 * BpCustPersonWorkExperience Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 个人客户-工作经历
 */
public class BpCustPersonWorkExperience extends com.zhiwei.core.model.BaseModel {

    protected Long workId;
//	protected Integer personId;
	protected java.util.Date workStartTime;
	protected java.util.Date workEndTime;
	protected String companyNature;
	protected String companyName;
	protected String duty;
	protected String companyBackground;
	protected Person person;

	/**
	 * Default Empty Constructor for class BpCustPersonWorkExperience
	 */
	public BpCustPersonWorkExperience () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class BpCustPersonWorkExperience
	 */
	public BpCustPersonWorkExperience (
		 Long in_workId
        ) {
		this.setWorkId(in_workId);
    }

    

	/**
	 * 工作经历信息编号，主键，自增	 * @return Long
     * @hibernate.id column="workId" type="java.lang.Long" generator-class="native"
	 */
	public Long getWorkId() {
		return this.workId;
	}
	
	/**
	 * Set the workId
	 */	
	public void setWorkId(Long aValue) {
		this.workId = aValue;
	}	

//	/**
//	 * 个人客户编号	 * @return Integer
//	 * @hibernate.property column="personId" type="java.lang.Integer" length="10" not-null="true" unique="false"
//	 */
//	public Integer getPersonId() {
//		return this.personId;
//	}
//	
//	/**
//	 * Set the personId
//	 * @spring.validator type="required"
//	 */	
//	public void setPersonId(Integer aValue) {
//		this.personId = aValue;
//	}	
	
	public Integer getPersonId(){
		return this.getPerson()==null?null:this.getPerson().getId();
	}
	
	public void setPersonId(Integer aValue){
		if(aValue==null){
			person = null;
		}else if(person == null){
			person = new Person(aValue);
		}else{
			person.setId(aValue);
		}
	}

	/**
	 * 工作开始时间	 * @return java.util.Date
	 * @hibernate.property column="workStartTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getWorkStartTime() {
		return this.workStartTime;
	}
	
	/**
	 * Set the workStartTime
	 */	
	public void setWorkStartTime(java.util.Date aValue) {
		this.workStartTime = aValue;
	}	

	/**
	 * 工作结束时间	 * @return java.util.Date
	 * @hibernate.property column="workEndTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getWorkEndTime() {
		return this.workEndTime;
	}
	
	/**
	 * Set the workEndTime
	 */	
	public void setWorkEndTime(java.util.Date aValue) {
		this.workEndTime = aValue;
	}	

	/**
	 * 单位性质	 * @return String
	 * @hibernate.property column="companyNature" type="java.lang.String" length="30" not-null="false" unique="false"
	 */
	public String getCompanyNature() {
		return this.companyNature;
	}
	
	/**
	 * Set the companyNature
	 */	
	public void setCompanyNature(String aValue) {
		this.companyNature = aValue;
	}	

	/**
	 * 单位名称	 * @return String
	 * @hibernate.property column="companyName" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	public String getCompanyName() {
		return this.companyName;
	}
	
	/**
	 * Set the companyName
	 */	
	public void setCompanyName(String aValue) {
		this.companyName = aValue;
	}	

	/**
	 * 职务	 * @return String
	 * @hibernate.property column="duty" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	public String getDuty() {
		return this.duty;
	}
	
	/**
	 * Set the duty
	 */	
	public void setDuty(String aValue) {
		this.duty = aValue;
	}	

	/**
	 * 单位背景	 * @return String
	 * @hibernate.property column="companyBackground" type="java.lang.String" length="1000" not-null="false" unique="false"
	 */
	public String getCompanyBackground() {
		return this.companyBackground;
	}
	
	/**
	 * Set the companyBackground
	 */	
	public void setCompanyBackground(String aValue) {
		this.companyBackground = aValue;
	}
	
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof BpCustPersonWorkExperience)) {
			return false;
		}
		BpCustPersonWorkExperience rhs = (BpCustPersonWorkExperience) object;
		return new EqualsBuilder()
				.append(this.workId, rhs.workId)
				.append(this.workStartTime, rhs.workStartTime)
				.append(this.workEndTime, rhs.workEndTime)
				.append(this.companyNature, rhs.companyNature)
				.append(this.companyName, rhs.companyName)
				.append(this.duty, rhs.duty)
				.append(this.companyBackground, rhs.companyBackground)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.workId) 
				.append(this.workStartTime) 
				.append(this.workEndTime) 
				.append(this.companyNature) 
				.append(this.companyName) 
				.append(this.duty) 
				.append(this.companyBackground) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("workId", this.workId) 
				.append("workStartTime", this.workStartTime) 
				.append("workEndTime", this.workEndTime) 
				.append("companyNature", this.companyNature) 
				.append("companyName", this.companyName) 
				.append("duty", this.duty) 
				.append("companyBackground", this.companyBackground) 
				.toString();
	}



}
