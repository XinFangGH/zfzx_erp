package com.zhiwei.credit.model.p2p.redMoney;
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
 * BpCustRedMember Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class BpCustRedMember extends com.zhiwei.core.model.BaseModel {

    protected Long redTopersonId;
    /**
     * 红包金额
     */
	protected java.math.BigDecimal redMoney;
	/**
	 * 已派发金额
	 */
	protected java.math.BigDecimal edredMoney;
	/**
	 * 主红包ID
	 */
	protected Long redId;
	/**
	 * 用户id
	 */
	protected Long bpCustMemberId;
	/**
	 * 用户名
	 */
	protected String bpCustMemberName;
	/**
	 * 派发时间
	 */
	protected java.util.Date distributeTime;
	/**
	 * 第三方对账流水号
	 */
	protected String requestNo;
	/**
	 * 系统生成的流水号
	 */
	protected String orderNo;
	
	//数据库没有对应字段
	protected String telphone;
	protected String email;
	protected String loginname;
	protected String truename;
	protected String thirdPayFlag0 ;
	/**
	 * 红包活动操作点记录
	 */
	private String intention;
	
	/**
	 * 红包类型
	 * 手动红包为null 
	 * 活动红包规则为 bpActivityManage
	 */
	protected String redType;  
	/**
	 * 活动编号
	 */
	protected String activityNumber;
	/**
	 * 活动说明
	 */
	protected String description;
	/**
	 * 红包发送类型，["邀请", "2"], ["投标", "3"], ["充值", "4"], ["邀请好友第一次投标", "5"]，
		["累计投资", "6"],["累计充值", "7"],["首投", "8"], ["累计推荐投资", "9"]
	 */
	protected String sendType;
	/**
	 * bp_cust_member表的登录名
	 */
	private String logginName;
	
	
	
	
	public String getIntention() {
		return intention;
	}

	public void setIntention(String intention) {
		this.intention = intention;
	}

	public String getLogginName() {
		return logginName;
	}

	public void setLogginName(String logginName) {
		this.logginName = logginName;
	}

	public String getRedType() {
		return redType;
	}

	public void setRedType(String redType) {
		this.redType = redType;
	}

	public String getActivityNumber() {
		return activityNumber;
	}

	public void setActivityNumber(String activityNumber) {
		this.activityNumber = activityNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Long getBpCustMemberId() {
		return bpCustMemberId;
	}

	public void setBpCustMemberId(Long bpCustMemberId) {
		this.bpCustMemberId = bpCustMemberId;
	}

	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}



	public java.util.Date getDistributeTime() {
		return distributeTime;
	}

	public void setDistributeTime(java.util.Date distributeTime) {
		this.distributeTime = distributeTime;
	}

	public java.math.BigDecimal getEdredMoney() {
		return edredMoney;
	}

	public void setEdredMoney(java.math.BigDecimal edredMoney) {
		this.edredMoney = edredMoney;
	}

	public String getThirdPayFlag0() {
		return thirdPayFlag0;
	}

	public void setThirdPayFlag0(String thirdPayFlag0) {
		this.thirdPayFlag0 = thirdPayFlag0;
	}

	/**
	 * Default Empty Constructor for class BpCustRedMember
	 */
	public BpCustRedMember () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class BpCustRedMember
	 */
	public BpCustRedMember (
		 Long in_redTopersonId
        ) {
		this.setRedTopersonId(in_redTopersonId);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="redTopersonId" type="java.lang.Long" generator-class="native"
	 */
	public Long getRedTopersonId() {
		return this.redTopersonId;
	}
	
	/**
	 * Set the redTopersonId
	 */	
	public void setRedTopersonId(Long aValue) {
		this.redTopersonId = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="redMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getRedMoney() {
		return this.redMoney;
	}
	
	/**
	 * Set the redMoney
	 */	
	public void setRedMoney(java.math.BigDecimal aValue) {
		this.redMoney = aValue;
	}	

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	/**
	 * 	 * @return Long
	 * @hibernate.property column="redId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getRedId() {
		return this.redId;
	}
	
	/**
	 * Set the redId
	 */	
	public void setRedId(Long aValue) {
		this.redId = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof BpCustRedMember)) {
			return false;
		}
		BpCustRedMember rhs = (BpCustRedMember) object;
		return new EqualsBuilder()
				.append(this.redTopersonId, rhs.redTopersonId)
				.append(this.redMoney, rhs.redMoney)
				.append(this.redId, rhs.redId)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.redTopersonId) 
				.append(this.redMoney) 
				.append(this.redId) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("redTopersonId", this.redTopersonId) 
				.append("redMoney", this.redMoney) 
				.append("redId", this.redId) 
				.toString();
	}

	public String getBpCustMemberName() {
		return bpCustMemberName;
	}

	public void setBpCustMemberName(String bpCustMemberName) {
		this.bpCustMemberName = bpCustMemberName;
	}
	
	
	


}
