package com.zhiwei.credit.model.system;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * UserPosition Base Java Bean, base class for the.est.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 
 */
public class UserPosition extends com.zhiwei.core.model.BaseModel {
	
	/**
	 * 非主要
	 */
	public static final Long NOT_PRIMARY=0l;
	
    protected Long userPosId;
	protected Long isPrimary;
	protected com.zhiwei.credit.model.system.AppUser appUser;
	protected com.zhiwei.credit.model.system.Position position;

	
	//加上PosId,为了方便进行Json化处理
	private Long posId;
	
	/**
	 * Default Empty Constructor for class UserPosition
	 */
	public UserPosition () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class UserPosition
	 */
	public UserPosition (
		 Long in_userPosId
        ) {
		this.setUserPosId(in_userPosId);
    }

	
	public com.zhiwei.credit.model.system.AppUser getAppUser () {
		return appUser;
	}	
	
	public void setAppUser (com.zhiwei.credit.model.system.AppUser in_appUser) {
		this.appUser = in_appUser;
	}
	
	public com.zhiwei.credit.model.system.Position getPosition () {
		return position;
	}	
	
	public void setPosition (com.zhiwei.credit.model.system.Position in_position) {
		this.position = in_position;
	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="USER_POS_ID" type="java.lang.Long" generator-class="native"
	 */
	public Long getUserPosId() {
		return this.userPosId;
	}
	
	/**
	 * Set the userPosId
	 */	
	public void setUserPosId(Long aValue) {
		this.userPosId = aValue;
	}	

	/**
	 * 	 * @return Long
	 */
	public Long getPosId() {
		return this.posId;
//		return this.getPosition()==null?null:this.getPosition().getPosId();
	}
	
	/**
	 * Set the posId
	 */	
	public void setPosId(Long aValue) {
		this.posId=aValue;
//	    if (aValue==null) {
//	    	position = null;
//	    } else if (position == null) {
//	        position = new com.zhiwei.est.model.system.Position(aValue);
//	        position.setVersion(new Integer(0));//set a version to cheat hibernate only
//	    } else {
//	    	//
//			position.setPosId(aValue);
//	    }
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
	 * @hibernate.property column="ISPRIMARY" type="java.lang.Long" length="22" not-null="false" unique="false"
	 */
	public Long getIsPrimary() {
		return this.isPrimary;
	}
	
	/**
	 * Set the isPrimary
	 */	
	public void setIsPrimary(Long aValue) {
		this.isPrimary = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof UserPosition)) {
			return false;
		}
		UserPosition rhs = (UserPosition) object;
		return new EqualsBuilder()
				.append(this.userPosId, rhs.userPosId)
								.append(this.isPrimary, rhs.isPrimary)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.userPosId) 
								.append(this.isPrimary) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("userPosId", this.userPosId) 
								.append("isPrimary", this.isPrimary) 
				.toString();
	}



}
