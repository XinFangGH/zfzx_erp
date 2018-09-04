package com.zhiwei.credit.model.creditFlow.customer.enterprise;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
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
 * BpCustEntCashflowAndSaleIncome Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class BpCustEntCashflowAndSaleIncome extends com.zhiwei.core.model.BaseModel {

    protected Integer cashflowAndSaleIncomeId;
	protected Integer month;
	protected BigDecimal mainBusinessIncomeMoney;
	protected BigDecimal cashflowincomeMoney;
	protected BigDecimal payGoodsMoney;
	protected BigDecimal cashflowpayMoney;
	protected Enterprise enterprise;


	/**
	 * Default Empty Constructor for class BpCustEntCashflowAndSaleIncome
	 */
	public BpCustEntCashflowAndSaleIncome () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class BpCustEntCashflowAndSaleIncome
	 */
	public BpCustEntCashflowAndSaleIncome (
			Integer in_cashflowAndSaleIncomeId
        ) {
		this.setCashflowAndSaleIncomeId(in_cashflowAndSaleIncomeId);
    }

    

	public Enterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}


	
	public Integer getCashflowAndSaleIncomeId() {
		return cashflowAndSaleIncomeId;
	}

	public void setCashflowAndSaleIncomeId(Integer cashflowAndSaleIncomeId) {
		this.cashflowAndSaleIncomeId = cashflowAndSaleIncomeId;
	}


	
	/**
	 * Set the mainBusinessIncome
	
	


	





	public Integer getEnterpriseId() {
		return this.getEnterprise()==null?null:this.getEnterprise().getId();
	}
	
	/**
	 * Set the mortgageId
	 */	
	public void setEnterpriseId(Integer aValue) {
	    if (aValue==null) {
	    	enterprise = null;
	    } else if (enterprise == null) {
	    //	enterprise = new com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise(aValue);
	    	enterprise.setVersion(new Integer(0));
	    } else {
	    	//
	    	enterprise.setId(aValue);
	    }
	}	
	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}


	public BigDecimal getMainBusinessIncomeMoney() {
		return mainBusinessIncomeMoney;
	}

	public void setMainBusinessIncomeMoney(BigDecimal mainBusinessIncomeMoney) {
		this.mainBusinessIncomeMoney = mainBusinessIncomeMoney;
	}

	public BigDecimal getCashflowincomeMoney() {
		return cashflowincomeMoney;
	}

	public void setCashflowincomeMoney(BigDecimal cashflowincomeMoney) {
		this.cashflowincomeMoney = cashflowincomeMoney;
	}

	public BigDecimal getPayGoodsMoney() {
		return payGoodsMoney;
	}

	public void setPayGoodsMoney(BigDecimal payGoodsMoney) {
		this.payGoodsMoney = payGoodsMoney;
	}

	public BigDecimal getCashflowpayMoney() {
		return cashflowpayMoney;
	}

	public void setCashflowpayMoney(BigDecimal cashflowpayMoney) {
		this.cashflowpayMoney = cashflowpayMoney;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof BpCustEntCashflowAndSaleIncome)) {
			return false;
		}
		BpCustEntCashflowAndSaleIncome rhs = (BpCustEntCashflowAndSaleIncome) object;
		return new EqualsBuilder()
				.append(this.cashflowAndSaleIncomeId, rhs.cashflowAndSaleIncomeId)
				.append(this.month, rhs.month)
				.append(this.mainBusinessIncomeMoney, rhs.mainBusinessIncomeMoney)
				.append(this.cashflowincomeMoney, rhs.cashflowincomeMoney)
				.append(this.payGoodsMoney, rhs.payGoodsMoney)
				.append(this.cashflowpayMoney, rhs.cashflowpayMoney)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.cashflowAndSaleIncomeId) 
				.append(this.month) 
				.append(this.mainBusinessIncomeMoney) 
				.append(this.cashflowincomeMoney) 
				.append(this.payGoodsMoney) 
				.append(this.cashflowpayMoney) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("cashflowAndSaleIncomeId", this.cashflowAndSaleIncomeId) 
				.append("month", this.month) 
				.append("mainBusinessIncomeMoney", this.mainBusinessIncomeMoney) 
				.append("cashflowincomeMoney", this.cashflowincomeMoney) 
				.append("payGoodsMoney", this.payGoodsMoney) 
				.append("cashflowpayMoney", this.cashflowpayMoney) 
				.toString();
	}



}
