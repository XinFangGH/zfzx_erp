package com.thirdPayInteface.Huifu;

/**
 * 汇付天下
 * @author Administrator
 *
 */
public class Huifu{
	//
	
	
	
	/**
	 * 版本号
	 * 定长 2 位
	 * 目前固定为 10。如版本升级，能向前兼容
	 */
	private String Version="10" ;
	/**
	 * 消息类型
	 * 变长
	 * 每一种消息类型代表一种交易，具体交易类型代码见HHTUTU接口交易类型定义UUTT 
	 */
	private String CmdId;
	/**
	 * 页面返回 url 
	 * 变长 128 位
	 * 交易完成后,商户专属平台系统把交易结果通过页面方式，发送到该地址上。使用时请注意不要包含中文。
	 */
	private String RetUrl;
	/**
	 * 商户后台应答地址
	 * 变长 128 位
	 * 通过后台异步通知，  商户网站都应在应答接收页面输出RECV_ORD_ID  字样的字符串，表明商户已经收到该笔交易结果 。使用时请注意不要包含中文
	 */
	private String BgRetUrl;
	/**
	 * 商户私有域  
	 * 变长 120 位 
	 * 为商户的自定义字段， 该字段在交易完成后由商户专属平台原样返回。 注意： 如果该字段中包含了中文字符请对该字段的数据进行 base64 加密后再使用
	 */
	private String MerPriv;
	/**
	 * 签名  
	 * 定长 256 位
	 * 各接口所列有的请求参数和返回参数如无个别说明， 都需要参与签名， 参与签名的数据体为： 按照每个接口中包含的参数值（不包含参数名）的顺序（按接口表格中
     * 从左到右，从上到下的顺序）进行字符串相加。如果参数为可选项并且为空，则该参数值不参与签名
	 */
	private String ChkValue ;
	/**
	 * 应答返回码
	 * 定长 3 位
	 * 000 代表交易成功 ,其它代表失败
	 */
	private String RespCode;
	/**
	 * 应答描述
	 * 变长 
	 * 如果 RespCode 为失败，则该域为具体的错误信息描述
	 */
	private String RespDesc;
	/**
	 * 商户客户号
	 * 变长 16 位
	 * 商户客户号，由汇付生成，商户的唯一性标识
	 */
	private String  MerCustId;
	/**
	 * 用户客户号
	 * 变长 16 位
	 * 用户客户号，由汇付生成，用户的唯一性标识
	 */
	private String UsrCustId ;
	/**
	 * 用户号
	 * 变长 6-25 位
	 * 商户下的平台用户号， 在每个商户下唯一 （ 必须是 6 6- - 25位的半角字符）
	 */
	private String UsrId;
	/**
	 * 真实名称
	 * 变长 50 位
	 * 用户的真实姓名
	 */
	private String UsrName ;
	/**
	 * 证件类型
	 * 定长 2 位
	 * '00' – 身份证
	 */
	private String IdType;
	/**
	 * 证件号码
	 * 变长 30 位
	 * 用户证件号码
	 */
	private String IdNo ;
	/**
	 * 手机号
	 * 定长 11 位
	 * 商户专属平台系统提供按照手机号查询订单的功能
	 */
	private String UsrMp;
	/**
	 * 用户 Email
	 * 变长 40 位
	 * 操作员的 Email
	 */
	private String UsrEmail;
	/**
	 * 用户登录密码
	 * 定长 32 位
	 * 用户登录密码
	 */
	private String LoginPwd ;
	/**
	 * 用户交易密码
	 * 定长 32 位
	 * 用户交易密码
	 */
	private String TransPwd;
	/**
	 * 开户银行账号
	 * 变长 40 位
	 * 取现银行的账户号（银行卡号）
	 */
	private String OpenAcctId  ;
	/**
	 * 开户银行代号
	 * 变长 8 位
	 * 具体参见客服提供的相关文档
	 */
	private String OpenBankId;
	/**
	 * 开户银行账号
	 * 变长 40 位 
	 * 取现银行的账户号（银行卡号）
	 */
	private String CardId;
	/**
	 * 开户银行代号
	 * 变长 8 位 
	 * 具体参见客服提供的相关文档。
	 */
	private String GateBankId;
	/**
	 * 开户银行省份
	 * 定长 4 位 
	 * 具体参见 HHTUTU 客服提供的相关文档。 UUTT
	 */
	private String OpenProvId ;
	/**
	 * 
	 */
	private String OpenAreaId;
	
}