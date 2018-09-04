package com.thirdPayInteface;

import java.math.BigDecimal;
import java.util.List;

import com.thirdPayInteface.Fuiou.FuiouResponse.Detail;


/**
 * 所有第三方通用回调通知实体对象
 * @author Administrator
 *
 */
public class CommonResponse {
	/**
	 * 回调通知响应码类型
	 */
	public static final String RESPONSECODE_APPLAY="responsecode_apply";//交易申请成功
	public static final String RESPONSECODE_SUCCESS="responsecode_success";//交易成功
	public static final String RESPONSECODE_FAILD="responsecode_faild";//交易失败
	public static final String RESPONSECODE_ISNOTPASSSIGN="responsecode_isNotPassSign";//签名验证失败
	public static final String RESPONSECODE_NOTRECIVEPARAMETER="responsecode_notReciveParmeter";//没有接收到回调参数
	public static final String RESPONSECODE_SYSTEMERROR="responsecode_systemError";//系统报错
	/**
	 * 返回第三方数据类型
	 */
	public static final String RETURNTYPE_JOSN="returntype_josn";//直连接口,返回josn
	public static final String RETURNTYPE_HTML="returntype_html";//网关接口,返回html
	
	
	/**
	 * 调用传入的参数
	 */
	public CommonRequst CommonRequst;
	
	/**
	 * 回调通知响应编码（第三方通知的响应码解析）
	 */
	private  String responsecode;
	/**
	 * 回调通知响应编码描述（第三方通知的响应码解析描述）
	 */
	private String responseMsg;
	/**
	 * 返回给第三方支付服务器数据类型（json串还是直接页面输出）
	 */
	private String returnType;
	/**
	 * 返回给第三方支付的响应参数
	 */
	private String returnMsg;
	/**
	 * 返回系统中每次请求的流水号
	 */
	private String  requestNo;
	/**
	 * 业务类型（指调用接口的具体业务类型）  来自第三方支付常量池
	 */
	private String bussinessType;
	
	private String custMemberId;//客户标Id
	private String custMemberType;//客户类型
	private String thirdPayConfig;//第三方支付类型
	private String thirdPayConfigId;//第三方支付账号
	private String thirdPayConfigId0;//第三方支付别名（或者默认第三方账号）
	private String custmemberStatus;//第三方用户账户状态
	
	private BigDecimal balance;//第三方账户金额
	private BigDecimal availableAmount;//可用金额  
	private BigDecimal freezeAmount;//冻结金额
	private BigDecimal unTransfersAmount;//未转结余额
	
	private String  bankName;//绑定银行卡银行名称
	private String  bankCode;//绑定银行卡卡号
	private String  bindBankStatus;//绑定银行卡状态
	
	private List<Detail> details;//资金明细
	
	private String truename;//真实姓名
	private String cardCode;//身份证号
	
	public String getTruename() {
		return truename;
	}
	public void setTruename(String truename) {
		this.truename = truename;
	}
	public String getCardCode() {
		return cardCode;
	}
	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	public String getReturnType() {
		return returnType;
	}
	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}
	public String getResponseMsg() {
		return responseMsg;
	}
	public void setResponsecode(String responsecode) {
		this.responsecode = responsecode;
	}
	public String getResponsecode() {
		return responsecode;
	}
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	public String getRequestNo() {
		return requestNo;
	}
	public void setBussinessType(String bussinessType) {
		this.bussinessType = bussinessType;
	}
	public String getBussinessType() {
		return bussinessType;
	}
	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}
	public String getReturnMsg() {
		return returnMsg;
	}
	public void setCustMemberId(String custMemberId) {
		this.custMemberId = custMemberId;
	}
	public String getCustMemberId() {
		return custMemberId;
	}
	public void setCustMemberType(String custMemberType) {
		this.custMemberType = custMemberType;
	}
	public String getCustMemberType() {
		return custMemberType;
	}
	public void setThirdPayConfigId(String thirdPayConfigId) {
		this.thirdPayConfigId = thirdPayConfigId;
	}
	public String getThirdPayConfigId() {
		return thirdPayConfigId;
	}
	public void setThirdPayConfig(String thirdPayConfig) {
		this.thirdPayConfig = thirdPayConfig;
	}
	public String getThirdPayConfig() {
		return thirdPayConfig;
	}
	public void setThirdPayConfigId0(String thirdPayConfigId0) {
		this.thirdPayConfigId0 = thirdPayConfigId0;
	}
	public String getThirdPayConfigId0() {
		return thirdPayConfigId0;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBindBankStatus(String bindBankStatus) {
		this.bindBankStatus = bindBankStatus;
	}
	public String getBindBankStatus() {
		return bindBankStatus;
	}
	public void setCustmemberStatus(String custmemberStatus) {
		this.custmemberStatus = custmemberStatus;
	}
	public String getCustmemberStatus() {
		return custmemberStatus;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setAvailableAmount(BigDecimal availableAmount) {
		this.availableAmount = availableAmount;
	}
	public BigDecimal getAvailableAmount() {
		return availableAmount;
	}
	public void setFreezeAmount(BigDecimal freezeAmount) {
		this.freezeAmount = freezeAmount;
	}
	public BigDecimal getFreezeAmount() {
		return freezeAmount;
	}
	public List<Detail> getDetails() {
		return details;
	}
	public void setDetails(List<Detail> details) {
		this.details = details;
	}
	public BigDecimal getUnTransfersAmount() {
		return unTransfersAmount;
	}
	public void setUnTransfersAmount(BigDecimal unTransfersAmount) {
		this.unTransfersAmount = unTransfersAmount;
	}
	public void setCommonRequst(CommonRequst commonRequst) {
		CommonRequst = commonRequst;
	}
	public CommonRequst getCommonRequst() {
		return CommonRequst;
	}
	

}
