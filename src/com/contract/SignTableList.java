package com.contract;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.zhiwei.credit.model.creditFlow.finance.BpFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.SlActualToCharge;
import com.zhiwei.credit.model.creditFlow.fund.project.SettlementInfo;
import com.zhiwei.credit.model.customer.InvestPersonInfo;
import com.zhiwei.credit.service.creditFlow.finance.BpFundIntentService;

/**
 * 创建表格数据模板类
 * @author zhangcb
 *
 */
public class SignTableList {
	
	public static SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 投资人款项计划
	 * 序号-期数-资金类型-计划收入金额-计划支出金额-计划到账日期
	 * @param jkrFundList
	 * @return
	 */
	public static List<List<String>> tzrFundIntentTable(List<BpFundIntent> jkrFundList){
		List<List<String>> resultList = new ArrayList<List<String>>();
		if(null!=jkrFundList && jkrFundList.size()>0){
			List<String> headerList = new ArrayList<String>();
			headerList.add("序号");
			headerList.add("期数");
			headerList.add("资金类型");
			headerList.add("计划收入金额");
			headerList.add("计划到账日期");
			resultList.add(headerList);
			for (int i = 1; i <= jkrFundList.size(); i++) {
				BpFundIntent  f = jkrFundList.get(i-1);
				List<String> list = new ArrayList<String>();
				list.add("" + i);
				list.add("第"+f.getPayintentPeriod()+"期");
				list.add(f.getFundType());
				list.add((null==f.getIncomeMoney()?"0":f.getIncomeMoney())+"元");
				list.add(null!=f.getIntentDate()?sd.format(f.getIntentDate()):"");
				resultList.add(list);
			}
		}
		return resultList;
	}
	
	/**
	 * 借款人还款计划
	 * @param esList  款项记录
	 * @return
	 */
	public static List<List<String>> jkrBpFundIntentTable(List<BpFundIntent> esList){
		List<List<String>> resultList = new ArrayList<List<String>>();
		if(null!=esList && esList.size()>0){
			List<String> headerList = new ArrayList<String>();
			headerList.add("还款日");
			headerList.add("本金");
			headerList.add("利息");
			headerList.add("财务服务费");
			headerList.add("管理咨询费");
			headerList.add("合计");
			resultList.add(headerList);
			for (int i = 1; i <= esList.size(); i++) {
				BpFundIntent  es = esList.get(i-1);
				List<String> list = new ArrayList<String>();
				list.add(sd.format(es.getIntentDate()).toString());
				if(null==es.getPrincipal()){
					es.setPrincipal(new BigDecimal("0"));
				}
				list.add(es.getPrincipal().toString());
				if(null==es.getInterest()){
					es.setInterest(new BigDecimal("0"));
				}
				list.add(es.getInterest().toString());
				if(null==es.getConsultationMoney()){
					es.setConsultationMoney(new BigDecimal("0"));
				}
				list.add(es.getConsultationMoney().toString());
				if(null==es.getServiceMoney()){
					es.setServiceMoney(new BigDecimal("0"));
				}
				list.add(es.getServiceMoney().toString());
				list.add(es.getPrincipal().add(es.getInterest()).add(es.getConsultationMoney()).add(es.getServiceMoney()).toString());
				resultList.add(list);
			}
		}
		return resultList;
	}
	
	/**
	 * 投资人列表
	 * 序号-资金来源-投资方-投资金额-应收本息
	 * @param plist 投资人投资记录
	 * @return
	 */
	public static List<List<String>> investPersonTable(List<InvestPersonInfo> plist,BpFundIntentService service){
		List<List<String>> resultList = new ArrayList<List<String>>();
		if(null!=plist && plist.size()>0){
			List<String> headerList = new ArrayList<String>();
			headerList.add("序号");
			headerList.add("资金来源");
			headerList.add("投资方");
			headerList.add("投资金额");
			headerList.add("应收本息");
			resultList.add(headerList);
			for (int i = 1; i <= plist.size(); i++) {
				InvestPersonInfo  p = plist.get(i-1);
				BigDecimal money= service.getPrincipalAndInterest(p.getBidPlanId(),p.getOrderNo());
				List<String> list = new ArrayList<String>();
				list.add(i+"");
				list.add("个人");
				list.add(p.getInvestPersonName());
				list.add((null==p.getInvestMoney()?0:p.getInvestMoney())+"元");
				list.add((null==money?0:money)+"元");
				resultList.add(list);
			}
		}
		return resultList;
	}
	
	/**
	 * 结算手续费清单
	 * @param plist
	 * @param service
	 * @return
	 */
	public static List<List<String>> settleChargeTable(List<SlActualToCharge> plist){
		List<List<String>> resultList = new ArrayList<List<String>>();
		if(null!=plist && plist.size()>0){
			List<String> headerList = new ArrayList<String>();
			headerList.add("序号");
			headerList.add("费用类型");
			headerList.add("奖励金额");
			headerList.add("扣款金额");
			headerList.add("备注");
			resultList.add(headerList);
			for (int i = 1; i <= plist.size(); i++) {
				SlActualToCharge  p = plist.get(i-1);
				List<String> list = new ArrayList<String>();
				list.add(i+"");
				list.add(p.getTypeName());
				list.add(p.getIncomeMoney()==null?"0元":(p.getIncomeMoney().doubleValue()+"元"));
				list.add(p.getPayMoney()==null?"0元":(p.getPayMoney().doubleValue()+"元"));
				list.add(p.getRemark());
				resultList.add(list);
			}
		}
		return resultList;
	}
	/**
	 * 提成明细
	 * @param plist
	 * @return
	 */
	public static List<List<String>> settleInfoTable(List<SettlementInfo> plist){
		List<List<String>> resultList = new ArrayList<List<String>>();
		if(null!=plist && plist.size()>0){
			List<String> headerList = new ArrayList<String>();
			headerList.add("序号");
			headerList.add("日期");
			headerList.add("当日保有量");
			headerList.add("提成比例%");
			headerList.add("提成金额");
			resultList.add(headerList);
			for (int i = 1; i <= plist.size(); i++) {
				SettlementInfo  p = plist.get(i-1);
				List<String> list = new ArrayList<String>();
				list.add(i+"");
				list.add(sd.format(p.getCreateDate()));
				list.add(p.getSettleMoney()==null?"0元":(p.getSettleMoney().doubleValue()+"元"));
				list.add(p.getRoyaltyRatio()==null?"0":(p.getRoyaltyRatio().doubleValue()+""));
				list.add(p.getRoyaltyMoney()==null?"0元":(p.getRoyaltyMoney().doubleValue()+"元"));
				resultList.add(list);
			}
		}
		return resultList;
	}
}
