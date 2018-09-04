package com.zhiwei.credit.service.p2p.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.p2p.PlatDataPublishDao;
import com.zhiwei.credit.model.p2p.Operate;
import com.zhiwei.credit.model.p2p.PlatDataPublish;
import com.zhiwei.credit.service.p2p.PlatDataPublishService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.Bidi;


/**
 * 
 * @author 
 *
 */
public class PlatDataPublishServiceImpl extends BaseServiceImpl<PlatDataPublish> implements PlatDataPublishService{
	@SuppressWarnings("unused")
	private PlatDataPublishDao dao;
	
	public PlatDataPublishServiceImpl(PlatDataPublishDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public Operate showAll(String startDate,String endDate){
		//当月累计交易总额与笔数
		Operate operate  = dao.getSumMoneyAndCount(startDate,endDate);
		//借贷余额
		BigDecimal balanceLoanMoney = dao.getBalanceLoanMoney(startDate,endDate);
		operate.setBalanceLoanMoney(balanceLoanMoney);
		//借贷余额笔数
		Integer balanceLoanCount = dao.getBalanceLoanCount(startDate,endDate);
		operate.setBalanceLoanCount(balanceLoanCount);
		//客户累计收益
		BigDecimal sumProfit=dao.getSumProfit(startDate,endDate);
		operate.setSumProfit(sumProfit);
		//利息余额
		BigDecimal interestBalance = dao.getInterestBalance(startDate,endDate);
		operate.setInterestBalance(interestBalance);
		//累计出借人数量
		Integer sumPayPeople = dao.getSumPayPeople(startDate,endDate);
		operate.setSumPayPeople(sumPayPeople);
		//当前出借人数量
		Integer payPeople = dao.getPayPeople(startDate,endDate);
		operate.setPayPeople(payPeople);
		//累计借款人数量
		Integer sumIncomePeople = dao.getSumIncomePeople(startDate,endDate);
		operate.setSumIncomePeople(sumIncomePeople);
		//当前借款人数量
		Integer incomePeople = dao.getIncomePeople(startDate,endDate);
		operate.setIncomePeople(incomePeople);
		//前十大借款人待还金额占比
		BigDecimal tenIncomeMoneyProporion = dao.getTenIncomeMoneyProporion(startDate,endDate);
		operate.setTenIncomeMoneyProporion(tenIncomeMoneyProporion.multiply(new BigDecimal(100)));
		//最大借款人待还金额占比
		BigDecimal maxIncomeMoneyProporion = dao.getMaxIncomeMoneyProporion(startDate,endDate);
		operate.setMaxIncomeMoneyProporion(maxIncomeMoneyProporion.multiply(new BigDecimal(100)));
		//人均累计借款金额
		BigDecimal avgSumIncomePeople = operate.getSumMoney().divide(new BigDecimal(sumIncomePeople),3, RoundingMode.UP);
		operate.setAvgSumIncomePeople(avgSumIncomePeople);
		//人均累计出借金额
		BigDecimal avgSumPayPeople = operate.getSumMoney().divide(new BigDecimal(sumPayPeople),3, RoundingMode.UP);
		operate.setAvgSumPayPeople(avgSumPayPeople);
		//最大单户出借余额占比
		BigDecimal maxPayPeopleProporion =dao.getMaxPayPeopleProporion(startDate,endDate);
		operate.setMaxPayPeopleProporion(maxPayPeopleProporion.divide(operate.getSumMoney(),3, RoundingMode.UP).multiply(new BigDecimal(100)));
		//最大10户出借余额占比
		BigDecimal tenPayPeopleProporion =dao.getTenPayPeopleProporion(startDate,endDate);
		operate.setTenPayPeopleProporion(tenPayPeopleProporion.divide(operate.getSumMoney(),3, RoundingMode.UP).multiply(new BigDecimal(100)));
		//人均借款额度   借贷余额/借贷人数
		BigDecimal avgIncomePeople=balanceLoanMoney.divide(new BigDecimal(incomePeople),3, RoundingMode.UP);
		operate.setAvgIncomePeople(avgIncomePeople);
		//累计投资期限
		Integer sumDay = dao.getSumDay(startDate,endDate);
		//人均借款期限
		operate.setAvgIncomeDay(sumDay/operate.getSumCount());
		//人均借款成本
		BigDecimal sumCost = dao.getSumCost(startDate,endDate);
		operate.setAvgIncomeCost(sumCost.divide(new BigDecimal(sumIncomePeople),3, RoundingMode.UP));
		//人均投资期限
		operate.setAvgPayDay(sumDay/operate.getSumCount());
		//人均投资回报
		BigDecimal sumInterest = dao.getSumInterest(startDate,endDate);
		operate.setAvgInterestMoney(sumInterest.divide(new BigDecimal(sumPayPeople),3, RoundingMode.UP));
		//平均满标用时
		Integer avgFullTime = dao.getAvgFullTime(startDate,endDate);
		operate.setAvgFullTime(avgFullTime);
		//平均借款利率
		Integer avgRate = dao.getAvgRate(startDate,endDate);
		operate.setAvgRate(avgRate);

		return operate;
	}
}