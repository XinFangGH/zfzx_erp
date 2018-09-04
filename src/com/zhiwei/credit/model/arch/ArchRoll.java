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
 * ArchRoll Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class ArchRoll extends com.zhiwei.core.model.BaseModel {
	@Expose
	protected Integer fileNums;
	@Expose
	protected Long archFondId;
	@Expose
    protected Long rollId;
	@Expose
    protected String typeName;
	@Expose
    protected String rolllName;
	@Expose
    protected String afNo;
	@Expose
    protected String rollNo;
	@Expose
    protected String catNo;
	@Expose
    protected String timeLimit;
	@Expose
    protected java.util.Date startTime;
	@Expose
    protected java.util.Date endTime;
	@Expose
    protected String openStyle;
	@Expose
    protected String author;
	@Expose
    protected java.util.Date setupTime;
	@Expose
    protected String checker;
	@Expose
    protected String creatorName;
	@Expose
    protected java.util.Date createTime;
	@Expose
    protected String keyWords;
	@Expose
    protected String editCompany;
	@Expose
    protected String editDep;
	@Expose
    protected String decp;
	@Expose
    protected Short status;
	@Expose
    protected com.zhiwei.credit.model.arch.ArchFond archFond;
	@Expose
    protected com.zhiwei.credit.model.system.GlobalType globalType;
	@Expose
    protected java.util.Set<com.zhiwei.credit.model.arch.BorrowFileList> borrowFileList = new java.util.HashSet();
	@Expose
    protected java.util.Set rollFiles = new java.util.HashSet();
	
	
	
	
	
	
	
	public Integer getFileNums() {
		return fileNums;
	}

	public void setFileNums(Integer fileNums) {
		this.fileNums = fileNums;
	}
	public java.util.Set getBorrowFileList() {
		return borrowFileList;
	}

	public void setBorrowFileList(java.util.Set<com.zhiwei.credit.model.arch.BorrowFileList> borrowFileList) {
		this.borrowFileList = borrowFileList;
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

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}
	/**
	 * Default Empty Constructor for class ArchRoll
	 */
	public ArchRoll () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ArchRoll
	 */
	public ArchRoll (
		 Long in_rollId
        ) {
		this.setRollId(in_rollId);
    }

	
	public com.zhiwei.credit.model.arch.ArchFond getArchFond () {
		return archFond;
	}	
	
	public void setArchFond (com.zhiwei.credit.model.arch.ArchFond in_archFond) {
		this.archFond = in_archFond;
	}
	
	public com.zhiwei.credit.model.system.GlobalType getGlobalType () {
		return globalType;
	}	
	
	public void setGlobalType (com.zhiwei.credit.model.system.GlobalType in_globalType) {
		this.globalType = in_globalType;
	}



	public java.util.Set getRollFiles () {
		return rollFiles;
	}	
	
	public void setRollFiles (java.util.Set in_rollFiles) {
		this.rollFiles = in_rollFiles;
	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="rollId" type="java.lang.Long" generator-class="native"
	 */
	public Long getRollId() {
		return this.rollId;
	}
	
	/**
	 * Set the rollId
	 */	
	public void setRollId(Long aValue) {
		this.rollId = aValue;
	}	

	/**
	 * 案卷分类ID	 * @return Long
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
	 * 全宗ID	 * @return Long
	 */
	public Long getArchFondId() {
		return this.getArchFond()==null?null:this.getArchFond().getArchFondId();
	}
	
	/**
	 * Set the archFondId
	 */	
	public void setArchFondId(Long aValue) {
	    if (aValue==null) {
	    	archFond = null;
	    } else if (archFond == null) {
	        archFond = new com.zhiwei.credit.model.arch.ArchFond(aValue);
	        archFond.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			archFond.setArchFondId(aValue);
	    }
	}	

	/**
	 * 案卷分类名称	 * @return String
	 * @hibernate.property column="typeName" type="java.lang.String" length="32" not-null="false" unique="false"
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
	 * 案卷名称	 * @return String
	 * @hibernate.property column="rolllName" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getRolllName() {
		return this.rolllName;
	}
	
	/**
	 * Set the rolllName
	 * @spring.validator type="required"
	 */	
	public void setRolllName(String aValue) {
		this.rolllName = aValue;
	}	

	/**
	 * 全宗号	 * @return String
	 * @hibernate.property column="afNo" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getAfNo() {
		return this.afNo;
	}
	
	/**
	 * Set the afNo
	 * @spring.validator type="required"
	 */	
	public void setAfNo(String aValue) {
		this.afNo = aValue;
	}	

	/**
	 * 案卷号	 * @return String
	 * @hibernate.property column="rollNo" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getRollNo() {
		return this.rollNo;
	}
	
	/**
	 * Set the rollNo
	 * @spring.validator type="required"
	 */	
	public void setRollNo(String aValue) {
		this.rollNo = aValue;
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
	 * 保管期限
            	 * @return String
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
	 * 起始日期	 * @return java.util.Date
	 * @hibernate.property column="startTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getStartTime() {
		return this.startTime;
	}
	
	/**
	 * Set the startTime
	 */	
	public void setStartTime(java.util.Date aValue) {
		this.startTime = aValue;
	}	

	/**
	 * 结束日期	 * @return java.util.Date
	 * @hibernate.property column="endTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getEndTime() {
		return this.endTime;
	}
	
	/**
	 * Set the endTime
	 */	
	public void setEndTime(java.util.Date aValue) {
		this.endTime = aValue;
	}	

	/**
	 * 开放形式	 * @return Short
	 * @hibernate.property column="openStyle" type="java.lang.Short" length="5" not-null="false" unique="false"
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
	 * 立卷人	 * @return String
	 * @hibernate.property column="author" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getAuthor() {
		return this.author;
	}
	
	/**
	 * Set the author
	 */	
	public void setAuthor(String aValue) {
		this.author = aValue;
	}	

	/**
	 * 立卷时间	 * @return java.util.Date
	 * @hibernate.property column="setupTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getSetupTime() {
		return this.setupTime;
	}
	
	/**
	 * Set the setupTime
	 */	
	public void setSetupTime(java.util.Date aValue) {
		this.setupTime = aValue;
	}	

	/**
	 * 检查人	 * @return String
	 * @hibernate.property column="checker" type="java.lang.String" length="32" not-null="false" unique="false"
	 */
	public String getChecker() {
		return this.checker;
	}
	
	/**
	 * Set the checker
	 */	
	public void setChecker(String aValue) {
		this.checker = aValue;
	}	





	/**
	 * 	 * @return String
	 * @hibernate.property column="editCompany" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getEditCompany() {
		return this.editCompany;
	}
	
	/**
	 * Set the editCompany
	 */	
	public void setEditCompany(String aValue) {
		this.editCompany = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="editDep" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getEditDep() {
		return this.editDep;
	}
	
	/**
	 * Set the editDep
	 */	
	public void setEditDep(String aValue) {
		this.editDep = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="decp" type="java.lang.String" length="65535" not-null="false" unique="false"
	 */
	public String getDecp() {
		return this.decp;
	}
	
	/**
	 * Set the decp
	 */	
	public void setDecp(String aValue) {
		this.decp = aValue;
	}	

	/**
	 * 状态	 * @return Short
	 * @hibernate.property column="status" type="java.lang.Short" length="5" not-null="false" unique="false"
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
		if (!(object instanceof ArchRoll)) {
			return false;
		}
		ArchRoll rhs = (ArchRoll) object;
		return new EqualsBuilder()
				.append(this.rollId, rhs.rollId)
								.append(this.typeName, rhs.typeName)
				.append(this.rolllName, rhs.rolllName)
				.append(this.afNo, rhs.afNo)
				.append(this.rollNo, rhs.rollNo)
				.append(this.catNo, rhs.catNo)
				.append(this.timeLimit, rhs.timeLimit)
				.append(this.startTime, rhs.startTime)
				.append(this.endTime, rhs.endTime)
				.append(this.openStyle, rhs.openStyle)
				.append(this.author, rhs.author)
				.append(this.setupTime, rhs.setupTime)
				.append(this.checker, rhs.checker)
				.append(this.creatorName, rhs.creatorName)
				.append(this.createTime, rhs.createTime)
				.append(this.keyWords, rhs.keyWords)
				.append(this.editCompany, rhs.editCompany)
				.append(this.editDep, rhs.editDep)
				.append(this.decp, rhs.decp)
				.append(this.status, rhs.status)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.rollId) 
								.append(this.typeName) 
				.append(this.rolllName) 
				.append(this.afNo) 
				.append(this.rollNo) 
				.append(this.catNo) 
				.append(this.timeLimit) 
				.append(this.startTime) 
				.append(this.endTime) 
				.append(this.openStyle) 
				.append(this.author) 
				.append(this.setupTime) 
				.append(this.checker) 
				.append(this.creatorName) 
				.append(this.createTime) 
				.append(this.keyWords) 
				.append(this.editCompany) 
				.append(this.editDep) 
				.append(this.decp) 
				.append(this.status) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("rollId", this.rollId) 
								.append("typeName", this.typeName) 
				.append("rolllName", this.rolllName) 
				.append("afNo", this.afNo) 
				.append("rollNo", this.rollNo) 
				.append("catNo", this.catNo) 
				.append("timeLimit", this.timeLimit) 
				.append("startTime", this.startTime) 
				.append("endTime", this.endTime) 
				.append("openStyle", this.openStyle) 
				.append("author", this.author) 
				.append("setupTime", this.setupTime) 
				.append("checker", this.checker) 
				.append("creatorName", this.creatorName) 
				.append("createTime", this.createTime) 
				.append("keyWords", this.keyWords) 
				.append("editCompany", this.editCompany) 
				.append("editDep", this.editDep) 
				.append("decp", this.decp) 
				.append("status", this.status) 
				.toString();
	}



}
