package com.zhiwei.credit.model.p2p.loan;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * @author 
 *
 */
/**
 * P2pLoanProduct Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * p2p贷款产品
 */
public class P2pLoanProduct extends com.zhiwei.core.model.BaseModel {

    protected Long productId;
    /**
     * 产品名称
     */
	protected String productName;
	/**
	 * 业务品种 person=个人贷，enterprise=企业贷
	 */
	protected String operationType;
	/**
	 * 使用范围
	 */
	protected String userScope;
	/**
	 * 产品状态(1新增信贷产品、产品参数配置,2申请步骤配置,3信贷产品发布 ,4信贷产品关闭,5信贷产品启用)
	 */
	protected Long productState;
	/**
	 * 贷款额度起
	 */
	protected BigDecimal loanStartMoney;
	/**
	 * 贷款额度至
	 */
	protected BigDecimal loanEndMoney;
	/**
	 * 审批时间起
	 */
	protected Long approveStartTime;
	/**
	 * 审批时间至
	 */
	protected Long approveEndTime;
	/**
	 * 还款方式 , sameprincipalandInterest=等额本息，singleInterest=等额本金，sameprincipalsameInterest=等本等息，singleInterest=按期收息，到期还本
	 * otherMothod=其他还款方式
	 */
	protected String accrualtype; 
	/**
	 * 还款周期,monthPay=月，yearPay=年，dayPay=日，seasonPay=季
	 */
	protected String payaccrualType; 
	/**
	 * 前置付息，0为否，1为是
	 */
	protected Integer isPreposePayAccrual; 
	/**
	 * 一次性支付全部利息，0为否，1为是
	 */
	protected Integer isInterestByOneTime; 

	
	//不与数据库映射
	public String productStateValue;
	public String operationTypeValue;
	
	
	public String getAccrualtype() {
		return accrualtype;
	}

	public void setAccrualtype(String accrualtype) {
		this.accrualtype = accrualtype;
	}

	public String getPayaccrualType() {
		return payaccrualType;
	}

	public void setPayaccrualType(String payaccrualType) {
		this.payaccrualType = payaccrualType;
	}

	public Integer getIsPreposePayAccrual() {
		return isPreposePayAccrual;
	}

	public void setIsPreposePayAccrual(Integer isPreposePayAccrual) {
		this.isPreposePayAccrual = isPreposePayAccrual;
	}

	public Integer getIsInterestByOneTime() {
		return isInterestByOneTime;
	}

	public void setIsInterestByOneTime(Integer isInterestByOneTime) {
		this.isInterestByOneTime = isInterestByOneTime;
	}

	/**
	 * Default Empty Constructor for class P2pLoanProduct
	 */
	public P2pLoanProduct () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class P2pLoanProduct
	 */
	public P2pLoanProduct (
		 Long in_productId
        ) {
		this.setProductId(in_productId);
    }

	public BigDecimal getLoanStartMoney() {
		return loanStartMoney;
	}

	public void setLoanStartMoney(BigDecimal loanStartMoney) {
		this.loanStartMoney = loanStartMoney;
	}

	public BigDecimal getLoanEndMoney() {
		return loanEndMoney;
	}

	public void setLoanEndMoney(BigDecimal loanEndMoney) {
		this.loanEndMoney = loanEndMoney;
	}

	public Long getApproveStartTime() {
		return approveStartTime;
	}

	public void setApproveStartTime(Long approveStartTime) {
		this.approveStartTime = approveStartTime;
	}

	public Long getApproveEndTime() {
		return approveEndTime;
	}

	public void setApproveEndTime(Long approveEndTime) {
		this.approveEndTime = approveEndTime;
	}

	/**
	 * 	 * @return Long
     * @hibernate.id column="productId" type="java.lang.Long" generator-class="native"
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

	public String getProductStateValue() {
		return productStateValue;
	}

	public void setProductStateValue(String productStateValue) {
		this.productStateValue = productStateValue;
	}

	public String getOperationTypeValue() {
		return operationTypeValue;
	}

	public void setOperationTypeValue(String operationTypeValue) {
		this.operationTypeValue = operationTypeValue;
	}

	/**
	 * 	 * @return String
	 * @hibernate.property column="productName" type="java.lang.String" length="255" not-null="true" unique="false"
	 */
	public String getProductName() {
		return this.productName;
	}
	
	/**
	 * Set the productName
	 * @spring.validator type="required"
	 */	
	public void setProductName(String aValue) {
		this.productName = aValue;
	}	

	public Long getProductState() {
		return productState;
	}

	public void setProductState(Long productState) {
		this.productState = productState;
	}

	/**
	 * 	 * @return String
	 * @hibernate.property column="operationType" type="java.lang.String" length="255" not-null="true" unique="false"
	 */
	public String getOperationType() {
		return this.operationType;
	}
	
	/**
	 * Set the operationType
	 * @spring.validator type="required"
	 */	
	public void setOperationType(String aValue) {
		this.operationType = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="userScope" type="java.lang.String" length="255" not-null="true" unique="false"
	 */
	public String getUserScope() {
		return this.userScope;
	}
	
	/**
	 * Set the userScope
	 * @spring.validator type="required"
	 */	
	public void setUserScope(String aValue) {
		this.userScope = aValue;
	}	

	

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.productId) 
				.append(this.productName) 
				.append(this.operationType) 
				.append(this.userScope) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("productId", this.productId) 
				.append("productName", this.productName) 
				.append("operationType", this.operationType) 
				.append("userScope", this.userScope) 
				.toString();
	}



}
