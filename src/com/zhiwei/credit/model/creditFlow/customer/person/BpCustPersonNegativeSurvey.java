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
 * BpCustPersonNegativeSurvey Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 个人客户-负面调查
 */
public class BpCustPersonNegativeSurvey extends com.zhiwei.core.model.BaseModel {

    protected Long negativeId;
//	protected Integer personId;
	protected java.util.Date negativeTime;
	protected String negativeExplain;
	protected Long userId;
	protected String negativeOperator;
	protected java.util.Date negativeEnteringTime;
	protected Person person;


	/**
	 * Default Empty Constructor for class BpCustPersonNegativeSurvey
	 */
	public BpCustPersonNegativeSurvey () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class BpCustPersonNegativeSurvey
	 */
	public BpCustPersonNegativeSurvey (
		 Long in_negativeId
        ) {
		this.setNegativeId(in_negativeId);
    }

    

	/**
	 * 负面信息编号，主键，自增	 * @return Long
     * @hibernate.id column="negativeId" type="java.lang.Long" generator-class="native"
	 */
	public Long getNegativeId() {
		return this.negativeId;
	}
	
	/**
	 * Set the negativeId
	 */	
	public void setNegativeId(Long aValue) {
		this.negativeId = aValue;
	}	

	/**
	 * 个人客户编号	 * @return Integer
	 * @hibernate.property column="personId" type="java.lang.Integer" length="10" not-null="true" unique="false"
	 */
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

	/**
	 * 负面信息出现时间	 * @return java.util.Date
	 * @hibernate.property column="negativeTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getNegativeTime() {
		return this.negativeTime;
	}
	
	/**
	 * Set the negativeTime
	 */	
	public void setNegativeTime(java.util.Date aValue) {
		this.negativeTime = aValue;
	}	

	/**
	 * 负面情况说明，即描述	 * @return String
	 * @hibernate.property column="negativeExplain" type="java.lang.String" length="500" not-null="false" unique="false"
	 */
	public String getNegativeExplain() {
		return this.negativeExplain;
	}
	
	/**
	 * Set the negetiveExplain
	 */	
	public void setNegativeExplain(String aValue) {
		this.negativeExplain = aValue;
	}	

	/**
	 * 负面录入人	 * @return Long
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
	 * 负面录入人名字	 * @return String
	 * @hibernate.property column="negativeOperator" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	public String getNegativeOperator() {
		return this.negativeOperator;
	}
	
	/**
	 * Set the negetiveOperator
	 */	
	public void setNegativeOperator(String aValue) {
		this.negativeOperator = aValue;
	}	

	/**
	 * 负面录入时间	 * @return java.util.Date
	 * @hibernate.property column="negetiveEnteringTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getNegativeEnteringTime() {
		return this.negativeEnteringTime;
	}
	
	/**
	 * Set the negetiveEnteringTime
	 */	
	public void setNegativeEnteringTime(java.util.Date aValue) {
		this.negativeEnteringTime = aValue;
	}	

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
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
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof BpCustPersonNegativeSurvey)) {
			return false;
		}
		BpCustPersonNegativeSurvey rhs = (BpCustPersonNegativeSurvey) object;
		return new EqualsBuilder()
				.append(this.negativeId, rhs.negativeId)
//				.append(this.personId, rhs.personId)
				.append(this.negativeTime, rhs.negativeTime)
				.append(this.negativeExplain, rhs.negativeExplain)
				.append(this.userId, rhs.userId)
				.append(this.negativeOperator, rhs.negativeOperator)
				.append(this.negativeEnteringTime, rhs.negativeEnteringTime)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.negativeId) 
//				.append(this.personId) 
				.append(this.negativeTime) 
				.append(this.negativeExplain) 
				.append(this.userId) 
				.append(this.negativeOperator) 
				.append(this.negativeEnteringTime) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("negativeId", this.negativeId) 
//				.append("personId", this.personId) 
				.append("negativeTime", this.negativeTime) 
				.append("negetiveExplain", this.negativeExplain) 
				.append("userId", this.userId) 
				.append("negetiveOperator", this.negativeOperator) 
				.append("negetiveEnteringTime", this.negativeEnteringTime) 
				.toString();
	}



}
