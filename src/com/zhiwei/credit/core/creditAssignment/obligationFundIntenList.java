package com.zhiwei.credit.core.creditAssignment;



import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.zhiwei.core.util.DateUtil;
import com.zhiwei.credit.core.project.FundIntentListPro3;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;

public class obligationFundIntenList {
	/**
	 * 投资人投资额支出
	 */
	public static final String InvestOut = "investPrincipalLending"; // 投资人投资额支出
	/**
	 * 投资人利息收取
	 */
	public static final String InvestAccrual = "investInterest"; // 投资人利息收取
	/**
	 * 投资人本金收回
	 */
	public static final String InvestRoot = "investPrincipalRepayment"; // 投资人本金收回
	

	public static List<SlFundIntent> getFundIntentDefaultList(
			BigDecimal accrual, String payaccrualType, String date1,
			String date2, BigDecimal divide, Integer payintentPerioDate,
			Integer payintentPeriod, String isStartDatePay,Integer isPreposePayAccrual,
			String isHaveLending, BigDecimal projectMoney,String accrualType,Integer dayOfEveryPeriod) {
		// TODO Auto-generated method stub
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
		if (null ==isHaveLending ||isHaveLending.equals("yes")){//生成投资支出款项
			sf.setFundType(obligationFundIntenList.InvestOut);// 资金类型
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
		
		/**
		 * 等本等息
		 */
		if (accrualType.equals("sameprincipalsameInterest") && payaccrualType.equals("dayPay")){//按日
			if(!isStartDatePay.equals("1")){//按实际放款日对日还款
				payintentPeriod=DateUtil.getDaysBetweenDate(date1, date2);//期限是按月写的 
				accrual =accrual.divide(BigDecimal.valueOf(365),10, BigDecimal.ROUND_HALF_EVEN);//利率是按年算的
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
		    		   sf1 =calculslfundintent(obligationFundIntenList.InvestAccrual,            // 生成利息方法
		    				   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.multiply(accrual) ,i,isPreposePayAccrual,
								payaccrualType,date1,date2,isStartDatePay,null,accrualType );
		    		   sf2 =calculslfundintent(obligationFundIntenList.InvestRoot,            // 资金类型（生成本金）
		    				   DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.divide(new BigDecimal(payintentPeriod),4,BigDecimal.ROUND_UP) ,i,isPreposePayAccrual,payaccrualType,date1,date2,isStartDatePay,null,accrualType);
		    		   if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		    			   list.add(sf1);
		    		   }
		    		   	
					   if(sf2.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
					    list.add(sf2);
					   }
		    	   }
	    	   
	       }
		}
		if (accrualType.equals("sameprincipalsameInterest") && payaccrualType.equals("monthPay")){//按月
			if(!isStartDatePay.equals("1")){//按实际放款日对日还款
				accrual =accrual.divide(BigDecimal.valueOf(12),10, BigDecimal.ROUND_HALF_EVEN);//利率是按年算的
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
		    		   sf1 =calculslfundintent(obligationFundIntenList.InvestAccrual,            // 生成利息方法
		    				   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.multiply(accrual) ,i,isPreposePayAccrual,
								payaccrualType,date1,date2,isStartDatePay,null,accrualType );
		    		   sf2 =calculslfundintent(obligationFundIntenList.InvestRoot,            // 资金类型（生成本金）
		    				   DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.divide(new BigDecimal(payintentPeriod),4,BigDecimal.ROUND_UP) ,i,isPreposePayAccrual,payaccrualType,date1,date2,isStartDatePay,null,accrualType);
		    		   if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		    			   list.add(sf1);
		    		   }
		    		   	
					   if(sf2.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
					    list.add(sf2);
					   }
		    	   }
	    	   
	       }
			
		}
		if (accrualType.equals("sameprincipalsameInterest") && payaccrualType.equals("seasonPay")){//按季
			if(!isStartDatePay.equals("1")){//按实际放款日对日还款
				payintentPeriod=payintentPeriod/3;//期限是按照月填写的
				accrual =accrual.divide(BigDecimal.valueOf(4),10, BigDecimal.ROUND_HALF_EVEN);//利率是按年算的
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
		    		   sf1 =calculslfundintent(obligationFundIntenList.InvestAccrual,            // 生成利息方法
		    				   intentDate,  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.multiply(accrual) ,i,isPreposePayAccrual,
								payaccrualType,date1,date2,isStartDatePay,null,accrualType );
		    		   sf2 =calculslfundintent(obligationFundIntenList.InvestRoot,            // 资金类型（生成本金）
		    				   DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								projectMoney.divide(new BigDecimal(payintentPeriod),4,BigDecimal.ROUND_UP) ,i,isPreposePayAccrual,payaccrualType,date1,date2,isStartDatePay,null,accrualType);
		    		   if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		    			   list.add(sf1);
		    		   }
		    		   	
					   if(sf2.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
					    list.add(sf2);
					   }
		    	   }
	    	   
	       }
	}
	if (accrualType.equals("sameprincipalsameInterest") && payaccrualType.equals("yearPay")){//按年
		if(!isStartDatePay.equals("1")){//按实际放款日对日还款
			payintentPeriod=payintentPeriod/12;//期限是按照月填写的
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
	    		   sf1 =calculslfundintent(obligationFundIntenList.InvestAccrual,            // 生成利息方法
	    				   intentDate,  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							projectMoney.multiply(accrual) ,i,isPreposePayAccrual,
							payaccrualType,date1,date2,isStartDatePay,null,accrualType );
	    		   sf2 =calculslfundintent(obligationFundIntenList.InvestRoot,            // 资金类型（生成本金）
	    				   DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							projectMoney.divide(new BigDecimal(payintentPeriod),4,BigDecimal.ROUND_UP) ,i,isPreposePayAccrual,payaccrualType,date1,date2,isStartDatePay,null,accrualType);
	    		   if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
	    			   list.add(sf1);
	    		   }
	    		   	
				   if(sf2.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
				    list.add(sf2);
				   }
	    	   }
    	   
       }
	}
	if (accrualType.equals("sameprincipalsameInterest")&& payaccrualType.equals("owerPay")){//自定义周期
		if(!isStartDatePay.equals("1")){//按实际放款日对日还款
			payintentPeriod=DateUtil.getDaysBetweenDate(date1, date2)/dayOfEveryPeriod;//期限是按月写的 
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
	    		   sf1 =calculslfundintent(obligationFundIntenList.InvestAccrual,            // 生成利息方法
	    				   intentDate,  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							projectMoney.multiply(accrual) ,i,isPreposePayAccrual,
							payaccrualType,date1,date2,isStartDatePay,null,accrualType );
	    		   sf2 =calculslfundintent(obligationFundIntenList.InvestRoot,            // 资金类型（生成本金）
	    				   DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							projectMoney.divide(new BigDecimal(payintentPeriod),4,BigDecimal.ROUND_UP) ,
							i,isPreposePayAccrual,payaccrualType,date1,date2,isStartDatePay,null,accrualType);
	    		   if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
	    			   list.add(sf1);
	    		   }
	    		   	
				   if(sf2.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
				    list.add(sf2);
				   }
	    	   }
    	   
       }
	}
	/**
	 * 按期收息，到期还本
	 */
	if (accrualType.equals("singleInterest") && payaccrualType.equals("dayPay")) {// 日尾
           for (int i = 0; i < payintentPeriod; i++) {
        	   payintentPeriod=DateUtil.getDaysBetweenDate(date1, date2);//期限是按月写的 
        	   accrual =accrual.divide(BigDecimal.valueOf(365),10, BigDecimal.ROUND_HALF_EVEN);//利率是按年算的
        	   Date intentDate=null;
				 if(isPreposePayAccrual==1){
					 intentDate=DateUtil.addDaysToDate(DateUtil.parseDate(date1,"yyyy-MM-dd"), i);
				 }else{
					 intentDate=DateUtil.addDaysToDate(DateUtil.parseDate(date1), i+1);
				 }
				 sf1 = calculslfundintent(obligationFundIntenList.InvestAccrual,            // 资金类型(利息)
						 intentDate,  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							projectMoney.multiply(accrual),
							i+1,isPreposePayAccrual,payaccrualType,
							date1,date2,isStartDatePay,null,accrualType 	);        // 收入金额
				 if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
				 list.add(sf1);
				 }
				 
			 }
			    
		
		sf2 =calculslfundintent(obligationFundIntenList.InvestRoot,            // 资金类型
				DateUtil.parseDate(date2, "yyyy-MM-dd"),  //计划日期
				BigDecimal.valueOf(0),                                                // 支出金额
				projectMoney,payintentPeriod,isPreposePayAccrual,payaccrualType,date1,date2,isStartDatePay,null,accrualType);      // 收入金额
		list.add(sf2);
	}
	if (accrualType.equals("singleInterest") && payaccrualType.equals("monthPay")) {//按月
  		 Date adate=DateUtil.addMonthsToDate(DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), -1),payintentPeriod);
  		 BigDecimal shanshi=new BigDecimal("30");
  		 accrual =accrual.divide(BigDecimal.valueOf(365),10, BigDecimal.ROUND_HALF_EVEN);//利率是按年算的
  		 if(isStartDatePay.equals("1")){ //固定在某日
				if(Long.valueOf(payintentPerioDate)>Long.valueOf(arr[2])){
		 			BigDecimal firstPerioddays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate(date1,startDate)).toString());
		 			sf1 = calculslfundintent(obligationFundIntenList.InvestAccrual,            // 资金类型
		 					isPreposePayAccrual==1?DateUtil.parseDate(date1,"yyyy-MM-dd"):DateUtil.parseDate(startDate,"yyyy-MM-dd"),  //计划日期
		 					BigDecimal.valueOf(0),                                                // 支出金额
		 					projectMoney.multiply(accrual).multiply(firstPerioddays),0,isPreposePayAccrual,payaccrualType,date1,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType 	);        // 收入金额
		 			if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		 			list.add(sf1);
		 			}	
		  		    
				}
	 			for (int i = 1; i < payintentPeriod; i++) {
	 				Date startactualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), i-1);
					Date endctualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), i);
					BigDecimal actualdays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(startactualdate), sdf.format(endctualdate))).toString());
	 				 sf1 = calculslfundintent(obligationFundIntenList.InvestAccrual,            // 资金类型
	 						isPreposePayAccrual==1?startactualdate:endctualdate,  //计划日期
	 							BigDecimal.valueOf(0),                                                // 支出金额
	 							projectMoney.multiply(accrual).multiply(actualdays),i,isPreposePayAccrual,payaccrualType,date1,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType 	);        // 收入金额
	 				if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
	 				 list.add(sf1);
	 				}
 				    
	 			 };
	 			 if(payintentPeriod>1){
		 			 adate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"),payintentPeriod), -1);
		 			   Date bdate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"),payintentPeriod-1);
		 			  
		 			BigDecimal endPerioddays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(bdate), sdf.format(adate))).toString());
		 			sf1 = calculslfundintent(obligationFundIntenList.InvestAccrual,            // 资金类型
		 					isPreposePayAccrual==1?bdate:adate,  //计划日期
		 					BigDecimal.valueOf(0),                                                // 支出金额
		 					projectMoney.multiply(accrual).multiply(endPerioddays), payintentPeriod,isPreposePayAccrual,payaccrualType,date1,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType	);        // 收入金额
		 			if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		 			list.add(sf1);
		 			}
		  		   
	 			 }
			
		}else{
				for (int i = 1; i < payintentPeriod+1; i++) {
	   			  Date startactualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i-1);
	   			  if(i>1){
	   				  startactualdate=DateUtil.addDaysToDate(startactualdate, -1);
	   			  }
	   			  Date endctualdate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i), -1);
	   			  BigDecimal actualdays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(startactualdate), sdf.format(endctualdate))).toString());
	   			
	   			  Date intentDate=null;
	   			  if(isPreposePayAccrual==1){
	   				  intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1,"yyyy-MM-dd"), i-1), -1);
	   				  if(i==1){
	   					  intentDate=DateUtil.parseDate(date1,"yyyy-MM-dd");
	   				  }
	   			  }else if(isPreposePayAccrual==0){
	   				  intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i), -1);
	   			  }
	   		
	   			  sf1 = calculslfundintent(obligationFundIntenList.InvestAccrual,            // 资金类型
	   					intentDate,  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						projectMoney.multiply(accrual).multiply(actualdays),i,isPreposePayAccrual,payaccrualType,date1,date2,isStartDatePay,null,accrualType 	);
	   			if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
	   			  list.add(sf1);
	   			}
  				  
			}
	 		
		 }
  		 	sf2 =calculslfundintent(obligationFundIntenList.InvestRoot,            // 资金类型
  		 		DateUtil.parseDate(date2, "yyyy-MM-dd"),  //计划日期
				BigDecimal.valueOf(0),                                                // 支出金额
				projectMoney,payintentPeriod,isPreposePayAccrual,payaccrualType,date1,date2,isStartDatePay,null,accrualType);      // 收入金额
  		 	list.add(sf2);
			
		}
	if (accrualType.equals("singleInterest") && payaccrualType.equals("seasonPay")) {
		payintentPeriod=payintentPeriod/3;//期限是按月填写的
		int remainMonths =payintentPeriod%3;//期限是按月填写的,这个是求余得出非整除剩余的月份
		accrual =accrual.divide(BigDecimal.valueOf(365),10, BigDecimal.ROUND_HALF_EVEN);//利率是按年算的
  		 Date adate=DateUtil.addDaysToDate(DateUtil.parseDate(date2, "yyyy-MM-dd"), -1);
  		 if(isStartDatePay.equals("1")){ //固定在某日
				if(Long.valueOf(payintentPerioDate)>Long.valueOf(arr[2])){
		 			BigDecimal firstPerioddays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate(date1,startDate)).toString());
		 			sf1 = calculslfundintent(obligationFundIntenList.InvestAccrual,            // 资金类型
		 					isPreposePayAccrual==1?DateUtil.parseDate(date1,"yyyy-MM-dd"):DateUtil.parseDate(startDate,"yyyy-MM-dd"),  //计划日期
		 					BigDecimal.valueOf(0),                                                // 支出金额
		 					projectMoney.multiply(accrual).multiply(firstPerioddays),0,isPreposePayAccrual,payaccrualType,date1,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType 	);        // 收入金额
		 			if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		 			list.add(sf1);
		 			}
				}
	 			
	 			 if(remainMonths!=0){//期限不为整数时的情况
	 				for (int i = 1; i < payintentPeriod+1; i++) {
		 				Date startactualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), (i-1)*3);
						Date endctualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), i*3);
						BigDecimal actualdays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(startactualdate), sdf.format(endctualdate))).toString());
		 				 sf1 = calculslfundintent(obligationFundIntenList.InvestAccrual,            // 资金类型
		 						isPreposePayAccrual==1?startactualdate:endctualdate,  //计划日期
		 							BigDecimal.valueOf(0),                                                // 支出金额
		 							projectMoney.multiply(accrual).multiply(actualdays),i,isPreposePayAccrual,payaccrualType,date1,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType 	);        // 收入金额
		 				if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		 				list.add(sf1);
		 				}
		 			 };
		 			 Date startactualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), payintentPeriod*3);
					 startactualdate=DateUtil.addDaysToDate(startactualdate, -1);
					 Date endctualdate=DateUtil.addDaysToDate(DateUtil.parseDate(date2, "yyyy-MM-dd"), -1);
		   			  BigDecimal actualdays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(startactualdate), sdf.format(endctualdate))).toString());
		   			
		   			  Date intentDate=null;
		   			  if(isPreposePayAccrual==1){
		   				  intentDate=startactualdate;
		   			  }else if(isPreposePayAccrual==0){
		   				  intentDate=DateUtil.addDaysToDate(DateUtil.parseDate(date2, "yyyy-MM-dd"), -1);;
		   			  }
		   			
		   			  sf1 = calculslfundintent(obligationFundIntenList.InvestAccrual,            // 资金类型
		   					intentDate,  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							projectMoney.multiply(accrual).multiply(actualdays),payintentPeriod+1,isPreposePayAccrual,payaccrualType,date1,date2,isStartDatePay,null,accrualType 	);
		   			if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		   			  list.add(sf1);
		   			}
		 			 
	 			 }else{//期数为整数的情况
	 				for (int i = 1; i < payintentPeriod; i++) {
		 				Date startactualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), (i-1)*3);
						Date endctualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), i*3);
						BigDecimal actualdays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(startactualdate), sdf.format(endctualdate))).toString());
		 				 sf1 = calculslfundintent(obligationFundIntenList.InvestAccrual,            // 资金类型
		 						isPreposePayAccrual==1?startactualdate:endctualdate,  //计划日期
		 							BigDecimal.valueOf(0),                                                // 支出金额
		 							projectMoney.multiply(accrual).multiply(actualdays),i,isPreposePayAccrual,payaccrualType,date1,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType 	);        // 收入金额
		 				if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		 				list.add(sf1);
		 				}
		 			 };
	 				if(payintentPeriod>1){
			 			 adate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"),payintentPeriod*3), -1);
			 			   Date bdate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"),(payintentPeriod-1)*3);
			 			  
			 			BigDecimal endPerioddays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(bdate), sdf.format(adate))).toString());
			 			sf1 = calculslfundintent(obligationFundIntenList.InvestAccrual,            // 资金类型
			 					isPreposePayAccrual==1?bdate:adate,  //计划日期
			 					BigDecimal.valueOf(0),                                                // 支出金额
			 					projectMoney.multiply(accrual).multiply(endPerioddays), payintentPeriod,isPreposePayAccrual,payaccrualType,date1,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType	);        // 收入金额
			 			if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
			 			list.add(sf1);
			 			}
			  		    
		 			 }
	 			 }
	 			 
			
		}else{
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
		   			
		   			  sf1 = calculslfundintent(obligationFundIntenList.InvestAccrual,            // 资金类型
		   					intentDate,  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							projectMoney.multiply(accrual).multiply(actualdays),i,isPreposePayAccrual,payaccrualType,date1,date2,isStartDatePay,null,accrualType 	);
		   			if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		   			  list.add(sf1);
		   			}
			  			
			  }
				if(remainMonths!=0){//计算期数不为整数的情况
					Date startactualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), payintentPeriod*3);
					 startactualdate=DateUtil.addDaysToDate(startactualdate, -1);
					 Date endctualdate=DateUtil.addDaysToDate(DateUtil.parseDate(date2, "yyyy-MM-dd"), -1);
		   			  BigDecimal actualdays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(startactualdate), sdf.format(endctualdate))).toString());
		   			
		   			  Date intentDate=null;
		   			  if(isPreposePayAccrual==1){
		   				  intentDate=startactualdate;
		   			  }else if(isPreposePayAccrual==0){
		   				  intentDate=DateUtil.addDaysToDate(DateUtil.parseDate(date2, "yyyy-MM-dd"), -1);;
		   			  }
		   			
		   			  sf1 = calculslfundintent(obligationFundIntenList.InvestAccrual,            // 资金类型
		   					intentDate,  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							projectMoney.multiply(accrual).multiply(actualdays),payintentPeriod+1,isPreposePayAccrual,payaccrualType,date1,date2,isStartDatePay,null,accrualType 	);
		   			if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		   			  list.add(sf1);
		   			}
				}
	 		
		}
			 sf =calculslfundintent(obligationFundIntenList.InvestRoot,            // 资金类型
					 DateUtil.addDaysToDate(DateUtil.parseDate(date2, "yyyy-MM-dd"), -1),  //计划日期
					BigDecimal.valueOf(0),                                                // 支出金额
					projectMoney ,payintentPeriod,isPreposePayAccrual,payaccrualType,date1,date2,isStartDatePay,isStartDatePay.equals("1")?String.valueOf(payintentPerioDate):null,accrualType 	);   

			list.add(sf);
			
	}
	if (accrualType.equals("singleInterest") && payaccrualType.equals("yearPay")) {//按年计息

		payintentPeriod=payintentPeriod/12;//期限是按月填写的
		int remainMonths =payintentPeriod%12;//期限是按月填写的,这个是求余得出非整除剩余的月份
		accrual =accrual.divide(BigDecimal.valueOf(365),10, BigDecimal.ROUND_HALF_EVEN);//利率是按年算的
  		 Date adate=DateUtil.addDaysToDate(DateUtil.parseDate(date2, "yyyy-MM-dd"), -1);
  		 if(isStartDatePay.equals("1")){ //固定在某日
				if(Long.valueOf(payintentPerioDate)>Long.valueOf(arr[2])){
		 			BigDecimal firstPerioddays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate(date1,startDate)).toString());
		 			sf1 = calculslfundintent(obligationFundIntenList.InvestAccrual,            // 资金类型
		 					isPreposePayAccrual==1?DateUtil.parseDate(date1,"yyyy-MM-dd"):DateUtil.parseDate(startDate,"yyyy-MM-dd"),  //计划日期
		 					BigDecimal.valueOf(0),                                                // 支出金额
		 					projectMoney.multiply(accrual).multiply(firstPerioddays),0,isPreposePayAccrual,payaccrualType,date1,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType 	);        // 收入金额
		 			if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		 			list.add(sf1);
		 			}
				}
	 			
	 			 if(remainMonths!=0){//期限不为整数时的情况
	 				for (int i = 1; i < payintentPeriod+1; i++) {
		 				Date startactualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), (i-1)*12);
						Date endctualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), i*12);
						BigDecimal actualdays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(startactualdate), sdf.format(endctualdate))).toString());
		 				 sf1 = calculslfundintent(obligationFundIntenList.InvestAccrual,            // 资金类型
		 						isPreposePayAccrual==1?startactualdate:endctualdate,  //计划日期
		 							BigDecimal.valueOf(0),                                                // 支出金额
		 							projectMoney.multiply(accrual).multiply(actualdays),i,isPreposePayAccrual,payaccrualType,date1,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType 	);        // 收入金额
		 				if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		 				list.add(sf1);
		 				}
		 			 };
		 			 Date startactualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), payintentPeriod*12);
					 startactualdate=DateUtil.addDaysToDate(startactualdate, -1);
					 Date endctualdate=DateUtil.addDaysToDate(DateUtil.parseDate(date2, "yyyy-MM-dd"), -1);
		   			  BigDecimal actualdays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(startactualdate), sdf.format(endctualdate))).toString());
		   			
		   			  Date intentDate=null;
		   			  if(isPreposePayAccrual==1){
		   				  intentDate=startactualdate;
		   			  }else if(isPreposePayAccrual==0){
		   				  intentDate=DateUtil.addDaysToDate(DateUtil.parseDate(date2, "yyyy-MM-dd"), -1);;
		   			  }
		   			
		   			  sf1 = calculslfundintent(obligationFundIntenList.InvestAccrual,            // 资金类型
		   					intentDate,  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							projectMoney.multiply(accrual).multiply(actualdays),payintentPeriod+1,isPreposePayAccrual,payaccrualType,date1,date2,isStartDatePay,null,accrualType 	);
		   			if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		   			  list.add(sf1);
		   			}
		 			 
	 			 }else{//期数为整数的情况
	 				for (int i = 1; i < payintentPeriod; i++) {
		 				Date startactualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), (i-1)*12);
						Date endctualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), i*12);
						BigDecimal actualdays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(startactualdate), sdf.format(endctualdate))).toString());
		 				 sf1 = calculslfundintent(obligationFundIntenList.InvestAccrual,            // 资金类型
		 						isPreposePayAccrual==1?startactualdate:endctualdate,  //计划日期
		 							BigDecimal.valueOf(0),                                                // 支出金额
		 							projectMoney.multiply(accrual).multiply(actualdays),i,isPreposePayAccrual,payaccrualType,date1,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType 	);        // 收入金额
		 				if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		 				list.add(sf1);
		 				}
		 			 };
	 				if(payintentPeriod>1){
			 			 adate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"),payintentPeriod*12), -1);
			 			   Date bdate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"),(payintentPeriod-1)*12);
			 			  
			 			BigDecimal endPerioddays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(bdate), sdf.format(adate))).toString());
			 			sf1 = calculslfundintent(obligationFundIntenList.InvestAccrual,            // 资金类型
			 					isPreposePayAccrual==1?bdate:adate,  //计划日期
			 					BigDecimal.valueOf(0),                                                // 支出金额
			 					projectMoney.multiply(accrual).multiply(endPerioddays), payintentPeriod,isPreposePayAccrual,payaccrualType,date1,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType	);        // 收入金额
			 			if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
			 			list.add(sf1);
			 			}
			  		    
		 			 }
	 			 }
	 			 
			
		}else{
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
		   			
		   			  sf1 = calculslfundintent(obligationFundIntenList.InvestAccrual,            // 资金类型
		   					intentDate,  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							projectMoney.multiply(accrual).multiply(actualdays),i,isPreposePayAccrual,payaccrualType,date1,date2,isStartDatePay,null,accrualType 	);
		   			if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		   			  list.add(sf1);
		   			}
			  			
			  }
				if(remainMonths!=0){//计算期数不为整数的情况
					Date startactualdate=DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), payintentPeriod*12);
					 startactualdate=DateUtil.addDaysToDate(startactualdate, -1);
					 Date endctualdate=DateUtil.addDaysToDate(DateUtil.parseDate(date2, "yyyy-MM-dd"), -1);
		   			  BigDecimal actualdays=new BigDecimal(Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(startactualdate), sdf.format(endctualdate))).toString());
		   			
		   			  Date intentDate=null;
		   			  if(isPreposePayAccrual==1){
		   				  intentDate=startactualdate;
		   			  }else if(isPreposePayAccrual==0){
		   				  intentDate=DateUtil.addDaysToDate(DateUtil.parseDate(date2, "yyyy-MM-dd"), -1);;
		   			  }
		   			
		   			  sf1 = calculslfundintent(obligationFundIntenList.InvestAccrual,            // 资金类型
		   					intentDate,  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							projectMoney.multiply(accrual).multiply(actualdays),payintentPeriod+1,isPreposePayAccrual,payaccrualType,date1,date2,isStartDatePay,null,accrualType 	);
		   			if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		   			  list.add(sf1);
		   			}
				}
	 		
		}
			 sf =calculslfundintent(obligationFundIntenList.InvestRoot,            // 资金类型
					 DateUtil.addDaysToDate(DateUtil.parseDate(date2, "yyyy-MM-dd"), -1),  //计划日期
					BigDecimal.valueOf(0),                                                // 支出金额
					projectMoney ,payintentPeriod,isPreposePayAccrual,payaccrualType,date1,date2,isStartDatePay,isStartDatePay.equals("1")?String.valueOf(payintentPerioDate):null,accrualType 	);   

			list.add(sf);
			
		}
	/**
	 * 等额本金
	 */
	if (accrualType.equals("sameprincipal") && payaccrualType.equals("monthPay")) {// 月尾
		BigDecimal preperiodsurplus=projectMoney;
		accrual =accrual.divide(BigDecimal.valueOf(365),10, BigDecimal.ROUND_HALF_EVEN);//利率是按年算的
		if(payintentPeriod>0){
		 BigDecimal sameprojectMoney = projectMoney.divide(new BigDecimal(payintentPeriod),100,BigDecimal.ROUND_HALF_UP);
		 
		 if(isStartDatePay.equals("1")){ //固定在某日
			 if(payintentPeriod>1){
				 //第一期的情况
				String firstdays=Integer.valueOf(DateUtil.getDaysBetweenDate( date1,sdf.format(DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), 1)))).toString();
				sf1 = calculslfundintent(obligationFundIntenList.InvestAccrual,            // 资金类型
				 			isPreposePayAccrual==0?DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), 1):DateUtil.parseDate(date1,"yyyy-MM-dd"),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							FundIntentListPro3.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(firstdays)),      // 收入金额
							1,isPreposePayAccrual,payaccrualType,date1,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType
					);
		    	  sf =calculslfundintent(obligationFundIntenList.InvestRoot,            // 资金类型
		    			  DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), 1),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							sameprojectMoney,
							1,isPreposePayAccrual,payaccrualType,date1,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType);      // 收入金额
		    	  if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
		    	  list.add(sf1);
		    	  }
					list.add(sf);
					preperiodsurplus=preperiodsurplus.subtract(sf.getIncomeMoney());
			 //整个月的情况
				 for (int i = 2; i <payintentPeriod; i++) {
					 Date intentDate=null;
					 if(isPreposePayAccrual==0){
						 intentDate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), i);
					 }else if(isPreposePayAccrual==1){
						 intentDate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), i-1);
					 }
				 sf1 = calculslfundintent(obligationFundIntenList.InvestAccrual,            // 资金类型
						 intentDate,  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							obligationFundIntenList.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(30)),
							i,isPreposePayAccrual,payaccrualType,date1,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType);        // 收入金额
				    sf =calculslfundintent(obligationFundIntenList.InvestRoot,            // 资金类型
				    		intentDate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), i),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								sameprojectMoney,
								i,isPreposePayAccrual,payaccrualType,date1,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType);      // 收入金额
				    if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
				    list.add(sf1);
				    }
					list.add(sf);
					
					preperiodsurplus=preperiodsurplus.subtract(sf.getIncomeMoney());
				 }
				 //最后一期的情况
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
							payintentPeriod,isPreposePayAccrual,payaccrualType,date1,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType
				 );
				    sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
				    		DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod), -1),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							sameprojectMoney,     // 收入金额
							payintentPeriod,isPreposePayAccrual,payaccrualType,date1,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType
				    );
				    if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
				    list.add(sf1);
				    }
					list.add(sf);
				
					preperiodsurplus=preperiodsurplus.subtract(sf.getIncomeMoney());
			 }else{//只有1期的情况
				 String enddays=Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(DateUtil.parseDate(date1, "yyyy-MM-dd")), sdf.format(DateUtil.addDaysToDate(DateUtil.parseDate(date2), -1)))).toString();
				 Date intDate=null;
				 if(isPreposePayAccrual==0){
					 intDate=DateUtil.parseDate(date2);
				 }else if(isPreposePayAccrual==1){
					 intDate=DateUtil.parseDate(date1, "yyyy-MM-dd");
				 }
				 sf1 = calculslfundintent(FundIntentListPro3.ProjectLoadAccrual,            // 资金类型
						 intDate,  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							FundIntentListPro3.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(enddays)),      // 收入金额
							payintentPeriod,isPreposePayAccrual,payaccrualType,date1,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType
				 );
				    sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
				    		DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod), -1),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							sameprojectMoney,     // 收入金额
							payintentPeriod,isPreposePayAccrual,payaccrualType,date1,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType
				    );
				    if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
				    list.add(sf1);
				    }
					list.add(sf);
			 }
			
			 
			 
				
		
		 }else{
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
					 sf1 = calculslfundintent(obligationFundIntenList.InvestAccrual,            // 资金类型
							intentDate,  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							obligationFundIntenList.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(30)),
							i,isPreposePayAccrual,payaccrualType,date1,date2,isStartDatePay,null,accrualType);        // 收入金额
				    sf =calculslfundintent(obligationFundIntenList.InvestRoot,            // 资金类型
				    		DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i), -1),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								sameprojectMoney ,
								i,isPreposePayAccrual,payaccrualType,date1,date2,isStartDatePay,null,accrualType);   
				    if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
					list.add(sf1);// 收入金额
				    }		
					list.add(sf);
					
					preperiodsurplus=preperiodsurplus.subtract(sf.getIncomeMoney());
				 }
			 
		 }
		}
	}
	/**
	 * 等额本息
	 */
	if (accrualType.equals("sameprincipalandInterest") && payaccrualType.equals("monthPay")) {// 月尾
		 accrual =accrual.divide(BigDecimal.valueOf(365),10, BigDecimal.ROUND_HALF_EVEN);//利率是按年算的
	     BigDecimal periodtimemoney=obligationFundIntenList.periodtimemoney(accrual.multiply(new BigDecimal(30)),projectMoney,payintentPeriod).divide(new BigDecimal(1),2,BigDecimal.ROUND_HALF_UP);
	     BigDecimal preperiodsurplus=projectMoney;
	     if(isStartDatePay.equals("1")){ //固定在某日
				// String enddays=Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(DateUtil.addMonthsToDate(DateUtil.parseDate(startperiodDatejianyi, "yyyy-MM-dd"), payintentPeriod-1)),sdf.format(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), payintentPeriod)))).toString();
				if(payintentPeriod>1){
					//第一期情况
					 String firstdays=Integer.valueOf(DateUtil.getDaysBetweenDate( date1,sdf.format(DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), 1)))).toString();
			    	 sf1 = calculslfundintent(obligationFundIntenList.InvestAccrual,            // 资金类型
			    			 isPreposePayAccrual==0?DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), 1):DateUtil.parseDate(date1,"yyyy-MM-dd"),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPro3.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(firstdays)),      // 收入金额
								1,isPreposePayAccrual,payaccrualType,date1,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType
			    	 );
			    	  BigDecimal firstperiodtimemoney=periodtimemoney.add(sf1.getIncomeMoney().subtract(obligationFundIntenList.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(30))));
					    sf =calculslfundintent(obligationFundIntenList.InvestRoot,            // 资金类型
								DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), 1),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								firstperiodtimemoney.subtract(sf1.getIncomeMoney()),        // 收入金额
								1,isPreposePayAccrual,payaccrualType,date1,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType
					    );
					    if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
						list.add(sf1);
					    }
						list.add(sf);
						preperiodsurplus=preperiodsurplus.subtract(sf.getIncomeMoney());
						
					//整期的	
					for (int i = 2; i < payintentPeriod; i++) {
					sf1 = calculslfundintent(obligationFundIntenList.InvestAccrual,            // 资金类型
							isPreposePayAccrual==0?DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), i):DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), i-1),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							obligationFundIntenList.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(30)),      // 收入金额
							i,isPreposePayAccrual,payaccrualType,date1,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType
							);
				    sf =calculslfundintent(obligationFundIntenList.InvestRoot,            // 资金类型
							DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), i),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							periodtimemoney.subtract(obligationFundIntenList.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(30))),        // 收入金额
							i,isPreposePayAccrual,payaccrualType,date1,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType
				    );
				    if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
						list.add(sf1);
					    }
				    
					list.add(sf);
					preperiodsurplus=preperiodsurplus.subtract(sf.getIncomeMoney());
					}
					//最后一期情况
					String enddays=Integer.valueOf(DateUtil.getDaysBetweenDate( sdf.format(DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), payintentPeriod-1)),sdf.format(DateUtil.addDaysToDate(DateUtil.parseDate(date2), -1)))).toString();
					sf1 = calculslfundintent(obligationFundIntenList.InvestAccrual,            // 资金类型
							isPreposePayAccrual==0?DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod), -1):DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), payintentPeriod-1),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPro3.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(enddays)),      // 收入金额
								payintentPeriod,isPreposePayAccrual,payaccrualType,date1,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType
								);
			    	  BigDecimal endperiodtimemoney=periodtimemoney.subtract((FundIntentListPro3.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(30))).subtract(sf1.getIncomeMoney()));
					    sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
					    		DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod), -1),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								endperiodtimemoney.subtract(sf1.getIncomeMoney()),        // 收入金额
								payintentPeriod,isPreposePayAccrual,payaccrualType,date1,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType
					    );
					    if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
							list.add(sf1);
						    }
					 
						list.add(sf);
					}else if(payintentPeriod==1){
						String enddays=Integer.valueOf(DateUtil.getDaysBetweenDate( date1,sdf.format(DateUtil.addDaysToDate(DateUtil.parseDate(date2), -1)))).toString();
						sf1 = calculslfundintent(obligationFundIntenList.InvestAccrual,            // 资金类型
								isPreposePayAccrual==0?DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod), -1):DateUtil.addMonthsToDate(DateUtil.parseDate(startDate, "yyyy-MM-dd"), payintentPeriod-1),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									FundIntentListPro3.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(enddays)),      // 收入金额
									payintentPeriod,isPreposePayAccrual,payaccrualType,date1,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType
									);
				    	  BigDecimal endperiodtimemoney=periodtimemoney.subtract((FundIntentListPro3.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(30))).subtract(sf1.getIncomeMoney()));
						    sf =calculslfundintent(FundIntentListPro3.ProjectLoadRoot,            // 资金类型
						    		DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1), payintentPeriod), -1),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									endperiodtimemoney.subtract(sf1.getIncomeMoney()),        // 收入金额
									payintentPeriod,isPreposePayAccrual,payaccrualType,date1,date2,isStartDatePay,String.valueOf(payintentPerioDate),accrualType
						    );
						    if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
								list.add(sf1);
							    }
						 
							list.add(sf);
					}
						
	    	 
	     }else{
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
					sf1 = calculslfundintent(obligationFundIntenList.InvestAccrual,            // 资金类型
							intentDate,  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							obligationFundIntenList.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(30)),      // 收入金额
							i,isPreposePayAccrual,payaccrualType,date1,date2,isStartDatePay,null,accrualType
							);
				    sf =calculslfundintent(obligationFundIntenList.InvestRoot,            // 资金类型
				    		DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i), -1),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							periodtimemoney.subtract(FundIntentListPro3.calculAccrualnew(preperiodsurplus,accrual).multiply(new BigDecimal(30))) ,       // 收入金额
							i,isPreposePayAccrual,payaccrualType,date1,date2,isStartDatePay,null,accrualType
							);
				    if(sf1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
						list.add(sf1);
					    }
				   
					
					
				   
					list.add(sf);
					preperiodsurplus=preperiodsurplus.subtract(sf.getIncomeMoney());
				}
	    	 
	    	 
	    	 
	     }
		}
		return list;
	
		}


//生成利息方法
	public static SlFundIntent calculslfundintent(String fundType,Date intentdate,BigDecimal paymoney,BigDecimal incomemoeny,
			Integer payintentPeriod,Integer isPreposePayAccrual,String payaccrualType,String date1,String date2,String isStartDatePay,String gddate,String accrualtype){
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
		return  sf1;
		
	}

	public static BigDecimal calculAccrualnew(BigDecimal preperiodsurplus,BigDecimal accrual){

		BigDecimal dayp = new BigDecimal(0);
         

		return  preperiodsurplus.multiply(accrual);
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
}
