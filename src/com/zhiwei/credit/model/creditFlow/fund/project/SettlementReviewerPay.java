package com.zhiwei.credit.model.creditFlow.fund.project;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
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
 * SettlementReviewerPay Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 结算流程表
 */
public class SettlementReviewerPay extends com.zhiwei.core.model.BaseModel {
	
	private final static Short STATE_0 = 0;  //待审核
	private final static Short STATE_1 = 1;  //待支付
	private final static Short STATE_2 = 2;	 //已支付

    protected Long id;
	protected String flowType;
	protected String settlementStartDate;
	protected String settlementEndDate;
	protected String collectionDepartment;
	protected String paymentMethod;
	protected String settlementMoney;
	protected String otherSettlementMoney;
	protected String actualSettlementMoney;
	protected String bankName;
	protected String bankNum;
	protected String managerName;
	protected String reviewer;
	protected String payer;
	protected String managerDate;
	protected BigDecimal payMoney;   //结算金额
	protected BigDecimal factMoney;   //实际结算金额
	protected BigDecimal otherMoney;  //其他结算金额
	protected Date payStartDate;    //结算开始时间
	protected Date payEndDate;     //结算结束时间
	protected Date createDate;    //创建时间
	protected String projectNumber;  //项目编号
	protected Short state;
	protected String remark1;
	protected String remark2;
	protected String remark3;
	protected String remark4;
	protected String remark5;
	protected String url;

	//与数据库无关
	private Long organId;//组织机构ID
	private String organName;//部门姓名
	private String intentDate;//日期
	private String incomeMoney;//当日投资保有量
	private String commissionRate;//提成比例
	private BigDecimal ticheng;//提成金额
	private Long fundIntentId;//台账主键
	private String loginname;//账号
	private String truename;//真实姓名
	private String bidProName;//标的名字
	
	
	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	public String getBidProName() {
		return bidProName;
	}

	public void setBidProName(String bidProName) {
		this.bidProName = bidProName;
	}

	public Long getFundIntentId() {
		return fundIntentId;
	}

	public void setFundIntentId(Long fundIntentId) {
		this.fundIntentId = fundIntentId;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getIntentDate() {
		return intentDate;
	}

	public void setIntentDate(String intentDate) {
		this.intentDate = intentDate;
	}

	public String getIncomeMoney() {
		return incomeMoney;
	}

	public void setIncomeMoney(String incomeMoney) {
		this.incomeMoney = incomeMoney;
	}

	public String getCommissionRate() {
		return commissionRate;
	}

	public void setCommissionRate(String commissionRate) {
		this.commissionRate = commissionRate;
	}

	public BigDecimal getTicheng() {
		return ticheng;
	}

	public void setTicheng(BigDecimal ticheng) {
		this.ticheng = ticheng;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}
	
	public Long getOrganId() {
		return organId;
	}

	public void setOrganId(Long organId) {
		this.organId = organId;
	}

	/**
	 * Default Empty Constructor for class SettlementReviewerPay
	 */
	public SettlementReviewerPay () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class SettlementReviewerPay
	 */
	public SettlementReviewerPay (
		 Long in_id
        ) {
		this.setId(in_id);
    }

    

	/**
	 * 结算ID	 * @return Long
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
	 * 流程的key	 * @return String
	 * @hibernate.property column="flowType" type="java.lang.String" length="200" not-null="false" unique="false"
	 */
	public String getFlowType() {
		return this.flowType;
	}
	
	/**
	 * Set the flowType
	 */	
	public void setFlowType(String aValue) {
		this.flowType = aValue;
	}	

	/**
	 * 结算开始日期	 * @return String
	 * @hibernate.property column="settlementStartDate" type="java.lang.String" length="200" not-null="false" unique="false"
	 */
	public String getSettlementStartDate() {
		return this.settlementStartDate;
	}
	
	/**
	 * Set the settlementStartDate
	 */	
	public void setSettlementStartDate(String aValue) {
		this.settlementStartDate = aValue;
	}	

	/**
	 * 结算截止日期	 * @return String
	 * @hibernate.property column="settlementEndDate" type="java.lang.String" length="200" not-null="false" unique="false"
	 */
	public String getSettlementEndDate() {
		return this.settlementEndDate;
	}
	
	/**
	 * Set the settlementEndDate
	 */	
	public void setSettlementEndDate(String aValue) {
		this.settlementEndDate = aValue;
	}	

	/**
	 * 收款部门ID	 * @return String
	 * @hibernate.property column="collectionDepartment" type="java.lang.String" length="200" not-null="false" unique="false"
	 */
	public String getCollectionDepartment() {
		return this.collectionDepartment;
	}
	
	/**
	 * Set the collectionDepartment
	 */	
	public void setCollectionDepartment(String aValue) {
		this.collectionDepartment = aValue;
	}	

	/**
	 * 支付方式	 * @return String
	 * @hibernate.property column="paymentMethod" type="java.lang.String" length="200" not-null="false" unique="false"
	 */
	public String getPaymentMethod() {
		return this.paymentMethod;
	}
	
	/**
	 * Set the paymentMethod
	 */	
	public void setPaymentMethod(String aValue) {
		this.paymentMethod = aValue;
	}	

	/**
	 * 应结算金额	 * @return String
	 * @hibernate.property column="settlementMoney" type="java.lang.String" length="200" not-null="false" unique="false"
	 */
	public String getSettlementMoney() {
		return this.settlementMoney;
	}
	
	/**
	 * Set the settlementMoney
	 */	
	public void setSettlementMoney(String aValue) {
		this.settlementMoney = aValue;
	}	

	/**
	 * 其他结算金额	 * @return String
	 * @hibernate.property column="otherSettlementMoney" type="java.lang.String" length="200" not-null="false" unique="false"
	 */
	public String getOtherSettlementMoney() {
		return this.otherSettlementMoney;
	}
	
	/**
	 * Set the otherSettlementMoney
	 */	
	public void setOtherSettlementMoney(String aValue) {
		this.otherSettlementMoney = aValue;
	}	

	/**
	 * 实际结算金额	 * @return String
	 * @hibernate.property column="actualSettlementMoney" type="java.lang.String" length="200" not-null="false" unique="false"
	 */
	public String getActualSettlementMoney() {
		return this.actualSettlementMoney;
	}
	
	/**
	 * Set the actualSettlementMoney
	 */	
	public void setActualSettlementMoney(String aValue) {
		this.actualSettlementMoney = aValue;
	}	

	/**
	 * 银行名称	 * @return String
	 * @hibernate.property column="bankName" type="java.lang.String" length="200" not-null="false" unique="false"
	 */
	public String getBankName() {
		return this.bankName;
	}
	
	/**
	 * Set the bankName
	 */	
	public void setBankName(String aValue) {
		this.bankName = aValue;
	}	

	/**
	 * 银行账号	 * @return String
	 * @hibernate.property column="bankNum" type="java.lang.String" length="200" not-null="false" unique="false"
	 */
	public String getBankNum() {
		return this.bankNum;
	}
	
	/**
	 * Set the bankNum
	 */	
	public void setBankNum(String aValue) {
		this.bankNum = aValue;
	}	

	/**
	 * 经办人	 * @return String
	 * @hibernate.property column="managerName" type="java.lang.String" length="200" not-null="false" unique="false"
	 */
	public String getManagerName() {
		return this.managerName;
	}
	
	/**
	 * Set the managerName
	 */	
	public void setManagerName(String aValue) {
		this.managerName = aValue;
	}	

	/**
	 * 审核人	 * @return String
	 * @hibernate.property column="reviewer" type="java.lang.String" length="200" not-null="false" unique="false"
	 */
	public String getReviewer() {
		return this.reviewer;
	}
	
	/**
	 * Set the reviewer
	 */	
	public void setReviewer(String aValue) {
		this.reviewer = aValue;
	}	

	/**
	 * 支付人	 * @return String
	 * @hibernate.property column="payer" type="java.lang.String" length="200" not-null="false" unique="false"
	 */
	public String getPayer() {
		return this.payer;
	}
	
	/**
	 * Set the payer
	 */	
	public void setPayer(String aValue) {
		this.payer = aValue;
	}	

	/**
	 * 经办日期	 * @return String
	 * @hibernate.property column="managerDate" type="java.lang.String" length="200" not-null="false" unique="false"
	 */
	public String getManagerDate() {
		return this.managerDate;
	}
	
	/**
	 * Set the managerDate
	 */	
	public void setManagerDate(String aValue) {
		this.managerDate = aValue;
	}	

	/**
	 * 备注一	 * @return String
	 * @hibernate.property column="remark1" type="java.lang.String" length="200" not-null="false" unique="false"
	 */
	public String getRemark1() {
		return this.remark1;
	}
	
	/**
	 * Set the remark1
	 */	
	public void setRemark1(String aValue) {
		this.remark1 = aValue;
	}	

	/**
	 * 备注二	 * @return String
	 * @hibernate.property column="remark2" type="java.lang.String" length="200" not-null="false" unique="false"
	 */
	public String getRemark2() {
		return this.remark2;
	}
	
	/**
	 * Set the remark2
	 */	
	public void setRemark2(String aValue) {
		this.remark2 = aValue;
	}	

	/**
	 * 备注三	 * @return String
	 * @hibernate.property column="remark3" type="java.lang.String" length="200" not-null="false" unique="false"
	 */
	public String getRemark3() {
		return this.remark3;
	}
	
	/**
	 * Set the remark3
	 */	
	public void setRemark3(String aValue) {
		this.remark3 = aValue;
	}	

	/**
	 * 备注四	 * @return String
	 * @hibernate.property column="remark4" type="java.lang.String" length="200" not-null="false" unique="false"
	 */
	public String getRemark4() {
		return this.remark4;
	}
	
	/**
	 * Set the remark4
	 */	
	public void setRemark4(String aValue) {
		this.remark4 = aValue;
	}	

	/**
	 * 备注五	 * @return String
	 * @hibernate.property column="remark5" type="java.lang.String" length="200" not-null="false" unique="false"
	 */
	public String getRemark5() {
		return this.remark5;
	}
	
	/**
	 * Set the remark5
	 */	
	public void setRemark5(String aValue) {
		this.remark5 = aValue;
	}	

	public BigDecimal getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(BigDecimal payMoney) {
		this.payMoney = payMoney;
	}

	public BigDecimal getFactMoney() {
		return factMoney;
	}

	public void setFactMoney(BigDecimal factMoney) {
		this.factMoney = factMoney;
	}

	public BigDecimal getOtherMoney() {
		return otherMoney;
	}

	public void setOtherMoney(BigDecimal otherMoney) {
		this.otherMoney = otherMoney;
	}

	public Date getPayStartDate() {
		return payStartDate;
	}

	public void setPayStartDate(Date payStartDate) {
		this.payStartDate = payStartDate;
	}

	public Date getPayEndDate() {
		return payEndDate;
	}

	public void setPayEndDate(Date payEndDate) {
		this.payEndDate = payEndDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Short getState() {
		return state;
	}

	public void setState(Short state) {
		this.state = state;
	}

	public String getProjectNumber() {
		return projectNumber;
	}

	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SettlementReviewerPay)) {
			return false;
		}
		SettlementReviewerPay rhs = (SettlementReviewerPay) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.flowType, rhs.flowType)
				.append(this.settlementStartDate, rhs.settlementStartDate)
				.append(this.settlementEndDate, rhs.settlementEndDate)
				.append(this.collectionDepartment, rhs.collectionDepartment)
				.append(this.paymentMethod, rhs.paymentMethod)
				.append(this.settlementMoney, rhs.settlementMoney)
				.append(this.otherSettlementMoney, rhs.otherSettlementMoney)
				.append(this.actualSettlementMoney, rhs.actualSettlementMoney)
				.append(this.bankName, rhs.bankName)
				.append(this.bankNum, rhs.bankNum)
				.append(this.managerName, rhs.managerName)
				.append(this.reviewer, rhs.reviewer)
				.append(this.payer, rhs.payer)
				.append(this.managerDate, rhs.managerDate)
				.append(this.remark1, rhs.remark1)
				.append(this.remark2, rhs.remark2)
				.append(this.remark3, rhs.remark3)
				.append(this.remark4, rhs.remark4)
				.append(this.remark5, rhs.remark5)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.flowType) 
				.append(this.settlementStartDate) 
				.append(this.settlementEndDate) 
				.append(this.collectionDepartment) 
				.append(this.paymentMethod) 
				.append(this.settlementMoney) 
				.append(this.otherSettlementMoney) 
				.append(this.actualSettlementMoney) 
				.append(this.bankName) 
				.append(this.bankNum) 
				.append(this.managerName) 
				.append(this.reviewer) 
				.append(this.payer) 
				.append(this.managerDate) 
				.append(this.remark1) 
				.append(this.remark2) 
				.append(this.remark3) 
				.append(this.remark4) 
				.append(this.remark5) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("flowType", this.flowType) 
				.append("settlementStartDate", this.settlementStartDate) 
				.append("settlementEndDate", this.settlementEndDate) 
				.append("collectionDepartment", this.collectionDepartment) 
				.append("paymentMethod", this.paymentMethod) 
				.append("settlementMoney", this.settlementMoney) 
				.append("otherSettlementMoney", this.otherSettlementMoney) 
				.append("actualSettlementMoney", this.actualSettlementMoney) 
				.append("bankName", this.bankName) 
				.append("bankNum", this.bankNum) 
				.append("managerName", this.managerName) 
				.append("reviewer", this.reviewer) 
				.append("payer", this.payer) 
				.append("managerDate", this.managerDate) 
				.append("remark1", this.remark1) 
				.append("remark2", this.remark2) 
				.append("remark3", this.remark3) 
				.append("remark4", this.remark4) 
				.append("remark5", this.remark5) 
				.toString();
	}



}
