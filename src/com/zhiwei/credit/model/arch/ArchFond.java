package com.zhiwei.credit.model.arch;

/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
 */
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

import com.google.gson.annotations.Expose;

/**
 * ArchFond Base Java Bean, base class for the.credit.model, mapped directly to
 * database table
 * 
 * Avoid changing this file if not necessary, will be overwritten.
 * 
 * TODO: add class/table comments
 */
public class ArchFond extends com.zhiwei.core.model.BaseModel {
	@Expose
	protected Long archFondId;
	@Expose
	protected String afNo;
	@Expose
	protected String afName;
	@Expose
	protected String shortDesc;
	@Expose
	protected String descp;
	@Expose
	protected String clearupDesc;
	@Expose
	protected java.util.Date createTime;

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.util.Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	@Expose
	protected java.util.Date updateTime;
	@Expose
	protected String creatorName;
	@Expose
	protected Long creatorId;
	@Expose
	protected Integer caseNums;
	@Expose
	protected Short status;
	@Expose
	protected String typeName;
	@Expose
	protected String openStyle;
	@Expose
	protected com.zhiwei.credit.model.system.GlobalType globalType;

	@Expose
	protected java.util.Set<com.zhiwei.credit.model.arch.ArchRoll> archRolls = new java.util.HashSet();
	@Expose
	protected java.util.Set<com.zhiwei.credit.model.arch.BorrowFileList> borrowFileList = new java.util.HashSet();

	public java.util.Set getBorrowFileList() {
		return borrowFileList;
	}

	public void setBorrowFileList(java.util.Set<com.zhiwei.credit.model.arch.BorrowFileList> borrowFileList) {
		this.borrowFileList = borrowFileList;
	}



	/**
	 * Default Empty Constructor for class ArchFond
	 */
	public ArchFond() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class ArchFond
	 */
	public ArchFond(Long in_archFondId) {
		this.setArchFondId(in_archFondId);
	}

	public com.zhiwei.credit.model.system.GlobalType getGlobalType() {
		return globalType;
	}

	public void setGlobalType(
			com.zhiwei.credit.model.system.GlobalType in_globalType) {
		this.globalType = in_globalType;
	}

	public java.util.Set getArchRolls() {
		return archRolls;
	}

	public void setArchRolls(java.util.Set in_archRolls) {
		this.archRolls = in_archRolls;
	}

	/**
	 * * @return Long
	 * 
	 * @hibernate.id column="archFondId" type="java.lang.Long"
	 *               generator-class="native"
	 */
	public Long getArchFondId() {
		return this.archFondId;
	}

	/**
	 * Set the archFondId
	 */
	public void setArchFondId(Long aValue) {
		this.archFondId = aValue;
	}

	/**
	 * 全宗号 * @return String
	 * 
	 * @hibernate.property column="afNo" type="java.lang.String" length="64"
	 *                     not-null="true" unique="false"
	 */
	public String getAfNo() {
		return this.afNo;
	}

	/**
	 * Set the afNo
	 * 
	 * @spring.validator type="required"
	 */
	public void setAfNo(String aValue) {
		this.afNo = aValue;
	}

	/**
	 * 全宗名 * @return String
	 * 
	 * @hibernate.property column="afName" type="java.lang.String" length="128"
	 *                     not-null="true" unique="false"
	 */
	public String getAfName() {
		return this.afName;
	}

	/**
	 * Set the afName
	 * 
	 * @spring.validator type="required"
	 */
	public void setAfName(String aValue) {
		this.afName = aValue;
	}

	/**
	 * 全宗概述 * @return String
	 * 
	 * @hibernate.property column="shortDesc" type="java.lang.String"
	 *                     length="4000" not-null="false" unique="false"
	 */
	public String getShortDesc() {
		return this.shortDesc;
	}

	/**
	 * Set the shortDesc
	 */
	public void setShortDesc(String aValue) {
		this.shortDesc = aValue;
	}

	/**
	 * 全宗描述 * @return String
	 * 
	 * @hibernate.property column="descp" type="java.lang.String" length="65535"
	 *                     not-null="false" unique="false"
	 */
	public String getDescp() {
		return this.descp;
	}

	/**
	 * Set the descp
	 */
	public void setDescp(String aValue) {
		this.descp = aValue;
	}

	/**
	 * 全宗整理描述 * @return String
	 * 
	 * @hibernate.property column="clearupDesc" type="java.lang.String"
	 *                     length="4000" not-null="false" unique="false"
	 */
	public String getClearupDesc() {
		return this.clearupDesc;
	}

	/**
	 * Set the clearupDesc
	 */
	public void setClearupDesc(String aValue) {
		this.clearupDesc = aValue;
	}

	/**
	 * 创建时间 * @return java.util.Date
	 * 
	 * @hibernate.property column="createtime" type="java.util.Date" length="19"
	 *                     not-null="false" unique="false"
	 */

	/**
	 * 创建人ID * @return Long
	 * 
	 * @hibernate.property column="creatorId" type="java.lang.Long" length="19"
	 *                     not-null="false" unique="false"
	 */
	public Long getCreatorId() {
		return this.creatorId;
	}

	/**
	 * Set the creatorId
	 */
	public void setCreatorId(Long aValue) {
		this.creatorId = aValue;
	}

	/**
	 * 案件数 * @return Integer
	 * 
	 * @hibernate.property column="caseNums" type="java.lang.Integer"
	 *                     length="10" not-null="false" unique="false"
	 */
	public Integer getCaseNums() {
		return this.caseNums;
	}

	/**
	 * Set the caseNums
	 */
	public void setCaseNums(Integer aValue) {
		this.caseNums = aValue;
	}

	/**
	 * 状态 * @return Short
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
	 * 全宗分类ID * @return Long
	 */
	public Long getProTypeId() {
		return this.getGlobalType() == null ? null : this.getGlobalType()
				.getProTypeId();
	}

	/**
	 * Set the proTypeId
	 */
	public void setProTypeId(Long aValue) {
		if (aValue == null) {
			globalType = null;
		} else if (globalType == null) {
			globalType = new com.zhiwei.credit.model.system.GlobalType(aValue);
			globalType.setVersion(new Integer(0));// set a version to cheat
													// hibernate only
		} else {
			globalType.setProTypeId(aValue);
		}
	}

	/**
	 * 全宗分类名称 * @return String
	 * 
	 * @hibernate.property column="typeName" type="java.lang.String"
	 *                     length="128" not-null="false" unique="false"
	 */
	public String getTypeName() {
		return this.typeName;
	}

	/**
	 * Set the typeName
	 */
	public void setTypeName(String aValue) {
		this.typeName = aValue;
	}

	/**
	 * 开放形式(来自数字字典) * @return String
	 * 
	 * @hibernate.property column="openStyle" type="java.lang.String"
	 *                     length="64" not-null="false" unique="false"
	 */
	public String getOpenStyle() {
		return this.openStyle;
	}

	/**
	 * Set the openStyle
	 */
	public void setOpenStyle(String aValue) {
		this.openStyle = aValue;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ArchFond)) {
			return false;
		}
		ArchFond rhs = (ArchFond) object;
		return new EqualsBuilder().append(this.archFondId, rhs.archFondId)
				.append(this.afNo, rhs.afNo).append(this.afName, rhs.afName)
				.append(this.shortDesc, rhs.shortDesc).append(this.descp,
						rhs.descp).append(this.clearupDesc, rhs.clearupDesc)
				.append(this.createTime, rhs.createTime).append(
						this.updateTime, rhs.updateTime).append(
						this.creatorName, rhs.creatorName).append(
						this.creatorId, rhs.creatorId).append(this.caseNums,
						rhs.caseNums).append(this.status, rhs.status).append(
						this.typeName, rhs.typeName).append(this.openStyle,
						rhs.openStyle).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(
				this.archFondId).append(this.afNo).append(this.afName).append(
				this.shortDesc).append(this.descp).append(this.clearupDesc)
				.append(this.createTime).append(this.updateTime).append(
						this.creatorName).append(this.creatorId).append(
						this.caseNums).append(this.status)
				.append(this.typeName).append(this.openStyle).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("archFondId", this.archFondId)
				.append("afNo", this.afNo).append("afName", this.afName)
				.append("shortDesc", this.shortDesc)
				.append("descp", this.descp).append("clearupDesc",
						this.clearupDesc).append("createtime", this.createTime)
				.append("updatetime", this.updateTime).append("creator",
						this.creatorName).append("creatorId", this.creatorId)
				.append("caseNums", this.caseNums)
				.append("status", this.status)
				.append("typeName", this.typeName).append("openStyle",
						this.openStyle).toString();
	}

}
