package com.zhiwei.credit.model.hrm;

/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
 */
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * @description 岗位信息
 * @class Job
 * @author YHZ
 * @company www.credit-software.com
 * @data 2010-12-23AM
 * 
 */
@SuppressWarnings("serial")
public class Job extends com.zhiwei.core.model.BaseModel {

	public static short DELFLAG_NOT = (short) 0;
	public static short DELFLAG_HAD = (short) 1;

	protected Long jobId;
	protected String jobName;
	protected String memo;
	protected Short delFlag;
	protected Long parentId;
	protected String path;
	protected Long depth;

	@SuppressWarnings("unchecked")
	protected Set empProfiles = new HashSet();

	/**
	 * Default Empty Constructor for class Job
	 */
	public Job() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class Job
	 */
	public Job(Long in_jobId) {
		this.setJobId(in_jobId);
	}

	@SuppressWarnings("unchecked")
	public Set getEmpProfiles() {
		return empProfiles;
	}

	@SuppressWarnings("unchecked")
	public void setEmpProfiles(Set in_empProfiles) {
		this.empProfiles = in_empProfiles;
	}

	/**
	 * * @return Long
	 * 
	 * @hibernate.id column="jobId" type="java.lang.Long"
	 *               generator-class="native"
	 */
	public Long getJobId() {
		return this.jobId;
	}

	/**
	 * Set the jobId
	 */
	public void setJobId(Long aValue) {
		this.jobId = aValue;
	}

	/**
	 * 职位名称 * @return String
	 * 
	 * @hibernate.property column="jobName" type="java.lang.String" length="128"
	 *                     not-null="true" unique="false"
	 */
	public String getJobName() {
		return this.jobName;
	}

	/**
	 * Set the jobName
	 * 
	 * @spring.validator type="required"
	 */
	public void setJobName(String aValue) {
		this.jobName = aValue;
	}

	/**
	 * 备注 * @return String
	 * 
	 * @hibernate.property column="memo" type="java.lang.String" length="512"
	 *                     not-null="false" unique="false"
	 */
	public String getMemo() {
		return this.memo;
	}

	/**
	 * Set the memo
	 */
	public void setMemo(String aValue) {
		this.memo = aValue;
	}

	public Short getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Short delFlag) {
		this.delFlag = delFlag;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Long getDepth() {
		return depth;
	}

	public void setDepth(Long depth) {
		this.depth = depth;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Job))
			return false;
		Job rhs = (Job) object;
		return new EqualsBuilder().append(this.jobId, rhs.jobId).append(
				this.jobName, rhs.jobName).append(this.memo, rhs.memo).append(
				this.delFlag, rhs.delFlag).append(this.parentId, rhs.parentId)
				.append(this.path, rhs.path).append(this.depth, rhs.depth)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.jobId)
				.append(this.jobName).append(this.memo).append(this.delFlag)
				.append(this.parentId).append(this.path).append(this.depth)
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("jobId", this.jobId).append(
				"jobName", this.jobName).append("memo", this.memo).append(
				"delFlag", this.delFlag).append("parentId", this.parentId)
				.append("path", this.path).append("depth", this.depth)
				.toString();
	}

}
