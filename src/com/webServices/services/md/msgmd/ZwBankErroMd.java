package com.webServices.services.md.msgmd;

public class ZwBankErroMd {
	/**
	 * 机构号
	 */
	private String corpno;
	private String duebillno;//	贷款编码
	private String custname	;	//3	客户名称	
	private String custtype	;	//4	客户类别	
	private String loantype	;	//5	贷款业务品种
	private String custbankname	;//6	客户银行账户名称	
	private String bankname;//7	开户行名称	
	private String accountno;	//8	账号	
	private String accounttype;	//9	账号性质	F01个人帐户	 F02公司帐户	
	
	public String getCorpno() {
		return corpno;
	}
	public void setCorpno(String corpno) {
		this.corpno = corpno;
	}
	public String getDuebillno() {
		return duebillno;
	}
	public void setDuebillno(String duebillno) {
		this.duebillno = duebillno;
	}
	public String getCustname() {
		return custname;
	}
	public void setCustname(String custname) {
		this.custname = custname;
	}
	public String getCusttype() {
		return custtype;
	}
	public void setCusttype(String custtype) {
		this.custtype = custtype;
	}
	public String getLoantype() {
		return loantype;
	}
	public void setLoantype(String loantype) {
		this.loantype = loantype;
	}
	public String getCustbankname() {
		return custbankname;
	}
	public void setCustbankname(String custbankname) {
		this.custbankname = custbankname;
	}
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	public String getAccountno() {
		return accountno;
	}
	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}
	public String getAccounttype() {
		return accounttype;
	}
	public void setAccounttype(String accounttype) {
		this.accounttype = accounttype;
	}
							
							

}
