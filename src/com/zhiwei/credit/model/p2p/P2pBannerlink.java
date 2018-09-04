package com.zhiwei.credit.model.p2p;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * 
 * @author 
 *
 */
/**
 * P2pBannerlink Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class P2pBannerlink extends com.zhiwei.core.model.BaseModel {

    protected Long id;
	protected java.util.Date createDate;
	protected java.util.Date modifyDate;
	protected String banner;
	protected String name;
	protected Integer orderList;
	protected String url;
	protected String urlLink;
	protected Integer type;
	protected Short linkType;
	protected Short isShow;
	protected String show;
	/**
	 * 网站类别
	 */
	protected String webKey;
	
	public String getWebKey() {
		return webKey;
	}

	public void setWebKey(String webKey) {
		this.webKey = webKey;
	}

	/**
	 * Default Empty Constructor for class P2pBannerlink
	 */
	public P2pBannerlink () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class P2pBannerlink
	 */
	public P2pBannerlink (
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
	 * 	 * @return Integer
	 * @hibernate.property column="type" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getType() {
		return this.type;
	}
	
	/**
	 * Set the type
	 */	
	public void setType(Integer aValue) {
		this.type = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="linkType" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getLinkType() {
		return this.linkType;
	}
	
	/**
	 * Set the linkType
	 */	
	public void setLinkType(Short aValue) {
		this.linkType = aValue;
	}	

	/**
	 * 是否显示	 * @return Short
	 * @hibernate.property column="isShow" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getIsShow() {
		return this.isShow;
	}
	
	/**
	 * Set the isShow
	 */	
	public void setIsShow(Short aValue) {
		this.isShow = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof P2pBannerlink)) {
			return false;
		}
		P2pBannerlink rhs = (P2pBannerlink) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.createDate, rhs.createDate)
				.append(this.modifyDate, rhs.modifyDate)
				.append(this.banner, rhs.banner)
				.append(this.name, rhs.name)
				.append(this.orderList, rhs.orderList)
				.append(this.url, rhs.url)
				.append(this.type, rhs.type)
				.append(this.linkType, rhs.linkType)
				.append(this.isShow, rhs.isShow)
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
				.append(this.banner) 
				.append(this.name) 
				.append(this.orderList) 
				.append(this.url) 
				.append(this.type) 
				.append(this.linkType) 
				.append(this.isShow) 
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
				.append("logo", this.banner) 
				.append("name", this.name) 
				.append("orderList", this.orderList) 
				.append("url", this.url) 
				.append("type", this.type) 
				.append("linkType", this.linkType) 
				.append("isShow", this.isShow) 
				.toString();
	}

	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	public String getShow() {
		return show;
	}

	public void setShow(String show) {
		this.show = show;
	}

	public String getUrlLink() {
		return urlLink;
	}

	public void setUrlLink(String urlLink) {
		this.urlLink = urlLink;
	}



}
