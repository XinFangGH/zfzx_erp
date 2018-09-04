package com.zhiwei.credit.model.thirdPay;

/**
 * 国付宝 直付接口 实体
 * 
 * @author Yzc
 * 
 */
public class GoZhiFuVO {
	/**
	 * 1 GBK 2 UTF-8 默认为 1
	 */
	public static final String charset = "1";// 编码格式
	/**
	 * 格式：数字 1 中文 2 英文
	 */
	public static final String language = "1";// 语言
	/**
	 * 1 MD5 2 SHA 默认为 1
	 */
	public static final String signType = "1";// 加密方式
	/**
	 * 本域指明了交易的类型，支付网关接口必须为8888
	 */
	public static final String tranCode = "4025"; // 交易代码
	/**
	 * 多币种预留字段，暂只能为156，代表人民币
	 */
	public static final String currencyType = "156";// 币种

	public static final String version = "1.0"; // 版本号
	/**
	 * 公私标识 1 格式：数字 1 对公 2 对私
	 */
	public static final String corpPersonFlag1 = "1";
	public static final String corpPersonFlag2 = "2";

	String customerId = "";// 企业ID
	String merOrderNum = ""; // 订单号
	String tranAmt = "";// 交易金额
	String merURL = "";// 商户后台通知地址

	/**
	 * 收款人银行开户名 100 格式：中文、英文 不可 张三
	 */
	String recvBankAcctName = "";
	/**
	 * 收款方银行所在省份 20 格式：中文、英文 不可 北京
	 */
	String recvBankProvince = "";
	/**
	 * 收款方银行所在城市 20 格式：中文、英文 不可 北京
	 */
	String recvBankCity = "";
	/**
	 * 收款方银行名称 50 格式：中文、英文 不可 北京银行
	 */
	String recvBankName = "";
	/**
	 * 收款方银行网点名称
	 */
	String recvBankBranchName = "";
	/**
	 * 收款方银行账号
	 */
	String recvBankAcctNum = "";

	String tranDateTime = "";// 交易时间
	/**
	 * 交易备注
	 */
	String description="";
	String verficationCode="";
	
	public String getVerficationCode() {
		return verficationCode;
	}
	public void setVerficationCode(String verficationCode) {
		this.verficationCode = verficationCode;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getMerOrderNum() {
		return merOrderNum;
	}
	public void setMerOrderNum(String merOrderNum) {
		this.merOrderNum = merOrderNum;
	}
	public String getTranAmt() {
		return tranAmt;
	}
	public void setTranAmt(String tranAmt) {
		this.tranAmt = tranAmt;
	}
	public String getMerURL() {
		return merURL;
	}
	public void setMerURL(String merURL) {
		this.merURL = merURL;
	}
	public String getRecvBankAcctName() {
		return recvBankAcctName;
	}
	public void setRecvBankAcctName(String recvBankAcctName) {
		this.recvBankAcctName = recvBankAcctName;
	}
	public String getRecvBankProvince() {
		return recvBankProvince;
	}
	public void setRecvBankProvince(String recvBankProvince) {
		this.recvBankProvince = recvBankProvince;
	}
	public String getRecvBankCity() {
		return recvBankCity;
	}
	public void setRecvBankCity(String recvBankCity) {
		this.recvBankCity = recvBankCity;
	}
	public String getRecvBankName() {
		return recvBankName;
	}
	public void setRecvBankName(String recvBankName) {
		this.recvBankName = recvBankName;
	}
	public String getRecvBankBranchName() {
		return recvBankBranchName;
	}
	public void setRecvBankBranchName(String recvBankBranchName) {
		this.recvBankBranchName = recvBankBranchName;
	}
	public String getRecvBankAcctNum() {
		return recvBankAcctNum;
	}
	public void setRecvBankAcctNum(String recvBankAcctNum) {
		this.recvBankAcctNum = recvBankAcctNum;
	}
	public String getTranDateTime() {
		return tranDateTime;
	}
	public void setTranDateTime(String tranDateTime) {
		this.tranDateTime = tranDateTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
