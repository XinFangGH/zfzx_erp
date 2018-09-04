package com.zhiwei.credit.model.p2p;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * @author 
 *
 */
/**
 * BpFinanceApplyUser Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class BpFinanceApplyUser extends com.zhiwei.core.model.BaseModel {

	
	protected Long loanId;
	protected Long productId;
	protected String productName;
	protected Long userID;
	protected String loanTitle;
	protected BigDecimal loanMoney;
	protected Integer loanTimeLen;
	protected String remark;
	protected BigDecimal expectAccrual;
	protected String payIntersetWay;
	protected Long loanUse;
	protected BigDecimal monthlyInterest;
	protected BigDecimal monthlyCharge;
	protected BigDecimal startCharge;
	protected String state;//0.未提交，1.未受理，2.已受理，4.基础材料通过审核 ，5.已启动借款项目(发标中)，7.借款项目审批通过，请补充材料，8.用户已上传补充材料，9.已通过审核，3.用户申请被驳回（终止用户申请），6审批流程被驳回（终止项目）
	protected Date createTime;
	protected String appName;
	protected String proName;
	protected Long projectId;
	protected String loanUseStr;//资金用途字符串
	protected String truename;
	
	protected String flownodes; //流程配置
	protected String telphone;//电话号码
	
	/**
	 * 审批金额
	 */
	protected BigDecimal approvalLoanMoney;
	/**
	 * 审批期限
	 */
	protected Long approvalLoanTimeLen;
	/**
	 * 审批贷款年化利率
	 */
	protected BigDecimal approvalYearAccrualRate;
	/**
	 * 审批咨询管理费年化利率
	 */
	protected BigDecimal approvalYearManagementConsultingOfRate;
	/**
	 * 审批财务服务费年化利率
	 */
	protected BigDecimal approvalYearFinanceServiceOfRate;
	/**
	 * 审批逾期贷款利率
	 */
	protected BigDecimal approvalOverdueRateLoan;
	/**
	 * 审批罚息利率
	 */
	protected BigDecimal approvalOverdueRate;
	/**
	 * 审批时间
	 */
	protected java.util.Date approvalDate;
	
	
	//不与数据库映射
	protected BigDecimal yearAccrualRate;
	protected BigDecimal yearFinanceServiceOfRate;
	protected BigDecimal yearManagementConsultingOfRate;
	
	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	public String getLoanUseStr() {
		return loanUseStr;
	}

	public void setLoanUseStr(String loanUseStr) {
		this.loanUseStr = loanUseStr;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	/**
	 * Default Empty Constructor for class BpFinanceApplyUser
	 */
	public BpFinanceApplyUser () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class BpFinanceApplyUser
	 */
	public BpFinanceApplyUser (
		 Long in_loanId
        ) {
		this.setLoanId(in_loanId);
    }

    

	/**
	 * 主键	 * @return Long
     * @hibernate.id column="loanId" type="java.lang.Long" generator-class="native"
	 */
	public Long getLoanId() {
		return this.loanId;
	}
	
	/**
	 * Set the loanId
	 */	
	public void setLoanId(Long aValue) {
		this.loanId = aValue;
	}	

	/**
	 * 贷款产品表ID	 * @return Long
	 * @hibernate.property column="productId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getProductId() {
		return this.productId;
	}
	
	/**
	 * Set the productId
	 */	
	public void setProductId(Long aValue) {
		this.productId = aValue;
	}	

	/**
	 * 贷款产品名称	 * @return String
	 * @hibernate.property column="productName" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getProductName() {
		return this.productName;
	}
	
	/**
	 * Set the productName
	 */	
	public void setProductName(String aValue) {
		this.productName = aValue;
	}	

	/**
	 * 注册用户ID	 * @return Long
	 * @hibernate.property column="userID" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getUserID() {
		return this.userID;
	}
	
	/**
	 * Set the userID
	 */	
	public void setUserID(Long aValue) {
		this.userID = aValue;
	}	

	/**
	 * 借款标题	 * @return String
	 * @hibernate.property column="loanTitle" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getLoanTitle() {
		return this.loanTitle;
	}
	
	/**
	 * Set the loanTitle
	 */	
	public void setLoanTitle(String aValue) {
		this.loanTitle = aValue;
	}	

	/**
	 * 借款金额	 * @return String
	 * @hibernate.property column="loanMoney" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public BigDecimal getLoanMoney() {
		return this.loanMoney;
	}
	
	/**
	 * Set the loanMoney
	 */	
	public void setLoanMoney(BigDecimal aValue) {
		this.loanMoney = aValue;
	}	

	/**
	 * 借款期限	 * @return String
	 * @hibernate.property column="loanTimeLen" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public Integer getLoanTimeLen() {
		return this.loanTimeLen;
	}
	
	/**
	 * Set the loanTimeLen
	 */	
	public void setLoanTimeLen(Integer aValue) {
		this.loanTimeLen = aValue;
	}	

	/**
	 * 借款描述	 * @return String
	 * @hibernate.property column="remark" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getRemark() {
		return this.remark;
	}
	
	/**
	 * Set the remark
	 */	
	public void setRemark(String aValue) {
		this.remark = aValue;
	}	

	/**
	 * 年化利率	 * @return String
	 * @hibernate.property column="expectAccrual" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public BigDecimal getExpectAccrual() {
		return this.expectAccrual;
	}
	
	/**
	 * Set the expectAccrual
	 */	
	public void setExpectAccrual(BigDecimal aValue) {
		this.expectAccrual = aValue;
	}	

	/**
	 * 还款方式	 * @return String
	 * @hibernate.property column="payIntersetWay" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getPayIntersetWay() {
		return this.payIntersetWay;
	}
	
	/**
	 * Set the payIntersetWay
	 */	
	public void setPayIntersetWay(String aValue) {
		this.payIntersetWay = aValue;
	}	

	/**
	 * 借款用途	 * @return String
	 * @hibernate.property column="loanUse" type="java.lang.String" length="80" not-null="false" unique="false"
	 */
	public Long getLoanUse() {
		return this.loanUse;
	}
	
	/**
	 * Set the loanUse
	 */	
	public void setLoanUse(Long aValue) {
		this.loanUse = aValue;
	}	

	/**
	 * 每月还本金及利息	 * @return String
	 * @hibernate.property column="monthlyInterest" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public BigDecimal getMonthlyInterest() {
		return this.monthlyInterest;
	}
	
	/**
	 * Set the monthlyInterest
	 */	
	public void setMonthlyInterest(BigDecimal aValue) {
		this.monthlyInterest = aValue;
	}	

	/**
	 * 每月交借款管理费	 * @return String
	 * @hibernate.property column="monthlyCharge" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public BigDecimal getMonthlyCharge() {
		return this.monthlyCharge;
	}
	
	/**
	 * Set the monthlyCharge
	 */	
	public void setMonthlyCharge(BigDecimal aValue) {
		this.monthlyCharge = aValue;
	}	

	/**
	 * 期初服务费	 * @return String
	 * @hibernate.property column="startCharge" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public BigDecimal getStartCharge() {
		return this.startCharge;
	}
	
	/**
	 * Set the startCharge
	 */	
	public void setStartCharge(BigDecimal aValue) {
		this.startCharge = aValue;
	}	

	/**
	 * 申请状态	 * @return String
	 * @hibernate.property column="state" type="java.lang.String" length="5" not-null="false" unique="false"
	 */
	public String getState() {
		return this.state;
	}
	
	/**
	 * Set the state
	 */	
	public void setState(String aValue) {
		this.state = aValue;
	}	

	/**
	 * 借款申请时间	 * @return String
	 * @hibernate.property column="createTime" type="java.lang.String" length="50" not-null="false" unique="false"
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
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof BpFinanceApplyUser)) {
			return false;
		}
		BpFinanceApplyUser rhs = (BpFinanceApplyUser) object;
		return new EqualsBuilder()
				.append(this.loanId, rhs.loanId)
				.append(this.productId, rhs.productId)
				.append(this.productName, rhs.productName)
				.append(this.userID, rhs.userID)
				.append(this.loanTitle, rhs.loanTitle)
				.append(this.loanMoney, rhs.loanMoney)
				.append(this.loanTimeLen, rhs.loanTimeLen)
				.append(this.remark, rhs.remark)
				.append(this.expectAccrual, rhs.expectAccrual)
				.append(this.payIntersetWay, rhs.payIntersetWay)
				.append(this.loanUse, rhs.loanUse)
				.append(this.monthlyInterest, rhs.monthlyInterest)
				.append(this.monthlyCharge, rhs.monthlyCharge)
				.append(this.startCharge, rhs.startCharge)
				.append(this.state, rhs.state)
				.append(this.createTime, rhs.createTime)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.loanId) 
				.append(this.productId) 
				.append(this.productName) 
				.append(this.userID) 
				.append(this.loanTitle) 
				.append(this.loanMoney) 
				.append(this.loanTimeLen) 
				.append(this.remark) 
				.append(this.expectAccrual) 
				.append(this.payIntersetWay) 
				.append(this.loanUse) 
				.append(this.monthlyInterest) 
				.append(this.monthlyCharge) 
				.append(this.startCharge) 
				.append(this.state) 
				.append(this.createTime) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("loanId", this.loanId) 
				.append("productId", this.productId) 
				.append("productName", this.productName) 
				.append("userID", this.userID) 
				.append("loanTitle", this.loanTitle) 
				.append("loanMoney", this.loanMoney) 
				.append("loanTimeLen", this.loanTimeLen) 
				.append("remark", this.remark) 
				.append("expectAccrual", this.expectAccrual) 
				.append("payIntersetWay", this.payIntersetWay) 
				.append("loanUse", this.loanUse) 
				.append("monthlyInterest", this.monthlyInterest) 
				.append("monthlyCharge", this.monthlyCharge) 
				.append("startCharge", this.startCharge) 
				.append("state", this.state) 
				.append("createTime", this.createTime) 
				.toString();
	}

	public String getFlownodes() {
		return flownodes;
	}

	public void setFlownodes(String flownodes) {
		this.flownodes = flownodes;
	}

	public Long getApprovalLoanTimeLen() {
		return approvalLoanTimeLen;
	}

	public void setApprovalLoanTimeLen(Long approvalLoanTimeLen) {
		this.approvalLoanTimeLen = approvalLoanTimeLen;
	}

	public java.util.Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(java.util.Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	public BigDecimal getApprovalLoanMoney() {
		return approvalLoanMoney;
	}

	public void setApprovalLoanMoney(BigDecimal approvalLoanMoney) {
		this.approvalLoanMoney = approvalLoanMoney;
	}
	
	public BigDecimal getApprovalYearManagementConsultingOfRate() {
		return approvalYearManagementConsultingOfRate;
	}

	public void setApprovalYearManagementConsultingOfRate(
			BigDecimal approvalYearManagementConsultingOfRate) {
		this.approvalYearManagementConsultingOfRate = approvalYearManagementConsultingOfRate;
	}

	public BigDecimal getApprovalYearFinanceServiceOfRate() {
		return approvalYearFinanceServiceOfRate;
	}

	public void setApprovalYearFinanceServiceOfRate(
			BigDecimal approvalYearFinanceServiceOfRate) {
		this.approvalYearFinanceServiceOfRate = approvalYearFinanceServiceOfRate;
	}

	public BigDecimal getApprovalYearAccrualRate() {
		return approvalYearAccrualRate;
	}

	public void setApprovalYearAccrualRate(BigDecimal approvalYearAccrualRate) {
		this.approvalYearAccrualRate = approvalYearAccrualRate;
	}
	public BigDecimal getApprovalOverdueRateLoan() {
		return approvalOverdueRateLoan;
	}

	public void setApprovalOverdueRateLoan(BigDecimal approvalOverdueRateLoan) {
		this.approvalOverdueRateLoan = approvalOverdueRateLoan;
	}

	public BigDecimal getApprovalOverdueRate() {
		return approvalOverdueRate;
	}

	public void setApprovalOverdueRate(BigDecimal approvalOverdueRate) {
		this.approvalOverdueRate = approvalOverdueRate;
	}
	
	public BigDecimal getYearAccrualRate() {
		return yearAccrualRate;
	}

	public void setYearAccrualRate(BigDecimal yearAccrualRate) {
		this.yearAccrualRate = yearAccrualRate;
	}

	public BigDecimal getYearFinanceServiceOfRate() {
		return yearFinanceServiceOfRate;
	}

	public void setYearFinanceServiceOfRate(BigDecimal yearFinanceServiceOfRate) {
		this.yearFinanceServiceOfRate = yearFinanceServiceOfRate;
	}

	public BigDecimal getYearManagementConsultingOfRate() {
		return yearManagementConsultingOfRate;
	}

	public void setYearManagementConsultingOfRate(
			BigDecimal yearManagementConsultingOfRate) {
		this.yearManagementConsultingOfRate = yearManagementConsultingOfRate;
	}


	


}
