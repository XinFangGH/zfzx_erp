package com.zhiwei.credit.model.p2p;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * @author 
 *
 */
/**
 * IndexShow Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 页面显示数据
 */
public class IndexShow extends com.zhiwei.core.model.BaseModel {

	protected Long id;
	/**
	 * 类型
	 * 1：p2p网站参数
	 * 2：众筹网站
	 * 3：一元夺宝网站参数
	 */
	protected String type;
	protected String typename;
	protected String description;
	protected java.util.Date insertTime;
	
	/**
	 * 一级类型
	 */
	protected String oneLevelType;
	/**
	 * 二级类型
	 */
	protected String twoLevelType;
	/**
	 * 修改日期
	 */
	protected java.util.Date updateTime;
	/**
	 * 网站类别
	 */
	protected String webKey;
	/**
	 * 是否显示
	 */
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
	 * Default Empty Constructor for class IndexShow
	 */
	public IndexShow () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class IndexShow
	 */
	public IndexShow (
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
	 * 类型（1： 2:  3:   4： 5：）	 * @return String
	 * @hibernate.property column="type" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getType() {
		return this.type;
	}
	
	/**
	 * Set the type
	 */	
	public void setType(String aValue) {
		this.type = aValue;
	}	

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	/**
	 * 内容	 * @return String
	 * @hibernate.property column="description" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getDescription() {
		return this.description;
	}
	
	/**
	 * Set the description
	 */	
	public void setDescription(String aValue) {
		this.description = aValue;
	}	

	/**
	 * 添加时间	 * @return java.util.Date
	 * @hibernate.property column="insertTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getInsertTime() {
		return this.insertTime;
	}
	
	/**
	 * Set the insertTime
	 */	
	public void setInsertTime(java.util.Date aValue) {
		this.insertTime = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof IndexShow)) {
			return false;
		}
		IndexShow rhs = (IndexShow) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.type, rhs.type)
				.append(this.description, rhs.description)
				.append(this.insertTime, rhs.insertTime)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.type) 
				.append(this.description) 
				.append(this.insertTime) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("type", this.type) 
				.append("description", this.description) 
				.append("insertTime", this.insertTime) 
				.toString();
	}
	
    public java.util.Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getOneLevelType() {
		return oneLevelType;
	}

	public void setOneLevelType(String oneLevelType) {
		this.oneLevelType = oneLevelType;
	}

	public String getTwoLevelType() {
		return twoLevelType;
	}

	public void setTwoLevelType(String twoLevelType) {
		this.twoLevelType = twoLevelType;
	}



}
