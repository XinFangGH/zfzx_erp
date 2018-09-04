package com.zhiwei.credit.model.pay;

/**
 * 网贷平台 转账信息 二次分配
 * 
 * @author
 * 
 */
public class MadaiLoanInfoSecondaryBean
{
	/*
	 * 二次收款人ID
	 */
	private String LoanInMoneymoremore = "";
	
	/*
	 * 金额
	 */
	private String Amount = "";
	
	/*
	 * 用途
	 */
	private String TransferName = "";
	
	/*
	 * 备注
	 */
	private String Remark = "";
	
	public String getLoanInMoneymoremore()
	{
		return LoanInMoneymoremore;
	}
	
	public void setLoanInMoneymoremore(String loanInMoneymoremore)
	{
		LoanInMoneymoremore = loanInMoneymoremore;
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
	
}
