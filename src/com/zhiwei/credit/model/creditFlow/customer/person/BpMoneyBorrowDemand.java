package com.zhiwei.credit.model.creditFlow.customer.person;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * 
 * @author 
 *
 */
/**
 * BpMoneyBorrowDemand Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class BpMoneyBorrowDemand extends com.zhiwei.core.model.BaseModel {

    protected Integer borrowid;
	protected Long projectID;
	protected BigDecimal repaymentAmount;//最高月还款金额
	protected Integer familyKnow;//家人是否知晓
	protected BigDecimal quotaApplicationSmall;//申请额度(元)最小
	protected BigDecimal quotaApplicationBig;//最高申请额度
	protected Integer longestRepaymentPeriod;//最长还款期限
	protected String coment1;//贷款用途
	protected String coment2;
	protected String coment3;
	
	protected String assuretypeid;//主担保方式
	protected BigDecimal accrual; //期望利率
	protected java.util.Date startDate; // 期望资金到位日期
	protected String remark;//备注
	
	protected String linkmanKnown;//联系人是否知晓，大连天储要求 新增字段  取代家人是否知晓，录入文本功能


	/**
	 * Default Empty Constructor for class BpMoneyBorrowDemand
	 */
	public BpMoneyBorrowDemand () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class BpMoneyBorrowDemand
	 */
	public BpMoneyBorrowDemand (
		 Integer in_borrowid
        ) {
		this.setBorrowid(in_borrowid);
    }

    

	/**
	 * id	 * @return Integer
     * @hibernate.id column="borrowid" type="java.lang.Integer" generator-class="native"
	 */
	public Integer getBorrowid() {
		return this.borrowid;
	}
	
	/**
	 * Set the borrowid
	 */	
	public void setBorrowid(Integer aValue) {
		this.borrowid = aValue;
	}	

	/**
	 * 项目ID	 * @return Long
	 * @hibernate.property column="projectID" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	public Long getProjectID() {
		return this.projectID;
	}
	
	/**
	 * Set the projectID
	 * @spring.validator type="required"
	 */	
	public void setProjectID(Long aValue) {
		this.projectID = aValue;
	}	

	/**
	 * 最高月还款金额	 * @return Long
	 * @hibernate.property column="repaymentAmount" type="java.lang.Long" length="20" not-null="false" unique="false"
	 */
	public BigDecimal getRepaymentAmount() {
		return this.repaymentAmount;
	}
	
	/**
	 * Set the repaymentAmount
	 */	
	public void setRepaymentAmount(BigDecimal aValue) {
		this.repaymentAmount = aValue;
	}	

	/**
	 * 家人是否知晓	 * @return Integer
	 * @hibernate.property column="familyKnow" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getFamilyKnow() {
		return this.familyKnow;
	}
	
	/**
	 * Set the familyKnow
	 */	
	public void setFamilyKnow(Integer aValue) {
		this.familyKnow = aValue;
	}	

	/**
	 * 最小申请额度	 * @return Long
	 * @hibernate.property column="quotaApplicationSmall" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public BigDecimal getQuotaApplicationSmall() {
		return this.quotaApplicationSmall;
	}
	
	/**
	 * Set the quotaApplicationSmall
	 */	
	public void setQuotaApplicationSmall(BigDecimal aValue) {
		this.quotaApplicationSmall = aValue;
	}	

	/**
	 * 最高申请额度	 * @return Long
	 * @hibernate.property column="quotaApplicationBig" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public BigDecimal getQuotaApplicationBig() {
		return this.quotaApplicationBig;
	}
	
	/**
	 * Set the quotaApplicationBig
	 */	
	public void setQuotaApplicationBig(BigDecimal aValue) {
		this.quotaApplicationBig = aValue;
	}	

	/**
	 * 最长还款期限	 * @return Integer
	 * @hibernate.property column="longestRepaymentPeriod" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getLongestRepaymentPeriod() {
		return this.longestRepaymentPeriod;
	}
	
	/**
	 * Set the longestRepaymentPeriod
	 */	
	public void setLongestRepaymentPeriod(Integer aValue) {
		this.longestRepaymentPeriod = aValue;
	}	

	/**
	 * 注释1	 * @return String
	 * @hibernate.property column="coment1" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getComent1() {
		return this.coment1;
	}
	
	/**
	 * Set the coment1
	 */	
	public void setComent1(String aValue) {
		this.coment1 = aValue;
	}	

	/**
	 * 注释2	 * @return String
	 * @hibernate.property column="coment2" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getComent2() {
		return this.coment2;
	}
	
	/**
	 * Set the coment2
	 */	
	public void setComent2(String aValue) {
		this.coment2 = aValue;
	}	

	/**
	 * 注释3	 * @return String
	 * @hibernate.property column="coment3" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getComent3() {
		return this.coment3;
	}
	
	/**
	 * Set the coment3
	 */	
	public void setComent3(String aValue) {
		this.coment3 = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof BpMoneyBorrowDemand)) {
			return false;
		}
		BpMoneyBorrowDemand rhs = (BpMoneyBorrowDemand) object;
		return new EqualsBuilder()
				.append(this.borrowid, rhs.borrowid)
				.append(this.projectID, rhs.projectID)
				.append(this.repaymentAmount, rhs.repaymentAmount)
				.append(this.familyKnow, rhs.familyKnow)
				.append(this.quotaApplicationSmall, rhs.quotaApplicationSmall)
				.append(this.quotaApplicationBig, rhs.quotaApplicationBig)
				.append(this.longestRepaymentPeriod, rhs.longestRepaymentPeriod)
				.append(this.coment1, rhs.coment1)
				.append(this.coment2, rhs.coment2)
				.append(this.coment3, rhs.coment3)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.borrowid) 
				.append(this.projectID) 
				.append(this.repaymentAmount) 
				.append(this.familyKnow) 
				.append(this.quotaApplicationSmall) 
				.append(this.quotaApplicationBig) 
				.append(this.longestRepaymentPeriod) 
				.append(this.coment1) 
				.append(this.coment2) 
				.append(this.coment3) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("borrowid", this.borrowid) 
				.append("projectID", this.projectID) 
				.append("repaymentAmount", this.repaymentAmount) 
				.append("familyKnow", this.familyKnow) 
				.append("quotaApplicationSmall", this.quotaApplicationSmall) 
				.append("quotaApplicationBig", this.quotaApplicationBig) 
				.append("longestRepaymentPeriod", this.longestRepaymentPeriod) 
				.append("coment1", this.coment1) 
				.append("coment2", this.coment2) 
				.append("coment3", this.coment3) 
				.toString();
	}

	public String getAssuretypeid() {
		return assuretypeid;
	}

	public void setAssuretypeid(String assuretypeid) {
		this.assuretypeid = assuretypeid;
	}

	public BigDecimal getAccrual() {
		return accrual;
	}

	public void setAccrual(BigDecimal accrual) {
		this.accrual = accrual;
	}

	public java.util.Date getStartDate() {
		return startDate;
	}

	public void setStartDate(java.util.Date startDate) {
		this.startDate = startDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLinkmanKnown() {
		return linkmanKnown;
	}

	public void setLinkmanKnown(String linkmanKnown) {
		this.linkmanKnown = linkmanKnown;
	}



}
