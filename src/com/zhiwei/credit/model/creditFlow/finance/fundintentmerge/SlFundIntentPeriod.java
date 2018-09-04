package com.zhiwei.credit.model.creditFlow.finance.fundintentmerge;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


import com.google.gson.annotations.Expose;
import com.zhiwei.credit.model.creditFlow.finance.BpFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.FundIntent;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.fundintentmerge.annotation.FundType;

public  class SlFundIntentPeriod extends FundIntentPeriod {
//	public final static Comparator comparator= new FundIntentPeriodComparator();//用来给期数排序
	
	public SlFundIntentPeriod(){
		
	}
	
	protected String ids;
	
	
	protected Long fundProjectId;
	protected Long planId;
	protected String bidPlanName;
	protected String bidPlanProjectNum;
	protected Long borrowId;//借款人Id
	protected String borrowName;//借款人姓名
	protected Long ptpborrowId;//招标项目借款人P2P账号表主键Id
	protected String ptpborrowName;//招标项目借款人p2p账号表登陆名
	private String receiverName;//表示收款人姓名：债权标表示原始债权人姓名，直投标表示借款人姓名
	private Long receiverP2PId;//表示收款人P2P表主键Id：债权标表示原始债权人P2P表主键Id，直投标表示借款人P2P表主键Id
	private String receiverP2PName;//表示收款人P2P表主键Id：债权标表示原始债权人P2P表主键Id，直投标表示借款人P2P表主键Id
	protected Short repaySource;
	private Short authorizationStatus;//是否授权，1表示已经授权，null表示没有授权或者取消了授权
	public String rebateType;//返利类型
	public String rebateWay;//返利方式
	public String fundType;//资金类型
	public String raiseRate;
	public BigDecimal incomeMoney;
	public BigDecimal afterMoney;//已对账金额
	public Integer payintentPeriod;//期数
	public String payintentPeriodShow;//期数
	public Date intentDate;
	public Date factDate;
	public String rebateTypeName;
	public String rebateWayName;
	public String fundTypeName;
	public Date bidtime;
	public Date startTime;
	public String days;
	public BigDecimal couponsIncome;
	public BigDecimal addRateIncome;
	public BigDecimal sumIncome;
	public BigDecimal notIncome;
	//设置初始值的原因，如果principalRepayment为null那么gson中 principalRepayment的属性不会显示，所以出示一个都为null的飞空对象，通过isNull属性来判断是否为新加对象
	//也可以通过principalRepayment==SlFundIntent.slFundIntentNull；来判断
	@FundType(name="principalRepayment",prior=FundType.priorLevel.One)@Expose
	public SlFundIntent principalRepayment = new SlFundIntent();//本金偿还
	
	@FundType(name="loanInterest",prior=FundType.priorLevel.Two)@Expose
	public SlFundIntent loanInterest = new SlFundIntent();//利息
	
	@FundType(name="consultationMoney",prior=FundType.priorLevel.Three)@Expose
	public SlFundIntent consultationMoney = new SlFundIntent();//咨询服务费
	
	@FundType(name="principalLending",prior=FundType.priorLevel.None)@Expose
	public SlFundIntent principalLending = new SlFundIntent();//本金放款 1条记录
	
	@FundType(name="serviceMoney",prior=FundType.priorLevel.Four)@Expose
	public SlFundIntent serviceMoney = new SlFundIntent();
	
	@FundType(name="interestPenalty",prior=FundType.priorLevel.Five)@Expose
	public BpFundIntent interestPenalty = new BpFundIntent();
	/**
	 * 全部未还金额
	 */
	public BigDecimal allTotalMoney;
	/**
	 * 还款状态
	 */
	public Integer loanerRepayMentStatus;
	/**
	 * 标的金额
	 */
	private String bidMoney;
	/**
	 * 年利率
	 */
	private String yearInterestRate;
	/**
	 * 借款结束时间
	 */
	private Date endIntentDate;
	/**
	 * 借款总期数
	 */
	private Integer planPeriod;
	/**
	 * 借款分期方式   日，月，年
	 */
	private String payaccrualType;

	/**
	 * 每期天数（只针对自定义）
	 */
	private Integer dayOfEveryPeriod;
	/**
	 * 用户类型
	 */
	private String oppositeType;
	/**
	 * 投资人数
	 */
	private Integer investNum;

	/**
	 * 下一期还款时间
	 */
	private Date nestPayBankDate;
	/**
	 * 本金
	 */
	private BigDecimal principal;

	/**
	 * 利息
	 */
	private BigDecimal interest;

	/**
	 * 管理费
	 */
	private BigDecimal manageMoney;

	/**
	 * 借款期限
	 */
	private String term;

	public BigDecimal getAllTotalMoney() {
		return allTotalMoney;
	}

	public void setAllTotalMoney(BigDecimal allTotalMoney) {
		this.allTotalMoney = allTotalMoney;
	}

	public void initSlFundIntentPeriod(List<BpFundIntent> bpfundlist){
		this.ids="";
		principalRepayment.setIncomeMoney(new BigDecimal("0"));
		principalRepayment.setAfterMoney(new BigDecimal("0"));
		principalRepayment.setAccrualMoney(new BigDecimal("0"));
		
		loanInterest.setIncomeMoney(new BigDecimal("0"));
		loanInterest.setAfterMoney(new BigDecimal("0"));
		loanInterest.setAccrualMoney(new BigDecimal("0"));
		
		consultationMoney.setIncomeMoney(new BigDecimal("0"));
		consultationMoney.setAfterMoney(new BigDecimal("0"));
		consultationMoney.setAccrualMoney(new BigDecimal("0"));
		
		serviceMoney.setIncomeMoney(new BigDecimal("0"));
		serviceMoney.setAfterMoney(new BigDecimal("0"));
		serviceMoney.setAccrualMoney(new BigDecimal("0"));
		
		interestPenalty.setIncomeMoney(new BigDecimal("0"));
		interestPenalty.setAfterMoney(new BigDecimal("0"));
		interestPenalty.setAccrualMoney(new BigDecimal("0"));
		int i=0;
		while(i<bpfundlist.size()){
			BpFundIntent fp=bpfundlist.get(i);
			if(fp.getFundType().equals("principalRepayment")){
				principalRepayment.setIncomeMoney(principalRepayment.getIncomeMoney().add(fp.getIncomeMoney()));
				if(principalRepayment.getAfterMoney()!=null&&fp.getAfterMoney()!=null){
					
					principalRepayment.setAfterMoney(principalRepayment.getAfterMoney().add(fp.getAfterMoney()));
				}
				principalRepayment.setAccrualMoney(principalRepayment.getAccrualMoney().add(fp.getAccrualMoney()));
			}else if(fp.getFundType().equals("loanInterest")){
				if(loanInterest.getAfterMoney()!=null&&fp.getAfterMoney()!=null){
					loanInterest.setAfterMoney(loanInterest.getAfterMoney().add(fp.getAfterMoney()));
				}
				loanInterest.setIncomeMoney(loanInterest.getIncomeMoney().add(fp.getIncomeMoney()));
				loanInterest.setAccrualMoney(loanInterest.getAccrualMoney().add(fp.getAccrualMoney()));
			}else if(fp.getFundType().equals("consultationMoney")){
				consultationMoney.setIncomeMoney(consultationMoney.getIncomeMoney().add(fp.getIncomeMoney()));
				if(consultationMoney.getAfterMoney()!=null&&fp.getAfterMoney()!=null){
					consultationMoney.setAfterMoney(consultationMoney.getAfterMoney().add(fp.getAfterMoney()));
				}
				consultationMoney.setAccrualMoney(consultationMoney.getAccrualMoney().add(fp.getAccrualMoney()));
			}else if(fp.getFundType().equals("serviceMoney")){
				serviceMoney.setIncomeMoney(serviceMoney.getIncomeMoney().add(fp.getIncomeMoney()));
			if(serviceMoney.getAfterMoney()!=null&&fp.getAfterMoney()!=null){
				
				serviceMoney.setAfterMoney(serviceMoney.getAfterMoney().add(fp.getAfterMoney()));
			}
				serviceMoney.setAccrualMoney(serviceMoney.getAccrualMoney().add(fp.getAccrualMoney()));
			}else if(fp.getFundType().equals("interestPenalty")){
				interestPenalty.setIncomeMoney(interestPenalty.getIncomeMoney().add(fp.getIncomeMoney()));
				if(interestPenalty.getAfterMoney()!=null&&fp.getAfterMoney()!=null){
					
					interestPenalty.setAfterMoney(interestPenalty.getAfterMoney().add(fp.getAfterMoney()));
				}
				interestPenalty.setAccrualMoney(interestPenalty.getAccrualMoney().add(fp.getAccrualMoney()));
			}
			i++;
			//初始化ids
			 if(!this.ids.contains(fp.getOrderNo())){
			    	
				 this.ids=this.ids+","+fp.getOrderNo();
		    }
		}
	//	System.out.println(ids);
	//	System.out.println(ids.length());
		if(ids.length()>0){
			this.ids=this.ids.substring(1,this.ids.length());
		}
		
		
		
	}
	
	public void couponsSlFundIntentPeriod(List<BpFundIntent> bpfundlist){
		this.ids="";
		principalRepayment.setIncomeMoney(new BigDecimal("0"));
		principalRepayment.setAfterMoney(new BigDecimal("0"));
		principalRepayment.setAccrualMoney(new BigDecimal("0"));
		
		loanInterest.setIncomeMoney(new BigDecimal("0"));
		loanInterest.setAfterMoney(new BigDecimal("0"));
		loanInterest.setAccrualMoney(new BigDecimal("0"));
		
		int i=0;
		while(i<bpfundlist.size()){
			BpFundIntent fp=bpfundlist.get(i);
			if(fp.getFundType().equals("principalRepayment")){
				principalRepayment.setIncomeMoney(principalRepayment.getIncomeMoney().add(fp.getIncomeMoney()));
				principalRepayment.setAfterMoney(principalRepayment.getAfterMoney().add(fp.getAfterMoney()));
				principalRepayment.setAccrualMoney(principalRepayment.getAccrualMoney().add(fp.getAccrualMoney()));
			}else if(fp.getFundType().equals("loanInterest")){
				loanInterest.setIncomeMoney(loanInterest.getIncomeMoney().add(fp.getIncomeMoney()));
				loanInterest.setAfterMoney(loanInterest.getAfterMoney().add(fp.getAfterMoney()));
				loanInterest.setAccrualMoney(loanInterest.getAccrualMoney().add(fp.getAccrualMoney()));
			}
			i++;
			//初始化ids
			 if(!this.ids.contains(fp.getOrderNo())){
			    	
				 this.ids=this.ids+","+fp.getOrderNo();
		    }
		}
		
		
		this.ids=this.ids.substring(1,this.ids.length());
		
		
	}
	
	public BigDecimal getAfterMoney() {
		return afterMoney;
	}

	public void setAfterMoney(BigDecimal afterMoney) {
		this.afterMoney = afterMoney;
	}

	public BigDecimal getCouponsIncome() {
		return couponsIncome;
	}

	public void setCouponsIncome(BigDecimal couponsIncome) {
		this.couponsIncome = couponsIncome;
	}

	public BigDecimal getAddRateIncome() {
		return addRateIncome;
	}

	public void setAddRateIncome(BigDecimal addRateIncome) {
		this.addRateIncome = addRateIncome;
	}

	public BigDecimal getSumIncome() {
		return sumIncome;
	}

	public void setSumIncome(BigDecimal sumIncome) {
		this.sumIncome = sumIncome;
	}

	public BigDecimal getNotIncome() {
		return notIncome;
	}

	public void setNotIncome(BigDecimal notIncome) {
		this.notIncome = notIncome;
	}

	public Date getBidtime() {
		return bidtime;
	}

	public void setBidtime(Date bidtime) {
		this.bidtime = bidtime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public String getRaiseRate() {
		return raiseRate;
	}

	public void setRaiseRate(String raiseRate) {
		this.raiseRate = raiseRate;
	}

	public Integer getPayintentPeriod() {
		return payintentPeriod;
	}

	public void setPayintentPeriod(Integer payintentPeriod) {
		this.payintentPeriod = payintentPeriod;
	}

	public Date getIntentDate() {
		return intentDate;
	}

	public void setIntentDate(Date intentDate) {
		this.intentDate = intentDate;
	}

	public Date getFactDate() {
		return factDate;
	}

	public void setFactDate(Date factDate) {
		this.factDate = factDate;
	}

	public String getRebateTypeName() {
		return rebateTypeName;
	}

	public void setRebateTypeName(String rebateTypeName) {
		this.rebateTypeName = rebateTypeName;
	}

	public String getRebateWayName() {
		return rebateWayName;
	}

	public void setRebateWayName(String rebateWayName) {
		this.rebateWayName = rebateWayName;
	}

	public String getFundTypeName() {
		return fundTypeName;
	}

	public void setFundTypeName(String fundTypeName) {
		this.fundTypeName = fundTypeName;
	}

	public String getRebateType() {
		return rebateType;
	}

	public void setRebateType(String rebateType) {
		this.rebateType = rebateType;
	}

	public String getRebateWay() {
		return rebateWay;
	}

	public void setRebateWay(String rebateWay) {
		this.rebateWay = rebateWay;
	}

	public String getFundType() {
		return fundType;
	}

	public void setFundType(String fundType) {
		this.fundType = fundType;
	}

	public BigDecimal getIncomeMoney() {
		return incomeMoney;
	}

	public void setIncomeMoney(BigDecimal incomeMoney) {
		this.incomeMoney = incomeMoney;
	}

	public Short getRepaySource() {
		return repaySource;
	}




	public void setRepaySource(Short repaySource) {
		this.repaySource = repaySource;
	}




	public Long getFundProjectId() {
		return fundProjectId;
	}

	public void setFundProjectId(Long fundProjectId) {
		this.fundProjectId = fundProjectId;
	}


	public BigDecimal getManageMoney() {
		return manageMoney;
	}

	public void setManageMoney(BigDecimal manageMoney) {
		this.manageMoney = manageMoney;
	}

	public Long getPlanId() {
		return planId;
	}

	public void setPlanId(Long planId) {
		this.planId = planId;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getBidPlanName() {
		return bidPlanName;
	}

	public void setBidPlanName(String bidPlanName) {
		this.bidPlanName = bidPlanName;
	}

	public String getBidPlanProjectNum() {
		return bidPlanProjectNum;
	}

	public void setBidPlanProjectNum(String bidPlanProjectNum) {
		this.bidPlanProjectNum = bidPlanProjectNum;
	}

	public SlFundIntent getPrincipalRepayment() {
		return principalRepayment;
	}

	public void setPrincipalRepayment(SlFundIntent principalRepayment) {
		this.principalRepayment = principalRepayment;
	}

	public SlFundIntent getLoanInterest() {
		return loanInterest;
	}

	public void setLoanInterest(SlFundIntent loanInterest) {
		this.loanInterest = loanInterest;
	}

	public SlFundIntent getConsultationMoney() {
		return consultationMoney;
	}

	public void setConsultationMoney(SlFundIntent consultationMoney) {
		this.consultationMoney = consultationMoney;
	}

	public SlFundIntent getPrincipalLending() {
		return principalLending;
	}

	public void setPrincipalLending(SlFundIntent principalLending) {
		this.principalLending = principalLending;
	}

	public SlFundIntent getServiceMoney() {
		return serviceMoney;
	}

	public void setServiceMoney(SlFundIntent serviceMoney) {
		this.serviceMoney = serviceMoney;
	}




	public Long getBorrowId() {
		return borrowId;
	}




	public void setBorrowId(Long borrowId) {
		this.borrowId = borrowId;
	}




	public String getBorrowName() {
		return borrowName;
	}




	public void setBorrowName(String borrowName) {
		this.borrowName = borrowName;
	}




	public Long getPtpborrowId() {
		return ptpborrowId;
	}




	public void setPtpborrowId(Long ptpborrowId) {
		this.ptpborrowId = ptpborrowId;
	}




	public String getPtpborrowName() {
		return ptpborrowName;
	}




	public void setPtpborrowName(String ptpborrowName) {
		this.ptpborrowName = ptpborrowName;
	}




	public BpFundIntent getInterestPenalty() {
		return interestPenalty;
	}




	public void setInterestPenalty(BpFundIntent interestPenalty) {
		this.interestPenalty = interestPenalty;
	}




	public Short getAuthorizationStatus() {
		return authorizationStatus;
	}




	public void setAuthorizationStatus(Short authorizationStatus) {
		this.authorizationStatus = authorizationStatus;
	}




	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}




	public String getReceiverName() {
		return receiverName;
	}




	public void setReceiverP2PId(Long receiverP2PId) {
		this.receiverP2PId = receiverP2PId;
	}




	public Long getReceiverP2PId() {
		return receiverP2PId;
	}




	public void setReceiverP2PName(String receiverP2PName) {
		this.receiverP2PName = receiverP2PName;
	}




	public String getReceiverP2PName() {
		return receiverP2PName;
	}

	public Integer getLoanerRepayMentStatus() {
		return loanerRepayMentStatus;
	}

	public void setLoanerRepayMentStatus(Integer loanerRepayMentStatus) {
		this.loanerRepayMentStatus = loanerRepayMentStatus;
	}

	public String getPayintentPeriodShow() {
		return payintentPeriodShow;
	}

	public void setPayintentPeriodShow(String payintentPeriodShow) {
		this.payintentPeriodShow = payintentPeriodShow;
	}

	public String getBidMoney() {
		return bidMoney;
	}

	public void setBidMoney(String bidMoney) {
		this.bidMoney = bidMoney;
	}

	public String getYearInterestRate() {
		return yearInterestRate;
	}

	public void setYearInterestRate(String yearInterestRate) {
		this.yearInterestRate = yearInterestRate;
	}

	public Date getEndIntentDate() {
		return endIntentDate;
	}

	public void setEndIntentDate(Date endIntentDate) {
		this.endIntentDate = endIntentDate;
	}

	public Integer getPlanPeriod() {
		return planPeriod;
	}

	public void setPlanPeriod(Integer planPeriod) {
		this.planPeriod = planPeriod;
	}

	public String getPayaccrualType() {
		return payaccrualType;
	}

	public void setPayaccrualType(String payaccrualType) {
		this.payaccrualType = payaccrualType;
	}

	public Integer getDayOfEveryPeriod() {
		return dayOfEveryPeriod;
	}

	public void setDayOfEveryPeriod(Integer dayOfEveryPeriod) {
		this.dayOfEveryPeriod = dayOfEveryPeriod;
	}

	@Override
	public String getOppositeType() {
		return oppositeType;
	}

	@Override
	public void setOppositeType(String oppositeType) {
		this.oppositeType = oppositeType;
	}

	public Integer getInvestNum() {
		return investNum;
	}

	public void setInvestNum(Integer investNum) {
		this.investNum = investNum;
	}

	public Date getNestPayBankDate() {
		return nestPayBankDate;
	}

	public void setNestPayBankDate(Date nestPayBankDate) {
		this.nestPayBankDate = nestPayBankDate;
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

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}
}
