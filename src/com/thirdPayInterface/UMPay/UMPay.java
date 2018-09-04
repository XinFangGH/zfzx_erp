package com.thirdPayInterface.UMPay;

import java.util.Date;

public class UMPay {
	/**
	 * 字符编码类型
	 */
	public static final String UTF8="UTF-8";
	public static final String GBK="GBK";
	public static final String GB2312="GB2312";
	public static final String GB18030="GB18030";
	/**
	 * 证件类型
	 */
	public static final String IDENTITY_CARD="IDENTITY_CARD";
	/**
	 * 银行卡支付方式
	 * B2CDEBITBANK：个人网银
	 */
	public static final String BANK_PERSON="B2CDEBITBANK";//个人网银
	public static final String BANK_ENTERPRISE="B2BBANK";//企业网银
	public static final String BANK_DEBIT="DEBITCARD";//个人银行卡快捷
	
	/**
	 * 是否开启快捷支付操作属性
	 */
	public static final String FASTPAYMENT_NO="0";//默认不开启
	public static final String FASTPAYMENT_YES="1";//强制开启
	/**
	 * 开启无密交易协议号
	 * 均指平台帮助用户进行充值，还款，投标
	 */
	public static final String NO_PASSWORD_FASTPAYMENT="ZCZP0800";//无密充值快捷协议和开通快捷支付银行卡不同
	public static final String NO_PASSWORD_INVST="ZTBB0G00";//无密投资协议
	public static final String NO_PASSWORD_REPAYMENT="ZHKB0H01";//无密还款协议
	/**
	 * 转账类型
	 * 普通类转账（个人对平台转账验密模式） 01对私（个人用户） 02对公（企业用户）（暂不支持）
	 * 投标类转账类型  01 个人（放款时只能是个人） 02 商户（平台）
	 */
	public static final String PARTIPAY_PERSON="01" ;//01对私（个人用户）                                 01 个人（放款时只能是个人）
	public static final String PARTIPAY_PUBLIC="02" ;//02对公（企业用户）（暂不支持）   02 商户（平台）
	
	/**
	 * 用户查询是否查询余额标识
	 */
	public static final String IS_HAVESELECT="01";//01：查询
	public static final String IS_NOHAVESELECT="02";//02：不查（默认）
	
	/**
	 * 用户查询是否查询授权情况标识
	 */
	public static final String IS_HAVEAUTHORIZATION="1";//1：查询
	public static final String IS_NOHAVEAUTHORIZATION="0";//0：不查（默认）
	
	/**
	 * 转账方向
	 * 普通非标转账取值范围：01给P2P平台转账    02 P2P平台向用户转账
	 * 标类的转账取值范围：01标的转入（对应serv_type标的转入类型）  02标的转出（对应serv_type标的转出类型）
	 */
	public static final String DIRECTION_CUSTOMER="01";//01：针对用户（个人用户，企业用户） 01标的转入（对应serv_type标的转入类型）
	public static final String DIRECTION_PLATFORM="02";//02：针对平台                                                        02标的转出（对应serv_type标的转出类型）
	/**
	 * 更新标的类型
	 */
	public static final String UPDATE_TYPE1="01";//01:更新标
	public static final String UPDATE_TYPE2="02";//02:标的融资人 即为借款人，借款人不一定是资金使用方（注意：仅限建标后，开标前可以修改。）
	public static final String UPDATE_TYPE3="03";//03：标的代偿方 
	public static final String UPDATE_TYPE4="04";//04：标的资金使用方 即为标的资金使用方，目前仅支持个人
	
	/**
	 * 建标成功后标的状态
	 */
	public static final String BID_STATUS0="0";//开标
	public static final String BID_STATUS1="1";//投标
	public static final String BID_STATUS2="2";//还款中
	public static final String BID_STATUS3="3";//已还款
	public static final String BID_STATUS4="4";//结束
	
	/**
	 * 转账方类型(定长2)
	 * 取值范围：
	 * 01投资者
	 * 02融资人
	 * 03 P2P平台
	 * 04担保方
	 * 05资金使用方
	 */
	public static final String TRABSFER_INVEST="01";//01投资者
	public static final String TRABSFER_LOANER="02";//02融资人
	public static final String TRABSFER_PLATFORM="03";//03 P2P平台
	public static final String TRABSFER_GUANTEEN="04";//04担保方
	public static final String TRABSFER_MONEYUSED="05";//05资金使用方
	
	/**
	 * 业务类型(定长2)
	 * 取值范围：
	 * 转入：
     * 01投标（投标中）、02债权购买（还款中）、03还款（还款中）、04偿付（还款中）、05贴现（投标中、还款中）
     * 转出：
     * 51流标后返款（投标中、还款中、已还款）、52平台收费（投标中、还款中）、53放款（投标中、还款中）、54还款后返款（还款中、已还款）、55偿付后返款（投标中、还款中、已还款）、56债权转让的返款（投标中、还款中、已还款）、57撤资后的返款（投标中、还款中、已还款）
	 */
	public static final String BUSSINESS_INVEST="01";//01投标
	public static final String BUSSINESS_DEALINVEST="02";//02债权购买（还款中）
	public static final String BUSSINESS_REPAYMENT="03";//03还款（还款中）
	public static final String BUSSINESS_REIMABURSEMENT="04";//04偿付（还款中）
	public static final String BUSSINESS_DISCOUNT="05";//05贴现（投标中、还款中）
	public static final String BUSSINESS_BIDFAILE="51";//51流标后返款（投标中、还款中、已还款）
	public static final String BUSSINESS_PLATFORMFEE="52";//52平台收费（投标中、还款中）
	public static final String BUSSINESS_LOAN="53";//53放款（投标中、还款中）
	public static final String BUSSINESS_REPAYMENTUSER="54";//54还款后返款（还款中、已还款）
	public static final String BUSSINESS_REIMABURSEMENTUSER="55";//55偿付后返款（投标中、还款中、已还款）
	public static final String BUSSINESS_DEALINVESTUSER="56";//56债权转让的返款（投标中、还款中、已还款）
	public static final String BUSSINESS_PLANCANSEL="57";//57撤资后的返款（投标中、还款中、已还款）

	/**
	 * 对接接口名称
	 */
	public static final String SER_REGIST="mer_register_person";//注册接口
	public static final String SER_RECHARGE="mer_recharge_person";//个人有密码充值接口
	public static final String SER_WITHDRAWRECHARGE="cust_withdrawals";//个人有密码取现接口
	public static final String SER_BINDCARD="mer_bind_card";//绑卡接口
	public static final String SER_NOPASSWORD="ptp_mer_bind_agreement";//无密签约接口
	public static final String SER_CANCELNOPASSWORD="mer_unbind_agreement";//取消无密签约接口
	public static final String SER_NOMALTRANSFER="transfer_asyn";//非投标类you密码转账接口（个人转平台）
	public static final String SER_TRANSFER="project_transfer";//投标类转账接口（涉及投标，还款，放款，各种转账操作）
	public static final String SER_NOPASSWORDBID = "project_transfer_nopwd";//投标类无密转账
	public static final String SER_QUERYCUSTOMER="user_search";//用户查询
	public static final String SER_PLATFORMTRANSFER="transfer";//非投标类转账（平台针对用户转账）
	public static final String SER_CREATBIDACCOUNT="mer_bind_project";//发标接口
	public static final String SER_UPDATEBIDACCOUNT="mer_update_project";//更新标的状态
	public static final String SER_SENDCODE="mer_send_sms_verification";//下发手机验证码接口
	public static final String SER_CHECKCODEREGISTER="mer_register_person_veri";//根据收机验证码来完成注册接口
	
	
	public static final String NOTIFY_RECHARGE="recharge_notify";//充值回调通知service标识
	public static final String NOTIFY_WITHDRAW="withdraw_notify";//取现回调通知service标识
	public static final String NOTIFY_BINDCARD="mer_bind_card_notify";//绑银行卡回调通知service标识
	public static final String NOTIFY_APPLY_BINDCARD="mer_bind_card_apply_notify";//绑银行卡回调通知申请成功
												
	public static final String NOTIFY_BINDAGERRMENT="mer_bind_agreement_notify";//授权无密交易回调通知service标识
	public static final String NOTIFY_CANCELAGERRMENT="mer_unbind_agreement_notify";//取消授权无密交易回调通知service标识
	public static final String NOTIFY_NOMALTRANSFER="transfer_notify";//普通转账回调通知service标识
	public static final String NOTIFY_BIDTRANSFER="project_tranfer_notify";//标类的转账回调通知service标识
	
	public static final String DOWNLOAD_SETTLE_FILE_P="download_settle_file_p";//对账类service标识
	/**
	 * 对账类类型
	 */
	public static final String DOWN_FILETYPE1="01";//充值对账文件
	public static final String DOWN_FILETYPE2="02";//提现对账文件
	public static final String DOWN_FILETYPE3="03";//标的对账文件（全量）
	public static final String DOWN_FILETYPE4="04";//标的转账对账文件
	public static final String DOWN_FILETYPE5="05";//转账对账文件
	public static final String DOWN_FILETYPE6="06";//托管用户开户对账文件（增量）
	
	public static final String  NOTIFY_BINDCHAGECARD="";
	public static final String SER_ENPRISE_RECHARGE="mer_recharge";//调用充值service标识
	public static final String SER_PTP_MER_QUERY="ptp_mer_query";
	public static final String SER_TRANSEQ_SEARCH="transeq_search";//账户流水查询(商户平台)
	//联动优势接口回调成功标识
	public static final String CODE_SUCESS="0000";
	
	//接口名称(每个接口都不同)
	private String service;
	//签名方式(暂只支持RSA必须大写)
	private String sign_type;
	//签名
	private String sign;
	// 商户网站使用的编码格式，支持UTF-8、GBK、GB2312、GB18030
	private String charset;
	//响应数据格式(暂支持HTML)
	private String res_format;
	//商户编号(由平台统一分配合作商户唯一标识)
	private String mer_id;
	//版本号(定值 1.0)
	private String version;
	//商户用户标识(用户在商户端的唯一标识。)
	private String mer_cust_id;
	/**
	 * 用户姓名
	 * 使用联动公钥进行RSA加密后，BASE64(GBK编码)加密该字段
	 */
	private String mer_cust_name;
	/**
	 * 默认只用身份证
	 */
	private String identity_type;
	//证件号
	private String  identity_code;
	//手机号
	private String mobile_id;
	//邮箱
	private String email;
	//开户日期
	private String reg_date;
	//资金账户托管平台用户号
	private String user_id;
	//资金账户托管平台账户号
	private String  account_id;
	//返回码
	private String ret_code;
	//返回信息
	private String ret_msg;
	//页面端回调返回的url
	private String ret_url;
	//服务器端回调通知url
	private String notify_url;
	//传入HTML5可访问手机页面，web不需要传入(手机端可以使用)
	private String sourceV;
	//商户订单号支持数字、英文字母，其他字符不建议使用     长度4至32位
	private String order_id;
	//商户生成订单的日期，格式YYYYMMDD
	private String mer_date;
	//支付方式   变长16  取值范围：B2CDEBITBANK（借记卡网银）B2BBANK（企业网银）	DEBITCARD（借记卡快捷）
	private String pay_type;
	//交易金额（以分为单位）
	private String amount;
	//发卡银行编号     (变长8) 详见发卡银行编码个人网银，企业网银充值时必传借记卡快捷时无需上送
	private String gate_id;
	//用户IP地址 (变长 16) 用户在创建交易时，该用户当前所使用机器的 IP。 用作防钓鱼校验为了提高安全性，如果没有技术困难，建议提供
	private String user_ip;
	//卡号(变长256) 使用联动公钥进行RSA加密后，BASE64(GBK编码)加密该字段
	private String card_id;
	//开卡户名(变长256)使用联动公钥进行RSA加密后，BASE64(GBK编码)加密该字段
	private String account_name;
	private String loan_acc_type;//借款人类型  针对联动优势发企业标必填
	/**
	 * 快捷协议标志  ()
	 * 商户制定其用户绑卡时是否开通快捷协议（注意：用户只有在绑卡页面可以签约借记卡快捷，同时此签约目前无法解约）
     * 0：不开通，绑卡输交易密码页面不显示开通快捷支付协议文字及复选框。
     * 1：商户强制要求绑卡时开通快捷支付，如果发卡行不支持开通快捷支付，抛异常，发卡行支持开通快捷支付，绑卡输交易密码页面显示开通快捷支付协议文字及复选框，复选框为选中状态，置灰，用户不可操作。（支持开通快捷支付银行请参考文档）。
     * 该字段值为空：商户未传或字段为空时，表示商户不控制是否开通快捷支付，使用默认页面，用户可勾选是否开通快捷支付复选框。
     * 字段值只可输入0、1或为空，其他值校验不通过
	 */
	private String is_open_fastPayment;
	private String warranty_user_id;//标的代偿方平台用户号
	/**
	 * 充值的企业账户号
	 */
	private String recharge_mer_id;
	private String busi_type;
	public String getBusi_type() {
		return busi_type;
	}
	public void setBusi_type(String busiType) {
		busi_type = busiType;
	}
	/**
	 * 用户需签约的协议列表信息(变长16)
	 * 协议与协议之间用竖线“|”分割，每个协议包含的数据有：协议类型（见协议类型列表）。
     * 如：ZCZP0800|ZTBB0G00| ZHKB0H01
	 */
	private String user_bind_agreement_list;
	
	/**
	 * 转账方用户号(变长 32)联动开立的用户号
	 */
	private String partic_user_id;
	
	/**
	 * 转账方类型(定长2)
	 * 取值范围：01对私（个人用户）02对公（企业用户）（暂不支持）
	 */
	private String partic_acc_type;
	
	/**
	 * 是否查询余额(定长2) 01：查询 02：不查（默认）
	 */
	private String is_find_account;
	
	/**
	 * 是否查询授权协议(定长1) 1：查询 0：不查（默认）
	 */
	private String is_select_agreement;
	
	/**
	 * 转账方向（定长2）取值范围：01给P2P平台转账
	 * 02 P2P平台向用户转账
	 */
	private String trans_action;
	/**
	 * 标的号(变长32) 标的号
	 */
	private String project_id;
	/**
	 * 标的名称(变长16) 标的名称，命名规范：中文、数字、英文、下划线
	 */
	private String project_name;
	/**
	 * 标的金额(变长13) 标的金额,以分为单位
	 */
	private String project_amount;
	/**
	 * 标的融资人资金账户托管平台用户号(变长32) 标的融资人资金账户托管平台用户号联动开立的用户号
	 */
	private String loan_user_id;
	
	/**
	 * 标的更新类型(定长2) 
	 * 01：更新标的
     * 02：标的融资人 即为借款人，借款人不一定是资金使用方（注意：仅限建标后，开标前可以修改。）
     * 03：标的代偿方 
     * 04：标的资金使用方 即为标的资金使用方，目前仅支持个人
     * 每次操作只能选择一种更新类型；
	 */
	private String change_type;
	/**
	 * 标的状态(定长1)
	 * 取值范围：
     * 0：开标、
     * 1：投标中、
     * 2：还款中、
     * 3：已还款、
     * 4：结束（前提条件：余额为0）
     * 建标后，标的状态为[建标成功]，由商户设定为开标状态，然后在更新为投标中才可以投资；
     * change_type=01时，才使用
	 */
	private String project_state;
	/**
	 * 业务类型(定长2)
	 * 取值范围：
	 * 转入：
     * 01投标（投标中）、02债权购买（还款中）、03还款（还款中）、04偿付（还款中）、05贴现（投标中、还款中）
     * 转出：
     * 51流标后返款（投标中、还款中、已还款）、52平台收费（投标中、还款中）、53放款（投标中、还款中）、54还款后返款（还款中、已还款）、55偿付后返款（投标中、还款中、已还款）、56债权转让的返款（投标中、还款中、已还款）、57撤资后的返款（投标中、还款中、已还款）
	 */
	private String serv_type;
	/**
	 * 转账方类型(定长2)  取值范围：
     * 01投资者
     * 02融资人
     * 03 P2P平台
     * 04担保方
     * 05资金使用方
	 */
	private String partic_type;
	/**
	 * 账户余额
	 */
	private String balance;
	/**
	 * 账户状态
	 */
	private String account_state;
	/**
	 * 用户要取消签约的协议列表信息(变长16)
	 * 协议与协议之间用竖线“|”分割，每个协议包含的数据有：协议类型（见协议类型列表）。
     * 如：ZCZP0800|ZTBB0G00| ZHKB0H01
	 */
	private String user_unbind_agreement_list;
	/**
	 * 下发验证码后 联动优势返回的流水号
	 */
	private  String sms_trace ;
	/**
	 * 联动优势下发的验证码
	 */
	private String verification_code;
	/**
	 * 查询的平台商户号
	 */
	private String query_mer_id;
	/**
	 * 查询的开始日期
	 */
	private String start_date;
	/**
	 * 查询的结束日期
	 */
	private String end_date;
	/**
	 * 查询的页码
	 */
	private String page_num;
	public String getPage_num() {
		return page_num;
	}
	public void setPage_num(String pageNum) {
		page_num = pageNum;
	}
	public String getQuery_mer_id() {
		return query_mer_id;
	}
	public void setQuery_mer_id(String queryMerId) {
		query_mer_id = queryMerId;
	}
	public String getAccount_type() {
		return account_type;
	}
	public void setAccount_type(String accountType) {
		account_type = accountType;
	}
	public String getRecharge_mer_id() {
		return recharge_mer_id;
	}
	public void setRecharge_mer_id(String rechargeMerId) {
		recharge_mer_id = rechargeMerId;
	}
	
	
	
	/**
	 *开始查询日期 
	 */
	private Date queryStartDate;
	/**
	 *结束查询日期 
	 */
	private Date queryEndDate;
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String endDate) {
		end_date = endDate;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String startDate) {
		start_date = startDate;
	}
	public Date getQueryStartDate() {
		return queryStartDate;
	}
	public void setQueryStartDate(Date queryStartDate) {
		this.queryStartDate = queryStartDate;
	}
	public Date getQueryEndDate() {
		return queryEndDate;
	}
	public void setQueryEndDate(Date queryEndDate) {
		this.queryEndDate = queryEndDate;
	}
	private String account_type;
	/**
	 * 对账日期
	 */
	private String settle_date_p2p;
	/**
	 * 对账类型
	 */
	private String settle_type_p2p;
	
	public String getWarranty_user_id() {
		return warranty_user_id;
	}
	public void setWarranty_user_id(String warrantyUserId) {
		warranty_user_id = warrantyUserId;
	}
	public String getLoan_acc_type() {
		return loan_acc_type;
	}
	public void setLoan_acc_type(String loanAccType) {
		loan_acc_type = loanAccType;
	}
	public String getRet_url() {
		return ret_url;
	}
	public void setRet_url(String retUrl) {
		ret_url = retUrl;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notifyUrl) {
		notify_url = notifyUrl;
	}
	public String getSourceV() {
		return sourceV;
	}
	public void setSourceV(String sourceV) {
		this.sourceV = sourceV;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String orderId) {
		order_id = orderId;
	}
	public String getMer_date() {
		return mer_date;
	}
	public void setMer_date(String merDate) {
		mer_date = merDate;
	}
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String payType) {
		pay_type = payType;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getGate_id() {
		return gate_id;
	}
	public void setGate_id(String gateId) {
		gate_id = gateId;
	}
	public String getUser_ip() {
		return user_ip;
	}
	public void setUser_ip(String userIp) {
		user_ip = userIp;
	}

	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String signType) {
		sign_type = signType;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	public String getRes_format() {
		return res_format;
	}
	public void setRes_format(String resFormat) {
		res_format = resFormat;
	}
	public String getMer_id() {
		return mer_id;
	}
	public void setMer_id(String merId) {
		mer_id = merId;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getMer_cust_id() {
		return mer_cust_id;
	}
	public void setMer_cust_id(String merCustId) {
		mer_cust_id = merCustId;
	}
	public String getMer_cust_name() {
		return mer_cust_name;
	}
	public void setMer_cust_name(String merCustName) {
		mer_cust_name = merCustName;
	}
	public String getIdentity_type() {
		return identity_type;
	}
	public void setIdentity_type(String identityType) {
		identity_type = identityType;
	}
	public String getIdentity_code() {
		return identity_code;
	}
	public void setIdentity_code(String identityCode) {
		identity_code = identityCode;
	}
	public String getMobile_id() {
		return mobile_id;
	}
	public void setMobile_id(String mobileId) {
		mobile_id = mobileId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String regDate) {
		reg_date = regDate;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String userId) {
		user_id = userId;
	}
	public String getAccount_id() {
		return account_id;
	}
	public void setAccount_id(String accountId) {
		account_id = accountId;
	}
	public String getRet_code() {
		return ret_code;
	}
	public void setRet_code(String retCode) {
		ret_code = retCode;
	}
	
	public void setRet_msg(String ret_msg) {
		this.ret_msg = ret_msg;
	}
	public String getRet_msg() {
		return ret_msg;
	}
	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}
	public String getCard_id() {
		return card_id;
	}
	public void setAccount_name(String account_name) {
		this.account_name = account_name;
	}
	public String getAccount_name() {
		return account_name;
	}
	public void setIs_open_fastPayment(String is_open_fastPayment) {
		this.is_open_fastPayment = is_open_fastPayment;
	}
	public String getIs_open_fastPayment() {
		return is_open_fastPayment;
	}
	public void setUser_bind_agreement_list(String user_bind_agreement_list) {
		this.user_bind_agreement_list = user_bind_agreement_list;
	}
	public String getUser_bind_agreement_list() {
		return user_bind_agreement_list;
	}
	public void setPartic_user_id(String partic_user_id) {
		this.partic_user_id = partic_user_id;
	}
	public String getPartic_user_id() {
		return partic_user_id;
	}
	public void setPartic_acc_type(String partic_acc_type) {
		this.partic_acc_type = partic_acc_type;
	}
	public String getPartic_acc_type() {
		return partic_acc_type;
	}
	public void setIs_find_account(String is_find_account) {
		this.is_find_account = is_find_account;
	}
	public String getIs_find_account() {
		return is_find_account;
	}
	public void setIs_select_agreement(String is_select_agreement) {
		this.is_select_agreement = is_select_agreement;
	}
	public String getIs_select_agreement() {
		return is_select_agreement;
	}
	public void setTrans_action(String trans_action) {
		this.trans_action = trans_action;
	}
	public String getTrans_action() {
		return trans_action;
	}
	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}
	public String getProject_id() {
		return project_id;
	}
	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}
	public String getProject_name() {
		return project_name;
	}
	public void setProject_amount(String project_amount) {
		this.project_amount = project_amount;
	}
	public String getProject_amount() {
		return project_amount;
	}
	public void setLoan_user_id(String loan_user_id) {
		this.loan_user_id = loan_user_id;
	}
	public String getLoan_user_id() {
		return loan_user_id;
	}
	public void setChange_type(String change_type) {
		this.change_type = change_type;
	}
	public String getChange_type() {
		return change_type;
	}
	public void setProject_state(String project_state) {
		this.project_state = project_state;
	}
	public String getProject_state() {
		return project_state;
	}
	public void setServ_type(String serv_type) {
		this.serv_type = serv_type;
	}
	public String getServ_type() {
		return serv_type;
	}
	public void setPartic_type(String partic_type) {
		this.partic_type = partic_type;
	}
	public String getPartic_type() {
		return partic_type;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getBalance() {
		return balance;
	}
	public void setAccount_state(String account_state) {
		this.account_state = account_state;
	}
	public String getAccount_state() {
		return account_state;
	}
	public void setUser_unbind_agreement_list(String user_unbind_agreement_list) {
		this.user_unbind_agreement_list = user_unbind_agreement_list;
	}
	public String getUser_unbind_agreement_list() {
		return user_unbind_agreement_list;
	}
	public void setSms_trace(String sms_trace) {
		this.sms_trace = sms_trace;
	}
	public String getSms_trace() {
		return sms_trace;
	}
	public void setVerification_code(String verification_code) {
		this.verification_code = verification_code;
	}
	public String getVerification_code() {
		return verification_code;
	}
	public String getSettle_date_p2p() {
		return settle_date_p2p;
	}
	public void setSettle_date_p2p(String settleDateP2p) {
		settle_date_p2p = settleDateP2p;
	}
	public String getSettle_type_p2p() {
		return settle_type_p2p;
	}
	public void setSettle_type_p2p(String settleTypeP2p) {
		settle_type_p2p = settleTypeP2p;
	}
	

}
