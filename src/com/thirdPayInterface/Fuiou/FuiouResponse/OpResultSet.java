package com.thirdPayInterface.Fuiou.FuiouResponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="opResult")
@XmlAccessorType(XmlAccessType.FIELD)
public class OpResultSet {
	/**
	 * 用户名
	 */
	private String user_id;
	/**
	 * 期初账面总余额
	 */
	private String ct_balance;
	/**
	 * 期初可用总余额
	 */
	private String ca_balance;
	/**
	 * 期初未转结总余额
	 */
	private String cu_balance;
	/**
	 * 期初冻结总余额
	 */
	private String cf_balance;
	
	private OpResult opResult;
	

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

	public OpResult getOpResult() {
		return opResult;
	}

	public void setOpResult(OpResult opResult) {
		this.opResult = opResult;
	}
}