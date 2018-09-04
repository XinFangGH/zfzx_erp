package com.zhiwei.credit.model.p2p;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Date;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * 
 * @author 
 *
 */
/**
 * OaNewsMessagerinfo Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class OaNewsMessagerinfo extends com.zhiwei.core.model.BaseModel {

    protected Long id;
    /**
     * 收件人Id
     */
	protected Long userId;
	/**
	 * P2P --P2P网站用户登陆客户类型
	 * APPUSER---ERP用户登陆客户类型.收件人用户类型
	 */
	protected String userType;
	/**
	 * 收件人用户名称
	 */
	protected String userName;
	/**
	 * 站内信息表Id
	 */
	protected Long messageId;
	/**
	 * 站内信状态
	 * 0    ---未发送
	 * 1    ---已删除
	 * 2    ---已发送
	 */
	protected Integer status=0;
	/**
	 * 0  ----未读
	 * 1  ----已读
	 */
	protected Integer readStatus=0;
	/**
	 * 阅读时间
	 */
	protected Date readTime;


	/**
	 * 只有
	 * 0  ----不置顶
	 * 1  ----置顶
	 */
	protected Integer istop=0;
	/**
	 * 置顶时间
	 */
	protected Date isTopTime;
	
	/**
	 * 数据库没有对应字段支持
	 */
	protected String title;//站内性标题
	protected String content;//站内性内容
	protected java.util.Date sendTime;//站内信发送时间
	protected String operator;//站内信操作人
	protected String addresser;//站内信发送人
	protected String typename;//站内信类型
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public java.util.Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(java.util.Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getAddresser() {
		return addresser;
	}

	public void setAddresser(String addresser) {
		this.addresser = addresser;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	


	/**
	 * Default Empty Constructor for class OaNewsMessagerinfo
	 */
	public OaNewsMessagerinfo () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class OaNewsMessagerinfo
	 */
	public OaNewsMessagerinfo (
		 Long in_id
        ) {
		this.setId(in_id);
    }

    

	/**
	 * 用户站内信Id	 * @return Long
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
	 * 用户id	 * @return Long
	 * @hibernate.property column="userId" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	public Long getUserId() {
		return this.userId;
	}
	
	/**
	 * Set the userId
	 * @spring.validator type="required"
	 */	
	public void setUserId(Long aValue) {
		this.userId = aValue;
	}	

	/**
	 * 用户类型，分为线上用户（p2p登陆用户）,erp登陆用户	 * @return String
	 * @hibernate.property column="userType" type="java.lang.String" length="255" not-null="true" unique="false"
	 */
	public String getUserType() {
		return this.userType;
	}
	
	/**
	 * Set the userType
	 * @spring.validator type="required"
	 */	
	public void setUserType(String aValue) {
		this.userType = aValue;
	}	

	/**
	 * 站内信Id	 * @return Long
	 * @hibernate.property column="messageId" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	public Long getMessageId() {
		return this.messageId;
	}
	
	/**
	 * Set the messageId
	 * @spring.validator type="required"
	 */	
	public void setMessageId(Long aValue) {
		this.messageId = aValue;
	}	

	/**
	 * 信件状态:0表示未发送，1表示删除，2已发送	 * @return Integer
	 * @hibernate.property column="status" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getStatus() {
		return this.status;
	}
	
	/**
	 * Set the status
	 */	
	public void setStatus(Integer aValue) {
		this.status = aValue;
	}	

	/**
	 * 阅读状态:0表示未读，1表示已读	 * @return Integer
	 * @hibernate.property column="readStatus" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getReadStatus() {
		return this.readStatus;
	}
	
	/**
	 * Set the readStatus
	 */	
	public void setReadStatus(Integer aValue) {
		this.readStatus = aValue;
	}	

	/**
	 * 是否置顶：0表示不置顶，1表示置顶	 * @return Integer
	 * @hibernate.property column="istop" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getIstop() {
		return this.istop;
	}
	
	/**
	 * Set the istop
	 */	
	public void setIstop(Integer aValue) {
		this.istop = aValue;
	}	

	public Date getReadTime() {
		return readTime;
	}

	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}

	public Date getIsTopTime() {
		return isTopTime;
	}

	public void setIsTopTime(Date isTopTime) {
		this.isTopTime = isTopTime;
	}
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof OaNewsMessagerinfo)) {
			return false;
		}
		OaNewsMessagerinfo rhs = (OaNewsMessagerinfo) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.userId, rhs.userId)
				.append(this.userType, rhs.userType)
				.append(this.messageId, rhs.messageId)
				.append(this.status, rhs.status)
				.append(this.readStatus, rhs.readStatus)
				.append(this.istop, rhs.istop)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.userId) 
				.append(this.userType) 
				.append(this.messageId) 
				.append(this.status) 
				.append(this.readStatus) 
				.append(this.istop) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("userId", this.userId) 
				.append("userType", this.userType) 
				.append("messageId", this.messageId) 
				.append("status", this.status) 
				.append("readStatus", this.readStatus) 
				.append("istop", this.istop) 
				.toString();
	}



}
