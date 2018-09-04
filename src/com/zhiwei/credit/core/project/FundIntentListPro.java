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

public class FundIntentListPro {
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
		List<SlFundIntent> list = new ArrayList<SlFundIntent>();
		SlFundIntent sf;
		if (date1 == "" || date2 == "") {
			return null;
		}
		// 贷款贷出
		sf = new SlFundIntent();
		if (calcutype.equals("SmallLoan")) { // 贷款项目
			sf.setFundType(FundIntentListPro.ProjectLoadOut);// 资金类型
			sf.setIntentDate(DateUtil.parseDate(date1, "yyyy-MM-dd"));
			sf.setPayMoney(projectMoney); // 支出金额
			sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入金额
			sf.setRemark("");
			list.add(sf);
		} else { // 融资项目
			sf.setFundType(FundIntentListPro.Financingborrow);// 资金类型
			sf.setIntentDate(DateUtil.parseDate(date1, "yyyy-MM-dd"));
			sf.setPayMoney(BigDecimal.valueOf(0)); // 支出金额
			sf.setIncomeMoney(projectMoney); // 收入金额
			sf.setRemark("");
			list.add(sf);
		}
		if (payaccrualType.equals("oneTimePay") && isPreposePayAccrual == 1) {// 一次性前置付息
			// 贷款收息
			if (calcutype.equals("SmallLoan")) { // 贷款项目
				sf = new SlFundIntent();
				sf.setFundType(FundIntentListPro.ProjectLoadAccrual);// 资金类型
				sf.setIntentDate(DateUtil.parseDate(date1, "yyyy-MM-dd"));
				sf.setPayMoney(BigDecimal.valueOf(0)); // 支出金额
				sf.setIncomeMoney(FundIntentListPro.calculAccrual(calcutype,
						dayProfit, accrualType, payaccrualType, accrual,
						projectMoney, commisionType, commisionMoney, dateMode,
						date1, date2));// 收入金额
				sf.setRemark("");
				list.add(sf);
			} else { // 融资项目
				sf = new SlFundIntent();
				sf.setFundType(FundIntentListPro.FinancingInterest);// 资金类型
				sf.setIntentDate(DateUtil.parseDate(date1, "yyyy-MM-dd"));
				sf.setPayMoney(FundIntentListPro.calculAccrual(calcutype,
						dayProfit, accrualType, payaccrualType, accrual,
						projectMoney, commisionType, commisionMoney, dateMode,
						date1, date2)); // 支出金额
				sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入金额
				sf.setRemark("");
				list.add(sf);
			}
			
			//获得管理咨询费和财务服务费 start by chencc
			if(!managementConsultingOfRate.equals(BigDecimal.valueOf(0))){
				sf = new SlFundIntent();
				sf.setIntentDate(DateUtil.parseDate(date1, "yyyy-MM-dd"));
				if (calcutype.equals("SmallLoan")) { // 贷款项目
					sf.setFundType(FundIntentListPro.ProjectManagementConsulting);// 资金类型
					sf.setPayMoney(BigDecimal.valueOf(0)); // 支出金额
					sf.setIncomeMoney(FundIntentListPro.calculAccrual(calcutype,
							managementOfRate, accrualType, payaccrualType, managementConsultingOfRate,
							projectMoney, commisionType, commisionMoney, dateMode,
							date1, date2));// 收入金额

				} else {// 融资 项目
					sf.setFundType(FundIntentListPro.FinancingconsultationMoney);// 资金类型
					sf.setPayMoney(FundIntentListPro.calculAccrual(calcutype,
							managementOfRate, accrualType, payaccrualType, managementConsultingOfRate,
							projectMoney, commisionType, commisionMoney, dateMode,
							date1, date2)); // 支出金额
					sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入金额
				}
				sf.setRemark("");
				list.add(sf);
			}
			//---------------------------------
			if(!financeServiceOfRate.equals(BigDecimal.valueOf(0))){
				sf = new SlFundIntent();
				sf.setIntentDate(DateUtil.parseDate(date1, "yyyy-MM-dd"));
				if (calcutype.equals("SmallLoan")) { // 贷款项目
					sf.setFundType(FundIntentListPro.ProjectFinanceService);// 资金类型
					sf.setPayMoney(BigDecimal.valueOf(0)); // 支出金额
					sf.setIncomeMoney(FundIntentListPro.calculAccrual(calcutype,
							financeOfRate, accrualType, payaccrualType, financeServiceOfRate,
							projectMoney, commisionType, commisionMoney, dateMode,
							date1, date2));// 收入金额

				} else {// 融资 项目
					sf.setFundType(FundIntentListPro.FinancingserviceMoney);// 资金类型
					sf.setPayMoney(FundIntentListPro.calculAccrual(calcutype,
							financeOfRate, accrualType, payaccrualType, financeServiceOfRate,
							projectMoney, commisionType, commisionMoney, dateMode,
							date1, date2)); // 支出金额
					sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入金额
				}
				sf.setRemark("");
				list.add(sf);
			}
			//获得管理咨询费和财务服务费 end by chencc
			
		}
		if (payaccrualType.equals("oneTimePay") && isPreposePayAccrual == 0) {// 一次性利尾
			// 贷款收息
			if (calcutype.equals("SmallLoan")) { // 贷款项目
				sf = new SlFundIntent();
				sf.setFundType(FundIntentListPro.ProjectLoadAccrual);// 资金类型
				sf.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
				sf.setPayMoney(BigDecimal.valueOf(0)); // 支出金额
				sf.setIncomeMoney(FundIntentListPro.calculAccrual(calcutype,
						dayProfit, accrualType, payaccrualType, accrual,
						projectMoney, commisionType, commisionMoney, dateMode,
						date1, date2)); // 收入金额
				sf.setRemark("");
				list.add(sf);
			} else { // 融资项目
				sf = new SlFundIntent();
				sf.setFundType(FundIntentListPro.FinancingInterest);// 资金类型
				sf.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
				sf.setPayMoney(FundIntentListPro.calculAccrual(calcutype,
						dayProfit, accrualType, payaccrualType, accrual,
						projectMoney, commisionType, commisionMoney, dateMode,
						date1, date2));
				sf.setIncomeMoney(BigDecimal.valueOf(0));
				sf.setRemark("");
				list.add(sf);
			}
			//获得管理咨询费和财务服务费 start by chencc
			if(!managementConsultingOfRate.equals(BigDecimal.valueOf(0))){
				sf = new SlFundIntent();
				sf.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
				if (calcutype.equals("SmallLoan")) { // 贷款项目
					sf.setFundType(FundIntentListPro.ProjectManagementConsulting);// 资金类型
					sf.setPayMoney(BigDecimal.valueOf(0)); // 支出金额
					sf.setIncomeMoney(FundIntentListPro.calculAccrual(calcutype,
							managementOfRate, accrualType, payaccrualType, managementConsultingOfRate,
							projectMoney, commisionType, commisionMoney, dateMode,
							date1, date2));// 收入金额

				} else {// 融资 项目
					sf.setFundType(FundIntentListPro.FinancingconsultationMoney);// 资金类型
					sf.setPayMoney(FundIntentListPro.calculAccrual(calcutype,
							managementOfRate, accrualType, payaccrualType, managementConsultingOfRate,
							projectMoney, commisionType, commisionMoney, dateMode,
							date1, date2)); // 支出金额
					sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入金额
				}
				sf.setRemark("");
				list.add(sf);
			}
			//---------------------------------
			if(!financeServiceOfRate.equals(BigDecimal.valueOf(0))){
				sf = new SlFundIntent();
				sf.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
				if (calcutype.equals("SmallLoan")) { // 贷款项目
					sf.setFundType(FundIntentListPro.ProjectFinanceService);// 资金类型
					sf.setPayMoney(BigDecimal.valueOf(0)); // 支出金额
					sf.setIncomeMoney(FundIntentListPro.calculAccrual(calcutype,
							financeOfRate, accrualType, payaccrualType, financeServiceOfRate,
							projectMoney, commisionType, commisionMoney, dateMode,
							date1, date2));// 收入金额

				} else {// 融资 项目
					sf.setFundType(FundIntentListPro.FinancingserviceMoney);// 资金类型
					sf.setPayMoney(FundIntentListPro.calculAccrual(calcutype,
							financeOfRate, accrualType, payaccrualType, financeServiceOfRate,
							projectMoney, commisionType, commisionMoney, dateMode,
							date1, date2)); // 支出金额
					sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入金额
				}
				sf.setRemark("");
				list.add(sf);
			}
			//获得管理咨询费和财务服务费 end by chencc
		}
		if (payaccrualType.equals("dayPay") && isPreposePayAccrual == 1) {// 足日头
			int days = DateUtil.getDaysBetweenDate(date1, date2);// 得到两日期相差天数
			if (days > 0) {
				days = days +1;
				for (int i = 0; i < days; i++) {
					if (calcutype.equals("SmallLoan")) { // 贷款项目
						// 贷款收息
						sf = new SlFundIntent();
						sf.setFundType(FundIntentListPro.ProjectLoadAccrual);// 资金类型
						sf.setIntentDate(DateUtil.addDaysToDate(DateUtil
								.parseDate(date1, "yyyy-MM-dd"), i));
						sf.setPayMoney(BigDecimal.valueOf(0)); // 支出金额
						sf.setIncomeMoney(FundIntentListPro.calculAccrual(
								calcutype, dayProfit, accrualType,
								payaccrualType, accrual, projectMoney,
								commisionType, commisionMoney, dateMode, date1,
								date2)); // 收入金额
						sf.setRemark("");
						list.add(sf);
					} else { // 融资项目
						// 贷款收息
						sf = new SlFundIntent();
						sf.setFundType(FundIntentListPro.FinancingInterest);// 资金类型
						sf.setIntentDate(DateUtil.addDaysToDate(DateUtil
								.parseDate(date1, "yyyy-MM-dd"), i));
						sf.setPayMoney(FundIntentListPro.calculAccrual(
								calcutype, dayProfit, accrualType,
								payaccrualType, accrual, projectMoney,
								commisionType, commisionMoney, dateMode, date1,
								date2)); // 支出金额
						sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入金额
						sf.setRemark("");
						list.add(sf);
					}
					
					//获得管理咨询费和财务服务费 start by chencc
					if(!managementConsultingOfRate.equals(BigDecimal.valueOf(0))){
						sf = new SlFundIntent();
						sf.setIntentDate(DateUtil.addDaysToDate(DateUtil
								.parseDate(date1, "yyyy-MM-dd"), i));
						if (calcutype.equals("SmallLoan")) { // 贷款项目
							sf.setFundType(FundIntentListPro.ProjectManagementConsulting);// 资金类型
							sf.setPayMoney(BigDecimal.valueOf(0));// 支出
							sf.setIncomeMoney(FundIntentListPro.calculAccrual(
									calcutype, managementOfRate, accrualType,
									payaccrualType, managementConsultingOfRate, projectMoney,
									commisionType, commisionMoney, dateMode, date1,
									date2));// 收入

						} else {// 融资 项目
							sf.setFundType(FundIntentListPro.FinancingconsultationMoney);// 资金类型
							sf.setPayMoney(FundIntentListPro.calculAccrual(
									calcutype, managementConsultingOfRate, accrualType,
									payaccrualType, managementOfRate, projectMoney,
									commisionType, commisionMoney, dateMode, date1,
									date2)); // 支出
							sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
						}
						sf.setRemark("");
						list.add(sf);
					}
					//---------------------------------
					if(!financeServiceOfRate.equals(BigDecimal.valueOf(0))){
						sf = new SlFundIntent();
						sf.setIntentDate(DateUtil.addDaysToDate(DateUtil
								.parseDate(date1, "yyyy-MM-dd"), i));
						if (calcutype.equals("SmallLoan")) { // 贷款项目
							sf.setFundType(FundIntentListPro.ProjectFinanceService);// 资金类型
							sf.setPayMoney(BigDecimal.valueOf(0));// 支出
							sf.setIncomeMoney(FundIntentListPro.calculAccrual(
									calcutype, financeOfRate, accrualType,
									payaccrualType, financeServiceOfRate, projectMoney,
									commisionType, commisionMoney, dateMode, date1,
									date2));// 收入

						} else {// 融资 项目
							sf.setFundType(FundIntentListPro.FinancingserviceMoney);// 资金类型
							sf.setPayMoney(FundIntentListPro.calculAccrual(
									calcutype, financeOfRate, accrualType,
									payaccrualType, financeServiceOfRate, projectMoney,
									commisionType, commisionMoney, dateMode, date1,
									date2)); // 支出
							sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
						}
						sf.setRemark("");
						list.add(sf);
					}
					//获得管理咨询费和财务服务费 end by chencc

				}
			}
		}
		if (payaccrualType.equals("dayPay") && isPreposePayAccrual == 0) {// 足日尾
			int days = DateUtil.getDaysBetweenDate(date1, date2);// 得到两日期相差天数
			// 贷款收息
			if (days > 0) {
				for (int i = 0; i < days+1; i++) {
					sf = new SlFundIntent();
					sf.setIntentDate(DateUtil.addDaysToDate(DateUtil.parseDate(
							date1, "yyyy-MM-dd"), i));
					if (calcutype.equals("SmallLoan")) { // 贷款项目
						sf.setFundType(FundIntentListPro.ProjectLoadAccrual);// 资金类型
						sf.setPayMoney(BigDecimal.valueOf(0));
						sf.setIncomeMoney(FundIntentListPro.calculAccrual(
								calcutype, dayProfit, accrualType,
								payaccrualType, accrual, projectMoney,
								commisionType, commisionMoney, dateMode, date1,
								date2));
					} else {// 融资 项目
						sf.setFundType(FundIntentListPro.FinancingInterest);// 资金类型
						sf.setPayMoney(FundIntentListPro.calculAccrual(
								calcutype, dayProfit, accrualType,
								payaccrualType, accrual, projectMoney,
								commisionType, commisionMoney, dateMode, date1,
								date2));
						sf.setIncomeMoney(BigDecimal.valueOf(0));
					}
					sf.setRemark("");
					list.add(sf);
					
					//获得管理咨询费和财务服务费 start by chencc
					if(!managementConsultingOfRate.equals(BigDecimal.valueOf(0))){
						sf = new SlFundIntent();
						sf.setIntentDate(DateUtil.addDaysToDate(DateUtil.parseDate(
								date1, "yyyy-MM-dd"), i));
						if (calcutype.equals("SmallLoan")) { // 贷款项目
							sf.setFundType(FundIntentListPro.ProjectManagementConsulting);// 资金类型
							sf.setPayMoney(BigDecimal.valueOf(0));// 支出
							sf.setIncomeMoney(FundIntentListPro.calculAccrual(
									calcutype, managementOfRate, accrualType,
									payaccrualType, managementConsultingOfRate, projectMoney,
									commisionType, commisionMoney, dateMode, date1,
									date2));// 收入

						} else {// 融资 项目
							sf.setFundType(FundIntentListPro.FinancingconsultationMoney);// 资金类型
							sf.setPayMoney(FundIntentListPro.calculAccrual(
									calcutype, managementOfRate, accrualType,
									payaccrualType, managementConsultingOfRate, projectMoney,
									commisionType, commisionMoney, dateMode, date1,
									date2)); // 支出
							sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
						}
						sf.setRemark("");
						list.add(sf);
					}
					//---------------------------------
					if(!financeServiceOfRate.equals(BigDecimal.valueOf(0))){
						sf = new SlFundIntent();
						sf.setIntentDate(DateUtil.addDaysToDate(DateUtil.parseDate(
								date1, "yyyy-MM-dd"), i));
						if (calcutype.equals("SmallLoan")) { // 贷款项目
							sf.setFundType(FundIntentListPro.ProjectFinanceService);// 资金类型
							sf.setPayMoney(BigDecimal.valueOf(0));// 支出
							sf.setIncomeMoney(FundIntentListPro.calculAccrual(
									calcutype, financeOfRate, accrualType,
									payaccrualType, financeServiceOfRate, projectMoney,
									commisionType, commisionMoney, dateMode, date1,
									date2));// 收入

						} else {// 融资 项目
							sf.setFundType(FundIntentListPro.FinancingserviceMoney);// 资金类型
							sf.setPayMoney(FundIntentListPro.calculAccrual(
									calcutype, financeOfRate, accrualType,
									payaccrualType, financeServiceOfRate, projectMoney,
									commisionType, commisionMoney, dateMode, date1,
									date2)); // 支出
							sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
						}
						sf.setRemark("");
						list.add(sf);
					}
					//获得管理咨询费和财务服务费 end by chencc
				}
			}
		}
		if (payaccrualType.equals("monthPay") && isPreposePayAccrual == 1) {// 足月头

			// 贷款收息
			Map<Integer, Integer> map = DateUtil.getMonthAndDaysBetweenDate3(
					date1, date2);
			int months = map.get(1).intValue();
			
			if (months >=0) {
				for (int i = 0; i < months; i++) {
					sf = new SlFundIntent();
					sf.setIntentDate(DateUtil.addMonthsToDate(DateUtil
							.parseDate(date1, "yyyy-MM-dd"), i));
					if (calcutype.equals("SmallLoan")) { // 贷款项目
						sf.setFundType(FundIntentListPro.ProjectLoadAccrual);// 资金类型
						sf.setPayMoney(BigDecimal.valueOf(0));// 支出
						sf.setIncomeMoney(FundIntentListPro.calculAccrual(
								calcutype, dayProfit, accrualType,
								payaccrualType, accrual, projectMoney,
								commisionType, commisionMoney, dateMode, date1,
								date2));// 收入

					} else {// 融资 项目
						sf.setFundType(FundIntentListPro.FinancingInterest);// 资金类型
						sf.setPayMoney(FundIntentListPro.calculAccrual(
								calcutype, dayProfit, accrualType,
								payaccrualType, accrual, projectMoney,
								commisionType, commisionMoney, dateMode, date1,
								date2)); // 支出
						sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
					}

					sf.setRemark("");
					list.add(sf);
					
					//获得管理咨询费和财务服务费 start by chencc
					if(!managementConsultingOfRate.equals(BigDecimal.valueOf(0))){
						sf = new SlFundIntent();
						sf.setIntentDate(DateUtil.addMonthsToDate(DateUtil
								.parseDate(date1, "yyyy-MM-dd"), i));
						if (calcutype.equals("SmallLoan")) { // 贷款项目
							sf.setFundType(FundIntentListPro.ProjectManagementConsulting);// 资金类型
							sf.setPayMoney(BigDecimal.valueOf(0));// 支出
							sf.setIncomeMoney(FundIntentListPro.calculAccrual(
									calcutype, managementOfRate, accrualType,
									payaccrualType, managementConsultingOfRate, projectMoney,
									commisionType, commisionMoney, dateMode, date1,
									date2));// 收入

						} else {// 融资 项目
							sf.setFundType(FundIntentListPro.FinancingconsultationMoney);// 资金类型
							sf.setPayMoney(FundIntentListPro.calculAccrual(
									calcutype, managementOfRate, accrualType,
									payaccrualType, managementConsultingOfRate, projectMoney,
									commisionType, commisionMoney, dateMode, date1,
									date2)); // 支出
							sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
						}
						sf.setRemark("");
						list.add(sf);
					}
					//---------------------------------
					if(!financeServiceOfRate.equals(BigDecimal.valueOf(0))){
						sf = new SlFundIntent();
						sf.setIntentDate(DateUtil.addMonthsToDate(DateUtil
								.parseDate(date1, "yyyy-MM-dd"), i));
						if (calcutype.equals("SmallLoan")) { // 贷款项目
							sf.setFundType(FundIntentListPro.ProjectFinanceService);// 资金类型
							sf.setPayMoney(BigDecimal.valueOf(0));// 支出
							sf.setIncomeMoney(FundIntentListPro.calculAccrual(
									calcutype, financeOfRate, accrualType,
									payaccrualType, financeServiceOfRate, projectMoney,
									commisionType, commisionMoney, dateMode, date1,
									date2));// 收入

						} else {// 融资 项目
							sf.setFundType(FundIntentListPro.FinancingserviceMoney);// 资金类型
							sf.setPayMoney(FundIntentListPro.calculAccrual(
									calcutype, financeOfRate, accrualType,
									payaccrualType, financeServiceOfRate, projectMoney,
									commisionType, commisionMoney, dateMode, date1,
									date2)); // 支出
							sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
						}
						sf.setRemark("");
						list.add(sf);
					}
					//获得管理咨询费和财务服务费 end by chencc
				}
				sf = new SlFundIntent();
				sf.setIntentDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1,
						"yyyy-MM-dd"), months));

				int mdays = map.get(2).intValue();
				if(mdays != 0){
					if (calcutype.equals("SmallLoan")) { // 贷款项目
						//mdays = mdays + 1;
						if(mdays >=2){
							mdays = mdays-1;
						}
						sf.setFundType(FundIntentListPro.ProjectLoadAccrual);// 资金类型
						sf.setPayMoney(BigDecimal.valueOf(0));// 支出
						sf.setIncomeMoney((dayProfit.multiply(projectMoney))
								.multiply(BigDecimal.valueOf(mdays)).divide(BigDecimal.valueOf(100)));// 收入

					} else {// 融资 项目
						sf.setFundType(FundIntentListPro.FinancingInterest);// 资金类型
						sf.setPayMoney((dayProfit.multiply(projectMoney))
								.multiply(BigDecimal.valueOf(mdays)).divide(BigDecimal.valueOf(100))); // 支出
						sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
					}

					sf.setRemark("");
					list.add(sf);
					
					//获得管理咨询费和财务服务费 start by chencc
					if(!managementConsultingOfRate.equals(BigDecimal.valueOf(0))){
						sf = new SlFundIntent();
						sf.setIntentDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1,
								"yyyy-MM-dd"), months));

						if (calcutype.equals("SmallLoan")) { // 贷款项目
							sf.setFundType(FundIntentListPro.ProjectManagementConsulting);// 资金类型
							sf.setPayMoney(BigDecimal.valueOf(0));// 支出
							sf.setIncomeMoney((managementOfRate.multiply(projectMoney))
									.multiply(BigDecimal.valueOf(mdays)).divide(BigDecimal.valueOf(100)));// 收入

						} else {// 融资 项目
							sf.setFundType(FundIntentListPro.FinancingconsultationMoney);// 资金类型
							sf.setPayMoney((managementOfRate.multiply(projectMoney))
									.multiply(BigDecimal.valueOf(mdays)).divide(BigDecimal.valueOf(100))); // 支出
							sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
						}

						sf.setRemark("");
						list.add(sf);
					}
					//---------------------------------
					if(!financeServiceOfRate.equals(BigDecimal.valueOf(0))){
						sf = new SlFundIntent();
						sf.setIntentDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1,
								"yyyy-MM-dd"), months));

						if (calcutype.equals("SmallLoan")) { // 贷款项目
							sf.setFundType(FundIntentListPro.ProjectFinanceService);// 资金类型
							sf.setPayMoney(BigDecimal.valueOf(0));// 支出
							sf.setIncomeMoney((financeOfRate.multiply(projectMoney))
									.multiply(BigDecimal.valueOf(mdays)).divide(BigDecimal.valueOf(100)));// 收入

						} else {// 融资 项目
							sf.setFundType(FundIntentListPro.FinancingserviceMoney);// 资金类型
							sf.setPayMoney((financeOfRate.multiply(projectMoney))
									.multiply(BigDecimal.valueOf(mdays)).divide(BigDecimal.valueOf(100))); // 支出
							sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
						}

						sf.setRemark("");
						list.add(sf);
					}
					//获得管理咨询费和财务服务费 end by chencc
				}
			}
			
			
		}
		if (payaccrualType.equals("monthPay") && isPreposePayAccrual == 0) {// 足月尾
		
			Map<Integer, Integer> map = DateUtil.getMonthAndDaysBetweenDate2(
					date1, date2);
			int months = map.get(1).intValue();
			if (months >= 0) {
				for (int i = 0; i < months; i++) {
					sf = new SlFundIntent();
					sf.setIntentDate(DateUtil.addMonthsToDate(DateUtil
							.parseDate(date1, "yyyy-MM-dd"), i + 1,-1));
					if (calcutype.equals("SmallLoan")) { // 贷款项目
						sf.setFundType(FundIntentListPro.ProjectLoadAccrual);// 资金类型
						sf.setPayMoney(BigDecimal.valueOf(0));// 支出
						sf.setIncomeMoney(FundIntentListPro.calculAccrual(
								calcutype, dayProfit, accrualType,
								payaccrualType, accrual, projectMoney,
								commisionType, commisionMoney, dateMode, date1,
								date2));// 收入

					} else {// 融资 项目
						sf.setFundType(FundIntentListPro.FinancingInterest);// 资金类型
						sf.setPayMoney(FundIntentListPro.calculAccrual(
								calcutype, dayProfit, accrualType,
								payaccrualType, accrual, projectMoney,
								commisionType, commisionMoney, dateMode, date1,
								date2)); // 支出
						sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
					}
					sf.setRemark("");
					list.add(sf);
					
					//获得管理咨询费和财务服务费 start by chencc
					if(!managementConsultingOfRate.equals(BigDecimal.valueOf(0))){
						sf = new SlFundIntent();
						sf.setIntentDate(DateUtil.addMonthsToDate(DateUtil
								.parseDate(date1, "yyyy-MM-dd"), i + 1,-1));
						if (calcutype.equals("SmallLoan")) { // 贷款项目
							sf.setFundType(FundIntentListPro.ProjectManagementConsulting);// 资金类型
							sf.setPayMoney(BigDecimal.valueOf(0));// 支出
							sf.setIncomeMoney(FundIntentListPro.calculAccrual(
									calcutype, managementOfRate, accrualType,
									payaccrualType, managementConsultingOfRate, projectMoney,
									commisionType, commisionMoney, dateMode, date1,
									date2));// 收入

						} else {// 融资 项目
							sf.setFundType(FundIntentListPro.FinancingconsultationMoney);// 资金类型
							sf.setPayMoney(FundIntentListPro.calculAccrual(
									calcutype, managementOfRate, accrualType,
									payaccrualType, managementConsultingOfRate, projectMoney,
									commisionType, commisionMoney, dateMode, date1,
									date2)); // 支出
							sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
						}
						sf.setRemark("");
						list.add(sf);
					}
					//---------------------------------
					if(!financeServiceOfRate.equals(BigDecimal.valueOf(0))){
						sf = new SlFundIntent();
						sf.setIntentDate(DateUtil.addMonthsToDate(DateUtil
								.parseDate(date1, "yyyy-MM-dd"), i + 1,-1));
						if (calcutype.equals("SmallLoan")) { // 贷款项目
							sf.setFundType(FundIntentListPro.ProjectFinanceService);// 资金类型
							sf.setPayMoney(BigDecimal.valueOf(0));// 支出
							sf.setIncomeMoney(FundIntentListPro.calculAccrual(
									calcutype, financeOfRate, accrualType,
									payaccrualType, financeServiceOfRate, projectMoney,
									commisionType, commisionMoney, dateMode, date1,
									date2));// 收入

						} else {// 融资 项目
							sf.setFundType(FundIntentListPro.FinancingserviceMoney);// 资金类型
							sf.setPayMoney(FundIntentListPro.calculAccrual(
									calcutype, financeOfRate, accrualType,
									payaccrualType, financeServiceOfRate, projectMoney,
									commisionType, commisionMoney, dateMode, date1,
									date2)); // 支出
							sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
						}
						sf.setRemark("");
						list.add(sf);
					}
					//获得管理咨询费和财务服务费 end by chencc
				}
				sf = new SlFundIntent();
				sf.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
				int mdays = map.get(2).intValue();
		//		if(mdays != 0 && mdays != 30){
				if(mdays != 0){
					if (calcutype.equals("SmallLoan")) { // 贷款项目
						//mdays = mdays + 1;
						sf.setFundType(FundIntentListPro.ProjectLoadAccrual);// 资金类型
						sf.setPayMoney(BigDecimal.valueOf(0));// 支出
						sf.setIncomeMoney((dayProfit.multiply(projectMoney))
								.multiply(BigDecimal.valueOf(mdays)).divide(BigDecimal.valueOf(100)));// 收入

					} else {// 融资 项目
						sf.setFundType(FundIntentListPro.FinancingInterest);// 资金类型
						sf.setPayMoney((dayProfit.multiply(projectMoney))
								.multiply(BigDecimal.valueOf(mdays)).divide(BigDecimal.valueOf(100))); // 支出
						sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
					}
					sf.setRemark("");
					list.add(sf);
					
					//获得管理咨询费和财务服务费 start by chencc
					if(!managementConsultingOfRate.equals(BigDecimal.valueOf(0))){
						sf = new SlFundIntent();
						sf.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));

						if (calcutype.equals("SmallLoan")) { // 贷款项目
							sf.setFundType(FundIntentListPro.ProjectManagementConsulting);// 资金类型
							sf.setPayMoney(BigDecimal.valueOf(0));// 支出
							sf.setIncomeMoney((managementOfRate.multiply(projectMoney))
									.multiply(BigDecimal.valueOf(mdays)).divide(BigDecimal.valueOf(100)));// 收入

						} else {// 融资 项目
							sf.setFundType(FundIntentListPro.FinancingconsultationMoney);// 资金类型
							sf.setPayMoney((managementOfRate.multiply(projectMoney))
									.multiply(BigDecimal.valueOf(mdays)).divide(BigDecimal.valueOf(100))); // 支出
							sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
						}

						sf.setRemark("");
						list.add(sf);
					}
					//---------------------------------
					if(!financeServiceOfRate.equals(BigDecimal.valueOf(0))){
						sf = new SlFundIntent();
						sf.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));

						if (calcutype.equals("SmallLoan")) { // 贷款项目
							sf.setFundType(FundIntentListPro.ProjectFinanceService);// 资金类型
							sf.setPayMoney(BigDecimal.valueOf(0));// 支出
							sf.setIncomeMoney((financeOfRate.multiply(projectMoney))
									.multiply(BigDecimal.valueOf(mdays)).divide(BigDecimal.valueOf(100)));// 收入

						} else {// 融资 项目
							sf.setFundType(FundIntentListPro.FinancingserviceMoney);// 资金类型
							sf.setPayMoney((financeOfRate.multiply(projectMoney))
									.multiply(BigDecimal.valueOf(mdays)).divide(BigDecimal.valueOf(100))); // 支出
							sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
						}

						sf.setRemark("");
						list.add(sf);
					}
					//获得管理咨询费和财务服务费 end by chencc
				}
			}
			
			
		}
		if (payaccrualType.equals("seasonPay") && isPreposePayAccrual == 1) {// 足季头
			// 贷款收息
			Map<Integer, Integer> map = DateUtil.getMonthAndDaysBetweenDate(
					date1, date2);
			int months = map.get(1).intValue();
			if (months > 0) {
				for (int i = 0; i < months / 3; i++) {
					sf = new SlFundIntent();
					sf.setIntentDate(DateUtil.addMonthsToDate(DateUtil
							.parseDate(date1, "yyyy-MM-dd"), i * 3));
					if (calcutype.equals("SmallLoan")) { // 贷款项目
						sf.setFundType(FundIntentListPro.ProjectLoadAccrual);// 资金类型
						sf.setPayMoney(BigDecimal.valueOf(0));// 支出
						sf.setIncomeMoney(FundIntentListPro.calculAccrual(
								calcutype, dayProfit, accrualType,
								payaccrualType, accrual, projectMoney,
								commisionType, commisionMoney, dateMode, date1,
								date2));// 收入

					} else {// 融资 项目
						sf.setFundType(FundIntentListPro.FinancingInterest);// 资金类型
						sf.setPayMoney(FundIntentListPro.calculAccrual(
								calcutype, dayProfit, accrualType,
								payaccrualType, accrual, projectMoney,
								commisionType, commisionMoney, dateMode, date1,
								date2)); // 支出
						sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
					}
					sf.setRemark("");
					list.add(sf);
					
					//获得管理咨询费和财务服务费 start by chencc
					if(!managementConsultingOfRate.equals(BigDecimal.valueOf(0))){
						sf = new SlFundIntent();
						sf.setIntentDate(DateUtil.addMonthsToDate(DateUtil
								.parseDate(date1, "yyyy-MM-dd"), i * 3));
						if (calcutype.equals("SmallLoan")) { // 贷款项目
							sf.setFundType(FundIntentListPro.ProjectManagementConsulting);// 资金类型
							sf.setPayMoney(BigDecimal.valueOf(0));// 支出
							sf.setIncomeMoney(FundIntentListPro.calculAccrual(
									calcutype, managementOfRate, accrualType,
									payaccrualType, managementConsultingOfRate, projectMoney,
									commisionType, commisionMoney, dateMode, date1,
									date2));// 收入

						} else {// 融资 项目
							sf.setFundType(FundIntentListPro.FinancingconsultationMoney);// 资金类型
							sf.setPayMoney(FundIntentListPro.calculAccrual(
									calcutype, managementOfRate, accrualType,
									payaccrualType, managementConsultingOfRate, projectMoney,
									commisionType, commisionMoney, dateMode, date1,
									date2)); // 支出
							sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
						}
						sf.setRemark("");
						list.add(sf);
					}
					//---------------------------------
					if(!financeServiceOfRate.equals(BigDecimal.valueOf(0))){
						sf = new SlFundIntent();
						sf.setIntentDate(DateUtil.addMonthsToDate(DateUtil
								.parseDate(date1, "yyyy-MM-dd"), i * 3));
						if (calcutype.equals("SmallLoan")) { // 贷款项目
							sf.setFundType(FundIntentListPro.ProjectFinanceService);// 资金类型
							sf.setPayMoney(BigDecimal.valueOf(0));// 支出
							sf.setIncomeMoney(FundIntentListPro.calculAccrual(
									calcutype, financeOfRate, accrualType,
									payaccrualType, financeServiceOfRate, projectMoney,
									commisionType, commisionMoney, dateMode, date1,
									date2));// 收入

						} else {// 融资 项目
							sf.setFundType(FundIntentListPro.FinancingserviceMoney);// 资金类型
							sf.setPayMoney(FundIntentListPro.calculAccrual(
									calcutype, financeOfRate, accrualType,
									payaccrualType, financeServiceOfRate, projectMoney,
									commisionType, commisionMoney, dateMode, date1,
									date2)); // 支出
							sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
						}
						sf.setRemark("");
						list.add(sf);
					}
					//获得管理咨询费和财务服务费 end by chencc
				}
			}
			int mdays = map.get(2).intValue();
			if(mdays != 0){
				int jd = months / 3;
				int jdays = (months - jd * 3) * 30 + mdays;
				sf = new SlFundIntent();
				sf.setIntentDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1,
						"yyyy-MM-dd"), jd * 3));
				if (calcutype.equals("SmallLoan")) { // 贷款项目
					jdays = jdays + 1;
					sf.setFundType(FundIntentListPro.ProjectLoadAccrual);// 资金类型
					sf.setPayMoney(BigDecimal.valueOf(0));// 支出
					sf.setIncomeMoney((dayProfit.multiply(projectMoney))
							.multiply(BigDecimal.valueOf(jdays)).divide(BigDecimal.valueOf(100)));// 收入

				} else {// 融资 项目
					sf.setFundType(FundIntentListPro.FinancingInterest);// 资金类型
					sf.setPayMoney((dayProfit.multiply(projectMoney))
							.multiply(BigDecimal.valueOf(jdays)).divide(BigDecimal.valueOf(100))); // 支出
					sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
				}

				sf.setRemark("");
				list.add(sf);
				
				//获得管理咨询费和财务服务费 start by chencc
				if(!managementConsultingOfRate.equals(BigDecimal.valueOf(0))){
					sf = new SlFundIntent();
					sf.setIntentDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1,
					"yyyy-MM-dd"), jd * 3));

					if (calcutype.equals("SmallLoan")) { // 贷款项目
						sf.setFundType(FundIntentListPro.ProjectManagementConsulting);// 资金类型
						sf.setPayMoney(BigDecimal.valueOf(0));// 支出
						sf.setIncomeMoney((managementOfRate.multiply(projectMoney))
								.multiply(BigDecimal.valueOf(jdays)).divide(BigDecimal.valueOf(100)));// 收入

					} else {// 融资 项目
						sf.setFundType(FundIntentListPro.FinancingconsultationMoney);// 资金类型
						sf.setPayMoney((managementOfRate.multiply(projectMoney))
								.multiply(BigDecimal.valueOf(jdays)).divide(BigDecimal.valueOf(100))); // 支出
						sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
					}

					sf.setRemark("");
					list.add(sf);
				}
				//---------------------------------
				if(!financeServiceOfRate.equals(BigDecimal.valueOf(0))){
					sf = new SlFundIntent();
					sf.setIntentDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1,
					"yyyy-MM-dd"), jd * 3));

					if (calcutype.equals("SmallLoan")) { // 贷款项目
						sf.setFundType(FundIntentListPro.ProjectFinanceService);// 资金类型
						sf.setPayMoney(BigDecimal.valueOf(0));// 支出
						sf.setIncomeMoney((financeOfRate.multiply(projectMoney))
								.multiply(BigDecimal.valueOf(jdays)).divide(BigDecimal.valueOf(100)));// 收入

					} else {// 融资 项目
						sf.setFundType(FundIntentListPro.FinancingserviceMoney);// 资金类型
						sf.setPayMoney((financeOfRate.multiply(projectMoney))
								.multiply(BigDecimal.valueOf(jdays)).divide(BigDecimal.valueOf(100))); // 支出
						sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
					}

					sf.setRemark("");
					list.add(sf);
				}
				//获得管理咨询费和财务服务费 end by chencc
			}
			
		}
		if (payaccrualType.equals("seasonPay") && isPreposePayAccrual == 0) {// 足季尾
			// //贷款贷出
			// sf=new SlFundIntent();
			// sf.setFundType(FundIntentListPro.ProjectLoadOut);// 资金类型
			// sf.setIntentDate(DateUtil.parseDate(date1, "yyyy-MM-dd"));
			// sf.setPayMoney(FundIntentListPro.calculAccrual(accrualType,
			// payaccrualType, accrual, projectMoney, commisionType,
			// commisionMoney,
			// dateMode, date1, date2));
			// sf.setIncomeMoney(BigDecimal.valueOf(0));
			// sf.setRemark("");
			// list.add(sf);
			// 贷款收息
			Map<Integer, Integer> map = DateUtil.getMonthAndDaysBetweenDate(
					date1, date2);
			int months = map.get(1).intValue();
			if (months > 0) {
				for (int i = 0; i < months / 3; i++) {
					sf = new SlFundIntent();
					sf.setIntentDate(DateUtil.addMonthsToDate(DateUtil
							.parseDate(date1, "yyyy-MM-dd"), (i + 1) * 3));
					if (calcutype.equals("SmallLoan")) { // 贷款项目
						sf.setFundType(FundIntentListPro.ProjectLoadAccrual);// 资金类型
						sf.setPayMoney(BigDecimal.valueOf(0));// 支出
						sf.setIncomeMoney(FundIntentListPro.calculAccrual(
								calcutype, dayProfit, accrualType,
								payaccrualType, accrual, projectMoney,
								commisionType, commisionMoney, dateMode, date1,
								date2));// 收入

					} else {// 融资 项目
						sf.setFundType(FundIntentListPro.FinancingInterest);// 资金类型
						sf.setPayMoney(FundIntentListPro.calculAccrual(
								calcutype, dayProfit, accrualType,
								payaccrualType, accrual, projectMoney,
								commisionType, commisionMoney, dateMode, date1,
								date2)); // 支出
						sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
					}
					sf.setRemark("");
					list.add(sf);
					
					//获得管理咨询费和财务服务费 start by chencc
					if(!managementConsultingOfRate.equals(BigDecimal.valueOf(0))){
						sf = new SlFundIntent();
						sf.setIntentDate(DateUtil.addMonthsToDate(DateUtil
								.parseDate(date1, "yyyy-MM-dd"), (i + 1) * 3));
						if (calcutype.equals("SmallLoan")) { // 贷款项目
							sf.setFundType(FundIntentListPro.ProjectManagementConsulting);// 资金类型
							sf.setPayMoney(BigDecimal.valueOf(0));// 支出
							sf.setIncomeMoney(FundIntentListPro.calculAccrual(
									calcutype, managementOfRate, accrualType,
									payaccrualType, managementConsultingOfRate, projectMoney,
									commisionType, commisionMoney, dateMode, date1,
									date2));// 收入

						} else {// 融资 项目
							sf.setFundType(FundIntentListPro.FinancingconsultationMoney);// 资金类型
							sf.setPayMoney(FundIntentListPro.calculAccrual(
									calcutype, managementOfRate, accrualType,
									payaccrualType, managementConsultingOfRate, projectMoney,
									commisionType, commisionMoney, dateMode, date1,
									date2)); // 支出
							sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
						}
						sf.setRemark("");
						list.add(sf);
					}
					//---------------------------------
					if(!financeServiceOfRate.equals(BigDecimal.valueOf(0))){
						sf = new SlFundIntent();
						sf.setIntentDate(DateUtil.addMonthsToDate(DateUtil
								.parseDate(date1, "yyyy-MM-dd"), (i + 1) * 3));
						if (calcutype.equals("SmallLoan")) { // 贷款项目
							sf.setFundType(FundIntentListPro.ProjectFinanceService);// 资金类型
							sf.setPayMoney(BigDecimal.valueOf(0));// 支出
							sf.setIncomeMoney(FundIntentListPro.calculAccrual(
									calcutype, financeOfRate, accrualType,
									payaccrualType, financeServiceOfRate, projectMoney,
									commisionType, commisionMoney, dateMode, date1,
									date2));// 收入

						} else {// 融资 项目
							sf.setFundType(FundIntentListPro.FinancingserviceMoney);// 资金类型
							sf.setPayMoney(FundIntentListPro.calculAccrual(
									calcutype, financeOfRate, accrualType,
									payaccrualType, financeServiceOfRate, projectMoney,
									commisionType, commisionMoney, dateMode, date1,
									date2)); // 支出
							sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
						}
						sf.setRemark("");
						list.add(sf);
					}
					//获得管理咨询费和财务服务费 end by chencc
				}
			}
			int mdays = map.get(2).intValue();
			if(mdays != 0){
				int jd = months / 3;
				int jdays = (months - jd * 3) * 30 + mdays;

				sf = new SlFundIntent();
				sf.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
				if (calcutype.equals("SmallLoan")) { // 贷款项目
					jdays = jdays + 1;
					sf.setFundType(FundIntentListPro.ProjectLoadAccrual);// 资金类型
					sf.setPayMoney(BigDecimal.valueOf(0));// 支出
					sf.setIncomeMoney((dayProfit.multiply(projectMoney))
							.multiply(BigDecimal.valueOf(jdays)).divide(BigDecimal.valueOf(100)));// 收入

				} else {// 融资 项目
					sf.setFundType(FundIntentListPro.FinancingInterest);// 资金类型
					sf.setPayMoney((dayProfit.multiply(projectMoney))
							.multiply(BigDecimal.valueOf(jdays)).divide(BigDecimal.valueOf(100))); // 支出
					sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
				}
				sf.setRemark("");
				list.add(sf);
				
				//获得管理咨询费和财务服务费 start by chencc
				if(!managementConsultingOfRate.equals(BigDecimal.valueOf(0))){
					sf = new SlFundIntent();
					sf.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));

					if (calcutype.equals("SmallLoan")) { // 贷款项目
						sf.setFundType(FundIntentListPro.ProjectManagementConsulting);// 资金类型
						sf.setPayMoney(BigDecimal.valueOf(0));// 支出
						sf.setIncomeMoney((managementOfRate.multiply(projectMoney))
								.multiply(BigDecimal.valueOf(jdays)).divide(BigDecimal.valueOf(100)));// 收入

					} else {// 融资 项目
						sf.setFundType(FundIntentListPro.FinancingconsultationMoney);// 资金类型
						sf.setPayMoney((managementOfRate.multiply(projectMoney))
								.multiply(BigDecimal.valueOf(jdays)).divide(BigDecimal.valueOf(100))); // 支出
						sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
					}

					sf.setRemark("");
					list.add(sf);
				}
				//---------------------------------
				if(!financeServiceOfRate.equals(BigDecimal.valueOf(0))){
					sf = new SlFundIntent();
					sf.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));

					if (calcutype.equals("SmallLoan")) { // 贷款项目
						sf.setFundType(FundIntentListPro.ProjectFinanceService);// 资金类型
						sf.setPayMoney(BigDecimal.valueOf(0));// 支出
						sf.setIncomeMoney((financeOfRate.multiply(projectMoney))
								.multiply(BigDecimal.valueOf(jdays)).divide(BigDecimal.valueOf(100)));// 收入

					} else {// 融资 项目
						sf.setFundType(FundIntentListPro.FinancingserviceMoney);// 资金类型
						sf.setPayMoney((financeOfRate.multiply(projectMoney))
								.multiply(BigDecimal.valueOf(jdays)).divide(BigDecimal.valueOf(100))); // 支出
						sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
					}

					sf.setRemark("");
					list.add(sf);
				}
				//获得管理咨询费和财务服务费 end by chencc
			}
			
		}
		if (payaccrualType.equals("halfYearPay") && isPreposePayAccrual == 1) {// 足半年头

			// 贷款收息
			Map<Integer, Integer> map = DateUtil.getMonthAndDaysBetweenDate(
					date1, date2);
			int months = map.get(1).intValue();
			if (months > 0) {
				for (int i = 0; i < months / 6; i++) {
					sf = new SlFundIntent();
					sf.setIntentDate(DateUtil.addMonthsToDate(DateUtil
							.parseDate(date1, "yyyy-MM-dd"), i * 6));
					if (calcutype.equals("SmallLoan")) { // 贷款项目
						sf.setFundType(FundIntentListPro.ProjectLoadAccrual);// 资金类型
						sf.setPayMoney(BigDecimal.valueOf(0));// 支出
						sf.setIncomeMoney(FundIntentListPro.calculAccrual(
								calcutype, dayProfit, accrualType,
								payaccrualType, accrual, projectMoney,
								commisionType, commisionMoney, dateMode, date1,
								date2));// 收入

					} else {// 融资 项目
						sf.setFundType(FundIntentListPro.FinancingInterest);// 资金类型
						sf.setPayMoney(FundIntentListPro.calculAccrual(
								calcutype, dayProfit, accrualType,
								payaccrualType, accrual, projectMoney,
								commisionType, commisionMoney, dateMode, date1,
								date2)); // 支出
						sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
					}
					sf.setRemark("");
					list.add(sf);
					
					//获得管理咨询费和财务服务费 start by chencc
					if(!managementConsultingOfRate.equals(BigDecimal.valueOf(0))){
						sf = new SlFundIntent();
						sf.setIntentDate(DateUtil.addMonthsToDate(DateUtil
								.parseDate(date1, "yyyy-MM-dd"), i * 6));
						if (calcutype.equals("SmallLoan")) { // 贷款项目
							sf.setFundType(FundIntentListPro.ProjectManagementConsulting);// 资金类型
							sf.setPayMoney(BigDecimal.valueOf(0));// 支出
							sf.setIncomeMoney(FundIntentListPro.calculAccrual(
									calcutype, managementOfRate, accrualType,
									payaccrualType, managementConsultingOfRate, projectMoney,
									commisionType, commisionMoney, dateMode, date1,
									date2));// 收入

						} else {// 融资 项目
							sf.setFundType(FundIntentListPro.FinancingconsultationMoney);// 资金类型
							sf.setPayMoney(FundIntentListPro.calculAccrual(
									calcutype, managementOfRate, accrualType,
									payaccrualType, managementConsultingOfRate, projectMoney,
									commisionType, commisionMoney, dateMode, date1,
									date2)); // 支出
							sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
						}
						sf.setRemark("");
						list.add(sf);
					}
					//---------------------------------
					if(!financeServiceOfRate.equals(BigDecimal.valueOf(0))){
						sf = new SlFundIntent();
						sf.setIntentDate(DateUtil.addMonthsToDate(DateUtil
								.parseDate(date1, "yyyy-MM-dd"), i * 6));
						if (calcutype.equals("SmallLoan")) { // 贷款项目
							sf.setFundType(FundIntentListPro.ProjectFinanceService);// 资金类型
							sf.setPayMoney(BigDecimal.valueOf(0));// 支出
							sf.setIncomeMoney(FundIntentListPro.calculAccrual(
									calcutype, financeOfRate, accrualType,
									payaccrualType, financeServiceOfRate, projectMoney,
									commisionType, commisionMoney, dateMode, date1,
									date2));// 收入

						} else {// 融资 项目
							sf.setFundType(FundIntentListPro.FinancingserviceMoney);// 资金类型
							sf.setPayMoney(FundIntentListPro.calculAccrual(
									calcutype, financeOfRate, accrualType,
									payaccrualType, financeServiceOfRate, projectMoney,
									commisionType, commisionMoney, dateMode, date1,
									date2)); // 支出
							sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
						}
						sf.setRemark("");
						list.add(sf);
					}
					//获得管理咨询费和财务服务费 end by chencc
				}
			}
			int mdays = map.get(2).intValue();
			if(mdays != 0){
				int bn = months / 6;
				int jdays = (months - bn * 6) * 30 + mdays;

				sf = new SlFundIntent();
				sf.setIntentDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1,
						"yyyy-MM-dd"), bn * 6));
				if (calcutype.equals("SmallLoan")) { // 贷款项目
					jdays = jdays + 1;
					sf.setFundType(FundIntentListPro.ProjectLoadAccrual);// 资金类型
					sf.setPayMoney(BigDecimal.valueOf(0));// 支出
					sf.setIncomeMoney((dayProfit.multiply(projectMoney))
							.multiply(BigDecimal.valueOf(jdays)).divide(BigDecimal.valueOf(100)));// 收入

				} else {// 融资 项目
					sf.setFundType(FundIntentListPro.FinancingInterest);// 资金类型
					sf.setPayMoney((dayProfit.multiply(projectMoney))
							.multiply(BigDecimal.valueOf(jdays)).divide(BigDecimal.valueOf(100))); // 支出
					sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
				}

				sf.setRemark("");
				list.add(sf);
				
				//获得管理咨询费和财务服务费 start by chencc
				if(!managementConsultingOfRate.equals(BigDecimal.valueOf(0))){
					sf = new SlFundIntent();
					sf.setIntentDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1,
					"yyyy-MM-dd"), bn * 6));

					if (calcutype.equals("SmallLoan")) { // 贷款项目
						sf.setFundType(FundIntentListPro.ProjectManagementConsulting);// 资金类型
						sf.setPayMoney(BigDecimal.valueOf(0));// 支出
						sf.setIncomeMoney((managementOfRate.multiply(projectMoney))
								.multiply(BigDecimal.valueOf(jdays)).divide(BigDecimal.valueOf(100)));// 收入

					} else {// 融资 项目
						sf.setFundType(FundIntentListPro.FinancingconsultationMoney);// 资金类型
						sf.setPayMoney((managementOfRate.multiply(projectMoney))
								.multiply(BigDecimal.valueOf(jdays)).divide(BigDecimal.valueOf(100))); // 支出
						sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
					}

					sf.setRemark("");
					list.add(sf);
				}
				//---------------------------------
				if(!financeServiceOfRate.equals(BigDecimal.valueOf(0))){
					sf = new SlFundIntent();
					sf.setIntentDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1,
					"yyyy-MM-dd"), bn * 6));

					if (calcutype.equals("SmallLoan")) { // 贷款项目
						sf.setFundType(FundIntentListPro.ProjectFinanceService);// 资金类型
						sf.setPayMoney(BigDecimal.valueOf(0));// 支出
						sf.setIncomeMoney((financeOfRate.multiply(projectMoney))
								.multiply(BigDecimal.valueOf(jdays)).divide(BigDecimal.valueOf(100)));// 收入

					} else {// 融资 项目
						sf.setFundType(FundIntentListPro.FinancingserviceMoney);// 资金类型
						sf.setPayMoney((financeOfRate.multiply(projectMoney))
								.multiply(BigDecimal.valueOf(jdays)).divide(BigDecimal.valueOf(100))); // 支出
						sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
					}

					sf.setRemark("");
					list.add(sf);
				}
				//获得管理咨询费和财务服务费 end by chencc
			}
			
		}
		if (payaccrualType.equals("halfYearPay") && isPreposePayAccrual == 0) {// 足半年尾

			// 贷款收息
			Map<Integer, Integer> map = DateUtil.getMonthAndDaysBetweenDate(
					date1, date2);
			int months = map.get(1).intValue();
			if (months > 0) {
				for (int i = 0; i < months / 6; i++) {
					sf = new SlFundIntent();
					sf.setIntentDate(DateUtil.addMonthsToDate(DateUtil
							.parseDate(date1, "yyyy-MM-dd"), (i + 1) * 6));
					if (calcutype.equals("SmallLoan")) { // 贷款项目
						sf.setFundType(FundIntentListPro.ProjectLoadAccrual);// 资金类型
						sf.setPayMoney(BigDecimal.valueOf(0));// 支出
						sf.setIncomeMoney(FundIntentListPro.calculAccrual(
								calcutype, dayProfit, accrualType,
								payaccrualType, accrual, projectMoney,
								commisionType, commisionMoney, dateMode, date1,
								date2));// 收入

					} else {// 融资 项目
						sf.setFundType(FundIntentListPro.FinancingInterest);// 资金类型
						sf.setPayMoney(FundIntentListPro.calculAccrual(
								calcutype, dayProfit, accrualType,
								payaccrualType, accrual, projectMoney,
								commisionType, commisionMoney, dateMode, date1,
								date2)); // 支出
						sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
					}
					sf.setRemark("");
					list.add(sf);
					
					//获得管理咨询费和财务服务费 start by chencc
					if(!managementConsultingOfRate.equals(BigDecimal.valueOf(0))){
						sf = new SlFundIntent();
						sf.setIntentDate(DateUtil.addMonthsToDate(DateUtil
								.parseDate(date1, "yyyy-MM-dd"), (i + 1) * 6));
						if (calcutype.equals("SmallLoan")) { // 贷款项目
							sf.setFundType(FundIntentListPro.ProjectManagementConsulting);// 资金类型
							sf.setPayMoney(BigDecimal.valueOf(0));// 支出
							sf.setIncomeMoney(FundIntentListPro.calculAccrual(
									calcutype, managementOfRate, accrualType,
									payaccrualType, managementConsultingOfRate, projectMoney,
									commisionType, commisionMoney, dateMode, date1,
									date2));// 收入

						} else {// 融资 项目
							sf.setFundType(FundIntentListPro.FinancingconsultationMoney);// 资金类型
							sf.setPayMoney(FundIntentListPro.calculAccrual(
									calcutype, managementOfRate, accrualType,
									payaccrualType, managementConsultingOfRate, projectMoney,
									commisionType, commisionMoney, dateMode, date1,
									date2)); // 支出
							sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
						}
						sf.setRemark("");
						list.add(sf);
					}
					//---------------------------------
					if(!financeServiceOfRate.equals(BigDecimal.valueOf(0))){
						sf = new SlFundIntent();
						sf.setIntentDate(DateUtil.addMonthsToDate(DateUtil
								.parseDate(date1, "yyyy-MM-dd"), (i + 1) * 6));
						if (calcutype.equals("SmallLoan")) { // 贷款项目
							sf.setFundType(FundIntentListPro.ProjectFinanceService);// 资金类型
							sf.setPayMoney(BigDecimal.valueOf(0));// 支出
							sf.setIncomeMoney(FundIntentListPro.calculAccrual(
									calcutype, financeOfRate, accrualType,
									payaccrualType, financeServiceOfRate, projectMoney,
									commisionType, commisionMoney, dateMode, date1,
									date2));// 收入

						} else {// 融资 项目
							sf.setFundType(FundIntentListPro.FinancingserviceMoney);// 资金类型
							sf.setPayMoney(FundIntentListPro.calculAccrual(
									calcutype, financeOfRate, accrualType,
									payaccrualType, financeServiceOfRate, projectMoney,
									commisionType, commisionMoney, dateMode, date1,
									date2)); // 支出
							sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
						}
						sf.setRemark("");
						list.add(sf);
					}
					//获得管理咨询费和财务服务费 end by chencc
				}
			}
			int mdays = map.get(2).intValue();
			if(mdays != 0){
				int bn = months / 6;
				int jdays = (months - bn * 6) * 30 + mdays;
				sf = new SlFundIntent();
				sf.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
				if (calcutype.equals("SmallLoan")) { // 贷款项目
					jdays = jdays + 1;
					sf.setFundType(FundIntentListPro.ProjectLoadAccrual);// 资金类型
					sf.setPayMoney(BigDecimal.valueOf(0));// 支出
					sf.setIncomeMoney((dayProfit.multiply(projectMoney))
							.multiply(BigDecimal.valueOf(jdays)).divide(BigDecimal.valueOf(100)));// 收入

				} else {// 融资 项目
					sf.setFundType(FundIntentListPro.FinancingInterest);// 资金类型
					sf.setPayMoney((dayProfit.multiply(projectMoney))
							.multiply(BigDecimal.valueOf(jdays)).divide(BigDecimal.valueOf(100))); // 支出
					sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
				}
				sf.setRemark("");
				list.add(sf);
				
				//获得管理咨询费和财务服务费 start by chencc
				if(!managementConsultingOfRate.equals(BigDecimal.valueOf(0))){
					sf = new SlFundIntent();
					sf.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));

					if (calcutype.equals("SmallLoan")) { // 贷款项目
						sf.setFundType(FundIntentListPro.ProjectManagementConsulting);// 资金类型
						sf.setPayMoney(BigDecimal.valueOf(0));// 支出
						sf.setIncomeMoney((managementOfRate.multiply(projectMoney))
								.multiply(BigDecimal.valueOf(jdays)).divide(BigDecimal.valueOf(100)));// 收入

					} else {// 融资 项目
						sf.setFundType(FundIntentListPro.FinancingconsultationMoney);// 资金类型
						sf.setPayMoney((managementOfRate.multiply(projectMoney))
								.multiply(BigDecimal.valueOf(jdays)).divide(BigDecimal.valueOf(100))); // 支出
						sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
					}

					sf.setRemark("");
					list.add(sf);
				}
				//---------------------------------
				if(!financeServiceOfRate.equals(BigDecimal.valueOf(0))){
					sf = new SlFundIntent();
					sf.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));

					if (calcutype.equals("SmallLoan")) { // 贷款项目
						sf.setFundType(FundIntentListPro.ProjectFinanceService);// 资金类型
						sf.setPayMoney(BigDecimal.valueOf(0));// 支出
						sf.setIncomeMoney((financeOfRate.multiply(projectMoney))
								.multiply(BigDecimal.valueOf(jdays)).divide(BigDecimal.valueOf(100)));// 收入

					} else {// 融资 项目
						sf.setFundType(FundIntentListPro.FinancingserviceMoney);// 资金类型
						sf.setPayMoney((financeOfRate.multiply(projectMoney))
								.multiply(BigDecimal.valueOf(jdays)).divide(BigDecimal.valueOf(100))); // 支出
						sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
					}

					sf.setRemark("");
					list.add(sf);
				}
				//获得管理咨询费和财务服务费 end by chencc
			}
			
		}
		if (payaccrualType.equals("yearPay") && isPreposePayAccrual == 1) {// 足年头

			// 贷款收息
			Map<Integer, Integer> map = DateUtil.getMonthAndDaysBetweenDate(
					date1, date2);
			int months = map.get(1).intValue();
			if (months > 0) {
				for (int i = 0; i < months / 12; i++) {
					sf = new SlFundIntent();
					sf.setIntentDate(DateUtil.addMonthsToDate(DateUtil
							.parseDate(date1, "yyyy-MM-dd"), i * 12));
					if (calcutype.equals("SmallLoan")) { // 贷款项目
						sf.setFundType(FundIntentListPro.ProjectLoadAccrual);// 资金类型
						sf.setPayMoney(BigDecimal.valueOf(0));// 支出
						sf.setIncomeMoney(FundIntentListPro.calculAccrual(
								calcutype, dayProfit, accrualType,
								payaccrualType, accrual, projectMoney,
								commisionType, commisionMoney, dateMode, date1,
								date2));// 收入

					} else {// 融资 项目
						sf.setFundType(FundIntentListPro.FinancingInterest);// 资金类型
						sf.setPayMoney(FundIntentListPro.calculAccrual(
								calcutype, dayProfit, accrualType,
								payaccrualType, accrual, projectMoney,
								commisionType, commisionMoney, dateMode, date1,
								date2)); // 支出
						sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
					}
					sf.setRemark("");
					list.add(sf);
					
					//获得管理咨询费和财务服务费 start by chencc
					if(!managementConsultingOfRate.equals(BigDecimal.valueOf(0))){
						sf = new SlFundIntent();
						sf.setIntentDate(DateUtil.addMonthsToDate(DateUtil
								.parseDate(date1, "yyyy-MM-dd"), i * 12));
						if (calcutype.equals("SmallLoan")) { // 贷款项目
							sf.setFundType(FundIntentListPro.ProjectManagementConsulting);// 资金类型
							sf.setPayMoney(BigDecimal.valueOf(0));// 支出
							sf.setIncomeMoney(FundIntentListPro.calculAccrual(
									calcutype, managementOfRate, accrualType,
									payaccrualType, managementConsultingOfRate, projectMoney,
									commisionType, commisionMoney, dateMode, date1,
									date2));// 收入

						} else {// 融资 项目
							sf.setFundType(FundIntentListPro.FinancingconsultationMoney);// 资金类型
							sf.setPayMoney(FundIntentListPro.calculAccrual(
									calcutype, managementOfRate, accrualType,
									payaccrualType, managementConsultingOfRate, projectMoney,
									commisionType, commisionMoney, dateMode, date1,
									date2)); // 支出
							sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
						}
						sf.setRemark("");
						list.add(sf);
					}
					//---------------------------------
					if(!financeServiceOfRate.equals(BigDecimal.valueOf(0))){
						sf = new SlFundIntent();
						sf.setIntentDate(DateUtil.addMonthsToDate(DateUtil
								.parseDate(date1, "yyyy-MM-dd"), i * 12));
						if (calcutype.equals("SmallLoan")) { // 贷款项目
							sf.setFundType(FundIntentListPro.ProjectFinanceService);// 资金类型
							sf.setPayMoney(BigDecimal.valueOf(0));// 支出
							sf.setIncomeMoney(FundIntentListPro.calculAccrual(
									calcutype, financeOfRate, accrualType,
									payaccrualType, financeServiceOfRate, projectMoney,
									commisionType, commisionMoney, dateMode, date1,
									date2));// 收入

						} else {// 融资 项目
							sf.setFundType(FundIntentListPro.FinancingserviceMoney);// 资金类型
							sf.setPayMoney(FundIntentListPro.calculAccrual(
									calcutype, financeOfRate, accrualType,
									payaccrualType, financeServiceOfRate, projectMoney,
									commisionType, commisionMoney, dateMode, date1,
									date2)); // 支出
							sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
						}
						sf.setRemark("");
						list.add(sf);
					}
					//获得管理咨询费和财务服务费 end by chencc
				}
			}
			int nt = months / 12;
			int mdays = map.get(2).intValue();
			if(mdays != 0){
				int jdays = (months - nt * 12) * 30 + mdays;
				sf = new SlFundIntent();
				sf.setIntentDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1,
						"yyyy-MM-dd"), nt * 12));
				/*if (calcutype.equals("SmallLoan")) { // 贷款项目
					jdays = jdays + 1;
					sf.setPayMoney(BigDecimal.valueOf(0));// 支出
					sf.setIncomeMoney(FundIntentListPro.calculAccrual(calcutype,
							dayProfit, accrualType, payaccrualType, accrual,
							projectMoney, commisionType, commisionMoney, dateMode,
							date1, date2));// 收入

				} else {// 融资 项目
					sf.setPayMoney(FundIntentListPro.calculAccrual(calcutype,
							dayProfit, accrualType, payaccrualType, accrual,
							projectMoney, commisionType, commisionMoney, dateMode,
							date1, date2)); // 支出
					sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
				}*/
				
				if (calcutype.equals("SmallLoan")) { // 贷款项目
					jdays = jdays + 1;
					sf.setFundType(FundIntentListPro.ProjectLoadAccrual);// 资金类型
					sf.setPayMoney(BigDecimal.valueOf(0));// 支出
					sf.setIncomeMoney((dayProfit.multiply(projectMoney))
							.multiply(BigDecimal.valueOf(jdays)).divide(BigDecimal.valueOf(100)));// 收入

				} else {// 融资 项目
					sf.setFundType(FundIntentListPro.FinancingInterest);// 资金类型
					sf.setPayMoney((dayProfit.multiply(projectMoney))
							.multiply(BigDecimal.valueOf(jdays)).divide(BigDecimal.valueOf(100))); // 支出
					sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
				}

				sf.setRemark("");
				list.add(sf);
				
				//获得管理咨询费和财务服务费 start by chencc
				if(!managementConsultingOfRate.equals(BigDecimal.valueOf(0))){
					sf = new SlFundIntent();
					sf.setIntentDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1,
					"yyyy-MM-dd"), nt * 12));

					if (calcutype.equals("SmallLoan")) { // 贷款项目
						sf.setFundType(FundIntentListPro.ProjectManagementConsulting);// 资金类型
						sf.setPayMoney(BigDecimal.valueOf(0));// 支出
						sf.setIncomeMoney((managementOfRate.multiply(projectMoney))
								.multiply(BigDecimal.valueOf(jdays)).divide(BigDecimal.valueOf(100)));// 收入

					} else {// 融资 项目
						sf.setFundType(FundIntentListPro.FinancingconsultationMoney);// 资金类型
						sf.setPayMoney((managementOfRate.multiply(projectMoney))
								.multiply(BigDecimal.valueOf(jdays)).divide(BigDecimal.valueOf(100))); // 支出
						sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
					}

					sf.setRemark("");
					list.add(sf);
				}
				//---------------------------------
				if(!financeServiceOfRate.equals(BigDecimal.valueOf(0))){
					sf = new SlFundIntent();
					sf.setIntentDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1,
					"yyyy-MM-dd"), nt * 12));

					if (calcutype.equals("SmallLoan")) { // 贷款项目
						sf.setFundType(FundIntentListPro.ProjectFinanceService);// 资金类型
						sf.setPayMoney(BigDecimal.valueOf(0));// 支出
						sf.setIncomeMoney((financeOfRate.multiply(projectMoney))
								.multiply(BigDecimal.valueOf(jdays)).divide(BigDecimal.valueOf(100)));// 收入

					} else {// 融资 项目
						sf.setFundType(FundIntentListPro.FinancingserviceMoney);// 资金类型
						sf.setPayMoney((financeOfRate.multiply(projectMoney))
								.multiply(BigDecimal.valueOf(jdays)).divide(BigDecimal.valueOf(100))); // 支出
						sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
					}

					sf.setRemark("");
					list.add(sf);
				}
				//获得管理咨询费和财务服务费 end by chencc
			}
			
		}
		if (payaccrualType.equals("yearPay") && isPreposePayAccrual == 0) {// 足年尾
			// //贷款贷出
			// sf=new SlFundIntent();
			// sf.setFundType(FundIntentListPro.ProjectLoadOut);// 资金类型
			// sf.setIntentDate(DateUtil.parseDate(date1, "yyyy-MM-dd"));
			// sf.setPayMoney(FundIntentListPro.calculAccrual(accrualType,
			// payaccrualType, accrual, projectMoney, commisionType,
			// commisionMoney,
			// dateMode, date1, date2));
			// sf.setIncomeMoney(BigDecimal.valueOf(0));
			// sf.setRemark("");
			// list.add(sf);
			// 贷款收息
			Map<Integer, Integer> map = DateUtil.getMonthAndDaysBetweenDate(
					date1, date2);
			int months = map.get(1).intValue();
			if (months > 0) {
				for (int i = 0; i < months / 12; i++) {
					sf = new SlFundIntent();
					sf.setIntentDate(DateUtil.addMonthsToDate(DateUtil
							.parseDate(date1, "yyyy-MM-dd"), (i + 1) * 12));
					if (calcutype.equals("SmallLoan")) { // 贷款项目
						sf.setFundType(FundIntentListPro.ProjectLoadAccrual);// 资金类型
						sf.setPayMoney(BigDecimal.valueOf(0));// 支出
						sf.setIncomeMoney(FundIntentListPro.calculAccrual(
								calcutype, dayProfit, accrualType,
								payaccrualType, accrual, projectMoney,
								commisionType, commisionMoney, dateMode, date1,
								date2));// 收入

					} else {// 融资 项目
						sf.setFundType(FundIntentListPro.FinancingInterest);// 资金类型
						sf.setPayMoney(FundIntentListPro.calculAccrual(
								calcutype, dayProfit, accrualType,
								payaccrualType, accrual, projectMoney,
								commisionType, commisionMoney, dateMode, date1,
								date2)); // 支出
						sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
					}
					sf.setRemark("");
					list.add(sf);
					
					//获得管理咨询费和财务服务费 start by chencc
					if(!managementConsultingOfRate.equals(BigDecimal.valueOf(0))){
						sf = new SlFundIntent();
						sf.setIntentDate(DateUtil.addMonthsToDate(DateUtil
								.parseDate(date1, "yyyy-MM-dd"), (i + 1) * 12));
						if (calcutype.equals("SmallLoan")) { // 贷款项目
							sf.setFundType(FundIntentListPro.ProjectManagementConsulting);// 资金类型
							sf.setPayMoney(BigDecimal.valueOf(0));// 支出
							sf.setIncomeMoney(FundIntentListPro.calculAccrual(
									calcutype, managementOfRate, accrualType,
									payaccrualType, managementConsultingOfRate, projectMoney,
									commisionType, commisionMoney, dateMode, date1,
									date2));// 收入

						} else {// 融资 项目
							sf.setFundType(FundIntentListPro.FinancingconsultationMoney);// 资金类型
							sf.setPayMoney(FundIntentListPro.calculAccrual(
									calcutype, managementOfRate, accrualType,
									payaccrualType, managementConsultingOfRate, projectMoney,
									commisionType, commisionMoney, dateMode, date1,
									date2)); // 支出
							sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
						}
						sf.setRemark("");
						list.add(sf);
					}
					//---------------------------------
					if(!financeServiceOfRate.equals(BigDecimal.valueOf(0))){
						sf = new SlFundIntent();
						sf.setIntentDate(DateUtil.addMonthsToDate(DateUtil
								.parseDate(date1, "yyyy-MM-dd"), (i + 1) * 12));
						if (calcutype.equals("SmallLoan")) { // 贷款项目
							sf.setFundType(FundIntentListPro.ProjectFinanceService);// 资金类型
							sf.setPayMoney(BigDecimal.valueOf(0));// 支出
							sf.setIncomeMoney(FundIntentListPro.calculAccrual(
									calcutype, financeOfRate, accrualType,
									payaccrualType, financeServiceOfRate, projectMoney,
									commisionType, commisionMoney, dateMode, date1,
									date2));// 收入

						} else {// 融资 项目
							sf.setFundType(FundIntentListPro.FinancingserviceMoney);// 资金类型
							sf.setPayMoney(FundIntentListPro.calculAccrual(
									calcutype, financeOfRate, accrualType,
									payaccrualType, financeServiceOfRate, projectMoney,
									commisionType, commisionMoney, dateMode, date1,
									date2)); // 支出
							sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
						}
						sf.setRemark("");
						list.add(sf);
					}
					//获得管理咨询费和财务服务费 end by chencc
				}
			}
			int nt = months / 12;
			int mdays = map.get(2).intValue();
			if(mdays != 0){
				int jdays = (months - nt * 12) * 30 + mdays;
				sf = new SlFundIntent();
				sf.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
				if (calcutype.equals("SmallLoan")) { // 贷款项目
					jdays = jdays + 1;
					sf.setFundType(FundIntentListPro.ProjectLoadAccrual);// 资金类型
					sf.setPayMoney(BigDecimal.valueOf(0));// 支出
					sf.setIncomeMoney((dayProfit.multiply(projectMoney))
							.multiply(BigDecimal.valueOf(jdays)).divide(BigDecimal.valueOf(100)));// 收入

				} else {// 融资 项目
					sf.setFundType(FundIntentListPro.FinancingInterest);// 资金类型
					sf.setPayMoney((dayProfit.multiply(projectMoney))
							.multiply(BigDecimal.valueOf(jdays)).divide(BigDecimal.valueOf(100))); // 支出
					sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
				}
				sf.setRemark("");
				list.add(sf);
				
				//获得管理咨询费和财务服务费 start by chencc
				if(!managementConsultingOfRate.equals(BigDecimal.valueOf(0))){
					sf = new SlFundIntent();
					sf.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));

					if (calcutype.equals("SmallLoan")) { // 贷款项目
						sf.setFundType(FundIntentListPro.ProjectManagementConsulting);// 资金类型
						sf.setPayMoney(BigDecimal.valueOf(0));// 支出
						sf.setIncomeMoney((managementOfRate.multiply(projectMoney))
								.multiply(BigDecimal.valueOf(jdays)).divide(BigDecimal.valueOf(100)));// 收入

					} else {// 融资 项目
						sf.setFundType(FundIntentListPro.FinancingconsultationMoney);// 资金类型
						sf.setPayMoney((managementOfRate.multiply(projectMoney))
								.multiply(BigDecimal.valueOf(jdays)).divide(BigDecimal.valueOf(100))); // 支出
						sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
					}

					sf.setRemark("");
					list.add(sf);
				}
				//---------------------------------
				if(!financeServiceOfRate.equals(BigDecimal.valueOf(0))){
					sf = new SlFundIntent();
					sf.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));

					if (calcutype.equals("SmallLoan")) { // 贷款项目
						sf.setFundType(FundIntentListPro.ProjectFinanceService);// 资金类型
						sf.setPayMoney(BigDecimal.valueOf(0));// 支出
						sf.setIncomeMoney((financeOfRate.multiply(projectMoney))
								.multiply(BigDecimal.valueOf(jdays)).divide(BigDecimal.valueOf(100)));// 收入

					} else {// 融资 项目
						sf.setFundType(FundIntentListPro.FinancingserviceMoney);// 资金类型
						sf.setPayMoney((financeOfRate.multiply(projectMoney))
								.multiply(BigDecimal.valueOf(jdays)).divide(BigDecimal.valueOf(100))); // 支出
						sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
					}

					sf.setRemark("");
					list.add(sf);
				}
				//获得管理咨询费和财务服务费 end by chencc
				// 佣金
				// sf=new SlFundIntent();
				// sf.setFundType(FundIntentListPro.ProjectLoadCommisin);// 资金类型
				// if(payaccrualType==1){ //佣金方式为1.随收息支付或
				// sf.setIntentDate(DateUtil.parseDate(date1, "yyyy-MM-dd"));
				// }else if(payaccrualType==2){//2.项目放款支付
				// sf.setIntentDate(DateUtil.parseDate(date1, "yyyy-MM-dd"));
				// }else{ //3.项目结款支付
				// sf.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
				// }
				// sf.setPayMoney(commisionMoney);
				// sf.setIncomeMoney(BigDecimal.valueOf(0));
				// sf.setRemark("");
				// list.add(sf);
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
			if (d2.compareTo(d1) >= 0) {
				Calendar calendar = Calendar.getInstance();   
		        calendar.setTime(d1);
		        Calendar calendar2 = Calendar.getInstance();   
		        calendar2.setTime(d2);
		        int y1_1 = calendar.get(Calendar.YEAR);
		        int y2_1 = calendar2.get(Calendar.YEAR);
		        /*
		        int dm1 = calendar.get(Calendar.MONTH);
		        String dm1_1 = "";
		        if(dm1<10){dm1_1 = "0"+dm1;}else{dm1_1 = String.valueOf(dm1);}
		        int dm2 = calendar2.get(Calendar.MONTH);
		        String dm2_1 = "";
		        if(dm2<10){dm2_1 = "0"+dm2;}else{dm2_1 = String.valueOf(dm2);}
		        int dd1 = calendar.get(Calendar.DAY_OF_MONTH);
		        String dd1_1 = "";
		        if(dd1<10){dd1_1 = "0"+dd1;}else{dd1_1 = String.valueOf(dd1);}
		        int dd2 = calendar2.get(Calendar.DAY_OF_MONTH);
		        String dd2_1 = "";
		        if(dd2<10){dd2_1 = "0"+dd2;}else{dd2_1 = String.valueOf(dd2);}*/

				int y1 = d1.getYear();
				int y2 = d2.getYear();
				int dm1 = d1.getMonth();// 起始日期月份
				int dm2 = d2.getMonth();// 结束日期月份
				int dd1 = d1.getDate(); // 起始日期天
				int dd2 = d2.getDate(); // 结束日期天
				sf = new SlFundIntent();
				sf.setIntentDate(DateUtil.parseDate(date1, "yyyy-MM-dd"));
				
				Map<Integer, Integer> map_ = DateUtil.getMonthAndDaysBetweenDate(
						date1, date2);
				int months_ = map_.get(1).intValue();
				int mdays = 0;
				if(months_ > 0){
					if(dm1 + 1 < 10){
						mdays = DateUtil.getDaysBetweenDate(date1, String.valueOf(y1_1)+ "-0"+String.valueOf(dm1 + 2) + "-01");
					}else{
						mdays = DateUtil.getDaysBetweenDate(date1, String
								.valueOf(y1_1)
								+ "-"+String.valueOf(dm1 + 2) + "-01");
					}
				}else{
					mdays = DateUtil.getDaysBetweenDate(date1, date2);
				}
				
				if (calcutype.equals("SmallLoan")) { // 贷款项目
					//mdays = mdays + 1;
					sf.setFundType(FundIntentListPro.ProjectLoadAccrual);// 资金类型
					sf.setPayMoney(BigDecimal.valueOf(0));// 支出
					sf.setIncomeMoney((dayProfit.multiply(projectMoney))
							.multiply(BigDecimal.valueOf(mdays)).divide(BigDecimal.valueOf(100)));// 收入

				} else {// 融资 项目
					sf.setFundType(FundIntentListPro.FinancingInterest);// 资金类型
					sf.setPayMoney((dayProfit.multiply(projectMoney))
							.multiply(BigDecimal.valueOf(mdays)).divide(BigDecimal.valueOf(100))); // 支出
					sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
				}
				sf.setRemark("");
				list.add(sf);
				
				//获得管理咨询费和财务服务费 start by chencc
				if(!managementConsultingOfRate.equals(BigDecimal.valueOf(0))){
					sf = new SlFundIntent();
					sf.setIntentDate(DateUtil.parseDate(date1, "yyyy-MM-dd"));
					if (calcutype.equals("SmallLoan")) { // 贷款项目
						sf.setFundType(FundIntentListPro.ProjectManagementConsulting);// 资金类型
						sf.setPayMoney(BigDecimal.valueOf(0));// 支出
						sf.setIncomeMoney((managementOfRate.multiply(projectMoney))
								.multiply(BigDecimal.valueOf(mdays)).divide(BigDecimal.valueOf(100)));// 收入
					} else {// 融资 项目
						sf.setFundType(FundIntentListPro.FinancingconsultationMoney);// 资金类型
						sf.setPayMoney((managementOfRate.multiply(projectMoney))
								.multiply(BigDecimal.valueOf(mdays)).divide(BigDecimal.valueOf(100))); // 支出
						sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
					}
					sf.setRemark("");
					list.add(sf);
				}
				//---------------------------------
				if(!financeServiceOfRate.equals(BigDecimal.valueOf(0))){
					sf = new SlFundIntent();
					sf.setIntentDate(DateUtil.parseDate(date1, "yyyy-MM-dd"));
					if (calcutype.equals("SmallLoan")) { // 贷款项目
						sf.setFundType(FundIntentListPro.ProjectFinanceService);// 资金类型
						sf.setPayMoney(BigDecimal.valueOf(0));// 支出
						sf.setIncomeMoney((financeOfRate.multiply(projectMoney))
								.multiply(BigDecimal.valueOf(mdays)).divide(BigDecimal.valueOf(100)));// 收入

					} else {// 融资 项目
						sf.setFundType(FundIntentListPro.FinancingserviceMoney);// 资金类型
						sf.setPayMoney((financeOfRate.multiply(projectMoney))
								.multiply(BigDecimal.valueOf(mdays)).divide(BigDecimal.valueOf(100))); // 支出
						sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
					}
					sf.setRemark("");
					list.add(sf);
				}
				//获得管理咨询费和财务服务费 end by chencc

				int months = d2.getMonth() - d1.getMonth() + (y2 - y1) * 12;
//				int months = calendar2.get(Calendar.MONTH) - calendar.get(Calendar.MONTH) + (y2 - y1) * 12;
				for (int i = 0; i < months-1; i++) {
					Date d11 = new Date(y1, dm1 + i + 1, 01);
					if (d11.compareTo(d2) < 0) {
						sf = new SlFundIntent();
						sf.setIntentDate(d11);
						if (calcutype.equals("SmallLoan")) { // 贷款项目
							sf.setFundType(FundIntentListPro.ProjectLoadAccrual);// 资金类型
							sf.setPayMoney(BigDecimal.valueOf(0));// 支出
							sf.setIncomeMoney(FundIntentListPro.calculAccrual(
									calcutype, dayProfit, accrualType,
									payaccrualType, accrual, projectMoney,
									commisionType, commisionMoney, dateMode,
									date1, date2));// 收入

						} else {// 融资 项目
							sf.setFundType(FundIntentListPro.FinancingInterest);// 资金类型
							sf.setPayMoney(FundIntentListPro.calculAccrual(
									calcutype, dayProfit, accrualType,
									payaccrualType, accrual, projectMoney,
									commisionType, commisionMoney, dateMode,
									date1, date2)); // 支出
							sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
						}
						sf.setRemark("");
						list.add(sf);
						
						//获得管理咨询费和财务服务费 start by chencc
						if(!managementConsultingOfRate.equals(BigDecimal.valueOf(0))){
							sf = new SlFundIntent();
							sf.setIntentDate(d11);
							if (calcutype.equals("SmallLoan")) { // 贷款项目
								sf.setFundType(FundIntentListPro.ProjectManagementConsulting);// 资金类型
								sf.setPayMoney(BigDecimal.valueOf(0));// 支出
								sf.setIncomeMoney(FundIntentListPro.calculAccrual(
										calcutype, managementOfRate, accrualType,
										payaccrualType, managementConsultingOfRate, projectMoney,
										commisionType, commisionMoney, dateMode, date1,
										date2));// 收入

							} else {// 融资 项目
								sf.setFundType(FundIntentListPro.FinancingconsultationMoney);// 资金类型
								sf.setPayMoney(FundIntentListPro.calculAccrual(
										calcutype, managementOfRate, accrualType,
										payaccrualType, managementConsultingOfRate, projectMoney,
										commisionType, commisionMoney, dateMode, date1,
										date2)); // 支出
								sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
							}
							sf.setRemark("");
							list.add(sf);
						}
						//---------------------------------
						if(!financeServiceOfRate.equals(BigDecimal.valueOf(0))){
							sf = new SlFundIntent();
							sf.setIntentDate(d11);
							if (calcutype.equals("SmallLoan")) { // 贷款项目
								sf.setFundType(FundIntentListPro.ProjectFinanceService);// 资金类型
								sf.setPayMoney(BigDecimal.valueOf(0));// 支出
								sf.setIncomeMoney(FundIntentListPro.calculAccrual(
										calcutype, financeOfRate, accrualType,
										payaccrualType, financeServiceOfRate, projectMoney,
										commisionType, commisionMoney, dateMode, date1,
										date2));// 收入

							} else {// 融资 项目
								sf.setFundType(FundIntentListPro.FinancingserviceMoney);// 资金类型
								sf.setPayMoney(FundIntentListPro.calculAccrual(
										calcutype, financeOfRate, accrualType,
										payaccrualType, financeServiceOfRate, projectMoney,
										commisionType, commisionMoney, dateMode, date1,
										date2)); // 支出
								sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
							}
							sf.setRemark("");
							list.add(sf);
						}
						//获得管理咨询费和财务服务费 end by chencc
					}
				}
				if(months != 0){
					int mdays_ = 0;
					String date1_ = "";
					if(dm2 + 1 < 10){
						date1_ = String.valueOf(y2_1)+ "-0"+String.valueOf(dm2 + 1) + "-01";
						mdays_ = DateUtil.getDaysBetweenDate(date1_,date2);
					}else{
						date1_ = String.valueOf(y2_1)+ "-"+String.valueOf(dm2 + 1) + "-01";
						mdays_ = DateUtil.getDaysBetweenDate(date1_,date2);
					}
					if(mdays_ > 0){
						sf = new SlFundIntent();
						sf.setIntentDate(DateUtil.parseDate(date1_, "yyyy-MM-dd"));
						if (calcutype.equals("SmallLoan")) { // 贷款项目
							//mdays_ = mdays_ + 1;
							sf.setFundType(FundIntentListPro.ProjectLoadAccrual);// 资金类型
							sf.setPayMoney(BigDecimal.valueOf(0));// 支出
							sf.setIncomeMoney((dayProfit.multiply(projectMoney))
									.multiply(BigDecimal.valueOf(mdays_)).divide(BigDecimal.valueOf(100)));// 收入

						} else {// 融资 项目
							sf.setFundType(FundIntentListPro.FinancingInterest);// 资金类型
							sf.setPayMoney((dayProfit.multiply(projectMoney))
									.multiply(BigDecimal.valueOf(mdays_)).divide(BigDecimal.valueOf(100))); // 支出
							sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
						}
						sf.setRemark("");
						list.add(sf);
						
						//获得管理咨询费和财务服务费 start by chencc
						if(!managementConsultingOfRate.equals(BigDecimal.valueOf(0))){
							sf = new SlFundIntent();
							sf.setIntentDate(DateUtil.parseDate(date1_, "yyyy-MM-dd"));
							if (calcutype.equals("SmallLoan")) { // 贷款项目
								sf.setFundType(FundIntentListPro.ProjectManagementConsulting);// 资金类型
								sf.setPayMoney(BigDecimal.valueOf(0));// 支出
								sf.setIncomeMoney((managementOfRate.multiply(projectMoney))
										.multiply(BigDecimal.valueOf(mdays_)).divide(BigDecimal.valueOf(100)));// 收入
							} else {// 融资 项目
								sf.setFundType(FundIntentListPro.FinancingconsultationMoney);// 资金类型
								sf.setPayMoney((managementOfRate.multiply(projectMoney))
										.multiply(BigDecimal.valueOf(mdays_)).divide(BigDecimal.valueOf(100))); // 支出
								sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
							}
							sf.setRemark("");
							list.add(sf);
						}
						//---------------------------------
						if(!financeServiceOfRate.equals(BigDecimal.valueOf(0))){
							sf = new SlFundIntent();
							sf.setIntentDate(DateUtil.parseDate(date1_, "yyyy-MM-dd"));
							if (calcutype.equals("SmallLoan")) { // 贷款项目
								sf.setFundType(FundIntentListPro.ProjectFinanceService);// 资金类型
								sf.setPayMoney(BigDecimal.valueOf(0));// 支出
								sf.setIncomeMoney((financeOfRate.multiply(projectMoney))
										.multiply(BigDecimal.valueOf(mdays_)).divide(BigDecimal.valueOf(100)));// 收入

							} else {// 融资 项目
								sf.setFundType(FundIntentListPro.FinancingserviceMoney);// 资金类型
								sf.setPayMoney((financeOfRate.multiply(projectMoney))
										.multiply(BigDecimal.valueOf(mdays_)).divide(BigDecimal.valueOf(100))); // 支出
								sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
							}
							sf.setRemark("");
							list.add(sf);
						}
						//获得管理咨询费和财务服务费 end by chencc
					}
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
	        /*
	        int dm1 = calendar.get(Calendar.MONTH);
	        String dm1_1 = "";
	        if(dm1<10){dm1_1 = "0"+dm1;}else{dm1_1 = String.valueOf(dm1);}
	        int dm2 = calendar2.get(Calendar.MONTH);
	        String dm2_1 = "";
	        if(dm2<10){dm2_1 = "0"+dm2;}else{dm2_1 = String.valueOf(dm2);}
	        int dd1 = calendar.get(Calendar.DAY_OF_MONTH);
	        String dd1_1 = "";
	        if(dd1<10){dd1_1 = "0"+dd1;}else{dd1_1 = String.valueOf(dd1);}
	        int dd2 = calendar2.get(Calendar.DAY_OF_MONTH);
	        String dd2_1 = "";
	        if(dd2<10){dd2_1 = "0"+dd2;}else{dd2_1 = String.valueOf(dd2);}*/

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
						if(i == 0){
							sf = new SlFundIntent();
							sf.setIntentDate(d11);
							
							Map<Integer, Integer> map_ = DateUtil.getMonthAndDaysBetweenDate(
									date1, date2);
							int months_ = map_.get(1).intValue();
							int mdays = 0;
							if(dm1 + 1 < 10){
								mdays = DateUtil.getDaysBetweenDate(date1,String.valueOf(y2_2)+ "-0"+String.valueOf(dm1 + 2) + "-01");
							}else{
								mdays = DateUtil.getDaysBetweenDate(date1,String.valueOf(y2_2)+ "-0"+String.valueOf(dm1 + 2) + "-01");
							}
							if (calcutype.equals("SmallLoan")) { // 贷款项目
								//mdays = mdays + 1;
								sf.setFundType(FundIntentListPro.ProjectLoadAccrual);// 资金类型
								sf.setPayMoney(BigDecimal.valueOf(0));// 支出
								sf.setIncomeMoney((dayProfit.multiply(projectMoney))
										.multiply(BigDecimal.valueOf(mdays)).divide(BigDecimal.valueOf(100)));// 收入

							} else {// 融资 项目
								sf.setFundType(FundIntentListPro.FinancingInterest);// 资金类型
								sf.setPayMoney((dayProfit.multiply(projectMoney))
										.multiply(BigDecimal.valueOf(mdays)).divide(BigDecimal.valueOf(100))); // 支出
								sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
							}
							sf.setRemark("");
							list.add(sf);
							
							//获得管理咨询费和财务服务费 start by chencc
							if(!managementConsultingOfRate.equals(BigDecimal.valueOf(0))){
								sf = new SlFundIntent();
								sf.setIntentDate(d11);
								if (calcutype.equals("SmallLoan")) { // 贷款项目
									sf.setFundType(FundIntentListPro.ProjectManagementConsulting);// 资金类型
									sf.setPayMoney(BigDecimal.valueOf(0));// 支出
									sf.setIncomeMoney((managementOfRate.multiply(projectMoney))
											.multiply(BigDecimal.valueOf(mdays)).divide(BigDecimal.valueOf(100)));// 收入
								} else {// 融资 项目
									sf.setFundType(FundIntentListPro.FinancingconsultationMoney);// 资金类型
									sf.setPayMoney((managementOfRate.multiply(projectMoney))
											.multiply(BigDecimal.valueOf(mdays)).divide(BigDecimal.valueOf(100))); // 支出
									sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
								}
								sf.setRemark("");
								list.add(sf);
							}
							//---------------------------------
							if(!financeServiceOfRate.equals(BigDecimal.valueOf(0))){
								sf = new SlFundIntent();
								sf.setIntentDate(d11);
								if (calcutype.equals("SmallLoan")) { // 贷款项目
									sf.setFundType(FundIntentListPro.ProjectFinanceService);// 资金类型
									sf.setPayMoney(BigDecimal.valueOf(0));// 支出
									sf.setIncomeMoney((financeOfRate.multiply(projectMoney))
											.multiply(BigDecimal.valueOf(mdays)).divide(BigDecimal.valueOf(100)));// 收入

								} else {// 融资 项目
									sf.setFundType(FundIntentListPro.FinancingserviceMoney);// 资金类型
									sf.setPayMoney((financeOfRate.multiply(projectMoney))
											.multiply(BigDecimal.valueOf(mdays)).divide(BigDecimal.valueOf(100))); // 支出
									sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
								}
								sf.setRemark("");
								list.add(sf);
							}
							//获得管理咨询费和财务服务费 end by chencc
						}else{
							sf = new SlFundIntent();
							sf.setIntentDate(d11);
							if (calcutype.equals("SmallLoan")) { // 贷款项目
								sf.setFundType(FundIntentListPro.ProjectLoadAccrual);// 资金类型
								sf.setPayMoney(BigDecimal.valueOf(0));// 支出
								sf.setIncomeMoney(FundIntentListPro.calculAccrual(
										calcutype, dayProfit, accrualType,
										payaccrualType, accrual, projectMoney,
										commisionType, commisionMoney, dateMode,
										date1, date2));// 收入

							} else {// 融资 项目
								sf.setFundType(FundIntentListPro.FinancingInterest);// 资金类型
								sf.setPayMoney(FundIntentListPro.calculAccrual(
										calcutype, dayProfit, accrualType,
										payaccrualType, accrual, projectMoney,
										commisionType, commisionMoney, dateMode,
										date1, date2)); // 支出
								sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
							}
							sf.setRemark("");
							list.add(sf);
							
							//获得管理咨询费和财务服务费 start by chencc
							if(!managementConsultingOfRate.equals(BigDecimal.valueOf(0))){
								sf = new SlFundIntent();
								sf.setIntentDate(d11);
								if (calcutype.equals("SmallLoan")) { // 贷款项目
									sf.setFundType(FundIntentListPro.ProjectManagementConsulting);// 资金类型
									sf.setPayMoney(BigDecimal.valueOf(0));// 支出
									sf.setIncomeMoney(FundIntentListPro.calculAccrual(
											calcutype, managementOfRate, accrualType,
											payaccrualType, managementConsultingOfRate, projectMoney,
											commisionType, commisionMoney, dateMode, date1,
											date2));// 收入

								} else {// 融资 项目
									sf.setFundType(FundIntentListPro.FinancingconsultationMoney);// 资金类型
									sf.setPayMoney(FundIntentListPro.calculAccrual(
											calcutype, managementOfRate, accrualType,
											payaccrualType, managementConsultingOfRate, projectMoney,
											commisionType, commisionMoney, dateMode, date1,
											date2)); // 支出
									sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
								}
								sf.setRemark("");
								list.add(sf);
							}
							//---------------------------------
							if(!financeServiceOfRate.equals(BigDecimal.valueOf(0))){
								sf = new SlFundIntent();
								sf.setIntentDate(d11);
								if (calcutype.equals("SmallLoan")) { // 贷款项目
									sf.setFundType(FundIntentListPro.ProjectFinanceService);// 资金类型
									sf.setPayMoney(BigDecimal.valueOf(0));// 支出
									sf.setIncomeMoney(FundIntentListPro.calculAccrual(
											calcutype, financeOfRate, accrualType,
											payaccrualType, financeServiceOfRate, projectMoney,
											commisionType, commisionMoney, dateMode, date1,
											date2));// 收入

								} else {// 融资 项目
									sf.setFundType(FundIntentListPro.FinancingserviceMoney);// 资金类型
									sf.setPayMoney(FundIntentListPro.calculAccrual(
											calcutype, financeOfRate, accrualType,
											payaccrualType, financeServiceOfRate, projectMoney,
											commisionType, commisionMoney, dateMode, date1,
											date2)); // 支出
									sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
								}
								sf.setRemark("");
								list.add(sf);
							}
							//获得管理咨询费和财务服务费 end by chencc
						}
						
					}
				}

			}

			sf = new SlFundIntent();
			sf.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
//			int mdays = DateUtil.getDaysBetweenDate(String.valueOf(y2)
//					+ String.valueOf(dm2) + "01", date2);
			int mdays = 0;
			if(dm1 + 1 < 10){
				mdays = DateUtil.getDaysBetweenDate(String
						.valueOf(y2_2)
						+ "-0"+String.valueOf(dm2+1) + "-01", date2);
			}else{
				mdays = DateUtil.getDaysBetweenDate(String
						.valueOf(y2_2)
						+ "-"+String.valueOf(dm2+1) + "-01", date2);
			}
			if (calcutype.equals("SmallLoan")) { // 贷款项目
				//mdays = mdays + 1;
				sf.setFundType(FundIntentListPro.ProjectLoadAccrual);// 资金类型
				sf.setPayMoney(BigDecimal.valueOf(0));// 支出
				sf.setIncomeMoney((dayProfit.multiply(projectMoney))
						.multiply(BigDecimal.valueOf(mdays)).divide(BigDecimal.valueOf(100)));// 收入

			} else {// 融资 项目
				sf.setFundType(FundIntentListPro.FinancingInterest);// 资金类型
				sf.setPayMoney((dayProfit.multiply(projectMoney))
						.multiply(BigDecimal.valueOf(mdays)).divide(BigDecimal.valueOf(100))); // 支出
				sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
			}
			sf.setRemark("");
			list.add(sf);
			
			//获得管理咨询费和财务服务费 start by chencc
			if(!managementConsultingOfRate.equals(BigDecimal.valueOf(0))){
				sf = new SlFundIntent();
				sf.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));

				if (calcutype.equals("SmallLoan")) { // 贷款项目
					sf.setFundType(FundIntentListPro.ProjectManagementConsulting);// 资金类型
					sf.setPayMoney(BigDecimal.valueOf(0));// 支出
					sf.setIncomeMoney((managementOfRate.multiply(projectMoney))
							.multiply(BigDecimal.valueOf(mdays)).divide(BigDecimal.valueOf(100)));// 收入

				} else {// 融资 项目
					sf.setFundType(FundIntentListPro.FinancingconsultationMoney);// 资金类型
					sf.setPayMoney((managementOfRate.multiply(projectMoney))
							.multiply(BigDecimal.valueOf(mdays)).divide(BigDecimal.valueOf(100))); // 支出
					sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
				}

				sf.setRemark("");
				list.add(sf);
			}
			//---------------------------------
			if(!financeServiceOfRate.equals(BigDecimal.valueOf(0))){
				sf = new SlFundIntent();
				sf.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));

				if (calcutype.equals("SmallLoan")) { // 贷款项目
					sf.setFundType(FundIntentListPro.ProjectFinanceService);// 资金类型
					sf.setPayMoney(BigDecimal.valueOf(0));// 支出
					sf.setIncomeMoney((financeOfRate.multiply(projectMoney))
							.multiply(BigDecimal.valueOf(mdays)).divide(BigDecimal.valueOf(100)));// 收入

				} else {// 融资 项目
					sf.setFundType(FundIntentListPro.FinancingserviceMoney);// 资金类型
					sf.setPayMoney((financeOfRate.multiply(projectMoney))
							.multiply(BigDecimal.valueOf(mdays)).divide(BigDecimal.valueOf(100))); // 支出
					sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
				}

				sf.setRemark("");
				list.add(sf);
			}
			//获得管理咨询费和财务服务费 end by chencc
		}
		if (payaccrualType.equals("abcdef") && isPreposePayAccrual == 1) {// 日历季头

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
			if (d2.compareTo(d1) >= 0) {
				int y1 = d1.getYear();
				int y2 = d2.getYear();
				int dm1 = d2.getMonth();// 起始日期月份
				int dm2 = d2.getMonth();// 结束日期月份
				int dd1 = d1.getDate(); // 起始日期天
				int dd2 = d2.getDate(); // 结束日期天
				sf = new SlFundIntent();
				sf.setIntentDate(DateUtil.parseDate(date1, "yyyy-MM-dd"));
				if (calcutype.equals("SmallLoan")) { // 贷款项目
					sf.setFundType(FundIntentListPro.ProjectLoadAccrual);// 资金类型
					sf.setPayMoney(BigDecimal.valueOf(0));// 支出
					sf.setIncomeMoney(FundIntentListPro.calculAccrual(
							calcutype, dayProfit, accrualType, payaccrualType,
							accrual, projectMoney, commisionType,
							commisionMoney, dateMode, date1, date2));// 收入

				} else {// 融资 项目
					sf.setFundType(FundIntentListPro.FinancingInterest);// 资金类型
					sf.setPayMoney(FundIntentListPro.calculAccrual(calcutype,
							dayProfit, accrualType, payaccrualType, accrual,
							projectMoney, commisionType, commisionMoney,
							dateMode, date1, date2)); // 支出
					sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
				}
				sf.setRemark("");
				list.add(sf);

				int months = d2.getMonth() - d1.getMonth() + (y2 - y1) * 12;

				for (int i = 0; i < months / 3; i++) {
					Date d11 = new Date(y1, dm1 + i * 3 + 1, 01);
					if (d11.compareTo(d2) < 0) {
						sf = new SlFundIntent();
						sf.setIntentDate(d11);
						if (calcutype.equals("SmallLoan")) { // 贷款项目
							sf.setFundType(FundIntentListPro.ProjectLoadAccrual);// 资金类型
							sf.setPayMoney(BigDecimal.valueOf(0));// 支出
							sf.setIncomeMoney(FundIntentListPro.calculAccrual(
									calcutype, dayProfit, accrualType,
									payaccrualType, accrual, projectMoney,
									commisionType, commisionMoney, dateMode,
									date1, date2));// 收入

						} else {// 融资 项目
							sf.setFundType(FundIntentListPro.FinancingInterest);// 资金类型
							sf.setPayMoney(FundIntentListPro.calculAccrual(
									calcutype, dayProfit, accrualType,
									payaccrualType, accrual, projectMoney,
									commisionType, commisionMoney, dateMode,
									date1, date2)); // 支出
							sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
						}
						sf.setRemark("");
						list.add(sf);
						
						//获得管理咨询费和财务服务费 start by chencc
						if(!managementConsultingOfRate.equals(BigDecimal.valueOf(0))){
							sf = new SlFundIntent();
							sf.setIntentDate(d11);
							if (calcutype.equals("SmallLoan")) { // 贷款项目
								sf.setFundType(FundIntentListPro.ProjectManagementConsulting);// 资金类型
								sf.setPayMoney(BigDecimal.valueOf(0));// 支出
								sf.setIncomeMoney(FundIntentListPro.calculAccrual(
										calcutype, managementOfRate, accrualType,
										payaccrualType, managementConsultingOfRate, projectMoney,
										commisionType, commisionMoney, dateMode, date1,
										date2));// 收入

							} else {// 融资 项目
								sf.setFundType(FundIntentListPro.FinancingconsultationMoney);// 资金类型
								sf.setPayMoney(FundIntentListPro.calculAccrual(
										calcutype, managementOfRate, accrualType,
										payaccrualType, managementConsultingOfRate, projectMoney,
										commisionType, commisionMoney, dateMode, date1,
										date2)); // 支出
								sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
							}
							sf.setRemark("");
							list.add(sf);
						}
						//---------------------------------
						if(!financeServiceOfRate.equals(BigDecimal.valueOf(0))){
							sf = new SlFundIntent();
							sf.setIntentDate(d11);
							if (calcutype.equals("SmallLoan")) { // 贷款项目
								sf.setFundType(FundIntentListPro.ProjectFinanceService);// 资金类型
								sf.setPayMoney(BigDecimal.valueOf(0));// 支出
								sf.setIncomeMoney(FundIntentListPro.calculAccrual(
										calcutype, financeOfRate, accrualType,
										payaccrualType, financeServiceOfRate, projectMoney,
										commisionType, commisionMoney, dateMode, date1,
										date2));// 收入

							} else {// 融资 项目
								sf.setFundType(FundIntentListPro.FinancingserviceMoney);// 资金类型
								sf.setPayMoney(FundIntentListPro.calculAccrual(
										calcutype, financeOfRate, accrualType,
										payaccrualType, financeServiceOfRate, projectMoney,
										commisionType, commisionMoney, dateMode, date1,
										date2)); // 支出
								sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
							}
							sf.setRemark("");
							list.add(sf);
						}
						//获得管理咨询费和财务服务费 end by chencc
					}
				}

			}

		}
		if (payaccrualType.equals("abcdef") && isPreposePayAccrual == 0) {// 日历季尾

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
			if (d2.compareTo(d1) >= 0) {
				int y1 = d1.getYear();
				int y2 = d2.getYear();
				int dm1 = d2.getMonth();// 起始日期月份
				int dm2 = d2.getMonth();// 结束日期月份
				int dd1 = d1.getDate(); // 起始日期天
				int dd2 = d2.getDate(); // 结束日期天

				int months = d2.getMonth() - d1.getMonth() + (y2 - y1) * 12;

				for (int i = 0; i < months / 3; i++) {
					Date d11 = new Date(y1, dm1 + i * 3 + 1, 01);
					if (d11.compareTo(d2) < 0) {
						sf = new SlFundIntent();
						sf.setIntentDate(d11);
						if (calcutype.equals("SmallLoan")) { // 贷款项目
							sf.setFundType(FundIntentListPro.ProjectLoadAccrual);// 资金类型
							sf.setPayMoney(BigDecimal.valueOf(0));// 支出
							sf.setIncomeMoney(FundIntentListPro.calculAccrual(
									calcutype, dayProfit, accrualType,
									payaccrualType, accrual, projectMoney,
									commisionType, commisionMoney, dateMode,
									date1, date2));// 收入

						} else {// 融资 项目
							sf.setFundType(FundIntentListPro.FinancingInterest);// 资金类型
							sf.setPayMoney(FundIntentListPro.calculAccrual(
									calcutype, dayProfit, accrualType,
									payaccrualType, accrual, projectMoney,
									commisionType, commisionMoney, dateMode,
									date1, date2)); // 支出
							sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
						}
						sf.setRemark("");
						list.add(sf);
						
						//获得管理咨询费和财务服务费 start by chencc
						if(!managementConsultingOfRate.equals(BigDecimal.valueOf(0))){
							sf = new SlFundIntent();
							sf.setIntentDate(d11);
							if (calcutype.equals("SmallLoan")) { // 贷款项目
								sf.setFundType(FundIntentListPro.ProjectManagementConsulting);// 资金类型
								sf.setPayMoney(BigDecimal.valueOf(0));// 支出
								sf.setIncomeMoney(FundIntentListPro.calculAccrual(
										calcutype, managementOfRate, accrualType,
										payaccrualType, managementConsultingOfRate, projectMoney,
										commisionType, commisionMoney, dateMode, date1,
										date2));// 收入

							} else {// 融资 项目
								sf.setFundType(FundIntentListPro.FinancingconsultationMoney);// 资金类型
								sf.setPayMoney(FundIntentListPro.calculAccrual(
										calcutype, managementOfRate, accrualType,
										payaccrualType, managementConsultingOfRate, projectMoney,
										commisionType, commisionMoney, dateMode, date1,
										date2)); // 支出
								sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
							}
							sf.setRemark("");
							list.add(sf);
						}
						//---------------------------------
						if(!financeServiceOfRate.equals(BigDecimal.valueOf(0))){
							sf = new SlFundIntent();
							sf.setIntentDate(d11);
							if (calcutype.equals("SmallLoan")) { // 贷款项目
								sf.setFundType(FundIntentListPro.ProjectFinanceService);// 资金类型
								sf.setPayMoney(BigDecimal.valueOf(0));// 支出
								sf.setIncomeMoney(FundIntentListPro.calculAccrual(
										calcutype, financeOfRate, accrualType,
										payaccrualType, financeServiceOfRate, projectMoney,
										commisionType, commisionMoney, dateMode, date1,
										date2));// 收入

							} else {// 融资 项目
								sf.setFundType(FundIntentListPro.FinancingserviceMoney);// 资金类型
								sf.setPayMoney(FundIntentListPro.calculAccrual(
										calcutype, financeOfRate, accrualType,
										payaccrualType, financeServiceOfRate, projectMoney,
										commisionType, commisionMoney, dateMode, date1,
										date2)); // 支出
								sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
							}
							sf.setRemark("");
							list.add(sf);
						}
						//获得管理咨询费和财务服务费 end by chencc
					}
				}
			}

		}
		//等额本息
	//	int period=10;
		
		if (payaccrualType.equals("sameprincipalandInterest") && isPreposePayAccrual == 0) {
			int days = DateUtil.getDaysBetweenDate(date1, date2)+1;// 得到两日期相差天数
			int intervalday=days/period;
			int mode=days%period;
			if(mode !=0){
			//	intervalday++;
			}
			BigDecimal dayp = new BigDecimal(0);
			
			dayp = StatsPro.calcuDayProfit("0", accrualType, accrual.doubleValue(), date1, date2);
			BigDecimal periodtimep = dayp.multiply(BigDecimal.valueOf(intervalday)).divide(BigDecimal.valueOf(100));;
			BigDecimal   periodtimemoney=projectMoney.multiply(periodtimep).multiply(mnfang(periodtimep.add(new BigDecimal(1)),period)).divide((mnfang(periodtimep.add(new BigDecimal(1)),period).subtract(new BigDecimal(1))),100,BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(1),2,BigDecimal.ROUND_HALF_UP);
			
			BigDecimal incomeMoney = new BigDecimal(0);
			BigDecimal preperiodsurplus = projectMoney; 
			if (period > 0) {
				for (int i = 1; i <= period; i++) {
						// 贷款收息
						sf = new SlFundIntent();
						sf.setFundType(FundIntentListPro.ProjectLoadAccrual);// 资金类型
						if(i==period){
							sf.setIntentDate(DateUtil.addDaysToDate(DateUtil.parseDate(date2, "yyyy-MM-dd"),0));
							sf.setPayMoney(BigDecimal.valueOf(0)); 
							  SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
							  BigDecimal lastday= new BigDecimal(DateUtil.getDaysBetweenDate(sd.format(DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"),(i-1)*intervalday)), date2));
							sf.setIncomeMoney(preperiodsurplus.multiply(dayp.multiply(lastday)).divide(BigDecimal.valueOf(100))); 
						}else{
							sf.setIntentDate(DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*intervalday));
							sf.setPayMoney(BigDecimal.valueOf(0)); 
							sf.setIncomeMoney(preperiodsurplus.multiply(periodtimep)); 
						}
						sf.setRemark("");
						list.add(sf);
						
						SlFundIntent sf1 = new SlFundIntent();
						sf1.setFundType(FundIntentListPro.ProjectLoadRoot);// 资金类型
						if(i==period){
							sf1.setIntentDate(DateUtil.addDaysToDate(DateUtil.parseDate(date2, "yyyy-MM-dd"),0));
						}else{
							sf1.setIntentDate(DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*intervalday));
						}
						sf1.setPayMoney(BigDecimal.valueOf(0)); 
						sf1.setIncomeMoney(periodtimemoney.subtract(sf.getIncomeMoney())); 
						sf1.setRemark("");
						
						list.add(sf1);
				   
						preperiodsurplus=preperiodsurplus.subtract(periodtimemoney).add(preperiodsurplus.multiply(periodtimep));

				}
			}
		}
		//等额本金
		if (payaccrualType.equals("sameprincipal") && isPreposePayAccrual == 0) {// 足日头
			int days = DateUtil.getDaysBetweenDate(date1, date2)+1;// 得到两日期相差天数
			int intervalday=days/period;
			BigDecimal dayp = new BigDecimal(0);
			
			dayp = StatsPro.calcuDayProfit("0", accrualType, accrual.doubleValue(), date1, date2);
			BigDecimal periodtimep = dayp.multiply(BigDecimal.valueOf(intervalday)).divide(BigDecimal.valueOf(100));;
			BigDecimal   periodtimemoney=projectMoney.multiply(periodtimep).multiply(mnfang(periodtimep.add(new BigDecimal(1)),period)).divide((mnfang(periodtimep.add(new BigDecimal(1)),period).subtract(new BigDecimal(1))),100,BigDecimal.ROUND_HALF_UP);
			
			BigDecimal sameprojectMoney = projectMoney.divide(new BigDecimal(period),100,BigDecimal.ROUND_HALF_UP);
			BigDecimal preperiodsurplus = projectMoney; 
			if (period > 0) {
				for (int i = 1; i <= period; i++) {
						// 贷款收息
						sf = new SlFundIntent();
						sf.setFundType(FundIntentListPro.ProjectLoadAccrual);// 资金类型
						if(i==period){
							sf.setIntentDate(DateUtil.addDaysToDate(DateUtil.parseDate(date2, "yyyy-MM-dd"),0));
							sf.setPayMoney(BigDecimal.valueOf(0)); 
							  SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
							  BigDecimal lastday= new BigDecimal(DateUtil.getDaysBetweenDate(sd.format(DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"),(i-1)*intervalday)), date2));
							sf.setIncomeMoney(preperiodsurplus.multiply(dayp.multiply(lastday)).divide(BigDecimal.valueOf(100))); 
						}else{
							sf.setIntentDate(DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*intervalday));
							sf.setPayMoney(BigDecimal.valueOf(0)); 
							sf.setIncomeMoney(preperiodsurplus.multiply(periodtimep)); 
						}
						sf.setRemark("");
						list.add(sf);
						
						SlFundIntent sf1 = new SlFundIntent();
						sf1.setFundType(FundIntentListPro.ProjectLoadRoot);// 资金类型
						if(i==period){
							sf1.setIntentDate(DateUtil.addDaysToDate(DateUtil.parseDate(date2, "yyyy-MM-dd"),0));
						}else{
							sf1.setIntentDate(DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i*intervalday));
						}
						sf1.setPayMoney(BigDecimal.valueOf(0)); 
						sf1.setIncomeMoney(sameprojectMoney);
						sf1.setRemark("");
						
						list.add(sf1);
				   
						preperiodsurplus=preperiodsurplus.subtract(sameprojectMoney);

				}
			}
		}
		//等额本息
		
		if (payaccrualType.equals("sameprincipalandInterest") && isPreposePayAccrual == 1) {
			int days = DateUtil.getDaysBetweenDate(date1, date2)+1;// 得到两日期相差天数
			int intervalday=days/period;
			int mode=days%period;
			if(mode !=0){
			//	intervalday++;
			}
			BigDecimal dayp = new BigDecimal(0);
			
			dayp = StatsPro.calcuDayProfit("0", accrualType, accrual.doubleValue(), date1, date2);
			BigDecimal periodtimep = dayp.multiply(BigDecimal.valueOf(intervalday)).divide(BigDecimal.valueOf(100));;
			BigDecimal   periodtimemoney=projectMoney.multiply(periodtimep).multiply(mnfang(periodtimep.add(new BigDecimal(1)),period)).divide((mnfang(periodtimep.add(new BigDecimal(1)),period).subtract(new BigDecimal(1))),100,BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(1),2,BigDecimal.ROUND_HALF_UP);
			
			BigDecimal incomeMoney = new BigDecimal(0);
			BigDecimal preperiodsurplus = projectMoney; 
			if (period > 0) {
				for (int i = 1; i <= period; i++) {
						// 贷款收息
						sf = new SlFundIntent();
						sf.setFundType(FundIntentListPro.ProjectLoadAccrual);// 资金类型
						if(i==period){
							sf.setIntentDate(DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i-1)*intervalday));
							sf.setPayMoney(BigDecimal.valueOf(0)); 
							  SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
							  BigDecimal lastday= new BigDecimal(DateUtil.getDaysBetweenDate(sd.format(DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"),(i-1)*intervalday)), date2));
							sf.setIncomeMoney(preperiodsurplus.multiply(dayp.multiply(lastday)).divide(BigDecimal.valueOf(100))); 
						}else{
							sf.setIntentDate(DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i-1)*intervalday));
							sf.setPayMoney(BigDecimal.valueOf(0)); 
							sf.setIncomeMoney(preperiodsurplus.multiply(periodtimep)); 
						}
						sf.setRemark("");
						list.add(sf);
						
						SlFundIntent sf1 = new SlFundIntent();
						sf1.setFundType(FundIntentListPro.ProjectLoadRoot);// 资金类型
						if(i==period){
							sf1.setIntentDate(DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i-1)*intervalday));
						}else{
							sf1.setIntentDate(DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i-1)*intervalday));
						}
						sf1.setPayMoney(BigDecimal.valueOf(0)); 
						sf1.setIncomeMoney(periodtimemoney.subtract(sf.getIncomeMoney())); 
						sf1.setRemark("");
						
						list.add(sf1);
				   
						preperiodsurplus=preperiodsurplus.subtract(periodtimemoney).add(preperiodsurplus.multiply(periodtimep));

				}
			}
		}
		//等额本金
		if (payaccrualType.equals("sameprincipal") && isPreposePayAccrual == 1) {// 
			int days = DateUtil.getDaysBetweenDate(date1, date2)+1;// 得到两日期相差天数
			int intervalday=days/period;
			BigDecimal dayp = new BigDecimal(0);
			
			dayp = StatsPro.calcuDayProfit("0", accrualType, accrual.doubleValue(), date1, date2);
			BigDecimal periodtimep = dayp.multiply(BigDecimal.valueOf(intervalday)).divide(BigDecimal.valueOf(100));;
			BigDecimal   periodtimemoney=projectMoney.multiply(periodtimep).multiply(mnfang(periodtimep.add(new BigDecimal(1)),period)).divide((mnfang(periodtimep.add(new BigDecimal(1)),period).subtract(new BigDecimal(1))),100,BigDecimal.ROUND_HALF_UP);
			
			BigDecimal sameprojectMoney = projectMoney.divide(new BigDecimal(period),100,BigDecimal.ROUND_HALF_UP);
			BigDecimal preperiodsurplus = projectMoney; 
			if (period > 0) {
				for (int i = 1; i <= period; i++) {
						// 贷款收息
						sf = new SlFundIntent();
						sf.setFundType(FundIntentListPro.ProjectLoadAccrual);// 资金类型
						if(i==period){
							sf.setIntentDate(DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i-1)*intervalday));
							sf.setPayMoney(BigDecimal.valueOf(0)); 
							  SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
							  BigDecimal lastday= new BigDecimal(DateUtil.getDaysBetweenDate(sd.format(DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"),(i-1)*intervalday)), date2));
							sf.setIncomeMoney(preperiodsurplus.multiply(dayp.multiply(lastday)).divide(BigDecimal.valueOf(100))); 
						}else{
							sf.setIntentDate(DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i-1)*intervalday));
							sf.setPayMoney(BigDecimal.valueOf(0)); 
							sf.setIncomeMoney(preperiodsurplus.multiply(periodtimep)); 
						}
						sf.setRemark("");
						list.add(sf);
						
						SlFundIntent sf1 = new SlFundIntent();
						sf1.setFundType(FundIntentListPro.ProjectLoadRoot);// 资金类型
						if(i==period){
							sf1.setIntentDate(DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i-1)*intervalday));
						}else{
							sf1.setIntentDate(DateUtil.addDaysToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), (i-1)*intervalday));
						}
						sf1.setPayMoney(BigDecimal.valueOf(0)); 
						sf1.setIncomeMoney(sameprojectMoney);
						sf1.setRemark("");
						
						list.add(sf1);
				   
						preperiodsurplus=preperiodsurplus.subtract(sameprojectMoney);

				}
			}
		}
		// 贷款收本
		if(!payaccrualType.equals("sameprincipal")&&!payaccrualType.equals("sameprincipalandInterest")){
			sf = new SlFundIntent();
			sf.setIntentDate(DateUtil.parseDate(date2, "yyyy-MM-dd"));
			if (calcutype.equals("SmallLoan")) { // 贷款项目
			
				sf.setFundType(FundIntentListPro.ProjectLoadRoot);// 资金类型
				sf.setPayMoney(BigDecimal.valueOf(0));// 支出
				sf.setIncomeMoney(projectMoney);// 收入
			} else {// 融资 项目
				sf.setFundType(FundIntentListPro.FinancingRepay);// 资金类型
				sf.setPayMoney(projectMoney); // 支出
				sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入
			}
			sf.setRemark("");
			list.add(sf);
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
	public static BigDecimal calculAccrual(String calcutype, BigDecimal dayProfit,
			String accrualType, String payaccrualType, BigDecimal accrual,
			BigDecimal projectMoney, short commisionType,
			BigDecimal commisionMoney, String dateMode, String date1,
			String date2) {

		BigDecimal dayp = new BigDecimal(0);

		if (payaccrualType.equals("oneTimePay")) { // 一次性sub类型
			int days = DateUtil.getDaysBetweenDate(date1, date2);

			if (calcutype.equals("SmallLoan")) {
				days = days + 1;
				dayp = StatsPro.calcuDayProfit("SmallLoan", payaccrualType,
						accrual.doubleValue(), date1, date2);
			} else {
				dayp = StatsPro.calcuDayProfit("0", payaccrualType,
						accrual.doubleValue(), date1, date2);
			}
			return (dayp.multiply(projectMoney)).multiply(
					BigDecimal.valueOf(days)).divide(BigDecimal.valueOf(100));
		}
		if (payaccrualType.equals("dayPay")) // 日
		{

			return dayProfit.multiply(projectMoney).divide(
					BigDecimal.valueOf(100));
		}
		if (payaccrualType.equals("monthPay")) { // 月
			dayp = StatsPro.calcuDayProfit("0", payaccrualType, accrual
					.doubleValue(), date1, date2);
			return (dayp.multiply(projectMoney)).multiply(
					BigDecimal.valueOf(30)).divide(BigDecimal.valueOf(100));
		}
		if (payaccrualType.equals("seasonPay")) { // 季
			dayp = StatsPro.calcuDayProfit("0", payaccrualType, accrual
					.doubleValue(), date1, date2);
			return (dayp.multiply(projectMoney)).multiply(
					BigDecimal.valueOf(90)).divide(BigDecimal.valueOf(100));
		}
		if (payaccrualType.equals("halfYearPay")) { // 半年
			dayp = StatsPro.calcuDayProfit("0", payaccrualType, accrual
					.doubleValue(), date1, date2);
			return (dayp.multiply(projectMoney)).multiply(
					BigDecimal.valueOf(180)).divide(BigDecimal.valueOf(100));
		}
		if (payaccrualType.equals("yearPay")) {// 年
			dayp = StatsPro.calcuDayProfit("0", payaccrualType, accrual
					.doubleValue(), date1, date2);
			return (dayp.multiply(projectMoney)).multiply(
					BigDecimal.valueOf(360)).divide(BigDecimal.valueOf(100));
		}
		if (payaccrualType.equals("calendarMonthPay")) {// 日历月
			dayp = StatsPro.calcuDayProfit("0", payaccrualType, accrual
					.doubleValue(), date1, date2);
			return (dayp.multiply(projectMoney)).multiply(
					BigDecimal.valueOf(30)).divide(BigDecimal.valueOf(100));
		}
		return new BigDecimal(0.00);
	}
}
