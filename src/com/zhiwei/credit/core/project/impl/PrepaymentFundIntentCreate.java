package com.zhiwei.credit.core.project.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.credit.model.creditFlow.finance.BpFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.FundIntent;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan;
import com.zhiwei.credit.model.creditFlow.fund.project.BpFundProject;
import com.zhiwei.credit.model.creditFlow.smallLoan.finance.SlEarlyRepaymentRecord;
import com.zhiwei.credit.model.customer.InvestPersonInfo;

public class PrepaymentFundIntentCreate {
/**
 * 确定利率、咨询管理费率、财务服务费率之间的关系
 * 
 * */
	/**
	 * 贷款贷出
	 */
	public static final String ProjectLoadOut = "principalLending"; // 贷款贷出/贷款本金放贷
	/**
	 * 保证金
	 */
	public static final String ProjectRisk = "riskRate"; // 保证金

	/**
	 * 贷款收息
	 */
	public static final String ProjectLoadAccrual = "loanInterest"; // 贷款收息/贷款利息收取
	/**
	 * 管理咨询费用收取
	 */
	public static final String ProjectManagementConsulting = "consultationMoney"; // 管理咨询费用收取
	
	/**
	 * 财务服务费用收取
	 */
	public static final String ProjectFinanceService = "serviceMoney"; // 财务服务费用收取
	
	/**
	 * 余额管理费
	 */
	public static final String ProjectSurplusManage = "surplusManageMoney"; // 随息收取，剩余本金*余额管理费率
	/**
	 * 贷款收本
	 */
	public static final String ProjectLoadRoot = "principalRepayment"; // 贷款收本/贷款本金偿还
	/**
	 * 罚息
	 */
	public static final String ProjectInterestPenalty = "interestPenalty"; //罚息
	/**
	 * 费用退回
	 */
	public static final String projectBackInterest="backInterest";
	public   BigDecimal thirty = new BigDecimal(30); 
	private  static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private String  accrualType;//还款方式
	private String payaccrualType;//还款周期
	private Date earlyDate;//提前还款日期     
	private BigDecimal earlyProjectMoney;//提前还款金额
	private Integer penaltyDays;//罚息天数
	private Date startDate;//气息日期
	private Date intentDate;//还款日期
	private BigDecimal managementConsultingOfRate=new BigDecimal(0);//咨询管理费
	private BigDecimal financeServiceOfRate=new BigDecimal(0);//服务费
	private BigDecimal accrual=new BigDecimal(0);//利率
	private Long investPersonId;//投资人Id
	private String investPersonName;//投资人姓名
	private String fundResource;
	private BigDecimal projectMoney;
	private String orderNo;
	private Integer isPreposePayAccrual;
	private String businessType;
	private Long projectId;
	private String projectName;
	private String projectNumber;
	private String isHaveLin="no";
	private String isccalculateFirstAndEnd;
	private Integer payintentPeriod;//还款期数
	private Date afterDate;
	private Date beforeDate;
	private Integer allPayintentPeriod;
	private String isActualDay;//是否按实际天数
	private String isStartDatePay;//
	private Integer isInterestByOneTime;
	private BigDecimal fundProjectMoney;
	private BigDecimal investMoney;
	private Integer dayOfEveryPeriod;
	private BigDecimal sumAccrual=new BigDecimal(0);
	private BpFundIntent bpFundIntent;
	public PrepaymentFundIntentCreate(BpFundProject project,InvestPersonInfo person,SlEarlyRepaymentRecord slEarlyRepaymentRecord,PlBidPlan plBidPlan,Integer payintentPeriod,Date afterDate,Date beforeDate,BigDecimal principalMoney,BpFundIntent bf){
		accrualType=project.getAccrualtype();
		payaccrualType=project.getPayaccrualType();
		earlyDate=slEarlyRepaymentRecord.getEarlyDate();
		earlyProjectMoney=slEarlyRepaymentRecord.getEarlyProjectMoney();
		penaltyDays=slEarlyRepaymentRecord.getPenaltyDays();
		startDate=plBidPlan.getStartIntentDate();
		intentDate=plBidPlan.getEndIntentDate();
		if(null!=project.getManagementConsultingOfRate()){
			managementConsultingOfRate=project.getManagementConsultingOfRate();
		}
		if(null!=project.getFinanceServiceOfRate()){
			financeServiceOfRate=project.getFinanceServiceOfRate();
		}
		if(null!=project.getAccrual()){
			accrual=project.getAccrual();
		}
		sumAccrual=this.accrual.add( this.managementConsultingOfRate).add( this.financeServiceOfRate);
		
		allPayintentPeriod=project.getPayintentPeriod();
		isPreposePayAccrual=project.getIsPreposePayAccrual();
		businessType=project.getBusinessType();
		projectId=project.getProjectId();
		projectName=project.getProjectName();
		projectNumber=project.getProjectNumber();
		isStartDatePay=project.getIsStartDatePay();
		isInterestByOneTime=project.getIsInterestByOneTime();
		dayOfEveryPeriod=project.getDayOfEveryPeriod();
		this.fundProjectMoney=plBidPlan.getBidMoney();
		this.afterDate=afterDate;
		this.beforeDate=beforeDate;
		this.isccalculateFirstAndEnd=AppUtil.getInterest();  //算头不算尾0 算头又算尾1 AppUtil.getInterest()
		isActualDay=AppUtil.getIsActualDay();
		this.payintentPeriod=payintentPeriod;
		this.bpFundIntent=bf;
		 if(null !=person){
	       this.investPersonId= person.getInvestId();
	       this.investPersonName=person.getInvestPersonName();
	       this.fundResource=person.getFundResource().toString();
	       this.projectMoney=person.getInvestMoney().subtract(principalMoney);  //贷款金额（单个投资人）
	       this.orderNo=person.getOrderNo();
	       this.investMoney=person.getInvestMoney();
       }
	}  
	

   public void create(List list){
	   BpFundIntent bf=calculBpFundIntent(ProjectInterestPenalty,this.earlyDate,earlyDate,intentDate,BigDecimal.valueOf(0),earlyProjectMoney.multiply(this.accrual.divide(BigDecimal.valueOf(3000),5,BigDecimal.ROUND_HALF_UP)).multiply(BigDecimal.valueOf(this.penaltyDays)).multiply(this.investMoney.divide(this.fundProjectMoney,5,BigDecimal.ROUND_HALF_UP)),payintentPeriod);
	   if(bf.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		   list.add(bf);
	   }
	   //提前还款的金额
	   BpFundIntent bf1=calculBpFundIntent(ProjectLoadRoot,this.earlyDate,beforeDate,earlyDate,BigDecimal.valueOf(0),earlyProjectMoney,payintentPeriod);
		if(bf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
			list.add(bf1);
		}
		BigDecimal everybaseMoney=projectMoney.subtract(earlyProjectMoney);
		if(isPreposePayAccrual==0){
		
			// 不管是不是一次性都走一下四行代码 公共代码开始
			if(earlyDate.compareTo(afterDate)!=0){
				//本期提前还款日之前的利息
				thirty=this.getfirstPerioddays(beforeDate, earlyDate);
				createList(list,this.earlyDate,beforeDate,earlyDate,projectMoney,payintentPeriod);
				//本期提起还款日之后的利息
				thirty=this.getfirstPerioddays(earlyDate,afterDate );
				createList(list,afterDate,earlyDate,afterDate,everybaseMoney,payintentPeriod);
			}
		}else{
			BigDecimal days=this.getfirstPerioddays(beforeDate, afterDate);
			BigDecimal allMoney=this.projectMoney.multiply(this.accrual.add(this.managementConsultingOfRate).add(this.financeServiceOfRate)).divide(new BigDecimal(3000),5,BigDecimal.ROUND_HALF_UP).multiply(days);
			BigDecimal money1=this.projectMoney.multiply(this.accrual.add(this.managementConsultingOfRate).add(this.financeServiceOfRate)).divide(new BigDecimal(3000),5,BigDecimal.ROUND_HALF_UP).multiply(this.getfirstPerioddays(beforeDate, earlyDate));
			BigDecimal money2=everybaseMoney.multiply(this.accrual.add(this.managementConsultingOfRate).add(this.financeServiceOfRate)).divide(new BigDecimal(3000),5,BigDecimal.ROUND_HALF_UP).multiply(this.getfirstPerioddays(earlyDate,afterDate ));
			BpFundIntent s=calculBpFundIntent(projectBackInterest,this.earlyDate,beforeDate,afterDate,allMoney.subtract(money1).subtract(money2),BigDecimal.valueOf(0),payintentPeriod);
			if(s.getPayMoney().compareTo(new BigDecimal(0))!=0){
				list.add(s);
			}
		}
	   //等本等息，用得30（*3或*12）*日化利率 即按期算利息(用实际日期没意义，要的就是等本等息。没有有固定日需要日化*实际天数，没有算头算尾加一天)
	  if(accrualType.equals("sameprincipalsameInterest") && payaccrualType.equals("monthPay")){//等本等息，按月
	   		createOfSameprincipalsameInterestAndMonthPay(list);
	   	  }
	     if (accrualType.equals("sameprincipalsameInterest") && payaccrualType.equals("seasonPay")){//等本等息，按季
	    	  createOfSameprincipalsameInterestAndSeasonPay(list);
	     }
	      if (accrualType.equals("sameprincipalsameInterest") && payaccrualType.equals("yearPay")){//等本等息，按年
	    	  createOfSameprincipalsameInterestAndYearPay(list);
	   	 }
	    
	    
	    
	        if (accrualType.equals("sameprincipalsameInterest") && payaccrualType.equals("dayPay")){//等本等息，按日
	   		createOfSameprincipalsameInterestAndDayPay(list);
	     }
	       	if (accrualType.equals("sameprincipalsameInterest") && payaccrualType.equals("owerPay")){//等本等息，按自定义
	   		createOfSameprincipalsameInterestAndOwerPay(list);
	   	}
	   	
	   	//按期收息，到期还本，用得是每期的实际日期（开始日期-结束日期）*日化利率即按实际天数算利息（可以乘30天，算头算尾加一天）
		if (accrualType.equals("singleInterest") && payaccrualType.equals("monthPay")) {//按期收息，按月
			createOfSingleInterestAndMonthPay(list);
	   	}
		if (accrualType.equals("singleInterest") && payaccrualType.equals("seasonPay")) {// 按期收息，按季
			createOfSingleInterestAndSeasonPay(list);
	   	}
			if (accrualType.equals("singleInterest") && payaccrualType.equals("yearPay")) {// 按期收息，按年
			createOfSingleInterestAndYearPay(list);
	   	}
		
		
		
			if (accrualType.equals("singleInterest") && payaccrualType.equals("dayPay")) {// 按期收息，按日
	   		createOfSingleInterestAndDayPay(list);
	   	}
			if (accrualType.equals("singleInterest") && payaccrualType.equals("owerPay")) {// 按期收息，按自定义
			createOfSingleInterestAndOwerPay(list);
	   	}
		
				 //等本本金，用得30*日化利率 即按期算利息,实际天数没意义
		if (accrualType.equals("sameprincipal") && payaccrualType.equals("monthPay")) {// 等额本金，按月
			createOfSameprincipalAndMonthPay(list);
		}
		 //等额本息，用得30*日化利率 即按期算利息，实际天数没意义
		  if (accrualType.equals("sameprincipalandInterest") && payaccrualType.equals("monthPay")) {//等额本息， 按月
   		   createOfSameprincipalandInterestAndMonthPay(list);
    	}
	   
   }
   public void createOfSameprincipalandInterestAndMonthPay(List list){
		try{
			
			BigDecimal everybaseMoney=projectMoney.subtract(earlyProjectMoney.multiply(this.investMoney.divide(this.fundProjectMoney,5,BigDecimal.ROUND_HALF_UP)));
		
				if(earlyDate.compareTo(afterDate)!=0){
					
					BpFundIntent bf1=this.bpFundIntent;
					if(bf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
						list.add(bf1);
					}
					everybaseMoney=everybaseMoney.subtract(bf1.getIncomeMoney());
				}
				Integer period=allPayintentPeriod-payintentPeriod;
				BigDecimal periodtimemoney=periodtimemoney(this.sumAccrual.divide(new BigDecimal(100)),everybaseMoney,period).divide(new BigDecimal(1),5,BigDecimal.ROUND_HALF_UP);
				//公共代码结束
				/**
				 * 日对日   非一次性
				 */
					
				if(isStartDatePay.equals("2") && isInterestByOneTime == 0){
					
					for(int i=payintentPeriod+1;i<=allPayintentPeriod;i++){
						Date edate=DateUtil.addMonthsToDate(startDate, i,-1);
						Date lastintentDate=DateUtil.addMonthsToDate(startDate, i-1,-1);
						this.thirty=new BigDecimal(30);
						createList(list,(isPreposePayAccrual==0?edate:lastintentDate),lastintentDate,edate,everybaseMoney,i);
						BigDecimal money=everybaseMoney.multiply(this.sumAccrual.divide(new BigDecimal(100)));
						BigDecimal bjmoney=periodtimemoney.subtract(money);
						//剩余金额
						BpFundIntent bf1=calculBpFundIntent(ProjectLoadRoot,(isPreposePayAccrual==0?edate:lastintentDate),lastintentDate,edate,BigDecimal.valueOf(0),bjmoney.setScale(5,BigDecimal.ROUND_HALF_UP),i);
						if(bf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
							list.add(bf1);
						}
						everybaseMoney=everybaseMoney.subtract(bjmoney);
					}
					
				}
				
				if(isStartDatePay.equals("1") && isInterestByOneTime==0){
					for(int i=1;i<=allPayintentPeriod-payintentPeriod-1;i++){
						Date edate=DateUtil.addMonthsToDate(afterDate, i);
						Date lastintentDate=DateUtil.addMonthsToDate(afterDate, i-1);
						this.thirty=new BigDecimal(30);
						createList(list,(isPreposePayAccrual==0?edate:lastintentDate),lastintentDate,edate,everybaseMoney,i+payintentPeriod);
						BigDecimal money=everybaseMoney.multiply(this.sumAccrual.divide(new BigDecimal(100)));
						BigDecimal bjmoney=periodtimemoney.subtract(money);
						BpFundIntent bf1=calculBpFundIntent(ProjectLoadRoot,(isPreposePayAccrual==0?edate:lastintentDate),lastintentDate,edate,BigDecimal.valueOf(0),bjmoney.setScale(5,BigDecimal.ROUND_HALF_UP),i);
						if(bf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
							list.add(bf1);
						}
						everybaseMoney=everybaseMoney.subtract(bjmoney);
					}
					Date sdate=DateUtil.addMonthsToDate(afterDate, allPayintentPeriod-payintentPeriod-1);
					thirty=this.getfirstPerioddays(sdate,intentDate );
					createList(list,(isPreposePayAccrual==0?intentDate:sdate),sdate,intentDate,everybaseMoney,allPayintentPeriod);
					BigDecimal comsumDayaccrualMoney=everybaseMoney.multiply(this.sumAccrual.divide(new BigDecimal(100)));
					BigDecimal bjmoney=periodtimemoney.subtract(comsumDayaccrualMoney);
					BpFundIntent bf1=calculBpFundIntent(ProjectLoadRoot,(isPreposePayAccrual==0?intentDate:sdate),sdate,intentDate,BigDecimal.valueOf(0),bjmoney,allPayintentPeriod);
					if(bf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
						list.add(bf1);
					}
					everybaseMoney=everybaseMoney.subtract(bjmoney);
				}
				
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
   public void createOfSameprincipalAndMonthPay(List list){
		try{
			
			BigDecimal everybaseMoney=projectMoney.subtract(earlyProjectMoney.multiply(this.investMoney.divide(this.fundProjectMoney,5,BigDecimal.ROUND_HALF_UP)));
			Integer period=allPayintentPeriod-payintentPeriod;
			if(earlyDate.compareTo(afterDate)!=0){
				period=period+1;
			}
			BigDecimal sameMoney=everybaseMoney.divide(new BigDecimal(period),5,BigDecimal.ROUND_HALF_UP);
			
				// 不管是不是一次性都走一下四行代码 公共代码开始
				if(earlyDate.compareTo(afterDate)!=0){
			
					BpFundIntent bf1=calculBpFundIntent(ProjectLoadRoot,(isPreposePayAccrual==0?afterDate:beforeDate),beforeDate,afterDate,BigDecimal.valueOf(0),sameMoney,payintentPeriod);
					if(bf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
						list.add(bf1);
					}
					everybaseMoney=everybaseMoney.subtract(sameMoney);
				}
				//公共代码结束
				/**
				 * 日对日   非一次性
				 */
					
				if(isStartDatePay.equals("2") && isInterestByOneTime == 0){
					
					for(int i=payintentPeriod+1;i<=allPayintentPeriod;i++){
						Date edate=DateUtil.addMonthsToDate(startDate, i,-1);
						Date lastintentDate=DateUtil.addMonthsToDate(startDate, i-1,-1);
						this.thirty=getActualdays(lastintentDate,edate,0,i);
						createList(list,(isPreposePayAccrual==0?edate:lastintentDate),lastintentDate,edate,everybaseMoney,i);
						//剩余金额
						BpFundIntent bf1=calculBpFundIntent(ProjectLoadRoot,(isPreposePayAccrual==0?edate:lastintentDate),lastintentDate,edate,BigDecimal.valueOf(0),sameMoney,i);
						if(bf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
							list.add(bf1);
						}
						everybaseMoney=everybaseMoney.subtract(sameMoney);
					}
					
				}
				
				if(isStartDatePay.equals("1") && isInterestByOneTime==0){
					for(int i=1;i<=allPayintentPeriod-payintentPeriod-1;i++){
						Date edate=DateUtil.addMonthsToDate(afterDate, i);
						Date lastintentDate=DateUtil.addMonthsToDate(afterDate, i-1);
						thirty=getActualdays(lastintentDate,edate,0,i);
						createList(list,(isPreposePayAccrual==0?edate:lastintentDate),lastintentDate,edate,everybaseMoney,i+payintentPeriod);
						BpFundIntent bf1=calculBpFundIntent(ProjectLoadRoot,(isPreposePayAccrual==0?edate:lastintentDate),lastintentDate,edate,BigDecimal.valueOf(0),sameMoney,i);
						if(bf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
							list.add(bf1);
						}
						everybaseMoney=everybaseMoney.subtract(sameMoney);
					}
					Date sdate=DateUtil.addMonthsToDate(afterDate, allPayintentPeriod-payintentPeriod-1);
					thirty=this.getfirstPerioddays(sdate,intentDate );
					createList(list,(isPreposePayAccrual==0?intentDate:sdate),sdate,intentDate,everybaseMoney,allPayintentPeriod);
					BpFundIntent bf1=calculBpFundIntent(ProjectLoadRoot,(isPreposePayAccrual==0?intentDate:sdate),sdate,intentDate,BigDecimal.valueOf(0),sameMoney,allPayintentPeriod);
					if(bf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
						list.add(bf1);
					}
					everybaseMoney=everybaseMoney.subtract(sameMoney);
				}
				
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
   public void createOfSameprincipalsameInterestAndDayPay(List list){
		try{
			
			BigDecimal everybaseMoney=projectMoney.subtract(earlyProjectMoney.multiply(this.investMoney.divide(this.fundProjectMoney,5,BigDecimal.ROUND_HALF_UP)));
				/**
				 * 日对日   非一次性
				 */
				Integer period=allPayintentPeriod-payintentPeriod;
				if(earlyDate.compareTo(afterDate)!=0){
					period=period+1;
				}
				BigDecimal sameMoney=everybaseMoney.divide(new BigDecimal(period),5,BigDecimal.ROUND_HALF_UP);
				if(isStartDatePay.equals("2") && isInterestByOneTime == 0){
					for(int i=payintentPeriod+1;i<=allPayintentPeriod;i++){
						Date edate=DateUtil.addMonthsToDate(startDate, i,-1);
						Date lastintentDate=DateUtil.addMonthsToDate(startDate, i-1,-1);
						thirty=BigDecimal.valueOf(1);
						createList(list,(isPreposePayAccrual==0?edate:lastintentDate),lastintentDate,edate,everybaseMoney,i);
						BpFundIntent bf1=calculBpFundIntent(ProjectLoadRoot,(isPreposePayAccrual==0?edate:lastintentDate),lastintentDate,edate,BigDecimal.valueOf(0),sameMoney,i);
						if(bf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
							list.add(bf1);
						}
					}
					
				}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
   public void createOfSameprincipalsameInterestAndMonthPay(List list){
		try{
			
			BigDecimal everybaseMoney=projectMoney.subtract(earlyProjectMoney.multiply(this.investMoney.divide(this.fundProjectMoney,5,BigDecimal.ROUND_HALF_UP)));
				Integer period=allPayintentPeriod-payintentPeriod;
				if(earlyDate.compareTo(afterDate)!=0){
					period=period+1;
				}
				BigDecimal sameMoney=everybaseMoney.divide(new BigDecimal(period),5,BigDecimal.ROUND_HALF_UP);
				
				/**
				 * 日对日   非一次性
				 */
				if(isStartDatePay.equals("2") && isInterestByOneTime == 0){
					for(int i=payintentPeriod+1;i<=allPayintentPeriod;i++){
						Date edate=DateUtil.addMonthsToDate(startDate, i,-1);
						Date lastintentDate=DateUtil.addMonthsToDate(startDate, i-1,-1);
						thirty=BigDecimal.valueOf(30);
						createList(list,(isPreposePayAccrual==0?edate:lastintentDate),lastintentDate,edate,everybaseMoney,i);
						BpFundIntent bf1=calculBpFundIntent(ProjectLoadRoot,(isPreposePayAccrual==0?edate:lastintentDate),lastintentDate,edate,BigDecimal.valueOf(0),sameMoney,i);
						if(bf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
							list.add(bf1);
						}
					}
					
				}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

   public void createOfSameprincipalsameInterestAndSeasonPay(List list){
		try{
			
			BigDecimal everybaseMoney=projectMoney.subtract(earlyProjectMoney.multiply(this.investMoney.divide(this.fundProjectMoney,5,BigDecimal.ROUND_HALF_UP)));
				Integer period=allPayintentPeriod-payintentPeriod;
				if(earlyDate.compareTo(afterDate)!=0){
					period=period+1;
				}
				BigDecimal sameMoney=everybaseMoney.divide(new BigDecimal(period),5,BigDecimal.ROUND_HALF_UP);
				
				/**
				 * 日对日   非一次性
				 */
				if(isStartDatePay.equals("2") && isInterestByOneTime == 0){
					for(int i=payintentPeriod+1;i<=allPayintentPeriod;i++){
						Date edate=DateUtil.addMonthsToDate(startDate, i,-1);
						Date lastintentDate=DateUtil.addMonthsToDate(startDate, i-1,-1);
						thirty=BigDecimal.valueOf(90);
						createList(list,(isPreposePayAccrual==0?edate:lastintentDate),lastintentDate,edate,everybaseMoney,i);
						BpFundIntent bf1=calculBpFundIntent(ProjectLoadRoot,(isPreposePayAccrual==0?edate:lastintentDate),lastintentDate,edate,BigDecimal.valueOf(0),sameMoney,i);
						if(bf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
							list.add(bf1);
						}
					}
					
				}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
   public void createOfSameprincipalsameInterestAndYearPay(List list){
		try{
			
			BigDecimal everybaseMoney=projectMoney.subtract(earlyProjectMoney.multiply(this.investMoney.divide(this.fundProjectMoney,5,BigDecimal.ROUND_HALF_UP)));
				Integer period=allPayintentPeriod-payintentPeriod;
				if(earlyDate.compareTo(afterDate)!=0){
					period=period+1;
				}
				BigDecimal sameMoney=everybaseMoney.divide(new BigDecimal(period),5,BigDecimal.ROUND_HALF_UP);
				
				/**
				 * 日对日   非一次性
				 */
				if(isStartDatePay.equals("2") && isInterestByOneTime == 0){
					for(int i=payintentPeriod+1;i<=allPayintentPeriod;i++){
						Date edate=DateUtil.addMonthsToDate(startDate, i,-1);
						Date lastintentDate=DateUtil.addMonthsToDate(startDate, i-1,-1);
						thirty=BigDecimal.valueOf(360);
						createList(list,(isPreposePayAccrual==0?edate:lastintentDate),lastintentDate,edate,everybaseMoney,i);
						BpFundIntent bf1=calculBpFundIntent(ProjectLoadRoot,(isPreposePayAccrual==0?edate:lastintentDate),lastintentDate,edate,BigDecimal.valueOf(0),sameMoney,i);
						if(bf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
							list.add(bf1);
						}
					}
					
				}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
   public void createOfSameprincipalsameInterestAndOwerPay(List list){
		try{
			
			BigDecimal everybaseMoney=projectMoney.subtract(earlyProjectMoney.multiply(this.investMoney.divide(this.fundProjectMoney,5,BigDecimal.ROUND_HALF_UP)));
			Integer period=allPayintentPeriod-payintentPeriod;
			if(earlyDate.compareTo(afterDate)!=0){
				period=period+1;
			}
			BigDecimal sameMoney=everybaseMoney.divide(new BigDecimal(period),5,BigDecimal.ROUND_HALF_UP);
			
			/**
			 * 日对日   非一次性
			 */
			if(isStartDatePay.equals("2") && isInterestByOneTime == 0){
				for(int i=payintentPeriod+1;i<=allPayintentPeriod;i++){
					Date edate=DateUtil.addMonthsToDate(startDate, i,-1);
					Date lastintentDate=DateUtil.addMonthsToDate(startDate, i-1,-1);
					thirty=BigDecimal.valueOf(this.dayOfEveryPeriod);
					createList(list,(isPreposePayAccrual==0?edate:lastintentDate),lastintentDate,edate,everybaseMoney,i);
					BpFundIntent bf1=calculBpFundIntent(ProjectLoadRoot,(isPreposePayAccrual==0?edate:lastintentDate),lastintentDate,edate,BigDecimal.valueOf(0),sameMoney,i);
					if(bf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
						list.add(bf1);
					}
				}
				
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void createOfSingleInterestAndMonthPay(List list){
		try{
			
			BigDecimal everybaseMoney=projectMoney.subtract(earlyProjectMoney.multiply(this.investMoney.divide(this.fundProjectMoney,5,BigDecimal.ROUND_HALF_UP)));
			
			
				/**
				 * 日对日   非一次性
				 */
				if(isStartDatePay.equals("2") && isInterestByOneTime == 0){
					for(int i=payintentPeriod+1;i<=allPayintentPeriod;i++){
						Date edate=DateUtil.addMonthsToDate(startDate, i,-1);
						Date lastintentDate=DateUtil.addMonthsToDate(startDate, i-1,-1);
						thirty=getActualdays(lastintentDate,edate,0,i);
						createList(list,(isPreposePayAccrual==0?edate:lastintentDate),lastintentDate,edate,everybaseMoney,i);
					}
					
				}
				
				if(isStartDatePay.equals("1") && isInterestByOneTime==0){
					for(int i=1;i<=allPayintentPeriod-payintentPeriod-1;i++){
						Date edate=DateUtil.addMonthsToDate(afterDate, i);
						Date lastintentDate=DateUtil.addMonthsToDate(afterDate, i-1);
						thirty=getActualdays(lastintentDate,edate,0,i);
						createList(list,(isPreposePayAccrual==0?edate:lastintentDate),lastintentDate,edate,everybaseMoney,i+payintentPeriod);
					}
					Date sdate=DateUtil.addMonthsToDate(afterDate, allPayintentPeriod-payintentPeriod-1);
					thirty=this.getfirstPerioddays(sdate,intentDate );
					createList(list,(isPreposePayAccrual==0?intentDate:sdate),sdate,intentDate,everybaseMoney,allPayintentPeriod);
				}
				
			//剩余金额
			BpFundIntent bf1=calculBpFundIntent(ProjectLoadRoot,intentDate,startDate,intentDate,BigDecimal.valueOf(0),everybaseMoney,allPayintentPeriod);
			if(bf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
				list.add(bf1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void createOfSingleInterestAndSeasonPay(List list){
		try{
			
			BigDecimal everybaseMoney=projectMoney.subtract(earlyProjectMoney.multiply(this.investMoney.divide(this.fundProjectMoney,5,BigDecimal.ROUND_HALF_UP)));
			
				
				/**
				 * 日对日   非一次性
				 */
				if(isStartDatePay.equals("2") && isInterestByOneTime == 0){
					for(int i=payintentPeriod+1;i<=allPayintentPeriod;i++){
						Date edate=DateUtil.addMonthsToDate(startDate, i*3,-1);
						Date lastintentDate=DateUtil.addMonthsToDate(startDate, (i-1)*3,-1);
						thirty=getActualdays(lastintentDate,edate,0,i);
						createList(list,(isPreposePayAccrual==0?edate:lastintentDate),lastintentDate,edate,everybaseMoney,i);
					}
					
				}
				
				if(isStartDatePay.equals("1") && isInterestByOneTime==0){
					for(int i=1;i<=allPayintentPeriod-payintentPeriod-1;i++){
						Date edate=DateUtil.addMonthsToDate(afterDate, i*3);
						Date lastintentDate=DateUtil.addMonthsToDate(afterDate, (i-1)*3);
						thirty=getActualdays(lastintentDate,edate,0,i);
						createList(list,(isPreposePayAccrual==0?edate:lastintentDate),lastintentDate,edate,everybaseMoney,i+payintentPeriod);
					}
					Date sdate=DateUtil.addMonthsToDate(afterDate, (allPayintentPeriod-payintentPeriod-1)*3);
					thirty=this.getfirstPerioddays(sdate,intentDate );
					createList(list,(isPreposePayAccrual==0?intentDate:sdate),sdate,intentDate,everybaseMoney,allPayintentPeriod);
				}
				
			//剩余金额
			BpFundIntent bf1=calculBpFundIntent(ProjectLoadRoot,intentDate,startDate,intentDate,BigDecimal.valueOf(0),everybaseMoney,allPayintentPeriod);
			if(bf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
				list.add(bf1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void createOfSingleInterestAndYearPay(List list){
		try{
			
			BigDecimal everybaseMoney=projectMoney.subtract(earlyProjectMoney.multiply(this.investMoney.divide(this.fundProjectMoney,5,BigDecimal.ROUND_HALF_UP)));
			
				/**
				 * 日对日   非一次性
				 */
				if(isStartDatePay.equals("2") && isInterestByOneTime == 0){
					for(int i=payintentPeriod+1;i<=allPayintentPeriod;i++){
						Date edate=DateUtil.addMonthsToDate(startDate, i*12,-1);
						Date lastintentDate=DateUtil.addMonthsToDate(startDate, (i-1)*12,-1);
						thirty=getActualdays(lastintentDate,edate,0,i);
						createList(list,(isPreposePayAccrual==0?edate:lastintentDate),lastintentDate,edate,everybaseMoney,i);
					}
					
				}
				
				if(isStartDatePay.equals("1") && isInterestByOneTime==0){
					for(int i=1;i<=allPayintentPeriod-payintentPeriod-1;i++){
						Date edate=DateUtil.addMonthsToDate(afterDate, i*12);
						Date lastintentDate=DateUtil.addMonthsToDate(afterDate, (i-1)*12);
						thirty=getActualdays(lastintentDate,edate,0,i);
						createList(list,(isPreposePayAccrual==0?edate:lastintentDate),lastintentDate,edate,everybaseMoney,i+payintentPeriod);
					}
					Date sdate=DateUtil.addMonthsToDate(afterDate, (allPayintentPeriod-payintentPeriod-1)*12);
					thirty=this.getfirstPerioddays(sdate,intentDate );
					createList(list,(isPreposePayAccrual==0?intentDate:sdate),sdate,intentDate,everybaseMoney,allPayintentPeriod);
				}
			
			//剩余金额
			BpFundIntent bf1=calculBpFundIntent(ProjectLoadRoot,intentDate,startDate,intentDate,BigDecimal.valueOf(0),everybaseMoney,allPayintentPeriod);
			if(bf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
				list.add(bf1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void createOfSingleInterestAndDayPay(List list){
		try{
			
			BigDecimal everybaseMoney=projectMoney.subtract(earlyProjectMoney.multiply(this.investMoney.divide(this.fundProjectMoney,5,BigDecimal.ROUND_HALF_UP)));
			
				/**
				 * 日对日   非一次性
				 */
				if(isStartDatePay.equals("2") && isInterestByOneTime == 0){
					for(int i=payintentPeriod+1;i<=allPayintentPeriod;i++){
						Date edate=DateUtil.addDaysToDate(startDate, i);
						Date lastintentDate=DateUtil.addDaysToDate(startDate, i-1);
						thirty=getActualdays(lastintentDate,edate,0,i);
						createList(list,(isPreposePayAccrual==0?edate:lastintentDate),lastintentDate,edate,everybaseMoney,i);
					}
					
				}
				
			//剩余金额
			BpFundIntent bf1=calculBpFundIntent(ProjectLoadRoot,intentDate,startDate,intentDate,BigDecimal.valueOf(0),everybaseMoney,allPayintentPeriod);
			if(bf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
				list.add(bf1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void createOfSingleInterestAndOwerPay(List list){
		try{
			BigDecimal everybaseMoney=projectMoney.subtract(earlyProjectMoney.multiply(this.investMoney.divide(this.fundProjectMoney,5,BigDecimal.ROUND_HALF_UP)));
			
				/**
				 * 日对日   非一次性
				 */
				if(isStartDatePay.equals("2") && isInterestByOneTime == 0){
					for(int i=payintentPeriod+1;i<=allPayintentPeriod;i++){
						Date edate=DateUtil.addDaysToDate(startDate, i*dayOfEveryPeriod);
						Date lastintentDate=DateUtil.addDaysToDate(startDate, (i-1)*dayOfEveryPeriod);
						thirty=getActualdays(lastintentDate,edate,0,i);
						createList(list,(isPreposePayAccrual==0?edate:lastintentDate),lastintentDate,edate,everybaseMoney,i);
					}
					
				}
				
			//剩余金额
			BpFundIntent bf1=calculBpFundIntent(ProjectLoadRoot,intentDate,startDate,intentDate,BigDecimal.valueOf(0),everybaseMoney,allPayintentPeriod);
			if(bf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
				list.add(bf1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void createList(List list,Date intentdate,Date lastintentDate,Date nextintentDate,BigDecimal everybaseMoney,Integer payintentPeriod){
		BpFundIntent sf1 = calculBpFundIntent(ProjectLoadAccrual,intentdate,lastintentDate,nextintentDate,BigDecimal.valueOf(0),everybaseMoney.multiply(this.accrual.multiply(thirty).divide(BigDecimal.valueOf(3000),10,BigDecimal.ROUND_HALF_UP)).setScale(2,BigDecimal.ROUND_HALF_UP),payintentPeriod) ;
		BpFundIntent sf2 = calculBpFundIntent(ProjectManagementConsulting,intentdate,lastintentDate,nextintentDate,BigDecimal.valueOf(0), everybaseMoney.multiply(this.managementConsultingOfRate.multiply(thirty).divide(BigDecimal.valueOf(3000),10,BigDecimal.ROUND_HALF_UP)).setScale(2,BigDecimal.ROUND_HALF_UP),payintentPeriod);
		BpFundIntent sf3 = calculBpFundIntent(ProjectFinanceService,intentdate,lastintentDate,nextintentDate,BigDecimal.valueOf(0), everybaseMoney.multiply(this.financeServiceOfRate.multiply(thirty).divide(BigDecimal.valueOf(3000),10,BigDecimal.ROUND_HALF_UP)).setScale(2,BigDecimal.ROUND_HALF_UP),payintentPeriod);
		if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
			list.add(sf1);
		}
		if(sf2.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
			list.add(sf2);
		}
		if(sf3.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
			list.add(sf3);
		}
	}
    public  BigDecimal periodtimemoney(BigDecimal accrual,BigDecimal projectMoney,int period) {
		
		BigDecimal   periodtimemoney=new  BigDecimal("0");
		  periodtimemoney=projectMoney.multiply(accrual).multiply(mnfang(accrual.add(new BigDecimal(1)),period)).divide((mnfang(accrual.add(new BigDecimal(1)),period).subtract(new BigDecimal(1))),100,BigDecimal.ROUND_HALF_UP);
			return periodtimemoney;
	}
    public  BigDecimal mnfang(BigDecimal m,int n){
		BigDecimal s=m;
		for(int i=1;i<n;i++){
			s=s.multiply(m);
			
		}
		return s;
	}
	public  FundIntent calculfundintent(String fundType,Date intentdate,Date lastintentDate,Date nextintentDate,BigDecimal paymoney,BigDecimal incomemoeny,int payintentPeriod){
		FundIntent sf1=new FundIntent();
		sf1.setFundType(fundType);// 资金类型
		sf1.setIntentDate(intentdate);
		sf1.setPayMoney(paymoney); // 支出金额
		sf1.setIncomeMoney(incomemoeny); // 收入金额
		sf1.setAfterMoney(new BigDecimal("0"));
		sf1.setFlatMoney(new BigDecimal("0"));
		sf1.setAccrualMoney(new BigDecimal("0"));
		sf1.setNotMoney(paymoney.compareTo(new BigDecimal("0"))==0?incomemoeny:paymoney);
		sf1.setRemark("");
		sf1.setBusinessType(businessType);
		sf1.setInvestPersonId(this.investPersonId);
		sf1.setInvestPersonName(this.investPersonName);
		sf1.setPayintentPeriod(payintentPeriod);
		sf1.setFundResource(this.fundResource);
		sf1.setOrderNo(this.orderNo);
		sf1.setProjectId(projectId);
		sf1.setProjectName(projectName);
		sf1.setProjectNumber(projectNumber);
		 Date interestStarTime=null;
		    Date interestEndTime=null;
		    if(!fundType.equals("principalLending")){
		    	if(payintentPeriod==0){
			    	isHaveLin="yes";
			    }
			    if(this.isccalculateFirstAndEnd.equals("0")){
			    	if(this.isPreposePayAccrual==0){
			    		interestStarTime=lastintentDate;
			    	    interestEndTime=DateUtil.addDaysToDate(intentdate,-1);
			    	}else{
			    		interestStarTime=intentdate;
			    		interestEndTime=DateUtil.addDaysToDate(nextintentDate,-1);
			    	}
			    	if(fundType.equals("principalRepayment")&& accrualType.equals("singleInterest")){
				    	interestStarTime=startDate;
				    	interestEndTime=DateUtil.addDaysToDate(intentDate,-1);;
				    }
			    }else{
			    	if(this.isPreposePayAccrual==0){
			    		if(intentdate.equals(startDate) || (isHaveLin.equals("no")&&payintentPeriod==1)|| (isHaveLin.equals("yes")&&payintentPeriod==0)){
			    			interestStarTime=lastintentDate;  //第一期的时候不减加一天
			    		}else{
			    		   interestStarTime=DateUtil.addDaysToDate(lastintentDate,1);
			    		}
			    		  interestEndTime=intentdate;
				    }else{
			    		if(intentdate.equals(startDate)|| (isHaveLin.equals("no")&&payintentPeriod==1)|| (isHaveLin.equals("yes")&&payintentPeriod==0)){
			    			interestStarTime=intentdate;  //第一期的时候不加一天
			    		}else{
			    		   interestStarTime=DateUtil.addDaysToDate(intentdate,1);
			    		}
			    		interestEndTime=nextintentDate;
				    	}
			    	
			    	if(fundType.equals("principalRepayment")&& accrualType.equals("singleInterest")){
				    	interestStarTime=startDate;
				    	interestEndTime=intentDate;
				    }
			    	
			    }
			    
			    sf1.setInterestStarTime(interestStarTime);
				sf1.setInterestEndTime(interestEndTime);
		    }
			return  sf1;
	}
	public  BpFundIntent calculBpFundIntent(String fundType,Date intentdate,Date lastintentDate,Date nextintentDate,BigDecimal paymoney,BigDecimal incomemoeny,int payintentPeriod){
		BpFundIntent sf1=new BpFundIntent();
		sf1.setFundType(fundType);// 资金类型
		sf1.setIntentDate(intentdate);
		sf1.setPayMoney(paymoney); // 支出金额
		sf1.setIncomeMoney(incomemoeny); // 收入金额
		sf1.setAfterMoney(new BigDecimal("0"));
		sf1.setFlatMoney(new BigDecimal("0"));
		sf1.setAccrualMoney(new BigDecimal("0"));
		sf1.setNotMoney(paymoney.compareTo(new BigDecimal("0"))==0?incomemoeny:paymoney);
		sf1.setRemark("");
		sf1.setBusinessType(businessType);
		sf1.setInvestPersonId(this.investPersonId);
		sf1.setInvestPersonName(this.investPersonName);
		sf1.setPayintentPeriod(payintentPeriod);
		sf1.setFundResource(this.fundResource);
		sf1.setProjectId(projectId);
		sf1.setProjectName(projectName);
		sf1.setProjectNumber(projectNumber);
		sf1.setOrderNo(this.orderNo);
		
	    Date interestStarTime=null;
	    Date interestEndTime=null;
	    if(!fundType.equals("principalLending")){
	    	if(payintentPeriod==0){
		    	isHaveLin="yes";
		    }
		    if(this.isccalculateFirstAndEnd.equals("0")){
		    	if(this.isPreposePayAccrual==0){
		    		interestStarTime=lastintentDate;
		    	    interestEndTime=DateUtil.addDaysToDate(nextintentDate,-1);
		    	}else{
		    		interestStarTime=intentdate;
		    		interestEndTime=DateUtil.addDaysToDate(nextintentDate,-1);
		    	}
		    	if(fundType.equals("principalRepayment")&& accrualType.equals("singleInterest")){
			    	interestStarTime=startDate;
			    	interestEndTime=DateUtil.addDaysToDate(intentDate,-1);;
			    }
		    }else{
		    	if(this.isPreposePayAccrual==0){
		    		if(intentdate.equals(startDate) || (isHaveLin.equals("no")&&payintentPeriod==1)|| (isHaveLin.equals("yes")&&payintentPeriod==0)){
		    			interestStarTime=lastintentDate;  //第一期的时候不减加一天
		    		}else{
		    		   interestStarTime=DateUtil.addDaysToDate(lastintentDate,1);
		    		}
		    		  interestEndTime=intentdate;
			    }else{
		    		if(intentdate.equals(startDate)|| (isHaveLin.equals("no")&&payintentPeriod==1)|| (isHaveLin.equals("yes")&&payintentPeriod==0)){
		    			interestStarTime=intentdate;  //第一期的时候不加一天
		    		}else{
		    		   interestStarTime=DateUtil.addDaysToDate(intentdate,1);
		    		}
		    		interestEndTime=nextintentDate;
			    	}
		    	
		    	if(fundType.equals("principalRepayment")&& accrualType.equals("singleInterest")){
			    	interestStarTime=startDate;
			    	interestEndTime=intentDate;
			    }
		    	
		    }
		    
		    sf1.setInterestStarTime(interestStarTime);
			sf1.setInterestEndTime(interestEndTime);
	    }
		return  sf1;
	}


	
	public  SlFundIntent calculslfundintent(String fundType,Date intentdate,Date lastintentDate,Date nextintentDate,BigDecimal paymoney,BigDecimal incomemoeny,int payintentPeriod){
		SlFundIntent sf1=new SlFundIntent();
		sf1.setFundType(fundType);// 资金类型
		sf1.setIntentDate(intentdate);
		sf1.setPayMoney(paymoney); // 支出金额
		sf1.setIncomeMoney(incomemoeny); // 收入金额
		sf1.setAfterMoney(new BigDecimal("0"));
		sf1.setFlatMoney(new BigDecimal("0"));
		sf1.setAccrualMoney(new BigDecimal("0"));
		sf1.setNotMoney(paymoney.compareTo(new BigDecimal("0"))==0?incomemoeny:paymoney);
		sf1.setRemark("");
		sf1.setBusinessType(businessType);
		sf1.setInvestPersonId(this.investPersonId);
		sf1.setInvestPersonName(this.investPersonName);
		sf1.setPayintentPeriod(payintentPeriod);
		sf1.setFundResource(this.fundResource);
		sf1.setProjectId(projectId);
		sf1.setProjectName(projectName);
		sf1.setProjectNumber(projectNumber);
	    Date interestStarTime=null;
	    Date interestEndTime=null;
	    
	    if(!fundType.equals("principalLending")){
	    	if(payintentPeriod==0){
		    	isHaveLin="yes";
		    }
		    if(this.isccalculateFirstAndEnd.equals("0")){
		    	if(this.isPreposePayAccrual==0){
		    		interestStarTime=lastintentDate;
		    	    interestEndTime=DateUtil.addDaysToDate(intentdate,-1);
		    	}else{
		    		interestStarTime=intentdate;
		    		interestEndTime=DateUtil.addDaysToDate(nextintentDate,-1);
		    	}
		    	if(fundType.equals("principalRepayment")&& accrualType.equals("singleInterest")){
			    	interestStarTime=startDate;
			    	interestEndTime=DateUtil.addDaysToDate(intentDate,-1);;
			    }
		    }else{
		    	if(this.isPreposePayAccrual==0){
		    		if(intentdate.equals(startDate) || (isHaveLin.equals("no")&&payintentPeriod==1)|| (isHaveLin.equals("yes")&&payintentPeriod==0)){
		    			interestStarTime=lastintentDate;  //第一期的时候不减加一天
		    		}else{
		    		   interestStarTime=DateUtil.addDaysToDate(lastintentDate,1);
		    		}
		    		  interestEndTime=intentdate;
			    }else{
		    		if(intentdate.equals(startDate)|| (isHaveLin.equals("no")&&payintentPeriod==1)|| (isHaveLin.equals("yes")&&payintentPeriod==0)){
		    			interestStarTime=intentdate;  //第一期的时候不加一天
		    		}else{
		    		   interestStarTime=DateUtil.addDaysToDate(intentdate,1);
		    		}
		    		interestEndTime=nextintentDate;
			    	}
		    	
		    	if(fundType.equals("principalRepayment")&& accrualType.equals("singleInterest")){
			    	interestStarTime=startDate;
			    	interestEndTime=intentDate;
			    }
		    	
		    }
		    
		    sf1.setInterestStarTime(interestStarTime);
			sf1.setInterestEndTime(interestEndTime);
	    }
		
		return  sf1;
	}

	//单利，循环体里面计算天数（其中对日有算头算尾)
	public BigDecimal getActualdays(Date startactualdate,Date endctualdate,int n,int payintentPeriod){
		BigDecimal actualdays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(startactualdate), sdf.format(endctualdate))).toString());
		if("no".equals(isActualDay)){
			actualdays=thirty.multiply(new BigDecimal(n));
		}else{
			if(this.isccalculateFirstAndEnd.equals("1")){ //对日，会走这里
				if(startactualdate.equals(startDate)){
					actualdays=actualdays.add(new BigDecimal(1));
				}
				
			}
		}
		
		return actualdays;
	}
	//单利（日，自定义）首期的天数(都有算头算尾)
	public BigDecimal getDayfirstPerioddays(BigDecimal actualdays,int payintentPeriod ){
		
		if(this.isccalculateFirstAndEnd.equals("1")){ //对日，会走这里
			if(payintentPeriod==1){
				actualdays=actualdays.add(new BigDecimal(1));
			}
			
		}
		return actualdays;
	}
	//固定日（单利，等额本金，等额本息）首期的天数(都有算头算尾)
	public BigDecimal getfirstPerioddays(Date startactualdate,Date endctualdate){
		
		BigDecimal actualdays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(startactualdate), sdf.format(endctualdate))).toString());
		if(this.isccalculateFirstAndEnd.equals("1")){ //对日，会走这里
			if(startactualdate.equals(startDate)){
				actualdays=actualdays.add(new BigDecimal(1));
			}
			
		}
		return actualdays;
	}
}
