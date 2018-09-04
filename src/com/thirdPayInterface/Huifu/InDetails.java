package com.thirdPayInterface.Huifu;

import java.util.List;

public class InDetails {
	private String OrdId;
	private String SubOrdId;
	private String InCustId;
	private String InAcctId;
	private String TransAmt;
	private String DzBorrCustId;
	private String FeeObjFlag;
	private String Fee;
	private List<DivDetails> DivDetails;
	public String getOrdId() {
		return OrdId;
	}
	public void setOrdId(String ordId) {
		OrdId = ordId;
	}
	public String getSubOrdId() {
		return SubOrdId;
	}
	public void setSubOrdId(String subOrdId) {
		SubOrdId = subOrdId;
	}
	public String getInCustId() {
		return InCustId;
	}
	public void setInCustId(String inCustId) {
		InCustId = inCustId;
	}
	public String getInAcctId() {
		return InAcctId;
	}
	public void setInAcctId(String inAcctId) {
		InAcctId = inAcctId;
	}
	public String getTransAmt() {
		return TransAmt;
	}
	public void setTransAmt(String transAmt) {
		TransAmt = transAmt;
	}
	public String getDzBorrCustId() {
		return DzBorrCustId;
	}
	public void setDzBorrCustId(String dzBorrCustId) {
		DzBorrCustId = dzBorrCustId;
	}
	public String getFeeObjFlag() {
		return FeeObjFlag;
	}
	public void setFeeObjFlag(String feeObjFlag) {
		FeeObjFlag = feeObjFlag;
	}
	public String getFee() {
		return Fee;
	}
	public void setFee(String fee) {
		Fee = fee;
	}
	public List<DivDetails> getDivDetails() {
		return DivDetails;
	}
	public void setDivDetails(List<DivDetails> divDetails) {
		DivDetails = divDetails;
	}
}
