package com.zhiwei.credit.core.project;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;

public class FundIntentListPro4 {
	/**
	 * 贷款贷出
	 */
	public static final String ProjectLoadOut = "principalLending"; // 贷款贷出/贷款本金放贷

	/**
	 * 贷款收息
	 */
	public static final String ProjectLoadAccrual = "loanInterest"; // 贷款收息/贷款利息收取
	/**
	 * 贷款收本
	 */
	public static final String ProjectLoadRoot = "principalRepayment"; // 贷款收本/贷款本金偿还
	/**
	 * 货款佣金支付
	 */
	public static final int ProjectLoadCommisin = 1749; // 货款佣金支付---无用
	
	public static final String riskRate = "riskRate";//风险保证金
	
	/**
	 * 管理咨询费用收取
	 */
	public static final String ProjectManagementConsulting = "consultationMoney"; // 管理咨询费用收取
	
	/**
	 * 财务服务费用收取
	 */
	public static final String ProjectFinanceService = "serviceMoney"; // 财务服务费用收取
	/**
	 * 融资本金借入
	 * */
	public static final String Financingborrow = "Financingborrow";
	/**
	 * 融资利息支付
	 * */
	public static final String FinancingInterest = "FinancingInterest";
	
	/**
	 * 融资本金偿还
	 * */
	public static final String FinancingRepay = "FinancingRepay";
	
	/**
	 * 融资管理咨询费用收取
	 * */
	public static final String FinancingconsultationMoney = "financingconsultationMoney";
	
	/**
	 * 融资财务服务费用收取
	 * */
	public static final String FinancingserviceMoney = "financingserviceMoney";
	/**
	 * @param calcutype
	 *            贷款方式 : 0为贷款;1为融资
	 * 
	 * @param accrualType
	 *            accrualType为 
	 * 
	 * @param dayProfit
	 *            日化利率
	 * 
	 * @param payaccrualType
	 *            -付息方式
	 * @param accrual
	 *            利率
	 * @param projectMoney
	 *            -贷款金额
	 * @param payintentPeriod
	 *            -贷款期数
	 * @param isStartDatePay
	 *            -1,固定日，2对日

	 * @param date1
	 *            起始时间
	 * @param date2
	 *            结束时间
	 * @param managementConsultingOfRate
	 *			  管理咨询费率
	 * @param financeServiceOfRate
	 * 			  财务服务费率         
	 * 	 * @param dayOfEveryPeriod
	 * 			      
	 * @return
	 */
	public static List<SlFundIntent> getFundIntentDefaultList(String calcutype,
			 String accrualType, Integer isPreposePayAccrual,
			String payaccrualType, BigDecimal accrual, BigDecimal projectMoney,
			String date1,String date2, BigDecimal managementConsultingOfRate,BigDecimal financeServiceOfRate,Integer payintentPeriod,String isStartDatePay,Integer payintentPerioDate,Integer dayOfEveryPeriod,String isHaveLending,Boolean isPreposePayConsultingCheck,Integer isInterestByOneTime,String dateModel) {
		List<SlFundIntent> list = new ArrayList<SlFundIntent>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String startDate=date1;
		String[] arr=date1.split("-");
		if(null!=isStartDatePay && isStartDatePay.equals("1")){
			startDate=arr[0]+"-"+arr[1]+"-"+payintentPerioDate;
		}
	
		//上面的代码是为了算出每期的利率
	
		SlFundIntent sf;
		SlFundIntent sf1;
		SlFundIntent sf2;
		SlFundIntent sf3;
		sf = new SlFundIntent();
		/**
		 * 放款记录
		 */
		if (null ==isHaveLending ||isHaveLending.equals("yes")) { //像提前还款什么的都是不需要生成放款记录
			sf.setFundType(FundIntentListPro3.ProjectLoadOut);// 资金类型
			sf.setIntentDate(DateUtil.parseDate(date1, "yyyy-MM-dd"));
			sf.setPayMoney(projectMoney); // 支出金额
			sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入金额
			sf.setAfterMoney(new BigDecimal("0"));
			sf.setFlatMoney(new BigDecimal("0"));
			sf.setAccrualMoney(new BigDecimal("0"));
			sf.setRemark("");
			list.add(sf);
		} 
		int alldays=DateUtil.getDaysBetweenDate(date1, date2);
//		彩云金融系统   财务服务费用收取  都是按期收的
//		sf3 = calculslfundintent(
//				FundIntentListPro3.ProjectFinanceService,            // 资金类型
//				DateUtil.parseDate(date1,"yyyy-MM-dd"),  //计划日期
//				BigDecimal.valueOf(0),                                                // 支出金额                                              // 支出金额
//				projectMoney.multiply(financeServiceOfRate).multiply(BigDecimal.valueOf(alldays)),
//				0,
//				isPreposePayAccrual
//				,payaccrualType,
//				dayOfEveryPeriod,
//				date1,
//				isInterestByOneTime,
//				date2,
//				isStartDatePay,
//				isStartDatePay.equals("1")?String.valueOf(payintentPerioDate):null,
//						accrualType          // 收入金额
//		);
//		if(sf3.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
//			list.add(sf3);
//		}
		/*
		 * 等本等息
		 */
		if (accrualType.equals("sameprincipalsameInterest") && payaccrualType.equals("dayPay")){//按日
			if(!isStartDatePay.equals("1")){//按实际放款日对日还款
				if(isInterestByOneTime==0){//不一次性首付利息
		    	   for (int i = 1; i <=payintentPeriod; i++) {
		    		   Date intentDate=null;
		    		   if(isPreposePayAccrual == 1){//前置付息
		    			   intentDate=DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i-1);
		    			   if(i==1){
		    				   intentDate=DateUtil.parseDate(date1,"yyyy-MM-dd");
		    			   }
		    		   }else{//后置付息
		    			   intentDate=DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i);
		    		   }
		    		   sf1 =calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
		    				   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.multiply(accrual) ,i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType );
		    		   sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
		    				   DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.divide(new BigDecimal(payintentPeriod),2,BigDecimal.ROUND_UP) ,i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType);
		    		   if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		    			   list.add(sf1);
		    		   }
		    		   	
					   sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
							   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,managementConsultingOfRate),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay ,null,accrualType        // 收入金额
								);
					   sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
							   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额                                              // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,financeServiceOfRate),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
								);
					   if(sf2.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
					    list.add(sf2);
					   }
					   if(sf3.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
						    list.add(sf3);
						}
		    		   list.add(sf);
		    	   }
	    	   }else if(isInterestByOneTime==1){//一次性收取全部利息
	    		   Date intentDate=null;
	    		  
	    		   if(isPreposePayAccrual == 1){//前置付息
	    			 intentDate=DateUtil.parseDate(date1,"yyyy-MM-dd");
	    			 sf1 =calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
		    				   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.multiply(accrual).multiply(new BigDecimal(payintentPeriod)) ,1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType );
	    			 if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
	    			   list.add(sf1);
	    		   }
					   sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
							   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,managementConsultingOfRate).multiply(new BigDecimal(payintentPeriod)),1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
								);
					   sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
							   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额                                              // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,financeServiceOfRate).multiply(new BigDecimal(payintentPeriod)),1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay ,null,accrualType        // 收入金额
								);
					   if(sf2.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
					    list.add(sf2);
					   }
					   if(sf3.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
						    list.add(sf3);
						}
	    		   }
	    		  
				   for (int i = 1; i <=payintentPeriod; i++) {
		    		   if(isPreposePayAccrual ==0 && i==payintentPeriod){//前置付息
		    			   sf1 =calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
		    					   DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									projectMoney.multiply(accrual).multiply(new BigDecimal(payintentPeriod)) ,payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType );
		    			   if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		    			   list.add(sf1);
		    			   }
						   sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
								   DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									FundIntentListPro3.calculAccrualnew(projectMoney,managementConsultingOfRate).multiply(new BigDecimal(payintentPeriod)),payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
									);
						   sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
								   DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额                                              // 支出金额
									FundIntentListPro3.calculAccrualnew(projectMoney,financeServiceOfRate).multiply(new BigDecimal(payintentPeriod)),payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType         // 收入金额
									);
						   if(sf2.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
						    list.add(sf2);
						   }
						   if(sf3.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
							    list.add(sf3);
							}
		    		   }
		    		
		    		   sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
		    				   DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.divide(new BigDecimal(payintentPeriod),2,BigDecimal.ROUND_UP) ,i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType );
		    		   
		    		   list.add(sf);
		    	   }  
	    	   }
	       }
		}
		if (accrualType.equals("sameprincipalsameInterest") && payaccrualType.equals("monthPay")){//按月
			if(!isStartDatePay.equals("1")){//按实际放款日对日还款
				if(isInterestByOneTime==0){//不一次性首付利息
		    	   for (int i = 1; i <=payintentPeriod; i++) {
		    		   Date intentDate=null;
		    		   if(isPreposePayAccrual == 1){//前置付息
		    			   intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i-1), -1);
		    			   if(i==1){
		    				   intentDate=DateUtil.parseDate(date1,"yyyy-MM-dd");
		    			   }
		    		   }else{//后置付息
		    			   intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i), -1);
		    		   }
		    		   sf1 =calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
		    				   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.multiply(accrual).multiply(new BigDecimal(30)) ,i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType );
		    		   sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
		    				   DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i), -1),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.divide(new BigDecimal(payintentPeriod),2,BigDecimal.ROUND_UP) ,i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType );
		    		   if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		    		   list.add(sf1);
		    		   }
					   sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
							   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,managementConsultingOfRate).multiply(new BigDecimal(30)),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
								);
					   sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
							   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额                                              // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,financeServiceOfRate).multiply(new BigDecimal(30)),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
								);
					   if(sf2.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
					    list.add(sf2);
					   }
					   if(sf3.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
						    list.add(sf3);
						}
		    		   list.add(sf);
		    	   }
	    	   }else if(isInterestByOneTime==1){//一次性收取全部利息
	    		   Date intentDate=null;
	    		  
	    		   if(isPreposePayAccrual == 1){//前置付息
	    			 intentDate=DateUtil.parseDate(date1,"yyyy-MM-dd");
	    			 sf1 =calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
		    				   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.multiply(accrual).multiply(new BigDecimal(payintentPeriod)).multiply(new BigDecimal(30)) ,1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType);
	    			 if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
	    			   list.add(sf1);
	    			 }
					   sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
							   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,managementConsultingOfRate).multiply(new BigDecimal(30)).multiply(new BigDecimal(payintentPeriod)),1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
								);
					   sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
							   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额                                              // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,financeServiceOfRate).multiply(new BigDecimal(30)).multiply(new BigDecimal(payintentPeriod)),1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
								);
					   if(sf2.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
					    list.add(sf2);
					   }
					   if(sf3.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
						    list.add(sf3);
						}
	    		   }
	    		  
				   for (int i = 1; i <=payintentPeriod; i++) {
		    		 
		    		   if(isPreposePayAccrual == 0 && i==payintentPeriod){
		    			   sf1 =calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
		    					   DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), payintentPeriod),-1),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									projectMoney.multiply(accrual).multiply(new BigDecimal(payintentPeriod)).multiply(new BigDecimal(30)) ,payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType);
		    			   if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		    			   list.add(sf1);
		    			   }
						   sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
								   DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), payintentPeriod),-1),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									FundIntentListPro3.calculAccrualnew(projectMoney,managementConsultingOfRate).multiply(new BigDecimal(30)).multiply(new BigDecimal(payintentPeriod)),payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
									);
						   sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
								   DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), payintentPeriod),-1),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额                                              // 支出金额
									FundIntentListPro3.calculAccrualnew(projectMoney,financeServiceOfRate).multiply(new BigDecimal(30)).multiply(new BigDecimal(payintentPeriod)),payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
									);
						   if(sf2.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
						    list.add(sf2);
						   }
						   if(sf3.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
							    list.add(sf3);
							}
		    		   }
		    		
		    		   sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
		    				   DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i), -1),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.divide(new BigDecimal(payintentPeriod),2,BigDecimal.ROUND_UP) ,i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType );
		    		   
		    		   list.add(sf);
		    	   }  
	    	   }
	       }else{//等本等息  以月为期 固定日还款  add by gao for zyht
	    	   //zyht start
		    	 //按实际放款日对日还款
					if(isInterestByOneTime==0){//不一次性首付利息
						//从当月放款日开始计息    add by gao
					Date date1Date = DateUtil.parseDate(date1, "yyyy-MM-dd");
					int additionalDays =0;
						
			    	   for (int i = 1; i <=payintentPeriod; i++) {
			    		   Date intentDate=null;
			    		   if(isPreposePayAccrual == 1){//前置付息
			    			   intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), 0), -1);
			    			   if(i==1){
			    				   intentDate=DateUtil.parseDate(date1,"yyyy-MM-dd");
			    			   }
			    		   }else{//后置付息
			    			   if( date1Date.getDate() > payintentPerioDate){
									intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.setDayToDate(date1Date, payintentPerioDate),1),0);
								}else{
									intentDate=DateUtil.addDaysToDate(DateUtil.setRoundDayToDate(date1Date, payintentPerioDate),0);
								}
			    		   }
			    		   additionalDays = Math.abs(DateUtil.getDaysBetweenDate((intentDate),date1Date));
			    		   if(additionalDays>15){
			    			   intentDate = DateUtil.addMonthsToDate(intentDate, i-1);
			    			   intentDate = DateUtil.setRoundDayToDate(intentDate,payintentPerioDate);
			    		   }else{
			    			   intentDate = DateUtil.addMonthsToDate(intentDate, i);
			    			   intentDate = DateUtil.setRoundDayToDate(intentDate,payintentPerioDate);
			    		   }
			    		   if(i==1){//第一期计算要加上  固定放款日之间的几天利息
//			    			   if(additionalDays>15){
//			    				   sf1 =calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
//			    						   intentDate,  //计划日期
//			    						   BigDecimal.valueOf(0),                                                // 支出金额
//			    						   projectMoney.multiply(accrual).multiply(new BigDecimal(additionalDays)).setScale(10, BigDecimal.ROUND_HALF_UP) ,i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,payintentPerioDate.toString(),accrualType );
//			    			   }else{
//			    				   sf1 =calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
//			    						   intentDate,  //计划日期
//			    						   BigDecimal.valueOf(0),                                                // 支出金额
//			    						   projectMoney.multiply(accrual).multiply(new BigDecimal(30+additionalDays)).setScale(10, BigDecimal.ROUND_HALF_UP) ,i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,payintentPerioDate.toString(),accrualType);
//			    			   }
//			    			   if(additionalDays>15){
//				    			   sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
//										   intentDate,  //计划日期
//											BigDecimal.valueOf(0),                                                // 支出金额
//											FundIntentListPro3.calculAccrualnew(projectMoney,managementConsultingOfRate).multiply(new BigDecimal(additionalDays)).setScale(10, BigDecimal.ROUND_HALF_UP),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,payintentPerioDate.toString(),accrualType          // 收入金额
//											);
//				    		   }else{
//				    			   sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
//										   intentDate,  //计划日期
//											BigDecimal.valueOf(0),                                                // 支出金额
//											FundIntentListPro3.calculAccrualnew(projectMoney,managementConsultingOfRate).multiply(new BigDecimal(30+additionalDays)).setScale(10, BigDecimal.ROUND_HALF_UP),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,payintentPerioDate.toString(),accrualType          // 收入金额
//											);
//				    		   }
			    			   sf1 =calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
		    						   intentDate,  //计划日期
		    						   BigDecimal.valueOf(0),                                                // 支出金额
		    						   projectMoney.multiply(accrual).multiply(new BigDecimal(30)).setScale(10, BigDecimal.ROUND_HALF_UP) ,i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,payintentPerioDate.toString(),accrualType );
			    			   sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
									   intentDate,  //计划日期
										BigDecimal.valueOf(0),                                                // 支出金额
										FundIntentListPro3.calculAccrualnew(projectMoney,managementConsultingOfRate).multiply(new BigDecimal(30)).setScale(10, BigDecimal.ROUND_HALF_UP),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,payintentPerioDate.toString(),accrualType          // 收入金额
										);
		    				   sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
				    				   intentDate,
										BigDecimal.valueOf(0),                                                // 支出金额
										projectMoney.divide(new BigDecimal(payintentPeriod),2,BigDecimal.ROUND_UP) ,i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,payintentPerioDate.toString(),accrualType );
				    		   if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
				    			   list.add(sf1);
				    		   }
							   if(sf2.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
							    list.add(sf2);
							   }
			    		   }else{
			    			   sf1 =calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
				    				   intentDate,  //计划日期
										BigDecimal.valueOf(0),                                                // 支出金额
										projectMoney.multiply(accrual).multiply(new BigDecimal(30)).setScale(10, BigDecimal.ROUND_HALF_UP) ,i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,payintentPerioDate.toString(),accrualType);
				    		   sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
				    				   intentDate,
										BigDecimal.valueOf(0),                                                // 支出金额
										projectMoney.divide(new BigDecimal(payintentPeriod),2,BigDecimal.ROUND_UP) ,i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,payintentPerioDate.toString(),accrualType );
				    		   if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
				    			   list.add(sf1);
				    		   }
							   sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
									   intentDate,  //计划日期
										BigDecimal.valueOf(0),                                                // 支出金额
										FundIntentListPro3.calculAccrualnew(projectMoney,managementConsultingOfRate).multiply(new BigDecimal(30)).setScale(10, BigDecimal.ROUND_HALF_UP),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,payintentPerioDate.toString(),accrualType          // 收入金额
										);
							   if(sf2.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
							    list.add(sf2);
							   }
			    		   }
			    		   list.add(sf);
			    	   }
		    	   }else if(isInterestByOneTime==1){//一次性收取全部利息
		    		   Date intentDate=null;
		    		  
		    		   if(isPreposePayAccrual == 1){//前置付息
		    			 intentDate=DateUtil.parseDate(date1,"yyyy-MM-dd");
		    			 sf1 =calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
			    				   intentDate,  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									projectMoney.multiply(accrual).multiply(new BigDecimal(payintentPeriod)).multiply(new BigDecimal(30)).setScale(10, BigDecimal.ROUND_HALF_UP) ,1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType);
		    			 if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		    			   list.add(sf1);
		    			 }
						   sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
								   intentDate,  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									FundIntentListPro3.calculAccrualnew(projectMoney,managementConsultingOfRate).multiply(new BigDecimal(30)).setScale(10, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(payintentPeriod)),1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
									);
						  /* sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
								   intentDate,  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额                                              // 支出金额
									FundIntentListPro3.calculAccrualnew(projectMoney,financeServiceOfRate).multiply(new BigDecimal(30)).multiply(new BigDecimal(payintentPeriod)),1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType,defaultInterest          // 收入金额
									);*/
						   if(sf2.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
						    list.add(sf2);
						   }
						  /* if(sf3.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
							    list.add(sf3);
							}*/
		    		   }
		    		  
					   for (int i = 1; i <=payintentPeriod; i++) {
			    		 
			    		   if(isPreposePayAccrual == 0 && i==payintentPeriod){
			    			   sf1 =calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
			    					   DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), payintentPeriod),-1),  //计划日期
										BigDecimal.valueOf(0),                                                // 支出金额
										projectMoney.multiply(accrual).multiply(new BigDecimal(payintentPeriod)).multiply(new BigDecimal(30)).setScale(10, BigDecimal.ROUND_HALF_UP) ,payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType);
			    			   if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
			    			   list.add(sf1);
			    			   }
							   sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
									   DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), payintentPeriod),-1),  //计划日期
										BigDecimal.valueOf(0),                                                // 支出金额
										FundIntentListPro3.calculAccrualnew(projectMoney,managementConsultingOfRate).multiply(new BigDecimal(30)).setScale(10, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(payintentPeriod)),payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
										);
							 /*  sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
									   DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), payintentPeriod),-1),  //计划日期
										BigDecimal.valueOf(0),                                                // 支出金额                                              // 支出金额
										FundIntentListPro3.calculAccrualnew(projectMoney,financeServiceOfRate).multiply(new BigDecimal(30)).multiply(new BigDecimal(payintentPeriod)),payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType,defaultInterest          // 收入金额
										);*/
							   if(sf2.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
							    list.add(sf2);
							   }
							  /* if(sf3.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
								    list.add(sf3);
								}*/
			    		   }
			    		
			    		   sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
			    				   DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i), -1),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									projectMoney.divide(new BigDecimal(payintentPeriod),2,BigDecimal.ROUND_UP) ,i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType );
			    		   
			    		   list.add(sf);
			    	   }  
		    	   }
		    	   //zyht end
		       }
		}
		if (accrualType.equals("sameprincipalsameInterest") && payaccrualType.equals("seasonPay")){//按季
			if(!isStartDatePay.equals("1")){//按实际放款日对日还款
				if(isInterestByOneTime==0){//不一次性首付利息
		    	   for (int i = 1; i <=payintentPeriod; i++) {
		    		   Date intentDate=null;
		    		   if(isPreposePayAccrual == 1){//前置付息
		    			   intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i-1)*3), -1);
		    			   if(i==1){
		    				   intentDate=DateUtil.parseDate(date1,"yyyy-MM-dd");
		    			   }
		    		   }else{//后置付息
		    			   intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*3), -1);
		    		   }
		    		   sf1 =calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
		    				   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.multiply(accrual).multiply(new BigDecimal(30)).multiply(new BigDecimal(3)) ,i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType );
		    		   sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
		    				   DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*3), -1),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.divide(new BigDecimal(payintentPeriod),2,BigDecimal.ROUND_UP) ,i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType );
		    		   if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		    		   list.add(sf1);
		    		   }
					   sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
							   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,managementConsultingOfRate).multiply(new BigDecimal(30)).multiply(new BigDecimal(3)),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
								);
					   sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
							   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额                                              // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,financeServiceOfRate).multiply(new BigDecimal(30)).multiply(new BigDecimal(3)),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
								);
					   if(sf2.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
					    list.add(sf2);
					   }
					   if(sf3.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
						    list.add(sf3);
						}
		    		   list.add(sf);
		    	   }
	    	   }else if(isInterestByOneTime==1){//一次性收取全部利息
	    		   Date intentDate=null;
	    		   
	    		   if(isPreposePayAccrual == 1){//前置付息
	    			 intentDate=DateUtil.parseDate(date1,"yyyy-MM-dd");
	    			 sf1 =calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
		    				   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.multiply(accrual).multiply(new BigDecimal(payintentPeriod)).multiply(new BigDecimal(30)).multiply(new BigDecimal(3)) ,1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType );
	    			 if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){  
	    			 list.add(sf1);
	    			 } 	
					   sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
							   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,managementConsultingOfRate).multiply(new BigDecimal(30)).multiply(new BigDecimal(payintentPeriod)).multiply(new BigDecimal(3)),1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay ,null,accrualType         // 收入金额
								);
					   sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
							   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额                                              // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,financeServiceOfRate).multiply(new BigDecimal(30)).multiply(new BigDecimal(payintentPeriod)).multiply(new BigDecimal(3)),1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay ,null,accrualType         // 收入金额
								);
					   if(sf2.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
					    list.add(sf2);
					   }
					   if(sf3.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
						    list.add(sf3);
						}
	    		   }else if(isPreposePayAccrual ==0){//后置付息
	    			   intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), payintentPeriod*3), -1);
	    			  
	    		   }
	    		   
				   for (int i = 1; i <=payintentPeriod; i++) {
		    		  
		    		   if(isPreposePayAccrual == 0 && i==payintentPeriod){
		    			   sf1 =calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
		    					   DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*3),-1),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									projectMoney.multiply(accrual).multiply(new BigDecimal(payintentPeriod)).multiply(new BigDecimal(30)).multiply(new BigDecimal(3)) ,payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType );
		    			   if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		    			   list.add(sf1);
		    			   }
						   sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
								   DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*3),-1),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									FundIntentListPro3.calculAccrualnew(projectMoney,managementConsultingOfRate).multiply(new BigDecimal(30)).multiply(new BigDecimal(payintentPeriod)).multiply(new BigDecimal(3)),payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
									);
						   sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
								   DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*3),-1),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额                                              // 支出金额
									FundIntentListPro3.calculAccrualnew(projectMoney,financeServiceOfRate).multiply(new BigDecimal(30)).multiply(new BigDecimal(payintentPeriod)).multiply(new BigDecimal(3)),payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType         // 收入金额
									);
						   if(sf2.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
						    list.add(sf2);
						   }
						   if(sf3.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
							    list.add(sf3);
							}
		    		   }
		    		
		    		   sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
		    				   DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*3),-1),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.divide(new BigDecimal(payintentPeriod),2,BigDecimal.ROUND_UP) ,i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType );
		    		   
		    		   list.add(sf);
		    	   }  
	    	   }
	       }
		}
		if (accrualType.equals("sameprincipalsameInterest") && payaccrualType.equals("yearPay")){//按年
			if(null!=dateModel && dateModel.equals("dateModel_360")){
 			   accrual=accrual.multiply(new BigDecimal(360));
 			   managementConsultingOfRate=managementConsultingOfRate.multiply(new BigDecimal(360));
 			   //financeServiceOfRate=financeServiceOfRate.multiply(new BigDecimal(360));
 		   }else{
 			   accrual=accrual.multiply(new BigDecimal(365));
 			   managementConsultingOfRate=managementConsultingOfRate.multiply(new BigDecimal(365));
 			  // financeServiceOfRate=financeServiceOfRate.multiply(new BigDecimal(365));
 		   }
			if(!isStartDatePay.equals("1")){//按实际放款日对日还款
				if(isInterestByOneTime==0){//不一次性支付利息
		    	   for (int i = 1; i <=payintentPeriod; i++) {
		    		   Date intentDate=null;
		    		   if(isPreposePayAccrual == 1){//前置付息
		    			   intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i-1)*12), -1);
		    			   if(i==1){
		    				   intentDate=DateUtil.parseDate(date1,"yyyy-MM-dd");
		    			   }
		    		   }else{//后置付息
		    			   intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*12), -1);
		    		   }
		    		   
		    		   sf1 =calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
		    				   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.multiply(accrual) ,i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType );
		    		   sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
		    				   DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*12), -1),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.divide(new BigDecimal(payintentPeriod),2,BigDecimal.ROUND_UP) ,i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType );
		    		   if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		    		   list.add(sf1);
		    		   }
					   sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
							   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,managementConsultingOfRate),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
								);
					   sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
							   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额                                              // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,financeServiceOfRate),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
								);
					   if(sf2.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
					    list.add(sf2);
					   }
					   if(sf3.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
						    list.add(sf3);
						}
		    		   list.add(sf);
		    	   }
	    	   }else if(isInterestByOneTime==1){//一次性收取全部利息
	    		   Date intentDate=null;
	    		   
	    		   if(isPreposePayAccrual == 1){//前置付息
	    			 intentDate=DateUtil.parseDate(date1,"yyyy-MM-dd");
	    			 sf1 =calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
		    				   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.multiply(accrual).multiply(new BigDecimal(payintentPeriod)) ,1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType );
	    			 if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
	    			   list.add(sf1);
	    			 }	
					   sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
							   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,managementConsultingOfRate).multiply(new BigDecimal(payintentPeriod)),1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
								);
					   sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
							   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额                                              // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,financeServiceOfRate).multiply(new BigDecimal(payintentPeriod)),1 ,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType         // 收入金额
								);
					   if(sf2.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
					    list.add(sf2);
					   }
					   if(sf3.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
						    list.add(sf3);
						}
	    		   }
	    		   
				   for (int i = 1; i <=payintentPeriod; i++) {
		    		  
		    		   if(isPreposePayAccrual == 0 && i==payintentPeriod){
		    			   sf1 =calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
		    					   DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*12),-1),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									projectMoney.multiply(accrual).multiply(new BigDecimal(payintentPeriod)) ,payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType );
		    			   if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		    			   list.add(sf1);
		    			   }
						   sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
								   DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*12),-1),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									FundIntentListPro3.calculAccrualnew(projectMoney,managementConsultingOfRate).multiply(new BigDecimal(payintentPeriod)),payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
									);
						   sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
								   DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*12),-1),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额                                              // 支出金额
									FundIntentListPro3.calculAccrualnew(projectMoney,financeServiceOfRate).multiply(new BigDecimal(payintentPeriod)),payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
									);
						   if(sf2.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
						    list.add(sf2);
						   }
						   if(sf3.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
							    list.add(sf3);
							}
		    		   }
		    		
		    		   sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
		    				   DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*12),-1),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.divide(new BigDecimal(payintentPeriod),2,BigDecimal.ROUND_UP) ,i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType );
		    		   
		    		   list.add(sf);
		    	   }  
	    	   }
	       }
		}
		if (accrualType.equals("sameprincipalsameInterest") && payaccrualType.equals("owerPay")){//按月
			if(!isStartDatePay.equals("1")){//按实际放款日对日还款
				if(isInterestByOneTime==0){//不一次性首付利息
		    	   for (int i = 1; i <=payintentPeriod; i++) {
		    		   Date intentDate=null;
		    		   if(isPreposePayAccrual == 1){//前置付息
		    			   intentDate=DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i-1)*dayOfEveryPeriod);
		    			   if(i==1){
		    				   intentDate=DateUtil.parseDate(date1,"yyyy-MM-dd");
		    			   }
		    		   }else{//后置付息
		    			   intentDate=DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*dayOfEveryPeriod);
		    		   }
		    		   sf1 =calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
		    				   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.multiply(accrual).multiply(new BigDecimal(dayOfEveryPeriod)) ,i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType );
		    		   sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
		    				   DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*dayOfEveryPeriod),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.divide(new BigDecimal(payintentPeriod),2,BigDecimal.ROUND_UP) ,i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType );
		    		   if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		    		   list.add(sf1);
		    		   }
					   sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
							   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,managementConsultingOfRate).multiply(new BigDecimal(dayOfEveryPeriod)),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
								);
					   sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
							   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额                                              // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,financeServiceOfRate).multiply(new BigDecimal(dayOfEveryPeriod)),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
								);
					   if(sf2.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
					    list.add(sf2);
					   }
					   if(sf3.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
						    list.add(sf3);
						}
		    		   list.add(sf);
		    	   }
	    	   }else if(isInterestByOneTime==1){//一次性收取全部利息
	    		   Date intentDate=null;
	    		  
	    		   if(isPreposePayAccrual == 1){//前置付息
	    			 intentDate=DateUtil.parseDate(date1,"yyyy-MM-dd");
	    			 sf1 =calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
		    				   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.multiply(accrual).multiply(new BigDecimal(payintentPeriod)).multiply(new BigDecimal(dayOfEveryPeriod)) ,1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType );
	    			 if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){ 
	    			 list.add(sf1);
	    			 }
					   sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
							   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,managementConsultingOfRate).multiply(new BigDecimal(dayOfEveryPeriod)).multiply(new BigDecimal(payintentPeriod)),1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
								);
					   sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
							   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额                                              // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,financeServiceOfRate).multiply(new BigDecimal(dayOfEveryPeriod)).multiply(new BigDecimal(payintentPeriod)),1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
								);
					   if(sf2.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
					    list.add(sf2);
					   }
					   if(sf3.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
						    list.add(sf3);
						}
	    		   }
	    		   
				   for (int i = 1; i <=payintentPeriod; i++) {
		    		  
		    		   if(isPreposePayAccrual ==0 && i==payintentPeriod){//前置付息
		    			   sf1 =calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
		    					   DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*dayOfEveryPeriod),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									projectMoney.multiply(accrual).multiply(new BigDecimal(payintentPeriod)).multiply(new BigDecimal(dayOfEveryPeriod)) ,payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType );
		    			   if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		    			   list.add(sf1);
		    			   }
						   sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
								   DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*dayOfEveryPeriod),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									FundIntentListPro3.calculAccrualnew(projectMoney,managementConsultingOfRate).multiply(new BigDecimal(dayOfEveryPeriod)).multiply(new BigDecimal(payintentPeriod)),payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
									);
						   sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
								   DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*dayOfEveryPeriod),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额                                              // 支出金额
									FundIntentListPro3.calculAccrualnew(projectMoney,financeServiceOfRate).multiply(new BigDecimal(dayOfEveryPeriod)).multiply(new BigDecimal(payintentPeriod)),payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
									);
						   if(sf2.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
						    list.add(sf2);
						   }
						   if(sf3.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
							    list.add(sf3);
							}
		    		   }
		    		
		    		   sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
		    				   DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*dayOfEveryPeriod),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.divide(new BigDecimal(payintentPeriod),2,BigDecimal.ROUND_UP) ,i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType );
		    		   
		    		   list.add(sf);
		    	   }  
	    	   }
	       }
		}
		/**
		 * 按期收息，到期还本
		 */
		if (accrualType.equals("singleInterest") && payaccrualType.equals("dayPay")) {// 日尾
			if(isInterestByOneTime==0){
	           for (int i = 0; i < payintentPeriod; i++) {
	        	   Date intentDate=null;
					 if(isPreposePayAccrual==1){
						 intentDate=DateUtil.addDaysToDate(DateUtil.parseDate(date1,"yyyy-MM-dd"), i);
					 }else{
						 intentDate=DateUtil.addDaysToDate(DateUtil.parseDate(date1), i+1);
					 }
					 sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
							 intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,accrual),i+1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType 	);        // 收入金额
					 if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
					 list.add(sf1);
					 }
					 sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
					    		intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,managementConsultingOfRate),i+1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
								);
					 sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
								intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,financeServiceOfRate),i+1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
								);
						
						if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
						if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}
						
					 
				 }
				    
			}else if(isInterestByOneTime==1){
				Date intentDate=null;
				int qs=0;
				if(isPreposePayAccrual==1){
					intentDate=DateUtil.parseDate(date1,"yyyy-MM-dd");
					qs=1;
				}else if(isPreposePayAccrual==0){
					intentDate=DateUtil.addDaysToDate(DateUtil.parseDate(date1,"yyyy-MM-dd"), payintentPeriod);
					qs=payintentPeriod;
				}
				 sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
						 intentDate,  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							FundIntentListPro3.calculAccrualnew(projectMoney,accrual).multiply(new BigDecimal(payintentPeriod)),qs,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType 	);        // 收入金额
				 if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
				 list.add(sf1);
				 }
				 sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
				    		intentDate,  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							FundIntentListPro3.calculAccrualnew(projectMoney,managementConsultingOfRate).multiply(new BigDecimal(payintentPeriod)),qs,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
							);
				 sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
							intentDate,  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							FundIntentListPro3.calculAccrualnew(projectMoney,financeServiceOfRate).multiply(new BigDecimal(payintentPeriod)),qs,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
							);
					
					if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
					if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}
			}
			sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
					DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), payintentPeriod),  //计划日期
					BigDecimal.valueOf(0),                                                // 支出金额
					projectMoney,payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType);      // 收入金额
			list.add(sf);
		}
		if (accrualType.equals("singleInterest") && payaccrualType.equals("monthPay")) {
   		 Date adate=DateUtil.addMonthsToDate(DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), -1),payintentPeriod);
   		 BigDecimal shanshi=new BigDecimal("30");
   		 if(isStartDatePay.equals("1")){ //固定在某日
 			if(isInterestByOneTime==0){
 				if(Long.valueOf(payintentPerioDate).compareTo(Long.valueOf(arr[2]))<0){
 					Date sDate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1,"yyyy-MM-dd"), 1);
					String dateStr=sdf.format(sDate);
					String[] dateArr=dateStr.split("-");
					startDate=dateArr[0]+"-"+dateArr[1]+"-"+payintentPerioDate;
 				}
 				if(Long.valueOf(payintentPerioDate).compareTo(Long.valueOf(arr[2]))!=0){
		 			BigDecimal firstPerioddays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate(date1,startDate)).toString());
		 			sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
		 					isPreposePayAccrual==1?DateUtil.parseDate(date1,"yyyy-MM-dd"):DateUtil.parseDate(startDate,"yyyy-MM-dd"),  //计划日期
		 					BigDecimal.valueOf(0),                                                // 支出金额
		 					projectMoney.multiply(accrual).multiply(firstPerioddays),0,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType 	);        // 收入金额
		 			if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		 			list.add(sf1);
		 			}	
		  		    sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
		  		    		isPreposePayAccrual==1?DateUtil.parseDate(date1,"yyyy-MM-dd"):DateUtil.parseDate(startDate,"yyyy-MM-dd"),  //计划日期
		  					BigDecimal.valueOf(0),                                                // 支出金额
		  					projectMoney.multiply(managementConsultingOfRate).multiply(firstPerioddays),0,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType          // 收入金额
		  					);
		  			sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
		  					isPreposePayAccrual==1?DateUtil.parseDate(date1,"yyyy-MM-dd"):DateUtil.parseDate(startDate,"yyyy-MM-dd"),  //计划日期
		  					BigDecimal.valueOf(0),                                                // 支出金额
		  					projectMoney.multiply(financeServiceOfRate).multiply(firstPerioddays),0,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType          // 收入金额
		  					);
		  			
		  			if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
		  			if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}
 				}
	 			for (int i = 1; i < payintentPeriod; i++) {
	 				Date startactualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), i-1);
					Date endctualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), i);
					BigDecimal actualdays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(startactualdate), sdf.format(endctualdate))).toString());
	 				 sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
	 						isPreposePayAccrual==1?startactualdate:endctualdate,  //计划日期
	 							BigDecimal.valueOf(0),                                                // 支出金额
	 							projectMoney.multiply(accrual).multiply(actualdays),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType 	);        // 收入金额
	 				if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
	 				 list.add(sf1);
	 				}
  				    sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
  				    		isPreposePayAccrual==1?startactualdate:endctualdate,  //计划日期
  							BigDecimal.valueOf(0),                                                // 支出金额
  							projectMoney.multiply(managementConsultingOfRate).multiply(actualdays),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType          // 收入金额
  							);
  					sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
  							isPreposePayAccrual==1?startactualdate:endctualdate,  //计划日期
  							BigDecimal.valueOf(0),                                                // 支出金额
  							projectMoney.multiply(financeServiceOfRate).multiply(actualdays),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType          // 收入金额
  							);
  					
  					if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
  					if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}
	 			 };
	 			 //if(payintentPeriod>1){
		 			 adate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"),payintentPeriod), -1);
		 			   Date bdate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"),payintentPeriod-1);
		 			  
		 			BigDecimal endPerioddays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(bdate), sdf.format(adate))).toString());
		 			sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
		 					isPreposePayAccrual==1?bdate:adate,  //计划日期
		 					BigDecimal.valueOf(0),                                                // 支出金额
		 					projectMoney.multiply(accrual).multiply(endPerioddays), payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType	);        // 收入金额
		 			if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		 			list.add(sf1);
		 			//}
		  		    sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
		  		    		isPreposePayAccrual==1?bdate:adate,  //计划日期
		  					BigDecimal.valueOf(0),                                                // 支出金额
		  					projectMoney.multiply(managementConsultingOfRate).multiply(endPerioddays),payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay ,String.valueOf(payintentPerioDate),accrualType         // 收入金额
		  					);
		  			sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
		  					isPreposePayAccrual==1?bdate:adate,  //计划日期
		  					BigDecimal.valueOf(0),                                                // 支出金额
		  					projectMoney.multiply(financeServiceOfRate).multiply(endPerioddays),payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType          // 收入金额
		  					);
		  			
		  			if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
		  			if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}
	 			 }
 			}else if(isInterestByOneTime==1){
 				BigDecimal accrualMoney=new BigDecimal(0);
 				BigDecimal managementConsultingOfRateMoney=new BigDecimal(0);
 				BigDecimal financeServiceOfRateMoney=new BigDecimal(0);
 				if(Long.valueOf(payintentPerioDate).compareTo(Long.valueOf(arr[2]))<0){
 					Date sDate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1,"yyyy-MM-dd"), 1);
					String dateStr=sdf.format(sDate);
					String[] dateArr=dateStr.split("-");
					startDate=dateArr[0]+"-"+dateArr[1]+"-"+payintentPerioDate;
 				}
 				if(Long.valueOf(payintentPerioDate).compareTo(Long.valueOf(arr[2]))!=0){
	 				BigDecimal firstPerioddays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate(date1,startDate)).toString());
	 				accrualMoney=accrualMoney.add(projectMoney.multiply(accrual).multiply(firstPerioddays));
	 				managementConsultingOfRateMoney=managementConsultingOfRateMoney.add(projectMoney.multiply(managementConsultingOfRate).multiply(firstPerioddays));
	 				financeServiceOfRateMoney=financeServiceOfRateMoney.add(projectMoney.multiply(financeServiceOfRate).multiply(firstPerioddays));
 				}
	 			for (int i = 1; i < payintentPeriod; i++) {
	 				Date startactualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), i-1);
					Date endctualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), i);
					BigDecimal actualdays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(startactualdate), sdf.format(endctualdate))).toString());
					accrualMoney=accrualMoney.add(projectMoney.multiply(accrual).multiply(actualdays));
					managementConsultingOfRateMoney=managementConsultingOfRateMoney.add(projectMoney.multiply(managementConsultingOfRate).multiply(actualdays));
					financeServiceOfRateMoney=financeServiceOfRateMoney.add(projectMoney.multiply(financeServiceOfRate).multiply(actualdays));
					
	 			 };
	 			 //if(payintentPeriod>1){
		 			 adate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"),payintentPeriod), -1);
		 			   Date bdate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"),payintentPeriod-1);
		 			  
		 			BigDecimal endPerioddays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(bdate), sdf.format(adate))).toString());
		 			accrualMoney=accrualMoney.add(projectMoney.multiply(accrual).multiply(endPerioddays));
		 			managementConsultingOfRateMoney=managementConsultingOfRateMoney.add(projectMoney.multiply(managementConsultingOfRate).multiply(endPerioddays));
		 			financeServiceOfRateMoney=financeServiceOfRateMoney.add(projectMoney.multiply(financeServiceOfRate).multiply(endPerioddays));
		 			
	 			// }
	 			 
	 			sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
	 					isPreposePayAccrual==1?DateUtil.parseDate(date1,"yyyy-MM-dd"):DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod), -1),  //计划日期
	 					BigDecimal.valueOf(0),                                                // 支出金额
	 					accrualMoney, isPreposePayAccrual==1?1:payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType	);        // 收入金额
	 			if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
	 			list.add(sf1);
	 			}
	  		    sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
	  		    		isPreposePayAccrual==1?DateUtil.parseDate(date1,"yyyy-MM-dd"):DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod), -1),  //计划日期
	  					BigDecimal.valueOf(0),                                                // 支出金额
	  					managementConsultingOfRateMoney,isPreposePayAccrual==1?1:payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType          // 收入金额
	  					);
	  			sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
	  					isPreposePayAccrual==1?DateUtil.parseDate(date1,"yyyy-MM-dd"):DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod), -1),  //计划日期
	  					BigDecimal.valueOf(0),                                                // 支出金额
	  					financeServiceOfRateMoney,isPreposePayAccrual==1?1:payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1 ,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType         // 收入金额
	  					);
	  			
	  			if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
	  			if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}
 			}
 		}else{
 			if(isInterestByOneTime==0){
 				for (int i = 1; i < payintentPeriod+1; i++) {
	   		 
//	   			  Date startactualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i-1);
//	   			  if(i>1){
//	   				  startactualdate=DateUtil.addDaysToDate(startactualdate, -1);
//	   			  }
//	   			  Date endctualdate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i), -1);
//	   			  BigDecimal actualdays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(startactualdate), sdf.format(endctualdate))).toString());
	   			  BigDecimal actualdays=new BigDecimal("30");
	   			
	   			  Date intentDate=null;
	   			  if(isPreposePayAccrual==1){
	   				  intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1,"yyyy-MM-dd"), i-1), 0);
	   				  if(i==1){
	   					  intentDate=DateUtil.parseDate(date1,"yyyy-MM-dd");
	   				  }
	   			  }else if(isPreposePayAccrual==0){
	   				  intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i), -1);
	   			  }
	   		
	   			  sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
	   					intentDate,  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						projectMoney.multiply(accrual).multiply(actualdays),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType 	);
	   			if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
	   			  list.add(sf1);
	   			}
   				  sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
   						intentDate,  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						projectMoney.multiply(managementConsultingOfRate).multiply(actualdays),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
						);
   				  sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
   						intentDate,  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						projectMoney.multiply(financeServiceOfRate).multiply(actualdays),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
						);
					if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
					if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}
			  			
				 }
	 		}else if(isInterestByOneTime==1){
	 			BigDecimal accrualMoney=new BigDecimal(0);
	 			BigDecimal managementConsultingOfRateMoney=new BigDecimal(0);
	 			//BigDecimal financeServiceOfRateMoney=new BigDecimal(0);
	 			for (int i = 1; i < payintentPeriod+1; i++) {
	 		   		 
		   			  Date startactualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i-1);
		   			  if(i>1){
		   				  startactualdate=DateUtil.addDaysToDate(startactualdate, -1);
		   			  }
		   			  Date endctualdate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i), -1);
		   			  BigDecimal actualdays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(startactualdate), sdf.format(endctualdate))).toString());
		   			accrualMoney=accrualMoney.add(projectMoney.multiply(accrual).multiply(actualdays));
		   			managementConsultingOfRateMoney=managementConsultingOfRateMoney.add(projectMoney.multiply(managementConsultingOfRate).multiply(actualdays));
		   			//financeServiceOfRateMoney=financeServiceOfRateMoney.add(projectMoney.multiply(financeServiceOfRate).multiply(actualdays));
		   			 
				 }
	 			Date intentDate=null;
	 			int qs=0;
	 			if(isPreposePayAccrual==0){
	 				intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1,"yyyy-MM-dd"), payintentPeriod), -1);
	 				qs=payintentPeriod;
	 			}else if(isPreposePayAccrual==1){
	 				intentDate=DateUtil.parseDate(date1,"yyyy-MM-dd");
	 				qs=1;
	 			}
	 			 sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
		   					intentDate,  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							accrualMoney,qs,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType 	);
	 			if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		   			  list.add(sf1);
	 			}
	   				  sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
	   						intentDate,  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							managementConsultingOfRateMoney,qs,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType         // 收入金额
							);
	   				 /* sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
	   						intentDate,  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							financeServiceOfRateMoney,qs,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
							);*/
						if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
						/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
	 		}
 		}
   			 sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
   					 DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"),payintentPeriod), -1),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						projectMoney ,payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,isStartDatePay.equals("1")?String.valueOf(payintentPerioDate):null,accrualType 	);   
   
				list.add(sf);
			
		}
		
		if (accrualType.equals("singleInterest") && payaccrualType.equals("seasonPay")) {
	   		 Date adate=DateUtil.addMonthsToDate(DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), -1),payintentPeriod*3);
	   		 if(isStartDatePay.equals("1")){ //固定在某日
	 			if(isInterestByOneTime==0){
	 				if(Long.valueOf(payintentPerioDate).compareTo(Long.valueOf(arr[2]))<0){
	 					Date sDate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1,"yyyy-MM-dd"), 3);
						String dateStr=sdf.format(sDate);
						String[] dateArr=dateStr.split("-");
						startDate=dateArr[0]+"-"+dateArr[1]+"-"+payintentPerioDate;
	 				}
	 				if(Long.valueOf(payintentPerioDate).compareTo(Long.valueOf(arr[2]))!=0){
			 			BigDecimal firstPerioddays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate(date1,startDate)).toString());
			 			sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
			 					isPreposePayAccrual==1?DateUtil.parseDate(date1,"yyyy-MM-dd"):DateUtil.parseDate(startDate,"yyyy-MM-dd"),  //计划日期
			 					BigDecimal.valueOf(0),                                                // 支出金额
			 					projectMoney.multiply(accrual).multiply(firstPerioddays),0,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType 	);        // 收入金额
			 			if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
			 			list.add(sf1);
			 			}
			  		    sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
			  		    		isPreposePayAccrual==1?DateUtil.parseDate(date1,"yyyy-MM-dd"):DateUtil.parseDate(startDate,"yyyy-MM-dd"),  //计划日期
			  					BigDecimal.valueOf(0),                                                // 支出金额
			  					projectMoney.multiply(managementConsultingOfRate).multiply(firstPerioddays),0,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType          // 收入金额
			  					);
			  			/*sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
			  					isPreposePayAccrual==1?DateUtil.parseDate(date1,"yyyy-MM-dd"):DateUtil.parseDate(startDate,"yyyy-MM-dd"),  //计划日期
			  					BigDecimal.valueOf(0),                                                // 支出金额
			  					projectMoney.multiply(financeServiceOfRate).multiply(firstPerioddays),0,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType          // 收入金额
			  					);*/
			  			
			  			if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
			  			/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
	 				}
		 			for (int i = 1; i < payintentPeriod; i++) {
		 				Date startactualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), (i-1)*3);
						Date endctualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), i*3);
						BigDecimal actualdays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(startactualdate), sdf.format(endctualdate))).toString());
		 				 sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
		 						isPreposePayAccrual==1?startactualdate:endctualdate,  //计划日期
		 							BigDecimal.valueOf(0),                                                // 支出金额
		 							projectMoney.multiply(accrual).multiply(actualdays),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType 	);        // 收入金额
		 				if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		 				list.add(sf1);
		 				}
	  				    sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
	  				    		isPreposePayAccrual==1?startactualdate:endctualdate,  //计划日期
	  							BigDecimal.valueOf(0),                                                // 支出金额
	  							projectMoney.multiply(managementConsultingOfRate).multiply(actualdays),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType          // 收入金额
	  							);
	  					/*sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
	  							isPreposePayAccrual==1?startactualdate:endctualdate,  //计划日期
	  							BigDecimal.valueOf(0),                                                // 支出金额
	  							projectMoney.multiply(financeServiceOfRate).multiply(actualdays),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType          // 收入金额
	  							);*/
	  					
	  					if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
	  					/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
		 			 };
		 			// if(payintentPeriod>1){
			 			 adate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"),payintentPeriod*3), -1);
			 			   Date bdate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"),(payintentPeriod-1)*3);
			 			  
			 			BigDecimal endPerioddays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(bdate), sdf.format(adate))).toString());
			 			sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
			 					isPreposePayAccrual==1?bdate:adate,  //计划日期
			 					BigDecimal.valueOf(0),                                                // 支出金额
			 					projectMoney.multiply(accrual).multiply(endPerioddays), payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType	);        // 收入金额
			 			if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
			 			list.add(sf1);
			 			//}
			  		    sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
			  		    		isPreposePayAccrual==1?bdate:adate,  //计划日期
			  					BigDecimal.valueOf(0),                                                // 支出金额
			  					projectMoney.multiply(managementConsultingOfRate).multiply(endPerioddays),payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType         // 收入金额
			  					);
			  			/*sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
			  					isPreposePayAccrual==1?bdate:adate,  //计划日期
			  					BigDecimal.valueOf(0),                                                // 支出金额
			  					projectMoney.multiply(financeServiceOfRate).multiply(endPerioddays),payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType          // 收入金额
			  					);*/
			  			
			  			if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
			  			/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
		 			 }
	 			}else if(isInterestByOneTime==1){
	 				BigDecimal accrualMoney=new BigDecimal(0);
	 				BigDecimal managementConsultingOfRateMoney=new BigDecimal(0);
	 				//BigDecimal financeServiceOfRateMoney=new BigDecimal(0);
	 				if(Long.valueOf(payintentPerioDate).compareTo(Long.valueOf(arr[2]))<0){
	 					Date sDate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1,"yyyy-MM-dd"), 3);
						String dateStr=sdf.format(sDate);
						String[] dateArr=dateStr.split("-");
						startDate=dateArr[0]+"-"+dateArr[1]+"-"+payintentPerioDate;
	 				}
	 				if(Long.valueOf(payintentPerioDate).compareTo(Long.valueOf(arr[2]))!=0){
		 				BigDecimal firstPerioddays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate(date1,startDate)).toString());
		 				accrualMoney=accrualMoney.add(projectMoney.multiply(accrual).multiply(firstPerioddays));
		 				managementConsultingOfRateMoney=managementConsultingOfRateMoney.add(projectMoney.multiply(managementConsultingOfRate).multiply(firstPerioddays));
		 				//financeServiceOfRateMoney=financeServiceOfRateMoney.add(projectMoney.multiply(financeServiceOfRate).multiply(firstPerioddays));
	 				}
		 			for (int i = 1; i < payintentPeriod; i++) {
		 				Date startactualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), (i-1)*3);
						Date endctualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), i*3);
						BigDecimal actualdays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(startactualdate), sdf.format(endctualdate))).toString());
						accrualMoney=accrualMoney.add(projectMoney.multiply(accrual).multiply(actualdays));
						managementConsultingOfRateMoney=managementConsultingOfRateMoney.add(projectMoney.multiply(managementConsultingOfRate).multiply(actualdays));
						//financeServiceOfRateMoney=financeServiceOfRateMoney.add(projectMoney.multiply(financeServiceOfRate).multiply(actualdays));
						
		 			 };
		 			// if(payintentPeriod>1){
			 			 adate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"),payintentPeriod*3), -1);
			 			   Date bdate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"),(payintentPeriod-1)*3);
			 			  
			 			BigDecimal endPerioddays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(bdate), sdf.format(adate))).toString());
			 			accrualMoney=accrualMoney.add(projectMoney.multiply(accrual).multiply(endPerioddays));
			 			managementConsultingOfRateMoney=managementConsultingOfRateMoney.add(projectMoney.multiply(managementConsultingOfRate).multiply(endPerioddays));
			 			//financeServiceOfRateMoney=financeServiceOfRateMoney.add(projectMoney.multiply(financeServiceOfRate).multiply(endPerioddays));
			 			
		 			 //}
		 			 
		 			sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
		 					isPreposePayAccrual==1?DateUtil.parseDate(date1,"yyyy-MM-dd"):DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod*3), -1),  //计划日期
		 					BigDecimal.valueOf(0),                                                // 支出金额
		 					accrualMoney, isPreposePayAccrual==1?1:payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType	);        // 收入金额
		 			if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		 			list.add(sf1);
		 			}
		  		    sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
		  		    		isPreposePayAccrual==1?DateUtil.parseDate(date1,"yyyy-MM-dd"):DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod*3), -1),  //计划日期
		  					BigDecimal.valueOf(0),                                                // 支出金额
		  					managementConsultingOfRateMoney,isPreposePayAccrual==1?1:payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType          // 收入金额
		  					);
		  			/*sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
		  					isPreposePayAccrual==1?DateUtil.parseDate(date1,"yyyy-MM-dd"):DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod*3), -1),  //计划日期
		  					BigDecimal.valueOf(0),                                                // 支出金额
		  					financeServiceOfRateMoney,isPreposePayAccrual==1?1:payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType          // 收入金额
		  					);*/
		  			
		  			if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
		  			/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
	 			}
	 		}else{
	 			if(isInterestByOneTime==0){
	 				for (int i = 1; i < payintentPeriod+1; i++) {
		   		 
		   			  Date startactualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i-1)*3);
		   			  if(i>1){
		   				  startactualdate=DateUtil.addDaysToDate(startactualdate, -1);
		   			  }
		   			  Date endctualdate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*3), -1);
		   			  BigDecimal actualdays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(startactualdate), sdf.format(endctualdate))).toString());
		   			
		   			  Date intentDate=null;
		   			  if(isPreposePayAccrual==1){
		   				  intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1,"yyyy-MM-dd"), (i-1)*3), -1);
		   				  if(i==1){
		   					  intentDate=DateUtil.parseDate(date1,"yyyy-MM-dd");
		   				  }
		   			  }else if(isPreposePayAccrual==0){
		   				  intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*3), -1);
		   			  }
		   			
		   			  sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
		   					intentDate,  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							projectMoney.multiply(accrual).multiply(actualdays),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType 	);
		   			if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		   			  list.add(sf1);
		   			}
	   				  sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
	   						intentDate,  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							projectMoney.multiply(managementConsultingOfRate).multiply(actualdays),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
							);
	   				 /* sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
	   						intentDate,  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							projectMoney.multiply(financeServiceOfRate).multiply(actualdays),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
							);*/
						if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
						/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
		   			
		   			
				  			
					 }
		 		}else if(isInterestByOneTime==1){
		 			BigDecimal accrualMoney=new BigDecimal(0);
		 			BigDecimal managementConsultingOfRateMoney=new BigDecimal(0);
		 			BigDecimal financeServiceOfRateMoney=new BigDecimal(0);
		 			for (int i = 1; i < payintentPeriod+1; i++) {
		 		   		 
			   			  Date startactualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i-1)*3);
			   			  if(i>1){
			   				  startactualdate=DateUtil.addDaysToDate(startactualdate, -1);
			   			  }
			   			  Date endctualdate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*3), -1);
			   			  BigDecimal actualdays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(startactualdate), sdf.format(endctualdate))).toString());
			   			
			   			accrualMoney=accrualMoney.add(projectMoney.multiply(accrual).multiply(actualdays));
			   			managementConsultingOfRateMoney=managementConsultingOfRateMoney.add(projectMoney.multiply(managementConsultingOfRate).multiply(actualdays));
			   			financeServiceOfRateMoney=financeServiceOfRateMoney.add(projectMoney.multiply(financeServiceOfRate).multiply(actualdays));
			   			 
					 }
		 			Date intentDate=null;
		 			int qs=0;
		 			if(isPreposePayAccrual==0){
		 				intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1,"yyyy-MM-dd"), payintentPeriod*3), -1);
		 				qs=payintentPeriod;
		 			}else if(isPreposePayAccrual==1){
		 				intentDate=DateUtil.parseDate(date1,"yyyy-MM-dd");
		 				qs=1;
		 			}
		 			 sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
			   					intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								accrualMoney,qs,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType 	);
		 			if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
			   			  list.add(sf1);
		 			}
		   				  sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
		   						intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								managementConsultingOfRateMoney,qs,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
								);
		   				 /* sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
		   						intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								financeServiceOfRateMoney,qs,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
								);*/
							if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
							/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
		 		}
	 		}
   			 sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
   					 DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"),payintentPeriod*3), -1),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						projectMoney ,payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,isStartDatePay.equals("1")?String.valueOf(payintentPerioDate):null,accrualType 	);   
   
				list.add(sf);
				
		}
		if (accrualType.equals("singleInterest") && payaccrualType.equals("yearPay")) {
	   		 Date adate=DateUtil.addMonthsToDate(DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), -1),payintentPeriod*12);
	   		 if(isStartDatePay.equals("1")){ //固定在某日
	 			if(isInterestByOneTime==0){
	 				if(Long.valueOf(payintentPerioDate).compareTo(Long.valueOf(arr[2]))<0){
	 					Date sDate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1,"yyyy-MM-dd"), 12);
						String dateStr=sdf.format(sDate);
						String[] dateArr=dateStr.split("-");
						startDate=dateArr[0]+"-"+dateArr[1]+"-"+payintentPerioDate;
	 				}
	 				if(Long.valueOf(payintentPerioDate).compareTo(Long.valueOf(arr[2]))!=0){
			 			BigDecimal firstPerioddays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate(date1,startDate)).toString());
			 			sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
			 					isPreposePayAccrual==1?DateUtil.parseDate(date1,"yyyy-MM-dd"):DateUtil.parseDate(startDate,"yyyy-MM-dd"),  //计划日期
			 					BigDecimal.valueOf(0),                                                // 支出金额
			 					projectMoney.multiply(accrual).multiply(firstPerioddays),0,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType 	);        // 收入金额
			 			if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
			 			list.add(sf1);
			 			}
			  		    sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
			  		    		isPreposePayAccrual==1?DateUtil.parseDate(date1,"yyyy-MM-dd"):DateUtil.parseDate(startDate,"yyyy-MM-dd"),  //计划日期
			  					BigDecimal.valueOf(0),                                                // 支出金额
			  					projectMoney.multiply(managementConsultingOfRate).multiply(firstPerioddays),0,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay ,String.valueOf(payintentPerioDate),accrualType         // 收入金额
			  					);
			  			/*sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
			  					isPreposePayAccrual==1?DateUtil.parseDate(date1,"yyyy-MM-dd"):DateUtil.parseDate(startDate,"yyyy-MM-dd"),  //计划日期
			  					BigDecimal.valueOf(0),                                                // 支出金额
			  					projectMoney.multiply(financeServiceOfRate).multiply(firstPerioddays),0,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay ,String.valueOf(payintentPerioDate),accrualType         // 收入金额
			  					);*/
			  			
			  			if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
			  			/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
	 				}
		 			for (int i = 1; i < payintentPeriod; i++) {
		 				Date startactualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), (i-1)*12);
						Date endctualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), i*12);
						BigDecimal actualdays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(startactualdate), sdf.format(endctualdate))).toString());
		 				 sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
		 						isPreposePayAccrual==1?startactualdate:endctualdate,  //计划日期
		 							BigDecimal.valueOf(0),                                                // 支出金额
		 							projectMoney.multiply(accrual).multiply(actualdays),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType 	);        // 收入金额
		 				if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		 				list.add(sf1);
		 				}	
	  				    sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
	  				    		isPreposePayAccrual==1?startactualdate:endctualdate,  //计划日期
	  							BigDecimal.valueOf(0),                                                // 支出金额
	  							projectMoney.multiply(managementConsultingOfRate).multiply(actualdays),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType          // 收入金额
	  							);
	  					/*sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
	  							isPreposePayAccrual==1?startactualdate:endctualdate,  //计划日期
	  							BigDecimal.valueOf(0),                                                // 支出金额
	  							projectMoney.multiply(financeServiceOfRate).multiply(actualdays),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay ,String.valueOf(payintentPerioDate),accrualType         // 收入金额
	  							);*/
	  					
	  					if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
	  					/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
		 			 };
		 			// if(payintentPeriod>1){
			 			 adate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"),payintentPeriod*12), -1);
			 			   Date bdate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"),(payintentPeriod-1)*12);
			 			  
			 			BigDecimal endPerioddays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(bdate), sdf.format(adate))).toString());
			 			sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
			 					isPreposePayAccrual==1?bdate:adate,  //计划日期
			 					BigDecimal.valueOf(0),                                                // 支出金额
			 					projectMoney.multiply(accrual).multiply(endPerioddays), payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType	);        // 收入金额
			 			if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
			 			list.add(sf1);
			 		//	}
			  		    sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
			  		    		isPreposePayAccrual==1?bdate:adate,  //计划日期
			  					BigDecimal.valueOf(0),                                                // 支出金额
			  					projectMoney.multiply(managementConsultingOfRate).multiply(endPerioddays),payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType          // 收入金额
			  					);
			  			/*sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
			  					isPreposePayAccrual==1?bdate:adate,  //计划日期
			  					BigDecimal.valueOf(0),                                                // 支出金额
			  					projectMoney.multiply(financeServiceOfRate).multiply(endPerioddays),payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType          // 收入金额
			  					);*/
			  			
			  			if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
			  			/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
		 			 }
	 			}else if(isInterestByOneTime==1){
	 				BigDecimal accrualMoney=new BigDecimal(0);
	 				BigDecimal managementConsultingOfRateMoney=new BigDecimal(0);
	 				//BigDecimal financeServiceOfRateMoney=new BigDecimal(0);
	 				if(Long.valueOf(payintentPerioDate).compareTo(Long.valueOf(arr[2]))<0){
	 					Date sDate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1,"yyyy-MM-dd"), 12);
						String dateStr=sdf.format(sDate);
						String[] dateArr=dateStr.split("-");
						startDate=dateArr[0]+"-"+dateArr[1]+"-"+payintentPerioDate;
	 				}
	 				if(Long.valueOf(payintentPerioDate).compareTo(Long.valueOf(arr[2]))!=0){
		 				BigDecimal firstPerioddays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate(date1,startDate)).toString());
		 				accrualMoney=accrualMoney.add(projectMoney.multiply(accrual).multiply(firstPerioddays));
		 				managementConsultingOfRateMoney=managementConsultingOfRateMoney.add(projectMoney.multiply(managementConsultingOfRate).multiply(firstPerioddays));
		 				//financeServiceOfRateMoney=financeServiceOfRateMoney.add(projectMoney.multiply(financeServiceOfRate).multiply(firstPerioddays));
	 				}
		 			for (int i = 1; i < payintentPeriod; i++) {
		 				Date startactualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), (i-1)*12);
						Date endctualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), i*12);
						BigDecimal actualdays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(startactualdate), sdf.format(endctualdate))).toString());
						accrualMoney=accrualMoney.add(projectMoney.multiply(accrual).multiply(actualdays));
						managementConsultingOfRateMoney=managementConsultingOfRateMoney.add(projectMoney.multiply(managementConsultingOfRate).multiply(actualdays));
						//financeServiceOfRateMoney=financeServiceOfRateMoney.add(projectMoney.multiply(financeServiceOfRate).multiply(actualdays));
						
		 			 };
		 			 //if(payintentPeriod>1){
			 			 adate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"),payintentPeriod*12), -1);
			 			   Date bdate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"),(payintentPeriod-1)*12);
			 			  
			 			BigDecimal endPerioddays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(bdate), sdf.format(adate))).toString());
			 			accrualMoney=accrualMoney.add(projectMoney.multiply(accrual).multiply(endPerioddays));
			 			managementConsultingOfRateMoney=managementConsultingOfRateMoney.add(projectMoney.multiply(managementConsultingOfRate).multiply(endPerioddays));
			 			//financeServiceOfRateMoney=financeServiceOfRateMoney.add(projectMoney.multiply(financeServiceOfRate).multiply(endPerioddays));
			 			
		 			// }
		 			 
		 			sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
		 					isPreposePayAccrual==1?DateUtil.parseDate(date1,"yyyy-MM-dd"):DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod*12), -1),  //计划日期
		 					BigDecimal.valueOf(0),                                                // 支出金额
		 					accrualMoney, isPreposePayAccrual==1?1:payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType	);        // 收入金额
		 			if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		 			list.add(sf1);
		 			}
		  		    sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
		  		    		isPreposePayAccrual==1?DateUtil.parseDate(date1,"yyyy-MM-dd"):DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod*12), -1),  //计划日期
		  					BigDecimal.valueOf(0),                                                // 支出金额
		  					managementConsultingOfRateMoney,isPreposePayAccrual==1?1:payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType          // 收入金额
		  					);
		  			/*sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
		  					isPreposePayAccrual==1?DateUtil.parseDate(date1,"yyyy-MM-dd"):DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod*12), -1),  //计划日期
		  					BigDecimal.valueOf(0),                                                // 支出金额
		  					financeServiceOfRateMoney,isPreposePayAccrual==1?1:payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType          // 收入金额
		  					);*/
		  			
		  			if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
		  			/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
	 			}
	 		}else{
	 			if(isInterestByOneTime==0){
	 				for (int i = 1; i < payintentPeriod+1; i++) {
		   		 
		   			  Date startactualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i-1)*12);
		   			  if(i>1){
		   				  startactualdate=DateUtil.addDaysToDate(startactualdate, -1);
		   			  }
		   			  Date endctualdate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*12), -1);
		   			  BigDecimal actualdays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(startactualdate), sdf.format(endctualdate))).toString());
		   			
		   			  Date intentDate=null;
		   			  if(isPreposePayAccrual==1){
		   				  intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1,"yyyy-MM-dd"), (i-1)*12), -1);
		   				  if(i==1){
		   					  intentDate=DateUtil.parseDate(date1,"yyyy-MM-dd");
		   				  }
		   			  }else if(isPreposePayAccrual==0){
		   				  intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*12), -1);
		   			  }
		   			  sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
		   					intentDate,  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							projectMoney.multiply(accrual).multiply(actualdays),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType 	);
		   			if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		   			  list.add(sf1);
		   			}
	   				  sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
	   						intentDate,  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							projectMoney.multiply(managementConsultingOfRate).multiply(actualdays),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
							);
	   				 /* sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
	   						intentDate,  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							projectMoney.multiply(financeServiceOfRate).multiply(actualdays),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
							);*/
						if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
						/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
		   			
		   			
				  			
					 }
		 		}else if(isInterestByOneTime==1){
		 			BigDecimal accrualMoney=new BigDecimal(0);
		 			BigDecimal managementConsultingOfRateMoney=new BigDecimal(0);
		 			//BigDecimal financeServiceOfRateMoney=new BigDecimal(0);
		 			for (int i = 1; i < payintentPeriod+1; i++) {
		 		   		 
			   			  Date startactualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i-1)*12);
			   			  if(i>1){
			   				  startactualdate=DateUtil.addDaysToDate(startactualdate, -1);
			   			  }
			   			  Date endctualdate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*12), -1);
			   			  BigDecimal actualdays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(startactualdate), sdf.format(endctualdate))).toString());
			   			
			   			accrualMoney=accrualMoney.add(projectMoney.multiply(accrual).multiply(actualdays));
			   			managementConsultingOfRateMoney=managementConsultingOfRateMoney.add(projectMoney.multiply(managementConsultingOfRate).multiply(actualdays));
			   			//financeServiceOfRateMoney=financeServiceOfRateMoney.add(projectMoney.multiply(financeServiceOfRate).multiply(actualdays));
			   			 
					 }
		 			Date intentDate=null;
		 			int qs=0;
		 			if(isPreposePayAccrual==0){
		 				intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1,"yyyy-MM-dd"), payintentPeriod*12), -1);
		 				qs=payintentPeriod;
		 			}else if(isPreposePayAccrual==1){
		 				intentDate=DateUtil.parseDate(date1,"yyyy-MM-dd");
		 				qs=1;
		 			}
		 			 sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
			   					intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								accrualMoney,qs,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType 	);
		 			if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
			   			  list.add(sf1);
		 			}
		   				  sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
		   						intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								managementConsultingOfRateMoney,qs,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
								);
		   				 /* sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
		   						intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								financeServiceOfRateMoney,qs ,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null ,accrualType        // 收入金额
								);*/
							if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
							/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
		 		}
	 		}
	   			 sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
	   					 DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"),payintentPeriod*12), -1),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							projectMoney ,payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,isStartDatePay.equals("1")?String.valueOf(payintentPerioDate):null,accrualType 	);   
	   
					list.add(sf);
				
			}
		if (accrualType.equals("singleInterest") && payaccrualType.equals("owerPay")) {// 日尾
			if(isInterestByOneTime==0){
	          for (int i = 1; i < payintentPeriod+1; i++) {
	        	  Date intentDate=null;
	        	  if(isPreposePayAccrual==0){
	        		  intentDate=DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*dayOfEveryPeriod);
	        	  }else if(isPreposePayAccrual==1){
	        		  intentDate=DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i-1)*dayOfEveryPeriod);
	        	  }
	        	  Date sdate=DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i-1)*dayOfEveryPeriod);
	        	  Date edate=DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*dayOfEveryPeriod);
	        	  int days=DateUtil.getDaysBetweenDate(sdf.format(sdate), sdf.format(edate));
					 sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
							intentDate,  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							projectMoney.multiply(accrual).multiply(new BigDecimal(days)),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType 	);        // 收入金额
					 if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
					 list.add(sf1);
					 }
				    sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
				    		intentDate,  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							projectMoney.multiply(managementConsultingOfRate).multiply(new BigDecimal(days)),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
							);
					/*sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
							intentDate,  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							projectMoney.multiply(financeServiceOfRate).multiply(new BigDecimal(days)),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
							);*/
					
					if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
					/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
						
					 
				 }
				    
			}else if(isInterestByOneTime==1){
				BigDecimal accrualMoney=new BigDecimal(0);
				BigDecimal managementConsultingOfRateMoney=new BigDecimal(0);
				//BigDecimal financeServiceOfRateMoney=new BigDecimal(0);
				for (int i = 1; i < payintentPeriod+1; i++) {
		        	
		        	  Date sdate=DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i-1)*dayOfEveryPeriod);
		        	  Date edate=DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*dayOfEveryPeriod);
		        	  int days=DateUtil.getDaysBetweenDate(sdf.format(sdate), sdf.format(edate));
		        	  accrualMoney=accrualMoney.add(projectMoney.multiply(accrual).multiply(new BigDecimal(days)));
		        	  managementConsultingOfRateMoney=managementConsultingOfRateMoney.add(projectMoney.multiply(managementConsultingOfRate).multiply(new BigDecimal(days)));
		        	 // financeServiceOfRateMoney=financeServiceOfRateMoney.add(projectMoney.multiply(financeServiceOfRate).multiply(new BigDecimal(days)));
						
						 
					 }
				Date intentDate=null;
				int qs=0;
				 if(isPreposePayAccrual==0){
	        		  intentDate=DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), payintentPeriod*dayOfEveryPeriod);
	        		  qs=payintentPeriod;
	        	  }else if(isPreposePayAccrual==1){
	        		  intentDate=DateUtil.parseDate(date1,"yyyy-MM-dd");
	        		  qs=1;
	        	  }
				 sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
							intentDate,  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							accrualMoney,qs,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType	);        // 收入金额
				 if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
					 list.add(sf1);
				 }
				    sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
				    		intentDate,  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							managementConsultingOfRateMoney,qs,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
							);
					/*sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
							intentDate,  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							financeServiceOfRateMoney,qs,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
							);*/
					
					if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
					/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
						
			}
			sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
					DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), payintentPeriod*dayOfEveryPeriod),  //计划日期
					BigDecimal.valueOf(0),                                                // 支出金额
					projectMoney ,payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType 	);      // 收入金额
			list.add(sf);
		}
		/**
		 * 等额本金
		 */
		if (accrualType.equals("sameprincipal") && payaccrualType.equals("monthPay")) {// 月尾
			BigDecimal preperiodsurplus=projectMoney;
			if(payintentPeriod>0){
			 BigDecimal sameprojectMoney = projectMoney.divide(new BigDecimal(payintentPeriod),100,BigDecimal.ROUND_HALF_UP);
			 
			 if(isStartDatePay.equals("1")){ //固定在某日
				 if(isInterestByOneTime==0){
				 //if(payintentPeriod>1){
				 String firstdays=Integer.valueOf(DateUtil.getDaysBetweenDate( date1,sdf.format(DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), 1)))).toString();
				 	sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
				 			isPreposePayAccrual==0?DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), 1):DateUtil.parseDate(date1,"yyyy-MM-dd"),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							FundIntentListPro3.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(firstdays)),      // 收入金额
							1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType
					);
		    	  sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
		    			  DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), 1),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							sameprojectMoney,
							1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType);      // 收入金额
		    	  if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		    	  list.add(sf1);
		    	 // }
		    		BigDecimal  managementConsultingOfRate1=managementConsultingOfRate.multiply(new BigDecimal(firstdays));
		    	  sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
		    			  isPreposePayAccrual==0?DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), 1):DateUtil.parseDate(date1,"yyyy-MM-dd"),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							projectMoney.multiply(managementConsultingOfRate1),1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType          // 收入金额
							);
		    	  /*  BigDecimal financeServiceOfRate1=financeServiceOfRate.multiply(new BigDecimal(firstdays));
					sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
							isPreposePayAccrual==0?DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), 1):DateUtil.parseDate(date1,"yyyy-MM-dd"),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							projectMoney.multiply(financeServiceOfRate1) ,1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType         // 收入金额
							);*/
					
					if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
					//if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}
		    	 
					list.add(sf);
					preperiodsurplus=preperiodsurplus.subtract(sf.getIncomeMoney());
				 
				 for (int i = 2; i <payintentPeriod; i++) {
					 Date intentDate=null;
					 if(isPreposePayAccrual==0){
						 intentDate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), i);
					 }else if(isPreposePayAccrual==1){
						 intentDate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), i-1);
					 }
				 sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
						 intentDate,  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							FundIntentListPro3.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(30)),
							i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType);        // 收入金额
				    sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
				    		intentDate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), i),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								sameprojectMoney,
								i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType);      // 收入金额
				    if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
				    list.add(sf1);
				    }
				    sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
				    		intentDate,  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							projectMoney.multiply(managementConsultingOfRate).multiply(new BigDecimal(30)),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType          // 收入金额
							);
					/*sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
							intentDate,  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							projectMoney.multiply(financeServiceOfRate).multiply(new BigDecimal(30)) ,i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType         // 收入金额
							);*/
					
					if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
					/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
					
					list.add(sf);
					
					preperiodsurplus=preperiodsurplus.subtract(sf.getIncomeMoney());
				 }
				 }
				
				 String enddays=Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), payintentPeriod-1)), sdf.format(DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod), -1)))).toString();
				 Date intDate=null;
				 if(isPreposePayAccrual==0){
					 intDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod), -1);
				 }else if(isPreposePayAccrual==1){
					 intDate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), payintentPeriod-1);
				 }
				 sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
						 intDate,  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							FundIntentListPro3.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(enddays)),      // 收入金额
							payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType
				 );
				    sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
				    		DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod), -1),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							sameprojectMoney,     // 收入金额
							payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType
				    );
				    if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
				    list.add(sf1);
				    }
				    	BigDecimal managementConsultingOfRate2=managementConsultingOfRate.multiply(new BigDecimal(enddays));
				    sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
				    		intDate,  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							projectMoney.multiply(managementConsultingOfRate2),payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType         // 收入金额
							);
				  /*  BigDecimal financeServiceOfRate2=financeServiceOfRate.multiply(new BigDecimal(enddays));
					sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
							intDate,  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							projectMoney.multiply(financeServiceOfRate2),payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType       // 收入金额
							);*/
					
					if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
					/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
				   
					list.add(sf);
				
					preperiodsurplus=preperiodsurplus.subtract(sf.getIncomeMoney());
				 }else if(isInterestByOneTime==1){
					 BigDecimal accrualMoney=new BigDecimal(0);
					 BigDecimal managementConsultingOfRateMoney=new BigDecimal(0);
					// BigDecimal financeServiceOfRateMoney=new BigDecimal(0);
					 
						 String firstdays=Integer.valueOf(DateUtil.getDaysBetweenDate( date1,sdf.format(DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), 1)))).toString();
						 accrualMoney=accrualMoney.add(FundIntentListPro3.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(firstdays)));
						 BigDecimal  managementConsultingOfRate1=managementConsultingOfRate.multiply(new BigDecimal(firstdays));
						 managementConsultingOfRateMoney=managementConsultingOfRateMoney.add(projectMoney.multiply(managementConsultingOfRate1));
						 BigDecimal financeServiceOfRate1=financeServiceOfRate.multiply(new BigDecimal(firstdays));
						 //financeServiceOfRateMoney=financeServiceOfRateMoney.add(projectMoney.multiply(financeServiceOfRate1));
					
							preperiodsurplus=preperiodsurplus.subtract(sameprojectMoney);
						 
						 for (int i = 2; i <payintentPeriod; i++) {
							 accrualMoney=accrualMoney.add(FundIntentListPro3.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(30)));
							 managementConsultingOfRateMoney=managementConsultingOfRateMoney.add(projectMoney.multiply(managementConsultingOfRate).multiply(new BigDecimal(30)));
							// financeServiceOfRateMoney=financeServiceOfRateMoney.add(projectMoney.multiply(financeServiceOfRate).multiply(new BigDecimal(30)));
							preperiodsurplus=preperiodsurplus.subtract(sameprojectMoney);
						 }
						
						
						 String enddays=Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), payintentPeriod-1)), sdf.format(DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod), -1)))).toString();
						 BigDecimal managementConsultingOfRate2=managementConsultingOfRate.multiply(new BigDecimal(enddays));
						 BigDecimal financeServiceOfRate2=financeServiceOfRate.multiply(new BigDecimal(enddays));
						 accrualMoney=accrualMoney.add(FundIntentListPro3.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(enddays)));
						 managementConsultingOfRateMoney=managementConsultingOfRateMoney.add(projectMoney.multiply(managementConsultingOfRate2));
						// financeServiceOfRateMoney=financeServiceOfRateMoney.add(projectMoney.multiply(financeServiceOfRate2));
						preperiodsurplus=preperiodsurplus.subtract(sameprojectMoney);
						
						if(isPreposePayAccrual==1){
							 sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
									 DateUtil.parseDate(date1,"yyyy-MM-dd"),  //计划日期
										BigDecimal.valueOf(0),                                                // 支出金额
										accrualMoney,
										1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType);        // 收入金额
							 if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
							    list.add(sf1);
							 }
							    sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
							    		DateUtil.parseDate(date1,"yyyy-MM-dd"),  //计划日期
										BigDecimal.valueOf(0),                                                // 支出金额
										managementConsultingOfRateMoney,1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType          // 收入金额
										);
								/*sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
										DateUtil.parseDate(date1,"yyyy-MM-dd"),  //计划日期
										BigDecimal.valueOf(0),                                                // 支出金额
										financeServiceOfRateMoney,1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType         // 收入金额
										);*/
								
								if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
								/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
								
						}
						 sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
		    			  DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), 1),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							sameprojectMoney,
							1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType);      // 收入金额
						 list.add(sf);
						 for (int i = 2; i <payintentPeriod; i++) {
							
						    sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
						    		DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), i),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									sameprojectMoney,
									i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType);      // 收入金额
						   
							list.add(sf);
						   
							preperiodsurplus=preperiodsurplus.subtract(sf.getIncomeMoney());
						 }
					
						 if(isPreposePayAccrual==0){
							 sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
									 DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod), -1),  //计划日期
										BigDecimal.valueOf(0),                                                // 支出金额
										accrualMoney,
										payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType);        // 收入金额
							 if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
							    list.add(sf1);
							 }
							    sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
							    		DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod), -1),  //计划日期
										BigDecimal.valueOf(0),                                                // 支出金额
										managementConsultingOfRateMoney,payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType          // 收入金额
										);
								/*sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
										DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod), -1),  //计划日期
										BigDecimal.valueOf(0),                                                // 支出金额
										financeServiceOfRateMoney,payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType        // 收入金额
										);*/
								
								if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
								/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
								
						}
						 sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
						    		DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod), -1),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									sameprojectMoney,     // 收入金额
									payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType
						    );
						 list.add(sf);
					 }
					
			
			 }else{
				 if(isInterestByOneTime==0){
					 for (int i = 1; i < payintentPeriod+1; i++) {
						 Date intentDate=null;
						 if(isPreposePayAccrual==0){
							 intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i), -1);
						 }else if(isPreposePayAccrual==1){
							 intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i-1), -1);
							 if(i==1){
								 intentDate=DateUtil.parseDate(date1,"yyyy-MM-dd");
							 }
						 }
						 sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
								intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPro3.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(30)),
								i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType);        // 收入金额
					    sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
					    		DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i), -1),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									sameprojectMoney ,
									i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType);   
					    if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
						list.add(sf1);// 收入金额
					    }		
					    sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
					    		intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.multiply(managementConsultingOfRate).multiply(new BigDecimal(30)),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
								);
						/*sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
								intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.multiply(financeServiceOfRate).multiply(new BigDecimal(30)),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
								);*/
					
						if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
						/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
						
						list.add(sf);
						
						preperiodsurplus=preperiodsurplus.subtract(sf.getIncomeMoney());
					 }
				 }else if(isInterestByOneTime==1){
					 BigDecimal accrualMoney=new BigDecimal(0);
					 BigDecimal managementConsultingOfRateMoney=new BigDecimal(0);
					 //BigDecimal financeServiceOfRateMoney=new BigDecimal(0);
					 for (int i = 1; i < payintentPeriod+1; i++) {
						 accrualMoney=accrualMoney.add(FundIntentListPro3.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(30)));
						 managementConsultingOfRateMoney=managementConsultingOfRateMoney.add(projectMoney.multiply(managementConsultingOfRate).multiply(new BigDecimal(30)));
						// financeServiceOfRateMoney=financeServiceOfRateMoney.add(projectMoney.multiply(financeServiceOfRate).multiply(new BigDecimal(30)));
						 preperiodsurplus=preperiodsurplus.subtract(sameprojectMoney);
					 }
					 if(isPreposePayAccrual==1){
						 sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
									DateUtil.parseDate(date1,"yyyy-MM-dd"),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									accrualMoney,
									1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType);
						 if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
						 list.add(sf1);// 收入金额
						 }
						 sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
								 DateUtil.parseDate(date1,"yyyy-MM-dd"),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									managementConsultingOfRateMoney,1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
									);
							/*sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
									DateUtil.parseDate(date1,"yyyy-MM-dd"),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									financeServiceOfRateMoney,1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
									);*/
						
							if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
							/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
					 }
					 for (int i = 1; i < payintentPeriod+1; i++) {
						 if(isPreposePayAccrual==0 && i==payintentPeriod){
							 sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
										DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1,"yyyy-MM-dd"), payintentPeriod), -1),  //计划日期
										BigDecimal.valueOf(0),                                                // 支出金额
										accrualMoney,
										payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType); 
							 if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
							 list.add(sf1);// 收入金额
							 }
							 sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
									 DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1,"yyyy-MM-dd"), payintentPeriod), -1),  //计划日期
										BigDecimal.valueOf(0),                                                // 支出金额
										managementConsultingOfRateMoney,payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
										);
								/*sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
										DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1,"yyyy-MM-dd"), payintentPeriod), -1),  //计划日期
										BigDecimal.valueOf(0),                                                // 支出金额
										financeServiceOfRateMoney,payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
										);*/
							
								if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
								/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
						 }
						
					    sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
					    		DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1,"yyyy-MM-dd"),i), -1),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								sameprojectMoney ,
								i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType);   
						list.add(sf);
						
						preperiodsurplus=preperiodsurplus.subtract(sf.getIncomeMoney());
					 }
				 }
			 }
			}
		}
		/**
		 * 等额本息
		 */
		if (accrualType.equals("sameprincipalandInterest") && payaccrualType.equals("monthPay")) {// 月尾
		     BigDecimal periodtimemoney=FundIntentListPro3.periodtimemoney(accrual.multiply(new BigDecimal(30)),projectMoney,payintentPeriod).divide(new BigDecimal(1),2,BigDecimal.ROUND_HALF_UP);
		     BigDecimal preperiodsurplus=projectMoney;
		     if(isStartDatePay.equals("1")){ //固定在某日
		    	 if(isInterestByOneTime==0){
			    	 String firstdays=Integer.valueOf(DateUtil.getDaysBetweenDate( date1,sdf.format(DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), 1)))).toString();
			    	
			    	 sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
			    			 isPreposePayAccrual==0?DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), 1):DateUtil.parseDate(date1,"yyyy-MM-dd"),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPro3.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(firstdays)),      // 收入金额
								1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType
			    	 );
			    	  BigDecimal firstperiodtimemoney=periodtimemoney.add(sf1.getIncomeMoney().subtract(FundIntentListPro3.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(30))));
					    sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
								DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), 1),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								firstperiodtimemoney.subtract(sf1.getIncomeMoney()),        // 收入金额
								1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType
					    );
					    if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
						list.add(sf1);
					    }
				    	BigDecimal managementConsultingOfRate1=managementConsultingOfRate.multiply(new BigDecimal(firstdays));
					    sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
					    		isPreposePayAccrual==0?DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), 1):DateUtil.parseDate(date1,"yyyy-MM-dd"),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.multiply(managementConsultingOfRate1),1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType         // 收入金额
								);
					 /*   BigDecimal financeServiceOfRate1=financeServiceOfRate.multiply(new BigDecimal(firstdays));
						sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
								isPreposePayAccrual==0?DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), 1):DateUtil.parseDate(date1,"yyyy-MM-dd"),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.multiply(financeServiceOfRate1) ,1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay ,String.valueOf(payintentPerioDate),accrualType      // 收入金额
								);*/
					  
						if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
						/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
					   
						list.add(sf);
						preperiodsurplus=preperiodsurplus.subtract(sf.getIncomeMoney());
						
						
					for (int i = 2; i < payintentPeriod; i++) {
					sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
							isPreposePayAccrual==0?DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), i):DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), i-1),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							FundIntentListPro3.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(30)),      // 收入金额
							i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType
							);
				    sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
							DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), i),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							periodtimemoney.subtract(FundIntentListPro3.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(30))),        // 收入金额
							i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType
				    );
				    if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
						list.add(sf1);
					    }
				   
				    sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
				    		isPreposePayAccrual==0?DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), i):DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), i-1),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							projectMoney.multiply(managementConsultingOfRate).multiply(new BigDecimal(30)) ,i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType        // 收入金额
							);
				/*	sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
							isPreposePayAccrual==0?DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), i):DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), i-1),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							projectMoney.multiply(financeServiceOfRate).multiply(new BigDecimal(30)) ,i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType       // 收入金额
							);*/
					
					if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
					/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
				    
					list.add(sf);
					preperiodsurplus=preperiodsurplus.subtract(sf.getIncomeMoney());
					}
					
					
					// String enddays=Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDatejianyi, "yyyy-MM-dd"), payintentPeriod-1)),sdf.format(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), payintentPeriod)))).toString();
					//if(payintentPeriod>1){
					String enddays=Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), payintentPeriod-1)),sdf.format(DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod), -1)))).toString();
					sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
							isPreposePayAccrual==0?DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod), -1):DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), payintentPeriod-1),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPro3.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(enddays)),      // 收入金额
								payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType
								);
			    	  BigDecimal endperiodtimemoney=periodtimemoney.subtract((FundIntentListPro3.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(30))).subtract(sf1.getIncomeMoney()));
					    sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
					    		DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod), -1),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								endperiodtimemoney.subtract(sf1.getIncomeMoney()),        // 收入金额
								payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType
					    );
					    if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
							list.add(sf1);
						    }
					 
					    	BigDecimal managementConsultingOfRate2=managementConsultingOfRate.multiply(new BigDecimal(enddays));
					    sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
					    		isPreposePayAccrual==0?DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod), -1):DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), payintentPeriod-1),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.multiply(managementConsultingOfRate2),payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType         // 收入金额
								);
					   /* BigDecimal financeServiceOfRate2=financeServiceOfRate.multiply(new BigDecimal(enddays));
						sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
								isPreposePayAccrual==0?DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod), -1):DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), payintentPeriod-1),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.multiply(financeServiceOfRate2),payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType        // 收入金额
								);*/
						
						if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
						/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
					  
					
						list.add(sf);
					//}
						preperiodsurplus=preperiodsurplus.subtract(sf.getIncomeMoney());
		    	 }else if(isInterestByOneTime==1){
		    		 BigDecimal accrualMoney=new BigDecimal(0);
		    		 BigDecimal managementConsultingOfRateMoney=new BigDecimal(0);
		    		// BigDecimal financeServiceOfRateMoney=new BigDecimal(0);
		    		 BigDecimal preperiodsurplus1=projectMoney;
			    	 String firstdays=Integer.valueOf(DateUtil.getDaysBetweenDate( date1,sdf.format(DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), 1)))).toString();
			    	 accrualMoney=accrualMoney.add(FundIntentListPro3.calculAccrualnew(preperiodsurplus1,accrual).multiply(new BigDecimal(firstdays)));
			    	 BigDecimal managementConsultingOfRate1=managementConsultingOfRate.multiply(new BigDecimal(firstdays));
			    	 managementConsultingOfRateMoney=managementConsultingOfRateMoney.add(projectMoney.multiply(managementConsultingOfRate1));
			    	/* BigDecimal financeServiceOfRate1=financeServiceOfRate.multiply(new BigDecimal(firstdays));
			    	 financeServiceOfRateMoney=financeServiceOfRateMoney.add(projectMoney.multiply(financeServiceOfRate1));*/
			    	
			    	 
						
						preperiodsurplus1=preperiodsurplus1.subtract(periodtimemoney.subtract(FundIntentListPro3.calculAccrualnew(preperiodsurplus1,accrual).multiply(new BigDecimal(30))));
						
						
						
					for (int i = 2; i < payintentPeriod; i++) {
						accrualMoney=accrualMoney.add(FundIntentListPro3.calculAccrualnew(preperiodsurplus1,accrual).multiply(new BigDecimal(30)));
						managementConsultingOfRateMoney=managementConsultingOfRateMoney.add(projectMoney.multiply(managementConsultingOfRate).multiply(new BigDecimal(30)));
						//financeServiceOfRateMoney=financeServiceOfRateMoney.add(projectMoney.multiply(financeServiceOfRate).multiply(new BigDecimal(30)));
					
					preperiodsurplus1=preperiodsurplus1.subtract(periodtimemoney.subtract(FundIntentListPro3.calculAccrualnew(preperiodsurplus1,accrual).multiply(new BigDecimal(30))));
					}
					
					
					
				
					String enddays=Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), payintentPeriod-1)),sdf.format(DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod), -1)))).toString();
					accrualMoney=accrualMoney.add(FundIntentListPro3.calculAccrualnew(preperiodsurplus1,accrual).multiply(new BigDecimal(enddays)));
					BigDecimal managementConsultingOfRate2=managementConsultingOfRate.multiply(new BigDecimal(enddays));
					managementConsultingOfRateMoney=managementConsultingOfRateMoney.add(projectMoney.multiply(managementConsultingOfRate2));
					BigDecimal financeServiceOfRate2=financeServiceOfRate.multiply(new BigDecimal(enddays));
					//financeServiceOfRateMoney=financeServiceOfRateMoney.add(projectMoney.multiply(financeServiceOfRate2));
					
					if(isPreposePayAccrual==1){
						sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
								DateUtil.parseDate(date1,"yyyy-MM-dd"),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								accrualMoney,      // 收入金额
								1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType
								);
						if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
							list.add(sf1);
						    }
					    	
					    sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
					    		DateUtil.parseDate(date1,"yyyy-MM-dd"),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								managementConsultingOfRateMoney,1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType         // 收入金额
								);
					    
						/*sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
								DateUtil.parseDate(date1,"yyyy-MM-dd"),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								financeServiceOfRateMoney,1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType        // 收入金额
								);*/
						
						if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
						/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
					}
					  sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
								DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), 1),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								periodtimemoney.subtract(FundIntentListPro3.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(30))),        // 收入金额
								1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType
					    );
					  	list.add(sf);
						
						preperiodsurplus=preperiodsurplus.subtract(periodtimemoney.subtract(FundIntentListPro3.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(30))));
						for (int i = 2; i < payintentPeriod; i++) {
							
						    sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
									DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), i),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									periodtimemoney.subtract(FundIntentListPro3.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(30))),        // 收入金额
									i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType
						    );
						    
							list.add(sf);
							preperiodsurplus=preperiodsurplus.subtract(periodtimemoney.subtract(FundIntentListPro3.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(30))));
						}
						if(isPreposePayAccrual==0){
							sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
									DateUtil.parseDate(date1,"yyyy-MM-dd"),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									accrualMoney,      // 收入金额
									payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType
									);
							if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
								list.add(sf1);
							    }
						    sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
						    		DateUtil.parseDate(date1,"yyyy-MM-dd"),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									managementConsultingOfRateMoney,payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType        // 收入金额
									);
						    
							/*sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
									DateUtil.parseDate(date1,"yyyy-MM-dd"),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									financeServiceOfRateMoney,payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType        // 收入金额
									);*/
							
							if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
							/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
						}
					    sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
					    		DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod), -1),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								periodtimemoney.subtract(FundIntentListPro3.calculAccrualnew(preperiodsurplus1,accrual).multiply(new BigDecimal(enddays))),        // 收入金额
								payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType
					    );
						list.add(sf);
					
						preperiodsurplus=preperiodsurplus.subtract(sf.getIncomeMoney());
		    	 }
		     }else{
		    	 if(isInterestByOneTime==0){
					for (int i = 1; i < payintentPeriod+1; i++) {
						Date intentDate=null;
						if(isPreposePayAccrual==0){
							intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i), -1);
						}else if(isPreposePayAccrual==1){
							intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i-1), -1);
							if(i==1){
								intentDate=DateUtil.parseDate(date1,"yyyy-MM-dd");
							}
						}
						sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
								intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPro3.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(30)),      // 收入金额
								i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType
								);
					    sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
					    		DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i), -1),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								periodtimemoney.subtract(FundIntentListPro3.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(30))) ,       // 收入金额
								i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType
								);
					    if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
							list.add(sf1);
						    }
					   
					    sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
					    		intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.multiply(managementConsultingOfRate).multiply(new BigDecimal(30)),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType         // 收入金额
								);
						/*sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
								intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.multiply(financeServiceOfRate).multiply(new BigDecimal(30)) ,i ,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType      // 收入金额
								);*/
						
						if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
						/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
					   
						list.add(sf);
						preperiodsurplus=preperiodsurplus.subtract(sf.getIncomeMoney());
					}
		    	 }else if(isInterestByOneTime==1){
		    		 BigDecimal accrualMoney=new BigDecimal(0);
		    		 BigDecimal managementConsultingOfRateMoney=new BigDecimal(0);
		    		 //BigDecimal financeServiceOfRateMoney=new BigDecimal(0);
		    		 for (int i = 1; i < payintentPeriod+1; i++) {
		    			 accrualMoney=accrualMoney.add(FundIntentListPro3.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(30)));
		    			 managementConsultingOfRateMoney=managementConsultingOfRateMoney.add(projectMoney.multiply(managementConsultingOfRate).multiply(new BigDecimal(30)));
		    			// financeServiceOfRateMoney=financeServiceOfRateMoney.add(projectMoney.multiply(financeServiceOfRate).multiply(new BigDecimal(30)));
						
						preperiodsurplus=preperiodsurplus.subtract(periodtimemoney.subtract(FundIntentListPro3.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(30))));
					}
		    		 for (int i = 1; i < payintentPeriod+1; i++) {
		    			if(isPreposePayAccrual==1 && i==1){
		    				sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
									DateUtil.parseDate(date1,"yyyy-MM-dd"),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									accrualMoney,      // 收入金额
									i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType
									);
		    				if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
								list.add(sf1);
							    }
						    sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
						    		DateUtil.parseDate(date1,"yyyy-MM-dd"),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									managementConsultingOfRateMoney,i ,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType        // 收入金额
									);
							/*sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
									DateUtil.parseDate(date1,"yyyy-MM-dd"),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									financeServiceOfRateMoney ,i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType       // 收入金额
									);*/
							
							if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
							/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
		    			}else if(isPreposePayAccrual==0 && i==payintentPeriod){
		    				sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
									DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1,"yyyy-MM-dd"), payintentPeriod), -1),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									accrualMoney,      // 收入金额
									i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType
									);
		    				if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
								list.add(sf1);
							    }
						    sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
						    		DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1,"yyyy-MM-dd"), payintentPeriod), -1),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									managementConsultingOfRateMoney,i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType         // 收入金额
									);
							/*sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
									DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1,"yyyy-MM-dd"), payintentPeriod), -1),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									financeServiceOfRateMoney ,i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType       // 收入金额
									);*/
							
							if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
							/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
		    			}
						
					    sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
						    DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1,"yyyy-MM-dd"), i), -1),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							periodtimemoney.subtract(FundIntentListPro3.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(30))) ,       // 收入金额
							i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType
							);
							list.add(sf);
							preperiodsurplus=preperiodsurplus.subtract(sf.getIncomeMoney());
						}
		    	 }
		    	 
		    	 
		     }
			}
    	return list;

	}
	public static BigDecimal periodtimemoney(BigDecimal accrual,BigDecimal projectMoney,int period) {
		
		BigDecimal   periodtimemoney=new  BigDecimal("0");
		  periodtimemoney=projectMoney.multiply(accrual).multiply(mnfang(accrual.add(new BigDecimal(1)),period)).divide((mnfang(accrual.add(new BigDecimal(1)),period).subtract(new BigDecimal(1))),100,BigDecimal.ROUND_HALF_UP);
			return periodtimemoney;
	}
	public static BigDecimal mnfang(BigDecimal m,int n){
		BigDecimal s=m;
		for(int i=1;i<n;i++){
			s=s.multiply(m);
			
		}
		return s;
	}
	public static BigDecimal calculAccrualnew(BigDecimal preperiodsurplus,BigDecimal accrual){

		BigDecimal dayp = new BigDecimal(0);
         

		return  preperiodsurplus.multiply(accrual);
	}
	public static SlFundIntent calculslfundintent(String fundType,Date intentdate,BigDecimal paymoney,BigDecimal incomemoeny,Integer payintentPeriod,Integer isPreposePayAccrual,String payaccrualType,Integer dayOfEveryPeriod,String date1,Integer isInterestByOneTime,String date2,String isStartDatePay,String gddate,String accrualtype){
		SlFundIntent sf1=new SlFundIntent();
		sf1.setFundType(fundType);// 资金类型
		sf1.setIntentDate(intentdate);
		sf1.setPayMoney(paymoney); // 支出金额
		sf1.setIncomeMoney(incomemoeny); // 收入金额
		sf1.setAfterMoney(new BigDecimal("0"));
		sf1.setFlatMoney(new BigDecimal("0"));
		sf1.setAccrualMoney(new BigDecimal("0"));
		sf1.setRemark("");
		sf1.setPayintentPeriod(payintentPeriod);
		if(!fundType.equals("principalLending")){
			SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
			//计息开始日期
			Date interestStarTime=new Date();
			//计息结束日期
			Date interestEndTime=new Date();
			//计息结束日期为计划还款日
			if(fundType.equals("principalRepayment")){
				isPreposePayAccrual=0;
			}
			
			if(isInterestByOneTime==1){
				interestStarTime=DateUtil.parseDate(date1,"yyyy-MM-dd");
				if(AppUtil.getInterest().equals("0")){
					interestEndTime=DateUtil.addDaysToDate(DateUtil.parseDate(date2,"yyyy-MM-dd"), 0);
				}else{
					interestEndTime=DateUtil.parseDate(date2,"yyyy-MM-dd");
				}
			}else{
				if(AppUtil.getInterest().equals("0")){
					if(isPreposePayAccrual==1){
						if(payaccrualType.equals("dayPay")){
							interestEndTime=DateUtil.addDaysToDate(intentdate, 1);
						}else if(payaccrualType.equals("monthPay")){
							interestEndTime=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(intentdate, 1), 0);
						}else if(payaccrualType.equals("seasonPay")){
							interestEndTime=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(intentdate, 3), 0);
						}else if(payaccrualType.equals("yearPay")){
							interestEndTime=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(intentdate, 12), 0);
						}else if(payaccrualType.equals("owerPay")){
							interestEndTime=DateUtil.addDaysToDate(intentdate, dayOfEveryPeriod);
						}
						
						if(isStartDatePay.equals("1")){
							String intentdateStr=sd.format(intentdate);
							String[] intentdateArr=intentdateStr.split("-");
							if((intentdateArr[0]+"-"+intentdateArr[1]).equals(date2.substring(0,date2.lastIndexOf("-")))){
								interestEndTime=DateUtil.addDaysToDate(DateUtil.parseDate(date2,"yyyy-MM-dd"), 0);
							}
							String[] dateArr=date1.split("-");
							if(payintentPeriod==0){
								
								if(payaccrualType.equals("monthPay")){
									if(Long.valueOf(gddate)<Long.valueOf(dateArr[2])){
										interestEndTime=DateUtil.parseDate(intentdateArr[0]+"-"+(Integer.valueOf(intentdateArr[1])+1)+"-"+(Integer.valueOf(gddate)-1),"yyyy-MM-dd");
									}else{
										interestEndTime=DateUtil.parseDate(intentdateArr[0]+"-"+Integer.valueOf(intentdateArr[1])+"-"+(Integer.valueOf(gddate)-1),"yyyy-MM-dd");
									}
								}else if(payaccrualType.equals("seasonPay")){
									if(Long.valueOf(gddate)<Long.valueOf(dateArr[2])){
										interestEndTime=DateUtil.parseDate(intentdateArr[0]+"-"+(Integer.valueOf(intentdateArr[1])+3)+"-"+(Integer.valueOf(gddate)-1),"yyyy-MM-dd");
									}else{
										interestEndTime=DateUtil.parseDate(intentdateArr[0]+"-"+Integer.valueOf(intentdateArr[1])+"-"+(Integer.valueOf(gddate)-1),"yyyy-MM-dd");
									}
								}else if(payaccrualType.equals("yearPay")){
									if(Long.valueOf(gddate)<Long.valueOf(dateArr[2])){
										interestEndTime=DateUtil.parseDate(intentdateArr[0]+"-"+(Integer.valueOf(intentdateArr[1])+12)+"-"+(Integer.valueOf(gddate)-1),"yyyy-MM-dd");
									}else{
										interestEndTime=DateUtil.parseDate(intentdateArr[0]+"-"+Integer.valueOf(intentdateArr[1])+"-"+(Integer.valueOf(gddate)-1),"yyyy-MM-dd");
									}
								}
							}
							if(Long.valueOf(gddate)>=Long.valueOf(dateArr[2])){
								String str=sd.format(interestEndTime);
								String[] strArr=str.split("-");
								if((strArr[0]+"-"+strArr[1]).equals(date2.substring(0,date2.lastIndexOf("-")))){
									interestEndTime=DateUtil.addDaysToDate(DateUtil.parseDate(date2,"yyyy-MM-dd"), 0);
								}
								}
						}else{
							if(payintentPeriod==1 && (payaccrualType.equals("monthPay") || payaccrualType.equals("seasonPay") || payaccrualType.equals("yearPay"))){
								interestEndTime=DateUtil.addDaysToDate(interestEndTime, 0);
							}
							if(payaccrualType.equals("dayPay") || payaccrualType.equals("owerPay")){
								interestEndTime=DateUtil.addDaysToDate(interestEndTime, 0);
							}
						}
					}else{
						interestEndTime=DateUtil.addDaysToDate(intentdate,0);
					}
					if(fundType.equals("principalRepayment") && accrualtype.equals("singleInterest")){
						interestEndTime=DateUtil.addDaysToDate(DateUtil.parseDate(date2,"yyyy-MM-dd"), 0);
					}
				}else{}
				if(AppUtil.getInterest().equals("0")){
					if(isStartDatePay.equals("1")){
						String intentdateStr=sd.format(intentdate);
						String[] intentdateArr=intentdateStr.split("-");
						String[] dateArr=date1.split("-");
						if(isPreposePayAccrual==1){
							interestStarTime=intentdate;
						}else{
							if(payaccrualType.equals("dayPay")){
								interestStarTime=DateUtil.addDaysToDate(intentdate, -1);
							}else if(payaccrualType.equals("monthPay")){
								interestStarTime=DateUtil.addMonthsToDate(intentdate, -1);
								
							}else if(payaccrualType.equals("seasonPay")){
								interestStarTime=DateUtil.addMonthsToDate(intentdate, -3);
							}else if(payaccrualType.equals("yearPay")){
								interestStarTime=DateUtil.addMonthsToDate(intentdate, -12);
							}else if(payaccrualType.equals("owerPay")){
								interestStarTime=DateUtil.addDaysToDate(intentdate, -dayOfEveryPeriod);
							}
						}
						if(payintentPeriod==0){
							interestStarTime=DateUtil.parseDate(date1,"yyyy-MM-dd");
						}
						if(intentdateStr.equals(date2)){
							if(payaccrualType.equals("monthPay")){
								if(Long.valueOf(gddate)>=Long.valueOf(dateArr[2])){
									interestStarTime=DateUtil.parseDate(intentdateArr[0]+"-"+(Integer.valueOf(intentdateArr[1])-1)+"-"+gddate,"yyyy-MM-dd");
								}else{
									interestStarTime=DateUtil.parseDate(intentdateArr[0]+"-"+(Integer.valueOf(intentdateArr[1]))+"-"+gddate,"yyyy-MM-dd");
								}
							}else if(payaccrualType.equals("seasonPay")){
								if(Long.valueOf(gddate)>=Long.valueOf(dateArr[2])){
									interestStarTime=DateUtil.parseDate(intentdateArr[0]+"-"+(Integer.valueOf(intentdateArr[1])-3)+"-"+gddate,"yyyy-MM-dd");
								}else{
									interestStarTime=DateUtil.parseDate(intentdateArr[0]+"-"+Integer.valueOf(intentdateArr[1])+"-"+gddate,"yyyy-MM-dd");
								}
							}else if(payaccrualType.equals("yearPay")){
								if(Long.valueOf(gddate)>=Long.valueOf(dateArr[2])){
									interestStarTime=DateUtil.parseDate(intentdateArr[0]+"-"+(Integer.valueOf(intentdateArr[1])-12)+"-"+gddate,"yyyy-MM-dd");
								}else{
									interestStarTime=DateUtil.parseDate(intentdateArr[0]+"-"+Integer.valueOf(intentdateArr[1])+"-"+gddate,"yyyy-MM-dd");
								}
							}
						}
					}else{
						if(isPreposePayAccrual==1){
							interestStarTime=intentdate;
						}else{
							if(payaccrualType.equals("dayPay")){
								interestStarTime=DateUtil.addDaysToDate(intentdate, 0);
							}else if(payaccrualType.equals("monthPay")){
								interestStarTime=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.addDaysToDate(intentdate, 1), -1), 0);
							}else if(payaccrualType.equals("seasonPay")){
								interestStarTime=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.addDaysToDate(intentdate, 1), -3), 0);
							}else if(payaccrualType.equals("yearPay")){
								interestStarTime=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.addDaysToDate(intentdate, 1), -12), 0);
							}else if(payaccrualType.equals("owerPay")){
								interestStarTime=DateUtil.addDaysToDate(intentdate, -dayOfEveryPeriod);
							}
							if(payintentPeriod==1){
								interestStarTime=DateUtil.parseDate(date1,"yyyy-MM-dd");
							}
						}
					}
					
				}else{}
				if(fundType.equals("principalRepayment") && accrualtype.equals("singleInterest")){
					interestStarTime=DateUtil.parseDate(date1,"yyyy-MM-dd");
				}
			}
			/*if(fundType.equals("serviceMoney")){
				interestStarTime=DateUtil.parseDate(date1,"yyyy-MM-dd");
				if(AppUtil.getInterest().equals("0")){
					interestEndTime=DateUtil.addDaysToDate(DateUtil.parseDate(date2,"yyyy-MM-dd"), 0);
				}else{
					interestEndTime=DateUtil.parseDate(date2,"yyyy-MM-dd");
				}
			}*/
			sf1.setInterestStarTime(interestStarTime);
			sf1.setInterestEndTime(interestEndTime);
		}
		return  sf1;
		
	}
	
	public static SlFundIntent calculslfundintent(String fundType,Date intentdate,BigDecimal paymoney,BigDecimal incomemoeny,Long investPersonId,String investPersonName,Integer payintentPeriod){
		SlFundIntent sf1=new SlFundIntent();
		sf1.setFundType(fundType);// 资金类型
		sf1.setIntentDate(intentdate);
		sf1.setPayMoney(paymoney); // 支出金额
		sf1.setIncomeMoney(incomemoeny); // 收入金额
		sf1.setAfterMoney(new BigDecimal("0"));
		sf1.setFlatMoney(new BigDecimal("0"));
		sf1.setAccrualMoney(new BigDecimal("0"));
		sf1.setRemark("");
		sf1.setInvestPersonId(investPersonId);
		sf1.setInvestPersonName(investPersonName);
		sf1.setPayintentPeriod(payintentPeriod);
		return  sf1;
	}
	
	public static List<SlFundIntent> getFundIntentDefaultList(String calcutype, String accrualType, Integer isPreposePayAccrual,String payaccrualType, BigDecimal accrual, 
			BigDecimal projectMoney,String date1,String date2,BigDecimal managementConsultingOfRate,BigDecimal financeServiceOfRate,Integer payintentPeriod,
			String isStartDatePay,Integer payintentPerioDate,Integer dayOfEveryPeriod,Long investPersonId,String investPersonName,Integer prepayintentPeriod) {
		BigDecimal sumaccrual=accrual;//accrual.add(financeServiceOfRate).add(managementConsultingOfRate);
		BigDecimal managementpre=managementConsultingOfRate.divide(sumaccrual, 100, BigDecimal.ROUND_HALF_UP);
		BigDecimal financepre=financeServiceOfRate.divide(sumaccrual, 100, BigDecimal.ROUND_HALF_UP);
		BigDecimal accrualpre=new BigDecimal("1");//.subtract(managementpre).subtract(financepre);
		List<SlFundIntent> list = new ArrayList<SlFundIntent>();
		SlFundIntent sf;
		SlFundIntent sf1;
		SlFundIntent sf2;
		SlFundIntent sf3;
		sf = new SlFundIntent();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        BigDecimal periodtimemoney=new BigDecimal("0");
        BigDecimal preperiodsurplus=projectMoney;
        String startperiodDate=date1;
		if(isStartDatePay.equals("1")){
			String[] array1=startperiodDate.split("-");
			startperiodDate=array1[0]+"-"+array1[1]+"-"+payintentPerioDate.toString();
			if(Integer.valueOf(array1[2])>payintentPerioDate){
				startperiodDate=sdf.format(DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), 1));
			}
		}else{
			startperiodDate=sdf.format(DateUtil.addDaysToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), -1));
		}
		if (calcutype.equals("SmallLoan")) { // 贷款项目
			sf.setFundType(FundIntentListPronew.ProjectLoadOut);// 资金类型
			sf.setIntentDate(DateUtil.parseDate(date1, "yyyy-MM-dd"));
			sf.setPayMoney(projectMoney); // 支出金额
			sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入金额
			sf.setRemark("");
			sf.setInvestPersonId(investPersonId);
			sf.setInvestPersonName(investPersonName);
			list.add(sf);
		} 
		
		if (accrualType.equals("sameprincipalandInterest") && payaccrualType.equals("monthPay") && isPreposePayAccrual == 1) {// 月头
			periodtimemoney=FundIntentListPro3.periodtimemoney(accrual,projectMoney,payintentPeriod).divide(new BigDecimal(1),2,BigDecimal.ROUND_HALF_UP);
		     if(isStartDatePay.equals("1")){ //固定在某日	
		    	String firstdays=Integer.valueOf(DateUtil.getDaysBetweenDate( date1,sdf.format(DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), 1)))+1).toString();
		    	sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), 0),BigDecimal.valueOf(0),
						FundIntentListPro3.calculAccrualnew(preperiodsurplus,accrual).divide(new BigDecimal(30),100,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(firstdays)),
						investPersonId,investPersonName,1);
		    	BigDecimal firstperiodtimemoney=periodtimemoney.add(sf1.getIncomeMoney().subtract(FundIntentListPro3.calculAccrualnew(preperiodsurplus,accrual)));
				sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), 1),
							BigDecimal.valueOf(0),firstperiodtimemoney.subtract(sf1.getIncomeMoney()),investPersonId,investPersonName,1);
				list.add(sf1);
			    BigDecimal managementConsultingOfRate1=managementConsultingOfRate.divide(new BigDecimal(30),100,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(firstdays));
			    BigDecimal financeServiceOfRate1=financeServiceOfRate.divide(new BigDecimal(30),100,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(firstdays));

				/*sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), 0), 
							BigDecimal.valueOf(0),projectMoney.multiply(managementConsultingOfRate1),investPersonId,investPersonName,1);
				sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), 0),
							BigDecimal.valueOf(0),projectMoney.multiply(financeServiceOfRate1) ,investPersonId,investPersonName,1 );
				if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
				if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
				list.add(sf);
				preperiodsurplus=preperiodsurplus.subtract(sf.getIncomeMoney());
				for (int i = 2; i < payintentPeriod; i++) {
					sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), i-1),
							BigDecimal.valueOf(0),FundIntentListPro3.calculAccrualnew(preperiodsurplus,accrual),investPersonId,investPersonName,i);
				    sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), i),
							BigDecimal.valueOf(0),periodtimemoney.subtract(FundIntentListPro3.calculAccrualnew(preperiodsurplus,accrual)),investPersonId,investPersonName,i );
				    list.add(sf1);
				
				   /* sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), i-1),
							BigDecimal.valueOf(0),projectMoney.multiply(managementConsultingOfRate),investPersonId,investPersonName ,i);
					sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), i-1),
							BigDecimal.valueOf(0),projectMoney.multiply(financeServiceOfRate) ,investPersonId,investPersonName,i);
					*/
					/*if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
					if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}
			*/
					list.add(sf);
					preperiodsurplus=preperiodsurplus.subtract(sf.getIncomeMoney());
				}
				
				if(payintentPeriod>1){
					String enddays=Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), payintentPeriod-1)),sdf.format(DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod), -1)))).toString();
					sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), payintentPeriod-1),BigDecimal.valueOf(0),
							FundIntentListPro3.calculAccrualnew(preperiodsurplus,accrual).divide(new BigDecimal(30),100,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(enddays)),
							investPersonId,investPersonName,payintentPeriod);
					BigDecimal endperiodtimemoney=periodtimemoney.subtract((FundIntentListPro3.calculAccrualnew(preperiodsurplus,accrual)).subtract(sf1.getIncomeMoney()));
				    sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod), -1),
					BigDecimal.valueOf(0),endperiodtimemoney.subtract(sf1.getIncomeMoney()),investPersonId,investPersonName,payintentPeriod);
				    list.add(sf1);
			    	BigDecimal managementConsultingOfRate2=managementConsultingOfRate.divide(new BigDecimal(30),100,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(enddays));
			    	BigDecimal financeServiceOfRate2=financeServiceOfRate.divide(new BigDecimal(30),100,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(enddays));

				  /*  sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), payintentPeriod-1),
							BigDecimal.valueOf(0), projectMoney.multiply(managementConsultingOfRate2),investPersonId,investPersonName,payintentPeriod );
					sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), payintentPeriod-1),
							BigDecimal.valueOf(0),projectMoney.multiply(financeServiceOfRate2),investPersonId,investPersonName,payintentPeriod);
					if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
					if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
					list.add(sf);
				}
				preperiodsurplus=preperiodsurplus.subtract(sf.getIncomeMoney());
		    }else{
				for (int i = 0; i < payintentPeriod; i++) {
					if(i==0){
						sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i),
								BigDecimal.valueOf(0),
								FundIntentListPro3.calculAccrualnew(preperiodsurplus,sumaccrual).multiply(accrualpre),investPersonId,investPersonName,1);
					    sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i),
								BigDecimal.valueOf(0),
								periodtimemoney.subtract(FundIntentListPro3.calculAccrualnew(preperiodsurplus,accrual)),investPersonId,investPersonName,1
								);
					   /* sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i),  //计划日期
								BigDecimal.valueOf(0), // 支出金额
								FundIntentListPro3.calculAccrualnew(preperiodsurplus,sumaccrual).multiply(managementpre),investPersonId,investPersonName,1  // 收入金额
								);
						sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i),  //计划日期
										BigDecimal.valueOf(0), // 支出金额
										FundIntentListPro3.calculAccrualnew(preperiodsurplus,sumaccrual).multiply(financepre),investPersonId,investPersonName,1  // 收入金额
								);*/
					}else{
						sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,  // 资金类型
								DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i), -1),  //计划日期
								BigDecimal.valueOf(0),   // 支出金额
								FundIntentListPro3.calculAccrualnew(preperiodsurplus,sumaccrual).multiply(accrualpre),investPersonId,investPersonName,i+1 // 收入金额
								);
					    sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,  // 资金类型
					    		DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i), -1),  //计划日期
								BigDecimal.valueOf(0),   // 支出金额
								periodtimemoney.subtract(FundIntentListPro3.calculAccrualnew(preperiodsurplus,accrual)),investPersonId,investPersonName,i+1  // 收入金额
								);
					 /*   sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting, // 资金类型
					    		DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i), -1),  //计划日期
								BigDecimal.valueOf(0),  // 支出金额
								FundIntentListPro3.calculAccrualnew(preperiodsurplus,sumaccrual).multiply(managementpre),investPersonId,investPersonName,i+1 // 收入金额
								);
						sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService, // 资金类型
								DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i), -1),  //计划日期
								BigDecimal.valueOf(0),  // 支出金额
								FundIntentListPro3.calculAccrualnew(preperiodsurplus,sumaccrual).multiply(financepre),investPersonId,investPersonName,i+1  // 收入金额
								);*/
					}
					list.add(sf1);
				/*	if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
					if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
					list.add(sf);
					preperiodsurplus=preperiodsurplus.subtract(sf.getIncomeMoney());
				}
		     }
		}
		//等本等息
		if (accrualType.equals("sameprincipalandInterest") && payaccrualType.equals("monthPay")) {
			//算法： 本金*(1+利息) /期数
			/****
			 * 应该算出每次应还的本金和每期应还的利息
			 * **/
				
		}
		
		
		
		//
		if (accrualType.equals("sameprincipalandInterest") && payaccrualType.equals("monthPay") && isPreposePayAccrual == 0) {// 月尾
			periodtimemoney=FundIntentListPronew.periodtimemoney(accrual,projectMoney,payintentPeriod).divide(new BigDecimal(1),2,BigDecimal.ROUND_HALF_UP);
			if(isStartDatePay.equals("1")){ //固定在某日	
				String firstdays=Integer.valueOf(DateUtil.getDaysBetweenDate( date1,sdf.format(DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), 1)))+1).toString();
				sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual, // 资金类型
						DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), 1),  //计划日期
						BigDecimal.valueOf(0),// 支出金额
						FundIntentListPro3.calculAccrualnew(preperiodsurplus,accrual).divide(new BigDecimal(30),100,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(firstdays)),investPersonId,investPersonName,// 收入金额
						1
				);
				BigDecimal firstperiodtimemoney=periodtimemoney.add(sf1.getIncomeMoney().subtract(FundIntentListPro3.calculAccrualnew(preperiodsurplus,accrual)));
			    sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,   // 资金类型
						DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), 1),  //计划日期
						BigDecimal.valueOf(0),// 支出金额
						firstperiodtimemoney.subtract(sf1.getIncomeMoney()),investPersonId,investPersonName,  // 收入金额
						1
			    );
				list.add(sf1);
		    	BigDecimal managementConsultingOfRate1=managementConsultingOfRate.divide(new BigDecimal(30),100,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(firstdays));
		    	BigDecimal financeServiceOfRate1=financeServiceOfRate.divide(new BigDecimal(30),100,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(firstdays));
		    	/*sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,    // 资金类型
					DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), 1),  //计划日期
					BigDecimal.valueOf(0),  // 支出金额
					projectMoney.multiply(managementConsultingOfRate1),investPersonId,investPersonName,1 // 收入金额
				);
				sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,   // 资金类型
						DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), 1),  //计划日期
						BigDecimal.valueOf(0),  // 支出金额
						projectMoney.multiply(financeServiceOfRate1) ,investPersonId,investPersonName,1  // 收入金额
				);
				if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
				if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
				list.add(sf);
				preperiodsurplus=preperiodsurplus.subtract(sf.getIncomeMoney());
				for (int i = 2; i < payintentPeriod; i++) {
					sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,  // 资金类型
							DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), i),  //计划日期
							BigDecimal.valueOf(0),   // 支出金额
							FundIntentListPro3.calculAccrualnew(preperiodsurplus,accrual),investPersonId,investPersonName,  // 收入金额
							i
					);
				    sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,   // 资金类型
							DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), i),  //计划日期
							BigDecimal.valueOf(0),   // 支出金额
							periodtimemoney.subtract(FundIntentListPro3.calculAccrualnew(preperiodsurplus,accrual)),investPersonId,investPersonName,// 收入金额
							i
				    );
				    list.add(sf1);
				   /* sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
							DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), i),  //计划日期
							BigDecimal.valueOf(0),     // 支出金额
							projectMoney.multiply(managementConsultingOfRate),investPersonId,investPersonName ,i    // 收入金额
					);
					sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
							DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), i),  //计划日期
							BigDecimal.valueOf(0),     // 支出金额
							projectMoney.multiply(financeServiceOfRate) ,investPersonId,investPersonName,i       // 收入金额
					);
					if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
					if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
					list.add(sf);
					preperiodsurplus=preperiodsurplus.subtract(sf.getIncomeMoney());
				}
				if(payintentPeriod>1){
					 String enddays=Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), payintentPeriod-1)),sdf.format(DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod), -1)))).toString();
					 sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,   // 资金类型
							DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod), -1),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							FundIntentListPro3.calculAccrualnew(preperiodsurplus,accrual).divide(new BigDecimal(30),100,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(enddays)),investPersonId,investPersonName,      // 收入金额
							payintentPeriod
					 );
			    	 BigDecimal endperiodtimemoney=periodtimemoney.subtract((FundIntentListPro3.calculAccrualnew(preperiodsurplus,accrual)).subtract(sf1.getIncomeMoney()));
					 sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,     // 资金类型
				    		DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod), -1),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							endperiodtimemoney.subtract(sf1.getIncomeMoney()),investPersonId,investPersonName,        // 收入金额
							payintentPeriod
					 );
					 list.add(sf1);
			    	 BigDecimal managementConsultingOfRate2=managementConsultingOfRate.divide(new BigDecimal(30),100,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(enddays));
			    	 BigDecimal financeServiceOfRate2=financeServiceOfRate.divide(new BigDecimal(30),100,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(enddays));
		
			    	/* sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
				    		DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod), -1),  //计划日期
							BigDecimal.valueOf(0),      // 支出金额
							projectMoney.multiply(managementConsultingOfRate2),investPersonId,investPersonName,payintentPeriod   // 收入金额
					 );
					 sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
								DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod), -1),  //计划日期
								BigDecimal.valueOf(0),     // 支出金额
								projectMoney.multiply(financeServiceOfRate2),investPersonId,investPersonName,payintentPeriod       // 收入金额
					 );
					 if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
					 if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
					 list.add(sf);
			}
			preperiodsurplus=preperiodsurplus.subtract(sf.getIncomeMoney());
	     }else{
				for (int i = 1; i < payintentPeriod+1; i++) {
					sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,         // 资金类型
							DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i), -1),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							FundIntentListPro3.calculAccrualnew(preperiodsurplus,sumaccrual).multiply(accrualpre),investPersonId,investPersonName,i        // 收入金额
					);
				    sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
				    		DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i), -1),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							periodtimemoney.subtract(FundIntentListPro3.calculAccrualnew(preperiodsurplus,accrual)),investPersonId,investPersonName,i        // 收入金额
					);
				   /* sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
				    		DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i), -1),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							FundIntentListPro3.calculAccrualnew(preperiodsurplus,sumaccrual).multiply(managementpre),investPersonId,investPersonName,i          // 收入金额
					);
					sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
							DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i), -1),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							FundIntentListPro3.calculAccrualnew(preperiodsurplus,sumaccrual).multiply(financepre),investPersonId,investPersonName,i          // 收入金额
					);*/
					list.add(sf1);
					/*if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
					if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
					list.add(sf);
					preperiodsurplus=preperiodsurplus.subtract(sf.getIncomeMoney());
				}
		     }
		}
		if (accrualType.equals("sameprincipal") && payaccrualType.equals("monthPay")&& isPreposePayAccrual == 1) {// 月头
			 BigDecimal sameprojectMoney = projectMoney.divide(new BigDecimal(payintentPeriod),100,BigDecimal.ROUND_HALF_UP);
			 if(isStartDatePay.equals("1")){ //固定在某日	
				 if(payintentPeriod>1){
					 	String firstdays=Integer.valueOf(DateUtil.getDaysBetweenDate( date1,sdf.format(DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), 1)))+1).toString();
					 	sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
							DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), 0),  //计划日期
							BigDecimal.valueOf(0),          // 支出金额
							FundIntentListPro3.calculAccrualnew(sameprojectMoney,accrual).divide(new BigDecimal(30),100,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(firstdays)),investPersonId,investPersonName,      // 收入金额
							1
					 	);
					 	sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
							DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), 1),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							sameprojectMoney,investPersonId,investPersonName,
							1	// 收入金额
						);      
					 	list.add(sf1);
		
			    		BigDecimal  managementConsultingOfRate1=managementConsultingOfRate.divide(new BigDecimal(30),100,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(firstdays));
			    		BigDecimal  financeServiceOfRate1=financeServiceOfRate.divide(new BigDecimal(30),100,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(firstdays));
			    		/*sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
			    				DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), 0),  //计划日期
			    				BigDecimal.valueOf(0),                                                // 支出金额
			    				projectMoney.multiply(managementConsultingOfRate1),investPersonId,investPersonName,1          // 收入金额
						);
			    		sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
								DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), 0),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.multiply(financeServiceOfRate1) ,investPersonId,investPersonName,1         // 收入金额
						);
					
						if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
						if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
			    
						list.add(sf);
						preperiodsurplus=preperiodsurplus.subtract(sf.getIncomeMoney());
				 
						for (int i = 2; i <payintentPeriod; i++) {
							 sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
										DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), i-1),  //计划日期
										BigDecimal.valueOf(0),                                                // 支出金额
										FundIntentListPro3.calculAccrualnew(sameprojectMoney,accrual),investPersonId,investPersonName,
										i
							  );        // 收入金额
							  sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
										DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), i),  //计划日期
										BigDecimal.valueOf(0),                                                // 支出金额
										sameprojectMoney,investPersonId,investPersonName,
										i
							  );      // 收入金额
							  
							  list.add(sf1);
						
							 /* sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
									DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), i-1),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									projectMoney.multiply(managementConsultingOfRate),investPersonId,investPersonName,i          // 收入金额
							  );
							  sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
									DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), i-1),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									projectMoney.multiply(financeServiceOfRate) ,investPersonId,investPersonName,i         // 收入金额
							  );
								
							  if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
							  if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
							  list.add(sf);
							  preperiodsurplus=preperiodsurplus.subtract(sf.getIncomeMoney());
						}
				}
				String enddays=Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), payintentPeriod-1)), sdf.format(DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod), -1)))).toString();
				sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
						DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), payintentPeriod-1),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						FundIntentListPro3.calculAccrualnew(preperiodsurplus,accrual).divide(new BigDecimal(30),100,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(enddays)),investPersonId,investPersonName,      // 收入金额
						payintentPeriod
				 );
			     sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
			    		DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod), -1),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						sameprojectMoney,investPersonId,investPersonName,     // 收入金额
						payintentPeriod
			     );
				 list.add(sf1);
		
		    	 BigDecimal managementConsultingOfRate2=managementConsultingOfRate.divide(new BigDecimal(30),100,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(enddays));
		    	 BigDecimal financeServiceOfRate2=financeServiceOfRate.divide(new BigDecimal(30),100,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(enddays));
				    	
		    	/* sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
		    			DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), payintentPeriod-1),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						projectMoney.multiply(managementConsultingOfRate2),investPersonId,investPersonName,payintentPeriod         // 收入金额
				 );
				 sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
						DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), payintentPeriod-1),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						projectMoney.multiply(financeServiceOfRate2),investPersonId,investPersonName,payintentPeriod        // 收入金额
				 );
					
				 if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
				 if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
			
				 list.add(sf);
				 preperiodsurplus=preperiodsurplus.subtract(sf.getIncomeMoney());
			 }else{
				 for (int i = 0; i < payintentPeriod; i++) {
					if(i==0){
						sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
								DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPro3.calculAccrualnew(preperiodsurplus,sumaccrual).multiply(accrualpre),investPersonId,investPersonName,1 	
						);        // 收入金额
					    sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
								DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								sameprojectMoney ,investPersonId,investPersonName ,1	
						);      // 收入金额
								
					   /* sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
								DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPro3.calculAccrualnew(preperiodsurplus,sumaccrual).multiply(managementpre),investPersonId,investPersonName,1   // 收入金额
						);
						sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
								DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPro3.calculAccrualnew(preperiodsurplus,sumaccrual).multiply(financepre),investPersonId,investPersonName,1          // 收入金额
						);*/
					}else{
						sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
								DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i), -1),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPro3.calculAccrualnew(preperiodsurplus,sumaccrual).multiply(accrualpre),investPersonId,investPersonName ,i+1	
						);        // 收入金额
					    sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
					    		DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i), -1),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								sameprojectMoney,investPersonId,investPersonName,i+1  
						);      // 收入金额
								
					   /* sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
					    		DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i), -1),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPro3.calculAccrualnew(preperiodsurplus,sumaccrual).multiply(managementpre),investPersonId,investPersonName,i+1          // 收入金额
						);
						sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
								DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i), -1),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPro3.calculAccrualnew(preperiodsurplus,sumaccrual).multiply(financepre) ,investPersonId,investPersonName,i+1         // 收入金额
						);*/
					} 
					list.add(sf1);
					/*if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
					if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
					list.add(sf);
					preperiodsurplus=preperiodsurplus.subtract(sf.getIncomeMoney());
				 }
			 }
		}
		if (accrualType.equals("sameprincipal") && payaccrualType.equals("monthPay")&& isPreposePayAccrual == 0) {// 月尾
			 BigDecimal sameprojectMoney = projectMoney.divide(new BigDecimal(payintentPeriod),100,BigDecimal.ROUND_HALF_UP);
			 if(isStartDatePay.equals("1")){ //固定在某日	
				 if(payintentPeriod>1){
					 String firstdays=Integer.valueOf(DateUtil.getDaysBetweenDate( date1,sdf.format(DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), 1)))+1).toString();
				 	 sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
							DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), 1),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							FundIntentListPro3.calculAccrualnew(sameprojectMoney,accrual).divide(new BigDecimal(30),100,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(firstdays)),investPersonId,investPersonName,      // 收入金额
							1
					 );
		    	    sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
							DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), 1),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							sameprojectMoney,investPersonId,investPersonName,
							1
					);      // 收入金额
		    	    list.add(sf1);
		    		BigDecimal  managementConsultingOfRate1=managementConsultingOfRate.divide(new BigDecimal(30),100,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(firstdays));
		    		BigDecimal  financeServiceOfRate1=financeServiceOfRate.divide(new BigDecimal(30),100,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(firstdays));
		    		/*sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
							DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), 1),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							projectMoney.multiply(managementConsultingOfRate1),investPersonId,investPersonName,1          // 收入金额
					);
					sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
							DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), 1),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							projectMoney.multiply(financeServiceOfRate1) ,investPersonId,investPersonName,1         // 收入金额
					);*/
					
					/*if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
					if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
					list.add(sf);
					preperiodsurplus=preperiodsurplus.subtract(sf.getIncomeMoney());
					for (int i = 2; i <payintentPeriod; i++) {
						sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
								DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), i),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPro3.calculAccrualnew(sameprojectMoney,accrual),investPersonId,investPersonName,
								i
						);        // 收入金额
					    sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
								DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), i),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								sameprojectMoney,investPersonId,investPersonName,
								i
						);      // 收入金额
					    list.add(sf1);
				
					  /*  sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
								DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), i),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.multiply(managementConsultingOfRate),investPersonId,investPersonName,i          // 收入金额
						);
						sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
								DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), i),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.multiply(financeServiceOfRate) ,investPersonId,investPersonName,i         // 收入金额
						);
						
						if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
						if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
						
						list.add(sf);
						
						preperiodsurplus=preperiodsurplus.subtract(sf.getIncomeMoney());
					}
				 }
				 String enddays=Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), payintentPeriod-1)), sdf.format(DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod), -1)))).toString();
				 sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
						DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod), -1),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						FundIntentListPro3.calculAccrualnew(preperiodsurplus,accrual).divide(new BigDecimal(30),100,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(enddays)),investPersonId,investPersonName,      // 收入金额
						payintentPeriod
				  );
			     sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
			    		DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod), -1),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						sameprojectMoney,investPersonId,investPersonName,     // 收入金额
						payintentPeriod
			     );
		     
			     list.add(sf1);
			
		    	 BigDecimal managementConsultingOfRate2=managementConsultingOfRate.divide(new BigDecimal(30),100,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(enddays));
		    	 BigDecimal financeServiceOfRate2=financeServiceOfRate.divide(new BigDecimal(30),100,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(enddays));
	
		    	/* sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
			    		DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod), -1),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						projectMoney.multiply(managementConsultingOfRate2),investPersonId,investPersonName,payintentPeriod         // 收入金额
				 );
				 sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
						DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod), -1),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						projectMoney.multiply(financeServiceOfRate2),investPersonId,investPersonName,payintentPeriod        // 收入金额
				 );*/
				/*		
				 if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
				 if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
				 list.add(sf);
				 preperiodsurplus=preperiodsurplus.subtract(sf.getIncomeMoney());
			 }else{
				 for (int i = 1; i < payintentPeriod+1; i++) {
					 sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
							DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i), -1),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							FundIntentListPro3.calculAccrualnew(preperiodsurplus,sumaccrual).multiply(accrualpre),investPersonId,investPersonName,i 	
					 );        // 收入金额
				     sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
				    		DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i), -1),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							sameprojectMoney ,investPersonId,investPersonName,i 	
					 );      // 收入金额
							
				    /* sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
				    		DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i), -1),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							FundIntentListPro3.calculAccrualnew(preperiodsurplus,sumaccrual).multiply(managementpre),investPersonId,investPersonName,i          // 收入金额
					 );
					 sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
							DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i), -1),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							FundIntentListPro3.calculAccrualnew(preperiodsurplus,sumaccrual).multiply(financepre),investPersonId,investPersonName,i          // 收入金额
					);*/
					list.add(sf1);
				/*	if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
					if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
					list.add(sf);
					preperiodsurplus=preperiodsurplus.subtract(sf.getIncomeMoney());
				 }
			 }
		}
		
		if (accrualType.equals("ontTimeAccrual")  && isPreposePayAccrual == 1) {// 一次性头
       	 	sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
					DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), 0),  //计划日期
					BigDecimal.valueOf(0),                                                // 支出金额
					FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(accrualpre),investPersonId,investPersonName,1 	
			);        // 收入金额
						
			/*sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
				DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), 0),  //计划日期
				BigDecimal.valueOf(0),                                                // 支出金额
				FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(managementpre),investPersonId,investPersonName,1          // 收入金额
			);
			sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
					DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), 0),  //计划日期
					BigDecimal.valueOf(0),                                                // 支出金额
					FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(financepre),investPersonId,investPersonName,1          // 收入金额
			);*/

       	 	sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
					DateUtil.addMonthsToDate(DateUtil.parseDate(date2, "yyyy-MM-dd"),0),  //计划日期
					BigDecimal.valueOf(0),                                                // 支出金额
					projectMoney,investPersonId,investPersonName,1  	
			);      // 收入金额
	       	list.add(sf1);	
	      /* 	if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
			if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
	       	list.add(sf);
		}
        if (accrualType.equals("ontTimeAccrual")  && isPreposePayAccrual == 0) {// 一次性尾
       	 	sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
					DateUtil.addMonthsToDate(DateUtil.parseDate(date2, "yyyy-MM-dd"), 0),  //计划日期
					BigDecimal.valueOf(0),                                                // 支出金额
					FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(accrualpre),investPersonId,investPersonName,1 	
			);        // 收入金额
		   /* sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
					DateUtil.addMonthsToDate(DateUtil.parseDate(date2, "yyyy-MM-dd"), 0),  //计划日期
					BigDecimal.valueOf(0),                                                // 支出金额
					FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(managementpre),investPersonId,investPersonName,1         // 收入金额
			);
			sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
					DateUtil.addMonthsToDate(DateUtil.parseDate(date2, "yyyy-MM-dd"), 0),  //计划日期
					BigDecimal.valueOf(0),                                                // 支出金额
					FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(financepre),investPersonId,investPersonName,1          // 收入金额
			);*/

			sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
					DateUtil.addMonthsToDate(DateUtil.parseDate(date2, "yyyy-MM-dd"),0),  //计划日期
					BigDecimal.valueOf(0),                                                // 支出金额
					projectMoney,investPersonId,investPersonName,1  	
			);      // 收入金额
	    	list.add(sf1);	
	    	/*if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
			if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
	    	list.add(sf);
		}
		if (accrualType.equals("singleInterest") && payaccrualType.equals("dayPay") && isPreposePayAccrual == 1) {// 日头
			 for (int i = 0; i < payintentPeriod; i++) {
				 sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
						DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(accrualpre),investPersonId,investPersonName,i+1 	
				 );        // 收入金额
				/* sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
						DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(managementpre),investPersonId,investPersonName,i+1          // 收入金额
				  );
				 sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
						DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(financepre),investPersonId,investPersonName ,i+1         // 收入金额
				 );*/
				 list.add(sf1);
				 /*if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
				 if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
			 }
			 sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
					DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), payintentPeriod-1),  //计划日期
					BigDecimal.valueOf(0),                                                // 支出金额
					projectMoney,investPersonId,investPersonName,payintentPeriod  	
			 );      // 收入金额
			 list.add(sf);
		}
        if (accrualType.equals("singleInterest") && payaccrualType.equals("dayPay") && isPreposePayAccrual == 0) {// 日尾
        	for (int i = 1; i < payintentPeriod+1; i++) {
				 sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
						DateUtil.addDaysToDate(DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i), -1),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(accrualpre),investPersonId,investPersonName,i 	
				);        // 收入金额
			   /* sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
			    		DateUtil.addDaysToDate(DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i), -1),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(managementpre),investPersonId,investPersonName,i          // 收入金额
				);
				sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
						DateUtil.addDaysToDate(DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i), -1),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(financepre) ,investPersonId,investPersonName,i         // 收入金额
				);*/
				list.add(sf1);
				/*if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
				if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
			 }
			 sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
		    		DateUtil.addDaysToDate(DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), payintentPeriod), -1),  //计划日期
					BigDecimal.valueOf(0),                                                // 支出金额
					projectMoney,investPersonId,investPersonName,payintentPeriod  	
			);      // 收入金额
			list.add(sf);
		}
		if (accrualType.equals("singleInterest") && payaccrualType.equals("monthPay") && isPreposePayAccrual == 1) {// 月头
			 Date enddate=DateUtil.addMonthsToDate(DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), -1),payintentPeriod);
			 if(isStartDatePay.equals("1")){ //固定在某日
				 BigDecimal shanshi=new BigDecimal("30");
				 BigDecimal firstPerioddays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate(date1,startperiodDate)+1).toString());
				 BigDecimal a=FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(firstPerioddays.divide(shanshi,100,BigDecimal.ROUND_HALF_UP));
				 sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
						DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), 0),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						a.multiply(accrualpre),investPersonId,investPersonName,0 	
				 );        // 收入金额
				/* sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
						DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), 0),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						a.multiply(managementpre) ,investPersonId,investPersonName,0         // 收入金额
				 );
				 sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
						DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), 0),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						a.multiply(financepre),investPersonId,investPersonName ,0         // 收入金额
				 );*/
				 list.add(sf1);
			/*	 if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
				 if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
				 for (int i = 0; i < payintentPeriod-1; i++) {
					 sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
							DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), i),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(accrualpre),investPersonId,investPersonName ,i+1	
					 );        // 收入金额
					/* sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
							DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), i),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(managementpre),investPersonId,investPersonName,i+1          // 收入金额
					 );
					 sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
							DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), i),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(financepre),investPersonId,investPersonName ,i+1         // 收入金额
					 );
					 list.add(sf1);
					 if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
					 if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
				 }
				 if(payintentPeriod>1){
					 Date adate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"),payintentPeriod), -1);
					 Date bdate=DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"),payintentPeriod-1);
					 sdf = new SimpleDateFormat("yyyy-MM-dd");
					 BigDecimal endPerioddays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(bdate), sdf.format(adate))).toString());
					 BigDecimal b=FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(endPerioddays.divide(shanshi,100,BigDecimal.ROUND_HALF_UP));
					 sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
							DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"),payintentPeriod-1),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							b.multiply(accrualpre),investPersonId,investPersonName,payintentPeriod 	
					 );        // 收入金额
				    /* sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
							DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), payintentPeriod-1),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							b.multiply(managementpre) ,investPersonId,investPersonName ,payintentPeriod        // 收入金额
					 );
					 sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
							DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"),payintentPeriod-1),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							b.multiply(financepre) ,investPersonId,investPersonName,payintentPeriod         // 收入金额
					 );*/
					list.add(sf1);
					/*if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
					if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
				 }
		}else{
			for (int i = 0; i < payintentPeriod; i++) {
				 if(i==0){
					 sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
							DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(accrualpre),investPersonId,investPersonName,1 	
					 );        // 收入金额
					/* sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
							DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(managementpre),investPersonId,investPersonName,1          // 收入金额
					);
					sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
							DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(financepre),investPersonId,investPersonName,1          // 收入金额
					);*/
					list.add(sf1);
					/*if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
					if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
				 }else{
					 sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
							DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i), -1),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(accrualpre),investPersonId,investPersonName,i+1 	
					 );        // 收入金额
				    /* sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
				    		DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i), -1),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(managementpre),investPersonId,investPersonName,i+1          // 收入金额
					 );
					 sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
							DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i), -1),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(financepre),investPersonId,investPersonName,i+1          // 收入金额
					 );*/
					 list.add(sf1);
					/* if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
					 if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
				 }
			 }
		}
		    sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
		    		DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), payintentPeriod), -1),  //计划日期
					BigDecimal.valueOf(0),                                                // 支出金额
					projectMoney,investPersonId,investPersonName,payintentPeriod  	
			);      // 收入金额
			list.add(sf);
	  }
      if (accrualType.equals("singleInterest") && payaccrualType.equals("monthPay") && isPreposePayAccrual == 0) {// 月头尾
    		 Date adate=DateUtil.addMonthsToDate(DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), -1),payintentPeriod);
    		 if(isStartDatePay.equals("1")){ //固定在某日
	  			 BigDecimal shanshi=new BigDecimal("30");
	  			 BigDecimal firstPerioddays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate(date1,startperiodDate)+1).toString());
	  			 BigDecimal a=FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(firstPerioddays.divide(shanshi,100,BigDecimal.ROUND_HALF_UP));
  		
	  			 sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
	  					DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), 0),  //计划日期
	  					BigDecimal.valueOf(0),                                                // 支出金额
	  					a.multiply(accrualpre),investPersonId,investPersonName,0 	
  				 );        // 收入金额

	  		   /* sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
	  					DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), 0),  //计划日期
	  					BigDecimal.valueOf(0),                                                // 支出金额
	  					a.multiply(managementpre),investPersonId,investPersonName,0          // 收入金额
	  			);
	  			sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
	  					DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), 0),  //计划日期
	  					BigDecimal.valueOf(0),                                                // 支出金额
	  					a.multiply(financepre) ,investPersonId,investPersonName,0         // 收入金额
	  			);*/
	  			list.add(sf1);
	  			/*if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
	  			if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
	  			for (int i = 1; i < payintentPeriod; i++) {
	  				sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
  							DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), i),  //计划日期
  							BigDecimal.valueOf(0),                                                // 支出金额
  							FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(accrualpre),investPersonId,investPersonName,i 	
  					);        // 收入金额
  	
  				  /*  sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
  							DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), i),  //计划日期
  							BigDecimal.valueOf(0),                                                // 支出金额
  							FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(managementpre),investPersonId,investPersonName,i          // 收入金额
  					);
  					sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
  							DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), i),  //计划日期
  							BigDecimal.valueOf(0),                                                // 支出金额
  							FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(financepre),investPersonId,investPersonName,i          // 收入金额
  					);*/
  					list.add(sf1);
  					/*if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
  					if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
  			  };
  			  if(payintentPeriod>1){
  				  adate=	DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), payintentPeriod), -1);  //计划日期
  				  Date bdate=DateUtil.addMonthsToDate(DateUtil.addDaysToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), 0),payintentPeriod-1);
  				  sdf = new SimpleDateFormat("yyyy-MM-dd");
  				  BigDecimal endPerioddays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(bdate), sdf.format(adate))).toString());
  				  BigDecimal b=FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(endPerioddays.divide(shanshi,100,BigDecimal.ROUND_HALF_UP));
  				  sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
  						  DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), payintentPeriod), -1),  //计划日期
  						  BigDecimal.valueOf(0),                                                // 支出金额
  						  b.multiply(accrualpre),investPersonId,investPersonName,payintentPeriod 	
  				  );        // 收入金额
  				 /* sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
						DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), payintentPeriod), -1),  //计划日期
	  					BigDecimal.valueOf(0),                                                // 支出金额
	  					b.multiply(managementpre),investPersonId,investPersonName,payintentPeriod          // 收入金额
  				  );
  				  sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
						DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), payintentPeriod), -1),  //计划日期
	  					BigDecimal.valueOf(0),                                                // 支出金额
	  					b.multiply(financepre),investPersonId,investPersonName,payintentPeriod          // 收入金额
  				  );*/
		  		  list.add(sf1);
		  		  /*if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
		  		  if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
  			  }
  		}else{
    	  for (int i = prepayintentPeriod+1; i < payintentPeriod+1; i++) {
				 sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
							DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i),-1),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(accrualpre),investPersonId,investPersonName,i 	);        // 收入金额
	
							
				   /* sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
							DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i), -1),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(managementpre),investPersonId,investPersonName,i          // 收入金额
							);
					sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
							DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i), -1),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(financepre),investPersonId,investPersonName,i          // 收入金额
							);*/
					list.add(sf1);
					/*if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
					if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
				
					
		  			
			 }
    	  
    	  
  		}
			    sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
						DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), payintentPeriod), -1),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						projectMoney,investPersonId,investPersonName,payintentPeriod  	);      // 收入金额
				list.add(sf);
			
		}
		if (accrualType.equals("singleInterest") && payaccrualType.equals("seasonPay") && isPreposePayAccrual == 1) {// 季头
			  Date enddate=DateUtil.addMonthsToDate(DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), -1),payintentPeriod*3);
			if(isStartDatePay.equals("1")){ //固定在某日
				BigDecimal shanshi=new BigDecimal("90");
				BigDecimal firstPerioddays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate(date1,startperiodDate)+1).toString());
				BigDecimal a=FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(firstPerioddays.divide(shanshi,100,BigDecimal.ROUND_HALF_UP));
			
				sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
						DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), 0),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						a.multiply(accrualpre),investPersonId,investPersonName,0 	);        // 收入金额

						
			    /*sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
						DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), 0),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						a.multiply(managementpre),investPersonId,investPersonName,0          // 收入金额
						);
				sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
						DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), 0),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						a.multiply(financepre) ,investPersonId,investPersonName ,0        // 收入金额
						);*/
				list.add(sf1);
				/*if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
				if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
				for (int i = 0; i < payintentPeriod-1; i++) {
					 
					 sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
								DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), i*3),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(accrualpre),investPersonId,investPersonName,i+1 	);        // 收入金额
		
								
					   /* sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
								DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), i*3),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(managementpre),investPersonId,investPersonName,i+1          // 收入金额
								);
						sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
								DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), i*3),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(financepre),investPersonId,investPersonName,i+1          // 收入金额
								);*/
						list.add(sf1);
						/*if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
						if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
					
					 
				 }
				if(payintentPeriod>1){
				 Date adate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"),payintentPeriod*3), -1);
				   Date bdate=DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"),(payintentPeriod-1)*3);
				    sdf = new SimpleDateFormat("yyyy-MM-dd");
				BigDecimal endPerioddays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(bdate), sdf.format(adate))).toString());
				BigDecimal b=FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(endPerioddays.divide(shanshi,100,BigDecimal.ROUND_HALF_UP));
				sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
						DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"),(payintentPeriod-1)*3),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						b.multiply(accrualpre) ,investPersonId,investPersonName,payintentPeriod	);        // 收入金额

						
			   /* sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
						DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), (payintentPeriod-1)*3),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						b.multiply(managementpre),investPersonId,investPersonName,payintentPeriod          // 收入金额
						);
				sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
						DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"),(payintentPeriod-1)*3),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						b.multiply(financepre),investPersonId,investPersonName,payintentPeriod          // 收入金额
						);*/
				list.add(sf1);
				/*if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
				if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
				}
				
				
				
			}else{
			for (int i = 0; i < payintentPeriod; i++) {
				 if(i==0){
					 sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
								DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*3),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(accrualpre),investPersonId,investPersonName,1 	);        // 收入金额
		
								
					    /*sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
								DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*3),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(managementpre),investPersonId,investPersonName,1          // 收入金额
								);
						sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
								DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*3),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(financepre),investPersonId,investPersonName,1          // 收入金额
								);*/
						list.add(sf1);
						/*if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
						if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
					 
					 
				 }else{
					 sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
								DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*3), -1),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(accrualpre),investPersonId,investPersonName,i+1 	);        // 收入金额
		
								
					  /*  sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
					    		DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*3), -1),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(managementpre),investPersonId,investPersonName,i+1          // 收入金额
								);
						sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
								DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*3), -1),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(financepre),investPersonId,investPersonName ,i+1         // 收入金额
								);*/
						list.add(sf1);
						/*if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
						if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
					 
				 }
				
			 }
			}
			    sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
			    		DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), payintentPeriod*3), -1),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						projectMoney,investPersonId,investPersonName,payintentPeriod  	);      // 收入金额
				list.add(sf);
		}
       if (accrualType.equals("singleInterest") && payaccrualType.equals("seasonPay") && isPreposePayAccrual == 0) {// 季尾
    	   Date enddate=DateUtil.addMonthsToDate(DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), -1),payintentPeriod*3);
    	   if(isStartDatePay.equals("1")){ //固定在某日
     			BigDecimal shanshi=new BigDecimal("90");
     			BigDecimal firstPerioddays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate(date1,startperiodDate)+1).toString());
     			BigDecimal a=FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(firstPerioddays.divide(shanshi,100,BigDecimal.ROUND_HALF_UP));
     		
     			sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
     					DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), 0),  //计划日期
     					BigDecimal.valueOf(0),                                                // 支出金额
     					a.multiply(accrualpre),investPersonId,investPersonName,0 	);        // 收入金额

     					
     		    /*sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
     					DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), 0),  //计划日期
     					BigDecimal.valueOf(0),                                                // 支出金额
     					a.multiply(managementpre),investPersonId,investPersonName,0          // 收入金额
     					);
     			sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
     					DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), 0),  //计划日期
     					BigDecimal.valueOf(0),                                                // 支出金额
     					a.multiply(financepre) ,investPersonId,investPersonName,0         // 收入金额
     					);*/
     			list.add(sf1);
     			/*if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
     			if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
     			 for (int i = 1; i < payintentPeriod; i++) {
    				 
    				 sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
    							DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), i*3),  //计划日期
    							BigDecimal.valueOf(0),                                                // 支出金额
    							FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(accrualpre),investPersonId,investPersonName ,i	);        // 收入金额
    	
    							
    				 /*   sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
    							DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), i*3),  //计划日期
    							BigDecimal.valueOf(0),                                                // 支出金额
    							FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(managementpre),investPersonId,investPersonName,i          // 收入金额
    							);
    					sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
    							DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), i*3),  //计划日期
    							BigDecimal.valueOf(0),                                                // 支出金额
    							FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(financepre),investPersonId,investPersonName,i          // 收入金额
    							);*/
    					list.add(sf1);
    					/*if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
    					if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
    				
    				 
    			 }
     			if(payintentPeriod>1){
     			 Date adate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"),payintentPeriod*3), -1);
     			   Date bdate=DateUtil.addMonthsToDate(DateUtil.addDaysToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), 0),(payintentPeriod-1)*3);
     			    sdf = new SimpleDateFormat("yyyy-MM-dd");
     			BigDecimal endPerioddays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(bdate), sdf.format(adate))).toString());
     			BigDecimal b=FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(endPerioddays.divide(shanshi,100,BigDecimal.ROUND_HALF_UP));
     			sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
     					adate,  //计划日期
     					BigDecimal.valueOf(0),                                                // 支出金额
     					b.multiply(accrualpre),investPersonId,investPersonName,payintentPeriod 	);        // 收入金额

     					
     		   /* sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
     		    		adate,  //计划日期
     					BigDecimal.valueOf(0),                                                // 支出金额
     					b.multiply(managementpre) ,investPersonId,investPersonName,payintentPeriod         // 收入金额
     					);
     			sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
     					adate,  //计划日期
     					BigDecimal.valueOf(0),                                                // 支出金额
     					b.multiply(financepre),investPersonId,investPersonName,payintentPeriod          // 收入金额
     					);*/
     			list.add(sf1);
     			/*if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
     			if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
     			}
     			
     			
     		}else{
    	   for (int i = 1; i < payintentPeriod+1; i++) {
				 
				 sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
							DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*3), -1),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(accrualpre),investPersonId,investPersonName,i 	);        // 收入金额
	
							
				    /*sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
							DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*3), -1),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(managementpre),investPersonId,investPersonName,i          // 收入金额
							);
					sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
							DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*3), -1),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(financepre),investPersonId,investPersonName,i          // 收入金额
							);*/
					list.add(sf1);
					/*if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
					if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
				
				 
			 }
     		}
			    sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
						DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), payintentPeriod*3), -1),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						projectMoney ,investPersonId,investPersonName,payintentPeriod 	);      // 收入金额
				list.add(sf);
		}
		
		if (accrualType.equals("singleInterest") && payaccrualType.equals("yearPay") && isPreposePayAccrual == 1 ) {// 年头
			  Date enddate=DateUtil.addMonthsToDate(DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), -1),payintentPeriod*12);
			if(isStartDatePay.equals("1")){ //固定在某日
				BigDecimal shanshi=new BigDecimal("360");
				BigDecimal firstPerioddays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate(date1,startperiodDate)+1).toString());
				BigDecimal a=FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(firstPerioddays.divide(shanshi,100,BigDecimal.ROUND_HALF_UP));
			
				sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
						DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), 0),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						a.multiply(accrualpre),investPersonId,investPersonName,0 	);        // 收入金额

						
			   /* sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
						DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), 0),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						a.multiply(managementpre),investPersonId,investPersonName,0          // 收入金额
						);
				sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
						DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), 0),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						a.multiply(financepre),investPersonId,investPersonName,0          // 收入金额
						);*/
				list.add(sf1);
				/*if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
				if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
				 for (int i = 0; i < payintentPeriod-1; i++) {
					 
					 sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
								DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), i*12),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(accrualpre),investPersonId,investPersonName,i+1 	);        // 收入金额
		
								
					   /* sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
								DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), i*12),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(managementpre),investPersonId,investPersonName,i+1          // 收入金额
								);
						sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
								DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), i*12),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(financepre),investPersonId,investPersonName,i+1          // 收入金额
								);*/
						list.add(sf1);
					/*	if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
						if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
					
					 
				 }
				 if(payintentPeriod>1){
				 Date adate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"),payintentPeriod*12), -1);
				   Date bdate=DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"),(payintentPeriod-1)*12);
				    sdf = new SimpleDateFormat("yyyy-MM-dd");
				BigDecimal endPerioddays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(bdate), sdf.format(adate))).toString());
				BigDecimal b=FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(endPerioddays.divide(shanshi,100,BigDecimal.ROUND_HALF_UP));
				sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
						DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"),(payintentPeriod-1)*12),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						b.multiply(accrualpre),investPersonId,investPersonName,payintentPeriod 	);        // 收入金额

						
			   /* sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
						DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), (payintentPeriod-1)*12),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						b.multiply(managementpre),investPersonId,investPersonName,payintentPeriod          // 收入金额
						);
				sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
						DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"),(payintentPeriod-1)*12),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						b.multiply(financepre),investPersonId,investPersonName,payintentPeriod          // 收入金额
						);*/
				list.add(sf1);
				/*if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
				if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
				 }
				
				
				
			}else{
        for (int i = 0; i < payintentPeriod; i++) {
				 if(i==0){
					 sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
								DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*12),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(accrualpre),investPersonId,investPersonName,1 	);        // 收入金额
		
								
					    /*sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
								DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*12),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(managementpre),investPersonId,investPersonName,1          // 收入金额
								);
						sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
								DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*12),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(financepre) ,investPersonId,investPersonName,1         // 收入金额
								);*/
						list.add(sf1);
						/*if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
						if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
					 
				 }else{
					 
					 sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
								DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*12), -1),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(accrualpre),investPersonId,investPersonName,i+1 	);        // 收入金额
		
								
					   /* sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
					    		DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*12), -1),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(managementpre),investPersonId,investPersonName,i+1          // 收入金额
								);
						sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
								DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*12), -1),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(financepre),investPersonId,investPersonName,i+1          // 收入金额
								);*/
						list.add(sf1);
					/*	if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
						if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
					 
				 }
				 
				
				 
			 }
        
			}
			    sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
			    		DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), payintentPeriod*12), -1),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						projectMoney,investPersonId,investPersonName,payintentPeriod  	);      // 收入金额
				list.add(sf);
			
		}
       if (accrualType.equals("singleInterest") && payaccrualType.equals("yearPay") && isPreposePayAccrual == 0) {// 年尾
    	   Date enddate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"),payintentPeriod*12), -1);
    	   if(isStartDatePay.equals("1")){ //固定在某日
    			BigDecimal shanshi=new BigDecimal("360");
    			BigDecimal firstPerioddays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate(date1,startperiodDate)+1).toString());
    			BigDecimal a=FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(firstPerioddays.divide(shanshi,100,BigDecimal.ROUND_HALF_UP));
    		
    			sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
    					DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), 0),  //计划日期
    					BigDecimal.valueOf(0),                                                // 支出金额
    					a.multiply(accrualpre),investPersonId,investPersonName,0 	);        // 收入金额

    					
    		   /* sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
    					DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), 0),  //计划日期
    					BigDecimal.valueOf(0),                                                // 支出金额
    					a.multiply(managementpre),investPersonId,investPersonName,0          // 收入金额
    					);
    			sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
    					DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), 0),  //计划日期
    					BigDecimal.valueOf(0),                                                // 支出金额
    					a.multiply(financepre),investPersonId,investPersonName,0          // 收入金额
    					);*/
    			list.add(sf1);
    			/*if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
    			if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
    			 for (int i = 1; i < payintentPeriod; i++) {
    				 
    				 sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
    							DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), i*12),  //计划日期
    							BigDecimal.valueOf(0),                                                // 支出金额
    							FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(accrualpre),investPersonId,investPersonName,i 	);        // 收入金额
    	
    							
    				   /* sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
    							DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), i*12),  //计划日期
    							BigDecimal.valueOf(0),                                                // 支出金额
    							FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(managementpre),investPersonId,investPersonName,i          // 收入金额
    							);
    					sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
    							DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), i*12),  //计划日期
    							BigDecimal.valueOf(0),                                                // 支出金额
    							FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(financepre),investPersonId,investPersonName,i          // 收入金额
    							);*/
    					list.add(sf1);
    					/*if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
    					if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
    				
    				 
    			 }
    			 if(payintentPeriod>1){
	    			 	Date adate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"),payintentPeriod*12), -1);
	    			    Date bdate=DateUtil.addMonthsToDate(DateUtil.addDaysToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), 0),(payintentPeriod-1)*12);
	    			    sdf = new SimpleDateFormat("yyyy-MM-dd");
		    			BigDecimal endPerioddays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(bdate), sdf.format(adate))).toString());
		    			BigDecimal b=FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(endPerioddays.divide(shanshi,100,BigDecimal.ROUND_HALF_UP));
		    			sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
	    					adate,  //计划日期
	    					BigDecimal.valueOf(0),                                                // 支出金额
	    					b.multiply(accrualpre),investPersonId,investPersonName,payintentPeriod 	
	    				);        // 收入金额
	    					
		    			/*sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
	    		    		adate,  //计划日期
	    					BigDecimal.valueOf(0),                                                // 支出金额
	    					b.multiply(managementpre) ,investPersonId,investPersonName,payintentPeriod         // 收入金额
	    				);
		    			sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
	    					adate,  //计划日期
	    					BigDecimal.valueOf(0),                                                // 支出金额
	    					b.multiply(financepre) ,investPersonId,investPersonName,payintentPeriod         // 收入金额
	    				);*/
		    			list.add(sf1);
		    			/*if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
		    			if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
    			 }
    		}else{
    			for (int i = 1; i < payintentPeriod+1; i++) {
				 
    				sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
							DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*12), -1),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(accrualpre),investPersonId,investPersonName,i 	);        // 收入金额
	
							
				   /* sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
				    		DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*12), -1),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(managementpre),investPersonId,investPersonName,i          // 收入金额
							);
					sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
							DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*12), -1),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(financepre),investPersonId,investPersonName,i          // 收入金额
							);*/
					list.add(sf1);
					/*if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
					if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
				
				 
    			}
    		}
		    sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
		    		DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), payintentPeriod*12), -1),  //计划日期
					BigDecimal.valueOf(0),                                                // 支出金额
					projectMoney,investPersonId,investPersonName,payintentPeriod  	
			);      // 收入金额
			list.add(sf);
		}
        if (accrualType.equals("singleInterest") && payaccrualType.equals("ontTimeAccrual") && isPreposePayAccrual == 1) {// 一次性头
			
        	 sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
						DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), 0),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(accrualpre),investPersonId,investPersonName,1 	);        // 收入金额

						
			   /* sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
						DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), 0),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(managementpre),investPersonId,investPersonName,1          // 收入金额
						);
				sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
						DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), 0),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(financepre),investPersonId,investPersonName,1          // 收入金额
						);
*/
        	 sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
						DateUtil.addMonthsToDate(DateUtil.parseDate(date2, "yyyy-MM-dd"),0),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						projectMoney,investPersonId,investPersonName,1  	);      // 收入金额
        	 list.add(sf1);	
        	/* if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
				if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
        	 list.add(sf);
		}
        if (accrualType.equals("singleInterest") && payaccrualType.equals("ontTimeAccrual") && isPreposePayAccrual == 0) {// 一次性尾
			
        	    sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
						DateUtil.addMonthsToDate(DateUtil.parseDate(date2, "yyyy-MM-dd"), 0),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(accrualpre),investPersonId,investPersonName,1 	);        // 收入金额

						
			   /* sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
						DateUtil.addMonthsToDate(DateUtil.parseDate(date2, "yyyy-MM-dd"), 0),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(managementpre),investPersonId,investPersonName,1          // 收入金额
						);
				sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
						DateUtil.addMonthsToDate(DateUtil.parseDate(date2, "yyyy-MM-dd"), 0),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(financepre),investPersonId,investPersonName,1          // 收入金额
						);*/

				sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
						DateUtil.addMonthsToDate(DateUtil.parseDate(date2, "yyyy-MM-dd"),0),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						projectMoney ,investPersonId,investPersonName,1 	);      // 收入金额
     	 	  list.add(sf1);	
	     	 /* if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
			  if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
	     	  list.add(sf);
		}
		
		if (accrualType.equals("singleInterest") && payaccrualType.equals("owerPay") && isPreposePayAccrual == 1) {// 日头
			 for (int i = 0; i < payintentPeriod; i++) {
				 if(i==0){
					 sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
								DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*dayOfEveryPeriod),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(accrualpre),investPersonId,investPersonName,1 	);        // 收入金额
		
								
					   /* sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
								DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"),  i*dayOfEveryPeriod),//计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(managementpre),investPersonId,investPersonName,1          // 收入金额
								);
						sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
								DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"),  i*dayOfEveryPeriod), //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(financepre),investPersonId,investPersonName,1          // 收入金额
								);*/
						list.add(sf1);
						/*if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
						if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
					 
				 }else{
					 sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
								DateUtil.addDaysToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), i*dayOfEveryPeriod),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(accrualpre),investPersonId,investPersonName,i+1 	);        // 收入金额
		
								
					   /* sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
								DateUtil.addDaysToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"),  i*dayOfEveryPeriod),//计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(managementpre),investPersonId,investPersonName,i+1          // 收入金额
								);
						sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
								DateUtil.addDaysToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"),  i*dayOfEveryPeriod), //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(financepre),investPersonId,investPersonName,i+1          // 收入金额
								);*/
						list.add(sf1);
						/*if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
						if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
					 
				 }
			 }
			    sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
						DateUtil.addDaysToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), payintentPeriod*dayOfEveryPeriod-1),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						projectMoney,investPersonId,investPersonName,payintentPeriod  	
				);      // 收入金额
				list.add(sf);
		}
        if (accrualType.equals("singleInterest") && payaccrualType.equals("owerPay") && isPreposePayAccrual == 0) {// 日尾
           for (int i = 1; i < payintentPeriod+1; i++) {
				 sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
						DateUtil.addDaysToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), i*dayOfEveryPeriod),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(accrualpre),investPersonId,investPersonName,i 	
				);        // 收入金额
			    /*sf2 = calculslfundintent(FundIntentListPro3.ProjectManagementConsulting,            // 资金类型
						DateUtil.addDaysToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), i*dayOfEveryPeriod),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(managementpre),investPersonId,investPersonName ,i         // 收入金额
				);
				sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
						DateUtil.addDaysToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), i*dayOfEveryPeriod),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						FundIntentListPro3.calculAccrualnew(projectMoney,sumaccrual).multiply(financepre),investPersonId,investPersonName,i          // 收入金额
				);*/
				list.add(sf1);
				/*if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
				if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
			}
		     sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
					DateUtil.addDaysToDate(DateUtil.parseDate(startperiodDate, "yyyy-MM-dd"), payintentPeriod*dayOfEveryPeriod-1),  //计划日期
					BigDecimal.valueOf(0),                                                // 支出金额
					projectMoney,investPersonId,investPersonName,payintentPeriod  	
			 );      // 收入金额
			 list.add(sf);
		}
		return list;
	}
}
