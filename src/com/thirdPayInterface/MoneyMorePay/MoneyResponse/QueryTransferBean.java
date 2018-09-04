package com.thirdPayInterface.MoneyMorePay.MoneyResponse;

import java.util.List;

/**
 * 查询转账返回参数
 * @author hgh
 *
 */

public class QueryTransferBean {
	/**
	 * 付款人乾多多标识
	 */
	protected String LoanOutMoneymoremore;
	/**
	 * 收款人乾多多标识
	 */
	protected String LoanInMoneymoremore;
	/**
	 * 乾多多流水号
	 */
	protected String LoanNo;
	/**
	 * 网贷平台订单号
	 */
	protected String OrderNo;
	/**
	 * 网贷平台标号
	 */
	protected String BatchNo;
	/**
	 * 流转标标号
	 */
	protected String ExchangeBatchNo;
	/**
	 * 垫资标号
	 */
	protected String AdvanceBatchNo;
	/**
	 * 金额
	 */
	protected Double Amount;
	/**
	 * 平台乾多多标识
	 */
	protected String PlatformMoneymoremore;
	/**
	 * 转账类型
	 * 1.投标
     * 2.还款
	 */
	protected Integer TransferAction;
	/**
	 * 操作类型
	 * 1.手动投标
	 * 2.自动投标
	 */
	protected Integer Action;
	/**
	 * 转账方式
	 * 1.桥连
	 * 2.直连
	 */
	protected Integer TransferType;
	/**
	 * 转账状态
	 * 0.未转账
	 * 1.已转账
	 */
	protected Integer TransferState;
	/**
	 * 转账时间
	 */
	protected String TransferTime;
	/**
	 * 操作状态
	 * 0.未操作
	 * 1.已通过
	 * 2.已退回
	 * 3.自动通过
	 */
	protected Integer ActState;
	/**
	 * 操作时间
	 */
	protected String ActTime;
	/**
	 * 操作流水号
	 */
	protected String ActNo;
	/**
	 * 二次分配确认状态
	 * 空.无二次分配
	 * 0.未确认
	 * 1.同意
	 * 2.不同意
	 * 3.系统自动同意
	 */
	protected Integer SecondaryState;
	/**
	 * 二次分配确认时间
	 */
	protected String SecondaryTime;
	/**
	 * 二次分配列表
	 */
	protected List SecondaryJsonList;
	/**
	 * 用途 ,投标
	 */
	protected String TransferName;
	/**
	 * 备注
	 */
	protected String Remark;
	public String getLoanOutMoneymoremore() {
		return LoanOutMoneymoremore;
	}
	public void setLoanOutMoneymoremore(String loanOutMoneymoremore) {
		LoanOutMoneymoremore = loanOutMoneymoremore;
	}
	public String getLoanInMoneymoremore() {
		return LoanInMoneymoremore;
	}
	public void setLoanInMoneymoremore(String loanInMoneymoremore) {
		LoanInMoneymoremore = loanInMoneymoremore;
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
	public String getBatchNo() {
		return BatchNo;
	}
	public void setBatchNo(String batchNo) {
		BatchNo = batchNo;
	}
	public String getExchangeBatchNo() {
		return ExchangeBatchNo;
	}
	public void setExchangeBatchNo(String exchangeBatchNo) {
		ExchangeBatchNo = exchangeBatchNo;
	}
	public String getAdvanceBatchNo() {
		return AdvanceBatchNo;
	}
	public void setAdvanceBatchNo(String advanceBatchNo) {
		AdvanceBatchNo = advanceBatchNo;
	}
	public Double getAmount() {
		return Amount;
	}
	public void setAmount(Double amount) {
		Amount = amount;
	}
	public String getPlatformMoneymoremore() {
		return PlatformMoneymoremore;
	}
	public void setPlatformMoneymoremore(String platformMoneymoremore) {
		PlatformMoneymoremore = platformMoneymoremore;
	}
	public Integer getTransferAction() {
		return TransferAction;
	}
	public void setTransferAction(Integer transferAction) {
		TransferAction = transferAction;
	}
	public Integer getAction() {
		return Action;
	}
	public void setAction(Integer action) {
		Action = action;
	}
	public Integer getTransferType() {
		return TransferType;
	}
	public void setTransferType(Integer transferType) {
		TransferType = transferType;
	}
	public Integer getTransferState() {
		return TransferState;
	}
	public void setTransferState(Integer transferState) {
		TransferState = transferState;
	}
	public String getTransferTime() {
		return TransferTime;
	}
	public void setTransferTime(String transferTime) {
		TransferTime = transferTime;
	}
	public Integer getActState() {
		return ActState;
	}
	public void setActState(Integer actState) {
		ActState = actState;
	}
	public String getActTime() {
		return ActTime;
	}
	public void setActTime(String actTime) {
		ActTime = actTime;
	}
	public String getActNo() {
		return ActNo;
	}
	public void setActNo(String actNo) {
		ActNo = actNo;
	}
	public Integer getSecondaryState() {
		return SecondaryState;
	}
	public void setSecondaryState(Integer secondaryState) {
		SecondaryState = secondaryState;
	}
	public String getSecondaryTime() {
		return SecondaryTime;
	}
	public void setSecondaryTime(String secondaryTime) {
		SecondaryTime = secondaryTime;
	}
	public String getTransferName() {
		return TransferName;
	}
	public void setTransferName(String transferName) {
		TransferName = transferName;
	}
	public String getRemark() {
		return Remark;
	}
	public void setRemark(String remark) {
		Remark = remark;
	}
	public List getSecondaryJsonList() {
		return SecondaryJsonList;
	}
	public void setSecondaryJsonList(List secondaryJsonList) {
		SecondaryJsonList = secondaryJsonList;
	}
	
	

}
