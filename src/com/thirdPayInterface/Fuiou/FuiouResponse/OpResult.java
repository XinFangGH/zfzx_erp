package com.thirdPayInterface.Fuiou.FuiouResponse;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

@XmlAccessorType(XmlAccessType.FIELD)
public class OpResult {

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
	 * 资金明细list
	 */
	@XmlElementWrapper(name = "details")
	@XmlElement(name = "detail")
	private List<Detail> details;

	public List<Detail> getDetails() {
		return details;
	}

	public void setDetails(List<Detail> details) {
		this.details = details;
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
}
