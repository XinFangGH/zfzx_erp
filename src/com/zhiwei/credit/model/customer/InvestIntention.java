package com.zhiwei.credit.model.customer;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Date;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * 
 * @author 
 *
 */
/**
 * InvestIntention Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class InvestIntention extends com.zhiwei.core.model.BaseModel {

    protected Long intentId;
	protected String addrDemand;
	protected String perName;
	protected String dangerDemand;
	protected Integer intentDate;
	protected Long intentMoney;
	protected String issingleDemand;
	protected String otherInvest;
	protected String status;
	protected Integer investPersonId;
	protected String investPersonName;
	protected String findPerson;
	protected Date endDate;
	private InvestPerson investPerson;
	


	public String getIssingleDemand() {
		return issingleDemand;
	}

	public InvestPerson getInvestPerson() {
		return investPerson;
	}
	
	public String getPerName() {
		return this.perName;
	}

	public void setPerName(String perName) {
		
		this.perName = perName;
	}

	public void setInvestPerson(InvestPerson investPerson) {
		this.investPerson = investPerson;
	}

	/**
	 * Default Empty Constructor for class InvestIntention
	 */
	public InvestIntention () {
		super();
	}
	
	public String getFindPerson() {
		return findPerson;
	}

	public void setFindPerson(String findPerson) {
		this.findPerson = findPerson;
	}

	/**
	 * Default Key Fields Constructor for class InvestIntention
	 */
	public InvestIntention (
		 Long in_intentId
        ) {
		this.setIntentId(in_intentId);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="intent_id" type="java.lang.Long" generator-class="native"
	 */
	public Long getIntentId() {
		return this.intentId;
	}
	
	/**
	 * Set the intentId
	 */	
	public void setIntentId(Long aValue) {
		this.intentId = aValue;
	}	

	public String getInvestPersonName() {
		return investPersonName;
	}

	public void setInvestPersonName(String investPersonName) {
		this.investPersonName = investPersonName;
	}
	
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * 	 * @return String
	 * @hibernate.property column="addr_demand" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getAddrDemand() {
		return this.addrDemand;
	}
	
	/**
	 * Set the addrDemand
	 */	
	public void setAddrDemand(String aValue) {
		this.addrDemand = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="danger_demand" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	

	/**
	 * 	 * @return String
	 * @hibernate.property column="intent_date" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	
	
	public String getDangerDemand() {
		return dangerDemand;
	}

	public void setDangerDemand(String dangerDemand) {
		this.dangerDemand = dangerDemand;
	}

	public void setIssingleDemand(String issingleDemand) {
		this.issingleDemand = issingleDemand;
	}

		

		

	

	public Integer getIntentDate() {
		return intentDate;
	}

	public void setIntentDate(Integer intentDate) {
		this.intentDate = intentDate;
	}

	public Long getIntentMoney() {
		return intentMoney;
	}

	public void setIntentMoney(Long intentMoney) {
		this.intentMoney = intentMoney;
	}

	/**
	 * 	 * @return String
	 * @hibernate.property column="other_invest" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getOtherInvest() {
		return this.otherInvest;
	}
	
	/**
	 * Set the otherInvest
	 */	
	public void setOtherInvest(String aValue) {
		this.otherInvest = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="status" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getStatus() {
		return this.status;
	}
	
	/**
	 * Set the status
	 */	
	public void setStatus(String aValue) {
		this.status = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="invest_person_id" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getInvestPersonId() {
		return this.investPersonId;
	}
	
	/**
	 * Set the investPersonId
	 */	
	public void setInvestPersonId(Integer aValue) {
		this.investPersonId = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof InvestIntention)) {
			return false;
		}
		InvestIntention rhs = (InvestIntention) object;
		return new EqualsBuilder()
				.append(this.intentId, rhs.intentId)
				.append(this.addrDemand, rhs.addrDemand)
				.append(this.perName, rhs.perName)
				.append(this.dangerDemand, rhs.dangerDemand)
				.append(this.intentDate, rhs.intentDate)
				.append(this.intentMoney, rhs.intentMoney)
				.append(this.issingleDemand, rhs.issingleDemand)
				.append(this.otherInvest, rhs.otherInvest)
				.append(this.status, rhs.status)
				.append(this.investPersonId, rhs.investPersonId)
				.append(this.investPersonName, rhs.investPersonName)
				.append(this.findPerson, rhs.findPerson)
				.append(this.endDate, rhs.endDate)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.intentId) 
				.append(this.addrDemand) 
				.append(this.perName)
				.append(this.dangerDemand) 
				.append(this.intentDate) 
				.append(this.intentMoney) 
				.append(this.issingleDemand) 
				.append(this.otherInvest) 
				.append(this.status) 
				.append(this.investPersonId) 
				.append(this.investPersonName) 
				.append(this.findPerson) 
				.append(this.endDate) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("intentId", this.intentId) 
				.append("addrDemand", this.addrDemand) 
				.append("perName",this.perName)
				.append("dangerDemand", this.dangerDemand) 
				.append("intentDate", this.intentDate) 
				.append("intentMoney", this.intentMoney) 
				.append("issingleDemand", this.issingleDemand) 
				.append("otherInvest", this.otherInvest) 
				.append("status", this.status) 
				.append("investPersonId", this.investPersonId) 
				.append("investPersonId", this.investPersonName)
				.append("findPerson", this.findPerson)
				.append("endDate", this.endDate)
				.toString();
	}



}
