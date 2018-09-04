package com.zhiwei.credit.model.creditFlow.personrelation.phonecheck;
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
 * BpPersonPhonecheckInfo Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class BpPersonPhonecheckInfo extends com.zhiwei.core.model.BaseModel {

    protected Long id;
	protected Integer personRelationId;  //联系人Id
	protected Short isKnowLoan=0;
	protected String phoneText;
	protected Integer checkUserId;
	protected Long projectId;
	
	//不与数据库映射字段
	protected String personRelationName;//联系人姓名
	protected String relation;//关系
	protected String telphone;//手机号
	protected String checkUserName;


	/**
	 * Default Empty Constructor for class BpPersonPhonecheckInfo
	 */
	public BpPersonPhonecheckInfo () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class BpPersonPhonecheckInfo
	 */
	public BpPersonPhonecheckInfo (
		 Long in_id
        ) {
		this.setId(in_id);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="id" type="java.lang.Long" generator-class="native"
	 */
	public Long getId() {
		return this.id;
	}
	
	/**
	 * Set the id
	 */	
	public void setId(Long aValue) {
		this.id = aValue;
	}	

	/**
	 * 联系人信息表主键	 * @return Integer
	 * @hibernate.property column="personRelationId" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getPersonRelationId() {
		return this.personRelationId;
	}
	
	/**
	 * Set the personRelationId
	 */	
	public void setPersonRelationId(Integer aValue) {
		this.personRelationId = aValue;
	}	

	/**
	 * 是否知悉贷款 0知道  1不知道	 * @return Short
	 * @hibernate.property column="isKnowLoan" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getIsKnowLoan() {
		return this.isKnowLoan;
	}
	
	/**
	 * Set the isKnowLoan
	 */	
	public void setIsKnowLoan(Short aValue) {
		this.isKnowLoan = aValue;
	}	

	/**
	 * 电核内容	 * @return String
	 * @hibernate.property column="phoneText" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getPhoneText() {
		return this.phoneText;
	}
	
	/**
	 * Set the phoneText
	 */	
	public void setPhoneText(String aValue) {
		this.phoneText = aValue;
	}	

	/**
	 * 审核员Id	 * @return Integer
	 * @hibernate.property column="checkUserId" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getCheckUserId() {
		return this.checkUserId;
	}
	
	/**
	 * Set the checkUserId
	 */	
	public void setCheckUserId(Integer aValue) {
		this.checkUserId = aValue;
	}	

	/**
	 * 项目表主键	 * @return Long
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
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof BpPersonPhonecheckInfo)) {
			return false;
		}
		BpPersonPhonecheckInfo rhs = (BpPersonPhonecheckInfo) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.personRelationId, rhs.personRelationId)
				.append(this.isKnowLoan, rhs.isKnowLoan)
				.append(this.phoneText, rhs.phoneText)
				.append(this.checkUserId, rhs.checkUserId)
				.append(this.projectId, rhs.projectId)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.personRelationId) 
				.append(this.isKnowLoan) 
				.append(this.phoneText) 
				.append(this.checkUserId) 
				.append(this.projectId) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("personRelationId", this.personRelationId) 
				.append("isKnowLoan", this.isKnowLoan) 
				.append("phoneText", this.phoneText) 
				.append("checkUserId", this.checkUserId) 
				.append("projectId", this.projectId) 
				.toString();
	}

	public String getPersonRelationName() {
		return personRelationName;
	}

	public void setPersonRelationName(String personRelationName) {
		this.personRelationName = personRelationName;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getCheckUserName() {
		return checkUserName;
	}

	public void setCheckUserName(String checkUserName) {
		this.checkUserName = checkUserName;
	}



}
