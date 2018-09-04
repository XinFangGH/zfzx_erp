package com.thirdPayInterface.MoneyMorePay.MoneyResponse;
/**
 * 查询充值返回具体参数
 * @author hgh
 *
 */
public class QueryRechargeBean {
	/**
	 * 充值人乾多多标识
	 */
	private String RechargeMoneymoremore;
	/**
	 * 平台乾多多标识
	 */
	private String PlatformMoneymoremore;
	/**
	 * 乾多多流水号
	 */
	private String LoanNo;
	/**
	 * 网贷平台订单号
	 */
	private String OrderNo;
	/**
	 * 金额
	 */
	private Double Amount;
	/**
	 * 手续费
	 */
	private Double Fee;
	/**
	 * 平台承担手续费
	 */
	private Double FeePlatform;
	/**
	 * 手续费类型
	 * 1.充值成功时从充值人账户全额扣除
     * 2.充值成功时从平台自有账户全额扣除
     * 3.充值成功时从充值人账户扣除与提现手续费的差值
	 */
	private Integer FeeType;
	/**
	 * 充值类型
	 * 空.网银充值
     *  1.代扣充值
	 *  2.快捷支付
     *  3.汇款充值
     *  4.企业网银
	 */
	private Integer RechargeType;
	/**
	 * 充值状态
	 * 0.未充值
     * 1.成功
     * 2.失败
	 */
	private Integer RechargeState;
	/**
	 * 充值时间
	 */
	private String RechargeTime;
	public String getRechargeMoneymoremore() {
		return RechargeMoneymoremore;
	}
	public void setRechargeMoneymoremore(String rechargeMoneymoremore) {
		RechargeMoneymoremore = rechargeMoneymoremore;
	}
	public String getPlatformMoneymoremore() {
		return PlatformMoneymoremore;
	}
	public void setPlatformMoneymoremore(String platformMoneymoremore) {
		PlatformMoneymoremore = platformMoneymoremore;
	}
	public String getLoanNo() {
		return LoanNo;
	}
	public void setLoanNo(String loanNo) {
		LoanNo = loanNo;
	}
	public String getOrderNo() {
		return OrderNo;
	}
	public void setOrderNo(String orderNo) {
		OrderNo = orderNo;
	}
	public Double getAmount() {
		return Amount;
	}
	public void setAmount(Double amount) {
		Amount = amount;
	}
	public Double getFee() {
		return Fee;
	}
	public void setFee(Double fee) {
		Fee = fee;
	}
	public Double getFeePlatform() {
		return FeePlatform;
	}
	public void setFeePlatform(Double feePlatform) {
		FeePlatform = feePlatform;
	}
	public Integer getFeeType() {
		return FeeType;
	}
	public void setFeeType(Integer feeType) {
		FeeType = feeType;
	}
	public Integer getRechargeType() {
		return RechargeType;
	}
	public void setRechargeType(Integer rechargeType) {
		RechargeType = rechargeType;
	}
	public Integer getRechargeState() {
		return RechargeState;
	}
	public void setRechargeState(Integer rechargeState) {
		RechargeState = rechargeState;
	}
	public String getRechargeTime() {
		return RechargeTime;
	}
	public void setRechargeTime(String rechargeTime) {
		RechargeTime = rechargeTime;
	}

}
