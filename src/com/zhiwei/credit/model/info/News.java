package com.zhiwei.credit.model.info;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

import com.google.gson.annotations.Expose;

/**
 * News Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * ������
 */
public class News extends com.zhiwei.core.model.BaseModel {

	@Expose
    protected Long newsId;
	@Expose
	protected String subjectIcon;
	protected String subjectIconId;
	@Expose
	protected String subject;
	@Expose
	protected String author;
	@Expose
	protected java.util.Date createtime;
	@Expose
	protected java.util.Date expTime;
	@Expose
	protected Integer replyCounts;
	@Expose
	protected Integer viewCounts;
	@Expose
	protected String issuer;
	@Expose
	protected String content;
	@Expose
	protected java.util.Date updateTime;
	@Expose
	protected Short status;
	@Expose
	protected Short isDeskImage;
	@Expose
	protected Short isNotice;
	@Expose
	protected Integer sn;
	@Expose
	protected com.zhiwei.credit.model.info.Section section;
	@Expose
	protected java.util.Set newsComments = new java.util.HashSet();

	
	public String getSubjectIconId() {
		return subjectIconId;
	}

	public void setSubjectIconId(String subjectIconId) {
		this.subjectIconId = subjectIconId;
	}

	/**
	 * Default Empty Constructor for class News
	 */
	public News () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class News
	 */
	public News (
		 Long in_newsId
        ) {
		this.setNewsId(in_newsId);
    }

	
	public com.zhiwei.credit.model.info.Section getSection () {
		return section;
	}	
	
	public void setSection (com.zhiwei.credit.model.info.Section in_section) {
		this.section = in_section;
	}

	public java.util.Set getNewsComments () {
		return newsComments;
	}	
	
	public void setNewsComments (java.util.Set in_newsComments) {
		this.newsComments = in_newsComments;
	}
    

	/**
	 * ID	 * @return Long
     * @hibernate.id column="newsId" type="java.lang.Long" generator-class="native"
	 */
	public Long getNewsId() {
		return this.newsId;
	}
	
	/**
	 * Set the newsId
	 */	
	public void setNewsId(Long aValue) {
		this.newsId = aValue;
	}	

	/**
	 * 	 * @return Long
	 */
	public Long getSectionId() {
		return this.getSection()==null?null:this.getSection().getSectionId();
	}
	
	/**
	 * Set the sectionId
	 */	
	public void setSectionId(Long aValue) {
	    if (aValue==null) {
	    	section = null;
	    } else if (section == null) {
	        section = new com.zhiwei.credit.model.info.Section(aValue);
	        section.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			section.setSectionId(aValue);
	    }
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="subjectIcon" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getSubjectIcon() {
		return this.subjectIcon;
	}
	
	/**
	 * Set the subjectIcon
	 */	
	public void setSubjectIcon(String aValue) {
		this.subjectIcon = aValue;
	}	

	/**
	 * 新闻标题	 * @return String
	 * @hibernate.property column="subject" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getSubject() {
		return this.subject;
	}
	
	/**
	 * Set the subject
	 * @spring.validator type="required"
	 */	
	public void setSubject(String aValue) {
		this.subject = aValue;
	}	

	/**
	 * 作者	 * @return String
	 * @hibernate.property column="author" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getAuthor() {
		return this.author;
	}
	
	/**
	 * Set the author
	 * @spring.validator type="required"
	 */	
	public void setAuthor(String aValue) {
		this.author = aValue;
	}	

	/**
	 * 创建时间	 * @return java.util.Date
	 * @hibernate.property column="createtime" type="java.util.Date" length="19" not-null="true" unique="false"
	 */
	public java.util.Date getCreatetime() {
		return this.createtime;
	}
	
	/**
	 * Set the createtime
	 * @spring.validator type="required"
	 */	
	public void setCreatetime(java.util.Date aValue) {
		this.createtime = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="expTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getExpTime() {
		return this.expTime;
	}
	
	/**
	 * Set the expTime
	 */	
	public void setExpTime(java.util.Date aValue) {
		this.expTime = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="replyCounts" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getReplyCounts() {
		return this.replyCounts;
	}
	
	/**
	 * Set the replyCounts
	 */	
	public void setReplyCounts(Integer aValue) {
		this.replyCounts = aValue;
	}	

	/**
	 * 浏览数	 * @return Integer
	 * @hibernate.property column="viewCounts" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getViewCounts() {
		return this.viewCounts;
	}
	
	/**
	 * Set the viewCounts
	 */	
	public void setViewCounts(Integer aValue) {
		this.viewCounts = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="issuer" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getIssuer() {
		return this.issuer;
	}
	
	/**
	 * Set the issuer
	 * @spring.validator type="required"
	 */	
	public void setIssuer(String aValue) {
		this.issuer = aValue;
	}	

	/**
	 * 内容	 * @return String
	 * @hibernate.property column="content" type="java.lang.String" length="65535" not-null="true" unique="false"
	 */
	public String getContent() {
		return this.content;
	}
	
	/**
	 * Set the content
	 * @spring.validator type="required"
	 */	
	public void setContent(String aValue) {
		this.content = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="updateTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
	
	/**
	 * Set the updateTime
	 */	
	public void setUpdateTime(java.util.Date aValue) {
		this.updateTime = aValue;
	}	

	/**
	 * 
            0=待审核
            1=审核通过	 * @return Short
	 * @hibernate.property column="status" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getStatus() {
		return this.status;
	}
	
	/**
	 * Set the status
	 * @spring.validator type="required"
	 */	
	public void setStatus(Short aValue) {
		this.status = aValue;
	}	

	/**
	 * 是否为桌面新闻	 * @return Short
	 * @hibernate.property column="isDeskImage" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getIsDeskImage() {
		return this.isDeskImage;
	}
	
	/**
	 * Set the isDeskImage
	 */	
	public void setIsDeskImage(Short aValue) {
		this.isDeskImage = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="isNotice" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getIsNotice() {
		return this.isNotice;
	}
	
	/**
	 * Set the isNotice
	 */	
	public void setIsNotice(Short aValue) {
		this.isNotice = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="sn" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getSn() {
		return this.sn;
	}
	
	/**
	 * Set the sn
	 */	
	public void setSn(Integer aValue) {
		this.sn = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof News)) {
			return false;
		}
		News rhs = (News) object;
		return new EqualsBuilder()
				.append(this.newsId, rhs.newsId)
						.append(this.subjectIcon, rhs.subjectIcon)
				.append(this.subject, rhs.subject)
				.append(this.author, rhs.author)
				.append(this.createtime, rhs.createtime)
				.append(this.expTime, rhs.expTime)
				.append(this.replyCounts, rhs.replyCounts)
				.append(this.viewCounts, rhs.viewCounts)
				.append(this.issuer, rhs.issuer)
				.append(this.content, rhs.content)
				.append(this.updateTime, rhs.updateTime)
				.append(this.status, rhs.status)
				.append(this.isDeskImage, rhs.isDeskImage)
				.append(this.isNotice, rhs.isNotice)
				.append(this.sn, rhs.sn)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.newsId) 
						.append(this.subjectIcon) 
				.append(this.subject) 
				.append(this.author) 
				.append(this.createtime) 
				.append(this.expTime) 
				.append(this.replyCounts) 
				.append(this.viewCounts) 
				.append(this.issuer) 
				.append(this.content) 
				.append(this.updateTime) 
				.append(this.status) 
				.append(this.isDeskImage) 
				.append(this.isNotice) 
				.append(this.sn) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("newsId", this.newsId) 
						.append("subjectIcon", this.subjectIcon) 
				.append("subject", this.subject) 
				.append("author", this.author) 
				.append("createtime", this.createtime) 
				.append("expTime", this.expTime) 
				.append("replyCounts", this.replyCounts) 
				.append("viewCounts", this.viewCounts) 
				.append("issuer", this.issuer) 
				.append("content", this.content) 
				.append("updateTime", this.updateTime) 
				.append("status", this.status) 
				.append("isDeskImage", this.isDeskImage) 
				.append("isNotice", this.isNotice) 
				.append("sn", this.sn) 
				.toString();
	}



}
