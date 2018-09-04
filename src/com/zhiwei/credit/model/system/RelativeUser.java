package com.zhiwei.credit.model.system;

/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
 */
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * @description 相对岗位人员管理
 * @author 智维软件
 * @company www.credit-software.com
 * @data 2010-12-13PM
 * 
 */
@SuppressWarnings("serial")
public class RelativeUser extends com.zhiwei.core.model.BaseModel {

	/**
	 * 上级标识
	 */
	public static Short SUPER_FLAG_TRUE = 1;
	/**
	 * 下级标识
	 */
	public static Short SUPER_FLAG_FALSE = 0;
	
	protected Long relativeUserId;
	protected Short isSuper;
	protected RelativeJob relativeJob;
	protected AppUser appUser;
	protected AppUser jobUser;

	/**
	 * Default Empty Constructor for class RelativeUser
	 */
	public RelativeUser() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class RelativeUser
	 */
	public RelativeUser(Long in_relativeUserId) {
		this.setRelativeUserId(in_relativeUserId);
	}

	public RelativeJob getRelativeJob() {
		return relativeJob;
	}

	public void setRelativeJob(RelativeJob in_relativeJob) {
		this.relativeJob = in_relativeJob;
	}

	public AppUser getAppUser() {
		return appUser;
	}

	public void setAppUser(AppUser in_appUser) {
		this.appUser = in_appUser;
	}

	/**
	 * ID * @return Long
	 * 
	 * @hibernate.id column="relativeUserId" type="java.lang.Long"
	 *               generator-class="native"
	 */
	public Long getRelativeUserId() {
		return this.relativeUserId;
	}

	/**
	 * Set the relativeUserId
	 */
	public void setRelativeUserId(Long aValue) {
		this.relativeUserId = aValue;
	}

	/**
	 * 所属相对岗位 * @return Long
	 */
	public Long getReJobId() {
		return this.getRelativeJob() == null ? null : this.getRelativeJob()
				.getReJobId();
	}

	/**
	 * Set the reJobId
	 */
	public void setReJobId(Long aValue) {
		if (aValue == null) {
			relativeJob = null;
		} else if (relativeJob == null) {
			relativeJob = new RelativeJob(aValue);
			relativeJob.setVersion(new Integer(0));// set a version to cheat
			// hibernate only
		} else {
			//
			relativeJob.setReJobId(aValue);
		}
	}

	/**
	 * 所属员工 * @return Long
	 */
	public Long getUserId() {
		return this.getAppUser() == null ? null : this.getAppUser().getUserId();
	}

	/**
	 * Set the userId
	 */
	public void setUserId(Long aValue) {
		if (aValue == null) {
			appUser = null;
		} else if (appUser == null) {
			appUser = new AppUser(aValue);
			appUser.setVersion(new Integer(0));// set a version to cheat
			// hibernate only
		} else {
			//
			appUser.setUserId(aValue);
		}
	}

	/**
	 * 上下级标识 1=上级 0=下级 * @return Short
	 * 
	 * @hibernate.property column="isSuper" type="java.lang.Short" length="5"
	 *                     not-null="false" unique="false"
	 */
	public Short getIsSuper() {
		return this.isSuper;
	}

	/**
	 * Set the isSuper
	 */
	public void setIsSuper(Short aValue) {
		this.isSuper = aValue;
	}

	public AppUser getJobUser() {
		return jobUser;
	}

	public void setJobUser(AppUser jobUser) {
		this.jobUser = jobUser;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof RelativeUser)) {
			return false;
		}
		RelativeUser rhs = (RelativeUser) object;
		return new EqualsBuilder().append(this.relativeUserId,
				rhs.relativeUserId).append(this.jobUser, rhs.jobUser).append(
				this.isSuper, rhs.isSuper).append(this.appUser, rhs.appUser)
				.append(this.relativeJob, rhs.relativeJob).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(
				this.relativeUserId).append(this.jobUser).append(this.isSuper)
				.append(this.appUser).append(this.relativeJob).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("relativeUserId",
				this.relativeUserId).append("jobUser", this.jobUser).append(
				"isSuper", this.isSuper).append("appUser", this.appUser)
				.append("relativeJob", this.relativeJob).toString();
	}

}
