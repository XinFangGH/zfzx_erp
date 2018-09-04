package com.zhiwei.credit.model.p2p.article;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.gson.annotations.Expose;



/**
 * 
 * @author 
 *
 */
/**
 * Articlecategory Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class Articlecategory extends com.zhiwei.core.model.BaseModel {
	public static final String PATH_SEPARATOR = ",";// 树路径分隔符
	@Expose
	protected Long id;
	@Expose
    protected Long parentId;
	@Expose
	protected String parentName;//父类名称
	@Expose
	protected String parentKey;//父类key值
	/**
	 * 创建时间
	 */
	@Expose
	protected java.util.Date createDate;
	/**
	 * 修改时间
	 */
	@Expose
	protected java.util.Date modifyDate;
	/**
	 * 页面描述
	 */
	@Expose
	protected String metaDescription;
	/**
	 * 页面关键字
	 */
	@Expose
	protected String metaKeywords;
	/**
	 * 名称
	 */
	@Expose
	protected String name;
	/**
	 * 排序
	 */
	@Expose
	protected Integer orderList;
	@Expose
	protected String path;
	@Expose
	protected Integer depth;
	/**
	 * 1为新闻类类别 0为单页面类别
	 */
	@Expose
	protected Integer type=0;
	@Expose(serialize = false)
	protected java.util.Set articles = new java.util.HashSet();
	/**
	 *  分类key值（不允许重复）
	 * 新增字段
	 */
	@Expose
	protected String cateKey;
	/**
	 * 网站类别:网贷：P2P；云购  YunGou；众筹 Crowdfunding
	 * 新增字段
	 */
	@Expose
	protected String webKey;


	/**
	 * 是否显示：0不显示，1显示
	 * 新增字段
	 */
	@Expose
	protected String isShow;
    
	public String getParentKey() {
		return parentKey;
	}

	public void setParentKey(String parentKey) {
		this.parentKey = parentKey;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getCateKey() {
		return cateKey;
	}

	public void setCateKey(String cateKey) {
		this.cateKey = cateKey;
	}

	public String getWebKey() {
		return webKey;
	}

	public void setWebKey(String webKey) {
		this.webKey = webKey;
	}

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	/**
	 * 层次	 * @return Integer
	 * @hibernate.property column="depth" type="java.lang.Integer" length="10" not-null="true" unique="false"
	 */
	public Integer getDepth() {
		return this.depth;
	}
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * Set the depth
	 * @spring.validator type="required"
	 */	
	public void setDepth(Integer aValue) {
		this.depth = aValue;
	}	

	/**
	 * Default Empty Constructor for class Articlecategory
	 */
	public Articlecategory () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Articlecategory
	 */
	public Articlecategory (
		 Long in_id
        ) {
		this.setId(in_id);
    }



	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public java.util.Set getArticles () {
		return articles;
	}	
	
	public void setArticles (java.util.Set in_articles) {
		this.articles = in_articles;
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
	 * @hibernate.property column="metaDescription" type="java.lang.String" length="65535" not-null="false" unique="false"
	 */
	public String getMetaDescription() {
		return this.metaDescription;
	}
	
	/**
	 * Set the metaDescription
	 */	
	public void setMetaDescription(String aValue) {
		this.metaDescription = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="metaKeywords" type="java.lang.String" length="65535" not-null="false" unique="false"
	 */
	public String getMetaKeywords() {
		return this.metaKeywords;
	}
	
	/**
	 * Set the metaKeywords
	 */	
	public void setMetaKeywords(String aValue) {
		this.metaKeywords = aValue;
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
	 * @hibernate.property column="path" type="java.lang.String" length="65535" not-null="false" unique="false"
	 */
	public String getPath() {
		return this.path;
	}
	
	/**
	 * Set the path
	 */	
	public void setPath(String aValue) {
		this.path = aValue;
	}	


	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Articlecategory)) {
			return false;
		}
		Articlecategory rhs = (Articlecategory) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.createDate, rhs.createDate)
				.append(this.modifyDate, rhs.modifyDate)
				.append(this.metaDescription, rhs.metaDescription)
				.append(this.metaKeywords, rhs.metaKeywords)
				.append(this.name, rhs.name)
				.append(this.orderList, rhs.orderList)
				.append(this.path, rhs.path)
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
				.append(this.metaDescription) 
				.append(this.metaKeywords) 
				.append(this.name) 
				.append(this.orderList) 
				.append(this.path) 
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
				.append("metaDescription", this.metaDescription) 
				.append("metaKeywords", this.metaKeywords) 
				.append("name", this.name) 
				.append("orderList", this.orderList) 
				.append("path", this.path) 
						.toString();
	}



}
