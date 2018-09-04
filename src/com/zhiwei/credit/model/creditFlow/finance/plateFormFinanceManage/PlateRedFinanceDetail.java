package com.zhiwei.credit.model.creditFlow.finance.plateFormFinanceManage;

import java.math.BigDecimal;
import java.util.Date;

public class PlateRedFinanceDetail {

	private Long redTopersonId;//发放红包的id
	private Long redId;//红包表的主键
	private String redName;//红包的名称
	private Long bpCustMemberId;//收红包的人员id
	private String truename;//收红包人员的真实姓名
	private String loginname;//收红包人员的p2p登录名
	private Date registrationDate;//收红包人员的p2p账号注册时间
	private BigDecimal redMoney=new BigDecimal(0);//预计发放红包的金额
	private BigDecimal dredMoney=new BigDecimal(0);//实际发放红包的金额
	private Date distributeTime;//红包到账时间
	private String orderNo;//系统设置的红包编号
	private String requestNo;//第三方支付的对账流水号
	
	public Long getRedTopersonId() {
		return redTopersonId;
	}
	public void setRedTopersonId(Long redTopersonId) {
		this.redTopersonId = redTopersonId;
	}
	public Long getRedId() {
		return redId;
	}
	public void setRedId(Long redId) {
		this.redId = redId;
	}
	public String getRedName() {
		return redName;
	}
	public void setRedName(String redName) {
		this.redName = redName;
	}
	public Long getBpCustMemberId() {
		return bpCustMemberId;
	}
	public void setBpCustMemberId(Long bpCustMemberId) {
		this.bpCustMemberId = bpCustMemberId;
	}
	public String getTruename() {
		return truename;
	}
	public void setTruename(String truename) {
		this.truename = truename;
	}
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public Date getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	public BigDecimal getRedMoney() {
		return redMoney;
	}
	public void setRedMoney(BigDecimal redMoney) {
		this.redMoney = redMoney;
	}
	public BigDecimal getDredMoney() {
		return dredMoney;
	}
	public void setDredMoney(BigDecimal dredMoney) {
		this.dredMoney = dredMoney;
	}
	public Date getDistributeTime() {
		return distributeTime;
	}
	public void setDistributeTime(Date distributeTime) {
		this.distributeTime = distributeTime;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}


	
}
