package com.zhiwei.credit.core.project;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.model.creditFlow.smallLoan.supervise.SlSuperviseRecord;

public class StatsPro {
	
	/**
	 * 计算贷款项目年化净利率
	 * （1）Stub型
	 *   年化净利率=[（stub利率+管理咨询费率+财务服务费率）/贷款天数*360*贷款总额 +杂项收入总额-杂项支出总额]/贷款总额
	 * （2）按日计息型
     *   年化净利率=（（日利率+管理咨询费率+财务服务费率）*360*贷款总额+杂项收入总额-杂项支出总额）/贷款总额
	 * （3）按月计息型
	 * 	   年化净利率=（（月利率+管理咨询费率+财务服务费率）*12*贷款总额+杂项收入总额-杂项支出总额）/贷款总额
	 * （4）按年计息型
	 *    年化净利率=（（年利率+管理咨询费率+财务服务费率）*贷款总额+杂项收入总额-杂项支出总额）/贷款总额
	 * @param accrualType accrualType为 1时为stub型, 2时按日计息,3是按月计息,4.按年计息
	 * @param accrual 利率
	 * @param projectMoney -贷款金额
	 * @param commisionMoney -佣金
	 * @param elseMoney -其它费用
	 * @param date1 起始时间
	 * @param date2 结束时间
	 * @return  年化净利率
	 */
	public static double calculLoanNetProfit(String accrualType,double accrual,double projectMoney,double managementConsultingOfRate,double financeServiceOfRate,String date1,String date2,double incomechargeMoney,double paychargeMoney,String payaccrualTyepID,int dayOfEveryPeriod){
		
		/*if(projectMoney==0){
			return 0;
		}else{
			
				if(accrualType=="ontTimeAccrual"){
					return 0;
				}else{
					
							if(payaccrualTyepID.equals("dayPay")){
								return ((accrual+managementConsultingOfRate+financeServiceOfRate)*360*projectMoney+incomechargeMoney-paychargeMoney)/projectMoney;
								
							}else if(payaccrualTyepID.equals("monthPay")){
								return ((accrual+managementConsultingOfRate+financeServiceOfRate)*12*projectMoney+incomechargeMoney-paychargeMoney)/projectMoney ;
								
							}else if(payaccrualTyepID.equals("seasonPay")){
								return ((accrual+managementConsultingOfRate+financeServiceOfRate)*4*projectMoney+incomechargeMoney-paychargeMoney)/projectMoney;
							}else if(payaccrualTyepID.equals("yearPay")){
								return ((accrual+managementConsultingOfRate+financeServiceOfRate)*projectMoney+incomechargeMoney-paychargeMoney)/projectMoney;
								
							}else if(payaccrualTyepID.equals("owerPay")){
								
								return ((accrual+managementConsultingOfRate+financeServiceOfRate)*360*projectMoney/dayOfEveryPeriod+incomechargeMoney-paychargeMoney)/projectMoney;
							}
				        return 0;
			}
		}*/
		
		return (accrual+managementConsultingOfRate)*12;
		
	}
	
	
	/**
	 * 计算融资项目年化净利率
	 * （1）Stub型
	 *   年化净利率=[（stub利率/贷款天数）*360*贷款总额 -佣金-其他支出]/贷款总额
	 * （2）按日计息型
     *   年化净利率=（日利率*360*贷款总额-佣金-其他支出）/贷款总额
	 * （3）按月计息型
	 * 	   年化净利率=（月利率*12*贷款总额-佣金-其他支出）/贷款总额
	 * （4）按年计息型
	 *    年化净利率=（年利率*贷款总额-佣金-其他支出）/贷款总额
	 * @param accrualType accrualType为 1时为stub型, 2时按日计息,3是按月计息,4.按年计息
	 * @param accrual 利率
	 * @param projectMoney -贷款金额
	 * @param commisionMoney -佣金
	 * @param elseMoney -其它费用
	 * @param date1 起始时间
	 * @param date2 结束时间
	 * @return  年化净利率
	 */
	public static double calculfinnalNetProfit(int accrualType,double accrual,double projectMoney,double commisionMoney,double elseMoney,String date1,String date2){
		if(date1=="" || date2==""){
			return 0;
		}
		int days=DateUtil.getDaysBetweenDate(date1, date2);
		if(projectMoney==0 || accrualType==0){
			return 0;
		}else if(accrualType==1){//sub型
			return ((accrual/days)*360*projectMoney-commisionMoney-elseMoney)/projectMoney;
		}else if(accrualType==2){ //按日计息
			return (accrual*360*projectMoney-commisionMoney-elseMoney)/projectMoney;
		}else if(accrualType==3){
			return (accrual*12*projectMoney-commisionMoney-elseMoney)/projectMoney ;
		}else{
			return ((accrual*projectMoney-commisionMoney-elseMoney)/projectMoney);
		}	
	}

	/**
	 * 
	 * @param calcutype  贷款方式 0为贷款;1为融资 
	 * @param accrualType 计息方式  1-sub型 2-按日计息 3-按月计息 ,4-按季计息,5-按半年计息,6-按年计息 
	 * @param accrual  利率
	 * @param date1  起始日
	 * @param date2 结束日 
	 * @return 日化利率
	 */
	public static BigDecimal calcuDayProfit(String calcutype, String payaccrualType,double accrual1,String date1,String date2){
		BigDecimal accrual=BigDecimal.valueOf(accrual1);
		if(date1=="" || date2==""){
			return new BigDecimal(0);
		}
		int days=DateUtil.getDaysBetweenDate(date1, date2);
		if(calcutype.equals("SmallLoan")){
			days=days+1;
		}
		if(payaccrualType.equals("oneTimePay")){//sub型
			return accrual.divide(new BigDecimal(days),26,BigDecimal.ROUND_HALF_UP);
		}else if(payaccrualType.equals("dayPay")){ //按日计息
			return accrual;
		}else if(payaccrualType.equals("monthPay")){ //按月计息
			return accrual.divide(new BigDecimal(30),26,BigDecimal.ROUND_HALF_UP);
		}else if(payaccrualType.equals("seasonPay")){//按季计息
			return accrual.divide(new BigDecimal(90),26,BigDecimal.ROUND_HALF_UP);
		}else if(payaccrualType.equals("halfYearPay")){//按半年计息
			return accrual.divide(new BigDecimal(180),26,BigDecimal.ROUND_HALF_UP);
		}else if(payaccrualType.equals("yearPay")){//按年计息
			return accrual.divide(new BigDecimal(360),26,BigDecimal.ROUND_HALF_UP);
		}else if(payaccrualType.equals("calendarMonthPay")){//按年计息
			return accrual.divide(new BigDecimal(30),26,BigDecimal.ROUND_HALF_UP);
		}else{
			return new BigDecimal(0);
		}
	}
	
	public static BigDecimal calcuDayProfit1(int calcutype, int accrualType,BigDecimal accrual,String date1,String date2){
		if(date1=="" || date2==""){
			return new BigDecimal(0);
		}
		int days=DateUtil.getDaysBetweenDate(date1, date2);
		if(calcutype==6574){
			days=days+1;
		}
		if(accrualType==326){//sub型
			return accrual.divide(new BigDecimal(days),10);
		}else if(accrualType==327){ //按日计息
			return accrual;
		}else if(accrualType==328){ //按月计息
			return accrual.divide(new BigDecimal(30),10);
		}else if(accrualType==329){//按季计息
			return accrual.divide(new BigDecimal(90),10);
		}else if(accrualType==330){//按半年计息
			return accrual.divide(new BigDecimal(180),10);
		}else if(accrualType==379){//按年计息
			 	return accrual.divide(new BigDecimal(360),10);
		}else{
			return accrual.divide(new BigDecimal(days),10);
		}
	}
	/**
	 * 
	 * @param calcutype  贷款方式 0为贷款;1为融资 
	 * @param accrualType 计息方式  1-sub型 2-按日计息 3-按月计息 ,4-按季计息,5-按半年计息,6-按年计息
	 * @param accrual  利率
	 * @param date1  起始日
	 * @param date2 结束日 
	 * @return
	 */
	public static BigDecimal calcuProfitMoney(String calcutype,String accrualType,double accrual,double projectMoney,String date1,String date2){
		BigDecimal dayProfit=calcuDayProfit(calcutype, accrualType, accrual, date1, date2);
		return dayProfit.multiply(BigDecimal.valueOf(projectMoney));
	}
	
	public static Date calcuProIntentDate(SlSmallloanProject  sl){
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		 Date startperiodDate=sl.getStartDate();
		//  if(sl.getIsStartDatePay().equals("2")){
			
			  startperiodDate=DateUtil.addDaysToDate(sl.getStartDate(), -1);
		//	}
		  
		
		Date intentDate=new Date();
		if(!"ontTimeAccrual".equals(sl.getAccrualtype())){	  
				if (sl.getPayaccrualType().equals("dayPay")) {
					intentDate=DateUtil.addDaysToDate(startperiodDate, sl.getPayintentPeriod());
					sl.setIntentDate(intentDate);
					
				}
		           if (sl.getPayaccrualType().equals("monthPay")) {
		        	   intentDate= DateUtil.addMonthsToDate(startperiodDate, sl.getPayintentPeriod());
		        	   sl.setIntentDate(intentDate);
					
				}
		           if (sl.getPayaccrualType().equals("seasonPay")) {
		        	   intentDate= DateUtil.addMonthsToDate(startperiodDate, sl.getPayintentPeriod()*3);
		        	   sl.setIntentDate(intentDate);
					
				}
		           if (sl.getPayaccrualType().equals("yearPay")) {
		        	   intentDate=  DateUtil.addMonthsToDate(startperiodDate, sl.getPayintentPeriod()*12);
		        	   sl.setIntentDate(intentDate);
				}
			
		           if (sl.getPayaccrualType().equals("owerPay")) {
		        	   intentDate=DateUtil.addDaysToDate(startperiodDate, sl.getPayintentPeriod()*sl.getDayOfEveryPeriod());
		   			
		   		}
		}

        return null;
		
		
	}
	
	public static Date calcuProEndDate(SlSuperviseRecord  sl){
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		 Date startperiodDate=null;
		 if(sl.getStartDate()!=null){
			 startperiodDate=sl.getStartDate();
			 startperiodDate=DateUtil.addDaysToDate(sl.getStartDate(), -1);
		 }
		//  if(sl.getIsStartDatePay().equals("2")){
			
			 
		//	}
		  
		
		Date intentDate=new Date();
		if(!sl.getAccrualtype().equals("ontTimeAccrual")&&sl.getPayintentPeriod()!=null){	  
				if (sl.getPayaccrualType().equals("dayPay")) {
					intentDate=DateUtil.addDaysToDate(startperiodDate, sl.getPayintentPeriod());
					sl.setEndDate(intentDate);
					
				}
	           if (sl.getPayaccrualType().equals("monthPay")) {
	        	   intentDate= DateUtil.addMonthsToDate(startperiodDate, sl.getPayintentPeriod());
	        	   sl.setEndDate(intentDate);
					
				}
	           if (sl.getPayaccrualType().equals("seasonPay")) {
	        	   intentDate= DateUtil.addMonthsToDate(startperiodDate, sl.getPayintentPeriod()*3);
	        	   sl.setEndDate(intentDate);
					
				}
	           if (sl.getPayaccrualType().equals("yearPay")) {
	        	   intentDate=  DateUtil.addMonthsToDate(startperiodDate, sl.getPayintentPeriod()*12);
	        	   sl.setEndDate(intentDate);
				}
			
	           if (sl.getPayaccrualType().equals("owerPay")) {
	        	   intentDate=DateUtil.addDaysToDate(startperiodDate, sl.getPayintentPeriod()*sl.getDayOfEveryPeriod());
	        	   sl.setEndDate(intentDate);
		   	   }
		}

       return null;
		
		
	}
	
	public static Date getIntentDate(String startDate,String payAccrualType,Integer payintentPeriod,Integer dayOfEveryPeriod){
		Date intentDate=null;
		
		if(null!=startDate && !"".equals(startDate) && null!=payintentPeriod){
			if(payAccrualType.equals("dayPay")){
				if(AppUtil.getInterest().equals("0")){
					intentDate=DateUtil.addDaysToDate(DateUtil.parseDate(startDate,"yyyy-MM-dd"), payintentPeriod);
				}else{
					intentDate=DateUtil.addDaysToDate(DateUtil.parseDate(startDate,"yyyy-MM-dd"), payintentPeriod-1);
				}
			}else if(payAccrualType.equals("monthPay")){
				intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(startDate,"yyyy-MM-dd"), payintentPeriod), -1);
			}else if(payAccrualType.equals("seasonPay")){
				intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(startDate,"yyyy-MM-dd"), payintentPeriod*3), -1);
			}else if(payAccrualType.equals("yearPay")){
				intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(startDate,"yyyy-MM-dd"), payintentPeriod*12), -1);
			}else if(payAccrualType.equals("owerPay")){
				if(null!=dayOfEveryPeriod){
					if(AppUtil.getInterest().equals("0")){
						intentDate=DateUtil.addDaysToDate(DateUtil.parseDate(startDate,"yyyy-MM-dd"), payintentPeriod*dayOfEveryPeriod);
					}else{
						intentDate=DateUtil.addDaysToDate(DateUtil.parseDate(startDate,"yyyy-MM-dd"), payintentPeriod*dayOfEveryPeriod-1);
					}
				}
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				String dateStr=sdf.format(sdf.parse(startDate));
				String[] dateArr=dateStr.split("-");
				String dateStri=sdf.format(intentDate);
				String[] dateArri=dateStri.split("-");
//				if(Integer.valueOf(dateArr[2])-Integer.valueOf(dateArri[2])>1){
//					intentDate=DateUtil.addDaysToDate(intentDate, 1);
//				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		return intentDate;
	}
	public static Date getLeaseIntentDate(String startDate,String payAccrualType,Integer payintentPeriod,Integer dayOfEveryPeriod){
		Date intentDate=null;
		if(null!=startDate && !"".equals(startDate) && null!=payintentPeriod){
			if(payAccrualType.equals("dayPay")){
				if(AppUtil.getInterest().equals("0")){
					intentDate=DateUtil.addDaysToDate(DateUtil.parseDate(startDate,"yyyy-MM-dd"), payintentPeriod);
				}else{
					intentDate=DateUtil.addDaysToDate(DateUtil.parseDate(startDate,"yyyy-MM-dd"), payintentPeriod-1);
				}
			}else if(payAccrualType.equals("monthPay")){
				intentDate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate,"yyyy-MM-dd"), payintentPeriod);
			}else if(payAccrualType.equals("seasonPay")){
				intentDate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate,"yyyy-MM-dd"), payintentPeriod*3);
			}else if(payAccrualType.equals("yearPay")){
				intentDate=DateUtil.addMonthsToDate(DateUtil.parseDate(startDate,"yyyy-MM-dd"), payintentPeriod*12);
			}else if(payAccrualType.equals("owerPay")){
				if(null!=dayOfEveryPeriod){
					if(AppUtil.getInterest().equals("0")){
						intentDate=DateUtil.addDaysToDate(DateUtil.parseDate(startDate,"yyyy-MM-dd"), payintentPeriod*dayOfEveryPeriod);
					}else{
						intentDate=DateUtil.addDaysToDate(DateUtil.parseDate(startDate,"yyyy-MM-dd"), payintentPeriod*dayOfEveryPeriod-1);
					}
				}
			}
		}
		return intentDate;
	}
}
