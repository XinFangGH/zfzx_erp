package com.zhiwei.credit.model.admin;

/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
 */
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * @description conf_privilege
 * @author YHZ
 * @date 2010-10-8 PM
 * 
 */
public class ConfPrivilege extends com.zhiwei.core.model.BaseModel {

	private static final long serialVersionUID = 1L;

	protected Integer privilegeId;
	protected Long userId;
	protected Long confId;
	protected String fullname;
	protected Short rights;

	/**
	 * Default Empty Constructor for class ConfPrivilege
	 */
	public ConfPrivilege() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class ConfPrivilege
	 */
	public ConfPrivilege(Integer in_privilegeId) {
		this.setPrivilegeId(in_privilegeId);
	}

	/**
	 * * @return Integer
	 * 
	 * @hibernate.id column="privilegeId" type="java.lang.Integer"
	 *               generator-class="native"
	 */
	public Integer getPrivilegeId() {
		return this.privilegeId;
	}

	/**
	 * Set the privilegeId
	 */
	public void setPrivilegeId(Integer aValue) {
		this.privilegeId = aValue;
	}

	/**
	 * * @return Long
	 * 
	 * @hibernate.property column="confId" type="java.lang.Long" length="10"
	 *                     not-null="false" unique="false"
	 */
	public Long getConfId() {
		return this.confId;
	}

	/**
	 * Set the confId
	 */
	public void setConfId(Long confId) {
		this.confId = confId;
	}

	/**
	 * * @return Integer
	 * 
	 * @hibernate.property column="userId" type="java.lang.Long" length="10"
	 *                     not-null="true" unique="false"
	 */
	public Long getUserId() {
		return this.userId;
	}

	/**
	 * Set the userId
	 * 
	 * @spring.validator type="required"
	 */
	public void setUserId(Long aValue) {
		this.userId = aValue;
	}

	/**
	 * * @return String
	 * 
	 * @hibernate.property column="fullname" type="java.lang.String" length="64"
	 *                     not-null="true" unique="false"
	 */
	public String getFullname() {
		return this.fullname;
	}

	/**
	 * Set the fullname
	 * 
	 * @spring.validator type="required"
	 */
	public void setFullname(String aValue) {
		this.fullname = aValue;
	}

	/**
	 * * @return Short
	 * 
	 * @hibernate.property column="rights" type="java.lang.Short" length="5"
	 *                     not-null="true" unique="false"
	 */
	public Short getRights() {
		return this.rights;
	}

	/**
	 * Set the rights
	 * 
	 * @spring.validator type="required"
	 */
	public void setRights(Short aValue) {
		this.rights = aValue;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ConfPrivilege)) {
			return false;
		}
		ConfPrivilege rhs = (ConfPrivilege) object;
		return new EqualsBuilder().append(this.privilegeId, rhs.privilegeId)
				.append(this.confId, rhs.confId)
				.append(this.userId, rhs.userId).append(this.fullname,
						rhs.fullname).append(this.rights, rhs.rights)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(
				this.privilegeId).append(this.confId).append(this.userId)
				.append(this.fullname).append(this.rights).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("privilegeId", this.privilegeId).append("confId",
						this.confId).append("userId", this.userId).append(
						"fullname", this.fullname)
				.append("rights", this.rights).toString();
	}

}
