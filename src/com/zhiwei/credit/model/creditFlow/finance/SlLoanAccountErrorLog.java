package com.zhiwei.credit.model.creditFlow.finance;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * 
 * @author 
 *
 */
/**
 * SlLoanAccountErrorLog Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class SlLoanAccountErrorLog extends com.zhiwei.core.model.BaseModel {

    protected Long accountErrorId;
	protected String corpno;
	protected String duebillno;
	protected String custname;
	protected String custtype;
	protected String loantype;
	protected String custbankname;
	protected String bankname;
	protected String accountno;
	protected String accounttype;


	/**
	 * Default Empty Constructor for class SlLoanAccountErrorLog
	 */
	public SlLoanAccountErrorLog () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class SlLoanAccountErrorLog
	 */
	public SlLoanAccountErrorLog (
		 Long in_accountErrorId
        ) {
		this.setAccountErrorId(in_accountErrorId);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="accountErrorId" type="java.lang.Long" generator-class="native"
	 */
	public Long getAccountErrorId() {
		return this.accountErrorId;
	}
	
	/**
	 * Set the accountErrorId
	 */	
	public void setAccountErrorId(Long aValue) {
		this.accountErrorId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="corpno" type="java.lang.String" length="30" not-null="false" unique="false"
	 */
	public String getCorpno() {
		return this.corpno;
	}
	
	/**
	 * Set the corpno
	 */	
	public void setCorpno(String aValue) {
		this.corpno = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="duebillno" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	public String getDuebillno() {
		return this.duebillno;
	}
	
	/**
	 * Set the duebillno
	 */	
	public void setDuebillno(String aValue) {
		this.duebillno = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="custname" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getCustname() {
		return this.custname;
	}
	
	/**
	 * Set the custname
	 */	
	public void setCustname(String aValue) {
		this.custname = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="custtype" type="java.lang.String" length="30" not-null="false" unique="false"
	 */
	public String getCusttype() {
		return this.custtype;
	}
	
	/**
	 * Set the custtype
	 */	
	public void setCusttype(String aValue) {
		this.custtype = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="loantype" type="java.lang.String" length="30" not-null="false" unique="false"
	 */
	public String getLoantype() {
		return this.loantype;
	}
	
	/**
	 * Set the loantype
	 */	
	public void setLoantype(String aValue) {
		this.loantype = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="custbankname" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getCustbankname() {
		return this.custbankname;
	}
	
	/**
	 * Set the custbankname
	 */	
	public void setCustbankname(String aValue) {
		this.custbankname = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="bankname" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getBankname() {
		return this.bankname;
	}
	
	/**
	 * Set the bankname
	 */	
	public void setBankname(String aValue) {
		this.bankname = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="accountno" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getAccountno() {
		return this.accountno;
	}
	
	/**
	 * Set the accountno
	 */	
	public void setAccountno(String aValue) {
		this.accountno = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="accounttype" type="java.lang.String" length="30" not-null="false" unique="false"
	 */
	public String getAccounttype() {
		return this.accounttype;
	}
	
	/**
	 * Set the accounttype
	 */	
	public void setAccounttype(String aValue) {
		this.accounttype = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SlLoanAccountErrorLog)) {
			return false;
		}
		SlLoanAccountErrorLog rhs = (SlLoanAccountErrorLog) object;
		return new EqualsBuilder()
				.append(this.accountErrorId, rhs.accountErrorId)
				.append(this.corpno, rhs.corpno)
				.append(this.duebillno, rhs.duebillno)
				.append(this.custname, rhs.custname)
				.append(this.custtype, rhs.custtype)
				.append(this.loantype, rhs.loantype)
				.append(this.custbankname, rhs.custbankname)
				.append(this.bankname, rhs.bankname)
				.append(this.accountno, rhs.accountno)
				.append(this.accounttype, rhs.accounttype)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.accountErrorId) 
				.append(this.corpno) 
				.append(this.duebillno) 
				.append(this.custname) 
				.append(this.custtype) 
				.append(this.loantype) 
				.append(this.custbankname) 
				.append(this.bankname) 
				.append(this.accountno) 
				.append(this.accounttype) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("accountErrorId", this.accountErrorId) 
				.append("corpno", this.corpno) 
				.append("duebillno", this.duebillno) 
				.append("custname", this.custname) 
				.append("custtype", this.custtype) 
				.append("loantype", this.loantype) 
				.append("custbankname", this.custbankname) 
				.append("bankname", this.bankname) 
				.append("accountno", this.accountno) 
				.append("accounttype", this.accounttype) 
				.toString();
	}



}
