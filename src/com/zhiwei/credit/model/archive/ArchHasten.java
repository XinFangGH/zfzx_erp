package com.zhiwei.credit.model.archive;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * ArchHasten Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class ArchHasten extends com.zhiwei.core.model.BaseModel {

    protected Long recordId;
	protected String content;
	protected java.util.Date createtime;
	protected String hastenFullname;
	protected String handlerFullname;
	protected Long handlerUserId;
	protected com.zhiwei.credit.model.archive.Archives archives;


	/**
	 * Default Empty Constructor for class ArchHasten
	 */
	public ArchHasten () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ArchHasten
	 */
	public ArchHasten (
		 Long in_recordId
        ) {
		this.setRecordId(in_recordId);
    }

	
	public com.zhiwei.credit.model.archive.Archives getArchives () {
		return archives;
	}	
	
	public void setArchives (com.zhiwei.credit.model.archive.Archives in_archives) {
		this.archives = in_archives;
	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="record" type="java.lang.Long" generator-class="native"
	 */
	public Long getRecordId() {
		return this.recordId;
	}
	
	/**
	 * Set the recordId
	 */	
	public void setRecordId(Long aValue) {
		this.recordId = aValue;
	}	

	/**
	 * 	 * @return Long
	 */
	public Long getArchivesId() {
		return this.getArchives()==null?null:this.getArchives().getArchivesId();
	}
	
	/**
	 * Set the archivesId
	 */	
	public void setArchivesId(Long aValue) {
	    if (aValue==null) {
	    	archives = null;
	    } else if (archives == null) {
	        archives = new com.zhiwei.credit.model.archive.Archives(aValue);
	        archives.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			archives.setArchivesId(aValue);
	    }
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="content" type="java.lang.String" length="1024" not-null="false" unique="false"
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
	 * 	 * @return java.util.Date
	 * @hibernate.property column="createtime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getCreatetime() {
		return this.createtime;
	}
	
	/**
	 * Set the createtime
	 */	
	public void setCreatetime(java.util.Date aValue) {
		this.createtime = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="hastenFullname" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getHastenFullname() {
		return this.hastenFullname;
	}
	
	/**
	 * Set the hastenFullname
	 */	
	public void setHastenFullname(String aValue) {
		this.hastenFullname = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="handlerFullname" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getHandlerFullname() {
		return this.handlerFullname;
	}
	
	/**
	 * Set the handlerFullname
	 */	
	public void setHandlerFullname(String aValue) {
		this.handlerFullname = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="handlerUserId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getHandlerUserId() {
		return this.handlerUserId;
	}
	
	/**
	 * Set the handlerUserId
	 */	
	public void setHandlerUserId(Long aValue) {
		this.handlerUserId = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ArchHasten)) {
			return false;
		}
		ArchHasten rhs = (ArchHasten) object;
		return new EqualsBuilder()
				.append(this.recordId, rhs.recordId)
						.append(this.content, rhs.content)
				.append(this.createtime, rhs.createtime)
				.append(this.hastenFullname, rhs.hastenFullname)
				.append(this.handlerFullname, rhs.handlerFullname)
				.append(this.handlerUserId, rhs.handlerUserId)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.recordId) 
						.append(this.content) 
				.append(this.createtime) 
				.append(this.hastenFullname) 
				.append(this.handlerFullname) 
				.append(this.handlerUserId) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("recordId", this.recordId) 
						.append("content", this.content) 
				.append("createtime", this.createtime) 
				.append("hastenFullname", this.hastenFullname) 
				.append("handlerFullname", this.handlerFullname) 
				.append("handlerUserId", this.handlerUserId) 
				.toString();
	}



}
