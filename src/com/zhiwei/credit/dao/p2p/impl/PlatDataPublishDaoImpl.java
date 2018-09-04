package com.zhiwei.credit.dao.p2p.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
 */

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.p2p.PlatDataPublishDao;
import com.zhiwei.credit.model.p2p.Operate;
import com.zhiwei.credit.model.p2p.PlatDataPublish;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;

import java.math.BigDecimal;
import java.math.BigInteger;


/**
 * @author
 */
@SuppressWarnings("unchecked")
public class PlatDataPublishDaoImpl extends BaseDaoImpl<PlatDataPublish> implements PlatDataPublishDao {

    public PlatDataPublishDaoImpl() {
        super(PlatDataPublish.class);
    }

    @Override
    public Operate getSumMoneyAndCount(String startDate, String endDate) {
        StringBuffer sb = new StringBuffer("SELECT sum(bidMoney) as sumMoney,count(*) as sumCount  from pl_bid_plan where state in (7,10) ");
        if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
            sb.append("and fullTime BETWEEN '" + startDate + " 00:00:00' and '" + endDate + " 23:59:59'");
        } else if (StringUtils.isNotBlank(endDate)) {
            sb.append("and fullTime <= '" + endDate + " 23:59:59'");
        }
        Operate operate = (Operate) this.getSession().createSQLQuery(sb.toString()).addScalar("sumMoney", Hibernate.BIG_DECIMAL)
                .addScalar("sumCount", Hibernate.INTEGER)
                .setResultTransformer(Transformers.aliasToBean(Operate.class)).list().get(0);
        return operate;
    }

    @Override
    public BigDecimal getBalanceLoanMoney(String startDate, String endDate) {
        StringBuffer sb = new StringBuffer("SELECT sum(bp.notMoney) from bp_fund_intent bp , (SELECT bidId from pl_bid_plan WHERE state = 7 ");
        if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
            sb.append("and fullTime BETWEEN '" + startDate + " 00:00:00' and '" + endDate + " 23:59:59'");
        } else if (StringUtils.isNotBlank(endDate)) {
            sb.append("and fullTime <= '" + endDate + " 23:59:59'");
        }
        sb.append(")as pp where pp.bidId = bp.bidPlanId AND bp.fundType  = 'principalRepayment' and bp.notMoney > 0 ");
        BigDecimal money = (BigDecimal) this.getSession().createSQLQuery(sb.toString()).uniqueResult();
        if (money == null ){
            money= BigDecimal.ZERO;
        }
        return money;
    }

    @Override
    public Integer getBalanceLoanCount(String startDate, String endDate) {
        StringBuffer sb = new StringBuffer("SELECT count(*) from (SELECT * from bp_fund_intent bp , (SELECT bidId from pl_bid_plan WHERE state = 7  ");
        if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
            sb.append("and fullTime BETWEEN '" + startDate + " 00:00:00' and '" + endDate + " 23:59:59'");
        } else if (StringUtils.isNotBlank(endDate)) {
            sb.append("and fullTime <= '" + endDate + " 23:59:59'");
        }
        sb.append(")as pp where pp.bidId = bp.bidPlanId AND bp.fundType  = 'principalRepayment' and bp.notMoney > 0 GROUP BY bidPlanId) as c");
        BigInteger count = (BigInteger) this.getSession().createSQLQuery(sb.toString()).uniqueResult();
        if (count == null ){
            count= BigInteger.ZERO;
        }
        return count.intValue();
    }

    @Override
    public Integer getSumPayPeople(String startDate, String endDate) {
        StringBuffer sb = new StringBuffer("SELECT count(*) from (SELECT count(userId) from pl_bid_info ");
        if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
            sb.append("where bidtime BETWEEN '" + startDate + " 00:00:00' and '" + endDate + " 23:59:59'");
        } else if (StringUtils.isNotBlank(endDate)) {
            sb.append("where bidtime <= '" + endDate + " 23:59:59'");
        }
        sb.append("GROUP BY userId ) as pl");
        BigInteger count = (BigInteger) this.getSession().createSQLQuery(sb.toString()).uniqueResult();
        if (count == null ){
            count= BigInteger.ZERO;
        }
        return count.intValue();
    }

    @Override
    public Integer getPayPeople(String startDate, String endDate) {
        StringBuffer sb = new StringBuffer("SELECT count(*) from (SELECT count(*) from bp_fund_intent bp,(SELECT orderNo from pl_bid_info where state in (1,2) ");
        if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
            sb.append("and bidtime BETWEEN '" + startDate + " 00:00:00' and '" + endDate + " 23:59:59'");
        } else if (StringUtils.isNotBlank(endDate)) {
            sb.append("and bidtime <= '" + endDate + " 23:59:59'");
        }
        sb.append(") as pl where bp.orderNo = pl.orderNo and  bp.fundType ='principalRepayment' and bp.notMoney > 0  GROUP BY bp.investPersonId) as bpfund");
        BigInteger count = (BigInteger) this.getSession().createSQLQuery(sb.toString()).uniqueResult();
        if (count == null ){
            count= BigInteger.ZERO;
        }
        return count.intValue();
    }

    @Override
    public Integer getSumIncomePeople(String startDate, String endDate) {
        StringBuffer sb = new StringBuffer("SELECT count(*) from (SELECT * from pl_bid_plan  WHERE state != -1 ");
        if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
            sb.append("and createtime BETWEEN '" + startDate + " 00:00:00' and '" + endDate + " 23:59:59'");
        } else if (StringUtils.isNotBlank(endDate)) {
            sb.append("and createtime <= '" + endDate + " 23:59:59'");
        }
        sb.append(" GROUP BY  receiverP2PAccountNumber) as pp ");
        BigInteger count = (BigInteger) this.getSession().createSQLQuery(sb.toString()).uniqueResult();
        if (count == null ){
            count= BigInteger.ZERO;
        }
        return count.intValue();
    }

    @Override
    public Integer getIncomePeople(String startDate, String endDate) {
        StringBuffer sb = new StringBuffer("SELECT count(*) from (SELECT * from pl_bid_plan  WHERE state not in (-1,10)  ");
        if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
            sb.append("and createtime BETWEEN '" + startDate + " 00:00:00' and '" + endDate + " 23:59:59'");
        } else if (StringUtils.isNotBlank(endDate)) {
            sb.append("and createtime <= '" + endDate + " 23:59:59'");
        }
        sb.append(" GROUP BY  receiverP2PAccountNumber) as pp ");
        BigInteger count = (BigInteger) this.getSession().createSQLQuery(sb.toString()).uniqueResult();
        if (count == null ){
            count= BigInteger.ZERO;
        }
        return count.intValue();
    }

    @Override
    public BigDecimal getTenIncomeMoneyProporion(String startDate, String endDate) {
        StringBuffer sb = new StringBuffer("SELECT SUM(a.allMoney)/(SELECT sum(bidMoney)  from pl_bid_plan where state in (7,10) ");
        if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
            sb.append("and fullTime BETWEEN '" + startDate + " 00:00:00' and '" + endDate + " 23:59:59'");
        } else if (StringUtils.isNotBlank(endDate)) {
            sb.append("and fullTime <= '" + endDate + " 23:59:59'");
        }

        sb.append(") as tenIncomeMoney from (SELECT SUM(b.notMoney) as allMoney from bp_fund_intent b LEFT JOIN pl_bid_plan p on b.bidPlanId = p.bidId where b.notMoney>0 and p.state = 7 ");

        if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
            sb.append("and p.fullTime BETWEEN '" + startDate + " 00:00:00' and '" + endDate + " 23:59:59'");
        } else if (StringUtils.isNotBlank(endDate)) {
            sb.append("and p.fullTime <= '" + endDate + " 23:59:59'");
        }
        sb.append(" GROUP BY p.receiverP2PAccountNumber  order by SUM(b.notMoney) desc LIMIT 10) as a");

        BigDecimal money = (BigDecimal) this.getSession().createSQLQuery(sb.toString()).uniqueResult();
        if (money == null ){
            money=new BigDecimal(0);
        }
        return money.setScale(3,BigDecimal.ROUND_UP);
    }

    @Override
    public BigDecimal getMaxIncomeMoneyProporion(String startDate, String endDate) {
        StringBuffer sb = new StringBuffer("SELECT SUM(b.notMoney)/(SELECT sum(bidMoney)  from pl_bid_plan where state in (7,10) ");
        if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
            sb.append("and fullTime BETWEEN '" + startDate + " 00:00:00' and '" + endDate + " 23:59:59'");
        } else if (StringUtils.isNotBlank(endDate)) {
            sb.append("and fullTime <= '" + endDate + " 23:59:59'");
        }

        sb.append(") as maxIncomeMoney   from bp_fund_intent b LEFT JOIN pl_bid_plan p on b.bidPlanId = p.bidId where b.notMoney>0 and p.state = 7 ");

        if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
            sb.append("and p.fullTime BETWEEN '" + startDate + " 00:00:00' and '" + endDate + " 23:59:59'");
        } else if (StringUtils.isNotBlank(endDate)) {
            sb.append("and p.fullTime <= '" + endDate + " 23:59:59'");
        }
        sb.append(" GROUP BY p.receiverP2PAccountNumber  order by SUM(b.notMoney) desc LIMIT 1 ");

        BigDecimal money = (BigDecimal) this.getSession().createSQLQuery(sb.toString()).uniqueResult();
        if (money == null ){
            money=new BigDecimal(0);
        }
        return money.setScale(3,BigDecimal.ROUND_UP);
    }

    @Override
    public BigDecimal getInterestBalance(String startDate, String endDate) {
        StringBuffer sb = new StringBuffer("SELECT SUM(notMoney) from bp_fund_intent b,(SELECT * from pl_bid_plan where state = 7  ");
        if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
            sb.append("and fullTime BETWEEN'" + startDate + " 00:00:00' and '" + endDate + " 23:59:59'");
        } else if (StringUtils.isNotBlank(endDate)) {
            sb.append("and fullTime <= '" + endDate + " 23:59:59'");
        }
        sb.append(") as p where fundType = 'loanInterest' and notMoney >0 and p.bidId = b.bidPlanId");
        BigDecimal money = (BigDecimal) this.getSession().createSQLQuery(sb.toString()).uniqueResult();
        if (money == null ){
            money= BigDecimal.ZERO;
        }
        return money;

    }

    @Override
    public BigDecimal getMaxPayPeopleProporion(String startDate, String endDate) {
        StringBuffer sb = new StringBuffer("SELECT MAX(a.money) from (SELECT SUM(investMoney)as money from invest_person_info i,(SELECT userId from pl_bid_info  ");
        if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
            sb.append(" where bidtime BETWEEN '" + startDate + " 00:00:00' and '" + endDate + " 23:59:59'");
        } else if (StringUtils.isNotBlank(endDate)) {
            sb.append(" where bidtime <= '" + endDate + " 23:59:59'");
        }
        sb.append("GROUP BY userId )as u where i.investPersonId=u.userId GROUP BY investPersonId)as  a ");
        BigDecimal money = (BigDecimal) this.getSession().createSQLQuery(sb.toString()).uniqueResult();
        if (money == null ){
            money= BigDecimal.ZERO;
        }
        return money;
    }

    @Override
    public BigDecimal getTenPayPeopleProporion(String startDate, String endDate) {
        StringBuffer sb = new StringBuffer("SELECT SUM(c.money) from (SELECT SUM(investMoney) as money FROM invest_person_info i,( SELECT userId FROM pl_bid_info  ");
        if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
            sb.append(" where bidtime BETWEEN '" + startDate + " 00:00:00' and '" + endDate + " 23:59:59'");
        } else if (StringUtils.isNotBlank(endDate)) {
            sb.append(" where bidtime <= '" + endDate + " 23:59:59'");
        }
        sb.append(" GROUP BY userId ) AS u  WHERE\t\ti.investPersonId = u.userId  GROUP BY investPersonId ORDER BY\tSUM(investMoney) desc LIMIT 10) as c");
        BigDecimal money = (BigDecimal) this.getSession().createSQLQuery(sb.toString()).uniqueResult();
        if (money == null ){
            money= BigDecimal.ZERO;
        }
        return money;
    }

    @Override
    public Integer getSumDay(String startDate, String endDate) {
        StringBuffer sb = new StringBuffer("SELECT SUM(DATEDIFF(endIntentDate,startIntentDate)) from pl_bid_plan where state !=-1  ");
        if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
            sb.append("and createtime BETWEEN '" + startDate + " 00:00:00' and '" + endDate + " 23:59:59'");
        } else if (StringUtils.isNotBlank(endDate)) {
            sb.append("and createtime <= '" + endDate + " 23:59:59'");
        }
        BigDecimal count = (BigDecimal) this.getSession().createSQLQuery(sb.toString()).uniqueResult();
        if (count == null ){
            count= BigDecimal.ZERO;
        }
        return count.intValue();
    }

    @Override
    public BigDecimal getSumCost(String startDate, String endDate) {
        StringBuffer sb = new StringBuffer("SELECT  SUM(incomeMoney) from bp_fund_intent where fundType not in ('principalLending','principalRepayment')  ");
        if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
            sb.append(" and intentDate BETWEEN '" + startDate + " 00:00:00' and '" + endDate + " 23:59:59'");
        } else if (StringUtils.isNotBlank(endDate)) {
            sb.append(" and intentDate <= '" + endDate + " 23:59:59'");
        }
        BigDecimal money = (BigDecimal) this.getSession().createSQLQuery(sb.toString()).uniqueResult();
        if (money == null ){
            money= BigDecimal.ZERO;
        }
        return money;
    }

    @Override
    public BigDecimal getSumInterest(String startDate, String endDate) {
        StringBuffer sb = new StringBuffer("SELECT SUM(afterMoney) from bp_fund_intent where fundType = 'loanInterest'   ");
        if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
            sb.append(" and intentDate BETWEEN '" + startDate + " 00:00:00' and '" + endDate + " 23:59:59'");
        } else if (StringUtils.isNotBlank(endDate)) {
            sb.append(" and intentDate <= '" + endDate + " 23:59:59'");
        }
        BigDecimal money = (BigDecimal) this.getSession().createSQLQuery(sb.toString()).uniqueResult();
        if (money == null ){
            money= BigDecimal.ZERO;
        }
        return money;
    }

    @Override
    public Integer getAvgFullTime(String startDate, String endDate) {
        StringBuffer sb = new StringBuffer("SELECT SUM(TIMESTAMPDIFF(HOUR,createtime,fullTime))/count(*) from pl_bid_plan  where state != -1  ");
        if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
            sb.append("and fullTime BETWEEN '" + startDate + " 00:00:00' and '" + endDate + " 23:59:59'");
        } else if (StringUtils.isNotBlank(endDate)) {
            sb.append("and fullTime <= '" + endDate + " 23:59:59'");
        }
        BigDecimal count = (BigDecimal) this.getSession().createSQLQuery(sb.toString()).uniqueResult();
        if (count == null ){
            count= BigDecimal.ZERO;
        }
        return count.intValue();
    }

    @Override
    public Integer getAvgRate(String startDate, String endDate) {
        StringBuffer sb = new StringBuffer("SELECT SUM(yearInterestRate)/count(*) from pl_bid_plan where state != -1    ");
        if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
            sb.append("and createtime BETWEEN '" + startDate + " 00:00:00' and '" + endDate + " 23:59:59'");
        } else if (StringUtils.isNotBlank(endDate)) {
            sb.append("and createtime <= '" + endDate + " 23:59:59'");
        }
        BigDecimal count = (BigDecimal) this.getSession().createSQLQuery(sb.toString()).uniqueResult();
        if (count == null ){
            count= BigDecimal.ZERO;
        }
        return count.intValue();
    }

    @Override
    public BigDecimal getSumProfit(String startDate, String endDate) {
        StringBuffer sb = new StringBuffer("SELECT SUM(a.incomeMoney) from bp_fund_intent a ,(SELECT * from pl_bid_plan where state in(7, 10)   ");
        if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
            sb.append("and fullTime BETWEEN '" + startDate + " 00:00:00' and '" + endDate + " 23:59:59'");
        } else if (StringUtils.isNotBlank(endDate)) {
            sb.append("and fullTime <= '" + endDate + " 23:59:59'");
        }
        sb.append(") as b   where a.fundType in ('loanInterest','raiseinterest') and a.bidPlanId in (b.bidId)");
        BigDecimal count = (BigDecimal) this.getSession().createSQLQuery(sb.toString()).uniqueResult();
        if (count == null ){
            count= BigDecimal.ZERO;
        }
        return count;
    }
}