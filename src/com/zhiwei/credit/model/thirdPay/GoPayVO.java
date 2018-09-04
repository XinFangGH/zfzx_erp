package com.zhiwei.credit.model.thirdPay;

public class GoPayVO {
    /**
     * 1 GBK 2 UTF-8
                    默认为 1
     */
	public static final String charset ="1" ;//编码格式
	/**
	 * 格式：数字
1 中文 2 英文
	 */
	public static final String language ="1";//语言
	/**
	 * 1 MD5 2 SHA
默认为 1
	 */
	public static final String signType ="1";//加密方式
	/**
	 * 本域指明了交易的类型，支付网关接口必须为8888
	 */
	public static final String tranCode = "8888"; //交易代码
	/**
	 * 多币种预留字段，暂只能为156，代表人民币
	 */
	public static final String currencyType = "156";//币种
	
	String version =""; //版本号
	
	String merchantID = "";//商户代码
	String merOrderNum = ""; //订单号
	String tranAmt = "";//交易金额
	String feeAmt = "";//商户提取佣金金额
	
	String frontMerUrl = ""; //商户前台通知地址
	String backgroundMerUrl = "";//商户后台通知地址
	String tranDateTime = "";//交易时间
	String virCardNoIn = "";//国付宝转入账户
	String tranIP = "";//ip
	String isRepeatSubmit = ""; //订单是否允许重复提交
	String goodsName = "";//商品名称
	String goodsDetail =  ""; //商品详情
	String buyerName =  "";//买方姓名
	String buyerContact = "";//买方联系方式
	String merRemark1 = "";//商户备用信息字段
	String merRemark2 = "";//商户备用信息字段
	String bankCode = "";//银行代码 直连银行交易必填
	String userType = "";//用户类型 直连银行交易必填
	String VerficationCode = "";
    String gopayServerTime = "";//服务器时间   开启时间戳防钓鱼机制必填
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getMerchantID() {
		return merchantID;
	}
	public void setMerchantID(String merchantID) {
		this.merchantID = merchantID;
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
	public String getFeeAmt() {
		return feeAmt;
	}
	public void setFeeAmt(String feeAmt) {
		this.feeAmt = feeAmt;
	}
	public String getFrontMerUrl() {
		return frontMerUrl;
	}
	public void setFrontMerUrl(String frontMerUrl) {
		this.frontMerUrl = frontMerUrl;
	}
	public String getBackgroundMerUrl() {
		return backgroundMerUrl;
	}
	public void setBackgroundMerUrl(String backgroundMerUrl) {
		this.backgroundMerUrl = backgroundMerUrl;
	}
	public String getTranDateTime() {
		return tranDateTime;
	}
	public void setTranDateTime(String tranDateTime) {
		this.tranDateTime = tranDateTime;
	}
	public String getVirCardNoIn() {
		return virCardNoIn;
	}
	public void setVirCardNoIn(String virCardNoIn) {
		this.virCardNoIn = virCardNoIn;
	}
	public String getTranIP() {
		return tranIP;
	}
	public void setTranIP(String tranIP) {
		this.tranIP = tranIP;
	}
	public String getIsRepeatSubmit() {
		return isRepeatSubmit;
	}
	public void setIsRepeatSubmit(String isRepeatSubmit) {
		this.isRepeatSubmit = isRepeatSubmit;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getGoodsDetail() {
		return goodsDetail;
	}
	public void setGoodsDetail(String goodsDetail) {
		this.goodsDetail = goodsDetail;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public String getBuyerContact() {
		return buyerContact;
	}
	public void setBuyerContact(String buyerContact) {
		this.buyerContact = buyerContact;
	}
	public String getMerRemark1() {
		return merRemark1;
	}
	public void setMerRemark1(String merRemark1) {
		this.merRemark1 = merRemark1;
	}
	public String getMerRemark2() {
		return merRemark2;
	}
	public void setMerRemark2(String merRemark2) {
		this.merRemark2 = merRemark2;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getVerficationCode() {
		return VerficationCode;
	}
	public void setVerficationCode(String verficationCode) {
		VerficationCode = verficationCode;
	}
	public String getGopayServerTime() {
		return gopayServerTime;
	}
	public void setGopayServerTime(String gopayServerTime) {
		this.gopayServerTime = gopayServerTime;
	}
	
}
