package com.zhiwei.credit.model.web;
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
 * WebTemplateMessage Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 站内信模板配置表
 */
public class WebTemplateMessage extends com.zhiwei.core.model.BaseModel {

    protected Long id;
	protected Integer type;
	protected String name;
	protected String content;
	protected String comment1;
	protected String comment2;
	protected String comment3;


	/**
	 * Default Empty Constructor for class WebTemplateMessage
	 */
	public WebTemplateMessage () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class WebTemplateMessage
	 */
	public WebTemplateMessage (
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
	 * 类型（1邮件，2短信，3站内信）	 * @return Integer
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
	 * 站内信标题	 * @return String
	 * @hibernate.property column="name" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Set the name
	 */	
	public void setName(String aValue) {
		this.name = aValue;
	}	

	/**
	 * 站内信模板内容	 * @return String
	 * @hibernate.property column="content" type="java.lang.String" length="255" not-null="false" unique="false"
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
	 * 保留字段1	 * @return String
	 * @hibernate.property column="comment1" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getComment1() {
		return this.comment1;
	}
	
	/**
	 * Set the comment1
	 */	
	public void setComment1(String aValue) {
		this.comment1 = aValue;
	}	

	/**
	 * 保留字段2	 * @return String
	 * @hibernate.property column="comment2" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getComment2() {
		return this.comment2;
	}
	
	/**
	 * Set the comment2
	 */	
	public void setComment2(String aValue) {
		this.comment2 = aValue;
	}	

	/**
	 * 保留字段3	 * @return String
	 * @hibernate.property column="comment3" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getComment3() {
		return this.comment3;
	}
	
	/**
	 * Set the comment3
	 */	
	public void setComment3(String aValue) {
		this.comment3 = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof WebTemplateMessage)) {
			return false;
		}
		WebTemplateMessage rhs = (WebTemplateMessage) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.type, rhs.type)
				.append(this.name, rhs.name)
				.append(this.content, rhs.content)
				.append(this.comment1, rhs.comment1)
				.append(this.comment2, rhs.comment2)
				.append(this.comment3, rhs.comment3)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.type) 
				.append(this.name) 
				.append(this.content) 
				.append(this.comment1) 
				.append(this.comment2) 
				.append(this.comment3) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("type", this.type) 
				.append("name", this.name) 
				.append("content", this.content) 
				.append("comment1", this.comment1) 
				.append("comment2", this.comment2) 
				.append("comment3", this.comment3) 
				.toString();
	}



}
