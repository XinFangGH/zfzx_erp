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
 * BpCustPersonEducation Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 个人客户-教育情况
 */
public class BpCustPersonEducation extends com.zhiwei.core.model.BaseModel {

    protected Long educationId;
//	protected Integer personId;
	protected java.util.Date educationStartTime;
	protected java.util.Date educationEndTime;
	protected String educationSchool;
	protected String degreeAwarded;
	protected String awards;
	protected String remarks;
	protected Person person;


	/**
	 * Default Empty Constructor for class BpCustPersonEducation
	 */
	public BpCustPersonEducation () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class BpCustPersonEducation
	 */
	public BpCustPersonEducation (
		 Long in_educationId
        ) {
		this.setEducationId(in_educationId);
    }

    

	/**
	 * 教育信息编号，主键，自增	 * @return Long
     * @hibernate.id column="educationId" type="java.lang.Long" generator-class="native"
	 */
	public Long getEducationId() {
		return this.educationId;
	}
	
	/**
	 * Set the educationId
	 */	
	public void setEducationId(Long aValue) {
		this.educationId = aValue;
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
	 * 教育开始时间	 * @return java.util.Date
	 * @hibernate.property column="educationStartTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getEducationStartTime() {
		return this.educationStartTime;
	}
	
	/**
	 * Set the educationStartTime
	 */	
	public void setEducationStartTime(java.util.Date aValue) {
		this.educationStartTime = aValue;
	}	

	/**
	 * 教育结束时间	 * @return java.util.Date
	 * @hibernate.property column="educationEndTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getEducationEndTime() {
		return this.educationEndTime;
	}
	
	/**
	 * Set the educationEndTime
	 */	
	public void setEducationEndTime(java.util.Date aValue) {
		this.educationEndTime = aValue;
	}	

	/**
	 * 就读学校	 * @return String
	 * @hibernate.property column="educationSchool" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	public String getEducationSchool() {
		return this.educationSchool;
	}
	
	/**
	 * Set the educationSchool
	 */	
	public void setEducationSchool(String aValue) {
		this.educationSchool = aValue;
	}	

	/**
	 * 所获学位	 * @return String
	 * @hibernate.property column="degreeAwarded" type="java.lang.String" length="30" not-null="false" unique="false"
	 */
	public String getDegreeAwarded() {
		return this.degreeAwarded;
	}
	
	/**
	 * Set the degreeAwarded
	 */	
	public void setDegreeAwarded(String aValue) {
		this.degreeAwarded = aValue;
	}	

	/**
	 * 所获奖励	 * @return String
	 * @hibernate.property column="awards" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getAwards() {
		return this.awards;
	}
	
	/**
	 * Set the awards
	 */	
	public void setAwards(String aValue) {
		this.awards = aValue;
	}	

	/**
	 * 备注	 * @return String
	 * @hibernate.property column="remarks" type="java.lang.String" length="200" not-null="false" unique="false"
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
		if (!(object instanceof BpCustPersonEducation)) {
			return false;
		}
		BpCustPersonEducation rhs = (BpCustPersonEducation) object;
		return new EqualsBuilder()
				.append(this.educationId, rhs.educationId)
				.append(this.educationStartTime, rhs.educationStartTime)
				.append(this.educationEndTime, rhs.educationEndTime)
				.append(this.educationSchool, rhs.educationSchool)
				.append(this.degreeAwarded, rhs.degreeAwarded)
				.append(this.awards, rhs.awards)
				.append(this.remarks, rhs.remarks)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.educationId) 
				.append(this.educationStartTime) 
				.append(this.educationEndTime) 
				.append(this.educationSchool) 
				.append(this.degreeAwarded) 
				.append(this.awards) 
				.append(this.remarks) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("educationId", this.educationId) 
				.append("educationStartTime", this.educationStartTime) 
				.append("educationEndTime", this.educationEndTime) 
				.append("educationSchool", this.educationSchool) 
				.append("degreeAwarded", this.degreeAwarded) 
				.append("awards", this.awards) 
				.append("remarks", this.remarks) 
				.toString();
	}



}
