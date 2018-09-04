package com.zhiwei.credit.model.system;
/*
 *  北京互融时代软件有限公司
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited Company.
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * UserOrg Base Java Bean, base class for the.est.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 
 */
public class UserOrg extends com.zhiwei.core.model.BaseModel {
	/**
	 * 主要
	 */
	public static final Short PRIMARY=1;
	/**
	 * 非主要
	 */
	public static final Short NOT_PRIMARY=0;
	
	/**
	 * 主负责
	 */
	public static final Short CHARGE=1;
	/**
	 * 非负责
	 */
	public static final Short NOT_CHARGE=0;

    protected Long userOrgId;
	protected Short isPrimary;
	protected Short isCharge;
	protected com.zhiwei.credit.model.system.AppUser appUser;
	protected com.zhiwei.credit.model.system.Organization organization;

	//加上orgId为了方便Json格式化处理
//	public Long orgId;
	
	/**
	 * Default Empty Constructor for class UserOrg
	 */
	public UserOrg () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class UserOrg
	 */
	public UserOrg (
		 Long in_userOrgId
        ) {
		this.setUserOrgId(in_userOrgId);
    }

	
	public com.zhiwei.credit.model.system.AppUser getAppUser () {
		return appUser;
	}	
	
	public void setAppUser (com.zhiwei.credit.model.system.AppUser in_appUser) {
		this.appUser = in_appUser;
	}
	
	public com.zhiwei.credit.model.system.Organization getOrganization () {
		return organization;
	}	
	
	public void setOrganization (com.zhiwei.credit.model.system.Organization in_organization) {
		this.organization = in_organization;
	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="USER_ORG_ID" type="java.lang.Long" generator-class="native"
	 */
	public Long getUserOrgId() {
		return this.userOrgId;
	}
	
	/**
	 * Set the userOrgId
	 */	
	public void setUserOrgId(Long aValue) {
		this.userOrgId = aValue;
	}	

	/**
	 * 	 * @return Long
	 */
	public Long getUserid() {
		return this.getAppUser()==null?null:this.getAppUser().getUserId();
	}
	
	/**
	 * Set the userid
	 */	
	public void setUserid(Long aValue) {
	    if (aValue==null) {
	    	appUser = null;
	    } else if (appUser == null) {
	        appUser = new com.zhiwei.credit.model.system.AppUser(aValue);
	        appUser.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
	    	//
			appUser.setUserId(aValue);
	    }
	}	

	/**
	 * 	 * @return Long
	 */
	public Long getOrgId() {
		//return this.getOrganization()==null?null:this.getOrganization().getOrgId();
		return orgId;
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
		
		orgId=aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="IS_PRIMARY" type="java.lang.Long" length="22" not-null="true" unique="false"
	 */
	public Short getIsPrimary() {
		return this.isPrimary;
	}
	
	/**
	 * Set the isPrimary
	 * @spring.validator type="required"
	 */	
	public void setIsPrimary(Short aValue) {
		this.isPrimary = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="IS_CHARGE" type="java.lang.Long" length="22" not-null="false" unique="false"
	 */
	public Short getIsCharge() {
		return this.isCharge;
	}
	
	/**
	 * Set the isCharge
	 */	
	public void setIsCharge(Short aValue) {
		this.isCharge = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof UserOrg)) {
			return false;
		}
		UserOrg rhs = (UserOrg) object;
		return new EqualsBuilder()
				.append(this.userOrgId, rhs.userOrgId)
								.append(this.isPrimary, rhs.isPrimary)
				.append(this.isCharge, rhs.isCharge)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.userOrgId) 
								.append(this.isPrimary) 
				.append(this.isCharge) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("userOrgId", this.userOrgId) 
								.append("isPrimary", this.isPrimary) 
				.append("isCharge", this.isCharge) 
				.toString();
	}



}
