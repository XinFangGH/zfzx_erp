package com.thirdPayInterface.Fuiou.FuiouResponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class Detail {

	/**
	 * 交易流水号
	 */
	private String transSsn;
	/**
	 * 记账时间点
	 */
	private String rec_crt_ts;
	/**
	 * 账面余额出账金额
	 */
	private String ct_debit_amt;
	/**
	 * 账面余额入账金额
	 */
	private String ct_credit_amt;
	/**
	 * 可用余额出账金额
	 */
	private String ca_debit_amt;
	/**
	 * 可用余额入账金额
	 */
	private String ca_credit_amt;
	/**
	 * 未转结余额出账金额
	 */
	private String cu_debit_amt;
	/**
	 * 未转结余额入账金额
	 */
	private String cu_credit_amt;
	/**
	 * 冻结余额出账金额
	 */
	private String cf_debit_amt;
	/**
	 * 冻结余额入账金额
	 */
	private String cf_credit_amt;
	/**
	 * 账面余额
	 */
	private String ct_balance;
	/**
	 * 可用余额
	 */
	private String ca_balance;
	/**
	 * 未转接余额
	 */
	private String cu_balance;
	/**
	 * 冻结余额
	 */
	private String cf_balance;
	/**
	 * 摘要信息
	 */
	private String book_digest;
	
	public String getTransSsn() {
		return transSsn;
	}
	public void setTransSsn(String transSsn) {
		this.transSsn = transSsn;
	}
	public String getRec_crt_ts() {
		return rec_crt_ts;
	}
	public void setRec_crt_ts(String recCrtTs) {
		rec_crt_ts = recCrtTs;
	}
	public String getCt_debit_amt() {
		return ct_debit_amt;
	}
	public void setCt_debit_amt(String ctDebitAmt) {
		ct_debit_amt = ctDebitAmt;
	}
	public String getCt_credit_amt() {
		return ct_credit_amt;
	}
	public void setCt_credit_amt(String ctCreditAmt) {
		ct_credit_amt = ctCreditAmt;
	}
	public String getCa_debit_amt() {
		return ca_debit_amt;
	}
	public void setCa_debit_amt(String caDebitAmt) {
		ca_debit_amt = caDebitAmt;
	}
	public String getCa_credit_amt() {
		return ca_credit_amt;
	}
	public void setCa_credit_amt(String caCreditAmt) {
		ca_credit_amt = caCreditAmt;
	}
	public String getCu_debit_amt() {
		return cu_debit_amt;
	}
	public void setCu_debit_amt(String cuDebitAmt) {
		cu_debit_amt = cuDebitAmt;
	}
	public String getCu_credit_amt() {
		return cu_credit_amt;
	}
	public void setCu_credit_amt(String cuCreditAmt) {
		cu_credit_amt = cuCreditAmt;
	}
	public String getCf_debit_amt() {
		return cf_debit_amt;
	}
	public void setCf_debit_amt(String cfDebitAmt) {
		cf_debit_amt = cfDebitAmt;
	}
	public String getCf_credit_amt() {
		return cf_credit_amt;
	}
	public void setCf_credit_amt(String cfCreditAmt) {
		cf_credit_amt = cfCreditAmt;
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
	public String getBook_digest() {
		return book_digest;
	}
	public void setBook_digest(String bookDigest) {
		book_digest = bookDigest;
	}
}
