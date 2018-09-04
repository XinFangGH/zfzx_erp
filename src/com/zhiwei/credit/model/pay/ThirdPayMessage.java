package com.zhiwei.credit.model.pay;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
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
 * ThirdPayMessage Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 与第三方支付交互信息表
 */
public class ThirdPayMessage extends com.zhiwei.core.model.BaseModel {
	/** 
	 * 1充值  2提现 3注册 4投标 5放款 6资金释放 7审核 8转账 9绑定 10 授权
	 */
    public  static  final String TYPE1="1";
    public  static  final String TYPE2="2";
    public  static  final String TYPE3="3";
    public  static  final String TYPE4="4";
    public  static  final String TYPE5="5";
    public  static  final String TYPE6="6";
    public  static  final String TYPE7="7";
    public  static  final String TYPE8="8";
    public  static  final String TYPE9="9";
    public  static  final String TYPE10="10";

    protected Long id;
	protected String type;
	protected String typename;
	protected Date createTime;
	protected Long memberId;
	protected String resultcode;
	protected String jsonMessage;
	protected String comment1;
	protected String comment2;
	protected String comment3;


	/**
	 * Default Empty Constructor for class ThirdPayMessage
	 */
	public ThirdPayMessage () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class ThirdPayMessage
	 */
	public ThirdPayMessage (
		 Long in_id
        ) {
		this.setId(in_id);
    }

    

	/**
	 * 唯一标识符	 * @return Long
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
	 * 类型	 * @return String  1充值  2提现 3注册 4投标 5放款
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

	/**
	 * 接口名称	 * @return String
	 * @hibernate.property column="typename" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getTypename() {
		return this.typename;
	}
	
	/**
	 * Set the typename
	 */	
	public void setTypename(String aValue) {
		this.typename = aValue;
	}	

	/**
	 * 操作时间	 * @return String
	 * @hibernate.property column="createTime" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public Date getCreateTime() {
		return this.createTime;
	}
	
	/**
	 * Set the createTime
	 */	
	public void setCreateTime(Date aValue) {
		this.createTime = aValue;
	}	

	/**
	 * 操作用户	 * @return Long
	 * @hibernate.property column="memberId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getMemberId() {
		return this.memberId;
	}
	
	/**
	 * Set the memberId
	 */	
	public void setMemberId(Long aValue) {
		this.memberId = aValue;
	}	

	/**
	 * 返回状态（是否成功）	 * @return String
	 * @hibernate.property column="resultcode" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getResultcode() {
		return this.resultcode;
	}
	
	/**
	 * Set the resultcode
	 */	
	public void setResultcode(String aValue) {
		this.resultcode = aValue;
	}	

	/**
	 * 返回信息	 * @return String
	 * @hibernate.property column="jsonMessage" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getJsonMessage() {
		return this.jsonMessage;
	}
	
	/**
	 * Set the jsonMessage
	 */	
	public void setJsonMessage(String aValue) {
		this.jsonMessage = aValue;
	}	

	/**
	 * 注释1	 * @return String
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
	 * 注释2	 * @return String
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
	 * 注释3	 * @return String
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
		if (!(object instanceof ThirdPayMessage)) {
			return false;
		}
		ThirdPayMessage rhs = (ThirdPayMessage) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.type, rhs.type)
				.append(this.typename, rhs.typename)
				.append(this.createTime, rhs.createTime)
				.append(this.memberId, rhs.memberId)
				.append(this.resultcode, rhs.resultcode)
				.append(this.jsonMessage, rhs.jsonMessage)
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
				.append(this.typename) 
				.append(this.createTime) 
				.append(this.memberId) 
				.append(this.resultcode) 
				.append(this.jsonMessage) 
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
				.append("typename", this.typename) 
				.append("createTime", this.createTime) 
				.append("memberId", this.memberId) 
				.append("resultcode", this.resultcode) 
				.append("jsonMessage", this.jsonMessage) 
				.append("comment1", this.comment1) 
				.append("comment2", this.comment2) 
				.append("comment3", this.comment3) 
				.toString();
	}



}
