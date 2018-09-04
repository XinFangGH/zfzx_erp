package com.zhiwei.credit.core.project;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.zhiwei.core.util.DateUtil;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;

public class FundIntentListPronew {
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
	 *            accrualType为 1时为stub型, 2时按日计息,3是按月计息,4.按年计息
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
	 * @param commisionType
	 *            -佣金方式
	 * @param commisionMoney
	 *            -佣金
	 * @param elseMoney
	 *            -其它费用
	 * @param elseMoney
	 *            -日期模型
	 * @param date1
	 *            起始时间
	 * @param date2
	 *            结束时间
	 * @param managementConsultingOfRate
	 *			  管理咨询费率
	 * @param financeServiceOfRate
	 * 			  财务服务费率         
	 * @return
	 */
	public static List<SlFundIntent> getFundIntentDefaultList(String calcutype,
			BigDecimal dayProfit, String accrualType, Integer isPreposePayAccrual,
			String payaccrualType, BigDecimal accrual, BigDecimal projectMoney,
			short commisionType, BigDecimal commisionMoney, String dateMode,
			String date1, String date2,BigDecimal managementConsultingOfRate,BigDecimal financeServiceOfRate,BigDecimal managementOfRate,BigDecimal financeOfRate,Integer period) {
		BigDecimal managementpre=managementConsultingOfRate.divide(accrual, 100, BigDecimal.ROUND_HALF_UP);
		BigDecimal financepre=financeServiceOfRate.divide(accrual, 100, BigDecimal.ROUND_HALF_UP);
		BigDecimal accrualpre=new BigDecimal("1").subtract(managementpre).subtract(financepre);
		
		List<SlFundIntent> list = new ArrayList<SlFundIntent>();
		SlFundIntent sf;
		SlFundIntent sf1;
		SlFundIntent sf2;
		SlFundIntent sf3;
		if (date1 == "" || date2 == "") {
			return null;
		}
		// 贷款贷出
		sf = new SlFundIntent();
		if (calcutype.equals("SmallLoan")) { // 贷款项目
			sf.setFundType(FundIntentListPronew.ProjectLoadOut);// 资金类型
			sf.setIntentDate(DateUtil.parseDate(date1, "yyyy-MM-dd"));
			sf.setPayMoney(projectMoney); // 支出金额
			sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入金额
			sf.setRemark("");
			list.add(sf);
		} 

		BigDecimal preperiodsurplus1=projectMoney;
		BigDecimal	periodtimemoney1=new BigDecimal("0");
		if (payaccrualType.equals("oneTimePay") && isPreposePayAccrual == 1) {// 一次性前置付息
			periodtimemoney1=FundIntentListPronew.periodtimemoney(accrual,projectMoney,1).divide(new BigDecimal(1),2,BigDecimal.ROUND_HALF_UP);
			if(accrualType.equals("sameprincipalandInterest")){
				sf1 = calculslfundintent(FundIntentListPronew.ProjectLoadAccrual,            // 资金类型
						DateUtil.parseDate(date1, "yyyy-MM-dd"),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(accrualpre)        // 收入金额
						);
			    sf =calculslfundintent(FundIntentListPronew.ProjectLoadRoot,            // 资金类型
			    		DateUtil.parseDate(date1, "yyyy-MM-dd"),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						periodtimemoney1.subtract(FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual))        // 收入金额
						);
				
				sf2 = calculslfundintent(FundIntentListPronew.ProjectManagementConsulting,            // 资金类型
						DateUtil.parseDate(date1, "yyyy-MM-dd"),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(managementpre)          // 收入金额
						);
				sf3 = calculslfundintent(FundIntentListPronew.ProjectFinanceService,            // 资金类型
						DateUtil.parseDate(date1, "yyyy-MM-dd"),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(financepre)          // 收入金额
								);
				list.add(sf1);
				if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
				if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}
				list.add(sf);
				preperiodsurplus1=preperiodsurplus1.subtract(sf.getIncomeMoney());
			}
			 if(accrualType.equals("sameprincipal")){
					
		    	   BigDecimal sameprojectMoney = projectMoney.divide(new BigDecimal(1),100,BigDecimal.ROUND_HALF_UP);
		    	    sf1 = calculslfundintent(FundIntentListPronew.ProjectLoadAccrual,            // 资金类型
		    	    		DateUtil.parseDate(date1, "yyyy-MM-dd"),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(accrualpre)	);        // 收入金额
				    sf =calculslfundintent(FundIntentListPronew.ProjectLoadRoot,            // 资金类型
				    		DateUtil.parseDate(date1, "yyyy-MM-dd"),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								sameprojectMoney  	);      // 收入金额
							
				    sf2 = calculslfundintent(FundIntentListPronew.ProjectManagementConsulting,            // 资金类型
				    		DateUtil.parseDate(date1, "yyyy-MM-dd"),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(managementpre)          // 收入金额
							);
					sf3 = calculslfundintent(FundIntentListPronew.ProjectFinanceService,            // 资金类型
							DateUtil.parseDate(date1, "yyyy-MM-dd"),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(financepre)          // 收入金额
									);
					list.add(sf1);
					if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
					if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}
					list.add(sf);
					
					preperiodsurplus1=preperiodsurplus1.subtract(sf.getIncomeMoney());
					
				}
		       
		}
		if (payaccrualType.equals("oneTimePay") && isPreposePayAccrual == 0) {// 一次性利尾
			
			periodtimemoney1=FundIntentListPronew.periodtimemoney(accrual,projectMoney,1).divide(new BigDecimal(1),2,BigDecimal.ROUND_HALF_UP);
			if(accrualType.equals("sameprincipalandInterest")){
				sf1 = calculslfundintent(FundIntentListPronew.ProjectLoadAccrual,            // 资金类型
						DateUtil.parseDate(date2, "yyyy-MM-dd"),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(accrualpre)        // 收入金额
						);
			    sf =calculslfundintent(FundIntentListPronew.ProjectLoadRoot,            // 资金类型
			    		DateUtil.parseDate(date2, "yyyy-MM-dd"),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						periodtimemoney1.subtract(FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual))        // 收入金额
						);
				
				sf2 = calculslfundintent(FundIntentListPronew.ProjectManagementConsulting,            // 资金类型
						DateUtil.parseDate(date2, "yyyy-MM-dd"),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(managementpre)          // 收入金额
						);
				sf3 = calculslfundintent(FundIntentListPronew.ProjectFinanceService,            // 资金类型
						DateUtil.parseDate(date2, "yyyy-MM-dd"),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(financepre)          // 收入金额
								);
				list.add(sf1);
				if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
				if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}
				list.add(sf);
				preperiodsurplus1=preperiodsurplus1.subtract(sf.getIncomeMoney());
			}
			 if(accrualType.equals("sameprincipal")){
					
		    	   BigDecimal sameprojectMoney = projectMoney.divide(new BigDecimal(1),100,BigDecimal.ROUND_HALF_UP);
		    	    sf1 = calculslfundintent(FundIntentListPronew.ProjectLoadAccrual,            // 资金类型
		    	    		DateUtil.parseDate(date2, "yyyy-MM-dd"),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(accrualpre)	);        // 收入金额
				    sf =calculslfundintent(FundIntentListPronew.ProjectLoadRoot,            // 资金类型
				    		DateUtil.parseDate(date2, "yyyy-MM-dd"),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								sameprojectMoney  	);      // 收入金额
							
				    sf2 = calculslfundintent(FundIntentListPronew.ProjectManagementConsulting,            // 资金类型
				    		DateUtil.parseDate(date2, "yyyy-MM-dd"),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(managementpre)          // 收入金额
							);
					sf3 = calculslfundintent(FundIntentListPronew.ProjectFinanceService,            // 资金类型
							DateUtil.parseDate(date2, "yyyy-MM-dd"),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(financepre)          // 收入金额
									);
					list.add(sf1);
					if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
					if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}
					list.add(sf);
					
					preperiodsurplus1=preperiodsurplus1.subtract(sf.getIncomeMoney());
					
				}
			
			
			
			
		}
		if (payaccrualType.equals("dayPay")) {// 足日头/尾
			int days = DateUtil.getDaysBetweenDate(date1, date2);// 得到两日期相差天数
			
			if (days > 0) {
				days = days +1;
				periodtimemoney1=FundIntentListPronew.periodtimemoney(accrual,projectMoney,days).divide(new BigDecimal(1),2,BigDecimal.ROUND_HALF_UP);
				for (int i = 0; i < days; i++) {
					
					if (calcutype.equals("SmallLoan")) { // 贷款项目
						if(accrualType.equals("sameprincipalandInterest")){
								sf1 = calculslfundintent(FundIntentListPronew.ProjectLoadAccrual,            // 资金类型
										DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i),  //计划日期
										BigDecimal.valueOf(0),                                                // 支出金额
										FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(accrualpre)        // 收入金额
										);
							    sf =calculslfundintent(FundIntentListPronew.ProjectLoadRoot,            // 资金类型
										DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i),  //计划日期
										BigDecimal.valueOf(0),                                                // 支出金额
										periodtimemoney1.subtract(FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual))        // 收入金额
										);
								
								sf2 = calculslfundintent(FundIntentListPronew.ProjectManagementConsulting,            // 资金类型
										DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i),  //计划日期
										BigDecimal.valueOf(0),                                                // 支出金额
										FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(managementpre)          // 收入金额
										);
								sf3 = calculslfundintent(FundIntentListPronew.ProjectFinanceService,            // 资金类型
												DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i),  //计划日期
												BigDecimal.valueOf(0),                                                // 支出金额
												FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(financepre)          // 收入金额
												);
								list.add(sf1);
								if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
								if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}
								list.add(sf);
								preperiodsurplus1=preperiodsurplus1.subtract(sf.getIncomeMoney());
								
						
						}
				       if(accrualType.equals("sameprincipal")){
							
				    	   BigDecimal sameprojectMoney = projectMoney.divide(new BigDecimal(days),100,BigDecimal.ROUND_HALF_UP);
				    	    sf1 = calculslfundintent(FundIntentListPronew.ProjectLoadAccrual,            // 资金类型
									DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(accrualpre)	);        // 收入金额
						    sf =calculslfundintent(FundIntentListPronew.ProjectLoadRoot,            // 资金类型
										DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i),  //计划日期
										BigDecimal.valueOf(0),                                                // 支出金额
										sameprojectMoney  	);      // 收入金额
									
						    sf2 = calculslfundintent(FundIntentListPronew.ProjectManagementConsulting,            // 资金类型
									DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(managementpre)          // 收入金额
									);
							sf3 = calculslfundintent(FundIntentListPronew.ProjectFinanceService,            // 资金类型
											DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i),  //计划日期
											BigDecimal.valueOf(0),                                                // 支出金额
											FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(financepre)          // 收入金额
											);
							list.add(sf1);
							if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
							if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}
							list.add(sf);
							
							preperiodsurplus1=preperiodsurplus1.subtract(sf.getIncomeMoney());
							
						}
				       
					} 
					
					

				}
			}
		}
		
		if (payaccrualType.equals("monthPay") && isPreposePayAccrual == 1) {// 足月头

			// 贷款收息
			Map<Integer, Integer> map = DateUtil.getMonthAndDaysBetweenDate3(
					date1, date2);
			int months = map.get(1).intValue();
			
			if (months >=0) {
				periodtimemoney1=FundIntentListPronew.periodtimemoney(accrual,projectMoney,months).divide(new BigDecimal(1),2,BigDecimal.ROUND_HALF_UP);
				for (int i = 0; i < months; i++) {
					if (calcutype.equals("SmallLoan")) { // 贷款项目
						if(accrualType.equals("sameprincipalandInterest")){
							sf1 = calculslfundintent(FundIntentListPronew.ProjectLoadAccrual,            // 资金类型
									DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(accrualpre)        // 收入金额
									);
						    sf =calculslfundintent(FundIntentListPronew.ProjectLoadRoot,            // 资金类型
									DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									periodtimemoney1.subtract(FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual))        // 收入金额
									);
						    sf2 = calculslfundintent(FundIntentListPronew.ProjectManagementConsulting,            // 资金类型
									DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(managementpre)          // 收入金额
									);
							sf3 = calculslfundintent(FundIntentListPronew.ProjectFinanceService,            // 资金类型
											DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i),  //计划日期
											BigDecimal.valueOf(0),                                                // 支出金额
											FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(financepre)          // 收入金额
											);
							list.add(sf1);
							if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
							if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}
							list.add(sf);
							preperiodsurplus1=preperiodsurplus1.subtract(sf.getIncomeMoney());
							
							
					
					}
			       if(accrualType.equals("sameprincipal")){
						
			    	   BigDecimal sameprojectMoney = projectMoney.divide(new BigDecimal(months),100,BigDecimal.ROUND_HALF_UP);
			    	    sf1 = calculslfundintent(FundIntentListPronew.ProjectLoadAccrual,            // 资金类型
								DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(accrualpre) 	);        // 收入金额
					    sf =calculslfundintent(FundIntentListPronew.ProjectLoadRoot,            // 资金类型
									DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									sameprojectMoney  	);      // 收入金额
								
					    sf2 = calculslfundintent(FundIntentListPronew.ProjectManagementConsulting,            // 资金类型
								DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(managementpre)          // 收入金额
								);
						sf3 = calculslfundintent(FundIntentListPronew.ProjectFinanceService,            // 资金类型
										DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i),  //计划日期
										BigDecimal.valueOf(0),                                                // 支出金额
										FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(financepre)          // 收入金额
										);
						list.add(sf1);
						if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
						if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}
						list.add(sf);
						
						preperiodsurplus1=preperiodsurplus1.subtract(sf.getIncomeMoney());
						
					}

					} 

				
					
				}}
			
		}
		if (payaccrualType.equals("monthPay") && isPreposePayAccrual == 0) {// 足月尾
			Map<Integer, Integer> map = DateUtil.getMonthAndDaysBetweenDate2(
					date1, date2);
			int months = map.get(1).intValue();
			if (months >= 0) {
				periodtimemoney1=FundIntentListPronew.periodtimemoney(accrual,projectMoney,months).divide(new BigDecimal(1),2,BigDecimal.ROUND_HALF_UP);
				for (int i = 0; i < months; i++) {
					
					if (calcutype.equals("SmallLoan")) { // 贷款项目
						if(accrualType.equals("sameprincipalandInterest")){
							sf1 = calculslfundintent(FundIntentListPronew.ProjectLoadAccrual,            // 资金类型
									DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i + 1,-1),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(accrualpre)         // 收入金额
									);
							
						    sf =calculslfundintent(FundIntentListPronew.ProjectLoadRoot,            // 资金类型
						    		DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i + 1,-1),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									periodtimemoney1.subtract(FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual))        // 收入金额
									);
						    sf2 = calculslfundintent(FundIntentListPronew.ProjectManagementConsulting,            // 资金类型
						    		DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i + 1,-1),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(managementpre)          // 收入金额
									);
							sf3 = calculslfundintent(FundIntentListPronew.ProjectFinanceService,            // 资金类型
									DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i + 1,-1),  //计划日期
											BigDecimal.valueOf(0),                                                // 支出金额
											FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(financepre)          // 收入金额
											);
							
						  if(i == months-1){
							  sf.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
							  sf1.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
							  sf2.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
							  sf3.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
						  }	
						
							list.add(sf1);
							if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
							if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}
							list.add(sf);
						  
						  preperiodsurplus1=preperiodsurplus1.subtract(sf.getIncomeMoney());
					}
			       if(accrualType.equals("sameprincipal")){
						
			    	   BigDecimal sameprojectMoney = projectMoney.divide(new BigDecimal(months),100,BigDecimal.ROUND_HALF_UP);
			    	    sf1 = calculslfundintent(FundIntentListPronew.ProjectLoadAccrual,            // 资金类型
			    	    		DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i + 1,-1),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(accrualpre) 	);        // 收入金额
					    sf =calculslfundintent(FundIntentListPronew.ProjectLoadRoot,            // 资金类型
					    		DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i + 1,-1),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									sameprojectMoney  	);      // 收入金额
					    sf2 = calculslfundintent(FundIntentListPronew.ProjectManagementConsulting,            // 资金类型
					    		DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i + 1,-1),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(managementpre)          // 收入金额
								);
						sf3 = calculslfundintent(FundIntentListPronew.ProjectFinanceService,            // 资金类型
								DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i + 1,-1), //计划日期
										BigDecimal.valueOf(0),                                                // 支出金额
										FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(financepre)          // 收入金额
										);
								
					   if(i == months-1){
							  sf.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
							  sf1.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
							  sf2.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
							  sf3.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
						  }	
						  
						  list.add(sf1);
						  if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
						if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}
						list.add(sf);
						
						preperiodsurplus1=preperiodsurplus1.subtract(sf.getIncomeMoney());
						
					}

					} 
				}

			
			}
			
			
		}
		if (payaccrualType.equals("seasonPay") && isPreposePayAccrual == 1) {// 足季头
			// 贷款收息
			Map<Integer, Integer> map = DateUtil.getMonthAndDaysBetweenDate(
					date1, date2);
			int months = map.get(1).intValue();
			if (months > 0) {
				periodtimemoney1=FundIntentListPronew.periodtimemoney(accrual,projectMoney,months/3).divide(new BigDecimal(1),2,BigDecimal.ROUND_HALF_UP);
				for (int i = 0; i < months / 3; i++) {
					if (calcutype.equals("SmallLoan")) { // 贷款项目
						if(accrualType.equals("sameprincipalandInterest")){
							sf1 = calculslfundintent(FundIntentListPronew.ProjectLoadAccrual,            // 资金类型
									DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i * 3),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(accrualpre)        // 收入金额
									);
						    sf =calculslfundintent(FundIntentListPronew.ProjectLoadRoot,            // 资金类型
									DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i * 3),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									periodtimemoney1.subtract(FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual))        // 收入金额
									);
						    sf2 = calculslfundintent(FundIntentListPronew.ProjectManagementConsulting,            // 资金类型
						    		DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i * 3),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(managementpre)          // 收入金额
									);
							sf3 = calculslfundintent(FundIntentListPronew.ProjectFinanceService,            // 资金类型
									DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i * 3), //计划日期
											BigDecimal.valueOf(0),                                                // 支出金额
											FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(financepre)          // 收入金额
											);
							
							  list.add(sf1);
							  if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
							  if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}
							  list.add(sf);
							preperiodsurplus1=preperiodsurplus1.subtract(sf.getIncomeMoney());
							
					
					}
			       if(accrualType.equals("sameprincipal")){
						
			    	   BigDecimal sameprojectMoney = projectMoney.divide(new BigDecimal(months/3),100,BigDecimal.ROUND_HALF_UP);
			    	    sf1 = calculslfundintent(FundIntentListPronew.ProjectLoadAccrual,            // 资金类型
								DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i * 3),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(accrualpre)	);        // 收入金额
					    sf =calculslfundintent(FundIntentListPronew.ProjectLoadRoot,            // 资金类型
									DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i * 3),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									sameprojectMoney  	);      // 收入金额
								
					    sf2 = calculslfundintent(FundIntentListPronew.ProjectManagementConsulting,            // 资金类型
					    		DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i * 3),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(managementpre)          // 收入金额
								);
						sf3 = calculslfundintent(FundIntentListPronew.ProjectFinanceService,            // 资金类型
								DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i * 3), //计划日期
										BigDecimal.valueOf(0),                                                // 支出金额
										FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(financepre)          // 收入金额
										);
						
						  list.add(sf1);
						  if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
						  if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}
						  list.add(sf);
						preperiodsurplus1=preperiodsurplus1.subtract(sf.getIncomeMoney());
						
					}

					}
				}
			}
			
			
			
		}
		if (payaccrualType.equals("seasonPay") && isPreposePayAccrual == 0) {// 足季尾
	
			// 贷款收息
			Map<Integer, Integer> map = DateUtil.getMonthAndDaysBetweenDate(
					date1, date2);
			int months = map.get(1).intValue();
			if (months > 0) {
				periodtimemoney1=FundIntentListPronew.periodtimemoney(accrual,projectMoney,months/3).divide(new BigDecimal(1),2,BigDecimal.ROUND_HALF_UP);
				for (int i = 0; i < months / 3; i++) {
					if (calcutype.equals("SmallLoan")) { // 贷款项目
						if(accrualType.equals("sameprincipalandInterest")){
							sf1 = calculslfundintent(FundIntentListPronew.ProjectLoadAccrual,            // 资金类型
									DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i + 1) * 3),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(accrualpre)         // 收入金额
									);
						    sf =calculslfundintent(FundIntentListPronew.ProjectLoadRoot,            // 资金类型
									DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i + 1) * 3),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									periodtimemoney1.subtract(FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual))        // 收入金额
									);
							
						    sf2 = calculslfundintent(FundIntentListPronew.ProjectManagementConsulting,            // 资金类型
						    		DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"),(i + 1) * 3),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(managementpre)          // 收入金额
									);
							sf3 = calculslfundintent(FundIntentListPronew.ProjectFinanceService,            // 资金类型
									DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i + 1) * 3), //计划日期
											BigDecimal.valueOf(0),                                                // 支出金额
											FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(financepre)          // 收入金额
											);
							if(i == months/3-1){
								  sf.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
								  sf1.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
								  sf2.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
								  sf3.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
							  }	
							  list.add(sf1);
							  if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
							  if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}
							  list.add(sf);
							preperiodsurplus1=preperiodsurplus1.subtract(sf.getIncomeMoney());
							
					
					}
			       if(accrualType.equals("sameprincipal")){
						
			    	   BigDecimal sameprojectMoney = projectMoney.divide(new BigDecimal(months/3),100,BigDecimal.ROUND_HALF_UP);
			    	    sf1 = calculslfundintent(FundIntentListPronew.ProjectLoadAccrual,            // 资金类型
								DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i + 1) * 3),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(accrualpre)	);        // 收入金额
					    sf =calculslfundintent(FundIntentListPronew.ProjectLoadRoot,            // 资金类型
									DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i + 1) * 3),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									sameprojectMoney  	);      // 收入金额
								
					    
					    sf2 = calculslfundintent(FundIntentListPronew.ProjectManagementConsulting,            // 资金类型
					    		DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"),(i + 1) * 3),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(managementpre)          // 收入金额
								);
						sf3 = calculslfundintent(FundIntentListPronew.ProjectFinanceService,            // 资金类型
								DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i + 1) * 3), //计划日期
										BigDecimal.valueOf(0),                                                // 支出金额
										FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(financepre)          // 收入金额
										);
						if(i == months/3-1){
							  sf.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
							  sf1.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
							  sf2.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
							  sf3.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
						  }	
						  list.add(sf1);
						  if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
						  if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}
						  list.add(sf);
						preperiodsurplus1=preperiodsurplus1.subtract(sf.getIncomeMoney());
						
					}
					} 
				}
			
			
			}
			
		}
		if (payaccrualType.equals("halfYearPay") && isPreposePayAccrual == 1) {// 足半年头

			// 贷款收息
			Map<Integer, Integer> map = DateUtil.getMonthAndDaysBetweenDate(
					date1, date2);
			int months = map.get(1).intValue();
			if (months > 0) {
				periodtimemoney1=FundIntentListPronew.periodtimemoney(accrual,projectMoney,months/6).divide(new BigDecimal(1),2,BigDecimal.ROUND_HALF_UP);
				for (int i = 0; i < months / 6; i++) {
					if (calcutype.equals("SmallLoan")) { // 贷款项目
						if(accrualType.equals("sameprincipalandInterest")){
							sf1 = calculslfundintent(FundIntentListPronew.ProjectLoadAccrual,            // 资金类型
									DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i * 6),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(accrualpre)        // 收入金额
									);
						    sf =calculslfundintent(FundIntentListPronew.ProjectLoadRoot,            // 资金类型
									DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i * 6),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									periodtimemoney1.subtract(FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual))        // 收入金额
									);
						    
						    sf2 = calculslfundintent(FundIntentListPronew.ProjectManagementConsulting,            // 资金类型
						    		DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"),i * 6),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(managementpre)          // 收入金额
									);
							sf3 = calculslfundintent(FundIntentListPronew.ProjectFinanceService,            // 资金类型
									DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i * 6), //计划日期
											BigDecimal.valueOf(0),                                                // 支出金额
											FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(financepre)          // 收入金额
											);
						
							  list.add(sf1);
							  if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
							  if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}
							  list.add(sf);
							
							preperiodsurplus1=preperiodsurplus1.subtract(sf.getIncomeMoney());
							
					
					}
			       if(accrualType.equals("sameprincipal")){
						
			    	   BigDecimal sameprojectMoney = projectMoney.divide(new BigDecimal(months/6),100,BigDecimal.ROUND_HALF_UP);
			    	    sf1 = calculslfundintent(FundIntentListPronew.ProjectLoadAccrual,            // 资金类型
								DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i * 6),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(accrualpre)  	);        // 收入金额
					    sf =calculslfundintent(FundIntentListPronew.ProjectLoadRoot,            // 资金类型
									DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i * 6),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									sameprojectMoney  	);      // 收入金额
								
					    sf2 = calculslfundintent(FundIntentListPronew.ProjectManagementConsulting,            // 资金类型
					    		DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"),i * 6),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(managementpre)          // 收入金额
								);
						sf3 = calculslfundintent(FundIntentListPronew.ProjectFinanceService,            // 资金类型
								DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i * 6), //计划日期
										BigDecimal.valueOf(0),                                                // 支出金额
										FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(financepre)          // 收入金额
										);
					
						  list.add(sf1);
						  if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
						  if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}
						  list.add(sf);
						
						preperiodsurplus1=preperiodsurplus1.subtract(sf.getIncomeMoney());
						
					}

					} 
				}
			}
		
			
		}
		if (payaccrualType.equals("halfYearPay") && isPreposePayAccrual == 0) {// 足半年尾

			// 贷款收息
			Map<Integer, Integer> map = DateUtil.getMonthAndDaysBetweenDate(
					date1, date2);
			int months = map.get(1).intValue();
			if (months > 0) {
				periodtimemoney1=FundIntentListPronew.periodtimemoney(accrual,projectMoney,months/6).divide(new BigDecimal(1),2,BigDecimal.ROUND_HALF_UP);
				for (int i = 0; i < months / 6; i++) {
					
					if (calcutype.equals("SmallLoan")) { // 贷款项目
						if(accrualType.equals("sameprincipalandInterest")){
							sf1 = calculslfundintent(FundIntentListPronew.ProjectLoadAccrual,            // 资金类型
									DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i + 1) * 6),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(accrualpre)         // 收入金额
									);
						    sf =calculslfundintent(FundIntentListPronew.ProjectLoadRoot,            // 资金类型
									DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i + 1) * 6),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									periodtimemoney1.subtract(FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual))        // 收入金额
									);
							  sf2 = calculslfundintent(FundIntentListPronew.ProjectManagementConsulting,            // 资金类型
							    		DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"),(i + 1) * 6),  //计划日期
										BigDecimal.valueOf(0),                                                // 支出金额
										FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(managementpre)          // 收入金额
										);
								sf3 = calculslfundintent(FundIntentListPronew.ProjectFinanceService,            // 资金类型
										DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"),(i + 1) * 6),  //计划日期
												BigDecimal.valueOf(0),                                                // 支出金额
												FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(financepre)          // 收入金额
												);
								if(i == months/6-1){
									  sf.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
									  sf1.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
									  sf2.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
									  sf3.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
								  }	
								 list.add(sf);
								  list.add(sf1);
								  if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
								  if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}
							preperiodsurplus1=preperiodsurplus1.subtract(sf.getIncomeMoney());
							
					
					}
			       if(accrualType.equals("sameprincipal")){
						
			    	   BigDecimal sameprojectMoney = projectMoney.divide(new BigDecimal(months/6),100,BigDecimal.ROUND_HALF_UP);
			    	    sf1 = calculslfundintent(FundIntentListPronew.ProjectLoadAccrual,            // 资金类型
								DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i + 1) * 6),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(accrualpre)  	);        // 收入金额
					    sf =calculslfundintent(FundIntentListPronew.ProjectLoadRoot,            // 资金类型
									DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i + 1) * 6),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									sameprojectMoney  	);      // 收入金额
								
					    sf2 = calculslfundintent(FundIntentListPronew.ProjectManagementConsulting,            // 资金类型
					    		DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"),(i + 1) * 6),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(managementpre)          // 收入金额
								);
						sf3 = calculslfundintent(FundIntentListPronew.ProjectFinanceService,            // 资金类型
								DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"),(i + 1) * 6),  //计划日期
										BigDecimal.valueOf(0),                                                // 支出金额
										FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(financepre)          // 收入金额
										);
						if(i == months/6-1){
							  sf.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
							  sf1.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
							  sf2.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
							  sf3.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
						  }	
						  list.add(sf1);
						  if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
						  if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}
						preperiodsurplus1=preperiodsurplus1.subtract(sf.getIncomeMoney());
						 list.add(sf);
						
					}

					} 
				}
			}
			
			
		}
		if (payaccrualType.equals("yearPay") && isPreposePayAccrual == 1) {// 足年头

			// 贷款收息
			Map<Integer, Integer> map = DateUtil.getMonthAndDaysBetweenDate(
					date1, date2);
			int months = map.get(1).intValue();
			if (months > 0) {
				periodtimemoney1=FundIntentListPronew.periodtimemoney(accrual,projectMoney,months/12).divide(new BigDecimal(1),2,BigDecimal.ROUND_HALF_UP);
				for (int i = 0; i < months / 12; i++) {
					if (calcutype.equals("SmallLoan")) { // 贷款项目
				   if(accrualType.equals("sameprincipalandInterest")){
						sf1 = calculslfundintent(FundIntentListPronew.ProjectLoadAccrual,            // 资金类型
								DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i * 12),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(accrualpre)        // 收入金额
								);
					    sf =calculslfundintent(FundIntentListPronew.ProjectLoadRoot,            // 资金类型
								DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i * 12),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								periodtimemoney1.subtract(FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual))        // 收入金额
								);
					    sf2 = calculslfundintent(FundIntentListPronew.ProjectManagementConsulting,            // 资金类型
					    		DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i * 12),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(managementpre)          // 收入金额
								);
						sf3 = calculslfundintent(FundIntentListPronew.ProjectFinanceService,            // 资金类型
								DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i * 12),  //计划日期
										BigDecimal.valueOf(0),                                                // 支出金额
										FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(financepre)          // 收入金额
										);
						  list.add(sf1);
						  if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
						  if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}
						  list.add(sf);
						preperiodsurplus1=preperiodsurplus1.subtract(sf.getIncomeMoney());
						
				
				}
		       if(accrualType.equals("sameprincipal")){
					
		    	   BigDecimal sameprojectMoney = projectMoney.divide(new BigDecimal(months/12),100,BigDecimal.ROUND_HALF_UP);
		    	    sf1 = calculslfundintent(FundIntentListPronew.ProjectLoadAccrual,            // 资金类型
							DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i * 12),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(accrualpre)	);        // 收入金额
				    sf =calculslfundintent(FundIntentListPronew.ProjectLoadRoot,            // 资金类型
								DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i * 12),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								sameprojectMoney  	);      // 收入金额
							
				    sf2 = calculslfundintent(FundIntentListPronew.ProjectManagementConsulting,            // 资金类型
				    		DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i * 12),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(managementpre)          // 收入金额
							);
					sf3 = calculslfundintent(FundIntentListPronew.ProjectFinanceService,            // 资金类型
							DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i * 12),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(financepre)          // 收入金额
									);
					  list.add(sf1);
					  if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
					  if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}
					  list.add(sf);
					preperiodsurplus1=preperiodsurplus1.subtract(sf.getIncomeMoney());
					
				}

					}
				}
			}
			
			
		}
		if (payaccrualType.equals("yearPay") && isPreposePayAccrual == 0) {// 足年尾
		
			Map<Integer, Integer> map = DateUtil.getMonthAndDaysBetweenDate(
					date1, date2);
			int months = map.get(1).intValue();
			if (months > 0) {
				periodtimemoney1=FundIntentListPronew.periodtimemoney(accrual,projectMoney,months/12).divide(new BigDecimal(1),2,BigDecimal.ROUND_HALF_UP);
				for (int i = 0; i < months / 12; i++) {
					if (calcutype.equals("SmallLoan")) { // 贷款项目
						if(accrualType.equals("sameprincipalandInterest")){
							sf1 = calculslfundintent(FundIntentListPronew.ProjectLoadAccrual,            // 资金类型
									DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i + 1) * 12),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(accrualpre)        // 收入金额
									);
						    sf =calculslfundintent(FundIntentListPronew.ProjectLoadRoot,            // 资金类型
									DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i + 1) * 12),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									periodtimemoney1.subtract(FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual))        // 收入金额
									);
						
							  sf2 = calculslfundintent(FundIntentListPronew.ProjectManagementConsulting,            // 资金类型
							    		DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i + 1) * 12),  //计划日期
										BigDecimal.valueOf(0),                                                // 支出金额
										FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(managementpre)          // 收入金额
										);
								sf3 = calculslfundintent(FundIntentListPronew.ProjectFinanceService,            // 资金类型
										DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i + 1) * 12),  //计划日期
												BigDecimal.valueOf(0),                                                // 支出金额
												FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(financepre)          // 收入金额
												);
								if(i == months/12-1){
									  sf.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
									  sf1.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
									  sf2.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
									  sf3.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
								  }	
								  list.add(sf1);
								  if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
								  if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}
								  list.add(sf);
							preperiodsurplus1=preperiodsurplus1.subtract(sf.getIncomeMoney());
							
					
					}
			       if(accrualType.equals("sameprincipal")){
						
			    	   BigDecimal sameprojectMoney = projectMoney.divide(new BigDecimal(months/12),100,BigDecimal.ROUND_HALF_UP);
			    	    sf1 = calculslfundintent(FundIntentListPronew.ProjectLoadAccrual,            // 资金类型
								DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i + 1) * 12),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(accrualpre)	);        // 收入金额
					    sf =calculslfundintent(FundIntentListPronew.ProjectLoadRoot,            // 资金类型
									DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i + 1) * 12),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									sameprojectMoney  	);      // 收入金额
								
					    sf2 = calculslfundintent(FundIntentListPronew.ProjectManagementConsulting,            // 资金类型
					    		DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i + 1) * 12),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(managementpre)          // 收入金额
								);
						sf3 = calculslfundintent(FundIntentListPronew.ProjectFinanceService,            // 资金类型
								DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i + 1) * 12),  //计划日期
										BigDecimal.valueOf(0),                                                // 支出金额
										FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(financepre)          // 收入金额
										);
						if(i == months/12-1){
							  sf.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
							  sf1.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
							  sf2.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
							  sf3.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
						  }	
						  list.add(sf1);
						  if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
						  if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}
						  list.add(sf);
						preperiodsurplus1=preperiodsurplus1.subtract(sf.getIncomeMoney());
						
					}


					} 
				}
			}
		
			
		}
		if (payaccrualType.equals("calendarMonthPay") && isPreposePayAccrual == 1) {// 日历月头
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
			Date d1 = null;
			try {
				d1 = sd.parse(date1);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Date d2 = null;
			try {
				d2 = sd.parse(date2);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Calendar calendar = Calendar.getInstance();   
	        calendar.setTime(d1);
	        Calendar calendar2 = Calendar.getInstance();   
	        calendar2.setTime(d2);
	        int y1_1 = calendar.get(Calendar.YEAR);
	        int y2_2 = calendar2.get(Calendar.YEAR);
	

			int y1 = d1.getYear();
			int y2 = d2.getYear();
			int dm1 = d1.getMonth();// 起始日期月份
			int dm2 = d2.getMonth();// 结束日期月份
			int dd1 = d1.getDate(); // 起始日期天
			int dd2 = d2.getDate(); // 结束日期天
			if (d2.compareTo(d1) >= 0) {

				int months = d2.getMonth() - d1.getMonth() + (y2 - y1) * 12;
				periodtimemoney1=FundIntentListPronew.periodtimemoney(accrual,projectMoney,months+1).divide(new BigDecimal(1),2,BigDecimal.ROUND_HALF_UP);
				if(accrualType.equals("sameprincipalandInterest")){
					sf1 = calculslfundintent(FundIntentListPronew.ProjectLoadAccrual,            // 资金类型
							DateUtil.parseDate(date1, "yyyy-MM-dd"),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(accrualpre)         // 收入金额
							);
				    sf =calculslfundintent(FundIntentListPronew.ProjectLoadRoot,            // 资金类型
				    		DateUtil.parseDate(date1, "yyyy-MM-dd"),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							periodtimemoney1.subtract(FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual))        // 收入金额
							);
				    sf2 = calculslfundintent(FundIntentListPronew.ProjectManagementConsulting,            // 资金类型
				    		DateUtil.parseDate(date1, "yyyy-MM-dd"),   //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(managementpre)          // 收入金额
							);
					sf3 = calculslfundintent(FundIntentListPronew.ProjectFinanceService,            // 资金类型
							DateUtil.parseDate(date1, "yyyy-MM-dd"),   //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(financepre)          // 收入金额
									);
					
					list.add(sf1);
					if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
					if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}
					list.add(sf);
					preperiodsurplus1=preperiodsurplus1.subtract(sf.getIncomeMoney());
					
			
			}
	       if(accrualType.equals("sameprincipal")){
				
	    	
	    	    sf1 = calculslfundintent(FundIntentListPronew.ProjectLoadAccrual,            // 资金类型
	    	    		DateUtil.parseDate(date1, "yyyy-MM-dd"),  //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(accrualpre)	);        // 收入金额
			    sf =calculslfundintent(FundIntentListPronew.ProjectLoadRoot,            // 资金类型
			    		DateUtil.parseDate(date1, "yyyy-MM-dd"),  //计划日期
							BigDecimal.valueOf(0),                                                // 支出金额
							 projectMoney.divide(new BigDecimal(months+1),100,BigDecimal.ROUND_HALF_UP)  	);      // 收入金额
			    sf2 = calculslfundintent(FundIntentListPronew.ProjectManagementConsulting,            // 资金类型
			    		DateUtil.parseDate(date1, "yyyy-MM-dd"),   //计划日期
						BigDecimal.valueOf(0),                                                // 支出金额
						FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(managementpre)          // 收入金额
						);
				sf3 = calculslfundintent(FundIntentListPronew.ProjectFinanceService,            // 资金类型
						DateUtil.parseDate(date1, "yyyy-MM-dd"),   //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(financepre)          // 收入金额
								);
				 
				list.add(sf1);
				if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
				if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}
				list.add(sf);		
				preperiodsurplus1=preperiodsurplus1.subtract(sf.getIncomeMoney());
				
			}
				
				
				for (int i = 0; i < months; i++) {
					Date d11 = new Date(y1, dm1 + i + 1, 01);
					if (d11.compareTo(d2) < 0) {
							
							Map<Integer, Integer> map_ = DateUtil.getMonthAndDaysBetweenDate(
									date1, date2);
							int months_ = map_.get(1).intValue();
							int mdays = 0;
							if(dm1 + 1 < 10){
								mdays = DateUtil.getDaysBetweenDate(date1,String.valueOf(y2_2)+ "-0"+String.valueOf(dm1 + 2) + "-01");
							}else{
								mdays = DateUtil.getDaysBetweenDate(date1,String.valueOf(y2_2)+ "-0"+String.valueOf(dm1 + 2) + "-01");
							}
							
							periodtimemoney1=FundIntentListPronew.periodtimemoney(accrual,projectMoney,months+1).divide(new BigDecimal(1),2,BigDecimal.ROUND_HALF_UP);
							if (calcutype.equals("SmallLoan")) { // 贷款项目
								if(accrualType.equals("sameprincipalandInterest")){
									sf1 = calculslfundintent(FundIntentListPronew.ProjectLoadAccrual,            // 资金类型
											d11,  //计划日期
											BigDecimal.valueOf(0),                                                // 支出金额
											FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(accrualpre)         // 收入金额
											);
								    sf =calculslfundintent(FundIntentListPronew.ProjectLoadRoot,            // 资金类型
								    		d11,  //计划日期
											BigDecimal.valueOf(0),                                                // 支出金额
											periodtimemoney1.subtract(FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual))        // 收入金额
											);
								    sf2 = calculslfundintent(FundIntentListPronew.ProjectManagementConsulting,            // 资金类型
								    		d11,   //计划日期
											BigDecimal.valueOf(0),                                                // 支出金额
											FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(managementpre)          // 收入金额
											);
									sf3 = calculslfundintent(FundIntentListPronew.ProjectFinanceService,            // 资金类型
											        d11,   //计划日期
													BigDecimal.valueOf(0),                                                // 支出金额
													FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(financepre)          // 收入金额
													);
									 if(i == months-1){
										  sf.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
										  sf1.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
										  sf2.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
										  sf3.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
									  }
									list.add(sf1);
									if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
									if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}
									list.add(sf);
									preperiodsurplus1=preperiodsurplus1.subtract(sf.getIncomeMoney());
									
							
							}
					       if(accrualType.equals("sameprincipal")){
								
					    	   BigDecimal sameprojectMoney = projectMoney.divide(new BigDecimal(months+1),100,BigDecimal.ROUND_HALF_UP);
					    	    sf1 = calculslfundintent(FundIntentListPronew.ProjectLoadAccrual,            // 资金类型
					    	    		d11,  //计划日期
										BigDecimal.valueOf(0),                                                // 支出金额
										FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(accrualpre)	);        // 收入金额
							    sf =calculslfundintent(FundIntentListPronew.ProjectLoadRoot,            // 资金类型
							    		d11,  //计划日期
											BigDecimal.valueOf(0),                                                // 支出金额
											sameprojectMoney  	);      // 收入金额
							    sf2 = calculslfundintent(FundIntentListPronew.ProjectManagementConsulting,            // 资金类型
							    		d11,   //计划日期
										BigDecimal.valueOf(0),                                                // 支出金额
										FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(managementpre)          // 收入金额
										);
								sf3 = calculslfundintent(FundIntentListPronew.ProjectFinanceService,            // 资金类型
										d11,   //计划日期
												BigDecimal.valueOf(0),                                                // 支出金额
												FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(financepre)          // 收入金额
												);
								 if(i == months-1){
									  sf.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
									  sf1.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
									  sf2.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
									  sf3.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
								  }
								list.add(sf1);
								if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
								if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}
								list.add(sf);		
								preperiodsurplus1=preperiodsurplus1.subtract(sf.getIncomeMoney());
								
							}
							
						
							}}
					
					
				}

			

			
			
			
			}
			

		}
		if (payaccrualType.equals("calendarMonthPay") && isPreposePayAccrual == 0) {// 日历月尾

			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
			Date d1 = null;
			try {
				d1 = sd.parse(date1);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Date d2 = null;
			try {
				d2 = sd.parse(date2);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Calendar calendar = Calendar.getInstance();   
	        calendar.setTime(d1);
	        Calendar calendar2 = Calendar.getInstance();   
	        calendar2.setTime(d2);
	        int y1_1 = calendar.get(Calendar.YEAR);
	        int y2_2 = calendar2.get(Calendar.YEAR);
	

			int y1 = d1.getYear();
			int y2 = d2.getYear();
			int dm1 = d1.getMonth();// 起始日期月份
			int dm2 = d2.getMonth();// 结束日期月份
			int dd1 = d1.getDate(); // 起始日期天
			int dd2 = d2.getDate(); // 结束日期天
			if (d2.compareTo(d1) >= 0) {

				int months = d2.getMonth() - d1.getMonth() + (y2 - y1) * 12;
//				int months = calendar2.get(Calendar.MONTH) - calendar.get(Calendar.MONTH) + (y2 - y1) * 12;
				for (int i = 0; i < months; i++) {
					Date d11 = new Date(y1, dm1 + i + 1, 01);
					if (d11.compareTo(d2) < 0) {
							
							Map<Integer, Integer> map_ = DateUtil.getMonthAndDaysBetweenDate(
									date1, date2);
							int months_ = map_.get(1).intValue();
							int mdays = 0;
							if(dm1 + 1 < 10){
								mdays = DateUtil.getDaysBetweenDate(date1,String.valueOf(y2_2)+ "-0"+String.valueOf(dm1 + 2) + "-01");
							}else{
								mdays = DateUtil.getDaysBetweenDate(date1,String.valueOf(y2_2)+ "-0"+String.valueOf(dm1 + 2) + "-01");
							}
							
							periodtimemoney1=FundIntentListPronew.periodtimemoney(accrual,projectMoney,months).divide(new BigDecimal(1),2,BigDecimal.ROUND_HALF_UP);
							if (calcutype.equals("SmallLoan")) { // 贷款项目
								if(accrualType.equals("sameprincipalandInterest")){
									sf1 = calculslfundintent(FundIntentListPronew.ProjectLoadAccrual,            // 资金类型
											d11,  //计划日期
											BigDecimal.valueOf(0),                                                // 支出金额
											FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(accrualpre)         // 收入金额
											);
								    sf =calculslfundintent(FundIntentListPronew.ProjectLoadRoot,            // 资金类型
								    		d11,  //计划日期
											BigDecimal.valueOf(0),                                                // 支出金额
											periodtimemoney1.subtract(FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual))        // 收入金额
											);
								    sf2 = calculslfundintent(FundIntentListPronew.ProjectManagementConsulting,            // 资金类型
								    		d11,   //计划日期
											BigDecimal.valueOf(0),                                                // 支出金额
											FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(managementpre)          // 收入金额
											);
									sf3 = calculslfundintent(FundIntentListPronew.ProjectFinanceService,            // 资金类型
											        d11,   //计划日期
													BigDecimal.valueOf(0),                                                // 支出金额
													FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(financepre)          // 收入金额
													);
									 if(i == months-1){
										  sf.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
										  sf1.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
										  sf2.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
										  sf3.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
									  }
									list.add(sf1);
									if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
									if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}
									list.add(sf);
									preperiodsurplus1=preperiodsurplus1.subtract(sf.getIncomeMoney());
									
							
							}
					       if(accrualType.equals("sameprincipal")){
								
					    	   BigDecimal sameprojectMoney = projectMoney.divide(new BigDecimal(months),100,BigDecimal.ROUND_HALF_UP);
					    	    sf1 = calculslfundintent(FundIntentListPronew.ProjectLoadAccrual,            // 资金类型
					    	    		d11,  //计划日期
										BigDecimal.valueOf(0),                                                // 支出金额
										FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(accrualpre)	);        // 收入金额
							    sf =calculslfundintent(FundIntentListPronew.ProjectLoadRoot,            // 资金类型
							    		d11,  //计划日期
											BigDecimal.valueOf(0),                                                // 支出金额
											sameprojectMoney  	);      // 收入金额
							    sf2 = calculslfundintent(FundIntentListPronew.ProjectManagementConsulting,            // 资金类型
							    		d11,   //计划日期
										BigDecimal.valueOf(0),                                                // 支出金额
										FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(managementpre)          // 收入金额
										);
								sf3 = calculslfundintent(FundIntentListPronew.ProjectFinanceService,            // 资金类型
										d11,   //计划日期
												BigDecimal.valueOf(0),                                                // 支出金额
												FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(financepre)          // 收入金额
												);
								 if(i == months-1){
									  sf.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
									  sf1.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
									  sf2.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
									  sf3.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
								  }
								list.add(sf1);
								if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
								if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}
								list.add(sf);		
								preperiodsurplus1=preperiodsurplus1.subtract(sf.getIncomeMoney());
								
							}
							
						
						
				
							
						
						
							}}
				}

			}

		
			
		}
		
		

		
			int days=0;// 得到两日期相差天数
			int intervalday=0;
		  if (payaccrualType.equals("ownerPay") && isPreposePayAccrual == 0) {// 
			  days = DateUtil.getDaysBetweenDate(date1, date2)+1;
			  intervalday=days/period;
				if (period > 0) {
					periodtimemoney1=FundIntentListPronew.periodtimemoney(accrual,projectMoney,period).divide(new BigDecimal(1),2,BigDecimal.ROUND_HALF_UP);
					for (int i = 0; i < period; i++) {
							// 贷款收息
						if(accrualType.equals("sameprincipalandInterest")){
							sf1 = calculslfundintent(FundIntentListPronew.ProjectLoadAccrual,            // 资金类型
									DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"),(i+1)*intervalday-1),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(accrualpre)         // 收入金额
									);
						    sf =calculslfundintent(FundIntentListPronew.ProjectLoadRoot,            // 资金类型
									DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i+1)*intervalday-1),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									periodtimemoney1.subtract(FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual))        // 收入金额
									);
						    sf2 = calculslfundintent(FundIntentListPronew.ProjectManagementConsulting,            // 资金类型
									DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i+1)*intervalday-1),   //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(managementpre)          // 收入金额
									);
							sf3 = calculslfundintent(FundIntentListPronew.ProjectFinanceService,            // 资金类型
											DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i+1)*intervalday-1),   //计划日期
											BigDecimal.valueOf(0),                                                // 支出金额
											FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(financepre)          // 收入金额
											);
							 if(i == period-1){
								  sf.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
								  sf1.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
								  sf2.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
								  sf3.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
							  }
							list.add(sf1);
							if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
							if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}
							list.add(sf);
							preperiodsurplus1=preperiodsurplus1.subtract(sf.getIncomeMoney());
							
					
					}
			       if(accrualType.equals("sameprincipal")){
						
			    	   BigDecimal sameprojectMoney = projectMoney.divide(new BigDecimal(period),100,BigDecimal.ROUND_HALF_UP);
			    	    sf1 = calculslfundintent(FundIntentListPronew.ProjectLoadAccrual,            // 资金类型
								DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i+1)*intervalday-1),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(accrualpre)	);        // 收入金额
					    sf =calculslfundintent(FundIntentListPronew.ProjectLoadRoot,            // 资金类型
									DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i+1)*intervalday-1),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									sameprojectMoney  	);      // 收入金额
					    sf2 = calculslfundintent(FundIntentListPronew.ProjectManagementConsulting,            // 资金类型
								DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i+1)*intervalday-1),   //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(managementpre)          // 收入金额
								);
						sf3 = calculslfundintent(FundIntentListPronew.ProjectFinanceService,            // 资金类型
										DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i+1)*intervalday-1),   //计划日期
										BigDecimal.valueOf(0),                                                // 支出金额
										FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(financepre)          // 收入金额
										);
						 if(i == period-1){
							  sf.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
							  sf1.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
							  sf2.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
							  sf3.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
						  }
						list.add(sf1);
						if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
						if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}
						list.add(sf);		
						preperiodsurplus1=preperiodsurplus1.subtract(sf.getIncomeMoney());
						
					}
							
							
							
				
			
			
			
				}
			
		
		

		
	
		}
		  }
		  if (payaccrualType.equals("ownerPay") && isPreposePayAccrual == 1) {// 
			  days = DateUtil.getDaysBetweenDate(date1, date2)+1;
			  intervalday=days/period;
				if (period > 0) {
					periodtimemoney1=FundIntentListPronew.periodtimemoney(accrual,projectMoney,period).divide(new BigDecimal(1),2,BigDecimal.ROUND_HALF_UP);
					for (int i = 0; i < period; i++) {
							// 贷款收息
						if(accrualType.equals("sameprincipalandInterest")){
							sf1 = calculslfundintent(FundIntentListPronew.ProjectLoadAccrual,            // 资金类型
									DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"),(i)*intervalday),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(accrualpre)         // 收入金额
									);
						    sf =calculslfundintent(FundIntentListPronew.ProjectLoadRoot,            // 资金类型
									DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i)*intervalday),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									periodtimemoney1.subtract(FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual))        // 收入金额
									);
						    sf2 = calculslfundintent(FundIntentListPronew.ProjectManagementConsulting,            // 资金类型
									DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i)*intervalday),   //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(managementpre)          // 收入金额
									);
							sf3 = calculslfundintent(FundIntentListPronew.ProjectFinanceService,            // 资金类型
											DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i)*intervalday),   //计划日期
											BigDecimal.valueOf(0),                                                // 支出金额
											FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(financepre)          // 收入金额
											);
							list.add(sf1);
							if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
							if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}
							list.add(sf);
							preperiodsurplus1=preperiodsurplus1.subtract(sf.getIncomeMoney());
							
					
					}
			       if(accrualType.equals("sameprincipal")){
						
			    	   BigDecimal sameprojectMoney = projectMoney.divide(new BigDecimal(period),100,BigDecimal.ROUND_HALF_UP);
			    	    sf1 = calculslfundintent(FundIntentListPronew.ProjectLoadAccrual,            // 资金类型
								DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i)*intervalday),  //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(accrualpre) 	);        // 收入金额
					    sf =calculslfundintent(FundIntentListPronew.ProjectLoadRoot,            // 资金类型
									DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i)*intervalday),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额
									sameprojectMoney  	);      // 收入金额
								
					    sf2 = calculslfundintent(FundIntentListPronew.ProjectManagementConsulting,            // 资金类型
								DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i)*intervalday),   //计划日期
								BigDecimal.valueOf(0),                                                // 支出金额
								FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(managementpre)          // 收入金额
								);
						sf3 = calculslfundintent(FundIntentListPronew.ProjectFinanceService,            // 资金类型
										DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i)*intervalday),   //计划日期
										BigDecimal.valueOf(0),                                                // 支出金额
										FundIntentListPronew.calculAccrualnew(preperiodsurplus1,accrual).multiply(financepre)          // 收入金额
										);
						list.add(sf1);
						if(sf2.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf2);}
						if(sf3.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){list.add(sf3);}
						list.add(sf);
						preperiodsurplus1=preperiodsurplus1.subtract(sf.getIncomeMoney());
						
					}
							
							
							
				}
			
			
			
				}
		}
		return list;

	}
	public static BigDecimal mnfang(BigDecimal m,int n){
		BigDecimal s=m;
		for(int i=1;i<n;i++){
			s=s.multiply(m);
			
		}
		return s;
	}
	/**
	 * 
	 * @param accrualType
	 *            accrualType为 1时为stub型, 2时按日计息,3是按月计息,4.按年计息
	 * @param payaccrualType
	 *            -付息方式
	 * @param accrual
	 *            利率
	 * @param projectMoney
	 *            -贷款金额
	 * @param commisionType
	 *            -佣金方式
	 * @param commisionMoney
	 *            -佣金
	 * @param elseMoney
	 *            -其它费用
	 * @param elseMoney
	 *            -日期模型
	 * @param date1
	 *            起始时间
	 * @param date2
	 *            结束时间
	 * @return 计算利息方法
	 */
	public static BigDecimal periodtimemoney(BigDecimal accrual,BigDecimal projectMoney,int period) {
		
		BigDecimal   periodtimemoney=new  BigDecimal("0");
		  periodtimemoney=projectMoney.multiply(accrual).multiply(mnfang(accrual.add(new BigDecimal(1)),period)).divide((mnfang(accrual.add(new BigDecimal(1)),period).subtract(new BigDecimal(1))),100,BigDecimal.ROUND_HALF_UP);
			return periodtimemoney;
	}
	
	public static BigDecimal calculAccrualnew(BigDecimal preperiodsurplus,BigDecimal accrual){

		BigDecimal dayp = new BigDecimal(0);
         

		return  preperiodsurplus.multiply(accrual);
	}
	public static SlFundIntent calculslfundintent(String fundType,Date intentdate,BigDecimal paymoney,BigDecimal incomemoeny){
		SlFundIntent sf1=new SlFundIntent();
		sf1.setFundType(fundType);// 资金类型
		sf1.setIntentDate(intentdate);
		sf1.setPayMoney(paymoney); // 支出金额
		sf1.setIncomeMoney(incomemoeny); // 收入金额
		sf1.setRemark("");
		return  sf1;
		
	}

	
}
