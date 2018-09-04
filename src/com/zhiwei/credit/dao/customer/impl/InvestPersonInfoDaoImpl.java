package com.zhiwei.credit.dao.customer.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;

import com.thirdPayInterface.CommonRequestInvestRecord;
import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.customer.InvestPersonInfoDao;
import com.zhiwei.credit.model.customer.InvestPersonInfo;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class InvestPersonInfoDaoImpl extends BaseDaoImpl<InvestPersonInfo> implements InvestPersonInfoDao{

	public InvestPersonInfoDaoImpl() {
		super(InvestPersonInfo.class);
	}

	/**
	 * 获取投资人Id列表
	 * @return
	 */
	public List<InvestPersonInfo> getByPersonId(Long personId){
		String hql = "from InvestPersonInfo i where i.investPersonId=?";
		return findByHql(hql, new Object[]{personId});
	}

	@Override
	public List<InvestPersonInfo> getByMoneyPlanId(Long moneyPlanId) {
		String hql = "from InvestPersonInfo i where i.moneyPlanId=?";
		return findByHql(hql, new Object[]{moneyPlanId});
	}

	@Override
	public List<InvestPersonInfo> getByBidPlanId(Long bidPlanId) {
		String hql = "from InvestPersonInfo i  where i.bidPlanId=?";
		return findByHql(hql, new Object[]{bidPlanId});
	}
	
	@Override
	public List<InvestPersonInfo> getByRequestNumber(String requestNo) {
		String hql = "from InvestPersonInfo i where i.orderNo=?";
		return this.getSession().createQuery(hql).setParameter(0, requestNo).list();
	}
	@Override
	public List<CommonRequestInvestRecord> getRepaymentList(String planId,String peridId) {
		List<CommonRequestInvestRecord> list=new ArrayList<CommonRequestInvestRecord>();
		StringBuffer sql=new StringBuffer("select bf.intentDate as intentDate,bf.fundIntentId,bm.thirdPayFlagId as invest_thirdPayConfigId,bf.requestDate," +
				" IFNULL(pb.newOrderNo,info.orderNo) as bidRequestNo," +//还款请求流水号
				" sum(IF (bf.accrualMoney>0,bf.accrualMoney,0) + IF (bf.fundType not IN ('couponInterest','principalCoupons','subjoinInterest','raiseinterest','commoninterest'),bf.notMoney,0)) AS amount," +//-- 还款金额
				" sum(IF (bf.fundType IN ('consultationMoney','serviceMoney')and bf.accrualMoney>0,bf.accrualMoney,0) +IF (bf.fundType IN ('consultationMoney','serviceMoney'),bf.notMoney,0)) AS fee" +//平台服务费
				" from invest_person_info as info" +
				" LEFT JOIN  pl_bid_info as pb on info.orderNo=pb.orderNo" +
				" LEFT JOIN bp_cust_member as bm on bm.id=IFNULL(pb.newInvestPersonId,info.investPersonId)" +
				" LEFT JOIN bp_fund_intent as bf on bf.bidPlanId=info.bidPlanId and info.orderNo=bf.orderNo and bf.isCheck=0 and bf.isValid=0 ");
		if(null!=peridId && !"".equals(peridId)){
			sql.append(" and bf.intentDate='"+peridId+" 00:00:00'");
		}
		if(null!=planId && !"".equals(planId)){
			sql.append(" where info.bidPlanId="+planId);
		}
		sql.append(" GROUP BY info.orderNo");
		System.out.println("还款列表="+sql);
		list=this.getSession().createSQLQuery(sql.toString())
			.addScalar("invest_thirdPayConfigId",Hibernate.STRING)
			.addScalar("bidRequestNo",Hibernate.STRING)
			.addScalar("requestDate",Hibernate.DATE)
			.addScalar("fundIntentId",Hibernate.LONG)
			.addScalar("amount",Hibernate.BIG_DECIMAL)
			.addScalar("fee",Hibernate.BIG_DECIMAL)
			.addScalar("intentDate",Hibernate.DATE)
			.setResultTransformer(Transformers.aliasToBean(CommonRequestInvestRecord.class))
			.list();
		return list;
	}

	@Override
	public List<InvestPersonInfo> getByPlanId(Long bidId) {
		String hql = "from InvestPersonInfo i where i.bidPlanId=?";
		return findByHql(hql, new Object[]{bidId});
	}

	@Override
	public BigDecimal getInvestTotalMoney(Long bidId) {
		return (BigDecimal)this.getSession().createSQLQuery("SELECT SUM(investMoney) from invest_person_info where bidPlanId=?").setLong(0, bidId).uniqueResult();
	}
	
}