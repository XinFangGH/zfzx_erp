package com.thirdPayInteface;

import java.math.BigDecimal;

/**
 * 投资人投资记录，还款信息
 * @author Administrator
 *
 */
public class CommonRequestInvestRecord {
	private String investCusterId;//投资客户的id
	private String investCusterType;//投资客户的类型
	private String invest_thirdPayConfigId;//投资客户的第三方账号
	
	private String requestNo;//请求流水号
	private String bussinessType;//业务类型
	private String bidRequestNo;//投标流水号（已经投标成功的流水号）
	private BigDecimal amount =new BigDecimal("0");//金额（投标金额，还款金额）
	private BigDecimal fee=new BigDecimal("0");//分润金额（还款时需要）
	private String plateType="";//分润账户类型（还款时使用）
	private String plateAccount;//分润账号（还款时使用）
	
	private String inverstRecordStatus;//单笔交易状态 
	private String inverstRecordStatusMsg;//单笔交易状态描述
	
	private String loanerCusterId;//借款人的id
	private String loanerCusterType;//借款人的类型
	private String loaner_thirdPayConfigId;//借款人的第三方账号
	
	public String getInvestCusterId() {
		return investCusterId;
	}
	public void setInvestCusterId(String investCusterId) {
		this.investCusterId = investCusterId;
	}
	public String getInvestCusterType() {
		return investCusterType;
	}
	public void setInvestCusterType(String investCusterType) {
		this.investCusterType = investCusterType;
	}
	public String getInvest_thirdPayConfigId() {
		return invest_thirdPayConfigId;
	}
	public void setInvest_thirdPayConfigId(String investThirdPayConfigId) {
		invest_thirdPayConfigId = investThirdPayConfigId;
	}
	public String getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	public String getBussinessType() {
		return bussinessType;
	}
	public void setBussinessType(String bussinessType) {
		this.bussinessType = bussinessType;
	}
	public String getBidRequestNo() {
		return bidRequestNo;
	}
	public void setBidRequestNo(String bidRequestNo) {
		this.bidRequestNo = bidRequestNo;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getFee() {
		return fee;
	}
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
	public String getPlateType() {
		return plateType;
	}
	public void setPlateType(String plateType) {
		this.plateType = plateType;
	}
	public String getPlateAccount() {
		return plateAccount;
	}
	public void setPlateAccount(String plateAccount) {
		this.plateAccount = plateAccount;
	}
	public String getInverstRecordStatus() {
		return inverstRecordStatus;
	}
	public void setInverstRecordStatus(String inverstRecordStatus) {
		this.inverstRecordStatus = inverstRecordStatus;
	}
	public String getInverstRecordStatusMsg() {
		return inverstRecordStatusMsg;
	}
	public void setInverstRecordStatusMsg(String inverstRecordStatusMsg) {
		this.inverstRecordStatusMsg = inverstRecordStatusMsg;
	}
	public void setLoanerCusterId(String loanerCusterId) {
		this.loanerCusterId = loanerCusterId;
	}
	public String getLoanerCusterId() {
		return loanerCusterId;
	}
	public void setLoanerCusterType(String loanerCusterType) {
		this.loanerCusterType = loanerCusterType;
	}
	public String getLoanerCusterType() {
		return loanerCusterType;
	}
	public void setLoaner_thirdPayConfigId(String loaner_thirdPayConfigId) {
		this.loaner_thirdPayConfigId = loaner_thirdPayConfigId;
	}
	public String getLoaner_thirdPayConfigId() {
		return loaner_thirdPayConfigId;
	}

	
}
