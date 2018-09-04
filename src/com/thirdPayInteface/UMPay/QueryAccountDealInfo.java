package com.thirdPayInteface.UMPay;


import java.math.BigDecimal;
import java.util.Date;

/**
 * 第三方支付查询出来的交易流水明细
 * @author Administrator
 *
 */
public class QueryAccountDealInfo {
	/**
	 * 第三方支付账号（包括平台账号）
	 */
	private String  thirdPayConfigId;
	/**
	 * 单笔交易金额  
	 */
	private BigDecimal amount=new BigDecimal(0) ;
	
	/**
	 * 单笔交易后余额
	 */
	private BigDecimal balance=new BigDecimal(0);
	/**
	 * 交易类型
	 */
	private String  transferType;
	
	/**
	 * 交易方向
	 */
	private String transferDirection;
	/**
	 * 交易流水号
	 */
	private String requestNo;
	/**
	 * 交易状态
	 */
	private String status;
	/**
	 * 交易日期
	 */
	private Date transferDate;
	/**
	 * 手续费
	 */
	private BigDecimal fee=new BigDecimal(0);
	public String getThirdPayConfigId() {
		return thirdPayConfigId;
	}
	public void setThirdPayConfigId(String thirdPayConfigId) {
		this.thirdPayConfigId = thirdPayConfigId;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public String getTransferType() {
		return transferType;
	}
	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}
	public String getTransferDirection() {
		return transferDirection;
	}
	public void setTransferDirection(String transferDirection) {
		this.transferDirection = transferDirection;
	}
	public String getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setTransferDate(Date transferDate) {
		this.transferDate = transferDate;
	}
	public Date getTransferDate() {
		return transferDate;
	}
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
	public BigDecimal getFee() {
		return fee;
	} 
	
	
	

	
	
	

	
	
}
