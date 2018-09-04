package com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney;
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
 * PlMmOrderInfo Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class PlMmOrderInfo extends com.zhiwei.core.model.BaseModel {

	/**
	 * 主键
	 */
    protected Long id;    
    /**
	 * 投资人ID
	 */
	protected String investPersonId;		
	/**
	 * 理财产品订单ID  plmanagemoneyplanbuyinfo  表的ID 
	 */
	private String orderId; 
	/**
	 * 投资人
	 */
	protected String investPersonName;		
	/**
	 * 产品ID
	 */
	protected String mmplanId;				
	/**
	 * 产品名称
	 */
	protected String mmplanName;		
	/**
	 * 产品编号
	 */
	protected String mmplanNumber;		
	/**
	 * 产品期限
	 */
	protected String investDue;		
	/**
	 * 产品年化利率
	 */
	protected java.math.BigDecimal rate;   
	/**
	 * 投资人投资金额
	 */
	protected java.math.BigDecimal buyMoney;  
	/**
	 * 合同编号
	 */
	protected String orderNumber;			 
	/**
	 * 理财顾问
	 */
	protected String counselor;				
	/**
	 * 购买日期
	 */
	protected java.util.Date buyDate;	
	/**
	 * 门店名称
	 */
	private String departMentName;
	/**
	 * 门店Id
	 */
	private String departId;
	/**
	 * 门店经理
	 */
	private String departUser; 
	/**
	 * 门店经理Id
	 */
	private String departUserId;
	/**
	 * 团队经理
	 */
	private String teamManageName; 
	/**
	 * 团队经理Id
	 */
	private String teamManageNameId;
	/**
	 * 客户经理
	 */
	private String customerManagerName;
	/**
	 * 客户经理ID
	 */
	private String customerManagerNameId; 
	/**
	 * 计息开始时间
	 */
	private Date startinInterestTime;
	/**
	 * 计算结束时间
	 */
	private Date endinInterestTime;
	/**
	 * 投资期限  单位天
	 */
	private String orderlimit; 
	/**
	 * 理财产品购买流水号
	 */
	private String recordNumber; 
	/**
	 * 投资说明
	 */
	private String investDescription;
	/**
	 * 账单寄送形式
	 */
	private String orderpostType; 
	
	
	
	public String getOrderpostType() {
		return orderpostType;
	}

	public void setOrderpostType(String orderpostType) {
		this.orderpostType = orderpostType;
	}

	public String getInvestDescription() {
		return investDescription;
	}

	public void setInvestDescription(String investDescription) {
		this.investDescription = investDescription;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getRecordNumber() {
		return recordNumber;
	}

	public void setRecordNumber(String recordNumber) {
		this.recordNumber = recordNumber;
	}

	/**
	 * 确认到账方式
	 * 0 系统自动充值扣除账户余额
	 * 1 已充值扣除账户余额
	 */
	private String confirmType;
	public String getConfirmType() {
		return confirmType;
	}

	public void setConfirmType(String confirmType) {
		this.confirmType = confirmType;
	}

	/**
	 * Default Empty Constructor for class PlMmOrderInfo
	 */
	public PlMmOrderInfo () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class PlMmOrderInfo
	 */
	public PlMmOrderInfo (
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
	 * 投资人ID	 * @return String
	 * @hibernate.property column="investPersonId" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getInvestPersonId() {
		return this.investPersonId;
	}
	
	/**
	 * Set the investPersonId
	 */	
	public void setInvestPersonId(String aValue) {
		this.investPersonId = aValue;
	}	

	/**
	 * 投资人姓名	 * @return String
	 * @hibernate.property column="investPersonName" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getInvestPersonName() {
		return this.investPersonName;
	}
	
	/**
	 * Set the investPersonName
	 */	
	public void setInvestPersonName(String aValue) {
		this.investPersonName = aValue;
	}	

	/**
	 * 产品ID	 * @return String
	 * @hibernate.property column="mmplanId" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getMmplanId() {
		return this.mmplanId;
	}
	
	/**
	 * Set the mmplanId
	 */	
	public void setMmplanId(String aValue) {
		this.mmplanId = aValue;
	}	

	/**
	 * 产品名称	 * @return String
	 * @hibernate.property column="mmplanName" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getMmplanName() {
		return this.mmplanName;
	}
	
	/**
	 * Set the mmplanName
	 */	
	public void setMmplanName(String aValue) {
		this.mmplanName = aValue;
	}	

	/**
	 * 产品编号	 * @return String
	 * @hibernate.property column="mmplanNumber" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getMmplanNumber() {
		return this.mmplanNumber;
	}
	
	/**
	 * Set the mmplanNumber
	 */	
	public void setMmplanNumber(String aValue) {
		this.mmplanNumber = aValue;
	}	

	/**
	 * 产品期限	 * @return String
	 * @hibernate.property column="investDue" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getInvestDue() {
		return this.investDue;
	}
	
	/**
	 * Set the investDue
	 */	
	public void setInvestDue(String aValue) {
		this.investDue = aValue;
	}	

	/**
	 * 产品年化利率	 * @return java.math.BigDecimal
	 * @hibernate.property column="rate" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getRate() {
		return this.rate;
	}
	
	/**
	 * Set the rate
	 */	
	public void setRate(java.math.BigDecimal aValue) {
		this.rate = aValue;
	}	

	/**
	 * 投资金额	 * @return java.math.BigDecimal
	 * @hibernate.property column="buyMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getBuyMoney() {
		return this.buyMoney;
	}
	
	/**
	 * Set the buyMoney
	 */	
	public void setBuyMoney(java.math.BigDecimal aValue) {
		this.buyMoney = aValue;
	}	

	/**
	 * 订单编号	 * @return String
	 * @hibernate.property column="orderNumber" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getOrderNumber() {
		return this.orderNumber;
	}
	
	/**
	 * Set the orderNumber
	 */	
	public void setOrderNumber(String aValue) {
		this.orderNumber = aValue;
	}	

	/**
	 * 理财顾问	 * @return String
	 * @hibernate.property column="counselor" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getCounselor() {
		return this.counselor;
	}
	
	/**
	 * Set the counselor
	 */	
	public void setCounselor(String aValue) {
		this.counselor = aValue;
	}	

	/**
	 * 购买时间	 * @return java.util.Date
	 * @hibernate.property column="buyDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getBuyDate() {
		return this.buyDate;
	}
	
	/**
	 * Set the buyDate
	 */	
	public void setBuyDate(java.util.Date aValue) {
		this.buyDate = aValue;
	}	
	
	public String getDepartUser() {
		return departUser;
	}

	public void setDepartUser(String departUser) {
		this.departUser = departUser;
	}

	public String getDepartUserId() {
		return departUserId;
	}

	public void setDepartUserId(String departUserId) {
		this.departUserId = departUserId;
	}

	public String getTeamManageName() {
		return teamManageName;
	}

	public void setTeamManageName(String teamManageName) {
		this.teamManageName = teamManageName;
	}

	public String getTeamManageNameId() {
		return teamManageNameId;
	}

	public void setTeamManageNameId(String teamManageNameId) {
		this.teamManageNameId = teamManageNameId;
	}

	public String getCustomerManagerName() {
		return customerManagerName;
	}

	public void setCustomerManagerName(String customerManagerName) {
		this.customerManagerName = customerManagerName;
	}

	public String getCustomerManagerNameId() {
		return customerManagerNameId;
	}

	public void setCustomerManagerNameId(String customerManagerNameId) {
		this.customerManagerNameId = customerManagerNameId;
	}

	public Date getStartinInterestTime() {
		return startinInterestTime;
	}

	public void setStartinInterestTime(Date startinInterestTime) {
		this.startinInterestTime = startinInterestTime;
	}

	public Date getEndinInterestTime() {
		return endinInterestTime;
	}

	public void setEndinInterestTime(Date endinInterestTime) {
		this.endinInterestTime = endinInterestTime;
	}

	public String getOrderlimit() {
		return orderlimit;
	}

	public void setOrderlimit(String orderlimit) {
		this.orderlimit = orderlimit;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof PlMmOrderInfo)) {
			return false;
		}
		PlMmOrderInfo rhs = (PlMmOrderInfo) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.investPersonId, rhs.investPersonId)
				.append(this.investPersonName, rhs.investPersonName)
				.append(this.mmplanId, rhs.mmplanId)
				.append(this.mmplanName, rhs.mmplanName)
				.append(this.mmplanNumber, rhs.mmplanNumber)
				.append(this.investDue, rhs.investDue)
				.append(this.rate, rhs.rate)
				.append(this.buyMoney, rhs.buyMoney)
				.append(this.orderNumber, rhs.orderNumber)
				.append(this.counselor, rhs.counselor)
				.append(this.buyDate, rhs.buyDate)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.investPersonId) 
				.append(this.investPersonName) 
				.append(this.mmplanId) 
				.append(this.mmplanName) 
				.append(this.mmplanNumber) 
				.append(this.investDue) 
				.append(this.rate) 
				.append(this.buyMoney) 
				.append(this.orderNumber) 
				.append(this.counselor) 
				.append(this.buyDate) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("investPersonId", this.investPersonId) 
				.append("investPersonName", this.investPersonName) 
				.append("mmplanId", this.mmplanId) 
				.append("mmplanName", this.mmplanName) 
				.append("mmplanNumber", this.mmplanNumber) 
				.append("investDue", this.investDue) 
				.append("rate", this.rate) 
				.append("buyMoney", this.buyMoney) 
				.append("orderNumber", this.orderNumber) 
				.append("counselor", this.counselor) 
				.append("buyDate", this.buyDate) 
				.toString();
	}

	public String getDepartMentName() {
		return departMentName;
	}

	public void setDepartMentName(String departMentName) {
		this.departMentName = departMentName;
	}

	public String getDepartId() {
		return departId;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
	}



}
