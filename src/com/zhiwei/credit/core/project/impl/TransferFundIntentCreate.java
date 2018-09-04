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

import com.hurong.credit.service.creditFlow.financingAgency.PlBidPlanService;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.credit.core.project.FundIntentListPro3;
import com.zhiwei.credit.model.creditFlow.finance.BpFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.FundIntent;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan;
import com.zhiwei.credit.model.creditFlow.fund.project.BpFundProject;
import com.zhiwei.credit.model.customer.InvestPersonInfo;
import com.zhiwei.credit.service.creditFlow.finance.BpFundIntentService;
import com.zhiwei.credit.service.creditFlow.fund.project.BpFundProjectService;

public class TransferFundIntentCreate {
/**
 * 确定利率、咨询管理费率、财务服务费率之间的关系
 * 
 * */
	
	/**
	 * 优惠券利息   ---优惠券计算的
	 */
	public static final String CouponInterest = "couponInterest";
	
	/**
	 * 加息券 ---加息利率计算的
	 */
	public static final String SubjoinInterest = "subjoinInterest";
	/**
	 * 优惠券 返息现，本金
	 */
	public static final String PrincipalCoupons = "principalCoupons";
	/**
	 * 普通加息
	 */
	public static final String CommonInterest = "commoninterest";
	/**
	 * 募集期
	 */
	public static final String RaiseInterest = "raiseinterest";
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
	public static  Integer couNum=0;//利息次数
	public static BigDecimal couMoney=new BigDecimal("0");//合计利息
	public static BigDecimal couPrincipalMoney=new BigDecimal("0");//合计本金
	public static String createType="";
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
	private Date manyStartDate;//起息日期
	private Integer startPeriod;
	
	private InvestPersonInfo personInfo;
	
	public TransferFundIntentCreate(Object prtoject1,InvestPersonInfo person,SlFundIntent s,String isHaveLending){
		   this.personInfo = person;
			
		   BpFundProject project=(BpFundProject)prtoject1;
		   this.projectId= project.getProjectId();
		   this.projectName= project.getProjectName();
		   this.projectNumber= project.getProjectNumber();
		   SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
	       this.isActualDay=AppUtil.getIsActualDay();
	       this.isccalculateFirstAndEnd=AppUtil.getInterest();  //算头不算尾0 算头又算尾1 AppUtil.getInterest()
	       
	       this.accrualType=project.getAccrualtype(); 
	       this.riskAccrual=Double.valueOf(project.getRiskRate()==null?"0":project.getRiskRate().toString());
	       this.payaccrualType=project.getPayaccrualType(); 
	       this.projectMoney=project.getProjectMoney();
	       this.preceptId=project.getId();
	       this.intentDate=project.getIntentDate();   
	       this.date2=sd.format(project.getIntentDate());   
	       this.manyStartDate=project.getStartInterestDate();
	       if(null !=s.getIntentDate()){
		       this.date1=sd.format(DateUtil.addDaysToDate(s.getIntentDate(), 1));   
		       this.startDate=DateUtil.addDaysToDate(s.getIntentDate(), 1);   
		    }
	      
	      this.startPeriod=s.getPayintentPeriod();
	       this.isStartDatePay=project.getIsStartDatePay();
	       this.isInterestByOneTime = project.getIsInterestByOneTime();
	       
	     /*  this.accrual=project.getAccrual().divide(new BigDecimal(100));
	       this.dayaccrual=project.getAccrual().divide(new BigDecimal(3000),100,BigDecimal.ROUND_UP);//日化*/
	         //直接除以360天得到日化利率
	       this.dayaccrual=project.getYearAccrualRate().divide(new BigDecimal(100)).divide(new BigDecimal(360),100,BigDecimal.ROUND_UP);
	       
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
	       if(null !=project.getBalanceRate()){ 
	    	   this.surplusManageOfRate=project.getBalanceRate().divide(new BigDecimal(1000));
	       }else{
	    	   this.surplusManageOfRate=new BigDecimal(0);
	       }
	   //    this.sumDayaccrual=this.dayaccrual.add( this.daymanagementConsultingOfRate).add( this.dayfinanceServiceOfRate);
	       this.sumDayaccrual=this.dayaccrual;
	       this.isPreposePayAccrual=project.getIsPreposePayAccrual();
	       this.payintentPeriod=project.getPayintentPeriod();
	       this.dayOfEveryPeriod=project.getDayOfEveryPeriod();
	       this.payintentPerioDate=String.valueOf(project.getPayintentPerioDate());
	       
	       if(null !=person){
	    	   
	    	   
	       this.investPersonId= person.getInvestPersonId();
	       this.investPersonName=person.getInvestPersonName();
	       if(null!=person.getFundResource()){
	    	   this.fundResource=person.getFundResource().toString();
	       }
	     
	       this.projectMoney=person.getInvestMoney();  //贷款金额（单个投资人）
	       this.orderNo=person.getOrderNo();
	       }
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
    	this.businessType="SmallLoan";
    	
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
		this.createType="";
    	this.fundIntentType="BpFundIntent";
    	this.businessType="SmallLoan";
    	
    	List<BpFundIntent> list = new ArrayList<BpFundIntent>();
       //贷款本金放贷
    	BpFundIntent   sf = new BpFundIntent();   
    	if (null ==this.isHaveLending ||this.isHaveLending.equals("yes")) { //像提前还款什么的都是不需要生成放款记录
    		sf=calculBpFundIntent(ProjectLoadOut,manyStartDate,null,null,projectMoney,new BigDecimal("0"),0);
			list.add(sf);
		} 
    	BigDecimal days=new BigDecimal(DateUtil.getDaysBetweenDate(manyStartDate, startDate)-1);
    	BpFundIntent sf1 = calculBpFundIntent(ProjectLoadAccrual,DateUtil.addDaysToDate(startDate, -1),manyStartDate,DateUtil.addDaysToDate(startDate, -1),BigDecimal.valueOf(0),  projectMoney.multiply(this.dayaccrual).multiply(days),startPeriod);
    	BpFundIntent sf2 = calculBpFundIntent(ProjectManagementConsulting,DateUtil.addDaysToDate(startDate, -1),manyStartDate,DateUtil.addDaysToDate(startDate, -1),BigDecimal.valueOf(0),  projectMoney.multiply(this.daymanagementConsultingOfRate).multiply(days),startPeriod);
		BpFundIntent sf3 = calculBpFundIntent(ProjectFinanceService,DateUtil.addDaysToDate(startDate, -1),manyStartDate,DateUtil.addDaysToDate(startDate, -1),BigDecimal.valueOf(0),  projectMoney.multiply(this.dayfinanceServiceOfRate).multiply(days),startPeriod);
		BpFundIntent sf4 = calculBpFundIntent(ProjectLoadRoot,DateUtil.addDaysToDate(startDate, -1),manyStartDate,DateUtil.addDaysToDate(startDate, -1),BigDecimal.valueOf(0),  projectMoney,startPeriod);
		if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){list.add(sf1);}
		if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
		if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}
		if(isInterestByOneTime == 1){//一次性的时候才生成
			if(sf4.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf4);}
		}
    	
    	create(list);
    	
    	if(accrualType.equals("singleInterest")&&null!=isStartDatePay && isStartDatePay.equals("1")){
	/*	try {
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
				}*/
    	}
    	return list;
		
	}
public  List<BpFundIntent> createCouponsBpFundIntent(String createType,PlBidPlan plBidPlan){
	this.createType=createType;
	this.fundIntentType="BpFundIntent";
	this.businessType="SmallLoan";
	BpFundIntent couponInterest=null;
	List<BpFundIntent> list = new ArrayList<BpFundIntent>();
	//贷款本金放贷
	BpFundIntent   sf = new BpFundIntent();   
	BigDecimal days=new BigDecimal(DateUtil.getDaysBetweenDate(manyStartDate, startDate)-1);
	if (null ==this.isHaveLending ||this.isHaveLending.equals("yes")) { //像提前还款什么的都是不需要生成放款记录
		if(personInfo.getRebateType()!=null&&personInfo.getRebateWay()!=null){
			if(personInfo.getRebateWay()==1||personInfo.getRebateWay()==3){//立返,到期
				if(personInfo.getRebateType()==1){//返现
					Date date = null;
					int payPeriod=0;
					if(personInfo.getRebateWay()==1){
						date= new Date();
					}else{
						date=plBidPlan.getEndIntentDate();
						payPeriod=payintentPeriod;
					}
					 couponInterest = calculBpFundIntent(PrincipalCoupons,date,null,null,new BigDecimal("0"),personInfo.getCouponInterest(),payPeriod);
				}else if(personInfo.getRebateType()==2){//返息
					projectMoney=personInfo.getCouponInterest();
					couNum=startPeriod;
					couMoney=new BigDecimal("0");
					couMoney=couMoney.add( projectMoney.multiply(this.dayaccrual).multiply(days).setScale(2,BigDecimal.ROUND_HALF_UP));
				}else if(personInfo.getRebateType()==3){//返息现
					projectMoney=personInfo.getCouponInterest();
					couNum=startPeriod;
					couMoney=new BigDecimal("0");
					couMoney=couMoney.add( projectMoney.multiply(this.dayaccrual).multiply(days).setScale(2,BigDecimal.ROUND_HALF_UP));
				}else if(personInfo.getRebateType()==4){//加息
					couNum=startPeriod;
					couMoney=new BigDecimal("0");
					couMoney=couMoney.add( projectMoney.multiply(personInfo.getSubjoinInterest()).multiply(days).setScale(2,BigDecimal.ROUND_HALF_UP));
				}else if(personInfo.getRebateType()==0){//普通加息
					couNum=startPeriod;
					couMoney=new BigDecimal("0");
					couMoney=couMoney.add( projectMoney.multiply(personInfo.getSubjoinInterest()).multiply(days).setScale(2,BigDecimal.ROUND_HALF_UP));
				}
			}else{//随期
				if(personInfo.getRebateType()==1){//返现
					// couponInterest = calculBpFundIntent(CouponInterest,DateUtil.addDaysToDate(startDate, -1),manyStartDate,DateUtil.addDaysToDate(startDate, -1),BigDecimal.valueOf(0),  projectMoney.multiply(personInfo.getCouponInterest()).divide(personInfo.getInvestMoney(),2,BigDecimal.ROUND_HALF_UP),startPeriod);
				}else if(personInfo.getRebateType()==2){//返息
					projectMoney=personInfo.getCouponInterest();
					couponInterest = calculBpFundIntent(CouponInterest,DateUtil.addDaysToDate(startDate, -1),manyStartDate,DateUtil.addDaysToDate(startDate, -1),BigDecimal.valueOf(0),  projectMoney.multiply(this.dayaccrual).multiply(days),startPeriod);
				}else if(personInfo.getRebateType()==3){//返息现
					projectMoney=personInfo.getCouponInterest();
					couponInterest = calculBpFundIntent(CouponInterest,DateUtil.addDaysToDate(startDate, -1),manyStartDate,DateUtil.addDaysToDate(startDate, -1),BigDecimal.valueOf(0),  projectMoney.multiply(this.dayaccrual).multiply(days),startPeriod);
				}else if(personInfo.getRebateType()==4){//加息
					couponInterest = calculBpFundIntent(SubjoinInterest,DateUtil.addDaysToDate(startDate, -1),manyStartDate,DateUtil.addDaysToDate(startDate, -1),BigDecimal.valueOf(0),  projectMoney.multiply(personInfo.getSubjoinInterest()).multiply(days),startPeriod);
				}else if(personInfo.getRebateType()==0){//普通加息
					couponInterest = calculBpFundIntent(CommonInterest,DateUtil.addDaysToDate(startDate, -1),manyStartDate,DateUtil.addDaysToDate(startDate, -1),BigDecimal.valueOf(0),  projectMoney.multiply(personInfo.getSubjoinInterest()).multiply(days),startPeriod);
				}
			}
		}
	} 
		if(couponInterest!=null){
			if(couponInterest.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
				list.add(couponInterest);
			}
		}
		
		create(list);
		
		//判断是否有	募集期利率
		if(plBidPlan.getRaiseRate()!=null&&!plBidPlan.getRaiseRate().equals("")&&plBidPlan.getRaiseRate().compareTo(new BigDecimal(0))>0){
			//募集期天数 按每个投资人实际投资日期到起息日计算天数，公式：实际投资金额*募集期天数*募集期利率/360 (立还)
			java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
				try {
					Date beginDate = format.parse(personInfo.getInvestTime().toString());
					Date endDate = format.parse(format.format(plBidPlan.getStartIntentDate()));
					long  day = (endDate.getTime() - beginDate.getTime())/ (24 * 60 * 60 * 1000);
					/*if(day<=0){
						day=1;
					}*/
					if(day>0){
					BigDecimal raiseMoney = personInfo.getInvestMoney().multiply(new BigDecimal(day)).
											multiply(plBidPlan.getRaiseRate()).divide(new BigDecimal(100)).
											divide(new BigDecimal(360),2,BigDecimal.ROUND_HALF_UP);
					BpFundIntent raiseInterest = calculBpFundIntent(RaiseInterest,new Date(),null,null,new BigDecimal("0"),raiseMoney,0);
					list.add(raiseInterest);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
		
	
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
		Date lastintentDate = this.manyStartDate; Date nextintentDate = null;
		BigDecimal sameprojectMoney=projectMoney.divide(new BigDecimal(payintentPeriod-startPeriod+1),2,BigDecimal.ROUND_UP);
		
		if (isStartDatePay.equals("1") && isInterestByOneTime == 0) { // 固定在某日
	  		 String[] arr=date1.split("-");
				if(Long.valueOf(payintentPerioDate).compareTo(Long.valueOf(arr[2]))<0){
					Date sDate=startDate;
					String dateStr=sdf.format(sDate);
					String[] dateArr=dateStr.split("-");
					try {
						payintentPerioDateDate=sdf.parse(dateArr[0]+"-"+dateArr[1]+"-"+payintentPerioDate);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
	  		for (int i = startPeriod; i <payintentPeriod; i++) {
					
	/*				if (isPreposePayAccrual == 1) {// 前置付息
						intentDate = DateUtil.addMonthsToDate(payintentPerioDateDate, i - 1);
						if(i==1){
		    				   intentDate=startDate;
		    			   }
					} else {// 后置付息
						intentDate = DateUtil.addMonthsToDate(payintentPerioDateDate, i);
					}*/
					
					BigDecimal everybaseMoney= projectMoney;
					BigDecimal  principalRepaymentMoney=sameprojectMoney;
					BigDecimal actualdays=new BigDecimal(90);
					nextintentDate = DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, (i-startPeriod)*3), -1);
					if(isPreposePayAccrual == 1){
						Date intentDate=DateUtil.addMonthsToDate(payintentPerioDateDate, (i-1)*3);
		    			   if(i==1){
		    				   intentDate=startDate;
		    			   }
	    			   everyPeriodFundintent(list,intentDate,lastintentDate,nextintentDate, new BigDecimal(0),everybaseMoney,actualdays, i,surplusManagMoney);
	    			   everyPeriodFundintent(list,DateUtil.addMonthsToDate(payintentPerioDateDate, i*3),lastintentDate,nextintentDate,principalRepaymentMoney,new BigDecimal(0),new BigDecimal(0), i,surplusManagMoney);
					}else{
						if(i==startPeriod){
							everyPeriodFundintent(list,DateUtil.addMonthsToDate(payintentPerioDateDate, (i-startPeriod)*3),lastintentDate,nextintentDate,principalRepaymentMoney,new BigDecimal(0),actualdays, i,surplusManagMoney);
						}else{
							everyPeriodFundintent(list,DateUtil.addMonthsToDate(payintentPerioDateDate, (i-startPeriod)*3),lastintentDate,nextintentDate,principalRepaymentMoney,everybaseMoney,actualdays, i,surplusManagMoney);
						}
					}
					surplusManagMoney=surplusManagMoney.subtract(principalRepaymentMoney);
					lastintentDate=nextintentDate;
				
	  		}
	  		if(isPreposePayAccrual == 1){
	  			everyPeriodFundintent(list, DateUtil.addMonthsToDate(payintentPerioDateDate, (payintentPeriod-1)*3),lastintentDate,intentDate,new BigDecimal(0),this.projectMoney,thirty, payintentPeriod,surplusManagMoney);
	  			everyPeriodFundintent(list, this.intentDate,lastintentDate,this.intentDate,sameprojectMoney,new BigDecimal(0),new BigDecimal(0), payintentPeriod,surplusManagMoney);
	  		}else{
	  			everyPeriodFundintent(list,this.intentDate,lastintentDate,this.intentDate,sameprojectMoney,this.projectMoney,new BigDecimal(DateUtil.getDaysBetweenDate(lastintentDate, this.intentDate)), payintentPeriod,surplusManagMoney);
	  		}

		   }/*else if (isStartDatePay.equals("1") && isInterestByOneTime == 1) { // 固定在某日
			 String[] arr=date1.split("-");
				if(Long.valueOf(payintentPerioDate).compareTo(Long.valueOf(arr[2]))<0){
					Date sDate=startDate;
					String dateStr=sdf.format(sDate);
					String[] dateArr=dateStr.split("-");
					try {
						payintentPerioDateDate=sdf.parse(dateArr[0]+"-"+dateArr[1]+"-"+payintentPerioDate);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				BigDecimal money=new BigDecimal(0);
			for (int i = 1; i <payintentPeriod; i++) {
			
					BigDecimal everybaseMoney= projectMoney;
					BigDecimal  principalRepaymentMoney=sameprojectMoney;
					BigDecimal actualdays=thirty;
					nextintentDate = DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, i), -1);
					money=money.add(everybaseMoney.multiply(actualdays).multiply(this.dayaccrual));
					
					everyPeriodFundintent(list,DateUtil.addMonthsToDate(payintentPerioDateDate, i),lastintentDate,nextintentDate,principalRepaymentMoney,new BigDecimal(0),new BigDecimal(0), i,surplusManagMoney);
					surplusManagMoney=surplusManagMoney.subtract(principalRepaymentMoney);
					lastintentDate=nextintentDate;
			}
			money=money.add(this.projectMoney.multiply(thirty).multiply(this.dayaccrual));
			SlFundIntent sf1 = calculslfundintent(this.ProjectLoadAccrual,(isPreposePayAccrual == 1?startDate:this.intentDate),startDate,this.intentDate,BigDecimal.valueOf(0), money,(isPreposePayAccrual == 1?1:payintentPeriod));
			if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
				list.add(sf1);
			}
			
			everyPeriodFundintent(list,this.intentDate,lastintentDate,intentDate,sameprojectMoney,new BigDecimal(0),new BigDecimal(0), payintentPeriod,surplusManagMoney);

		   }*/
		
		if (isStartDatePay.equals("2") && isInterestByOneTime == 0) {// 按实际放款日对日还款//不一次性支付利息
			
			for (int i =startPeriod; i <= payintentPeriod; i++) {
				
				BigDecimal everybaseMoney= projectMoney;
				BigDecimal  principalRepaymentMoney=sameprojectMoney;
				BigDecimal actualdays=new BigDecimal(90);
				nextintentDate = DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate,  (i-startPeriod)*3), -1);
				if(isPreposePayAccrual==1){
					Date intentDate=null;
					if(i==this.startPeriod){
						intentDate=startDate;
					}else{
						intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, (i - 1)*3), -1);
					}
					everyPeriodFundintent(list,intentDate,lastintentDate,nextintentDate,new BigDecimal(0),everybaseMoney,actualdays, i,surplusManagMoney);
					everyPeriodFundintent(list,DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate,  i*3), -1),lastintentDate,nextintentDate,principalRepaymentMoney,new BigDecimal(0),new BigDecimal(0), i,surplusManagMoney);
				}else{
					if(i==this.startPeriod){
						everyPeriodFundintent(list,DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate,  (i-startPeriod)*3), -1),lastintentDate,nextintentDate,principalRepaymentMoney,new BigDecimal(0),actualdays, i,surplusManagMoney);
					}else{
						everyPeriodFundintent(list,DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate,  (i-startPeriod)*3), -1),lastintentDate,nextintentDate,principalRepaymentMoney,everybaseMoney,actualdays, i,surplusManagMoney);
					}
				}
				surplusManagMoney=surplusManagMoney.subtract(principalRepaymentMoney);
				lastintentDate=nextintentDate;
			}
		}
		/*if (isStartDatePay.equals("2") && isInterestByOneTime == 1) {// 按实际放款日对日还款//一次性收取全部利息
				Date intentDate = null;
				int period=1;
				if (isPreposePayAccrual == 1) {// 前置付息
					 intentDate=startDate;
				} else {// 后置付息
					intentDate = this.intentDate;
					period=this.payintentPeriod;
				}
				everyPeriodFundintent(list,intentDate,startDate,this.intentDate,new BigDecimal(0),projectMoney,thirty.multiply(new BigDecimal(payintentPeriod)), period,surplusManagMoney);
				for (int i = 1; i <= payintentPeriod; i++) {
				
					BigDecimal  principalRepaymentMoney=sameprojectMoney;
					nextintentDate = DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate,  i), -1);
					everyPeriodFundintent(list,DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate,  i), -1),lastintentDate,nextintentDate,principalRepaymentMoney,new BigDecimal(0),new BigDecimal(0), i,surplusManagMoney);
					surplusManagMoney=surplusManagMoney.subtract(principalRepaymentMoney);
					lastintentDate=nextintentDate;
				}
			}*/
}
  public  void createOfSameprincipalsameInterestAndYearPay(List list){
	  	BigDecimal surplusManagMoney=projectMoney;
		Date lastintentDate = this.manyStartDate; Date nextintentDate = null;
		BigDecimal sameprojectMoney=projectMoney.divide(new BigDecimal(payintentPeriod-startPeriod+1),2,BigDecimal.ROUND_UP);
		
		if (isStartDatePay.equals("1") && isInterestByOneTime == 0) { // 固定在某日
	  		 String[] arr=date1.split("-");
				if(Long.valueOf(payintentPerioDate).compareTo(Long.valueOf(arr[2]))<0){
					Date sDate=startDate;
					String dateStr=sdf.format(sDate);
					String[] dateArr=dateStr.split("-");
					try {
						payintentPerioDateDate=sdf.parse(dateArr[0]+"-"+dateArr[1]+"-"+payintentPerioDate);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
	  		for (int i = startPeriod; i <payintentPeriod; i++) {
					
	/*				if (isPreposePayAccrual == 1) {// 前置付息
						intentDate = DateUtil.addMonthsToDate(payintentPerioDateDate, i - 1);
						if(i==1){
		    				   intentDate=startDate;
		    			   }
					} else {// 后置付息
						intentDate = DateUtil.addMonthsToDate(payintentPerioDateDate, i);
					}*/
					
					BigDecimal everybaseMoney= projectMoney;
					BigDecimal  principalRepaymentMoney=sameprojectMoney;
					BigDecimal actualdays=new BigDecimal(360);
					nextintentDate = DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, (i-startPeriod)*12), -1);
					if(isPreposePayAccrual == 1){
						Date intentDate=DateUtil.addMonthsToDate(payintentPerioDateDate, (i-1)*12);
		    			   if(i==1){
		    				   intentDate=startDate;
		    			   }
	    			   everyPeriodFundintent(list,intentDate,lastintentDate,nextintentDate, new BigDecimal(0),everybaseMoney,actualdays, i,surplusManagMoney);
	    			   everyPeriodFundintent(list,DateUtil.addMonthsToDate(payintentPerioDateDate, i*12),lastintentDate,nextintentDate,principalRepaymentMoney,new BigDecimal(0),new BigDecimal(0), i,surplusManagMoney);
					}else{
						if(i==startPeriod){
							everyPeriodFundintent(list,DateUtil.addMonthsToDate(payintentPerioDateDate, (i-startPeriod)*12),lastintentDate,nextintentDate,principalRepaymentMoney,new BigDecimal(0),actualdays, i,surplusManagMoney);
						}else{
							everyPeriodFundintent(list,DateUtil.addMonthsToDate(payintentPerioDateDate, (i-startPeriod)*12),lastintentDate,nextintentDate,principalRepaymentMoney,everybaseMoney,actualdays, i,surplusManagMoney);
						}
					}
					surplusManagMoney=surplusManagMoney.subtract(principalRepaymentMoney);
					lastintentDate=nextintentDate;
				
	  		}
	  		if(isPreposePayAccrual == 1){
	  			everyPeriodFundintent(list, DateUtil.addMonthsToDate(payintentPerioDateDate, (payintentPeriod-1)*12),lastintentDate,intentDate,new BigDecimal(0),this.projectMoney,thirty, payintentPeriod,surplusManagMoney);
	  			everyPeriodFundintent(list, this.intentDate,lastintentDate,this.intentDate,sameprojectMoney,new BigDecimal(0),new BigDecimal(0), payintentPeriod,surplusManagMoney);
	  		}else{
	  			everyPeriodFundintent(list,this.intentDate,lastintentDate,this.intentDate,sameprojectMoney,this.projectMoney,new BigDecimal(DateUtil.getDaysBetweenDate(lastintentDate, this.intentDate)), payintentPeriod,surplusManagMoney);
	  		}

		   }/*else if (isStartDatePay.equals("1") && isInterestByOneTime == 1) { // 固定在某日
			 String[] arr=date1.split("-");
				if(Long.valueOf(payintentPerioDate).compareTo(Long.valueOf(arr[2]))<0){
					Date sDate=startDate;
					String dateStr=sdf.format(sDate);
					String[] dateArr=dateStr.split("-");
					try {
						payintentPerioDateDate=sdf.parse(dateArr[0]+"-"+dateArr[1]+"-"+payintentPerioDate);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				BigDecimal money=new BigDecimal(0);
			for (int i = 1; i <payintentPeriod; i++) {
			
					BigDecimal everybaseMoney= projectMoney;
					BigDecimal  principalRepaymentMoney=sameprojectMoney;
					BigDecimal actualdays=thirty;
					nextintentDate = DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, i), -1);
					money=money.add(everybaseMoney.multiply(actualdays).multiply(this.dayaccrual));
					
					everyPeriodFundintent(list,DateUtil.addMonthsToDate(payintentPerioDateDate, i),lastintentDate,nextintentDate,principalRepaymentMoney,new BigDecimal(0),new BigDecimal(0), i,surplusManagMoney);
					surplusManagMoney=surplusManagMoney.subtract(principalRepaymentMoney);
					lastintentDate=nextintentDate;
			}
			money=money.add(this.projectMoney.multiply(thirty).multiply(this.dayaccrual));
			SlFundIntent sf1 = calculslfundintent(this.ProjectLoadAccrual,(isPreposePayAccrual == 1?startDate:this.intentDate),startDate,this.intentDate,BigDecimal.valueOf(0), money,(isPreposePayAccrual == 1?1:payintentPeriod));
			if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
				list.add(sf1);
			}
			
			everyPeriodFundintent(list,this.intentDate,lastintentDate,intentDate,sameprojectMoney,new BigDecimal(0),new BigDecimal(0), payintentPeriod,surplusManagMoney);

		   }*/
		
		if (isStartDatePay.equals("2") && isInterestByOneTime == 0) {// 按实际放款日对日还款//不一次性支付利息
			
			for (int i =startPeriod; i <= payintentPeriod; i++) {
				
				BigDecimal everybaseMoney= projectMoney;
				BigDecimal  principalRepaymentMoney=sameprojectMoney;
				BigDecimal actualdays=new BigDecimal(360);
				nextintentDate = DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate,  (i-startPeriod)*12), -1);
				if(isPreposePayAccrual==1){
					Date intentDate=null;
					if(i==this.startPeriod){
						intentDate=startDate;
					}else{
						intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, (i - 1)*12), -1);
					}
					everyPeriodFundintent(list,intentDate,lastintentDate,nextintentDate,new BigDecimal(0),everybaseMoney,actualdays, i,surplusManagMoney);
					everyPeriodFundintent(list,DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate,  i*12), -1),lastintentDate,nextintentDate,principalRepaymentMoney,new BigDecimal(0),new BigDecimal(0), i,surplusManagMoney);
				}else{
					if(i==this.startPeriod){
						everyPeriodFundintent(list,DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate,  (i-startPeriod)*12), -1),lastintentDate,nextintentDate,principalRepaymentMoney,new BigDecimal(0),actualdays, i,surplusManagMoney);
					}else{
						everyPeriodFundintent(list,DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate,  (i-startPeriod)*12), -1),lastintentDate,nextintentDate,principalRepaymentMoney,everybaseMoney,actualdays, i,surplusManagMoney);
					}
				}
				surplusManagMoney=surplusManagMoney.subtract(principalRepaymentMoney);
				lastintentDate=nextintentDate;
			}
		}
		/*if (isStartDatePay.equals("2") && isInterestByOneTime == 1) {// 按实际放款日对日还款//一次性收取全部利息
				Date intentDate = null;
				int period=1;
				if (isPreposePayAccrual == 1) {// 前置付息
					 intentDate=startDate;
				} else {// 后置付息
					intentDate = this.intentDate;
					period=this.payintentPeriod;
				}
				everyPeriodFundintent(list,intentDate,startDate,this.intentDate,new BigDecimal(0),projectMoney,thirty.multiply(new BigDecimal(payintentPeriod)), period,surplusManagMoney);
				for (int i = 1; i <= payintentPeriod; i++) {
				
					BigDecimal  principalRepaymentMoney=sameprojectMoney;
					nextintentDate = DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate,  i), -1);
					everyPeriodFundintent(list,DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate,  i), -1),lastintentDate,nextintentDate,principalRepaymentMoney,new BigDecimal(0),new BigDecimal(0), i,surplusManagMoney);
					surplusManagMoney=surplusManagMoney.subtract(principalRepaymentMoney);
					lastintentDate=nextintentDate;
				}
			}*/
}
  public  void createOfSameprincipalsameInterestAndDayPay(List list){
	  	BigDecimal surplusManagMoney=projectMoney;
	  	Date lastintentDate = DateUtil.addDaysToDate(startDate, -1);
	  	Date nextintentDate = null;
		BigDecimal sameprojectMoney=projectMoney.divide(new BigDecimal(payintentPeriod),2,BigDecimal.ROUND_UP);
		
		if ( isInterestByOneTime == 0) {// 按实际放款日对日还款//不一次性支付利息
		
			for (int i =this.startPeriod; i <= payintentPeriod; i++) {
				Date intentDate = null;
				
				BigDecimal everybaseMoney= projectMoney;
				BigDecimal  principalRepaymentMoney=sameprojectMoney;
				BigDecimal actualdays=new BigDecimal(1);
				nextintentDate = DateUtil.addDaysToDate(startDate, (i-startPeriod-1)*1);
				if (isPreposePayAccrual == 1) {// 前置付息
					  intentDate=DateUtil.addDaysToDate(startDate, (i-1)*1);
					if(i==1){
	    				   intentDate=startDate;
	    			   }
					everyPeriodFundintent(list,intentDate,lastintentDate,nextintentDate,new BigDecimal(0),everybaseMoney,actualdays, i,surplusManagMoney);
					everyPeriodFundintent(list,DateUtil.addDaysToDate(startDate, i*1),lastintentDate,nextintentDate,principalRepaymentMoney,new BigDecimal(0),new BigDecimal(0), i,surplusManagMoney);
				}else{
					 intentDate=DateUtil.addDaysToDate(startDate, (i-startPeriod-1)*1);
					 if(i==startPeriod){
						 everyPeriodFundintent(list,intentDate,lastintentDate,nextintentDate,principalRepaymentMoney,new BigDecimal(0),new BigDecimal(0), i,surplusManagMoney);
					 }else{
						 everyPeriodFundintent(list,intentDate,lastintentDate,nextintentDate,principalRepaymentMoney,everybaseMoney,actualdays, i,surplusManagMoney);
					 }
				}
				
				surplusManagMoney=surplusManagMoney.subtract(principalRepaymentMoney);
				lastintentDate=intentDate;
			}
		}
	/*	if (isInterestByOneTime == 1) {// 按实际放款日对日还款//一次性收取全部利息
				Date intentDate = null;
				int period=1;
				if (isPreposePayAccrual == 1) {// 前置付息
					 intentDate=startDate;
				} else {// 后置付息
					intentDate = this.intentDate;
					period=this.payintentPeriod;
				}
				everyPeriodFundintent(list,intentDate,this.startDate,intentDate,new BigDecimal(0),projectMoney,new BigDecimal(payintentPeriod),period,surplusManagMoney);
				for (int i = 0; i < payintentPeriod; i++) {
					
					Date eDate=DateUtil.addDaysToDate(startDate, i*1);
					
					BigDecimal  principalRepaymentMoney=sameprojectMoney;
					nextintentDate = DateUtil.addDaysToDate(startDate, i*1);
					everyPeriodFundintent(list,eDate,lastintentDate,nextintentDate,principalRepaymentMoney,new BigDecimal(0),new BigDecimal(0), i,surplusManagMoney);
					lastintentDate=nextintentDate;
				}
			}*/
       }
	 
  
     public  void createOfSameprincipalsameInterestAndOwerPay(List list){
	  	BigDecimal surplusManagMoney=projectMoney;
	  	Date lastintentDate = this.manyStartDate; Date nextintentDate = null;
		BigDecimal sameprojectMoney=projectMoney.divide(new BigDecimal(payintentPeriod),2,BigDecimal.ROUND_UP);
		
		if (isStartDatePay.equals("2") && isInterestByOneTime == 0) {// 按实际放款日对日还款//不一次性支付利息
		/*if(payintentPeriod==1 && dayOfEveryPeriod<5){
			everyPeriodFundintent(list,intentDate,startDate,intentDate,projectMoney,projectMoney,new BigDecimal(5), 1,surplusManagMoney);
		}else{*/
			for (int i = startPeriod; i <= payintentPeriod; i++) {
				Date intentDate = null;
				
				BigDecimal everybaseMoney= projectMoney;
				BigDecimal  principalRepaymentMoney=sameprojectMoney;
				BigDecimal actualdays=new BigDecimal(dayOfEveryPeriod);
				nextintentDate =DateUtil.addDaysToDate(startDate, (i-startPeriod)*dayOfEveryPeriod-1);
				if (isPreposePayAccrual == 1) {// 前置付息
					  intentDate=DateUtil.addDaysToDate(startDate, (i-1)*dayOfEveryPeriod-1);
					if(i==1){
	    				   intentDate=startDate;
	    			   }
					everyPeriodFundintent(list,intentDate,lastintentDate,nextintentDate,new BigDecimal(0),everybaseMoney,actualdays, i,surplusManagMoney);
					everyPeriodFundintent(list,DateUtil.addDaysToDate(startDate, i*dayOfEveryPeriod-1),lastintentDate,nextintentDate,principalRepaymentMoney,new BigDecimal(0),new BigDecimal(0), i,surplusManagMoney);
				}else{
					 intentDate=DateUtil.addDaysToDate(startDate, (i-startPeriod)*dayOfEveryPeriod-1);
					 if(i==startPeriod){
						 everyPeriodFundintent(list,intentDate,lastintentDate,nextintentDate,principalRepaymentMoney,new BigDecimal(0),actualdays, i,surplusManagMoney);
					 }else{
						 everyPeriodFundintent(list,intentDate,lastintentDate,nextintentDate,principalRepaymentMoney,everybaseMoney,actualdays, i,surplusManagMoney); 
					 }
				}
				surplusManagMoney=surplusManagMoney.subtract(principalRepaymentMoney);
				lastintentDate=nextintentDate;
			}
		//}
	}
	/*if (isStartDatePay.equals("2") && isInterestByOneTime == 1) {// 按实际放款日对日还款//一次性收取全部利息
			Date intentDate = null;
			int period=1;
			if (isPreposePayAccrual == 1) {// 前置付息
				 intentDate=startDate;
			} else {// 后置付息
				intentDate = this.intentDate;
				period=this.payintentPeriod;
			}
			BigDecimal everybaseMoney= projectMoney;
			BigDecimal  principalRepaymentMoney=sameprojectMoney;
			BigDecimal actualdays=new BigDecimal(dayOfEveryPeriod).multiply(new BigDecimal(payintentPeriod));
			if(actualdays.compareTo(new BigDecimal(5))<0){
				actualdays=new BigDecimal(5);
			}
			nextintentDate = this.intentDate;
			everyPeriodFundintent(list,intentDate,lastintentDate,nextintentDate,new BigDecimal(0),everybaseMoney,actualdays,period,surplusManagMoney);
			for (int i = 1; i <= payintentPeriod; i++) {
				
					Date eDate=DateUtil.addDaysToDate(startDate, i*dayOfEveryPeriod-1);
				
				nextintentDate =DateUtil.addDaysToDate(startDate, i*dayOfEveryPeriod-1);
				everyPeriodFundintent(list,eDate,lastintentDate,nextintentDate,principalRepaymentMoney,new BigDecimal(0),new BigDecimal(0), i,surplusManagMoney);
				surplusManagMoney=surplusManagMoney.subtract(principalRepaymentMoney);
				lastintentDate=nextintentDate;
			}
			
		}*/
}

	public  void createOfSameprincipalsameInterestAndMonthPay(List list){
    	BigDecimal surplusManagMoney=projectMoney;
    	Date lastintentDate = this.manyStartDate; Date nextintentDate = null;
    	BigDecimal sameprojectMoney=projectMoney.divide(new BigDecimal(payintentPeriod-startPeriod+1),2,BigDecimal.ROUND_UP);
    	
    	if (isStartDatePay.equals("1") && isInterestByOneTime == 0) { // 固定在某日
	  		 String[] arr=date1.split("-");
				if(Long.valueOf(payintentPerioDate).compareTo(Long.valueOf(arr[2]))<0){
					Date sDate=startDate;
					String dateStr=sdf.format(sDate);
					String[] dateArr=dateStr.split("-");
					try {
						payintentPerioDateDate=sdf.parse(dateArr[0]+"-"+dateArr[1]+"-"+payintentPerioDate);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
	  		for (int i = startPeriod; i <payintentPeriod; i++) {
					
	/*				if (isPreposePayAccrual == 1) {// 前置付息
						intentDate = DateUtil.addMonthsToDate(payintentPerioDateDate, i - 1);
						if(i==1){
		    				   intentDate=startDate;
		    			   }
					} else {// 后置付息
						intentDate = DateUtil.addMonthsToDate(payintentPerioDateDate, i);
					}*/
					
					BigDecimal everybaseMoney= projectMoney;
					BigDecimal  principalRepaymentMoney=sameprojectMoney;
					BigDecimal actualdays=thirty;
					nextintentDate = DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, i-startPeriod), -1);
					if(isPreposePayAccrual == 1){
						Date intentDate=DateUtil.addMonthsToDate(payintentPerioDateDate, (i-1));
		    			   if(i==1){
		    				   intentDate=startDate;
		    			   }
	    			   everyPeriodFundintent(list,intentDate,lastintentDate,nextintentDate, new BigDecimal(0),everybaseMoney,actualdays, i,surplusManagMoney);
	    			   everyPeriodFundintent(list,DateUtil.addMonthsToDate(payintentPerioDateDate, i),lastintentDate,nextintentDate,principalRepaymentMoney,new BigDecimal(0),new BigDecimal(0), i,surplusManagMoney);
					}else{
						if(i==startPeriod){
							everyPeriodFundintent(list,DateUtil.addMonthsToDate(payintentPerioDateDate, i-startPeriod),lastintentDate,nextintentDate,principalRepaymentMoney,new BigDecimal(0),actualdays, i,surplusManagMoney);
						}else{
							everyPeriodFundintent(list,DateUtil.addMonthsToDate(payintentPerioDateDate, i-startPeriod),lastintentDate,nextintentDate,principalRepaymentMoney,everybaseMoney,actualdays, i,surplusManagMoney);
						}
					}
					surplusManagMoney=surplusManagMoney.subtract(principalRepaymentMoney);
					lastintentDate=nextintentDate;
				
	  		}
	  		if(isPreposePayAccrual == 1){
	  			everyPeriodFundintent(list, DateUtil.addMonthsToDate(payintentPerioDateDate, (payintentPeriod-1)),lastintentDate,intentDate,new BigDecimal(0),this.projectMoney,thirty, payintentPeriod,surplusManagMoney);
	  			everyPeriodFundintent(list, this.intentDate,lastintentDate,this.intentDate,sameprojectMoney,new BigDecimal(0),new BigDecimal(0), payintentPeriod,surplusManagMoney);
	  		}else{
	  			everyPeriodFundintent(list,this.intentDate,lastintentDate,this.intentDate,sameprojectMoney,this.projectMoney,new BigDecimal(DateUtil.getDaysBetweenDate(lastintentDate, this.intentDate)), payintentPeriod,surplusManagMoney);
	  		}

		   }/*else if (isStartDatePay.equals("1") && isInterestByOneTime == 1) { // 固定在某日
			 String[] arr=date1.split("-");
				if(Long.valueOf(payintentPerioDate).compareTo(Long.valueOf(arr[2]))<0){
					Date sDate=startDate;
					String dateStr=sdf.format(sDate);
					String[] dateArr=dateStr.split("-");
					try {
						payintentPerioDateDate=sdf.parse(dateArr[0]+"-"+dateArr[1]+"-"+payintentPerioDate);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				BigDecimal money=new BigDecimal(0);
			for (int i = 1; i <payintentPeriod; i++) {
			
					BigDecimal everybaseMoney= projectMoney;
					BigDecimal  principalRepaymentMoney=sameprojectMoney;
					BigDecimal actualdays=thirty;
					nextintentDate = DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, i), -1);
					money=money.add(everybaseMoney.multiply(actualdays).multiply(this.dayaccrual));
					
					everyPeriodFundintent(list,DateUtil.addMonthsToDate(payintentPerioDateDate, i),lastintentDate,nextintentDate,principalRepaymentMoney,new BigDecimal(0),new BigDecimal(0), i,surplusManagMoney);
					surplusManagMoney=surplusManagMoney.subtract(principalRepaymentMoney);
					lastintentDate=nextintentDate;
			}
			money=money.add(this.projectMoney.multiply(thirty).multiply(this.dayaccrual));
			SlFundIntent sf1 = calculslfundintent(this.ProjectLoadAccrual,(isPreposePayAccrual == 1?startDate:this.intentDate),startDate,this.intentDate,BigDecimal.valueOf(0), money,(isPreposePayAccrual == 1?1:payintentPeriod));
			if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
				list.add(sf1);
			}
			
			everyPeriodFundintent(list,this.intentDate,lastintentDate,intentDate,sameprojectMoney,new BigDecimal(0),new BigDecimal(0), payintentPeriod,surplusManagMoney);

		   }*/
		
		if (isStartDatePay.equals("2") && isInterestByOneTime == 0) {// 按实际放款日对日还款//不一次性支付利息
			
			for (int i =startPeriod; i <= payintentPeriod; i++) {
				
				BigDecimal everybaseMoney= projectMoney;
				BigDecimal  principalRepaymentMoney=sameprojectMoney;
				BigDecimal actualdays=thirty;
				nextintentDate = DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate,  i-startPeriod), -1);
				if(i==payintentPeriod){
					principalRepaymentMoney=surplusManagMoney;
				}
				if(isPreposePayAccrual==1){
					Date intentDate=null;
					if(i==this.startPeriod){
						intentDate=startDate;
					}else{
						intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, (i - 1)), -1);
					}
					everyPeriodFundintent(list,intentDate,lastintentDate,nextintentDate,new BigDecimal(0),everybaseMoney,actualdays, i,surplusManagMoney);
					everyPeriodFundintent(list,DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate,  i), -1),lastintentDate,nextintentDate,principalRepaymentMoney,new BigDecimal(0),new BigDecimal(0), i,surplusManagMoney);
				}else{
					if(i==this.startPeriod){
						everyPeriodFundintent(list,DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate,  i-startPeriod), -1),lastintentDate,nextintentDate,principalRepaymentMoney,new BigDecimal(0),actualdays, i,surplusManagMoney);
					}else{
						everyPeriodFundintent(list,DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate,  i-startPeriod), -1),lastintentDate,nextintentDate,principalRepaymentMoney,everybaseMoney,actualdays, i,surplusManagMoney);
					}
				}
				surplusManagMoney=surplusManagMoney.subtract(principalRepaymentMoney);
				lastintentDate=nextintentDate;
			}
		}
		/*if (isStartDatePay.equals("2") && isInterestByOneTime == 1) {// 按实际放款日对日还款//一次性收取全部利息
				Date intentDate = null;
				int period=1;
				if (isPreposePayAccrual == 1) {// 前置付息
					 intentDate=startDate;
				} else {// 后置付息
					intentDate = this.intentDate;
					period=this.payintentPeriod;
				}
				everyPeriodFundintent(list,intentDate,startDate,this.intentDate,new BigDecimal(0),projectMoney,thirty.multiply(new BigDecimal(payintentPeriod)), period,surplusManagMoney);
				for (int i = 1; i <= payintentPeriod; i++) {
				
					BigDecimal  principalRepaymentMoney=sameprojectMoney;
					nextintentDate = DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate,  i), -1);
					everyPeriodFundintent(list,DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate,  i), -1),lastintentDate,nextintentDate,principalRepaymentMoney,new BigDecimal(0),new BigDecimal(0), i,surplusManagMoney);
					surplusManagMoney=surplusManagMoney.subtract(principalRepaymentMoney);
					lastintentDate=nextintentDate;
				}
			}*/
	}
	
	
	  
	  
	  
	  
	  
	  //单利
    public  void createOfSingleInterestAndSeasonPay(List list){
  	  Date lastintentDate = DateUtil.addDaysToDate(startDate, -1); Date nextintentDate = null;
		if (isStartDatePay.equals("1") && isInterestByOneTime == 0) { // 固定在某日
			
			for (int i = startPeriod; i <payintentPeriod-1; i++) {
		   		 
	   			  Date startactualdate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, (i-startPeriod)*3), -1);
	   			  Date endctualdate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, (i-startPeriod+1)*3), -1);
	   			  BigDecimal actualdays=getActualdays(startactualdate,endctualdate,3,i);
	   			  Date intentDate=null;
	   			  if(isPreposePayAccrual==1){
	   				  intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, (i-1)*3), -1);
	   				  if(i==1){
	   					  intentDate=startDate;
	   				  }
	   			  }else if(isPreposePayAccrual==0){
	   				  intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, (i-startPeriod+1)*3), -1);
	   			  }
	   			BigDecimal principalRepaymentMoney = new BigDecimal("0");
				BigDecimal everybaseMoney = projectMoney;
			    nextintentDate =DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate,(i-startPeriod+1)*3), -1);
				everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, everybaseMoney, actualdays,i+1,projectMoney);
				lastintentDate=intentDate;
				 }

 			
 			   Date adate=this.intentDate;
			   Date bdate=lastintentDate;
	 			if (isPreposePayAccrual == 1) {// 前置付息
					intentDate =bdate;
				} else {// 后置付息
					intentDate = adate;
				}
	 			BigDecimal endPerioddays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(bdate), sdf.format(adate))).toString());
	 			
			    nextintentDate =adate;
	 			everyPeriodFundintent(list,intentDate,lastintentDate,nextintentDate,new BigDecimal("0"), projectMoney, endPerioddays,payintentPeriod,projectMoney);
	 			lastintentDate=intentDate;
	 			
	 			everyPeriodFundintent(list, this.intentDate,lastintentDate,nextintentDate,projectMoney, new BigDecimal("0"), endPerioddays,payintentPeriod,new BigDecimal("0"));
	 			lastintentDate=intentDate;
			
	
		   }
			
			if (isStartDatePay.equals("2") && isInterestByOneTime == 0) { // 对日  ///不一次性支付利息


				for (int i = this.startPeriod+1; i <= payintentPeriod; i++) {
	   		 
	   			  Date startactualdate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, (i-startPeriod-1)*3),-1);
	   			 
	   			  Date endctualdate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, (i-startPeriod)*3), -1);
	   			  BigDecimal actualdays=getActualdays(startactualdate,endctualdate,3,i);
	   			  Date intentDate=null;
	   			  if(isPreposePayAccrual==1){
	   				  intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, (i-1)*3), -1);
	   				  if(i==1){
	   					  intentDate=startDate;
	   				  }
	   			  }else if(isPreposePayAccrual==0){
	   				  intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, (i-startPeriod)*3), -1);
	   			  }
	   			BigDecimal principalRepaymentMoney = new BigDecimal("0");
				BigDecimal everybaseMoney = projectMoney;
			    nextintentDate =DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, (i-startPeriod)*3), -1);
				everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, everybaseMoney, actualdays,i,projectMoney);
				lastintentDate=intentDate;
				 }
				everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,projectMoney, new BigDecimal("0"), new BigDecimal("0"),payintentPeriod,new BigDecimal("0"));
				lastintentDate=intentDate;
			}
		/*	if (isInterestByOneTime == 1) { // 按实际放款日对日还款//一次性收取全部利息
				 Date startactualdate=startDate;
				 Date endctualdate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, payintentPeriod), -1);
				 BigDecimal actualdays=getActualdays(startactualdate,endctualdate,payintentPeriod,1);
				Date intentDate = null;
				Integer period=1;
				if (isPreposePayAccrual == 1) {// 前置付息
					 intentDate=startDate;
				} else {// 后置付息
					intentDate = this.intentDate;
					period=this.payintentPeriod;
				}
				BigDecimal principalRepaymentMoney = new BigDecimal("0");
				BigDecimal everybaseMoney= projectMoney;
				 nextintentDate =this.intentDate;
				everyPeriodFundintent(list,intentDate,lastintentDate,nextintentDate,principalRepaymentMoney,everybaseMoney,actualdays, period,projectMoney);
				lastintentDate=this.intentDate;
				
				
				everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,projectMoney, new BigDecimal("0"), new BigDecimal("0"),payintentPeriod,new BigDecimal("0"));
				lastintentDate=intentDate;
			}*/
  	
  }
      public  void createOfSingleInterestAndMonthPay(List list){
    	  Date lastintentDate = DateUtil.addDaysToDate(startDate, -1); Date nextintentDate = null;
		if (isStartDatePay.equals("1") && isInterestByOneTime == 0) { // 固定在某日
  			
  			for (int i = startPeriod; i <payintentPeriod-1; i++) {
  		   		 
  	   			  Date startactualdate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, i-startPeriod), -1);
  	   			  Date endctualdate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, i-startPeriod+1), -1);
  	   			  BigDecimal actualdays=getActualdays(startactualdate,endctualdate,1,i);
  	   			  Date intentDate=null;
  	   			  if(isPreposePayAccrual==1){
  	   				  intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, i-1), -1);
  	   				  if(i==1){
  	   					  intentDate=startDate;
  	   				  }
  	   			  }else if(isPreposePayAccrual==0){
  	   				  intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, i-startPeriod+1), -1);
  	   			  }
  	   			BigDecimal principalRepaymentMoney = new BigDecimal("0");
  				BigDecimal everybaseMoney = projectMoney;
  			    nextintentDate =DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, i-startPeriod+1), -1);
  				everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, everybaseMoney, actualdays,i+1,projectMoney);
  				lastintentDate=intentDate;
  				 }

   			
   			   Date adate=this.intentDate;
  			   Date bdate=lastintentDate;
  	 			if (isPreposePayAccrual == 1) {// 前置付息
  					intentDate =bdate;
  				} else {// 后置付息
  					intentDate = adate;
  				}
  	 			BigDecimal endPerioddays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(bdate), sdf.format(adate))).toString());
  	 			
  			    nextintentDate =adate;
  	 			everyPeriodFundintent(list,intentDate,lastintentDate,nextintentDate,new BigDecimal("0"), projectMoney, endPerioddays,payintentPeriod,projectMoney);
  	 			lastintentDate=intentDate;
  	 			
  	 			everyPeriodFundintent(list, this.intentDate,lastintentDate,nextintentDate,projectMoney, new BigDecimal("0"), endPerioddays,payintentPeriod,new BigDecimal("0"));
  	 			lastintentDate=intentDate;
  			
  	
  		   }
			
			if (isStartDatePay.equals("2") && isInterestByOneTime == 0) { // 对日  ///不一次性支付利息


 				for (int i = this.startPeriod+1; i <= payintentPeriod; i++) {
	   		 
	   			  Date startactualdate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, i-startPeriod-1), -1);
	   			 
	   			  Date endctualdate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, i-startPeriod), -1);
	   			  BigDecimal actualdays=getActualdays(startactualdate,endctualdate,1,i);
	   			  Date intentDate=null;
	   			  if(isPreposePayAccrual==1){
	   				  intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, i-1), -1);
	   				  if(i==1){
	   					  intentDate=startDate;
	   				  }
	   			  }else if(isPreposePayAccrual==0){
	   				  intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, i-startPeriod), -1);
	   			  }
	   			BigDecimal principalRepaymentMoney = new BigDecimal("0");
				BigDecimal everybaseMoney = projectMoney;
			    nextintentDate =DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, i-startPeriod), -1);
				everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, everybaseMoney, actualdays,i,projectMoney);
				lastintentDate=intentDate;
				 }
 				everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,projectMoney, new BigDecimal("0"), new BigDecimal("0"),payintentPeriod,new BigDecimal("0"));
 				lastintentDate=intentDate;
			}
			
    	
    }
     
    public  void createOfSingleInterestAndYearPay(List list){
    	  Date lastintentDate = DateUtil.addDaysToDate(startDate, -1); Date nextintentDate = null;
  		if (isStartDatePay.equals("1") && isInterestByOneTime == 0) { // 固定在某日
  			
  			for (int i = startPeriod; i <payintentPeriod-1; i++) {
  		   		 
  	   			  Date startactualdate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, (i-startPeriod)*12), -1);
  	   			  Date endctualdate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, (i-startPeriod+1)*12), -1);
  	   			  BigDecimal actualdays=getActualdays(startactualdate,endctualdate,12,i);
  	   			  Date intentDate=null;
  	   			  if(isPreposePayAccrual==1){
  	   				  intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, (i-1)*12), -1);
  	   				  if(i==1){
  	   					  intentDate=startDate;
  	   				  }
  	   			  }else if(isPreposePayAccrual==0){
  	   				  intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, (i-startPeriod+1)*12), -1);
  	   			  }
  	   			BigDecimal principalRepaymentMoney = new BigDecimal("0");
  				BigDecimal everybaseMoney = projectMoney;
  			    nextintentDate =DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate,(i-startPeriod+1)*12), -1);
  				everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, everybaseMoney, actualdays,i+1,projectMoney);
  				lastintentDate=intentDate;
  				 }

   			
   			   Date adate=this.intentDate;
  			   Date bdate=lastintentDate;
  	 			if (isPreposePayAccrual == 1) {// 前置付息
  					intentDate =bdate;
  				} else {// 后置付息
  					intentDate = adate;
  				}
  	 			BigDecimal endPerioddays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(bdate), sdf.format(adate))).toString());
  	 			
  			    nextintentDate =adate;
  	 			everyPeriodFundintent(list,intentDate,lastintentDate,nextintentDate,new BigDecimal("0"), projectMoney, endPerioddays,payintentPeriod,projectMoney);
  	 			lastintentDate=intentDate;
  	 			
  	 			everyPeriodFundintent(list, this.intentDate,lastintentDate,nextintentDate,projectMoney, new BigDecimal("0"), endPerioddays,payintentPeriod,new BigDecimal("0"));
  	 			lastintentDate=intentDate;
  			
  	
  		   }
  			
  			if (isStartDatePay.equals("2") && isInterestByOneTime == 0) { // 对日  ///不一次性支付利息


  				for (int i = this.startPeriod+1; i <= payintentPeriod; i++) {
  	   		 
  	   			  Date startactualdate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, (i-startPeriod-1)*12),-1);
  	   			 
  	   			  Date endctualdate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, (i-startPeriod)*12), -1);
  	   			  BigDecimal actualdays=getActualdays(startactualdate,endctualdate,12,i);
  	   			  Date intentDate=null;
  	   			  if(isPreposePayAccrual==1){
  	   				  intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, (i-1)*12), -1);
  	   				  if(i==1){
  	   					  intentDate=startDate;
  	   				  }
  	   			  }else if(isPreposePayAccrual==0){
  	   				  intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, (i-startPeriod)*12), -1);
  	   			  }
  	   			BigDecimal principalRepaymentMoney = new BigDecimal("0");
  				BigDecimal everybaseMoney = projectMoney;
  			    nextintentDate =DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, (i-startPeriod)*12), -1);
  				everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, everybaseMoney, actualdays,i,projectMoney);
  				lastintentDate=intentDate;
  				 }
  				everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,projectMoney, new BigDecimal("0"), new BigDecimal("0"),payintentPeriod,new BigDecimal("0"));
  				lastintentDate=intentDate;
  			}
  		/*	if (isInterestByOneTime == 1) { // 按实际放款日对日还款//一次性收取全部利息
  				 Date startactualdate=startDate;
  				 Date endctualdate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, payintentPeriod), -1);
  				 BigDecimal actualdays=getActualdays(startactualdate,endctualdate,payintentPeriod,1);
  				Date intentDate = null;
  				Integer period=1;
  				if (isPreposePayAccrual == 1) {// 前置付息
  					 intentDate=startDate;
  				} else {// 后置付息
  					intentDate = this.intentDate;
  					period=this.payintentPeriod;
  				}
  				BigDecimal principalRepaymentMoney = new BigDecimal("0");
  				BigDecimal everybaseMoney= projectMoney;
  				 nextintentDate =this.intentDate;
  				everyPeriodFundintent(list,intentDate,lastintentDate,nextintentDate,principalRepaymentMoney,everybaseMoney,actualdays, period,projectMoney);
  				lastintentDate=this.intentDate;
  				
  				
  				everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,projectMoney, new BigDecimal("0"), new BigDecimal("0"),payintentPeriod,new BigDecimal("0"));
  				lastintentDate=intentDate;
  			}*/
    	
    }
    public void createOfSingleInterestAndOwerPay(List list){
    	Date lastintentDate = DateUtil.addDaysToDate(startDate, -1); Date nextintentDate = null;
		if (isInterestByOneTime == 0) {// 按实际放款日对日还款//不一次性支付利息
			
				for (int i = startPeriod+1; i <= payintentPeriod; i++) {
					Date intentDate = null;
					if (isPreposePayAccrual == 1) {// 前置付息
						  intentDate=DateUtil.addDaysToDate(startDate,(i-1)*dayOfEveryPeriod-1);
						if(i==1){
		    				   intentDate=startDate;
		    			   }
					} else {// 后置付息
						  intentDate=DateUtil.addDaysToDate(startDate, (i-startPeriod)*dayOfEveryPeriod-1);
					}
	    			BigDecimal principalRepaymentMoney = new BigDecimal("0");
	  				BigDecimal everybaseMoney = projectMoney;
	  				BigDecimal actualdays=new BigDecimal(dayOfEveryPeriod);
	  				nextintentDate = DateUtil.addDaysToDate(startDate, (i-startPeriod)*dayOfEveryPeriod-1);
	  				everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, everybaseMoney, actualdays,i,projectMoney);
	  				lastintentDate=nextintentDate;	
				}
   				everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,projectMoney, new BigDecimal("0"), new BigDecimal("0"),payintentPeriod,new BigDecimal("0"));
		}
/*		if (isInterestByOneTime == 1) {// 按实际放款日对日还款//一次性收取全部利息
				Date intentDate = null;
				int period=1;
				if (isPreposePayAccrual == 1) {// 前置付息
					 intentDate=startDate;
				} else {// 后置付息
					intentDate = this.intentDate;
					period=payintentPeriod;
				}
				BigDecimal principalRepaymentMoney = new BigDecimal("0");
  				BigDecimal everybaseMoney = projectMoney;
  				BigDecimal actualdays=new BigDecimal(dayOfEveryPeriod).multiply(new BigDecimal(payintentPeriod));
  				if(actualdays.compareTo(new BigDecimal(5))<0){
  					actualdays=new BigDecimal(5);
  				}
  				nextintentDate = this.intentDate;
  				everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, everybaseMoney, actualdays,period,projectMoney);
   				
  				everyPeriodFundintent(list,this.intentDate,lastintentDate,nextintentDate,projectMoney, new BigDecimal("0"), new BigDecimal("0"),payintentPeriod,new BigDecimal("0"));
			}*/

  	  
    
	   
	   
	   
  }
  public void createOfSingleInterestAndDayPay(List list){
	  Date lastintentDate =DateUtil.addDaysToDate(startDate, -1); Date nextintentDate = null;
		
		if ( isInterestByOneTime == 0) {// 按实际放款日对日还款//不一次性支付利息
		
			for (int i = this.startPeriod; i < payintentPeriod; i++) {
				Date intentDate = null;
				//if (isPreposePayAccrual == 1) {// 前置付息
					  intentDate=DateUtil.addDaysToDate(startDate, (i-startPeriod)*1);
				/*	if(i==1){
	    				   intentDate=startDate;
	    			   }
				} else {// 后置付息
					  intentDate=DateUtil.addDaysToDate(startDate, i*1);
				}*/
				BigDecimal principalRepaymentMoney = new BigDecimal("0");
			BigDecimal everybaseMoney = projectMoney;
			BigDecimal actualdays=new BigDecimal(1);
			nextintentDate = DateUtil.addDaysToDate(startDate, (i-startPeriod)*1);
			everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, everybaseMoney, actualdays,i+1,projectMoney);
			lastintentDate=nextintentDate;
			 }
				everyPeriodFundintent(list, intentDate,startDate,nextintentDate,projectMoney, new BigDecimal("0"), new BigDecimal("0"),payintentPeriod,new BigDecimal("0"));
		}
	/*	if (isInterestByOneTime == 1) {// 按实际放款日对日还款//一次性收取全部利息
				Date intentDate = null;
				int period=1;
				if (isPreposePayAccrual == 1) {// 前置付息
					 intentDate=startDate;
				} else {// 后置付息
					intentDate = this.intentDate;
					period=this.payintentPeriod;
				}
				BigDecimal principalRepaymentMoney = new BigDecimal("0");
				BigDecimal everybaseMoney = projectMoney;
				BigDecimal actualdays=getDayfirstPerioddays(new BigDecimal(payintentPeriod),1);
				nextintentDate = this.intentDate;
				everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, everybaseMoney, actualdays,period,projectMoney);
				everyPeriodFundintent(list,this.intentDate,lastintentDate,nextintentDate,projectMoney, new BigDecimal("0"), new BigDecimal("0"),payintentPeriod,new BigDecimal("0"));
			}*/

	  
}
    public  void createOfSameprincipalandInterestAndMonthPay(List list){
    	Date lastintentDate = DateUtil.addDaysToDate(startDate, -1); Date nextintentDate = null;
    	// 月尾
    	BigDecimal periodtimemoney=periodtimemoney(sumDayaccrual.multiply(new BigDecimal(30)),projectMoney,payintentPeriod-startPeriod+1).divide(new BigDecimal(1),2,BigDecimal.ROUND_HALF_UP);
		     BigDecimal preperiodsurplus=projectMoney;

			if (isStartDatePay.equals("1") && isInterestByOneTime == 0) { // 固定在某日
																			// ////不一次性支付利息
				Date intentDate = null;
				BigDecimal comsumDayaccrualMoney=new BigDecimal(0);
				BigDecimal principalRepaymentMoney=new BigDecimal(0);
				BigDecimal everybaseMoney=new BigDecimal(0);
				/*BigDecimal firstPerioddays=getfirstPerioddays(startDate,DateUtil.addMonthsToDate(payintentPerioDateDate, 1));
				BigDecimal comsumDayaccrualMoney=preperiodsurplus.multiply(sumDayaccrual).multiply(thirty);
				BigDecimal firstsumDayaccrualMoney=preperiodsurplus.multiply(sumDayaccrual).multiply(firstPerioddays);
				
				
				
				BigDecimal firstperiodtimemoney=periodtimemoney.add(firstsumDayaccrualMoney).subtract(comsumDayaccrualMoney);
				BigDecimal principalRepaymentMoney = firstperiodtimemoney.subtract(firstsumDayaccrualMoney);
				BigDecimal everybaseMoney = preperiodsurplus;
				nextintentDate = DateUtil.addMonthsToDate(payintentPerioDateDate, 1);
				if (isPreposePayAccrual == 1) {// 前置付息
					intentDate = this.startDate;
					everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,new BigDecimal(0), everybaseMoney, firstPerioddays,1,everybaseMoney);
					everyPeriodFundintent(list, DateUtil.addMonthsToDate(payintentPerioDateDate, 1),lastintentDate,nextintentDate,principalRepaymentMoney, new BigDecimal(0), new BigDecimal(0),1,everybaseMoney);
				}else{
					intentDate = DateUtil.addMonthsToDate(payintentPerioDateDate, 1);
					everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, everybaseMoney, firstPerioddays,1,everybaseMoney);
				}
				preperiodsurplus = preperiodsurplus.subtract(principalRepaymentMoney);
				lastintentDate=intentDate;*/
				for (int i = startPeriod; i < payintentPeriod; i++) {
					
					comsumDayaccrualMoney=preperiodsurplus.multiply(sumDayaccrual).multiply(thirty);
					everybaseMoney = preperiodsurplus;
					principalRepaymentMoney = periodtimemoney.subtract(comsumDayaccrualMoney);
					nextintentDate =  DateUtil.addMonthsToDate(payintentPerioDateDate, i-startPeriod);
					if (isPreposePayAccrual == 0) {
						intentDate = DateUtil.addMonthsToDate(payintentPerioDateDate, i-startPeriod);
						everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, preperiodsurplus, thirty,i,everybaseMoney);
						
					} else if (isPreposePayAccrual == 1) {
						intentDate = DateUtil.addMonthsToDate(payintentPerioDateDate, i - 1);
						everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,new BigDecimal(0), preperiodsurplus, thirty,i,everybaseMoney);
						everyPeriodFundintent(list, DateUtil.addMonthsToDate(payintentPerioDateDate, i),lastintentDate,nextintentDate,principalRepaymentMoney, new BigDecimal(0), new BigDecimal(0),i,everybaseMoney);
					}
					
					preperiodsurplus = preperiodsurplus.subtract(principalRepaymentMoney);
					lastintentDate=nextintentDate;
                
				}
				
				BigDecimal enddays = BigDecimal.valueOf(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(DateUtil.addMonthsToDate(payintentPerioDateDate, payintentPeriod-startPeriod-1)),sdf.format(this.intentDate))));
				BigDecimal endsumDayaccrualMoney=preperiodsurplus.multiply(sumDayaccrual).multiply(enddays);
				
				comsumDayaccrualMoney=preperiodsurplus.multiply(sumDayaccrual).multiply(thirty);
				 BigDecimal endperiodtimemoney=periodtimemoney.subtract(comsumDayaccrualMoney).add(endsumDayaccrualMoney);
				 principalRepaymentMoney = endperiodtimemoney.subtract(endsumDayaccrualMoney);
				 everybaseMoney = preperiodsurplus;
				nextintentDate = this.intentDate;
				if (isPreposePayAccrual == 1) {
					everyPeriodFundintent(list,DateUtil.addMonthsToDate(payintentPerioDateDate, payintentPeriod-1) ,lastintentDate,DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod), -1),new BigDecimal(0), everybaseMoney, enddays,payintentPeriod-1,preperiodsurplus);
					everyPeriodFundintent(list, DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod), -1),lastintentDate,DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod), -1),principalRepaymentMoney, new BigDecimal(0), new BigDecimal(0),payintentPeriod,preperiodsurplus);
				}else{
					everyPeriodFundintent(list, this.intentDate,lastintentDate,this.intentDate,principalRepaymentMoney, everybaseMoney, enddays,payintentPeriod,preperiodsurplus);
				}
			}
			/*if (isStartDatePay.equals("1") && isInterestByOneTime == 1) { // 固定在某日 一次性支付利息
				BigDecimal money=new BigDecimal(0);		
				
				intentDate = DateUtil.addMonthsToDate(payintentPerioDateDate, 1);
				BigDecimal firstPerioddays=getfirstPerioddays(startDate,DateUtil.addMonthsToDate(payintentPerioDateDate, 1));
				BigDecimal comsumDayaccrualMoney=preperiodsurplus.multiply(sumDayaccrual).multiply(thirty);
				BigDecimal firstsumDayaccrualMoney=preperiodsurplus.multiply(sumDayaccrual).multiply(firstPerioddays);
				
				money=money.add(firstsumDayaccrualMoney);
				
				BigDecimal firstperiodtimemoney=periodtimemoney.add(firstsumDayaccrualMoney).subtract(comsumDayaccrualMoney);
				BigDecimal principalRepaymentMoney = firstperiodtimemoney.subtract(firstsumDayaccrualMoney);
				BigDecimal everybaseMoney = preperiodsurplus;
				nextintentDate = DateUtil.addMonthsToDate(payintentPerioDateDate, 1);
				everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, new BigDecimal(0), new BigDecimal(0),1,everybaseMoney);
				preperiodsurplus = preperiodsurplus.subtract(principalRepaymentMoney);
				lastintentDate=intentDate;
				for (int i = 2; i < payintentPeriod; i++) {
				intentDate = DateUtil.addMonthsToDate(payintentPerioDateDate, i);
				
				comsumDayaccrualMoney=preperiodsurplus.multiply(sumDayaccrual).multiply(thirty);
				money=money.add(comsumDayaccrualMoney);
				
				everybaseMoney = preperiodsurplus;
				principalRepaymentMoney = periodtimemoney.subtract(comsumDayaccrualMoney);
				nextintentDate =  DateUtil.addMonthsToDate(payintentPerioDateDate, i - 1);
				everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, new BigDecimal(0), new BigDecimal(0),i,everybaseMoney);
				preperiodsurplus = preperiodsurplus.subtract(principalRepaymentMoney);
				lastintentDate=intentDate;
				
				}
				
				BigDecimal enddays = BigDecimal.valueOf(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(DateUtil.addMonthsToDate(payintentPerioDateDate, payintentPeriod-1)),sdf.format(DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod), -1)))));
				BigDecimal endsumDayaccrualMoney=preperiodsurplus.multiply(sumDayaccrual).multiply(enddays);
				
				comsumDayaccrualMoney=preperiodsurplus.multiply(sumDayaccrual).multiply(thirty);
				
				
				BigDecimal endperiodtimemoney=periodtimemoney.subtract(comsumDayaccrualMoney).add(endsumDayaccrualMoney);
				principalRepaymentMoney = endperiodtimemoney.subtract(endsumDayaccrualMoney);
				
				money=money.add(endsumDayaccrualMoney);
				everybaseMoney = preperiodsurplus;
				nextintentDate = this.intentDate;
				
				SlFundIntent sf1 = calculslfundintent(this.ProjectLoadAccrual,(isPreposePayAccrual==1?startDate:this.intentDate),startDate,DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod), -1),BigDecimal.valueOf(0), money,(isPreposePayAccrual==1?1:payintentPeriod));
				if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
					list.add(sf1);
				}
				
				everyPeriodFundintent(list, DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod), -1),lastintentDate,DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod), -1),principalRepaymentMoney , new BigDecimal(0), new BigDecimal(0),payintentPeriod,preperiodsurplus);
				}*/
				if (isStartDatePay.equals("2") && isInterestByOneTime == 0) { // 对日  ///不一次性支付利息

					for (int i = startPeriod; i <= payintentPeriod; i++) {
						BigDecimal sumDayaccrualMoney=preperiodsurplus.multiply(sumDayaccrual).multiply(thirty);
						
						BigDecimal everybaseMoney = preperiodsurplus;
						BigDecimal	principalRepaymentMoney = periodtimemoney.subtract(sumDayaccrualMoney);
						nextintentDate =  DateUtil.addMonthsToDate(payintentPerioDateDate, i-startPeriod);
						if (isPreposePayAccrual == 1) {
							intentDate = DateUtil.addMonthsToDate(payintentPerioDateDate, i - 1);
							if (i == 1) {
								intentDate = DateUtil.parseDate(date1,"yyyy-MM-dd");
							}
							everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,new BigDecimal(0), everybaseMoney,thirty, i,preperiodsurplus);
							everyPeriodFundintent(list, DateUtil.addMonthsToDate(payintentPerioDateDate, i-startPeriod),lastintentDate,nextintentDate,principalRepaymentMoney, new BigDecimal(0),new BigDecimal(0), i,preperiodsurplus);
						} else if (isPreposePayAccrual == 0) {
							intentDate = DateUtil.addMonthsToDate(payintentPerioDateDate, i-startPeriod);
							if(i==startPeriod){
								everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, new BigDecimal(0),new BigDecimal(0), i,preperiodsurplus);
							}else{
								everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, everybaseMoney,thirty, i,preperiodsurplus);
							}
						}
						
						preperiodsurplus = preperiodsurplus.subtract(principalRepaymentMoney);
						lastintentDate=intentDate;

					}

				}
				/*if (isStartDatePay.equals("2") && isInterestByOneTime == 1) { // 对日  一次性支付利息
					BigDecimal money=new BigDecimal(0);
					for (int i = 1; i <= payintentPeriod; i++) {
						
						BigDecimal sumDayaccrualMoney=preperiodsurplus.multiply(sumDayaccrual).multiply(thirty);
						money=money.add(sumDayaccrualMoney);
						BigDecimal everybaseMoney = preperiodsurplus;
						BigDecimal	principalRepaymentMoney = periodtimemoney.subtract(sumDayaccrualMoney);
						nextintentDate =  DateUtil.addMonthsToDate(payintentPerioDateDate, i);
						if(i==payintentPeriod){
							SlFundIntent sf1 = calculslfundintent(this.ProjectLoadAccrual,(isPreposePayAccrual==1?startDate:intentDate),startDate,intentDate,BigDecimal.valueOf(0), money,(isPreposePayAccrual==1?1:payintentPeriod));
							if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
								list.add(sf1);
							}
						}
						everyPeriodFundintent(list, DateUtil.addMonthsToDate(payintentPerioDateDate, i),lastintentDate,nextintentDate,principalRepaymentMoney, new BigDecimal(0),new BigDecimal(0), i,preperiodsurplus);
						preperiodsurplus = preperiodsurplus.subtract(principalRepaymentMoney);
						lastintentDate=intentDate;

					}

				}*/


    }
    public  void createOfSameprincipalAndMonthPay(List list){
    	//一次性没有意义
		BigDecimal preperiodsurplus = projectMoney;
		Date lastintentDate = this.manyStartDate; Date nextintentDate = null;
			BigDecimal sameprojectMoney = projectMoney.divide(new BigDecimal(payintentPeriod-startPeriod+1), 100, BigDecimal.ROUND_HALF_UP);

			if (isStartDatePay.equals("1") && isInterestByOneTime == 0) { // 固定在某日
																			// ////不一次性支付利息
				Date intentDate = null;
				
				BigDecimal everybaseMoney = projectMoney;
				BigDecimal principalRepaymentMoney = sameprojectMoney;
				
				nextintentDate = payintentPerioDateDate;
				everyPeriodFundintent(list, DateUtil.addDaysToDate(startDate, -1),lastintentDate,nextintentDate,principalRepaymentMoney, new BigDecimal(0), new BigDecimal(0),this.startPeriod,everybaseMoney);
				preperiodsurplus = preperiodsurplus.subtract(principalRepaymentMoney);
				lastintentDate=nextintentDate;
				for (int i = startPeriod+1; i < payintentPeriod; i++) {
					
					everybaseMoney = preperiodsurplus;
					principalRepaymentMoney = sameprojectMoney;
					 nextintentDate = DateUtil.addMonthsToDate(payintentPerioDateDate, i-startPeriod);
					 if (isPreposePayAccrual ==1) {
							intentDate = DateUtil.addMonthsToDate(payintentPerioDateDate, i-1);
							everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,new BigDecimal(0), preperiodsurplus, thirty,i,everybaseMoney);
							everyPeriodFundintent(list, DateUtil.addMonthsToDate(payintentPerioDateDate, i),lastintentDate,nextintentDate,principalRepaymentMoney, new BigDecimal(0), new BigDecimal(0),i,everybaseMoney);
						} else if (isPreposePayAccrual == 0) {
							intentDate = DateUtil.addMonthsToDate(payintentPerioDateDate, i-startPeriod);
							everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, preperiodsurplus, thirty,i,everybaseMoney);
						}
					
					preperiodsurplus = preperiodsurplus.subtract(principalRepaymentMoney);
					lastintentDate=nextintentDate;
				}
				 BigDecimal enddays=BigDecimal.valueOf(DateUtil.getDaysBetweenDate( sdf.format(DateUtil.addMonthsToDate(payintentPerioDateDate, payintentPeriod-startPeriod-1)),sdf.format(this.intentDate)));
				 everybaseMoney = preperiodsurplus;
				 principalRepaymentMoney = sameprojectMoney;
				 nextintentDate = this.intentDate;
				 if (isPreposePayAccrual ==1) {
					 everyPeriodFundintent(list,DateUtil.addMonthsToDate(payintentPerioDateDate, payintentPeriod-1),lastintentDate,nextintentDate,new BigDecimal(0),everybaseMoney,enddays,payintentPeriod,everybaseMoney);
					 everyPeriodFundintent(list,this.intentDate,lastintentDate,nextintentDate,principalRepaymentMoney,new BigDecimal(0),new BigDecimal(0),payintentPeriod,everybaseMoney);
				 }else{
					 everyPeriodFundintent(list,this.intentDate,lastintentDate,nextintentDate,principalRepaymentMoney,everybaseMoney,enddays,payintentPeriod,everybaseMoney);
				 }
				 lastintentDate=intentDate;
			}
			
/*			if (isStartDatePay.equals("1") && isInterestByOneTime == 1) { // 固定在某日 一次性支付利息
				BigDecimal money=new BigDecimal(0);				
				Date intentDate = null;
				
				intentDate = DateUtil.addMonthsToDate(payintentPerioDateDate, 1);
				
				BigDecimal everybaseMoney = projectMoney;
				BigDecimal principalRepaymentMoney = sameprojectMoney;
				BigDecimal firstPerioddays=getfirstPerioddays(startDate,DateUtil.addMonthsToDate(payintentPerioDateDate, 1));
				nextintentDate = DateUtil.addMonthsToDate(payintentPerioDateDate, 1);
				
				money=money.add(everybaseMoney.multiply(firstPerioddays).multiply(this.dayaccrual));
				everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,principalRepaymentMoney,new BigDecimal(0),new BigDecimal(0),1,everybaseMoney);
				preperiodsurplus = preperiodsurplus.subtract(principalRepaymentMoney);
				lastintentDate=intentDate;
				
				for (int i = 2; i < payintentPeriod; i++) {
				
				intentDate = DateUtil.addMonthsToDate(payintentPerioDateDate, i);
				
				everybaseMoney = preperiodsurplus;
				principalRepaymentMoney = sameprojectMoney;
				nextintentDate = DateUtil.addMonthsToDate(payintentPerioDateDate, i);
				//利息
				money=money.add(preperiodsurplus.multiply(thirty).multiply(this.dayaccrual));
				
				everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, new BigDecimal(0),new BigDecimal(0),i,everybaseMoney);
				preperiodsurplus = preperiodsurplus.subtract(principalRepaymentMoney);
				lastintentDate=intentDate;
				}
				BigDecimal enddays=BigDecimal.valueOf(DateUtil.getDaysBetweenDate( sdf.format(DateUtil.addMonthsToDate(payintentPerioDateDate, payintentPeriod-1)),sdf.format(this.intentDate)));
				everybaseMoney = preperiodsurplus;
				principalRepaymentMoney = sameprojectMoney;
				nextintentDate = this.intentDate;
				//利息
				money=money.add(everybaseMoney.multiply(enddays).multiply(this.dayaccrual));
				
				SlFundIntent sf1 = calculslfundintent(this.ProjectLoadAccrual,(isPreposePayAccrual==1?startDate:this.intentDate),startDate,this.intentDate,BigDecimal.valueOf(0), money,(isPreposePayAccrual==1?1:payintentPeriod));
				if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
					list.add(sf1);
				}
				everyPeriodFundintent(list,this.intentDate,lastintentDate,nextintentDate,principalRepaymentMoney,new BigDecimal(0),new BigDecimal(0),payintentPeriod,everybaseMoney);
				lastintentDate=intentDate;
				}*/
			
			if (isStartDatePay.equals("2") && isInterestByOneTime == 0) { // 对日  ///不一次性支付利息

				for (int i = startPeriod; i <= payintentPeriod; i++) {
				
					BigDecimal everybaseMoney = preperiodsurplus;
					BigDecimal principalRepaymentMoney = sameprojectMoney;
					 nextintentDate = DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, i-startPeriod), -1);
					 if(isPreposePayAccrual==1){
						 intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, i-startPeriod-1), -1);
						 if(i==1){
							 intentDate=startDate;
						 }
						 everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,new BigDecimal(0), preperiodsurplus,thirty, i,everybaseMoney);
						 everyPeriodFundintent(list, DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, i-startPeriod), -1),lastintentDate,nextintentDate,principalRepaymentMoney, new BigDecimal(0),new BigDecimal(0), i,everybaseMoney);
					 }else{
						 intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, i-startPeriod), -1);
						 if(i==this.startPeriod){
							 everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, new BigDecimal(0),new BigDecimal(0), i,everybaseMoney);
						 }else{
							 everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, preperiodsurplus,thirty, i,everybaseMoney);
						 }
					 }
					
					preperiodsurplus = preperiodsurplus.subtract(principalRepaymentMoney);
					lastintentDate=intentDate;

				}

			}
			
		/*	if (isStartDatePay.equals("2") && isInterestByOneTime ==1) { // 对日  ///不一次性支付利息
				BigDecimal money=new BigDecimal(0);
				for (int i = 1; i <= payintentPeriod; i++) {
				
					 intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, i), -1);
					
					BigDecimal everybaseMoney = preperiodsurplus;
					BigDecimal principalRepaymentMoney = sameprojectMoney;
					 nextintentDate = DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, i), -1);
					 
					 money=money.add(preperiodsurplus.multiply(thirty).multiply(this.dayaccrual));
					 if(i==payintentPeriod){
						 SlFundIntent sf1 = calculslfundintent(this.ProjectLoadAccrual,(isPreposePayAccrual==1?startDate:this.intentDate),startDate,this.intentDate,BigDecimal.valueOf(0), money,(isPreposePayAccrual==1?1:payintentPeriod));
							if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
								list.add(sf1);
							}
					 }
					everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, new BigDecimal(0),new BigDecimal(0), i,everybaseMoney);
					preperiodsurplus = preperiodsurplus.subtract(principalRepaymentMoney);
					lastintentDate=intentDate;

				}

			}*/
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
			FundIntent sf3 = calculfundintent(ProjectFinanceService,intentdate,lastintentDate,nextintentDate,BigDecimal.valueOf(0), everybaseMoney.multiply(dayfinanceServiceOfRate).multiply(thirty),payintentPeriod);
			FundIntent sf4 = calculfundintent(ProjectSurplusManage,intentdate,lastintentDate,nextintentDate,BigDecimal.valueOf(0), surplusManagMoney.multiply(surplusManageOfRate),payintentPeriod);
			
			
		
			if(sf1.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){
				list.add(sf1);
				//风险金
				if(riskAccrual!=0){
				BigDecimal riskMoney=sf1.getIncomeMoney().multiply(new BigDecimal(riskAccrual/100));
				FundIntent sf5=calculfundintent(ProjectRisk,intentdate,null,null,riskMoney,new BigDecimal("0"),sf1.getPayintentPeriod());
				list.add(sf5);
				}
				}
			if (everybaseMoney.compareTo(new BigDecimal("0")) !=0){
            	BigDecimal consultingRate=new BigDecimal(0);
            	if(this.payaccrualType.equals("dayPay")){
            		consultingRate=this.daymanagementConsultingOfRate;
            	}else if(this.payaccrualType.equals("monthPay")){
            		consultingRate=this.managementConsultingOfRate;
            	}else if(this.payaccrualType.equals("seasonPay")){
            		consultingRate=this.managementConsultingOfRate.multiply(new BigDecimal(3));
            	}else if(this.payaccrualType.equals("yearPay")){
            		consultingRate=this.managementConsultingOfRate.multiply(new BigDecimal(12));
            	}else if(this.payaccrualType.equals("owerPay")){
            		consultingRate=this.daymanagementConsultingOfRate.multiply(new BigDecimal(this.dayOfEveryPeriod));
            	}
            	FundIntent sf2 = calculfundintent(ProjectManagementConsulting,intentdate,lastintentDate,nextintentDate,BigDecimal.valueOf(0), projectMoney.multiply(consultingRate),payintentPeriod);
            	if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
            }
			if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}
			if(sf4.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf4);}
			if(sf.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf);}
		}else if("SlFundIntent".equals(this.fundIntentType)){
			SlFundIntent sf = calculslfundintent(ProjectLoadRoot, intentdate,lastintentDate,nextintentDate, BigDecimal.valueOf(0),principalRepaymentMoney,payintentPeriod);
			SlFundIntent sf1 = calculslfundintent(ProjectLoadAccrual,intentdate,lastintentDate,nextintentDate,BigDecimal.valueOf(0),everybaseMoney.multiply(dayaccrual).multiply(thirty),payintentPeriod) ;
			SlFundIntent sf3 = calculslfundintent(ProjectFinanceService,intentdate,lastintentDate,nextintentDate,BigDecimal.valueOf(0),everybaseMoney.multiply(dayfinanceServiceOfRate).multiply(thirty),payintentPeriod);
			SlFundIntent sf4 = calculslfundintent(ProjectSurplusManage,intentdate,lastintentDate,nextintentDate,BigDecimal.valueOf(0),surplusManagMoney.multiply(surplusManageOfRate),payintentPeriod);
		
			
			if(sf1.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){
				list.add(sf1);
				//风险金
				if(riskAccrual!=0){/*
				BigDecimal riskMoney=sf1.getIncomeMoney().multiply(new BigDecimal(riskAccrual/100));
				SlFundIntent sf5=calculslfundintent(ProjectRisk,intentdate,null,null,riskMoney,new BigDecimal("0"),sf1.getPayintentPeriod());
				list.add(sf5);
				*/}
				}
			if (everybaseMoney.compareTo(new BigDecimal("0")) !=0){
            	BigDecimal consultingRate=new BigDecimal(0);
            	if(this.payaccrualType.equals("dayPay")){
            		consultingRate=this.daymanagementConsultingOfRate;
            	}else if(this.payaccrualType.equals("monthPay")){
            		consultingRate=this.managementConsultingOfRate;
            	}else if(this.payaccrualType.equals("seasonPay")){
            		consultingRate=this.managementConsultingOfRate.multiply(new BigDecimal(3));
            	}else if(this.payaccrualType.equals("yearPay")){
            		consultingRate=this.managementConsultingOfRate.multiply(new BigDecimal(12));
            	}else if(this.payaccrualType.equals("owerPay")){
            		consultingRate=this.daymanagementConsultingOfRate.multiply(new BigDecimal(this.dayOfEveryPeriod));
            	}
            	SlFundIntent sf2 = calculslfundintent(ProjectManagementConsulting,intentdate,lastintentDate,nextintentDate,BigDecimal.valueOf(0), projectMoney.multiply(consultingRate),payintentPeriod);
            	if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
            }
			if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}
			if(sf4.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf4);}
			if(sf.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf);}
		}else if("BpFundIntent".equals(this.fundIntentType)){
			
			
			if(personInfo.getRebateWay()!=null&&personInfo.getRebateType()!=null&&this.createType.equals("couponInterest")){
				//创建优惠券台账
				createCouponsFundIntent(list,intentdate,lastintentDate,nextintentDate,
						principalRepaymentMoney,everybaseMoney,thirty , payintentPeriod, surplusManagMoney);
			}else if(personInfo.getRebateWay()==null&&personInfo.getRebateType()==null&&this.createType.equals("couponInterest")){
				
			}else{
				//借款人台账生成
				BpFundIntent sf = calculBpFundIntent(ProjectLoadRoot, intentdate,lastintentDate,nextintentDate, BigDecimal.valueOf(0),principalRepaymentMoney,payintentPeriod);
				BpFundIntent sf1 = calculBpFundIntent(ProjectLoadAccrual,intentdate,lastintentDate,nextintentDate,BigDecimal.valueOf(0),everybaseMoney.multiply(dayaccrual).multiply(thirty),payintentPeriod) ;
				BpFundIntent sf3 = calculBpFundIntent(ProjectFinanceService,intentdate,lastintentDate,nextintentDate,BigDecimal.valueOf(0), everybaseMoney.multiply(dayfinanceServiceOfRate).multiply(thirty),payintentPeriod);
				BpFundIntent sf4 = calculBpFundIntent(ProjectSurplusManage,intentdate,lastintentDate,nextintentDate,BigDecimal.valueOf(0), surplusManagMoney.multiply(surplusManageOfRate),payintentPeriod);
					
					if(sf1.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){
						list.add(sf1);
						//风险金
						if(riskAccrual!=0){
						/*BigDecimal riskMoney=sf1.getIncomeMoney().multiply(new BigDecimal(riskAccrual/100));
						BpFundIntent sf5=calculBpFundIntent(ProjectRisk,intentdate,null,null,riskMoney,new BigDecimal("0"),sf1.getPayintentPeriod());
						list.add(sf5);*/
						}
						}
					 if (everybaseMoney.compareTo(new BigDecimal("0")) !=0){
			            	/*BigDecimal consultingRate=new BigDecimal(0);
			            	if(this.payaccrualType.equals("dayPay")){
			            		consultingRate=this.daymanagementConsultingOfRate;
			            	}else if(this.payaccrualType.equals("monthPay")){
			            		consultingRate=this.managementConsultingOfRate;
			            	}else if(this.payaccrualType.equals("seasonPay")){
			            		consultingRate=this.managementConsultingOfRate.multiply(new BigDecimal(3));
			            	}else if(this.payaccrualType.equals("yearPay")){
			            		consultingRate=this.managementConsultingOfRate.multiply(new BigDecimal(12));
			            	}else if(this.payaccrualType.equals("owerPay")){
			            		consultingRate=this.daymanagementConsultingOfRate.multiply(new BigDecimal(this.dayOfEveryPeriod));
			            	}*/
			            	BpFundIntent sf2 = calculBpFundIntent(ProjectManagementConsulting,intentdate,lastintentDate,nextintentDate,BigDecimal.valueOf(0), projectMoney.multiply(daymanagementConsultingOfRate).multiply(thirty),payintentPeriod);
			            	if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
			            }
					if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}
					
					if(sf4.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf4);}
					if(sf.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf);}
			}
			}
		}
		

	public void createCouponsFundIntent(List list,Date intentdate,Date lastintentDate,Date nextintentDate,BigDecimal principalRepaymentMoney,BigDecimal everybaseMoney,BigDecimal thirty ,int payintentPeriod,BigDecimal surplusManagMoney){
		BpFundIntent couponInterest=null;
		BpFundIntent principalCoupons=null;
		if(personInfo.getRebateWay()==1||personInfo.getRebateWay()==3){//立返，到期
			Date date = null;
			int payPeriod=0;
			if(personInfo.getRebateWay()==1){
				date= new Date();
			}else{
				date=intentdate;
				payPeriod=payintentPeriod;
			}
			if(personInfo.getRebateType()==1){//返现
				
			}else if(personInfo.getRebateType()==2){//返息 
				if(everybaseMoney.compareTo(new BigDecimal("0"))==0){
					couNum=couNum;
				}else{
					couNum=couNum+1;
				}
				couMoney=couMoney.add(everybaseMoney.multiply(dayaccrual).multiply(thirty).setScale(2,BigDecimal.ROUND_HALF_UP));
				if(couNum.compareTo(this.payintentPeriod)==0){
					couponInterest = calculBpFundIntent(CouponInterest,date,lastintentDate,nextintentDate,BigDecimal.valueOf(0),couMoney,payPeriod) ;
				}
			}else if(personInfo.getRebateType()==3){//返息现 couPrincipalMoney
				if(everybaseMoney.compareTo(new BigDecimal("0"))==0){
					couNum=couNum;
				}else{
					couNum=couNum+1;
				}
				couMoney=couMoney.add(everybaseMoney.multiply(dayaccrual).multiply(thirty).setScale(2,BigDecimal.ROUND_HALF_UP));
				couPrincipalMoney=couPrincipalMoney.add(principalRepaymentMoney);
				if(couNum.compareTo(this.payintentPeriod)==0){//返息现
					couponInterest = calculBpFundIntent(CouponInterest,date,lastintentDate,nextintentDate,BigDecimal.valueOf(0),couMoney,payPeriod) ;
					 principalCoupons = calculBpFundIntent(PrincipalCoupons,date,lastintentDate,nextintentDate,BigDecimal.valueOf(0),couPrincipalMoney,payPeriod) ;
				}
			}else if(personInfo.getRebateType()==4){//加息 
				if(everybaseMoney.compareTo(new BigDecimal("0"))==0){
					couNum=couNum;
				}else{
					couNum=couNum+1;
				}
				couMoney=couMoney.add(everybaseMoney.multiply(personInfo.getSubjoinInterest()).multiply(thirty).setScale(2,BigDecimal.ROUND_HALF_UP));
				if(couNum.compareTo(this.payintentPeriod)==0){//加息
					couponInterest = calculBpFundIntent(SubjoinInterest,date,lastintentDate,nextintentDate,BigDecimal.valueOf(0),couMoney,payPeriod) ;
				}
			}else if(personInfo.getRebateType()==0){//普通加息 
				if(everybaseMoney.compareTo(new BigDecimal("0"))==0){
					couNum=couNum;
				}else{
					couNum=couNum+1;
				}
				couMoney=couMoney.add(everybaseMoney.multiply(personInfo.getSubjoinInterest()).multiply(thirty).setScale(2,BigDecimal.ROUND_HALF_UP));
				if(couNum.compareTo(this.payintentPeriod)==0){
					couponInterest = calculBpFundIntent(SubjoinInterest,date,lastintentDate,nextintentDate,BigDecimal.valueOf(0),couMoney,payPeriod) ;
				}
			}
		}else{//随期
			if(personInfo.getRebateType()==1){//返现
				couponInterest = calculBpFundIntent(PrincipalCoupons,intentdate,lastintentDate,nextintentDate,BigDecimal.valueOf(0),principalRepaymentMoney.multiply(personInfo.getCouponInterest()).divide(personInfo.getInvestMoney(),2,BigDecimal.ROUND_HALF_UP),payintentPeriod) ;
				
			}else if(personInfo.getRebateType()==2){//返息
				 couponInterest = calculBpFundIntent(CouponInterest,intentdate,lastintentDate,nextintentDate,BigDecimal.valueOf(0),everybaseMoney.multiply(dayaccrual).multiply(thirty).setScale(2,BigDecimal.ROUND_HALF_UP),payintentPeriod) ;
			}else if(personInfo.getRebateType()==3){//返息现
				 couponInterest = calculBpFundIntent(CouponInterest,intentdate,lastintentDate,nextintentDate,BigDecimal.valueOf(0),everybaseMoney.multiply(dayaccrual).multiply(thirty).setScale(2,BigDecimal.ROUND_HALF_UP),payintentPeriod) ;
				 principalCoupons = calculBpFundIntent(PrincipalCoupons,intentdate,lastintentDate,nextintentDate,BigDecimal.valueOf(0),principalRepaymentMoney,payintentPeriod) ;
			}else if(personInfo.getRebateType()==4){//加息
				couponInterest = calculBpFundIntent(SubjoinInterest,intentdate,lastintentDate,nextintentDate,BigDecimal.valueOf(0),everybaseMoney.multiply(personInfo.getSubjoinInterest()).multiply(thirty),payintentPeriod) ;
			}else if(personInfo.getRebateType()==0){//普通加息
				couponInterest = calculBpFundIntent(CommonInterest,intentdate,lastintentDate,nextintentDate,BigDecimal.valueOf(0),everybaseMoney.multiply(personInfo.getSubjoinInterest()).multiply(thirty),payintentPeriod) ;
			}
		}
		if(couponInterest!=null){
			couNum=0;couMoney=new BigDecimal("0");couPrincipalMoney=new BigDecimal("0");
			if(couponInterest.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(couponInterest);}
		}
		if(principalCoupons!=null){
			couNum=0;couMoney=new BigDecimal("0");couPrincipalMoney=new BigDecimal("0");
			if(principalCoupons.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(principalCoupons);}
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
				    	interestStarTime=this.manyStartDate;
				    	interestEndTime=DateUtil.addDaysToDate(intentDate,-1);;
				    }
			    }else{
			    	if(this.isPreposePayAccrual==0){
			    		if(intentdate.equals(startDate) || (isHaveLin.equals("no")&&payintentPeriod==1)|| (isHaveLin.equals("yes")&&payintentPeriod==0)){
			    			interestStarTime=lastintentDate;  //第一期的时候不减加一天
			    		}else{
			    		   interestStarTime=DateUtil.addDaysToDate(lastintentDate,1);
			    		}
			    		  interestEndTime=nextintentDate;
				    }else{
			    		if(intentdate.equals(startDate)|| (isHaveLin.equals("no")&&payintentPeriod==1)|| (isHaveLin.equals("yes")&&payintentPeriod==0)){
			    			interestStarTime=lastintentDate;  //第一期的时候不加一天
			    		}else{
			    		   interestStarTime=DateUtil.addDaysToDate(lastintentDate,1);
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
			    	interestStarTime=this.manyStartDate;
			    	interestEndTime=DateUtil.addDaysToDate(intentDate,-1);;
			    }
		    }else{
		    	if(this.isPreposePayAccrual==0){
		    		if(intentdate.equals(startDate) || (isHaveLin.equals("no")&&payintentPeriod==1)|| (isHaveLin.equals("yes")&&payintentPeriod==0)){
		    			interestStarTime=lastintentDate;  //第一期的时候不减加一天
		    		}else{
		    		   interestStarTime=DateUtil.addDaysToDate(lastintentDate,1);
		    		}
		    		  interestEndTime=nextintentDate;
			    }else{
		    		if(intentdate.equals(startDate)|| (isHaveLin.equals("no")&&payintentPeriod==1)|| (isHaveLin.equals("yes")&&payintentPeriod==0)){
		    			interestStarTime=lastintentDate;  //第一期的时候不加一天
		    		}else{
		    		   interestStarTime=DateUtil.addDaysToDate(lastintentDate,1);
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
			    	interestStarTime=this.manyStartDate;
			    	interestEndTime=DateUtil.addDaysToDate(intentDate,-1);;
			    }
		    }else{
		    	if(this.isPreposePayAccrual==0){
		    		if(intentdate.equals(startDate) || (isHaveLin.equals("no")&&payintentPeriod==1)|| (isHaveLin.equals("yes")&&payintentPeriod==0)){
		    			interestStarTime=lastintentDate;  //第一期的时候不减加一天
		    		}else{
		    		   interestStarTime=DateUtil.addDaysToDate(lastintentDate,1);
		    		}
		    		  interestEndTime=nextintentDate;
			    }else{
		    		if(intentdate.equals(startDate)|| (isHaveLin.equals("no")&&payintentPeriod==1)|| (isHaveLin.equals("yes")&&payintentPeriod==0)){
		    			interestStarTime=lastintentDate;  //第一期的时候不加一天
		    		}else{
		    		   interestStarTime=DateUtil.addDaysToDate(lastintentDate,1);
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
