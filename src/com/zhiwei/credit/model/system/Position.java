package com.zhiwei.credit.model.system;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * Position Base Java Bean, base class for the.est.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 
 */
public class Position extends com.zhiwei.core.model.BaseModel {

    protected Long posId;
	protected String posName;
	protected String posDesc;
	protected Long posSupId;
	protected String path;
	protected Long depth;
	protected com.zhiwei.credit.model.system.Organization organization;

	transient protected java.util.Set mainPositionSubs = new java.util.HashSet();
	transient protected java.util.Set subPositionSubs = new java.util.HashSet();
	transient protected java.util.Set roles = new java.util.HashSet();
	transient protected java.util.Set userPositions = new java.util.HashSet();

	/**
	 * Default Empty Constructor for class Position
	 */
	public Position () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Position
	 */
	public Position (
		 Long in_posId
        ) {
		this.setPosId(in_posId);
    }

	
	public com.zhiwei.credit.model.system.Organization getOrganization () {
		return organization;
	}	
	
	public void setOrganization (com.zhiwei.credit.model.system.Organization in_organization) {
		this.organization = in_organization;
	}

	public java.util.Set getMainPositionSubs () {
		return mainPositionSubs;
	}	
	
	public void setMainPositionSubs (java.util.Set in_mainPositionSubs) {
		this.mainPositionSubs = in_mainPositionSubs;
	}
	
	public java.util.Set getSubPositionSubs () {
		return subPositionSubs;
	}	
	
	public void setSubPositionSubs (java.util.Set in_subPositionSubs) {
		this.subPositionSubs = in_subPositionSubs;
	}

	public java.util.Set getRoles () {
		return roles;
	}	
	
	public void setRoles (java.util.Set in_rolePositions) {
		this.roles = in_rolePositions;
	}

	public java.util.Set getUserPositions () {
		return userPositions;
	}	
	
	public void setUserPositions (java.util.Set in_userPositions) {
		this.userPositions = in_userPositions;
	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="POS_ID" type="java.lang.Long" generator-class="native"
	 */
	public Long getPosId() {
		return this.posId;
	}
	
	/**
	 * Set the posId
	 */	
	public void setPosId(Long aValue) {
		this.posId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="POS_NAME" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getPosName() {
		return this.posName;
	}
	
	/**
	 * Set the posName
	 * @spring.validator type="required"
	 */	
	public void setPosName(String aValue) {
		this.posName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="POS_DESC" type="java.lang.String" length="1024" not-null="false" unique="false"
	 */
	public String getPosDesc() {
		return this.posDesc;
	}
	
	/**
	 * Set the posDesc
	 */	
	public void setPosDesc(String aValue) {
		this.posDesc = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="POS_SUP_ID" type="java.lang.Long" length="18" not-null="false" unique="false"
	 */
	public Long getPosSupId() {
		return this.posSupId;
	}
	
	/**
	 * Set the posSupId
	 */	
	public void setPosSupId(Long aValue) {
		this.posSupId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="PATH" type="java.lang.String" length="256" not-null="false" unique="false"
	 */
	public String getPath() {
		return this.path;
	}
	
	/**
	 * Set the path
	 */	
	public void setPath(String aValue) {
		this.path = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="DEPTH" type="java.lang.Long" length="22" not-null="false" unique="false"
	 */
	public Long getDepth() {
		return this.depth;
	}
	
	/**
	 * Set the depth
	 */	
	public void setDepth(Long aValue) {
		this.depth = aValue;
	}	

	/**
	 * 	 * @return Long
	 */
	public Long getOrgId() {
		return this.getOrganization()==null?null:this.getOrganization().getOrgId();
	}
	
	/**
	 * Set the orgId
	 */	
	public void setOrgId(Long aValue) {
	    if (aValue==null) {
	    	organization = null;
	    } else if (organization == null) {
	        organization = new com.zhiwei.credit.model.system.Organization(aValue);
	        organization.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
	    	//
			organization.setOrgId(aValue);
	    }
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Position)) {
			return false;
		}
		Position rhs = (Position) object;
		return new EqualsBuilder()
				.append(this.posId, rhs.posId)
				.append(this.posName, rhs.posName)
				.append(this.posDesc, rhs.posDesc)
				.append(this.posSupId, rhs.posSupId)
				.append(this.path, rhs.path)
				.append(this.depth, rhs.depth)
						.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.posId) 
				.append(this.posName) 
				.append(this.posDesc) 
				.append(this.posSupId) 
				.append(this.path) 
				.append(this.depth) 
						.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("posId", this.posId) 
				.append("posName", this.posName) 
				.append("posDesc", this.posDesc) 
				.append("posSupId", this.posSupId) 
				.append("path", this.path) 
				.append("depth", this.depth) 
						.toString();
	}



}
