package com.zhiwei.credit.model.creditFlow.finance;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class BpFundIntent extends FundIntent {

	
	/**
	 * 用来进行投资人款项对账的资金来源
	 * 1 正常放款，还款
	 * 2 还款时由平台代偿
	 * 3还款由担保公司代偿（暂时没有用）
	 */
	public static final Short REPAYSOURCE1=1;//1 正常放款，还款
	public static final Short REPAYSOURCE2=2;//2 还款时由平台代偿
	/**
	 * 
	 */
	private static final long serialVersionUID = -4356794928998267625L;
	
	private String orderNo;
	
	protected Long bidPlanId;

	/**
	 * 交易流水号（针对放款，还款，派息，操作记录字段）
	 */
	protected String requestNo;
	/**
	 * 最新请求第三方交易时间
	 */
	protected Date requestDate;
	/**
	 * 请求第三方交易次数
	 */
	protected Integer requestCount=0;
	/**
	 * 交易流水号（针对联动优势第三方新增字段）
	 * 主要是为了记录融资方或代偿方对资金偿还的流水号
	 */
	protected String requestNOLoaner;
	
	/**
	 * 还款时间
	 */
	protected Date returnDate;
	
	
	private String loanDate;//放款时间
	
	public String getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(String loanDate) {
		this.loanDate = loanDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public String getRequestNOLoaner() {
		return requestNOLoaner;
	}

	public void setRequestNOLoaner(String requestNOLoaner) {
		this.requestNOLoaner = requestNOLoaner;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public Integer getRequestCount() {
		return requestCount;
	}

	public void setRequestCount(Integer requestCount) {
		this.requestCount = requestCount;
	}

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	protected Set<SlFundDetail> slFundDetails = new HashSet<SlFundDetail>(
			0);

	/**
	 * 不和数据库字段映射
	 */
	protected String protype;
	
	//@HT_version3.0 begin
	//生成借款人合同时需要
	protected BigDecimal principal;
	protected BigDecimal interest;
	protected BigDecimal consultationMoney;
	protected BigDecimal serviceMoney;
	
	protected String bidProName;   //标的名称
	protected String investpersonName;   //标的名称
	protected BigDecimal sumMoney;   //合计金额
	//@HT_version3.0 end

	
	/**
	 * 用来进行对账的资金来源
	 */
	protected Short repaySource;
	/**
     * 用来标注还款成功返款失败的情况
     *   主要针对于联动优势对于还款要调两次接口 
     */
	protected Integer loanerRepayMentStatus;//还款成功 1 

	public Integer getLoanerRepayMentStatus() {
		return loanerRepayMentStatus;
	}

	public void setLoanerRepayMentStatus(Integer loanerRepayMentStatus) {
		this.loanerRepayMentStatus = loanerRepayMentStatus;
	}

	public Short getRepaySource() {
		return repaySource;
	}

	public void setRepaySource(Short repaySource) {
		this.repaySource = repaySource;
	}

	public String getProtype() {
		return protype;
	}

	public void setProtype(String protype) {
		this.protype = protype;
	}

	public Set<SlFundDetail> getSlFundDetails() {
		return slFundDetails;
	}

	public void setSlFundDetails(Set<SlFundDetail> slFundDetails) {
		this.slFundDetails = slFundDetails;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Long getBidPlanId() {
		return bidPlanId;
	}

	public void setBidPlanId(Long bidPlanId) {
		this.bidPlanId = bidPlanId;
	}

	public BigDecimal getPrincipal() {
		return principal;
	}

	public void setPrincipal(BigDecimal principal) {
		this.principal = principal;
	}

	public BigDecimal getInterest() {
		return interest;
	}

	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}

	public BigDecimal getConsultationMoney() {
		return consultationMoney;
	}

	public void setConsultationMoney(BigDecimal consultationMoney) {
		this.consultationMoney = consultationMoney;
	}

	public BigDecimal getServiceMoney() {
		return serviceMoney;
	}

	public void setServiceMoney(BigDecimal serviceMoney) {
		this.serviceMoney = serviceMoney;
	}

	public String getBidProName() {
		return bidProName;
	}

	public void setBidProName(String bidProName) {
		this.bidProName = bidProName;
	}

	public BigDecimal getSumMoney() {
		return sumMoney;
	}

	public void setSumMoney(BigDecimal sumMoney) {
		this.sumMoney = sumMoney;
	}

	public String getInvestpersonName() {
		return investpersonName;
	}

	public void setInvestpersonName(String investpersonName) {
		this.investpersonName = investpersonName;
	}

}
