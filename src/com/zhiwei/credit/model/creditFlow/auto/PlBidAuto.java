package com.zhiwei.credit.model.creditFlow.auto;

import java.math.BigDecimal;
import java.util.Date;

@SuppressWarnings("serial")
public class PlBidAuto extends com.hurong.core.model.BaseModel{
	public static final Integer MAXAUTO=100;
	 public static final String ISBANNED="1";
	    public static final String MAXBIDMONEY="1";
	    public static final int DIVIDEMONEY=1;
	    public static final int TOTALMONEY=50;
	    //自动投标开启状态
	    public static final String OPEN="1";
	  //利率下限
	    public static final int ISTART=1;
	  //利率上限
	    public static final int IEND=24;
	  //期限下限
	    public static final int PSTART=0;
	  //期限上限
	    public static final int PEND=36;
		/**
		 * 主键id
		 */
	private Long id;
	/**
	 * 投资人Id bp_cust_member的id
	 */
	private Long userID;
	/**
	 * 每次投标金额
	 */
	private java.math.BigDecimal bidMoney;
	/**
	 * 利息范围起始值
	 */
	private Integer interestStart;
	/**
	 * 利息范围结束值
	 */
	private Integer interestEnd;
	/**
	 * 借款期限起始值
	 */
	private Integer periodStart;
	/**
	 * 借款期限结束值
	 */
	private Integer periodEnd;
	/**
	 * 信用等级范围起始值
	 */
	private String rateStart;
	/**
	 * 信用等级范围结束值
	 */
	private String rateEnd;
	/**
	 * 账户保留金额
	 */
	private java.math.BigDecimal keepMoney;
	/**
	 *  是否开启自动投标  1开启，0关闭
	 */
	private Integer isOpen;
	/**
	 * 操作时间
	 */
	private java.util.Date orderTime;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 第三方支付流水号
	 */
	private String requestNo;
	/**
	 * 是否禁用 0 开启 1禁用
	 */
	private Integer banned;
	public String getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	
	
	public Integer getBanned() {
		return banned;
	}
	public void setBanned(Integer banned) {
		this.banned = banned;
	}
	public java.util.Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(java.util.Date orderTime) {
		this.orderTime = orderTime;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getUserID() {
		return userID;
	}
	public void setUserID(Long userID) {
		this.userID = userID;
	}
	public java.math.BigDecimal getBidMoney() {
		return bidMoney;
	}
	public void setBidMoney(java.math.BigDecimal bidMoney) {
		this.bidMoney = bidMoney;
	}
	public Integer getInterestStart() {
		return interestStart;
	}
	public void setInterestStart(Integer interestStart) {
		this.interestStart = interestStart;
	}
	public Integer getInterestEnd() {
		return interestEnd;
	}
	public void setInterestEnd(Integer interestEnd) {
		this.interestEnd = interestEnd;
	}
	public Integer getPeriodStart() {
		return periodStart;
	}
	public void setPeriodStart(Integer periodStart) {
		this.periodStart = periodStart;
	}
	public Integer getPeriodEnd() {
		return periodEnd;
	}
	public void setPeriodEnd(Integer periodEnd) {
		this.periodEnd = periodEnd;
	}
	public String getRateStart() {
		return rateStart;
	}
	public void setRateStart(String rateStart) {
		this.rateStart = rateStart;
	}
	public String getRateEnd() {
		return rateEnd;
	}
	public void setRateEnd(String rateEnd) {
		this.rateEnd = rateEnd;
	}
	public java.math.BigDecimal getKeepMoney() {
		return keepMoney;
	}
	public void setKeepMoney(java.math.BigDecimal keepMoney) {
		this.keepMoney = keepMoney;
	}
	public Integer getIsOpen() {
		return isOpen;
	}
	public void setIsOpen(Integer isOpen) {
		this.isOpen = isOpen;
	}
	public PlBidAuto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PlBidAuto(Long id, Long userID, BigDecimal bidMoney,
			Integer interestStart, Integer interestEnd, Integer periodStart,
			Integer periodEnd, String rateStart, String rateEnd,
			BigDecimal keepMoney, Integer isOpen, Date orderTime) {
		super();
		this.id = id;
		this.userID = userID;
		this.bidMoney = bidMoney;
		this.interestStart = interestStart;
		this.interestEnd = interestEnd;
		this.periodStart = periodStart;
		this.periodEnd = periodEnd;
		this.rateStart = rateStart;
		this.rateEnd = rateEnd;
		this.keepMoney = keepMoney;
		this.isOpen = isOpen;
		this.orderTime = orderTime;
	}
	
}
