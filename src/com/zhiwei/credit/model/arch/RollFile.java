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
 * RollFile Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class RollFile extends com.zhiwei.core.model.BaseModel {
	
	@Expose
    protected Long rollFileId;
	@Expose
    protected String typeName;
	@Expose
    protected String fileName;
	@Expose
    protected String fileNo;
	@Expose
    protected String dutyPerson;
	@Expose
    protected String afNo;
	@Expose
    protected String catNo;
	@Expose
    protected String rollNo;
	@Expose
    protected Integer seqNo;
	@Expose
    protected Integer pageNo;
	@Expose
    protected Integer pageNums;
	@Expose
    protected String secretLevel;
	@Expose
    protected String timeLimit;
	@Expose
    protected String openStyle;
	@Expose
    protected String keyWords;
	@Expose
    protected String notes;
	@Expose
    protected String content;
	@Expose
    protected java.util.Date fileTime;
	@Expose
    protected String creatorName;
	@Expose
    protected java.util.Date createTime;
	@Expose
	protected Short archStatus;
	@Expose
	protected com.zhiwei.credit.model.arch.ArchRoll archRoll;
	@Expose
	protected com.zhiwei.credit.model.system.GlobalType globalType;
	@Expose
	protected java.util.Set<com.zhiwei.credit.model.arch.BorrowFileList> borrowFileList = new java.util.HashSet();
	@Expose
	protected java.util.Set rollFileLists = new java.util.HashSet();
	@Expose
	protected String tidyName;
	@Expose
	protected java.util.Date tidyTime;
	
	public java.util.Set getBorrowFileList() {
		return borrowFileList;
	}

	public void setBorrowFileList(java.util.Set<com.zhiwei.credit.model.arch.BorrowFileList> borrowFileList) {
		this.borrowFileList = borrowFileList;
	}
	
	public String getTidyName() {
		return tidyName;
	}

	public void setTidyName(String tidyName) {
		this.tidyName = tidyName;
	}

	public java.util.Date getTidyTime() {
		return tidyTime;
	}

	public void setTidyTime(java.util.Date tidyTime) {
		this.tidyTime = tidyTime;
	}

	
	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}


	/**
	 * Default Empty Constructor for class RollFile
	 */
	public RollFile () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class RollFile
	 */
	public RollFile (
		 Long in_rollFileId
        ) {
		this.setRollFileId(in_rollFileId);
    }

	
	public com.zhiwei.credit.model.arch.ArchRoll getArchRoll () {
		return archRoll;
	}	
	
	public void setArchRoll (com.zhiwei.credit.model.arch.ArchRoll in_archRoll) {
		this.archRoll = in_archRoll;
	}
	
	public com.zhiwei.credit.model.system.GlobalType getGlobalType () {
		return globalType;
	}	
	
	public void setGlobalType (com.zhiwei.credit.model.system.GlobalType in_globalType) {
		this.globalType = in_globalType;
	}



	public java.util.Set getRollFileLists () {
		return rollFileLists;
	}	
	
	public void setRollFileLists (java.util.Set in_rollFileLists) {
		this.rollFileLists = in_rollFileLists;
	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="rollFileId" type="java.lang.Long" generator-class="native"
	 */
	public Long getRollFileId() {
		return this.rollFileId;
	}
	
	/**
	 * Set the rollFileId
	 */	
	public void setRollFileId(Long aValue) {
		this.rollFileId = aValue;
	}	

	/**
	 * 	 * @return Long
	 */
	public Long getProTypeId() {
		return this.getGlobalType()==null?null:this.getGlobalType().getProTypeId();
	}
	
	/**
	 * Set the proTypeId
	 */	
	public void setProTypeId(Long aValue) {
	    if (aValue==null) {
	    	globalType = null;
	    } else if (globalType == null) {
	        globalType = new com.zhiwei.credit.model.system.GlobalType(aValue);
	        globalType.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			globalType.setProTypeId(aValue);
	    }
	}	

	/**
	 * 分类名称	 * @return String
	 * @hibernate.property column="typeName" type="java.lang.String" length="128" not-null="false" unique="false"
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
	 * 案卷ID	 * @return Long
	 */
	public Long getRollId() {
		return this.getArchRoll()==null?null:this.getArchRoll().getRollId();
	}
	
	/**
	 * Set the rollId
	 */	
	public void setRollId(Long aValue) {
	    if (aValue==null) {
	    	archRoll = null;
	    } else if (archRoll == null) {
	        archRoll = new com.zhiwei.credit.model.arch.ArchRoll(aValue);
	        archRoll.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			archRoll.setRollId(aValue);
	    }
	}	

	/**
	 * 文件题名	 * @return String
	 * @hibernate.property column="fileName" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getFileName() {
		return this.fileName;
	}
	
	/**
	 * Set the fileName
	 * @spring.validator type="required"
	 */	
	public void setFileName(String aValue) {
		this.fileName = aValue;
	}	

	/**
	 * 文件编号	 * @return String
	 * @hibernate.property column="fileNo" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getFileNo() {
		return this.fileNo;
	}
	
	/**
	 * Set the fileNo
	 * @spring.validator type="required"
	 */	
	public void setFileNo(String aValue) {
		this.fileNo = aValue;
	}	

	/**
	 * 责任者	 * @return String
	 * @hibernate.property column="dutyPerson" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getDutyPerson() {
		return this.dutyPerson;
	}
	
	/**
	 * Set the dutyPerson
	 */	
	public void setDutyPerson(String aValue) {
		this.dutyPerson = aValue;
	}	

	/**
	 * 全宗号	 * @return String
	 * @hibernate.property column="afNo" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getAfNo() {
		return this.afNo;
	}
	
	/**
	 * Set the afNo
	 */	
	public void setAfNo(String aValue) {
		this.afNo = aValue;
	}	

	/**
	 * 目录号	 * @return String
	 * @hibernate.property column="catNo" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getCatNo() {
		return this.catNo;
	}
	
	/**
	 * Set the catNo
	 */	
	public void setCatNo(String aValue) {
		this.catNo = aValue;
	}	

	/**
	 * 案卷号	 * @return String
	 * @hibernate.property column="rollNo" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getRollNo() {
		return this.rollNo;
	}
	
	/**
	 * Set the rollNo
	 */	
	public void setRollNo(String aValue) {
		this.rollNo = aValue;
	}	

	/**
	 * 顺序号	 * @return Integer
	 * @hibernate.property column="seqNo" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getSeqNo() {
		return this.seqNo;
	}
	
	/**
	 * Set the seqNo
	 */	
	public void setSeqNo(Integer aValue) {
		this.seqNo = aValue;
	}	

	/**
	 * 页号	 * @return Integer
	 * @hibernate.property column="pageNo" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getPageNo() {
		return this.pageNo;
	}
	
	/**
	 * Set the pageNo
	 */	
	public void setPageNo(Integer aValue) {
		this.pageNo = aValue;
	}	

	/**
	 * 页数	 * @return Integer
	 * @hibernate.property column="pageNums" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getPageNums() {
		return this.pageNums;
	}
	
	/**
	 * Set the pageNums
	 */	
	public void setPageNums(Integer aValue) {
		this.pageNums = aValue;
	}	

	/**
	 * 密级	 * @return Short
	 * @hibernate.property column="secretLevel" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public String getSecretLevel() {
		return this.secretLevel;
	}
	
	/**
	 * Set the secretLevel
	 */	
	public void setSecretLevel(String aValue) {
		this.secretLevel = aValue;
	}	

	/**
	 * 保管期限	 * @return String
	 * @hibernate.property column="timeLimit" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getTimeLimit() {
		return this.timeLimit;
	}
	
	/**
	 * Set the timeLimit
	 */	
	public void setTimeLimit(String aValue) {
		this.timeLimit = aValue;
	}	

	/**
	 * 开放形式	 * @return String
	 * @hibernate.property column="openStyle" type="java.lang.String" length="64" not-null="false" unique="false"
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
	 * 附注	 * @return String
	 * @hibernate.property column="notes" type="java.lang.String" length="4000" not-null="false" unique="false"
	 */
	public String getNotes() {
		return this.notes;
	}
	
	/**
	 * Set the notes
	 */	
	public void setNotes(String aValue) {
		this.notes = aValue;
	}	

	/**
	 * 内容	 * @return String
	 * @hibernate.property column="content" type="java.lang.String" length="65535" not-null="false" unique="false"
	 */
	public String getContent() {
		return this.content;
	}
	
	/**
	 * Set the content
	 */	
	public void setContent(String aValue) {
		this.content = aValue;
	}	

	/**
	 * 文件时间	 * @return java.util.Date
	 * @hibernate.property column="fileTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getFileTime() {
		return this.fileTime;
	}
	
	/**
	 * Set the fileTime
	 */	
	public void setFileTime(java.util.Date aValue) {
		this.fileTime = aValue;
	}	


	/**
	 * 归档状态	 * @return Short
	 * @hibernate.property column="archStatus" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getArchStatus() {
		return this.archStatus;
	}
	
	/**
	 * Set the archStatus
	 */	
	public void setArchStatus(Short aValue) {
		this.archStatus = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof RollFile)) {
			return false;
		}
		RollFile rhs = (RollFile) object;
		return new EqualsBuilder()
				.append(this.rollFileId, rhs.rollFileId)
						.append(this.typeName, rhs.typeName)
						.append(this.fileName, rhs.fileName)
				.append(this.fileNo, rhs.fileNo)
				.append(this.dutyPerson, rhs.dutyPerson)
				.append(this.afNo, rhs.afNo)
				.append(this.catNo, rhs.catNo)
				.append(this.rollNo, rhs.rollNo)
				.append(this.seqNo, rhs.seqNo)
				.append(this.pageNo, rhs.pageNo)
				.append(this.pageNums, rhs.pageNums)
				.append(this.secretLevel, rhs.secretLevel)
				.append(this.timeLimit, rhs.timeLimit)
				.append(this.openStyle, rhs.openStyle)
				.append(this.keyWords, rhs.keyWords)
				.append(this.notes, rhs.notes)
				.append(this.content, rhs.content)
				.append(this.fileTime, rhs.fileTime)
				.append(this.creatorName, rhs.creatorName)
				.append(this.createTime, rhs.createTime)
				.append(this.archStatus, rhs.archStatus)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.rollFileId) 
						.append(this.typeName) 
						.append(this.fileName) 
				.append(this.fileNo) 
				.append(this.dutyPerson) 
				.append(this.afNo) 
				.append(this.catNo) 
				.append(this.rollNo) 
				.append(this.seqNo) 
				.append(this.pageNo) 
				.append(this.pageNums) 
				.append(this.secretLevel) 
				.append(this.timeLimit) 
				.append(this.openStyle) 
				.append(this.keyWords) 
				.append(this.notes) 
				.append(this.content) 
				.append(this.fileTime) 
				.append(this.creatorName) 
				.append(this.createTime) 
				.append(this.archStatus) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("rollFileId", this.rollFileId) 
						.append("typeName", this.typeName) 
						.append("fileName", this.fileName) 
				.append("fileNo", this.fileNo) 
				.append("dutyPerson", this.dutyPerson) 
				.append("afNo", this.afNo) 
				.append("catNo", this.catNo) 
				.append("rollNo", this.rollNo) 
				.append("seqNo", this.seqNo) 
				.append("pageNo", this.pageNo) 
				.append("pageNums", this.pageNums) 
				.append("secretLevel", this.secretLevel) 
				.append("timeLimit", this.timeLimit) 
				.append("openStyle", this.openStyle) 
				.append("keyWords", this.keyWords) 
				.append("notes", this.notes) 
				.append("content", this.content) 
				.append("fileTime", this.fileTime) 
				.append("creatorName", this.creatorName) 
				.append("createTime", this.createTime) 
				.append("archStatus", this.archStatus) 
				.toString();
	}



}
