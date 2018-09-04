package com.thirdPayInteface.Fuiou.FuiouResponse;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="plain")
@XmlAccessorType(XmlAccessType.FIELD)
public class Plain {
	/**
	 * 返回码
	 */
	private String resp_code;
	/**
	 * 商户代码
	 */
	private String mchnt_cd;
	/**
	 * 流水号
	 */
	private String mchnt_txn_ssn;
	/**
	 * 业务类型
	 */
	private String busi_tp;
	
	/**
	 * 总记录数
	 */
	private String total_number;
	/**
	 * 预授权合同号
	 */
	private String contract_no;
	/**
	 * 请求解冻金额
	 */
	private String amt;
	/**
	 * 成功解冻金额
	 */
	private String suc_amt;
	
	/**
	 * 记录列表list
	 */
	@XmlElementWrapper(name = "results")
	@XmlElement(name = "result")
	private List<Result> results;
	
	private OpResultSet opResultSet;

	public String getResp_code() {
		return resp_code;
	}

	public void setResp_code(String respCode) {
		resp_code = respCode;
	}

	public String getMchnt_cd() {
		return mchnt_cd;
	}

	public void setMchnt_cd(String mchntCd) {
		mchnt_cd = mchntCd;
	}

	public String getMchnt_txn_ssn() {
		return mchnt_txn_ssn;
	}

	public void setMchnt_txn_ssn(String mchntTxnSsn) {
		mchnt_txn_ssn = mchntTxnSsn;
	}

	public String getBusi_tp() {
		return busi_tp;
	}

	public void setBusi_tp(String busiTp) {
		busi_tp = busiTp;
	}

	public String getTotal_number() {
		return total_number;
	}

	public void setTotal_number(String totalNumber) {
		total_number = totalNumber;
	}

	public List<Result> getResults() {
		return results;
	}

	public void setResults(List<Result> results) {
		this.results = results;
	}

	public OpResultSet getOpResultSet() {
		return opResultSet;
	}

	public void setOpResultSet(OpResultSet opResultSet) {
		this.opResultSet = opResultSet;
	}

	public String getContract_no() {
		return contract_no;
	}

	public void setContract_no(String contractNo) {
		contract_no = contractNo;
	}

	public String getAmt() {
		return amt;
	}

	public void setAmt(String amt) {
		this.amt = amt;
	}

	public String getSuc_amt() {
		return suc_amt;
	}

	public void setSuc_amt(String sucAmt) {
		suc_amt = sucAmt;
	}

}