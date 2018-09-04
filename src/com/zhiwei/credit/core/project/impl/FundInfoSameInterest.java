package com.zhiwei.credit.core.project.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.zhiwei.core.util.DateUtil;
import com.zhiwei.credit.core.project.FundIntent;
import com.zhiwei.credit.core.project.FundIntentListPro3;
import com.zhiwei.credit.core.project.FundIntentListPronew;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.fund.project.BpFundProject;
import com.zhiwei.credit.model.customer.InvestPersonInfo;

@SuppressWarnings("all")
public class FundInfoSameInterest implements FundIntent {

	public enum accrualType {dayPay,monthPay,seasonPay,yearPay,owerPay}
	
	@Override
	public List<SlFundIntent> createList(BpFundProject project,
			InvestPersonInfo invester) {
		// 等本等息算法：（本金*利息+本金）/期数=每期需还款金额
		// 在等本等息中
		/*SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		Map<Integer,Date> map = FundIntentGenerate.getDateAndCount(project);
		if(null!=map){
			Iterator ite = map.keySet().iterator();
			while(ite.hasNext()){
				Integer perd = (Integer) ite.next();
				Date date = map.get(perd);
				System.out.println(perd+":"+sdf1.format(date));
			}
		}*/
if(null==invester){
	return null;
}
		invester.getInvestMoney();
		List<SlFundIntent> list = new ArrayList<SlFundIntent>();;
		if (project.getPayintentPeriod() == null
				|| "".equals(project.getPayintentPeriod())
				|| 0 == project.getPayintentPeriod()) {
			return null;
		}
		SlFundIntent sf = new SlFundIntent();//本金
		SlFundIntent slFund =  null;//
		SlFundIntent slInterset =  null;//
		SlFundIntent slConsult =  null;//
		SlFundIntent slService =  null;//
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date1 = sdf.format(project.getStartDate());
		String date2 = sdf.format(project.getIntentDate());
		Integer period = project.getPayintentPeriod();
		// 每期金额
		BigDecimal fund = invester.getInvestMoney().divide(new BigDecimal(period),100,BigDecimal.ROUND_HALF_UP);
		// 每期利息
		BigDecimal interest = null;
		// 每期管理咨询费
		BigDecimal consulting = null;
		// 每期财务服务费
		BigDecimal servcies = null;

		if ("dayPay".equals(project.getPayaccrualType())) {
			interest = invester.getInvestMoney().multiply(project.getDayOfAccrual()).divide(new BigDecimal(100),100,BigDecimal.ROUND_HALF_UP);
			consulting = invester.getInvestMoney().multiply(project.getDayManagementConsultingOfRate()).divide(new BigDecimal(100),100,BigDecimal.ROUND_HALF_UP);
			servcies = invester.getInvestMoney().multiply(project.getDayFinanceServiceOfRate()).divide(new BigDecimal(100),100,BigDecimal.ROUND_HALF_UP);
		} else if ("monthPay".equals(project.getPayaccrualType())) {
			interest = invester.getInvestMoney().multiply(project.getAccrual()).divide(new BigDecimal(100),100,BigDecimal.ROUND_HALF_UP);
			consulting = invester.getInvestMoney().multiply(project.getManagementConsultingOfRate()).divide(new BigDecimal(100),100,BigDecimal.ROUND_HALF_UP);
			servcies = invester.getInvestMoney().multiply(project.getFinanceServiceOfRate()).divide(new BigDecimal(100),100,BigDecimal.ROUND_HALF_UP);
		} else if ("seasonPay".equals(project.getPayaccrualType())) {
			interest = invester.getInvestMoney().multiply(project.getAccrual()).multiply(new BigDecimal(3)).divide(new BigDecimal(100),100,BigDecimal.ROUND_HALF_UP);
			consulting = invester.getInvestMoney().multiply(project.getManagementConsultingOfRate()).multiply(new BigDecimal(3)).divide(new BigDecimal(100),100,BigDecimal.ROUND_HALF_UP);
			servcies = invester.getInvestMoney().multiply(project.getFinanceServiceOfRate()).multiply(new BigDecimal(3)).divide(new BigDecimal(100),100,BigDecimal.ROUND_HALF_UP);
		} else if ("yearPay".equals(project.getPayaccrualType())) {
			interest = invester.getInvestMoney().multiply(project.getYearAccrualRate()).divide(new BigDecimal(100),100,BigDecimal.ROUND_HALF_UP);
			consulting = invester.getInvestMoney().multiply(project.getYearManagementConsultingOfRate()).divide(new BigDecimal(100),100,BigDecimal.ROUND_HALF_UP);
			servcies = invester.getInvestMoney().multiply(project.getYearFinanceServiceOfRate()).divide(new BigDecimal(100),100,BigDecimal.ROUND_HALF_UP);
		} else if ("owerPay".equals(project.getPayaccrualType())) {
			
		}
		sf.setFundType(FundIntentListPro3.ProjectLoadOut);// 资金类型
		sf.setIntentDate(DateUtil.parseDate(date1, "yyyy-MM-dd"));
		sf.setPayMoney(invester.getInvestMoney()); // 支出金额
		sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入金额
		sf.setRemark("");
		sf.setInvestPersonId(invester.getInvestId());
		sf.setInvestPersonName(invester.getInvestPersonName());
		list.add(sf);
		// BigDecimal interest =
		// invester.getInvestMoney().multiply(project.get)
		for (int i = 1; i <= period; i++) {
			if ("dayPay".equals(project.getPayaccrualType())) {
				
			} else if ("monthPay".equals(project.getPayaccrualType())) {
				slFund = calculslfundintent(
						FundIntentListPro3.ProjectLoadRoot,
						DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i), -1),
						new BigDecimal(0),
						invester.getInvestMoney().divide(new BigDecimal(project.getPayintentPeriod()),100,BigDecimal.ROUND_HALF_UP),
						invester.getInvestId(),
						invester.getInvestPersonName(),
						i
						);
				slInterset = calculslfundintent(
						FundIntentListPro3.ProjectLoadAccrual,
						DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i), -1),
						new BigDecimal(0),
						interest,//.divide(new BigDecimal(project.getPayintentPeriod())),
						invester.getInvestId(),
						invester.getInvestPersonName(),
						i
						);
				slConsult = calculslfundintent(
						FundIntentListPro3.ProjectManagementConsulting,
						DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i), -1),
						new BigDecimal(0),
						consulting,//.divide(new BigDecimal(project.getPayintentPeriod())),
						invester.getInvestId(),
						invester.getInvestPersonName(),
						i
						);
				slService = calculslfundintent(
						FundIntentListPro3.ProjectFinanceService,
						DateUtil.addDaysToDate(DateUtil.addMonthsToDate(DateUtil.parseDate(date1, "yyyy-MM-dd"), i), -1),
						new BigDecimal(0),
						servcies,//.divide(new BigDecimal(project.getPayintentPeriod())),
						invester.getInvestId(),
						invester.getInvestPersonName(),
						i
						);
			} else if ("seasonPay".equals(project.getPayaccrualType())) {
				
			} else if ("yearPay".equals(project.getPayaccrualType())) {
				
			} else if ("owerPay".equals(project.getPayaccrualType())) {
				
			}
			if(slFund!=null){
				list.add(slFund);
			}
			if(null!=slInterset){
				list.add(slInterset);
			}
			/*if(null!=slConsult){
				list.add(slConsult);
			}
			if(null!=slService){
				list.add(slService);
			}*/
		
		}
		return list;
	}

	private SlFundIntent calculslfundintent(String fundType,Date intentdate,BigDecimal paymoney,BigDecimal incomemoeny,Long investPersonId,String investPersonName,Integer payintentPeriod){
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
	/**
	 * @param accrualType
	 *            还款周期
	 * @param projectMoney
	 *            贷款金额
	 * @param accrual
	 *            贷款利率 ---(目前是月利率)
	 * @param moneyType
	 *            费用类型
	 * **/
	private BigDecimal acountMoney(String accrualType, BigDecimal projectMoney,
			BigDecimal accrual, String moneyType) {
	
		return projectMoney.multiply(accrual);
	}
	
}
