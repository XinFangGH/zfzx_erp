package com.zhiwei.credit.model.info;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * NewsComment Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class NewsComment extends com.zhiwei.core.model.BaseModel {

    protected Long commentId;
	protected String content;
	protected java.util.Date createtime;
	protected String fullname;
	protected com.zhiwei.credit.model.info.News news;
	protected com.zhiwei.credit.model.system.AppUser appUser;


	/**
	 * Default Empty Constructor for class NewsComment
	 */
	public NewsComment () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class NewsComment
	 */
	public NewsComment (
		 Long in_commentId
        ) {
		this.setCommentId(in_commentId);
    }

	
	public com.zhiwei.credit.model.info.News getNews () {
		return news;
	}	
	
	public void setNews (com.zhiwei.credit.model.info.News in_news) {
		this.news = in_news;
	}
	
	public com.zhiwei.credit.model.system.AppUser getAppUser () {
		return appUser;
	}	
	
	public void setAppUser (com.zhiwei.credit.model.system.AppUser in_appUser) {
		this.appUser = in_appUser;
	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="commentId" type="java.lang.Long" generator-class="native"
	 */
	public Long getCommentId() {
		return this.commentId;
	}
	
	/**
	 * Set the commentId
	 */	
	public void setCommentId(Long aValue) {
		this.commentId = aValue;
	}	

	/**
	 * 	 * @return Long
	 */
	public Long getNewsId() {
		return this.getNews()==null?null:this.getNews().getNewsId();
	}
	
	/**
	 * Set the newsId
	 */	
	public void setNewsId(Long aValue) {
	    if (aValue==null) {
	    	news = null;
	    } else if (news == null) {
	        news = new com.zhiwei.credit.model.info.News(aValue);
	        news.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			news.setNewsId(aValue);
	    }
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="content" type="java.lang.String" length="500" not-null="true" unique="false"
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
	 * 	 * @return String
	 * @hibernate.property column="fullname" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getFullname() {
		return this.fullname;
	}
	
	/**
	 * Set the fullname
	 * @spring.validator type="required"
	 */	
	public void setFullname(String aValue) {
		this.fullname = aValue;
	}	

	/**
	 * 	 * @return Long
	 */
	public Long getUserId() {
		return this.getAppUser()==null?null:this.getAppUser().getUserId();
	}
	
	/**
	 * Set the userId
	 */	
	public void setUserId(Long aValue) {
	    if (aValue==null) {
	    	appUser = null;
	    } else if (appUser == null) {
	        appUser = new com.zhiwei.credit.model.system.AppUser(aValue);
	        appUser.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			appUser.setUserId(aValue);
	    }
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof NewsComment)) {
			return false;
		}
		NewsComment rhs = (NewsComment) object;
		return new EqualsBuilder()
				.append(this.commentId, rhs.commentId)
						.append(this.content, rhs.content)
				.append(this.createtime, rhs.createtime)
				.append(this.fullname, rhs.fullname)
						.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.commentId) 
						.append(this.content) 
				.append(this.createtime) 
				.append(this.fullname) 
						.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("commentId", this.commentId) 
						.append("content", this.content) 
				.append("createtime", this.createtime) 
				.append("fullname", this.fullname) 
						.toString();
	}



}
