package com.zhiwei.credit.model.creditFlow.finance.compensatory;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * 
 * @author 
 *
 */
/**
 * PlBidCompensatory Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class PlBidCompensatory extends com.zhiwei.core.model.BaseModel {
	
	/**
	 * 
	 */
    protected Long id;
    /**
     * 借款人P2P账号
     */
	protected Long loanerp2pId;
	/**
	 * 借款客户表Id
	 */
	protected Long custmerId;
	/**
	 * 借款客户类型：和bp_cust_relation 表线下客户类型对应
	 */
	protected String custmerType;
	/**
	 * 散标Id
	 */
	protected Long planId;
	/**
	 * 代偿总金额
	 */
	protected java.math.BigDecimal compensatoryMoney;
	
	public static final String TYPE_PLATE="PLATE";//平台代偿
	public static final String TYPE_GURANEE="GURANEE";//担保户代偿
	/**
	 * 代偿类型：平台代偿PLATE  担保户代偿： GURANEE
	 * 默认平台代偿
	 * 
	 */
	protected String compensatoryType="PLATE";
	/**
	 * 代偿用户名称
	 */
	protected String compensatoryName;
	/**
	 * 代偿用户P2P账号  平台代偿没有Id
	 */
	protected Long compensatoryP2PId;
	/**
	 * 代偿期数
	 */
	protected Integer payintentPeriod;
	/**
	 * 代偿款项登记流水号
	 */
	protected String requestNo;
	/**
	 * 代偿款付款人线下账户姓名
	 */
	protected String  custmerName;
	
	/**
	 * 代偿款项的招标项目的名称
	 */
	protected String bidPlanname;
	/**
	 * 代垫款项的招标项目的编号
	 */
	protected String bidPlanNumber;
	/**
	 * 待回款总金额
	 * 计算方式：代垫金额+罚息总金额-已偿还罚息-已偿还代垫金额-平账总金额
	 */
	protected BigDecimal totalMoney=new BigDecimal(0);
	/**
	 * 未偿付罚息总额
	 */
	protected BigDecimal  unBackPunishMoney=new BigDecimal(0);
	/**
	 * 未偿付代偿金额
	 */ 
	protected BigDecimal  unBackCompensatoryMoney=new BigDecimal(0);
	
	public BigDecimal getUnBackPunishMoney() {
		return unBackPunishMoney;
	}

	public void setUnBackPunishMoney(BigDecimal unBackPunishMoney) {
		this.unBackPunishMoney = unBackPunishMoney;
	}

	public BigDecimal getUnBackCompensatoryMoney() {
		return unBackCompensatoryMoney;
	}

	public void setUnBackCompensatoryMoney(BigDecimal unBackCompensatoryMoney) {
		this.unBackCompensatoryMoney = unBackCompensatoryMoney;
	}


	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	public String getCustmerName() {
		return custmerName;
	}

	public void setCustmerName(String custmerName) {
		this.custmerName = custmerName;
	}

	public String getBidPlanname() {
		return bidPlanname;
	}

	public void setBidPlanname(String bidPlanname) {
		this.bidPlanname = bidPlanname;
	}

	public String getBidPlanNumber() {
		return bidPlanNumber;
	}

	public void setBidPlanNumber(String bidPlanNumber) {
		this.bidPlanNumber = bidPlanNumber;
	}


	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	public Integer getPayintentPeriod() {
		return payintentPeriod;
	}

	public void setPayintentPeriod(Integer payintentPeriod) {
		this.payintentPeriod = payintentPeriod;
	}

	/**
	 * 代偿日期
	 */
	protected java.util.Date compensatoryDate;
	/**
	 * 代偿产生罚息天数
	 */
	protected Integer compensatoryDays;
	/**
	 * 代偿罚息利率
	 */
	protected java.math.BigDecimal punishRate;
	
	/**
	 * 平账金额
	 */
	protected java.math.BigDecimal plateMoney=new  java.math.BigDecimal(0);
	
	public java.math.BigDecimal getPlateMoney() {
		return plateMoney;
	}

	public void setPlateMoney(java.math.BigDecimal plateMoney) {
		this.plateMoney = plateMoney;
	}

	public java.math.BigDecimal getPunishRate() {
		return punishRate;
	}

	public void setPunishRate(java.math.BigDecimal punishRate) {
		this.punishRate = punishRate;
	}

	/**
	 * 代偿罚息总额
	 */
	protected java.math.BigDecimal punishMoney;
	/**
	 * 已偿还代偿罚息
	 */
	protected java.math.BigDecimal backPunishMoney;
	/**
	 * 已偿还代偿金额
	 */
	protected java.math.BigDecimal backCompensatoryMoney;
	/**
	 * 最近代偿日期
	 */
	protected java.util.Date backDate;
	/**
	 * 代偿状态（是否全额代偿）  2表示全部代偿  默认值为0 
	 */
	protected Integer backStatus=0;


	/**
	 * Default Empty Constructor for class PlBidCompensatory
	 */
	public PlBidCompensatory () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class PlBidCompensatory
	 */
	public PlBidCompensatory (
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
	 * 	 * @return Long
	 * @hibernate.property column="loanerp2pId" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	public Long getLoanerp2pId() {
		return this.loanerp2pId;
	}
	
	/**
	 * Set the loanerp2pId
	 * @spring.validator type="required"
	 */	
	public void setLoanerp2pId(Long aValue) {
		this.loanerp2pId = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="custmerId" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	public Long getCustmerId() {
		return this.custmerId;
	}
	
	/**
	 * Set the custmerId
	 * @spring.validator type="required"
	 */	
	public void setCustmerId(Long aValue) {
		this.custmerId = aValue;
	}	

	/**
	 * 借款客户类型（默认是个人借款客户）	 * @return String
	 * @hibernate.property column="custmerType" type="java.lang.String" length="255" not-null="true" unique="false"
	 */
	public String getCustmerType() {
		return this.custmerType;
	}
	
	/**
	 * Set the custmerType
	 * @spring.validator type="required"
	 */	
	public void setCustmerType(String aValue) {
		this.custmerType = aValue;
	}	

	/**
	 * 散标主键Id	 * @return Long
	 * @hibernate.property column="planId" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	public Long getPlanId() {
		return this.planId;
	}
	
	/**
	 * Set the planId
	 * @spring.validator type="required"
	 */	
	public void setPlanId(Long aValue) {
		this.planId = aValue;
	}	

	/**
	 * 代偿总金额	 * @return java.math.BigDecimal
	 * @hibernate.property column="compensatoryMoney" type="java.math.BigDecimal" length="20" not-null="true" unique="false"
	 */
	public java.math.BigDecimal getCompensatoryMoney() {
		return this.compensatoryMoney;
	}
	
	/**
	 * Set the compensatoryMoney
	 * @spring.validator type="required"
	 */	
	public void setCompensatoryMoney(java.math.BigDecimal aValue) {
		this.compensatoryMoney = aValue;
	}	

	/**
	 * 代偿类型（默认代偿类型为平台代偿）	 * @return String
	 * @hibernate.property column="compensatoryType" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getCompensatoryType() {
		return this.compensatoryType;
	}
	
	/**
	 * Set the compensatoryType
	 */	
	public void setCompensatoryType(String aValue) {
		this.compensatoryType = aValue;
	}	

	/**
	 * 代偿用户名称（担保机构名称）	 * @return String
	 * @hibernate.property column="compensatoryName" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getCompensatoryName() {
		return this.compensatoryName;
	}
	
	/**
	 * Set the compensatoryName
	 */	
	public void setCompensatoryName(String aValue) {
		this.compensatoryName = aValue;
	}	

	/**
	 * 代偿用户的P2P账号   平台无账号	 * @return Long
	 * @hibernate.property column="compensatoryP2PId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getCompensatoryP2PId() {
		return this.compensatoryP2PId;
	}
	
	/**
	 * Set the compensatoryP2PId
	 */	
	public void setCompensatoryP2PId(Long aValue) {
		this.compensatoryP2PId = aValue;
	}	

	/**
	 * 代偿日期	 * @return java.util.Date
	 * @hibernate.property column="compensatoryDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getCompensatoryDate() {
		return this.compensatoryDate;
	}
	
	/**
	 * Set the compensatoryDate
	 */	
	public void setCompensatoryDate(java.util.Date aValue) {
		this.compensatoryDate = aValue;
	}	

	/**
	 * 已代偿天数	 * @return Integer
	 * @hibernate.property column="compensatoryDays" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getCompensatoryDays() {
		return this.compensatoryDays;
	}
	
	/**
	 * Set the compensatoryDays
	 */	
	public void setCompensatoryDays(Integer aValue) {
		this.compensatoryDays = aValue;
	}	

	/**
	 * 代偿产生的利息总和（定时器计算）	 * @return java.math.BigDecimal
	 * @hibernate.property column="punishMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getPunishMoney() {
		return this.punishMoney;
	}
	
	/**
	 * Set the punishMoney
	 */	
	public void setPunishMoney(java.math.BigDecimal aValue) {
		this.punishMoney = aValue;
	}	

	/**
	 * 已偿还代偿利息	 * @return java.math.BigDecimal
	 * @hibernate.property column="backPunishMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getBackPunishMoney() {
		return this.backPunishMoney;
	}
	
	/**
	 * Set the backPunishMoney
	 */	
	public void setBackPunishMoney(java.math.BigDecimal aValue) {
		this.backPunishMoney = aValue;
	}	

	/**
	 * 已偿还代偿本金	 * @return java.math.BigDecimal
	 * @hibernate.property column="backCompensatoryMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getBackCompensatoryMoney() {
		return this.backCompensatoryMoney;
	}
	
	/**
	 * Set the backCompensatoryMoney
	 */	
	public void setBackCompensatoryMoney(java.math.BigDecimal aValue) {
		this.backCompensatoryMoney = aValue;
	}	

	/**
	 * 最后一笔偿还代偿金额日期	 * @return java.util.Date
	 * @hibernate.property column="backDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getBackDate() {
		return this.backDate;
	}
	
	/**
	 * Set the backDate
	 */	
	public void setBackDate(java.util.Date aValue) {
		this.backDate = aValue;
	}	

	/**
	 * 代偿偿还是否全部完成：0表示没有全部完成，2表示已完成	 * @return Integer
	 * @hibernate.property column="backStatus" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getBackStatus() {
		return this.backStatus;
	}
	
	/**
	 * Set the backStatus
	 */	
	public void setBackStatus(Integer aValue) {
		this.backStatus = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof PlBidCompensatory)) {
			return false;
		}
		PlBidCompensatory rhs = (PlBidCompensatory) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.loanerp2pId, rhs.loanerp2pId)
				.append(this.custmerId, rhs.custmerId)
				.append(this.custmerType, rhs.custmerType)
				.append(this.planId, rhs.planId)
				.append(this.compensatoryMoney, rhs.compensatoryMoney)
				.append(this.compensatoryType, rhs.compensatoryType)
				.append(this.compensatoryName, rhs.compensatoryName)
				.append(this.compensatoryP2PId, rhs.compensatoryP2PId)
				.append(this.compensatoryDate, rhs.compensatoryDate)
				.append(this.compensatoryDays, rhs.compensatoryDays)
				.append(this.punishMoney, rhs.punishMoney)
				.append(this.backPunishMoney, rhs.backPunishMoney)
				.append(this.backCompensatoryMoney, rhs.backCompensatoryMoney)
				.append(this.backDate, rhs.backDate)
				.append(this.backStatus, rhs.backStatus)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.loanerp2pId) 
				.append(this.custmerId) 
				.append(this.custmerType) 
				.append(this.planId) 
				.append(this.compensatoryMoney) 
				.append(this.compensatoryType) 
				.append(this.compensatoryName) 
				.append(this.compensatoryP2PId) 
				.append(this.compensatoryDate) 
				.append(this.compensatoryDays) 
				.append(this.punishMoney) 
				.append(this.backPunishMoney) 
				.append(this.backCompensatoryMoney) 
				.append(this.backDate) 
				.append(this.backStatus) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("loanerp2pId", this.loanerp2pId) 
				.append("custmerId", this.custmerId) 
				.append("custmerType", this.custmerType) 
				.append("planId", this.planId) 
				.append("compensatoryMoney", this.compensatoryMoney) 
				.append("compensatoryType", this.compensatoryType) 
				.append("compensatoryName", this.compensatoryName) 
				.append("compensatoryP2PId", this.compensatoryP2PId) 
				.append("compensatoryDate", this.compensatoryDate) 
				.append("compensatoryDays", this.compensatoryDays) 
				.append("punishMoney", this.punishMoney) 
				.append("backPunishMoney", this.backPunishMoney) 
				.append("backCompensatoryMoney", this.backCompensatoryMoney) 
				.append("backDate", this.backDate) 
				.append("backStatus", this.backStatus) 
				.toString();
	}



}
