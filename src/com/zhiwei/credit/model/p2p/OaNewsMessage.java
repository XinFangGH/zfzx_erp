package com.zhiwei.credit.model.p2p;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
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
 * OaNewsMessage Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 站内信
 */
public class OaNewsMessage extends com.zhiwei.core.model.BaseModel {

    protected Long id;
    /**
     * 标题
     */
	protected String title;
	/**
	 * 内容
	 */
	protected String content;
	/**
	 * 发送时间
	 */
	protected java.util.Date sendTime;
	/**
	 * 接收人
	 */
	protected Long recipient;
	/**
	 * 操作人
	 */
	protected String operator;
	/**
	 * 发件人（全名）
	 */
	protected String addresser;
	/**
	 * 状态：0未发送，1已发送
	 */
	protected String status="0";
	/**
	 * 阅读时间
	 */
	protected java.util.Date readTime;
	/**
	 * 是否删除，1已删除，0未删除
	 */
	protected String isDelete="0";
	/**
	 * 接收人p2p用户名
	 */
	protected String comment1;
	/**
	 * 接收人p2pId
	 */
	protected String comment2;
	/**
	 * 备注
	 */
	protected String comment3;
	/**
	 * 类型 coupons=优惠券 null=普通
	 */
	protected String type;
	/**
	 * 类型名称
	 */
	protected String typename;
	/**
	 * 是否发送全部用户   0表示部分人员发送，1表示发送全员
	 */
	protected String isAllSend="0";


	/**
	 * Default Empty Constructor for class OaNewsMessage
	 */
	public OaNewsMessage () {
		super();
	}
	
	public String getIsAllSend() {
		return isAllSend;
	}

	public void setIsAllSend(String isAllSend) {
		this.isAllSend = isAllSend;
	}

	/**
	 * Default Key Fields Constructor for class OaNewsMessage
	 */
	public OaNewsMessage (
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
	 * 标题	 * @return String
	 * @hibernate.property column="title" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getTitle() {
		return this.title;
	}
	
	/**
	 * Set the title
	 */	
	public void setTitle(String aValue) {
		this.title = aValue;
	}	

	/**
	 * 内容	 * @return String
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
	 * 发送时间	 * @return java.util.Date
	 * @hibernate.property column="sendTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getSendTime() {
		return this.sendTime;
	}
	
	/**
	 * Set the sendTime
	 */	
	public void setSendTime(java.util.Date aValue) {
		this.sendTime = aValue;
	}	

	/**
	 * 接收人	 * @return Long
	 * @hibernate.property column="recipient" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getRecipient() {
		return this.recipient;
	}
	
	/**
	 * Set the recipient
	 */	
	public void setRecipient(Long aValue) {
		this.recipient = aValue;
	}	

	/**
	 * 操作人	 * @return String
	 * @hibernate.property column="operator" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getOperator() {
		return this.operator;
	}
	
	/**
	 * Set the operator
	 */	
	public void setOperator(String aValue) {
		this.operator = aValue;
	}	

	/**
	 * 发件人（全名）	 * @return String
	 * @hibernate.property column="addresser" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getAddresser() {
		return this.addresser;
	}
	
	/**
	 * Set the addresser
	 */	
	public void setAddresser(String aValue) {
		this.addresser = aValue;
	}	

	/**
	 * 状态（已读，未读）	 * @return String
	 * @hibernate.property column="status" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getStatus() {
		return this.status;
	}
	
	/**
	 * Set the status
	 */	
	public void setStatus(String aValue) {
		this.status = aValue;
	}	

	/**
	 * 阅读时间	 * @return java.util.Date
	 * @hibernate.property column="readTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getReadTime() {
		return this.readTime;
	}
	
	/**
	 * Set the readTime
	 */	
	public void setReadTime(java.util.Date aValue) {
		this.readTime = aValue;
	}	

	/**
	 * 是否删除	 * @return String
	 * @hibernate.property column="isDelete" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getIsDelete() {
		return this.isDelete;
	}
	
	/**
	 * Set the isDelete
	 */	
	public void setIsDelete(String aValue) {
		this.isDelete = aValue;
	}	

	/**
	 * 	 * @return String
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
	 * 	 * @return String
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
	 * 	 * @return String
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof OaNewsMessage)) {
			return false;
		}
		OaNewsMessage rhs = (OaNewsMessage) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.title, rhs.title)
				.append(this.content, rhs.content)
				.append(this.sendTime, rhs.sendTime)
				.append(this.recipient, rhs.recipient)
				.append(this.operator, rhs.operator)
				.append(this.addresser, rhs.addresser)
				.append(this.status, rhs.status)
				.append(this.readTime, rhs.readTime)
				.append(this.isDelete, rhs.isDelete)
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
				.append(this.title) 
				.append(this.content) 
				.append(this.sendTime) 
				.append(this.recipient) 
				.append(this.operator) 
				.append(this.addresser) 
				.append(this.status) 
				.append(this.readTime) 
				.append(this.isDelete) 
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
				.append("title", this.title) 
				.append("content", this.content) 
				.append("sendTime", this.sendTime) 
				.append("recipient", this.recipient) 
				.append("operator", this.operator) 
				.append("addresser", this.addresser) 
				.append("status", this.status) 
				.append("readTime", this.readTime) 
				.append("isDelete", this.isDelete) 
				.append("comment1", this.comment1) 
				.append("comment2", this.comment2) 
				.append("comment3", this.comment3) 
				.toString();
	}



}
