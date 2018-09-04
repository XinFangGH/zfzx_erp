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
 * BpCustLoginlog Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 会员登录系统记录表
 */
public class BpCustLoginlog extends com.zhiwei.core.model.BaseModel {

    protected Long id;
	protected Integer type;
	protected String loginIp;
	protected java.util.Date loginTime;
	protected Long memberId;
	protected java.util.Date exitTime;
	protected String loginMemberName;

	public String getLoginMemberName() {
		return loginMemberName;
	}

	public void setLoginMemberName(String loginMemberName) {
		this.loginMemberName = loginMemberName;
	}

	/**
	 * Default Empty Constructor for class BpCustLoginlog
	 */
	public BpCustLoginlog () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class BpCustLoginlog
	 */
	public BpCustLoginlog (
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
	 * 动作类型（0登录，1退出）	 * @return Integer
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
	 * 登录IP	 * @return String
	 * @hibernate.property column="loginIp" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getLoginIp() {
		return this.loginIp;
	}
	
	/**
	 * Set the loginIp
	 */	
	public void setLoginIp(String aValue) {
		this.loginIp = aValue;
	}	

	/**
	 * 操作时间	 * @return java.util.Date
	 * @hibernate.property column="loginTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getLoginTime() {
		return this.loginTime;
	}
	
	/**
	 * Set the loginTime
	 */	
	public void setLoginTime(java.util.Date aValue) {
		this.loginTime = aValue;
	}	

	/**
	 * 	 * @return Long
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
	 * 	 * @return java.util.Date
	 * @hibernate.property column="exitTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getExitTime() {
		return this.exitTime;
	}
	
	/**
	 * Set the exitTime
	 */	
	public void setExitTime(java.util.Date aValue) {
		this.exitTime = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof BpCustLoginlog)) {
			return false;
		}
		BpCustLoginlog rhs = (BpCustLoginlog) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.type, rhs.type)
				.append(this.loginIp, rhs.loginIp)
				.append(this.loginTime, rhs.loginTime)
				.append(this.memberId, rhs.memberId)
				.append(this.exitTime, rhs.exitTime)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.type) 
				.append(this.loginIp) 
				.append(this.loginTime) 
				.append(this.memberId) 
				.append(this.exitTime) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("type", this.type) 
				.append("loginIp", this.loginIp) 
				.append("loginTime", this.loginTime) 
				.append("memberId", this.memberId) 
				.append("exitTime", this.exitTime) 
				.toString();
	}



}
