package com.thirdPayInteface.Fuiou.FuiouResponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class Result {
	/**
	 * 扩展类型
	 */
	private String ext_tp;
	/**
	 * 交易日期
	 */
	private String txn_date;
	/**
	 * 交易时分
	 */
	private String txn_time;
	/**
	 * 交易请求方式
	 */
	private String src_tp;
	/**
	 * 交易流水
	 */
	private String mchnt_ssn;
	/**
	 * 交易金额
	 */
	private String txn_amt;
	/**
	 * 成功交易金额
	 */
	private String txn_amt_suc;
	/**
	 * 合同号
	 */
	private String contract_no;
	/**
	 * 出账用户虚拟账户
	 */
	private String out_fuiou_acct_no;
	/**
	 * 出账用户名
	 */
	private String out_cust_no;
	/**
	 * 出账用户名称
	 */
	private String out_artif_nm;
	
	/**
	 * 入账用户虚拟账户
	 */
	private String in_fuiou_acct_no;
	/**
	 * 入账用户名
	 */
	private String in_cust_no;
	/**
	 * 入账用户名称
	 */
	private String in_artif_nm;
	/**
	 * 交易备注
	 */
	private String remark;
	/**
	 * 交易返回码
	 */
	private String txn_rsp_cd;
	/**
	 * 交易返回码描述
	 */
	private String rsp_cd_desc;
	
	/**
	 * 用户名
	 */
	private String user_id;
	/**
	 * 账面总余额
	 */
	private String ct_balance;
	/**
	 * 可用余额
	 */
	private String ca_balance;
	/**
	 * 未转结余额
	 */
	private String cu_balance;
	/**
	 * 冻结余额
	 */
	private String cf_balance;
	/**
	 * 手机号码
	 */
	private String mobile_no;
	/**
	 * 客户姓名
	 */
	private String cust_nm;
	/**
	 * 身份证号码
	 */
	private String certif_id;
	/**
	 * 邮箱地址
	 */
	private String email;
	/**
	 * 开户行地区代码
	 */
	private String city_id;
	/**
	 * 开户行行别
	 */
	private String parent_bank_id;
	/**
	 * 开户行支行名称
	 */
	private String bank_nm;
	/**
	 * 帐号
	 */
	private String capAcntNo;

	public String getExt_tp() {
		return ext_tp;
	}

	public void setExt_tp(String extTp) {
		ext_tp = extTp;
	}

	public String getTxn_date() {
		return txn_date;
	}

	public void setTxn_date(String txnDate) {
		txn_date = txnDate;
	}

	public String getTxn_time() {
		return txn_time;
	}

	public void setTxn_time(String txnTime) {
		txn_time = txnTime;
	}

	public String getSrc_tp() {
		return src_tp;
	}

	public void setSrc_tp(String srcTp) {
		src_tp = srcTp;
	}

	public String getMchnt_ssn() {
		return mchnt_ssn;
	}

	public void setMchnt_ssn(String mchntSsn) {
		mchnt_ssn = mchntSsn;
	}

	public String getTxn_amt() {
		return txn_amt;
	}

	public void setTxn_amt(String txnAmt) {
		txn_amt = txnAmt;
	}

	public String getTxn_amt_suc() {
		return txn_amt_suc;
	}

	public void setTxn_amt_suc(String txnAmtSuc) {
		txn_amt_suc = txnAmtSuc;
	}

	public String getContract_no() {
		return contract_no;
	}

	public void setContract_no(String contractNo) {
		contract_no = contractNo;
	}

	public String getOut_fuiou_acct_no() {
		return out_fuiou_acct_no;
	}

	public void setOut_fuiou_acct_no(String outFuiouAcctNo) {
		out_fuiou_acct_no = outFuiouAcctNo;
	}

	public String getOut_cust_no() {
		return out_cust_no;
	}

	public void setOut_cust_no(String outCustNo) {
		out_cust_no = outCustNo;
	}

	public String getOut_artif_nm() {
		return out_artif_nm;
	}

	public void setOut_artif_nm(String outArtifNm) {
		out_artif_nm = outArtifNm;
	}

	public String getIn_fuiou_acct_no() {
		return in_fuiou_acct_no;
	}

	public void setIn_fuiou_acct_no(String inFuiouAcctNo) {
		in_fuiou_acct_no = inFuiouAcctNo;
	}

	public String getIn_cust_no() {
		return in_cust_no;
	}

	public void setIn_cust_no(String inCustNo) {
		in_cust_no = inCustNo;
	}

	public String getIn_artif_nm() {
		return in_artif_nm;
	}

	public void setIn_artif_nm(String inArtifNm) {
		in_artif_nm = inArtifNm;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTxn_rsp_cd() {
		return txn_rsp_cd;
	}

	public void setTxn_rsp_cd(String txnRspCd) {
		txn_rsp_cd = txnRspCd;
	}

	public String getRsp_cd_desc() {
		return rsp_cd_desc;
	}

	public void setRsp_cd_desc(String rspCdDesc) {
		rsp_cd_desc = rspCdDesc;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String userId) {
		user_id = userId;
	}

	public String getCt_balance() {
		return ct_balance;
	}

	public void setCt_balance(String ctBalance) {
		ct_balance = ctBalance;
	}

	public String getCa_balance() {
		return ca_balance;
	}

	public void setCa_balance(String caBalance) {
		ca_balance = caBalance;
	}

	public String getCu_balance() {
		return cu_balance;
	}

	public void setCu_balance(String cuBalance) {
		cu_balance = cuBalance;
	}

	public String getCf_balance() {
		return cf_balance;
	}

	public void setCf_balance(String cfBalance) {
		cf_balance = cfBalance;
	}

	public String getMobile_no() {
		return mobile_no;
	}

	public void setMobile_no(String mobileNo) {
		mobile_no = mobileNo;
	}

	public String getCust_nm() {
		return cust_nm;
	}

	public void setCust_nm(String custNm) {
		cust_nm = custNm;
	}

	public String getCertif_id() {
		return certif_id;
	}

	public void setCertif_id(String certifId) {
		certif_id = certifId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCity_id() {
		return city_id;
	}

	public void setCity_id(String cityId) {
		city_id = cityId;
	}

	public String getParent_bank_id() {
		return parent_bank_id;
	}

	public void setParent_bank_id(String parentBankId) {
		parent_bank_id = parentBankId;
	}

	public String getBank_nm() {
		return bank_nm;
	}

	public void setBank_nm(String bankNm) {
		bank_nm = bankNm;
	}

	public String getCapAcntNo() {
		return capAcntNo;
	}

	public void setCapAcntNo(String capAcntNo) {
		this.capAcntNo = capAcntNo;
	}
}