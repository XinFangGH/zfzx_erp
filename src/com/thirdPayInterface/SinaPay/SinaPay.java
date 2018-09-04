package com.thirdPayInterface.SinaPay;

public class SinaPay {
	
	
	//================================新浪支付业务编码开始=============================
	public static final String DEMO_REGISTER="register";//注册
	public static final String DEMO_CERTIFY="certifyMember";//实名认证
	public static final String DEMO_REGARGE="recharge";//账户充值
	
	public static final String DEMO_BID="bid";//散标投资
	public static final String DEMO_MONEYPLANBID="moneyPlanBid";//理财计划投资
	public static final String DEMO_NOMALREPAY="normalRepay";//借款人还散标
	public static final String DEMO_NOMALREPAY_MONEYPLAN="normalRepay_moneyPlan";//借款人还理财计划
	
	public static final String DEMO_BIDFAILD="bidFaild";//散标流标
	public static final String DEMO_MONEYPLANBIDFAILD="moneyPlanBidFaild";//理财计划流标
	
	
	public static final String URL_MEMBER="/mgs/gateway.do";//会员类网关地址
	public static final String URL_ORDER="/mas/gateway.do";//会员类网关地址
	
	public static final String ISCERTIFY="Y";//第三方完成实名认证
	public static final String NOTCERTIFY="N";//第三方不进行实名认证
	
	public static final String CART_TYPE1="IC";//证件类型：身份证
	public static final String CART_TYPE2="PP";//证件类型：护照
	public static final String CART_TYPE3="HMP";//证件类型：港澳通行证
	
	public static final String VERSION="1.0";//接口版本
	public static final String CHARTSET="UTF-8";//编码格式
	public static final String SIGNTYPE="MD5";//签名类型
	public static final String SIGNVERSION="1.0";//签名版本号
	public static final String ENCRYPTVERSION="1.0";//加密版本号
	
	public static final String MEMBER_TYPE1="1";//个人会员
	public static final String MEMBER_TYPE2="2";//企业会员
	
	/**
	 * 用户标识类型  （用户标识类型）
	 */
	public static final String IDENTITY_TYPE1="UID";//商户用户ID
	public static final String IDENTITY_TYPE2="MOBILE";//钱包绑定手机号
	public static final String IDENTITY_TYPE3="EMAIL";//钱包绑定邮箱
	/**
	 * 外部交易编码（用来投标，放款，还款，分润）
	 */
	public static final String RECIVE_BID="1001";//代收投资金
	public static final String RECIVE_REPAYMENT="1002";//代收还款金
	public static final String RELEASE_LOAN="2001";//代付还款金
	public static final String RELEASE_REPAYMENT="2002";//代付本金和收益
	
	
	public static final String SERVICE_CREATEMEMBER="create_activate_member";//创建并激活会员接口
	public static final String SERVICE_CERTIFYMEMBER="set_real_name";//实名认证会员接口
	public static final String SERVICE_RECHARGE="create_hosting_deposit";//充值接口（托管充值接口）
	public static final String SERVICE_RECIVE="create_hosting_collect_trade";//创建托管代收接口，收款类交易接口，如用户投资、还款。支持多种支付方式
	public static final String SERVICE_FAILBID="create_hosting_refund";//托管返款接口（一般用作流标或者关闭）
	public static final String SERVICE_RELEASELOAN="pay_hosting_trade";//创建托管代付  （一般用作放款）
	public static final String SERVICE_RETURNINVEST="create_batch_hosting_pay_trade";//创建批量托管代付
	
	
	/**
	 * 服务器端回调通知类型
	 */
	public static final String NOTIFY_TRADE="trade_status_sync";//交易结果通知
	public static final String NOTIFY_REFUND="refund_status_sync";//交易退款通知
	public static final String NOTIFY_RECHAGE="deposit_status_sync";//充值结果通知
	public static final String NOTIFY_WITHDRAW="withdraw_status_sync";//取现结果通知
	public static final String NOTIFY_BATCHTRADE="batch_trade_status_sync";//批量交易结果通知
	public static final String NOTIFY_CHECK="audit_status_sync";//审核结果通知
	
	
	public static final String  RECHAGE_SUCCESS="SUCCESS";//充值成功状态
	public static final String  RECHAGE_FAILD="FAILED";//充值失败状态
	
//================================新浪支付业务编码结束=============================
	
//================================基础参数开始=====================================
	/**
	 * 接口名称
	 * 非空 String(64)
	 */
	private String service;
	/**
	 * 接口版本   非空   默认1.0
	 */
	private String version;
	/**
	 * 请求时间   非空   发起请求时间，格式yyyyMMddhhmmss
	 */
	private String request_time;
	/**
	 * 合作者身份ID   非空   即平台商户号
	 */
	private String partner_id;
	
	/**
	 * 参数编码字符集   非空   即商户网站使用的编码格式，网站默认使用utf-8
	 */
	private String _input_charset;
	/**
	 * 签名   非空   参见“签名机制”。
	 */
	private String sign;

	/**
	 * 签名方式   非空    签名方式支持RSA、MD5。建议使用MD5
	 */
	private String sign_type;
	/**
	 * 签名版本号   可空    签名密钥版本，默认1.0
	 */
	private String sign_version;
	/**
	 * 加密版本号   可空    加密密钥版本，默认1.0
	 */
	private String encrypt_version;
	/**
	 * 系统异步回调通知地址  可空    String
	 * 钱包处理发生状态变迁后异步通知结果，响应结果为“success”，全部小写
	 */
	private String notify_url;
	/**
	 * 页面跳转同步返回页面路径   可空     String
	 * 钱包处理完请求后，当前页面自动跳转到商户网站里指定页面的http路径。
	 */
	private String return_url;
	/**
	 * 备注   可空     说明信息，原文返回。客户可根据需要存放需要在响应时带回的信息。
	 */
	private String memo;
	
//================================基础参数结束======================================

//================================业务参数开始======================================
	/**
	 * 用户标识信息 (非空)  商户系统用户ID(字母或数字)
	 */
	private String identity_id;
	/**
	 * 用户标识类型 (非空) ID的类型，目前只包括UID
	 */
	private String identity_type;
	/**
	 * 会员类型 (可空) 会员类型，详见附录，默认个人
	 */
	private String member_type;
	/**
	 * 扩展信息(可空) 
	 * 业务扩展信息，
	 * 参数格式：参数名1^参数值1|参数名2^参数值2|……
	 */
	private String extend_param;
	/** 
	 * 真实姓名 (非空) 密文，使用新浪支付RSA公钥加密。明文长度：50
	 */
	private String real_name;
	/**
	 * 证件类型 (非空) 见附录，目前只支持身份证
	 */
	private String cert_type;
	/**
	 *证件号码  (非空)
	 * 密文，使用新浪支付RSA公钥加密。明文长度：30
	 */
	private String cert_no;
	/**
	 * 是否认证(可空)
	 * 是否需要钱包做实名认证，值为Y/N，默认Y。暂不开放外部自助实名认证。
	 */
	private String need_confirm;
	/**
	 * 交易订单号   商户网站交易订单号，商户内部保证唯一 (非空) 
	 */
	private String out_trade_no;
	/**
	 * 摘要     可空   充值内容摘要 String(64)  
	 * 流标返款时非空字段
	 */
	private String summary;
	/**
	 * 账户类型   可空  账户类型（基本户、保证金户,存钱罐）。默认基本户BASIC  String(64)
	 */
	private String account_type;
	/**
	 * 金额    (非空)单位为：RMB Yuan。精确到小数点后两位。
	 */
	private String amount;
	/**
	 * 用户承担的手续费金额   可空     单位为： RMB Yuan。精确到小数点后两位。
	 */
	private String user_fee;
	/**
	 * 付款用户IP地址     可空
	 * 用户在商户平台发起支付时候的IP地址，公网IP，不是内网IP用于风控校验
	 */
	private String payer_ip;
	/**
	 * 支付方式(非空)
	 * 取值范围：
	 * online_bank (网银支付)
	 * quick_pay（快捷）
	 * binding_pay（绑定支付）
	 * 格式：支付方式^金额^扩展|支付方式^金额^扩展。扩展信息内容以“，”分隔，针对不同支付方式的扩展定义见附录
	 */
	private String pay_method;
	/**
	 * 交易码  (非空) String(16)
	 * 商户网站代收交易业务码，见附录
	 */
	private String out_trade_code;
	/**
	 * 交易关闭时间（可空） 
	 * 设置未付款交易的超时时间，一旦超时，该笔交易就会自动被关闭。
	 * 取值范围：1m～15d。
	 * m-分钟，h-小时，d-天
	 * 不接受小数点，如1.5d，可转换为36h。
	 */
	private String trade_close_time;
	/**
	 * 支付失败后是否可以再次支付(可空)
	 * 支付失败后，是否可以重复发起支付
	 * 取值范围：Y、N(忽略大小写)
	 * Y：可以再次支付
	 * N：不能再次支付
	 * 默认值为Y
	 */
	private String can_repay_on_failed;
	/**
	 * 付款用户ID（非空） String(32)
	 * 商户系统用户ID(字母或数字)
	 */
	private String payer_id;
	/**
	 * 付款用户标识类型（非空）
	 * ID的类型，参考“标志类型”
	 */
	private String payer_identity_type;
	/**
	 * 需要退款的商户订单号（非空） String
	 * 需要退款的商户订单号（确保在合作伙伴系统中唯一）。同交易中的一致。
	 */
	private String orig_outer_trade_no ;
	/**
	 * 退款金额 （非空）  单位为：RMB Yuan，精确到小数点后两位。
	 * 支持部分退款，退款金额不能大于交易金额。
	 */
	private String refund_amount;
	/**
	 * 分账信息列表（目前代付不支持退款，因此退款时分账列表都为空）（可空）
	 * 收款信息列表，参见收款信息，参数间用“^”分隔，各条目之间用“|”分隔，备注信息不要包含特殊分隔符
	 */
	private String split_list;
	/**
	 * 收款人标识（非空）
	 * 商户系统用户ID、钱包系统会员ID
	 */
	private String payee_identity_id;
	/**
	 * 收款人标识类型
	 */
	private String payee_identity_type;

	
//================================业务参数结束===================================
	
//================================回调响应参数开始===================================
	/**
	 * 响应请求时间(非空)
	 */
	private String response_time;
	/**
	 * 响应码（非空）
	 */
	private String response_code;
	/**
	 * 响应信息(可空)
	 */
	private String response_message;
	/**
	 * 交易状态(非空) 部分业务接口返回
	 * 交易状态，详见附录“交易状态”
	 */
	private String trade_status;
	/**
	 * 支付状态(非空) 部分业务接口返回
	 */
	private String pay_status;
	
	/**
	 * 后续推进需要的参数（可空）
	 * 如果支付需要推进则会返回此参数，支付推进时需要带上此参数，ticket有效期为15分钟，只能使用一次
	 */
	private String ticket;
	public String getTrade_status() {
		return trade_status;
	}
	public void setTrade_status(String tradeStatus) {
		trade_status = tradeStatus;
	}
	public String getPay_status() {
		return pay_status;
	}
	public void setPay_status(String payStatus) {
		pay_status = payStatus;
	}
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public String getRefund_status() {
		return refund_status;
	}
	public void setRefund_status(String refundStatus) {
		refund_status = refundStatus;
	}
	/**
	 * 退款状态(可空空)   
	 * 托管退款参数  退款状态，详见附录“退款状态”
	 */
	private String refund_status;
	
//================================回调响应参数结束===================================
	
	public String getOut_trade_code() {
		return out_trade_code;
	}
	public void setOut_trade_code(String outTradeCode) {
		out_trade_code = outTradeCode;
	}
	public String getTrade_close_time() {
		return trade_close_time;
	}
	public void setTrade_close_time(String tradeCloseTime) {
		trade_close_time = tradeCloseTime;
	}
	public String getCan_repay_on_failed() {
		return can_repay_on_failed;
	}
	public void setCan_repay_on_failed(String canRepayOnFailed) {
		can_repay_on_failed = canRepayOnFailed;
	}
	public String getPayer_id() {
		return payer_id;
	}
	public void setPayer_id(String payerId) {
		payer_id = payerId;
	}
	public String getPayer_identity_type() {
		return payer_identity_type;
	}
	public void setPayer_identity_type(String payerIdentityType) {
		payer_identity_type = payerIdentityType;
	}
	public String getOrig_outer_trade_no() {
		return orig_outer_trade_no;
	}
	public void setOrig_outer_trade_no(String origOuterTradeNo) {
		orig_outer_trade_no = origOuterTradeNo;
	}
	public String getRefund_amount() {
		return refund_amount;
	}
	public void setRefund_amount(String refundAmount) {
		refund_amount = refundAmount;
	}
	public String getSplit_list() {
		return split_list;
	}
	public void setSplit_list(String splitList) {
		split_list = splitList;
	}
	public String getPayee_identity_id() {
		return payee_identity_id;
	}
	public void setPayee_identity_id(String payeeIdentityId) {
		payee_identity_id = payeeIdentityId;
	}
	public String getPayee_identity_type() {
		return payee_identity_type;
	}
	public void setPayee_identity_type(String payeeIdentityType) {
		payee_identity_type = payeeIdentityType;
	}
	public String getIdentity_id() {
		return identity_id;
	}
	public void setIdentity_id(String identityId) {
		identity_id = identityId;
	}
	public String getIdentity_type() {
		return identity_type;
	}
	public void setIdentity_type(String identityType) {
		identity_type = identityType;
	}
	public String getMember_type() {
		return member_type;
	}
	public void setMember_type(String memberType) {
		member_type = memberType;
	}
	public String getExtend_param() {
		return extend_param;
	}
	public void setExtend_param(String extendParam) {
		extend_param = extendParam;
	}
	public String getReal_name() {
		return real_name;
	}
	public void setReal_name(String realName) {
		real_name = realName;
	}
	public String getCert_type() {
		return cert_type;
	}
	public void setCert_type(String certType) {
		cert_type = certType;
	}
	public String getCert_no() {
		return cert_no;
	}
	public void setCert_no(String certNo) {
		cert_no = certNo;
	}
	public String getNeed_confirm() {
		return need_confirm;
	}
	public void setNeed_confirm(String needConfirm) {
		need_confirm = needConfirm;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String outTradeNo) {
		out_trade_no = outTradeNo;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getAccount_type() {
		return account_type;
	}
	public void setAccount_type(String accountType) {
		account_type = accountType;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getUser_fee() {
		return user_fee;
	}
	public void setUser_fee(String userFee) {
		user_fee = userFee;
	}
	public String getPayer_ip() {
		return payer_ip;
	}
	public void setPayer_ip(String payerIp) {
		payer_ip = payerIp;
	}
	public String getPay_method() {
		return pay_method;
	}
	public void setPay_method(String payMethod) {
		pay_method = payMethod;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getRequest_time() {
		return request_time;
	}
	public void setRequest_time(String requestTime) {
		request_time = requestTime;
	}
	public String getPartner_id() {
		return partner_id;
	}
	public void setPartner_id(String partnerId) {
		partner_id = partnerId;
	}
	public String get_input_charset() {
		return _input_charset;
	}
	public void set_input_charset(String inputCharset) {
		_input_charset = inputCharset;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String signType) {
		sign_type = signType;
	}
	public String getSign_version() {
		return sign_version;
	}
	public void setSign_version(String signVersion) {
		sign_version = signVersion;
	}
	public String getEncrypt_version() {
		return encrypt_version;
	}
	public void setEncrypt_version(String encryptVersion) {
		encrypt_version = encryptVersion;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notifyUrl) {
		notify_url = notifyUrl;
	}
	public String getReturn_url() {
		return return_url;
	}
	public void setReturn_url(String returnUrl) {
		return_url = returnUrl;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public String getResponse_time() {
		return response_time;
	}
	public void setResponse_time(String responseTime) {
		response_time = responseTime;
	}
	public String getResponse_code() {
		return response_code;
	}
	public void setResponse_code(String responseCode) {
		response_code = responseCode;
	}
	public String getResponse_message() {
		return response_message;
	}
	public void setResponse_message(String responseMessage) {
		response_message = responseMessage;
	}
}
