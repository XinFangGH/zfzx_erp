package com.zhiwei.credit.dao.p2p;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
 */

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.p2p.Operate;
import com.zhiwei.credit.model.p2p.PlatDataPublish;

import java.math.BigDecimal;

/**
 * @author
 */
public interface PlatDataPublishDao extends BaseDao<PlatDataPublish> {

    Operate getSumMoneyAndCount(String startDate, String endDate);

    BigDecimal getBalanceLoanMoney(String startDate, String endDate);

    Integer getBalanceLoanCount(String startDate, String endDate);

    Integer getSumPayPeople(String startDate, String endDate);

    Integer getPayPeople(String startDate, String endDate);

    Integer getSumIncomePeople(String startDate, String endDate);

    Integer getIncomePeople(String startDate, String endDate);

    BigDecimal getTenIncomeMoneyProporion(String startDate, String endDate);

    BigDecimal getMaxIncomeMoneyProporion(String startDate, String endDate);

    BigDecimal getInterestBalance(String startDate, String endDate);

    BigDecimal getMaxPayPeopleProporion(String startDate, String endDate);

    BigDecimal getTenPayPeopleProporion(String startDate, String endDate);

    Integer getSumDay(String startDate, String endDate);

    BigDecimal getSumCost(String startDate, String endDate);

    BigDecimal getSumInterest(String startDate, String endDate);

    Integer getAvgFullTime(String startDate, String endDate);

    Integer getAvgRate(String startDate, String endDate);

    BigDecimal getSumProfit(String startDate, String endDate);
}