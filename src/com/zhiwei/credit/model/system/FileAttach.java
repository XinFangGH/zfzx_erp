package com.zhiwei.credit.model.system;

/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
 */

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

import com.google.gson.annotations.Expose;

/**
 * @description FileAttach[附件信息]
 * @class FileAttach
 * @author 智维软件
 * @updater YHZ
 * @company www.credit-software.com
 * @createtime 2011-1-14AM
 * 
 */
@SuppressWarnings("serial")
public class FileAttach extends com.zhiwei.core.model.BaseModel {

	/**
	 * 删除标识,1=已删除
	 */
	public static final Integer FLAG_DEL = 1;
	/**
	 * 删除标识,0=未删除
	 */
	public static final Integer FLAG_NOT_DEL = 0;
	@Expose
	protected Long fileId;
	@Expose
	protected String fileName;
	@Expose
	protected String filePath;
	@Expose
	protected java.util.Date createtime;
	@Expose
	protected String ext;
	@Expose
	protected String fileType;
	@Expose
	protected String note;
	@Expose
	protected String creator;
	
	@Expose
	protected Long creatorId;
	@Expose
	protected Long totalBytes;
	@Expose
	protected Integer delFlag;
	protected GlobalType globalType;
	@Expose
	protected Integer csFileId;
	@Expose
	protected Integer csContractId;
	/**
	 * Default Empty Constructor for class FileAttach
	 */
	public FileAttach() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class FileAttach
	 */
	public FileAttach(Long in_fileId) {
		this.setFileId(in_fileId);
	}

	/**
	 * * @return Long
	 * 
	 * @hibernate.id column="fileId" type="java.lang.Long"
	 *               generator-class="native"
	 */
	
	
	public Long getFileId() {
		return this.fileId;
	}


	/**
	 * Set the fileId
	 */
	public void setFileId(Long aValue) {
		this.fileId = aValue;
	}

	/**
	 * 文件名 * @return String
	 * 
	 * @hibernate.property column="fileName" type="java.lang.String"
	 *                     length="128" not-null="true" unique="false"
	 */
	public String getFileName() {
		return this.fileName;
	}

	/**
	 * Set the fileName
	 * 
	 * @spring.validator type="required"
	 */
	public void setFileName(String aValue) {
		this.fileName = aValue;
	}

	/**
	 * 文件路径 * @return String
	 * 
	 * @hibernate.property column="filePath" type="java.lang.String"
	 *                     length="128" not-null="true" unique="false"
	 */
	public String getFilePath() {
		return this.filePath;
	}

	/**
	 * Set the filePath
	 * 
	 * @spring.validator type="required"
	 */
	public void setFilePath(String aValue) {
		this.filePath = aValue;
	}

	/**
	 * 创建时间 * @return java.util.Date
	 * 
	 * @hibernate.property column="createtime" type="java.util.Date" length="19"
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
	 * 扩展名 * @return String
	 * 
	 * @hibernate.property column="ext" type="java.lang.String" length="32"
	 *                     not-null="false" unique="false"
	 */
	public String getExt() {
		return this.ext;
	}

	/**
	 * Set the ext
	 */
	public void setExt(String aValue) {
		this.ext = aValue;
	}

	/**
	 * 附件类型 如：邮件附件 * @return String
	 * 
	 * @hibernate.property column="fileType" type="java.lang.String" length="32"
	 *                     not-null="true" unique="false"
	 */
	public String getFileType() {
		return this.fileType;
	}

	/**
	 * Set the type
	 * 
	 * @spring.validator type="required"
	 */
	public void setFileType(String aValue) {
		this.fileType = aValue;
	}

	/**
	 * 说明 * @return String
	 * 
	 * @hibernate.property column="note" type="java.lang.String" length="1024"
	 *                     not-null="false" unique="false"
	 */
	public String getNote() {
		return this.note;
	}

	/**
	 * Set the note
	 */
	public void setNote(String aValue) {
		this.note = aValue;
	}

	/**
	 * 上传者 * @return String
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

	public Long getTotalBytes() {
		return totalBytes;
	}

	public void setTotalBytes(Long totalBytes) {
		this.totalBytes = totalBytes;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public GlobalType getGlobalType() {
		return globalType;
	}

	public void setGlobalType(GlobalType globalType) {
		this.globalType = globalType;
	}

	public Integer getCsFileId() {
		return csFileId;
	}

	public void setCsFileId(Integer csFileId) {
		this.csFileId = csFileId;
	}
	

	public Integer getCsContractId() {
		return csContractId;
	}

	public void setCsContractId(Integer csContractId) {
		this.csContractId = csContractId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof FileAttach)) {
			return false;
		}
		FileAttach rhs = (FileAttach) object;
		return new EqualsBuilder().append(this.fileId, rhs.fileId).append(
				this.fileName, rhs.fileName)
				.append(this.filePath, rhs.filePath).append(this.createtime,
						rhs.createtime).append(this.ext, rhs.ext).append(
						this.fileType, rhs.fileType)
				.append(this.note, rhs.note).append(this.creator, rhs.creator)
				.append(this.delFlag, rhs.delFlag).append(this.creatorId,
						rhs.creatorId)
				.append(this.globalType, rhs.globalType).append(this.csFileId, rhs.csFileId).append(this.csContractId, rhs.csContractId)
						.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.fileId)
				.append(this.fileName).append(this.filePath).append(
						this.createtime).append(this.ext).append(this.fileType)
				.append(this.note).append(this.creator).append(this.delFlag)
				.append(this.creatorId)
				.append(this.globalType).append(this.csFileId).append(this.csContractId)
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("fileId", this.fileId).append(
				"fileName", this.fileName).append("filePath", this.filePath)
				.append("createtime", this.createtime).append("ext", this.ext)
				.append("type", this.fileType).append("note", this.note)
				.append("creator", this.creator).append("creatorId",
						this.creatorId).append("delFlag", this.delFlag)
				.append("globalType", this.globalType).append("csFileId",this.csFileId).append("csContractId",this.csContractId)
				.toString();
	}

	/**
	 * Return the name of the first key column
	 */
	public String getFirstKeyColumnName() {
		return "fileId";
	}

	/**
	 * Return the Id (pk) of the entity, must be Integer
	 */
	public Long getId() {
		return fileId;
	}

}
