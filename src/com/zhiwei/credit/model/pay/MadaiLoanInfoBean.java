package com.zhiwei.credit.model.pay;

/**
 * 网贷平台 操作信息
 * 
 * @author
 * 
 */
public class MadaiLoanInfoBean
{
	/*
	 * 付款人ID
	 */
	private String LoanOutMoneymoremore = "";
	
	/*
	 * 收款人ID
	 */
	private String LoanInMoneymoremore = "";
	
	/*
	 * 订单号
	 */
	private String OrderNo = "";
	
	private String ExchangeBatchNo="";
	private String AdvanceBatchNo="";
	

	/*
	 * 标号
	 */
	private String BatchNo = "";
	
	/*
	 * 金额
	 */
	private String Amount = "";
	
	/*
	 * 满标金额
	 */
	private String FullAmount = "";
	
	/*
	 * 用途
	 */
	private String TransferName = "";
	
	/*
	 * 备注
	 */
	private String Remark = "";
	
	/*
	 * 二次分配列表
	 */
	private String SecondaryJsonList = "";
	
	public String getLoanOutMoneymoremore()
	{
		return LoanOutMoneymoremore;
	}
	
	public void setLoanOutMoneymoremore(String loanOutMoneymoremore)
	{
		LoanOutMoneymoremore = loanOutMoneymoremore;
	}
	
	public String getLoanInMoneymoremore()
	{
		return LoanInMoneymoremore;
	}
	
	public void setLoanInMoneymoremore(String loanInMoneymoremore)
	{
		LoanInMoneymoremore = loanInMoneymoremore;
	}
	
	public String getOrderNo()
	{
		return OrderNo;
	}
	
	public void setOrderNo(String orderNo)
	{
		OrderNo = orderNo;
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

	public String getAmount()
	{
		return Amount;
	}
	
	public void setAmount(String amount)
	{
		Amount = amount;
	}
	
	public String getTransferName()
	{
		return TransferName;
	}
	
	public void setTransferName(String transferName)
	{
		TransferName = transferName;
	}
	
	public String getRemark()
	{
		return Remark;
	}
	
	public void setRemark(String remark)
	{
		Remark = remark;
	}
	
	public String getBatchNo()
	{
		return BatchNo;
	}
	
	public void setBatchNo(String batchNo)
	{
		BatchNo = batchNo;
	}
	
	public String getFullAmount()
	{
		return FullAmount;
	}
	
	public void setFullAmount(String fullAmount)
	{
		FullAmount = fullAmount;
	}
	
	public String getSecondaryJsonList()
	{
		return SecondaryJsonList;
	}
	
	public void setSecondaryJsonList(String secondaryJsonList)
	{
		SecondaryJsonList = secondaryJsonList;
	}
	
}
