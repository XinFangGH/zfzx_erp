package com.thirdPayInterface.AllinPay.AllinpayUtil;

public class AllinPay {
	
	//通联网关   页面订单提交接口
	private String inputCharset="";//字符集(不可空)，默认填1；1代表UTF-8、2代表GBK、3代表GB2312；
	private String pickupUrl="";//付款客户的取货url地址,客户的取货地址，pickupUrl和receiveUrl两个参数不能同时为空，建议两个地址都填写
	private String receiveUrl="";//服务器接受支付结果的后台地址,通知商户网站支付结果的url地址，pickupUrl和receiveUrl两个参数不能同时为空，建议两个地址都填写
	private String version="";//网关接收支付请求接口版本(不可空),默认填v1.0,固定选择值：v1.0、v2.0；注意为小写字母，无特殊需求请一律填写v1.0	该字段决定payType、issuerId是否参与签名
	private String language="";//网关页面显示语言种类,默认填1，固定选择值：1；1代表简体中文、2代表繁体中文、3代表英文
	private String signType="";//签名类型,默认填1(不可空)，固定选择值：0、1；	0表示订单上送和交易结果通知都使用MD5进行签名	1表示商户用使用MD5算法验签上送订单，通联交易结果通知使用证书签名Asp商户不使用通联dll文件签名验签的商户填0
	private String merchantId="";//商户号,(不可空)数字串，商户在通联申请开户的商户号
	private String payerName="";//付款人姓名，英文或中文字符，当payType为3、issuerId为telpshx“直连模式”时，该值不可空，为办理银行卡时的所使用的姓名
	private String payerEmail="";//付款人邮件联系方式
	private String payerTelephone="";//付款人电话联系方式,数字串，当payType为3、issuerId不为空“直连模式”时，该值不可空，为付款人支付时所使用的手机号码
	private String payerIDCard="";//付款人证件号,
	private String pid="";//合作伙伴的商户号,用于商户与第三方合作伙伴拓展支付业务，Partner merchantId
	private String orderNo="";//订单号，（不可空），字符串，只允许使用字母、数字、- 、_,并以字母或数字开头；每商户提交的订单号，必须在当天的该商户所有交易中唯一
	private String orderAmount="";//商户订单金额（不可空）,整型数字，金额与币种有关	如果是人民币，则单位是分，即10元提交时金额应为1000	如果是美元，单位是美分，即10美元提交时金额为1000
	private String orderCurrency="";//订单金额币种类型,（不可空）默认填0	0和156代表人民币、840代表美元、344代表港币
	private String orderDatetime="";//商户订单提交时间,日期格式：yyyyMMDDhhmmss，例如：20121116020101
	private String orderExpireDatetime ="";//订单过期时间，整形数字，单位为分钟。 最大值为9999分钟。	如填写则以商户上送时间为准，如不填写或填0或填非法值，则服务端默认该订单9999分钟后过期。
	private String productName="";//商品名称，英文或中文字符串，请勿首尾有空格字符
	private String productPrice="";//商品价格
	private String productNum="";//商品数量
	private String productId="";//商品代码，字母、数字或- 、_ 的组合；用于使用产品数据中心的产品数据，或用于市场活动的优惠
	private String productDesc="";//商品描述
	private String ext1="";//扩展字段，长度128，英文或中文字符串，支付完成后，按照原样返回给商户
	private String ext2="";//同上
	private String extTL="";//业务扩展字段
	private String issuerId="";//发卡方代码,银行或预付卡发卡机构代码，用于指定支付使用的付款方机构。	接入手机网关时，该值请留空	该字段在version =v2.0 时不参与签名，version=v1.0时参与签名	payType为0时，issuerId必须为空——显示该商户支持的所有支付类型和各支付类型下支持的全部
	private String pan="";//付款人支付卡号,数字串，目前交行B2B直连模式才必填,并使用公钥加密(PKCS1)后进行Base64编码
	private String tradeNature="";//贸易类型,固定选择值：GOODS或SERVICES	当币种为人民币时选填	当币种为非人民币时必填，GOODS表示实物类型，SERVICES表示服务类型
	private String signMsg="";//签名字符串(不可空),为防止非法篡改要求商户对请求内容进行签名，供服务端进行校验；	签名串生成机制：按上述顺序所有非空参数与密钥key组合，经加密后生成signMsg；
	/* 支付方式(不可空)	 固定选择值：0、1、4、10、11、12、21、22	接入手机网关时，该值填固定填0	接入互联网关时，默认为间连模式，填0	若需接入外卡支付，只支持直连模式，即固定上送payType=23，issuerId=visa或mastercard
	0代表未指定支付方式，即显示该商户开通的所有支付方式
	1个人网银支付
	4企业网银支付
	10 wap支付
	11信用卡支付
	12快捷付
	21认证支付
	23外卡支付
	非直连模式，设置为0；直连模式，值为非0的固定选择值
	该字段在version=v1.0时参与签名，version =v2.0 时不参与签名*/
	private String payType="";
	private String key="";
	//通联  账户支付系统接口  批量代付
	//接口 INFO
	String TRX_CODE="";//交易代码
	String VERSION="";//版本
	String DATA_TYPE="";//数据格式
	String LEVEL="";//处理级别,0-9  0优先级最低，默认为5
	String USER_NAME="";//用户名
	String USER_PASS="";//用户密码
	String REQ_SN="";//交易批次号，必须全局唯一，商户提交的批次号必须以商户号开头以保证与其他商户不冲突，一旦冲突交易将无法提交；建议格式：商户号+时间+固定位数顺序流水号。该字段值用于后续的查询交易、对账文件等的唯一标识，对应通联系统中的交易文件名，可以在通联系统交易查询页面查询到该值
	String SIGNED_MSG="";//签名信息，签名原文为除SIGNED_MSG标签以外的请求报文进行签名
	//接口 BODY/TRANS_SUM
	String BUSINESS_CODE="";//（非空）业务代码
	String MERCHANT_ID="";//（非空）商户代码
	String SETTDAY="";//清算日期，仅供特殊商户使用,普通商户不用理会该字段，不推荐使用
	String SUBMIT_TIME="";//（非空）提交时间，YYYYMMDDHHMMSS
	String TOTAL_ITEM="";//（非空）总记录数
	String TOTAL_SUM="";//（非空）总金额。整数，单位分
	//接口 BODY/TRANS_DETAILS/TRANS_DETAIL
	String SN="";//（非空）记录序号，同一个请求内必须唯一。建议从0001开始递增,交易结果查询时，该字段会原样返回
	String E_USER_CODE="";//用户编号，客户编号,开发人员可当作备注字段使用
	String BANK_CODE="";//银行代码，(存折必须填写)。强烈建议填写银行代码,注意，测试环境测试时，请用户建行、招行或者农行进行测试即可,不要用什么昆仑银行之类的小行进行测试，否则会报渠道不支持
	String ACCOUNT_TYPE="";//账号类型,00银行卡，01存折，02信用卡。不填默认为银行卡00。
	String ACCOUNT_NO="";//（非空）账号。银行卡或存折号码
	String ACCOUNT_NAME="";//（非空）账号名，银行卡或存折上的所有人姓名
	String PROVINCE="";//开户行所在省,不带“省”或“自治区”，如 广东，广西，内蒙古等。	建议根据后附的中国邮政区号表内的“省洲名称”列的内容填写
	String CITY="";//开户行所在市。不带“市”，如 广州，南宁等。 	如果是直辖市，则填区，如北京（市）朝阳（区）。	建议根据后附的中国邮政区号表内的“地区名称”列的内容填写
	String BANK_NAME="";//开户行名称,开户行详细名称，也叫网点，如 中国建设银行广州东山广场分理处
	String ACCOUNT_PROP="";//（非空）账号属性,0私人，1公司。不填时，默认为私人0。
	String AMOUNT="";//（非空）金额 ，整数，单位分
	String CURRENCY="";//货币类型，人民币：CNY, 港元：HKD，美元：USD。不填时，默认为人民币。
	String PROTOCOL="";//协议号，开发人员可省略该字段，也可以当作备注字段使用
	String PROTOCOL_USERID="";//协议用户编号,开发人员可省略该字段，也可以当作备注字段使用
	String ID_TYPE="";//开户证件类型,0：身份证,1: 户口簿，2：护照,3.军官证,4.士兵证，5. 港澳居民来往内地通行证,6. 台湾同胞来往内地通行证,7. 临时身份证,8. 外国人居留证,9. 警官证, X.其他证件
	String ID="";//证件号
	String TEL="";//手机号/小灵通,小灵通带区号，不带括号，减号
	String CUST_USERID="";//自定义用户号,商户自定义的用户号，开发人员可省略该字段，也可以当作备注字段使用,该字段会在对账文件中原样返回
	String SETTACCT="";//本交易结算户,结算到商户的账户，不需分别清算时不需填写。
	String REMARK="";//备注，供商户填入参考信息。若为信用卡，填有效期, 开发人员可省略该字段，也可以当作备注字段使用
	String SETTGROUPFLAG="";//分组清算标志,仅供特殊商户使用，普通商户不要使用该字段，可忽略
	String SUMMARY="";//交易附言,填入网银的交易备注,可以在网银明细中查询到该字段信息，但部分银行可能不支持
	String UNION_BANK="";//支付行号
	
	//批量代付 交易结果查询
	//1、上面的商户ID
	String QUERY_SN="";//2、要查询的交易流水,也就是原请求交易中的REQ_SN的值
	String STATUS="";//3、状态,交易状态条件, 0成功,1失败, 2全部,3退票
	String TYPE="";//4、查询类型,0.按完成日期1.按提交日期，默认为1（如果使用0查询，未完成交易将查不到）
	String START_DAY="";//5、开始日，格式YYYYMMDDHHmmss，若不填QUERY_SN则必填
	String END_DAY="";//6、结束日，格式YYYYMMDDHHmmss，填了开始时间必填
	
	
	public String getQUERY_SN() {
		return QUERY_SN;
	}
	public void setQUERY_SN(String qUERYSN) {
		QUERY_SN = qUERYSN;
	}
	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	public String getTYPE() {
		return TYPE;
	}
	public void setTYPE(String tYPE) {
		TYPE = tYPE;
	}
	public String getSTART_DAY() {
		return START_DAY;
	}
	public void setSTART_DAY(String sTARTDAY) {
		START_DAY = sTARTDAY;
	}
	public String getEND_DAY() {
		return END_DAY;
	}
	public void setEND_DAY(String eNDDAY) {
		END_DAY = eNDDAY;
	}
	public String getTRX_CODE() {
		return TRX_CODE;
	}
	public void setTRX_CODE(String tRXCODE) {
		TRX_CODE = tRXCODE;
	}
	public String getVERSION() {
		return VERSION;
	}
	public void setVERSION(String vERSION) {
		VERSION = vERSION;
	}
	public String getDATA_TYPE() {
		return DATA_TYPE;
	}
	public void setDATA_TYPE(String dATATYPE) {
		DATA_TYPE = dATATYPE;
	}
	public String getLEVEL() {
		return LEVEL;
	}
	public void setLEVEL(String lEVEL) {
		LEVEL = lEVEL;
	}
	public String getUSER_NAME() {
		return USER_NAME;
	}
	public void setUSER_NAME(String uSERNAME) {
		USER_NAME = uSERNAME;
	}
	public String getUSER_PASS() {
		return USER_PASS;
	}
	public void setUSER_PASS(String uSERPASS) {
		USER_PASS = uSERPASS;
	}
	public String getREQ_SN() {
		return REQ_SN;
	}
	public void setREQ_SN(String rEQSN) {
		REQ_SN = rEQSN;
	}
	public String getSIGNED_MSG() {
		return SIGNED_MSG;
	}
	public void setSIGNED_MSG(String sIGNEDMSG) {
		SIGNED_MSG = sIGNEDMSG;
	}
	public String getBUSINESS_CODE() {
		return BUSINESS_CODE;
	}
	public void setBUSINESS_CODE(String bUSINESSCODE) {
		BUSINESS_CODE = bUSINESSCODE;
	}
	public String getMERCHANT_ID() {
		return MERCHANT_ID;
	}
	public void setMERCHANT_ID(String mERCHANTID) {
		MERCHANT_ID = mERCHANTID;
	}
	public String getSETTDAY() {
		return SETTDAY;
	}
	public void setSETTDAY(String sETTDAY) {
		SETTDAY = sETTDAY;
	}
	public String getSUBMIT_TIME() {
		return SUBMIT_TIME;
	}
	public void setSUBMIT_TIME(String sUBMITTIME) {
		SUBMIT_TIME = sUBMITTIME;
	}
	public String getTOTAL_ITEM() {
		return TOTAL_ITEM;
	}
	public void setTOTAL_ITEM(String tOTALITEM) {
		TOTAL_ITEM = tOTALITEM;
	}
	public String getTOTAL_SUM() {
		return TOTAL_SUM;
	}
	public void setTOTAL_SUM(String tOTALSUM) {
		TOTAL_SUM = tOTALSUM;
	}
	public String getSN() {
		return SN;
	}
	public void setSN(String sN) {
		SN = sN;
	}
	public String getE_USER_CODE() {
		return E_USER_CODE;
	}
	public void setE_USER_CODE(String eUSERCODE) {
		E_USER_CODE = eUSERCODE;
	}
	public String getBANK_CODE() {
		return BANK_CODE;
	}
	public void setBANK_CODE(String bANKCODE) {
		BANK_CODE = bANKCODE;
	}
	public String getACCOUNT_TYPE() {
		return ACCOUNT_TYPE;
	}
	public void setACCOUNT_TYPE(String aCCOUNTTYPE) {
		ACCOUNT_TYPE = aCCOUNTTYPE;
	}
	public String getACCOUNT_NO() {
		return ACCOUNT_NO;
	}
	public void setACCOUNT_NO(String aCCOUNTNO) {
		ACCOUNT_NO = aCCOUNTNO;
	}
	public String getACCOUNT_NAME() {
		return ACCOUNT_NAME;
	}
	public void setACCOUNT_NAME(String aCCOUNTNAME) {
		ACCOUNT_NAME = aCCOUNTNAME;
	}
	public String getPROVINCE() {
		return PROVINCE;
	}
	public void setPROVINCE(String pROVINCE) {
		PROVINCE = pROVINCE;
	}
	public String getCITY() {
		return CITY;
	}
	public void setCITY(String cITY) {
		CITY = cITY;
	}
	public String getBANK_NAME() {
		return BANK_NAME;
	}
	public void setBANK_NAME(String bANKNAME) {
		BANK_NAME = bANKNAME;
	}
	public String getACCOUNT_PROP() {
		return ACCOUNT_PROP;
	}
	public void setACCOUNT_PROP(String aCCOUNTPROP) {
		ACCOUNT_PROP = aCCOUNTPROP;
	}
	public String getAMOUNT() {
		return AMOUNT;
	}
	public void setAMOUNT(String aMOUNT) {
		AMOUNT = aMOUNT;
	}
	public String getCURRENCY() {
		return CURRENCY;
	}
	public void setCURRENCY(String cURRENCY) {
		CURRENCY = cURRENCY;
	}
	public String getPROTOCOL() {
		return PROTOCOL;
	}
	public void setPROTOCOL(String pROTOCOL) {
		PROTOCOL = pROTOCOL;
	}
	public String getPROTOCOL_USERID() {
		return PROTOCOL_USERID;
	}
	public void setPROTOCOL_USERID(String pROTOCOLUSERID) {
		PROTOCOL_USERID = pROTOCOLUSERID;
	}
	public String getID_TYPE() {
		return ID_TYPE;
	}
	public void setID_TYPE(String iDTYPE) {
		ID_TYPE = iDTYPE;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getTEL() {
		return TEL;
	}
	public void setTEL(String tEL) {
		TEL = tEL;
	}
	public String getCUST_USERID() {
		return CUST_USERID;
	}
	public void setCUST_USERID(String cUSTUSERID) {
		CUST_USERID = cUSTUSERID;
	}
	public String getSETTACCT() {
		return SETTACCT;
	}
	public void setSETTACCT(String sETTACCT) {
		SETTACCT = sETTACCT;
	}
	public String getREMARK() {
		return REMARK;
	}
	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}
	public String getSETTGROUPFLAG() {
		return SETTGROUPFLAG;
	}
	public void setSETTGROUPFLAG(String sETTGROUPFLAG) {
		SETTGROUPFLAG = sETTGROUPFLAG;
	}
	public String getSUMMARY() {
		return SUMMARY;
	}
	public void setSUMMARY(String sUMMARY) {
		SUMMARY = sUMMARY;
	}
	public String getUNION_BANK() {
		return UNION_BANK;
	}
	public void setUNION_BANK(String uNIONBANK) {
		UNION_BANK = uNIONBANK;
	}
	public String getInputCharset() {
		return inputCharset;
	}
	public void setInputCharset(String inputCharset) {
		this.inputCharset = inputCharset;
	}
	public String getPickupUrl() {
		return pickupUrl;
	}
	public void setPickupUrl(String pickupUrl) {
		this.pickupUrl = pickupUrl;
	}
	public String getReceiveUrl() {
		return receiveUrl;
	}
	public void setReceiveUrl(String receiveUrl) {
		this.receiveUrl = receiveUrl;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getSignType() {
		return signType;
	}
	public void setSignType(String signType) {
		this.signType = signType;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getPayerName() {
		return payerName;
	}
	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}
	public String getPayerEmail() {
		return payerEmail;
	}
	public void setPayerEmail(String payerEmail) {
		this.payerEmail = payerEmail;
	}
	public String getPayerTelephone() {
		return payerTelephone;
	}
	public void setPayerTelephone(String payerTelephone) {
		this.payerTelephone = payerTelephone;
	}
	public String getPayerIDCard() {
		return payerIDCard;
	}
	public void setPayerIDCard(String payerIDCard) {
		this.payerIDCard = payerIDCard;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}
	public String getOrderCurrency() {
		return orderCurrency;
	}
	public void setOrderCurrency(String orderCurrency) {
		this.orderCurrency = orderCurrency;
	}
	public String getOrderDatetime() {
		return orderDatetime;
	}
	public void setOrderDatetime(String orderDatetime) {
		this.orderDatetime = orderDatetime;
	}
	public String getOrderExpireDatetime() {
		return orderExpireDatetime;
	}
	public void setOrderExpireDatetime(String orderExpireDatetime) {
		this.orderExpireDatetime = orderExpireDatetime;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}
	public String getProductNum() {
		return productNum;
	}
	public void setProductNum(String productNum) {
		this.productNum = productNum;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	public String getExt1() {
		return ext1;
	}
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	public String getExt2() {
		return ext2;
	}
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}
	public String getExtTL() {
		return extTL;
	}
	public void setExtTL(String extTL) {
		this.extTL = extTL;
	}
	public String getIssuerId() {
		return issuerId;
	}
	public void setIssuerId(String issuerId) {
		this.issuerId = issuerId;
	}
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	public String getTradeNature() {
		return tradeNature;
	}
	public void setTradeNature(String tradeNature) {
		this.tradeNature = tradeNature;
	}
	public String getSignMsg() {
		return signMsg;
	}
	public void setSignMsg(String signMsg) {
		this.signMsg = signMsg;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	
}
