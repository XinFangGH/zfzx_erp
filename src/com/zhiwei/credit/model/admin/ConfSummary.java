package com.zhiwei.credit.model.admin;

/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
 */
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

import com.zhiwei.credit.model.system.FileAttach;

/**
 * @description conf_summary
 * @author YHZ
 * @date 2010-10-8 PM
 * 
 */
@SuppressWarnings("serial")
public class ConfSummary extends com.zhiwei.core.model.BaseModel {

	protected Conference confId;

	protected Long sumId;
	protected java.util.Date createtime;
	protected String creator;
	protected String sumContent;
	protected Short status;
	protected Set<FileAttach> attachFiles = new HashSet<FileAttach>();

	public Set<FileAttach> getAttachFiles() {
		return attachFiles;
	}

	public void setAttachFiles(Set<FileAttach> attachFiles) {
		this.attachFiles = attachFiles;
	}

	/**
	 * Default Empty Constructor for class ConfSummary
	 */
	public ConfSummary() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class ConfSummary
	 */
	public ConfSummary(Long in_sumId) {
		this.setSumId(in_sumId);
	}

	/**
	 * * @return Long
	 * 
	 * @hibernate.id column="sumId" type="java.lang.Integer"
	 *               generator-class="native"
	 */
	public Long getSumId() {
		return this.sumId;
	}

	/**
	 * Set the sumId
	 */
	public void setSumId(Long aValue) {
		this.sumId = aValue;
	}

	/**
	 * * @return Integer
	 * 
	 * @hibernate.property column="confId" type="java.lang.Integer" length="10"
	 *                     not-null="false" unique="false"
	 */
	public Conference getConfId() {
		return this.confId;
	}

	/**
	 * Set the confId
	 */
	public void setConfId(Conference conference) {
		this.confId = conference;
	}

	/**
	 * * @return java.util.Date
	 * 
	 * @hibernate.property column="createtime" type="java.util.Date" length="10"
	 *                     not-null="true" unique="false"
	 */
	public java.util.Date getCreatetime() {
		return this.createtime;
	}

	/**
	 * Set the createtime
	 * 
	 * @spring.validator type="required"
	 */
	public void setCreatetime(java.util.Date aValue) {
		this.createtime = aValue;
	}

	/**
	 * * @return String
	 * 
	 * @hibernate.property column="creator" type="java.lang.String" length="32"
	 *                     not-null="true" unique="false"
	 */
	public String getCreator() {
		return this.creator;
	}

	/**
	 * Set the creator
	 * 
	 * @spring.validator type="required"
	 */
	public void setCreator(String aValue) {
		this.creator = aValue;
	}

	/**
	 * * @return String
	 * 
	 * @hibernate.property column="sumContent" type="java.lang.String"
	 *                     length="65535" not-null="true" unique="false"
	 */
	public String getSumContent() {
		return this.sumContent;
	}

	/**
	 * Set the sumContent
	 * 
	 * @spring.validator type="required"
	 */
	public void setSumContent(String aValue) {
		this.sumContent = aValue;
	}

	/**
	 * * @return Short
	 * 
	 * @hibernate.property column="status" type="java.lang.Short" length="5"
	 *                     not-null="false" unique="false"
	 */
	public Short getStatus() {
		return this.status;
	}

	/**
	 * Set the status
	 */
	public void setStatus(Short aValue) {
		this.status = aValue;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ConfSummary)) {
			return false;
		}
		ConfSummary rhs = (ConfSummary) object;
		return new EqualsBuilder().append(this.sumId, rhs.sumId).append(
				this.confId, rhs.confId)
				.append(this.createtime, rhs.createtime).append(this.creator,
						rhs.creator).append(this.sumContent, rhs.sumContent)
				.append(this.status, rhs.status).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.sumId)
				.append(this.confId).append(this.createtime).append(
						this.creator).append(this.sumContent).append(
						this.status).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("sumId", this.sumId).append(
				"confId", this.confId).append("createtime", this.createtime)
				.append("creator", this.creator).append("sumContent",
						this.sumContent).append("status", this.status)
				.toString();
	}

}
