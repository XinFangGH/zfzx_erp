package com.zhiwei.credit.model.p2p;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

import com.google.gson.annotations.Expose;

/**
 * 
 * @author 
 *
 */
/**
 * P2pFriendlink Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class P2pFriendlink extends com.zhiwei.core.model.BaseModel {
    @Expose
    protected Long id;
    @Expose
	protected java.util.Date createDate;
    @Expose
	protected java.util.Date modifyDate;
    @Expose
	protected String logo;
    @Expose
	protected String name;
    @Expose
	protected Integer orderList;
    @Expose
	protected String url;
    @Expose
    protected Short linkType;
	/**
	 * 网站类别
	 */
    @Expose
	protected String webKey;
	/**
	 * 是否显示
	 */
    @Expose
	protected Short isShow;
    
	public String getWebKey() {
		return webKey;
	}

	public void setWebKey(String webKey) {
		this.webKey = webKey;
	}

	public Short getIsShow() {
		return isShow;
	}

	public void setIsShow(Short isShow) {
		this.isShow = isShow;
	}

	/**
	 * Default Empty Constructor for class P2pFriendlink
	 */
	public P2pFriendlink () {
		super();
	}
	
	public Short getLinkType() {
		return linkType;
	}

	public void setLinkType(Short linkType) {
		this.linkType = linkType;
	}

	/**
	 * Default Key Fields Constructor for class P2pFriendlink
	 */
	public P2pFriendlink (
		 Long in_id
        ) {
		this.setId(in_id);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="id" type="java.lang.Long" generator-class="native"
	 */
	public Long getId() {
		return this.id;
	}
	
	/**
	 * Set the id
	 */	
	public void setId(Long aValue) {
		this.id = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="createDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getCreateDate() {
		return this.createDate;
	}
	
	/**
	 * Set the createDate
	 */	
	public void setCreateDate(java.util.Date aValue) {
		this.createDate = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="modifyDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getModifyDate() {
		return this.modifyDate;
	}
	
	/**
	 * Set the modifyDate
	 */	
	public void setModifyDate(java.util.Date aValue) {
		this.modifyDate = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="logo" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getLogo() {
		return this.logo;
	}
	
	/**
	 * Set the logo
	 */	
	public void setLogo(String aValue) {
		this.logo = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="name" type="java.lang.String" length="255" not-null="true" unique="false"
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Set the name
	 * @spring.validator type="required"
	 */	
	public void setName(String aValue) {
		this.name = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="orderList" type="java.lang.Integer" length="10" not-null="true" unique="false"
	 */
	public Integer getOrderList() {
		return this.orderList;
	}
	
	/**
	 * Set the orderList
	 * @spring.validator type="required"
	 */	
	public void setOrderList(Integer aValue) {
		this.orderList = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="url" type="java.lang.String" length="255" not-null="true" unique="false"
	 */
	public String getUrl() {
		return this.url;
	}
	
	/**
	 * Set the url
	 * @spring.validator type="required"
	 */	
	public void setUrl(String aValue) {
		this.url = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof P2pFriendlink)) {
			return false;
		}
		P2pFriendlink rhs = (P2pFriendlink) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.createDate, rhs.createDate)
				.append(this.modifyDate, rhs.modifyDate)
				.append(this.logo, rhs.logo)
				.append(this.name, rhs.name)
				.append(this.orderList, rhs.orderList)
				.append(this.url, rhs.url)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.createDate) 
				.append(this.modifyDate) 
				.append(this.logo) 
				.append(this.name) 
				.append(this.orderList) 
				.append(this.url) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("createDate", this.createDate) 
				.append("modifyDate", this.modifyDate) 
				.append("logo", this.logo) 
				.append("name", this.name) 
				.append("orderList", this.orderList) 
				.append("url", this.url) 
				.toString();
	}



}
