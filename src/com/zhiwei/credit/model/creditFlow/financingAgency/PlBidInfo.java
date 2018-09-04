package com.zhiwei.credit.model.creditFlow.financingAgency;
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
 * PlBidInfo Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 投标情况表
 */
public class PlBidInfo extends com.zhiwei.core.model.BaseModel {
	//0生成订单 属于未完成订单  ，等付款成功后 修改 状态   1 等待放款  2 还款中 3完成  4失败
	public static final short TYPE0=Short.valueOf("0");
	public static final short TYPE1=Short.valueOf("1");
	public static final short TYPE2=Short.valueOf("2");
	public static final short TYPE3=Short.valueOf("3");
	public static final short TYPE4=Short.valueOf("4");
	/**
	 * 主键id
	 */
	protected Long id;
	/**
	 * 用户名
	 */
	protected String userName;
	/**
	 * 用户id，bp_cust_member的id
	 */
	protected Long userId;
	/**
	 * 标名
	 */
	protected String bidName;
	/**
	 * 投资金额
	 */
	protected java.math.BigDecimal userMoney;
	/**
	 * 投标时间
	 */
	protected java.util.Date bidtime;
	protected com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan plBidPlan;
	/**
	 * 转账订单号
	 */
	@Expose
	protected String orderNo; //转账订单号
	/**
	 * 使用优惠券ID
	 */
	@Expose
	protected Long couponId; //使用优惠券ID
	/**
	 * 标的状态
	 */
	@Expose
	protected Short state;
	/**
	 * 富友，预授权合同号，双乾第三方投标流水号
	 */
	protected String preAuthorizationNum;//富友，预授权合同号
	/**
	 * 新的投资人的id
	 */
	protected Long newInvestPersonId;
	/**
	 * 新的投资人姓名
	 */
	protected String newInvestPersonName;
	/**
	 * 新的订单号
	 */
	protected String newOrderNo;
	/**
	 * 资金冻结的唯一标识(汇付)
	 */
	protected String freezeTrxId;
	protected String trxId;//平台交易唯一标识号
	public String getTrxId() {
		return trxId;
	}
	public void setTrxId(String trxId) {
		this.trxId = trxId;
	}
	public String getFreezeTrxId() {
		return freezeTrxId;
	}
	public void setFreezeTrxId(String freezeTrxId) {
		this.freezeTrxId = freezeTrxId;
	}

	/**
	 * 是否到债券库， null是没有生成到债权库的，1是生成到债权库去了
	 */
	protected Short isToObligatoryRightChildren;//null是没有生成到债权库的，1是生成到债权库去了

	//真实姓名
	public String truename;
	//手机号码
	public String telphone;

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	public Long getNewInvestPersonId() {
		return newInvestPersonId;
	}
	public void setNewInvestPersonId(Long newInvestPersonId) {
		this.newInvestPersonId = newInvestPersonId;
	}

	public String getNewInvestPersonName() {
		return newInvestPersonName;
	}

	public void setNewInvestPersonName(String newInvestPersonName) {
		this.newInvestPersonName = newInvestPersonName;
	}

	public String getNewOrderNo() {
		return newOrderNo;
	}

	public void setNewOrderNo(String newOrderNo) {
		this.newOrderNo = newOrderNo;
	}

	public Long getCouponId() {
		return couponId;
	}

	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}

	public String getPreAuthorizationNum() {
		return preAuthorizationNum;
	}

	public void setPreAuthorizationNum(String preAuthorizationNum) {
		this.preAuthorizationNum = preAuthorizationNum;
	}

	public Short getIsToObligatoryRightChildren() {
		return isToObligatoryRightChildren;
	}

	public void setIsToObligatoryRightChildren(Short isToObligatoryRightChildren) {
		this.isToObligatoryRightChildren = isToObligatoryRightChildren;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Short getState() {
		return state;
	}

	public void setState(Short state) {
		this.state = state;
	}

	/**
	 * Default Empty Constructor for class PlBidInfo
	 */
	public PlBidInfo () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class PlBidInfo
	 */
	public PlBidInfo (
		 Long in_id
        ) {
		this.setId(in_id);
    }

	
	public com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan getPlBidPlan () {
		return plBidPlan;
	}	
	
	public void setPlBidPlan (com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan in_plBidPlan) {
		this.plBidPlan = in_plBidPlan;
	}
    

	/**
	 * id	 * @return Long
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
	 * 投资人账号	 * @return String
	 * @hibernate.property column="userName" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	public String getUserName() {
		return this.userName;
	}
	
	/**
	 * Set the userName
	 */	
	public void setUserName(String aValue) {
		this.userName = aValue;
	}	

	/**
	 * 投资人id	 * @return Long
	 * @hibernate.property column="userId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getUserId() {
		return this.userId;
	}
	
	/**
	 * Set the userId
	 */	
	public void setUserId(Long aValue) {
		this.userId = aValue;
	}	

	/**
	 * 招标计划id	 * @return Long
	 */
	public Long getBidId() {
		return this.getPlBidPlan()==null?null:this.getPlBidPlan().getBidId();
	}
	
	/**
	 * Set the bidId
	 */	
	public void setBidId(Long aValue) {
	    if (aValue==null) {
	    	plBidPlan = null;
	    } else if (plBidPlan == null) {
	        plBidPlan = new com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan(aValue);
	        plBidPlan.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
	    	//
			plBidPlan.setBidId(aValue);
	    }
	}	

	/**
	 * 招标计划名称	 * @return String
	 * @hibernate.property column="bidName" type="java.lang.String" length="150" not-null="false" unique="false"
	 */
	public String getBidName() {
		return this.bidName;
	}
	
	/**
	 * Set the bidName
	 */	
	public void setBidName(String aValue) {
		this.bidName = aValue;
	}	

	/**
	 * 投标金额	 * @return java.math.BigDecimal
	 * @hibernate.property column="userMoney" type="java.math.BigDecimal" length="10" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getUserMoney() {
		return this.userMoney;
	}
	
	/**
	 * Set the userMoney
	 */	
	public void setUserMoney(java.math.BigDecimal aValue) {
		this.userMoney = aValue;
	}	

	/**
	 * 投标日期	 * @return java.util.Date
	 * @hibernate.property column="bidtime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getBidtime() {
		return this.bidtime;
	}
	
	/**
	 * Set the bidtime
	 */	
	public void setBidtime(java.util.Date aValue) {
		this.bidtime = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof PlBidInfo)) {
			return false;
		}
		PlBidInfo rhs = (PlBidInfo) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.userName, rhs.userName)
				.append(this.userId, rhs.userId)
						.append(this.bidName, rhs.bidName)
				.append(this.userMoney, rhs.userMoney)
				.append(this.bidtime, rhs.bidtime)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.userName) 
				.append(this.userId) 
						.append(this.bidName) 
				.append(this.userMoney) 
				.append(this.bidtime) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("userName", this.userName) 
				.append("userId", this.userId) 
						.append("bidName", this.bidName) 
				.append("userMoney", this.userMoney) 
				.append("bidtime", this.bidtime) 
				.toString();
	}



}
