package com.zhiwei.credit.util.xmlToWord.printOrder;

public class POTable1 {
	
	private String orderId;
	
	private String periods;
	private String productName = "";
	
	/**
	 * 本期年化收益率
	 */
	private String productRate = "";
	
	/**
	 * 出账日期
	 */
	private String planDate = "";
	
	/**
	 * 债权收益
	 */
	private String creditorIncomeMoney = "";
	/**
	 * 每期理财产品应支付的本金
	 */
	private String prinpalMoney="";
	/**
	 * 退出理财产品费用
	 */
	private String quitMoney="";

	public String getProductRate() {
		return productRate;
	}

	public void setProductRate(String productRate) {
		this.productRate = productRate;
	}

	public String getPlanDate() {
		return planDate;
	}

	public void setPlanDate(String planDate) {
		this.planDate = planDate;
	}

	public String getCreditorIncomeMoney() {
		return creditorIncomeMoney;
	}

	public void setCreditorIncomeMoney(String creditorIncomeMoney) {
		this.creditorIncomeMoney = creditorIncomeMoney;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setQuitMoney(String quitMoney) {
		this.quitMoney = quitMoney;
	}

	public String getQuitMoney() {
		return quitMoney;
	}

	public void setPrinpalMoney(String prinpalMoney) {
		this.prinpalMoney = prinpalMoney;
	}

	public String getPrinpalMoney() {
		return prinpalMoney;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setPeriods(String periods) {
		this.periods = periods;
	}

	public String getPeriods() {
		return periods;
	}


	
	
}
