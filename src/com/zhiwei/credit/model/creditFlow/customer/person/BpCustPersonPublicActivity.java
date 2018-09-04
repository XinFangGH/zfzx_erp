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
 * BpCustPersonPublicActivity Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 个人客户-社会活动
 */
public class BpCustPersonPublicActivity extends com.zhiwei.core.model.BaseModel {

    protected Long activityId;
//	protected Integer personId;
	protected java.util.Date activityStartTime;
	protected java.util.Date activityEndTime;
	protected String companyNature;
	protected String companyName;
	protected String duty;
	protected String honorAndAchievement;
	protected Person person;

	/**
	 * Default Empty Constructor for class BpCustPersonPublicActivity
	 */
	public BpCustPersonPublicActivity () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class BpCustPersonPublicActivity
	 */
	public BpCustPersonPublicActivity (
		 Long in_activityId
        ) {
		this.setActivityId(in_activityId);
    }

    

	/**
	 * 社会活动信息编号，主键，自增	 * @return Long
     * @hibernate.id column="activityId" type="java.lang.Long" generator-class="native"
	 */
	public Long getActivityId() {
		return this.activityId;
	}
	
	/**
	 * Set the activityId
	 */	
	public void setActivityId(Long aValue) {
		this.activityId = aValue;
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
	 * 活动开始时间	 * @return java.util.Date
	 * @hibernate.property column="activityStartTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getActivityStartTime() {
		return this.activityStartTime;
	}
	
	/**
	 * Set the activityStartTime
	 */	
	public void setActivityStartTime(java.util.Date aValue) {
		this.activityStartTime = aValue;
	}	

	/**
	 * 活动结束时间	 * @return java.util.Date
	 * @hibernate.property column="activityEndTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getActivityEndTime() {
		return this.activityEndTime;
	}
	
	/**
	 * Set the activityEndTime
	 */	
	public void setActivityEndTime(java.util.Date aValue) {
		this.activityEndTime = aValue;
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
	 * 社会荣誉与学术成就	 * @return String
	 * @hibernate.property column="honorAndAchievement" type="java.lang.String" length="1000" not-null="false" unique="false"
	 */
	public String getHonorAndAchievement() {
		return this.honorAndAchievement;
	}
	
	/**
	 * Set the honorAndAchievement
	 */	
	public void setHonorAndAchievement(String aValue) {
		this.honorAndAchievement = aValue;
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
		if (!(object instanceof BpCustPersonPublicActivity)) {
			return false;
		}
		BpCustPersonPublicActivity rhs = (BpCustPersonPublicActivity) object;
		return new EqualsBuilder()
				.append(this.activityId, rhs.activityId)
//				.append(this.personId, rhs.personId)
				.append(this.activityStartTime, rhs.activityStartTime)
				.append(this.activityEndTime, rhs.activityEndTime)
				.append(this.companyNature, rhs.companyNature)
				.append(this.companyName, rhs.companyName)
				.append(this.duty, rhs.duty)
				.append(this.honorAndAchievement, rhs.honorAndAchievement)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.activityId) 
//				.append(this.personId) 
				.append(this.activityStartTime) 
				.append(this.activityEndTime) 
				.append(this.companyNature) 
				.append(this.companyName) 
				.append(this.duty) 
				.append(this.honorAndAchievement) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("activityId", this.activityId) 
//				.append("personId", this.personId) 
				.append("activityStartTime", this.activityStartTime) 
				.append("activityEndTime", this.activityEndTime) 
				.append("companyNature", this.companyNature) 
				.append("companyName", this.companyName) 
				.append("duty", this.duty) 
				.append("honorAndAchievement", this.honorAndAchievement) 
				.toString();
	}



}
