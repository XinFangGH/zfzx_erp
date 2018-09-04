package com.zhiwei.credit.core.project.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.credit.core.project.FundIntentListPro3;
import com.zhiwei.credit.model.creditFlow.finance.BpFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.FundIntent;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan;
import com.zhiwei.credit.model.creditFlow.fund.project.BpFundProject;
import com.zhiwei.credit.model.creditFlow.smallLoan.supervise.SlSuperviseRecord;
import com.zhiwei.credit.model.customer.InvestPersonInfo;
import com.zhiwei.credit.service.creditFlow.finance.BpFundIntentService;
import com.zhiwei.credit.service.creditFlow.fund.project.BpFundProjectService;

public class SuperviseFundIntentCreate {
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
	public static final BigDecimal thirty = new BigDecimal(30); 
	private  static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	private String isHaveLending;
	private String isActualDay;
	private String businessType;
	private String fundIntentType;
	private String isccalculateFirstAndEnd;
	private String orderNo;
	
	
	private    Integer isPreposePayAccrual;
	private    Integer isInterestByOneTime;
	private    Integer payintentPeriod;
    private    BigDecimal projectMoney;
	private    BigDecimal accrual;
	private    double riskAccrual=0;
	private    BigDecimal managementConsultingOfRate;
	private    BigDecimal financeServiceOfRate;
	
	private    BigDecimal dayaccrual;
	private    BigDecimal daymanagementConsultingOfRate;
	private    BigDecimal dayfinanceServiceOfRate;
	private    BigDecimal sumDayaccrual;
	private    BigDecimal surplusManageOfRate;
	
	private    String accrualType;
	private    String payaccrualType;
	private    String date1;
	private    String date2;
	private    Date startDate;
	private    Date intentDate;
	
	private    Date  payintentPerioDateDate;
	
	
	private    String isStartDatePay;
	private    Integer dayOfEveryPeriod;
	private    String payintentPerioDate;
	
	private    String isHaveLin;
	
	private    Long investPersonId;
	private    String investPersonName;
	private    String fundResource;
	
	
	private    Date startDatecla;  //为了算投资人月末的日期
	
	private Long projectId;
	private String projectName;
	private String projectNumber;
	private Long preceptId;
	
	public SuperviseFundIntentCreate(Object prtoject1,String isHaveLending){
		
			SlSuperviseRecord project=(SlSuperviseRecord)prtoject1;
		   this.projectId= project.getProjectId();
		   SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
	       this.isActualDay=AppUtil.getIsActualDay();
	       this.isccalculateFirstAndEnd=AppUtil.getInterest();  //算头不算尾0 算头又算尾1 AppUtil.getInterest()
	       
	       this.accrualType=project.getAccrualtype(); 
	       this.payaccrualType=project.getPayaccrualType(); 
	       this.projectMoney=project.getContinuationMoney();
	       this.intentDate=project.getEndDate();   
	       this.date2=sd.format(project.getEndDate());   
	       this.date1=sd.format(project.getStartDate());  
	       this.startDate=project.getStartDate();   
	       
	      
	      
	       this.isStartDatePay=project.getIsStartDatePay();
	       this.isInterestByOneTime = project.getIsInterestByOneTime();
	       
	       this.accrual=project.getContinuationRate().divide(new BigDecimal(100));
	       this.dayaccrual=project.getContinuationRate().divide(new BigDecimal(3000),100,BigDecimal.ROUND_UP);//日化
	       if(null !=project.getManagementConsultingOfRate()){ 
	    	   this.managementConsultingOfRate=project.getManagementConsultingOfRate().divide(new BigDecimal(100));
	    	   this.daymanagementConsultingOfRate=project.getManagementConsultingOfRate().divide(new BigDecimal(3000),100,BigDecimal.ROUND_UP); 
	       }else{
	    	   this.managementConsultingOfRate=new BigDecimal(0);
	    	   this.daymanagementConsultingOfRate=new BigDecimal(0);
	       }
	       if(null !=project.getFinanceServiceOfRate()){ 
	    	   this.financeServiceOfRate=project.getFinanceServiceOfRate().divide(new BigDecimal(100));
	    	   this.dayfinanceServiceOfRate=project.getFinanceServiceOfRate().divide(new BigDecimal(3000),100,BigDecimal.ROUND_UP);;
	       }else{
	    	   this.financeServiceOfRate=new BigDecimal(0);
	    	   this.dayfinanceServiceOfRate=new BigDecimal(0);
	       }
	      
	   //    this.sumDayaccrual=this.dayaccrual.add( this.daymanagementConsultingOfRate).add( this.dayfinanceServiceOfRate);
	       this.sumDayaccrual=this.dayaccrual;
	       this.isPreposePayAccrual=project.getIsPreposePayAccrualsupervise();
	       this.payintentPeriod=project.getPayintentPeriod();
	       this.dayOfEveryPeriod=project.getDayOfEveryPeriod();
	       this.payintentPerioDate=String.valueOf(project.getPayintentPerioDate());
	       this.isHaveLending=isHaveLending;
	       
	      this.isHaveLin="no";
		   	List<SlFundIntent> list = new ArrayList<SlFundIntent>();
			Date startDate1=startDate;
			String[] arr=date1.split("-");
			if(null!=isStartDatePay && isStartDatePay.equals("1")){
				try {
					startDate1=sd.parse(arr[0]+"-"+arr[1]+"-"+payintentPerioDate);
					
					this.startDatecla= DateUtil.addMonthsToDate(DateUtil.addDaysToDate(this.intentDate, 1),0-payintentPeriod);
					Date date1max=sd.parse(DateUtil.getMaxMonthDate(date1));
			/*		if(startDate1.compareTo(this.startDatecla)>0){
						
						startDate1=	date1max;
					
					}*/
					if(!sd.format(startDate1).split("-")[1].equals(arr[1])){
						startDate1=	date1max;
						
					}
					
					
			//		this.startDatecla= DateUtil.addMonthsToDate(DateUtil.addDaysToDate(this.intentDate, 1),payintentPeriod);
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				
				startDate1=DateUtil.addDaysToDate(startDate, -1);
			}
		    
				this.payintentPerioDateDate=startDate1;
		//		System.out.print(this.payintentPerioDateDate);
			
	}  
	
	public  List<SlFundIntent> createBorrowerSlFundIntent(){
	
    	this.fundIntentType="SlFundIntent";
    	this.businessType="SmallLoan";
    	List list = new ArrayList();
       //贷款本金放贷
    	if (null ==this.isHaveLending ||this.isHaveLending.equals("yes")) { //像提前还款什么的都是不需要生成放款记录
    		SlFundIntent sf=calculslfundintent(ProjectLoadOut,startDate,null,null,projectMoney,new BigDecimal("0"),0);
			 list.add(sf);
		} 
    	
    	create(list);
    	return list;
		
	}
    public  List<FundIntent> createInvesterFundIntent(){
		
    	this.fundIntentType="FundIntent";
    	this.businessType="SlSmallloanProject";
    	
    	List<FundIntent> list = new ArrayList<FundIntent>();
       //贷款本金放贷
         FundIntent   sf = new FundIntent();   
    	if (null ==this.isHaveLending ||this.isHaveLending.equals("yes")) { //像提前还款什么的都是不需要生成放款记录
    		sf=calculfundintent(ProjectLoadOut,startDate,null,null,projectMoney,new BigDecimal("0"),0);
			list.add(sf);
		} 
    	
    	create(list);
    	
    	if(accrualType.equals("singleInterest")&&null!=isStartDatePay && isStartDatePay.equals("1")){
		try {
			//如果借款人的借款日期是1号的话，也就是说不管结束日期是30或31号，并说明是30或31号，而是在月末，别的也有这个问题
				if(sdf.format(this.startDatecla).split("-")[2].equals("01")){
					for(FundIntent l:list){
					if(l.getFundType().equals(ProjectLoadAccrual)){
						
							l.setIntentDate(sdf.parse(DateUtil.getMaxMonthDate(sdf.format(l.getIntentDate()))));
						
						
					}
					}
				}
				//如果借款人的借款日期是月末的话，那每期的还款日期都是月末的前一天
				if(this.startDatecla.equals(sdf.parse(DateUtil.getMaxMonthDate(sdf.format(this.startDatecla))))){
					for(FundIntent l:list){
					if(l.getFundType().equals(ProjectLoadAccrual)){
						try {
							l.setIntentDate(DateUtil.addDaysToDate(sdf.parse(DateUtil.getMaxMonthDate(sdf.format(l.getIntentDate()))),-1));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					}
				}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    	}
    	return list;
		
	}
public  List<BpFundIntent> createInvesterBpFundIntent(){
		
    	this.fundIntentType="BpFundIntent";
    	this.businessType="SlSmallloanProject";
    	
    	List<BpFundIntent> list = new ArrayList<BpFundIntent>();
       //贷款本金放贷
    	BpFundIntent   sf = new BpFundIntent();   
    	if (null ==this.isHaveLending ||this.isHaveLending.equals("yes")) { //像提前还款什么的都是不需要生成放款记录
    		sf=calculBpFundIntent(ProjectLoadOut,startDate,null,null,projectMoney,new BigDecimal("0"),0);
			list.add(sf);
		} 
    	
    	create(list);
    	
    	if(accrualType.equals("singleInterest")&&null!=isStartDatePay && isStartDatePay.equals("1")){
		try {
			//如果借款人的借款日期是1号的话，也就是说不管结束日期是30或31号，并说明是30或31号，而是在月末，别的也有这个问题
				if(sdf.format(this.startDatecla).split("-")[2].equals("01")){
					for(FundIntent l:list){
					if(l.getFundType().equals(ProjectLoadAccrual)){
						
							l.setIntentDate(sdf.parse(DateUtil.getMaxMonthDate(sdf.format(l.getIntentDate()))));
						
						
					}
					}
				}
				//如果借款人的借款日期是月末的话，那每期的还款日期都是月末的前一天
				if(this.startDatecla.equals(sdf.parse(DateUtil.getMaxMonthDate(sdf.format(this.startDatecla))))){
					for(FundIntent l:list){
					if(l.getFundType().equals(ProjectLoadAccrual)){
						try {
							l.setIntentDate(DateUtil.addDaysToDate(sdf.parse(DateUtil.getMaxMonthDate(sdf.format(l.getIntentDate()))),-1));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					}
				}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    	}
    	return list;
		
	}
   public void create(List list){
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
//觉得等本等息，等额本息，等额本金，一次性付息，前置付息没啥意义
   public  void createOfSameprincipalsameInterestAndSeasonPay(List list){
   	BigDecimal surplusManagMoney=projectMoney;
	BigDecimal sameprojectMoney=projectMoney.divide(new BigDecimal(payintentPeriod),2,BigDecimal.ROUND_UP);
	Date lastintentDate = startDate;
	Date nextintentDate = null;
	if (isStartDatePay.equals("2") && isInterestByOneTime == 0) {// 按实际放款日对日还款//不一次性支付利息
	
		for (int i = 1; i <= payintentPeriod; i++) {
			Date intentDate = null;
			if(isPreposePayAccrual == 1){//前置付息
    			   intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, (i - 1)*3), -1);
    			   if(i==1){
    				   intentDate=startDate;
    			   }
    		   }else{//后置付息
    			   intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate,  i*3), -1);
    		   }
			BigDecimal everybaseMoney= projectMoney;
			BigDecimal  principalRepaymentMoney=sameprojectMoney;
			BigDecimal actualdays=thirty.multiply(new BigDecimal("3"));
			nextintentDate = DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate,  i*3), -1);
			everyPeriodFundintent(list,intentDate,lastintentDate,nextintentDate,principalRepaymentMoney,everybaseMoney,actualdays, i,surplusManagMoney);
			surplusManagMoney=surplusManagMoney.subtract(principalRepaymentMoney);
			lastintentDate=intentDate;
		}
	}
	if (isStartDatePay.equals("2") && isInterestByOneTime == 1) {// 按实际放款日对日还款//一次性收取全部利息
			Date intentDate = null;
			if (isPreposePayAccrual == 1) {// 前置付息
				 intentDate=startDate;
			} else {// 后置付息
				intentDate = this.intentDate;
			}
			BigDecimal everybaseMoney= projectMoney.multiply(new BigDecimal(payintentPeriod));
			BigDecimal  principalRepaymentMoney=sameprojectMoney;
			BigDecimal actualdays=thirty.multiply(new BigDecimal("3"));
			nextintentDate = this.intentDate;
			everyPeriodFundintent(list,intentDate,lastintentDate,nextintentDate,projectMoney,everybaseMoney,actualdays, 1,surplusManagMoney);
			surplusManagMoney=surplusManagMoney.subtract(principalRepaymentMoney);
			lastintentDate=intentDate;
		}
   }
  public  void createOfSameprincipalsameInterestAndYearPay(List list){
	   	BigDecimal surplusManagMoney=projectMoney;
	   	Date lastintentDate = startDate;
	   	Date nextintentDate = null;
		BigDecimal sameprojectMoney=projectMoney.divide(new BigDecimal(payintentPeriod),2,BigDecimal.ROUND_UP);
		
		if (isStartDatePay.equals("2") && isInterestByOneTime == 0) {// 按实际放款日对日还款//不一次性支付利息
		
			for (int i = 1; i <= payintentPeriod; i++) {
				Date intentDate = null;
				if(isPreposePayAccrual == 1){//前置付息
	    			   intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, (i - 1)*12), -1);
	    			   if(i==1){
	    				   intentDate=startDate;
	    			   }
	    		   }else{//后置付息
	    			   intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate,  i*12), -1);
	    		   }
				BigDecimal everybaseMoney= projectMoney;
				BigDecimal  principalRepaymentMoney=sameprojectMoney;
				BigDecimal actualdays=thirty.multiply(new BigDecimal("12"));
				nextintentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate,  i*12), -1);
				everyPeriodFundintent(list,intentDate,lastintentDate,nextintentDate,principalRepaymentMoney,everybaseMoney,actualdays, i,surplusManagMoney);
				surplusManagMoney=surplusManagMoney.subtract(principalRepaymentMoney);
				lastintentDate=intentDate;
				
			}
		}
		if (isStartDatePay.equals("2") && isInterestByOneTime == 1) {// 按实际放款日对日还款//一次性收取全部利息
				Date intentDate = null;
				if (isPreposePayAccrual == 1) {// 前置付息
					 intentDate=startDate;
				} else {// 后置付息
					intentDate = this.intentDate;
				}
				BigDecimal everybaseMoney= projectMoney.multiply(new BigDecimal(payintentPeriod));
				BigDecimal  principalRepaymentMoney=sameprojectMoney;
				BigDecimal actualdays=thirty.multiply(new BigDecimal("12"));
				nextintentDate=this.intentDate;
				everyPeriodFundintent(list,intentDate,lastintentDate,nextintentDate,projectMoney,everybaseMoney,actualdays, 1,surplusManagMoney);
				surplusManagMoney=surplusManagMoney.subtract(principalRepaymentMoney);
				lastintentDate=intentDate;
			}
	   }
  public  void createOfSameprincipalsameInterestAndDayPay(List list){
	  	BigDecimal surplusManagMoney=projectMoney;
	  	Date lastintentDate = startDate;
	  	Date nextintentDate = null;
		BigDecimal sameprojectMoney=projectMoney.divide(new BigDecimal(payintentPeriod),2,BigDecimal.ROUND_UP);
		
		if ( isInterestByOneTime == 0) {// 按实际放款日对日还款//不一次性支付利息
		
			for (int i = 1; i <= payintentPeriod; i++) {
				Date intentDate = null;
				if (isPreposePayAccrual == 1) {// 前置付息
					  intentDate=DateUtil.addDaysToDate(startDate, (i-1)*1);
					if(i==1){
	    				   intentDate=startDate;
	    			   }
				} else {// 后置付息
					  intentDate=DateUtil.addDaysToDate(startDate, i*1);
				}
				BigDecimal everybaseMoney= projectMoney;
				BigDecimal  principalRepaymentMoney=sameprojectMoney;
				BigDecimal actualdays=new BigDecimal(1);
				nextintentDate = DateUtil.addDaysToDate(startDate, i*1);
				everyPeriodFundintent(list,intentDate,lastintentDate,nextintentDate,principalRepaymentMoney,everybaseMoney,actualdays, i,surplusManagMoney);
				surplusManagMoney=surplusManagMoney.subtract(principalRepaymentMoney);
				lastintentDate=intentDate;
			}
		}
		if (isInterestByOneTime == 1) {// 按实际放款日对日还款//一次性收取全部利息
				Date intentDate = null;
				if (isPreposePayAccrual == 1) {// 前置付息
					 intentDate=startDate;
				} else {// 后置付息
					intentDate = this.intentDate;
				}
				BigDecimal everybaseMoney= projectMoney.multiply(new BigDecimal(payintentPeriod));
				BigDecimal  principalRepaymentMoney=sameprojectMoney;
				BigDecimal actualdays=new BigDecimal(1);
				nextintentDate = this.intentDate;
				everyPeriodFundintent(list,intentDate,lastintentDate,nextintentDate,projectMoney,everybaseMoney,actualdays,1,surplusManagMoney);
				surplusManagMoney=surplusManagMoney.subtract(principalRepaymentMoney);
				lastintentDate=intentDate;
			}
       }
	 
  
     public  void createOfSameprincipalsameInterestAndOwerPay(List list){
	  	BigDecimal surplusManagMoney=projectMoney;
	  	Date lastintentDate = startDate; Date nextintentDate = null;
		BigDecimal sameprojectMoney=projectMoney.divide(new BigDecimal(payintentPeriod),2,BigDecimal.ROUND_UP);
		
		if (isStartDatePay.equals("2") && isInterestByOneTime == 0) {// 按实际放款日对日还款//不一次性支付利息
	
		for (int i = 1; i <= payintentPeriod; i++) {
			Date intentDate = null;
			if (isPreposePayAccrual == 1) {// 前置付息
				  intentDate=DateUtil.addDaysToDate(startDate, (i-1)*dayOfEveryPeriod);
				if(i==1){
    				   intentDate=startDate;
    			   }
			} else {// 后置付息
				  intentDate=DateUtil.addDaysToDate(startDate, i*dayOfEveryPeriod);
			}
			BigDecimal everybaseMoney= projectMoney;
			BigDecimal  principalRepaymentMoney=sameprojectMoney;
			BigDecimal actualdays=new BigDecimal(dayOfEveryPeriod);
			nextintentDate =DateUtil.addDaysToDate(startDate, i*dayOfEveryPeriod);
			everyPeriodFundintent(list,intentDate,lastintentDate,nextintentDate,principalRepaymentMoney,everybaseMoney,actualdays, i,surplusManagMoney);
			surplusManagMoney=surplusManagMoney.subtract(principalRepaymentMoney);
			lastintentDate=intentDate;
		}
	}
	if (isStartDatePay.equals("2") && isInterestByOneTime == 1) {// 按实际放款日对日还款//一次性收取全部利息
			Date intentDate = null;
			if (isPreposePayAccrual == 1) {// 前置付息
				 intentDate=startDate;
			} else {// 后置付息
				intentDate = this.intentDate;
			}
			BigDecimal everybaseMoney= projectMoney.multiply(new BigDecimal(payintentPeriod));
			BigDecimal  principalRepaymentMoney=sameprojectMoney;
			BigDecimal actualdays=new BigDecimal(dayOfEveryPeriod);
			nextintentDate = this.intentDate;
			everyPeriodFundintent(list,intentDate,lastintentDate,nextintentDate,projectMoney,everybaseMoney,actualdays,1,surplusManagMoney);
			surplusManagMoney=surplusManagMoney.subtract(principalRepaymentMoney);
			lastintentDate=intentDate;
		}
}

	public  void createOfSameprincipalsameInterestAndMonthPay(List list){
    	BigDecimal surplusManagMoney=projectMoney;
    	Date lastintentDate = startDate; Date nextintentDate = null;
    	BigDecimal sameprojectMoney=projectMoney.divide(new BigDecimal(payintentPeriod),2,BigDecimal.ROUND_UP);
    	
    	if (isStartDatePay.equals("1") && isInterestByOneTime == 0) { // 固定在某日
		    String[] arr=date1.split("-");
			if(Long.valueOf(payintentPerioDate).compareTo(Long.valueOf(arr[2]))<0){
				Date sDate=DateUtil.addMonthsToDate(startDate, 1);
				String dateStr=sdf.format(sDate);
				String[] dateArr=dateStr.split("-");
				try {
					payintentPerioDateDate=sdf.parse(dateArr[0]+"-"+dateArr[1]+"-"+payintentPerioDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			if(Long.valueOf(payintentPerioDate).compareTo(Long.valueOf(arr[2]))!=0){
 			BigDecimal firstPerioddays=getfirstPerioddays(startDate,payintentPerioDateDate);
 			Date intentDate = null;
			if (isPreposePayAccrual == 1) {// 前置付息
				intentDate = this.startDate;
			} else {// 后置付息
				intentDate = payintentPerioDateDate;
			}
			BigDecimal principalRepaymentMoney = projectMoney.divide(new BigDecimal(payintentPeriod),2,BigDecimal.ROUND_HALF_UP);
			BigDecimal everybaseMoney = projectMoney;
			nextintentDate =payintentPerioDateDate;
			everyPeriodFundintent(list,intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, everybaseMoney, firstPerioddays,0,projectMoney);
			lastintentDate=intentDate;
			for (int i = 1; i < payintentPeriod; i++) {
				if (isPreposePayAccrual == 1) {// 前置付息
					intentDate = DateUtil.addMonthsToDate(payintentPerioDateDate, i-1);
				} else {// 后置付息
					intentDate = DateUtil.addMonthsToDate(payintentPerioDateDate, i);
				}
 				Date startactualdate=DateUtil.addMonthsToDate(payintentPerioDateDate, i-1);
				Date endctualdate=DateUtil.addMonthsToDate(payintentPerioDateDate, i);
//				BigDecimal actualdays=getActualdays(startactualdate,endctualdate,1,i);
				BigDecimal actualdays=thirty;
				principalRepaymentMoney = projectMoney.divide(new BigDecimal(payintentPeriod),2,BigDecimal.ROUND_HALF_UP);
			    everybaseMoney = projectMoney;
			    nextintentDate =DateUtil.addMonthsToDate(payintentPerioDateDate, i);
				everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, everybaseMoney,actualdays,i,projectMoney);
				lastintentDate=intentDate;
			}
			
		  Date adate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"),payintentPeriod), -1);
		   Date bdate=DateUtil.addMonthsToDate(payintentPerioDateDate,payintentPeriod-1);
 			if (isPreposePayAccrual == 1) {// 前置付息
				intentDate =bdate;
			} else {// 后置付息
				intentDate = this.intentDate;
			}
 			BigDecimal endPerioddays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(bdate), sdf.format(adate))).toString());
 			
			 principalRepaymentMoney =new BigDecimal("0");
		    everybaseMoney = projectMoney;
		    nextintentDate =adate;
 			everyPeriodFundintent(list,intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, everybaseMoney, endPerioddays,payintentPeriod,everybaseMoney);
 			lastintentDate=intentDate;
 			
 			 principalRepaymentMoney =projectMoney;
			 everybaseMoney =new BigDecimal("0");
 			everyPeriodFundintent(list, this.intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, everybaseMoney, endPerioddays,payintentPeriod,new BigDecimal("0"));
 			lastintentDate=intentDate;
			 }
	   }else if (isStartDatePay.equals("2") && isInterestByOneTime == 0) {// 按实际放款日对日还款//不一次性支付利息
		
			for (int i = 1; i <= payintentPeriod; i++) {
				Date intentDate = null;
/*				if (isPreposePayAccrual == 1) {// 前置付息
					intentDate = DateUtil.addMonthsToDate(payintentPerioDateDate, i - 1);
					if(i==1){
	    				   intentDate=startDate;
	    			   }
				} else {// 后置付息
					intentDate = DateUtil.addMonthsToDate(payintentPerioDateDate, i);
				}*/
				if(isPreposePayAccrual == 1){//前置付息
	    			   intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, i-1), -1);
	    			   if(i==1){
	    				   intentDate=startDate;
	    			   }
	    		   }else{//后置付息
	    			   intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, i), -1);
	    		   }
				BigDecimal everybaseMoney= projectMoney;
				BigDecimal  principalRepaymentMoney=sameprojectMoney;
				BigDecimal actualdays=thirty;
				nextintentDate = DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, i), -1);
				everyPeriodFundintent(list,intentDate,lastintentDate,nextintentDate,principalRepaymentMoney,everybaseMoney,actualdays, i,surplusManagMoney);
				surplusManagMoney=surplusManagMoney.subtract(principalRepaymentMoney);
				lastintentDate=intentDate;
			}
		} else if (isStartDatePay.equals("2") && isInterestByOneTime == 1) {// 按实际放款日对日还款//一次性收取全部利息
				Date intentDate = null;
				if (isPreposePayAccrual == 1) {// 前置付息
					 intentDate=startDate;
				} else {// 后置付息
					intentDate = this.intentDate;
				}
				BigDecimal everybaseMoney= projectMoney.multiply(new BigDecimal(payintentPeriod));
				BigDecimal  principalRepaymentMoney=sameprojectMoney;
				BigDecimal actualdays=thirty;
				nextintentDate = this.intentDate;
				everyPeriodFundintent(list,intentDate,lastintentDate,nextintentDate,projectMoney,everybaseMoney,actualdays, 1,surplusManagMoney);
				surplusManagMoney=surplusManagMoney.subtract(principalRepaymentMoney);
				lastintentDate=intentDate;
			}
	}
	
	
	  
	  
	  
	  
	  
	  //单利
    public  void createOfSingleInterestAndSeasonPay(List list){
    	Date lastintentDate = startDate; Date nextintentDate = null;
		if (isStartDatePay.equals("1") && isInterestByOneTime == 0) { // 固定在某日
			    String[] arr=date1.split("-");
				if(Long.valueOf(payintentPerioDate).compareTo(Long.valueOf(arr[2]))<0){
					Date sDate=DateUtil.addMonthsToDate(startDate, 1);
					String dateStr=sdf.format(sDate);
					String[] dateArr=dateStr.split("-");
					try {
						payintentPerioDateDate=sdf.parse(dateArr[0]+"-"+dateArr[1]+"-"+payintentPerioDate);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				BigDecimal firstPerioddays=getfirstPerioddays(startDate,payintentPerioDateDate);
	 			Date intentDate = null;
				if (isPreposePayAccrual == 1) {// 前置付息
					intentDate = this.startDate;
				} else {// 后置付息
					intentDate = payintentPerioDateDate;
				}
				BigDecimal principalRepaymentMoney = new BigDecimal("0");
				BigDecimal everybaseMoney = projectMoney;
				nextintentDate =payintentPerioDateDate;
				everyPeriodFundintent(list,intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, everybaseMoney, firstPerioddays,0,projectMoney);
				lastintentDate=intentDate;
 			for (int i = 1; i < payintentPeriod; i++) {
 				if (isPreposePayAccrual == 1) {// 前置付息
					intentDate = DateUtil.addMonthsToDate(payintentPerioDateDate, (i-1)*3);
				} else {// 后置付息
					intentDate = DateUtil.addMonthsToDate(payintentPerioDateDate, i*3);
				}
 				Date startactualdate=DateUtil.addMonthsToDate(payintentPerioDateDate, (i-1)*3);
				Date endctualdate=DateUtil.addMonthsToDate(payintentPerioDateDate, i*3);
				BigDecimal actualdays=getActualdays(startactualdate,endctualdate,3,i);
				principalRepaymentMoney = new BigDecimal("0");
			    everybaseMoney = projectMoney;
			    nextintentDate = DateUtil.addMonthsToDate(payintentPerioDateDate, i*3);
				everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, everybaseMoney,actualdays,i,projectMoney);
				lastintentDate=nextintentDate;	
 			}
 			
 			   Date adate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"),payintentPeriod*3), -1);
			   Date bdate=DateUtil.addMonthsToDate(payintentPerioDateDate,(payintentPeriod-1)*3);
	 			if (isPreposePayAccrual == 1) {// 前置付息
					intentDate =bdate;
				} else {// 后置付息
					intentDate = adate;
				}
	 			BigDecimal endPerioddays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(bdate), sdf.format(adate))).toString());
	 			
				 principalRepaymentMoney =new BigDecimal("0");
			    everybaseMoney = projectMoney;
			    nextintentDate =adate;
	 			everyPeriodFundintent(list,intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, everybaseMoney, endPerioddays,payintentPeriod,everybaseMoney);
	 			lastintentDate=intentDate;
	 			
	 			 principalRepaymentMoney =projectMoney;
				 everybaseMoney =new BigDecimal("0");
	 			everyPeriodFundintent(list, this.intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, everybaseMoney, endPerioddays,payintentPeriod,new BigDecimal("0"));
	 			lastintentDate=intentDate;
			
	
		   }
			
			if (isStartDatePay.equals("2") && isInterestByOneTime == 0) { // 对日  ///不一次性支付利息


 				for (int i = 1; i <= payintentPeriod; i++) {
	   		 
	   			  Date startactualdate=DateUtil.addMonthsToDate(startDate, (i-1)*3);
	   			  if(i>1){
	   				  startactualdate=DateUtil.addDaysToDate(startactualdate, -1);
	   			  }
	   			  Date endctualdate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, i*3), -1);
	   			BigDecimal actualdays=getActualdays(startactualdate,endctualdate,3,i);
	   			  Date intentDate=null;
	   			  if(isPreposePayAccrual==1){
	   				  intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, (i-1)*3), -1);
	   				  if(i==1){
	   					  intentDate=startDate;
	   				  }
	   			  }else if(isPreposePayAccrual==0){
	   				  intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, i*3), -1);
	   			  }
	   			BigDecimal principalRepaymentMoney = new BigDecimal("0");
				BigDecimal everybaseMoney = projectMoney;
				 nextintentDate =DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, i*3), -1);
				everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, everybaseMoney, actualdays,i,projectMoney);
				lastintentDate=intentDate;
				 }
 				everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,projectMoney, new BigDecimal("0"), new BigDecimal("0"),payintentPeriod,new BigDecimal("0"));
 				lastintentDate=intentDate;
			}
			if (isInterestByOneTime == 1) { // 按实际放款日对日还款//一次性收取全部利息
				 Date startactualdate=startDate;
				 Date endctualdate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, payintentPeriod*3), -1);
				 BigDecimal actualdays=getActualdays(startactualdate,endctualdate,payintentPeriod*3,1);
				Date intentDate = null;
				if (isPreposePayAccrual == 1) {// 前置付息
					 intentDate=startDate;
				} else {// 后置付息
					intentDate = this.intentDate;
				}
				BigDecimal principalRepaymentMoney = new BigDecimal("0");
				BigDecimal everybaseMoney= projectMoney;
				 nextintentDate =this.intentDate;
				everyPeriodFundintent(list,intentDate,lastintentDate,nextintentDate,principalRepaymentMoney,everybaseMoney,actualdays, 1,projectMoney);
				lastintentDate=intentDate;
				
				everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,projectMoney, new BigDecimal("0"), new BigDecimal("0"),payintentPeriod,new BigDecimal("0"));
				lastintentDate=intentDate;
			}
    	
    }
      public  void createOfSingleInterestAndMonthPay(List list){
    	  Date lastintentDate = startDate; Date nextintentDate = null;
		if (isStartDatePay.equals("1") && isInterestByOneTime == 0) { // 固定在某日
			    String[] arr=date1.split("-");
				if(Long.valueOf(payintentPerioDate).compareTo(Long.valueOf(arr[2]))<0){
					Date sDate=DateUtil.addMonthsToDate(startDate, 1);
					String dateStr=sdf.format(sDate);
					String[] dateArr=dateStr.split("-");
					try {
						payintentPerioDateDate=sdf.parse(dateArr[0]+"-"+dateArr[1]+"-"+payintentPerioDate);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}else{
					payintentPerioDateDate=startDate;
				}
				
					
	 			BigDecimal firstPerioddays=getfirstPerioddays(startDate,payintentPerioDateDate);
	 			Date intentDate = null;
				if (isPreposePayAccrual == 1) {// 前置付息
					intentDate = this.startDate;
				} else {// 后置付息
					intentDate = payintentPerioDateDate;
				}
				BigDecimal principalRepaymentMoney = new BigDecimal("0");
				BigDecimal everybaseMoney = projectMoney;
				 nextintentDate =payintentPerioDateDate;
				everyPeriodFundintent(list,intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, everybaseMoney, firstPerioddays,0,projectMoney);
				lastintentDate=intentDate;
				for (int i = 1; i < payintentPeriod; i++) {
 				if (isPreposePayAccrual == 1) {// 前置付息
						intentDate = DateUtil.addMonthsToDate(payintentPerioDateDate, i-1);
					} else {// 后置付息
						intentDate = DateUtil.addMonthsToDate(payintentPerioDateDate, i);
					}
	 				Date startactualdate=DateUtil.addMonthsToDate(payintentPerioDateDate, i-1);
					Date endctualdate=DateUtil.addMonthsToDate(payintentPerioDateDate, i);
//					BigDecimal actualdays=getActualdays(startactualdate,endctualdate,1,i);
					BigDecimal actualdays=thirty;
					principalRepaymentMoney = new BigDecimal("0");
				    everybaseMoney = projectMoney;
				    nextintentDate =DateUtil.addMonthsToDate(payintentPerioDateDate, i);
					everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, everybaseMoney,actualdays,i,projectMoney);
					lastintentDate=nextintentDate;
				}
 			
 			   Date adate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"),payintentPeriod), -1);
			   Date bdate=lastintentDate;
	 			if (isPreposePayAccrual == 1) {// 前置付息
					intentDate =bdate;
				} else {// 后置付息
					intentDate = adate;
				}
	 			BigDecimal endPerioddays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(bdate), sdf.format(adate))).toString());
	 			
				 principalRepaymentMoney =new BigDecimal("0");
			    everybaseMoney = projectMoney;
			    nextintentDate =adate;
	 			everyPeriodFundintent(list,intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, everybaseMoney, endPerioddays,payintentPeriod,everybaseMoney);
	 			lastintentDate=intentDate;
	 			
	 			 principalRepaymentMoney =projectMoney;
				 everybaseMoney =new BigDecimal("0");
	 			everyPeriodFundintent(list, this.intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, everybaseMoney, endPerioddays,payintentPeriod,new BigDecimal("0"));
	 			lastintentDate=intentDate;
 			
			
	
		   }
			
			if (isStartDatePay.equals("2") && isInterestByOneTime == 0) { // 对日  ///不一次性支付利息


 				for (int i = 1; i <= payintentPeriod; i++) {
	   		 
	   			  Date startactualdate=DateUtil.addMonthsToDate(startDate, i-1);
	   			  if(i>1){
	   				  startactualdate=DateUtil.addDaysToDate(startactualdate, -1);
	   			  }
	   			  Date endctualdate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, i), -1);
	   			  BigDecimal actualdays=getActualdays(startactualdate,endctualdate,1,i);
	   			  Date intentDate=null;
	   			  if(isPreposePayAccrual==1){
	   				  intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, i-1), -1);
	   				  if(i==1){
	   					  intentDate=startDate;
	   				  }
	   			  }else if(isPreposePayAccrual==0){
	   				  intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, i), -1);
	   			  }
	   			BigDecimal principalRepaymentMoney = new BigDecimal("0");
				BigDecimal everybaseMoney = projectMoney;
			    nextintentDate =DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, i), -1);
				everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, everybaseMoney, actualdays,i,projectMoney);
				lastintentDate=intentDate;
				 }
 				everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,projectMoney, new BigDecimal("0"), new BigDecimal("0"),payintentPeriod,new BigDecimal("0"));
 				lastintentDate=intentDate;
			}
			if (isInterestByOneTime == 1) { // 按实际放款日对日还款//一次性收取全部利息
				 Date startactualdate=startDate;
				 Date endctualdate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, payintentPeriod), -1);
				 BigDecimal actualdays=getActualdays(startactualdate,endctualdate,payintentPeriod,1);
				Date intentDate = null;
				if (isPreposePayAccrual == 1) {// 前置付息
					 intentDate=startDate;
				} else {// 后置付息
					intentDate = this.intentDate;
				}
				BigDecimal principalRepaymentMoney = new BigDecimal("0");
				BigDecimal everybaseMoney= projectMoney;
				 nextintentDate =this.intentDate;
				everyPeriodFundintent(list,intentDate,lastintentDate,nextintentDate,principalRepaymentMoney,everybaseMoney,actualdays, 1,projectMoney);
				lastintentDate=this.intentDate;
				
				
				everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,projectMoney, new BigDecimal("0"), new BigDecimal("0"),payintentPeriod,new BigDecimal("0"));
				lastintentDate=intentDate;
			}
    	
    }
     
    public  void createOfSingleInterestAndYearPay(List list){
    	Date lastintentDate = startDate; Date nextintentDate = null;
		if (isStartDatePay.equals("1") && isInterestByOneTime == 0) { // 固定在某日
			    String[] arr=date1.split("-");
				if(Long.valueOf(payintentPerioDate).compareTo(Long.valueOf(arr[2]))<0){
					Date sDate=DateUtil.addMonthsToDate(startDate, 1);
					String dateStr=sdf.format(sDate);
					String[] dateArr=dateStr.split("-");
					try {
						payintentPerioDateDate=sdf.parse(dateArr[0]+"-"+dateArr[1]+"-"+payintentPerioDate);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				
				
				BigDecimal firstPerioddays=getfirstPerioddays(startDate,payintentPerioDateDate);
	 			Date intentDate = null;
				if (isPreposePayAccrual == 1) {// 前置付息
					intentDate = this.startDate;
				} else {// 后置付息
					intentDate = payintentPerioDateDate;
				}
				BigDecimal principalRepaymentMoney = new BigDecimal("0");
				BigDecimal everybaseMoney = projectMoney;
				 nextintentDate =payintentPerioDateDate;
				 nextintentDate =this.intentDate;
				everyPeriodFundintent(list,intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, everybaseMoney, firstPerioddays,0,projectMoney);
				lastintentDate=intentDate;
				for (int i = 1; i < payintentPeriod; i++) {
 				if (isPreposePayAccrual == 1) {// 前置付息
					intentDate = DateUtil.addMonthsToDate(payintentPerioDateDate, (i-1)*12);
				} else {// 后置付息
					intentDate = DateUtil.addMonthsToDate(payintentPerioDateDate, i*12);
				}
 				Date startactualdate=DateUtil.addMonthsToDate(payintentPerioDateDate, (i-1)*12);
				Date endctualdate=DateUtil.addMonthsToDate(payintentPerioDateDate, i*12);
				BigDecimal actualdays=getActualdays(startactualdate,endctualdate,12,i);
				principalRepaymentMoney = new BigDecimal("0");
			    everybaseMoney = projectMoney;
			    nextintentDate =DateUtil.addMonthsToDate(payintentPerioDateDate, i*12);
				everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, everybaseMoney,actualdays,i,projectMoney);
				lastintentDate=nextintentDate;
				}
 			
 			   Date adate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"),payintentPeriod*3), -1);
			   Date bdate=DateUtil.addMonthsToDate(payintentPerioDateDate,(payintentPeriod-1)*12);
	 			if (isPreposePayAccrual == 1) {// 前置付息
					intentDate =bdate;
				} else {// 后置付息
					intentDate = adate;
				}
	 			BigDecimal endPerioddays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(bdate), sdf.format(adate))).toString());
	 			
				 principalRepaymentMoney =new BigDecimal("0");
			    everybaseMoney = projectMoney;
			    nextintentDate =adate;
	 			everyPeriodFundintent(list,intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, everybaseMoney, endPerioddays,payintentPeriod,everybaseMoney);
	 			lastintentDate=intentDate;
	 			
	 			 principalRepaymentMoney =projectMoney;
				 everybaseMoney =new BigDecimal("0");
	 			everyPeriodFundintent(list, this.intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, everybaseMoney, endPerioddays,payintentPeriod,new BigDecimal("0"));
	 			lastintentDate=intentDate;
 			
			
	
		   }
			
			if (isStartDatePay.equals("2") && isInterestByOneTime == 0) { // 对日  ///不一次性支付利息


 				for (int i = 1; i <= payintentPeriod; i++) {
	   		 
	   			  Date startactualdate=DateUtil.addMonthsToDate(startDate, (i-1)*12);
	   			  if(i>1){
	   				  startactualdate=DateUtil.addDaysToDate(startactualdate, -1);
	   			  }
	   			  Date endctualdate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, i*12), -1);
	   			BigDecimal actualdays=getActualdays(startactualdate,endctualdate,12,i);
	   			  Date intentDate=null;
	   			  if(isPreposePayAccrual==1){
	   				  intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, (i-1)*12), -1);
	   				  if(i==1){
	   					  intentDate=startDate;
	   				  }
	   			  }else if(isPreposePayAccrual==0){
	   				  intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, i*12), -1);
	   			  }
	   			BigDecimal principalRepaymentMoney = new BigDecimal("0");
				BigDecimal everybaseMoney = projectMoney;
				nextintentDate =DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, i*12), -1);
				everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, everybaseMoney, actualdays,i,projectMoney);
				lastintentDate=intentDate;
				 }
 				everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,projectMoney, new BigDecimal("0"), new BigDecimal("0"),payintentPeriod,new BigDecimal("0"));
			}
			if (isInterestByOneTime == 1) { // 按实际放款日对日还款//一次性收取全部利息
				 Date startactualdate=startDate;
				 Date endctualdate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, payintentPeriod*12), -1);
				 BigDecimal actualdays=getActualdays(startactualdate,endctualdate,payintentPeriod*12,1);
				Date intentDate = null;
				if (isPreposePayAccrual == 1) {// 前置付息
					 intentDate=startDate;
				} else {// 后置付息
					intentDate = this.intentDate;
				}
				BigDecimal principalRepaymentMoney = new BigDecimal("0");
				BigDecimal everybaseMoney= projectMoney;
				nextintentDate = this.intentDate;
				everyPeriodFundintent(list,intentDate,lastintentDate,nextintentDate,principalRepaymentMoney,everybaseMoney,actualdays, 1,projectMoney);
				
				everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,projectMoney, new BigDecimal("0"), new BigDecimal("0"),payintentPeriod,new BigDecimal("0"));
			}
    	
    }
    public void createOfSingleInterestAndOwerPay(List list){
    	Date lastintentDate = startDate; Date nextintentDate = null;
		if (isInterestByOneTime == 0) {// 按实际放款日对日还款//不一次性支付利息
			for (int i = 1; i <= payintentPeriod; i++) {
				Date intentDate = null;
				if (isPreposePayAccrual == 1) {// 前置付息
					  intentDate=DateUtil.addDaysToDate(startDate,(i-1)*dayOfEveryPeriod);
					if(i==1){
	    				   intentDate=startDate;
	    			   }
				} else {// 后置付息
					  intentDate=DateUtil.addDaysToDate(startDate, i*dayOfEveryPeriod);
				}
    			BigDecimal principalRepaymentMoney = new BigDecimal("0");
  				BigDecimal everybaseMoney = projectMoney;
  				BigDecimal actualdays=getDayfirstPerioddays(new BigDecimal(dayOfEveryPeriod),i);
  				nextintentDate = DateUtil.addDaysToDate(startDate, i*dayOfEveryPeriod);
  				everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, everybaseMoney, actualdays,i,projectMoney);
  				lastintentDate=intentDate;	
			}
   				everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,projectMoney, new BigDecimal("0"), new BigDecimal("0"),payintentPeriod,new BigDecimal("0"));
		}
		if (isInterestByOneTime == 1) {// 按实际放款日对日还款//一次性收取全部利息
				Date intentDate = null;
				if (isPreposePayAccrual == 1) {// 前置付息
					 intentDate=startDate;
				} else {// 后置付息
					intentDate = this.intentDate;
				}
				BigDecimal principalRepaymentMoney = new BigDecimal("0");
  				BigDecimal everybaseMoney = projectMoney;
  				BigDecimal actualdays=getDayfirstPerioddays(new BigDecimal(dayOfEveryPeriod).multiply(new BigDecimal(payintentPeriod)),1);
  				nextintentDate = this.intentDate;
  				everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, everybaseMoney, actualdays,1,projectMoney);
   				
  				everyPeriodFundintent(list,this.intentDate,lastintentDate,nextintentDate,projectMoney, new BigDecimal("0"), new BigDecimal("0"),payintentPeriod,new BigDecimal("0"));
			}

  	  
    
	   
	   
	   
  }
  public void createOfSingleInterestAndDayPay(List list){
	  Date lastintentDate = startDate; Date nextintentDate = null;
		
		if ( isInterestByOneTime == 0) {// 按实际放款日对日还款//不一次性支付利息
		
			for (int i = 1; i <= payintentPeriod; i++) {
				Date intentDate = null;
				if (isPreposePayAccrual == 1) {// 前置付息
					  intentDate=DateUtil.addDaysToDate(startDate, (i-1)*1);
					if(i==1){
	    				   intentDate=startDate;
	    			   }
				} else {// 后置付息
					  intentDate=DateUtil.addDaysToDate(startDate, i*1);
				}
				BigDecimal principalRepaymentMoney = new BigDecimal("0");
			BigDecimal everybaseMoney = projectMoney;
			BigDecimal actualdays=getDayfirstPerioddays(new BigDecimal(1),i);
			nextintentDate = DateUtil.addDaysToDate(startDate, i*1);
			everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, everybaseMoney, actualdays,i,projectMoney);
			lastintentDate=intentDate;
			 }
				everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,projectMoney, new BigDecimal("0"), new BigDecimal("0"),payintentPeriod,new BigDecimal("0"));
		}
		if (isInterestByOneTime == 1) {// 按实际放款日对日还款//一次性收取全部利息
				Date intentDate = null;
				if (isPreposePayAccrual == 1) {// 前置付息
					 intentDate=startDate;
				} else {// 后置付息
					intentDate = this.intentDate;
				}
				BigDecimal principalRepaymentMoney = new BigDecimal("0");
				BigDecimal everybaseMoney = projectMoney;
				BigDecimal actualdays=getDayfirstPerioddays(new BigDecimal(payintentPeriod),1);
				nextintentDate = this.intentDate;
				everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, everybaseMoney, actualdays,1,projectMoney);
				everyPeriodFundintent(list,this.intentDate,lastintentDate,nextintentDate,projectMoney, new BigDecimal("0"), new BigDecimal("0"),payintentPeriod,new BigDecimal("0"));
			}

	  
  }
    public  void createOfSameprincipalandInterestAndMonthPay(List list){
    	Date lastintentDate = startDate; Date nextintentDate = null;
    	// 月尾
    	BigDecimal periodtimemoney=periodtimemoney(sumDayaccrual.multiply(new BigDecimal(30)),projectMoney,payintentPeriod).divide(new BigDecimal(1),2,BigDecimal.ROUND_HALF_UP);
		     BigDecimal preperiodsurplus=projectMoney;

			if (isStartDatePay.equals("1") && isInterestByOneTime == 0) { // 固定在某日
																			// ////不一次性支付利息
				Date intentDate = null;
				if (isPreposePayAccrual == 1) {// 前置付息
					intentDate = this.startDate;
				} else {// 后置付息
					intentDate = DateUtil.addMonthsToDate(payintentPerioDateDate, 1);
				}
				BigDecimal firstPerioddays=getfirstPerioddays(startDate,DateUtil.addMonthsToDate(payintentPerioDateDate, 1));
				BigDecimal comsumDayaccrualMoney=preperiodsurplus.multiply(sumDayaccrual).multiply(thirty);
				BigDecimal firstsumDayaccrualMoney=preperiodsurplus.multiply(sumDayaccrual).multiply(firstPerioddays);
				
				
				
				BigDecimal firstperiodtimemoney=periodtimemoney.add(firstsumDayaccrualMoney).subtract(comsumDayaccrualMoney);
				BigDecimal principalRepaymentMoney = firstperiodtimemoney.subtract(firstsumDayaccrualMoney);
				BigDecimal everybaseMoney = preperiodsurplus;
				nextintentDate = DateUtil.addMonthsToDate(payintentPerioDateDate, 1);
				everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, everybaseMoney, firstPerioddays,1,everybaseMoney);
				preperiodsurplus = preperiodsurplus.subtract(principalRepaymentMoney);
				lastintentDate=intentDate;
				for (int i = 2; i < payintentPeriod; i++) {
					if (isPreposePayAccrual == 0) {
						intentDate = DateUtil.addMonthsToDate(payintentPerioDateDate, i);
					} else if (isPreposePayAccrual == 1) {
						intentDate = DateUtil.addMonthsToDate(payintentPerioDateDate, i - 1);
					}
					comsumDayaccrualMoney=preperiodsurplus.multiply(sumDayaccrual).multiply(thirty);
					everybaseMoney = preperiodsurplus;
					principalRepaymentMoney = periodtimemoney.subtract(comsumDayaccrualMoney);
					nextintentDate =  DateUtil.addMonthsToDate(payintentPerioDateDate, i - 1);
					everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, preperiodsurplus, thirty,i,everybaseMoney);
					preperiodsurplus = preperiodsurplus.subtract(principalRepaymentMoney);
					lastintentDate=intentDate;
                
				}
				
				BigDecimal enddays = BigDecimal.valueOf(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(DateUtil.addMonthsToDate(payintentPerioDateDate, payintentPeriod-1)),sdf.format(DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod), -1)))));
				BigDecimal endsumDayaccrualMoney=preperiodsurplus.multiply(sumDayaccrual).multiply(enddays);
				
				comsumDayaccrualMoney=preperiodsurplus.multiply(sumDayaccrual).multiply(thirty);
				 BigDecimal endperiodtimemoney=periodtimemoney.subtract(comsumDayaccrualMoney).add(endsumDayaccrualMoney);
				 principalRepaymentMoney = endperiodtimemoney.subtract(endsumDayaccrualMoney);
				 everybaseMoney = preperiodsurplus;
				nextintentDate = this.intentDate;
				everyPeriodFundintent(list, DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod), -1),lastintentDate,DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod), -1),principalRepaymentMoney, everybaseMoney, enddays,payintentPeriod,preperiodsurplus);
			}
				if (isStartDatePay.equals("2") && isInterestByOneTime == 0) { // 对日  ///不一次性支付利息

					for (int i = 1; i <= payintentPeriod; i++) {
						if (isPreposePayAccrual == 1) {
							intentDate = DateUtil.addMonthsToDate(payintentPerioDateDate, i - 1);
							if (i == 1) {
								intentDate = DateUtil.parseDate(date1,"yyyy-MM-dd");
							}
							
						} else if (isPreposePayAccrual == 0) {
							intentDate = DateUtil.addMonthsToDate(payintentPerioDateDate, i);
						}
						BigDecimal sumDayaccrualMoney=preperiodsurplus.multiply(sumDayaccrual).multiply(thirty);
					
						BigDecimal everybaseMoney = preperiodsurplus;
						BigDecimal	principalRepaymentMoney = periodtimemoney.subtract(sumDayaccrualMoney);
						nextintentDate =  DateUtil.addMonthsToDate(payintentPerioDateDate, i);
						everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, everybaseMoney,thirty, i,preperiodsurplus);
						preperiodsurplus = preperiodsurplus.subtract(principalRepaymentMoney);
						lastintentDate=intentDate;

					}

				}


    }
    public  void createOfSameprincipalAndMonthPay(List list){
    	//一次性没有意义
		BigDecimal preperiodsurplus = projectMoney;
		Date lastintentDate = startDate; Date nextintentDate = null;
			BigDecimal sameprojectMoney = projectMoney.divide(new BigDecimal(payintentPeriod), 100, BigDecimal.ROUND_HALF_UP);

			if (isStartDatePay.equals("1") && isInterestByOneTime == 0) { // 固定在某日
																			// ////不一次性支付利息
				Date intentDate = null;
				if (isPreposePayAccrual == 1) {// 前置付息
					intentDate = this.startDate;
				} else {// 后置付息
					intentDate = DateUtil.addMonthsToDate(payintentPerioDateDate, 1);
				}
				BigDecimal everybaseMoney = projectMoney;
				BigDecimal principalRepaymentMoney = sameprojectMoney;
				BigDecimal firstPerioddays=getfirstPerioddays(startDate,DateUtil.addMonthsToDate(payintentPerioDateDate, 1));
				nextintentDate = DateUtil.addMonthsToDate(payintentPerioDateDate, 1);
				everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, everybaseMoney, firstPerioddays,1,everybaseMoney);
				preperiodsurplus = preperiodsurplus.subtract(principalRepaymentMoney);
				lastintentDate=intentDate;

				for (int i = 2; i < payintentPeriod; i++) {
					if (isPreposePayAccrual ==1) {
						intentDate = DateUtil.addMonthsToDate(payintentPerioDateDate, i-1);
					} else if (isPreposePayAccrual == 0) {
						intentDate = DateUtil.addMonthsToDate(payintentPerioDateDate, i);
					}
					everybaseMoney = preperiodsurplus;
					principalRepaymentMoney = sameprojectMoney;
					 nextintentDate = DateUtil.addMonthsToDate(payintentPerioDateDate, i);
					everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, preperiodsurplus, thirty,i,everybaseMoney);
					preperiodsurplus = preperiodsurplus.subtract(principalRepaymentMoney);
					lastintentDate=intentDate;
				}
				 BigDecimal enddays=BigDecimal.valueOf(DateUtil.getDaysBetweenDate( sdf.format(DateUtil.addMonthsToDate(payintentPerioDateDate, payintentPeriod-1)),sdf.format(this.intentDate)));
				 everybaseMoney = preperiodsurplus;
				 principalRepaymentMoney = sameprojectMoney;
				 nextintentDate = this.intentDate;
				 everyPeriodFundintent(list,this.intentDate,lastintentDate,nextintentDate,principalRepaymentMoney,everybaseMoney,enddays,payintentPeriod,everybaseMoney);
				 lastintentDate=intentDate;
			}
				
			if (isStartDatePay.equals("2") && isInterestByOneTime == 0) { // 对日  ///不一次性支付利息

				for (int i = 1; i <= payintentPeriod; i++) {
					 if(isPreposePayAccrual==0){
						 intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, i), -1);
					 }else if(isPreposePayAccrual==1){
						 intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, i-1), -1);
						 if(i==1){
							 intentDate=startDate;
						 }
					 }
					BigDecimal everybaseMoney = preperiodsurplus;
					BigDecimal principalRepaymentMoney = sameprojectMoney;
					 nextintentDate = DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, i), -1);
					everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, preperiodsurplus,thirty, i,everybaseMoney);
					preperiodsurplus = preperiodsurplus.subtract(principalRepaymentMoney);
					lastintentDate=intentDate;

				}

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
	//装配FundIntent对象
	public  void everyPeriodFundintent(List list,Date intentdate,Date lastintentDate,Date nextintentDate,BigDecimal principalRepaymentMoney,BigDecimal everybaseMoney,BigDecimal thirty ,int payintentPeriod,BigDecimal surplusManagMoney){
		if(intentdate.compareTo(this.intentDate)>0){
			return ;
		}
		if("FundIntent".equals(this.fundIntentType)){
			FundIntent sf = calculfundintent(ProjectLoadRoot, intentdate,lastintentDate,nextintentDate, BigDecimal.valueOf(0),principalRepaymentMoney,payintentPeriod);
			FundIntent sf1 = calculfundintent(ProjectLoadAccrual,intentdate,lastintentDate,nextintentDate,BigDecimal.valueOf(0),everybaseMoney.multiply(dayaccrual).multiply(thirty),payintentPeriod) ;
			FundIntent sf2 = calculfundintent(ProjectManagementConsulting,intentdate,lastintentDate,nextintentDate,BigDecimal.valueOf(0),  projectMoney.multiply(managementConsultingOfRate),payintentPeriod);
			FundIntent sf3 = calculfundintent(ProjectFinanceService,intentdate,lastintentDate,nextintentDate,BigDecimal.valueOf(0), everybaseMoney.multiply(dayfinanceServiceOfRate).multiply(thirty),payintentPeriod);
			FundIntent sf4 = calculfundintent(ProjectSurplusManage,intentdate,lastintentDate,nextintentDate,BigDecimal.valueOf(0), surplusManagMoney.multiply(surplusManageOfRate),payintentPeriod);
			
			
			if(sf.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf);}
			if(sf1.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){
				list.add(sf1);
				//风险金
				if(riskAccrual!=0){
				BigDecimal riskMoney=sf1.getIncomeMoney().multiply(new BigDecimal(riskAccrual/100));
				FundIntent sf5=calculfundintent(ProjectRisk,intentdate,null,null,riskMoney,new BigDecimal("0"),sf1.getPayintentPeriod());
				list.add(sf5);
				}
				}
			if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
			if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}
			if(sf4.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf4);}
		}else if("SlFundIntent".equals(this.fundIntentType)){
			SlFundIntent sf = calculslfundintent(ProjectLoadRoot, intentdate,lastintentDate,nextintentDate, BigDecimal.valueOf(0),principalRepaymentMoney,payintentPeriod);
			SlFundIntent sf1 = calculslfundintent(ProjectLoadAccrual,intentdate,lastintentDate,nextintentDate,BigDecimal.valueOf(0),everybaseMoney.multiply(dayaccrual).multiply(thirty),payintentPeriod) ;
			SlFundIntent sf2 = calculslfundintent(ProjectManagementConsulting,intentdate,lastintentDate,nextintentDate,BigDecimal.valueOf(0),everybaseMoney.multiply(daymanagementConsultingOfRate).multiply(thirty),payintentPeriod);
			SlFundIntent sf3 = calculslfundintent(ProjectFinanceService,intentdate,lastintentDate,nextintentDate,BigDecimal.valueOf(0),everybaseMoney.multiply(dayfinanceServiceOfRate).multiply(thirty),payintentPeriod);
/*			SlFundIntent sf4 = calculslfundintent(ProjectSurplusManage,intentdate,lastintentDate,nextintentDate,BigDecimal.valueOf(0),surplusManagMoney.multiply(surplusManageOfRate),payintentPeriod);*/
		
			if(sf.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf);}
			if(sf1.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){
				list.add(sf1);
				//风险金
				if(riskAccrual!=0){/*
				BigDecimal riskMoney=sf1.getIncomeMoney().multiply(new BigDecimal(riskAccrual/100));
				SlFundIntent sf5=calculslfundintent(ProjectRisk,intentdate,null,null,riskMoney,new BigDecimal("0"),sf1.getPayintentPeriod());
				list.add(sf5);
				*/}
				}
			if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
			if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}
			/*if(sf4.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf4);}*/
		}else if("BpFundIntent".equals(this.fundIntentType)){
			BpFundIntent sf = calculBpFundIntent(ProjectLoadRoot, intentdate,lastintentDate,nextintentDate, BigDecimal.valueOf(0),principalRepaymentMoney,payintentPeriod);
			BpFundIntent sf1 = calculBpFundIntent(ProjectLoadAccrual,intentdate,lastintentDate,nextintentDate,BigDecimal.valueOf(0),everybaseMoney.multiply(dayaccrual).multiply(thirty),payintentPeriod) ;
			BpFundIntent sf3 = calculBpFundIntent(ProjectFinanceService,intentdate,lastintentDate,nextintentDate,BigDecimal.valueOf(0), everybaseMoney.multiply(dayfinanceServiceOfRate).multiply(thirty),payintentPeriod);
			
            if (everybaseMoney.compareTo(new BigDecimal("0")) !=0){
            	BpFundIntent sf2 = calculBpFundIntent(ProjectManagementConsulting,intentdate,lastintentDate,nextintentDate,BigDecimal.valueOf(0), projectMoney.multiply(managementConsultingOfRate),payintentPeriod);
            	if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
            }
			
			BpFundIntent sf4 = calculBpFundIntent(ProjectSurplusManage,intentdate,lastintentDate,nextintentDate,BigDecimal.valueOf(0), surplusManagMoney.multiply(surplusManageOfRate),payintentPeriod);
				
				
				if(sf.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf);}
				if(sf1.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){
					list.add(sf1);
					//风险金
					if(riskAccrual!=0){
					/*BigDecimal riskMoney=sf1.getIncomeMoney().multiply(new BigDecimal(riskAccrual/100));
					BpFundIntent sf5=calculBpFundIntent(ProjectRisk,intentdate,null,null,riskMoney,new BigDecimal("0"),sf1.getPayintentPeriod());
					list.add(sf5);*/
					}
					}
				if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}
				
				if(sf4.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf4);}
				
				
			}
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
		sf1.setPreceptId(this.preceptId);
		sf1.setIsValid(Short.valueOf("0"));
		sf1.setIsCheck(Short.valueOf("1"));
		
		
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
		sf1.setPayintentPeriod(payintentPeriod);
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

	
}
