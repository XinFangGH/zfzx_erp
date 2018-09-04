package com.thirdPayInteface;

public class ThirdPayConstants {
	/**
	 * 第三方支付类型-资金账户托管模式
	 */
	public static final String THIRDPAYTYPE0="0";
	/**
	 * 第三方支付类型-资金池模式
	 */
	public static final String THIRDPAYTYPE1="1";
	/**
	 * 接口调用状态-成功
	 */
	public static final String RECOD_SUCCESS="SUCCESS";
	/**
	 * 接口调用状态-失败
	 */
	public static final String RECOD_FAILD="faild";
	/**
	 * 接口调用状态-无效调用
	 */
	public static final String RECOD_INVAILID="Invalid";
	
	/**
	 * 第三方支付使用环境-正式环境
	 */
	public static final String THIRDENVIRONMENT0="normalEnvironment";
	/**
	 * 第三方支付使用环境-测试环境
	 */
	public static final String THIRDENVIRONMENT1="testEnvironment";
	/**
	 * 易生第三方支付（资金池第三方）
	 */
	public static final String EASYPAY="easypayConfig";
	/**
	 * 国付宝支付（资金池第三方）
	 */
	public static final String GOPAY="gopayConfig";
	/**
	 * 富有支付（富有金账户，富有资金池）
	 */
	public static final String FUIOU="fuiouConfig";
	/**
	 * 双乾支付 （资金账户托管模式）
	 */
	public static final String MONEYMOREMORE="moneyMoreMoreConfig";
	/**
	 * 汇付天下支付（资金账户托管模式）
	 */
	public static final String HUIFU="huifuConfig";
	/**
	 * 易宝第三方支付（资金账户托管模式）
	 */
	public static final String YEEPAY="yeepayConfig";
	/**
	 * 联动优势第三方支付（资金账户托管模式）
	 */
	public static final String UMPAY="umpayConfig"; 
	/**
	 * 新浪支付第三方支付（资金账户托管模式）
	 */
	public static final String SINAPAY="sinapayConfig"; 
	/**
	 * 客户类型---企业客户
	 */
	public static final String CUSTERTYPE_ENTERPRISE="enterprise";
	/**
	 * 客户类型---个人客户
	 */
	public static final String CUSTERTYPE_PERSON="person";
	/**
	 * 接口业务处理状态返回状态（用来标识是否立即处理业务数据）--立即处理业务数据
	 */
	public static final String RETURNTYPE_DOOPRATE="returnType_doOprate";
	/**
	 * 接口业务处理状态返回状态（用来标识是否立即处理业务数据）--等待服务器端处理业务数据
	 */
	public static final String RETURNTYPE_WAITOPRATE="returnType_waitOprate";
	
	/**
	 * 系统支持调用接口类型-注册接口
	 */
	public static final String BUSSINESSTYPE_REGISTER="bussinessType_register";
	
	/**
	 * 系统支持调用接口类型-实名认证接口（个别第三方会用）
	 */
	public static final String BUSSINESSTYPE_CERTIFY="bussinessType_certify";
	
	
	/**
	 * 系统支持调用接口类型-充值接口
	 */
	public static final String BUSSINESSTYPE_RECHAGE="bussinessType_rechage";
	
	/**
	 * 系统支持调用接口类型-绑定银行卡接口
	 */
	public static final String BUSSINESSTYPE_BINDBANKCAR="bussinessType_bindBankCard";
	
	/**
	 * 系统支持调用接口类型-取消绑定银行卡接口
	 */
	public static final String BUSSINESSTYPE_CANCELBINDBANKCARD="bussinessType_cancelBindBankCard";
	
	/**
	 * 系统支持调用接口类型-取现接口
	 */
	public static final String BUSSINESSTYPE_WITHDRAW="bussinessType_withdraw";
	/**
	 * 系统支持调用接口类型-发标接口
	 */
	public static final String BUSSINESSTYPE_CREATEBID="bussinessType_createBid";
	/**
	 * 系统支持调用接口类型-更新标接口
	 */
	public static final String BUSSINESSTYPE_UPDATEBID="bussinessType_updateBid";
	/**
	 * 系统支持调用接口类型-散标投标接口
	 */
	public static final String BUSSINESSTYPE_BID="bussinessType_bid";
	
	/**
	 * 系统支持调用接口类型-理财计划投标接口
	 */
	public static final String BUSSINESSTYPE_MONEYPLANBID="bussinessType_moneyPlanBid";
	
	/**
	 * 系统支持调用接口类型-散标流标接口
	 */
	public static final String BUSSINESSTYPE_BIDFAILD="bussinessType_bidFaild";
	
	/**
	 * 系统支持调用接口类型-理财计划流标接口
	 */
	public static final String BUSSINESSTYPE_MONEYPLANBIDFAILD="bussinessType_moneyPlanBidFaild";
	
	
	/**
	 * 系统支持调用接口类型-散标放款接口
	 */
	public static final String BUSSINESSTYPE_LOAN="bussinessType_loan";
	
	/**
	 * 系统支持调用接口类型-理财计划放款接口
	 */
	public static final String BUSSINESSTYPE_MONEYPLANLOAN="bussinessType_moneyPlanLoan";
	
	/**
	 * 系统支持调用接口类型-借款人还款接口
	 */
	public static final String BUSSINESSTYPE_LOANERREPAYMENT="bussinessType_loanerRepayment";
	
	
	/**
	 * 系统支持调用接口类型-投资人返款接口（个别第三方接口会调用）
	 */
	public static final String BUSSINESSTYPE_BIDINVESTERRECIVEMONEY="bussinessType_bidInvestReciveMoney";
	
	/**
	 * 系统支持调用接口类型-平台收费返款接口（个别第三方接口会调用）
	 */
	public static final String BUSSINESSTYPE_BIDPLATERECIVEMONEY="bussinessType_bidPlateReciveMoney";
	/**
	 * 系统支持调用接口类型-自动投标授权接口
	 */
	public static final String BUSSINESSTYPE_AUTOBID="bussinessType_autoBid";
	/**
	 * 系统支持调用接口类型-关闭自动投标授权接口
	 */
	public static final String BUSSINESSTYPE_CLOSEAUTOBID="bussinessType_closeAutoBid";
	/**
	 * 系统支持调用接口类型-无密还款授权接口
	 */
	public static final String BUSSINESSTYPE_NOPWREPAYMENT="bussinessType_nopwrepayment";
	/**
	 * 系统支持调用接口类型-解除无密还款授权接口
	 */
	public static final String BUSSINESSTYPE_RELIEVENOPWREPAYMENT="bussinessType_relieveNopwrepayment";
	
	
	
}
