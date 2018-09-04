package com.zhiwei.credit.core.project;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;

public class LeaseFinanceFundIntentListPro {
	/**
	 * 租赁成本发放
	 */
	public static final String ProjectRentalCostsPaid = "rentalCostsPaid"; //租赁成本发放

	/**
	 * 保证金收取
	 */
	public static final String ProjectMarginCollection = "marginCollection"; // 保证金收取
	/**
	 * 租赁手续费收取
	 */
	public static final String ProjectRentalFeeCharging = "rentalFeeCharging"; // 租赁手续费收取
	/**
	 * 租赁费收取
	 */
	public static final String ProjectLeaseFeeCharging = "leaseFeeCharging"; // 租赁费收取
	
	/**
	 * 本金偿还
	 */
	public static final String ProjectLeasePrincipalRepayment = "leasePrincipalRepayment"; // 本金偿还
	
	/**
	 * 保证金退回
	 */
	public static final String ProjectMarginRefund = "marginRefund"; // 保证金退回
	/**
	 * 租赁物件留购费收取
	 * */
	public static final String ProjectLeaseObjectRetentionFee = "leaseObjectRetentionFee";//租赁物件留购费收取
	
	public static List<SlFundIntent> getFundIntentDefaultList(String calcutype,
			 String accrualType, Integer isPreposePayAccrual,
			String payaccrualType, BigDecimal accrual, BigDecimal projectMoney,
			String date1,String date2, BigDecimal managementConsultingOfRate,BigDecimal leaseDepositMoney,Integer payintentPeriod,String isStartDatePay,Integer payintentPerioDate,Integer dayOfEveryPeriod,String isHaveLending,Boolean isPreposePayConsultingCheck,Integer isInterestByOneTime,String dateModel,BigDecimal leaseRetentionFeeMoney,String feePayaccrualType,BigDecimal eachRentalReceivable) {
		List<SlFundIntent> list = new ArrayList<SlFundIntent>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String startDate=date1;
		String[] arr=date1.split("-");
		if(null!=isStartDatePay && isStartDatePay.equals("1")){
			startDate=arr[0]+"-"+arr[1]+"-"+payintentPerioDate;
		}
		
		accrual=accrual.divide(new BigDecimal(365),20,BigDecimal.ROUND_HALF_UP);
		if(feePayaccrualType.equals("withRentalFee")){
			managementConsultingOfRate=managementConsultingOfRate.divide(new BigDecimal(365),10,BigDecimal.ROUND_HALF_UP);
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
			sf.setFundType(LeaseFinanceFundIntentListPro.ProjectRentalCostsPaid);// 资金类型
			sf.setIntentDate(DateUtil.parseDate(date1, "yyyy-MM-dd"));
			sf.setPayMoney(projectMoney); // 支出金额
			sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入金额
			sf.setAfterMoney(new BigDecimal("0"));
			sf.setFlatMoney(new BigDecimal("0"));
			sf.setAccrualMoney(new BigDecimal("0"));
			sf.setRemark("");
			sf.setPayintentPeriod(0);
			list.add(sf);
		} 
		SlFundIntent sf4=calculslfundintent(LeaseFinanceFundIntentListPro.ProjectMarginCollection,            // 资金类型
			    DateUtil.parseDate(date1,"yyyy-MM-dd"),  //计划日期
				BigDecimal.valueOf(0),                                                // 支出金额                                              // 支出金额
				leaseDepositMoney,0,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,isStartDatePay.equals("1")?String.valueOf(payintentPerioDate):null,accrualType          // 收入金额
				);
		if(sf4.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
			list.add(sf4);
		}
		if(feePayaccrualType.equals("onetime")){
			sf2=calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
				    DateUtil.parseDate(date1,"yyyy-MM-dd"),  //计划日期
					BigDecimal.valueOf(0),                                                // 支出金额                                              // 支出金额
					projectMoney.multiply(managementConsultingOfRate),0,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,isStartDatePay.equals("1")?String.valueOf(payintentPerioDate):null,accrualType          // 收入金额
					);
			if(sf2.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
				list.add(sf2);
			}
		}else if(feePayaccrualType.equals("yearPay")){
			managementConsultingOfRate=managementConsultingOfRate.divide(new BigDecimal(365),10,BigDecimal.ROUND_HALF_UP);
			int alldays=DateUtil.getDaysBetweenDate(date1, date2);
			int periods=(int) Math.floor(alldays/365);
			Date enddate=DateUtil.addDaysToDate(DateUtil.parseDate(date1,"yyyy-MM-dd"), 365*periods);
			int days=DateUtil.getDaysBetweenDate(sdf.format(enddate), date2);
			for(int i=0;i<periods;i++){
				sf2=calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
					    DateUtil.parseDate(date1,"yyyy-MM-dd"),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额                                              // 支出金额
						projectMoney.multiply(managementConsultingOfRate).multiply(new BigDecimal(365)),i+1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,isStartDatePay.equals("1")?String.valueOf(payintentPerioDate):null,accrualType          // 收入金额
						);
				if(sf2.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
					list.add(sf2);
				}
			}
			sf2=calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
				    DateUtil.parseDate(date2,"yyyy-MM-dd"),  //计划日期
					BigDecimal.valueOf(0),                                                // 支出金额                                              // 支出金额
					projectMoney.multiply(managementConsultingOfRate).multiply(new BigDecimal(days)),periods+1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,isStartDatePay.equals("1")?String.valueOf(payintentPerioDate):null,accrualType          // 收入金额
					);
			if(sf2.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
				list.add(sf2);
			}
		}
		/*int alldays=DateUtil.getDaysBetweenDate(date1, date2);
		sf3 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectFinanceService,            // 资金类型
		    DateUtil.parseDate(date1,"yyyy-MM-dd"),  //计划日期
			BigDecimal.valueOf(0),                                                // 支出金额                                              // 支出金额
			projectMoney.multiply(financeServiceOfRate).multiply(BigDecimal.valueOf(alldays)),0,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,isStartDatePay.equals("1")?String.valueOf(payintentPerioDate):null,accrualType          // 收入金额
			);
		if(sf3.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
			list.add(sf3);
		}*/
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
		    		   sf1 =calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
		    				   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.multiply(accrual) ,i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType );
		    		   sf =calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeasePrincipalRepayment,            // 资金类型
		    				   DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.divide(new BigDecimal(payintentPeriod),2,BigDecimal.ROUND_UP) ,i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType);
		    		   if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		    			   list.add(sf1);
		    		   }
		    		   	if(feePayaccrualType.equals("withRentalFee")){
					   sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
							   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								LeaseFinanceFundIntentListPro.calculAccrualnew(projectMoney,managementConsultingOfRate),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay ,null,accrualType        // 收入金额
								);
					 /*  sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
							   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额                                              // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,financeServiceOfRate),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
								);*/
					   if(sf2.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
					    list.add(sf2);
					   }
		    		   	}
					  /* if(sf3.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
						    list.add(sf3);
						}*/
		    		   list.add(sf);
		    	   }
	    	   }else if(isInterestByOneTime==1){//一次性收取全部利息
	    		   Date intentDate=null;
	    		  
	    		   if(isPreposePayAccrual == 1){//前置付息
	    			 intentDate=DateUtil.parseDate(date1,"yyyy-MM-dd");
	    			 sf1 =calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
		    				   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.multiply(accrual).multiply(new BigDecimal(payintentPeriod)) ,1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType );
	    			 if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
	    			   list.add(sf1);
	    		   }
	    				if(feePayaccrualType.equals("withRentalFee")){
					   sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
							   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								LeaseFinanceFundIntentListPro.calculAccrualnew(projectMoney,managementConsultingOfRate).multiply(new BigDecimal(payintentPeriod)),1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
								);
					  /* sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
							   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额                                              // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,financeServiceOfRate).multiply(new BigDecimal(payintentPeriod)),1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay ,null,accrualType        // 收入金额
								);*/
					   if(sf2.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
					    list.add(sf2);
					   }
					   }
					  /* if(sf3.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
						    list.add(sf3);
						}*/
	    		   }
	    		  
				   for (int i = 1; i <=payintentPeriod; i++) {
		    		   if(isPreposePayAccrual ==0 && i==payintentPeriod){//前置付息
		    			   sf1 =calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
		    					   DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									projectMoney.multiply(accrual).multiply(new BigDecimal(payintentPeriod)) ,payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType );
		    			   if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		    			   list.add(sf1);
		    			   }
		    				if(feePayaccrualType.equals("withRentalFee")){
						   sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
								   DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									LeaseFinanceFundIntentListPro.calculAccrualnew(projectMoney,managementConsultingOfRate).multiply(new BigDecimal(payintentPeriod)),payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
									);
						  /* sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
								   DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额                                              // 支出金额
									FundIntentListPro3.calculAccrualnew(projectMoney,financeServiceOfRate).multiply(new BigDecimal(payintentPeriod)),payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType         // 收入金额
									);*/
						   if(sf2.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
						    list.add(sf2);
						   }
		    				}
						   /*if(sf3.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
							    list.add(sf3);
							}*/
		    		   }
		    		
		    		   sf =calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeasePrincipalRepayment,            // 资金类型
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
		    			   intentDate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i-1);
		    			  /* if(i==1){
		    				   intentDate=DateUtil.parseDate(date1,"yyyy-MM-dd");
		    			   }*/
		    		   }else{//后置付息
		    			   intentDate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i);
		    		   }
		    		   sf1 =calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
		    				   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.multiply(accrual).multiply(new BigDecimal(30)) ,i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType );
		    		   sf =calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeasePrincipalRepayment,            // 资金类型
		    				   DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i), -1),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.divide(new BigDecimal(payintentPeriod),2,BigDecimal.ROUND_UP) ,i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType );
		    		   if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		    		   list.add(sf1);
		    		   }
		    			if(feePayaccrualType.equals("withRentalFee")){
					   sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
							   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								LeaseFinanceFundIntentListPro.calculAccrualnew(projectMoney,managementConsultingOfRate).multiply(new BigDecimal(30)),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
								);
					  /* sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
							   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额                                              // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,financeServiceOfRate).multiply(new BigDecimal(30)),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
								);*/
					   if(sf2.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
					    list.add(sf2);
					   }
		    			}
					   /*if(sf3.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
						    list.add(sf3);
						}*/
		    		   list.add(sf);
		    	   }
	    	   }else if(isInterestByOneTime==1){//一次性收取全部利息
	    		   Date intentDate=null;
	    		  
	    		   if(isPreposePayAccrual == 1){//前置付息
	    			 intentDate=DateUtil.parseDate(date1,"yyyy-MM-dd");
	    			 sf1 =calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
		    				   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.multiply(accrual).multiply(new BigDecimal(payintentPeriod)).multiply(new BigDecimal(30)) ,1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType);
	    			 if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
	    			   list.add(sf1);
	    			 }
	    				if(feePayaccrualType.equals("withRentalFee")){
					   sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
							   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								LeaseFinanceFundIntentListPro.calculAccrualnew(projectMoney,managementConsultingOfRate).multiply(new BigDecimal(30)).multiply(new BigDecimal(payintentPeriod)),1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
								);
					  /* sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
							   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额                                              // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,financeServiceOfRate).multiply(new BigDecimal(30)).multiply(new BigDecimal(payintentPeriod)),1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
								);*/
					   if(sf2.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
					    list.add(sf2);
					   }
	    				}
					  /* if(sf3.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
						    list.add(sf3);
						}*/
	    		   }
	    		  
				   for (int i = 1; i <=payintentPeriod; i++) {
		    		 
		    		   if(isPreposePayAccrual == 0 && i==payintentPeriod){
		    			   sf1 =calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
		    					   DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), payintentPeriod),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									projectMoney.multiply(accrual).multiply(new BigDecimal(payintentPeriod)).multiply(new BigDecimal(30)) ,payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType);
		    			   if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		    			   list.add(sf1);
		    			   }
		    				if(feePayaccrualType.equals("withRentalFee")){
						   sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
								   DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), payintentPeriod),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									LeaseFinanceFundIntentListPro.calculAccrualnew(projectMoney,managementConsultingOfRate).multiply(new BigDecimal(30)).multiply(new BigDecimal(payintentPeriod)),payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
									);
						 /*  sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
								   DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), payintentPeriod),-1),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额                                              // 支出金额
									FundIntentListPro3.calculAccrualnew(projectMoney,financeServiceOfRate).multiply(new BigDecimal(30)).multiply(new BigDecimal(payintentPeriod)),payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
									);*/
						   if(sf2.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
						    list.add(sf2);
						   }
		    				}
						  /* if(sf3.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
							    list.add(sf3);
							}*/
		    		   }
		    		
		    		   sf =calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeasePrincipalRepayment,            // 资金类型
		    				  DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.divide(new BigDecimal(payintentPeriod),2,BigDecimal.ROUND_UP) ,i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType );
		    		   
		    		   list.add(sf);
		    	   }  
	    	   }
	       }
		}
		if (accrualType.equals("sameprincipalsameInterest") && payaccrualType.equals("seasonPay")){//按季
			if(!isStartDatePay.equals("1")){//按实际放款日对日还款
				if(isInterestByOneTime==0){//不一次性首付利息
		    	   for (int i = 1; i <=payintentPeriod; i++) {
		    		   Date intentDate=null;
		    		   if(isPreposePayAccrual == 1){//前置付息
		    			   intentDate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i-1)*3);
		    			  /* if(i==1){
		    				   intentDate=DateUtil.parseDate(date1,"yyyy-MM-dd");
		    			   }*/
		    		   }else{//后置付息
		    			   intentDate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*3);
		    		   }
		    		   sf1 =calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
		    				   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.multiply(accrual).multiply(new BigDecimal(30)).multiply(new BigDecimal(3)) ,i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType );
		    		   sf =calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeasePrincipalRepayment,            // 资金类型
		    				   DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*3),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.divide(new BigDecimal(payintentPeriod),2,BigDecimal.ROUND_UP) ,i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType );
		    		   if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		    		   list.add(sf1);
		    		   }
		    			if(feePayaccrualType.equals("withRentalFee")){
					   sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
							   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								LeaseFinanceFundIntentListPro.calculAccrualnew(projectMoney,managementConsultingOfRate).multiply(new BigDecimal(30)).multiply(new BigDecimal(3)),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
								);
					 /*  sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
							   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额                                              // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,financeServiceOfRate).multiply(new BigDecimal(30)).multiply(new BigDecimal(3)),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
								);*/
					   if(sf2.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
					    list.add(sf2);
					   }
		    			}
					  /* if(sf3.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
						    list.add(sf3);
						}*/
		    		   list.add(sf);
		    	   }
	    	   }else if(isInterestByOneTime==1){//一次性收取全部利息
	    		   Date intentDate=null;
	    		   
	    		   if(isPreposePayAccrual == 1){//前置付息
	    			 intentDate=DateUtil.parseDate(date1,"yyyy-MM-dd");
	    			 sf1 =calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
		    				   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.multiply(accrual).multiply(new BigDecimal(payintentPeriod)).multiply(new BigDecimal(30)).multiply(new BigDecimal(3)) ,1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType );
	    			 if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){  
	    			 list.add(sf1);
	    			 } 	
	    				if(feePayaccrualType.equals("withRentalFee")){
					   sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
							   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								LeaseFinanceFundIntentListPro.calculAccrualnew(projectMoney,managementConsultingOfRate).multiply(new BigDecimal(30)).multiply(new BigDecimal(payintentPeriod)).multiply(new BigDecimal(3)),1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay ,null,accrualType         // 收入金额
								);
					   /*sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
							   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额                                              // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,financeServiceOfRate).multiply(new BigDecimal(30)).multiply(new BigDecimal(payintentPeriod)).multiply(new BigDecimal(3)),1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay ,null,accrualType         // 收入金额
								);*/
					   if(sf2.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
					    list.add(sf2);
					   }
	    				}
					   /*if(sf3.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
						    list.add(sf3);
						}*/
	    		   }else if(isPreposePayAccrual ==0){//后置付息
	    			   intentDate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), payintentPeriod*3);
	    			  
	    		   }
	    		   
				   for (int i = 1; i <=payintentPeriod; i++) {
		    		  
		    		   if(isPreposePayAccrual == 0 && i==payintentPeriod){
		    			   sf1 =calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
		    					   DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*3),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									projectMoney.multiply(accrual).multiply(new BigDecimal(payintentPeriod)).multiply(new BigDecimal(30)).multiply(new BigDecimal(3)) ,payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType );
		    			   if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		    			   list.add(sf1);
		    			   }
		    				if(feePayaccrualType.equals("withRentalFee")){
						   sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
								   DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*3),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									LeaseFinanceFundIntentListPro.calculAccrualnew(projectMoney,managementConsultingOfRate).multiply(new BigDecimal(30)).multiply(new BigDecimal(payintentPeriod)).multiply(new BigDecimal(3)),payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
									);
						  /* sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
								   DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*3),-1),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额                                              // 支出金额
									FundIntentListPro3.calculAccrualnew(projectMoney,financeServiceOfRate).multiply(new BigDecimal(30)).multiply(new BigDecimal(payintentPeriod)).multiply(new BigDecimal(3)),payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType         // 收入金额
									);*/
						   if(sf2.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
						    list.add(sf2);
						   }
		    				}
						  /* if(sf3.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
							    list.add(sf3);
							}*/
		    		   }
		    		
		    		   sf =calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeasePrincipalRepayment,            // 资金类型
		    				   DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*3),  //计划日期
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
		    			   intentDate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i-1)*12);
		    			  /* if(i==1){
		    				   intentDate=DateUtil.parseDate(date1,"yyyy-MM-dd");
		    			   }*/
		    		   }else{//后置付息
		    			   intentDate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*12);
		    		   }
		    		   
		    		   sf1 =calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
		    				   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.multiply(accrual) ,i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType );
		    		   sf =calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeasePrincipalRepayment,            // 资金类型
		    				   DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*12),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.divide(new BigDecimal(payintentPeriod),2,BigDecimal.ROUND_UP) ,i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType );
		    		   if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		    		   list.add(sf1);
		    		   }
		    			if(feePayaccrualType.equals("withRentalFee")){
					   sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
							   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								LeaseFinanceFundIntentListPro.calculAccrualnew(projectMoney,managementConsultingOfRate),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
								);
					  /* sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
							   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额                                              // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,financeServiceOfRate),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
								);*/
					   if(sf2.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
					    list.add(sf2);
					   }
		    			}
					   /*if(sf3.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
						    list.add(sf3);
						}*/
		    		   list.add(sf);
		    	   }
	    	   }else if(isInterestByOneTime==1){//一次性收取全部利息
	    		   Date intentDate=null;
	    		   
	    		   if(isPreposePayAccrual == 1){//前置付息
	    			 intentDate=DateUtil.parseDate(date1,"yyyy-MM-dd");
	    			 sf1 =calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
		    				   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.multiply(accrual).multiply(new BigDecimal(payintentPeriod)) ,1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType );
	    			 if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
	    			   list.add(sf1);
	    			 }	
	    				if(feePayaccrualType.equals("withRentalFee")){
					   sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
							   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								LeaseFinanceFundIntentListPro.calculAccrualnew(projectMoney,managementConsultingOfRate).multiply(new BigDecimal(payintentPeriod)),1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
								);
					  /* sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
							   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额                                              // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,financeServiceOfRate).multiply(new BigDecimal(payintentPeriod)),1 ,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType         // 收入金额
								);*/
					   if(sf2.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
					    list.add(sf2);
					   }
	    				}
					  /* if(sf3.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
						    list.add(sf3);
						}*/
	    		   }
	    		   
				   for (int i = 1; i <=payintentPeriod; i++) {
		    		  
		    		   if(isPreposePayAccrual == 0 && i==payintentPeriod){
		    			   sf1 =calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
		    					   DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*12),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									projectMoney.multiply(accrual).multiply(new BigDecimal(payintentPeriod)) ,payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType );
		    			   if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		    			   list.add(sf1);
		    			   }
		    				if(feePayaccrualType.equals("withRentalFee")){
						   sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
								   DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*12),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									LeaseFinanceFundIntentListPro.calculAccrualnew(projectMoney,managementConsultingOfRate).multiply(new BigDecimal(payintentPeriod)),payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
									);
						  /* sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
								   DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*12),-1),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额                                              // 支出金额
									FundIntentListPro3.calculAccrualnew(projectMoney,financeServiceOfRate).multiply(new BigDecimal(payintentPeriod)),payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
									);*/
						   if(sf2.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
						    list.add(sf2);
						   }
		    				}
						  /* if(sf3.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
							    list.add(sf3);
							}*/
		    		   }
		    		
		    		   sf =calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeasePrincipalRepayment,            // 资金类型
		    				   DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*12),  //计划日期
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
		    		   sf1 =calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
		    				   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.multiply(accrual).multiply(new BigDecimal(dayOfEveryPeriod)) ,i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType );
		    		   sf =calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeasePrincipalRepayment,            // 资金类型
		    				   DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*dayOfEveryPeriod),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.divide(new BigDecimal(payintentPeriod),2,BigDecimal.ROUND_UP) ,i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType );
		    		   if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		    		   list.add(sf1);
		    		   }
		    			if(feePayaccrualType.equals("withRentalFee")){
					   sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
							   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								LeaseFinanceFundIntentListPro.calculAccrualnew(projectMoney,managementConsultingOfRate).multiply(new BigDecimal(dayOfEveryPeriod)),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
								);
					   /*sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
							   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额                                              // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,financeServiceOfRate).multiply(new BigDecimal(dayOfEveryPeriod)),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
								);*/
					   if(sf2.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
					    list.add(sf2);
					   }
		    			}
					  /* if(sf3.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
						    list.add(sf3);
						}*/
		    		   list.add(sf);
		    	   }
	    	   }else if(isInterestByOneTime==1){//一次性收取全部利息
	    		   Date intentDate=null;
	    		  
	    		   if(isPreposePayAccrual == 1){//前置付息
	    			 intentDate=DateUtil.parseDate(date1,"yyyy-MM-dd");
	    			 sf1 =calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
		    				   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.multiply(accrual).multiply(new BigDecimal(payintentPeriod)).multiply(new BigDecimal(dayOfEveryPeriod)) ,1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType );
	    			 if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){ 
	    			 list.add(sf1);
	    			 }
	    				if(feePayaccrualType.equals("withRentalFee")){
					   sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
							   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								LeaseFinanceFundIntentListPro.calculAccrualnew(projectMoney,managementConsultingOfRate).multiply(new BigDecimal(dayOfEveryPeriod)).multiply(new BigDecimal(payintentPeriod)),1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
								);
					 /*  sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
							   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额                                              // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,financeServiceOfRate).multiply(new BigDecimal(dayOfEveryPeriod)).multiply(new BigDecimal(payintentPeriod)),1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
								);*/
					   if(sf2.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
					    list.add(sf2);
					   }
	    				}
					  /* if(sf3.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
						    list.add(sf3);
						}*/
	    		   }
	    		   
				   for (int i = 1; i <=payintentPeriod; i++) {
		    		  
		    		   if(isPreposePayAccrual ==0 && i==payintentPeriod){//前置付息
		    			   sf1 =calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
		    					   DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*dayOfEveryPeriod),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									projectMoney.multiply(accrual).multiply(new BigDecimal(payintentPeriod)).multiply(new BigDecimal(dayOfEveryPeriod)) ,payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType );
		    			   if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		    			   list.add(sf1);
		    			   }
		    				if(feePayaccrualType.equals("withRentalFee")){
						   sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
								   DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*dayOfEveryPeriod),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									LeaseFinanceFundIntentListPro.calculAccrualnew(projectMoney,managementConsultingOfRate).multiply(new BigDecimal(dayOfEveryPeriod)).multiply(new BigDecimal(payintentPeriod)),payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
									);
						   /*sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
								   DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*dayOfEveryPeriod),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额                                              // 支出金额
									FundIntentListPro3.calculAccrualnew(projectMoney,financeServiceOfRate).multiply(new BigDecimal(dayOfEveryPeriod)).multiply(new BigDecimal(payintentPeriod)),payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
									);*/
						   if(sf2.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
						    list.add(sf2);
						   }
		    				}
						   /*if(sf3.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
							    list.add(sf3);
							}*/
		    		   }
		    		
		    		   sf =calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeasePrincipalRepayment,            // 资金类型
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
					 sf1 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
							 intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								LeaseFinanceFundIntentListPro.calculAccrualnew(projectMoney,accrual),i+1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType 	);        // 收入金额
					 if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
					 list.add(sf1);
					 }
						if(feePayaccrualType.equals("withRentalFee")){
					 sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
					    		intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								LeaseFinanceFundIntentListPro.calculAccrualnew(projectMoney,managementConsultingOfRate),i+1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
								);
					 /*sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
								intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPro3.calculAccrualnew(projectMoney,financeServiceOfRate),i+1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
								);*/
						
						if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
						}
						/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
						
					 
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
				 sf1 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
						 intentDate,  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							LeaseFinanceFundIntentListPro.calculAccrualnew(projectMoney,accrual).multiply(new BigDecimal(payintentPeriod)),qs,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType 	);        // 收入金额
				 if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
				 list.add(sf1);
				 }
					if(feePayaccrualType.equals("withRentalFee")){
				 sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
				    		intentDate,  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							LeaseFinanceFundIntentListPro.calculAccrualnew(projectMoney,managementConsultingOfRate).multiply(new BigDecimal(payintentPeriod)),qs,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
							);
				/* sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
							intentDate,  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							FundIntentListPro3.calculAccrualnew(projectMoney,financeServiceOfRate).multiply(new BigDecimal(payintentPeriod)),qs,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
							);*/
					
					if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
					}
					/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
			}
			sf =calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeasePrincipalRepayment,            // 资金类型
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
		 			sf1 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
		 					isPreposePayAccrual==1?DateUtil.parseDate(date1,"yyyy-MM-dd"):DateUtil.parseDate(startDate,"yyyy-MM-dd"),  //计划日期
		 					BigDecimal.valueOf(0),                                                // 支出金额
		 					projectMoney.multiply(accrual).multiply(firstPerioddays),0,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType 	);        // 收入金额
		 			if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		 			list.add(sf1);
		 			}
		 			if(feePayaccrualType.equals("withRentalFee")){
		  		    sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
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
		 			}
		  			/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
 				}
	 			for (int i = 1; i < payintentPeriod; i++) {
	 				Date startactualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), i-1);
					Date endctualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), i);
					BigDecimal actualdays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(startactualdate), sdf.format(endctualdate))).toString());
	 				 sf1 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
	 						isPreposePayAccrual==1?startactualdate:endctualdate,  //计划日期
	 							BigDecimal.valueOf(0),                                                // 支出金额
	 							projectMoney.multiply(accrual).multiply(actualdays),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType 	);        // 收入金额
	 				if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
	 				 list.add(sf1);
	 				}
	 				if(feePayaccrualType.equals("withRentalFee")){
  				    sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
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
	 				}
  					/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
	 			 };
	 			// if(payintentPeriod>1){
		 			 adate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"),payintentPeriod);
		 			   Date bdate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"),payintentPeriod-1);
		 			  
		 			BigDecimal endPerioddays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(bdate), sdf.format(adate))).toString());
		 			sf1 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
		 					isPreposePayAccrual==1?bdate:adate,  //计划日期
		 					BigDecimal.valueOf(0),                                                // 支出金额
		 					projectMoney.multiply(accrual).multiply(endPerioddays), payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType	);        // 收入金额
		 			if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		 			list.add(sf1);
		 			}
		 			if(feePayaccrualType.equals("withRentalFee")){
		  		    sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
		  		    		isPreposePayAccrual==1?bdate:adate,  //计划日期
		  					BigDecimal.valueOf(0),                                                // 支出金额
		  					projectMoney.multiply(managementConsultingOfRate).multiply(endPerioddays),payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay ,String.valueOf(payintentPerioDate),accrualType         // 收入金额
		  					);
		  			/*sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
		  					isPreposePayAccrual==1?bdate:adate,  //计划日期
		  					BigDecimal.valueOf(0),                                                // 支出金额
		  					projectMoney.multiply(financeServiceOfRate).multiply(endPerioddays),payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType          // 收入金额
		  					);*/
		  			
		  			if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
		 			}
		  			/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
	 			 //}
 			}else if(isInterestByOneTime==1){
 				BigDecimal accrualMoney=new BigDecimal(0);
 				BigDecimal managementConsultingOfRateMoney=new BigDecimal(0);
 				//BigDecimal financeServiceOfRateMoney=new BigDecimal(0);
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
	 				//financeServiceOfRateMoney=financeServiceOfRateMoney.add(projectMoney.multiply(financeServiceOfRate).multiply(firstPerioddays));
 				}
	 			for (int i = 1; i < payintentPeriod; i++) {
	 				Date startactualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), i-1);
					Date endctualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), i);
					BigDecimal actualdays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(startactualdate), sdf.format(endctualdate))).toString());
					accrualMoney=accrualMoney.add(projectMoney.multiply(accrual).multiply(actualdays));
					managementConsultingOfRateMoney=managementConsultingOfRateMoney.add(projectMoney.multiply(managementConsultingOfRate).multiply(actualdays));
					//financeServiceOfRateMoney=financeServiceOfRateMoney.add(projectMoney.multiply(financeServiceOfRate).multiply(actualdays));
					
	 			 };
	 			// if(payintentPeriod>1){
		 			 adate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"),payintentPeriod);
		 			   Date bdate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"),payintentPeriod-1);
		 			  
		 			BigDecimal endPerioddays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(bdate), sdf.format(adate))).toString());
		 			accrualMoney=accrualMoney.add(projectMoney.multiply(accrual).multiply(endPerioddays));
		 			managementConsultingOfRateMoney=managementConsultingOfRateMoney.add(projectMoney.multiply(managementConsultingOfRate).multiply(endPerioddays));
		 			//financeServiceOfRateMoney=financeServiceOfRateMoney.add(projectMoney.multiply(financeServiceOfRate).multiply(endPerioddays));
		 			
	 			// }
	 			 
	 			sf1 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
	 					isPreposePayAccrual==1?DateUtil.parseDate(date1,"yyyy-MM-dd"):DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod),  //计划日期
	 					BigDecimal.valueOf(0),                                                // 支出金额
	 					accrualMoney, isPreposePayAccrual==1?1:payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType	);        // 收入金额
	 			if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
	 			list.add(sf1);
	 			}
	 			if(feePayaccrualType.equals("withRentalFee")){
	  		    sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
	  		    		isPreposePayAccrual==1?DateUtil.parseDate(date1,"yyyy-MM-dd"):DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod),  //计划日期
	  					BigDecimal.valueOf(0),                                                // 支出金额
	  					managementConsultingOfRateMoney,isPreposePayAccrual==1?1:payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType          // 收入金额
	  					);
	  			/*sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
	  					isPreposePayAccrual==1?DateUtil.parseDate(date1,"yyyy-MM-dd"):DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod), -1),  //计划日期
	  					BigDecimal.valueOf(0),                                                // 支出金额
	  					financeServiceOfRateMoney,isPreposePayAccrual==1?1:payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1 ,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType         // 收入金额
	  					);*/
	  			
	  			if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
	 			}
	  			/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
 			}
 		}else{
 			if(isInterestByOneTime==0){
 				for (int i = 1; i < payintentPeriod+1; i++) {
	   		 
	   			  Date startactualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i-1);
	   			  if(i>1){
	   				  startactualdate=DateUtil.addDaysToDate(startactualdate, -1);
	   			  }
	   			  Date endctualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i);
	   			  BigDecimal actualdays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(startactualdate), sdf.format(endctualdate))).toString());
	   			
	   			  Date intentDate=null;
	   			  if(isPreposePayAccrual==1){
	   				  intentDate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1,"yyyy-MM-dd"), i-1);
	   				  if(i==1){
	   					  intentDate=DateUtil.parseDate(date1,"yyyy-MM-dd");
	   				  }
	   			  }else if(isPreposePayAccrual==0){
	   				  intentDate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i);
	   			  }
	   		
	   			  sf1 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
	   					intentDate,  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						projectMoney.multiply(accrual).multiply(actualdays),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType 	);
	   			if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
	   			  list.add(sf1);
	   			}
	   			if(feePayaccrualType.equals("withRentalFee")){
   				  sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
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
	   			
			  			
				 }
	 		}else if(isInterestByOneTime==1){
	 			BigDecimal accrualMoney=new BigDecimal(0);
	 			BigDecimal managementConsultingOfRateMoney=new BigDecimal(0);
	 			//BigDecimal financeServiceOfRateMoney=new BigDecimal(0);
	 			for (int i = 1; i < payintentPeriod+1; i++) {
	 		   		 
		   			  Date startactualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i-1);
		   			  /*if(i>1){
		   				  startactualdate=DateUtil.addDaysToDate(startactualdate, -1);
		   			  }*/
		   			  Date endctualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i);
		   			  BigDecimal actualdays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(startactualdate), sdf.format(endctualdate))).toString());
		   			
		   			accrualMoney=accrualMoney.add(projectMoney.multiply(accrual).multiply(actualdays));
		   			managementConsultingOfRateMoney=managementConsultingOfRateMoney.add(projectMoney.multiply(managementConsultingOfRate).multiply(actualdays));
		   			//financeServiceOfRateMoney=financeServiceOfRateMoney.add(projectMoney.multiply(financeServiceOfRate).multiply(actualdays));
		   			 
				 }
	 			Date intentDate=null;
	 			int qs=0;
	 			if(isPreposePayAccrual==0){
	 				intentDate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1,"yyyy-MM-dd"), payintentPeriod);
	 				qs=payintentPeriod;
	 			}else if(isPreposePayAccrual==1){
	 				intentDate=DateUtil.parseDate(date1,"yyyy-MM-dd");
	 				qs=1;
	 			}
	 			 sf1 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
		   					intentDate,  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							accrualMoney,qs,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType 	);
	 			if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		   			  list.add(sf1);
	 			}
	 			if(feePayaccrualType.equals("withRentalFee")){
	   				  sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
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
	 			}
						/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
	 		}
 		}
   			 sf =calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeasePrincipalRepayment,            // 资金类型
   					 DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"),payintentPeriod),  //计划日期
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
			 			sf1 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
			 					isPreposePayAccrual==1?DateUtil.parseDate(date1,"yyyy-MM-dd"):DateUtil.parseDate(startDate,"yyyy-MM-dd"),  //计划日期
			 					BigDecimal.valueOf(0),                                                // 支出金额
			 					projectMoney.multiply(accrual).multiply(firstPerioddays),0,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType 	);        // 收入金额
			 			if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
			 			list.add(sf1);
			 			}
			 			if(feePayaccrualType.equals("withRentalFee")){
			  		    sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
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
			 			}
			  			/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
	 				}
		 			for (int i = 1; i < payintentPeriod; i++) {
		 				Date startactualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), (i-1)*3);
						Date endctualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), i*3);
						BigDecimal actualdays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(startactualdate), sdf.format(endctualdate))).toString());
		 				 sf1 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
		 						isPreposePayAccrual==1?startactualdate:endctualdate,  //计划日期
		 							BigDecimal.valueOf(0),                                                // 支出金额
		 							projectMoney.multiply(accrual).multiply(actualdays),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType 	);        // 收入金额
		 				if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		 				list.add(sf1);
		 				}
		 				if(feePayaccrualType.equals("withRentalFee")){
	  				    sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
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
		 				}
	  					/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
		 			 };
		 			 //if(payintentPeriod>1){
			 			 adate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"),payintentPeriod*3);
			 			   Date bdate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"),(payintentPeriod-1)*3);
			 			  
			 			BigDecimal endPerioddays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(bdate), sdf.format(adate))).toString());
			 			sf1 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
			 					isPreposePayAccrual==1?bdate:adate,  //计划日期
			 					BigDecimal.valueOf(0),                                                // 支出金额
			 					projectMoney.multiply(accrual).multiply(endPerioddays), payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType	);        // 收入金额
			 			if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
			 			list.add(sf1);
			 		//	}
			 			if(feePayaccrualType.equals("withRentalFee")){
			  		    sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
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
			 			}
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
			 			 adate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"),payintentPeriod*3);
			 			   Date bdate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"),(payintentPeriod-1)*3);
			 			  
			 			BigDecimal endPerioddays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(bdate), sdf.format(adate))).toString());
			 			accrualMoney=accrualMoney.add(projectMoney.multiply(accrual).multiply(endPerioddays));
			 			managementConsultingOfRateMoney=managementConsultingOfRateMoney.add(projectMoney.multiply(managementConsultingOfRate).multiply(endPerioddays));
			 			//financeServiceOfRateMoney=financeServiceOfRateMoney.add(projectMoney.multiply(financeServiceOfRate).multiply(endPerioddays));
			 			
		 			// }
		 			 
		 			sf1 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
		 					isPreposePayAccrual==1?DateUtil.parseDate(date1,"yyyy-MM-dd"):DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod*3),  //计划日期
		 					BigDecimal.valueOf(0),                                                // 支出金额
		 					accrualMoney, isPreposePayAccrual==1?1:payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType	);        // 收入金额
		 			if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		 			list.add(sf1);
		 			}
		 			if(feePayaccrualType.equals("withRentalFee")){
		  		    sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
		  		    		isPreposePayAccrual==1?DateUtil.parseDate(date1,"yyyy-MM-dd"):DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod*3),  //计划日期
		  					BigDecimal.valueOf(0),                                                // 支出金额
		  					managementConsultingOfRateMoney,isPreposePayAccrual==1?1:payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType          // 收入金额
		  					);
		  			/*sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
		  					isPreposePayAccrual==1?DateUtil.parseDate(date1,"yyyy-MM-dd"):DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod*3), -1),  //计划日期
		  					BigDecimal.valueOf(0),                                                // 支出金额
		  					financeServiceOfRateMoney,isPreposePayAccrual==1?1:payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType          // 收入金额
		  					);*/
		  			
		  			if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
		 			}
		  			/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
	 			}
	 		}else{
	 			if(isInterestByOneTime==0){
	 				for (int i = 1; i < payintentPeriod+1; i++) {
		   		 
		   			  Date startactualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i-1)*3);
		   			  /*if(i>1){
		   				  startactualdate=DateUtil.addDaysToDate(startactualdate, -1);
		   			  }*/
		   			  Date endctualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*3);
		   			  BigDecimal actualdays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(startactualdate), sdf.format(endctualdate))).toString());
		   			
		   			  Date intentDate=null;
		   			  if(isPreposePayAccrual==1){
		   				  intentDate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1,"yyyy-MM-dd"), (i-1)*3);
		   				 /* if(i==1){
		   					  intentDate=DateUtil.parseDate(date1,"yyyy-MM-dd");
		   				  }*/
		   			  }else if(isPreposePayAccrual==0){
		   				  intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*3), -1);
		   			  }
		   			
		   			  sf1 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
		   					intentDate,  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							projectMoney.multiply(accrual).multiply(actualdays),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType 	);
		   			if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		   			  list.add(sf1);
		   			}
		   			if(feePayaccrualType.equals("withRentalFee")){
	   				  sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
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
		   			}
						/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
		   			
		   			
				  			
					 }
		 		}else if(isInterestByOneTime==1){
		 			BigDecimal accrualMoney=new BigDecimal(0);
		 			BigDecimal managementConsultingOfRateMoney=new BigDecimal(0);
		 			//BigDecimal financeServiceOfRateMoney=new BigDecimal(0);
		 			for (int i = 1; i < payintentPeriod+1; i++) {
		 		   		 
			   			  Date startactualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i-1)*3);
			   			  /*if(i>1){
			   				  startactualdate=DateUtil.addDaysToDate(startactualdate, -1);
			   			  }*/
			   			  Date endctualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*3);
			   			  BigDecimal actualdays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(startactualdate), sdf.format(endctualdate))).toString());
			   			
			   			accrualMoney=accrualMoney.add(projectMoney.multiply(accrual).multiply(actualdays));
			   			managementConsultingOfRateMoney=managementConsultingOfRateMoney.add(projectMoney.multiply(managementConsultingOfRate).multiply(actualdays));
			   			//financeServiceOfRateMoney=financeServiceOfRateMoney.add(projectMoney.multiply(financeServiceOfRate).multiply(actualdays));
			   			 
					 }
		 			Date intentDate=null;
		 			int qs=0;
		 			if(isPreposePayAccrual==0){
		 				intentDate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1,"yyyy-MM-dd"), payintentPeriod*3);
		 				qs=payintentPeriod;
		 			}else if(isPreposePayAccrual==1){
		 				intentDate=DateUtil.parseDate(date1,"yyyy-MM-dd");
		 				qs=1;
		 			}
		 			 sf1 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
			   					intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								accrualMoney,qs,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType 	);
		 			if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
			   			  list.add(sf1);
		 			}
		 			if(feePayaccrualType.equals("withRentalFee")){
		   				  sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
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
		 			}
							/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
		 		}
	 		}
   			 sf =calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeasePrincipalRepayment,            // 资金类型
   					DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"),payintentPeriod*3),  //计划日期
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
			 			sf1 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
			 					isPreposePayAccrual==1?DateUtil.parseDate(date1,"yyyy-MM-dd"):DateUtil.parseDate(startDate,"yyyy-MM-dd"),  //计划日期
			 					BigDecimal.valueOf(0),                                                // 支出金额
			 					projectMoney.multiply(accrual).multiply(firstPerioddays),0,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType 	);        // 收入金额
			 			if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
			 			list.add(sf1);
			 			}
			 			if(feePayaccrualType.equals("withRentalFee")){
			  		    sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
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
			 			}
			  			/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
	 				}
		 			for (int i = 1; i < payintentPeriod; i++) {
		 				Date startactualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), (i-1)*12);
						Date endctualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), i*12);
						BigDecimal actualdays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(startactualdate), sdf.format(endctualdate))).toString());
		 				 sf1 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
		 						isPreposePayAccrual==1?startactualdate:endctualdate,  //计划日期
		 							BigDecimal.valueOf(0),                                                // 支出金额
		 							projectMoney.multiply(accrual).multiply(actualdays),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType 	);        // 收入金额
		 				if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		 				list.add(sf1);
		 				}	
		 				if(feePayaccrualType.equals("withRentalFee")){
	  				    sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
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
		 				}
	  					/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
		 			 };
		 			// if(payintentPeriod>1){
			 			 adate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"),payintentPeriod*12);
			 			   Date bdate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"),(payintentPeriod-1)*12);
			 			  
			 			BigDecimal endPerioddays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(bdate), sdf.format(adate))).toString());
			 			sf1 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
			 					isPreposePayAccrual==1?bdate:adate,  //计划日期
			 					BigDecimal.valueOf(0),                                                // 支出金额
			 					projectMoney.multiply(accrual).multiply(endPerioddays), payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType	);        // 收入金额
			 			if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
			 			list.add(sf1);
			 			//}
			 			if(feePayaccrualType.equals("withRentalFee")){
			  		    sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
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
			 			}
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
		 			// if(payintentPeriod>1){
			 			 adate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"),payintentPeriod*12);
			 			   Date bdate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"),(payintentPeriod-1)*12);
			 			  
			 			BigDecimal endPerioddays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(bdate), sdf.format(adate))).toString());
			 			accrualMoney=accrualMoney.add(projectMoney.multiply(accrual).multiply(endPerioddays));
			 			managementConsultingOfRateMoney=managementConsultingOfRateMoney.add(projectMoney.multiply(managementConsultingOfRate).multiply(endPerioddays));
			 			//financeServiceOfRateMoney=financeServiceOfRateMoney.add(projectMoney.multiply(financeServiceOfRate).multiply(endPerioddays));
			 			
		 			 //}
		 			 
		 			sf1 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
		 					isPreposePayAccrual==1?DateUtil.parseDate(date1,"yyyy-MM-dd"):DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod*12),  //计划日期
		 					BigDecimal.valueOf(0),                                                // 支出金额
		 					accrualMoney, isPreposePayAccrual==1?1:payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType	);        // 收入金额
		 			if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		 			list.add(sf1);
		 			}
		 			if(feePayaccrualType.equals("withRentalFee")){
		  		    sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
		  		    		isPreposePayAccrual==1?DateUtil.parseDate(date1,"yyyy-MM-dd"):DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod*12),  //计划日期
		  					BigDecimal.valueOf(0),                                                // 支出金额
		  					managementConsultingOfRateMoney,isPreposePayAccrual==1?1:payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType          // 收入金额
		  					);
		  			/*sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
		  					isPreposePayAccrual==1?DateUtil.parseDate(date1,"yyyy-MM-dd"):DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod*12), -1),  //计划日期
		  					BigDecimal.valueOf(0),                                                // 支出金额
		  					financeServiceOfRateMoney,isPreposePayAccrual==1?1:payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType          // 收入金额
		  					);*/
		  			
		  			if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
		 			}
		  			/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
	 			}
	 		}else{
	 			if(isInterestByOneTime==0){
	 				for (int i = 1; i < payintentPeriod+1; i++) {
		   		 
		   			  Date startactualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i-1)*12);
		   			 /* if(i>1){
		   				  startactualdate=DateUtil.addDaysToDate(startactualdate, -1);
		   			  }*/
		   			  Date endctualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*12);
		   			  BigDecimal actualdays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(startactualdate), sdf.format(endctualdate))).toString());
		   			
		   			  Date intentDate=null;
		   			  if(isPreposePayAccrual==1){
		   				  intentDate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1,"yyyy-MM-dd"), (i-1)*12);
		   				 /* if(i==1){
		   					  intentDate=DateUtil.parseDate(date1,"yyyy-MM-dd");
		   				  }*/
		   			  }else if(isPreposePayAccrual==0){
		   				  intentDate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*12);
		   			  }
		   			  sf1 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
		   					intentDate,  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							projectMoney.multiply(accrual).multiply(actualdays),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType 	);
		   			if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		   			  list.add(sf1);
		   			}
		   			if(feePayaccrualType.equals("withRentalFee")){
	   				  sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
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
		   			
				  			
					 }
		 		}else if(isInterestByOneTime==1){
		 			BigDecimal accrualMoney=new BigDecimal(0);
		 			BigDecimal managementConsultingOfRateMoney=new BigDecimal(0);
		 			//BigDecimal financeServiceOfRateMoney=new BigDecimal(0);
		 			for (int i = 1; i < payintentPeriod+1; i++) {
		 		   		 
			   			  Date startactualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i-1)*12);
			   			  /*if(i>1){
			   				  startactualdate=DateUtil.addDaysToDate(startactualdate, -1);
			   			  }*/
			   			  Date endctualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*12);
			   			  BigDecimal actualdays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(startactualdate), sdf.format(endctualdate))).toString());
			   			
			   			accrualMoney=accrualMoney.add(projectMoney.multiply(accrual).multiply(actualdays));
			   			managementConsultingOfRateMoney=managementConsultingOfRateMoney.add(projectMoney.multiply(managementConsultingOfRate).multiply(actualdays));
			   			//financeServiceOfRateMoney=financeServiceOfRateMoney.add(projectMoney.multiply(financeServiceOfRate).multiply(actualdays));
			   			 
					 }
		 			Date intentDate=null;
		 			int qs=0;
		 			if(isPreposePayAccrual==0){
		 				intentDate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1,"yyyy-MM-dd"), payintentPeriod*12);
		 				qs=payintentPeriod;
		 			}else if(isPreposePayAccrual==1){
		 				intentDate=DateUtil.parseDate(date1,"yyyy-MM-dd");
		 				qs=1;
		 			}
		 			 sf1 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
			   					intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								accrualMoney,qs,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType 	);
		 			if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
			   			  list.add(sf1);
		 			}
		 			if(feePayaccrualType.equals("withRentalFee")){
		   				  sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
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
		 			}
							/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
		 		}
	 		}
	   			 sf =calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeasePrincipalRepayment,            // 资金类型
	   					 DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"),payintentPeriod*12),  //计划日期
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
					 sf1 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
							intentDate,  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							projectMoney.multiply(accrual).multiply(new BigDecimal(days)),i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType 	);        // 收入金额
					 if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
					 list.add(sf1);
					 }
						if(feePayaccrualType.equals("withRentalFee")){
				    sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
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
						}
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
				 sf1 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
							intentDate,  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							accrualMoney,qs,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType	);        // 收入金额
				 if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
					 list.add(sf1);
				 }
					if(feePayaccrualType.equals("withRentalFee")){
				    sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
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
					}
					/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
						
			}
			sf =calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeasePrincipalRepayment,            // 资金类型
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
				// if(payintentPeriod>1){
				 String firstdays=Integer.valueOf(DateUtil.getDaysBetweenDate( date1,sdf.format(DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), 1)))).toString();
				 	sf1 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
				 			isPreposePayAccrual==0?DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), 1):DateUtil.parseDate(date1,"yyyy-MM-dd"),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							LeaseFinanceFundIntentListPro.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(firstdays)),      // 收入金额
							1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType
					);
		    	  sf =calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeasePrincipalRepayment,            // 资金类型
		    			  DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), 1),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							sameprojectMoney,
							1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType);      // 收入金额
		    	  if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		    	  list.add(sf1);
		    	 // }
		    		if(feePayaccrualType.equals("withRentalFee")){
		    		BigDecimal  managementConsultingOfRate1=managementConsultingOfRate.multiply(new BigDecimal(firstdays));
		    	  sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
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
		    		}
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
				 sf1 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
						 intentDate,  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							LeaseFinanceFundIntentListPro.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(30)),
							i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType);        // 收入金额
				    sf =calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeasePrincipalRepayment,            // 资金类型
				    		intentDate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), i),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								sameprojectMoney,
								i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType);      // 收入金额
				    if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
				    list.add(sf1);
				    }
					if(feePayaccrualType.equals("withRentalFee")){
				    sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
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
					}
					/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
					
					list.add(sf);
					
					preperiodsurplus=preperiodsurplus.subtract(sf.getIncomeMoney());
				 }
				 }
				
				 String enddays=Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), payintentPeriod-1)), sdf.format(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod)))).toString();
				 Date intDate=null;
				 if(isPreposePayAccrual==0){
					 intDate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod);
				 }else if(isPreposePayAccrual==1){
					 intDate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), payintentPeriod-1);
				 }
				 sf1 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
						 intDate,  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							LeaseFinanceFundIntentListPro.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(enddays)),      // 收入金额
							payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType
				 );
				    sf =calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeasePrincipalRepayment,            // 资金类型
				    		DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							sameprojectMoney,     // 收入金额
							payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType
				    );
				    if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
				    list.add(sf1);
				    }
					if(feePayaccrualType.equals("withRentalFee")){
				    	BigDecimal managementConsultingOfRate2=managementConsultingOfRate.multiply(new BigDecimal(enddays));
				    sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
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
					}
					/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
				   
					list.add(sf);
				
					preperiodsurplus=preperiodsurplus.subtract(sf.getIncomeMoney());
				 }else if(isInterestByOneTime==1){
					 BigDecimal accrualMoney=new BigDecimal(0);
					 BigDecimal managementConsultingOfRateMoney=new BigDecimal(0);
					// BigDecimal financeServiceOfRateMoney=new BigDecimal(0);
					 
						 String firstdays=Integer.valueOf(DateUtil.getDaysBetweenDate( date1,sdf.format(DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), 1)))).toString();
						 accrualMoney=accrualMoney.add(LeaseFinanceFundIntentListPro.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(firstdays)));
						 BigDecimal  managementConsultingOfRate1=managementConsultingOfRate.multiply(new BigDecimal(firstdays));
						 managementConsultingOfRateMoney=managementConsultingOfRateMoney.add(projectMoney.multiply(managementConsultingOfRate1));
						// BigDecimal financeServiceOfRate1=financeServiceOfRate.multiply(new BigDecimal(firstdays));
						 //financeServiceOfRateMoney=financeServiceOfRateMoney.add(projectMoney.multiply(financeServiceOfRate1));
					
							preperiodsurplus=preperiodsurplus.subtract(sameprojectMoney);
						 
						 for (int i = 2; i <payintentPeriod; i++) {
							 accrualMoney=accrualMoney.add(LeaseFinanceFundIntentListPro.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(30)));
							 managementConsultingOfRateMoney=managementConsultingOfRateMoney.add(projectMoney.multiply(managementConsultingOfRate).multiply(new BigDecimal(30)));
							// financeServiceOfRateMoney=financeServiceOfRateMoney.add(projectMoney.multiply(financeServiceOfRate).multiply(new BigDecimal(30)));
							preperiodsurplus=preperiodsurplus.subtract(sameprojectMoney);
						 }
						
						
						 String enddays=Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), payintentPeriod-1)), sdf.format(DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod), -1)))).toString();
						 BigDecimal managementConsultingOfRate2=managementConsultingOfRate.multiply(new BigDecimal(enddays));
						// BigDecimal financeServiceOfRate2=financeServiceOfRate.multiply(new BigDecimal(enddays));
						 accrualMoney=accrualMoney.add(LeaseFinanceFundIntentListPro.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(enddays)));
						 managementConsultingOfRateMoney=managementConsultingOfRateMoney.add(projectMoney.multiply(managementConsultingOfRate2));
						// financeServiceOfRateMoney=financeServiceOfRateMoney.add(projectMoney.multiply(financeServiceOfRate2));
						preperiodsurplus=preperiodsurplus.subtract(sameprojectMoney);
						
						if(isPreposePayAccrual==1){
							 sf1 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
									 DateUtil.parseDate(date1,"yyyy-MM-dd"),  //计划日期
										BigDecimal.valueOf(0),                                                // 支出金额
										accrualMoney,
										1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType);        // 收入金额
							 if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
							    list.add(sf1);
							 }
								if(feePayaccrualType.equals("withRentalFee")){
							    sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
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
								}
								/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
								
						}
						 sf =calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeasePrincipalRepayment,            // 资金类型
		    			  DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), 1),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							sameprojectMoney,
							1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType);      // 收入金额
						 list.add(sf);
						 for (int i = 2; i <payintentPeriod; i++) {
							
						    sf =calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeasePrincipalRepayment,            // 资金类型
						    		DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), i),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									sameprojectMoney,
									i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType);      // 收入金额
						   
							list.add(sf);
						   
							preperiodsurplus=preperiodsurplus.subtract(sf.getIncomeMoney());
						 }
					
						 if(isPreposePayAccrual==0){
							 sf1 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
									 DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod),  //计划日期
										BigDecimal.valueOf(0),                                                // 支出金额
										accrualMoney,
										payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType);        // 收入金额
							 if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
							    list.add(sf1);
							 }
								if(feePayaccrualType.equals("withRentalFee")){
							    sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
							    		DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod),  //计划日期
										BigDecimal.valueOf(0),                                                // 支出金额
										managementConsultingOfRateMoney,payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType          // 收入金额
										);
								/*sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
										DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod), -1),  //计划日期
										BigDecimal.valueOf(0),                                                // 支出金额
										financeServiceOfRateMoney,payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType        // 收入金额
										);*/
								
								if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
								}
								/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
								
						}
						 sf =calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeasePrincipalRepayment,            // 资金类型
						    		DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod),  //计划日期
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
							 intentDate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i);
						 }else if(isPreposePayAccrual==1){
							 intentDate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i-1);
							/* if(i==1){
								 intentDate=DateUtil.parseDate(date1,"yyyy-MM-dd");
							 }*/
						 }
						 sf1 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
								intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								LeaseFinanceFundIntentListPro.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(30)),
								i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType);        // 收入金额
					    sf =calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeasePrincipalRepayment,            // 资金类型
					    		DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									sameprojectMoney ,
									i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType);   
					    if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
						list.add(sf1);// 收入金额
					    }	
						if(feePayaccrualType.equals("withRentalFee")){
					    sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
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
						}
						/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
						
						list.add(sf);
						
						preperiodsurplus=preperiodsurplus.subtract(sf.getIncomeMoney());
					 }
				 }else if(isInterestByOneTime==1){
					 BigDecimal accrualMoney=new BigDecimal(0);
					 BigDecimal managementConsultingOfRateMoney=new BigDecimal(0);
					 //BigDecimal financeServiceOfRateMoney=new BigDecimal(0);
					 for (int i = 1; i < payintentPeriod+1; i++) {
						 accrualMoney=accrualMoney.add(LeaseFinanceFundIntentListPro.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(30)));
						 managementConsultingOfRateMoney=managementConsultingOfRateMoney.add(projectMoney.multiply(managementConsultingOfRate).multiply(new BigDecimal(30)));
						// financeServiceOfRateMoney=financeServiceOfRateMoney.add(projectMoney.multiply(financeServiceOfRate).multiply(new BigDecimal(30)));
						 preperiodsurplus=preperiodsurplus.subtract(sameprojectMoney);
					 }
					 if(isPreposePayAccrual==1){
						 sf1 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
									DateUtil.parseDate(date1,"yyyy-MM-dd"),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									accrualMoney,
									1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType);
						 if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
						 list.add(sf1);// 收入金额
						 }
							if(feePayaccrualType.equals("withRentalFee")){
						 sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
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
							}
							/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
					 }
					 for (int i = 1; i < payintentPeriod+1; i++) {
						 if(isPreposePayAccrual==0 && i==payintentPeriod){
							 sf1 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
										DateUtil.addMonthsToDate(DateUtil.parseDate(date1,"yyyy-MM-dd"), payintentPeriod),  //计划日期
										BigDecimal.valueOf(0),                                                // 支出金额
										accrualMoney,
										payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType); 
							 if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
							 list.add(sf1);// 收入金额
							 }
								if(feePayaccrualType.equals("withRentalFee")){
							 sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
									DateUtil.addMonthsToDate(DateUtil.parseDate(date1,"yyyy-MM-dd"), payintentPeriod),  //计划日期
										BigDecimal.valueOf(0),                                                // 支出金额
										managementConsultingOfRateMoney,payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
										);
								/*sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
										DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1,"yyyy-MM-dd"), payintentPeriod), -1),  //计划日期
										BigDecimal.valueOf(0),                                                // 支出金额
										financeServiceOfRateMoney,payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType          // 收入金额
										);*/
							
								if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
								}
								/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
						 }
						
					    sf =calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeasePrincipalRepayment,            // 资金类型
					    		DateUtil.addMonthsToDate(DateUtil.parseDate(date1,"yyyy-MM-dd"),i),  //计划日期
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
		     BigDecimal periodtimemoney=LeaseFinanceFundIntentListPro.periodtimemoney(accrual.multiply(new BigDecimal(30)),projectMoney,payintentPeriod).divide(new BigDecimal(1),2,BigDecimal.ROUND_HALF_UP);
		     BigDecimal preperiodsurplus=projectMoney;
		     if(isStartDatePay.equals("1")){ //固定在某日
		    	 if(isInterestByOneTime==0){
			    	 String firstdays=Integer.valueOf(DateUtil.getDaysBetweenDate( date1,sdf.format(DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), 1)))).toString();
			    	
			    	 sf1 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
			    			 isPreposePayAccrual==0?DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), 1):DateUtil.parseDate(date1,"yyyy-MM-dd"),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								LeaseFinanceFundIntentListPro.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(firstdays)),      // 收入金额
								1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType
			    	 );
			    	  BigDecimal firstperiodtimemoney=periodtimemoney.add(sf1.getIncomeMoney().subtract(FundIntentListPro3.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(30))));
					    sf =calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeasePrincipalRepayment,            // 资金类型
								DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), 1),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								firstperiodtimemoney.subtract(sf1.getIncomeMoney()),        // 收入金额
								1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType
					    );
					    if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
						list.add(sf1);
					    }
						if(feePayaccrualType.equals("withRentalFee")){
				    	BigDecimal managementConsultingOfRate1=managementConsultingOfRate.multiply(new BigDecimal(firstdays));
					    sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
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
						}
						/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
					   
						list.add(sf);
						preperiodsurplus=preperiodsurplus.subtract(sf.getIncomeMoney());
						
						
					for (int i = 2; i < payintentPeriod; i++) {
					sf1 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
							isPreposePayAccrual==0?DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), i):DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), i-1),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							LeaseFinanceFundIntentListPro.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(30)),      // 收入金额
							i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType
							);
				    sf =calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeasePrincipalRepayment,            // 资金类型
							DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), i),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							periodtimemoney.subtract(LeaseFinanceFundIntentListPro.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(30))),        // 收入金额
							i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType
				    );
				    if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
						list.add(sf1);
					    }
					if(feePayaccrualType.equals("withRentalFee")){
				    sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
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
					}
					/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
				    
					list.add(sf);
					preperiodsurplus=preperiodsurplus.subtract(sf.getIncomeMoney());
					}
					
					
					// String enddays=Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDatejianyi, "yyyy-MM-dd"), payintentPeriod-1)),sdf.format(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), payintentPeriod)))).toString();
					//if(payintentPeriod>1){
					String enddays=Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), payintentPeriod-1)),sdf.format(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod)))).toString();
					sf1 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
							isPreposePayAccrual==0?DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod):DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), payintentPeriod-1),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								LeaseFinanceFundIntentListPro.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(enddays)),      // 收入金额
								payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType
								);
			    	  BigDecimal endperiodtimemoney=periodtimemoney.subtract((LeaseFinanceFundIntentListPro.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(30))).subtract(sf1.getIncomeMoney()));
					    sf =calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeasePrincipalRepayment,            // 资金类型
					    		DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								endperiodtimemoney.subtract(sf1.getIncomeMoney()),        // 收入金额
								payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType
					    );
					    if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
							list.add(sf1);
						    }
						if(feePayaccrualType.equals("withRentalFee")){
					    	BigDecimal managementConsultingOfRate2=managementConsultingOfRate.multiply(new BigDecimal(enddays));
					    sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
					    		isPreposePayAccrual==0?DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod):DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), payintentPeriod-1),  //计划日期
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
						}
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
			    	 accrualMoney=accrualMoney.add(LeaseFinanceFundIntentListPro.calculAccrualnew(preperiodsurplus1,accrual).multiply(new BigDecimal(firstdays)));
			    	 BigDecimal managementConsultingOfRate1=managementConsultingOfRate.multiply(new BigDecimal(firstdays));
			    	 managementConsultingOfRateMoney=managementConsultingOfRateMoney.add(projectMoney.multiply(managementConsultingOfRate1));
			    	/* BigDecimal financeServiceOfRate1=financeServiceOfRate.multiply(new BigDecimal(firstdays));
			    	 financeServiceOfRateMoney=financeServiceOfRateMoney.add(projectMoney.multiply(financeServiceOfRate1));*/
			    	
			    	 
						
						preperiodsurplus1=preperiodsurplus1.subtract(periodtimemoney.subtract(LeaseFinanceFundIntentListPro.calculAccrualnew(preperiodsurplus1,accrual).multiply(new BigDecimal(30))));
						
						
						
					for (int i = 2; i < payintentPeriod; i++) {
						accrualMoney=accrualMoney.add(LeaseFinanceFundIntentListPro.calculAccrualnew(preperiodsurplus1,accrual).multiply(new BigDecimal(30)));
						managementConsultingOfRateMoney=managementConsultingOfRateMoney.add(projectMoney.multiply(managementConsultingOfRate).multiply(new BigDecimal(30)));
						//financeServiceOfRateMoney=financeServiceOfRateMoney.add(projectMoney.multiply(financeServiceOfRate).multiply(new BigDecimal(30)));
					
					preperiodsurplus1=preperiodsurplus1.subtract(periodtimemoney.subtract(LeaseFinanceFundIntentListPro.calculAccrualnew(preperiodsurplus1,accrual).multiply(new BigDecimal(30))));
					}
					
					
					
				
					String enddays=Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), payintentPeriod-1)),sdf.format(DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod), -1)))).toString();
					accrualMoney=accrualMoney.add(LeaseFinanceFundIntentListPro.calculAccrualnew(preperiodsurplus1,accrual).multiply(new BigDecimal(enddays)));
					BigDecimal managementConsultingOfRate2=managementConsultingOfRate.multiply(new BigDecimal(enddays));
					managementConsultingOfRateMoney=managementConsultingOfRateMoney.add(projectMoney.multiply(managementConsultingOfRate2));
					//BigDecimal financeServiceOfRate2=financeServiceOfRate.multiply(new BigDecimal(enddays));
					//financeServiceOfRateMoney=financeServiceOfRateMoney.add(projectMoney.multiply(financeServiceOfRate2));
					
					if(isPreposePayAccrual==1){
						sf1 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
								DateUtil.parseDate(date1,"yyyy-MM-dd"),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								accrualMoney,      // 收入金额
								1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType
								);
						if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
							list.add(sf1);
						    }
						if(feePayaccrualType.equals("withRentalFee")){	
					    sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
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
						}
						/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
					}
					  sf =calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeasePrincipalRepayment,            // 资金类型
								DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), 1),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								periodtimemoney.subtract(LeaseFinanceFundIntentListPro.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(30))),        // 收入金额
								1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType
					    );
					  	list.add(sf);
						
						preperiodsurplus=preperiodsurplus.subtract(periodtimemoney.subtract(LeaseFinanceFundIntentListPro.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(30))));
						for (int i = 2; i < payintentPeriod; i++) {
							
						    sf =calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeasePrincipalRepayment,            // 资金类型
									DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), i),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									periodtimemoney.subtract(LeaseFinanceFundIntentListPro.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(30))),        // 收入金额
									i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType
						    );
						    
							list.add(sf);
							preperiodsurplus=preperiodsurplus.subtract(periodtimemoney.subtract(LeaseFinanceFundIntentListPro.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(30))));
						}
						if(isPreposePayAccrual==0){
							sf1 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
									DateUtil.parseDate(date1,"yyyy-MM-dd"),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									accrualMoney,      // 收入金额
									payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType
									);
							if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
								list.add(sf1);
							    }
							if(feePayaccrualType.equals("withRentalFee")){
						    sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
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
							}
							/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
						}
					    sf =calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeasePrincipalRepayment,            // 资金类型
					    		DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								periodtimemoney.subtract(LeaseFinanceFundIntentListPro.calculAccrualnew(preperiodsurplus1,accrual).multiply(new BigDecimal(enddays))),        // 收入金额
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
							intentDate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i);
						}else if(isPreposePayAccrual==1){
							intentDate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i-1);
							/*if(i==1){
								intentDate=DateUtil.parseDate(date1,"yyyy-MM-dd");
							}*/
						}
						sf1 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
								intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								LeaseFinanceFundIntentListPro.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(30)),      // 收入金额
								i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType
								);
					    sf =calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeasePrincipalRepayment,            // 资金类型
					    		DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								periodtimemoney.subtract(LeaseFinanceFundIntentListPro.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(30))) ,       // 收入金额
								i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType
								);
					    if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
							list.add(sf1);
						    }
						if(feePayaccrualType.equals("withRentalFee")){
					    sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
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
						}
						/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
					   
						list.add(sf);
						preperiodsurplus=preperiodsurplus.subtract(sf.getIncomeMoney());
					}
		    	 }else if(isInterestByOneTime==1){
		    		 BigDecimal accrualMoney=new BigDecimal(0);
		    		 BigDecimal managementConsultingOfRateMoney=new BigDecimal(0);
		    		 //BigDecimal financeServiceOfRateMoney=new BigDecimal(0);
		    		 for (int i = 1; i < payintentPeriod+1; i++) {
		    			 accrualMoney=accrualMoney.add(LeaseFinanceFundIntentListPro.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(30)));
		    			 managementConsultingOfRateMoney=managementConsultingOfRateMoney.add(projectMoney.multiply(managementConsultingOfRate).multiply(new BigDecimal(30)));
		    			// financeServiceOfRateMoney=financeServiceOfRateMoney.add(projectMoney.multiply(financeServiceOfRate).multiply(new BigDecimal(30)));
						
						preperiodsurplus=preperiodsurplus.subtract(periodtimemoney.subtract(LeaseFinanceFundIntentListPro.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(30))));
					}
		    		 for (int i = 1; i < payintentPeriod+1; i++) {
		    			if(isPreposePayAccrual==1 && i==1){
		    				sf1 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
									DateUtil.parseDate(date1,"yyyy-MM-dd"),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									accrualMoney,      // 收入金额
									i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType
									);
		    				if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
								list.add(sf1);
							    }
		    				if(feePayaccrualType.equals("withRentalFee")){
						    sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
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
		    				}
							/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
		    			}else if(isPreposePayAccrual==0 && i==payintentPeriod){
		    				sf1 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
									DateUtil.addMonthsToDate(DateUtil.parseDate(date1,"yyyy-MM-dd"), payintentPeriod),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									accrualMoney,      // 收入金额
									i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType
									);
		    				if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
								list.add(sf1);
							    }
		    				if(feePayaccrualType.equals("withRentalFee")){
						    sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
						    		DateUtil.addMonthsToDate(DateUtil.parseDate(date1,"yyyy-MM-dd"), payintentPeriod),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									managementConsultingOfRateMoney,i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType         // 收入金额
									);
							/*sf3 = calculslfundintent(FundIntentListPro3.ProjectFinanceService,            // 资金类型
									DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1,"yyyy-MM-dd"), payintentPeriod), -1),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									financeServiceOfRateMoney ,i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType       // 收入金额
									);*/
							
							if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
		    				}
							/*if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}*/
		    			}
						
					    sf =calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeasePrincipalRepayment,            // 资金类型
						    DateUtil.addMonthsToDate(DateUtil.parseDate(date1,"yyyy-MM-dd"), i),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							periodtimemoney.subtract(LeaseFinanceFundIntentListPro.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(30))) ,       // 收入金额
							i,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType
							);
							list.add(sf);
							preperiodsurplus=preperiodsurplus.subtract(sf.getIncomeMoney());
							
						}
		    	 }
		    	 
		    	 
		     }
			}
		if(accrualType.equals("matchingRepayment") && payaccrualType.equals("dayPay")){
			BigDecimal preperiodsurplus=projectMoney;
			for(int i=0;i<payintentPeriod;i++){
				
					
		   			  BigDecimal accrualMoney=preperiodsurplus.multiply(accrual);
		   			  sf1 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
							DateUtil.addDaysToDate(DateUtil.parseDate(date1,"yyyy-MM-dd"), (i+1)),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							accrualMoney,      // 收入金额
							i+1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType
							);
					if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
						list.add(sf1);
					    }
					if(feePayaccrualType.equals("withRentalFee")){
					    sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
					    		DateUtil.addDaysToDate(DateUtil.parseDate(date1,"yyyy-MM-dd"), (i+1)),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.multiply(managementConsultingOfRate),i+1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType         // 收入金额
								);
						
						
						if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
					}
					sf =calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeasePrincipalRepayment,            // 资金类型
						    DateUtil.addDaysToDate(DateUtil.parseDate(date1,"yyyy-MM-dd"), (i+1)),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							(i==payintentPeriod-1)?preperiodsurplus:eachRentalReceivable.subtract(accrualMoney) ,       // 收入金额
							i+1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType
							);
							list.add(sf);
							preperiodsurplus=preperiodsurplus.subtract(sf.getIncomeMoney());
				}
			
		}
		if(accrualType.equals("matchingRepayment") && payaccrualType.equals("monthPay")){
			BigDecimal preperiodsurplus=projectMoney;
			
			for(int i=0;i<payintentPeriod;i++){
			
					 Date startactualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i);
		   			 /* if(i>1){
		   				  startactualdate=DateUtil.addDaysToDate(startactualdate, -1);
		   			  }*/
		   			  Date endctualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i+1));
		   			  BigDecimal actualdays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(startactualdate), sdf.format(endctualdate))).toString());
		   			  BigDecimal accrualMoney=preperiodsurplus.multiply(accrual).multiply(actualdays);
		   			  sf1 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
		   					(isStartDatePay.equals("1") && (i!=payintentPeriod-1))?DateUtil.addMonthsToDate(DateUtil.parseDate(startDate,"yyyy-MM-dd"), (Integer.valueOf(arr[2])>payintentPerioDate)?i:(i+1)):DateUtil.addMonthsToDate(DateUtil.parseDate(date1,"yyyy-MM-dd"), (i+1)),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							accrualMoney,      // 收入金额
							i+1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,isStartDatePay.equals("1")?String.valueOf(payintentPerioDate):null,accrualType
							);
					if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
						list.add(sf1);
					    }
					if(feePayaccrualType.equals("withRentalFee")){
					    sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
					    		(isStartDatePay.equals("1") && (i!=payintentPeriod-1))?DateUtil.addMonthsToDate(DateUtil.parseDate(startDate,"yyyy-MM-dd"), (Integer.valueOf(arr[2])>payintentPerioDate)?i:(i+1)):DateUtil.addMonthsToDate(DateUtil.parseDate(date1,"yyyy-MM-dd"), (i+1)),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.multiply(managementConsultingOfRate).multiply(actualdays),i+1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,isStartDatePay.equals("1")?String.valueOf(payintentPerioDate):null,accrualType         // 收入金额
								);
						
						
						if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
					}
					sf =calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeasePrincipalRepayment,            // 资金类型
							(isStartDatePay.equals("1") && (i!=payintentPeriod-1))?DateUtil.addMonthsToDate(DateUtil.parseDate(startDate,"yyyy-MM-dd"), (Integer.valueOf(arr[2])>payintentPerioDate)?i:(i+1)):DateUtil.addMonthsToDate(DateUtil.parseDate(date1,"yyyy-MM-dd"), (i+1)),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							(i==payintentPeriod-1)?preperiodsurplus:eachRentalReceivable.subtract(accrualMoney) ,       // 收入金额
							i+1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,isStartDatePay.equals("1")?String.valueOf(payintentPerioDate):null,accrualType
							);
							list.add(sf);
							preperiodsurplus=preperiodsurplus.subtract(sf.getIncomeMoney());
				}
		}
		if(accrualType.equals("matchingRepayment") && payaccrualType.equals("seasonPay")){
			BigDecimal preperiodsurplus=projectMoney;
			for(int i=0;i<payintentPeriod;i++){
				
				 Date startactualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*3);
	   			  /*if(i>1){
	   				  startactualdate=DateUtil.addDaysToDate(startactualdate, -1);
	   			  }*/
	   			  Date endctualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i+1)*3);
	   			  BigDecimal actualdays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(startactualdate), sdf.format(endctualdate))).toString());
	   			  BigDecimal accrualMoney=preperiodsurplus.multiply(accrual).multiply(actualdays);
	   			  sf1 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
	   					(isStartDatePay.equals("1") && (i!=payintentPeriod-1))?DateUtil.addMonthsToDate(DateUtil.parseDate(startDate,"yyyy-MM-dd"), (Integer.valueOf(arr[2])>payintentPerioDate)?i*3:(i+1)*3):DateUtil.addMonthsToDate(DateUtil.parseDate(date1,"yyyy-MM-dd"), (i+1)*3),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						accrualMoney,      // 收入金额
						i+1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,isStartDatePay.equals("1")?String.valueOf(payintentPerioDate):null,accrualType
						);
				if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
					list.add(sf1);
				    }
				if(feePayaccrualType.equals("withRentalFee")){
				    sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
				    		(isStartDatePay.equals("1") && (i!=payintentPeriod-1))?DateUtil.addMonthsToDate(DateUtil.parseDate(startDate,"yyyy-MM-dd"), (Integer.valueOf(arr[2])>payintentPerioDate)?i*3:(i+1)*3):DateUtil.addMonthsToDate(DateUtil.parseDate(date1,"yyyy-MM-dd"), (i+1)*3),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							projectMoney.multiply(managementConsultingOfRate).multiply(actualdays),i+1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,isStartDatePay.equals("1")?String.valueOf(payintentPerioDate):null,accrualType         // 收入金额
							);
					
					
					if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
				}
				sf =calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeasePrincipalRepayment,            // 资金类型
						(isStartDatePay.equals("1") && (i!=payintentPeriod-1))?DateUtil.addMonthsToDate(DateUtil.parseDate(startDate,"yyyy-MM-dd"), (Integer.valueOf(arr[2])>payintentPerioDate)?i*3:(i+1)*3):DateUtil.addMonthsToDate(DateUtil.parseDate(date1,"yyyy-MM-dd"), (i+1)*3),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						(i==payintentPeriod-1)?preperiodsurplus:eachRentalReceivable.subtract(accrualMoney) ,       // 收入金额
						i+1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,isStartDatePay.equals("1")?String.valueOf(payintentPerioDate):null,accrualType
						);
						list.add(sf);
						preperiodsurplus=preperiodsurplus.subtract(sf.getIncomeMoney());
			}
			
		}
		if(accrualType.equals("matchingRepayment") && payaccrualType.equals("yearPay")){
			BigDecimal preperiodsurplus=projectMoney;
			for(int i=0;i<payintentPeriod;i++){
				
				 Date startactualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*12);
	   			 /* if(i>1){
	   				  startactualdate=DateUtil.addDaysToDate(startactualdate, -1);
	   			  }*/
	   			  Date endctualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i+1)*12);
	   			  BigDecimal actualdays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(startactualdate), sdf.format(endctualdate))).toString());
	   			  BigDecimal accrualMoney=preperiodsurplus.multiply(accrual).multiply(actualdays);
	   			  sf1 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
	   					(isStartDatePay.equals("1") && (i!=payintentPeriod-1))?DateUtil.addMonthsToDate(DateUtil.parseDate(startDate,"yyyy-MM-dd"), (Integer.valueOf(arr[2])>payintentPerioDate)?i*12:(i+1)*12):DateUtil.addMonthsToDate(DateUtil.parseDate(date1,"yyyy-MM-dd"), (i+1)*12),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						accrualMoney,      // 收入金额
						i+1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType
						);
				if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
					list.add(sf1);
				    }
				if(feePayaccrualType.equals("withRentalFee")){
				    sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
				    		(isStartDatePay.equals("1") && (i!=payintentPeriod-1))?DateUtil.addMonthsToDate(DateUtil.parseDate(startDate,"yyyy-MM-dd"), (Integer.valueOf(arr[2])>payintentPerioDate)?i*12:(i+1)*12):DateUtil.addMonthsToDate(DateUtil.parseDate(date1,"yyyy-MM-dd"), (i+1)*12),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							projectMoney.multiply(managementConsultingOfRate).multiply(actualdays),i+1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType         // 收入金额
							);
					
					
					if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
				}
				sf =calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeasePrincipalRepayment,            // 资金类型
						(isStartDatePay.equals("1") && (i!=payintentPeriod-1))?DateUtil.addMonthsToDate(DateUtil.parseDate(startDate,"yyyy-MM-dd"), (Integer.valueOf(arr[2])>payintentPerioDate)?i*12:(i+1)*12):DateUtil.addMonthsToDate(DateUtil.parseDate(date1,"yyyy-MM-dd"), (i+1)*12),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						(i==payintentPeriod-1)?preperiodsurplus:eachRentalReceivable.subtract(accrualMoney) ,       // 收入金额
						i+1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType
						);
						list.add(sf);
						preperiodsurplus=preperiodsurplus.subtract(sf.getIncomeMoney());
			}
			
		}
		if(accrualType.equals("matchingRepayment") && payaccrualType.equals("owerPay")){
			BigDecimal preperiodsurplus=projectMoney;
			for(int i=0;i<payintentPeriod;i++){
	        	  
	   			  BigDecimal accrualMoney=preperiodsurplus.multiply(accrual).multiply(new BigDecimal(dayOfEveryPeriod));
	   			  sf1 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseFeeCharging,            // 资金类型
	   					DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i+1)*dayOfEveryPeriod),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						accrualMoney,      // 收入金额
						i+1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType
						);
				if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
					list.add(sf1);
				    }
				if(feePayaccrualType.equals("withRentalFee")){
				    sf2 = calculslfundintent(LeaseFinanceFundIntentListPro.ProjectRentalFeeCharging,            // 资金类型
				    		DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i+1)*dayOfEveryPeriod),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							projectMoney.multiply(managementConsultingOfRate).multiply(new BigDecimal(dayOfEveryPeriod)),i+1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType         // 收入金额
							);
					
					
					if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
				}
				sf =calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeasePrincipalRepayment,            // 资金类型
						DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i+1)*dayOfEveryPeriod),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						(i==payintentPeriod-1)?preperiodsurplus:eachRentalReceivable.subtract(accrualMoney) ,       // 收入金额
						i+1,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,null,accrualType
						);
						list.add(sf);
						preperiodsurplus=preperiodsurplus.subtract(sf.getIncomeMoney());
			}
			
		}
		SlFundIntent sf5=calculslfundintent(LeaseFinanceFundIntentListPro.ProjectMarginRefund,            // 资金类型
			    DateUtil.parseDate(date2,"yyyy-MM-dd"),  //计划日期
			    leaseDepositMoney,                                                // 支出金额                                              // 支出金额
			    BigDecimal.valueOf(0),payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,isStartDatePay.equals("1")?String.valueOf(payintentPerioDate):null,accrualType          // 收入金额
				);
		if(sf5.getPayMoney().compareTo(new BigDecimal(0))!=0){
			list.add(sf5);
		}
		SlFundIntent sf6=calculslfundintent(LeaseFinanceFundIntentListPro.ProjectLeaseObjectRetentionFee,            // 资金类型
			    DateUtil.parseDate(date2,"yyyy-MM-dd"),  //计划日期
			    BigDecimal.valueOf(0),                                                // 支出金额                                              // 支出金额
			    leaseRetentionFeeMoney,payintentPeriod,isPreposePayAccrual,payaccrualType,dayOfEveryPeriod,date1,isInterestByOneTime,date2,isStartDatePay,isStartDatePay.equals("1")?String.valueOf(payintentPerioDate):null,accrualType          // 收入金额
				);
		if(sf6.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
			list.add(sf6);
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
		if(!fundType.equals("rentalCostsPaid")){
			SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
			//计息开始日期
			Date interestStarTime=new Date();
			//计息结束日期
			Date interestEndTime=new Date();
			//计息结束日期为计划还款日
			if(fundType.equals("marginCollection")){
				isPreposePayAccrual=0;
			}
			
			if(isInterestByOneTime==1){
				interestStarTime=DateUtil.parseDate(date1,"yyyy-MM-dd");
				if(AppUtil.getInterest().equals("0")){
					interestEndTime=DateUtil.addDaysToDate(DateUtil.parseDate(date2,"yyyy-MM-dd"), -1);
				}else{
					interestEndTime=DateUtil.parseDate(date2,"yyyy-MM-dd");
				}
			}else{
				if(AppUtil.getInterest().equals("0")){
					if(isPreposePayAccrual==1){
						if(payaccrualType.equals("dayPay")){
							interestEndTime=DateUtil.addDaysToDate(intentdate, 1);
						}else if(payaccrualType.equals("monthPay")){
							interestEndTime=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(intentdate, 1), -1);
						}else if(payaccrualType.equals("seasonPay")){
							interestEndTime=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(intentdate, 3), -1);
						}else if(payaccrualType.equals("yearPay")){
							interestEndTime=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(intentdate, 12), -1);
						}else if(payaccrualType.equals("owerPay")){
							interestEndTime=DateUtil.addDaysToDate(intentdate, dayOfEveryPeriod);
						}
						
						if(isStartDatePay.equals("1")){
							String intentdateStr=sd.format(intentdate);
							String[] intentdateArr=intentdateStr.split("-");
							if((intentdateArr[0]+"-"+intentdateArr[1]).equals(date2.substring(0,date2.lastIndexOf("-")))){
								interestEndTime=DateUtil.addDaysToDate(DateUtil.parseDate(date2,"yyyy-MM-dd"), -1);
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
								interestEndTime=DateUtil.addDaysToDate(DateUtil.parseDate(date2,"yyyy-MM-dd"), -1);
							}
							}
						}else{
							if(payintentPeriod==1 && (payaccrualType.equals("monthPay") || payaccrualType.equals("seasonPay") || payaccrualType.equals("yearPay"))){
								interestEndTime=DateUtil.addDaysToDate(interestEndTime, -1);
							}
							if(payaccrualType.equals("dayPay") || payaccrualType.equals("owerPay")){
								interestEndTime=DateUtil.addDaysToDate(interestEndTime, -1);
							}
						}
					}else{
						interestEndTime=DateUtil.addDaysToDate(intentdate,-1);
					}
					if(fundType.equals("principalRepayment") && accrualtype.equals("singleInterest")){
						interestEndTime=DateUtil.addDaysToDate(DateUtil.parseDate(date2,"yyyy-MM-dd"), -1);
					}
					if(fundType.equals("marginCollection") || fundType.equals("rentalFeeCharging") || fundType.equals("marginRefund") || fundType.equals("leaseObjectRetentionFee")){
						interestEndTime=DateUtil.addDaysToDate(DateUtil.parseDate(date2,"yyyy-MM-dd"), -1);
					}
				}else{
					
				}
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
								interestStarTime=DateUtil.addDaysToDate(intentdate, -1);
							}else if(payaccrualType.equals("monthPay")){
								interestStarTime=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.addDaysToDate(intentdate, 1), -1), -1);
							}else if(payaccrualType.equals("seasonPay")){
								interestStarTime=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.addDaysToDate(intentdate, 1), -3), -1);
							}else if(payaccrualType.equals("yearPay")){
								interestStarTime=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.addDaysToDate(intentdate, 1), -12), -1);
							}else if(payaccrualType.equals("owerPay")){
								interestStarTime=DateUtil.addDaysToDate(intentdate, -dayOfEveryPeriod);
							}
							if(payintentPeriod==1){
								interestStarTime=DateUtil.parseDate(date1,"yyyy-MM-dd");
							}
						}
					}
					
				}else{
					
				}
				if(fundType.equals("principalRepayment") && accrualtype.equals("singleInterest")){
					interestStarTime=DateUtil.parseDate(date1,"yyyy-MM-dd");
				}
				if(fundType.equals("marginCollection") || fundType.equals("rentalFeeCharging") || fundType.equals("marginRefund") || fundType.equals("leaseObjectRetentionFee")){
					interestStarTime=DateUtil.parseDate(date1,"yyyy-MM-dd");
				}
			}
			if(fundType.equals("serviceMoney")){
				interestStarTime=DateUtil.parseDate(date1,"yyyy-MM-dd");
				if(AppUtil.getInterest().equals("0")){
					interestEndTime=DateUtil.addDaysToDate(DateUtil.parseDate(date2,"yyyy-MM-dd"), -1);
				}else{
					interestEndTime=DateUtil.parseDate(date2,"yyyy-MM-dd");
				}
			}
			sf1.setInterestStarTime(interestStarTime);
			sf1.setInterestEndTime(interestEndTime);
		}
		return  sf1;
		
	}
}
