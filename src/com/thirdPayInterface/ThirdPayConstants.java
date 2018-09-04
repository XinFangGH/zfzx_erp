package com.thirdPayInterface;

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
	 * 											系统第三方业务类型 单业务类型   hry10001开头  
	 * 
	 *
	 */
	
	/**
	 * 注：bt=bussinesstype简写 ，业务代码  bt_后面不要超过12个字母
	 *     tn=transferName简写，业务描述   tn_后面不要超过12个字母
	 */
	/**
	 * 个人开通第三方
	 */
	public static final String BT_PREGISTER="hry10001";
	public static final String TN_PREGISTER="个人开通第三方";
	/**
	 * 企业开通第三方
	 */
	public static final String BT_EREGISTER="hry10002";
	public static final String TN_EREGISTER="企业开通第三方";
	/**
	 * 充值
	 */
	public static final String BT_RECHAGE="hry10003";
	public static final String TN_RECHAGE="充值";
	/**
	 * 取现
	 */
	public static final String BT_WITHDRAW="hry10004";
	public static final String TN_WITHDRAW="取现";
	/**
	 * 绑定银行卡
	 */
	public static final String BT_BINDCARD="hry10005";
	public static final String TN_BINDCARD="绑定银行卡";
	/**
	 * 取消绑定银行卡
	 */
	public static final String BT_CANCELCARD="hry10006";
	public static final String TN_CANCELCARD="取消绑定银行卡";
	/**
	 * 修改手机号
	 */
	public static final String BT_UPDATEPHONE="hry10007";
	public static final String TN_UPDATEPHONE="修改手机号";
	/**
	 * 更换银行卡
	 */
	public static final String BT_REPLACECARD="hry10008";
	public static final String TN_REPLACECARD="更换银行卡";
	/**
	 * 通用转账授权审核
	 */
	public static final String BT_USEALLAUDIT="hry10011";
	public static final String TN_USEALLAUDIT="通用转账授权审核";
	
	/**
	 * 查询订单
	 */
	public static final String BT_QUERYDEAL="hry10012";
	public static final String TN_QUERYDEAL="查询订单";
	
	/**
	 * 													系统第三方业务类型 散标业务类型   hry20001开头  
	 */
	
	
	
	/**
	 * 散标投标
	 */
	public static final String BT_BID="hry20001";
	public static final String TN_BID="散标投标";
	
	/**
	 * 散标放款
	 */
	public static final String BT_LOAN="hry20002";
	public static final String TN_LOAN="散标放款";
	/**
	 * 散标自助还款
	 */
	public static final String BT_REPAYMENT="hry20003";
	public static final String TN_REPAYMENT="散标自助还款";
	/**
	 * 平台帮助借款人还款
	 */
	public static final String BT_HELPPAY="hry20004";
	public static final String TN_HELPPAY="平台帮助借款人还款";
	/**
	 * 散标提前还款
	 */
	public static final String BT_BEFOREPAY="hry20005";
	public static final String TN_BEFOREPAY="散标提前还款";
	/**
	 * 散标自动投标
	 */
	public static final String BT_AUTOBID="hry20006";
	public static final String TN_AUTOBID="散标自动投标";
	/**
	 * 自动投标授权
	 */
	public static final String BT_OPENBIDAUTH="hry20007";
	public static final String TN_OPENBIDAUTH="自动投标授权";
	
	/**
	 * 自动还款授权
	 */
	public static final String BT_OPENPAYAUTH="hry20008";
	public static final String TN_OPENPAYAUTH="自动还款授权";
	
	/**
	 * 关闭自动投标授权
	 */
	public static final String BT_CLOSEBIDAUTH="hry20009";
	public static final String TN_CLOSEBIDAUTH="关闭自动投标授权";
	
	/**
	 * 关闭自动还款授权
	 */
	public static final String BT_CLOSEPAYAUTH="hry20010";
	public static final String TN_CLOSEPAYAUTH="关闭自动还款授权";
	/**
	 * 取消投标
	 */
	public static final String BT_CANCELBID="hry20011";
	public static final String TN_CANCELBID="取消投标";
	/**
	 * 代偿还款申请
	 */
	public static final String BT_REPLACEMONEY="hry20012";
	public static final String TN_REPLACEMONEY="代偿还款申请";
	/**
	 * 代偿还款审核
	 */
	public static final String BT_CHECKREPLACE="hry20013";
	public static final String TN_CHECKREPLACE="代偿还款审核";
	/**
	 * 建立标的信息
	 */
	public static final String BT_CREATEBID="hry20014";
	public static final String TN_CREATEBID="建立标的信息";
	/**
	 * 更新标的状态
	 */
	public static final String BT_UPDATEBID="hry20015";
	public static final String TN_UPDATEBID="更改标的状态";
	/**
	 * 散标返款 
	 */
	public static final String BT_RETURNMONEY="hry20016";
	public static final String TN_RETURNMONEY="散标返款";
	/**
	 * 散标放款授权
	 */
	public static final String BT_LOANAUTH="hry20017";
	public static final String TN_LOANAUTH="散标放款授权";
	/**
	 * 													系统第三方业务类型  理财计划业务类型   hry30001开头  
	 */
	
	/**
	 * 理财计划购买(收款账户为用户)
	 */
	public static final String BT_MMUSER="hry30001";
	public static final String TN_MMUSER="理财计划购买";
	/**
	 * 理财计划购买(收款账户为平台)
	 */
	public static final String BT_MMPLATFORM="hry30002";
	public static final String TN_MMPLATFORM="理财计划购买";
	/**
	 * 理财计划起息(收款账户为用户)
	 */
	public static final String BT_MMLOANUSER="hry30003";
	public static final String TN_MMLOANUSER="理财计划起息(收款账户为用户)";
	/**
	 * 理财计划起息(收款账户为平台)
	 */
	public static final String BT_MMLOANPLATF="hry30004";
	public static final String TN_MMLOANPLATF="理财计划起息(收款账户为平台)";
	/**
	 * 理财计划派息(收款账户为用户)
	 */
	public static final String BT_MMGIVEUSER="hry30005";
	public static final String TN_MMGIVEUSER="理财计划派息(收款账户为用户)";
	/**
	 * 理财计划派息(收款账户为平台)
	 */
	public static final String BT_MMGIVEPLATF="hry30006";
	public static final String TN_MMGIVEPLATF="理财计划派息(收款账户为平台)";
	
	/**
	 * 理财计划取消购买(收款账户为用户)
	 */
	public static final String BT_MMCANCELUSER="hry30007";
	public static final String TN_MMCANCELUSER="理财计划取消购买(收款账户为用户)";
	/**
	 * 理财计划取消购买(收款账户为平台)
	 */
	public static final String BT_MMCANCELPLATF="hry30008";
	public static final String TN_MMCANCELPLATF="理财计划取消购买(收款账户为平台)";
	/**
	 * 理财计划授权平台派息
	 */
	public static final String BT_MMAUTH="hry30009";
	public static final String TN_MMAUTH="理财计划授权平台派息";
	/**
	 * 理财计划提前退出(收款账户为用户)
	 */
	public static final String BT_MMSIGNOUTUSER="hry30010";
	public static final String TN_MMSIGNOUTUSER="理财计划提前退出(收款账户为用户)";
	/**
	 * 理财计划提前退出(收款账户为平台)
	 */
	public static final String BT_MMSIGNOUTPLATF="hry30011";
	public static final String TN_MMSIGNOUTPLATF="理财计划提前退出(收款账户为平台)";
	/**
	 * 理财计划返款(收款账户为用户)
	 */
	public static final String BT_MMBACKMONEY="hry300012";
	public static final String TN_MMBACKMONEY="理财计划返款(收款账户为用户)";
	
	/**
	 * 													系统第三方业务类型 债权交易业务类型   hry40001开头  
	 */
	
	/**
	 * 挂牌交易
	 */
	public static final String BT_HANGDEAL="hry40001";
	public static final String TN_HANGDEAL="挂牌交易";
	/**
	 * 购买债权
	 */
	public static final String BT_BUYDEAL="hry40002";
	public static final String TN_BUYDEAL="购买债权";
	/**
	 * 取消挂牌
	 */
	public static final String BT_CANCELDEAL="hry40003";
	public static final String TN_CANCELDEAL="取消挂牌";
	/**
	 * 挂牌交易服务费将预收转为实收
	 */
	public static final String BT_TRUESERVICE="hry40004";
	public static final String TN_TRUESERVICE="挂牌交易服务费将预收转为实收";
	/**
	 * 挂牌交易服务费将预收转实收后退费
	 */
	public static final String BT_FALSESERVICE="hry40005";
	public static final String TN_FALSESERVICE="挂牌交易服务费将预收转实收后退费";
	/**
	 * 债权交易成功后返款
	 */
	public static final String BT_BACKDEAL="hry40006";
	public static final String TN_BACKDEAL="债权交易成功后返款";
	
	/**
	 * 													系统第三方业务类型 平台服务业务类型   hry50001开头  
	 */
	
	
	/**
	 * 互融宝转入申请
	 */
	public static final String BT_HRBIN="hry50001";
	public static final String TN_HRBIN="互融宝转入申请";
	/**
	 * 互融宝转入审核
	 */
	public static final String BT_CHECKHRBIN="hry50002";
	public static final String TN_CHECKHRBIN="互融宝转入审核";
	/**
	 * 互融宝转出
	 */
	public static final String BT_HRBOUT="hry50003";
	public static final String TN_HRBOUT="互融宝转出";
	/**
	 * 派发红包
	 */
	public static final String BT_SEDRED="hry50004";
	public static final String TN_SEDRED="派发红包";
	/**
	 * 优惠券派发奖励
	 */
	public static final String BT_COUPONAWARD="hry50005";
	public static final String TN_COUPONAWARD="优惠券派发奖励";
	/**
	 * 平台准备金代偿还款
	 */
	public static final String BT_PREPAREPAY="hry50006";
	public static final String TN_PREPAREPAY="平台准备金代偿还款";
	/**
	 * 平台商户充值
	 */
	public static final String BT_PLATFORMPAY="hry50007";
	public static final String TN_PLATFORMPAY="平台商户充值";
	/**
	 *平台收费 
	 */
	public static final String BT_PLATEFORMRECHAGE="hry50008";
	public static final String TN_PLATEFORMRECHAGE="平台收取手续费";
	/**
	 *平台收费 (放款)
	 */
	public static final String BT_PLATEFORMRECHAGE_LOAN="hry50009";
	public static final String TN_PLATEFORMRECHAGE_LOAN="平台收取手续费(放款)";
	
	/**
	 * 散标自助批量还款
	 */
	public static final String BT_BATCHREPAYMENT="hry20019";
	public static final String TN_BATCHREPAYMENT="散标平台批量还款";

	/**
	 * 
	 */
	public static final String BT_HELPPAY_FEE="hry50010";
	public static final String TN_HELPPAY_FEE="平台收取手续费(后台还款)";
	
	
	/**
	 * 平台给用户转账
	 */
	public static final String BT_TRANSFER_MEMBER="hry50011";
	public static final String TN_TRANSFER_MEMBER="平台给用户转账";
	
	
	
	/**
	 * 													系统第三方业务类型 查询业务类型   hry60001开头  
	 */
	/**
	 * 平台会员交易查询(充值、取现、还款、放款、转账)
	 */
	public static final String BT_QUERYPLATF="hry60001";
	public static final String TN_QUERYPLATF="平台会员交易查询(充值、取现、还款、放款、转账)";
	/**
	 * 注册用户查询
	 */
	public static final String BT_QUERYUSER="hry60002";
	public static final String TN_QUERYUSER="注册用户查询";
	/**
	 * 业务对账
	 */
	public static final String BT_QUERYACCOUNT="hry60003";
	public static final String TN_QUERYACCOUNT="业务对账";
	/**
	 *平台账户流水查询 
	 */
	public static final String BT_PLATEFLOWQUERY="hry60004";
	public static final String TN_PLATEFLOWQUERY="平台账户流水查询 ";
	/**
	 *标的账户流水查询 
	 */
	public static final String BT_BIDFLOWQUERY="hry60005";
	public static final String TN_BIDFLOWQUERY="标的账户流水查询 ";
	/**
	 *商户账户流水查询 
	 */
	public static final String BT_ACCOUNTQUERY="hry60006";
	public static final String TN_ACCOUNTQUERY="商户账户流水查询";
	/**
	 * 充值对账文件
	 */
	public static final String BT_RECHARGEFILE="hry60007";
	public static final String TN_RECHARGEFILE="充值对账文件";
	/**
	 * 提现对账文件
	 */
	public static final String BT_WITHDRAWFILE="hry60008";
	public static final String TN_WITHDRAWFILE="提现对账文件";
	/**
	 * 标的对账文件
	 */
	public static final String BT_BIDBALANCEFILE="hry60009";
	public static final String TN_BIDBALANCEFILE="标的对账文件";
	/**
	 * 标的转账对账文件
	 */
	public static final String BT_BIDTRANSFERFILE="hry60010";
	public static final String TN_BIDTRANSFERFILE="标的转账对账文件";
	/**
	 * 普通转账对账文件
	 */
	public static final String BT_TRANSFERFILE="hry60011";
	public static final String TN_TRANSFERFILE="普通转账对账文件";
	/**
	 * 平台商户余额查询
	 */
	public static final String BT_GETPLATFORM="hry60012";
	public static final String TN_GETPLATFORM="平台商户余额查询";
	/**
	 * 平台商户流水查询
	 */
	public static final String BT_QPLATFORM="hry60013";
	public static final String TN_QPLATFORM="平台商户流水查询";
	/**
	 * 资金解冻
	 */
	public static final String BT_UNFREEZE="hry60015";
	public static final String TN_UNFREEZE="资金解冻接口";
	/**
	 *用户余额查询 
	 */
	public static final String BT_BALANCEQUERY="hry60014";
	public static final String TN_BALANCEQUERY="用户余额查询";
	
	
	
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
	 * 系统支持调用接口类型-取消投标接口
	 */
	public static final String BUSSINESSTYPE_REVOCATIONTRANSFER="bussinesstype_revocationtransfer";
	
	/**
	 * 系统支持调用接口类型-理财计划投标接口
	 */
	public static final String BUSSINESSTYPE_MONEYPLANBID="bussinessType_moneyPlanBid";
	
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
	 * 系统支持调用接口类型-借款人还款接口(网关)
	 */
	public static final String BUSSINESSTYPE_LOANERREPAYMENT="bussinessType_loanerRepayment";
	/**
	 * 系统支持调用接口类型-平台帮助借款人还款接口(直连)
	 */
	public static final String BUSSINESSTYPE_LOANERDIRECTLYREPAYMENT="bussinessType_loanerDirectlyRepayment";
	/**
	 * 系统支持调用接口类型-平台帮助借款人还款接口(直连)--散标
	 */
	public static final String BUSSINESSNAME_REPAYMENTBID="bussinessname_repaymentbid";
	/**
	 * 系统支持调用接口类型-平台帮助借款人还款接口(直连)--理财计划
	 */
	public static final String BUSSINESSNAME_REPAYMENTPLMMAN="bussinessname_repaymentplmman";
	
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
	 * 系统支持调用接口类型用途-无密还款授权接口（理财计划派息）
	 */
	public static final String BUSSINESSNAME_NOPWREPAYMENTMMPLAN="bussinessname_nopwrepaymentmmplan";
	/**
	 * 系统支持调用接口类型-解除无密还款授权接口
	 */
	public static final String BUSSINESSTYPE_RELIEVENOPWREPAYMENT="bussinessType_relieveNopwrepayment";
	/**
	 * 系统支持调用接口类型-债权转让
	 */
	public static final String BUSSINESSTYPE_TOTRANSFERCLAIMS="bussinessType_TOTRANSFERCLAIMS";
	/**
	 * 系统支持调用接口类型-修改手机号
	 */
	public static final String BUSSINESSTYPE_TORESETMOBILE="bussinessType_toResetMobile";
	/**
	 * 系统支持调用接口类型-单笔交易查询(充值，取现，投标，放款，通用转账)
	 */
	public static final String BUSSINESSTYPE_SINGLEQUERY="bussinessType_singleQuery";
	/**
	 * 平台划款
	 */
	public static final String BUSSINESSTYPE_PLATFORMTRANSFER="bussinessType_platformTransfer";
	/**
	 * 平台划款(互融宝转出)
	 */
	public static final String BUSSINESSNAME_HURONGTRANSFER="bussinessname_hurongtransfer";
	/**
	 * 平台划款(平台账户起息)
	 */
	public static final String BUSSINESSNAME_PTZHLOAN="bussinessname_ptzhLoan";
	/**
	 * 平台划款(平台派发红包)
	 */
	public static final String BUSSINESSNAME_CUSTRED="bussinessname_custred";
	/**
	 * 平台划款(优惠券派发)
	 */
	public static final String BUSSINESSNAME_COUPONS="bussinessname_coupons";
	/**
	 * 平台划款(理财计划派息)
	 */
	public static final String BUSSINESSNAME_PLMMANORDER="bussinessname_plmmanorder";
	/**
	 * 平台划款(挂牌交易预收已经转了实收退费)
	 */
	public static final String BUSSINESSNAME_BANKMONEY="bussinessname_bankmoney"; 
	
	/**
	 * 平台准备金代偿还款
	 */
	public static final String BUSSINESSTYPE_TOREPAYMENTBYRESERVE="bussinesstype_torepaymentbyreserve";
	/**
	 * 业务 对账
	 */
	public static final String BUSSINESSTYPE_RECONCILIATION="bussinessType_reconciliation";
	/**
	 * 系统支持调用接口类型-自动投标接口
	 */
	public static final String BUSSINESSTYPE_TOAUTOBID = "bussinessType_toAutoBid"; 
	/**
	 * 系统支持调用接口类型-查询用户第三方信息接口
	 */
	public static final String BUSSINESSTYPE_QUERYREGISTERINFO = "bussinessType_queryregisterinfo"; 
	/**
	 * 系统支持调用接口类型-通用转账授权审核接口 
	 */
	public static final String BUSSINESSTYPE_CHECKCOMMONTRANSFER = "bussinesstype_checkcommontransfer"; 
	/**
	 * 系统支持调用接口类型-通用转账授权审核接口 (取消债权交易挂牌)
	 */
	public static final String BUSSINESSNAME_CANCELDEAL = "bussinessname_canceldeal"; 
	/**
	 * 系统支持调用接口类型-通用转账授权审核接口 (债权交易预收转实收)
	 */
	public static final String BUSSINESSNAME_CONFIRMDEAL = "bussinessname_confirmdeal"; 
	/**
	 * 系统支持调用接口类型-通用转账授权审核接口 (理财计划收款账户为平台账户流标	)
	 */
	public static final String BUSSINESSNAME_CANCELPLMMAN = "bussinessname_cancelplmman"; 
	/**
	 * 系统支持调用接口用途 ------购买理财
	 */
	public static final String BUSSINESSNAME_MMPLAN="bussinessname_mmplan";
	/**
	 * 系统支持调用接口类型-通用转账授权
	 */
	public static final String BUSSINESSTYPE_TOCPTRANSACTION="bussinessType_toCpTransaction";
	/**
	 * 系统支持调用接口用途-通用转账授权
	 */
	public static final String BUSSINESSNAME_TOCPTRANSACTION="bussinessName_tocptransaction";
	/**
	 * 系统支持调用接口用途 ------代偿还款
	 */
	public static final String BUSSINESSNAME_DCHK="bussinessname_dchk";
	/**
	 * 系统支持调用接口用途 ------站岗资金的购买
	 */
	public static final String BUSSINESSNAME_PFUI="bussinessname_pfui";
}
