package com.thirdPayInterface.MoneyMorePay.MoneyResponse;
/**
 * 查询提现返回具体参数
 * @author hgh
 *
 */
public class QueryExtractionBean {
	/**
	 * 提现人乾多多标识
	 */
	private String WithdrawMoneymoremore;
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
	 * 用户承担的最高手续费
	 */
	private Double FeeMax;
	/**
	 * 用户实际承担的手续费金额
	 */
	private Double FeeWithdraws;
	/**
	 * 平台承担的手续费比例
	 */
	private Integer FeePercent;
	/**
	 * 平台承担的手续费金额
	 */
	private Double Fee;
	/**
	 * 平台扣除的免费提现额
	 */
	private Double FreeLimit;
	/**
	 * 上浮费率
	 */
	private Double FeeRate;
	/**
	 * 平台分润
	 */
	private Double FeeSplitting;
	/**
	 * 分润结算状态
	 * 空.无分润或未结算
     * 1.已结算
	 */
	private Integer SplittingSettleState;
	/**
	 * 提现状态
	 * 0.已提交
     * 1.成功
     * 2.已退回
     * 3.待平台审核
	 */
	private Integer WithdrawsState;
	/**
	 * 提现时间
	 */
	private String WithdrawsTime;
	/**
	 * 平台审核状态
	 * 空.不审核
     * 0.未审核
     * 1.审核通过
     * 2.审核退回
	 */
	private Integer PlatformAuditState;
	/**
	 * 平台审核时间
	 */
	private String PlatformAuditTime;
	/**
	 * 提现退回时间
	 */
	private String WithdrawsBackTime;
	public String getWithdrawMoneymoremore() {
		return WithdrawMoneymoremore;
	}
	public void setWithdrawMoneymoremore(String withdrawMoneymoremore) {
		WithdrawMoneymoremore = withdrawMoneymoremore;
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
	public Double getFeeMax() {
		return FeeMax;
	}
	public void setFeeMax(Double feeMax) {
		FeeMax = feeMax;
	}
	public Double getFeeWithdraws() {
		return FeeWithdraws;
	}
	public void setFeeWithdraws(Double feeWithdraws) {
		FeeWithdraws = feeWithdraws;
	}
	public Integer getFeePercent() {
		return FeePercent;
	}
	public void setFeePercent(Integer feePercent) {
		FeePercent = feePercent;
	}
	public Double getFee() {
		return Fee;
	}
	public void setFee(Double fee) {
		Fee = fee;
	}
	public Double getFreeLimit() {
		return FreeLimit;
	}
	public void setFreeLimit(Double freeLimit) {
		FreeLimit = freeLimit;
	}
	public Double getFeeRate() {
		return FeeRate;
	}
	public void setFeeRate(Double feeRate) {
		FeeRate = feeRate;
	}
	public Double getFeeSplitting() {
		return FeeSplitting;
	}
	public void setFeeSplitting(Double feeSplitting) {
		FeeSplitting = feeSplitting;
	}
	public Integer getSplittingSettleState() {
		return SplittingSettleState;
	}
	public void setSplittingSettleState(Integer splittingSettleState) {
		SplittingSettleState = splittingSettleState;
	}
	public Integer getWithdrawsState() {
		return WithdrawsState;
	}
	public void setWithdrawsState(Integer withdrawsState) {
		WithdrawsState = withdrawsState;
	}
	public String getWithdrawsTime() {
		return WithdrawsTime;
	}
	public void setWithdrawsTime(String withdrawsTime) {
		WithdrawsTime = withdrawsTime;
	}
	public Integer getPlatformAuditState() {
		return PlatformAuditState;
	}
	public void setPlatformAuditState(Integer platformAuditState) {
		PlatformAuditState = platformAuditState;
	}
	public String getPlatformAuditTime() {
		return PlatformAuditTime;
	}
	public void setPlatformAuditTime(String platformAuditTime) {
		PlatformAuditTime = platformAuditTime;
	}
	public String getWithdrawsBackTime() {
		return WithdrawsBackTime;
	}
	public void setWithdrawsBackTime(String withdrawsBackTime) {
		WithdrawsBackTime = withdrawsBackTime;
	}
	

}
