package com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney;

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
import com.zhiwei.credit.model.coupon.BpCoupons;
import com.zhiwei.credit.model.creditFlow.finance.BpFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.FundIntent;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlan;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanBuyinfo;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlMmOrderAssignInterest;
import com.zhiwei.credit.model.creditFlow.fund.project.BpFundProject;
import com.zhiwei.credit.model.customer.InvestPersonInfo;
import com.zhiwei.credit.service.coupon.BpCouponsService;
import com.zhiwei.credit.service.creditFlow.finance.BpFundIntentService;
import com.zhiwei.credit.service.creditFlow.fund.project.BpFundProjectService;

public class AssignInerestGenerate {
	
	/**
	 * 保证金
	 */
	public static final String ProjectRisk = "riskRate"; // 保证金

	/**
	 * 收息
	 */
	public static final String ProjectLoadAccrual = "loanInterest"; 
	/**
	 * 收本
	 */
	public static final String ProjectLoadRoot = "principalRepayment"; 
	/**
	 * 优惠券利息   ---优惠券计算的
	 */
	public static final String CouponInterest = "couponInterest";
	/**
	 * 优惠券 返息现，本金，立还
	 */
	public static final String PrincipalCoupons = "principalCoupons";
	
	/**
	 * 加息券---加息利率计算的
	 */
	public static final String SubjoinInterest = "subjoinInterest";
	/**
	 * 普通加息
	 */
	public static final String CommonInterest = "commoninterest";
	/**
	 * 募集期
	 */
	public static final String RaiseInterest = "raiseinterest";
	public static  Integer couNum=0;//利息次数
	public static BigDecimal couMoney=new BigDecimal("0");//合计利息
	public static BigDecimal couPrincipalMoney=new BigDecimal("0");//合计本金
	public static String rebateWay="";//返利方式
	public static String rebateType="";//返利类型
	public static BigDecimal addRate = new BigDecimal(0);//加息日化利率
	public static BigDecimal couponValue = new BigDecimal(0);//优惠券有效面值
	public static String createType = "";//设置是否是优惠券生成台账
	public static final BigDecimal thirty = new BigDecimal(30); 
	private  static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	private String isActualDay;
	private String isccalculateFirstAndEnd;
	private    Long orderId;
	private    String keystr;
	
	
	private    Integer isOne;
	private    Integer orderlimit;
    private    BigDecimal buyMoney;
	
	private    BigDecimal promisDayRate;
	private    BigDecimal promisMonthRate;
	
	
	private    String accrualType="singleInterest";
	private    String payaccrualType;
	private    String date1;
	private    String date2;
	private    Date startDate;
	private    Date intentDate;
	
	private    Date  payintentPerioDateDate;
	private    Date startDatecla;  //为了算投资人月末的日期
	
	private    String isStartDatePay;
	private    String payintentPerioDate;
	
	private    String mmName;
	private    Long mmplanId;
	private    Long investPersonId;
	private    String investPersonName;
	private    Integer investType;//1：收益再投资，2：提取主账户

	private static BigDecimal lin=new BigDecimal(0);
	
	public AssignInerestGenerate(PlManageMoneyPlanBuyinfo orderinfo,PlManageMoneyPlan plan){
		
		   SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
	   //    this.isActualDay=AppUtil.getIsActualDay();
	       this.isActualDay="no";
	       this.isccalculateFirstAndEnd=AppUtil.getInterest();  //算头不算尾0 算头又算尾1 AppUtil.getInterest()
	       
	       this.payaccrualType=plan.getPayaccrualType(); 
	       this.buyMoney=orderinfo.getBuyMoney();
	       this.intentDate=orderinfo.getEndinInterestTime();   
	       this.date2=sd.format(orderinfo.getEndinInterestTime()); 
	       if(null !=orderinfo.getStartinInterestTime()){
		       this.date1=sd.format(orderinfo.getStartinInterestTime());  
		       this.startDate=orderinfo.getStartinInterestTime();   
	       }
	      
	      
	       this.isStartDatePay=plan.getIsStartDatePay();
	       this.isOne = plan.getIsOne();
	       this.investType=orderinfo.getInvestType();
	       
	       this.promisMonthRate=orderinfo.getPromisMonthRate().divide(new BigDecimal(100));
	       this.promisDayRate=orderinfo.getPromisDayRate().divide(new BigDecimal(100),100,BigDecimal.ROUND_UP);//日化
	      
	       this.orderlimit=plan.getInvestlimit();
	       this.payintentPerioDate=String.valueOf(plan.getPayintentPerioDate());
	       this.mmplanId=plan.getMmplanId();
	       this.investPersonId= orderinfo.getInvestPersonId();
	       this.investPersonName=orderinfo.getInvestPersonName();
		   	List<SlFundIntent> list = new ArrayList<SlFundIntent>();
		
		   	this.orderId=orderinfo.getOrderId();
		   	this.mmName=orderinfo.getMmName();
		   	this.keystr=orderinfo.getKeystr();
		   		
			Date startDate1=startDate;
			String[] arr=date1.split("-");
			if(null!=isStartDatePay && isStartDatePay.equals("1")){
				try {
					startDate1=sd.parse(arr[0]+"-"+arr[1]+"-"+payintentPerioDate);
					
					this.startDatecla= DateUtil.addMonthsToDate(DateUtil.addDaysToDate(this.intentDate, 1),0-orderlimit);
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
	}  
	
	

public  List<PlMmOrderAssignInterest> createPlMmOrderAssignInterest(){
	createType="";
    	
    	List<PlMmOrderAssignInterest> list = new ArrayList<PlMmOrderAssignInterest>();
     
    	
    	create(list);

    	if(accrualType.equals("singleInterest")&&null!=isStartDatePay && isStartDatePay.equals("1")){
		try {
			//如果借款人的借款日期是1号的话，也就是说不管结束日期是30或31号，并说明是30或31号，而是在月末，别的也有这个问题
				if(sdf.format(this.startDatecla).split("-")[2].equals("01")){
					for(PlMmOrderAssignInterest l:list){
					if(l.getFundType().equals(ProjectLoadAccrual)){
							l.setIntentDate(sdf.parse(DateUtil.getMaxMonthDate(sdf.format(l.getIntentDate()))));
						
					}
					}
				}
				//如果借款人的借款日期是月末的话，那每期的还款日期都是月末的前一天
				if(this.startDatecla.equals(sdf.parse(DateUtil.getMaxMonthDate(sdf.format(this.startDatecla))))){
					for(PlMmOrderAssignInterest l:list){
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
	/**
	 * 投资人奖励明细台账
	 * @return
	 */
	public  List<PlMmOrderAssignInterest> createCouponsPlMmOrderAssignInterest(PlManageMoneyPlanBuyinfo orderinfo,PlManageMoneyPlan plan,BpCoupons bpCoupons){
		boolean falg = false;
		List<PlMmOrderAssignInterest> list = new ArrayList<PlMmOrderAssignInterest>();
		//计算优惠券有效面值 
		BigDecimal couponMoney = new BigDecimal(0);
		//此标可以用优惠券
		if(plan.getCoupon()!=null&&plan.getCoupon().intValue()==1){
			if(orderinfo.getCouponId()!=null&&plan.getRebateType()!=null&&plan.getRebateWay()!=null){//如果优惠券不为空
				createType="couponsCreate";
				if(plan.getRebateType()!=4){
					couponMoney = bpCoupons.getCouponValue().multiply(plan.getReturnRatio()).divide(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
				}
					rebateType=plan.getRebateType().toString();
					rebateWay=plan.getRebateWay().toString();
					if(plan.getRebateWay()==1||plan.getRebateWay()==3){//立返,到期
						if(plan.getRebateType()==1){//返现
							Date date = null;
							int payPeriod=0;
							if(plan.getRebateWay()==1){
								date= new Date();
							}else{
								date=orderinfo.getEndinInterestTime();
								payPeriod=orderlimit;
							}
							PlMmOrderAssignInterest couponInterest = calculslfundintent(PrincipalCoupons,date,null,null,new BigDecimal("0"),couponMoney,payPeriod);
							list.add(couponInterest);
							falg=true;
						}else if(plan.getRebateType()==2){//返息
							buyMoney=couponMoney;
						}else if(plan.getRebateType()==3){//返息现
							buyMoney=couponMoney; 
						}else if(plan.getRebateType()==4){//加息
							//计算加息利率
							addRate = bpCoupons.getCouponValue()
														.divide(new BigDecimal(100))
														.divide(new BigDecimal(360),100,BigDecimal.ROUND_UP);
						
						}
					}else{//随期
						if(plan.getRebateType()==1){//返现
							couponValue=couponMoney;
						}else if(plan.getRebateType()==2){//返息
							buyMoney=couponMoney;
						}else if(plan.getRebateType()==3){//返息现
							buyMoney=couponMoney;
						}else if(plan.getRebateType()==4){//加息
							//计算优惠券加息日化利率利率
							addRate = bpCoupons.getCouponValue()
														.divide(new BigDecimal(100))
														.divide(new BigDecimal(360),100,BigDecimal.ROUND_UP);
						}
					}
		}else{
			createType="cCreate";
		}
	}else{//判断此标是否有普通加息
		//普通加息日化利率
		if(plan.getAddRate()!=null&&!plan.getAddRate().equals("")&&plan.getAddRate().compareTo(new BigDecimal(0))!=0){
			createType="couponsCreate";
			addRate = plan.getAddRate().divide(new BigDecimal(100))	.divide(new BigDecimal(360),100,BigDecimal.ROUND_UP);
			rebateType=plan.getRebateType().toString();
			rebateWay=plan.getRebateWay().toString();
		}else{
			createType="cCreate";
		}
	}
	
		if(!falg){
			create(list);
		}
		
		//判断是否有	募集期利率
		if(plan.getRaiseRate()!=null&&!plan.getRaiseRate().equals("")&&plan.getRaiseRate().compareTo(new BigDecimal(0))>0){
			createType="couponsCreate";
			//募集期天数 按每个投资人实际投资日期到起息日计算天数，公式：实际投资金额*募集期天数*募集期利率/360 (立还)
			java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
				try {
					Date beginDate = format.parse(orderinfo.getBuyDatetime().toString());
					Date endDate = format.parse(format.format(orderinfo.getStartinInterestTime()));
					long  day = (endDate.getTime() - beginDate.getTime())/ (24 * 60 * 60 * 1000);
					BigDecimal raiseMoney = orderinfo.getBuyMoney().multiply(new BigDecimal(day)).
											multiply(plan.getRaiseRate()).divide(new BigDecimal(100)).
											divide(new BigDecimal(360),2,BigDecimal.ROUND_HALF_UP);
					//募集期奖励大于0才生成奖励
					if(raiseMoney.compareTo(new BigDecimal("0"))>0){
						PlMmOrderAssignInterest couponInterest = calculslfundintent(RaiseInterest,new Date(),null,null,new BigDecimal("0"),raiseMoney,0);
						if(couponInterest.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){
							list.add(couponInterest);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
		return list;
	}
   public void create(List list){
	 
	   	
	   	//按期收息，到期还本，用得是每期的实际日期（开始日期-结束日期）*日化利率即按实际天数算利息（可以乘30天，算头算尾加一天）
		if (accrualType.equals("singleInterest") && payaccrualType.equals("monthPay")) {//按期收息，按月
			createOfSingleInterestAndMonthPay(list);
	   	}
		if (accrualType.equals("singleInterest") && payaccrualType.equals("dayPay")) {// 按期收息，按日
	   		createOfSingleInterestAndDayPay(list);
	   	}
	
	
	   
   }
   public void createOfSingleInterestAndDayPay(List list){
		  Date lastintentDate = startDate; Date nextintentDate = null;
			
			if ( isOne == 0) {// 按实际放款日对日还款//不一次性支付利息
			
				for (int i = 1; i <= orderlimit; i++) {
					Date intentDate = null;
					intentDate=DateUtil.addDaysToDate(startDate, i*1);
					BigDecimal principalRepaymentMoney = new BigDecimal("0");
					BigDecimal everybaseMoney = buyMoney;
					BigDecimal actualdays=getDayfirstPerioddays(new BigDecimal(1),i);
					nextintentDate = DateUtil.addDaysToDate(startDate, i*1);
					everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, everybaseMoney, actualdays,i,buyMoney);
					lastintentDate=intentDate;
				 }
					everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,buyMoney, new BigDecimal("0"), new BigDecimal("0"),orderlimit,new BigDecimal("0"));
			}
			if (isOne == 1) {// 按实际放款日对日还款//一次性收取全部利息
					Date intentDate = null;
						intentDate = this.intentDate;
					BigDecimal principalRepaymentMoney = new BigDecimal("0");
					BigDecimal everybaseMoney = buyMoney;
					BigDecimal actualdays=getDayfirstPerioddays(new BigDecimal(orderlimit),1);
					nextintentDate = this.intentDate;
					everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, everybaseMoney, actualdays,1,buyMoney);
					everyPeriodFundintent(list,this.intentDate,lastintentDate,nextintentDate,buyMoney, new BigDecimal("0"), new BigDecimal("0"),orderlimit,new BigDecimal("0"));
				}

		  
	  }
   public  void createOfSingleInterestAndMonthPay(List list){
 	   Date lastintentDate = startDate; Date nextintentDate = null;
	   if (isStartDatePay.equals("1") && isOne == 0) { // 固定在某日
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
				intentDate = payintentPerioDateDate;
				BigDecimal principalRepaymentMoney = new BigDecimal("0");
				BigDecimal everybaseMoney = buyMoney;
				nextintentDate =payintentPerioDateDate;
				everyPeriodFundintent(list,intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, everybaseMoney, firstPerioddays,0,buyMoney);
				lastintentDate=intentDate;
				for (int i = 1; i < orderlimit; i++) {
						intentDate = DateUtil.addMonthsToDate(payintentPerioDateDate, i);
	 				Date startactualdate=DateUtil.addMonthsToDate(payintentPerioDateDate, i-1);
					Date endctualdate=DateUtil.addMonthsToDate(payintentPerioDateDate, i);
					BigDecimal actualdays=getActualdays(startactualdate,endctualdate,1,i);
					//BigDecimal actualdays=thirty;
					principalRepaymentMoney = new BigDecimal("0");
				    everybaseMoney = buyMoney;
				    nextintentDate =DateUtil.addMonthsToDate(payintentPerioDateDate, i);
					everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, everybaseMoney,actualdays,i,buyMoney);
					lastintentDate=intentDate;
				}
			
			   Date adate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"),orderlimit), -1);
			   Date bdate=lastintentDate;
			   intentDate = adate;
	 		   BigDecimal endPerioddays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(bdate), sdf.format(adate))).toString());
	 			
			   principalRepaymentMoney =new BigDecimal("0");
			   everybaseMoney = buyMoney;
			   nextintentDate =adate;
	 		   everyPeriodFundintent(list,this.intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, everybaseMoney, endPerioddays,orderlimit,everybaseMoney);
	 		   lastintentDate=intentDate;
	 			
	 		   principalRepaymentMoney =buyMoney;
			   everybaseMoney =new BigDecimal("0");
	 		   everyPeriodFundintent(list, this.intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, everybaseMoney, endPerioddays,orderlimit,new BigDecimal("0"));
	 		   lastintentDate=intentDate;
			 }
			
	
	    }
			
			if (isStartDatePay.equals("2") && isOne == 0) { // 对日  ///不一次性支付利息
				for (int i = 1; i <= orderlimit; i++) {
	   			    Date startactualdate=DateUtil.addMonthsToDate(startDate, i-1);
	   			    if(i>1){
	   				    startactualdate=DateUtil.addDaysToDate(startactualdate, -1);
	   			    }
	   			    Date endctualdate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, i), -1);
	   			    BigDecimal actualdays=getActualdays(startactualdate,endctualdate,1,i);
	   			    Date intentDate=null;
	   				intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, i), -1);
	   				BigDecimal principalRepaymentMoney = new BigDecimal("0");
	   				BigDecimal everybaseMoney = buyMoney;
	   				nextintentDate =DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, i), -1);
	   				everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, everybaseMoney, actualdays,i,buyMoney);
	   				lastintentDate=intentDate;
				 }
				everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,buyMoney, new BigDecimal("0"), new BigDecimal("0"),orderlimit,new BigDecimal("0"));
				lastintentDate=intentDate;
			}
			if (isOne == 1) { // 按实际放款日对日还款//一次性收取全部利息
				Date startactualdate=startDate;
				Date endctualdate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, orderlimit), -1);
				BigDecimal actualdays=getActualdays(startactualdate,endctualdate,orderlimit,1);
				Date intentDate = null;
				intentDate = this.intentDate;
				BigDecimal principalRepaymentMoney = new BigDecimal("0");
				BigDecimal everybaseMoney= buyMoney;
				nextintentDate =this.intentDate;
				everyPeriodFundintent(list,intentDate,lastintentDate,nextintentDate,principalRepaymentMoney,everybaseMoney,actualdays, 1,buyMoney);
				lastintentDate=this.intentDate;
				
				everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,buyMoney, new BigDecimal("0"), new BigDecimal("0"),1,new BigDecimal("0"));
				lastintentDate=intentDate;
			}
 	
 }

    
    
  
	//装配FundIntent对象
	public  void everyPeriodFundintent(List list,Date intentdate,Date lastintentDate,Date nextintentDate,BigDecimal principalRepaymentMoney,BigDecimal everybaseMoney,BigDecimal thirty ,int payintentPeriod,BigDecimal surplusManagMoney){
		if(intentdate.compareTo(this.intentDate)>0){
			return ;
		}
		if(!createType.equals("")&&createType.equals("couponsCreate")){
			//生成优惠券，加息台账
			createCouponsfundIntent(list, intentdate, lastintentDate, nextintentDate,
					 principalRepaymentMoney, everybaseMoney, thirty , payintentPeriod, surplusManagMoney);

		}else if(!createType.equals("")&&createType.equals("cCreate")){
		}else{
		
		PlMmOrderAssignInterest sf = calculslfundintent(ProjectLoadRoot, intentdate,lastintentDate,nextintentDate, BigDecimal.valueOf(0),principalRepaymentMoney,payintentPeriod);
		PlMmOrderAssignInterest sf1 = calculslfundintent(ProjectLoadAccrual,intentdate,lastintentDate,nextintentDate,BigDecimal.valueOf(0),everybaseMoney.multiply(promisDayRate).multiply(thirty),payintentPeriod) ;
			if(sf.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf);}
			if(sf1.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf1);}
		}
		
	}
	/**
	 * 优惠券奖励台账生成
	 * @param list
	 * @param intentdate
	 * @param lastintentDate
	 * @param nextintentDate
	 * @param principalRepaymentMoney
	 * @param everybaseMoney
	 * @param thirty
	 * @param payintentPeriod
	 * @param surplusManagMoney
	 */
	public void createCouponsfundIntent(List list,Date intentdate,Date lastintentDate,Date nextintentDate,
			BigDecimal principalRepaymentMoney,BigDecimal everybaseMoney,BigDecimal thirty ,int payintentPeriod,BigDecimal surplusManagMoney){
//		couNum=0;couMoney=new BigDecimal("0");couPrincipalMoney=new BigDecimal("0");
		if (isOne == 1) {
			this.orderlimit=1;
		}
		PlMmOrderAssignInterest couponInterest=null;
		PlMmOrderAssignInterest principalCoupons=null;
		if(rebateWay.equals("1")||rebateWay.equals("3")){//立返，到期
			Date date = null;
			int payPeriod=0;
			if(rebateWay.equals("1")){
				date= new Date();
			}else{
				date=intentdate;
				payPeriod=payintentPeriod;
			}
			if(rebateType.equals("1")){//返现
				
			}else if(rebateType.equals("2")){//返息 
				if(everybaseMoney.compareTo(new BigDecimal(0))!=0){
					couNum=couNum+1;
					couMoney=couMoney.add(everybaseMoney.multiply(promisDayRate).multiply(thirty).divide(new BigDecimal(1),2,BigDecimal.ROUND_HALF_UP));
					if(couNum.compareTo(this.orderlimit)==0){
						couponInterest = calculslfundintent(CouponInterest,date,lastintentDate,nextintentDate,BigDecimal.valueOf(0),couMoney,payPeriod) ;
					}
				}
				
			}else if(rebateType.equals("3")){//返息现 couPrincipalMoney
				if(everybaseMoney.compareTo(new BigDecimal(0))!=0){
					couNum=couNum+1;
					couMoney=couMoney.add(everybaseMoney.multiply(promisDayRate).multiply(thirty).divide(new BigDecimal(1),2,BigDecimal.ROUND_HALF_UP));
					couPrincipalMoney=couPrincipalMoney.add(principalRepaymentMoney);
					if(couNum.compareTo(this.orderlimit)==0){
						if(couPrincipalMoney.compareTo(new BigDecimal("0"))==0){
							couPrincipalMoney=surplusManagMoney;
						}
						couponInterest = calculslfundintent(CouponInterest,date,lastintentDate,nextintentDate,BigDecimal.valueOf(0),couMoney,payPeriod) ;
						principalCoupons = calculslfundintent(PrincipalCoupons, date,lastintentDate,nextintentDate, BigDecimal.valueOf(0),couPrincipalMoney,payPeriod);
					}
				}
			}else if(rebateType.equals("4")){//加息
				if(everybaseMoney.compareTo(new BigDecimal(0))!=0){
					couNum=couNum+1;
					couMoney=couMoney.add(everybaseMoney.multiply(addRate).multiply(thirty).divide(new BigDecimal(1),2,BigDecimal.ROUND_HALF_UP));
					if(couNum.compareTo(this.orderlimit)==0){
						couponInterest = calculslfundintent(SubjoinInterest,date,lastintentDate,nextintentDate,BigDecimal.valueOf(0),couMoney,payPeriod) ;
					}
				}
			}else if(rebateType.equals("0")){//普通加息
				if(everybaseMoney.compareTo(new BigDecimal(0))!=0){
					couNum=couNum+1;
					couMoney=couMoney.add(everybaseMoney.multiply(addRate).multiply(thirty).divide(new BigDecimal(1),2,BigDecimal.ROUND_HALF_UP));
					if(couNum.compareTo(this.orderlimit)==0){
						couponInterest = calculslfundintent(CommonInterest,date,lastintentDate,nextintentDate,BigDecimal.valueOf(0),couMoney,payPeriod) ;
					}
				}
			}
		}else{//随期
			if(rebateType.equals("1")){//返现
				couponInterest = calculslfundintent(PrincipalCoupons,intentdate,lastintentDate,nextintentDate,BigDecimal.valueOf(0),
						principalRepaymentMoney.multiply(couponValue).
						divide(buyMoney,2,BigDecimal.ROUND_HALF_UP),payintentPeriod) ;
			}else if(rebateType.equals("2")){//返息
				couponInterest = calculslfundintent(CouponInterest,intentdate,lastintentDate,nextintentDate,BigDecimal.valueOf(0),everybaseMoney.multiply(promisDayRate).multiply(thirty).divide(new BigDecimal(1),2,BigDecimal.ROUND_HALF_UP),payintentPeriod) ;
			}else if(rebateType.equals("3")){//返息现
				couponInterest = calculslfundintent(CouponInterest,intentdate,lastintentDate,nextintentDate,BigDecimal.valueOf(0),everybaseMoney.multiply(promisDayRate).multiply(thirty).divide(new BigDecimal(1),2,BigDecimal.ROUND_HALF_UP),payintentPeriod) ;
				principalCoupons = calculslfundintent(PrincipalCoupons, intentdate,lastintentDate,nextintentDate, BigDecimal.valueOf(0),principalRepaymentMoney,payintentPeriod);
			}else if(rebateType.equals("4")){//加息
				couponInterest = calculslfundintent(SubjoinInterest,intentdate,lastintentDate,nextintentDate,BigDecimal.valueOf(0),everybaseMoney.multiply(addRate).multiply(thirty).divide(new BigDecimal(1),2,BigDecimal.ROUND_HALF_UP),payintentPeriod) ;
			}else if(rebateType.equals("0")){//普通加息
				couponInterest = calculslfundintent(CommonInterest,intentdate,lastintentDate,nextintentDate,BigDecimal.valueOf(0),everybaseMoney.multiply(addRate).multiply(thirty).divide(new BigDecimal(1),2,BigDecimal.ROUND_HALF_UP),payintentPeriod) ;
				
			}
		}
		if(couponInterest!=null){
			if(couponInterest.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){
				list.add(couponInterest);
				//清空全局变量
				couNum=0;couMoney=new BigDecimal("0");couPrincipalMoney=new BigDecimal("0");
			}
		}
		if(principalCoupons!=null){
			if(principalCoupons.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){
				list.add(principalCoupons);
				//清空全局变量
				couNum=0;couMoney=new BigDecimal("0");couPrincipalMoney=new BigDecimal("0");
			}
		}
	
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
	
	public  PlMmOrderAssignInterest calculslfundintent(String fundType,Date intentdate1,Date lastintentDate,Date nextintentDate,BigDecimal paymoney,BigDecimal incomemoeny,int payintentPeriod){
		PlMmOrderAssignInterest ai1=new PlMmOrderAssignInterest();
		ai1.setOrderId(orderId);
		ai1.setFundType(fundType);
		ai1.setKeystr(keystr);
		ai1.setPeriods(payintentPeriod);
		ai1.setIncomeMoney(incomemoeny);
		ai1.setPayMoney(paymoney);
		ai1.setIntentDate(intentdate1);
		ai1.setInvestPersonId(investPersonId);
		ai1.setInvestPersonName(investPersonName);
		ai1.setMmName(mmName);
		ai1.setAfterMoney(lin);
		ai1.setMmplanId(mmplanId);
		ai1.setIsValid(Short.valueOf("0"));
		ai1.setIsCheck(Short.valueOf("0"));
		
		return  ai1;
	}
	
	public  List<PlMmOrderAssignInterest> createExperiencePlMmOrderAssignInterest(){
		createType="";
    	List<PlMmOrderAssignInterest> list = new ArrayList<PlMmOrderAssignInterest>();
    	
    	createExperience(list);
    	
    	if(accrualType.equals("singleInterest")&&null!=isStartDatePay && isStartDatePay.equals("1")){
			try {
				//如果借款人的借款日期是1号的话，也就是说不管结束日期是30或31号，并说明是30或31号，而是在月末，别的也有这个问题
					if(sdf.format(this.startDatecla).split("-")[2].equals("01")){
						for(PlMmOrderAssignInterest l:list){
						if(l.getFundType().equals(ProjectLoadAccrual)){
							l.setIntentDate(sdf.parse(DateUtil.getMaxMonthDate(sdf.format(l.getIntentDate()))));
							
						}
						}
					}
					//如果借款人的借款日期是月末的话，那每期的还款日期都是月末的前一天
					if(this.startDatecla.equals(sdf.parse(DateUtil.getMaxMonthDate(sdf.format(this.startDatecla))))){
						for(PlMmOrderAssignInterest l:list){
						if(l.getFundType().equals(ProjectLoadAccrual)){
							try {
								l.setIntentDate(DateUtil.addDaysToDate(sdf.parse(DateUtil.getMaxMonthDate(sdf.format(l.getIntentDate()))),-1));
							} catch (ParseException e) {
								e.printStackTrace();
							}
						}
						}
					}
			} catch (ParseException e) {
				e.printStackTrace();
			}
    	}
    	return list;
		
	}
	
	
	public void createExperience(List list){
	 
	   	//按期收息，到期还本，用得是每期的实际日期（开始日期-结束日期）*日化利率即按实际天数算利息（可以乘30天，算头算尾加一天）
		if (accrualType.equals("singleInterest") && payaccrualType.equals("dayPay")) {// 按期收息，按日
			createExperienceInterestAndDayPay(list);
	   	}
	   
    }
	
	public void createExperienceInterestAndDayPay(List list){
		Date lastintentDate = startDate; Date nextintentDate = null;
		if(isOne == 1){// 按实际放款日对日还款//一次性收取全部利息
			Date intentDate = null;
				intentDate = this.intentDate;
			BigDecimal principalRepaymentMoney = new BigDecimal("0");
			BigDecimal everybaseMoney = buyMoney;
			BigDecimal actualdays=new BigDecimal(orderlimit);
			nextintentDate = this.intentDate;
			everyPeriodExperienceintent(list, intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, everybaseMoney, actualdays,1,buyMoney);
		}
	  }
	
	//装配Experienceintent对象
	public  void everyPeriodExperienceintent(List list,Date intentdate,Date lastintentDate,Date nextintentDate,BigDecimal principalRepaymentMoney,BigDecimal everybaseMoney,BigDecimal thirty ,int payintentPeriod,BigDecimal surplusManagMoney){
		if(intentdate.compareTo(this.intentDate)>0){
			return ;
		}
		PlMmOrderAssignInterest sf = calculExperienceintent(ProjectLoadAccrual,intentdate,lastintentDate,nextintentDate,BigDecimal.valueOf(0),everybaseMoney.multiply(promisDayRate).multiply(thirty),payintentPeriod) ;
		if(sf.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){
			list.add(sf);
		}
    }
	
	public  PlMmOrderAssignInterest calculExperienceintent(String fundType,Date intentdate1,Date lastintentDate,Date nextintentDate,BigDecimal paymoney,BigDecimal incomemoeny,int payintentPeriod){
		PlMmOrderAssignInterest ai1=new PlMmOrderAssignInterest();
		ai1.setOrderId(orderId);
		ai1.setFundType(fundType);
		ai1.setKeystr(keystr);
		ai1.setPeriods(payintentPeriod);
		ai1.setIncomeMoney(incomemoeny);
		ai1.setPayMoney(paymoney);
		ai1.setIntentDate(intentdate1);
		ai1.setInvestPersonId(investPersonId);
		ai1.setInvestPersonName(investPersonName);
		ai1.setMmName(mmName);
		ai1.setAfterMoney(lin);
		ai1.setMmplanId(mmplanId);
		ai1.setIsValid(Short.valueOf("1"));
		ai1.setIsCheck(Short.valueOf("1"));
		
		return  ai1;
	}
	
	public  List<PlMmOrderAssignInterest> createUPlanPlMmOrderAssignInterest(){
		createType="";
    	List<PlMmOrderAssignInterest> list = new ArrayList<PlMmOrderAssignInterest>();
    	
    	createUPlan(list);

    	if(accrualType.equals("singleInterest")&&null!=isStartDatePay && isStartDatePay.equals("1")){
		try {
			//如果借款人的借款日期是1号的话，也就是说不管结束日期是30或31号，并说明是30或31号，而是在月末，别的也有这个问题
			if(sdf.format(this.startDatecla).split("-")[2].equals("01")){
				for(PlMmOrderAssignInterest l:list){
				if(l.getFundType().equals(ProjectLoadAccrual)){
						l.setIntentDate(sdf.parse(DateUtil.getMaxMonthDate(sdf.format(l.getIntentDate()))));
					
				}
				}
			}
			//如果借款人的借款日期是月末的话，那每期的还款日期都是月末的前一天
			if(this.startDatecla.equals(sdf.parse(DateUtil.getMaxMonthDate(sdf.format(this.startDatecla))))){
				for(PlMmOrderAssignInterest l:list){
				if(l.getFundType().equals(ProjectLoadAccrual)){
					try {
						l.setIntentDate(DateUtil.addDaysToDate(sdf.parse(DateUtil.getMaxMonthDate(sdf.format(l.getIntentDate()))),-1));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					
				}
				}
			}
			} catch (ParseException e) {
				e.printStackTrace();
			}
    	}
    	
    	return list;
		
	}
	
	public void createUPlan(List list){
	   	
	   	//按期收息，到期还本，用得是每期的实际日期（开始日期-结束日期）*日化利率即按实际天数算利息（可以乘30天，算头算尾加一天）
		if (accrualType.equals("singleInterest") && payaccrualType.equals("monthPay")) {//按期收息，按月
			createOfUPlanInterestAndMonthPay(list);
	   	}
	   
    }
	public  void createOfUPlanInterestAndMonthPay(List list){
				
 	    Date lastintentDate = startDate; 
 	    Date nextintentDate = null;
 	    if (isStartDatePay.equals("2") && isOne == 0 && investType == 1) { // 对日  //不一次性支付利息//收益再投资
 	    	BigDecimal previousIncomeMoney=new BigDecimal("0");
 	    	//BigDecimal  periodFund=buyMoney;
 	    	for (int i = 1; i <= orderlimit; i++) {
 	    		/*Date startactualdate=DateUtil.addMonthsToDate(startDate, i-1);
			    if(i>1){
				    startactualdate=DateUtil.addDaysToDate(startactualdate, -1);
			    }
			    Date endctualdate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, i), -1);
			    BigDecimal actualdays=getActualdays(startactualdate,endctualdate,1,i);
 	    		//BigDecimal periIntents=;//每期利息
 	    		previousIncomeMoney=previousIncomeMoney.add(periodFund.multiply(promisDayRate).multiply(actualdays).setScale(2, BigDecimal.ROUND_HALF_UP));
 	    		periodFund=periodFund.add(previousIncomeMoney);//下一期本金
			    */
 	    		Date startactualdate=DateUtil.addMonthsToDate(startDate, i-1);
			    if(i>1){
				    startactualdate=DateUtil.addDaysToDate(startactualdate, -1);
			    }
			    Date endctualdate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, i), -1);
			    BigDecimal actualdays=getActualdays(startactualdate,endctualdate,1,i);
			    Date intentDate=null;
			    intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, i), -1);
			    BigDecimal everybaseMoney = buyMoney.add(previousIncomeMoney);//下一期本金
			    nextintentDate =DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, i), -1);
			    lastintentDate=intentDate;
			    
			    previousIncomeMoney=previousIncomeMoney.add(everybaseMoney.multiply(actualdays).multiply(promisDayRate).setScale(2, BigDecimal.ROUND_HALF_UP));//利息
			}
 	    	
 	    	BigDecimal principalRepaymentMoney = new BigDecimal("0");
 	    	//这个比较特殊
 	    	PlMmOrderAssignInterest sf = calculslfundintent(ProjectLoadAccrual,intentDate,lastintentDate,nextintentDate,BigDecimal.valueOf(0),previousIncomeMoney,1) ;
 	    	if(sf.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){
 	    		list.add(sf);
 	    	}
 	    	//everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, previousIncomeMoney, new BigDecimal(1),1,buyMoney);
 	    	
		    everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,buyMoney, new BigDecimal("0"), new BigDecimal("0"),1,new BigDecimal("0"));
		    lastintentDate=intentDate;
	    }
 	    
 	   if (isStartDatePay.equals("2") && isOne == 0 && investType == 2) { // 对日  //不一次性支付利息//提取主账户
		    for (int i = 1; i <= orderlimit; i++) {
			    Date startactualdate=DateUtil.addMonthsToDate(startDate, i-1);
			    if(i>1){
				    startactualdate=DateUtil.addDaysToDate(startactualdate, -1);
			    }
			    Date endctualdate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, i), -1);
			    BigDecimal actualdays=getActualdays(startactualdate,endctualdate,1,i);
			    Date intentDate=null;
			    intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, i), -1);
			    BigDecimal principalRepaymentMoney = new BigDecimal("0");
			    BigDecimal everybaseMoney = buyMoney;
			    nextintentDate =DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, i), -1);
			    everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,principalRepaymentMoney, everybaseMoney, actualdays,i,buyMoney);
			    lastintentDate=intentDate;
			}
		    everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,buyMoney, new BigDecimal("0"), new BigDecimal("0"),orderlimit,new BigDecimal("0"));
		    lastintentDate=intentDate;
	    }
 	    
		if (isOne == 1) { // 按实际放款日对日还款//一次性收取全部利息
			Date startactualdate=startDate;
			Date endctualdate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(startDate, orderlimit), -1);
			BigDecimal actualdays=getActualdays(startactualdate,endctualdate,orderlimit,1);
			Date intentDate = null;
			intentDate = this.intentDate;
			BigDecimal principalRepaymentMoney = new BigDecimal("0");
			BigDecimal everybaseMoney= buyMoney;
			nextintentDate =this.intentDate;
			everyPeriodFundintent(list,intentDate,lastintentDate,nextintentDate,principalRepaymentMoney,everybaseMoney,actualdays, 1,buyMoney);
			lastintentDate=this.intentDate;
			
			everyPeriodFundintent(list, intentDate,lastintentDate,nextintentDate,buyMoney, new BigDecimal("0"), new BigDecimal("0"),1,new BigDecimal("0"));
			lastintentDate=intentDate;
		}
	 	
	 }
	
}
