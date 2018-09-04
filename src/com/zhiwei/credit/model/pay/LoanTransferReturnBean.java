package com.zhiwei.credit.model.pay;

/**
 * 网贷平台 转账接口 返回
 * 
 * @author
 * 
 */
public class LoanTransferReturnBean
{
	/*
	 * 转账列表
	 */
	private String LoanJsonList = "";
	
	/*
	 * 平台乾多多标识
	 */
	private String PlatformMoneymoremore = "";
	
	/*
	 * 操作类型
	 */
	private String Action = "";
	
	/*
	 * 随机时间戳
	 */
	private String RandomTimeStamp = "";
	
	/*
	 * 自定义备注
	 */
	private String Remark1 = "";
	
	/*
	 * 自定义备注
	 */
	private String Remark2 = "";
	
	/*
	 * 自定义备注
	 */
	private String Remark3 = "";
	
	/*
	 * 返回码
	 */
	private String ResultCode = "";
	
	/*
	 * 返回信息
	 */
	private String Message = "";
	
	/*
	 * 签名信息
	 */
	private String SignInfo = "";
	
	public String getLoanJsonList()
	{
		return LoanJsonList;
	}
	
	public void setLoanJsonList(String loanJsonList)
	{
		LoanJsonList = loanJsonList;
	}
	
	public String getPlatformMoneymoremore()
	{
		return PlatformMoneymoremore;
	}
	
	public void setPlatformMoneymoremore(String platformMoneymoremore)
	{
		PlatformMoneymoremore = platformMoneymoremore;
	}
	
	public String getAction()
	{
		return Action;
	}
	
	public void setAction(String action)
	{
		Action = action;
	}
	
	public String getRandomTimeStamp()
	{
		return RandomTimeStamp;
	}
	
	public void setRandomTimeStamp(String randomTimeStamp)
	{
		RandomTimeStamp = randomTimeStamp;
	}
	
	public String getRemark1()
	{
		return Remark1;
	}
	
	public void setRemark1(String remark1)
	{
		Remark1 = remark1;
	}
	
	public String getRemark2()
	{
		return Remark2;
	}
	
	public void setRemark2(String remark2)
	{
		Remark2 = remark2;
	}
	
	public String getRemark3()
	{
		return Remark3;
	}
	
	public void setRemark3(String remark3)
	{
		Remark3 = remark3;
	}
	
	public String getResultCode()
	{
		return ResultCode;
	}
	
	public void setResultCode(String resultCode)
	{
		ResultCode = resultCode;
	}
	
	public String getMessage()
	{
		return Message;
	}
	
	public void setMessage(String message)
	{
		Message = message;
	}
	
	public String getSignInfo()
	{
		return SignInfo;
	}
	
	public void setSignInfo(String signInfo)
	{
		SignInfo = signInfo;
	}
	
}
