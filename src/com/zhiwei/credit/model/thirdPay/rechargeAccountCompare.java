package com.zhiwei.credit.model.thirdPay;
/**
 *系统冲值对账信息与第三方信息对比类 
 * 
 */
public class rechargeAccountCompare {
	//第三方信息
	  private String p2pRequestNo;//P2P平台请求流水号
	  private String createDate;//P2P平台交易日期
	  private String accountNo;///账户号
	  private String plateFormNo;//资金账户托管平台用户号/商户号
	  private String money;//金额
	  private String accountNoDate;//资金账户托管平台日期
	  private String accountNoTime;//资金账户托管平台时间
	  private String accountOrderNo;//资金账户托管平台流水号
	  //系统信息
	  private String userName;
	  public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	private String rechargeTime;//充值时间
	  private String rechargeMoney;//充值金额
	  private String accountBalance;//账户余额
	  private String p2pRequestNoLocal;//p2p平台请求流水号
	  private String p2pTransferDate;//p2p平台交易日期
	public String getP2pRequestNo() {
		return p2pRequestNo;
	}
	public void setP2pRequestNo(String p2pRequestNo) {
		this.p2pRequestNo = p2pRequestNo;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getPlateFormNo() {
		return plateFormNo;
	}
	public void setPlateFormNo(String plateFormNo) {
		this.plateFormNo = plateFormNo;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getAccountNoDate() {
		return accountNoDate;
	}
	public void setAccountNoDate(String accountNoDate) {
		this.accountNoDate = accountNoDate;
	}
	public String getAccountNoTime() {
		return accountNoTime;
	}
	public void setAccountNoTime(String accountNoTime) {
		this.accountNoTime = accountNoTime;
	}
	public String getAccountOrderNo() {
		return accountOrderNo;
	}
	public void setAccountOrderNo(String accountOrderNo) {
		this.accountOrderNo = accountOrderNo;
	}
	public String getRechargeTime() {
		return rechargeTime;
	}
	public void setRechargeTime(String rechargeTime) {
		this.rechargeTime = rechargeTime;
	}
	public String getRechargeMoney() {
		return rechargeMoney;
	}
	public void setRechargeMoney(String rechargeMoney) {
		this.rechargeMoney = rechargeMoney;
	}
	public String getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(String accountBalance) {
		this.accountBalance = accountBalance;
	}
	public String getP2pRequestNoLocal() {
		return p2pRequestNoLocal;
	}
	public void setP2pRequestNoLocal(String p2pRequestNoLocal) {
		this.p2pRequestNoLocal = p2pRequestNoLocal;
	}
	public String getP2pTransferDate() {
		return p2pTransferDate;
	}
	public void setP2pTransferDate(String p2pTransferDate) {
		this.p2pTransferDate = p2pTransferDate;
	}
}
