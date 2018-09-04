package com.zhiwei.credit.model.thirdInterface;

public class EasyPay{
	
	/**
	 *  字符编码格式 目前支持 gbk 或 utf-8
	 */
	public static final String CHARSETGBK = "GBK";
	public static final String CHARSETUTF8 = "UTF-8";

	/**
	 * 签名方式
	 * 加密方式目前采用MD5   不允许修改
	 */
	public static final String SIGNTYPE= "MD5";
	
	/**
	 * 支付类型:
	 * 目前固定值：1
	 */
	public static final String PAYMENTTYPE="1";
	/**
	 * agentpay
	 * 代付交易编码
	 */
	public static final String AGENCYPAY="agentpay";
	/**
	 * 合作身份者ID，由纯数字组成的字符串
	 * 从数据库表（sysConfig）中读取参数partnerEasyPay
	 */
	private String partner;
	
	/**
	 * 交易安全检验码，由数字和字母组成的32位字符串
	 * 从数据库表（sysConfig）中读取参数easyPayKey
	 */
	private String key;
	/**
	 * 签约易生支付账号或卖家收款易生支付帐户
	 * 从数据库表（sysConfig）中读取参数easyPayNumber
	 */
	private String seller_email;
	/**
	 * 交易过程中服务器通知的页面 要用 http://格式的完整路径，不允许加?id=123这类自定义参数
	 * 后台通知服务器进行数据库数据操作
	 */
	private String notify_url;
	/**
	 * 付完款后跳转的页面 要用 http://格式的完整路径，不允许加?id=123这类自定义参数
	 * 前台页面:
	 */
	private String return_url;
	/**
	 * 收款方名称，如：公司名称、网站名称、收款人姓名等
	 */
	private String mainname;
	/**
	 * 接口服务名称，目前固定值：create_direct_pay_by_user（网上支付）
	 */
	private String service;
	/**
	 * 版本号，固定值：PRECARD_1.0（预付费卡支付）
	 *  从数据库表（sysConfig）中读取参数easyPayVer
	 */
	private String version;
	
	/**
	 * 支付提交地址
	 */
	private String easypay_url;
	/**
	 * 返回验证订单地址
	 */
	private String verify_url;
	/**
	 * 字符编码格式 目前支持 gbk 或 utf-8
	 */
	private String _input_charset ;
	/**
	 * 签名方式 不需修改
	 */
	private String sign_type ;
	/**
	 * 访问模式,根据自己的服务器是否支持ssl访问，若支持请选择https；若不支持请选择http
	 * 从数据库表（sysConfig）中读取参数easyPayTransport
	 */
	private String transport ;
	/**
	 * 接口URL:默认访问地址加上后缀名
	 * 从数据库表（sysConfig）中读取参数easyPayURL
	 */
	private String URL;
	
	/**
	 * 支付类签名生成规则：
	 * 请求参数按照参数名字符升序排列，如果有重复参数名，那么重复的参数再按照参数值的字符升序排列。
	 * 所有参数（除了sign和sign_type）按照上面的排序用&连接起来，格式是：p1=v1&p2=v2。
	 * 将上面参数组成的字符串加上安全校验码组成待签名的数据，
	 * 安全校验码商户可通过登录商户平台系统下载，假设安全校验码为123456789.
	 * 生成的数字签名--注意事项：
	 * 没有值的参数无需传递，也无需包含到待签名数据中。
	 * 签名时将字符转化成字节流时指定的字符集与_input_charset保持一致。
	 * 如果传递了_input_charset参数，这个参数也应该包含在待签名数据中。
	 * 根据HTTP协议要求，传递参数的值中如果存在特殊字符（如：&、@等），
	 * 那么该值需要做URL Encoding，这样请求接收方才能接收到正确的参数值。
	 * 这种情况下，待签名数据应该是原生值而不是encoding之后的值。
	 * 例如：调用某接口需要对请求参数email进行数字签名，
	 * 那么待签名数据应该是：email=test@msn.com，
	 * 而不是email=test%40msn.com。
	 */
	private String  sign;
	/**
	 * 支付方式：如果为银行直连，那么值为bankDirect
	 */
	private String paymethod;
	/**
	 * 网银代码:目前只提供了工商银行的网银直连
	 */
	private String defaultbank;
	/**
	 * 交易标题：
	 */
	private String subject;
	/**
	 * 交易标题描述
	 */
	private String body;
	/**
	 * 交易金额 没有正负，单位元
	 */
	private String total_fee;
	/**
	 * 外部交易号：即系统交易唯一编号
	 */
	private String out_trade_no;
	/**
	 * 默认买家易生支付账号
	 */
	private String buyer_email;
	
	/**
	 * 代付查询类交易编码
	 */
	private String TransCode;
	
	public void setPartner(String partner) {
		this.partner = partner;
	}
	public String getPartner() {
		return partner;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getKey() {
		return key;
	}
	public void setSeller_email(String seller_email) {
		this.seller_email = seller_email;
	}
	public String getSeller_email() {
		return seller_email;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}
	public String getReturn_url() {
		return return_url;
	}
	public void setMainname(String mainname) {
		this.mainname = mainname;
	}
	public String getMainname() {
		return mainname;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getService() {
		return service;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getVersion() {
		return version;
	}
	public void setEasypay_url(String easypay_url) {
		this.easypay_url = easypay_url;
	}
	public String getEasypay_url() {
		return easypay_url;
	}
	public void setVerify_url(String verify_url) {
		this.verify_url = verify_url;
	}
	public String getVerify_url() {
		return verify_url;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setTransport(String transport) {
		this.transport = transport;
	}
	public String getTransport() {
		return transport;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	public String getURL() {
		return URL;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getSign() {
		return sign;
	}
	public void setPaymethod(String paymethod) {
		this.paymethod = paymethod;
	}
	public String getPaymethod() {
		return paymethod;
	}
	public void setDefaultbank(String defaultbank) {
		this.defaultbank = defaultbank;
	}
	public String getDefaultbank() {
		return defaultbank;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getSubject() {
		return subject;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getBody() {
		return body;
	}
	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}
	public String getTotal_fee() {
		return total_fee;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void set_input_charset(String _input_charset) {
		this._input_charset = _input_charset;
	}
	public String get_input_charset() {
		return _input_charset;
	}
	public void setBuyer_email(String buyer_email) {
		this.buyer_email = buyer_email;
	}
	public String getBuyer_email() {
		return buyer_email;
	}
	public void setTransCode(String transCode) {
		TransCode = transCode;
	}
	public String getTransCode() {
		return TransCode;
	}
	
}