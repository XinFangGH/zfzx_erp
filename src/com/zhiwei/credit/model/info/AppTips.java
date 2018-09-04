package com.zhiwei.credit.model.info;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

import com.google.gson.annotations.Expose;

/**
 * AppTips Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class AppTips extends com.zhiwei.core.model.BaseModel {

	@Expose
    protected Long tipsId;
	@Expose
	protected String tipsName;
	@Expose
	protected String content;
	@Expose
	protected Integer disheight;
	@Expose
	protected Integer diswidth;
	@Expose
	protected Integer disleft;
	@Expose
	protected Integer distop;
	@Expose
	protected Integer dislevel;
	@Expose
	protected java.util.Date createTime;
	protected com.zhiwei.credit.model.system.AppUser appUser;


	/**
	 * Default Empty Constructor for class AppTips
	 */
	public AppTips () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class AppTips
	 */
	public AppTips (
		 Long in_tipsId
        ) {
		this.setTipsId(in_tipsId);
    }

	
	public com.zhiwei.credit.model.system.AppUser getAppUser () {
		return appUser;
	}	
	
	public void setAppUser (com.zhiwei.credit.model.system.AppUser in_appUser) {
		this.appUser = in_appUser;
	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="tipsId" type="java.lang.Long" generator-class="native"
	 */
	public Long getTipsId() {
		return this.tipsId;
	}
	
	/**
	 * Set the tipsId
	 */	
	public void setTipsId(Long aValue) {
		this.tipsId = aValue;
	}	

	/**
	 * 主键	 * @return Long
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
	 * 	 * @return String
	 * @hibernate.property column="tipsName" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getTipsName() {
		return this.tipsName;
	}
	
	/**
	 * Set the tipsName
	 */	
	public void setTipsName(String aValue) {
		this.tipsName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="content" type="java.lang.String" length="1000" not-null="false" unique="false"
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
	 * 	 * @return Integer
	 * @hibernate.property column="disheight" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getDisheight() {
		return this.disheight;
	}
	
	/**
	 * Set the disheight
	 */	
	public void setDisheight(Integer aValue) {
		this.disheight = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="diswidth" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getDiswidth() {
		return this.diswidth;
	}
	
	/**
	 * Set the diswidth
	 */	
	public void setDiswidth(Integer aValue) {
		this.diswidth = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="disleft" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getDisleft() {
		return this.disleft;
	}
	
	/**
	 * Set the disleft
	 */	
	public void setDisleft(Integer aValue) {
		this.disleft = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="distop" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getDistop() {
		return this.distop;
	}
	
	/**
	 * Set the distop
	 */	
	public void setDistop(Integer aValue) {
		this.distop = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="dislevel" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getDislevel() {
		return this.dislevel;
	}
	
	/**
	 * Set the dislevel
	 */	
	public void setDislevel(Integer aValue) {
		this.dislevel = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="createTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	/**
	 * Set the createTime
	 */	
	public void setCreateTime(java.util.Date aValue) {
		this.createTime = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof AppTips)) {
			return false;
		}
		AppTips rhs = (AppTips) object;
		return new EqualsBuilder()
				.append(this.tipsId, rhs.tipsId)
						.append(this.tipsName, rhs.tipsName)
				.append(this.content, rhs.content)
				.append(this.disheight, rhs.disheight)
				.append(this.diswidth, rhs.diswidth)
				.append(this.disleft, rhs.disleft)
				.append(this.distop, rhs.distop)
				.append(this.dislevel, rhs.dislevel)
				.append(this.createTime, rhs.createTime)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.tipsId) 
						.append(this.tipsName) 
				.append(this.content) 
				.append(this.disheight) 
				.append(this.diswidth) 
				.append(this.disleft) 
				.append(this.distop) 
				.append(this.dislevel) 
				.append(this.createTime) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("tipsId", this.tipsId) 
						.append("tipsName", this.tipsName) 
				.append("content", this.content) 
				.append("disheight", this.disheight) 
				.append("diswidth", this.diswidth) 
				.append("disleft", this.disleft) 
				.append("distop", this.distop) 
				.append("dislevel", this.dislevel) 
				.append("createTime", this.createTime) 
				.toString();
	}



}
