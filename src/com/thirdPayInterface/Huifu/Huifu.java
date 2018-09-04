package com.thirdPayInterface.Huifu;


/**
 * 汇付天下
 * @author Administrator
 *
 */
public class Huifu{
	//
	
	public String getMaxTenderRate() {
		return MaxTenderRate;
	}
	public void setMaxTenderRate(String maxTenderRate) {
		MaxTenderRate = maxTenderRate;
	}
	public static final String GBKCHARSET = "GBK";
	public static final String CHARSETUTF8 = "UTF-8";
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
	public String getBorrowerDetails() {
		return BorrowerDetails;
	}
	public void setBorrowerDetails(String borrowerDetails) {
		BorrowerDetails = borrowerDetails;
	}
	/**
	 * 前台系统返回地址
	 */
	private String RetUrl;
	/**
	 * 后台系统返回地址
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
	/**
	 * 编码集
	 * @return
	 */
	private String charSet;
	/**
	 *订单号 
	 */
	private String ordId;
	/**
	 *订单日期 
	 */
	private String ordDate;
	/**
	 * 交易金额
	 * 
	 * @return
	 */
	private String transAmt;
	/**
	 * 用户客户号
	 * @return
	 */
	private String usrCustId;
	/**
	 * 借款人信息
	 * @return
	 */
	
	private String BorrowerDetails;
	/**
	 * 订单号
	 */
	private String OrdId;
	/**
	 * 订单日期 
	 */
	private String OrdDate;
	/**
	 * 交易金额
	 */
	private String TransAmt;
	/**
	 * 子账户类型
	 */
	private String SubAcctType;
	/**
	 * 子账户号
	 */
	private String SubAcctId;
	/**
	 * 最大投资手续费率
	 * @return
	 */
	private String MaxTenderRate;
	/**
	 * 是否冻结
	 * IsFreeze
	 * @return
	 */
    private String isFreeze;
    /**
     * 冻结流水号
     * FreezeOrdId 
     * @return
     */
    private String  freezeOrdId;
    /**
     * 请求扩展域
     * 
     * @return
     */
    private String reqExt;
    /**
     * 年利率
     * @return
     */
    private String yearRate;
    /**
     * 
     * 结算总金额
     * BorrTotAmt
     * @return
     */
    private String borrTotAmt;
    /**
     * ProId
     * 项目id
     * @return
     */
    private String proId;
    /**
     * 借款人id
     * BorrCustId
     * @return
     */
    private String borrCustId;
    /**
     * 还款类型
     * RetType
     * @return
     */
    private String retType;
    /**
     * 标的开始时间
     * BidStartDate
     * @return
     */
    private String bidStartDate;
    /**
     * 标的结束时间
     * BidEndDate
     * @return
     */
    private String bidEndDate;
    /**
     * 应还款总金额
     * RetAmt
     * @return
     */
    private String retAmt;
    /**
     * 应还款日期
     * RetDate
     * @return
     */
    private  String retDate;
    /**
     * 担保金额
     * GuarAmt
     * @return
     */
    private String guarAmt;
    /**
     * 担保公司id
     * GuarCompId
     * @return
     */
    private String guarCompId;
	private String retInterest;//应还款总利息
	private String lastRetDate;//最后还款日期
	private String loanPeriod;//借款期限
	private String guarantType;//本息保障
	private String bidProdType;//标的产品类型
	private String riskCtlType;//风险控制方式
	private String recommer;//推荐机构
	private String limitMinBidAmt;//限定最低投标份数
	private String limitBidSum;//限定每份投标金额
	private String limitMaxBidSum;//限定最高投标金额
	private String limitMinBidSum;//限定最少投标金额
	private String overdueProba;//逾期概率
	private String bidPayforState;//逾期是否垫资
	private String borrType;//借款人类型
	private String borrName;//借款人名称
	private String borrBusiCode;//借款企业营业执照编号
	private String borrCertType;//借款人证件类型
	private String borrCertId;//借款人证件号码
	private String borrMobiPhone;//借款人手机号码
	private String borrPhone;//借款人固定电话
	private String borrWork;//借款人工作单位
	private String borrWorkYear;//借款人工作年限
	private String borrIncome;//借款人税后收入
	private String borrEdu;//借款人学历
	private String borrMarriage;//借款人的婚姻状况
	private String borrAddr;//借款人地址
	private String borrEmail;//借款人电子邮箱
	private String borrPurpose;//借款用途
    private String bidType;//标的类型
    private String bidName;//标的名称
    private String outCustId;//出账客户号
    private String subOrdId;//订单号
    private String subOrdDate;//订单日期
    private String inCustId;//入账客户号
    private String divDetails;//分账客户串
    private String feeObjFlag;//手续费收取对象标志
    private String isDefault;//是否默认
    private String isUnFreeze;//是否解冻
    private String unFreezeOrdId;//解冻订单号
    private String freezeTrxId;//解冻标识
    private String fee;//放款或扣款手续费
    private String trxId;//平台交易唯一标识
	private String batchId;//还款批次号
	private String merOrdDate;//客户还款日期
	private String inDetails;//还款账户串
	private String inAccId;//入账子账户
	
	private String beginDate;//开始时间
	private String endDate;//结束时间
	private String pageNum;//页数
	private String pageSize;//每页记录数
	
	private String principalAmt;//还款本金
	private String interestAmt;//还款利息
    public String getPrincipalAmt() {
		return principalAmt;
	}
	public void setPrincipalAmt(String principalAmt) {
		this.principalAmt = principalAmt;
	}
	public String getInterestAmt() {
		return interestAmt;
	}
	public void setInterestAmt(String interestAmt) {
		this.interestAmt = interestAmt;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getPageNum() {
		return pageNum;
	}
	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getInAccId() {
		return inAccId;
	}
	public void setInAccId(String inAccId) {
		this.inAccId = inAccId;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getMerOrdDate() {
		return merOrdDate;
	}
	public void setMerOrdDate(String merOrdDate) {
		this.merOrdDate = merOrdDate;
	}
	public String getInDetails() {
		return inDetails;
	}
	public void setInDetails(String inDetails) {
		this.inDetails = inDetails;
	}
	public String getTrxId() {
		return trxId;
	}
	public void setTrxId(String trxId) {
		this.trxId = trxId;
	}
    private String outAcctId;//出账子账户
    
    /**
	 * 交易类型
	 */
	private String QueryTransType;
	/**
     * 借贷记标记
     * @return
     */
    private String dcFlag;
    /**
     * 身份证号
     * @return
     */
    private String certId;
    /**
     * 支付网关业务代号
     * GateBusiId
     * @return
     */
    private String gateBusiId;
    
	
	public String getDcFlag() {
		return dcFlag;
	}
	public void setDcFlag(String dcFlag) {
		this.dcFlag = dcFlag;
	}
	public String getCertId() {
		return certId;
	}
	public void setCertId(String certId) {
		this.certId = certId;
	}
	public String getGateBusiId() {
		return gateBusiId;
	}
	public void setGateBusiId(String gateBusiId) {
		this.gateBusiId = gateBusiId;
	}
	public String getOutAcctId() {
		return outAcctId;
	}
	public void setOutAcctId(String outAcctId) {
		this.outAcctId = outAcctId;
	}
	public String getQueryTransType() {
		return QueryTransType;
	}
	public void setQueryTransType(String queryTransType) {
		QueryTransType = queryTransType;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public String getOutCustId() {
		return outCustId;
	}
	public void setOutCustId(String outCustId) {
		this.outCustId = outCustId;
	}
	public String getFee() {
		return fee;
	}
	public String getSubOrdId() {
		return subOrdId;
	}
	public void setSubOrdId(String subOrdId) {
		this.subOrdId = subOrdId;
	}
	public String getSubOrdDate() {
		return subOrdDate;
	}
	public void setSubOrdDate(String subOrdDate) {
		this.subOrdDate = subOrdDate;
	}
	public String getInCustId() {
		return inCustId;
	}
	public void setInCustId(String inCustId) {
		this.inCustId = inCustId;
	}
	public String getDivDetails() {
		return divDetails;
	}
	public void setDivDetails(String divDetails) {
		this.divDetails = divDetails;
	}
	public String getFeeObjFlag() {
		return feeObjFlag;
	}
	public void setFeeObjFlag(String feeObjFlag) {
		this.feeObjFlag = feeObjFlag;
	}
	public String getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	public String getIsUnFreeze() {
		return isUnFreeze;
	}
	public void setIsUnFreeze(String isUnFreeze) {
		this.isUnFreeze = isUnFreeze;
	}
	public String getUnFreezeOrdId() {
		return unFreezeOrdId;
	}
	public void setUnFreezeOrdId(String unFreezeOrdId) {
		this.unFreezeOrdId = unFreezeOrdId;
	}
	public String getFreezeTrxId() {
		return freezeTrxId;
	}
	public void setFreezeTrxId(String freezeTrxId) {
		this.freezeTrxId = freezeTrxId;
	}
	public String getBidType() {
		return bidType;
	}
	public void setBidType(String bidType) {
		this.bidType = bidType;
	}
	public String getBidName() {
		return bidName;
	}
	public void setBidName(String bidName) {
		this.bidName = bidName;
	}
	public String getRetInterest() {
		return retInterest;
	}
	public void setRetInterest(String retInterest) {
		this.retInterest = retInterest;
	}
	public String getLastRetDate() {
		return lastRetDate;
	}
	public void setLastRetDate(String lastRetDate) {
		this.lastRetDate = lastRetDate;
	}
	public String getLoanPeriod() {
		return loanPeriod;
	}
	public void setLoanPeriod(String loanPeriod) {
		this.loanPeriod = loanPeriod;
	}
	public String getGuarantType() {
		return guarantType;
	}
	public void setGuarantType(String guarantType) {
		this.guarantType = guarantType;
	}
	public String getBidProdType() {
		return bidProdType;
	}
	public void setBidProdType(String bidProdType) {
		this.bidProdType = bidProdType;
	}
	public String getRiskCtlType() {
		return riskCtlType;
	}
	public void setRiskCtlType(String riskCtlType) {
		this.riskCtlType = riskCtlType;
	}
	public String getRecommer() {
		return recommer;
	}
	public void setRecommer(String recommer) {
		this.recommer = recommer;
	}
	public String getLimitMinBidAmt() {
		return limitMinBidAmt;
	}
	public void setLimitMinBidAmt(String limitMinBidAmt) {
		this.limitMinBidAmt = limitMinBidAmt;
	}
	public String getLimitBidSum() {
		return limitBidSum;
	}
	public void setLimitBidSum(String limitBidSum) {
		this.limitBidSum = limitBidSum;
	}
	public String getLimitMaxBidSum() {
		return limitMaxBidSum;
	}
	public void setLimitMaxBidSum(String limitMaxBidSum) {
		this.limitMaxBidSum = limitMaxBidSum;
	}
	public String getLimitMinBidSum() {
		return limitMinBidSum;
	}
	public void setLimitMinBidSum(String limitMinBidSum) {
		this.limitMinBidSum = limitMinBidSum;
	}
	public String getOverdueProba() {
		return overdueProba;
	}
	public void setOverdueProba(String overdueProba) {
		this.overdueProba = overdueProba;
	}
	public String getBidPayforState() {
		return bidPayforState;
	}
	public void setBidPayforState(String bidPayforState) {
		this.bidPayforState = bidPayforState;
	}
	public String getBorrType() {
		return borrType;
	}
	public void setBorrType(String borrType) {
		this.borrType = borrType;
	}
	public String getBorrName() {
		return borrName;
	}
	public void setBorrName(String borrName) {
		this.borrName = borrName;
	}
	public String getBorrBusiCode() {
		return borrBusiCode;
	}
	public void setBorrBusiCode(String borrBusiCode) {
		this.borrBusiCode = borrBusiCode;
	}
	public String getBorrCertType() {
		return borrCertType;
	}
	public void setBorrCertType(String borrCertType) {
		this.borrCertType = borrCertType;
	}
	public String getBorrCertId() {
		return borrCertId;
	}
	public void setBorrCertId(String borrCertId) {
		this.borrCertId = borrCertId;
	}
	public String getBorrMobiPhone() {
		return borrMobiPhone;
	}
	public void setBorrMobiPhone(String borrMobiPhone) {
		this.borrMobiPhone = borrMobiPhone;
	}
	public String getBorrPhone() {
		return borrPhone;
	}
	public void setBorrPhone(String borrPhone) {
		this.borrPhone = borrPhone;
	}
	public String getBorrWork() {
		return borrWork;
	}
	public void setBorrWork(String borrWork) {
		this.borrWork = borrWork;
	}
	public String getBorrWorkYear() {
		return borrWorkYear;
	}
	public void setBorrWorkYear(String borrWorkYear) {
		this.borrWorkYear = borrWorkYear;
	}
	public String getBorrIncome() {
		return borrIncome;
	}
	public void setBorrIncome(String borrIncome) {
		this.borrIncome = borrIncome;
	}
	public String getBorrEdu() {
		return borrEdu;
	}
	public void setBorrEdu(String borrEdu) {
		this.borrEdu = borrEdu;
	}
	public String getBorrMarriage() {
		return borrMarriage;
	}
	public void setBorrMarriage(String borrMarriage) {
		this.borrMarriage = borrMarriage;
	}
	public String getBorrAddr() {
		return borrAddr;
	}
	public void setBorrAddr(String borrAddr) {
		this.borrAddr = borrAddr;
	}
	public String getBorrEmail() {
		return borrEmail;
	}
	public void setBorrEmail(String borrEmail) {
		this.borrEmail = borrEmail;
	}
	public String getBorrPurpose() {
		return borrPurpose;
	}
	public void setBorrPurpose(String borrPurpose) {
		this.borrPurpose = borrPurpose;
	}
	public String getBidStartDate() {
		return bidStartDate;
	}
	public void setBidStartDate(String bidStartDate) {
		this.bidStartDate = bidStartDate;
	}
	public String getBidEndDate() {
		return bidEndDate;
	}
	public void setBidEndDate(String bidEndDate) {
		this.bidEndDate = bidEndDate;
	}
	public String getRetAmt() {
		return retAmt;
	}
	public void setRetAmt(String retAmt) {
		this.retAmt = retAmt;
	}
	public String getRetDate() {
		return retDate;
	}
	public void setRetDate(String retDate) {
		this.retDate = retDate;
	}
	public String getGuarAmt() {
		return guarAmt;
	}
	public void setGuarAmt(String guarAmt) {
		this.guarAmt = guarAmt;
	}
	public String getGuarCompId() {
		return guarCompId;
	}
	public void setGuarCompId(String guarCompId) {
		this.guarCompId = guarCompId;
	}
	public String getProArea() {
		return proArea;
	}
	public void setProArea(String proArea) {
		this.proArea = proArea;
	}
	/**
     * 项目所在地
     * ProArea
     * @return
     */
    private String proArea;
    /**
     * 
     * MerPriv
     * 用户私有域
     * @return
     */
    private String merPriv;
    public String getRetType() {
		return retType;
	}
	public void setRetType(String retType) {
		this.retType = retType;
	}
	public String getProId() {
		return proId;
	}
	public void setProId(String proId) {
		this.proId = proId;
	}
	public String getBorrCustId() {
		return borrCustId;
	}
	public void setBorrCustId(String borrCustId) {
		this.borrCustId = borrCustId;
	}
	public String getBorrTotAmt() {
		return borrTotAmt;
	}
	public void setBorrTotAmt(String borrTotAmt) {
		this.borrTotAmt = borrTotAmt;
	}
	public String getYearRate() {
		return yearRate;
	}
	public void setYearRate(String yearRate) {
		this.yearRate = yearRate;
	}
	public String getReqExt() {
		return reqExt;
	}
	public void setReqExt(String reqExt) {
		this.reqExt = reqExt;
	}
	public String getPageType() {
		return pageType;
	}
	public void setPageType(String pageType) {
		this.pageType = pageType;
	}
	private String pageType;
	public String getIsFreeze() {
		return isFreeze;
	}
	public void setIsFreeze(String isFreeze) {
		this.isFreeze = isFreeze;
	}
	public String getFreezeOrdId() {
		return freezeOrdId;
	}
	public void setFreezeOrdId(String freezeOrdId) {
		this.freezeOrdId = freezeOrdId;
	}
	public String getSubAcctType() {
		return SubAcctType;
	}
	public void setSubAcctType(String subAcctType) {
		SubAcctType = subAcctType;
	}
	public String getSubAcctId() {
		return SubAcctId;
	}
	public void setSubAcctId(String subAcctId) {
		SubAcctId = subAcctId;
	}
	public String getTransAmt() {
		return TransAmt;
	}
	public void setTransAmt(String transAmt) {
		TransAmt = transAmt;
	}
	public String getOrdDate() {
		return OrdDate;
	}
	public void setOrdDate(String ordDate) {
		OrdDate = ordDate;
	}
	public String getOrdId() {
		return OrdId;
	}
	public void setOrdId(String ordId) {
		OrdId = ordId;
	}
	public String getVersion() {
		return Version;
	}
	public void setVersion(String version) {
		Version = version;
	}
	public String getCmdId() {
		return CmdId;
	}
	public void setCmdId(String cmdId) {
		CmdId = cmdId;
	}
	public String getMerPriv() {
		return MerPriv;
	}
	public void setMerPriv(String merPriv) {
		MerPriv = merPriv;
	}
	public String getChkValue() {
		return ChkValue;
	}
	public void setChkValue(String chkValue) {
		ChkValue = chkValue;
	}
	public String getRespCode() {
		return RespCode;
	}
	public void setRespCode(String respCode) {
		RespCode = respCode;
	}
	public String getRespDesc() {
		return RespDesc;
	}
	public void setRespDesc(String respDesc) {
		RespDesc = respDesc;
	}
	public String getMerCustId() {
		return MerCustId;
	}
	public void setMerCustId(String merCustId) {
		MerCustId = merCustId;
	}
	public String getUsrCustId() {
		return UsrCustId;
	}
	public void setUsrCustId(String usrCustId) {
		UsrCustId = usrCustId;
	}
	public String getUsrId() {
		return UsrId;
	}
	public void setUsrId(String usrId) {
		UsrId = usrId;
	}
	public String getUsrName() {
		return UsrName;
	}
	public void setUsrName(String usrName) {
		UsrName = usrName;
	}
	public String getIdType() {
		return IdType;
	}
	public void setIdType(String idType) {
		IdType = idType;
	}
	public String getIdNo() {
		return IdNo;
	}
	public void setIdNo(String idNo) {
		IdNo = idNo;
	}
	public String getUsrMp() {
		return UsrMp;
	}
	public void setUsrMp(String usrMp) {
		UsrMp = usrMp;
	}
	public String getUsrEmail() {
		return UsrEmail;
	}
	public void setUsrEmail(String usrEmail) {
		UsrEmail = usrEmail;
	}
	public String getLoginPwd() {
		return LoginPwd;
	}
	public void setLoginPwd(String loginPwd) {
		LoginPwd = loginPwd;
	}
	public String getTransPwd() {
		return TransPwd;
	}
	public void setTransPwd(String transPwd) {
		TransPwd = transPwd;
	}
	public String getOpenAcctId() {
		return OpenAcctId;
	}
	public void setOpenAcctId(String openAcctId) {
		OpenAcctId = openAcctId;
	}
	public String getOpenBankId() {
		return OpenBankId;
	}
	public void setOpenBankId(String openBankId) {
		OpenBankId = openBankId;
	}
	public String getCardId() {
		return CardId;
	}
	public void setCardId(String cardId) {
		CardId = cardId;
	}
	public String getGateBankId() {
		return GateBankId;
	}
	public void setGateBankId(String gateBankId) {
		GateBankId = gateBankId;
	}
	public String getOpenProvId() {
		return OpenProvId;
	}
	public void setOpenProvId(String openProvId) {
		OpenProvId = openProvId;
	}
	public String getOpenAreaId() {
		return OpenAreaId;
	}
	public void setOpenAreaId(String openAreaId) {
		OpenAreaId = openAreaId;
	}
	public String getCharSet() {
		return charSet;
	}
	public void setCharSet(String charSet) {
		this.charSet = charSet;
	}
	public String getRetUrl() {
		return RetUrl;
	}
	public void setRetUrl(String retUrl) {
		RetUrl = retUrl;
	}
	public String getBgRetUrl() {
		return BgRetUrl;
	}
	public void setBgRetUrl(String bgRetUrl) {
		BgRetUrl = bgRetUrl;
	}
	
}